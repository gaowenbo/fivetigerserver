$(function(){
	const C_DISTANCE = 150;
	const P_STARTX = 100;
	const P_STARTY = 100;
	const R_RADIUS = 60;
	
	var existCookie = $.cookie("guid");
	if (existCookie == null) {
		existCookie = guid();
		$.cookie("guid", existCookie);
	}
	var roomSearch = "";
	if (location.search == "") {
		roomSearch = "r";
	} else {
		roomSearch = location.search.substring(1);
	}

	var url = (window.location.protocol === "https:" ? "wss:" : "ws:") + "//" + window.location.host + window.location.pathname + "game?" + existCookie + ";" + roomSearch;
	var websocket = new WebSocket(url);
	var flag = null;
	var currentFlag = 1;
	var phase = 0;
	var fensiveGoal = 0;
	var defensiveGoal = 0;
	var mLastX = null;
	var mLastY = null;
	websocket.onmessage = function(event) {

		var msg =  JSON.parse(event.data);
		currentFlag = msg.movingFlag;
		phase = msg.phase;
		fensiveGoal = msg.fensiveGoal;
		defensiveGoal = msg.defensiveGoal;
		if (msg.hasOwnProperty("flag")){
			flag = msg.flag;
		}
		$('#canvas').removeLayerGroup('chesses');
		   //先声明一维
	       for(var i=0;i<5;i++){          //一维长度为5
	          for(var j=0;j<5;j++){      //二维长度为5
	        	  var chess = msg.chessBoard[i*5+j];
	        	  
	        	  var chessOpacity = 1;
	        	  if (chess == '0') {
	        		  chessOpacity = 0;
	        	  }
	        	  var chessfillStyle = '#000';
	        	  
	        	  if (chess == '1') {
	        		  chessfillStyle = '#000';
	        	  } else if (chess == '2') {
	        		  chessfillStyle = '#fff';
	        	  }
	        	  
	        	  
	        	  $('#canvas').drawArc({
	        		  layer: true,
	        		  groups: ['chesses'],
	        		  name: 'chess|' + i + '|' + j,
	        		  fillStyle: chessfillStyle,
	        		  x: C_DISTANCE * i + P_STARTX, 
	        		  y: C_DISTANCE * j + P_STARTY,
	        		  radius: R_RADIUS,
	        		  opacity: chessOpacity,
	           		  click:  function(layer) {
	             			var places = layer.name.split('|')
	             			if (flag == currentFlag) {
	             				if (phase == 0) {
	                 				websocket.send(currentFlag + "s" +  places[1] + places[2]);
	             				} else if(phase == 1) {
	             					websocket.send(currentFlag + "p" +  places[1] + places[2]);
	             				} else {
	             					if (mLastX == null) {
	             						mLastX = places[1];
	             						mLastY = places[2];
	             						$('#canvas').drawArc({
	             							 layer: true,
	             							groups: ['chesses'],
	             			        		 name: 'effect|' + mLastX + '|' + mLastY,
	             							x: C_DISTANCE * parseInt(mLastX) + P_STARTX, 
	             							y: C_DISTANCE * parseInt(mLastY) + P_STARTY,
	             							fillStyle: '#FF9999',
	             							radius: 10
	             						});
	             					} else {
	             						var moveAction = getAction(mLastX, mLastY, places[1], places[2])
	             						if (moveAction != null) {
	                 						websocket.send(currentFlag + moveAction + mLastX + mLastY);
	             						} 
	             						mLastX = null;
	             						mLastY = null;
	             					}
	             					
	             				}
	             			}
	             		  }
	        		});
	        
		       }
		}
	    $('#canvas').drawLayers();
	    setText();
	    
		$("#demo").text(event.data);
		
	};
	
	$("#sendButton").click(function(){
		websocket.send($("#msg").val());
		$("#msg").val("");
	});
	
	$('#canvas').drawRect({
		 layer: true,
		fillStyle: '#CC9966',
		  x: 0, y: 0,
		  width: 1600,
		  height: 1600
		});
	
	for(var i = 0; i < 5; i++){
		$('canvas').drawLine({
			layer: true,
		
			  strokeStyle: '#000',
			  strokeWidth: 2,
			  x1: P_STARTX, y1: P_STARTY + C_DISTANCE * i,
			  x2: P_STARTX + C_DISTANCE * 4, y2: P_STARTY + C_DISTANCE * i
			});
	}
	for(var i = 0; i < 5; i++){
		$('canvas').drawLine({
			layer: true,
		
			  strokeStyle: '#000',
			  strokeWidth: 2,
			  x1: P_STARTX + C_DISTANCE * i, y1: P_STARTY,
			  x2: P_STARTX + C_DISTANCE * i, y2: P_STARTY + C_DISTANCE*4
			});
	}
	

	$('#canvas').drawLayers();

	 
	 websocket.onopen = function(event){
		 websocket.send("come");
	 }
	
	function setText() {
		if (currentFlag == 1) {
			$("#flag").text("当前行棋：黑");
		} else {
			$("#flag").text("当前行棋：白");
		}
		
		$("#goal").text("黑棋分数：" + fensiveGoal + ", 白棋分数：" +　defensiveGoal);
		
		if (phase == 0) {
			$("#phase").text("落子阶段");
		} else if (phase == 1) {
			$("#phase").text("提子阶段");
		} else {
			$("#phase").text("走子 阶段");
		}
		
		if (result == 1) {
			$("#result").text("黑方获胜！");
		} else if (result == 2) {
			$("#result").text("白方获胜！");
		}
	}
	
	function getAction(lastX, lastY, x, y) {
		if (lastX == x) {
			if (parseInt(lastY) == parseInt(y) + 1) {
				return "u";
			} else if (parseInt(lastY) == parseInt(y) - 1) {
				return "d";
			} else {
				return null;
			}
		} else if (lastY = y) {
			if (parseInt(lastX) == parseInt(x) + 1) {
				return "l";
			} else if (parseInt(lastX) == parseInt(x) - 1) {
				return "r";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	//$('#canvas').hide();
	
	
	window.onbeforeunload = function(event) { 
		websocket.onclose =function(){};
		websocket.close();
	}
});

function S4() {
   return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
   return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4());
}
