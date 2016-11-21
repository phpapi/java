var Role = {

	"toAdd" : function() {
		window.self.location.href = contextPath + "/role/toAdd.do";
	},

	"add" : function() {
		$("#roleForm").form('submit', {
			url : contextPath + "/role/add.do?json",
			onSubmit : function() {
				return $("#roleForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					Role.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"batchDelete" : function() {
		var nameArray = new Array();
		var selectedRoles = $("#table").datagrid("getSelections");
		$.each(selectedRoles, function(i, role) {
			nameArray.push(role.name);
		});
		if (nameArray.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的角色", 'warning');
			return false;
		}
		$.ajax({
			url : contextPath + "/role/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				roleNames : nameArray.join(",")
			},
			success : function(data) {
				if (data.code == 200) {
					$("#table").datagrid("load",{});
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

	"toUpdate" : function(name) {
		window.self.location.href = contextPath + "/role/toUpdate.do?name=" + encodeURIComponent(encodeURI(name));
	},

	"update" : function() {
		$("#roleForm").form('submit', {
			url : contextPath + "/role/update.do?json",
			onSubmit : function() {
				return $("#roleForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					Role.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"deleteRole" : function(name) {
		$.ajax({
			url : contextPath + "/role/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				roleNames : name
			},
			success : function(data) {
				if (data.code == 200) {
					$("#table").datagrid("load",{});
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

	"toAssignPermission" : function(roleName) {
		$("#roleName").val(roleName);
		$.ajax({
			url : contextPath + "/role/getPermissionByRoleName.do?roleName=" + roleName,
			type : "get",
			async : true,
			dataType : "json",
			success : function(data) {
				$("#permissionID").combotree("setValues", data);
				$('#assignPermissionWindow').window('open');
				if (data.length > 0) {
					var permissionID = data[0];
					var permissionTreeSelect = $("#permissionID").combotree("tree");
					var permissionNode = permissionTreeSelect.tree("find", permissionID);
					if (!permissionNode) {
						return true;
					}
					permissionTreeSelect.tree("scrollTo", permissionNode.target);
				}
			}
		});
	},

	"cancelAssignPermission" : function() {
		$('#assignPermissionWindow').window('close');
		$("#roleName").val("");
		$("#permissionID").combotree("setValues", []);
	},

	"assignPermission" : function() {
		$("#assignPermissionForm").form('submit', {
			url : contextPath + "/role/assignPermission.do?json",
			onSubmit : function() {
				return $("#assignPermissionForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					$.messager.alert("系统提示", "分配权限成功", 'info');
					Role.cancelAssignPermission();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"toList" : function() {
		window.self.location.href = contextPath + "/role/list.do";
	},

	"end" : null
};