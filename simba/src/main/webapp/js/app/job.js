var Job = {

	"toAdd" : function() {
		window.self.location.href = contextPath + "/job/toAdd.do";
	},

	"search" : function() {
		$("#jobTable").datagrid("load", {
			name : $("#name").val()
		});
	},

	"start" : function(id) {
		$.ajax({
			url : contextPath + "/job/start.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
					$("#jobTable").datagrid("reload", {
						name : $("#name").val()
					});
					$.messager.alert("系统提示", "启动成功", 'info');
				} else {
					$.messager.alert("系统错误", data.msg, 'error');
				}
			},
			error : function() {
				$.messager.alert("系统错误", "启动失败", 'error');
			}
		});
	},

	"stop" : function(id) {
		$.ajax({
			url : contextPath + "/job/stop.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
					$("#jobTable").datagrid("reload", {
						name : $("#name").val()
					});
					$.messager.alert("系统提示", "暂停成功", 'info');
				} else {
					$.messager.alert("系统错误", data.msg, 'error');
				}
			},
			error : function() {
				$.messager.alert("系统错误", "暂停失败", 'error');
			}
		});
	},

	"batchDelete" : function() {
		var idArray = new Array();
		var selectedJobs = $("#jobTable").datagrid("getSelections");
		$.each(selectedJobs, function(i, job) {
			idArray.push(job.id);
		});
		if (idArray.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的记录", 'warning');
			return false;
		}
		$.ajax({
			url : contextPath + "/job/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				ids : idArray.join(",")
			},
			success : function(data) {
				if (data.code == 200) {
					$("#jobTable").datagrid("load", {
						name : $("#name").val()
					});
					$.messager.alert("系统提示", "删除成功", 'info');
				} else {
					$.messager.alert("系统错误", data.msg, 'error');
				}
			},
			error : function() {
				$.messager.alert("系统错误", "删除失败", 'error');
			}
		});
	},
	"toUpdate" : function(id) {
		window.self.location.href = contextPath + "/job/toUpdate.do?id=" + id;
	},

	"deleteJob" : function(id) {
		$.ajax({
			url : contextPath + "/job/delete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
					$("#jobTable").datagrid("load", {
						name : $("#name").val()
					});
					$.messager.alert("系统提示", "删除成功", 'info');
				} else {
					$.messager.alert("系统错误", data.msg, 'error');
				}
			},
			error : function() {
				$.messager.alert("系统错误", "删除失败", 'error');
			}
		});
	},

	"add" : function() {
		if ($("#cronExpression").val() == "" && $("#intervalTime").val() == "") {
			$.messager.alert("系统提示", "间隔时间和cron表达式不能同时为空", 'warning');
			return false;
		}
		$("#jobForm").form('submit', {
			url : contextPath + "/job/add.do?json",
			onSubmit : function() {
				return $("#jobForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					Job.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"update" : function() {
		if ($("#cronExpression").val() == "" && $("#intervalTime").val() == "") {
			$.messager.alert("系统提示", "间隔时间和cron表达式不能同时为空", 'warning');
			return false;
		}
		$("#jobForm").form('submit', {
			url : contextPath + "/job/update.do?json",
			onSubmit : function() {
				return $("#jobForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					Job.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"toList" : function() {
		window.self.location.href = contextPath + "/job/list.do";
	},

	"end" : null

};