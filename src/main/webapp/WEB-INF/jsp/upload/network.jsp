<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<HTML xmlns="http://www.w3.org/1999/xhtml">
<base href="<%=basePath%>">
	<HEAD>
		<META content="IE=11.0000" http-equiv="X-UA-Compatible">
		<META http-equiv="Content-Type" content="text/html;charset=utf-8">
		<META name="keywords" content="上传电脑图片,贴图库,图床,图盘,图片上传,免费图床,图片外链,免费图片外链,图片外链空间,淘宝图片外链,qq空间图片外链,相册外链,免费支持外链相册">
		<META name="description" content="专业提供图片上传，支持外链，提供高速免费无限量图片存储和外链空间。">
		<TITLE>上传网络图片 - 贴图库 — 免费、高速、稳定、专业图片外链</TITLE>
		<LINK href="/favicon.ico" rel="shortcut icon" type="image/x-icon" />
		<LINK href="static/css/common.css?v=1603010" rel="stylesheet" type="text/css" />
		<LINK href="static/css/useralbumphoto.css" rel="stylesheet" type="text/css" />
		<LINK href="static/css/userphoto.css" rel="stylesheet" type="text/css" />
		<LINK href="static/css/userupload.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="static/js/jquery-1.9.1.min.js" type="text/javascript"></SCRIPT>
		<SCRIPT language="javascript" src="static/js/drag.js" type="text/javascript"></SCRIPT>
		<SCRIPT language="javascript" src="static/js/jquery.dialog.js" type="text/javascript"></SCRIPT>
		<SCRIPT language="javascript" src="static/js/common.js" type="text/javascript"></SCRIPT>
		<SCRIPT language="javascript" src="static/js/getconnecting.js" type="text/javascript"></SCRIPT>
		<SCRIPT language="javascript" src="static/js/usercommon.js" type="text/javascript"></SCRIPT>
		<SCRIPT language="javascript" src="static/js/rotation.js" type="text/javascript"></SCRIPT>
		<META name="GENERATOR" content="MSHTML 11.00.9600.17207"></HEAD>
	<BODY>
		<SCRIPT language="javascript" src="static/js/common.js" type="text/javascript"></SCRIPT>
		<%@include file="../head.jsp" %>
		<DIV class="container">
			<DIV class="upload" style="margin-top:80px;">
				<DIV class="upload_t">
					<span>
						<a href="upload/local">本地上传</a></span>上传图片到我的图片库</DIV>
				<DIV class="upload_c">
					<DIV class="upload_c_a">
						<SPAN class="upload_c_a_a" style="font-size:14px">选择相册</SPAN>
						<SPAN class="upload_c_a_b">
							<select id="albums" style="border:none;height:28px;line-height:28px;width:100%;">
								<option value>--------选择相册--------</option>
								<option style="line-height:28px;color:#009900" value="1356442" selected="selected">222 (0)</option>
								<option style="line-height:28px;color:#009900" value="1352520">默认相册 (9)</option></select>
						</SPAN>
						<input type="button" value="创建相册" onclick="createalbum()" class="cbtn upload_c_a_c_b_a_b"></DIV>
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
				<SCRIPT language="javascript" src="static/js/userupload.js?t=160621" type="text/javascript"></SCRIPT>
			</DIV>
		</DIV>
		<SCRIPT language="javascript" src="static/js/useralbum.js?v=160705" type="text/javascript"></SCRIPT>
		<LINK href="static/css/useralbum.css?v=160705" rel="stylesheet" type="text/css" />
		<%@include file="../foot.jsp" %>
	</BODY>

</HTML>