var ProcessManagement = {

	"toAdd" : function() {
		$("#processWindow").window('open');
	},

	"batchDelete" : function() {
		var idArray = new Array();
		var selectedProcess = $("#table").datagrid("getSelections");
		$.each(selectedProcess, function(i, process) {
			idArray.push(process.id);
		});
		if (idArray.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的流程", 'warning');
			return false;
		}
		$
				.ajax({
					url : contextPath
							+ "/processManagement/batchDeleteProcess.do?json",
					type : "post",
					dataType : "json",
					async : true,
					data : {
						ids : idArray.join(",")
					},
					success : function(data) {
						if (data.code == 200) {
							$("#table").datagrid("load", {
								processName : $("#processName").val()
							});
							$.messager.alert("系统提示", "批量删除成功", 'info');
						} else {
							$.messager.alert("系统错误", data.msg, 'error');
						}
					},
					error : function() {
						$.messager.alert("系统错误", "批量删除失败", 'error');
					}
				});
	},

	"search" : function() {
		$("#table").datagrid("load", {
			processName : $("#processName").val()
		});
	},

	"showView" : function(id) {
		$("#processImage").attr("src",
				contextPath + "/processManagement/getProcessImage.do?id=" + id);
		$("#processImageWindow").window('open');
	},

	"showXml" : function(id) {
		$.ajax({
			url : contextPath + "/processManagement/getProcessXml.do?id=" + id
					+ "&json",
			type : "get",
			dataType : "json",
			async : true,
			success : function(data) {
				if (data.code == 200) {
					$("#processXml").val(data.data);
					$("#processXmlWindow").window('open');
				} else {
					$.messager.alert("系统提示", data.msg, "error");
				}
			}
		});
	},

	"deleteProcess" : function(id) {
		$
				.ajax({
					url : contextPath
							+ "/processManagement/batchDeleteProcess.do?json",
					type : "post",
					dataType : "json",
					async : true,
					data : {
						ids : id
					},
					success : function(data) {
						if (data.code == 200) {
							$("#table").datagrid("load", {
								processName : $("#processName").val()
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

	"stop" : function(id) {
		$.ajax({
			url : contextPath + "/processManagement/stopProcess.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
					$("#table").datagrid("reload", {
						processName : $("#processName").val()
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

	"start" : function(id) {
		$.ajax({
			url : contextPath + "/processManagement/startProcess.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
					$("#table").datagrid("reload", {
						processName : $("#processName").val()
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

	"uploadProcessFile" : function() {
		var file = $("[name=processFile]").val();
		if (file == "") {
			$.messager.alert("系统提示", "请选择流程文件", 'warning');
			return false;
		}
		var fileType = file.substring(file.lastIndexOf('.') + 1, file.length)
				.toLowerCase();
		if (fileType != "xml" && fileType != "zip" && fileType != "bar"
				&& fileType != "bpmn") {
			$.messager.alert("系统提示", "请选择正确的流程文件", 'warning');
			return false;
		}
		if (!$("#uploadProcessForm").form("validate")) {
			return false;
		}
		$("#uploadProcessForm").attr("action",
				contextPath + "/processManagement/uploadProcess.do");
		$("#uploadProcessForm").submit();
	},

	"cancelUploadProcess" : function() {
		$("#processWindow").window('close');
	},

	"setPros" : function(id) {

	},

	"setUser" : function(id) {

	},

	"end" : null
};