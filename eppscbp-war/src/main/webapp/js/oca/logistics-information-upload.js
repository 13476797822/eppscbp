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
require(['jquery','template','calendar','pagination','select','dialog','ajaxFileUpload'],function($,template,calendar,pagination,select,dialog,ajaxFileUpload){
    var main = {
        //初始化执行
        init:function(){
            // 查询列表方法
            this.queryLoanList();
            //筛选
            this.queryFun();
            //批量导入-导出
            this.bulkImportFun()
        },

        //批量导入
        bulkImportFun:function(){

            //导出
            $('.export').live('click',function(){
            	var merchantOrderNo = $(".ordenumber").val();//商户订单号
            	var logisticsStatus = $('.statuscode').attr('key');//物流状态
            	var payFromTime = $(".creationstart").val().trim();//支付时间开始
            	var payToTime = $(".creationend").val().trim();//支付时间结束
            	var uploadFromTime = $(".paystart").val().trim();//上传时间开始
            	var uploadToTime = $(".payend").val().trim();//上传时间结束
                
                window.location.href = sysconfig.ctx + "/oca/ocaLogisticsInfoController/export.htm?merchantOrderNo="+merchantOrderNo
                +"&logisticsStatus="+logisticsStatus+"&payFromTime="+payFromTime+"&payToTime="+payToTime+"&uploadFromTime="+uploadFromTime
                +"&uploadToTime="+uploadToTime;
            })

            //批量导入
            $('.bulk-import').live('click',function(){
                var d = $.dialog({
                    title: ' ',
                    content: template('dialog-upload', {
                        content: {
                            
                        }
                    }),
                    width: 530,
                    height:318,
                    showClose: false, // 显示右上角关闭按钮
                    css: {
                        border: 2,
                        'border-radius':4,
                        overflow: 'hidden'
                    },
                    afterShow: function ($dialog, callback) {
                        $('.dialog-upload .upload-select').live('click',function(){
                            $('.fileinput').click()
                        });
                        var uploadFailUrl = '';
                        $('.fileinput').live('change',function(e){
                        	$this = $(this);
                            path = $this.val();
                            $('.fileshow').val(path);
                            //alert(path);
                            
                            $.ajaxFileUpload({
                                url: sysconfig.ctx + "/oca/ocaLogisticsInfoController/fileUpload.htm",
                                secureuri: false,
                                fileElementId: 'fileinput',
                                type : 'POST',
                                dataType: 'JSON',
                                success:function(json){
                                	if(typeof json ==='string'){
                                			json = $.parseJSON(json)
                                		}
                                    if(json && json.success){
                                            $('.pre-content-list .nth01').text(json.logisticsCount)
                                            $('.pre-content-list .nth02').text(json.logisticsCountSucess)
                                            $('.pre-content-list .nth03').text(json.logisticsCountFail)
                                            $('.dialog-upload').removeClass('dialog-hide');
                                            if(json.filePathSuccess == "" ){
                                            	$('.fileshow').val(path);
                                            }else{
                                            	$('.fileshow').val(json.filePathSuccess);
                                            }
                                           
                                            if( Number(json.logisticsCountSucess) > 0 ){
                                            	 $('.uplod-btn').removeClass('nobtn');
                                            	
                                            }else{
                                            	$('.uplod-btn').addClass('nobtn');
                                            	}
                                            if(Number(json.logisticsCountFail) == 0 && !$('.pre-content-list .nth04').hasClass('noupload')){
                                                $('.pre-content-list .nth04').addClass('noupload')
                                            }
                                            uploadFailUrl = json.filePathFail;
                                    }else{
                                        var dserr = $.dialog({
                                            title: ' ',
                                            content: template('dialog-err', {
                                                content: json.message
                                            }),
                                            width: 210,
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
                                                },5000)
                                            }
                                        });
                                        dserr.show();
                                    }
                                }
                            })                            
                        })
                        $('.dialog-upload .close,.dialog-upload .cancel').live('click',function(){
                            callback.close();
                            window.location.reload();
                        })
                        //下载
                        $('.import-download').live('click',function(){
                        	if($('.pre-content-list .nth04').hasClass('noupload')) return
                            window.location.href = sysconfig.ctx + "/oca/ocaLogisticsInfoController/downLoadErrorFile.htm?fileAddress="+uploadFailUrl
                        });
                        //下载模板
                        $('.dialog-upload .download').live('click',function(){
                            window.location.href = sysconfig.ctx +'/template/file/oca_logistics_template.xlsx'
                        })

                        //保存
                        $('.dialog-upload .preservbtn').live('click',function(){
                        	if($(this).parent('.uplod-btn').hasClass('nobtn')){
                                return
                             }
                            var params = {
                            		fileshow : $('.fileshow').val()	
                            }
                            $.ajax({
                                url: sysconfig.ctx + "/oca/ocaLogisticsInfoController/fileSubmit.htm",
                                type: 'get',
                                data: params,
                                dataType: 'JSON',
                                success: function(json) {
                                    if(json && json.success){
                                        callback.close();
                                        var dsuccess = $.dialog({
                                            title: ' ',
                                            content: template('dialog-success', {
                                                content: '保存成功'
                                            }),
                                            width: 135,
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

                                    }else{
                                        alert("提交失败");
                                    }
                                }
                            })
                            

                        })

                    }

                })
                d.show();
            })

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
                window.location.href = ''
            })

            //重置
            $('.resetbtn').click(function(){
                $(".ordenumber").val('');
                $(".paymentNo").val('');
                $('.statuscode').attr('key','');
                $('.statuscode').val('全部')
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
                	merchantOrderNo: $(".ordenumber").val(),//商户订单号
                	logisticsStatus:$('.statuscode').attr('key'),//物流状态
                    payFromTime : $(".creationstart").val().trim(),//支付时间开始
                    payToTime : $(".creationend").val().trim(),//支付时间结束
                    uploadFromTime : $(".paystart").val().trim(),//上传时间开始
                    uploadToTime : $(".payend").val().trim()//上传时间结束

                    };
                },
            url: sysconfig.ctx + "/oca/ocaLogisticsInfoController/query.htm",
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