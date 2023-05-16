/*
Author: 王平 11060522
Website: https://github.com/wang-ping
*/
(function(factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        define(['jquery', 'template'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery', 'template'));
    } else {
        factory(jQuery, template);
    }
}(function($, template) {

    /*
        params:
            beforeRender (Function): 渲染数据之前处理数据的工作
            afterRender (Function): 渲染表格之后的操作
            getSearchConditions (Function): 获取查询列表的查询条件
            registerCustomSearch (Function)： 注册查询的事件
            url (String): ajax 请求链接的对象
     */
    var Pagination = function(params) {

        /*
            postParams: {
                pageSize: 10,
                pageNo: 1,

                回调方法中设置，总页数
                pageCount: '0'
            },
        */

        /*
            配置查询URL
            url: 'scripts/data/fund/performance_historical_net_worth.json',

            返回json要包含几个字段：
                 list (Array)：       列表的数据
                 success (Boolean):   后台获取数据是否成功
                 totalCount (Number): 列表总条数
         */

        /*
            列表容器句柄
            $listWrap

            分页容器句柄
            $pagination: null,
        */

        /*
            相关模版 id

            tpls: {
                list: 'list-tpl', 列表模版
                wait: 'wait-tpl', 查询等待模版
                empty: 'empty-tpl' 查询为空模版
            },

         */

        this.postParams = { pageSize: 10, pageNo: 1 };

        $.extend(true, this, params);

        this.init();
    };

    Pagination.prototype = {
        constructor: Pagination,

        /*
           获得查询条件
           每次查询时都会自动调用这个方法，并将该方法返回的对象作为请求参数传递给后台
        */
        getSearchConditions: $.noop,

        init: function() {
            this.initRegisterCustomSearch();

            this.query();
        },

        // 自定义搜索
        initRegisterCustomSearch: function() {
            var self = this;

            if (typeof this.registerCustomSearch === 'function') {
                this.registerCustomSearch(function() {
                    self.postParams.pageNo = 1;
                    self.query();
                });
            }
        },

        query: function() {
            var self = this,
                params = $.extend({}, this.postParams, this.getSearchConditions()),
                waitingHtml = template(this.tpls.wait);

            this.$listWrap.html(waitingHtml);

            $.get(this.url, params, function(data) {
                if (self.canBeRendered(data)) {
                    self.setPageCount(data);

                    data = self.beforeRender(data);
                    self.render(data);
                    self.afterRender(data);
                }
            }, 'json');
        },

        canBeRendered: function(data) {
            if (data.success) {
                var list = data.list;
                if (data.totalCount > 0 && list.length > 0) {
                    return true;
                } else {
                    this.showSearchEmptyMsg(data);
                }
            } else {
                this.showSearchErrorMsg(data);
            }

            return false;
        },
        showSearchErrorMsg: function(data) {
            this.$listWrap.find('.wait .info').html('<i></i>查询失败，请稍后再试');
        },

        showSearchEmptyMsg: function(data) {
            this.$listWrap.html(template(this.tpls.empty, data));
        },
        setPageCount: function(data) {
            this.postParams.pageCount = Math.ceil(data.totalCount / this.postParams.pageSize);
        },

        beforeRender: function(data) {
            return data;
        },

        afterRender: $.noop,

        render: function(data) {
            var $listWrap = this.$listWrap,
                postParams = this.postParams,
                pageNo = postParams.pageNo;

            data.paginationHtml = this.getPaginationHtml();
            var html = template(this.tpls.list, data);
            $listWrap.html(html);

            var $pagination = this.$pagination = $listWrap.find('.jr-pagination');

            $pagination.find('.jump-to-num').val(pageNo);
            $pagination.find('.page-num[pageno=' + pageNo + ']').addClass('current').removeClass('page-num');

            this.bindEvent();
            this.disableEvent();
        },

        /*
            获得分页按钮展示的HTML，分 2 大类 4 种情况
            1. 总页数小于等于 6 页时，展示全部分页按钮
            2. 总页数大于 6 页时
                2.1 当前页码属于前 4 页时，展示：前五页按钮 + ... + 最后一页按钮
                2.2 当前页码属于最后 4 页时，展示：第一页按钮 + ... + 最后 5 页按钮
                2.3 其他情况，展示：第一页按钮 + ... + 当前页的前一页按钮 + 当前页 + 当前页的后一页按钮 + ... + 最后一页按钮
        */
        getPaginationHtml: function() {
            var pageCount = this.postParams.pageCount;
            var currentPageNo = this.postParams.pageNo;
            var html = '';

            // 展示全部分页按钮的数量
            var SHOW_ALL_PAGING_BUTTON_AMOUNT = 6,
                showAllPaginBtn = pageCount <= SHOW_ALL_PAGING_BUTTON_AMOUNT,
                // 靠边的显示的分页数字
                KEEP_TO_THE_SIDE_NUMBER = 4,
                pageNo;

            if (showAllPaginBtn) {
                for (pageNo = 1; pageNo <= pageCount; pageNo++) {
                    html += '<span class="page-num" pageno="' + pageNo + '">' + pageNo + '</span>';
                }
            } else {
                // 当前页属于前 4 页，显示前 5 页的按钮
                if (currentPageNo <= KEEP_TO_THE_SIDE_NUMBER) {
                    for (pageNo = 1; pageNo <= KEEP_TO_THE_SIDE_NUMBER + 1; pageNo++) {
                        html += '<span class="page-num" pageno="' + pageNo + '">' + pageNo + '</span>';
                    }
                    html += '<span class="ellipsis">...</span><span class="page-num" pageno="' + pageCount + '">' + pageCount + '</span>';

                // 当前页属于后 4 页，显示后 5 页的按钮
                } else if (currentPageNo > pageCount - KEEP_TO_THE_SIDE_NUMBER) {
                    html = '<span class="page-num" pageno="1">1</span><span class="ellipsis">...</span>';

                    for (pageNo = pageCount - KEEP_TO_THE_SIDE_NUMBER; pageNo <= pageCount; pageNo++) {
                        html += '<span class="page-num" pageno="' + pageNo + '">' + pageNo + '</span>';
                    }

                // 当前页不是前 4 页，也不是后 4 页。显示的按钮包括当前页的前一页和后一页
                } else {
                    html = '<span class="page-num">1</span><span class="ellipsis">...</span>';
                    for (pageNo = currentPageNo - 1; pageNo <= currentPageNo + 1; pageNo++) {
                        html += '<span class="page-num" pageno="' + pageNo + '">' + pageNo + '</span>';
                    }
                    html += '<span class="ellipsis">...</span><span class="page-num">' + pageCount + '</span>';
                }
            }

            return html;
        },

        /*
         * 初始化分页控件
         * 自定义绑定事件
         */
        bindEvent: function() {
            var self = this,
                $pagination = this.$pagination,
                postParams = this.postParams;

            var bind = {
                init: function() {
                    this.initPageNum();
                    this.initPreviousBtn();
                    this.initNextBtn();
                    this.initJumpBtn();
                    this.initJumpToNumInput();
                },

                initPageNum: function() {
                    $pagination.find('.page-num').hover(function() {
                        $(this).addClass('hover');
                    }, function() {
                        $(this).removeClass('hover');
                    }).click(function() {
                        var pageNum = $(this).html();
                        var pageNo = parseInt(pageNum);
                        if (postParams.pageNo != pageNo) {
                            postParams.pageNo = pageNo;
                            self.query();
                        }
                    });
                },

                initPreviousBtn: function() {
                    $pagination.find('.previous').click(function() {
                        if (postParams.pageNo > 1) {
                            postParams.pageNo--;
                            self.query();
                        }
                    });
                },

                initNextBtn: function() {
                    $pagination.find('.next').click(function() {
                        if (postParams.pageNo < postParams.pageCount) {
                            postParams.pageNo++;
                            self.query();
                        }
                    });
                },

                // 跳转按钮
                initJumpBtn: function() {
                    $pagination.find('.jump').click(function() {
                        self.jump();
                    });
                },

                initJumpToNumInput: function() {
                    function stopKeydown(keyCode) {

                        /*
                           可以输入的键盘编码

                           删除：8 46
                           刷新F5：116
                           end：35
                           home：36
                           左上右下：37 38 39 40
                           标准键盘的 0~9：48~57
                           右手边数字键盘的 0~9：96~105
                        */
                        var acceptedKeyCodeArray = [8, 46,
                                116,
                                35,
                                36,
                                37, 38, 39, 40,
                                48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
                                96, 97, 98, 99, 100, 101, 102, 103, 104, 105],
                            // 阻止输入当前字符
                            stopKeydown = true;

                        for (var i = 0; i < acceptedKeyCodeArray.length && stopKeydown; i++) {
                            if (keyCode === acceptedKeyCodeArray[i]) {
                                stopKeydown = false;
                            }
                        }

                        return stopKeydown;
                    }

                    // 跳转到第几页的输入框触发的事件
                    $pagination.find('.jump-to-num').keydown(function(event) {
                        var keyCode = event.keyCode,
                            // 回车键编码
                            ENTER_KEY_CODE = 13;

                        if (keyCode === ENTER_KEY_CODE) {
                            self.jump();
                        } else {
                            // 页码文本框内仅允许输入正整数数字，其他字符不得输入
                            if (stopKeydown(keyCode)) {
                                event.keyCode = 0;
                                event.returnValue = false;
                                return false;
                            }
                        }
                    });
                }
            };

            bind.init();
        },

        disableEvent: function() {
            var $pagination = this.$pagination,
                postParams = this.postParams;

            // 如果是第一页，则禁用上一页按钮
            if (postParams.pageNo == 1) {
                $pagination.find('.previous').addClass('previous-disabled');
            }
            // 如果是最后一页，则禁用下一页按钮
            if (postParams.pageNo == postParams.pageCount) {
                $pagination.find('.next').addClass('next-disabled');
            }
        },

        jump: function() {
            var $pagination = this.$pagination,
                jumpTo = this.getTheNumberThatJumpTo();

            if (jumpTo != this.postParams.pageNo) {
                this.postParams.pageNo = jumpTo;
                this.query();
            } else {
                $pagination.find('.jump-to-num').val(jumpTo);
            }
        },

        getTheNumberThatJumpTo: function() {
            var $pagination = this.$pagination,
                pageNum = $pagination.find('.jump-to-num').val(),
                jumpTo = 1;

            if (pageNum && !isNaN(pageNum)) {
                pageNum = parseInt(pageNum);
                if (pageNum > this.postParams.pageCount) {
                    jumpTo = this.postParams.pageCount;
                } else if (pageNum > 0) {
                    jumpTo = pageNum;
                }
            }

            return jumpTo;
        }
    };

    $.fn.extend({
        pagination: function(params) {
            params = params || {};
            params.$listWrap = this;
            new Pagination(params);
        }
    });

    return Pagination;
}));
