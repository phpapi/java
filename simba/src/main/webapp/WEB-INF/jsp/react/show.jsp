<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>React演示</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/react.jsp"%>
</head>
<body>
	<div id="example"></div>
	<div id="content"></div>
	<script src="<%=request.getContextPath()%>/js/reactBuild/firstDemo.js"></script>
</body>
</html>