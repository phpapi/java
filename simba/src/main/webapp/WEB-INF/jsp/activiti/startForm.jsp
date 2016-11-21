<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>${pd.name}启动</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/process.js"></script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="流程信息" style="width: 800px" data-options="collapsible:true,collapsed:true">
		<div style="padding: 10px 60px 20px 60px">
				<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tr>
						<td>流程ID:</td>
						<td>${pd.id}</td>
					</tr> 
					<tr>
						<td>流程Key:</td>
						<td>${pd.key}</td>
					</tr>
					<tr>
						<td>流程名称:</td>
						<td>${pd.name}</td>
					</tr>
					<c:if test="${not empty pd.description}">
						<tr>
							<td>流程描述:</td>
							<td>${pd.description}</td>
						</tr>
					</c:if>
					<tr>
						<td>流程发起人:</td>
						<td>${startUserName}</td>
					</tr>
				</table>
		</div>
	</div>
	<br/>
	<br/>
	<div class="easyui-panel" title="表单数据" style="width: 800px" data-options="collapsible:true">
		<div style="padding: 10px 60px 20px 60px">
			<form id="processForm" method="post">
				<input type="hidden" id="startUser" name="startUser" value="${startUser}"/>
				<input type="hidden" id="startUserName" name="startUserName" value="${startUserName}"/>
				<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${pd.id}"/>
				${startForm}
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Process.saveStart();" data-options="iconCls:'icon-save'">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Process.submitStart();" data-options="iconCls:'icon-ok'">发送</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Process.cancelStart();"	data-options="iconCls:'icon-back'">返回</a>
			</div>
		</div>

	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>
