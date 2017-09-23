var iptless = function(exp, maxipt, lab) {
    var val = $(exp).val();
    if (val.length > maxipt) {
        val = val.substr(0, maxipt);
        $(exp).val(val);
    }
    if (lab) lab.html(val.length);
    else $(exp).parent().find("label").first().html(val.length);
}
var loadalbumwait = false,
creatalbumwait = false;

$(function() {
	//查询cookie, 设置选中的album
	var selectAId = $.cookie("selected_album_"+user);
	$('#albums').val(selectAId);
});

$('#albums').change(function() {
	//设置cookie, 存储用户行为, 并且session缓存
	var albumId = $(this).val();
	d_album = albumId;
	
    $.post(basePath+'album/selected', 
    		{"albumId": albumId},
		    function(result) {
		        if (result.code == 200) {
		        	//存储cookie
		    		$.cookie('selected_album_'+user, albumId, {
		    			expires : 30, path:'/'
		    		});
		        }
		    });
}); 

$(".upload_c_a_b").hover(function() {
    $(this).css("border-color", "#0c0");
    $(".upload_c_a_b_b").css("background-position", "-105px -110px");
},
function() {
    if (!$('.upload_c_a_c:visible').length) {
        $(this).css("border-color", "#ccc");
        $(".upload_c_a_b_b").css("background-position", "0 -110px");
    }
}).click(function() {
    return false;
    var ealo = $('.upload_c_a_c');
    if (ealo.length) ealo.toggle();
    else {
        if (!loadalbumwait) {
            $.ajax({
                beforeSend: function() {
                    loadalbumwait = true;
                    $.prompt.show('正在加载相册列表......');
                },
                complete: function() {
                    loadalbumwait = false;
                },
                cache: false,
                dataType: "json",
                error: function() {
                    $.prompt.show('加载相册列表失败！');
                    setTimeout(function() {
                        $.prompt.hidden();
                    },
                    3000);
                },
                success: function(result) {
                    if (result && result.album && $.isArray(result.album)) {
                        $.prompt.hidden();
                        var al = '';
                        al += '<span class="upload_c_a_c" style="z-index:100">';
                        if (result.album.length) {
                            al += '<span class="upload_c_a_c_a">';
                            $.each(result.album,
                            function(i, v) {
                                al += '<label pri_album="' + v.type + '" data-obj="' + v.aid + '" title="' + v.albumname + '">' + v.albumname + '（' + v.num + '）</label>';
                            });
                            al += '</span>';
                        }
                        al += '<span class="upload_c_a_c_b">';
                        al += '<span class="upload_c_a_c_b_a">';
                        al += '<input type="text" name="newalbum" maxlength="20" />';
                        al += '<span><label>0</label><label class="upload_c_a_c_b_a_s">/</label>20</span>';
                        al += '</span>';
                        al += '<input type="button" value="创建相册" class="cbtn upload_c_a_c_b_a_b" />';
                        al += '</span>';
                        al += '</span>';
                        $('.upload_c_a').append(al);
                        $('.upload_c_a_c_a label').click(function() {
                            $('.upload_c_a_b_a').html($(this).html().replace(/（\d+）$/g, ''));
                            $.post('/?m=Home&c=Pic&a=setDefaultAlbum', {
                                'pri_album': $(this).attr("pri_album"),
                                'd_album': $(this).attr("data-obj"),
                                'd_album_name': $(this).html().replace(/（\d+）$/g, '')
                            },
                            function(t) {
                                if (t) {
                                    window.location.reload();
                                }
                            });
                            $(document).click();
                        });
                        $('.upload_c_a_c_b_a :text[name=newalbum]').keyup(function() {
                            iptless(this, 20);
                        });
                        $(':button.upload_c_a_c_b_a_b').click(function() {
                            var newalbum = $('.upload_c_a_c_b_a :text[name=newalbum]').val();
                            if (!newalbum.length) $.alert.show("请输入相册名称！");
                            else {
                                if (!creatalbumwait) {
                                    $.ajax({
                                        beforeSend: function() {
                                            creatalbumwait = true;
                                            $.prompt.show('正在创建相册......')
                                        },
                                        complete: function() {
                                            creatalbumwait = false;
                                            $.prompt.hidden()
                                        },
                                        cache: false,
                                        data: {
                                            "name": newalbum
                                        },
                                        error: function() {
                                            $.alert.show('对不起，系统出现异常，相册创建失败！');
                                        },
                                        success: function(result) {
                                            if ("mgc" == result) {
                                                $.alert.show("相册名称中包含敏感词，请修改！");
                                            } else if (result == "had") {
                                                $.alert.show('已存在同名相册，请更改名称！');
                                            } else if (result == 'ok') {
                                                $.alert.show("创建成功",
                                                function() {
                                                    window.location.reload();
                                                });
                                            } else if (result == "unsignin") $.alert.show("登录已失效，请重新登录！",
                                            function() {
                                                showSignIn();
                                            });
                                            else if (result == "ulock") $.alert.show('你的账户被锁定了，不能完成操作！');
                                            else {
                                                $.alert.show('相册创建失败！');
                                            }
                                        },
                                        type: 'POST',
                                        url: '#'
                                    });
                                }
                            }
                        });
                    } else {
                        $.prompt.show('加载相册列表失败！');
                        setTimeout(function() {
                            $.prompt.hidden();
                        },
                        3000);
                    }
                },
                url: '#'
            });
        }
    }
    return false;
});
$(".upload_c_a").click(function() {
    return false;
});
$(document).click(function() {
    var ealo = $('.upload_c_a .upload_c_a_c');
    if (ealo.length) {
        ealo.hide();
        $(".upload_c_a_b").css("border-color", "#ccc");
        $(".upload_c_a_b_b").css("background-position", "0 -110px");
    }
});
var fileServer = '';
var imageInfo = [];
var imageCount = 0;
var dontleave = false;
function initnormal() {
    $(".upload_t span").html($("<a>").attr("href", "#flash").html("Flash上传").click(function() {
        initflash();
    }));
    $("#uploadcontainer").empty();
    imageInfo = [];
    imageCount = 0;
    var uploadnormal = $('<div class="upload_c_n"><div id="uploadcontainernormal"><div class="upload_c_n_i"><span>本地图片</span><input type="file" name="picdata" /></div></div></div>');
    var addfile = function(obj) {
        obj.one("change",
        function() {
            if (imageCount > 299) {
                $.dialog.showMsgLayer('提示', '一次最多只能上传300张图片！');
            } else {
                if ($(this).parents('.upload_c_n').find('.upload_c_n_i').last().find(":file[name='picdata']").val().length) {
                    var ni = $('<div class="upload_c_n_i"><span>&nbsp;</span><input type="file" name="picdata" />&nbsp;&nbsp;<a href="###" onclick="return false;">[移除]</a></div>');
                    ni.find("a").click(function() {
                        addfile($(this).parents('.upload_c_n_i').prev('.upload_c_n_i').find(":file[name='picdata']"));
                        $(this).parents('.upload_c_n_i').remove();
                        imageCount--;
                    });
                    addfile(ni.find(":file[name='picdata']"));
                    ni.appendTo("#uploadcontainernormal");
                    imageCount++;
                }
            }
        });
    };
    addfile(uploadnormal.find(":file[name='picdata']"));
    uploadnormal.find(":file[name='picdata']").live("change",
    function() {
        var val = this.value;
        if (val.length) {
            var extension = val.substr(val.lastIndexOf(".") + 1).toLowerCase();
            if (! (extension == "bmp" || extension == "gif" || extension == "png" || extension == "jpg" || extension == "jpeg")) $.dialog.showMsgLayer("提示", "只能上传JPG、JPEG、GIF、PNG、BMP格式的图片！");
        }
    });
    uploadnormal.appendTo("#uploadcontainer");
    imageCount++;
    $("#uploadbtn").show();
    $("#uploadbtn").unbind('click');
    $("#uploadbtn").click(function() {
        var val, extension;
        var uplistarray = new Array();
        var gonext = true;
        uploadnormal.find(":file[name='picdata']").each(function() {
            val = this.value;
            if (val.length) {
                extension = val.substr(val.lastIndexOf(".") + 1).toLowerCase();
                if (extension == "gif" || extension == "png" || extension == "jpg" || extension == "jpeg") {
                    var has = false;
                    for (var i = 0; i < uplistarray.length; i++) {
                        if (uplistarray[i] == val) {
                            $(this).parents('.upload_c_n_i').remove();
                            imageCount--;
                            has = true;
                            break;
                        }
                    }
                    if (!has) uplistarray.push(val);
                } else {
                    $.dialog.showMsgLayer("提示", "上传图片格式只可以是JPG、JPEG、GIF、PNG、BMP！");
                    gonext = false;
                    return false;
                }
            } else {
                if (imageCount > 1) {
                    $(this).parents('.upload_c_n_i').remove();
                    imageCount--;
                }
            }
        });
        if (gonext) {
            if (uploadnormal.find(":file[name='picdata']").length && uploadnormal.find(":file[name='picdata']").val().length) {
                up("#uploadcontainernormal .upload_c_n_i", {},
                fileServer + '/?action=upload&w=n', fileServer + '/?action=status',
                function(result) {
                    imageInfo = eval('[' + result.replace(/}{/g, '},{') + ']');
                    wsuploaded();
                });
                $("#uploadbtn").hide();
                dontleave = true;
            } else $.dialog.showMsgLayer("提示", "请选择要上传的图片！");
        }
    });
}
function initquote() {
    $("#uploadcontainer").empty();
    imageInfo = [];
    imageCount = 0;
    $("#uploadcontainer").html('<div class="upload_c_q"><div class="upload_c_q_a">请输入网址：<input type="text" class="upload_c_q_a_ipt" /><input type="button" class="upload_c_q_a_btn cbtn" value="查找图片" onclick="asyncloadimg()" /></div></div>');
    $("#uploadcontainer").find(":input").keypress(function(e) {
        var key = e.charCode ? e.charCode: e.keyCode ? e.keyCode: 0;
        if (key == 13) {
            asyncloadimg();
        }
    });
    $("#uploadbtn").hide();
}
function addmorepic() {
    if (!$('#albums').val()) {
        $.dialog.showMsgLayer("加载失败", "未登录每次只能上传一张图片");
        return false;
    }
    var url = $.trim($("#morepicv").val());
    if (url.length && url.indexOf('.') > 0) {
        $("<img>").load(function() {
            var ris = '';
            ris += '<div class="upload_c_q_r_b_i">';
            ris += '<div class="upload_c_q_r_b_i_i"><img src="' + this.src + '" alt=""/></div>';
            ris += '<div class="upload_c_q_r_b_i_c">';
            ris += this.width + 'x' + this.height;
            ris += '</div>';
            ris += '</div>';
            $(ris).appendTo(('.upload_c_q_r_b'));
            $("#morepicv").val('');
            $("#morepicv").focus();
        }).error(function() {
            $.dialog.showMsgLayer("加载失败", "这不是一个有效的图片地址！");
        }).attr("src", url);
    } else $("#morepicv").focus();
}

$(".upload_c_q_a_ipt").focus();
$(".upload_c_q_a_ipt").keypress(function(e) {
	if(e.keyCode == 13) {
		asyncloadimg();
	}
});


var asyncloadimgwait = false;
function asyncloadimg() {
	if($('#albums').val() == 0) {
		$.dialog.showMsgFuncLayer("提示", "请选择一个相册再上传");
		return;
	}
    var url = $.trim($(".upload_c_q_a_ipt").val());
    if(url == null || url.length==0) {
    	return false;
    } 
    if(!isImageUrl(url)) {
    	$.dialog.showMsgFuncLayer("提示", "网址错误, 请以http/https开头, 且支持JPG、JPEG、GIF、PNG格式图片");
    	$(".upload_c_q_a_ipt").focus();
    	return false;
    }
    if (url.length) {
        $.ajax({
            type: "POST",
            url: basePath+"network/upload",
            data: {"fileurl": url},
            beforeSend: function() {
                $.prompt.show('努力上传中......');
            },
            success: function(result) {
                $.prompt.hidden();
                var code = result.code;
                if (code == 200) {
                    $.dialog.showMsgFuncLayer("提示", "上传成功",
                    function() {
                        $.dialog.hidden();
                        window.location.href = basePath+"detail/" + result.data;
                    });
                } else if(code == 403) {
                    $.dialog.showMsgLayer("上传失败", "登录信息过期,请先登录", function() {
                    	showSignIn();
                    }, 1);
                } else {
                	$.dialog.showMsgLayer("上传失败", result.message);	
                }
            },
            error: function() {
            	$.prompt.hidden();
                $.dialog.showMsgLayer("上传失败", "服务器异常...");
            }
        });
    } else $(".upload_c_q_a_ipt").focus();
}

function isImageUrl(url) {
	var re = /((https|http):\/\/)[^\s]+\.(jpg|png|jpeg|gif)/gi;
	return re.test(url);
}

var asyncupwait = false;
function asyncloadimgover(surl, wris, result) {
    if (wris && wris.length) {
        var ris = '<div class="upload_c_q_r">';
        ris += '<div class="upload_c_q_r_a">请选择要上传的图片</div>';
        ris += '<div class="upload_c_q_r_b">';
        ris += wris;
        ris += '</div>';
        ris += '<div class="upload_c_q_r_c"></div>';
        ris += '</div>';
        $(".upload_c_q").empty();
        $(".upload_c_q").html(ris);
        $(".upload_c_q").find("#checkall").click(function() {
            $(".upload_c_q").find(":checkbox").attr("checked", "checked");
        });
        $(".upload_c_q").find("#invert").click(function() {
            $(".upload_c_q").find(":checkbox").each(function() {
                if ($(this).is(":checked")) $(this).attr("checked", "");
                else $(this).attr("checked", "checked");
            });
        });
        $("#uploadbtn").show();
        $("#uploadbtn").unbind('click');
        $("#uploadbtn").click(function() {
            $.prompt.show('努力上传中......');
            $('.upload_c_q_a').hide();
            $(".upload_c_b_a #uploadbtn").val('上传中...').attr('disabled', true).css('background', '#A3A3A3');
            var imgurls = '';
            var obj = $('.upload_c_q_r_b_i img');
            for (var i = 0; i < obj.length; i++) {
                imgurls += obj[i].src + '|||||';
            }
            $.post("/?m=Home&c=Pic&a=webup", {
                'imgs': imgurls
            },
            function(t) {
                if (t.status == 1) {
                    $.prompt.hidden();
                    $.dialog.showMsgFuncLayer("上传成功", t.info,
                    function() {
                        if (t.num > 1) {
                            window.location.href = '/album/' + getcookie('d_album');
                        } else {
                            window.location.href = '/' + t.ps;
                        }
                    },
                    1);
                } else {
                    $.prompt.hidden();
                    $.dialog.showMsgFuncLayer("上传失败", t.info);
                }
            });
        });
    } else $.dialog.showMsgFuncLayer("没有满足条件的图片", "呃，没有找到宽或高大于200像素的图片哦！",
    function() {
        initquote();
        $.dialog.hidden();
    },
    1);
}
var wswait = false;
function wsuploaded() {
    if (imageInfo.length) {
        window.onbeforeunload = function() {
            if (dontleave) window.event.returnValue = '图片上传未完成，确定要离开吗？';
        };
        var container = $(".upload");
        container.empty();
        var ws = '<div class="upload_t">完善图片信息</div>';
        ws += '<div class="uploaded_ws_a">';
        ws += '<span class="uploaded_ws_a_l"><span>同步分享到</span><span class="user_t_r_b"><span class="bdqq"></span><span class="bdsina"></span><span class="bdrenren"></span><span class="bdkaixin"></span></span></span>';
        ws += '<span class="uploaded_ws_a_r"><input type="checkbox" name="wsbat" value="" class="cbrva" /> 统一添加信息</span>';
        ws += '</div>';
        ws += '<div class="uploaded_ws_b">';
        ws += '<div class="uploaded_ws_b_a"><label>名称：</label><input type="text" id="cws_name" maxlength="30" /><label class="cog">*</label><span><label class="iptless">0</label>/30</span></div>';
        ws += '<div class="uploaded_ws_b_a"><label>标签：</label><input type="text" id="cws_tag" maxlength="30" /><label class="cog">*</label><span><label class="iptless">0</label>/30</span></div>';
        ws += '<div class="uploaded_ws_b_a uploaded_ws_b_h"><label>描述：</label><textarea id="cws_intro" rows="4" cols="101"></textarea><span class="uploaded_ws_b_h_s"><label class="iptless">0</label>/150</span></div>';
        ws += '</div>';
        ws += '<form id="ws_form" action="" method="post" onsubmit="return false;">';
        for (var idx in imageInfo) {
            ws += '<input type="hidden" name="obj" value="' + imageInfo[idx].obj + '" />';
            ws += '<div class="uploaded_ws_c">';
            ws += '<div class="uploaded_ws_c_a"><div class="uploaded_ws_c_a_a"><img src="' + imageInfo[idx].preview + '" alt="" /></div></div>';
            ws += '<div class="uploaded_ws_c_b">';
            ws += '<div class="uploaded_ws_b_a"><label>名称：</label><input type="text" name="name" maxlength="30" value="' + imageInfo[idx].name + '" /><label class="cog">*</label><span><label class="iptless">' + imageInfo[idx].name.length + '</label>/30</span></div>';
            ws += '<div class="uploaded_ws_b_a"><label>标签：</label><input type="text" name="tag" maxlength="30" value="" /><label class="cog">*</label><span><label class="iptless">0</label>/30</span></div>';
            ws += '<div class="uploaded_ws_b_a uploaded_ws_b_h"><label>描述：</label><textarea name="intro" rows="4" cols="101">' + imageInfo[idx].intro + '</textarea><span class="uploaded_ws_b_h_s"><label class="iptless">' + imageInfo[idx].intro.length + '</label>/150</span></div>';
            ws += '</div>';
            ws += '</div>';
        }
        ws += '</form>';
        ws += '<div class="upload_c_b_a uploaded_ws_d"><input type="button" id="ws_btn" class="cbtn" value="保存" /></div>';
        container.html(ws);
        $.getJSON("/getuserconnect.do?v=" + Math.random(),
        function(rs) {
            if (rs) container.find(".user_t_r_b").html(rs.uc);
        });
        var getconnecting = function(url) {
            var form = $("<form>");
            var ua = url.split("?");
            if (ua.length > 1) {
                var qp = ua[1].split("&");
                $.each(qp,
                function(i, p) {
                    var kv = p.split("=");
                    if (kv.length > 1) {
                        var input = $("<input>");
                        input.attr("type", "hidden");
                        input.attr("name", kv[0]);
                        input.attr("value", kv[1]);
                        input.appendTo(form);
                    }
                });
            }
            form.attr("action", ua[0]);
            form.attr("method", "get");
            form.attr("target", '_blank');
            form.appendTo(document.body);
            form.submit();
            form.empty();
            form.remove();
        };
        container.find(".user_t_r_b span").live("click",
        function() {
            if ($(this).is("[class^='bd']")) $(this).attr("class", $(this).attr("class").replace("bd", ""));
            else {
                if ($(this).is("[data-bd='0']")) {
                    var tc = $(this).attr("class");
                    if (tc == "qq") getconnecting('https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=100271575&redirect_uri=http://www.tuzhan.com/connect-tx.do&scope=get_user_info,add_share,get_info,add_pic_t,add_idol');
                    else if (tc == "sina") getconnecting('https://api.weibo.com/oauth2/authorize?client_id=843887157&response_type=code&redirect_uri=http://www.tuzhan.com/connect-xl.do');
                    else if (tc == "renren") getconnecting('https://graph.renren.com/oauth/authorize?client_id=f5d67f69833c4179b4e10a6e47d22a0a&redirect_uri=http://www.tuzhan.com/connect-rr.do&response_type=code&scope=publish_feed publish_share');
                    else if (tc == "kaixin") getconnecting('http://api.kaixin001.com/oauth2/authorize?client_id=8500894183902c301fda1674c0f47afc&response_type=code&redirect_uri=http://www.tuzhan.com/connect-kx.do&scope=basic user_birthday user_intro send_feed');
                    $(this).removeAttr("data-bd");
                    $(this).attr("class", "bd" + $(this).attr("class"));
                } else $(this).attr("class", "bd" + $(this).attr("class"));
            }
        });
        container.find(":checkbox[name='wsbat']").click(function() {
            if ($(this).is(":checked")) container.find(".uploaded_ws_b").show();
            else container.find(".uploaded_ws_b").hide();
        });
        container.find("#cws_name").keyup(function() {
            var nv = this.value;
            container.find(":input[name='name']").each(function(i) {
                this.value = nv + "_" + (i + 1);
                iptless(this, 30, $(this).parent().find("label.iptless").first());
            });
            iptless(this, 30, $(this).parent().find("label.iptless").first());
        });
        container.find("#cws_tag").keyup(function() {
            var tv = this.value;
            container.find(":input[name='tag']").each(function(i) {
                this.value = tv;
                iptless(this, 30, $(this).parent().find("label.iptless").first());
            });
            iptless(this, 30, $(this).parent().find("label.iptless").first());
        });
        container.find("#cws_intro").keyup(function() {
            var iv = this.value;
            container.find("textarea[name='intro']").each(function(i) {
                this.value = iv;
                iptless(this, 150, $(this).parent().find("label.iptless").first());
            });
            iptless(this, 150, $(this).parent().find("label.iptless").first());
        });
        container.find(":input[name='name']").keyup(function(e) {
            iptless(this, 30, $(this).parent().find("label.iptless").first());
        });
        container.find(":input[name='tag']").keyup(function(e) {
            iptless(this, 30, $(this).parent().find("label.iptless").first());
        });
        container.find("textarea[name='intro']").keyup(function(e) {
            iptless(this, 150, $(this).parent().find("label.iptless").first());
        });
        var sug_tags = '';
        container.find("#cws_tag,:input[name='tag']").focus(function() {
            var nthis = $(this);
            if (sug_tags.length) showsugtags(nthis);
            else {
                $.getJSON("/suggesttags.do?v=" + Math.random(),
                function(rs) {
                    if (rs) {
                        sug_tags = rs.tags;
                        showsugtags(nthis);
                    }
                });
            }
        });
        container.find("#cws_tag,:input[name='tag']").click(function() {
            return false;
        });
        $(document).click(function() {
            $.tips.hidden();
        });
        var showsugtags = function(nthis) {
            $.tips.hidden();
            $.tips.show(nthis.parent(), {
                top: 41,
                left: 41
            },
            sug_tags);
            nthis.parent().find(".us_tags a").click(function() {
                if ($(this).hasClass("current")) {
                    $(this).removeClass("current");
                    nthis.val($.trim(nthis.val().replace(new RegExp($(this).html(), "g"), '').replace(/(\s)\s/g, '$1')));
                } else {
                    var nval = $.trim(nthis.val() + ' ' + $(this).html());
                    if (nval.length < 30) {
                        $(this).addClass("current");
                        nthis.val(nval);
                    }
                }
                nthis.keyup();
                return false;
            });
            $.each(nthis.val().split(' '),
            function(i, v) {
                if ($.trim(v).length) {
                    nthis.parent().find(".us_tags a:contains('" + v.replace(/'/g, "\\'").replace(/"/g, "\\'") + "')").each(function(x, o) {
                        if ($(o).html() == v) $(o).addClass("current");
                    });
                }
            });
        };
        container.find("#ws_btn").show();
        container.find("#ws_btn").click(function() {
            $.tips.hidden();
            if (container.find(":input[name='name'][value='']").length) {
                $.dialog.showMsgLayer('提示', '请完善图片名称！');
            } else if (container.find(":input[name='tag'][value='']").length) {
                $.dialog.showMsgLayer('提示', '请完善图片标签！');
            } else if (!wswait) {
                var tbfx = 0;
                var unshare = '';
                if (container.find(".user_t_r_b span[class^='bd']").length) {
                    tbfx = 1;
                    $.each(container.find(".user_t_r_b span:not([class^='bd'])"),
                    function(i, v) {
                        unshare += $(v).attr("class") + ",";
                    });
                }
                $.ajax({
                    beforeSend: function() {
                        wswait = true;
                        $.prompt.show('正在保存......');
                    },
                    complete: function() {
                        wswait = false;
                        $.prompt.hidden();
                        dontleave = false;
                    },
                    cache: false,
                    data: $("#ws_form").serialize().replace(/,|%2c/gi, ' ') + "&tbfx=" + encodeURIComponent(tbfx) + "&unshare=" + encodeURIComponent(unshare) + "&preview=" + encodeURIComponent(imageInfo[0].preview),
                    error: function() {
                        $.dialog.showMsgLayer('系统异常', '对不起，系统出现异常，图片信息保存失败！');
                    },
                    success: function(result) {
                        if (result.indexOf("ok|") > -1) {
                            $.dialog.showMsgFuncLayer("保存成功", "图片信息保存成功！",
                            function() {
                                window.location.href = result.split('|')[1];
                            },
                            1);
                        } else $.dialog.showMsgLayer('保存失败', '图片信息保存失败！');
                    },
                    type: "POST",
                    url: '/wsuploaded.do'
                });
            }
        });
    } else {
        $.dialog.showMsgLayer('上传失败', '没有上传成功的文件！');
        dontleave = false;
    }
}