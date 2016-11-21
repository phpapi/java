<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>流程启动管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processStart.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processManagement.js"></script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div id="panel">
		<table id="table"></table>
		<div id="toolbar">
				<label>流程名称:</label> <input type="text" id="processName" name="processName" class="easyui-textbox" prompt="请输入您要查询的流程名称" /> <a
				href="javascript:void(0);" class="easyui-linkbutton" onclick="ProcessStart.search();" data-options="iconCls:'icon-search'">查询</a>
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
				title : "流程信息"
			});
			$("#table").datagrid({
				url : contextPath + "/processStart/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				toolbar : "#toolbar",
				singleSelect : true,
				pagination : false,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					processName : $("#processName").val()
				},
				columns : [ [ {
					field : 'processID',
					title : '流程ID',
					width : 150,
					formatter : function(value, row, index) {
						return row.id;
					}
				}, {
					field : 'key',
					title : '流程Key',
					width : 150
				}, {
					field : 'deploymentId',
					title : '部署ID',
					width : 150
				}, {
					field : 'version',
					title : '版本',
					width : 150
				}, {
					field : 'name',
					title : '名称',
					width : 150
				}, {
					field : 'description',
					title : '描述',
					width : 300
				}, {
					title : "操作",
					field : "oper",
					width : 250,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"ProcessStart.start('" + row["id"] + "')\">发起流程</a>";
						html += "&nbsp;&nbsp;";
						html +="<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.showView('" + row["id"] + "')\">查看流程图</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.showXml('" + row["id"] + "')\">查看XML</a>";
						return html;
					}
				} ] ]
			});
			
		});
	</script>
</body>
</html>
