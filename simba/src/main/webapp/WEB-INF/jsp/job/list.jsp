<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>任务管理</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/app/job.js"></script>
</head>

<body>
	<div style="margin: 20px 0;"></div>
	<div id="panel">
		<table id="jobTable"></table>
		<div id="jobToolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="Job.toAdd();" data-options="iconCls:'icon-add'">新增</a> <a href="javascript:void(0);" class="easyui-linkbutton"
				onclick="Job.batchDelete();" data-options="iconCls:'icon-remove'">删除</a>
				<input type="text" id="name" name="name" class="easyui-textbox" prompt="请输入您要查询的任务名称" /> <a
				href="javascript:void(0);" class="easyui-linkbutton" onclick="Job.search();" data-options="iconCls:'icon-search'">查询</a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#panel").panel({
				height : $(document).height() - 50,
				width : $(document).width() - 20,
				title : "任务列表信息"
			});
			$("#jobTable").datagrid({
				url : contextPath + "/job/listDataOfEasyUI.do",
				method : "post",
				animate : true,
				toolbar : "#jobToolbar",
				singleSelect : false,
				pagination : true,
				queryParams : {
					name : $("#name").val()
				},
				idField : "id",
				loadMsg : "正在加载数据，请耐心等待...",
				rownumbers : true,
				columns : [ [ {
					title : "全选",
					field : "ck",
					checkbox : true
				}
				, {
					field : 'name',
					title : '名称',
					width : 120
				}
				, {
					field : 'description',
					title : '描述',
					width : 150
				}
				, {
					field : 'cronExpression',
					title : 'cron表达式',
					width : 100
				}
				, {
					field : 'startTime',
					title : '开始执行时间',
					width : 120
				}
				, {
					field : 'endTime',
					title : '结束执行时间',
					width : 120
				}
				, {
					field : 'exeCount',
					title : '执行次数',
					width : 80
				}
				, {
					field : 'maxExeCount',
					title : '最大执行次数',
					width : 80
				}
				, {
					field : 'className',
					title : '完整类路径',
					width : 200
				}
				, {
					field : 'methodName',
					title : '执行类方法名',
					width : 80
				}
				, {
					field : 'delayTime',
					title : '延迟时间',
					width : 80
				}
				, {
					field : 'intervalTime',
					title : '间隔时间',
					width : 80
				}
				, {
					field : 'status',
					title : '状态',
					width : 150,
					formatter : function(value, row, index) {
						var html = "";
						if("waiting" == value ){
							html = "未启动";
						}else if("running" == value ){
							html = "运行中";
						}else if("error" == value ){
							html = "运行异常";
						}else if("finish" == value ){
							html = "运行完成";
						}else if("suspend" == value ){
							html = "暂停";
						}
						return html;
					}
				}
				, {
					title : "操作",
					field : "oper",
					width : 250,
					formatter : function(value, row, index) {
						var html = "<a href=\"javascript:void(0)\" onclick=\"Job.toUpdate('" + row["id"] + "')\">修改</a>";
						html += "&nbsp;&nbsp;";
						html += "<a href=\"javascript:void(0)\" onclick=\"Job.deleteJob('" + row["id"] + "')\">删除</a>";
						if("suspend" == row["status"] ){
							html += "&nbsp;&nbsp;";
							html += "<a href=\"javascript:void(0)\" onclick=\"Job.start('" + row["id"] + "')\">启动</a>";
						}else if("running" == row["status"]||"waiting" == row["status"]||"error" == row["status"]){
							html += "&nbsp;&nbsp;";
							html += "<a href=\"javascript:void(0)\" onclick=\"Job.stop('" + row["id"] + "')\">暂停</a>";
						}
						return html;
					}
				} ] ]
			});
		});
	</script>
</body>
</html>
