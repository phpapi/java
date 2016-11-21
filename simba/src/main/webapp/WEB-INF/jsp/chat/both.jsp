<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../common/header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/sockjs/websocket.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/sockjs/chat.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common/dateformat.js"></script>
<title>测试在线聊天页面</title>
<script type="text/javascript">
	var ws = null;

	function connect() {
		var fromAccount = $("#fromAccount").val();
		if (!fromAccount) {
			alert("From 不能为空");
			return false;
		}
		var toAccount = $("#toAccount").val();
		if (!toAccount) {
			alert("To 不能为空");
			return false;
		}
		ws = Chat.connect("websocket", function() {
			alert("连接成功");
		}, receive, function() {
			alert("连接关闭");
		});
	}

	function receive(data) {
		var fromName = data.fromName;
		var time = data.sendTime;
		var content = data.content;
		var html = fromName + "(" + new Date(time).format("yyyy-MM-dd HH:mm:ss") + ")说：</br>" + content + "</br>";
		$("#chatRecordsDiv").append(html);
	}

	function send() {
		var fromAccount = $("#fromAccount").val();
		if (!fromAccount) {
			alert("From 不能为空");
			return false;
		}
		var toAccount = $("#toAccount").val();
		if (!toAccount) {
			alert("To 不能为空");
			return false;
		}
		var message = $("#message").val();
		if (!message) {
			alert("Message 不能为空");
			return false;
		}
		Chat.sendMessage(ws, fromAccount, toAccount, message);
	}

	function disconnect() {
		Chat.disconnect(ws);
	}
</script>
</head>
<body style="padding: 0px; margin: 0px">
	From:
	<input type="text" id="fromAccount" name="fromAccount" value="${sessUser.account}" /> To:
	<input type="text" id="toAccount" name="toAccount" />
	<br />Message:
	<textarea rows="10" cols="30" id="message" name="message"></textarea>
	<input type="button" onclick="connect()" value="connect" />
	<input type="button" onclick="send()" value="send" />
	<input type="button" onclick="disconnect()" value="disconnect" />
	<br />
	<br />
	<br />
	<div id="chatRecordsDiv"></div>
</body>
</html>
