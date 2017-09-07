if(navigator.userAgent.indexOf("Firefox")>0){$(function(){setTimeout(function(){$('.c_p_l_c_i_a').css({'height':'200px'});$('.c_p_l_col').css({'margin':' 0 21px'});},600);});}else{if($(".container_photo_list").length&&$(".c_p_l_col").length){var ow=$(".container_photo_list").innerWidth();var lockrelist=false;function relist(){if(!lockrelist){var nw=1068;var iw=224;var num=Math.floor(nw/iw);var gap=Math.floor(nw%iw/(num- 1));var lastgap=gap+ nw%iw- gap*(num- 1);var cols=new Array();var chl=new Array();for(var i=0;i<num;i++){if(i==num- 2)
cols.push($("<div>").addClass("c_p_l_col").css("margin-right",lastgap));else if(i==num- 1)
cols.push($("<div>").addClass("c_p_l_col").css({"margin-right":0,"margin-left":"-1px"}));else
cols.push($("<div>").addClass("c_p_l_col").css("margin-right",gap));chl.push(0);}
var minhx=0;var ccn=$(".c_p_l_col").length,cin=$(".c_p_l_c_i").length;var cco,cio;$(".container_photo_list").empty();$.each(cols,function(i,j){$(".container_photo_list").append(j);});}
scrollload();}
var st;$(window).resize(function(){var nw=$(".container_photo_list").width();if(ow!=nw){ow=nw;if(st)
clearTimeout(st);st=setTimeout("relist();",100);}});relist();var cto;var cto_offset;$(window).scroll(function(){if(typeof cto==="undefined")
cto=$(".container_top");if(cto.length){if(typeof cto_offset==="undefined")
cto_offset=cto.offset();if($(this).scrollTop()>cto_offset.top){if(!!window.ActiveXObject&&!window.XMLHttpRequest)
cto.offset({top:$(this).scrollTop(),left:cto_offset.left});}else{if(!!window.ActiveXObject&&!window.XMLHttpRequest)
cto.offset({top:cto_offset.top,left:cto_offset.left});}}
if(isScrollBottom()&&!$(".scrollshowtips").length){scrollload();}
backTop();});var slwait=false,p=2;function scrollload(){if(!slwait){$.ajax({url:"/?c=Index&a=ajaxDatas",beforeSend:function(){slwait=true;$(".container_photo_list").scrollshowtips("show");},complete:function(){slwait=false;$(".container_photo_list").scrollshowtips("remove");},cache:false,data:{"p":p},dataType:"json",error:function(){$(".container_photo_list").scrollshowtips("fail");},success:function(result){if($.isArray(result.photo)){lockrelist=true;p++;$(".container_photo_list").scrollshowtips("removeall");var ndc='';var minhx=0;var cols=$(".c_p_l_col");$.each(result.photo,function(i,photo){ndc='';ndc+='<div class="c_p_l_c_i" data-obj="'+ photo.findurl+'" pid="'+photo.id+'" id="box'+photo.id+'">';if(photo.h>500){ndc+='<div class="c_p_l_c_i_a" style="height:500px;"><a href="'+ photo.findurl+'" target="_blank"><img src="'+ photo.linkurl+'" alt="'+photo.name+'" /></a></div>';}else{ndc+='<div class="c_p_l_c_i_a" style="height:'+ photo.h+'px;"><a href="'+ photo.findurl+'" target="_blank"><img src="'+ photo.linkurl+'" alt="'+photo.name+'" /></a></div>';}
ndc+='<div class="c_p_l_c_i_b"><a href="'+ photo.findurl+'" target="_blank">'+ photo.name+'</a></div>';if(typeof photo.intro!="undefined")
ndc+='<div class="c_p_l_c_i_c"></div>';ndc+='<div class="c_p_l_c_i_d">';if(photo.love!=1){ndc+='<SPAN class="favr" onclick="enjoy('+photo.id+');">喜欢</SPAN></div>';}else{ndc+='<SPAN class="favred" onclick="delenjoy('+photo.id+');">移除</SPAN></div>';}
ndc+='<DIV class="c_p_l_c_i_e"></DIV>';if(photo.h>500){ndc+='<A href="/'+photo.findurl+'" target="_blank">';ndc+='<div class="longimgtop" onmouseover="moveup('+photo.id+')" onmouseout="stopAni('+photo.id+')"></div>';ndc+='<div class="longimgbottom" onmouseover="movedown('+photo.id+','+photo.h+')" onmouseout="stopAni('+photo.id+')"></div>';ndc+='<div class="longimg"></div>';}
ndc+='</div>';minhx=0;for(var x=0;x<cols.length;x++){if(cols.eq(minhx).height()>cols.eq(x).height())
minhx=x;}
cols.eq(minhx).append(ndc);});lockrelist=false;}else
$(".container_photo_list").scrollshowtips("null");}});}}}}