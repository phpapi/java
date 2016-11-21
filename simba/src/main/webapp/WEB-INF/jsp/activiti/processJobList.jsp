<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>作业管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processJob.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processManagement.js"></script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div id="panel">
		<table id="table"></table>
	</div>
	<div id="processXmlWindow" class="easyui-window" title="查看流程XML" style="width:750px;height:750px" data-options="modal:false,collapsible:false,minimizable:false,maximizable:false,closable:true,closed:true">
		<textarea id="processXml" style="height:704px;width:727px;" readonly="readonly"></textarea>
	</div>
	<div id="processImageWindow" class="easyui-window" title="查看流程图" style="width:750px;height:750px" data-options="modal:false,collapsible:false,minimizable:false,maximizable:true,closable:true,closed:true">
		<img alt="" src="" id="processImage"/>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#panel").panel({
				height : $(document).height() - 50,
				width : $(document).width() - 20,
				title : "作业管理"
			});
			$("#table").datagrid({
				url : contextPath + "/processJob/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				singleSelect : true,
				pagination : true,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					processName : $("#processName").val()
				},
				columns : [ [ {
					field : 'jobID',
					title : '作业ID',
					width : 100,
					formatter : function(value, row, index) {
						return row.id;
					}
				}, {
					field : 'duedate',
					title : '预定时间',
					width : 150
				}, {
					field : 'jobHandlerType',
					title : '作业类型',
					width : 100
				}, {
					field : 'retries',
					title : '可重试次数',
					width : 80
				}, {
					field : 'processInstanceId',
					title : '流程实例ID',
					width : 100
				}, {
					field : 'processDefinitionId',
					title : '流程ID',
					width : 200
				}, {
					field : 'executionId',
					title : '执行ID',
					width : 150
				}, {
					field : 'jobHandlerConfiguration',
					title : '作业配置信息',
					width : 200
				}, {
					field : 'exceptionMessage',
					title : '异常信息',
					width : 150
				}, {
					title : "操作",
					field : "oper",
					width : 150,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"ProcessJob.execute('" + row["id"] + "')\">执行</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessJob.deleteJob('" + row["id"] + "')\">删除</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.showView('" + row["processDefinitionId"] + "')\">查看流程图</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.showXml('" + row["processDefinitionId"] + "')\">查看XML</a>";
						return html;
					}
				} ] ]
			});
			
		});
	</script>
</body>
</html>
