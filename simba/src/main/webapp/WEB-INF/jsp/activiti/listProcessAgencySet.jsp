<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>代办设置管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/activiti/processAgencySet.js"></script>
</head>

<body>
	<div style="margin: 20px 0;"></div>
	<div id="panel">
		<table id="processAgencySetTable"></table>
		<div id="processAgencySetToolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="ProcessAgencySet.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="ProcessAgencySet.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#panel").panel({
				height : $(document).height() - 50,
				width : $(document).width() - 20,
				title : "代办设置列表信息"
			});
			$("#processAgencySetTable").datagrid({
				url : contextPath + "/processAgencySet/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				toolbar : "#processAgencySetToolbar",
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
				, {
					field : 'startTime',
					title : '开始时间',
					width : 150
				}
				, {
					field : 'endTime',
					title : '结束时间',
					width : 150
				}
				, {
					field : 'agencyAccount',
					title : '代办人',
					width : 150
				}
				, {
					title : "操作",
					field : "oper",
					width : 250,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"ProcessAgencySet.toUpdate('" + row["id"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"ProcessAgencySet.deleteProcessAgencySet('" + row["id"] + "')\">删除</a>";
						return html;
					}
				} ] ]
			});
		});
	</script>
</body>
</html>
