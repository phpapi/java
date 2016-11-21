var RegistryTable = {

	"toAdd" : function() {
		window.self.location.href = contextPath + "/registryTable/toAdd.do?typeID=" + $("#typeID").val();
	},

	"selectRegistryType" : function(node) {
		var id = node.id;
		$("#typeID").val(id);
		$("#registryTableTable").datagrid("load", {
			typeID : id
		});
		var name = node.text + "--子注册表列表";
		$(".layout-panel-center .panel-title").html(name);
	},

	"batchDelete" : function() {
		var idArray = new Array();
		var selectedRegistryTables = $("#registryTableTable").datagrid("getSelections");
		$.each(selectedRegistryTables, function(i, registryTable) {
			idArray.push(registryTable.id);
		});
		if (idArray.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的记录", 'warning');
			return false;
		}
		$.ajax({
			url : contextPath + "/registryTable/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				ids : idArray.join(",")
			},
			success : function(data) {
				if (data.code == 200) {
					$("#registryTableTable").datagrid("load", {
						typeID : $("#typeID").val()
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
		window.self.location.href = contextPath + "/registryTable/toUpdate.do?id=" + id;
	},

	"deleteRegistryTable" : function(id) {
		$.ajax({
			url : contextPath + "/registryTable/delete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
					$("#registryTableTable").datagrid("load", {
						typeID : $("#typeID").val()
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
		$("#registryTableForm").form('submit', {
			url : contextPath + "/registryTable/add.do?json",
			onSubmit : function() {
				return $("#registryTableForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					RegistryTable.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"update" : function() {
		$("#registryTableForm").form('submit', {
			url : contextPath + "/registryTable/update.do?json",
			onSubmit : function() {
				return $("#registryTableForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					RegistryTable.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"toList" : function() {
		window.self.location.href = contextPath + "/registryTable/list.do?typeID=" + $("#typeID").combotree("getValue");
	},

	"end" : null

};