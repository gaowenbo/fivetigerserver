<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- 新 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
		
		<!-- 可选的Bootstrap主题文件（一般不用引入） -->
		<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
		
		<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
		<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
		
		<script src="http://cdn.bootcss.com/FileSaver.js/2014-11-29/FileSaver.min.js"></script>
		
		<script src="${pageContext.request.contextPath}/resources/jquery.form.js"></script>
		
        <title>log</title>
    </head>
    <body>
    <nav>
		<div class="nav-wrapper" style="text-align: center; height: 50px; background-color: rgb(54, 132, 55);">
			<span style="margin: auto; line-height: 2.4; font-size: 20px; color:rgb(255, 244, 207);">自动化部署平台</span>
		</div>
	</nav>
        <form id = "log-form" method="post" action="deploy"  data-wsurl = "ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/log">
	    <div class="container" style="padding-top: 20px;">
			<div class="row">
				<div class="col-md-12">
					<span class="row">查看日志</span>
					
					<div class="row">
						<div class="col-md-6">
							<label>服务器地址</label><input class="form-control" name="server" type="text" value="127.0.0.1" placeholder="需要发布的服务器地址" required="required" />
							<br/>
						</div>  
						<div class="col-md-6">
							<label>端口号</label><input class="form-control" name="port" type="text" value="22" placeholder="ssh 连接服务器的端口号" required="required"/> <br/>
						</div>
						<div class="col-md-6">
							<label>服务器用户名</label><input class="form-control" name="serverUsername" value = "gaowb-b" type="text"
							placeholder="ssh 远程登陆的用户名" required="required"/> <br/>
						</div>
						<div class="col-md-6">
							<label>服务器密码</label><input class="form-control" name="serverPassword" type="password"
							placeholder="ssh 登陆的密码" required="required" />
							<br />
						</div>
						<div class="col-md-6">
							<label>日志路径</label><input class="form-control" name="logPath" type="text" value = ""
							placeholder="日志路径" required="required" /> <br/>
						</div>
						<div class="col-md-6">
							<label>初始读取行数</label><input class="form-control" name="line" type="text" value = "200"
							required="required" /> <br/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6" style="padding: 10; margin: 0 auto">
					<span class="btn btn-info" style="position: relative; overflow: hidden;"> 
						<span>导入配置</span>
						<input id="import"
						style="cursor: pointer; opacity: 0; position: absolute; top: 0; right: 0; height: 100px"
						type="file" />
					</span> 
					<input id="export" class="btn btn-info" value="导出配置" type="button"/>
				</div>
				<div class="col-md-6"
					style="text-align: right; padding: 10; margin: 0 auto">
					<input class="btn btn-danger" value="查看" type="submit"/>
				</div>
			</div>
	    </div>
	</form>
	<footer class="page-footer" style="padding-top: 0; margin-top: 40px; text-align: center">
      <div class="footer-copyright">
        <div class="container">
        Copyright © 2016 <a class="grey-text text-lighten-4" href="http://" target="_blank"></a>. All rights reserved.
        <a class="grey-text text-lighten-4 right" href="https://github.com/gaowenbo" target="_blank">by Qici</a>
        </div>
      </div>
</footer>
<div>
</div>
	<div id="layer-modal" class="modal">
	
		<div class="modal-header" style="text-align: right">
			<a href="#!" class="btn-warning modal-action modal-close waves-effect waves-green btn-flat">点击此处关闭</a>
		</div>
		<div class="modal-content" style=" overflow-y: scroll; background-color: black; color: #ccc; font-size: 0.9em;">
		</div>
	</div>
 <script type="text/javascript">
 
 
 $(function(){
	 
	 	$("#import").change(function() {
	 		reader = new FileReader();
	 		reader.readAsText($(this).prop('files')[0], "UTF-8");//读取文件 
	 		  reader.onload = function(evt){ //读取完文件之后会回来这里
	 		    var fileString = evt.target.result;
	 		  fileString.split('\n').forEach(function(param){
	 			  param = param.split('=');
	 			  var name = param[0],
	 			      val = decodeURIComponent(param[1]);
	 			  $('#log-form [name=' + name + ']').val(val);
	 			})
	 		  }
	 	});
	 	$("#export").click(function() {
	 		var file = new File([$("#log-form").serialize().replace(new RegExp('&',"gm"),'\n')], "showLog.properties", {type: "text/plain;charset=utf-8"});
	 		saveAs(file, "showLog.properties");

	 	});
	 	
	 	$("#log-form").on('submit', function() {
	 		
	        var url = $(this).attr("data-wsurl") + "?type=showLog&" + $("#log-form").serialize();
			var websocket = new WebSocket(url);
			websocket.onmessage = function(event) {
				var msg = event.data;
				$("#layer-modal .modal-content>div").append(msg);
				$("#layer-modal .modal-content").scrollTop($("#layer-modal .modal-content>div").height() - $("#layer-modal .modal-content").height());
			};

			$("#layer-modal .modal-content").html("<div></div>");
			
			//$('#layer-modal').modal({backdrop: 'static', keyboard: false});
			$("#layer-modal").modal('show');
			
			$("#layer-modal .modal-content").height($(window).height() - $("#layer-modal .modal-header").outerHeight() - 2);
			$('#layer-modal').modal({backdrop: 'static', keyboard: false});
			$('#layer-modal').on('hidden.bs.modal', function () {

				websocket.close();
				
			});
	        return false; // 阻止表单自动提交事件
	    });
	 	
	 
 });
	

				 </script>
    </body>
</html>
