var ProcessFinish = {

	"search" : function() {
		$("#table").datagrid("load", {
			processName : $("#processName").val()
		});
	},

	"toView" : function(id) {
		window.self.location.href = contextPath
				+ "/process/viewProcessForm.do?id=" + id + "&type=finish";
	},
	
	"deleteProcessInstance" :function(id){
		$.ajax({
			url : contextPath + "/processFinish/deleteProcessInstance.do?json",
			type : "post",
			dataType : "json",
			async : true,
			data : {
				id : id
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

	"end" : null
};