<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>演示系统</title>
<%@ include file="./common/header.jsp"%>
<%@ include file="./common/easyui.jsp"%>
<%@ include file="./common/ext.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/user.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/index.css" />
</head>
<body>
	<script type="text/javascript">
		Ext.onReady(function() {
			Index.initMenuFrame("${sessUser.name}","<%=com.caozj.framework.session.SessionUtil.isAdmin(session)%>");
		});
	</script>
</body>
</html>
