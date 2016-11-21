var ProcessDoing = {

	"search" : function() {
		$("#table").datagrid("load", {
			processName : $("#processName").val(),
			taskName : $("#taskName").val()
		});
	},

	"toDeal" : function(id) {
		window.self.location.href = contextPath + "/process/taskForm.do?id="
				+ id;
	},

	"end" : null
};