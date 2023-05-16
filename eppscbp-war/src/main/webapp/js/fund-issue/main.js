require.config({
    baseUrl: 'js',
    paths: {
        'jquery': 'lib/jquery',
        'template': 'lib/template',
        'dialog': 'lib/dialog',
        'select': 'lib/select',

        'common': 'common/common'
    },
    shim: {
        dialog: ['jquery']
    }
});

require([
    'jquery', 
    'template',
    'common',
    'dialog',
    'select'
], function(
    $, 
    template,
    Common
) {

    var Main = {
        init: function() {

            this.bindEvent();
        },
        bindEvent: function() {
            var _this = this;
            // 添加
            $('.J_addBtn').on('click', function() {
                _this.addAccountDialog();
            });
            // 删除
            $('.J_delBtn').on('click', function() {
                Common.confirm('您确定要删除选中的记录吗？', function($dialog, callback) {

                });
            });
            // 导入
            $('.J_importBtn').on('click', function() {
                Common.import('', function($dialog, callback) {

                });
            });


            $('.J_fundTable').on('click', 'tbody .platform-checkbox', function() {
                if ($(this).hasClass('checked')) {
                    $(this).removeClass('checked');
                } else {
                    $(this).addClass('checked');
                }

                var $checkedList = $('.J_fundTable tbody').find('.platform-checkbox.checked');
                var $list = $('.J_fundTable tbody').find('tr');

                if ($checkedList.length == 0) {
                    $('.J_submitBtn').addClass('disabled');
                } else {
                    $('.J_submitBtn').removeClass('disabled');
                }

                var $checkAll = $('.J_fundTable').find('.J_checkAll');
                
                if ($checkedList.length == $list.length) {
                    $checkAll.addClass('checked');
                } else {
                    $checkAll.removeClass('checked');
                }
            });
            $('.J_fundTable').find('.J_checkAll').on('click', function() {
                var $list = $('.J_fundTable tbody').find('tr');

                if ($(this).hasClass('checked')) {
                    $(this).removeClass('checked');
                    $list.find('.platform-checkbox').removeClass('checked');
                    $('.J_submitBtn').addClass('disabled');
                } else {
                    $(this).addClass('checked');
                    $list.find('.platform-checkbox').addClass('checked');
                    $('.J_submitBtn').removeClass('disabled');
                }
            });


            // 批付
            $('.J_submitBtn').on('click', function() {
                if ($(this).hasClass('checked')) {
                    return;
                }
                _this.infConfirmDialog();
            });
        },
        addAccountDialog: function() {
            var _this = this;

            var html = template('add-account-dialog-tpl', {

            });

            var d = $.dialog({
                title: '添加收款账号',
                content: html,
                width: 710,
                onShow: function($dialog, callback) {
                    $dialog.find('.jr-select').initSelect();

                    Common.scrollTable($dialog.find('.J_table'));

                    $dialog.find('.J_submit').on('click', function() {
                        if ($(this).hasClass('disabled')) {
                            return;
                        }


                        callback.close();
                    });

                    $dialog.find('.J_cancel').on('click', function() {
                        callback.close();
                    });

                    $dialog.find('.J_table .platform-scroll-table-body').on('click', '.platform-checkbox', function() {
                        if ($(this).hasClass('checked')) {
                            $(this).removeClass('checked');
                        } else {
                            $(this).addClass('checked');
                        }

                        var $checkedList = $dialog.find('.J_table .platform-scroll-table-body').find('.platform-checkbox.checked');
                        var $list = $dialog.find('.J_table .platform-scroll-table-body').find('tr');

                        if ($checkedList.length > 0) {
                            $dialog.find('.J_submit').removeClass('disabled');
                        } else {
                            $dialog.find('.J_submit').addClass('disabled');
                        }

                        var $checkAll = $dialog.find('.J_table .platform-scroll-table-head').find('.platform-checkbox');
                        
                        if ($checkedList.length == $list.length) {
                            $checkAll.addClass('checked');
                        } else {
                            $checkAll.removeClass('checked');
                        }
                    });
                    $dialog.find('.J_table .platform-scroll-table-head').on('click', '.platform-checkbox', function() {
                        var $list = $dialog.find('.J_table .platform-scroll-table-body').find('tr');

                        if ($(this).hasClass('checked')) {
                            $(this).removeClass('checked');
                            $list.find('.platform-checkbox').removeClass('checked');
                            $dialog.find('.J_submit').addClass('disabled');
                        } else {
                            $(this).addClass('checked');
                            $list.find('.platform-checkbox').addClass('checked');
                            $dialog.find('.J_submit').removeClass('disabled');
                        }
                    });
                }
            });

            d.show();
        },

        infConfirmDialog: function() {
            var _this = this;

            var html = template('inf-confirm-dialog-tpl', {

            });

            var d = $.dialog({
                title: '批付信息确认',
                content: html,
                width: 710,
                onShow: function($dialog, callback) {
                    Common.scrollTable($dialog.find('.J_table'));

                    $dialog.find('.J_submit').on('click', function() {
                        Common.toast('success', '批付请求已提交成功');
                        callback.close();
                    });

                    $dialog.find('.J_cancel').on('click', function() {
                        callback.close();
                    });
                }
            });

            d.show();
        }
    };

    Main.init();
});
