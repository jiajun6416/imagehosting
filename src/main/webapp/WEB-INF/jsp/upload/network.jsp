<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
	 <TITLE>上传网络图片 - imagehosting</TITLE>
	<%@ include file="../common.jsp" %>
</HEAD>
<BODY>
	<SCRIPT language="javascript" src="static/js/common.js" type="text/javascript"></SCRIPT>
	<%@include file="../head.jsp" %>
	<DIV class="container">
		<DIV class="upload" style="margin-top:80px;">
			<DIV class="upload_t">
				<span>
					<a href="upload/local">本地上传</a></span>上传图片到我的图片库</DIV>
			<DIV class="upload_c">
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
					</SPAN> 
					<input type="button" value="创建相册" onclick="createalbum()" class="cbtn upload_c_a_c_b_a_b">
				</DIV>
				<DIV class="upload_c_b">
					<DIV id="uploadcontainer">
						<div class="upload_c_q">
							<div class="upload_c_q_a">请输入网址：
								<input type="text" class="upload_c_q_a_ipt">
								<input type="button" class="upload_c_q_a_btn cbtn" value="上传" onclick="asyncloadimg()"></div></div>
					</DIV>
					<div class="upload_c_b_a">
						<div style="width:700px;height:40px;display:inline-block">
							<div class="upload_c_q_a" style="text-align:left;display:none">继续添加：
								<input type="text" id="morepicv" style="width:400px;height:34px" />
								<input type="button" value="确定" class="cbtn" onclick="addmorepic()" style="width:100px" /></div></div>
						<input type="button" value="开始上传" class="cbtn" id="uploadbtn" /></div>
				</DIV>
				<DIV class="upload_c_c">温馨提示：图片格式支持JPG、JPEG、GIF、PNG；一次可添加上传300张图片，单张图片不可超过10M。</DIV>
			</DIV>
		</DIV>
	</DIV>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	<SCRIPT language="javascript" src="static/js/userupload.js?t=160622" type="text/javascript"></SCRIPT>
	<SCRIPT language="javascript" src="static/js/useralbum.js?v=160703" type="text/javascript"></SCRIPT>
		<LINK href="static/css/useralbum.css?v=160705" rel="stylesheet" type="text/css" />
	<%@include file="../foot.jsp" %>
</BODY>

</HTML>