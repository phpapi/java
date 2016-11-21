var Org = {

	"toAdd" : function() {
		window.self.location.href = contextPath + "/org/toAdd.do?parentID=" + $("#parentID").val();
	},

	"batchDelete" : function() {
		var idArray = new Array();
		var selectedOrgs = $("#orgTable").datagrid("getSelections");
		$.each(selectedOrgs, function(i, org) {
			idArray.push(org.id);
		});
		if (idArray.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的机构", 'warning');
			return false;
		}
		$.ajax({
			url : contextPath + "/org/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				ids : idArray.join(",")
			},
			success : function(data) {
				if (data.code == 200) {
					$("#orgTable").datagrid("load", {
						id : $("#parentID").val()
					});
					var parentNode = $("#orgTree").tree("find", $("#parentID").val());
					$("#orgTree").tree("reload", parentNode.target);
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

	"selectOrg" : function(node) {
		var id = node.id;
		$("#parentID").val(id);
		$("#orgTable").datagrid("load", {
			id : id
		});
		var name = node.text + "--子机构";
		$(".layout-panel-center .panel-title").html(name);
	},

	"toUpdate" : function(id) {
		window.self.location.href = contextPath + "/org/toUpdate.do?id=" + id;
	},

	"deleteOrg" : function(id) {
		$.ajax({
			url : contextPath + "/org/delete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
					$("#orgTable").datagrid("load", {
						id : $("#parentID").val()
					});
					var parentNode = $("#orgTree").tree("find", $("#parentID").val());
					$("#orgTree").tree("reload", parentNode.target);
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
		$("#orgForm").form('submit', {
			url : contextPath + "/org/add.do?json",
			onSubmit : function() {
				return $("#orgForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					Org.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"update" : function() {
		$("#orgForm").form('submit', {
			url : contextPath + "/org/update.do?json",
			onSubmit : function() {
				return $("#orgForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					Org.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"toList" : function() {
		window.self.location.href = contextPath + "/org/list.do?parentID=" + $("#parentID").combotree("getValue");
	},

	"end" : null

};