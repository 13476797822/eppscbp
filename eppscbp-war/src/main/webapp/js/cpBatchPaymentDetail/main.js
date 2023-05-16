require.config({
    baseUrl: '/eppscbp/scripts/lib/',
    paths: {
        'jquery': 'jquery.1.7.2',
        'template': 'template.min',
        'dialog': 'dialog/myDialog',
        'select': 'select',
        'pagination': 'pagination/pagination.1.0.0',
        'ECode.calendar': 'calendar/ECode.calendar',
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
    'select',
    'pagination',
    'ECode.calendar'
], function ($) {

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
                    formObj.attr("action", sysconfig.ctx + "/cpBatchPaymentDetail/cpBatchPaymentDetailQuery/query.htm");
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
                    formObj.attr("action", sysconfig.ctx + "/cpBatchPaymentDetail/cpBatchPaymentDetailQuery/query.htm");
                    formObj.submit();
                }
            });
        }
    };

    Main.init();
});
