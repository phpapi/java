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
<body class="easyui-layout" id="layout">
	<input type="hidden" id="parentID" name="parentID" value="${parentID}" />
	<div data-options="region:'west',split:true" title="权限树" style="width: 180px;">
		<ul class="easyui-tree" id="permissionTree"></ul>
	</div>
	<div data-options="region:'center',title:'${parentName}--子权限'">
		<table id="permissionTable"></table>
		<div id="permissionToolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="Permission.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="Permission.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#permissionTree").tree({
				url : contextPath + "/permission/listChildrenPermission.do?showRoot=true",
				method : "post",
				animate : true,
				onClick : function(node) {
					Permission.selectPermission(node);
				},
				onLoadSuccess: function(node,data){
					if(${parentID} != ${rootID}){
						var root =  $("#permissionTree").tree("find",${rootID});
						$("#permissionTree").tree("expandAll", root.target);
						var parentNode = $("#permissionTree").tree("find", ${parentID});
						if (!parentNode) {
							return true;
						}
						$("#permissionTree").tree("scrollTo", parentNode.target);
						$("#permissionTree").tree("select", parentNode.target);
					}
				}
			});
			$("#permissionTable").datagrid({
				url : contextPath + "/permission/listChildrenPermission.do",
				method : "post",
				animate : true,
				toolbar : "#permissionToolbar",
				singleSelect : false,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					id : $("#parentID").val()
				},
				columns : [ [ {
					title : "全选",
					field : "ck",
					checkbox : true
				}, {
					field : 'text',
					title : '名称',
					width : 150
				}
				, {
					field : 'url',
					title : 'URL',
					width : 150
				}
				, {
					title : "操作",
					field : "oper",
					width : 120,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0);\" onclick=\"Permission.toUpdate('" + row["id"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0);\" onclick=\"Permission.deletePermission('" + row["id"] + "')\">删除</a>";
						return html;
					}
				} ] ]
			});
		});
	</script>
</body>
</html>
