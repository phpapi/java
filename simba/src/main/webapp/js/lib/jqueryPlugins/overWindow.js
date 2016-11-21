/**
 * 用于将鼠标放到某个按钮上,会自动弹出窗口,当鼠标移出窗口且按钮时,窗口才消失(基于easyUI的window组件开发,如果使用其他组件请自行修改)
 */
var OverWindow = {

	/**
	 * 初始化
	 * 
	 * @param domId
	 *            按钮id
	 * @param easyUIWinId
	 *            easyUI的window组件的id
	 * @param eayUIParam
	 *            easyUI的window组件的参数设置
	 * @param reload
	 *            是否每个都重新加载窗口内容,默认为false
	 */
	"init" : function(domId, easyUIWinId, eayUIParam, reload) {
		if (reload == null) {
			reload = false;
		}
		$("#" + domId).mouseover(function(event) {
			// 在鼠标移入要执行的操作 begin
			eayUIParam.left = event.clientX;
			eayUIParam.top = event.clientY;
			if (reload || !$("#" + easyUIWinId).parent().is(".window")) {
				$("#" + easyUIWinId).window(eayUIParam);
			}
			$("#" + easyUIWinId).window("open");
			// 在鼠标移入要执行的操作 end
			// 当鼠标进入打开的窗口后，鼠标进入窗口执行的操作--取消事件冒泡,解除鼠标离开按钮的事件,重新打开窗口
			$("#" + easyUIWinId).window("window").mouseover(function(event) {
				event.stopPropagation();
				$("#" + domId).unbind("mouseout");
				$("#" + easyUIWinId).window("open");
			});
			$("#" + easyUIWinId).window("window").mouseout(function() {
				$("#" + easyUIWinId).window("close");
			});
			$("#" + domId).bind("mouseout", function() {
				$("#" + easyUIWinId).window("close");
			});
		});
	},

	"end" : null
};