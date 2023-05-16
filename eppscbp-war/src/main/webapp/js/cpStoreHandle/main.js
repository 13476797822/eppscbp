require.config({
    baseUrl: '/eppscbp/scripts/lib/',
    paths: {
        'jquery': 'jquery-1.11.1.min',
        'template': 'template.min',
        'dialog': 'dialog/crossDialog',
        'select': 'select',
        'pagination': 'pagination/pagination',
        'ajaxfileupload': 'upload/ajaxfileupload',

        'common': 'common/common'
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
    'pagination',
    'select',
    'ajaxfileupload'
], function(
    $, 
    template,
    Common
) {

    var Main = {
        init: function() {
            this.bindEvent();
            /*this.renderTable();*/
        },
        renderTable: function() {
            var params = {
                // 获取页面的查询条件
                getSearchConditions: function() {
                    return {
                        storeName: $("input[name='storeName']").val(), //店铺名称
                        site: $("input[name='site']").val(), //站点
                    };
                },
                url: sysconfig.ctx + "/cpStoreHandle/cpStoreHandleQuery/query.htm",

                // 模版 id
                tpls: {
                    list: 'list-tpl',
                    wait: 'wait-tpl',
                    empty: 'empty-tpl'
                },

                // 渲染数据之前处理数据的工作
                beforeRender: function(data) {
                    // 处理后台返回数据
                    return data;
                },

                // 渲染表格之后的操作
                afterRender: function() {

                },

                // 注册自定义搜索
/*                registerCustomSearch: function(query) {
                    $('.J_searchBtn').click(function() {
                        query();
                    });
                }*/
            };
            
            $('.J_listContent').pagination(params);
        },
        bindEvent: function() {
            var _this = this;

/*            $('.J_searchBtn').one('click', function() {
                _this.renderTable();
            });*/

            // 添加店铺
            $('.J_addShopBtn').on('click', function() {
                _this.addShopDialog();
            });

            // 店铺切换
            $('.J_shopToggle').on('click', 'a', function(){
                if ($(this).hasClass('active')) {
                    return;
                }

                $(this).addClass('active').siblings().removeClass('active');
                submitQuery();
            });

            // 重置
            $('.J_resetBtn').on('click', function() {
                $('.J_searchForm').find('input').each(function() {
                    if (typeof $(this).attr('key') !== 'undefined') {
                        $(this).attr('key', '');
                    }

                    $(this).val('');
                });
            });

            // 店铺名
            $('.batch-detail-table').on('click', 'tbody tr .J_shopName', function() {
                _this.shopDetailDialog();
            });
            // 银行账号
            $('.batch-detail-table').on('click', 'tbody tr .J_account', function() {
                _this.accountDetailDialog();
            });

            // 提现上传文件校验
            $(document).on('change', '.upload-files input[type=file]', function(e) {
                var fileAddress = $(this).val();
                if(fileAddress ==''||fileAddress  == undefined || fileAddress == null){
                    return false;
                }
                var cur = $('.upload-files #curType').val();
                $("#J_placeOrder").addClass("disabled");
                $("#fileAddress").val('');
                $.ajaxFileUpload({
                    url: sysconfig.ctx+"/cpStoreHandle/cpStoreHandleQuery/fileSubmit.htm",
                    type : 'POST',
                    fileElementId : 'file-btn',
                    dataType: 'text',
                    data: {"cur" : cur},
                    success: function (data) {
                        //若页面过期跳转至首页
                        isIntercepted(data);

                        var obj = eval('(' + data+ ')');
                        if(obj.success){
                            $('.upload-files').find('.file-path').val(fileAddress);
                            $('.upload-files').find('.file-path[name="fileAddress"]').val(obj.fileAddress);
                            $("#uploadSuccess").show();
                            $("#uploadFail").hide();
                            $("#wait").hide();
                            $('#fileAddress').val(obj.fileAddress);
                            $('#detailAmount').val(obj.detailAmount);
                            if($("#J_placeOrder").hasClass("disabled"))
                                $("#J_placeOrder").removeClass('disabled');
                        } else {
                            $('.upload-files').find('.file-path').val(fileAddress);
                            $("#uploadFail .err-text").html(obj.message);
                            $("#uploadFail").show();
                            $("#uploadSuccess").hide();
                            $("#wait").hide();
                        }
                    },
                    complete: function(xmlHttpRequet){
                        $("input[name='file']").replaceWith('<input type="file" id="file-btn" name="file">');
                    },
                    error: function (data) {
                    }
                });
            });


            /*批量新增*/
            $(document).on("click",".batch-add-btn",function(){
                var html = $("#store_upload").html();
                var firstId = $(window.event.target).parents("tr").children().first().text();
                var cur = $('#cur' + firstId).html();

                var win = $.dialog({
                    title : "提现",
                    content : html,
                    width : 520,
                    onShow : function($dialog, callback){
                        $('.upload-files #curType').val(cur);
                        $('.upload-files #id').val(firstId);
                        //保存按钮
                        $("#J_placeOrder").click(function(){
                            var requestDto = $('#f3').serialize();
                            if($("#J_placeOrder").hasClass("disabled")){
                                return false;
                            }
                            $.ajax({
                                type: "POST",
                                dataType: "JSON",
                                url: sysconfig.ctx+"/cpStoreHandle/cpStoreHandleQuery/batchAdd.htm",
                                data: requestDto,
                                success: function (data) {
                                    //若页面过期跳转至首页
                                    isIntercepted(data);

                                    if(data.success == "s"){
                                        var win = $.dialog({
                                            title : "",
                                            content : "<p class='add-success'><i></i>添加成功</p>",
                                            width : 114,
                                            onShow : function($dialog, callback){
                                                setTimeout(function(){
                                                    callback.close();
                                                    submitQuery('none');
                                                },1000)
                                            },
                                            showClose : true,
                                            maskCss: { // 遮罩层背景
                                                opacity: 0
                                            }
                                        })
                                        win.show();
                                        callback.close();
                                    }else{
                                        $(".upload-files .result-text .fail .err-text").html(data.responseMsg);
                                        $(".upload-files .result-text .fail").show();
                                        $(".upload-files .result-text .success").hide();
                                        $(".upload-files .result-text .wait").hide();
                                    }
                                },
                                error : function() {
                                    $(".upload-files .result-text .fail .err-text").html("系统错误");
                                    $(".upload-files .result-text .fail").show();
                                    $(".upload-files .result-text .success").hide();
                                    $(".upload-files .result-text .wait").hide();
                                }
                            });
                        })
                        //取消按钮
                        $(".cancel-btn").click(function(){
                            callback.close();
                        })
                    },
                    showClose : true
                })
                win.show();
            });

            // 刷新审核进度
            $('.J_listContent').on('click', 'tbody tr .J_refresh', function() {
                Common.toast('success', '刷新成功');
            });
            // 修改
            $('.batch-detail-table').on('click', 'tbody tr .J_modify', function() {
                _this.addShopDialog({});
            });
            // 删除
/*            $('.batch-detail-table').on('click', 'tbody tr .J_delete', function() {
                Common.confirm('确定删除选中记录吗？', function() {

                });
            });*/
        },
        // 添加或修改店铺弹窗
        addShopDialog: function(shopInfo) {
            var _this = this;
            var firstId = $(window.event.target).parents("tr").children().first().text();
            var storeName = $('#storeName' + firstId).html();
            var site = $('#site' + firstId).html();
            var storeUrl = $('#storeUrl' + firstId).html();
            var sellerID = $('#sellerId' + firstId).html();
            var sellGoods = $('#sellGoods' + firstId).html();
            var runTime = $('#runTime' + firstId).html();
            var mountAmt = $('#mountAmt' + firstId).html();
            var storeId = $('#storeId' + firstId).html();
            var entityName = $('#entityName' + firstId).html();
            var storeNo = $('#storeNo' + firstId).html();
            var html = template('add-shop-dialog-tpl', {});
            

            var d = $.dialog({
                title: typeof shopInfo === 'undefined' ? '添加店铺' : '修改店铺',
                content: html,
                width: 518,
                onShow: function($dialog, callback) {
                    if (typeof shopInfo !== 'undefined') {
                        $('.add-shop-dialog-body #storeName').val(storeName);
                        $('.add-shop-dialog-body #sites').val(site);
                        $('.add-shop-dialog-body #storeUrl').val(storeUrl);
                        $('.add-shop-dialog-body #sellerId').val(sellerID);
                        $('.add-shop-dialog-body #storeNo').val(storeNo);
                        $('.add-shop-dialog-body #sellGoods').val(sellGoods);
                        $('.add-shop-dialog-body #runTime').val(runTime);
                        $('.add-shop-dialog-body #mountAmt').val(mountAmt);
                        $('.operate').val("U");
                        $('.add-shop-dialog-body .storeId').val(storeId);
                        $('.add-shop-dialog-body .storeNo').val(storeNo);
                        $('.add-shop-dialog-body .storeId').removeAttr("disabled");
                        $('.add-shop-dialog-body #entityName').val(entityName);
                    }
                    if ($('.J_shopToggle .active input').val() == '2') {
                        $('.add-shop-dialog-body #sellerId').hide();
                    }
                    $dialog.find('.jr-select').initSelect();

                    $dialog.find('.J_form').on('blur keyup', 'dl .platform-input', function() {
                        if ($.trim($(this).val()) == '') {
                            $(this).parents('.input-field').find('.error-tip').show();
                        } else {
                            $(this).parents('.input-field').find('.error-tip').hide();
                        }

                        callback.reset();
                    });


                    $dialog.find('.J_submit').on('click', function() {
                        var len = 0;
                        $dialog.find('.J_form .input-field').each(function(index, el) {
                            var value = $.trim($(this).find('input').val());
                            if (index == '0' && value.length > 100) {
                                $(this).find('.error-show').show();
                                len++;
                            }
                            if (index == '3' && value.length > 200) {
                                $(this).find('.error-show').show();
                                len++;
                            }
                            if (index == '4' && ($('.J_shopToggle .active input').val() == '1') && ((value.length >= 0  && value.length < 10) || value.length > 16)) {
                                $(this).find('.error-show').show();
                                len++;
                            }
                            if (index == '5' && value.length > 100) {
                                $(this).find('.error-show').show();
                                len++;
                            }
                            if (index == '7' && parseInt(value) > 2147483647) {
                                $(this).find('.error-show').show();
                                len++;
                            }
                            
                            if (value == ''&& index != '4') {
                                $(this).find('.error-tip').show();
                                len++;
                            }
                        });

                        callback.reset();

                        if (len > 0) {
                            return;
                        }

                        if ($('.J_shopToggle a').hasClass('active')) {
                            $('.store_add').val($('.J_shopToggle .active input').val());
                        }
                        var requestDto = $('#f2').serialize();
                        // 提交代码
                        $.ajax({
                            type: "POST",
                            dataType: "JSON",
                            url: sysconfig.ctx + "/cpStoreHandle/cpStoreHandleQuery/addOrUpdateStore.htm",
                            data: requestDto,
                            success: function (data) {
                                //若页面过期跳转至首页
                                isIntercepted(data);
                                if (data.success == 's') {
                                    var win = $.dialog({
                                        title : "",
                                        content : "<p class='add-success'><i></i>操作成功</p>",
                                        width : 114,
                                        onShow : function($dialog, callback){
                                            setTimeout(function(){
                                                callback.close();
                                                submitQuery();
                                            },1000)
                                        },
                                        showClose : true,
                                        maskCss: { // 遮罩层背景
                                            opacity: 0
                                        }

                                    })
                                    win.show();
                                    callback.close();
                                } else {
                                	var win = $.dialog({
                                        title : "",
                                        content : "<p class='add-fail'><i></i>"+data.responseMsg+"</p>",
                                        width : 114,
                                        onShow : function($dialog, callback){
                                            setTimeout(function(){
                                                callback.close();
                                                //submitQuery();
                                            },1000)
                                        },
                                        showClose : true,
                                        maskCss: { // 遮罩层背景
                                            opacity: 0
                                        }

                                    })
                                    win.show();
                                    //callback.close();
                                }
                            },
                            error: function () {
                            	var win = $.dialog({
                                    title : "",
                                    content : "<p class='add-fail'><i></i>操作异常</p>",
                                    width : 114,
                                    onShow : function($dialog, callback){
                                        setTimeout(function(){
                                            callback.close();
                                            //submitQuery();
                                        },1000)
                                    },
                                    showClose : true,
                                    maskCss: { // 遮罩层背景
                                        opacity: 0
                                    }

                                })
                                win.show();
                                //callback.close();
                            }
                        })
                    });
                    $dialog.find('.J_cancel').on('click', function() {
                        callback.close();
                    });
                }
            });

            d.show();
        },
        // 店铺详情弹窗
        shopDetailDialog: function() {
            var _this = this;
            var firstId = $(window.event.target).parents("tr").children().first().text();
            var storeName = $('#storeName' + firstId).html();
            var site = $('#site' + firstId).html();
            var storeUrl = $('#storeUrl' + firstId).html();
            var sellerId = $('#sellerId' + firstId).html();
            var sellGoods = $('#sellGoods' + firstId).html();
            var runTime = $('#runTime' + firstId).html();
            var mountAmt = $('#mountAmt' + firstId).html() + "万美元";
            var entityName = $('#entityName' + firstId).html();

            var html = template('shop-detail-dialog-tpl', {});

            var platform = $('.J_shopToggle .active input').val();

            var d = $.dialog({
                title: '店铺详情',
                content: html,
                width: 518,
                onShow: function($dialog, callback) {
                    document.getElementById('storeName').innerHTML = storeName;
                    document.getElementById('site').innerHTML = site;
                    document.getElementById('storeUrl').innerHTML = storeUrl;
                    document.getElementById('entityName').innerHTML = entityName;
                    if (platform == '1') {
                        $('.detail-dialog-body #sellerId').show();
                        document.getElementById('sellerId').innerHTML = sellerId;
                    } else {
                        $('.detail-dialog-body #sellerID').hide();
                    }
                    document.getElementById('sellGoods').innerHTML = sellGoods;
                    document.getElementById('runTime').innerHTML = runTime;
                    document.getElementById('mountAmt').innerHTML = mountAmt;

                    $dialog.find('.J_close').on('click', function() {
                        callback.close();
                    });
                }
            });

            d.show();
        },
        // 账号详情弹窗
        accountDetailDialog: function() {
            var _this = this;
            var firstId = $(window.event.target).parents("tr").children().first().text();
            var storeName = $('#storeName' + firstId).html();
            var bankName = $('#bankName' + firstId).html();
            var custName = $('#custName' + firstId).html();
            var custAccount = $('#custAccount' + firstId).html();
            var cur = $('#cur' + firstId).html();
            var bankCountryCode = $('#bankCountryCode' + firstId).html();

            var html = template('account-detail-dialog-tpl', {});

            var d = $.dialog({
                title: '账号详情',
                content: html,
                width: 518,
                onShow: function($dialog, callback) {
                    document.getElementById('storeName').innerHTML = storeName;
                    document.getElementById('bankName').innerHTML = bankName;
                    document.getElementById('custName').innerHTML = custName;
                    document.getElementById('custAccount').innerHTML = custAccount;
                    document.getElementById('cur').innerHTML = cur;
                    document.getElementById('bankCountryCode').innerHTML = bankCountryCode;
                    $dialog.find('.J_close').on('click', function() {
                        callback.close();
                    });
                }
            });

            d.show();
        }

    };

    Main.init();
});
