<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>五虎棋</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/jcanvas.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/main.js"></script>

</head>
<body>
<!--         <button id="btnGet" onclick="getMessages()">Get Messages</button> -->
<!--         <button id="btnStop" onclick="stopMessages()" disabled="true">Stop Messages</button> -->
<!--         <p id="demo"></p> -->
<!--         <input id="msg" type="text" /> -->
<!-- 		<button id="sendButton">Send</button> -->
<!-- 		<div> -->
<!-- 		</div> -->
	<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="page-header" align="center">
				<h1>
					五虎棋
				</h1>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12" align="center">
		
			<canvas id = "canvas" width="500" height="500">
			
			</canvas>
		</div>
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-12">
					<h2 id="flag">
						
					</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
				<h2 id="goal">
						
					</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
				<h2 id="phase">
					</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
				<h2 id="result">
						
					</h2>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>