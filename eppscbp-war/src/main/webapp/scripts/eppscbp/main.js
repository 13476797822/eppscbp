require.config({
	baseUrl: 'scripts',
	paths: {
		'jquery': '/eppscbp/scripts/lib/jquery.1.7.2',
		valid: '/eppscbp/scripts/lib/validation/validate',
		tooltips: '/eppscbp/scripts/lib/tooltips/tooltips',
		dialog: '/eppscbp/scripts/lib/dialog/dialog',
		"select": '/eppscbp/scripts/lib/select',
		template: '/eppscbp/scripts/lib/template.min',
		ajaxfileupload: '/eppscbp/scripts/lib/upload/ajaxfileupload'
	},
	shim: {
		// 公共头尾需要的js
		valid: ['jquery'],
		tooltips: ['jquery'],
		dialog: ['jquery'],
		ajaxfileupload: ['jquery']
	}
});

require(['jquery', 'tooltips', 'valid', 'template', 'dialog', 'select', 'ajaxfileupload'], function ($, tooltips, valid, template) {

	// 单选框
	$(document).on("click", ".radio", function () {
		if ($(this).hasClass("disabled")) return false;
		$(this).addClass("active");
		$(this).siblings(".radio").removeClass("active");
		if ($(".full-account").hasClass("active")) {
			$(".full-money").show();
		} else {
			$(".full-money").hide();
		}
		// 是否人民币购汇
		if ($(".rmb-buy").hasClass("active")) {
			$(".ifAll").find("input").addClass("disabled").removeClass("active");
			$(".ifAll").find(".not-full-account").addClass("active").attr("checked", true);
			$("#curName").html("人民币");
			$("#fullAmt").hide();
			$("#fullAmt").parent().find("p.red-warn").remove();
		} else {
			$(".ifAll").find("input").removeClass("disabled");
			queryMerchantMeg();
		}
	});

	// 弹出提示框
	var $tips = $('.row_info .icon_tips_16');
	$tips.tooltips({
		width: 180,
		placement: 'right',
		arrowY: 15,
		position: {
			y: -15
		},
		content: function ($elem) {
			return $elem.html();
		}
	});

	//全额到账提示
	$(document).on("click", ".full-account", function () {
		if ($(this).hasClass("disabled")) {
			$(".not-full-account").attr("checked", true);
			return false;
		} else {
			if ($("#fullAmt").attr("data-type") == "N/A") {
				$("#fullAmt").parent().find("p.red-warn").remove();
				$("#fullAmt").parent().append("<p class='red-warn'>该币种的全额到账费用不存在</p>");
				$(".full-money").hide();
			} else {
				$("#fullAmt").parent().find("p.red-warn").remove();
			}
		}
	})
	$(document).on("click", ".not-full-account", function () {
		$("#fullAmt").parent().find("p.red-warn").remove();
	})

    $(document).on("click",".select-box",function(){
        return false;
    });
	// 表单验证
	$.validator.addMethod('upload', function (value, element) {
		var file = $('.input-item.upload-files .file-path').val();
		return $.trim(file).length > 0;
	}, '请选择上传文件');
	$.validator.addMethod("minNumber", function (value, element) {
		var returnVal = true;
		var inputZ = value;
		var ArrMen = inputZ.split(".");    // 截取字符串
		if (ArrMen.length == 2) {
			if (ArrMen[1].length > 2) {    // 判断小数点后面的字符串长度
				returnVal = false;
				return returnVal;
			}
		}
		return returnVal;
	}, "小数点后最多为两位");
	$.validator.addMethod('format', function (value, element) {
		var input = /^[\da-zA-Z\u4e00-\u9fa5 ]{1,100}$/;
		return input.test(value);
	}, '请输入正确格式');
	$.validator.addMethod('placeholder', function (value, element) {
		if (value == '收款方编码或收款方名称') {
			return false;
		}
		return true;
	}, '请输入收款方编码或名称');
	//校验输入交易附言格式
	$.validator.addMethod('remark', function (value, element) {
		var input = /^([a-z]|[A-Z]|[0-9]|[/\?\(\)\.\,\:\-\'\+\{\}\]]|[ ]){0,140}$/;
		return input.test(value);
	}, '交易附言只支持英文字母、英文符号和数字');
	//校验大于0的保留2位小数的正数   
	$.validator.addMethod('positive', function (value, element) {
		var input = /^\d+(\.\d{1,2})?$/;
		var inputZero = /^([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])$/;
		return input.test(value) && inputZero.test(value);
	}, '请输入正确的金额格式');
	//平台名称
	$.validator.addMethod('platformName', function (value, element) {
		var input = /^([a-z]|[A-Z]|[0-9]|[/\?\(\)\.\,\:\-\'\+\{\}\]]|[ ]){4,8}$|^[\u4e00-\u9fa5]{2,4}$/;
		return input.test(value);
	}, '请输入正确格式');
	var valid = $("#fillCross,#single-pay").validate({
		errorClass: 'tip-red',
		errorElement: 'p',
		rules: {
			payAmt: {
				required: true,
				positive: true
			}, remark: {
				required: true,
				remark: true
			}, detailAmount: {
				required: true,
				digits: true
			}, filePath: {
				upload: true
			}, payMes: {
				required: true,
				format: true
			}, platformName:{
				required: true,
				platformName:true
			}, logisticsType:{
                required: true
			}
		},
		messages: {
			payMes: {
				required: '请输入收款方编码或名称',
				format: '请输入正确格式',
				placeholder: '请输入收款方编码或名称'
			}, payAmt: {
				required: '请输入申请总金额',
				//                minNumber: '最多输入两位小数',
				positive: '请输入正确的格式'
			}, remark: {
				required: '请输入附言',
				remark: '请输入正确交易附言格式'
			}, detailAmount: {
				required: '请输入交易笔数',
				digits: '请输入正确的交易笔数'
			}, fileAddress: {
				upload: '请选择上传文件'
			}, platformName:{
				required: '请输入平台名称',
				platformName:'请输入正确格式'
			}, logisticsType: {
                required: '请选择国际物流进出境运输'
			}
		},
		errorPlacement: function (error, element) {
			// 报错信息放入元素最后
			error.appendTo(element.parent());
		}
	});

	// 提交订单
	$("#J_placeOrder").on("click", function () {
		if (!$(this).hasClass("disabled") && $(".input-list .input-item p.red-warn").length == 0 && valid.form()) {
			submitOrder();
		} else {
			return false;
		}
	})
	// 模板下载
	$(document).on("click", "#download", function () {
		download();
	})

	// 校验输入收款方信息
	$(document).on("blur", "#receiveName", function (event) {
		event.preventDefault();
		setTimeout(function () {
			if ($(".receive-name p.tip-red").css("display") == "block") {
				return false;
			}
			$(".receive-name p.red-warn").remove();
			queryMerchantMeg();
		}, 200);
	})

	//消除页面延时
	function ifPass() {
		if ($(".surplus-time").attr("data-time") > 0) {
			countDown();
		} else {
			$(".surplus").css("display", "none");
			$(".over-time").css("display", "block");
			var productType = $("#productType").val();
			var exchangeType = $("#exchangeType").val();
			if (!(productType == "RS01" || exchangeType == "01")) {
				$("#J_pay,#J_pay_confirm").addClass("disabled");
			}
		}
	}

	//风控查询
	function queryDetailFlag(businessNo, tryTimes) {
		$.ajax({
			type: "POST",
			data: { "businessNo": businessNo },
			url: sysconfig.ctx + "/singleQuery/singleOrderQuery/queryDetailFlag.htm",
			dataType: "json",
			success: function (data) {
				//若页面过期跳转至首页
				isIntercepted(data);				
				if (data.success) {
					$(".full-loading-bg").remove();
					$("#J_pay").show();
					$("#J_searchOrder").hide();
					$(".time-tip").show();
					$("#payStep").html("<span class='icon'>2</span>支付");
					$("#resultStep").show();
					$(".tip-text").text("提示：为保证订单有效时间内支付成功，仅支持余额支付，请先行充值");
					ifPass();
				} else if (tryTimes < 2) {
					setTimeout(function () {
						tryTimes++;
						queryDetailFlag(businessNo, tryTimes);
					}, 4000);
				} else{
					$(".full-loading-bg").remove();
				}
			},
			error: function (msg) {
				$(".full-loading-bg").remove();
			}
		});
	}
	function riskDetect() {
		if ($("#J_searchOrder").length > 0) {
			var businessNo = $("#businessNo").val();
			queryDetailFlag(businessNo, 0);
		} else {
			ifPass();
		}
	}
	riskDetect();

	// 去支付按钮事件
	$(document).on("click", "#J_pay", function () {
		if ($(this).hasClass("disabled")) return;
		submitPayment();
	});

	// 刷新汇率按钮事件
	$(document).on("click", "#J_refresh", function () {
		refreshRate();
	});

	// 提交支付按钮事件
	$(document).on("click", "#J_pay_confirm", function () {
		if ($(this).hasClass("disabled")) return;
		$(".red-warn").remove();
		var password = $("#password").val();
		if (password != null && password != "") {
			confirmPayment();
		} else {
			$("#J_pay_confirm").after("<i class='red-warn'>&nbsp;&nbsp;请先输入支付密码。</i>");
			return false;
		}

	});

	//文件上传前校验金额和明细笔数是否填写
	$(document).on("click", ".up", function () {
		if ($("#applyMoney").parent().find("p.tip-red").css("display") == "block" ||
			$("#detailAmount").parent().find("p.tip-red").css("display") == "block") {
			return false;
		}
		$("input[name='detailAmount']").parent().find("p.tip-red").remove();
		$("input[name='payAmt']").parent().find("p.tip-red").remove();
		var detailAmount = $("input[name='detailAmount']").val();
		var payAmt = $("input[name='payAmt']").val();
		if (detailAmount == "" && payAmt == "") {
			$("input[name='detailAmount']").parent().append("<p class='red-warn'>请先填写明细笔数</p>");
			$("input[name='payAmt']").parent().append("<p class='red-warn'>请先填写申请总金额</p>");
			return false;
		}
		if (detailAmount == "") {
			$("input[name='detailAmount']").parent().append("<p class='red-warn'>请先填写明细笔数</p>");
			return false;
		}
		if (payAmt == "") {
			$("input[name='payAmt']").parent().append("<p class='red-warn'>请先填写申请总金额</p>");
			return false;
		}
	});

	//申请总金额、交易笔数
	$(document).on("keyup", "#applyMoney,#detailAmount", function () {
		$(this).parents(".input-item").find("p.red-warn").remove();
		$("#file").val('');
		$(".result-text .fail,.result-text .success").hide();
	})
	//页面修改去除提交按钮错误提示
	$(document).on("change", "input,textarea", function () {
		$(".btn .red-warn").remove();
	})
	//当页面进行操作时，去除禁止提交
	$(document).on("click", "input,textarea", function () {
		if ($("#J_placeOrder").hasClass("disabled") && $(".result-text .fail").css("display") != "block") {
			$("#J_placeOrder").removeClass('disabled');
		}
	})
	// 文件上传
	$('.upload-files input[type=file]').on('change', function (e) {
		uploadFile();
	});

	function uploadFile() {
		$(".btn .red-warn").remove();
		$(".upload-files p.tip-red").remove();
		var html = '<div class="loading-box"><img src="' + sysconfig.ctx + '/style/images/submit-loading.gif" style="width:100%;height:100%;"><p>提交中...</p></div>';
		$("body").append(html);
		var fileAddress = $("input[name='file']").val();
		var detailAmount = $("input[name='detailAmount']").val();
		var payAmt = $("input[name='payAmt']").val();
		var requestUrl = $("input[name='requestUrl']").val();
		var curType = $("input[name='cur']").val();
		var exchangeType = $('input:radio[name="exchangeType"]:checked').val();
		$("#J_placeOrder").addClass("disabled");
		$.ajaxFileUpload({
			url: sysconfig.ctx + requestUrl,
			type: 'POST',
			fileElementId: 'file-btn',
			data: { detailAmount: detailAmount, payAmt: payAmt, curType: curType, exchangeType: exchangeType },
			dataType: 'text',
			success: function (data) {
				//若页面过期跳转至首页
				isIntercepted(data);

				var obj = eval('(' + data + ')');
				if (obj.success) {
					$('.upload-files').find('.file-path').val(fileAddress);
					$('.upload-files').find('.file-path[name="fileAddress"]').val(obj.fileAddress);
					if (obj.detailAmount != 0)
						$('.upload-files').find('.file-path[name="detailAmount"]').val(obj.detailAmount);
					$(".upload-files .result-text .success").show();
					$(".upload-files .result-text .fail").hide();
					if ($("#J_placeOrder").hasClass("disabled"))
						$("#J_placeOrder").removeClass('disabled');
				} else {
					$('.upload-files').find('.file-path').val(fileAddress);
					$(".upload-files .result-text .fail .err-text").html(obj.message);
					$(".upload-files .result-text .fail").show();
					$(".upload-files .result-text .success").hide();
				}
				$(".loading-box").remove();
			},
			complete: function (xmlHttpRequet) {
				$("input[name='file']").replaceWith('<input type="file" id="file-btn" name="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">');
				$("input[name='file']").on('change', function (e) {
					uploadFile();
				});
			},
			error: function (data) {
				$(".loading-box").remove();
			}
		});

	}

});
$(function () {
	var message = $("#receiveName").val();
	if (!(message == '' || message == undefined || message == null || message == '收款方编码或收款方名称')) {
		queryMerchantMeg();
	}
});
// 倒计时
function countDown() {
	$(".surplus-time").each(function () {

		var msTime = $(this).attr("data-time");
		// 分钟数
		var minTime = Math.floor(parseInt(msTime) / 60000);
		// 秒数
		var secTime = Math.floor((parseInt(msTime) % 60000) / 1000);
		if (minTime < 10) {
			minTime = "0" + minTime;
		}
		if (secTime < 10) {
			secTime = "0" + secTime;
		}
		$(".surplus-time").html(minTime + ":" + secTime);


		var timer = setInterval(function () {
			msTime = msTime - 1000;
			// 分钟数
			var minTime = Math.floor(parseInt(msTime) / 1000 / 60);
			// 秒数
			var secTime = Math.floor((parseInt(msTime) % 60000) / 1000);
			if (minTime < 10) {
				minTime = "0" + minTime;
			}
			if (secTime < 10) {
				secTime = "0" + secTime;
			}
			$(".surplus-time").html(minTime + ":" + secTime);
			$(".surplus-time").attr("data-time", msTime)
			if (msTime <= 0) {
				$(".surplus").hide();
				$(".over-time").show();
				$("#J_pay,#J_pay_confirm").addClass("disabled");
				clearInterval(timer);
			}
		}, 1000)

	})
}