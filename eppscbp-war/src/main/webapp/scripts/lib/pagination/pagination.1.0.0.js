define(['jquery', 'template'], function($, template) {
    'use strict';

    var Pagination = function(params) {
        this.init(params);
    };

    Pagination.prototype = {
        /*
         *  params:
         *      getConditions: 获取查询列表的查询条件
         *      callQueryEvent：里面是需要调用查询方法的注册事件
         *      url: ajax 请求链接的对象
         */
        init: function(params) {
            var self = this;

            this.postParams = new Object();
            this.postParams = {
                pageSize: '10',
                pageNo: '1',
                // 回调方法中设置，记录条数
                pageCount: '0'
            };

            this.tmpl = new Object();

            $.extend(true, this, params);

            if (typeof this.callQueryEvent == "function") {
                this.callQueryEvent(function() {
                    self.postParams.pageNo = 1;
                    self.query();
                });
            }

            this.query();
        },

        /*postParams: {
            pageSize: '10',
            pageNo: '1',
            // 回调方法中设置，记录条数
            pageCount: '0'
        },*/

        /*
         * 配置查询URL
         * url: "scripts/data/fund/performance_historical_net_worth.json",
         *
         * 返回json要包含几个字段：
         *      list (Array)：       列表的数据
         *      success (Boolean):   后台获取数据是否成功
         *      totalCount (Number): 列表总条数
         */ 
        url: "",

        // 列表容器
        $listWrap: null,

        /*
         * 模版 id
         *
         *  tmpl: {
         *      list: "historical_net_worth_list_tmpl", 列表模版
         *      wait: "historical_net_worth_wait_tmpl", 查询等待模版
         *      empty: "historical_net_worth_empty_tmpl" 查询为空模版
         *  },
         *
         */
        tmpl: {},

        /*
         * 渲染数据之前处理数据的工作
         *
         *
         *
         */
        beforeRenderList: function(data) {
            return data;
        },


        /*
         * 渲染完成列表后的动作
         *
         *
         *
         */
         afterRenderList: function($listWrap) {
            
         },

        /*
         * 初始化分页控件
         * 自定义绑定事件
         */
        bindEvent: function() {
            var self = this;
            var $listWrap = self.$listWrap;

            // 如果是第一页，则禁用上一页按钮
            if (self.postParams.pageNo == 1) {
                $listWrap.find(".m-paginate .previous").addClass("previous-disabled");
            }
            // 如果是最后一页，则禁用下一页按钮
            if (self.postParams.pageNo == self.postParams.pageCount) {
                $listWrap.find(".m-paginate .next").addClass("next-disabled");
            }

            $listWrap.find(".m-paginate .page-num").hover(function() {
                $(this).addClass("hover");
            }, function() {
                $(this).removeClass("hover");
            })

            $listWrap.find(".m-paginate .page-num[pageno=" + self.postParams.pageNo + "]").addClass("current").removeClass("page-num");

            $listWrap.find('.m-paginate .page-num').click(function() {
                var pageNum = $(this).html();
                var pageNo = parseInt(pageNum);
                if (self.postParams.pageNo != pageNo) {
                    self.postParams.pageNo = pageNo;
                    self.query();
                }
            });
            $listWrap.find('.m-paginate .previous').click(function() {
                if (self.postParams.pageNo > 1) {
                    self.postParams.pageNo--;
                    self.query();
                }
            });
            $listWrap.find('.m-paginate .next').click(function() {
                if (self.postParams.pageNo < self.postParams.pageCount) {
                    self.postParams.pageNo++;
                    self.query();
                }
            });

            // 跳转到多少页
            $listWrap.find('.m-paginate .jump').click(function() {
                self.jump();
            });
            // 按回车时跳转到多少页
            $listWrap.find('.m-paginate .jump-to-num').keydown(function(e) {
                // 页码规范，文本框内仅允许输入正整数数字，其他字符不得输入
                var kc = e.keyCode;
                if (kc == 13) {
                    self.jump();  
                } else {
                    // 删除：8 46。 刷新F5：116。 home:36 end:35 左上右下：37 38 39 40
                    var array = [8, 46, 116, 36, 35,37, 38, 39, 40, 48,49,50,51,52,53,54,55,56,57,96,97,98,99,100,101,102,103,104,105];
                    var flag = true;  
                    for(var i = 0;i < array.length && flag; i++) {
                        if(kc == array[i]){  
                            flag = false;  
                        }
                    }
                    if(flag){
                        e.keyCode = 0;  
                        e.returnValue=false;  
                        return false;  
                    }
                }
            });
        },

        jump: function() {
            var $listWrap = this.$listWrap;

            var pageNum = $listWrap.find('.m-paginate .jump-to-num').val();
            var jumpTo = 1;
            if (pageNum && !isNaN(pageNum)) {
                pageNum = parseInt(pageNum);
                if (pageNum > this.postParams.pageCount) {
                    jumpTo = this.postParams.pageCount;
                } else if (pageNum > 0) {
                    jumpTo = pageNum;
                }
            }
            if (jumpTo != this.postParams.pageNo) {
                this.postParams.pageNo = jumpTo;
                this.query();
            } else {
                $listWrap.find('.jump-to-num').val(jumpTo);
            }
        },

        pageNumHtmlInit: function() {
            var pageCount = this.postParams.pageCount;
            var pageNo = this.postParams.pageNo;
            var html = "";

            if (pageCount <= 6) {
                for (var i = 0; i < pageCount; i++) {
                    html += '<span class="page-num" pageno="' + (i + 1) + '">' + (i + 1) + '</span>';
                }
            } else {
                if (pageNo <= 4) {
                    for (var i = 0; i < 5; i++) {
                        html += '<span class="page-num" pageno="' + (i + 1) + '">' + (i + 1) + '</span>';
                    }
                    html += '<span class="ellipsis">...</span><span class="page-num" pageno="' + (i + 1) + '">' + pageCount + '</span>'
                } else if (pageNo < pageCount - 3) {
                    html = '<span class="page-num">1</span><span class="ellipsis">...</span>';
                    for (var i = pageNo - 1; i <= pageNo + 1; i++) {
                        html += '<span class="page-num" pageno="' + i + '">' + i + '</span>';
                    }
                    html += '<span class="ellipsis">...</span><span class="page-num">' + pageCount + '</span>';
                } else if (pageNo >= pageCount - 3){
                    html = '<span class="page-num">1</span><span class="ellipsis">...</span>';
                    for (var i = 0; i < 5; i++) {
                        var pn = pageCount - 4 + i;
                        html += '<span class="page-num" pageno="' + pn + '">' + pn + '</span>';
                    }
                }
            }

            return html;
        },

        // 后台请求数据的回调方法
        callback: function() {
            var self = this;
            var $listWrap = self.$listWrap;

            return function(data) {
                if (typeof data === 'string') {
                	data = $.parseJSON(data);
                }
                if (data.success) {
                    if (typeof data.list === 'string') {
                    	data.list = $.parseJSON(data.list);
                    }
                    var list = data.list;
                    if (data.totalCount > 0 && list.length > 0) {
                        var len = list.length;
                        var dataLen = data.totalCount;
                        var pageCount = Math.ceil(dataLen / self.postParams.pageSize);
                        self.postParams.pageCount = pageCount;

                        data.pageNumHtml = self.pageNumHtmlInit();

                        data = self.beforeRenderList(data);
                        var html = template(self.tmpl.list, data);
                        $listWrap.html(html);
                        $listWrap.find(".m-paginate .jump-to-num").val(self.postParams.pageNo);
                        self.bindEvent();
                        self.afterRenderList($listWrap);
                    } else {
                        data = self.beforeRenderList(data);
                        var html = template(self.tmpl.empty, data);
                        $listWrap.html(html);
                    }
                } else {
                    $listWrap.find(".wait .info").html("<i></i>查询失败，请稍后再试");
                }
            }
        },

        /*
         * 调用后台的ajax请求
         *  params 请求参数对象
         *  
         *  请求类型 apply redeem
         */
        getData: function(params) {
            params = params || {};
            params.pageIndex = params.pageNo || this.pageNo;
            params.pageSize = params.pageSize || this.pageSize;

            $.post(this.url, params, this.callback.apply(this));
        },

        // 获得查询条件
        getConditions: $.noop,

        query: function() {
            var params = $.extend({}, this.postParams, this.getConditions());

            var html = template(this.tmpl.wait);
            this.$listWrap.html(html);

            this.getData(params);
        }
    };

    return Pagination;
});