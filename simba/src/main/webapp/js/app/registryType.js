var RegistryType = {

	"toAdd" : function() {
			window.self.location.href = contextPath + "/registryType/toAdd.do?parentID=" + $("#parentID").val();
	},

	"batchDelete" : function() {
		var idArray = new Array();
		var selectedRegistryTypes = $("#registryTypeTable").datagrid("getSelections");
		$.each(selectedRegistryTypes, function(i, registryType) {
			idArray.push(registryType.id);
		});
		if (idArray.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的记录", 'warning');
			return false;
		}
		$.ajax({
			url : contextPath + "/registryType/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				ids : idArray.join(",")
			},
			success : function(data) {
				if (data.code == 200) {
						$("#registryTypeTable").datagrid("load", {
							id : $("#parentID").val()
						});
						var parentNode = $("#registryTypeTree").tree("find", $("#parentID").val());
						$("#registryTypeTree").tree("reload", parentNode.target);
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
	"selectRegistryType" : function(node) {
		var id = node.id;
		$("#parentID").val(id);
		$("#registryTypeTable").datagrid("load", {
			id : id
		});
		var name = node.text + "--子注册类型列表";
		$(".layout-panel-center .panel-title").html(name);
	},
	"toUpdate" : function(id) {
		window.self.location.href = contextPath + "/registryType/toUpdate.do?id=" + id;
	},

	"deleteRegistryType" : function(id) {
		$.ajax({
			url : contextPath + "/registryType/delete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
						$("#registryTypeTable").datagrid("load", {
							id : $("#parentID").val()
						});
						var parentNode = $("#registryTypeTree").tree("find", $("#parentID").val());
						$("#registryTypeTree").tree("reload", parentNode.target);
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
		$("#registryTypeForm").form('submit', {
			url : contextPath + "/registryType/add.do?json",
			onSubmit : function() {
				return $("#registryTypeForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					RegistryType.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"update" : function() {
		$("#registryTypeForm").form('submit', {
			url : contextPath + "/registryType/update.do?json",
			onSubmit : function() {
				return $("#registryTypeForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					RegistryType.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"toList" : function() {
			window.self.location.href = contextPath + "/registryType/list.do?parentID=" + $("#parentID").combotree("getValue");
	},

	"end" : null

};