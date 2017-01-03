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
		
        <title>Home</title>
    </head>
    <body>
    <nav>
		<div class="nav-wrapper" style="text-align: center; height: 50px; background-color: rgb(54, 132, 55);">
			<span style="margin: auto; line-height: 2.4; font-size: 20px; color:rgb(255, 244, 207);">自动化部署平台</span>
		</div>
	</nav>
        <form id = "deploy-form"  enctype="application/x-www-form-urlencoded"    method="post" action="deploy"  data-wsurl = "ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/log">
	    <div class="container" style="padding-top: 20px;">
			<div class="row">
				<div class="col-md-12">
					<span class="row">发布项目</span>
					
					<div class="row">
						<div class="col-md-6">
							<label>项目名称</label><input class="form-control" name="projectName" type="text" value = "" 
							 placeholder="填写项目名称，此名称为项目的唯一标识，不要去其他项目重复" required="required" />
							 <br/>
						</div>

						<div class="col-md-6">
							<label>项目svn路径 (注：需要写ip,如  server-code 改成 192.168.133.163)</label><input class="form-control" name="svnPath" type="text" value = ""
							 placeholder="项目工程在svn路径地址， 其下应该有pom.xml" required="required" />
							<br />
						</div>
						<div class="col-md-6">
							<label>项目附加svn路径</label><input class="form-control" name="svnPath2" type="text" value = ""
							placeholder="项目依赖的其他svn路径，可选" />
							<br/>
						</div>
						<div class="col-md-6">
							<label>svn用户名</label><input class="form-control" name="svnUsername" type="text" value = "" required="required"> <br/>
						</div>
						<div class="col-md-6">
							<label>svn密码</label><input class="form-control" name="svnPassword" type="password" required="required" /> <br/>
						</div>
						<div class="col-md-6">
							<label>发版配置</label><input class="form-control" name="productConfig" type="text" value = "" 
							placeholder="发版的配置，如debug, release, server-101等等，根据prepareConfig里面的文件夹,可选" />
							<br/>
						</div>
						<div class="col-md-6">
							<label>服务器地址</label><input class="form-control" name="server" type="text" value="" placeholder="需要发布的服务器地址" required="required" />
							<br/>
						</div>  
						<div class="col-md-6">
							<label>端口号</label><input class="form-control" name="port" type="text" value="22" placeholder="ssh 连接服务器的端口号" required="required"/> <br/>
						</div>
						<div class="col-md-6">
							<label>服务器用户名</label><input class="form-control" name="serverUsername" value = "" type="text"
							placeholder="ssh 远程登陆的用户名" required="required"/> <br/>
						</div>
						<div class="col-md-6">
							<label>服务器密码</label><input class="form-control" name="serverPassword" type="password"
							placeholder="ssh 登陆的密码" required="required" />
							<br />
						</div>
						<div class="col-md-6">
							<label>需要发布的服务器路径</label><input class="form-control" name="webServerName" type="text" value = ""
							placeholder="也就是打出来以后war包的文件名" required="required" /> <br/>
						</div>
						<div class="col-md-6">
							<label>服务器tomcat根目录</label><input class="form-control" name="tomcatHome" type="text" value = ""
							placeholder="服务器tomcat位置" required="required" /> <br/>
						</div>
						<div class="col-md-6">
							<label>重启tomcat</label><input class="form-control" name="forceRestart" type="text" value = ""
							placeholder="重启填true, 如果不需要重启tomcat 请不要填写"/> <br/>
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
					<!-- <a href="./showLog" class="btn btn-warning">查看日志工具</a> -->
					<input class="btn btn-danger" value="发布" type="submit"/>
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
	 			  $('#deploy-form [name=' + name + ']').val(val);
	 			})
	 		  }
	 		 $("#import").val("");
	 	});
	 	$("#export").click(function() {
	 		var file = new File([$("#deploy-form").serialize().replace(new RegExp('&',"gm"),'\n')], "deploy.properties", {type: "text/plain;charset=utf-8"});
	 		saveAs(file, "deploy.properties");
	 	});
	 	
	 	$("#deploy-form").on('submit', function() {
	        $(this).ajaxSubmit({
	            type: 'post', // 提交方式 get/post
	            url: $(this).attr("action"), // 需要提交的 url
	            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	                // 此处可对 data 作相关处理
	                alert('命令执行完毕');
	            }
	        });
	        
	        var url = $(this).attr("data-wsurl") + "?projectName="  + $("#deploy-form [name=projectName]").val() + "&type=deploy";
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
