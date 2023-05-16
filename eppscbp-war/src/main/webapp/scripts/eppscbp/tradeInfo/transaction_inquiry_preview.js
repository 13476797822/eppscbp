/**
 * ==========================================================
 * Copyright (c) 2015, suning.com All rights reserved.
 * 易付宝企业版 - 我的账户js
 * Author: luj
 * Date: 2015-08-18 16:30:00 897183
 * ==========================================================
 */
 if (require) require.config({
	baseUrl: 'scripts',
	paths: {
		jquery: '/eppscbp/scripts/lib/jquery.1.7.2',
		tabmin: '/eppscbp/scripts/lib/jiaoyi/ECode.tab.min',
		calendar:  '/eppscbp/scripts/lib/jiaoyi/ECode.calendar',
		dialog: '/eppscbp/scripts/lib/dialog/dialog-ie8'
	},
	shim: {
		valid: ['jquery'],
		bankSelect: ['jquery'],
		verCitySelect: ['jquery'],
		dialog: ['jquery']
	}
});

require(['jquery', 'tabmin', 'dialog'], function ($, abmin, dialog) {
	$("#J_busBtn_click p").toggle(function () {
		$("#J_busBtn_click").addClass("on");
		$("#J_busBtn_click").find(".bus-table-out").slideDown(300);
	}, function () {
		$("#J_busBtn_click").find(".bus-table-out").slideUp(300);
		$("#J_busBtn_click").removeClass("on");
	});

	var mailAry = [];
	var merchantName ="";
	var win;
	//发送电子版至邮箱
	$(".btnRight").click(function () {
		$.ajax({
			type: "get",
			url: sysconfig.ctx + "/tradeQueryAuth/tradeInfo/queryEmail.htm",
			data:{},
			dataType: "json",
			success: function(data) {
				if(data.success){
					//成功
					if(data.mail && data.mail != '' && data.mail != null && data.mail != 'null'){
						mailAry = data.mail.split(',');
					}
					if(data.payerMerchantName)merchantName = data.payerMerchantName;
				}
				opts = {
					title: '发送电子版至邮箱', // 标题
					content: $('#mailPop').html(),   // 内容
					width: 518,    // 宽度
					onShow: function ($dialog, callback) {
						$('.mail-input').on("keydown", function (e) {
							var e = e ? e : event;
							var k = e.keyCode || e.which;
							if (k == 8 && $(this).val() == '') {
								var $mark = $('#mailBox').children('.eidt-mark');
								if($mark.length > 0){
									$mark.eq($mark.length - 1).remove();
									initIptWidth();
								}
							}
						});
						if(mailAry.length > 0){
							$('#errorTip').hide();
							var str = '';
							for (var i = 0; i < mailAry.length; i++) {
								if (mailAry[i]) {
									str += '<div class="eidt-mark"><span>'+ mailAry[i] +'</span><i class="icon-closemark"></i></div>';
								}
							}
							$(".ipt-box").before(str);
							setTimeout(function(){
								var mailmark = $('#mailBox').children('.eidt-mark');
								var maxwidth = $('#mailBox').width();
								var left = 0;
								mailmark.each(function(index,item){
									left += $(item).width();
									if(left > maxwidth){
										left = $(item).width();
									}
								});
								var right = maxwidth - left - 4;
								if(right < 150){
									right ='100%';
								}
								$('.ipt-box').width(right);
								$('#mailBox .icon-closemark').click(function(event){
									$(this).parents('.eidt-mark').remove();
									initIptWidth();
									event.preventDefault(); 
								});
							},10);
						}
						//点击确定
						$("#btnOk1").click(function(){
							if($("#mailBox span").length <= 0 && $('.mail-input').val() == ''){
								$('#errorTip').show();
								$('#errorTip').text('请填写正确的邮箱地址');
								return;
							}
							if($('.mail-input').val()){
								if(!check($('.mail-input').val())){
									$('#errorTip').show();
									$('#errorTip').text('最后一项为无效邮箱地址，请修改后重新提交');
									return;
								}
							}
							var mailmark = $('#mailBox').children('.eidt-mark');
							var mailList = [];
							mailmark.each(function(index,item){
								if($(item).children('span').text()){
									mailList.push($(item).children('span').text())
								}
							});
							if($('.mail-input').val()){
								mailList.push($('.mail-input').val())
							}
							// console.log(mailList.join(','))
							 var params = {
								recipientEmailAddress: mailList.join(','),
								merchantName:merchantName,
								serialNumber:$('#serialNumber').val(),
								fileAddress:$('#fileAddress').val(),
							}
							$.ajax({
								type: "get",
								url:  sysconfig.ctx + "/tradeQueryAuth/tradeInfo/sendEmail.htm",
								data:params,
								dataType: "json",
								success: function (data) {
									$("#inputArea").hide();
									if(data.success && data.emailAddress){
										$("#infoAreaSuccess").show();
										$("#infoAreaSuccess").find('.infoContent').html('电子回单已发送至' + data.emailAddress + ',请及时查收。');
									}
									else{
										$("#infoAreaFail").show();
									}
								},
								error : function(data) {
									$("#infoMailBox").hide();
									$("#infoAreaFail").show();
								}
							});
						});
						$(".btnCancel,#btnOk2,#btnOk3").click(function(){
							win.close();
						});
						$('#mailBox').click(function(){
							$('.mail-input').focus();
						})
						$('.mail-input').on('input',function (event) {
							var mail = $(this).val();
							if($('#mailBox').children('.eidt-mark').length == 5){
								$(this).val('');
								return false;
							}
							if(mail !== '' && mail.indexOf(';') >= 0){
								mail = mail.slice(0,mail.length-1);
								if(check(mail)){
									$(this).parents('.ipt-box').before('<div class="eidt-mark"><span>'+ mail +'</span><i class="icon-closemark"></i></div>');
									$(this).val('');
									$('#errorTip').hide();
									setTimeout(function(){
										initIptWidth();
										$('#mailBox .icon-closemark').click(function(event){
											$(this).parents('.eidt-mark').remove();
											initIptWidth();
											event.preventDefault();
										})
									},10);
								}
								else{
									$(this).val('');
								}
							}
						});
					},  // 显示回调
					showClose: true
				}
				win = $.dialog(opts);
				win.show();

			},
			error: function(msg) {

			}
		});
	});
	function initIptWidth(){
		var mailmark = $('#mailBox').children('.eidt-mark');
		var maxwidth = $('#mailBox').width();
		var left = 0;
		mailmark.each(function(index,item){
			left += $(item).width();
			if(left > maxwidth){
				left = $(item).width();
			}
		});
		var right = maxwidth - left - 4;
		if(right < 150){
			right ='100%';
		}
		$('.ipt-box').width(right);
	}
	//验证邮箱格式
	function check(mailTxt) {
		var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
		if (mailTxt === "") { //输入不能为空
			return false;
		} else if (!reg.test(mailTxt)) { //正则验证不通过，格式不对
			return false;
		} else {
			return true;
		}
	}


	$('[data-href]').click(function(){
		if($(this).attr('data-href')){
			window.location.href = $(this).attr('data-href');
		}
	})
	
});
