<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >
<html>
<head>
<title>${pd.name}</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/process.js"></script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="流程信息" style="width: 800px" data-options="collapsible:true,collapsed:true">
		<div style="padding: 10px 60px 20px 60px">
				<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tr>
						<td>流程ID:</td>
						<td>${pd.id}</td>
					</tr> 
					<tr>
						<td>流程Key:</td>
						<td>${pd.key}</td>
					</tr>
					<tr>
						<td>流程名称:</td>
						<td>${pd.name}</td>
					</tr>
					<c:if test="${not empty pd.description}">
						<tr>
							<td>流程描述:</td>
							<td>${pd.description}</td>
						</tr>
					</c:if>
					<tr>
						<td>流程发起人:</td>
						<td>${startUserName}</td>
					</tr>
				</table>
		</div>
	</div>
	<br/>
	<br/>
	<div class="easyui-panel" title="任务信息" style="width: 800px" data-options="collapsible:true,collapsed:true">
		<div style="padding: 10px 60px 20px 60px">
				<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tr>
						<td>任务ID:</td>
						<td>${task.id}</td>
					</tr> 
					<tr>
						<td>任务名称:</td>
						<td>${task.name}</td>
					</tr>
					<tr>
						<td>开始时间:</td>
						<td>${task.startTime}</td>
					</tr>
					<c:if test="${not empty task.endTime }">
						<tr>
							<td>结束时间:</td>
							<td>${task.endTime}</td>
						</tr>
					</c:if>
					<c:if test="${not empty task.assignee}">
						<tr>
							<td>办理人:</td>
							<td>${assigneeName}</td>
						</tr>
					</c:if>
					<c:if test="${not empty task.description}">
						<tr>
							<td>任务描述:</td>
							<td>${task.description}</td>
						</tr>
					</c:if>
				</table>
		</div>
	</div>
	<br/>
	<br/>
	<div class="easyui-panel" title="活动记录" style="width: 800px" data-options="collapsible:true,collapsed:true">
		<div style="padding: 0px 0px 0px 0px">
				<table id="activityTable"></table>
		</div>
	</div>
	<br/>
	<br/>
	<div class="easyui-panel" title="意见列表" style="width: 800px" data-options="collapsible:true,collapsed:true">
		<div style="padding: 0px 0px 0px 0px">
				<table id="commentTable"></table>
		</div>
	</div>
	<br/>
	<br/> 
	<div class="easyui-panel" title="附件列表" style="width: 800px" data-options="collapsible:true,collapsed:true">
		<div style="padding: 0px 0px 0px 0px">
				<table id="attachmentTable"></table>
		</div>
	</div>
	<br/>
	<br/>
	<div class="easyui-panel" title="表单数据" style="width: 800px" data-options="collapsible:true">
		<div style="padding: 10px 60px 20px 60px">
			<form id="processForm" method="post">
				${taskForm}
			</form>
			<div style="text-align: center; padding: 5px">
				<c:if test="${type == 'done'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Process.backTaskDone();"	data-options="iconCls:'icon-back'">返回</a>
				</c:if>
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#activityTable").datagrid({
				url : contextPath + "/processUtil/getHistoryActivity.do",
				method : "post",
				animate : true,
				singleSelect : true,
				pagination : false,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					processInstanceId : ${task.processInstanceId}
				},
				columns : [ [
				{
					field : 'activityName',
					title : '活动名称',
					width : 150
				},{
					field : 'assignee',
					title : '办理人',
					width : 150
				},{
					field : 'taskId',
					title : '任务ID',
					width : 150
				}, {
					field : 'startTime',
					title : '活动开始时间',
					width : 150
				}, {
					field : 'endTime',
					title : '活动结束时间',
					width : 150
				} ] ]
			});
			$("#commentTable").datagrid({
				url : contextPath + "/processComment/list.do",
				method : "post",
				animate : true,
				singleSelect : true,
				pagination : false,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					processInstanceId : ${task.processInstanceId}
				},
				columns : [ [
				{
					field : 'taskId',
					title : '任务ID',
					width : 150
				},{
					field : 'taskName',
					title : '任务名称',
					width : 150
				},{
					field : 'userName',
					title : '提交人',
					width : 150
				}, {
					field : 'time',
					title : '提交时间',
					width : 150
				}, {
					field : 'fullMessage',
					title : '意见内容',
					width : 150
				} ] ]
			});
			$("#attachmentTable").datagrid({
				url : contextPath + "/processAttachment/list.do",
				method : "post",
				animate : true,
				singleSelect : true,
				pagination : false,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					processInstanceId : ${task.processInstanceId}
				},
				columns : [ [
				{
					field : 'name',
					title : '文件名',
					width : 210
				},{
					field : 'userName',
					title : '上传者',
					width : 150
				},{
					field : 'time',
					title : '上传时间',
					width : 180
				}, {
					title : "操作",
					field : "oper",
					width : 150,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"Process.downloadAttachment('" + row["id"] + "')\">下载</a>";
						return html;
					}
				}] ]
			});
			
			$("#processForm").find("input").attr("readonly","readonly");
		});
	</script>
</body>
</html>
