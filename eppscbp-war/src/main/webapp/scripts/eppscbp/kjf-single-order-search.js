require.config({
	baseUrl: '/eppscbp/scripts/lib/',
	paths: {
		'jquery': 'jquery.1.7.2',
		"select": 'select',
		'calendar': 'calendar/ECode.calendar',
		ajaxfileupload: 'upload/ajaxfileupload',
		dialog: 'dialog/myDialog',//myDialog
		pagination: 'pagination/paginationLocal',//20210319分页新增
		template: 'template.min'
	},
	shim: {
		// 公共头尾需要的js
		'ECode.calendar': ['jquery'],
		dialog: ['jquery'],
		ajaxfileupload: ['jquery'],
	}
});

require(['jquery', 'select', 'calendar', 'dialog', 'pagination', 'ajaxfileupload', 'template'], function ($,select,calendar,dialog, pagination) {

	dateFirstInit();

	function dateFirstInit() {
		dateInit('date1');
		dateInit('date2');
	}

	function dateInit(date) {
		var mindate;
		var maxdate;
		var other;
		//日历控件bug.火狐及低版本ie浏览器对日期格式为XX-XX-XX解析不了,替换为XX/XX/XX可正常显示
		if (date == "date1") {
			if ($("#date1").val() == "") {
				mindate = '1981/12/31';
			} else {
				mindate = $("#date1").val().replace(new RegExp(/-/gm), "/");
			}
			maxdate = addDays(new Date(), 0).replace(new RegExp(/-/gm), "/");
			other = "date2"
		} else {
			// 开始日期在结束日期的前90天
			if ($("#date2").val() == "") {
				maxdate = new Date();
				mindate = '1981/12/31';
			} else {
				maxdate = $("#date2").val().replace(new RegExp(/-/gm), "/");
				mindate = addDays(new Date(maxdate), -90).replace(new RegExp(/-/gm), "/");
			}
			other = "date1";
		}
		ECode.calendar({
			inputBox: "#" + other,
			count: 1,
			flag: false,
			isWeek: false,
			isTime: false,
			range: {
				mindate: mindate,
				maxdate: maxdate
			},
			callback: function () {
				dateInit(other);
			}
		});
	}

	function addDays(date, days) {
		var nd = new Date(date);
		nd = nd.valueOf();
		nd = nd + days * 24 * 60 * 60 * 1000;
		nd = new Date(nd);
		var y = nd.getFullYear();
		var m = nd.getMonth() + 1;
		var d = nd.getDate();
		if (m <= 9) m = "0" + m;
		if (d <= 9) d = "0" + d;
		var cdate = y + "-" + m + "-" + d;

		return cdate;
	}

	/*重置*/
	$(document).on("click", ".reset", function () {
		$(this).parents(".condition-list").find("input").val("");
		$(this).parents(".condition-list").find(".select-input").val("");
		dateFirstInit();
	})


	//点击上传按钮事件
	$(document).on("click", ".upload-btn", function () {
		var html = $("#upload-tpl").html();
		var url = $("#uploadUrlHid").val();
		var win = $.dialog({
			title: "货物贸易监管信息文件上传",
			content: html,
			width: 520,
			blur: false,
			afterShow: function ($dialog, callback) {
				//保存按钮
				$("#J_placeOrder").click(function () {
					if ($("#J_placeOrder").hasClass("disabled")) {
						return false;
					}
					var requestDto = $('#f4').serialize();
					$.ajax({
						type: "POST",
						dataType: "JSON",
						url: sysconfig.ctx + url,
						data: requestDto,
						success: function (data) {
							//若页面过期跳转至首页
							isIntercepted(data);

							if (data.success == "s") {
								$(".upload-files .result-text .success").show();
								$(".upload-files .result-text .fail").hide();
								$(".upload-files .result-text .wait").hide();
								callback.close();
								submitQuery();
							} else {
								$(".upload-files .result-text .fail .err-text").html(data.responseMsg);
								$(".upload-files .result-text .fail").show();
								$(".upload-files .result-text .success").hide();
								$(".upload-files .result-text .wait").hide();
							}
						},
						error: function () {
							$(".upload-files .result-text .fail .err-text").html("系统错误");
							$(".upload-files .result-text .fail").show();
							$(".upload-files .result-text .success").hide();
							$(".upload-files .result-text .wait").hide();
						}
					});
				})

				//取消按钮
				$(".cancel-btn").click(function () {
					callback.close();
				})

			},
			showClose: true
		})
		win.show();
		//业务单号赋值
		$("#businessNoHid").val($(this).parents("tr").find(".detail").html());
	});


	//文件上传校验
	$(document).on("change", ".upload-files input[type=file]", function () {
		var fileAddress = $(this).val();
		if (fileAddress == '' || fileAddress == undefined || fileAddress == null) {
			return false;
		}
		$("#J_placeOrder").addClass("disabled");
		$.ajaxFileUpload({
			url: sysconfig.ctx + "/singleQuery/singleOrderQuery/fileCheck.htm",
			type: 'POST',
			fileElementId: 'file-btn',
			dataType: 'text',
			success: function (data) {
				//若页面过期跳转至首页
				isIntercepted(data);

				var obj = eval('(' + data + ')');
				if (obj.success) {
					$('.upload-files').find('.file-path').val(fileAddress);
					$('.upload-files').find('.file-path[name="fileAddress"]').val(obj.fileAddress);
					$("#uploadSuccess").show();
					$("#uploadFail").hide();
					$("#wait").hide();
					if ($("#J_placeOrder").hasClass("disabled"))
						$("#J_placeOrder").removeClass('disabled');
				} else {
					$('.upload-files').find('.file-path').val(fileAddress);
					$("#uploadFail .err-text").html(obj.message);
					$("#uploadFail").show();
					$("#uploadSuccess").hide();
					$("#wait").hide();
				}
			},
			complete: function (xmlHttpRequet) {
				$("input[name='file']").replaceWith('<input type="file" id="file-btn" name="file">');
			},
			error: function (data) { }
		});
	});


	//////////////批付文件

	//点击导入按钮事件
	$(document).on("click", "#upload-bp-btn", function () {
		var html = $("#bp-upload-tpl").html();
		var url = "/singleQuery/collAndSettleQuery/bpOrdersParseAndCalculate.htm";
		var win = $.dialog({
			title: "批付信息文件上传",
			content: html,
			width: 520,
			blur: false,
			afterShow: function ($dialog, callback) {
				//保存按钮
				$("#BP_placeOrder").click(function () {
					if ($("#BP_placeOrder").hasClass("disabled")) {
						return false;
					}
					
					var requestDto = $('#f5').serialize();
					
					$.ajax({
						type: "POST",
						dataType: "JSON",
						url: sysconfig.ctx + url,
						data: requestDto,
						success: function (data) {
							//若页面过期跳转至首页
							isIntercepted(data);
							var bpBusinessNo = $("#bpBusinessNo").val();

							if (data.success == "s") {
								$(".bp-upload-files .result-text .success").hide();
								$(".bp-upload-files .result-text .fail").hide();
								$(".bp-upload-files .result-text .wait").hide();

								callback.close();
								//渲染表格底色
								var flag = 0;
								for (var i = 0; i < data.resultList.length; i++) {
									if (i > 0) {
										if (data.resultList[i].payeeMerchantName != data.resultList[i - 1].payeeMerchantName) {
											flag++;
										}
									}
									data.resultList[i].trIndex = flag;
								}
								//$("#fixed-table-result span[data-num=" + bpBusinessNo + "]").click();
								
								//显示批付详情弹窗
								var businessNo = data.businessNo;
								var fileAddress = data.fileAddress;
								var html = template('pf-dialog-tpl', data);
								var d = $.dialog({
									title: '批付信息确认',
									content: html,
									width: 560,
									showClose: true,
									blur: false,
									css: {
										top: "80px"
									},
									afterShow: function ($dialog, callback) {
										var chunk = 10;
										var len = data.resultList.length;
										var result = [];
										for (var i = 0; i < len; i += chunk) {
											result.push(data.resultList.slice(i, i + chunk)) // 每10项分成一组        
										} 
										var allData = {
											success: true,
											totalCount: len,
											result: result
										}
										var params = {
											allData: allData,
											$listWrap: $("#alertTable"),
											// 模版 id
											tpls: {
												list: "table-result-tpl",
												wait: "wait-tpl"
											},
							
											// 渲染数据之前处理数据的工作
											beforeRender: function(data) {
												// 处理后台返回数据
												isIntercepted(data);
												return data;
											},
							
											// 渲染表格之后的操作
											afterRender: function() {
												
											},
							
											// 注册自定义搜索
											registerCustomSearch: function(query) {

											}
										};
										new pagination(params);

										//批付信息确认--提交
										$dialog.find('#submit').click(function () {
											if ($("#submit").hasClass("disabled")){
												return false;
											}
											$("#submit").addClass("disabled");
											//拼装请求参数
											var req = {};
											req.fileAddress = fileAddress;
											req.businessNo = businessNo;
											//ajax请求后台
											$.ajax({
												type: "POST",
												url: sysconfig.ctx + "/singleQuery/collAndSettleQuery/batchPaymentOrderFileSubmit.htm",
												data: JSON.stringify(req),
												contentType: 'application/json;charset=utf-8', //设置请求头信息
												dataType: "json",
												success: function (data) {
													//若页面过期跳转至首页
													isIntercepted(data);
													$('.msg-box').html("");
													if (data.success == 's') {
													    callback.close();
														//成功结果提示弹窗
														var html = template('pf-result-tpl', data);
														var d = $.dialog({
															title: '提示',
															content: html,
															width: 510,
															showClose: true,
															blur: false,
															afterShow: function ($dialog, callback) {
																$dialog.find('.yes-btn').click(function () {
																	callback.close();

																	var bpBusinessNo = $("#bpBusNo").val();
																	$("#fixed-table-result span[data-num=" + bpBusinessNo + "]").click();
																});
															},
															afterClose: function () {
																var bpBusinessNo = $("#bpBusinessNo").val();
																$("#fixed-table-result span[data-num=" + bpBusinessNo + "]").click();
															}
														});
														d.show();
													} else {
														if(null == data.responseMsg){
															var html = "<i></i>" + "系统超时，请联系运营人员";
															$('.msg-box').html(html);	
														}else{
															var html = "<i></i>" + data.responseMsg;
															$('.msg-box').html(html);
														}

													}
												},
												error: function (msg) {

												}
											});
										});
										//批付信息确认--关闭
										$dialog.find('#close').click(function () {
											callback.close();
										});
									},
									afterClose: function () {

									}

								});
								d.show();
									//显示确认提示窗
							} else {
								$(".bp-upload-files .result-text .fail .err-text").html(data.responseMsg);
								//$("#bpUploadFail").show();
								$(".bp-upload-files .result-text .fail").show();
								$(".bp-upload-files .result-text .success").hide();
								$(".bp-upload-files .result-text .wait").hide();
							}
						},
						error: function () {
							$(".bp-upload-files .result-text .fail .err-text").html("系统错误");
							$(".bp-upload-files .result-text .fail").show();
							$(".bp-upload-files .result-text .success").hide();
							$(".bp-upload-files .result-text .wait").hide();
						}
					});
				})

				//取消按钮
				$(".cancel-btn").click(function () {
					callback.close();
				})

			},
			showClose: true
		})
		win.show();
		//业务单号赋值
		//var bus=$(this).parents("tr").find(".detail").html();
		var bpBusNo = $("#bpBusNo").val();
		$("#bpBusinessNo").val(bpBusNo);
		var prePayAmount = $("#prePayAmount").val();
		$("#bpPrePayAmount").val(prePayAmount);
        var prePayAmount = $("#prePayAmount").val();
        $("#bpPrePayAmount").val(prePayAmount);
	});


	//批付文件上传校验
	$(document).on("change", ".bp-upload-files input[type=file]", function () {
		var fileAddress = $(this).val();
		if (fileAddress == '' || fileAddress == undefined || fileAddress == null) {
			return false;
		}
		$("#BP_placeOrder").addClass("disabled");
		var prePayAmount = $("#bpPrePayAmount").val();

		
		$.ajaxFileUpload({
			url: sysconfig.ctx + "/singleQuery/collAndSettleQuery/bpFileUpload.htm",
			type: 'POST',
			fileElementId: 'bp-file-btn',
			data: { prePayAmount: prePayAmount },
			dataType: 'text',
			success: function (data) {
				//若页面过期跳转至首页
				isIntercepted(data);

				var obj = eval('(' + data + ')');
				if (obj.success) {
					$("#bpFile").val(fileAddress);
					$("#bpFileAddress").val(obj.fileAddress);
					$("#bpUploadSuccess").show();
					$("#bpUploadFail").hide();
					$("#bpWait").hide();
					if ($("#BP_placeOrder").hasClass("disabled"))
						$("#BP_placeOrder").removeClass('disabled');
				} else {
					var bp = $("#bpFile");
					$("#bpFile").val(fileAddress);
					$("#bpUploadFail .err-text").html(obj.message);
					$("#bpUploadFail").show();
					$("#bpUploadSuccess").hide();
					$("#bpWait").hide();
				}
			},
			complete: function (xmlHttpRequet) {
				$("input[name='bpFile']").replaceWith('<input type="file" id="bp-file-btn" name="file">');
			},
			error: function (data) { }
		});
	});





	// 去支付
	$(document).on("click", ".gopay", function () {
		if ($(this).hasClass("disabled")) return;
		var businessNo = $(this).attr("data-num");
		var bizType = $(this).attr("data-biz-type");
		var productType = $(this).attr("data-product-type");
		var url = sysconfig.ctx + "/singleQuery/singleOrderQuery/toPay.htm?" + "businessNo=" + businessNo + "&bizType=" + bizType + "&productType=" + productType;
		window.location.href = url;
	});

	// 关闭订单
	$(document).on("click", ".goclose", function () {
		if ($(this).hasClass("disabled")) return;
		var businessNo = $(this).attr("data-num");
		var win = $.dialog({
			title: "确认关闭订单",
			content: "<p style='text-align:center;padding:20px 0;font-size: 16px;'>确认关闭订单？</p>" +
				"<input type='button' value = '确认' class='submit-btn' id='close-btn' style='margin-left:83px;'/>",
			width: 300,
			blur: false,
			afterShow: function ($dialog, callback) {
				$("#close-btn").click(function () {
					$.get(sysconfig.ctx + "/singleQuery/singleOrderQuery/toClose.htm", {
						businessNo: businessNo
					}, function (data) {
						//若页面过期跳转至首页
						isIntercepted(data);

						if (data.success) {
							callback.close();
							submitQuery();
						} else {
							var error = $.dialog({
								title: "关闭失败",
								content: "<p style='text-align:center;padding:20px 0;font-size: 16px;'>" + data.message + "</p>" +
									"<input type='button' value = '关闭' class='submit-btn' id='close-btn1' style='margin-left:83px;'/>",
								width: 300,
								blur: false,
								afterShow: function ($dialog, callback) {
									$("#close-btn1").click(function () {
										callback.close();
									})
								},
								showClose: true
							});
							callback.close();
							error.show();
						}
					});
				})
			},
			showClose: true
		});
		win.show();
	});


	// 关闭收结汇订单
	$(document).on("click", ".goclose-cs", function () {
		if ($(this).hasClass("disabled")) return;
		var orderNo = $(this).attr("data-num");
		var win = $.dialog({
			title: "确认关闭订单",
			content: "<p style='text-align:center;padding:20px 0;font-size: 16px;'>确认关闭订单？</p>" +
				"<input type='button' value = '确认' class='submit-btn' id='close-btn' style='margin-left:83px;'/>",
			width: 300,
			blur: false,
			afterShow: function ($dialog, callback) {
				$("#close-btn").click(function () {
					$.get(sysconfig.ctx + "/singleQuery/collAndSettleQuery/toClose.htm", {
						orderNo: orderNo
					}, function (data) {
						//若页面过期跳转至首页
						isIntercepted(data);

						if (data.success) {
							callback.close();
							submitQuery();
						} else {
							var error = $.dialog({
								title: "关闭失败",
								content: "<p style='text-align:center;padding:20px 0;font-size: 16px;'>" + data.message + "</p>" +
									"<input type='button' value = '关闭' class='submit-btn' id='close-btn1' style='margin-left:83px;'/>",
								width: 300,
								blur: false,
								afterShow: function ($dialog, callback) {
									$("#close-btn1").click(function () {
										callback.close();
									})
								},
								showClose: true
							});
							callback.close();
							error.show();
						}
					});
				})
			},
			showClose: true
		});
		win.show();
	});

	$('.sel-val').click(function () {
		$(this).parents('.select-box').siblings('.tips').hide();
	});


	var len = $('.inp-wrap').length;
	for (var i = len; i > 0; i--) {
		$('.inp-wrap:eq(' + (len - i) + ')').css({
			'zIndex': i
		});
	}

	var wait = 30;
	function time(obj) {
		if (wait == 0) {
			obj.className = 'query-btn mr20';
			obj.removeAttribute("disabled");
			obj.text = "查询处理结果";
			wait = 30;
		} else {
			obj.className = 'query-btn mr20 disabled';//按钮变灰，不可点击
			obj.setAttribute("disabled", true);
			obj.text = "重新查询(" + wait + ")";
			wait--;
			setTimeout(function () {
				time(obj)
			},
				1000)
		}
	}

	function verifyAmout(amount, number) {
		if (amount == 0 && number != 0) {
			var d = $.dialog({
				title: '提示',
				content: "<p style='text-align:center;padding:20px 0;font-size: 16px;'>金额不为0,笔数不能为0</p>" +
					"<input type='button' value = '关闭' class='submit-btn' id='close-btn2' style='margin-left:75px;'/>",
				width: 280,
				showClose: true,
				blur: false,
				afterShow: function ($dialog, callback) {
					$("#close-btn2").click(function () {
						callback.close();
					})
				},
				showClose: true
			});
			d.show();
			return true;
		}
		return false;
	}

	//批付详情
	$(document).on('click', '.batchpay', function () {
		var businessNo = $(this).attr("data-num");
		document.getElementById("bpBusNo").value = businessNo;
		$.ajax({
			type: "GET",
			url: sysconfig.ctx + "/singleQuery/collAndSettleQuery/ordersQuery.htm",
			data: { businessNo: businessNo },
			dataType: "json",
			success: function (data) {
				//若页面过期跳转至首页
				isIntercepted(data);
				if (data.success) {
					$('.msg-box').html("");
					$('.info-content').show();
					$('.pf-btn-wrap').show();
					$('.pf-btn-wrap2').hide();
					$('.back-btn').click(function () {
						$('.info-content').hide();
						$('.pfxq-wrap').html("");
					});
					if (data.result) {

						document.getElementById("prePayAmount").value = data.prePayAmount;


						$("#prePayAmount").parent().find(".head-amount-content").remove();
						$("#prePayAmount").parent().prepend("<p class='head-amount-content'>待批付金额：<span class='num'></span>" + data.prePayAmount + "元</p>");

						var html = template('info-tpl', data);
						$('.pfxq-wrap').html(html);
						//点击批付按钮
						$('.yes-btn').off('click');
						$(".bankcard-inp").keyup(function () {
							if (this.value != "" && !this.value.match(/^($|0|[1-9]\d*)($|\.(\d{1,2})?$)/)) this.value = this.getAttribute("t_value"); else this.setAttribute("t_value", this.value); if (this.value.match(/^(0|[1-9]\d*)($|\.\d{1,2}$)/)) this.setAttribute("o_value", this.value);
						});
						$(".bankcard-inp").keypress(function () {
							if (this.value != "" && !this.value.match(/^($|0|[1-9]\d*)($|\.(\d{1,2})?$)/)) this.value = this.getAttribute("t_value"); else this.setAttribute("t_value", this.value); if (this.value.match(/^(0|[1-9]\d*)($|\.\d{1,2}$)/)) this.setAttribute("o_value", this.value);
						});
						$(".bankcard-inp").blur(function () {
							if (!this.value.match(/^(0|[1-9]\d*)($|\.\d{1,2}$)/)) this.value = this.getAttribute("o_value"); else { if (this.value.match(/^\.\d+$/)) this.value = 0 + this.value; if (this.value.match(/^\.$/)) this.value = 0; this.setAttribute("o_value", this.value) };
							if (this.value == 0) {
								//$(this).closest("tr").find("input[name='number']").attr('disabled','disabled');
								//$(this).closest("tr").find("input[name='number']").addClass('disabled');
								//$(this).closest("tr").find("input[name='number']").val(1);
							} else if ($(this).closest("tr").find("input[name='number']").attr('disabled') == 'disabled') {
								//$(this).closest("tr").find("input[name='number']").removeAttr('disabled');
								//$(this).closest("tr").find("input[name='number']").removeClass('disabled');
							}
						});
						$(".number-inp").keyup(function () {
							if (this.value != "" && !this.value.match(/^(0{1,1}|[1-9]\d*)$/)) this.value = this.getAttribute("t_value"); else this.setAttribute("t_value", this.value); if (this.value.match(/^(0{1,1}|[1-9]\d*)$/)) this.setAttribute("o_value", this.value);
						});
						$(".number-inp").keypress(function () {
							if (this.value != "" && !this.value.match(/^(0{1,1}|[1-9]\d*)$/)) this.value = this.getAttribute("t_value"); else this.setAttribute("t_value", this.value); if (this.value.match(/^(0{1,1}|[1-9]\d*)$/)) this.setAttribute("o_value", this.value);
						});

						$(".number-inp").blur(function() {
							if(verifyAmout(this.value, $(this).closest("tr").find("input[name='amount']").val())){
								this.value = 1;
							}
							if (this.value > 1000) {
								this.value = 1000;
								var d = $.dialog({
									title: '提示',
									content: "<p style='text-align:center;padding:20px 0;font-size: 16px;'>笔数不能超过1000笔</p>" +
										"<input type='button' value = '关闭' class='submit-btn' id='close-btn3' style='margin-left:75px;'/>",
									width: 280,
									showClose: true,
									blur: false,
									afterShow: function ($dialog, callback) {
										$("#close-btn3").click(function () {
											callback.close();
										})
									},
									showClose: true
								});
								d.show();
							}
						});

						$(".bankcard-inp").blur(function() {
							if(verifyAmout($(this).closest("tr").find("input[name='number']").val(), this.value)){
								$(this).closest("tr").find("input[name='number']").val(1);
							}					
						});


						$(".ordername-inp").keyup(function () {
							if (this.value.length > 32) {
								this.value = this.getAttribute("t_value");
							} else {
								this.setAttribute("t_value", this.value);
							}
						});
						$(".ordername-inp").keypress(function () {
							if (this.value.length > 32) {
								this.value = this.getAttribute("t_value");
							} else {
								this.setAttribute("t_value", this.value);
							}
						});
						$(".ordername-inp").blur(function () {
							if (this.value.length > 32) {
								this.value = this.getAttribute("t_value");
							} else {
								this.setAttribute("t_value", this.value);
							}
						});


						$('.yes-btn').on('click', function () {
							//$(".msg-box").eq(i).remove()
							// 进行分笔计算
							var calDetailArray = new Array();
							var i = 0;
							$(".bankcard-inp").each(function () {
								var amount = $(this).val();
								var number = $(this).closest("tr").find("input[name='number']").val();
								var orderName = $(this).closest("tr").find("input[name='orderName']").val();

								calDetailArray.push({ payeeMerchantCode: data.resultList[i].payeeMerchantCode, payeeMerchantName: data.resultList[i].payeeMerchantName, bankAccountNo: data.resultList[i].bankAccountNo, amount: amount, number: number, orderName: orderName });
								i++;
							});
							var calReq = {};
							calReq.prePayAmount = $("#prePayAmount").val();
							calReq.amountCount = "";
							calReq.numberCount = "";
							calReq.details = calDetailArray;


							$.ajax({
								type: "POST",
								url: sysconfig.ctx + "/singleQuery/collAndSettleQuery/ordersCalculate.htm",
								data: JSON.stringify(calReq),
								contentType: 'application/json;charset=utf-8', //设置请求头信息
								dataType: "json",
								success: function (data) {
									//若页面过期跳转至首页
									isIntercepted(data);
									if (data.success == 's') {
										//读取数据

										//渲染表格底色
										var detailArray = new Array();
										var flag = 0;
										for (var i = 0; i < data.resultList.length; i++) {
											if (i > 0) {
												if (data.resultList[i].payeeMerchantName != data.resultList[i - 1].payeeMerchantName) {
													flag++;
												}
											}
											data.resultList[i].trIndex = flag;
											detailArray.push({ payeeMerchantCode: data.resultList[i].payeeMerchantCode, amount: data.resultList[i].amount, orderName: data.resultList[i].orderName });
										}
										var html = template('pf-dialog-tpl', data);
										var d = $.dialog({
											title: '批付信息确认',
											content: html,
											width: 650,
											showClose: true,
											blur: false,
											css: {
												top: '80px'
											},
											afterShow: function ($dialog, callback) {
												var chunk = 10;
												var len = data.resultList.length;
												var result = [];
												for (var i = 0; i < len; i += chunk) {
													result.push(data.resultList.slice(i, i + chunk)) // 每10项分成一组        
												} 
												var allData = {
													success: true,
													totalCount: len,
													result: result
												}
												var params = {
													allData: allData,
													$listWrap: $("#alertTable"),
													// 模版 id
													tpls: {
														list: "table-result-tpl",
														wait: "wait-tpl"
													},
									
													// 渲染数据之前处理数据的工作
													beforeRender: function(data) {
														// 处理后台返回数据
														isIntercepted(data);
														return data;
													},
									
													// 渲染表格之后的操作
													afterRender: function() {
														
													},
									
													// 注册自定义搜索
													registerCustomSearch: function(query) {

													}
												};
												new pagination(params);

												//批付信息确认--提交
												$dialog.find('#submit').click(function () {
													if ($("#submit").hasClass("disabled")){
														return false;
													}
													$("#submit").addClass("disabled");
													//$('#submit').hide();
													//拼装请求参数
													var req = {};
													req.platformCode = "";
													req.payerAccount = "";
													req.businessNo = businessNo;
													req.details = detailArray;
													//ajax请求后台
													$.ajax({
														type: "POST",
														url: sysconfig.ctx + "/singleQuery/collAndSettleQuery/ordersAudit.htm",
														data: JSON.stringify(req),
														contentType: 'application/json;charset=utf-8', //设置请求头信息
														dataType: "json",
														success: function (data) {
															//若页面过期跳转至首页
															isIntercepted(data);
															$('.msg-box').html("");
															if (data.success == 's') {
																callback.close();
																//成功结果提示弹窗
																var html = template('pf-result-tpl', data);
																var d = $.dialog({
																	title: '提示',
																	content: html,
																	width: 510,
																	showClose: true,
																	blur: false,
																	afterShow: function ($dialog, callback) {
																		$dialog.find('.yes-btn').click(function () {
																			callback.close();

																			var bpBusinessNo = $("#bpBusNo").val();
																			$("#fixed-table-result span[data-num=" + bpBusinessNo + "]").click();
																		});
																	},
																	afterClose: function () {
																		var bpBusinessNo = $("#bpBusinessNo").val();
																		$("#fixed-table-result span[data-num=" + bpBusinessNo + "]").click();
																	}
																});
																d.show();
															} else {
																var html = "<i></i>" + data.responseMsg;
																$('.msg-box').html(html);

															}
														},
														error: function (msg) {

														}
													});
												});
												//批付信息确认--关闭
												$dialog.find('#close').click(function () {
													callback.close();
												});
												
											},	
											afterClose: function () {

											}

										});
										d.show();
									} else {
										//var html = "<i></i>" + data.responseMsg;
										//$('.msg-box').html(html);

										var win = $.dialog({
											title: "",
											content: "<p class='add-fail'><i></i>核算失败:" + data.responseMsg + "</p>",
											width: 114,
											showClose: true,
											blur: false,
											afterShow: function ($dialog, callback) {
												setTimeout(function () {
													callback.close();
												}, 3000)
											},
											showClose: true,
											//maskCss: { // 遮罩层背景
											//opacity: 0
											//}

										})
										win.show();
									}
								},
								error: function (msg) {

								}
							});
						});
						$('.clear-btn').off('click');
						$('.clear-btn').on('click', function () {
							$(".bankcard-inp").val(0);
						});
					} else {
						//渲染表格底色
						var flag = 0;
						for (var i = 0; i < data.resultList.length; i++) {
							if (i > 0) {
								if (data.resultList[i].payeeMerchantName != data.resultList[i - 1].payeeMerchantName) {
									flag++;
								}
							}
							data.resultList[i].trIndex = flag;
						}
						var html = template('pf-result-form-tpl', data);
						$('.pfxq-wrap').html(html);
						$('.pf-btn-wrap').hide();
						$('.pf-btn-wrap2').show();
						$('.query-btn').off('click');
						$('.query-btn').click(function () {
							time(this);
							$.ajax({
								type: "GET",
								url: sysconfig.ctx + "/singleQuery/collAndSettleQuery/resultQuery.htm",
								data: { businessNo: businessNo },
								dataType: "json",
								success: function (data) {
									//若页面过期跳转至首页
									isIntercepted(data);
									$('.msg-box').html("");
									if (data.success == 's') {
										var html = template('pf-result-form-tpl', data);
										$('.pfxq-wrap').html(html);
									} else {
										var html = "<i></i>" + data.responseMsg;
										$('.msg-box').html(html);
									}
								},
								error: function (msg) {

								}
							});
						})
					}
				}
			},
			error: function (msg) {

			}
		});
	});
});