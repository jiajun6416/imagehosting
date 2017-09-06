<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <HEAD>
  <base href="<%=basePath%>">
  
    <META content="IE=11.0000" http-equiv="X-UA-Compatible">
    <META http-equiv="Content-Type" content="text/html;charset=utf-8">
    <META name="keywords" content="上传电脑图片,贴图库,图床,图盘,图片上传,免费图床,图片外链,免费图片外链,图片外链空间,淘宝图片外链,qq空间图片外链,相册外链,免费支持外链相册">
    <META name="description" content="专业提供图片上传，支持外链，提供高速免费无限量图片存储和外链空间。">
    <TITLE>上传电脑图片 - 贴图库 — 免费、高速、稳定、专业图片外链</TITLE>
    <LINK href="/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <LINK href="static/css/common.css?v=1603010" rel="stylesheet" type="text/css" />
    <LINK href="static/css/userupload.css" rel="stylesheet" type="text/css">
    <SCRIPT language="javascript" src="static/js/jquery-1.9.1.min.js" type="text/javascript"></SCRIPT>
    <SCRIPT language="javascript" src="static/js/drag.js" type="text/javascript"></SCRIPT>
    <SCRIPT language="javascript" src="static/js/jquery.dialog.js" type="text/javascript"></SCRIPT>
    <SCRIPT language="javascript" src="static/js/common.js" type="text/javascript"></SCRIPT>
    <META name="GENERATOR" content="MSHTML 11.00.9600.17207"></HEAD>
  
  <BODY>
	<%@include file="../head.jsp"%>
    <link rel="stylesheet" href="static/js/upload/style.css">
    <link rel="stylesheet" href="static/js/upload/webuploader.css">
    <script language="javascript" type="text/javascript" src="static/js/base64.js"></script>
    <script>var token = 'BCBB0F5480391E6D7E4ACB9F2FA16E8924019E83:5f_sl4mgl8ZDS2PHStri4SS7dJo=:eyJkZWFkbGluZSI6MTUwNDY3MTAzNiwidWlkIjoiNjA5NTEwIiwiYWxidW0iOiIxMzU2NDQyIn0=';
      var allownum = '300';
      var successpid = '';
      var d_album = '1356442';
      $(function() {
        setTimeout(function() {
          $(window).resize();
        },
        500)
      })</script>
    <DIV class="container">
      <DIV class="upload" style="margin-top:80px;">
        <DIV class="upload_t" style="font-size:14px;">
          <span>
            <a href="upload/network">网络上传</a></span>上传图片到我的图片库</DIV>
        <DIV class="upload_c">
          <DIV class="upload_c_a">
            <SPAN class="upload_c_a_a" style="font-size:14px">选择相册</SPAN>
            <SPAN class="upload_c_a_b" onclick="return false;">
              <select id="albums" style="border:none;height:28px;line-height:28px;width:100%;">
                <option value>--------选择相册--------</option>
                <option style="line-height:28px;color:#009900" value="1356442" selected="selected">222 (0)</option>
                <option style="line-height:28px;color:#009900" value="1352520">默认相册 (9)</option></select>
            </SPAN>
            <input type="button" value="创建相册" onclick="createalbum()" class="cbtn upload_c_a_c_b_a_b"></DIV>
          <DIV class="upload_c_b">
            <DIV id="uploadcontainer">
              <div id="uploader">
                <div class="queueList">
                  <div id="dndArea" class="placeholder">
                    <div id="filePicker"></div>
                    <p>支持鼠标拖拽、QQ截屏后Ctrl+V粘贴、点击选择，单次最多可选
                      <b style="color:#999">300</b>张</p></div>
                </div>
                <div class="statusBar" style="display:none;">
                  <div class="progress">
                    <span class="text">0%</span>
                    <span class="percentage"></span>
                  </div>
                  <div class="info"></div>
                  <div class="btns">
                    <div id="filePicker2"></div>
                    <div class="uploadBtn">开始上传</div></div>
                </div>
              </div>
            </DIV>
          </DIV>
          <DIV class="upload_c_c">温馨提示：图片格式支持JPG、JPEG、GIF、PNG；一次可添加上传300张图片，单张图片不可超过10M。</DIV></DIV>
      </DIV>
      <SCRIPT language="javascript" src="static/js/useralbum.js" type="text/javascript"></SCRIPT>
      <script type="text/javascript" src="static/js/upload/webuploader.js"></script>
      <script type="text/javascript">
			var basePath = '<%=basePath%>';
	  </script>
      <script type="text/javascript" src="static/js/upload/upload.js"></script>
      <SCRIPT language="javascript" src="static/js/userupload.js" type="text/javascript"></SCRIPT>
      <LINK href="static/css/useralbum.css?v=160705" rel="stylesheet" type="text/css" />
      <%@include file="../foot.jsp"%>
  </BODY>

</HTML>