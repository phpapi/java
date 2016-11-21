<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>注册表管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/registryTable.js"></script>
</head>
<body style="padding:0px;margin:0px">
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="修改注册表" style="width:700px">
		<div style="padding:10px 60px 20px 60px">
			<form id="registryTableForm" method="post">
				<input type="hidden" id="id" name="id" value="${registryTable.id}" />
				<table cellpadding="0" cellspacing="0" style="table-layout:fixed;">
					<tr>
						<td>编码:</td>
						<td><input class="easyui-textbox" type="text" id="code" name="code" data-options="required:true" style="width:200px" value="${registryTable.code}"></input></td>
					</tr>
					<tr>
						<td>值:</td>
						<td><input class="easyui-textbox" type="text" id="value" name="value" data-options="required:true" style="width:200px" value="${registryTable.value}"></input></td>
					</tr>
					<tr>
						<td>类型:</td>
						<td><select id="typeID" name="typeID" style="width:200px;"></select></td>
					</tr>
					<tr>
						<td>描述:</td>
						<td><input class="easyui-textbox" type="text" id="description" name="description" data-options="required:true" style="width:200px" value="${registryTable.description}"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="RegistryTable.update();" data-options="iconCls:'icon-add'">修改</a> <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="RegistryTable.toList();" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>

	</div>
	<script type="text/javascript">
	$(document).ready(function() {
		var defaultTypeID = ${registryTable.typeID};
		$("#typeID").combotree({
			url : contextPath + "/registryType/listChildrenRegistryType.do?showRoot=true",
			required : true,
			onLoadSuccess : function(node, data) {
				if (defaultTypeID == 0) {
					return true;
				}
				var treeSelect = $("#typeID").combotree("tree");
				var typeNode = treeSelect.tree("find", defaultTypeID);
				if (!typeNode) {
					var root = treeSelect.tree("find", ${rootID});
					treeSelect.tree("expandAll", root.target);
					typeNode = treeSelect.tree("find", defaultTypeID);
				}
				if (!typeNode) {
					return true;
				}
				treeSelect.tree("scrollTo", typeNode.target);
				$("#typeID").combotree("setValue", defaultTypeID);
				defaultTypeID = 0;
			}
		});
	});
	</script>
</body>
</html>
