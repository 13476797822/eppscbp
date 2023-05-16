require.config({
    baseUrl: '/eppscbp/scripts/lib/',
    paths: {
        'jquery': 'jquery-1.11.1.min',
        'template': 'template.min',
        'dialog': 'dialog/crossDialog',
        'select': 'select',
        'pagination': 'pagination/paginationLocal',
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
    'ajaxfileupload'
], function (
    $,
    template,
    Common,
	dialog,
	select,
	pagination
) {
    var Main = {
        init: function () {

            this.bindEvent();
        },
        bindEvent: function () {
            var _this = this;
            // 添加
            $('.J_addBtn').on('click', function () {
                _this.addAccountDialog();
            });
            // 删除
            $('.J_delBtn').on('click', function () {
            	
            	var checkedList=$('.J_fundTable').find("tbody").find('.platform-checkbox');
            	
            	if(checkedList.length==0){
            		return;
            	}
            	
                Common.confirm('您确定要删除选中的记录吗？', function ($dialog, callback) {
                	
                	$('.J_fundTable').find('tbody').find('.platform-checkbox.checked').each(function(){
                		$(this).parents('tr').remove();
                		
                	});

                });
            });

            // 导入
            $('.J_importBtn').on('click', function () {
        		
        		var html = $("#upload-tpl").html();
        		var win = $.dialog({
        			title: "批付信息文件上传",
        			content: html,
        			width: 520,
        			blur:false,
        			onShow: function ($dialog, callback) {
        				
        				//保存按钮
        				$("#J_placeOrder").click(function () {
        					if ($("#J_placeOrder").hasClass("disabled")) {
        						return false;
        					}
        					var requestDto = $('#f4').serialize();
        					$.ajax({
        						type: "POST",
        						dataType: "JSON",
        						url: sysconfig.ctx + "/cpBatchPayment/cpBatchPaymentInit/bpOrdersParseAndCalculate.htm",
        						data: requestDto,
        						success: function (data) {
        							//若页面过期跳转至首页
        							isIntercepted(data);	

        							if (data.success == "s") {
        								$(".bp-upload-files .result-text .success").hide();
        								$(".bp-upload-files .result-text .fail").hide();
        								$(".bp-upload-files .result-text .wait").hide();
        								callback.close();
        								//读取数据
        								var detailArray = new Array();								
        								//渲染表格底色
        								var flag = 0;
        								for (var i = 0; i < data.resultList.length; i++) {
        									if (i > 0) {
        										if (data.resultList[i].payeeMerchantName != data.resultList[i - 1].payeeMerchantName) {
        											flag++;
        										}
        									}
        									data.resultList[i].trIndex = flag;
        									detailArray.push({payeeMerchantCode:data.resultList[i].payeeMerchantCode,payeeMerchantName:data.resultList[i].payeeMerchantName,bankAccountNo:data.resultList[i].bankAccountNo, amount:data.resultList[i].amount, orderName:data.resultList[i].orderName});
        								}
        								
        								var confirmDetailArray = {};
        								confirmDetailArray.totalAmount=data.amount;
        								confirmDetailArray.totalNumber=data.number;
        								confirmDetailArray.detailArray=detailArray;
        								
        								//批付信息确认：列表信息，总金额，笔数
        								var html = template('inf-confirm-dialog-tpl', {confirmDetailArray:confirmDetailArray});
        					            
        								var fileAddress= data.fileAddress;

        					            var d = $.dialog({
        					                title: '批付信息确认',
        					                content: html,
        					                width: 710,
											css:{
												top: '80px'
											},
        					                onShow: function ($dialog, callback) {
												//分页开始
												var chunk = 10;
												var len = confirmDetailArray.detailArray.length;
												var result = [];
												for (var i = 0; i < len; i += chunk) {
													result.push(confirmDetailArray.detailArray.slice(i, i + chunk)) // 每10项分成一组        
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
												//分页结束
        					                    Common.scrollTable($dialog.find('.J_table'));

        					                    $dialog.find('.J_submit').on('click', function () {
        					                    	if ($(".J_submit").hasClass("disabled")){
        												return false;
        											}
        											$(".J_submit").addClass("disabled");
        					                    	$.ajax({
        												type: "GET",
        												url: sysconfig.ctx + "/cpBatchPayment/cpBatchPaymentInit/importBatchPaymentDetails.htm",
        												data: {fileAddress: fileAddress},
        												dataType: "json",
        												success: function (data) {
        													//若页面过期跳转至首页
        													isIntercepted(data);
        													if (data.success == 's') {
        														Common.toast('success', data.responseMsg);
        								                        callback.close();
        								                        
        								                        setTimeout(function(){
        								                        	var url = sysconfig.ctx + "/cpBatchPayment/cpBatchPaymentInit/init.htm";
        									                        window.location.href = url;
        								                        	},2000)
        								                        
        								                        
        													} else{
        														if(null == data.responseMsg){
        															Common.toast('error', "系统超时,请联系运营人员");
        														}else{
        															Common.toast('error', data.responseMsg);
        														}
        														
        														}
        												},							
        												error: function (msg) {

        												}
        											});
        					                        
        					                    });

        					                    $dialog.find('.J_cancel').on('click', function () {
        					                        callback.close();
        					                    });
        					                }
        					            });

        					            d.show();
        								
        							} else {
        								$(".upload-files .result-text .fail .err-text").html(data.responseMsg);
        								//$("#bpUploadFail").show();
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
        				});
        				

        				//取消按钮
        				$(".cancel-btn").click(function () {
        					callback.close();
        				})

        			},
        			showClose: true
        		})
        		win.show();
        		
        		var prePayAmountHide=$("#prePayAmount").val();
        		$("#prePayAmountHide").val(prePayAmountHide);
        		
        	});
            
            
          
            
            
            
          
            
            
            
            
            //批付文件上传校验
        	$(document).on("change", ".upload-files input[type=file]", function () {
        		var fileAddress = $(this).val();
        		if (fileAddress == '' || fileAddress == undefined || fileAddress == null) {
        			return false;
        		}
        		$("#J_placeOrder").addClass("disabled");
        		var prePayAmountHide=$("#prePayAmountHide").val();
        		
        		
        		$.ajaxFileUpload({
        			url: sysconfig.ctx + "/cpBatchPayment/cpBatchPaymentInit/fileSubmit.htm",
        			type: 'POST',
        			fileElementId: 'file-btn',
        			data: {prePayAmount:prePayAmountHide},
        			dataType: 'text',
        			success: function (data) {
        				//若页面过期跳转至首页
        				isIntercepted(data);

        				var obj = eval('(' + data + ')');
        				if (obj.success) {
        					$("#fileAdd").val(fileAddress);
        					$("#fileAddress").val(obj.fileAddress);					
        					$("#uploadSuccess").show();
        					$("#uploadFail").hide();
        					$("#wait").hide();
        					if ($("#J_placeOrder").hasClass("disabled"))
        						$("#J_placeOrder").removeClass('disabled');
        				} else {
        					$("#fileAdd").val(fileAddress);
        					$("#uploadFail .err-text").html(obj.message);
        					$("#uploadFail").show();
        					$("#uploadSuccess").hide();
        					$("#wait").hide();
        				}
        			},
        			complete: function (xmlHttpRequet) {
        				$("input[name='bpFile']").replaceWith('<input type="file" id="bp-file-btn" name="file">');
        			},
        			error: function (data) {}
        		});
        	});
            
            
            


            $('.J_fundTable').on('click', 'tbody .platform-checkbox', function () {
                if ($(this).hasClass('checked')) {
                    $(this).removeClass('checked');
                } else {
                    $(this).addClass('checked');
                }

                var $checkedList = $('.J_fundTable tbody').find('.platform-checkbox.checked');
                var $list = $('.J_fundTable tbody').find('tr');

                if ($checkedList.length == 0) {
                    $('.J_submitBtn').addClass('disabled');
                } else {
                    $('.J_submitBtn').removeClass('disabled');
                }

                var $checkAll = $('.J_fundTable').find('.J_checkAll');

                if ($checkedList.length == $list.length) {
                    $checkAll.addClass('checked');
                } else {
                    $checkAll.removeClass('checked');
                }
            });
            $('.J_fundTable').find('.J_checkAll').on('click', function () {
                var $list = $('.J_fundTable tbody').find('tr');

                if ($(this).hasClass('checked')) {
                    $(this).removeClass('checked');
                    $list.find('.platform-checkbox').removeClass('checked');
                    $('.J_submitBtn').addClass('disabled');
                } else {
                    $(this).addClass('checked');
                    $list.find('.platform-checkbox').addClass('checked');
                    $('.J_submitBtn').removeClass('disabled');
                }
            });


            // 批付
            $('.J_submitBtn').on('click', function () {
                if ($(this).hasClass('checked')) {
                    return;
                }
                
                var checkedList=$('.J_fundTable').find("tbody").find('.platform-checkbox.checked');
            	
            	if(checkedList.length==0){
            		return;
            	}
                
                var flag=true;
                
             // 进行分笔计算
				var calDetailArray = new Array();
				var i = 0;
				$('.J_fundTable').find('tbody').find('.platform-checkbox.checked').each(function(){
					var payeeMerchantCode = $(this).find("input[name='disPayeeMerchantCode']").val();
					var payeeMerchantName = $(this).find("input[name='disPayeeMerchantName']").val();
					var bankAccountNo = $(this).find("input[name='disBankAccountNo']").val();
					var amount = $(this).parents('tr').find("input[name='amount']").val();
					var orderName = $(this).parents('tr').find("input[name='orderName']").val();
					
					var orderNameLL=orderName.length;

					
					if (!/^(?:[1-9][0-9]*(?:\.[0-9]{1,2})?|0(?:\.[0-9]{1,2})?)$/
							.test(amount)) {
						Common.toast('error', '出款金额不能为空，且只能输入数字，小数点后只能保留两位');
						flag=false;
						return false;
					}
					
					if(orderNameLL>32){
						Common.toast('error', '附言长度不超过32位');
						flag=false;
						return false;
					}
					
					calDetailArray.push({payeeMerchantCode:payeeMerchantCode,payeeMerchantName:payeeMerchantName,bankAccountNo:bankAccountNo,amount:amount,orderName:orderName});
				});
				
				if(!flag){
					return;
				}
				
				var calReq = {};
				calReq.prePayAmount=$("#prePayAmount").val();
				calReq.details=calDetailArray;
				
				
				
				$.ajax({
					type: "POST",
					url: sysconfig.ctx + "/cpBatchPayment/cpBatchPaymentInit/ordersCalculate.htm",
					data: JSON.stringify(calReq),
					contentType : 'application/json;charset=utf-8', //设置请求头信息
					dataType: "json",
					success: function (data) {	
						//若页面过期跳转至首页
						isIntercepted(data);
						if (data.success == 's') {									
							//读取数据
							var detailArray = new Array();								
							//渲染表格底色
							var flag = 0;
							for (var i = 0; i < data.resultList.length; i++) {
								if (i > 0) {
									if (data.resultList[i].payeeMerchantName != data.resultList[i - 1].payeeMerchantName) {
										flag++;
									}
								}
								data.resultList[i].trIndex = flag;
								detailArray.push({payeeMerchantCode:data.resultList[i].payeeMerchantCode,payeeMerchantName:data.resultList[i].payeeMerchantName,bankAccountNo:data.resultList[i].bankAccountNo, amount:data.resultList[i].amount, orderName:data.resultList[i].orderName});
							}
							
							var confirmDetailArray = {};
							confirmDetailArray.totalAmount=data.amount;
							confirmDetailArray.totalNumber=data.number;
							confirmDetailArray.detailArray=detailArray;
							
							_this.infConfirmDialog(confirmDetailArray);
							
							
						} else{
							
							Common.toast('error', data.responseMsg);
	                        callback.close();
							
							
							
						}
					},
					error: function (msg) {
		
					}
				});
				
				
				
			
            });
        },
        addAccountDialog: function () {
            var _this = this;

            var html = template('add-account-dialog-tpl', {});

            var d = $.dialog({
                title: '添加收款账号',
                content: html,
                width: 710,
                onShow: function ($dialog, callback) {
                    $dialog.find('.jr-select').initSelect();

                    Common.scrollTable($dialog.find('.J_table'));

                    $dialog.find('.J_submit').on('click', function () {
                        if ($(this).hasClass('disabled')) {
                            return;
                        }
                        
                        $dialog.find('.J_table .platform-scroll-table-body').find('.platform-checkbox.checked').each(function(){
							var payeeMerchantCode = $(this).find("input[name='disPayeeMerchantCode']").val();
							var payeeMerchantName = $(this).find("input[name='disPayeeMerchantName']").val();
							var bankAccountNo = $(this).find("input[name='disBankAccountNo']").val();
							
							//var disHtml="<tr><td><i class='platform-checkbox'></i></td><td>"+payeeMerchantCode+"</td><td>"+payeeMerchantName+"</td><td>"+bankAccountNo+"</td><td><input type='text' class='platform-input'></td></tr>";
							
							var calDetailArray = new Array();
							calDetailArray.push({payeeMerchantCode:payeeMerchantCode,payeeMerchantName:payeeMerchantName,bankAccountNo:bankAccountNo});

							
							var disHtml = template('confirm-table-body', {merchantList:calDetailArray});
							
							
							$("#fund-table-wrap").find('tbody').append(disHtml);
							 
						});


                        callback.close();
                    });

                    $dialog.find('.J_cancel').on('click', function () {
                        callback.close();
                    });

                    $dialog.find('.J_table .platform-scroll-table-body').on('click', '.platform-checkbox', function () {
                        if ($(this).hasClass('checked')) {
                            $(this).removeClass('checked');
                        } else {
                            $(this).addClass('checked');
                        }

                        var $checkedList = $dialog.find('.J_table .platform-scroll-table-body').find('.platform-checkbox.checked');
                        var $list = $dialog.find('.J_table .platform-scroll-table-body').find('tr');

                        if ($checkedList.length > 0) {
                            $dialog.find('.J_submit').removeClass('disabled');
                        } else {
                            $dialog.find('.J_submit').addClass('disabled');
                        }

                        var $checkAll = $dialog.find('.J_table .platform-scroll-table-head').find('.platform-checkbox');

                        if ($checkedList.length == $list.length) {
                            $checkAll.addClass('checked');
                        } else {
                            $checkAll.removeClass('checked');
                        }
                    });
                    $dialog.find('.J_table .platform-scroll-table-head').on('click', '.platform-checkbox', function () {
                        var $list = $dialog.find('.J_table .platform-scroll-table-body').find('tr');

                        if ($(this).hasClass('checked')) {
                            $(this).removeClass('checked');
                            $list.find('.platform-checkbox').removeClass('checked');
                            $dialog.find('.J_submit').addClass('disabled');
                        } else {
                            $(this).addClass('checked');
                            $list.find('.platform-checkbox').addClass('checked');
                            $dialog.find('.J_submit').removeClass('disabled');
                        }
                    });

                    $dialog.find('.J_search').on('click', function () {

                    	var payeeMerchantCode=$("#payeeMerchantCode").val();
                        var accountName=$("#accountName").val();
                        var bankAccount=$("#bankAccount").val();
                        if((payeeMerchantCode == '' || payeeMerchantCode == null) && (accountName == '' || accountName == null) &&
                            (bankAccount == '' || bankAccount == null)){
                        	Common.toast('error', '至少写入一个参数');
                            return;
                        }

                        $.ajax({
                            type: 'POST',
                            url: sysconfig.ctx + "/cpBatchPayment/cpBatchPaymentInit/batchPaymentQuery.htm",
                            data:{"payeeMerchantCode":payeeMerchantCode,"accountName":accountName,"bankAccount":bankAccount},
                            dataType: 'JSON',
                            async:false,
                            success: function (data) {
                            	//若页面过期跳转至首页
                				isIntercepted(data);                          	
                                if (data.success == 's') {
                                	var html = template('platform-scroll-table-body', {merchantList:data.merchantList});
                                    $dialog.find('tbody').html(html);
                                } else{
                                	var html = '<div class="no-result-s"><i></i>没有找到相关数据</div>';
                                	$dialog.find('tbody').html(html);
                                }
                            },
                            error: function (msg) {
                            	var html = '<div class="no-result-s"><i></i>查询失败，请稍后再试</div>';
                            	$dialog.find('tbody').html(html);
                            }
                        });
                     });

                    //_this.searchAccount($dialog, callback);
                }
            });

            d.show();
        },
        searchAccount: function ($dialog, callback) {
            var _this = this;

            var payeeMerchantCode=$("#payeeMerchantCode").val();
            var accountName=$("#accountName").val();
            var bankAccount=$("#bankAccount").val();
            
        },
        infConfirmDialog: function (confirmDetailArray) {
            var _this = this;

            var html = template('inf-confirm-dialog-tpl', {confirmDetailArray:confirmDetailArray});
            
            
            var detailArray = new Array();	
            for (var i = 0; i < confirmDetailArray.detailArray.length; i++) {
				
				detailArray.push({payeeMerchantCode:confirmDetailArray.detailArray[i].payeeMerchantCode, amount:confirmDetailArray.detailArray[i].amount, orderName:confirmDetailArray.detailArray[i].orderName});
			}
            
            
            
            var req = {};
			req.platformCode="";
			req.payerAccount="";
			req.businessNo="";
			req.details=detailArray;

            var d = $.dialog({
                title: '批付信息确认',
                content: html,
                width: 710,
				css: {
					top: "80px"
				},
                onShow: function ($dialog, callback) {
                    Common.scrollTable($dialog.find('.J_table'));

					//分页开始
					var chunk = 10;
					var len = confirmDetailArray.detailArray.length;
					var result = [];
					for (var i = 0; i < len; i += chunk) {
						result.push(confirmDetailArray.detailArray.slice(i, i + chunk)) // 每10项分成一组        
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
					//分页结束

                    $dialog.find('.J_submit').on('click', function () {
                    	if ($(".J_submit").hasClass("disabled")){
							return false;
						}
						$(".J_submit").addClass("disabled");
						
                    	$.ajax({
							type: "POST",
							url: sysconfig.ctx + "/cpBatchPayment/cpBatchPaymentInit/ordersAudit.htm",
							data: JSON.stringify(req),
							contentType : 'application/json;charset=utf-8', //设置请求头信息
							dataType: "json",
							success: function (data) {
								//若页面过期跳转至首页
								isIntercepted(data);
								if (data.success == 's') {
									Common.toast('success', data.responseMsg);
			                        callback.close();
			                        
			                        setTimeout(function(){
			                        	var url = sysconfig.ctx + "/cpBatchPayment/cpBatchPaymentInit/init.htm";
				                        window.location.href = url;
			                        	},2000)
			                        
			                        
								} else{
									Common.toast('error', data.responseMsg);
									}
							},							
							error: function (msg) {

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
