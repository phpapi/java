<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>角色管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/role.js"></script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div id="panel">
		<table id="table"></table>
		<div id="toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="Role.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="Role.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	<div id="assignPermissionWindow" class="easyui-window" title="分配权限" style="width:600px;height:400px" data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true">
		<form id="assignPermissionForm" method="post">
			<input id="roleName" name="roleName" type="hidden" value="" />
			<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<tr>
					<td>权限树:</td>
					<td><select id="permissionID" name="permissionID" style="width: 200px;"></select></td>
				</tr>
			</table>
		</form>
		<div style="text-align: center; padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Role.assignPermission();" data-options="iconCls:'icon-save'">分配权限</a> <a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="Role.cancelAssignPermission();" data-options="iconCls:'icon-cancel'">取消</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#panel").panel({
				height : $(document).height() - 50,
				width : $(document).width() - 20,
				title : "角色信息"
			});
			$("#table").datagrid({
				url : contextPath + "/role/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				toolbar : "#toolbar",
				singleSelect : false,
				pagination : true,
				idField : "name",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				columns : [ [ {
					title : "全选",
					field : "ck",
					checkbox : true
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
						var html = "<a href=\"javascript:void(0)\" onclick=\"Role.toUpdate('" + row["name"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"Role.deleteRole('" + row["name"] + "')\">删除</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"Role.toAssignPermission('" + row["name"] + "')\">分配权限</a>";
						return html;
					}
				} ] ]
			});
			$("#permissionID").combotree({
				url : contextPath + "/permission/listChildrenPermission.do?showRoot=true",
				required : true,
				checkbox:true,
				cascadeCheck:true,
				multiple:true,
				onLoadSuccess : function(node, data) {
					var permissionTreeSelect = $("#permissionID").combotree("tree");
					var root = permissionTreeSelect.tree("find", ${rootID});
					permissionTreeSelect.tree("expandAll", root.target);
				}
			});
		});
	</script>
</body>
</html>
