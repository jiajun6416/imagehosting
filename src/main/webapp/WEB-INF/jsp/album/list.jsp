<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人相册--${username}</title>
<%@ include file="../common.jsp" %>
</head>
<body>	
	<%@ include file="../head.jsp" %>
	<LINK href="static/css/useralbum.css?v=03141" rel="stylesheet" type="text/css" />
	<DIV class="container">
		<DIV class="user">
			<DIV class="user_nav">
				<SPAN class="user_nav_a" style="display:inline-block;">
					<a href="#">${username}</a>的个人相册&nbsp;&nbsp;</SPAN>
				<span class="user_nav_b" id="create_a" >
					<input type="button" value="创建相册" onclick="createalbum()" class="cbtn upload_c_a_c_b_a_b">
				</span>
			</DIV>
			<DIV class="album" style="margin-top:100px;">
			
				<c:forEach items="${albumList}" var="album" >
					<SPAN class="album_i" data-type="0">
						<a href="list/${album.id}">
							<SPAN class="album_i_a">
								<c:forEach var="image" items="${album.imageList}" varStatus="s">
									<c:choose>
										<c:when test="${s.count == 1}">
										   <img style="width:200px;height:200px;float: left;" src="${image.imageZoomUrl}" alt/>
										</c:when>
										<c:when test="${s.count == 2}">
											<SPAN class="album_i_a_b album_i_a_bno album_i_a_bnob"><img src="${image.imageZoomUrl}" alt/></SPAN>
										</c:when>
										<c:when test="${s.count == 3}">
											<SPAN class="album_i_a_b album_i_a_bno album_i_a_bnob"><img src="${image.imageZoomUrl}" alt/></SPAN>									
										</c:when>
										<c:when test="${s.count == 4}">
											<SPAN class="album_i_a_b album_i_a_bno"><img src="${image.imageZoomUrl}" alt/></SPAN>
										</c:when>
										<c:when test="${s.count == 5}">
											<SPAN class="album_i_a_b album_i_a_bno album_i_a_bnob"><img src="${image.imageZoomUrl}" alt/></SPAN>
										</c:when>
										<c:when test="${s.count == 6}">
											<SPAN class="album_i_a_b album_i_a_bno"><img src="${image.imageZoomUrl}" alt/></SPAN>
										</c:when>
									</c:choose>	
								</c:forEach>
								
							</SPAN>
							<SPAN class="album_i_b">${album.name}</SPAN></A>
						<SPAN class="album_i_c">
							<SPAN class="album_i_c_a">
								<num>${album.imageSize }</num>
								<a href="javascript:;" onclick="updatenum(this,${album.id})" title="更新图片数量">
									<img src="static/image/re.png" width="12" /></a>
							</SPAN>
							<c:choose>
								<c:when test="${album.isPublic}">
									<SPAN class="album_i_c_c_unlock cog tip" onclick="setalbumlock('1','${album.id}')" title="公开可见">公开</SPAN>								
								</c:when>
								<c:otherwise>
									<SPAN class="album_i_c_lock cog tip" onclick="setalbumlock('0','${album.id}')" title="私密相册内图片仅自己可见" style="color: green">私密</SPAN>	
								</c:otherwise>
							</c:choose>
							<SPAN class="album_i_c_b">
								<A class="album_i_c_b_a" onclick="delalbum('${album.id}',this);return false;" href="javascript:void(0);">删除</A>
								<A onclick="editalbum('${album.id}',this,${album.isPublic});return false;" href="javascript:void(0);">编辑</A>
							</SPAN>
						</SPAN>
					</SPAN>
				</c:forEach>
				<SPAN class="album_fixed"></SPAN>
			</DIV>
			<SCRIPT language="javascript" src="static/js/useralbum.js?v=161028" type="text/javascript"></SCRIPT>
		</DIV>
		<div class="pages" style="clear:both">
			<div></div>
		</div>
	</DIV>
	<DIV class="footer">
		<%@include file="../foot.jsp" %>
	</DIV>	
</body>
</html>