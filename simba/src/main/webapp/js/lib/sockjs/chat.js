var Chat = {

	"websocketUrl" : "/websck",

	"socketJsUrl" : "/sockjs/websck",

	/**
	 * websocket connect
	 * 
	 * @param type
	 *            连接类型 sockjs or websocket
	 * @param openFn
	 *            打开连接时回调函数
	 * @param messageFn
	 *            收到消息时的回调函数
	 * @param closeFn
	 *            关闭连接时的回调函数
	 * @returns websocket对象
	 */
	"connect" : function(type, openFn, messageFn, closeFn) {
		var url = null;
		var ws = null;
		if (type == "sockjs") {
			url = contextPath + Chat.socketJsUrl;
			ws = new SockJS(url);
		} else {
			if (window.location.protocol == 'http:') {
				url = 'ws://' + window.location.host + contextPath + Chat.websocketUrl;
			} else {
				url = 'wss://' + window.location.host + contextPath + Chat.websocketUrl;
			}
			ws = new WebSocket(url);
		}
		ws.onopen = openFn;
		ws.onmessage = function(event) {
			var data = jQuery.parseJSON(event.data);
			messageFn(data);
		};
		ws.onclose = closeFn;
		return ws;
	},

	/**
	 * 关闭连接
	 * 
	 * @param ws
	 */
	"disconnect" : function(ws) {
		if (ws != null) {
			ws.close();
		}
	},

	/**
	 * 发送消息给服务器
	 * 
	 * @param ws
	 * @param message
	 * @returns {Boolean}
	 */
	"send" : function(ws, message) {
		if (ws == null) {
			alert("还未连接成功，不能发送消息");
			return false;
		}
		ws.send(message);
	},

	/**
	 * 发送消息
	 * 
	 * @param ws
	 * @param fromAccount
	 *            发送方账号
	 * @param toAccount
	 *            接收方账号
	 * @param message
	 *            消息内容
	 */
	"sendMessage" : function(ws, fromAccount, toAccount, message) {
		var data = {
			"fromAccount" : fromAccount,
			"toAccount" : toAccount,
			"content" : message
		};
		var content = JSON.stringify(data);
		Chat.send(ws, content);
	},

	"end" : null

};