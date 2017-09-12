(function($) {
    var B = !!navigator.userAgent.match(/AppleWebKit.*Mobile.*/);
    $.dialog = {
        showMsgLayer: function(a, b, c, d, e) {
            this.show(430, a, b, null, 1, 0, c, 0, d, e)
        },
        showMsgFuncLayer: function(a, b, c, d, e, f) {
            this.show(430, a, b, c, 1, 0, 0, d, e, f)
        },
        showFuncLayer: function(a, b, c, d, e, f, g, h) {
            this.show(a, b, c, d, e, 0, 0, f, g, h)
        },
        showIframeDialog: function(a, b, c, d, e) {
            this.show(a, b, c, null, 1, 1, 0, 0, d, e)
        },
        show: function(c, d, f, g, h, i, j, k, l, m) {
            var n = $('#dialoglayer');
            if (!n.length) {
                n = $('<div id="dialoglayer"><div id="dlhead"><span id="dltitle"></span><span id="dlclose" ></span></div><div id="dlcont" style="text-align:center"></div><div id="dlbtn"><input type="button" value="确定" id="dlbtnconfirm" /><input type="button" value="取消" id="dlbtncancel" /></div></div>');
                n.css({
                    'position': 'absolute',
                    'z-index': '999',
                    'background-color': '#fff',
                    'font-size': '14px',
                    'color': '#666',
                    'padding-bottom': '10px',
                    'border': '5px solid #666',
                    '-webkit-border-radius': '4px',
                    '-moz-border-radius': '4px',
                    'border-radius': '4px'
                });
                n.find('#dlhead').css({
                    'width': '100%',
                    'height': '50px',
                    'background-color': '#f8f8f8',
                    'border-bottom': '4px solid #eee',
                    'font-weight': 'bold'
                });
                n.find('#dltitle').css({
                    'float': 'left',
                    'width': '80%',
                    'height': '50px',
                    'line-height': '50px',
                    'letter-spacing': '2px',
                    'text-indent': '1.5em'
                });
                n.find('#dlclose').css({
                    'float': 'right',
                    'width': '24px',
                    'height': '24px',
                    'margin': '13px 18px 0 0',
                    'cursor': 'pointer'
                });
                n.find('#dlclose').hover(function() {
                    $(this).css({
                        'background-position': '-24px 0'
                    })
                },
                function() {
                    $(this).css({
                        'background-position': '-24px 0'
                    })
                });
                n.find('#dlcont').css({
                    'line-height': '20px'
                });
                n.find('#dlbtn').css({
                    'width': '100%',
                    'height': '50px',
                    'text-align': 'center'
                });
                n.find('#dlbtn input').css({
                    'width': '80px',
                    'height': '30px',
                    'border': '0',
                    'line-height': '30px',
                    'letter-spacing': '5px',
                    'background-color': '#090',
                    'font-size': '14px',
                    'font-weight': 'bold',
                    'color': '#fff',
                    'cursor': 'pointer',
                    '-webkit-border-radius': '4px',
                    '-moz-border-radius': '4px',
                    'border-radius': '4px'
                });
                n.find('#dlbtn input').hover(function() {
                    $(this).css({
                        'background-color': '#0c0'
                    })
                },
                function() {
                    $(this).css({
                        'background-color': '#090'
                    })
                });
                n.find('#dlbtncancel').css({
                    'margin-left': '20px'
                });
                n.remove();
                n.appendTo(document.body);
                n.find('#dlclose').click(function() {
                    $.dialog.hidden()
                });
                n.find('#dlbtncancel').click(function() {
                    $.dialog.hidden()
                });
                if (!B) {
                    $(window).bind('resize scroll',
                    function() {
                        if (!l) {
                            var a = $('#dialoglayermask');
                            a.width($(document).width());
                            a.height($(document).height())
                        }
                        o()
                    })
                }
            }
            var o = function() {
                var a = $(window).height() - n.height();
                var b = $(window).width() - n.width();
                if (a > 0) {
                    a = $(window).scrollTop() + a / 2
                } else {
                    a = 0
                }
                if (b > 0) {
                    b = $(window).scrollLeft() + b / 2
                } else {
                    b = 0
                }
                n.css({
                    top: a,
                    left: b
                })
            };
            if (l) {
                n.css({
                    'border': '1px solid #ccc',
                    'backgroundColor': '#eee'
                })
            } else {
                var p = $('#dialoglayermask');
                if (!p.length) {
                    p = $('<div id="dialoglayermask"></div>');
                    p.css({
                        'position': 'absolute',
                        'z-index': '888',
                        'top': '0',
                        'left': '0',
                        'width': '100%',
                        'min-height': '100%',
                        'background-color': '#000',
                        'filter': 'alpha(opacity=30)',
                        '-moz-opacity': '0.30',
                        'opacity': '0.30'
                    });
                    p.appendTo(document.body)
                } else {
                    p.show()
                }
                p.width($(document).width());
                p.height($(document).height())
            }
            n.width(c);
            n.find('#dltitle').html(d);
            var q = n.find('#dlclose');
            if (k) {
                q.remove()
            } else {
                q.show()
            }
            var r = n.find('#dlcont');
            r.css({
                'margin': '20px 25px',
                'width': c - 50,
                'text-align': 'center'
            });
            r.html(f);
            if (i) {
                n.find('#dlbtn').remove()
            } else {
                n.find('#dlbtn').show();
                var s = n.find('#dlbtnconfirm');
                s.unbind('click');
                if (typeof g == 'function') {
                    s.click(g)
                } else {
                    s.click(function() {
                        $.dialog.hidden()
                    })
                }
                var t = n.find('#dlbtncancel');
                if (h) {
                    t.remove();
                    s.focus()
                } else {
                    t.show();
                    t.focus()
                }
            }
            if (j) {
                window.setTimeout('$.dialog.hidden();', (j * 1000))
            }
            var u = $('#dlhead');
            if (m) {
                u.unbind()
            } else {
                var v = 0,
                beginY = 0;
                var w = 0,
                posLeft = 0;
                var x = 0,
                y = 0;
                var z = function(e) {
                    x = posLeft + e.clientX - v;
                    y = w + e.clientY - beginY;
                    n.css({
                        top: y,
                        left: x
                    });
                    if (e.stopPropagation) e.stopPropagation();
                    else e.cancelBubble = true
                };
                var A = function(e) {
                    $(document).unbind("mousemove", z);
                    $(document).unbind("mouseup", A);
                    if (e.stopPropagation) e.stopPropagation();
                    else e.cancelBubble = true
                };
                u.unbind();
                u.mousedown(function(e) {
                    v = e.clientX;
                    beginY = e.clientY;
                    w = parseInt(n.position().top);
                    posLeft = parseInt(n.position().left);
                    $(document).unbind("mousemove", z);
                    $(document).unbind("mouseup", A);
                    $(document).bind("mousemove", z);
                    $(document).bind("mouseup", A);
                    if (e.stopPropagation) e.stopPropagation();
                    else e.cancelBubble = true;
                    if (e.preventDefault) e.preventDefault();
                    else e.returnValue = false
                })
            }
            o();
            n.show();
            if (r.height() < 30) {
                r.css('text-align', 'center')
            } else {
                r.css('text-align', 'left')
            }
        },
        hidden: function() {
            var a = $('#dialoglayermask');
            var b = $('#dialoglayer');
            if (a.length) {
                a.remove()
            }
            if (b.length) {
                b.remove()
            }
        }
    };
    $.alert = {
        show: function(c, d, e) {
            var f = $('#alertlayer');
            if (!f.length) {
                f = $('<div id="alertlayer"><div id="alertcont"></div><div id="alertbtn"><input type="button" value="确定" id="alertconfirm"/><input type="button" value="取消" id="alertcancel"/></div></div>');
                f.css({
                    'position': 'absolute',
                    'z-index': '1111',
                    'max-width': '460px',
                    'min-width': '300px',
                    'background-color': '#fff',
                    'font-size': '14px',
                    'color': '#333',
                    'border': '5px solid #666',
                    '-webkit-border-radius': '4px',
                    '-moz-border-radius': '4px',
                    'border-radius': '4px'
                });
                f.find("#alertcont").css({
                    'margin': '20px',
                    'line-height': '20px'
                });
                f.find("#alertbtn").css({
                    'width': '100%',
                    'height': '50px',
                    'text-align': 'center'
                });
                f.find("#alertbtn input").css({
                    'width': '80px',
                    'height': '30px',
                    'border': '0',
                    'line-height': '30px',
                    'letter-spacing': '5px',
                    'background-color': '#090',
                    'font-size': '14px',
                    'font-weight': 'bold',
                    'color': '#fff',
                    'cursor': 'pointer',
                    '-webkit-border-radius': '4px',
                    '-moz-border-radius': '4px',
                    'border-radius': '4px'
                });
                f.find("#alertbtn input").hover(function() {
                    $(this).css({
                        'background-color': '#0c0'
                    })
                },
                function() {
                    $(this).css({
                        'background-color': '#090'
                    })
                });
                f.find("#alertcancel").css({
                    'margin-left': '20px',
                    'display': 'none'
                });
                f.remove();
                f.appendTo(document.body);
                f.find("#alertcancel").click(function() {
                    $.alert.hidden()
                });
                if (!B) {
                    $(window).bind('resize scroll',
                    function() {
                        var a = $('#alertlayermask');
                        a.width($(document).width());
                        a.height($(document).height());
                        g()
                    })
                }
                f.width() > 460 ? f.width(380) : f.width() < 300 ? f.width(300) : null
            }
            var g = function() {
                var a = $(window).height() - f.height();
                var b = $(window).width() - f.width();
                if (a > 0) {
                    a = $(window).scrollTop() + a / 2
                } else {
                    a = 0
                }
                if (b > 0) {
                    b = $(window).scrollLeft() + b / 2
                } else {
                    b = 0
                }
                f.css({
                    top: a,
                    left: b
                })
            };
            var h = $('#alertlayermask');
            if (!h.length) {
                h = $('<div id="alertlayermask"></div>');
                h.css({
                    'position': 'absolute',
                    'z-index': '1100',
                    'top': '0',
                    'left': '0',
                    'width': '100%',
                    'min-height': '100%',
                    'background-color': '#fff',
                    'filter': 'alpha(opacity=0)',
                    '-moz-opacity': '0',
                    'opacity': '0'
                });
                h.appendTo(document.body)
            } else {
                h.show()
            }
            h.width($(document).width());
            h.height($(document).height());
            var i = f.find('#alertcont');
            i.html(c);
            var j = f.find('#alertconfirm');
            j.unbind('click');
            if (typeof d == 'function') {
                j.click(d)
            } else {
                j.click(function() {
                    $.alert.hidden()
                })
            }
            var k = f.find('#alertcancel');
            if (e) {
                k.show();
                k.focus()
            } else {
                k.remove();
                j.focus()
            }
            g();
            f.show();
            if (i.height() < 30) {
                i.css('text-align', 'center')
            } else {
                i.css('text-align', 'left')
            }
            f.find("#alertbtn").css({
                'width': f.width()
            })
        },
        hidden: function() {
            var a = $('#alertlayermask');
            if (a.length) {
                a.remove()
            }
            var b = $('#alertlayer');
            if (b.length) {
                b.remove()
            }
        },
        showb: function(c, d, e) {
            var f = $('#alertlayer');
            if (!f.length) {
                f = $('<div id="alertlayer"><div id="alertcont"></div><div id="alertbtn"><input type="button" value="下一步" id="alertconfirm"/><input type="button" value="取消" id="alertcancel"/></div></div>');
                f.css({
                    'position': 'absolute',
                    'z-index': '1111',
                    'max-width': '460px',
                    'min-width': '300px',
                    'background-color': '#fff',
                    'font-size': '14px',
                    'color': '#333',
                    'border': '5px solid #666',
                    '-webkit-border-radius': '4px',
                    '-moz-border-radius': '4px',
                    'border-radius': '4px'
                });
                f.find("#alertcont").css({
                    'margin': '20px',
                    'line-height': '20px'
                });
                f.find("#alertbtn").css({
                    'width': '100%',
                    'height': '50px',
                    'text-align': 'center'
                });
                f.find("#alertbtn input").css({
                    'width': '80px',
                    'height': '30px',
                    'border': '0',
                    'line-height': '30px',
                    'letter-spacing': '5px',
                    'background-color': '#090',
                    'font-size': '14px',
                    'font-weight': 'bold',
                    'color': '#fff',
                    'cursor': 'pointer',
                    '-webkit-border-radius': '4px',
                    '-moz-border-radius': '4px',
                    'border-radius': '4px'
                });
                f.find("#alertbtn input").hover(function() {
                    $(this).css({
                        'background-color': '#0c0'
                    })
                },
                function() {
                    $(this).css({
                        'background-color': '#090'
                    })
                });
                f.find("#alertcancel").css({
                    'margin-left': '20px',
                    'display': 'none',
                    'background-color': '#999'
                });
                f.remove();
                f.appendTo(document.body);
                f.find("#alertcancel").click(function() {
                    $.alert.hidden()
                });
                if (!B) {
                    $(window).bind('resize scroll',
                    function() {
                        var a = $('#alertlayermask');
                        a.width($(document).width());
                        a.height($(document).height());
                        g()
                    })
                }
                f.width() > 460 ? f.width(380) : f.width() < 300 ? f.width(300) : null
            }
            var g = function() {
                var a = $(window).height() - f.height();
                var b = $(window).width() - f.width();
                if (a > 0) {
                    a = $(window).scrollTop() + a / 2
                } else {
                    a = 0
                }
                if (b > 0) {
                    b = $(window).scrollLeft() + b / 2
                } else {
                    b = 0
                }
                f.css({
                    top: a,
                    left: b
                })
            };
            var h = $('#alertlayermask');
            if (!h.length) {
                h = $('<div id="alertlayermask"></div>');
                h.css({
                    'position': 'absolute',
                    'z-index': '1100',
                    'top': '0',
                    'left': '0',
                    'width': '100%',
                    'min-height': '100%',
                    'background-color': '#fff',
                    'filter': 'alpha(opacity=0)',
                    '-moz-opacity': '0',
                    'opacity': '0'
                });
                h.appendTo(document.body)
            } else {
                h.show()
            }
            h.width($(document).width());
            h.height($(document).height());
            var i = f.find('#alertcont');
            i.html(c);
            var j = f.find('#alertconfirm');
            j.unbind('click');
            if (typeof d == 'function') {
                j.click(d)
            } else {
                j.click(function() {
                    $.alert.hidden()
                })
            }
            var k = f.find('#alertcancel');
            if (e) {
                k.show();
                k.focus()
            } else {
                k.remove();
                j.focus()
            }
            g();
            f.show();
            if (i.height() < 30) {
                i.css('text-align', 'center')
            } else {
                i.css('text-align', 'left')
            }
            f.find("#alertbtn").css({
                'width': f.width()
            })
        }
    };
    $.prompt = {
        show: function(b) {
            var c = $('#promptlayer');
            if (!c.length) {
                c = $('<div id="promptlayer"></div>');
                c.css({
                    'position': 'absolute',
                    'z-index': '11111',
                    'float': 'left',
                    'padding': '6px 18px',
                    'background-color': '#ffff99',
                    'font-size': '12px',
                    'font-weight': 'bold',
                    'color': '#000'
                });
                c.remove();
                c.appendTo(document.body);
                $(window).bind('resize scroll',
                function() {
                    d()
                })
            }
            c.html(b);
            var d = function() {
                var a = $(window).width() - c.width();
                if (a > 0) {
                    a = $(window).scrollLeft() + a / 2
                } else {
                    a = 0
                }
                c.css({
                    top: $(window).scrollTop(),
                    left: a
                })
            };
            d();
            c.show()
        },
        hidden: function() {
            var a = $('#promptlayer');
            if (a.length) {
                a.remove()
            }
        }
    };
    $.tips = {
        show: function(a, b, c, d, e) {
            if (a && $(a).length) {
                var f = $(a).find('.dltipsbox');
                if (!f.length) {
                    $(a).css({
                        'position': 'relative',
                        'z-index': '665'
                    });
                    f = $('<div class="dltipsbox"><div class="dltipsboxjt"></div><div class="dltipsboxclose">x</div><div class="dltipsboxcont"></div></div>');
                    f.css({
                        'position': 'absolute',
                        'z-index': '1111'
                    });
                    f.find(".dltipsboxjt").css({
                        'position': 'absolute',
                        'width': '16px',
                        'height': '8px',
                        'background': 'url(/js/dialog/tipsbg.gif) no-repeat',
                        'overflow': 'hidden'
                    });
                    f.find(".dltipsboxclose").css({
                        'position': 'absolute',
                        'top': '0',
                        'right': '5px',
                        'line-height': '12px',
                        'font-size': '12px',
                        'color': '#999',
                        'font-family': '宋体,Arial,sans-serif',
                        'cursor': 'pointer'
                    });
                    f.find(".dltipsboxcont").css({
                        'padding': '10px',
                        'background-color': '#ffc',
                        'border': '1px solid #fc6'
                    });
                    f.remove();
                    f.appendTo(a)
                }
                if (e) f.find(".dltipsboxclose").remove();
                else f.find(".dltipsboxclose").click(function() {
                    $.tips.hidden(a)
                });
                f.find('.dltipsboxcont').html(c);
                var g = 0,
                left = 0;
                if (b) {
                    if (b.top) g = b.top;
                    if (b.left) left = b.left
                }
                if (d == "bl" || d == "lb" || d == "br" || d == "lr") {
                    f.find('.dltipsboxjt').css({
                        'top': f.height() - 1,
                        'background-position': '0 -8px'
                    });
                    if (d == "bl" || d == "lb") f.find('.dltipsboxjt').css('left', '10px');
                    else f.find('.dltipsboxjt').css('right', '10px');
                    g = g - f.height() - 8
                } else {
                    f.find('.dltipsboxjt').css({
                        'top': '-7px'
                    });
                    if (d == "tr" || d == "rt") f.find('.dltipsboxjt').css('right', '10px');
                    else f.find('.dltipsboxjt').css('left', '10px');
                    g = g + 7
                }
                f.css({
                    top: g,
                    left: left
                });
                f.show()
            }
        },
        hidden: function(a) {
            var b;
            if (a) b = $(a).find('.dltipsbox');
            else b = $('.dltipsbox');
            if (b.length) b.remove()
        }
    }
})(jQuery);