<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>移动效果</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/jqueryPlugins/overWindow.js"></script>

</head>
<body style="padding: 0px; margin: 0px">
	<div style="margin: 20px 0;"></div>
	<div style="padding: 10px 60px 20px 60px">
		<button id="moveButton">移动效果</button>
		<div id="windowDiv"></div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			OverWindow.init("moveButton", "windowDiv", {
				resizable : false,
				closed : true,
				modal : false,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				closable : true,
				title : "显示网页",
				height : 500,
				width : 800,
				content : Common.getFrameWithUrl("http://www.baidu.com")
			});
		});
	</script>
</body>
</html>
