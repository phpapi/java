<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>代办设置管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processAgencySet.js"></script>
</head>
<body style="padding:0px;margin:0px">
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="新增代办设置" style="width:700px">
		<div style="padding:10px 60px 20px 60px">
			<form id="processAgencySetForm" method="post">
				<table cellpadding="0" cellspacing="0" style="table-layout:fixed;">
					<tr>
						<td>开始时间:</td>
						<td><input class="easyui-datetimebox" type="text" id="startTime" name="startTime" data-options="required:true" style="width:200px"></input></td>
					</tr>
					<tr>
						<td>结束时间:</td>
						<td><input class="easyui-datetimebox" type="text" id="endTime" name="endTime" data-options="required:true" style="width:200px"></input></td>
					</tr>
					<tr>
						<td>代办人:</td>
						<td><input class="easyui-textbox" type="text" id="agencyAccount" name="agencyAccount" data-options="required:true" style="width:200px"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="ProcessAgencySet.add();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="ProcessAgencySet.toList();" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</body>
</html>
