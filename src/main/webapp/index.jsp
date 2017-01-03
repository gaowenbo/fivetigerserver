<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<script>
var url = "ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/message-endpoint";
var websocket = new WebSocket(url);
websocket.onopen = function() {
	websocket.send("GET");
}

websocket.onmessage = function(event) {
	var msg = event.data;
	alert(msg);
};
</script>
<script type="text/javascript">
    //set up websocket
//     var url = (window.location.protocol === "https:" ? "wss:" : "ws:") + "//" + window.location.host + window.location.pathname + "message-endpoint";
//     var webSocket = new WebSocket(url);
//     webSocket.onopen = function () {
//         console.log("WebSocket is connected.");
//     };
//     webSocket.onmessage = function (event) {
//         console.log(event.data);
//         var demo = document.getElementById("demo");
//         demo.innerHTML = demo.innerHTML + "<br/>" + event.data;
//     };
//     //get & stop messages
//     function getMessages() {
//         webSocket.send("GET");
//         document.getElementById("btnGet").disabled = true;
//         document.getElementById("btnStop").disabled = false;
//     }
//     function stopMessages() {
//         webSocket.send("STOP");
//         document.getElementById("btnGet").disabled = false;
//         document.getElementById("btnStop").disabled = true;
//     }
</script>
</head>
<body>
	<h2>Tomcat-based WebSocket on Heroku Demo</h2>
        <button id="btnGet" onclick="getMessages()">Get Messages</button>
        <button id="btnStop" onclick="stopMessages()" disabled="true">Stop Messages</button>
        <p id="demo"></p>

</body>
</html>