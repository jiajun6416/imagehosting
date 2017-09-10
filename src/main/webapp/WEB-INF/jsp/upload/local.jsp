<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<TITLE>上传本地图片 - imagehosting</TITLE>
<HEAD>
<%@ include file="../common.jsp"%>
</HEAD>
<BODY>
	<%@ include file="../head.jsp"%>
	<link rel="stylesheet" href="static/js/upload/style.css">
		<link rel="stylesheet" href="static/js/upload/webuploader.css">
			<style>
#sub_nav, #sub_nav2 {
	width: 122px;
}

#sub_nav ul, #sub_nav2 ul {
	margin: 0 20px;
}
</style>
	<script>
      var allownum = '300';
      var successpid = '';
      //选中的album id
      var d_album = '1356442';
      $(function() {
        setTimeout(function() {
          $(window).resize();
        },
        500);
      })
    </script>
	<DIV class="container">
		<DIV class="upload" style="margin-top: 80px;">
			<DIV class="upload_t" style="font-size: 14px;">
				<span> <a href="upload/network">网络上传</a></span>上传图片到我的图片库
			</DIV>
			<DIV class="upload_c">
				<DIV class="upload_c_a">
					<SPAN class="upload_c_a_a" style="font-size: 14px">选择相册</SPAN>
					 <SPAN class="upload_c_a_b" onclick="return false;"> 
					 <select id="albums" style="border: none; height: 28px; line-height: 28px; width: 100%;">
							<option value="0">--------选择相册--------</option>
							<c:forEach var="album" items="${albumList}" >
								<option style="line-height: 28px; color: #009900" value="${album.id}">
									${album.name} (${album.imageSize})
								</option>
							</c:forEach>
					</select>
					</SPAN> <input type="button" value="创建相册" onclick="createalbum()"
						class="cbtn upload_c_a_c_b_a_b">
				</DIV>
				<DIV class="upload_c_b">
					<DIV id="uploadcontainer">
						<div id="uploader">
							<div class="queueList">
								<div id="dndArea" class="placeholder">
									<div id="filePicker"></div>
									<p>
										支持鼠标拖拽、QQ截屏后Ctrl+V粘贴、点击选择，单次最多可选 <b style="color: #999">300</b>张
									</p>
								</div>
							</div>
							<div class="statusBar" style="display: none;">
								<div class="progress">
									<span class="text">0%</span> <span class="percentage"></span>
								</div>
								<div class="info"></div>
								<div class="btns">
									<div id="filePicker2"></div>
									<div class="uploadBtn">开始上传</div>
								</div>
							</div>
						</div>
					</DIV>
				</DIV>
				<DIV class="upload_c_c">温馨提示：图片格式支持JPG、JPEG、GIF、PNG；一次可添加上传300张图片，单张图片不可超过10M。</DIV>
			</DIV>
		</DIV>
		<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
		<SCRIPT language="javascript" src="static/js/useralbum.js" type="text/javascript"></SCRIPT>
		<script type="text/javascript" src="static/js/upload/webuploader.js"></script>
		<script type="text/javascript" src="static/js/upload/upload.js"></script>
		<SCRIPT language="javascript" src="static/js/userupload.js" type="text/javascript"></SCRIPT>
		<LINK href="static/css/useralbum.css?v=160705" rel="stylesheet" type="text/css" />
		
		<%@include file="../foot.jsp"%>
</BODY>

</HTML>