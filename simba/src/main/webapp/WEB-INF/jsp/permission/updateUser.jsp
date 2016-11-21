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
<body style="padding: 0px; margin: 0px">
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="修改用户" style="width: 500px">
		<div style="padding: 10px 60px 20px 60px">
			<form id="userForm" method="post">
				<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tr>
						<td>账号:</td>
						<td><input class="easyui-textbox" type="text" id="account" name="account" data-options="required:true,readonly:true" value="${user.account }"></input></td>
					</tr>
					<tr>
						<td>用户名:</td>
						<td><input class="easyui-textbox" type="text" id="name" name="name" data-options="required:true" value="${user.name }"></input></td>
					</tr>
					<c:forEach var="ext" items="${descs}">
						<tr>
							<td>${ext.name}:</td>
							<td><input class="easyui-textbox" type="text" id="${ext.key}" name="${ext.key}" value="${ext.value}" <c:if test="${ext.required}">data-options="required:true"</c:if>></input></td>
						</tr>
					</c:forEach>
					<tr>
						<td>所属机构:</td>
						<td><select id="orgID" name="orgID" style="width: 200px;"></select></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="User.update();" data-options="iconCls:'icon-save'">修改</a> <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="User.toList();" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		var orgIDs = ${orgIDs};
		var orgID = orgIDs[0];
		$("#orgID").combotree({
			url : contextPath + "/org/listChildrenOrg.do?showRoot=true",
			required : true,
			checkbox:true,
			cascadeCheck:false,
			multiple:true,
			onLoadSuccess : function(node, data) {
				if (orgID == 0) {
					return true;
				}
				var orgTreeSelect = $("#orgID").combotree("tree");
				var root = orgTreeSelect.tree("find", ${rootID});
				orgTreeSelect.tree("expandAll", root.target);
				var orgNode = orgTreeSelect.tree("find", orgID);
				if (!orgNode) {
					return true;
				}
				orgTreeSelect.tree("scrollTo", orgNode.target);
				$("#orgID").combotree("setValues", orgIDs);
			}
		});
	});
	</script>
</body>
</html>
