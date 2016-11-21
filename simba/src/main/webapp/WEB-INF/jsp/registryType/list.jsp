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
<body class="easyui-layout" id="layout">
	<input type="hidden" id="parentID" name="parentID" value="${parentID}"/>
	<div data-options="region:'west',split:true" title="注册类型树" style="width:180px;">
		<ul class="easyui-tree" id="registryTypeTree"></ul>
	</div>
	<div data-options="region:'center',title:'${parentName}--子注册类型列表'">
		<table id="registryTypeTable"></table>
		<div id="registryTypeToolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="RegistryType.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="RegistryType.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#registryTypeTree").tree({
				url : contextPath + "/registryType/listChildrenRegistryType.do?showRoot=true",
				method : "post",
				animate : true,
				onClick : function(node) {
					RegistryType.selectRegistryType(node);
				},
				onLoadSuccess: function(node,data){
					if(${parentID} != ${rootID}){
						var root =  $("#registryTypeTree").tree("find",${rootID});
						$("#registryTypeTree").tree("expandAll", root.target);
						var parentNode = $("#registryTypeTree").tree("find", ${parentID});
						if (!parentNode) {
							return true;
						}
						$("#registryTypeTree").tree("scrollTo", parentNode.target);
						$("#registryTypeTree").tree("select", parentNode.target);
					}
				}
			});
			$("#registryTypeTable").datagrid({
				url : contextPath + "/registryType/listChildrenRegistryType.do",
				method : "post",
				animate : true,
				toolbar : "#registryTypeToolbar",
				singleSelect : false,
				pagination : false,
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
				}
				, {
					field : 'text',
					title : '名称',
					width : 150
				}
				, {
					title : "操作",
					field : "oper",
					width : 250,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"RegistryType.toUpdate('" + row["id"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"RegistryType.deleteRegistryType('" + row["id"] + "')\">删除</a>";
						return html;
					}
				} ] ]
			});
		});
	</script>

</body>
</html>
