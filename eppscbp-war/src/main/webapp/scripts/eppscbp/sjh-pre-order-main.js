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

	//点击上传按钮事件
	$(document).on("click", "#uploadDetail", function () {
		var html = $("#upload-tpl").html();
		var url = $("#uploadUrlHid").val();
		var win = $.dialog({
			title: "订单明细文件上传",
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
		$("#dialogSelect").initSelect()
	});


	//文件上传校验
	$(document).on("change", ".upload-files input[type=file]", function () {
		var fileAddress = $(this).val();
		if (fileAddress == '' || fileAddress == undefined || fileAddress == null) {
			return false;
		}
		$("#J_placeOrder").addClass("disabled");
		$.ajaxFileUpload({
			url: sysconfig.ctx + "/preOrderCollAndSettle/preUploadFile.htm",
			type: 'POST',
			fileElementId: 'file-btn',
			dataType: 'json',
			success: function (data) {
				//若页面过期跳转至首页
				isIntercepted(data);

				if (data.success) {
					$('.upload-files').find('.file-path').val(fileAddress);
					$('.upload-files').find('.file-path[name="fileAddress"]').val(data.fileAddress);
					$("#uploadSuccess").show();
					$("#uploadFail").hide();
					$("#wait").hide();
					if ($("#J_placeOrder").hasClass("disabled"))
						$("#J_placeOrder").removeClass('disabled');
				} else {
					$('.upload-files').find('.file-path').val(fileAddress);
					$("#uploadFail .err-text").html(data.message);
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

	//点击导入按钮事件
	$(document).on("click", "#submitApply", function () {
		
		var html = $("#bp-upload-tpl").html();
		var url = "/preOrderCollAndSettle/bpOrdersParseCalc.htm";
		
		// 获取金额字段
		var bpPrePayAmount = $(this).attr('data')

		// 准备请求数据
		var payNo = $(this).attr('data2')
		var bankCode = $(this).attr('data3')
		var payerBankCard = $(this).attr('data4')
		var currency = $(this).attr('data5')
		var payeeMerchantCode = $(this).attr('data6')
		
		var win = $.dialog({
			title: "结汇付款",
			content: html,
			width: 520,
			blur: false,
			afterShow: function ($dialog, callback) {
				//保存按钮
				$("#BP_placeOrder").click(function () {
					if ($("#BP_placeOrder").hasClass("disabled")) {
						return false;
					}
					
					// 上传文件控件处理
					var fileAddress = $(".bp-upload-files input[type=file]").val();
					if (fileAddress == '' || fileAddress == undefined || fileAddress == null) {
						return false;
					}
					// 数据设置
					$("#BP_placeOrder").addClass("disabled");
					var prePayAmount = $("#bpPrePayAmount").val();
					var bizType = $("#bizType").val();
					
					//批付文件上传校验
					$.ajaxFileUpload({
						url: sysconfig.ctx + "/preOrderCollAndSettle/bpOrdersParseCalc.htm",
						type: 'POST',
						fileElementId: 'bp-file-btn',
						data: { prePayAmount: bpPrePayAmount, bizType: bizType },
						dataType: 'json',
						success: function (data) {
							//若页面过期跳转至首页
							isIntercepted(data);
							
							if ($("#BP_placeOrder").hasClass("disabled"))
								$("#BP_placeOrder").removeClass('disabled');
			
							if (data.success) {
								var amount = data.amount
								var number = data.number
								var resultList = data.resultList
								
								$("#bpUploadSuccess").show();
								$("#bpUploadFail").hide();
								$("#bpWait").hide();
								
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
								
								//显示批付详情弹窗
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
											req.payNo = payNo
											req.bankCode = bankCode
											req.payeeBankCard = payerBankCard
											req.currency = currency
											req.bizType = bizType
											req.amount = bpPrePayAmount
											req.bpayeeList = data.resultList
											req.payeeMerchantCode = payeeMerchantCode
											//ajax请求后台
											$.ajax({
												type: "POST",
												url: sysconfig.ctx + "/preOrderCollAndSettle/collAndPaymentApply.htm",
												data: JSON.stringify(req),
												contentType: 'application/json;charset=utf-8', //设置请求头信息
												dataType: "json",
												success: function (data) {
													//若页面过期跳转至首页
													isIntercepted(data);
													$('.msg-box').html("");
													if (data.responseCode == '0000') {
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
																});
															},
															afterClose: function () {
																submitQuery('none');
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
								callback.close();
								//显示确认提示窗
								d.show();
									
							} else {
								//显示错误信息
								if(data.responseMsg){
									$("#bpUploadFail .err-text").html(data.responseMsg);
								} else{
									$("#bpUploadFail .err-text").html('');
								}
								$("#bpUploadFail").show();
								$("#bpUploadSuccess").hide();
								$("#bpWait").hide();
								
								//激活下载错误信息按钮
								if(data.fileAddress){
									var downloadFile = data.fileAddress
									$(".bp-upload-files .result-text .download").removeClass('disabled')
									//提交导出
									$(".bp-upload-files .result-text .download").click( function () {
								    	$.ajax({
							                url: sysconfig.ctx + '/authStatus',
							                crossDomain: true,
							                cache: false,
							                dataType: 'jsonp',
							                success: function (data) {
							                    if (data.hasLogin) {
							                        var formObj = $("form[name='f1']");
							                        formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/downloadErrorCsv.htm?failFileAddress="+downloadFile+"&type=bp");
							                        formObj.submit(); 
							                    } else {
							                        window.location.href=sysconfig.ctx+"/preOrderCollAndSettle/preFile/init.htm";
							                    }
							                },
							                error : function(data) {
							                    window.location.href=sysconfig.ctx+"/preOrderCollAndSettle/preFile/init.htm";
							                }
							            });	    			
							    	})
								}								
								
							}
						},
						complete: function (xmlHttpRequet) {
							$("input[name='bpFile']").replaceWith('<input type="file" id="bp-file-btn" name="file">');
						},
						error: function (data) { 
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
		$("#bpPrePayAmount").val(bpPrePayAmount);
		$("#bpPrePayAmountTxt").html("分发文件 表格需填写总金额："+bpPrePayAmount);
        $("#dialogSelect").initSelect()
	});
	
	//批付文件上传
	$(document).on("change", ".bp-upload-files input[type=file]", function () {
		var fileAddress = $(this).val();
		if (fileAddress == '' || fileAddress == undefined || fileAddress == null) {
			$("#BP_placeOrder").addClass("disabled");
			return false;
		}
		$("#BP_placeOrder").removeClass("disabled");
		$("#bpFile").val(fileAddress);
	});
});