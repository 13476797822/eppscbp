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
            // 查询列表方法
            this.queryLoanList();
            //筛选
            this.queryFun();
        },
         
        //筛选
        queryFun:function(){
            var self = this;
            //获取当前日期
            var day = new Date();
            day.setTime(day.getTime());
            var dataTime = day.getFullYear()+"-" + (day.getMonth()+1) + "-" + day.getDate();
            $('.statuscode').attr('key','');
            $('.statuscode').val('全部')

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
                    mindate: "1199-12-31",
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
                    mindate: "1199-12-31",
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
            })

            $('.export').click(function(){//点击导出-拼接成的地址
            	var refundOrderNo = $(".ordenumber").val();//退款订单号
            	var merchantOrderNo = $(".paymentno").val();//商户订单号
            	var refundStatus = $('.statuscode').attr('key');//退款状态
            	var refundCreateFromTime = $(".creationstart").val().trim();//退款创建时间开始
            	var refundCreateToTime = $(".creationend").val().trim();//退款创建时间结束
            	var refundFinishFromTime = $(".paystart").val().trim();//退款完成时间开始
            	var refundFinishToTime = $(".payend").val().trim();//退款完成时间结束
            	
                window.location.href = sysconfig.ctx + "/oca/ocaRefundOrderController/export.htm?refundOrderNo="+refundOrderNo
                +"&merchantOrderNo="+merchantOrderNo+"&refundStatus="+refundStatus+"&refundCreateFromTime="+refundCreateFromTime+"&refundCreateToTime="
                +refundCreateToTime+"&refundFinishFromTime="+refundFinishFromTime+"&refundFinishToTime="+refundFinishToTime;
                
            })

            //重置
            $('.resetbtn').click(function(){
                $(".ordenumber").val('');
                $(".paymentno").val('');
                $('.statuscode').attr('key','101');
                $('.statuscode').val('退款成功')
                $('.creationstart').val('');
                $('.creationend').val('');
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
                	refundOrderNo: $(".ordenumber").val(),//退款订单号
                	merchantOrderNo:$(".paymentno").val(),//商户订单号
                	refundStatus:$('.statuscode').attr('key'),//退款状态
                    refundCreateFromTime: $(".creationstart").val().trim(),//退款创建时间开始
                    refundCreateToTime:$(".creationend").val().trim(),//退款创建时间结束
                    refundFinishFromTime: $(".paystart").val().trim(),//退款完成时间开始
                    refundFinishToTime:$(".payend").val().trim(),//退款完成时间结束
                    receiptOrderNo:$(".receiptOrderNo").val()//收单单号
                    };
                },
            url:  sysconfig.ctx + "/oca/ocaRefundOrderController/query.htm",
            $listWrap: $(".query-box-list"),
            // 模版 id
            tpls: {
                    list: "loan-tpl",
                    wait: "wait-tpl",
                    empty: "empty-tpl"
            },
            // 渲染数据之前处理数据的工作
            beforeRender: function (data) {
                    // 处理后台返回数据
                    return data;
                },
            }
            new pagination(params);
        }
    }
    main.init();

})