var ProcessAgencySet = {

	"toAdd" : function() {
			window.self.location.href = contextPath + "/processAgencySet/toAdd.do";
	},

	"batchDelete" : function() {
		var idArray = new Array();
		var selectedProcessAgencySets = $("#processAgencySetTable").datagrid("getSelections");
		$.each(selectedProcessAgencySets, function(i, processAgencySet) {
			idArray.push(processAgencySet.id);
		});
		if (idArray.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的记录", 'warning');
			return false;
		}
		$.ajax({
			url : contextPath + "/processAgencySet/batchDelete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				ids : idArray.join(",")
			},
			success : function(data) {
				if (data.code == 200) {
						$("#processAgencySetTable").datagrid("load",{});
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
		window.self.location.href = contextPath + "/processAgencySet/toUpdate.do?id=" + id;
	},

	"deleteProcessAgencySet" : function(id) {
		$.ajax({
			url : contextPath + "/processAgencySet/delete.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == 200) {
						$("#processAgencySetTable").datagrid("load",{});
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
		$("#processAgencySetForm").form('submit', {
			url : contextPath + "/processAgencySet/add.do?json",
			onSubmit : function() {
				return $("#processAgencySetForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					ProcessAgencySet.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"update" : function() {
		$("#processAgencySetForm").form('submit', {
			url : contextPath + "/processAgencySet/update.do?json",
			onSubmit : function() {
				return $("#processAgencySetForm").form("validate");
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 200) {
					ProcessAgencySet.toList();
				} else {
					$.messager.alert("系统错误", data.msg, "error");
				}
			}
		});
	},

	"toList" : function() {
			window.self.location.href = contextPath + "/processAgencySet/list.do";
	},

	"end" : null

};