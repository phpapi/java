<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../common/header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/sockjs/websocket.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/sockjs/chat.js"></script>
<title>测试websocket页面</title>
 <style type="text/css">  
        #connect-container {  
            float: left;  
            width: 400px  
        }  
  
        #connect-container div {  
            padding: 5px;  
        }  
  
        #console-container {  
            float: left;  
            margin-left: 15px;  
            width: 400px;  
        }  
  
        #console {  
            border: 1px solid #CCCCCC;  
            border-right-color: #999999;  
            border-bottom-color: #999999;  
            height: 170px;  
            overflow-y: scroll;  
            padding: 5px;  
            width: 100%;  
        }  
  
        #console p {  
            padding: 0;  
            margin: 0;  
        }  
    </style>  
    
     <script type="text/javascript">  
        var ws = null;  
        var url = null;  
        var transports = [];  
  
        function setConnected(connected) {  
            document.getElementById('connect').disabled = connected;  
            document.getElementById('disconnect').disabled = !connected;  
            document.getElementById('echo').disabled = !connected;  
        }  
  
        function connect() {  
            //alert(url);  
            //console.log(url);  
            if (!url) {  
                alert('Select whether to use W3C WebSocket or SockJS');  
                return;  
            }  
  
            //ws = (url.indexOf('sockjs') != -1) ?new SockJS(url, undefined, {protocols_whitelist: transports}) : new WebSocket(url);  
            //if ('WebSocket' in window) {  
            //	alert("WebSocket");
            //    ws= new WebSocket("ws://127.0.0.1:8080/demo/websck");  
           // }else {  
            //	alert("SockJS");
            //    ws = new SockJS("http://127.0.0.1:8080/demo/sockjs/websck");  
           // }  
            //websocket = new SockJS("http://localhost:8080/SpringWebSocketPush/sockjs/websck");  
             ws= new WebSocket("ws://127.0.0.1:8080/demo/websck");   
            ws.onopen = function () {  
                alert('open');  
                setConnected(true);  
                //log('Info: connection opened.');  
            };  
            ws.onmessage = function (event) {  
                alert('Received:' + event.data);  
                log('Received: ' + event.data);  
            };  
            ws.onclose = function (event) {  
                setConnected(false);  
                log('Info: connection closed.');  
                log(event);  
            };  
        }  
  
        function disconnect() {  
            if (ws != null) {  
                ws.close();  
                ws = null;  
            }  
            setConnected(false);  
        }  
  
        function echo() {  
            if (ws != null) {  
                var message = document.getElementById('message').value;  
                log('Sent: ' + message);  
                ws.send(message);  
            } else {  
                alert('connection not established, please connect.');  
            }  
        }  
  
        function updateUrl(urlPath) {  
            if (urlPath.indexOf('sockjs') != -1) {  
                url = urlPath;  
                document.getElementById('sockJsTransportSelect').style.visibility = 'visible';  
            }  
            else {  
              if (window.location.protocol == 'http:') {  
                  url = 'ws://' + window.location.host + urlPath;  
              } else {  
                  url = 'wss://' + window.location.host + urlPath;  
              }  
              document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';  
            }  
        }  
  
        function updateTransport(transport) {  
            alert(transport);  
          transports = (transport == 'all') ?  [] : [transport];  
        }  
          
        function log(message) {  
            var console = document.getElementById('console');  
            var p = document.createElement('p');  
            p.style.wordWrap = 'break-word';  
            p.appendChild(document.createTextNode(message));  
            console.appendChild(p);  
            while (console.childNodes.length > 25) {  
                console.removeChild(console.firstChild);  
            }  
            console.scrollTop = console.scrollHeight;  
        }  
    </script>  
</head>
<body style="padding: 0px; margin: 0px">
	 <div id="connect-container">  
        <input id="radio1" type="radio" name="group1" onclick="updateUrl('/SpringWebSocketPush/websck');">  
            <label for="radio1">W3C WebSocket</label>  
        <br>  
        <input id="radio2" type="radio" name="group1" onclick="updateUrl('/SpringWebSocketPush/sockjs/websck');">  
            <label for="radio2">SockJS</label>  
        <div id="sockJsTransportSelect" style="visibility:hidden;">  
            <span>SockJS transport:</span>  
            <select onchange="updateTransport(this.value)">  
              <option value="all">all</option>  
              <option value="websocket">websocket</option>  
              <option value="xhr-polling">xhr-polling</option>  
              <option value="jsonp-polling">jsonp-polling</option>  
              <option value="xhr-streaming">xhr-streaming</option>  
              <option value="iframe-eventsource">iframe-eventsource</option>  
              <option value="iframe-htmlfile">iframe-htmlfile</option>  
            </select>  
        </div>  
        <div>  
            <button id="connect" onclick="connect();">Connect</button>  
            <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>  
        </div>  
        <div>  
            <textarea id="message" style="width: 350px">Here is a message!</textarea>  
        </div>  
        <div>  
            <button id="echo" onclick="echo();" disabled="disabled">Echo message</button>  
        </div>  
    </div>  
    <div id="console-container">  
        <div id="console"></div>  
    </div>  
</div>  
</body>
</html>
