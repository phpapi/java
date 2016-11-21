<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>演示系统</title>
<%@ include file="./common/header.jsp"%>
<style type="text/css">
/*公共样式*/
* {
	padding: 0;
	margin: 0;
	border: none
}

body {
	height: 100%;
	width: 100%;
	background: #014880;
	overflow: hidden;
}
/*标题部分的样式*/
.titleWraper {
	width: 100% px;
	height: 223px;
}

.title {
	width: 980px;
	height: 223px;
	margin: 0 auto;
	background: url(<%=request.getContextPath()%>/images/title.jpg) no-repeat left center;
}
/*中间区域的样式*/
.contentWraper {
	width: 100% px;
	height: 246px;
}

.content {
	width: 980px;
	height: 246px;
	margin: 0 auto;
}

.picBg {
	width: 602px;
	height: 246px;
	float: left;
	background: url(<%=request.getContextPath()%>/images/boxPic.jpg) no-repeat
}

.inputBox {
	width: 334px;
	height: 246px;
	float: left
}

.account {
	width: 334px;
	height: 54px;
	padding-top: 69px;
}

.password {
	width: 334px;
	height: 78px;
	padding-top: 15px;
}

#tip {
	width: 334px;
	height: 30px;
}

.inputBox input {
	width: 232px;
	height: 36px;
	line-height: 36px;
	color: #777;
	font-size: 20px;
}

.inputBox span {
	color: #FFF;
	font-size: 22px;
	font-family: SimHei, Microsoft YaHei;
	font-weight: bold;
	padding-right: 15px;
}

#loginBtnBg {
	width: 44px;
	height: 44px;
	margin-top: 101px;
	margin-bottom: 101px;
	float: left;
	cursor: pointer;
	background: url(<%=request.getContextPath()%>/images/loginBtn.jpg) no-repeat;
}

.footWraper {
	width: 100%;
	height: 350px;
	background: url(<%=request.getContextPath()%>/images/cube.jpg) repeat-x bottom;
	overflow: hidden
}
</style>
<script type="text/javascript">
	function mouseOver() {
		document.getElementById("loginBtnBg").style.background = "url(<%=request.getContextPath()%>/images/loginBtnOver.jpg) no-repeat";
	}
	function mouseOut() {
		document.getElementById("loginBtnBg").style.background = "url(<%=request.getContextPath()%>/images/loginBtn.jpg) no-repeat";
	}
	function login() {
		$("form").submit();
	}
	
	function refreshCaptcha(){
		$("#captchaImage").attr("src","<%=request.getContextPath()%>/captcha/getCaptcha.do?"+Math.random());
	}
	
	<c:if test="${not empty top}">
		top.location.href = contextPath + "/login/toLogin.do";
	</c:if>
</script>
</head>
<body>
	<div class="titleWraper">
		<div class="title"></div>
	</div>
	<div class="contentWraper">
		<div class="content">
			<div class="picBg"></div>
			<form action="<%=request.getContextPath()%>/login/login.do" method="post">
			<div class="inputBox">
					<div class="account">
						<span>账 号</span><input type="text" id="userName" name="userName" value="${userName }" />
					</div>
					<div class="password">
						<span>密 码</span><input type="password" value="${password }" id="password" name="password" />
					</div>
					<c:if test="${'true'==captchaEnabled}">
						<span>验证码</span><input type="text" id="captcha" name="captcha" value="" /><br/><br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="点击刷新" title="点击刷新" src="<%=request.getContextPath()%>/captcha/getCaptcha.do" onclick="refreshCaptcha()" id="captchaImage" style="width:160px;heigh:30px;">
					</c:if>
				<div id="tip" style="color: red">${errMsg}</div>
			</div>
			<div id="loginBtnBg" onclick="login()" onmouseover="mouseOver()" onmouseout="mouseOut()">
				<div style="display:">
					<input type="submit" value=""/>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div class="footWraper"></div>
</body>
</html>