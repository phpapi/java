<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>测试图片滚动显示</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<%@ include file="../common/slider.jsp"%>
<style>
#main {
	margin-left: 260px;
	margin-right: 260px;
}

#container {
	padding: 60px 40px 0px;
}
</style>
</head>
<body style="padding: 0px; margin: 0px">
	<div id="container" class="cf">
		<div id="main" role="main">
			<section class="slider">
				<div class="flexslider" id="imagesDiv">
					<ul class="slides">
						<li><img src="<%=request.getContextPath()%>/images/kitchen_adventurer_cheesecake_brownie.jpg" />图片描述1</li>
						<li><img src="<%=request.getContextPath()%>/images/kitchen_adventurer_lemon.jpg" />图片描述2</li>
						<li><img src="<%=request.getContextPath()%>/images/kitchen_adventurer_donut.jpg" />图片描述3</li>
						<li><img src="<%=request.getContextPath()%>/images/kitchen_adventurer_caramel.jpg" />图片描述4</li>
					</ul>
				</div>
			</section>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#imagesDiv').flexslider({
				animation : "slide",
				start : function(slider) {

				}
			});
		});
	</script>
</body>
</html>
