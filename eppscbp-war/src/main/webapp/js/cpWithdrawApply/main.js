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
            var len = $('.withdraw-apply-form').find('dl').length;
            $('.withdraw-apply-form').find('dl').each(function (i, e) {
                $(this).css({'z-index': len - i});
            });
            this.bindEvent();
            this.uploadFile();
        },
        uploadFile: function () {
            var $file = $('input[type=file]'),
                $this, path, name, id;
            $file.unbind().live('change', function () {

                $this = $(this);
                path = $this.val();
                name = $this.prop('name');
                id = $this.prop('id');
                $('.success').addClass('hide');
                $('.fail').addClass('hide');
                $("#fileAddress").val('');
                $('.J_fileNameInput').val(path);

                var html = '<div class="loading-box"><img src="' + sysconfig.ctx + '/style/images/submit-loading.gif" style="width:100%;height:100%;"><p>文件上传中...</p></div>';
                $("body").append(html);

                var currency = $("#currency").val();//币种
                var receiveAmt = $("#applyAmtHD").val();//申请提现金额
                //上传文件
                $.ajaxFileUpload({
                    url: sysconfig.ctx + "/cpWithdrawApply/cpWithdrawApplyInit/fileSubmit.htm",
                    type: 'POST',
                    fileElementId: 'fileUpload',
                    data: {currency: currency, receiveAmt: receiveAmt, collAndSettleFlag: "0"},
                    dataType: 'text',
                    success: function (data) {
                        //若页面过期跳转至首页
                        isIntercepted(data);
                        var obj = eval('(' + data + ')');
                        if (obj.success) {
                            $('.success').removeClass('hide');
                            $('.fail').addClass('hide');
                            $('#fileAddress').val(obj.fileAddress);
                            $('#detailAmount').val(obj.detailAmount);
                            $('.J_submitBtn').removeClass('disabled');
                        } else {
                            $('.success').addClass('hide');
                            $('.fail').removeClass('hide');
                            $('.fail .error-txt').html(obj.message);
                            $('.J_submitBtn').addClass('disabled');
                        }
                        $(".loading-box").remove();
                    },
                    error: function () {
                        $(".loading-box").remove();
                        $('.success').addClass('hide');
                        $('.fail').removeClass('hide');
                        $('.fail .error-txt').html("上传异常,请重新上传!");
                        $('.J_submitBtn').addClass('disabled');
                    }
                });
            });
        },

        bindEvent: function () {
            var _this = this;

            // 提现币种选择
            $('#currency').on('change', function () {
                $("#select-box-merchant").parent().parent().find('.tip-txt').empty();
                $("#select-box-merchant").parent().parent().find('.tip-txt').removeClass('err');

                _this.clearData();
                var currency = $("#currency").val();
                $("#paymentAmountSel").val(null);//付款金额
                $('#paymentAccountSel').val(null);//付款账号
                $('#accountBatchSel').val(null);//出账批次
                $('.J_yfbWithdrawNum').text('');//易付宝商户提现笔数
                $('.J_yfbWithdrawAmount').text('');//易付宝商户提现金额
                $('.J_withdrawAmount').text('');//申请提现金额
                $('#isIncludeSel').val(null);//是否框

                $.ajax({
                    type: "POST",
                    data: {"currency": currency},
                    url: sysconfig.ctx + "/cpWithdrawApply/cpWithdrawApplyInit/currencyQuery.htm",
                    dataType: "JSON",
                    async: false,
                    success: function (data) {
                        //若页面过期跳转至首页
                        isIntercepted(data);
                        $('#select-box-merchant').find("li").remove();
                        if (data.success == true) {
                            var arrivalInfoList = data.result.arrivalInfoList;
                            if (typeof(arrivalInfoList) == "undefined" || arrivalInfoList.length == 0) {
                                $("#currency").parent().parent().find('.tip-txt').append('该币种未开通境外账号').addClass('err');
                                return;
                            }
                            $('#paymentAccountSel').parents('.jr-select').removeClass('disabled').parents('dd').find('.tip-txt').text('');
                            for (var i = 0; i < arrivalInfoList.length; i++) {
                                var arrivalInfoResDto = arrivalInfoList[i];
                                var payerBankCard = arrivalInfoResDto.payerBankCard;//付款帐号
                                // 双引号替换成单引号避免data属性错误
                                var arrivalNoticeList = JSON.stringify(arrivalInfoResDto.arrivalNoticeList);
                                arrivalNoticeList = arrivalNoticeList.replace(/\"/g, "\'");
                                var option = $('<li class="select-item"><a class="sel-val" href="javascript:void(0);" key="1" value="' + payerBankCard + '" data="' + arrivalNoticeList + '">' + payerBankCard + '</a></li>');
                                $('#select-box-merchant').append(option);
                                $('#select-merchant').initSelect();
                            }
                            $('#paymentAmountSel').parents('.jr-select').addClass('disabled').parents('dd').find('.tip-txt').text('请先选择付款账号');
                            $('.J_detailUploadLine').addClass('hide');//隐藏文件上传框
                            $('.J_yfbInfoLine').addClass('hide');

                        } else {
                            $("#currency").parent().parent().find('.tip-txt').append(data.responseMsg).addClass('err');
                        }
                    },
                    error: function () {
                        $("#currency").parent().parent().find('.tip-txt').append('该币种未开通境外账号').addClass('err');
                    }
                });


            });
            // 付款账号选择
            $('#select-merchant').on("click", ".sel-val", function () {
                $("#select-box-merchant").parent().parent().find('.tip-txt').empty();
                $("#select-box-merchant").parent().parent().find('.tip-txt').removeClass('err');

                _this.clearData();

                $('#select-box-Amount').find("li").remove();//清空付款金额列表
                var arrivalNoticeListStr = $(this).attr("data");//获取付款帐号对应的付款金额
                arrivalNoticeListStr = arrivalNoticeListStr.replace(/\'/g, "\"");
                var arrivalNoticeList = JSON.parse(arrivalNoticeListStr);
                if (arrivalNoticeList != null) {
                    for (var i = 0; i < arrivalNoticeList.length; i++) {
                        var amount = arrivalNoticeList[i].amount;//付款金额
                        var arrivalNoticeId = arrivalNoticeList[i].arrivalNoticeId;//待解付资金ID
                        var payNo = arrivalNoticeList[i].payNo;//待解付资金流水号
                        var info = arrivalNoticeId + ";" + payNo;
                        var option = $('<li class="select-item"><a class="sel-val" href="javascript:void(0);" key="1" value="' + amount + '" data="' + info + '">' + amount + '</a></li>');
                        $('#select-box-Amount').append(option);
                        $('#select-Amount').initSelect();
                    }
                    $('#paymentAmountSel').parents('.jr-select').removeClass('disabled').parents('dd').find('.tip-txt').text('');

                }
                $('body').click();
            });

            // 付款金额选择
            $('#select-Amount').on("click", ".sel-val", function () {
                _this.clearData();
                var value = $(this).attr("data");//获取待解付资金ID和待解付资金流水号
                var attr = value.split(";");
                $('#arrivalNoticeId').val(attr[0]);
                $('#payNo').val(attr[1]);
            });
            // 是否包含易付宝商户资金选择
            $('#isIncludeSel').on('change', function () {
            	$('.J_submitBtn').addClass('disabled');
            	$('.J_withdrawAmount').text("");
                if ($(this).val() == '是') {
                    $('#select-box-batch').find("li").remove();//清空出账批次列表
                    $('#accountBatchSel').val(null);//出账批次
                    $('.J_yfbWithdrawNum').text('');//易付宝商户提现笔数
                    $('.J_yfbWithdrawAmount').text('');//易付宝商户提现金额
                    $('.J_withdrawAmount').text('');//申请提现金额
                    $('.J_yfbInfoLine').removeClass('hide');
                    var currency = $('#currency').val();//提现币种
                    $.ajax({
                        type: "POST",
                        data: {"currency": currency},
                        url: sysconfig.ctx + "/cpWithdrawApply/cpWithdrawApplyInit/outBatchQuery.htm",
                        dataType: "JSON",
                        async: false,
                        success: function (data) {
                            //若页面过期跳转至首页
                            isIntercepted(data);
                            if (data.success == true) {
                                var listArray = data.result.list;
                                for (var i = 0; i < listArray.length; i++) {
                                    var arrivalInfoResDto = listArray[i];
                                    var outAccountBatch = arrivalInfoResDto.outAccountBatch;//出账批次
                                    if (outAccountBatch != null && outAccountBatch.length != 0) {
                                        var option = $('<li class="select-item"><a class="sel-val" href="javascript:void(0);" key="1" value="' + outAccountBatch + '">' + outAccountBatch + '</a></li>');
                                        $('#select-box-batch').append(option);
                                        $('#select-batch').initSelect();
                                    }
                                }

                            } else {
                                $("#accountBatchSel").parent().parent().find('.tip-txt').append(data.responseMsg).addClass('err');
                            }
                        },
                        error: function () {
                            $("#accountBatchSel").parent().parent().find('.tip-txt').append('未查询到有效的出账批次信息').addClass('err');
                        }
                    });

                } else if ($(this).val() == '否') {
                    $('.J_yfbInfoLine').addClass('hide');
                    _this.computeWithdrawAmount();
                }               
            });
            // 出账批次选择
            $('#accountBatchSel').on('change', function () {
                if ($(this).val() != '') {
                    var outAccountBatch = $(this).val();//出账批次
                    var currency = $('#currency').val();//提现币种
                    $.ajax({
                        type: "POST",
                        data: {"outAccountBatch": outAccountBatch, "currency": currency},
                        url: sysconfig.ctx + "/cpWithdrawApply/cpWithdrawApplyInit/batchQuery.htm",
                        dataType: "JSON",
                        async: false,
                        success: function (data) {
                            //若页面过期跳转至首页
                            isIntercepted(data);
                            if (data.success == true) {
                                var arrivalInfoList = data.result;
                                if (typeof(arrivalInfoList) == "undefined" || arrivalInfoList.length == 0) {
                                    $("#accountBatchSel").parent().parent().find('.tip-txt').append('未查询到有效的出账批次信息').addClass('err');
                                    return;
                                }
                                $('.J_yfbWithdrawNum').text(data.result.withdrawAmount + ' 笔');
                                $('.J_yfbWithdrawAmount').text(data.result.withdrawTotalAmt + ' 元');

                            } else {
                                $("#accountBatchSel").parent().parent().find('.tip-txt').append(data.responseMsg).addClass('err');
                            }
                        },
                        error: function () {
                            $("#accountBatchSel").parent().parent().find('.tip-txt').append('未查询到有效的出账批次信息').addClass('err');
                        }
                    });
                } else {
                    $('.J_yfbWithdrawNum').text('');
                    $('.J_yfbWithdrawAmount').text('');
                }

                _this.computeWithdrawAmount();
            });

            $('.J_fileBtn').on('click', function () {
                $(this).siblings('.J_fileInput').trigger('click');
            });

            // 提交
            $('.J_submitBtn').on('click', function () {
                if ($(this).hasClass('disabled')) {
                    return;
                }

                _this.infoConfirmDialog();
            });
            
            //平台名称
            $('#platformName').on('focus', function () {
            	$("#platformName").parent().parent().find('.tip-txt').empty();
            	$("#platformName").parent().parent().find('.tip-txt').removeClass('err');         	
            });
            $('#platformName').on('blur', function () {
            	if ($(this).val() != '') {
            		var reg = /^([a-z]|[A-Z]|[0-9]|[/\?\(\)\.\,\:\-\'\+\{\}\]]|[ ]){4,8}$|^[\u4e00-\u9fa5]{2,4}$/;
            		var platformName = $(this).val();
            		if(!reg.test(platformName)){
            			 $("#platformName").parent().parent().find('.tip-txt').append('格式不正确').addClass('err');
            			 $('.J_submitBtn').addClass('disabled');
            		}
            	}          	
            });
        },

        clearData: function () {
            $('.success').addClass('hide');
            $("#fileAddress").val('');
            $('.fail').addClass('hide');
            $("#fileUpload").val('');
            $(".J_fileNameInput").val('');
        },

        // 计算申请提现金额
        computeWithdrawAmount: function () {
            var withdrawAmountTxt = '';
            var withdrawAmount = 0;
            if ($('#isIncludeSel').val() != '' && $('#paymentAmountSel').val() != '') {

                var paymentAmount = +$('#paymentAmountSel').val();
                if ($('#isIncludeSel').val() == '是') {
                    if ($('.J_yfbWithdrawAmount').text() != '') {
                        var yfbWithdrawAmount = parseFloat($('.J_yfbWithdrawAmount').text());

                        withdrawAmount = paymentAmount - yfbWithdrawAmount;
                        withdrawAmount = withdrawAmount.toFixed(2);//四舍五入
                        withdrawAmountTxt = withdrawAmount + ' 元';
                    }
                } else if ($('#isIncludeSel').val() == '否') {
                    withdrawAmount = paymentAmount;
                    withdrawAmountTxt = withdrawAmount + ' 元';
                }


            }

            $('.J_withdrawAmount').text(withdrawAmountTxt);
            $('#applyAmtHD').val(withdrawAmount);//赋值提现申请金额 数字

            if (withdrawAmountTxt != '' && withdrawAmount != 0) {
            	$("#platformNameDiv").show();
                $('.J_detailUploadLine').removeClass('hide');
            } else {
            	$("#platformNameDiv").hide();
            	$("#platformName").val("");
                $('.J_detailUploadLine').addClass('hide');
                $('.J_submitBtn').removeClass('disabled');
            }

        },
        // 信息确认弹窗
        infoConfirmDialog: function () {
            var _this = this;
            var currency = $('#currency').val();////提现币种
            var paymentAccountSel = $('#paymentAccountSel').val();////付款账号
            var paymentAmountSel = $('#paymentAmountSel').val();////付款金额
            var accountBatchSel = $('#accountBatchSel').val();////出账批次
            var isIncludeSel = $('#isIncludeSel').val();////是否包含易付宝商户资金
            var withdrawNum = $('.J_yfbWithdrawNum').text();////易付宝商户提现笔数
            var yfbWithdrawAmount = $('.J_yfbWithdrawAmount').text();////易付宝商户提现金额
            var withdrawAmount = $('.J_withdrawAmount').text();////申请提现金额
            var applyAmtHD = $('#applyAmtHD').val();////申请提现金额隐藏域
            var detailAmount = $('#detailAmount').val();////申请明细笔数
            var platformName = $('#platformName').val();////平台名称
            var data ={
            	'currency':currency,
            	'payAccount':paymentAccountSel,
            	'payAmt':paymentAmountSel + ' 元',           	
            	'applyAmt':withdrawAmount,
            	'applyNum':detailAmount + ' 笔',
            	'isIncludeSel':$('#isIncludeSel').val(),
            	'platformName':platformName
            };
            if($('#isIncludeSel').val() == '是'){
            	var extraData = {
            		'batch':accountBatchSel,
            		'withdrawNum':withdrawNum,
            		'withdrawAmt':yfbWithdrawAmount           		
            	};
            	$.extend(data, extraData);
            }
            var html = template('inf-confirm-dialog-tpl', data);
            
            if(detailAmount==''||detailAmount==null||detailAmount==undefined){
            	detailAmount=0;
            }
            
            var fileAddress = $('#fileAddress').val();////地址
            var arrivalNoticeId = $('#arrivalNoticeId').val();////待解付资金ID
            var payNo = $('#payNo').val();////待解付资金流水号

            var d = $.dialog({
                title: '提现信息确认',
                content: html,
                width: 518,
                onShow: function ($dialog, callback) {
                   
                    $dialog.find('.J_submit').on('click', function () {
                        callback.close();
                        var formReq = {};
                        formReq.withdrawCur = currency;
                        formReq.payAccount = paymentAccountSel;
                        formReq.payerAmt = applyAmtHD;
                        formReq.isEppFund = isIncludeSel;
                        formReq.outAccountBatch = accountBatchSel;
                        formReq.fileAddress = fileAddress;
                        formReq.detailAmount = detailAmount;
                        formReq.payNo = payNo;
						formReq.arrivalNoticeId = arrivalNoticeId;
						formReq.pcToken = _dfp.getToken();
						formReq.platformName = platformName;
                        $.ajax({
                            type: "POST",
                            data: JSON.stringify(formReq),
                            url: sysconfig.ctx + "/cpWithdrawApply/cpWithdrawApplyInit/applySubmit.htm",
                            dataType: "JSON",
                            contentType: 'application/json;charset=utf-8', //设置请求头信息
                            async: false,
                            success: function (data) {
                            	//若页面过期跳转至首页
                				isIntercepted(data);
                                if (data.success) {
                                    Common.toast('success', '提现申请提交成功');
                                    $('input').val("");//清空输入框
                                    $('.J_submitBtn').addClass('disabled');//置灰提交框
                                    $('.J_yfbInfoLine').addClass('hide');//隐藏是否框
                                    $('.J_detailUploadLine').addClass('hide');//隐藏上传文件框
                                    $('.J_withdrawAmount').text('');//清空申请提现金额
                                    $("#platformNameDiv").hide();//隐藏平台名称
                                    $("#platformNameDiv").val("");
                                    $('#paymentAccountSel').parents('.jr-select').addClass('disabled').parents('dd').find('.tip-txt').text('请先选择提现币种');
                                    $('#paymentAmountSel').parents('.jr-select').addClass('disabled').parents('dd').find('.tip-txt').text('请先选择付款账号');
                                } else {
                                    Common.toast('error', '提现申请提交失败，失败原因' + data.responseMsg)
                                }
                            },
                            error: function () {
                                Common.toast('error', '提现申请提交失败')
                            }
                        });

                    });
                    $dialog.find('.J_cancel').on('click', function () {
                        callback.close();
                    });
                }
            });

            d.show();
        }
    };

    Main.init();

});
