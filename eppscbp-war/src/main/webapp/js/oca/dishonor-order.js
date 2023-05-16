require.config({
    paths: {
        jquery: baseJSPath + 'lib/jquery.min',
        template: baseJSPath + 'lib/template.min',
        pagination: baseJSPath + 'lib/pagination',
        select: baseJSPath + 'lib/select',
        dialog: baseJSPath + 'lib/dialog',
        calendar: baseJSPath + 'lib/calendar-alter',
        ajaxFileUpload: baseJSPath + 'lib/ajaxFileUpload'
    },
    shim: {
        calendar: ['jquery'],
        pagination: ['jquery'],
        ajaxFileUpload: ['jquery']
    }
})
require(['jquery','template','calendar','pagination','select','dialog', 'ajaxFileUpload'],function($,template,calendar,pagination,select, ajaxFileUpload){
    var main = {
        //初始化执行
        init:function(){
            // 查询列表方法
            this.queryLoanList();
            //筛选
            this.queryFun();
            //接受
            this.acceptFun();
        },
         
        //筛选
        queryFun:function(){
            var self = this;
            //获取当前日期
            var day = new Date();
            day.setTime(day.getTime());
            var dataTime = day.getFullYear()+"-" + (day.getMonth()+1) + "-" + day.getDate();

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
                },
                callback:function(){
                    
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
                },
                callback:function(){
                    if($('#creationend').val()){
                        $('#creationstart').calendar({
                            count: 1,
                            isWeek: false,
                            isTime: false,
                            range: {
                                mindate: "1999-12-31",
                                maxdate:$('#creationend').val()
                            },
                            selectRange: {
                                min: 2000,
                                max: 2099
                            }
                        })
                    }
                    
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
            	var rejectOrderNo = $(".ordenumber").val();//拒付业务单号
            	var merchantOrderNo = $(".paymentno").val();//商户订单号
            	var status = $('.statuscode').attr('key');//拒付状态
            	//#转码
            	if(status.indexOf("#")){
            		status=status.replace('#','%23');
            	}
            	var createTimeStart = $('.creationstart').val().trim();//拒付创建时间开始
            	var createTimeEnd = $('.creationend').val().trim();//拒付创建时间结束
            	var updateTimeStart = $('.paystart').val().trim();//拒付完成时间开始
            	var updateTimeEnd = $('.payend').val().trim();//拒付完成时间结束
            	
            	window.location.href = sysconfig.ctx + "/oca/ocaRejectOrderController/export.htm?rejectOrderNo="+rejectOrderNo
                +"&merchantOrderNo="+merchantOrderNo+"&status="+status+"&createTimeStart="+createTimeStart+"&createTimeEnd="
                +createTimeEnd+"&updateTimeStart="+updateTimeStart+"&updateTimeEnd="+updateTimeEnd;
            	
            })

            //重置
            $('.resetbtn').click(function(){
                $(".ordenumber").val('');
                $(".paymentno").val('');
                $('.statuscode').attr('key','');
                $('.statuscode').attr('value','全部');
                $('.creationstart').val('');
                $('.creationend').val('');
                $('.paystart').val('');
                $('.payend').val('');
            })

        },
        
        

        //接受-申诉-滚动条处理
        acceptFun:function(){
            $('.appeal').live('click',function(){
                var merchantOrderNo = $(this).parents('.operation-item').attr('data-paymentNo');
                var rejectOrderAmt = $(this).parents('.operation-item').attr('data-orderAmount');
                var reasonCode = $(this).parents('.operation-item').attr('data-reasonCode');
                var rejectOrderNo = $(this).parents('.operation-item').attr('data-rejectOrderNo');
                var rejectAmt = $(this).parents('.operation-item').attr('data-rejectAmt');
                var rejectAmtCny = $(this).parents('.operation-item').attr('data-rejectAmtCny');
                var cur = $(this).parents('.operation-item').attr('data-cur');
                //alert(rejectOrderAmt);
                var d = $.dialog({
                    title: ' ',
                    content: template('dialog-appeal', {
                        content: {
                            merchantOrderNo:merchantOrderNo,
                            rejectOrderAmt:rejectOrderAmt,
                            reasonCode:reasonCode,
                            rejectOrderNo:rejectOrderNo,
                            rejectAmtStr:rejectAmt,
                            rejectAmtCnyStr:rejectAmtCny,
                            cur:cur
                        }
                    }),
                    width: 590,
                    height:594,
                    showClose: false, // 显示右上角关闭按钮
                    css: {
                        border: 2
                    },
                    afterShow: function ($dialog, callback) {
                        $('.dialog-appeal .close,.dialog-appeal .appeal-caner').live('click',function(){
                            callback.close();
                            //重置状态展示
                            $('.statuscode').attr('value', '全部');
                            window.location.reload();
                        })
                        $('.reason').live('input propertychange', function(){
                            var txtLength = $(this).val().length;
                            $('.txt-num').text(txtLength);
                            if($(this).val() && $('.showfile').val()){
                                $('.dialog-appeal .btntj').removeClass('isbtn')
                            }else{
                                $('.dialog-appeal .btntj').addClass('isbtn')
                            }
                        })
                        $('.fileclass').live('click',function(){
                            if($('.reason').val()){
                                $(this).siblings('.fileopt').click();
                            }else{
                                var dsuccessyc = $.dialog({
                                    title: ' ',
                                    content: template('dialog-txt-err', {
                                        content: '请先填写申请理由，再上传文件'
                                    }),
                                    width: 244,
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
                                    afterShow: function ($dialogs, callbackerr){
                                        setTimeout(function(){
                                            callbackerr.close();
                                        },3000)
                                    }
                                });
                                dsuccessyc.show();
                                $('.reason').val('');
                                return;
                            }

                        })
                        $('.fileopt').unbind().live('change', function(e) {//监听上传绑定事件
                            $this = $(this);
                            path = $this.val();
                            $('.showfile').val(path);

                            var imgFile = e.target.files[0];
                            var imgSize = imgFile.size;
                            var fileName = $(this).val().split("\\").slice(-1);
                            var formatStr = fileName.join('').split('.')[1];
                            var fileTypeLists = ['RAR','rar','zip','ZIP','DOC','doc','XLSX','xlsx','xls','XLS','pdf','PDF','bmp','BMP','JPG','jpg','jpeg','JPEG','PNG','png','docx','DOCX'];
                            if(imgSize > 104857600){
                                $('.dialog-appeal .btntj').addClass('isbtn')
                                return;
                            }
                            if(fileTypeLists.indexOf(formatStr) === -1){
                                $('.dialog-appeal .btntj').addClass('isbtn')
                                return;
                            }
                            $.ajaxFileUpload({
                                url: sysconfig.ctx + "/oca/ocaRejectHandingOrderController/appealFileSubmit.htm",
                                secureuri: false,
                                fileElementId: 'fileopt',
                                dataType: 'json',
                                success:function(json){
                                    if(json && json.success){
                                        $('.showfile').val(json.fileAddress);
                                        if($('.reason').val()){
                                            $('.dialog-appeal .btntj').removeClass('isbtn')
                                        }
                                    }
                                }
                            })
                        })

                        //点击提交
                        $('.dialog-appeal .btntj').live('click',function(){
                            if($(this).hasClass('isbtn'))return;
                            var params = {
                                reason:$('.reason').val(),
                                showfile:$('.showfile').val(),
                                rejectOrderNo: rejectOrderNo
                            }
                            $.ajax({
                                url: sysconfig.ctx + "/oca/ocaRejectHandingOrderController/appealSubmit.htm",
                                type: 'get',
                                data: params,
                                dataType: 'JSON',
                                success: function(json) {
                                    if(json && json.success){
                                        callback.close();
                                        var dsuccess = $.dialog({
                                            title: ' ',
                                            content: template('dialog-success', {
                                                content: '提交成功'
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
                                                    $('.statuscode').attr('value','全部');
                                                    window.location.reload();
                                                },1000)
                                            }
                                        });
                                        dsuccess.show();
                                    }else{
                                        callback.close();
                                        console.log(json.message)
                                    }
                                }

                            })
                        })
                    }
                });

                d.show();
            })


            $('.accept').live('click',function(){
                var merchantOrderNo = $(this).parents('.operation-item').attr('data-paymentNo');
                var rejectOrderAmt = $(this).parents('.operation-item').attr('data-orderAmount');
                var reasonCode = $(this).parents('.operation-item').attr('data-reasonCode');
                var rejectOrderNo = $(this).parents('.operation-item').attr('data-rejectOrderNo');
                var rejectAmt = $(this).parents('.operation-item').attr('data-rejectAmt');
                var rejectAmtCny = $(this).parents('.operation-item').attr('data-rejectAmtCny');
                var cur = $(this).parents('.operation-item').attr('data-cur');
               //alert(merchantOrderNo +"--"+rejectOrderAmt);
                $.ajax({
                    type: "POST",
                    dataType: "JSON",
                    url: sysconfig.ctx +  "/oca/ocaRejectHandingOrderController/messageBox.htm",
                    data: {"merchantOrderNo":merchantOrderNo},
                    success: function(json) {
                        console.log("reasonCode"+reasonCode);
                        console.log("messageList:"+json.messagelist);
                        if (json.success && json){
                            var d = $.dialog({
                                title: ' ',
                                content: template('dialog-accept', {
                                    content: {
                                        merchantOrderNo:merchantOrderNo,
                                        rejectOrderAmt:rejectOrderAmt,
                                        reasonCode:reasonCode,
                                        rejectOrderNo:rejectOrderNo,
                                        rejectAmtStr:rejectAmt,
                                        rejectAmtCnyStr:rejectAmtCny,
                                        cur:cur,
                                        totalCount:json.totalCount
                                    },
                                    messages:json.messagelist

                                }),
                                width: 570,
                                height:278,
                                showClose: false, // 显示右上角关闭按钮
                                css: {
                                    border: 2
                                },
                                afterShow: function ($dialog, callback) {
                                    $('.dialog-accept .close,.dialog-accept .canuer').live('click',function(){
                                        callback.close();
                                        //重置
                                        $('.statuscode').attr('value', '全部');
                                        window.location.reload();
                                    })
                                    $('.dialog-accept .surebtn').live('click',function(){
                                        var params = {
                                            merchantOrderNo:merchantOrderNo,
                                            rejectOrderNo:rejectOrderNo
                                        }
                                        $.ajax({
                                            url: sysconfig.ctx + "/oca/ocaRejectHandingOrderController/accept.htm",
                                            type: 'get',
                                            data: params,
                                            dataType: 'JSON',
                                            success: function(json) {
                                                if(json && json.success){
                                                    callback.close();
                                                    var dsuccess = $.dialog({
                                                        title: ' ',
                                                        content: template('dialog-success', {
                                                            content: '提交成功'
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
                                                                $('.statuscode').attr('value','全部');
                                                                window.location.reload();
                                                            },3000)
                                                        }
                                                    });
                                                    dsuccess.show();
                                                }else{
                                                    console.log(json.message)
                                                    //alert(json.message)
                                                }
                                            }

                                        })
                                    })
                                }

                            })
                            d.show();
                        }else {
                            console.log("查询无数据")
                        }
                    }
                });

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
                	rejectOrderNo: $(".ordenumber").val(),//拒付业务单号
                	merchantOrderNo: $(".paymentno").val(),//商户订单号
                	status: $('.statuscode').attr('key'),//拒付状态
                	createTimeStart: $('.creationstart').val().trim(),//拒付创建时间开始
                	createTimeEnd: $('.creationend').val().trim(),//拒付创建时间结束
                	updateTimeStart: $('.paystart').val().trim(),//拒付完成时间开始
                	updateTimeEnd: $('.payend').val().trim()//拒付完成时间结束

                    };
                },
            url: sysconfig.ctx + "/oca/ocaRejectOrderController/query.htm",
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