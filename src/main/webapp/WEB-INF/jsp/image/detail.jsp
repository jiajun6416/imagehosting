<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<%@ include file="../common.jsp" %>
</HEAD>
<BODY>
  <%@ include file="../head.jsp" %>
  <SCRIPT language="javascript" src="static/js/clipboard.min.js" type="text/javascript"></SCRIPT>
  <LINK href="static/css/imginfo.css?t=06162" rel="stylesheet" type="text/css">
  <SCRIPT language="javascript" type="text/javascript">$(function() {
      $("#TTKCopy .TTKCopyFrame .TTKCopyTextFrame").mouseover(function() {
        $(this).children("input").focus();
        $(this).children("input").addClass("selected");
      });
      $("#TTKCopy .TTKCopyFrame .TTKCopyTextFrame").mouseleave(function() {
        $(this).children("input").blur();
        $(this).children("input").removeClass("selected");
      });
      $("#TTKCopy #TTKCopySwitch .TTKCopySwitchTab").click(function() {
        var TmarginTop = $(this).attr("data-num") * ( - 200);
        $("#TTKCopy #TTKCopySwitch .TTKCopySwitchTab").removeClass("active");
        $(this).addClass("active");
        if ($(".TTKCopyTab").first().css("marginTop") != TmarginTop) {
          $(".TTKCopyTab").first().css("marginTop", TmarginTop);
          $(".zclip").css("marginTop", TmarginTop);
        }
        $('.zclip').remove();
      });
      var clipboard = new Clipboard('.copybtn');
      clipboard.on('success',
      function(e) {
        $.prompt.show("复制成功");
        e.clearSelection();
        setTimeout(function() {
          $.prompt.hidden()
        },
        1200);
      });
      clipboard.on('error',
      function(e) {
        $.alert.show("复制失败");
        console.error('Action:', e.action);
        console.error('Trigger:', e.trigger);
      });

      $(window).scroll(function() {
        window_top = $(document).scrollTop();
        if (window_top <= 50) {
          $(".photo_r").css({
            "position": ""
          })
        } else {
          if (window_top > $('.photo_l').height() - 250) {
            $(".photo_r").css({
              'top': $('photo_l').height()
            });
            return false;
          }
          $(".photo_r").css({
            "position": "relative",
            "top": $(document).scrollTop() - 45
          })
        }
      })

    });
    var aid = '1356442';
    var pid = '24511619';</SCRIPT>
  <DIV class="container">
    <DIV class="user">
      <DIV class="user_nav">
        <SPAN class="user_nav_a">
          <A href="#">${sessionScope.session_user_key.username}</A>&gt;
          <A href="#">${album.name}</A>&gt; <span>${image.fileName}</span> </SPAN></DIV>
      <DIV class="c_p_l_c_i photo" pid="24511619" data-obj="24511619" style="margin-top:105px">
        <DIV class="photo_l">
          <DIV class="photo_l_t"></DIV>
          <DIV class="photo_a">
            <IMG id="24511619" alt="${image.fileName}" src="${image.httpUrl}" style></DIV>
          <DIV class="photo_b">
            <DIV class="photo_b_a">
              <SPAN class="c_p_l_c_i_d photo_b_a_b only" style="left:0;top:0">
                <span class="edit" style="float:right" onclick="edit_i('${image.id}', '${image.fileName}')">编辑</span>
                <a class="span" href="${image.httpUrl}" target="_blank">原图</a></SPAN>
            </DIV>
            <DIV class="photo_b_a">
              <SPAN class="photo_b_a_a">
                <SPAN>
                  <strong>${image.fileName}</strong>.${image.fileType}</SPAN>
                <SPAN>${image.width}x${image.height}像素</SPAN>
                <SPAN class="photo_b_a_a_l"></SPAN>
              </SPAN>
            </DIV>
            <DIV class="photo_b_c">
              <SPAN class="photo_b_c_a">上传时间：<fmt:formatDate value="${image.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/> </SPAN>
              </DIV></DIV>
        </DIV>
        <DIV class="photo_r">
          <div id="TTKCopy">
            <div id="TTKCopyTabs">
              <div class="TTKCopyTab">
                <div class="TTKCopyFrame">
                  <label>图片外链</label>
                  <div class="TTKCopyTextFrame">
                    <input type="text" value="${image.httpUrl}" />
                    <button class="copybtn" data-clipboard-action="copy" data-clipboard-text="${image.httpUrl}">复制</button></div>
                </div>
                <div class="clearfix"></div>
                <div class="TTKCopyFrame">
                  <label>HTML代码</label>
                  <div class="TTKCopyTextFrame">
                    <input type="text" value="<img src='${image.httpUrl}'>" />
                    <button class="copybtn" data-clipboard-action="copy" data-clipboard-text="<img src='${image.httpUrl}'>">复制</button></div>
                </div>
                <div class="clearfix"></div>
                <div class="TTKCopyFrame">
                  <label>UBB代码</label>
                  <div class="TTKCopyTextFrame">
                    <input type="text" value="[img]${image.httpUrl}[/img]" />
                    <button class="copybtn" data-clipboard-action="copy" data-clipboard-text="[img]${image.httpUrl}[/img]">复制</button></div>
                </div>
                <div class="clearfix"></div>
                <div class="TTKCopyFrame">
                  <label>MarkDown</label>
                  <div class="TTKCopyTextFrame">
                    <input type="text" value="![Markdown](${image.httpUrl})" />
                    <button class="copybtn" data-clipboard-action="copy" data-clipboard-text="![Markdown](${image.httpUrl})">复制</button></div>
                </div>
                <div class="clearfix"></div>
              </div>
            </div>
          </div>
        </DIV>
      </DIV>
    </DIV>
  </DIV>
  <SCRIPT language="javascript" src="static/js/photolive.js" type="text/javascript"></SCRIPT>
  <SCRIPT language="javascript" src="static/js/userphoto.js?v=1605" type="text/javascript"></SCRIPT>
  <SCRIPT language="javascript" src="static//js/exhibition_3.js" type="text/javascript"></SCRIPT>
	<%@ include file="../foot.jsp" %>
</BODY>
</HTML>