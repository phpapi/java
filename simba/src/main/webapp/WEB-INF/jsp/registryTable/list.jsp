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

<body class="easyui-layout" id="layout">
	<input type="hidden" id="typeID" name="typeID" value="${typeID}"/>
	<div data-options="region:'west',split:true" title="注册类型树" style="width:180px;">
		<ul class="easyui-tree" id="registryTypeTree"></ul>
	</div>
	<div data-options="region:'center',title:'${typeName}--子注册表列表'">
		<table id="registryTableTable"></table>
		<div id="registryTableToolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="RegistryTable.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="RegistryTable.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#registryTypeTree").tree({
				url : contextPath + "/registryType/listChildrenRegistryType.do?showRoot=true",
				method : "post",
				animate : true,
				onClick : function(node) {
					RegistryTable.selectRegistryType(node);
				},
				onLoadSuccess: function(node,data){
					if(${typeID} != ${rootID}){
						var root =  $("#registryTypeTree").tree("find",${rootID});
						$("#registryTypeTree").tree("expandAll", root.target);
						var typeNode = $("#registryTypeTree").tree("find", ${typeID});
						if (!typeNode) {
							return true;
						}
						$("#registryTypeTree").tree("scrollTo", typeNode.target);
						$("#registryTypeTree").tree("select", typeNode.target);
					}
				}
			});
			
			$("#registryTableTable").datagrid({
				url : contextPath + "/registryTable/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				toolbar : "#registryTableToolbar",
				singleSelect : false,
				pagination : true,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				queryParams : {
					typeID : $("#typeID").val()
				},
				columns : [ [ {
					title : "全选",
					field : "ck",
					checkbox : true
				}
				, {
					field : 'code',
					title : '编码',
					width : 150
				}
				, {
					field : 'value',
					title : '值',
					width : 150
				}
				, {
					field : 'description',
					title : '描述',
					width : 150
				}
				, {
					title : "操作",
					field : "oper",
					width : 250,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"RegistryTable.toUpdate('" + row["id"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"RegistryTable.deleteRegistryTable('" + row["id"] + "')\">删除</a>";
						return html;
					}
				} ] ]
			});
		});
	</script>
</body>
</html>
