$(function() {
    $('.bds_more').css({
        'background': 'none',
        'width': '70px',
        'height': '30px',
        'margin': '0 0 0 -50px',
        'clear': 'both',
        'text-indent': '30px',
        'color': '#999',
        'font-size': '13px',
        'line-height': '28px'
    });
    $('#sub_nav').css('z-index', '1000');
    $('.user_nav_b .upload_c_a_c_b_a :text[name=newalbum]').keyup(function() {
        var val = $(this).val();
        if (val.length > 20) {
            val = val.substr(0, 20);
            $(this).val(val);
        }
        $(this).parent().find("label").first().html(val.length);
    });
    document.onkeydown = function(event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) {
            $('#dlbtnconfirm').first().click();
        }
        if (e && e.keyCode == 27) {
            $('#dlclose').first().click();
        }
    }
/*    $.post("/?c=User&a=getmessnum", {},
    function(ret) {
        if (ret > 0) {
            if (ret > 10) {
                ret = '10+'
            }
            $('#messtip').html(ret);
            $('#messnum').show();
        }
    });*/
    $('.delete.admin').click(function() {
        var title = $(this).parents('.c_p_l_c_i').find('.c_p_l_c_i_b a').html();
        var r = confirm("彻底KO掉【" + title + "】，你确定吗？");
        if (r == false) {
            return false;
        }
        var pid = $(this).parents('.c_p_l_c_i').attr('pid');
        var obj = $(this).parents('.c_p_l_c_i');
        $.post("/?m=Home&c=Index&a=ajaxDelPic", {
            pid: pid
        },
        function(data) {
            if (data.code == '1') {
                $.dialog.showMsgFuncLayer("图片彻底删除成功", data.tip,
                function() {
                    obj.remove();
                    $('.c_p_l_col').resize();
                    $.dialog.hidden();
                });
            }
        },
        "json");
    });
    $('.deleteimginfo.admin').click(function() {
        var title = $(this).parents('.c_p_l_c_i.photo').find('.photo_b_a_a strong').html();
        var r = confirm("彻底KO掉【" + title + "】，你确定吗？");
        if (r == false) {
            return false;
        }
        var pid = $(this).parents('.c_p_l_c_i.photo').attr('pid');
        $.post("/?m=Home&c=Index&a=ajaxDelPic", {
            pid: pid
        },
        function(data) {
            if (data.code == '1') {
                $.dialog.showMsgFuncLayer("图片彻底删除成功", data.tip,
                function() {
                    $.dialog.hidden();
                });
            }
        },
        "json");
    });
    $('.is_admin_delete').click(function() {
        var title = $(this).parents('.c_p_l_c_i').find('.c_p_l_c_i_b a').html();
        var pid = $(this).parents('.c_p_l_c_i').attr('pid') || $(this).attr('pid');
        var obj = $(this).parents('.c_p_l_c_i');
        $.post("/?m=Home&c=Index&a=ajax_is_admin_delete", {
            pid: pid
        },
        function(data) {
            $.prompt.show(data.tip);
            setTimeout(function() {
                $.prompt.hidden()
            },
            1000);
            if (data.code == 1) {
                obj.find('.is_admin_delete').remove();
            }
        },
        "json");
    });
}) 
function impeach(t, s) {
    $.dialog.showIframeDialog(550, '举报', '<iframe frameborder="0" width="500" height="270" marginheight="0" marginwidth="0" scrolling="no" src="/impeach?photo=' + s + '"></iframe>');
}
function showSignIn() {
    window.location.href = $(".header_nav a:contains('登录')").attr("href");
}
function isScrollBottom() {
    return $(document).scrollTop() + $(window).height() >= $(document).height() - ( !! navigator.userAgent.match(/AppleWebKit.*Mobile.*/) || !!navigator.userAgent.match(/AppleWebKit/) ? 200 : 0);
}
function backTop() {
    var scrollTopDom = $("#scrolltopdom");
    if ($(document).scrollTop() > 0) {
        if (!scrollTopDom.length) {
            scrollTopDom = $('<div id="scrolltopdom">回 到顶 部</div>');
            scrollTopDom.css({
                "position": "fixed",
                "bottom": "5px",
                "right": "5px",
                "width": "50px",
                "height": "50px",
                "padding": "10px",
                "font-size": "18px",
                "text-align": "center",
                "border": "1px solid #ccc",
                "background": "#fff",
                "color": "#999",
                "cursor": "pointer"
            });
            if ( !! window.ActiveXObject && !window.XMLHttpRequest) {
                scrollTopDom.css({
                    "position": "absolute",
                    "bottom": "auto",
                    "display": "none"
                });
            }
            scrollTopDom.hover(function() {
                scrollTopDom.css({
                    "background": "#f8f8f8",
                    "color": "#666"
                });
            },
            function() {
                scrollTopDom.css({
                    "background": "#fff",
                    "color": "#999"
                });
            });
            scrollTopDom.click(function() {
                $("html,body").animate({
                    scrollTop: 0
                },
                1000);
            });
            scrollTopDom.appendTo(document.body);
        } else scrollTopDom.show();
        if ( !! window.ActiveXObject && !window.XMLHttpRequest) {
            scrollTopDom.css({
                "top": $(document).scrollTop() + $(window).height() - 80
            });
        }
    } else if (scrollTopDom.length) scrollTopDom.hide();
}; (function($) {
    $.fn.scrollshowtips = function(act) {
        if ("show" === act && !this.find(".scrollshowtips").length) $('<div class="scrollshowtips"><span class="scrollshowtipsloading">正在加载中，请稍候......</span></div>').appendTo(this);
        else if ("null" === act) this.find(".scrollshowtips").html('呃，没有了！');
        else if ("fail" === act) this.find(".scrollshowtips").html('加载失败了！<a href="###" onclick="$(this).parent().remove();scrollload();return false;">重新加载</a>');
        else if ("remove" === act && this.find(".scrollshowtipsloading").length) this.find(".scrollshowtips").remove();
        else if ("removeall" === act) this.find(".scrollshowtips").remove();
    };
})(jQuery);
function setcookie(a, b) {
    var d = new Date();
    var v = arguments;
    var c = arguments.length;
    var e = (c > 2) ? v[2] : null;
    var p = (c > 3) ? v[3] : null;
    var m = (c > 4) ? v[4] : window.location.host;
    var r = (c > 5) ? v[5] : false;
    if (e != null) {
        var T = parseFloat(e);
        var U = e.replace(T, "");
        T = (isNaN(T) || T <= 0) ? 1 : T;
        U = ("snhdwmqy".indexOf(U) == -1 || U == "") ? 's': U.toLowerCase();
        switch (U) {
        case 's':
            d.setSeconds(d.getSeconds() + T);
            break;
        case 'n':
            d.setMinutes(d.getMinutes() + T);
            break;
        case 'h':
            d.setHours(d.getHours() + T);
            break;
        case 'd':
            d.setDate(d.getDate() + T);
            break;
        case 'w':
            d.setDate(d.getDate() + 7 * T);
            break;
        case 'm':
            d.setMonth(d.getMonth() + 1 + T);
            break;
        case 'q':
            d.setMonth(d.getMonth() + 1 + 3 * T);
            break;
        case 'y':
            d.setFullYear(d.getFullYear() + T);
            break
        }
    }
    document.cookie = a + "=" + escape(b) + ((e == null) ? "": ("; expires=" + d.toGMTString())) + ((p == null) ? ("; path=/") : ("; path=" + p)) + ("; domain=tietuku.cn") + ((r == true) ? "; secure": "")
}
function getcookieval(a) {
    var b = document.cookie.indexOf(";", a);
    if (b == -1) b = document.cookie.length;
    return unescape(document.cookie.substring(a, b))
}
function getcookie(a) {
    var v = a + "=";
    var i = 0;
    while (i < document.cookie.length) {
        var j = i + v.length;
        if (document.cookie.substring(i, j) == v) return getcookieval(j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break
    }
    return null
}
function delcookie(a) {
    var e = new Date();
    e.setTime(e.getTime() - 1);
    var b = getCookie(a);
    document.cookie = a + "=" + a + ";path=/; domain=" + window.location.host + "; expires=" + e.toGMTString()
}
function movedown(id, imgtop) {
    var img = $('#box' + id + ' img');
    img.css('position', 'absolute');
    if (img.width() < 200) {
        var t = img.height() - 500;
    } else {
        var t = imgtop - 500;
    }
    img.animate({
        top: -t
    },
    parseInt(String(t) + 0));
}
function moveup(id) {
    var img = $('#box' + id + ' img');
    img.css('position', 'absolute');
    img.animate({
        top: 0
    },
    500);
    if (img.css('top') > 0) {
        img.stop();
    }
}
function stopAni(id) {
    var img = $('#box' + id + ' img');
    if (img.is(":animated")) {
        img.stop();
    }
}
function enjoy(id) {
    $.get("/?m=Home&c=User&a=addenjoy_ajax", {
        'pid': id
    },
    function(t) {
        if (t.status == 1) {
            $('#box' + id).find('.favr').html('移除');
            $('#box' + id).find('.favr').attr('onclick', 'delenjoy(' + id + ')');
            $('#box' + id).find('.favr').attr('class', 'favred');
        } else {}
    });
}
function delenjoy(id) {
    $.get("/?m=Home&c=User&a=delenjoy_ajax", {
        'pid': id
    },
    function(t) {
        if (t.status == 1) {
            $('#box' + id).find('.favred').html('喜欢');
            $('#box' + id).find('.favred').attr('onclick', 'enjoy(' + id + ')');
            $('#box' + id).find('.favred').attr('class', 'favr');
        }
    })
}