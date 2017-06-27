$(function(){

	const C_DISTANCE = 90;

	const P_STARTX = 70;
	const P_STARTY = 70;
	
	var url = (window.location.protocol === "https:" ? "wss:" : "ws:") + "//" + window.location.host + window.location.pathname + "message-endpoint" + location.search;
	var websocket = new WebSocket(url);
	// websocket.onopen = function() {
	// 	websocket.send("GET");
	// }
	var flag = 0;
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
	        		  radius: 35,
	        		  opacity: chessOpacity,
	           		  click:  function(layer) {
	             			var places = layer.name.split('|')
	             			//初始化
	             			if (flag == 0) {
	             				flag = currentFlag;
	             			}
	             			
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
	             							radius: 5
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
		  width: 1000,
		  height: 1000
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
	
//	$('#canvas').drawImage({
//		layer: true,
//		  source: 'resources/board.jpg',
//		  opacity: 0.1,
//		  x: 100, 
//		  y: 100
//		});
	
	$('#canvas').drawLayers();

	
//	 for(var i=0;i<5;i++){          //一维长度为5
//         for(var j=0;j<5;j++){      //二维长度为5
//       	  $('#canvas').drawArc({
//       		  layer: true,
//       		  index: 999,
//       		  groups: ['board'],
//       		  name: 'board|' + i + "|" + j,
//       		  fillStyle:  '#585',
//       		 opacity: 0,
//       		  x: 100 * i + 100, y: 100 * j + 100,
//       		  radius: 30,
//
//       		});
//       
//	       }
//	 }
	 
	 websocket.onopen = function(event){
		 websocket.send("come");
	 }
//	var ctx = document.getElementById("canvas").getContext("2d");
//
//	const P_STARTX = 70;
//	const P_STARTY = 70;
//	const C_DISTANCE = 90;
//
//	ctx.lineWidth="2";
//	ctx.fillStyle="gray";
//	ctx.fillRect(5,5,500,500);
//	ctx.strokeStyle="black";
//	ctx.beginPath();
//	for(var i = 0; i < 5; i++){
//		ctx.moveTo(P_STARTX, P_STARTY + C_DISTANCE * i);
//		ctx.lineTo(P_STARTX + C_DISTANCE * 4,  P_STARTY + C_DISTANCE * i);
//	}
//	//竖线
//	for(var i = 0; i < 5; i++){
//		ctx.moveTo(P_STARTX + C_DISTANCE * i, P_STARTY);
//		ctx.lineTo(P_STARTX + C_DISTANCE * i,  P_STARTY + C_DISTANCE*4);
//	}
//	
//	ctx.stroke();
	
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
});


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