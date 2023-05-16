require.config({
    baseUrl: '/eppscbp/scripts/lib/',
    paths: {
        'jquery': 'jquery.1.7.2',
        'template': 'template.min',
        'dialog': 'dialog/crossDialog',
        'select': 'select',
        'pagination': 'pagination/pagination.1.0.0',
        'ECode.calendar': 'calendar/ECode.calendar',
        'common': 'common/common',
        'ajaxfileupload': 'upload/ajaxfileupload'
    },
    shim: {
        dialog: ['jquery'],
        ajaxfileupload: ['jquery']
    }
});

require([
    'jquery',
    'template',
    'common',
    'dialog',
    'select',
    'pagination',
    'ECode.calendar',
    'ajaxfileupload'
], function ($,
             template,
             Common) {

    var Main = {
        init: function () {
            this.initCalendar();
            this.bindEvent();
        },
        initCalendar: function () {
            //日历初始化
            ECode.calendar({
                inputBox: '#startDateInput',
                count: 1,
                flag: false,
                isWeek: false,
                isTime: false,
                range: {
                    mindate: '1981/12/31',
                    maxdate: new Date()
                },
                callback: function () {
                    var mindate = $('#startDateInput').val().replace(new RegExp(/-/gm), '/');
                    var maxdate = addDays(new Date(), 0).replace(new RegExp(/-/gm), '/');

                    ECode.calendar({
                        inputBox: '#endDateInput',
                        count: 1,
                        flag: false,
                        isWeek: false,
                        isTime: false,
                        range: {
                            mindate: mindate,
                            maxdate: maxdate
                        }
                    });
                }
            });
            // 初始化结束日期
            ECode.calendar({
                inputBox: '#endDateInput',
                count: 1,
                flag: false,
                isWeek: false,
                isTime: false,
                range: {
                    mindate: '1981/12/31',
                    maxdate: new Date()
                }, //限制可选日期
                callback: function () {
                    // 开始日期在结束日期的前90天
                    var maxdate = $('#endDateInput').val().replace(new RegExp(/-/gm), '/');
                    var mindate = addDays(new Date(maxdate), -90).replace(new RegExp(/-/gm), '/');
                    ECode.calendar({
                        inputBox: '#startDateInput',
                        count: 1,
                        flag: false,
                        isWeek: false,
                        isTime: false,
                        range: {
                            mindate: mindate,
                            maxdate: maxdate
                        }
                    });
                }
            });

            function addDays(date, days) {
                var nd = new Date(date);
                nd = nd.valueOf();
                nd = nd + days * 24 * 60 * 60 * 1000;
                nd = new Date(nd);

                var y = nd.getFullYear();
                var m = nd.getMonth() + 1;
                var d = nd.getDate();
                var h = nd.getHours();
                var minu = nd.getMinutes();
                if (m <= 9) m = '0' + m;
                if (d <= 9) d = '0' + d;
                var cdate = y + '-' + m + '-' + d;

                return cdate;
            }
        },
        bindEvent: function () {
            var _this = this;
            // 重置
            $('.J_resetBtn').on('click', function () {
                $('.J_searchForm').find('input').each(function () {
                    if (typeof $(this).attr('key') !== 'undefined') {
                        $(this).attr('key', '');
                    }

                    $(this).val('');
                });
            });

            //分页查询,上一页
            $(".previous").on("click", function (event) {
                event.preventDefault();

                var currentPage = $('#currentPage').val();
                var nextPage = parseInt(currentPage) - 1;

                //首页无法点击上一页
                if (currentPage == 1) {
                    return;
                }

                if ($("#pageClass").length <= 0) {
                    alert("请先查询数据!");
                } else {
                    document.getElementById("currentPage").value = nextPage;
                    var formObj = $("form[name='f0']");
                    formObj.attr("action", sysconfig.ctx + "/cpWithdrawDetail/cpWithdrawDetailQuery/query.htm");
                    formObj.submit();
                }
            });

            //分页查询,下一页
            $(".next").on("click", function (event) {
                event.preventDefault();

                var currentPage = $('#currentPage').val();
                var nextPage = parseInt(currentPage) + 1;
                var pages = parseInt($('#pageSize').val())

                //末页无法点击下一页
                if (currentPage == pages) {
                    return;
                }

                if ($("#pageClass").length <= 0) {
                    alert("请先查询数据!");
                } else {
                    document.getElementById("currentPage").value = nextPage;
                    var formObj = $("form[name='f0']");
                    formObj.attr("action", sysconfig.ctx + "/cpWithdrawDetail/cpWithdrawDetailQuery/query.htm");
                    formObj.submit();
                }
            });

            // 结汇收入点击
            $('.J_listContent').on('click', 'tr .J_amount', function () {
                _this.settleDetailDialog();
            });


            //点击上传按钮事件
            $(document).on("click", ".J_upload", function () {
                var html = $("#upload-tpl").html();
                var url = $("#uploadUrlHid").val();
                var win = $.dialog({
                    title: "真实性材料文件上传",
                    content: html,
                    width: 520,
                    blur: false,
                    onShow: function ($dialog, callback) {
                        //保存按钮
                        $dialog.find('.submit-btn').on('click', function () {
                            if ($("#J_placeOrder").hasClass("disabled")) {
                                return false;
                            }
                            var requestDto = $('#f4').serialize();
                            $.ajax({
                                type: "POST",
                                dataType: "JSON",
                                url: sysconfig.ctx + url,
                                data: requestDto,
                                success: function (data) {
                                    //若页面过期跳转至首页
                                    isIntercepted(data);

                                    if (data.success == "s") {
                                        $(".upload-files .result-text .success").show();
                                        $(".upload-files .result-text .fail").hide();
                                        $(".upload-files .result-text .wait").hide();
                                        callback.close();
                                        submitQuery();
                                    } else {
                                        $(".upload-files .result-text .fail .err-text").html(data.responseMsg);
                                        $(".upload-files .result-text .fail").show();
                                        $(".upload-files .result-text .success").hide();
                                        $(".upload-files .result-text .wait").hide();
                                    }
                                },
                                error: function () {
                                    $(".upload-files .result-text .fail .err-text").html("系统错误");
                                    $(".upload-files .result-text .fail").show();
                                    $(".upload-files .result-text .success").hide();
                                    $(".upload-files .result-text .wait").hide();
                                }
                            });
                        })

                        //取消按钮
                        $dialog.find('.cancel-btn').on('click', function () {
                            callback.close();
                        });

                    },
                    showClose: true
                })
                win.show();
                //业务单号赋值
                var id = $(window.event.target).parents("tr").children().first().text();
                var withdrawOrderNo = $('#orderID' + id).val();//提现单号
                $("#businessNoHid").val(withdrawOrderNo);
            });

            //文件上传校验
            $(document).on("change", ".upload-files input[type=file]", function () {
                var fileAddress = $(this).val();
                if (fileAddress == '' || fileAddress == undefined || fileAddress == null) {
                    return false;
                }
                $("#J_placeOrder").addClass("disabled");
                $.ajaxFileUpload({
                    url: sysconfig.ctx + "/cpWithdrawDetail/cpWithdrawDetailQuery/fileCheck.htm",
                    type: 'POST',
                    fileElementId: 'file-btn',
                    dataType: 'text',
                    success: function (data) {
                        //若页面过期跳转至首页
                        isIntercepted(data);

                        var obj = eval('(' + data + ')');
                        if (obj.success) {
                            $('.upload-files').find('.file-path').val(fileAddress);
                            $('.upload-files').find('.file-path[name="fileAddress"]').val(obj.fileAddress);
                            $("#uploadSuccess").show();
                            $("#uploadFail").hide();
                            $("#wait").hide();
                            if ($("#J_placeOrder").hasClass("disabled"))
                                $("#J_placeOrder").removeClass('disabled');
                        } else {
                            $('.upload-files').find('.file-path').val(fileAddress);
                            $("#uploadFail .err-text").html(obj.message);
                            $("#uploadFail").show();
                            $("#uploadSuccess").hide();
                            $("#wait").hide();
                        }
                    },
                    complete: function (xmlHttpRequet) {
                        $("input[name='file']").replaceWith('<input type="file" id="file-btn" name="file">');
                    },
                    error: function (data) {
                    }
                });
            });

        },
        // 结汇收入详情弹窗
        settleDetailDialog: function () {
            var id = $(window.event.target).parents("tr").children().first().text();
            var settleAmtId = 'settleAmt' + id;

            var settleAmt = document.getElementById(settleAmtId).innerHTML;//获取结汇收入
            var settleRate = $('#settleRate' + id).val();//获取结汇汇率
            var feeAmt = $('#feeAmt' + id).val();//获取手续费

            var html = template('settle-detail-dialog-tpl', {});

            var d = $.dialog({
                title: '结汇收入（人民币）',
                content: html,
                width: 518,
                onShow: function ($dialog, callback) {
                    document.getElementById('settleAmt').innerHTML = settleAmt;
                    document.getElementById('settleRate').innerHTML = settleRate;
                    document.getElementById('feeAmt').innerHTML = feeAmt;
                    $dialog.find('.J_close').on('click', function () {
                        callback.close();
                    });
                }
            });

            d.show();
        }
    };

    Main.init();
});
