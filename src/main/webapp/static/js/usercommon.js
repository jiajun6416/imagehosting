function getUserFromPath(){var user=window.location.pathname;user=user.substring(1);user=user.substring(0,user.indexOf('/'));return user;}
var followWait=false;function follow(user,act){if($.cookie('puid')){if(!followWait){$.ajax({beforeSend:function(){followWait=true;},complete:function(){followWait=false;},cache:false,data:{'user':user,'act':act},error:function(){$.dialog.showMsgLayer('系统异常','对不起，系统出现异常，操作失败！');},success:function(result){if("unsignin"==result){$.dialog.showMsgFuncLayer("登录失效","登录已失效，请重新登录！",function(){showSignIn();},1);}else if("ok"==result){window.location.href=window.location.href;}else{$.dialog.showMsgLayer('操作失败',"呃，操作失败了！");}},type:"POST",url:'/followuser.do'});}}else
$.dialog.showMsgFuncLayer("请登录","对不起，仅登录用户可进行此操作！",function(){showSignIn();});}
function sendmsg(u){if($.cookie('puid')){var un=$('.un_'+ u).html();$.dialog.showIframeDialog(530,'发送私信','<div class="dsmtit">收信人：'+(un?un:u)+'</div>\
            <div class="dsmcont"><textarea rows="8" cols="60" onkeyup="smiptchange(\'#smsg\',\'#smiptless\')" onkeydown="ctrlEnter(event,\'#dsmsgbtn\')" id="smsg"></textarea></div>\
            <div class="dsmbtm">还可以输入<label id="smiptless">150</label>字<input type="button" value="发送" id="dsmsgbtn" class="cbtn" onclick="dosendmsg(\''+ u+'\')" title="或 Ctrl+Enter"/></div>');if($("#smsg").length)
$("#smsg").focus();}else
$.dialog.showMsgFuncLayer("请登录","对不起，仅登录用户可进行此操作！",function(){showSignIn();});}
var sendmsgWait=false;function dosendmsg(u){var msg=$.trim($("#smsg").val());if(!msg.length)
$.alert.show("请输入你要发送的私信内容！");else{if(!sendmsgWait){$.ajax({beforeSend:function(){sendmsgWait=true;$.prompt.show('正在发送私信......');},complete:function(){sendmsgWait=false;$.prompt.hidden();},cache:false,data:{'user':u,'msg':escape(msg)},error:function(){$.alert.show('对不起，系统出现异常，发送私信失败！');},success:function(result){if("mgc"==result){$.alert.show("私信中包含敏感词，请修改！");}else{if("unsignin"==result){$.dialog.showMsgFuncLayer("登录失效","登录已失效，请重新登录！",function(){showSignIn();},1);}else if("locked"==result){$.alert.show("呃，你的账户处于锁定状态，不能给他人发送私信！");}else if("wait"==result){$.alert.show("呃，你的发信速度太快了，请歇息下再发！");}else if("ok"==result){$("#smsg").val('');if(/\/message-with-\w+\.html/i.test(window.location.href))
$.alert.show("发送成功！",function(){window.location.href=window.location.href;});else
$.alert.show("发送成功！");$.dialog.hidden();}else{$.alert.show("发送失败！");}}},type:"POST",url:'/sendmsg.do'});}}}
function smiptchange(tao,wwo){var val=$(tao).val();val=$.trim(val);var less=150- val.length;if(less<0){$(tao).val(val.substr(0,150));$(wwo).html(0);}else
$(wwo).html(less);}
function isKeyTrigger(e,keyCode){var argv=isKeyTrigger.arguments;var argc=isKeyTrigger.arguments.length;var bCtrl=false;if(argc>2){bCtrl=argv[2];}
var bAlt=false;if(argc>3){bAlt=argv[3];}
var nav4=window.Event?true:false;if(typeof e=='undefined'){e=event;}
if(bCtrl&&!((typeof e.ctrlKey!='undefined')?e.ctrlKey:e.modifiers&Event.CONTROL_MASK>0)){return false;}
if(bAlt&&!((typeof e.altKey!='undefined')?e.altKey:e.modifiers&Event.ALT_MASK>0)){return false;}
var whichCode=0;if(nav4)whichCode=e.which;else if(e.type=="keypress"||e.type=="keydown")
whichCode=e.keyCode;else
whichCode=e.button;return(whichCode==keyCode);}
function ctrlEnter(e,dom){if($.browser.msie){if(event.ctrlKey&&event.keyCode==13)
$(dom).click();}else{if(isKeyTrigger(e,13,true))
$(dom).click();}}