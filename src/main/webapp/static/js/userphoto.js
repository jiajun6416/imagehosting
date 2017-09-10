var mzb;
$(function() {
    if (mzb) {
        var script = document.createElement("script");
        script.type = "text/javascript";
        document.body.appendChild(script);
    }
    loadover();
});
var loadover = function() {
    var ot = $(".user_nav").offset().top;
    var fh = 78 + ot + $(window).height() - ($(".header").outerHeight() + $(".container").outerHeight() + $(".footer").outerHeight());
    if (fh > 0) $("<div>").css({
        "float": "left",
        "height": fh
    }).appendTo(document.body);
    $(window).scrollTop(0);
    var the = $(".photo_a img");
    var prt = the.parent();
    $(prt).hover(function() {
        var imgrotateleft = $("#imgrotateleft");
        if (imgrotateleft.length) {
            imgrotateleft.show();
            $("#imgrotateright").show();
        } else {
            prt.css("position", "relative");
            $("<a>").css({
                "position": "absolute",
                "left": "0",
                "top": "0",
                "width": "16px",
                "height": "18px",
                "overflow": "hidden",
                "background": "#fff url(static/image/bg.png) no-repeat -321px -535px",
                "_background-image": "url(static/image/bg.gif)"
            }).attr({
                "href": "###",
                "id": "imgrotateleft",
                "title": "向左旋转"
            }).click(function(e) {
                the.rotateLeft();
                if (e.stopPropagation) e.stopPropagation();
                else e.cancelBubble = true;
                return false;
            }).appendTo(prt);
            $("<a>").css({
                "position": "absolute",
                "right": "0",
                "top": "0",
                "width": "16px",
                "height": "18px",
                "overflow": "hidden",
                "background": "#fff url(static/image/bg.png) no-repeat -425px -535px",
                "_background-image": "url(static/image/bg.gif)"
            }).attr({
                "href": "###",
                "id": "imgrotateright",
                "title": "向右旋转"
            }).click(function(e) {
                the.rotateRight();
                if (e.stopPropagation) e.stopPropagation();
                else e.cancelBubble = true;
                return false;
            }).appendTo(prt);
        }
    },
    function() {
        $("#imgrotateleft").hide();
        $("#imgrotateright").hide();
    });
};
var latlng, marktitle;
function showmap(lat, lng) {
    latlng = [lat, lng];
    marktitle = '拍摄地点';
    $.dialog.showIframeDialog(680, '拍摄地点', '<div id="placemarkmap" style="width:100%;height:380px;"></div>');
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "http://maps.google.com/maps/api/js?sensor=false&callback=mapready";
    document.body.appendChild(script);
}
function mapready() {
    var myOptions = {
        center: new google.maps.LatLng(latlng[0], latlng[1]),
        mapTypeControl: false,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        streetViewControl: false,
        zoom: 15
    };
    var map = new google.maps.Map(document.getElementById("placemarkmap"), myOptions);
    if (map) {
        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(latlng[0], latlng[1]),
            map: map,
            title: marktitle
        });
    }
}
$(".c_p_l_c_i_d .impeach").live('click',
function() {
    impeach('photo', $(this).parents('.c_p_l_c_i').attr('data-obj'));
});
function reply(dom) {
    if ($.cookie('puid')) {
        $(".c_p_l_c_i_d .comm").click();
        var commipt = $(dom).parents(".c_p_l_c_i_e").find(".c_p_l_c_i_e_f_a input");
        if (commipt.length) {
            commipt.focus();
            commipt.val("@" + $(dom).parents(".c_p_l_c_i_e_i").find(".c_p_l_c_i_e_i_b a").text().replace(/"/g, "&quot;") + "：");
        }
    } else $.dialog.showMsgFuncLayer("请登录", "对不起，仅登录用户可进行此操作！",
    function() {
        showSignIn();
    });
}
function showcomm(dom, obj, p) {
    $.getJSlive('/photocomment.do?r=' + Math.random(), {
        "obj": obj,
        "p": (p ? p: '')
    },
    function(result) {
        if ($.isArray(result.comment)) {
            var ch = '';
            $.each(result.comment,
            function(i, comment) {
                ch += '<div class="c_p_l_c_i_e_i">';
                ch += '<span class="c_p_l_c_i_e_i_a"><a href="' + comment.culink + '" target="_blank"><img src="' + comment.cuimg + '" alt="" /></a></span>';
                ch += '<span class="c_p_l_c_i_e_i_b"><a href="' + comment.culink + '" target="_blank">' + comment.cuname + '</a>' + comment.ctime + '</span>';
                if (comment.clock == 'True') ch += '<span class="c_p_l_c_i_e_i_c">内容审核中！</span>';
                else {
                    ch += '<span class="c_p_l_c_i_e_i_c">' + comment.cont + '<span class="c_p_l_c_i_e_i_c_f"><a href="###" onclick="impeach(\'comment\',\'' + comment.cobj + '\');return false;">举报</a>&nbsp;&nbsp;<a href="###" onclick="delcomment(this,\'' + comment.cobj + '\',\'' + obj + '\');return false;">删除</a></span></span>';
                }
                ch += '</div>';
            });
            ch += '<div class="c_p_l_c_i_e_p">' + result.page + '</div>';
            $(dom).parents(".c_p_l_c_i_e").html(ch);
        }
    });
}
var delcmWait = false;
function delcomment(dom, obj, forobj) {
    if ($.cookie('puid')) {
        $.dialog.showFuncLayer(430, "确认删除", '确定要删除这条评论吗？',
        function() {
            if (!delcmWait) {
                $.ajax({
                    beforeSend: function() {
                        delcmWait = true;
                    },
                    complete: function() {
                        delcmWait = false;
                    },
                    cache: false,
                    data: {
                        'obj': obj,
                        'forobj': forobj
                    },
                    error: function() {
                        $.dialog.showMsgLayer('系统异常', '对不起，系统出现异常，操作失败！');
                    },
                    success: function(result) {
                        if ("unsignin" == result) {
                            $.dialog.showMsgFuncLayer("登录失效", "登录已失效，请重新登录！",
                            function() {
                                showSignIn();
                            },
                            1);
                        } else if ("unown" == result) {
                            $.dialog.showMsgLayer('没有权限', "呃，只能删除自己的评论或上传图片的评论哦！");
                        } else if ("ok" == result) {
                            $.dialog.hidden();
                            $(dom).parents(".c_p_l_c_i_e_i").hide();
                        } else {
                            $.dialog.showMsgLayer('删除失败', "呃，删除评论失败了！");
                        }
                    },
                    type: "POST",
                    url: '/delcomment.do'
                });
            }
        });
    } else $.dialog.showMsgFuncLayer("请登录", "对不起，请登录后再进行此操作！",
    function() {
        showSignIn();
    });
}