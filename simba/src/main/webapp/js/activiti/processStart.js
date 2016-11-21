var ProcessStart = {

	"search" : function() {
		$("#table").datagrid("load", {
			processName : $("#processName").val()
		});
	},

	"start" : function(id) {
		window.self.location.href = contextPath + "/process/start.do?id="
				+ id;
	},

	"end" : null

};