var ProcessDone = {

	"search" : function() {
		$("#table").datagrid("load", {
			processName : $("#processName").val(),
			taskName : $("#taskName").val()
		});
	},

	"toView" : function(id) {
		window.self.location.href = contextPath
				+ "/process/viewTaskForm.do?id=" + id + "&type=done";
	},

	"end" : null
};