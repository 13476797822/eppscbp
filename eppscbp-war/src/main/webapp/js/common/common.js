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
                '{{if type == "confirm"}}',
                '<a href="javascript:void(0);" class="platform-button active mr J_submit">确定</a>',
                '<a href="javascript:void(0);" class="platform-button J_cancel">取消</a>',
                '{{else}}',
                '<a href="javascript:void(0);" class="platform-button active J_submit">确定</a>',
                '{{/if}}',
            '</div>',
        '</div>'
    ].join(''));

    var importDialogRender = template.compile([
        '<div class="common-import-dialog">',
            '<div class="upload-wrap clearfix">',
                '<label>文件上传：</label>',
                '<input type="text" class="platform-input file-path" readonly>',
                '<a href="javascript:void(0);" class="platform-button up">选择文件<input type="file" id="fileInput"></a>',
                '<a class="download" href="{{tmplUrl}}">下载模版</a>',
            '</div>',
            '<div class="result-text">',
                // '<div class="wait">',
                //     '<i class="icon icon-wait"></i>',
                //     '<p class="title">请上传模版文件</p>',
                // '</div>',
                // '<div class="success">',
                //     '<i class="icon icon-success"></i>',
                //     '<p class="title">上传成功</p>',
                // '</div>',
                '<div class="fail hide">',
                    '<i class="icon"></i>',
                    '<p class="title">上传失败，请修改后重新上传</p>',
                    '<p class="error-txt">',
                        '第6笔，订单号29134384，失败原因：原因的文案。<br>',
                        '第9笔，订单号29134384，失败原因：原因的文案<br>',
                        '文案原因文案文案案。',
                    '</p>',
                '</div>',
            '</div>',
            '<div class="dialog-ctrl">',
                '<a href="javascript:void(0);" class="platform-button active mr J_submit disabled">保存</a>',
                '<a href="javascript:void(0);" class="platform-button J_cancel">取消</a>',
            '</div>',
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
            var html = commonDialogRender({
                type: 'error',
                msg: msg
            });

            var d = $.dialog({
                title: '提示',
                content: html,
                width: 518,
                onShow: function($dialog, callback) {
                    $dialog.find('.J_submit').on('click', function() {
                        callback.close();
                    });
                }
            });

            d.show();
        },
        success: function(msg) {
            var html = commonDialogRender({
                type: 'success',
                msg: msg
            });

            var d = $.dialog({
                title: '提示',
                content: html,
                width: 518,
                onShow: function($dialog, callback) {
                    $dialog.find('.J_submit').on('click', function() {
                        callback.close();
                    });
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
                title: '导入',
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