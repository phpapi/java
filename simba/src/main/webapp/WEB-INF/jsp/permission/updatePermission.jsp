<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>权限管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/permission.js"></script>
</head>
<body style="padding:0px;margin:0px">
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="修改权限" style="width:700px">
		<div style="padding:10px 60px 20px 60px">
			<form id="permissionForm" method="post">
			<input type="hidden" name="id" id="id" value="${permission.id }"/>
				<table cellpadding="0" cellspacing="0" style="table-layout:fixed;">
					<tr>
						<td>名称:</td>
						<td><input class="easyui-textbox" type="text" id="text" name="text" data-options="required:true" value="${permission.text }"></input></td>
					</tr>
					<tr>
						<td>URL地址:</td>
						<td><input class="easyui-textbox" type="text" id="url" name="url" data-options="width:500,multiline:true,height:60,prompt:'多个url使用英文逗号隔开'" value="${permission.url}"></input></td>
					</tr>
					<tr>
						<td>父权限:</td>
						<td><select id="parentID" name="parentID" style="width: 200px;" data-options="required:true"></select></td>
					</tr>
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Permission.update();" data-options="iconCls:'icon-save'">修改</a> <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="Permission.toList();" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var defaultParentID = ${permission.parentID};
			$("#parentID").combotree({
				url : contextPath + "/permission/listChildrenPermission.do?showRoot=true",
				required : true,
				onLoadSuccess : function(node, data) {
					if (defaultParentID == 0) {
						return true;
					}
					var permissionTreeSelect = $("#parentID").combotree("tree");
					var parentNode = permissionTreeSelect.tree("find", defaultParentID);
					if (!parentNode) {
						var root = permissionTreeSelect.tree("find", ${rootID});
						permissionTreeSelect.tree("expandAll", root.target);
						parentNode = permissionTreeSelect.tree("find", defaultParentID);
					}
					if (!parentNode) {
						return true;
					}
					permissionTreeSelect.tree("scrollTo", parentNode.target);
					$("#parentID").combotree("setValue", defaultParentID);
					defaultParentID = 0;
				}
			});
		});
	</script>
</body>
</html>
