<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>用户管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/user.js"></script>
</head>
<body class="easyui-layout" id="layout">
	<input type="hidden" id="parentID" name="parentID" value="${orgID}" />
	<div data-options="region:'west',split:true" title="机构树" style="width: 180px;">
		<ul class="easyui-tree" id="orgTree"></ul>
	</div>
	<div data-options="region:'center',title:'${orgName}--用户列表'">
		<table id="userTable"></table>
		<div id="userToolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="User.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="User.batchDelete();" data-options="iconCls:'icon-remove'">删除</a> <label>账号:</label> <input type="text" id="account" name="account" class="easyui-textbox" prompt="请输入您要查询的账号" /> <a
				href="javascript:void(0);" class="easyui-linkbutton" onclick="User.search();" data-options="iconCls:'icon-search'">全局查询</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#orgTree").tree({
				url : contextPath + "/org/listChildrenOrg.do?showRoot=true",
				method : "post",
				animate : true,
				onClick : function(node) {
					User.selectOrg(node);
				},
				onLoadSuccess: function(node,data){
					if(${orgID} != ${rootID}){
						var root =  $("#orgTree").tree("find",${rootID});
						$("#orgTree").tree("expandAll", root.target);
						var orgNode = $("#orgTree").tree("find", ${orgID});
						if (!orgNode) {
							return true;
						}
						$("#orgTree").tree("scrollTo", orgNode.target);
						$("#orgTree").tree("select", orgNode.target);
					}
				}
			});
			$("#userTable").datagrid({
				url : contextPath + "/user/listFull.do?forSimple=true",
				method : "post",
				animate : true,
				toolbar : "#userToolbar",
				singleSelect : false,
				idField : "account",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				pagination : true,
				queryParams : {
					orgID : $("#parentID").val(),
					account : ""
				},
				columns : [ [ {
					title : "全选",
					field : "ck",
					checkbox : true
				}, {
					field : 'account',
					title : '账号',
					width : 150
				}, {
					field : 'name',
					title : '用户名',
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
					title : "操作",
					field : "oper",
					width : 300,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"User.toUpdate('" + row["account"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"User.deleteAccount('" + row["account"]  + "')\">删除</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"User.resetPwd('" + row["account"]  + "')\">重置密码</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"User.toAssignRole('" + row["account"]  + "')\">分配角色</a>";
						return html;
					}
				} ] ]
			});
		});
	</script>
</body>
</html>
