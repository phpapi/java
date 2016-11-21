<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>机构管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/org.js"></script>
</head>
<body class="easyui-layout" id="layout">
	<input type="hidden" id="parentID" name="parentID" value="${parentID}" />
	<div data-options="region:'west',split:true" title="机构树" style="width: 180px;">
		<ul class="easyui-tree" id="orgTree"></ul>
	</div>
	<div data-options="region:'center',title:'${parentName}--子机构'">
		<table id="orgTable"></table>
		<div id="orgToolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="Org.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="Org.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#orgTree").tree({
				url : contextPath + "/org/listChildrenOrg.do?showRoot=true",
				method : "post",
				animate : true,
				onClick : function(node) {
					Org.selectOrg(node);
				},
				onLoadSuccess: function(node,data){
					if(${parentID} != ${rootID}){
						var root =  $("#orgTree").tree("find",${rootID});
						$("#orgTree").tree("expandAll", root.target);
						var parentNode = $("#orgTree").tree("find", ${parentID});
						if (!parentNode) {
							return true;
						}
						$("#orgTree").tree("scrollTo", parentNode.target);
						$("#orgTree").tree("select", parentNode.target);
					}
				}
			});
			$("#orgTable").datagrid({
				url : contextPath + "/org/listChildrenFullOrg.do?forSimple=true",
				method : "post",
				animate : true,
				toolbar : "#orgToolbar",
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
				<c:forEach var="desc" items="${descs}">
				, {
					field : '${desc.key}',
					title : '${desc.value}',
					width : 100
				}
				</c:forEach>
				, {
					field : 'orderNo',
					title : '排序号',
					width : 100
				}, {
					title : "操作",
					field : "oper",
					width : 120,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0);\" onclick=\"Org.toUpdate('" + row["id"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0);\" onclick=\"Org.deleteOrg('" + row["id"] + "')\">删除</a>";
						return html;
					}
				} ] ]
			});
		});
	</script>
</body>
</html>
