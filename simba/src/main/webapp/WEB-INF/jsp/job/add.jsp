<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>任务管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/job.js"></script>
</head>
<body style="padding:0px;margin:0px">
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="新增任务" style="width:700px">
		<div style="padding:10px 60px 20px 60px">
			<form id="jobForm" method="post">
				<table cellpadding="0" cellspacing="0" style="table-layout:fixed;">
					<tr>
						<td>名称:</td>
						<td><input class="easyui-textbox" type="text" id="name" name="name" data-options="required:true" style="width:200px"></input></td>
					</tr>
					<tr>
						<td>描述:</td>
						<td><input class="easyui-textbox" type="text" id="description" name="description" data-options="required:true" style="width:200px"></input></td>
					</tr>
					<tr>
						<td>cron表达式:</td>
						<td><input class="easyui-textbox" type="text" id="cronExpression" name="cronExpression"  style="width:200px"></input></td>
					</tr>
					<tr>
						<td>开始执行时间:</td>
						<td><input class="easyui-datetimebox" type="text" id="startTime" name="startTime" style="width:200px"></input></td>
					</tr>
					<tr>
						<td>结束执行时间:</td>
						<td><input class="easyui-datetimebox" type="text" id="endTime" name="endTime"  style="width:200px"></input></td>
					</tr>
					<tr>
						<td>最大执行次数:</td>
						<td><input class="easyui-numberspinner" type="text" id="maxExeCount" name="maxExeCount"  style="width:200px"></input></td>
					</tr>
					<tr>
						<td>完整类路径:</td>
						<td><input class="easyui-textbox" type="text" id="className" name="className" data-options="required:true" style="width:200px"></input></td>
					</tr>
					<tr>
						<td>执行类方法名:</td>
						<td><input class="easyui-textbox" type="text" id="methodName" name="methodName" data-options="required:true" style="width:200px"></input></td>
					</tr>
					<tr>
						<td>延迟时间:</td>
						<td><input class="easyui-numberspinner" type="text" id="delayTime" name="delayTime"  style="width:200px"></input></td>
					</tr>
					<tr>
						<td>间隔时间:</td>
						<td><input class="easyui-numberspinner" type="text" id="intervalTime" name="intervalTime"  style="width:200px"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Job.add();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="Job.toList();" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</body>
</html>
