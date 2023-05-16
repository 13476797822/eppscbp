require.config({
    paths: {
        jquery: baseJSPath + 'lib/jquery.min',
        template: baseJSPath + 'lib/template.min',
        pagination: baseJSPath + 'lib/pagination',
        select: baseJSPath + 'lib/select',
        dialog: baseJSPath + 'lib/dialog',
        calendar: baseJSPath + 'lib/calendar-alter'
    },
    shim: {
        calendar: ['jquery'],
        pagination: ['jquery']
    }
})
require(['jquery','template','calendar','pagination','select','dialog'],function($,template,calendar,pagination,select){
    var main = {
        //初始化执行
        init:function(){
        	var _this = this;
            // 查询列表方法
        	setTimeout(function(){
                _this.queryLoanList();
            },1000);
            //筛选
            this.queryFun();
            //退款
            this.refundFun();
            //滚动事件
            this.scrollListFun();
        },

        //滚动事件处理
        scrollListFun:function(){
            setTimeout(function(){
                $('#manage-table').on('scroll',function(){
                    var winLeft=$(this).scrollLeft();
                    if( winLeft >=516 ){
                        $('.operation').removeClass('shadowhow')
                    }else{
                        $('.operation').addClass('shadowhow')
                    }
                })
            },1000)
        },

        //退款
        refundFun:function(){
         $('.refund').live('click',function(){
            var receiptOrderNo = $(this).attr('data-id');
            var merchantOrderNo = $(this).attr('data-merchantOrderNo');
            var params = {
                "receiptOrderNo":receiptOrderNo,
            }
            $.ajax({
                url: sysconfig.ctx + "/oca/ocaSaleOrderController/refundInfo.htm",
                type: 'get',
                data: params,
                dataType: 'JSON',
                success: function(json) {
                	//console.log(json)
                    if(json && json.success){
                        var d = $.dialog({
                            title: ' ',
                            content: template('dialog-refund', {
                                content: json
                            }),
                            width: 570,
                            height:370,
                            showClose: false, // 显示右上角关闭按钮
                            css: {
                                overflow: 'hidden',
                                'border-radius':4,
                                border: 4
                            },
                            afterShow: function ($dialog, callback) {
                                $('.dialog-refund .close,.dialog-refund .canor').click(function(){
                                    callback.close();
                                    window.location.reload();
                                })
                                if(json.record){
                                    $('.dialog-refund .record').show();
                                }else{
                                    $('.dialog-refund .record').hide();
                                }
                                $('.dialog-refund .record').live('click',function(){
                                    window.location.href = sysconfig.ctx + "/oca/ocaRefundOrderController/init.htm?merchantOrderNo="+merchantOrderNo;
                                })
                                //输入框校验
                                $('#returnmany').live("input propertychange",function(){
                                    // 清除"数字"和"."以外的字符
                                    var inputTxt = $(this).val().replace(/[^\d.]/g,"");
                                    // 验证第一个字符是非0数字
                                    //inputTxt = inputTxt.replace(/^0+|^\./g,"");
                                    // 只保留第一个, 清除多余的
                                    inputTxt = inputTxt.replace(/\.{2,}/g,".");                   
                                    inputTxt = inputTxt.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
                                    // 只能输入两个小数
                                    inputTxt = inputTxt.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
                                    $(this).val(inputTxt);

                                    if($(this).val() && Number($('#returnmany').val())>0){
                                        if(Number($('#returnmany').val())>Number(json.currencyOrder)){
                                            $('.cour').find('.item-right').addClass('err')
                                            $('.dialog-refund .refund-btn').removeClass('surebtn');
                                            $('.dialog-refund .recurrency').html('--')
                                        }else{
                                            $('.cour').find('.item-right').removeClass('err')
                                            $('.dialog-refund .refund-btn').addClass('surebtn');
                                            if(Number($('.returnmany').val()) == Number($("#refundAmt").html())){
                                            	$('.dialog-refund .recurrency').html(Number($('#refundAmtCny').html()));
                                            }else{
                                            	//四舍五入 保留两位小数，不足两位补0
                                            	var x=inputTxt*Number(json.exchangeRate);
                                            	var pos =2;
                                            	var f = parseFloat(x);
                                                if(isNaN(f)){
                                                    return false;
                                                }   
                                                f = Math.round(x*Math.pow(10, pos))/Math.pow(10, pos); // pow 幂   
                                                var s = f.toString();
                                                var rs = s.indexOf('.');
                                                if(rs < 0){
                                                    rs = s.length;
                                                    s += '.'; 
                                                }
                                                while(s.length <= rs + pos){
                                                    s += '0';
                                                }
                                            	$('.dialog-refund .recurrency').html(s);
                                            }
                                            
                                        }
                                    }else{
                                        $('.cour').find('.item-right').removeClass('err');
                                        $('.dialog-refund .refund-btn').removeClass('surebtn');
                                        $('.dialog-refund .recurrency').html('--')
                                    }                  
                                });
                                //取消退款
                                $('.dialog-refund .canor').live('click',function(){
                                    callback.close();
                                    window.location.reload();
                                })

                                //确认退款
                                $('.dialog-refund .refundsrue').live('click',function(){
                                    if(!$('.dialog-refund .refund-btn').hasClass('surebtn')) return;
                                    if(Number($('#returnmany').val())>Number(json.currencyOrder)){
                                        $('.cour').find('.item-right').addClass('err')
                                    }else{
                                    	var refundAmt = $('.returnmany').val();
                                    	var params = {
                                                "receiptOrderNo":receiptOrderNo,
                                                "refundAmt" :refundAmt ,
                                                "merchantOrderNo": merchantOrderNo
                                            }
                                        $.ajax({
                                            url:  sysconfig.ctx + "/oca/ocaRefundOrderController/refundOrderSubmit.htm",
                                            type: 'get',
                                            data: params,
                                            dataType: 'JSON',
                                            success: function(json) {
                                                if(json && json.success){
                                                    var dsuccess = $.dialog({
                                                        title: ' ',
                                                        content: template('dialog-refund-success', {
                                                            content: json
                                                        }),
                                                        width: 155,
                                                        height:36,
                                                        maskCss:{
                                                            'display':'none',
                                                            'width':0
                                                        },
                                                        showClose: false, // 显示右上角关闭按钮
                                                        css: {
                                                            border: 2,
                                                            'border-radius':4,
                                                            overflow: 'hidden',
                                                            'box-shadow': '0 2px 8px 0 rgba(0,0,0,0.20)'
                                                        },
                                                        afterShow: function ($dialogs, callbacks){
                                                            setTimeout(function(){
                                                                callbacks.close();
                                                                window.location.reload();
                                                            },1000)
                                                        }
                                                    });
                                                    dsuccess.show();
                                                    callback.close();
                                                }else{
                                                	var dsuccessorderfaile = $.dialog({
                                                        title: ' ',
                                                        content: template('qtdialog-err', {
                                                            content: json.message
                                                        }),
                                                        width: 315,
                                                        height:36,
                                                        maskCss:{
                                                            'display':'none',
                                                            'width':0
                                                        },
                                                        showClose: false, // 显示右上角关闭按钮
                                                        css: {
                                                            border: 2,
                                                            'border-radius':4,
                                                            overflow: 'hidden',
                                                            'box-shadow': '0 2px 8px 0 rgba(0,0,0,0.20)'
                                                        },
                                                        afterShow: function ($dialogs, callbackorderfaile){
                                                            setTimeout(function(){
                                                                dsuccessorderfaile.close();
                                                            },2000)                                                                                                                           
                                                        }
                                                    });
                                                    dsuccessorderfaile.show()
                                                }

                                            }
                                        })
                                    }
                                })
                                // callback.close()
                            }
                        });
                        d.show();

                    }
                }
            })
            
         }) 
        }, 
         
        //筛选
        queryFun:function(){
            var self = this;
            //获取当前日期
            var day = new Date();
            day.setTime(day.getTime());
            var dataTime = day.getFullYear()+"-" + (day.getMonth()+1) + "-" + day.getDate();
            $('.creationstart').val(dataTime);
            $('.creationend').val(dataTime);
            $('.statuscode').val('支付成功');

            //日期下拉组件
            $('#creationstart').calendar({
                count: 1,
                isWeek: false,
                isTime: false,
                range: {
                    mindate: "1999-12-31",
                    maxdate:dataTime
                },
                selectRange: {
                    min: 2000,
                    max: 2099
                }
            })

            $('#creationend').calendar({
                count: 1,
                isWeek: false,
                isTime: false,
                range: {
                    mindate: "1999-12-31",
                    maxdate:dataTime
                },
                selectRange: {
                    min: 2000,
                    max: 2099
                }
            })   
            
            $('#paystart').calendar({
                count: 1,
                isWeek: false,
                isTime: false,
                range: {
                    mindate: "1999-12-31",
                    maxdate:dataTime
                },
                selectRange: {
                    min: 2000,
                    max: 2099
                }
            })
            
            $('#payend').calendar({
                count: 1,
                isWeek: false,
                isTime: false,
                range: {
                    mindate: "1999-12-31",
                    maxdate:dataTime
                },
                selectRange: {
                    min: 2000,
                    max: 2099
                }
            })           
            
            //查询
            $('.querybtn').click(function(){
                self.queryLoanList();

                // 支付处理中才展示 批量交易查询
                var orderStatus = $('.statuscode').attr('key');//订单状态
                if(orderStatus == 301) {
                    $(".queryChannel").show();
                }else {
                    $(".queryChannel").hide();
                }
            })

            // 批量结果查询
            $(".queryChannel").click(function () {
                console.log(123321)
                var receiptOrderNoStr=[];
                var i=0;
                $(".chk:checked").each(
                    function(){
                        receiptOrderNoStr.push($(this).val());
                        receiptOrderNoStr.join(",");
                        i++;
                    });
                // 是否勾选数据
                if (receiptOrderNoStr == null || receiptOrderNoStr.length <= 0){
                    var dsuccess = $.dialog({
                        title: ' ',
                        content: template('qtdialog-err', {
                            content: '请勾选需要查询的订单'
                        }),
                        width: 250,
                        height:36,
                        maskCss:{
                            'display':'none',
                            'width':0
                        },
                        showClose: true, // 显示右上角关闭按钮
                        css: {
                            border: 2,
                            'border-radius':4,
                            overflow: 'hidden',
                            'box-shadow': '0 2px 8px 0 rgba(0,0,0,0.20)'
                        },
                        afterShow: function ($dialogs, callbacks){
                            setTimeout(function(){
                                dsuccess.close();
                            },1600)
                        }
                    });
                    dsuccess.show();
                }
                var params = {
                    "receiptOrderNoStr":receiptOrderNoStr.toString(),
                }

                $.ajax({
                    url: sysconfig.ctx + "/oca/ocaSaleOrderController/batchQueryChannel.htm",
                    type: 'get',
                    data: params,
                    dataType: 'JSON',
                    success:function (json) {
                        if(json && json.success){
                            var dsuccess = $.dialog({
                                title: ' ',
                                content: template('batch-query-success', {
                                    content: json
                                }),
                                width: 255,
                                height:36,
                                maskCss:{
                                    'display':'none',
                                    'width':0
                                },
                                showClose: false, // 显示右上角关闭按钮
                                css: {
                                    border: 2,
                                    'border-radius':4,
                                    overflow: 'hidden',
                                    'box-shadow': '0 2px 8px 0 rgba(0,0,0,0.20)'
                                },
                                afterShow: function ($dialogs, callbacks){
                                    setTimeout(function(){
                                        callbacks.close();
                                        window.location.reload();
                                    },1000)
                                }
                            });
                            dsuccess.show();
                            callback.close();
                        }else{
                            var dsuccessorderfaile = $.dialog({
                                title: ' ',
                                content: template('qtdialog-err', {
                                    content: json.message
                                }),
                                width: 315,
                                height:36,
                                maskCss:{
                                    'display':'none',
                                    'width':0
                                },
                                showClose: false, // 显示右上角关闭按钮
                                css: {
                                    border: 2,
                                    'border-radius':4,
                                    overflow: 'hidden',
                                    'box-shadow': '0 2px 8px 0 rgba(0,0,0,0.20)'
                                },
                                afterShow: function ($dialogs, callbackorderfaile){
                                    setTimeout(function(){
                                        dsuccessorderfaile.close();
                                    },2000)
                                }
                            });
                            dsuccessorderfaile.show()
                        }

                    }

                })

            })

            $('.export').click(function(){//点击导出-拼接成的地址
            	var merchantOrderNo = $(".ordenumber").val();//商户单号
            	var receiptOrderNo = $(".paymentno").val();//支付请求单号
            	var orderStatus = $('.statuscode').attr('key');//订单状态
            	var cur = $('.currency').attr('key');//币种
            	var orderCreateFromTime = $('.creationstart').val().trim();//订单创建时间开始
            	var orderCreateToTime = $('.creationend').val().trim();//订单创建时间结束
            	var payFinishFromTime = $('.paystart').val().trim();//支付完成时间开始
            	var payFinishToTime = $('.payend').val().trim();//支付完成时间结束
            	
                window.location.href = sysconfig.ctx + "/oca/ocaSaleOrderController/export.htm?merchantOrderNo="
                +merchantOrderNo+"&receiptOrderNo="+receiptOrderNo+"&orderStatus="+orderStatus+"&cur="+cur+"&orderCreateFromTime="
                +orderCreateFromTime+"&orderCreateToTime="+orderCreateToTime+"&payFinishFromTime="+payFinishFromTime+"&payFinishToTime="+payFinishToTime
            })

            //重置
            $('.resetbtn').click(function(){
                $(".ordenumber").val('');
                $(".paymentno").val('');
                $('.statuscode').attr('key','100');
                $('.statuscode').val('支付成功')
                $('.currency').attr('key','');
                $('.currency').val('全部');
                $('.creationstart').val(dataTime);
                $('.creationend').val(dataTime);
                $('.paystart').val('');
                $('.payend').val('');
            })

        },

        // 查询交易列表
        queryLoanList: function() {
            var self = this;
            
            var params = {
            // 获取页面的查询条件
            getSearchConditions: function () {
                return {
                    // 上传数据
                	merchantOrderNo: $(".ordenumber").val(),//商户单号
                	receiptOrderNo:$(".paymentno").val(),//支付请求单号
                	orderStatus:$('.statuscode').attr('key'),//订单状态
                	cur:$('.currency').attr('key'),//币种
                	orderCreateFromTime:$('.creationstart').val().trim(),//订单创建时间开始
                	orderCreateToTime:$('.creationend').val().trim(),//订单创建时间结束
                	payFinishFromTime:$('.paystart').val().trim(),//支付完成时间开始
                	payFinishToTime:$('.payend').val().trim()//支付完成时间结束
                	
                    };
                },
            url: sysconfig.ctx + "/oca/ocaSaleOrderController/query.htm",
            $listWrap: $(".query-box-list"),
            // 模版 id
            tpls: {
                    list: "loan-tpl",
                    wait: "wait-tpl",
                    empty: "empty-tpl"
            },
            // 渲染数据之前处理数据的工作
            beforeRender: function (data) {
            	//console.log(data)
                    // 处理后台返回数据
                    return data;
                },
            }
            new pagination(params);
        }
    }
    main.init();

})