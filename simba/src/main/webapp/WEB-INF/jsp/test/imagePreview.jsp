<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<title>图片预览</title>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/easyui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/jqueryPlugins/previewImage.js"></script>
</head>
<body style="padding: 0px; margin: 0px">
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="图片预览" style="width: 700px">
		<div style="padding: 10px 60px 20px 60px">
			<form id="form" method="post" enctype="multipart/form-data">
				<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tr>
						<td>图片:</td>
						<td>
							<div id="filesDiv">
								<div id="file1Div">
									<input class="easyui-filebox" style="width: 300px" name="file1" id="file1" data-options="buttonText:'选择文件',buttonAlign:'right'" /><a onclick="deleteImage(this)" class="easyui-linkbutton"
										data-options="iconCls:'icon-no'"></a>
								</div>
							</div>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addImage()">增加图片</a></td>
					</tr>
					<tr>
						<td colspan="3">
							<div id="previewImagesDiv">
								<img id="image1" name="image1" style="width: 130px; height: 130px; border: 0px; display: none;">
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="uploadImage()" data-options="iconCls:'icon-save'">上传文件</a>
			</div>
		</div>
		<div id="imageBigShowDiv" class="easyui-window" title="显示图片" style="width: 800px; height: 600px;" data-options="resizable:false,closed:true,modal:true,collapsible:false,minimizable:false,maximizable:false,closable:true">
			<img id="imageBig" style="width: 780px; height: 560px; border: 0px;"/>
		</div>
	</div>
	<script type="text/javascript">
		function uploadImage() {
			$("#form").attr("action", contextPath + "/test/uploadImages.do").submit();
		}

		var count = 1;
		var maxCount = 6;
		function getAddImageDiv() {
			count++;
			var html = "<div id=\"file"  +count +  "Div\">" + "<input  style=\"width: 300px\" name=\"file"+count+"\" id=\"file"+count+"\"/>" + "</div>";
			return html;
		}
		function getImagePreviewDiv() {
			var html = "<img id=\"image"  +count +  "\" name=\"image"  +count +  "\" style=\"width:130px;height:130px;border:0px; display: none;\">";
			return html;
		}

		function addImage() {
			if ($("input[type=file]").length == maxCount) {
				$.messager.alert('警告', '最多只能上传' + maxCount + "张", 'warning');
				return false;
			}
			var addImageDivHtml = getAddImageDiv();
			var imageDom = $(addImageDivHtml);
			$("#filesDiv").append(imageDom);
			imageDom.find("input").filebox({
				buttonText : '选择文件',
				buttonAlign : 'right'
			});
			var linkHtml = "<a onclick=\"deleteImage(this)\"></a>";
			var deleteLinkDom = $(linkHtml);
			imageDom.append(deleteLinkDom);
			deleteLinkDom.linkbutton({
				iconCls : 'icon-no'
			});
			var previewImageHtml = getImagePreviewDiv();
			var previewImageDom = $(previewImageHtml);
			$("#previewImagesDiv").append(previewImageDom);
			$("[name=file" + count + "]").uploadPreview({
				Img : "image" + count,
				Callback : function() {
					callbackPreview();
				}
			});
		}

		function getIndexFromId(id) {
			return id.substring("file".length, id.length - "Div".length);
		}

		function deleteImage(obj) {
			var id = $(obj).parent().attr("id");
			var index = getIndexFromId(id);
			var previewImageId = "image" + index;
			$("#" + previewImageId).remove();
			$(obj).parent().remove();
		}

		function callbackPreview() {
			$("#previewImagesDiv").find("img").each(function() {
				if ($(this).attr("src")) {
					$(this).show();
				} else {
					$(this).hide();
				}
			});
			$("#previewImagesDiv").find("img").unbind("click");
			$("#previewImagesDiv").find("img").click(function() {
				var imageSrc = $(this).attr("src");
				$("#imageBig").attr("src", imageSrc);
				$('#imageBigShowDiv').window('open');
			});
		}

		$(document).ready(function() {
			$("[name=file1]").uploadPreview({
				Img : "image1",
				Callback : function() {
					callbackPreview();
				}
			});
		});
	</script>
</body>
</html>
