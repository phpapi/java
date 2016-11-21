var Permission = {

	"toAdd" : function() {
		window.self.location.href = contextPath + "/permission/toAdd.do?parentID=" + $("#parentID").val();
	},

	"add" : function() {
		$("#permissionForm").form('submit', {
			url : contextPath + "/permission/add.do?json",
			onSubmit : function() {
				return $("#permissionForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					Permission.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"batchDelete" : function() {
		var idArray = new Array();
		var selectedPermissions = $("#permissionTable").datagrid("getSelections");
		$.each(selectedPermissions, function(i, permission) {
			idArray.push(permission.id);
		});
		if (idArray.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的权限", 'warning');
			return false;
		}
		$.ajax({
			url : contextPath + "/permission/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				ids : idArray.join(",")
			},
			success : function(data) {
				if (data.code == 200) {
					$("#permissionTable").datagrid("load", {
						id : $("#parentID").val()
					});
					var parentNode = $("#permissionTree").tree("find", $("#parentID").val());
					$("#permissionTree").tree("reload", parentNode.target);
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
		window.self.location.href = contextPath + "/permission/toUpdate.do?id=" + id;
	},

	"update" : function() {
		$("#permissionForm").form('submit', {
			url : contextPath + "/permission/update.do?json",
			onSubmit : function() {
				return $("#permissionForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					Permission.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"deletePermission" : function(id) {
		$.ajax({
			url : contextPath + "/permission/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				ids : id
			},
			success : function(data) {
				if (data.code == 200) {
					$("#permissionTable").datagrid("load", {
						id : $("#parentID").val()
					});
					var parentNode = $("#permissionTree").tree("find", $("#parentID").val());
					$("#permissionTree").tree("reload", parentNode.target);
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

	"toList" : function() {
		window.self.location.href = contextPath + "/permission/list.do?parentID="+ $("#parentID").combotree("getValue");
	},

	"selectPermission" : function(node) {
		var id = node.id;
		$("#parentID").val(id);
		$("#permissionTable").datagrid("load", {
			id : id
		});
		var name = node.text + "--子权限";
		$(".layout-panel-center .panel-title").html(name);
	},

	"end" : null
};