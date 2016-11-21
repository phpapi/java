<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>流程管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processManagement.js"></script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div id="panel">
		<table id="table"></table>
		<div id="toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="ProcessManagement.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="ProcessManagement.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
				<label>流程名称:</label> <input type="text" id="processName" name="processName" class="easyui-textbox" prompt="请输入您要查询的流程名称" /> <a
				href="javascript:void(0);" class="easyui-linkbutton" onclick="ProcessManagement.search();" data-options="iconCls:'icon-search'">查询</a>
		</div>
		<div id="processWindow" class="easyui-window" title="上传流程文件" style="width:400px;height:100px" data-options="iconCls:'icon-save',modal:true,collapsible:false,minimizable:false,maximizable:false,closable:true,closed:true">
			<form id="uploadProcessForm" method="post" enctype="multipart/form-data">
				<table cellpadding="0" cellspacing="0" style="table-layout:fixed;" >
					<tr>
						<td>流程文件:</td>
						<td><input class="easyui-filebox" style="width:300px" id="processFile" name="processFile" data-options="buttonText:'选择流程文件',prompt:''"></td>
					</tr>
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="ProcessManagement.uploadProcessFile();" data-options="iconCls:'icon-save'">上传</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="ProcessManagement.cancelUploadProcess();" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>
		<div id="processXmlWindow" class="easyui-window" title="查看流程XML" style="width:750px;height:750px" data-options="modal:false,collapsible:false,minimizable:false,maximizable:false,closable:true,closed:true">
			<textarea id="processXml" style="height:704px;width:727px;" readonly="readonly"></textarea>
		</div>
		<div id="processImageWindow" class="easyui-window" title="查看流程图" style="width:750px;height:750px" data-options="modal:false,collapsible:false,minimizable:false,maximizable:true,closable:true,closed:true">
			<img alt="" src="" id="processImage"/>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#panel").panel({
				height : $(document).height() - 50,
				width : $(document).width() - 20,
				title : "流程信息"
			});
			$("#table").datagrid({
				url : contextPath + "/processManagement/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				toolbar : "#toolbar",
				singleSelect : false,
				pagination : true,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					processName : $("#processName").val()
				},
				columns : [ [ {
					title : "全选",
					field : "ck",
					checkbox : true
				}, {
					field : 'processID',
					title : '流程ID',
					width : 150,
					formatter : function(value, row, index) {
						return row.id;
					}
				}, {
					field : 'key',
					title : '流程Key',
					width : 100
				}, {
					field : 'deploymentId',
					title : '部署ID',
					width : 100
				}, {
					field : 'version',
					title : '版本',
					width : 50
				}, {
					field : 'name',
					title : '名称',
					width : 150
				}, {
					field : 'description',
					title : '描述',
					width : 200
				}, {
					field : 'resourceName',
					title : 'XML资源名称',
					width : 250
				}, {
					field : 'diagramResourceName',
					title : '图片资源名称',
					width : 250
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
					width : 250,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.showView('" + row["id"] + "')\">查看流程图</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.showXml('" + row["id"] + "')\">查看XML</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.deleteProcess('" + row["id"] + "')\">删除</a>";
						html += "&nbsp;&nbsp;";
						if(row.suspended){
							html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.start('" + row["id"] + "')\">启动</a>";
						}else{
							html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.stop('" + row["id"] + "')\">暂停</a>";
						}
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.setPros('" + row["id"] + "')\">设置属性</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessManagement.setUser('" + row["id"] + "')\">设置执行人</a>";
						return html;
					}
				} ] ]
			});
			
			var errorMsg = "${errorMsg}";
			if(errorMsg!=""){
				$.messager.alert("系统提示", errorMsg, 'error');
			}
			
		});
	</script>
</body>
</html>
