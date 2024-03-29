<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>流程监控</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processMonitor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processManagement.js"></script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div id="panel">
		<table id="table"></table>
		<div id="toolbar">
				<label>流程名称:</label> <input type="text" id="processName" name="processName" class="easyui-textbox" prompt="请输入您要查询的流程名称" /> <a
				href="javascript:void(0);" class="easyui-linkbutton" onclick="ProcessMonitor.search();" data-options="iconCls:'icon-search'">查询</a>
		</div>
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
				title : "流程监控"
			});
			$("#table").datagrid({
				url : contextPath + "/processMonitor/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				toolbar : "#toolbar",
				singleSelect : true,
				pagination : true,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					processName : $("#processName").val()
				},
				columns : [ [ {
					field : 'title',
					title : '标题',
					width : 150
				},{
					field : 'processInstanceID',
					title : '流程实例ID',
					width : 150,
					formatter : function(value, row, index) {
						return row.id;
					}
				}, {
					field : 'processDefinitionId',
					title : '流程ID',
					width : 150
				}, {
					field : 'processDefinitionKey',
					title : '流程Key',
					width : 150
				}, {
					field : 'processDefinitionName',
					title : '流程名称',
					width : 150
				}, {
					field : 'description',
					title : '描述',
					width : 300
				},{
					field : 'status',
					title : '状态',
					width : 100,
					formatter : function(value, row, index) {
						var html = "";
						if(row.suspended){
							html = "暂停";
						}else{
							html = "启动";
						}
						return html;
					}
				}, {
					title : "操作",
					field : "oper",
					width : 350,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"ProcessMonitor.toView('" + row["id"] + "')\">查看</a>";
						html += "&nbsp;&nbsp;";
						if(row.suspended){
							html +="<a href=\"javascript:void(0)\" onclick=\"ProcessMonitor.start('" + row["id"] + "')\">启动</a>";
						}else{
							html +="<a href=\"javascript:void(0)\" onclick=\"ProcessMonitor.stop('" + row["id"] + "')\">暂停</a>";
						}
						html += "&nbsp;&nbsp;";
						html +="<a href=\"javascript:void(0)\" onclick=\"ProcessMonitor.deleteProcessInstance('" + row["id"] + "')\">删除</a>";
						html += "&nbsp;&nbsp;";
						html +="<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.showView('" + row["processDefinitionId"] + "')\">查看流程图</a>";
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
