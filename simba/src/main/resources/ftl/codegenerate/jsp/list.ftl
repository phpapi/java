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
<#if pageType=="treeTable">
<body class="easyui-layout" id="layout">
	<input type="hidden" id="parentID" name="parentID" value="${r'${parentID}'}"/>
	<div data-options="region:'west',split:true" title="${classDesc}树" style="width:180px;">
		<ul class="easyui-tree" id="${firstLower}Tree"></ul>
	</div>
	<div data-options="region:'center',title:'${r'${parentName}'}--子${classDesc}列表'">
		<table id="${firstLower}Table"></table>
		<div id="${firstLower}Toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="${className}.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="${className}.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#${firstLower}Tree").tree({
				url : contextPath + "/${firstLower}/listChildren${className}.do?showRoot=true",
				method : "post",
				animate : true,
				onClick : function(node) {
					${className}.select${className}(node);
				},
				onLoadSuccess: function(node,data){
					if(${r'${parentID}'} != ${r'${rootID}'}){
						var root =  $("#${firstLower}Tree").tree("find",${r'${rootID}'});
						$("#${firstLower}Tree").tree("expandAll", root.target);
						var parentNode = $("#${firstLower}Tree").tree("find", ${r'${parentID}'});
						if (!parentNode) {
							return true;
						}
						$("#${firstLower}Tree").tree("scrollTo", parentNode.target);
						$("#${firstLower}Tree").tree("select", parentNode.target);
					}
				}
			});
			$("#${firstLower}Table").datagrid({
				url : contextPath + "/${firstLower}/listChildren${className}.do",
				method : "post",
				animate : true,
				toolbar : "#${firstLower}Toolbar",
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
				<#list filedsWithPage as field> 
				, {
					field : '${field.key}',
					title : '${field.desc}',
					width : 150
				}
				</#list> 
				, {
					title : "操作",
					field : "oper",
					width : 250,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"${className}.toUpdate('" + row["id"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"${className}.delete${className}('" + row["id"] + "')\">删除</a>";
						return html;
					}
				} ] ]
			});
		});
	</script>
</#if> 

<#if pageType!="treeTable">
<body>
	<div style="margin: 20px 0;"></div>
	<div id="panel">
		<table id="${firstLower}Table"></table>
		<div id="${firstLower}Toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="${className}.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="${className}.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#panel").panel({
				height : $(document).height() - 50,
				width : $(document).width() - 20,
				title : "${classDesc}列表信息"
			});
			$("#${firstLower}Table").datagrid({
				url : contextPath + "/${firstLower}/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				toolbar : "#${firstLower}Toolbar",
				singleSelect : false,
				pagination : true,
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				columns : [ [ {
					title : "全选",
					field : "ck",
					checkbox : true
				}
				<#list filedsWithPage as field> 
				, {
					field : '${field.key}',
					title : '${field.desc}',
					width : 150
				}
				</#list> 
				, {
					title : "操作",
					field : "oper",
					width : 250,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"${className}.toUpdate('" + row["id"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"${className}.delete${className}('" + row["id"] + "')\">删除</a>";
						return html;
					}
				} ] ]
			});
		});
	</script>
</#if> 
</body>
</html>
