<!--自定义宏-->

<!-- 分页 -->
<#macro pagination>
	 <#assign totalPage=page.totalPage>
	 <#assign currentPage=page.currentPage>
	 <div class="pages" style="clear:both">
	     <div>
	     	<#if totalPage != 1>
		     	 <#if currentPage != 1>
		     	 	<a class="next" href="javascript:toPage(${currentPage-1})"> &lt;上一页</a> 
		     	 </#if>
		     	 <#if currentPage lte 6>
		     	 	<#if totalPage lte 10>
		     	 		<#list 1.. totalPage as s >
		     	 			<a <#if currentPage == s> class="current"<#else> class="num"</#if>href="javascript:toPage(${s})">${s}</a>
		     	 		</#list>
		     	 		<#else>
		     	 		<#list 1..10 as s >
		     	 			<a <#if currentPage == s> class="current"<#else> class="num"</#if>href="javascript:toPage(${s})">${s}</a>
		     	 		</#list>
		     	 	</#if>
	     	 	<#elseif totalPage lte 10>
	     	 		<#list 1..totalPage as s>
	     	 			<a <#if currentPage == s> class="current"<#else> class="num"</#if>href="javascript:toPage(${s})">${s}</a>
	 	 			</#list>
	 	 		<#elseif currentPage lte totalPage-4>
	 	 			<#list (currentPage-6)..(currentPage+4) as s>
	     	 			<a <#if currentPage == s>class="current"<#else>class="num"</#if> href="javascript:toPage(${s})">${s}</a>
					</#list>
				<#else>
					<#list (totalPage-9)..totalPage as s>
	     	 			<a <#if currentPage == s> class="current"<#else> class="num"</#if>href="javascript:toPage(${s})">${s}</a>
		     		</#list>
		     	</#if>
	     	</#if>
	     	 
	     	 <#if currentPage != totalPage>
		     	 <a class="next" href="javascript:toPage(${currentPage+1})">下一页&gt;</a> 
	     	</#if>
	     	 <label >共${totalPage}页</label>
			 <#if totalPage gt 10>
				 <input type="number" id="number" placeholder="页码" style="width: 50px; height:20px; border: 1px solid #6633FF;" min="1" max="${totalPage}">
				 <input type="button" onclick="toSkipPage()" value="跳转">
			 </#if>
	     </div>
	 </div>
    <script>
    	$(function() {
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
	  		if(pageNum > ${totalPage}) {
	  			pageNum = ${totalPage};
	  		}
	  		if(pageNum < 1) {
	  			pageNum = 1;
	  		}
	  		toPage(pageNum); 
	  	}
   </script>
</#macro> 
     
<#macro selectAlbum>
	<DIV class="upload_c_a">
		<SPAN class="upload_c_a_a" style="font-size: 14px">选择相册</SPAN>
		 <SPAN class="upload_c_a_b" onclick="return false;"> 
			 <select id="albums" style="border: none; height: 28px; line-height: 28px; width: 100%;">
					<option value="0">--------选择相册--------</option>
					<#list albumList as album>
						<option style="line-height: 28px; color: #009900" value="${album.id}">
										${album.name} (${album.imageSize})
									</option>
					</#list>
			</select>
		</SPAN> 
		<input type="button" value="创建相册" onclick="createalbum()" class="cbtn upload_c_a_c_b_a_b">
	</DIV>
</#macro>
