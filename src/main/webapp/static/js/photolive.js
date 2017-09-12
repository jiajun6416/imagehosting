function edit_i(pid) {
    if (true) {
        var pname = $('.photo_b_a_a span strong').html();
        s = ' <div class="register_f_a" >图片名称：<input type="text" name="pname" value="' + pname + '" style="float:none;width:200px"/><button id="delpicbtn" onclick="delpic(' + pid + ');return false" >删除</button></div>';
        $.dialog.showMsgFuncLayer("编辑/删除图片", s,
        function() {
            $.ajax({
                beforeSend: function() {
                	var inputName = $('input[name="pname"]').val();
                	if (inputName.length < 3 || inputName.length > 32) {
                        $.dialog.showMsgLayer('提示', '相册名称在3-32位之间！');
                        return false;
                    }
                	if(inputName == pname) {
                		$.dialog.showMsgLayer('提示', '未作修改!');
                		return false;
                	}
                    $.prompt.show('正在提交......');
                },
                complete: function() {
                    $.prompt.hidden()
                },
                cache: false,
                data: {
                    'pId': pid,
                    'pName': $('input[name="pname"]').val()
                },
                error: function() {
                    $.dialog.showMsgLayer('系统异常', '对不起，系统出现异常，修改失败！');
                },
                success: function(t) {
                    if (t.code == 200) {
                    	$('.photo_b_a_a span strong').html(t.data);
                    	$('.user_nav_a span').html(t.data);
                        $.dialog.hidden();
                    } else if(t.code == 403) {
                        $.dialog.showMsgLayer('提示', '登录超时，请重新登录');
                    } else {
                    	 $.dialog.showMsgLayer('提示', t.message);
                    }
                },
                type: 'POST',
                url: basePath+'update'
            });
        },
        0);
    } else $.dialog.showMsgFuncLayer("请登录", "对不起，仅登录用户可进行此操作！",
    function() {
        showSignIn();
    });
}
function delpic(pid) {
    $.dialog.showFuncLayer(430, "确认删除", '确定要删除这张图片吗？',
    function() {
        if (true) {
            $.post(basePath+"delete", {'pId': pid},
    	            function(t) {
                    if (t.code == 200) {
                        $.dialog.showMsgFuncLayer("成功", "删除成功",
                        function() {
                            //退回相册
                        	// window.location.reload();
                        	window.location.href=basePath+"list/"+aid;
                        },
                        1);
                    } else if(t.code == 403) {
                        $.dialog.showMsgFuncLayer("登录失效", "登录已失效，请重新登录！",
                                function() {
                                    showSignIn();
                                },
                              1);
                    } 
                    else {
                        $.dialog.showMsgLayer("提示", t.data);
                    }
                });
        }
    });
}
var favrwait = false,
commwait = false,
sharewait = false;
$(".c_p_l_c_i_d .favr").live('click',
function() {
    var theo = $(this);
    if (getcookie('uid')) {
        if (!favrwait) {
            $.ajax({
                beforeSend: function() {
                    favrwait = true;
                    $.prompt.show('正在加入喜欢列表......')
                },
                complete: function() {
                    favrwait = false;
                    $.prompt.hidden()
                },
                cache: false,
                data: {
                    'pid': theo.parents('.c_p_l_c_i').attr('pid') || theo.parents('.wallpaper_l_i').attr('pid')
                },
                error: function() {
                    $.dialog.showMsgLayer('系统异常', '对不起，系统出现异常，加入喜欢列表失败！');
                },
                success: function(t) {
                    if (t.status == 2) {
                        $.dialog.showMsgFuncLayer("登录失效", "登录已失效，请重新登录！",
                        function() {
                            showSignIn();
                        },
                        1);
                    } else if (t.status == 1) {
                        theo.attr("class", "favred");
                        theo.html("移除");
                        $.dialog.hidden();
                    } else if (t.status == 1) {
                        $.dialog.showMsgLayer("提示", "已经在喜欢列表里面了");
                    }
                },
                type: 'POST',
                url: '/?m=Home&c=User&a=addenjoy_ajax'
            });
        }
    } else $.dialog.showMsgFuncLayer("请登录", "对不起，仅登录用户可进行此操作！",
    function() {
        showSignIn();
    });
});
$(".c_p_l_c_i_d .favred").live('click',
function() {
    var theo = $(this);
    if (getcookie('uid')) {
        if (!favrwait) {
            $.ajax({
                beforeSend: function() {
                    favrwait = true;
                    $.prompt.show('正在从喜欢列表中移除......')
                },
                complete: function() {
                    favrwait = false;
                    $.prompt.hidden()
                },
                cache: false,
                data: {
                    'pid': theo.parents('.c_p_l_c_i').attr('pid') || theo.parents('.wallpaper_l_i').attr('pid')
                },
                error: function() {
                    $.dialog.showMsgLayer('系统异常', '对不起，系统出现异常，从喜欢列表中移除失败！');
                },
                success: function(t) {
                    if (t.status == 0) {} else if (t.status == 1) {
                        theo.attr("class", "favr");
                        theo.html("喜欢");
                        $.dialog.hidden();
                    } else {
                        $.dialog.showMsgLayer("操作失败", "操作失败");
                    }
                },
                type: 'POST',
                url: '/?m=Home&c=User&a=delenjoy_ajax'
            });
        }
    } else $.dialog.showMsgFuncLayer("请登录", "对不起，仅登录用户可进行此操作！",
    function() {
        showSignIn();
    });
});
$(".c_p_l_c_i_d .comm").live('click',
function() {
    var theo = $(this);
    if (getcookie('uid') == 1) {
        var pid = theo.parents('.c_p_l_c_i').attr('pid');
        $.post("/?c=User&a=setrec", {
            'pid': pid
        },
        function(t) {
            if (t.code == '200') {
                theo.attr('class', 'commed').html('取消');
                $.prompt.show("推荐成功");
                setTimeout(function() {
                    $.prompt.hidden()
                },
                1500);
            } else {
                $.dialog.showMsgFuncLayer("错误", "操作失败");
            }
        })
    } else $.dialog.showMsgFuncLayer("请登录", "对不起，仅admin可进行此操作！",
    function() {
        showSignIn();
    });
});
$(".c_p_l_c_i_d .commed").live('click',
function() {
    var theo = $(this);
    if (getcookie('uid') == 1) {
        var pid = theo.parents('.c_p_l_c_i').attr('pid');
        $.post("/?c=User&a=cancelrec", {
            'pid': pid
        },
        function(t) {
            if (t.code == '200') {
                theo.attr('class', 'comm').html('推荐');
                $.prompt.show("取消推荐成功");
                setTimeout(function() {
                    $.prompt.hidden()
                },
                1500);
            } else {
                $.dialog.showMsgFuncLayer("错误", "操作失败");
            }
        })
    } else $.dialog.showMsgFuncLayer("请登录", "对不起，仅admin可进行此操作！",
    function() {
        showSignIn();
    });
});
$(".c_p_l_c_i_d .editor").live('click',
function() {
    var theo = $(this);
    var pid = theo.parents('.c_p_l_c_i').attr('pid');
    var pname = theo.parents('.c_p_l_c_i').find('.c_p_l_c_i_b a').html();
    s = ' <div class="register_f_a" >图片名称：<input type="text" name="pname" value="' + pname + '" style="float:none;width:200px"/><button id="delpicbtn"  onclick="delpic(' + pid + ');return false" >删除</button></div>';
    $.dialog.showMsgFuncLayer("编辑/删除图片", s,
	    function() {
	        $.ajax({
	            beforeSend: function() {
	            	var inputName = $('input[name="pname"]').val();
	            	if(inputName == pname) {
	            		$.dialog.showMsgLayer("提示", "图片名未修改!");
	            		return false;
	            	}
	            	if (inputName.length < 2 || inputName.length > 32 ) {
	            		$.dialog.showMsgLayer('提示', '图片名在2-32位之间');
	            		return false;
	            	}
	                $.prompt.show('正在提交......');
	            },
	            complete: function() {
	                $.prompt.hidden()
	            },
	            cache: false,
	            data: {'pId': pid,'pName': $('input[name="pname"]').val()},
	            error: function() {
	                $.dialog.showMsgLayer('系统异常', '对不起，系统出现异常，修改失败！');
	            },
	            success: function(result) {
	            	$.dialog.hidden();
	            	if(result.code == 200) {
	                    $('#box' + pid).find('.c_p_l_c_i_b a').html(result.data);
	            	} else if(result.code == 403) {
	            		$.dialog.showMsgFuncLayer("请登录", "对不起，仅登录用户可进行此操作！",function() {
	            			showSignIn();
	            		});
	            	} else {
	            		 $.dialog.showMsgLayer('提示', result.message);
	            	}
	            },
	            type: 'POST',
	            url: basePath+'update'
	        });
	    },
	 0);
});
$(".c_p_l_c_i_d .share").live('click',
function() {});
$(".c_p_l_c_i_d .wl").live('click',
function() {});
$(".c_p_l_c_i_d .down").live('click',
function() {
    var link = $(this).attr('data-link');
    var rlink = $(this).attr('data-real');
    var sw = $(this).attr('data-for');
    var swah = $(this).attr('data-swah');
    var wha = [0, 0];
    if (swah) wha = swah.split(',');
    download(link, sw, wha[0], wha[1], rlink);
});
function download(link, sw, w, h, rlink) {
    if (getcookie('uid')) {
        if (w && h) {
            s = '尺寸：<select id="downfor"><option value="opic">原图</option><option value="spic">展示图</option></select>';
            $.dialog.showMsgFuncLayer("请选择尺寸", s,
            function() {
                if ($("#downfor").val() == 'opic') {
                    download(rlink, $("#downfor").val());
                } else if ($("#downfor").val() == 'spic') {
                    download(link, $("#downfor").val());
                } else {
                    alert('error');
                }
            });
        } else {
            var form = $("<form>");
            form.attr("action", link);
            form.attr("method", "get");
            form.attr("target", '_blank');
            var input = $("<input>");
            input.attr("type", "hidden");
            input.attr("name", "for");
            input.attr("value", sw);
            input.appendTo(form);
            form.appendTo(document.body);
            form.submit();
            input.remove();
            form.remove();
        }
    } else $.dialog.showMsgFuncLayer("请登录", "对不起，仅登录用户可进行此操作！",
    function() {
        showSignIn();
    });
}