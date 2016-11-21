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
<body style="padding: 0px; margin: 0px">
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="新增机构" style="width: 500px">
		<div style="padding: 10px 60px 20px 60px">
			<form id="orgForm" method="post">
				<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tr>
						<td>名称:</td>
						<td><input class="easyui-textbox" type="text" id="text" name="text" data-options="required:true" style="width: 200px"></input></td>
					</tr>
					<tr>
						<td>父机构:</td>
						<td><select id="parentID" name="parentID" style="width: 200px;"></select></td>
					</tr>
					<c:forEach var="ext" items="${descs}">
						<tr>
							<td>${ext.name}:</td>
							<td><input class="easyui-textbox" type="text" id="${ext.key}" name="${ext.key}" <c:if test="${ext.required}">data-options="required:true"</c:if>></input></td>
						</tr>
					</c:forEach>
					<tr>
						<td>排序:</td>
						<td><input class="easyui-numberspinner" required="required" type="text" id="orderNo" name="orderNo" data-options="min:1,max:999,editable:true" style="width: 200px"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Org.add();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0)" class="easyui-linkbutton" onclick="Org.toList();"
					data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var defaultParentID = ${parentID};
			$("#parentID").combotree({
				url : contextPath + "/org/listChildrenOrg.do?showRoot=true",
				required : true,
				onLoadSuccess : function(node, data) {
					if (defaultParentID == 0) {
						return true;
					}
					var orgTreeSelect = $("#parentID").combotree("tree");
					var parentNode = orgTreeSelect.tree("find", defaultParentID);
					if (!parentNode) {
						var root = orgTreeSelect.tree("find", ${rootID});	
						orgTreeSelect.tree("expandAll", root.target);
						parentNode = orgTreeSelect.tree("find", defaultParentID);
					}
					if (!parentNode) {
						return true;
					}
					orgTreeSelect.tree("scrollTo", parentNode.target);
					$("#parentID").combotree("setValue", defaultParentID);
					defaultParentID = 0;
				}
			});
		});
	</script>
</body>
</html>
