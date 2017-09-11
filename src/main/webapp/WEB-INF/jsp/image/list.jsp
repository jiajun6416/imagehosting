<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../common.jsp" %>

</head>
<body>
  <%@include file="../head.jsp" %>

   <DIV class="container">
  
     <DIV class="user">
       <DIV class="user_nav">
         <SPAN class="user_nav_a">
           <a href="album">${sessionScope.session_user_key.nickname}</a>&gt; ${album.name}</SPAN>
         <span class="user_nav_b" id="create_a" style="margin-top:4px">
           <!-- <input type="button" value="管理相册" onclick="window.location.href='/manage/1357769'" class="cbtn upload_c_a_c_b_a_b"> -->
			<b>每页显示:</b>
			<select id="sizeSelect" class="cbtn upload_c_a_c_b_a_b" style="background-color:#C0C0C0; width: 50px;"  >
				<option value='25'>25</option>
				<option value='50'>50</option>
				<option value='75'>75</option>
				<option value='100'>100</option>
			</select>
         </span>
       </DIV>
       <DIV class="container_photo_list user_photo_list" style>
    	
    	<c:forEach var="image" items="${page.list}">
	         <DIV class="c_p_l_col">
	           <DIV class="c_p_l_c_i" id="box${image.id}" data-obj="a571a2ad5659baa8" pid="${image.id}">
	             <DIV class="c_p_l_c_i_a" style="height: 200px;">
	               <A href="detail/${image.uniqueName}" target="_blank">
	                 <IMG alt="${image.fileName}" src="${image.imageZoomUrl}" onload="if(($(this).parents('.c_p_l_c_i_a').height()<500)&&($(this).height()!=$(this).parents('.c_p_l_c_i_a').height())){$(this).parents('.c_p_l_c_i_a').height($(this).height())};" /></A>
	             </DIV>
	             <DIV class="c_p_l_c_i_b">
	               <A href="detail/${image.uniqueName}" target="_blank">${image.fileName}</A></DIV>
	             <DIV class="c_p_l_c_i_c"></DIV>
	             <DIV class="c_p_l_c_i_d">
	               <span class="editor">编辑</span></DIV>
	             <DIV class="c_p_l_c_i_e"></DIV>
	           </DIV>
	         </DIV>
    	</c:forEach>
     </DIV>
	
	<form action="list/${album.id}" method="get">
		<input type="hidden" name="currentPage" value="${page.currentPage}">
		<input type="hidden" name="pageSize" value="${page.pageSize}">
	</form>   

	<!-- 模仿百度的分页 -->
     <div class="pages" style="clear:both">
	     <div>
	     	<c:if test="${page.totalPage != 1}">
		     	 <c:if test="${page.currentPage != 1}">
		     	 	<a class="next" href="javascript:toPage(${page.currentPage-1})"> &lt;上一页</a> 
		     	 </c:if>
		     	 <c:choose>
		     	 	<c:when test="${page.currentPage <=6 }">
	     	 			<c:forEach begin="1" end="${page.totalPage <=10 ? page.totalPage:10}" varStatus="s">
							<c:choose>
								<c:when test="${page.currentPage == s.current}">
		     	 					<a class="current" href="javascript:toPage(${s.current})">${s.current}</a>
		     	 				</c:when>
		     	 				<c:otherwise>
			     	 				<a class="num" href="javascript:toPage(${s.current})">${s.current}</a> 
		     	 				</c:otherwise>
							</c:choose>
	     	 			</c:forEach>
		     	 	</c:when>
		     	 	<c:otherwise>
		     	 		<c:choose>
		     	 			<c:when test="${page.totalPage <=10}">
				     	 		<c:forEach begin="1" end="${page.totalPage}" varStatus="s">
									<c:choose>
										<c:when test="${page.currentPage == s.current}">
				     	 					<a class="current" href="javascript:toPage(${s.current})">${s.current}</a>
				     	 				</c:when>
				     	 				<c:otherwise>
					     	 				<a class="num" href="javascript:toPage(${s.current})">${s.current}</a> 
				     	 				</c:otherwise>
									</c:choose>
			     	 			</c:forEach>
		     	 			</c:when>
		     	 			<c:otherwise>
		     	 				<c:choose>
		     	 					<c:when test="${ page.currentPage <= page.totalPage-4}">
		     	 						<c:forEach begin="${page.currentPage-6}" end="${page.currentPage+4}" varStatus="s">
											<c:choose>
												<c:when test="${page.currentPage == s.current}">
						     	 					<a class="current" href="javascript:toPage(${s.current})">${s.current}</a>
						     	 				</c:when>
						     	 				<c:otherwise>
							     	 				<a class="num" href="javascript:toPage(${s.current})">${s.current}</a> 
						     	 				</c:otherwise>
											</c:choose>     	 							
		     	 						</c:forEach>
		     	 					</c:when>
		     	 					<c:otherwise>
		 	     	 					<c:forEach begin="${page.totalPage-9}" end="${page.totalPage}" varStatus="s">
											<c:choose>
												<c:when test="${page.currentPage == s.current}">
						     	 					<a class="current" href="javascript:toPage(${s.current})">${s.current}</a>
						     	 				</c:when>
						     	 				<c:otherwise>
							     	 				<a class="num" href="javascript:toPage(${s.current})">${s.current}</a> 
						     	 				</c:otherwise>
											</c:choose>     	 							
		     	 						</c:forEach>    	 						
		     	 					</c:otherwise>
		     	 				</c:choose>
		     	 			</c:otherwise>
		     	 		</c:choose>
		     	 	</c:otherwise>
		     	 </c:choose>
	     	</c:if>
	     	 
	     	 <c:if test="${page.currentPage != page.totalPage}">
		     	 <a class="next" href="javascript:toPage(${page.currentPage+1})">下一页&gt;</a> 
	     	 </c:if>
	     	 
	     	 <label >共${page.totalPage}页</label>
			 <c:if test="${page.totalPage > 10}">
				 <input type="number" id="number" placeholder="页码" style="width: 50px; height:20px; border: 1px solid #6633FF;" min="1" max="${page.totalPage}">
				 <input type="button" onclick="toSkipPage()" value="跳转">
			 </c:if>
			 
	     </div>
     </div>
    <script>
    	$(function() {
    		$("#sizeSelect").val(${page.pageSize});
    		$('#number').keypress(function(e) {
    			if(e.keyCode == 13) {
    				toSkipPage();
    			}
    			　if (!String.fromCharCode(e.keyCode).match(/[0-9]/)) {
    			　　　　return false;
    			　　}
    		});
    		var maxInput = parseInt($('#number').attr('max'));
    		$("#number").keyup(function() {
    			if($("#number").val() < 1) {
    				$('#number').val(1);
    			}
    			if($("#number").val() > maxInput) {
    				$('#number').val(maxInput);
    			}
    		})
    		
    		$("#sizeSelect").change(function() {
				$("[name=pageSize]").val($(this).val());
				$("input[name='currentPage']").val(1);
				$("form:first").submit();
    		})
    	})
	  	function toPage(pageNum) {
	  		$("input[name='currentPage']").val(pageNum);
	  		$("form:first").submit();
	  	}
	  	function toSkipPage() {
	  		var pageNum = $("#number").val();
	  		if(pageNum == 0 || pageNum=='') {
	  			return;
	  		}
	  		if(pageNum > ${page.totalPage}) {
	  			pageNum = ${page.totalPage};
	  		}
	  		if(pageNum < 1) {
	  			pageNum = 1;
	  		}
	  		toPage(pageNum); 
	  	}
   </script>
   <!-- 模仿百度的分页  end-->  
     
   </DIV>
   <%@include file="../foot.jsp" %>
</body>
  <SCRIPT language="javascript" src="static/js/exhibition_2.js" type="text/javascript"></SCRIPT>
  <SCRIPT language="javascript" src="static/js/photolive.js" type="text/javascript"></SCRIPT>
</html>