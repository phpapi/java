var Process = {

	"saveStart" : function() {
		$("#processForm").form('submit', {
			url : contextPath + "/process/saveStart.do?json",
			onSubmit : function() {
				return $("#processForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					$.messager.alert("系统提示", "保存成功", "info");
					Process.cancelStart();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"submitStart" : function() {
		$("#processForm").form('submit', {
			url : contextPath + "/process/submitStart.do?json",
			onSubmit : function() {
				return $("#processForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					$.messager.alert("系统提示", "发送成功", "info");
					Process.cancelStart();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"cancelStart" : function() {
		window.self.location.href = contextPath + "/processStart/list.do";
	},

	"saveTask" : function() {
		$("#processForm").form('submit', {
			url : contextPath + "/process/saveTask.do?json",
			onSubmit : function() {
				return $("#processForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					$.messager.alert("系统提示", "保存成功", "info");
					Process.cancelTask();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"submitTask" : function() {
		$("#processForm").form('submit', {
			url : contextPath + "/process/submitTask.do?json",
			onSubmit : function() {
				return $("#processForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					$.messager.alert("系统提示", "发送成功", "info");
					Process.cancelTask();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"cancelTask" : function() {
		window.self.location.href = contextPath + "/processDoing/list.do";
	},

	"backTaskDone" : function() {
		window.self.location.href = contextPath + "/processDone/list.do";
	},

	"backFinish" : function() {
		window.self.location.href = contextPath + "/processFinish/list.do";
	},

	"backMonitor" : function() {
		window.self.location.href = contextPath + "/processMonitor/list.do";
	},

	"deleteProcessInstance" : function() {
		$
				.ajax({
					url : contextPath
							+ "/processMonitor/deleteProcessInstance.do?json",
					type : "post",
					dataType : "json",
					async : true,
					data : {
						id : $("#processInstanceId").val()
					},
					success : function(data) {
						if (data.code == 200) {
							$.messager.alert("系统提示", "删除成功", 'info');
							window.self.location.href = contextPath
									+ "/processDoing/list.do";
						} else {
							$.messager.alert("系统错误", data.msg, 'error');
						}
					},
					error : function() {
						$.messager.alert("系统错误", "删除失败", 'error');
					}
				});
	},

	"addComment" : function() {
		var content = $("#content").val();
		if (!content) {
			$.messager.alert("系统提示", "意见不能为空", 'warning');
			return false;
		}
		$.ajax({
			url : contextPath + "/processComment/add.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				processInstanceId : $("#processInstanceId").val(),
				taskId : $("#taskId").val(),
				content : content
			},
			success : function(data) {
				if (data.code == 200) {
					$.messager.alert("系统提示", "保存意见成功", 'info');
					$("#commentTable").datagrid("load", {
						processInstanceId : $("#processInstanceId").val()
					});
				} else {
					$.messager.alert("系统错误", data.msg, 'error');
				}
			},
			error : function() {
				$.messager.alert("系统错误", "保存意见失败", 'error');
			}
		});
	},

	"addAttachment" : function() {
		$("#attachmentForm").attr("action",contextPath + "/processAttachment/add.do?json");
		$("#attachmentForm").submit();
	},

	"downloadAttachment" : function(id) {
		window.self.location.href = contextPath
				+ "/processAttachment/download.do?id=" + id;
	},

	"deleteAttachment" : function(id) {
		$.ajax({
			url : contextPath + "/processAttachment/delete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
					$.messager.alert("系统提示", "删除附件成功", 'info');
					$("#attachmentTable").datagrid("load", {
						processInstanceId : $("#processInstanceId").val()
					});
				} else {
					$.messager.alert("系统错误", data.msg, 'error');
				}
			},
			error : function() {
				$.messager.alert("系统错误", "删除附件失败", 'error');
			}
		});
	},

	"end" : null

};