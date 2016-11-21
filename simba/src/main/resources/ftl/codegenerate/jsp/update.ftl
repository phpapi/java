<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>${classDesc}管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/${firstLower}.js"></script>
</head>
<#assign dollar = '$'>
<body style="padding:0px;margin:0px">
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="修改${classDesc}" style="width:700px">
		<div style="padding:10px 60px 20px 60px">
			<form id="${firstLower}Form" method="post">
				<input type="hidden" id="id" name="id" value="${dollar}{${firstLower}.id}" />
				<table cellpadding="0" cellspacing="0" style="table-layout:fixed;">
					<#list filedsWithPage as field> 
					<tr>
						<td>${field.desc}:</td>
						<td><input class="easyui-textbox" type="text" id="${field.key}" name="${field.key}" data-options="required:true" style="width:200px" value="${dollar}{${firstLower}.${field.key}}"></input></td>
					</tr>
					</#list> 
					<#if pageType=="treeTable">
					<tr>
						<td>父${classDesc}:</td>
						<td><select id="parentID" name="parentID" style="width:200px;"></select></td>
					</tr>
					</#if>
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="${className}.update();" data-options="iconCls:'icon-add'">修改</a> <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="${className}.toList();" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			<#if pageType=="treeTable">  
			var defaultParentID = ${dollar}{${firstLower}.parentID} ;
			$("#parentID").combotree({
				url : contextPath + "/${firstLower}/listChildren${className}.do?showRoot=true",
				required : true,
				onLoadSuccess : function(node, data) {
					if (defaultParentID == 0) {
						return true;
					}
					var treeSelect = $("#parentID").combotree("tree");
					var parentNode = treeSelect.tree("find", defaultParentID);
					if (!parentNode) {
						var root = treeSelect.tree("find", ${r'${rootID}'});
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
			</#if>
		});
	</script>
</body>
</html>
