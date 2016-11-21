<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>二维码</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/qrcode.jsp"%>
</head>
<body style="padding: 0px; margin: 0px">
	<img alt="" src="<%=request.getContextPath()%>/qrCode/getQRCode.do?text=http://www.baidu.com&height=100&width=100"><br/><br/><br/><br/><br/> 
	<div id="code"></div> 
	<script type="text/javascript">
		$(document).ready(function() {
			$('#code').qrcode({width:500,height:500,correctLevel:0,text:'http://www.sina.com'});  
		});
	</script>
</body>
</html>
