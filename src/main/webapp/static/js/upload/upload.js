function ltrim(str) {
    return str.replace(/[0]/g, "");
}
var successFindurl; 
(function($) {
    $(function() {
        var $wrap = $('#uploader'),
        $queue = $('<ul class="filelist"></ul>').appendTo($wrap.find('.queueList')),
        $statusBar = $wrap.find('.statusBar'),
        $info = $statusBar.find('.info'),
        $upload = $wrap.find('.uploadBtn'),
        $placeHolder = $wrap.find('.placeholder'),
        $progress = $statusBar.find('.progress').hide(),
        fileCount = 0,
        fileSize = 0,
        ratio = window.devicePixelRatio || 1,
        thumbnailWidth = 110 * ratio,
        thumbnailHeight = 110 * ratio,
        state = 'pedding',
        percentages = {},
        isSupportBase64 = (function() {
            var data = new Image();
            var support = true;
            data.onload = data.onerror = function() {
                if (this.width != 1 || this.height != 1) {
                    support = false;
                }
            }
            data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
            return support;
        })(),
        supportTransition = (function() {
            var s = document.createElement('p').style,
            r = 'transition' in s || 'WebkitTransition' in s || 'MozTransition' in s || 'msTransition' in s || 'OTransition' in s;
            s = null;
            return r;
        })(),
        uploader;
        if (!WebUploader.Uploader.support()) {
            alert('贴图库上传不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
            throw new Error('WebUploader does not support the browser you are using.');
        }
        uploader = WebUploader.create({
            pick: {
                id: '#filePicker',
                label: '点击选择图片'
            },
            formData: {
                Token: ''
            },
            compress: null,
            dnd: '#dndArea',
            paste: '#uploader',
            swf: '/static/js/upload/Uploader.swf',
            chunked: false,
            chunkSize: 512 * 1024,
            server: basePath+'local/upload',
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,png',
                mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif'
            },
            disableGlobalDnd: true,
            fileNumLimit: allownum,
            fileSizeLimit: 1024 * 1024 * 200,
            fileSingleSizeLimit: 10 * 1024 * 1024
        });
        uploader.on('uploadBeforeSend',
        function(block, data) {
/*            data.Token = token;
            console.log(token);*/
        });
        uploader.on('dndAccept',
        function(items) {
            var denied = false,
            len = items.length,
            i = 0,
            unAllowed = 'text/plain;application/javascript ';
            for (; i < len; i++) {
                if (~unAllowed.indexOf(items[i].type)) {
                    denied = true;
                    break;
                }
            }
            return ! denied;
        });
        uploader.addButton({
            id: '#filePicker2',
            label: '继续添加'
        });
        uploader.on('ready',
        function() {
            window.uploader = uploader;
        });
        function addFile(file) {
            var $li = $('<li id="' + file.id + '">' + '<p class="title">' + file.name + '</p>' + '<p class="imgWrap"></p>' + '<p class="progress"><span></span></p>' + '</li>'),
            $btns = $('<div class="file-panel">' + '<span class="cancel">删除</span>' + '<span class="rotateRight">向右旋转</span>' + '<span class="rotateLeft">向左旋转</span></div>').appendTo($li),
            $prgress = $li.find('p.progress span'),
            $wrap = $li.find('p.imgWrap'),
            $info = $('<p class="error"></p>'),
            showError = function(code) {
                switch (code) {
                case 'exceed_size':
                    text = '文件大小超出';
                    break;
                case 'interrupt':
                    text = '上传暂停';
                    break;
                default:
                    text = '上传失败，请重试';
                    break;
                }
                $info.text(text).appendTo($li);
            };
            if (file.getStatus() === 'invalid') {
                showError(file.statusText);
            } else {
                $wrap.text('预览中');
                uploader.makeThumb(file,
                function(error, src) {
                    var img;
                    if (error) {
                        $wrap.text('不能预览');
                        return;
                    }
                    if (isSupportBase64) {
                        img = $('<img src="' + src + '">');
                        $wrap.empty().append(img);
                    } else {
                        $.ajax('./preview.php', {
                            method: 'POST',
                            data: src,
                            dataType: 'json'
                        }).done(function(response) {
                            if (response.result) {
                                img = $('<img src="' + response.result + '">');
                                $wrap.empty().append(img);
                            } else {
                                $wrap.text("预览出错");
                            }
                        });
                    }
                },
                thumbnailWidth, thumbnailHeight);
                percentages[file.id] = [file.size, 0];
                file.rotation = 0;
            }
            file.on('statuschange',
            function(cur, prev) {
                if (prev === 'progress') {
                    $prgress.hide().width(0);
                } else if (prev === 'queued') {
                    $li.off('mouseenter mouseleave');
                    $btns.remove();
                }
                if (cur === 'error' || cur === 'invalid') {
                    showError(file.statusText);
                    percentages[file.id][1] = 1;
                } else if (cur === 'interrupt') {
                    showError('interrupt');
                } else if (cur === 'queued') {
                    percentages[file.id][1] = 0;
                } else if (cur === 'progress') {
                    $info.remove();
                    $prgress.css('display', 'block');
                } else if (cur === 'complete') {
                    $li.append('<span class="success"></span>');
                }
                $li.removeClass('state-' + prev).addClass('state-' + cur);
            });
            $li.on('mouseenter',
            function() {
                $btns.stop().animate({
                    height: 30
                });
            });
            $li.on('mouseleave',
            function() {
                $btns.stop().animate({
                    height: 0
                });
            });
            $btns.on('click', 'span',
            function() {
                var index = $(this).index(),
                deg;
                switch (index) {
                case 0:
                    uploader.removeFile(file);
                    return;
                case 1:
                    file.rotation += 90;
                    break;
                case 2:
                    file.rotation -= 90;
                    break;
                }
                if (supportTransition) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~ ((file.rotation / 90) % 4 + 4) % 4) + ')');
                }
            });
            $li.appendTo($queue);
        }
        function removeFile(file) {
            var $li = $('#' + file.id);
            delete percentages[file.id];
            updateTotalProgress();
            $li.off().find('.file-panel').off().end().remove();
        }
        function updateTotalProgress() {
            var loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;
            $.each(percentages,
            function(k, v) {
                total += v[0];
                loaded += v[0] * v[1];
            });
            percent = total ? loaded / total: 0;
            spans.eq(0).text(Math.round(percent * 100) + '%');
            spans.eq(1).css('width', Math.round(percent * 100) + '%');
            updateStatus();
        }
        function updateStatus() {
            var text = '',
            stats;
            if (state === 'ready') {
                text = '选中' + fileCount + '张图片，共' + WebUploader.formatSize(fileSize) + '。';
            } else if (state === 'confirm') {
                stats = uploader.getStats();
                if (stats.uploadFailNum) {
                    text = '已成功上传' + stats.successNum + '张照片至相册，' + stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#" style="color:#6194E9;text-decoration;">重新上传</a>失败图片或<a class="ignore" href="#" style="color:#6194E9;text-decoration;">忽略</a>'
                }
            } else {
                stats = uploader.getStats();
                text = '共' + fileCount + '张（' + WebUploader.formatSize(fileSize) + '），已上传' + stats.successNum + '张';
                if (stats.uploadFailNum) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }
            $info.html(text);
        }
        function setState(val) {
            var file, stats;
            if (val === state) {
                return;
            }
            $upload.removeClass('state-' + state);
            $upload.addClass('state-' + val);
            state = val;
            switch (state) {
            case 'pedding':
                $placeHolder.removeClass('element-invisible');
                $queue.hide();
                $statusBar.addClass('element-invisible');
                uploader.refresh();
                break;
            case 'ready':
                $placeHolder.addClass('element-invisible');
                $('#filePicker2').removeClass('element-invisible');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;
            case 'uploading':
                $('#filePicker2').addClass('element-invisible');
                $progress.show();
                $upload.text('暂停上传');
                break;
            case 'paused':
                $progress.show();
                $upload.text('继续上传');
                break;
            case 'confirm':
                $progress.hide();
                $upload.text('开始上传').addClass('disabled');
                stats = uploader.getStats();
                if (stats.successNum && !stats.uploadFailNum) {
                    setState('finish');
                    return;
                }
                if (stats.successNum && stats.uploadFailNum) {
                    $.dialog.showFuncLayer(430, "提示", stats.successNum + "张图片上传成功<br/><span class='cog'>失败" + stats.uploadFailNum + "张</span>",
                    function() {
                        $.dialog.hidden();
                        window.location.href = "/temp/" + encode64(successpid);
                    });
                    $('#dialoglayer #dlbtnconfirm').val('查看上传成功的图片').css({
                        'width': '145px',
                        'letter-spacing': '0'
                    });
                    $('#dialoglayer #dlbtncancel').val('重新上传').css({
                        'width': '80px',
                        'letter-spacing': '0'
                    }).attr('onclick', '$(".retry").click()');
                    $('#dialoglayer #dlcont').css({
                        'text-align': 'center'
                    });
                }
                if (!stats.successNum && stats.uploadFailNum) {
                    $.dialog.showMsgFuncLayer("提示", "全部上传失败（" + stats.uploadFailNum + "张）  (×_×)",
                    function() {
                        $.dialog.hidden();
                    });
                }
                break;
            case 'finish':
                stats = uploader.getStats();
                if (stats.successNum) {
                    $('.progress').hide();
                    $.dialog.showMsgFuncLayer("提示", stats.successNum + "张图片全部上传成功",
                    function() {
                        successpid = successpid.substring(0, successpid.length - 1);
                            if (successpid.split(',').length < 2) {
                                if (!successFindurl) {
                                    alert('图片异常，上传处理失败');
                                    return false;
                                }
                                //上传成功一张图片
                                window.location.href = basePath+"detail/"+successFindurl;
                            } else {
                            	//成功多条
                                window.location.href = basePath+"list/" + d_album;
                            }
                    	},
                    1);
                } else {
                    console.log(stats);
                    alert(stats);
                    state = 'done';
                    location.reload();
                }
                break;
            }
            updateStatus();
        }
        uploader.onUploadProgress = function(file, percentage) {
            var $li = $('#' + file.id),
            $percent = $li.find('.progress span');
            $percent.css('width', percentage * 100 + '%');
            percentages[file.id][1] = percentage;
            updateTotalProgress();
        };
        uploader.onFileQueued = function(file) {
            fileCount++;
            fileSize += file.size;
            if (fileCount === 1) {
                $placeHolder.addClass('element-invisible');
                $statusBar.show();
            }
            addFile(file);
            setState('ready');
            updateTotalProgress();
        };
        uploader.onFileDequeued = function(file) {
            fileCount--;
            fileSize -= file.size;
            if (!fileCount) {
                setState('pedding');
            }
            removeFile(file);
            updateTotalProgress();
        };
        uploader.on('uploadError',
        function(file, response) {
            response = JSON.parse(response);
            alert("上传失败：" + response.info + "(" + response.code + ")");
        });
        uploader.on('uploadSuccess',
        function(file, response) {
            if (response.code == '4791') {
                $.dialog.showMsgFuncLayer("提示", "全部上传失败,相册内图片数量已达上限",
                function() {
                    $.dialog.hidden();
                });
                return false;
            }
            if (successpid == '0') successpid = '';
            successpid += response.findurl + ',';
            successFindurl = response.data;
        });
        uploader.on('all',
        function(type) {
            var stats;
            switch (type) {
            case 'uploadFinished':
                setState('confirm');
                break;
            case 'startUpload':
                setState('uploading');
                break;
            case 'stopUpload':
                setState('paused');
                break;
            }
        });
        uploader.onError = function(code) {
            $.alert.show(code);
        };
        $upload.on('click',
        function() {
        	//必须选择相册才能上传
        	if($("#albums").val() == 0) {
        		$.dialog.showMsgLayer("提示", '请选择一个相册再上传！');
        		return false;
        	}
        	
            if ($(this).hasClass('disabled')) {
                return false;
            }
            if (state === 'ready') {
                uploader.upload();
            } else if (state === 'paused') {
                uploader.upload();
            } else if (state === 'uploading') {
                uploader.stop(true);
            }
        });
        $info.on('click', '.retry',
        function() {
            uploader.retry();
        });
        $info.on('click', '.ignore',
        function() {
            window.location.reload();
        });
        $upload.addClass('state-' + state);
        updateTotalProgress();
    });
})(jQuery);
var absDecodeChars = new Array( - 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
function absdecode(str) {
    var c1, c2, c3, c4;
    var i, len, out;
    len = str.length;
    i = 0;
    out = "";
    while (i < len) {
        do {
            c1 = absDecodeChars[str.charCodeAt(i++) & 0xff];
        } while ( i < len && c1 == - 1 );
        if (c1 == -1) break;
        do {
            c2 = absDecodeChars[str.charCodeAt(i++) & 0xff];
        } while ( i < len && c2 == - 1 );
        if (c2 == -1) break;
        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
        do {
            c3 = str.charCodeAt(i++) & 0xff;
            if (c3 == 61) return out;
            c3 = absDecodeChars[c3];
        } while ( i < len && c3 == - 1 );
        if (c3 == -1) break;
        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
        do {
            c4 = str.charCodeAt(i++) & 0xff;
            if (c4 == 61) return out;
            c4 = absDecodeChars[c4];
        } while ( i < len && c4 == - 1 );
        if (c4 == -1) break;
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
    }
    return out;
}
function utf16to8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}
function utf8to16(str) {
    var out, i, len, c;
    var char2, char3;
    out = "";
    len = str.length;
    i = 0;
    while (i < len) {
        c = str.charCodeAt(i++);
        switch (c >> 4) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
            out += str.charAt(i - 1);
            break;
        case 12:
        case 13:
            char2 = str.charCodeAt(i++);
            out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
            break;
        case 14:
            char2 = str.charCodeAt(i++);
            char3 = str.charCodeAt(i++);
            out += String.fromCharCode(((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
            break;
        }
    }
    return out;
}