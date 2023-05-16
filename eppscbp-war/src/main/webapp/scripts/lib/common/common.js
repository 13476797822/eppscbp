define([
    'jquery',
    'template',
    'dialog'
], function($, template) {
    var commonDialogRender = template.compile([
        '<div class="common-tip-dialog">',
            '<div class="common-tip-dialog-content">',
                '<i class="common-tip-icon {{type}}"></i>',
                '<p class="common-tip-msg">{{msg}}</p>',
            '</div>',
            '<div class="common-tip-dialog-ctrl">',
                '<a href="javascript:void(0);" class="platform-button active mr J_submit">确定</a>',
                '<a href="javascript:void(0);" class="platform-button J_cancel">取消</a>',
            '</div>',
        '</div>'
    ].join(''));


    var importDialogRender = template.compile([
        '<div class="common-import-dialog">',
        	'<form method="post" id="f1" name="f1">',
            '<div class="upload-wrap clearfix">',
                '<label>文件上传：</label>',
                '<input type="text" class="platform-input file-path" readonly>',
                '<input type="text" name="fileAddress" value="" class="input file-path hide">',
                '<div class="file-operation">',      	
            		'<a href="javascript:void(0);" class="platform-button up">选择文件',
            			'<form id="uploadForm" enctype="multipart/form-data">',
            			'<input id="fileInput" name="file" type="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">',
            			'</form>',
            		'</a>',
            		'<a class="download" href="{{tmplUrl}}">下载模版</a>',
            	'</div>',
            '</div>',
            '<div class="result-text">',
                '<div class="wait">',
                    '<img src="/eppscbp/style/images/empty-tip.jpg">',
                    '<p class="title">请上传模版文件</p>',
                '</div>',
                '<div class="wait_1 hide">',
                '<img src="/eppscbp/style/images/empty-tip.jpg">',
                '<p class="title">文件提交成功。</p>',
                '<span class="err-text"></span>',
                '</div>',
                '<div class="success hide">',
                    '<img src="/eppscbp/style/images/icon_success_24.png">',
                    '<p class="title">上传成功</p>',
                '</div>',
                '<div class="fail hide">',
                    '<img src="/eppscbp/style/images/icon_error_32.png">',
                    '<p class="title">上传失败，请修改后重新上传</p>',
                    '<span class="err-text"></span>',
                '</div>',
            '</div>',
            '<div class="dialog-ctrl">',
                '<a href="javascript:void(0);" class="platform-button active mr J_submit disabled" id="J_placeOrder">保存</a>',
                '<a href="javascript:void(0);" class="platform-button J_cancel">取消</a>',
            '</div>',
            '</form>',
        '</div>'
    ].join(''));



    var Common = {
        toast: function(type, msg) {
            var $toast = $('<div class="platform-toast"><i class="toast-icon ' + type + '"></i><p>' + msg + '</p></div>');

            $toast.hide().appendTo('body')
            .css({
                top: $(window).height() / 2 - 18,
                left: $(window).width() / 2 - $toast.outerWidth() / 2
            })
            .fadeIn();

            setTimeout(function() {
                $toast.fadeOut(function() {
                    $toast.remove();
                });
            }, 2000);
        },
        confirm: function(msg, submitCallback) {
            var html = commonDialogRender({
                type: 'confirm',
                msg: msg
            });

            var d = $.dialog({
                title: '提示',
                content: html,
                width: 518,
                onShow: function($dialog, callback) {
                    $dialog.find('.J_submit').on('click', function() {
                        if (typeof submitCallback === 'function') {
                            submitCallback($dialog, callback);
                        }

                        callback.close();
                    });
                    $dialog.find('.J_cancel').on('click', function() {
                        callback.close();
                    });
                }
            });

            d.show();
        },
        error: function(msg) {
        	var html = "<p class='add-fail'><i></i> 错误： " + msg + "</p>";
        	var d = $.dialog({
				title: "",
				content: html,
				width: 114,
				onShow: function ($dialog, callback) {
					setTimeout(function () {
						callback.close();
					}, 3000)
				},
				showClose: true,
				maskCss: { // 遮罩层背景
					opacity: 0
				}

			})
			d.show();
        },
        success: function() {
        	var html = "<p class='add-success'><i></i> 操作成功</p>";
        	var d = $.dialog({
				title: "",
				content: html,
				width: 114,
				onShow: function ($dialog, callback) {
					setTimeout(function () {
						callback.close();
					}, 1000)
				},
				showClose: true,
				maskCss: { // 遮罩层背景
					opacity: 0
				}

			});
			d.show();
        },
        // 通用导入弹窗
        import: function(tmplUrl, onShow) {
            var html = importDialogRender({
                tmplUrl: tmplUrl
            });

            var d = $.dialog({
                title: '批量新增',
                content: html,
                width: 518,
                onShow: function($dialog, callback) {
                    if (typeof onShow === 'function') {
                        onShow($dialog, callback);
                    }
                    $dialog.find('.J_cancel').on('click', function() {
                        callback.close();
                    });
                }
            });

            d.show();
        },
        scrollTable: function(table) {
            $(table).scroll(function(){
                var top = $(this).scrollTop();
                $(this).find('.platform-scroll-table-head, .platform-scroll-table-border').css('top', top);
            });
        }
    };

    return Common;
});