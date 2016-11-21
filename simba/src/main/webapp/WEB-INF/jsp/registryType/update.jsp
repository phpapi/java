<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>注册类型管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/registryType.js"></script>
</head>
<body style="padding:0px;margin:0px">
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="修改注册类型" style="width:700px">
		<div style="padding:10px 60px 20px 60px">
			<form id="registryTypeForm" method="post">
				<input type="hidden" id="id" name="id" value="${registryType.id}" />
				<table cellpadding="0" cellspacing="0" style="table-layout:fixed;">
					<tr>
						<td>名称:</td>
						<td><input class="easyui-textbox" type="text" id="text" name="text" data-options="required:true" style="width:200px" value="${registryType.text}"></input></td>
					</tr>
					<tr>
						<td>父注册类型:</td>
						<td><select id="parentID" name="parentID" style="width:200px;"></select></td>
					</tr>
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="RegistryType.update();" data-options="iconCls:'icon-add'">修改</a> <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="RegistryType.toList();" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var defaultParentID = ${registryType.parentID} ;
			$("#parentID").combotree({
				url : contextPath + "/registryType/listChildrenRegistryType.do?showRoot=true",
				required : true,
				onLoadSuccess : function(node, data) {
					if (defaultParentID == 0) {
						return true;
					}
					var treeSelect = $("#parentID").combotree("tree");
					var parentNode = treeSelect.tree("find", defaultParentID);
					if (!parentNode) {
						var root = treeSelect.tree("find", ${rootID});
						treeSelect.tree("expandAll", root.target);
						parentNode = treeSelect.tree("find", defaultParentID);
					}
					if (!parentNode) {
						return true;
					}
					treeSelect.tree("scrollTo", parentNode.target);
					$("#parentID").combotree("setValue", defaultParentID);
					defaultParentID = 0;
				}
			});
		});
	</script>
</body>
</html>
