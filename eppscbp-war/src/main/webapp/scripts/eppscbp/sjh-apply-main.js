require.config({
    baseUrl:"scripts",
    paths: {
        jquery: '/eppscbp/scripts/lib/jquery-1.11.1.min',
        template:'/eppscbp/scripts/lib/template/template.min',
        select:'/eppscbp/scripts/lib/select/select',
        searchableSelect:'/eppscbp/scripts/lib/select/jquery.searchableSelect',
        validate:'/eppscbp/scripts/lib/validation/validate',
        dialog:'/eppscbp/scripts/lib/dialog/dialog',
        ajaxFileUpload:'/eppscbp/scripts/lib/upload/ajaxfileupload'
    },
    shim: {
        'searchableSelect':['jquery'],
        'validate': ['jquery'],
        'dialog':['jquery'],
        'ajaxFileUpload':['jquery']
    }
});

require(['jquery','template','select','searchableSelect','validate','dialog','ajaxFileUpload'], function($,template,select,searchableSelect,validate,dialog,ajaxFileUpload) {
   // ie,firefox浏览器页面刷新输入框内容不清空
   $(document).ready(function () {
        $('input').val("");
   });
    //TODO 模糊查询查询插件去除默认选项，添加无匹配值的情况下可以以输入为准的逻辑
    $('#arrival').searchableSelect();
    
    
    $(document).on("click",".select-box",function(){
 	   return false;
    });
    
	
	//来账查询   
   $('#select-currency').on("click",".sel-val",function(){
	    $('#currency').parent().find('.tips').empty();
	    $("#currency").parent().find('.tips').removeClass('err');
	    $("#select-box-merchant").parent().find('.tips').empty();
	    $("#select-box-merchant").parent().find('.tips').removeClass('err');
		
		document.getElementById("referenceRateFake").innerText = "请以实际汇率为准";
		clearData();
		
		var currency = $("#currency").val();
		$("#receiveAmt").val(null);
		$('#referenceRate').val(null);
		$('#payeeMerchantCode').val(null);
		
		$.ajax({
      	type: "POST",
      	data:{"currency":currency},
      	url:sysconfig.ctx+"/collAndSettle/fileImport/arrivalQuery.htm",
      	dataType: "JSON",
      	async:false,
      	success: function(data) {
      		//若页面过期跳转至首页
        	isIntercepted(data);
        	//清空商户列表
  			$('#payeeBankCard').val(null);
  			$('#select-box-merchant').find("li").remove();
      		if(data.success == true){
      			var arrivalInfoList = data.result.arrivalInfoList;
      			if(typeof(arrivalInfoList) == "undefined" || arrivalInfoList.length == 0){
      				$("#currency").parent().find('.tips').append('该币种未开通境外账号').addClass('err');   
      				return;
      			}	
      			for(var i = 0;i < arrivalInfoList.length;i++){
      				var arrivalInfoResDto = arrivalInfoList[i];
      				var payeeMerchantCode = arrivalInfoResDto.payeeMerchantCode;
      				var payerBankCard = arrivalInfoResDto.payerBankCard
      				var notValid = arrivalInfoResDto.collectionType!="1"||(arrivalInfoResDto.settlementType!="1"&&currency!="人民币");
      				// 双引号替换成单引号避免data属性错误
      				var arrivalNoticeList = JSON.stringify(arrivalInfoResDto.arrivalNoticeList);
      				arrivalNoticeList = arrivalNoticeList.replace(/\"/g,"\'");
      				var option=$('<li class="select-item"><a class="sel-val" href="javascript:void(0);" key="1" value="'+payerBankCard+'" code="'+ payeeMerchantCode+'" notvalid="'+notValid+'" data="'+ arrivalNoticeList +'">'+payerBankCard+'</a></li>');
      				$('#select-box-merchant').append(option);
      				$('#select-merchant').initSelect();
      			} 		 	           		 	
		 	
         		if(currency=='人民币'){
         			$('#referenceRateDiv').attr({style:'display:none'});
         		}else{
         			$('#referenceRateDiv').removeAttr('style');
         		}      			
      		}else{     			
      			if(currency=='人民币'){
         			$('#referenceRateDiv').attr({style:'display:none'});
         		}else{
         			$('#referenceRateDiv').removeAttr('style');
         		}
      			
      			$("#currency").parent().find('.tips').append(data.responseMsg).addClass('err'); 	
      		}
     	},
      	error: function(msg) {    		
      		$("#currency").parent().find('.tips').append('该币种未开通境外账号').addClass('err');   
      	}
     });

   });
   
	
    $('.searchable-select-item').live("click",function () {
        var value = $.trim($(".searchable-select-holder").text());
        $("input[name='jhTotalNum']").val(value);
        $("input[name='jhTotalNum']").parent('.input-item').find('.err').remove();
        $(document).parent().find('.err').remove();
        
    });
    
    $('body').click(function () {
        $('.searchable-select-dropdown').hide();
    });
	
	$('.searchable-select').live("click",function(e){
		e.stopPropagation();
		$('.searchable-select-dropdown').show();           
    });
		

    $('.searchable-select-input').live('blur', function(event) {
        event.stopPropagation();
        var value = $(this).val();
        //不存在筛选匹配的值，enter取此值为所要值
        event.preventDefault();
        $('.searchable-select-item').removeClass('selected');
        $('.searchable-select-dropdown').hide();
        $('.searchable-select-holder').text(value);
        $("input[name='jhTotalNum']").val(value);
        $('#arrival').parent().find('.tips').empty();
        $('#arrival').parent().find('.tips').removeClass('err');
        
        var input=/^((([1-9]{1}\d{1,13})(\.(\d){0,2})?)|([1-9]{1}\d{1,13}))$/;
        if(!(input.test(value))){
        	$("#arrival").parent().find('.tips').append('长度最大为16位，可包含2位小数').addClass('err');         	
        }else{
        	$("#receiveAmt").val(value);
     		$('#referenceRate').val("请以实际汇率为准");
        }
        
        $('.result-wrap-success').addClass('hide');
        $("#fileAddress").val('');
        $('.result-wrap-fail').addClass('hide');
		$("#fileUpload").val('');
		$("#fakepath").val('');
        
    });
    
    $('.sel-val').click(function () {
        $(this).parents('.input-item').find('.tips').empty();
    });
    
    $('#select-merchant').on("click",".sel-val",function(){
 	    $("#select-box-merchant").parent().find('.tips').empty();
	    $("#select-box-merchant").parent().find('.tips').removeClass('err');
 	   	
 	   	clearData();
 	   	
    	//清空来账列表
		$(".searchable-select").remove();		
		$('#arrival').find('option').remove();		  	 		
		$('#payeeMerchantCode').val($(this).attr("code"));				
		var arrivalNoticeListStr = $(this).attr("data");
		arrivalNoticeListStr = arrivalNoticeListStr.replace(/\'/g,"\"");		
		var arrivalNoticeList = JSON.parse(arrivalNoticeListStr);		
		if(arrivalNoticeList!=null){
 			for(var i=0;i<arrivalNoticeList.length;i++){
 		 		var amount=arrivalNoticeList[i].amount;
 		 		var arrivalNoticeId=arrivalNoticeList[i].arrivalNoticeId;
 		 		var payNo=arrivalNoticeList[i].payNo;
 		 		var referenceRate=arrivalNoticeList[i].referenceRate;
 		 		var info=amount+";"+arrivalNoticeId+";"+payNo+";"+referenceRate;	         		 	
 		 		var option=$('<option value="'+info+'">'+amount+'</option>');
 		 		$('#arrival').append(option);
 		 	}
 		}
		$('#arrival').searchableSelect();
		if($(this).attr("notvalid")=="true"){
			$("#select-box-merchant").parent().find('.tips').append('境外商户未开通收结汇功能').addClass('err');   
			return;
		}
		$('body').click();
    });
    
    //文件上传前校验金额和币种是否填写
    $(document).on("click","#fileUpload",function(){
    	var curErr=$("#currency").parent().find(".err").length;
    	var arrErr=$("#arrival").parent().find(".err").length;
    	var merchantErr=$("#select-box-merchant").parent().find(".err").length;
    	if(curErr>0||arrErr>0 || merchantErr>0){    		
    		return false;
    	}
    	
    	var curValue=$("#currency").val();
    	var recValue=$("#receiveAmt").val();
    	
    	if(curValue=="" && recValue==""){
    		$("#currency").parent().find('.tips').append('请选择币种').addClass('err');
    		$("#arrival").parent().find('.tips').append('请填写结汇金额').addClass('err'); 
    		return false;
    	}
    	
    	if(curValue==""){
    		$("#currency").parent().find('.tips').append('请选择币种').addClass('err');
    		return false;
    	}
    	
    	if(recValue==""){
    		$("#arrival").parent().find('.tips').append('请填写结汇金额').addClass('err'); 
    		return false;
    	}
    	
    });
    
    //点击提交
    $(document).on("click","#J_placeOrder",function(){
    	$('body').find('.tips').empty();
    	$('.result-wrap-fail').addClass('hide');
    	window.onbeforeunload=function(){
            return;
        };
		$("#pcToken").val(_dfp.getToken())
		var formObj = $("#applyForm");
		formObj.attr("action", sysconfig.ctx+"/collAndSettle/fileImport/applySubmit.htm");
		formObj.submit(); 
		   
		if($('.err').length==0){
			var html = '<div class="loading-box"><img src="'+sysconfig.ctx+'/style/images/submit-loading.gif"><p>订单提交中...</p></div>';
			$("body").append(html);
			$("#J_placeOrder").attr('class','disable');
		}       
    });  
    
    //重复提交
    $(document).on("click","#fileUpload",function(){
    	$('#fileUpload').val('');
    });
    
    function clearData(){
    	$('.result-wrap-success').addClass('hide');
        $("#fileAddress").val('');
		$('.result-wrap-fail').addClass('hide');
		$("#fileUpload").val('');
		$("#fakepath").val('');
    };
    
    var sjhApply ={
            init:function () {
            	
                this.initValid();
                this.uploadFile();
            },
            initValid:function () {	
            	
            	//平台名称
            	$.validator.addMethod('platformName', function (value, element) {
            		var input = /^([a-z]|[A-Z]|[0-9]|[/\?\(\)\.\,\:\-\'\+\{\}\]]|[ ]){4,8}$|^[\u4e00-\u9fa5]{2,4}$/;
            		return input.test(value);
            	}, '请输入正确格式');
               
            //表单校验
            var res = $("#applyForm").validate({         
               errorPlacement: function(error, element) {
            	   $(element).parents(".input-item").find('.tips').append(error).addClass('err');  
               },
               rules: {
                	currency:{
                		required:true
                    },
                    receiveAmt:{
                        required:true
                    },
                    bizType:{
                        required:true
                    },
                    fileAddress:{
                    	required:true
                    },
                    payeeMerchantCode:{
                    	required:true
                    }, platformName:{
        				required: true,
        				platformName:true
        			}
                },
                messages: {
                	currency:{
                		required:"请选择币种"
                    },
                    receiveAmt:{
                        required:"请填写结汇金额"
                    },
                    bizType:{
                        required:"请选择业务类型"
                    },
                    fileAddress:{
                    	required:"请上传明细文件"
                    },
                    payeeMerchantCode:{
                    	required:"请选择收款方账号"
                    }, platformName:{
        				required: '请输入平台名称',
        				platformName:'请输入正确格式'
        			}
                }
            });
                
            },
            uploadFile:function () {
                var $file = $('input[type=file]'),
                    $this, path, name, id;
                $file.unbind().live('change', function() {

                    $this = $(this);
                    path = $this.val();
                    name = $this.prop('name');
                    id = $this.prop('id');
                    $this.parents('.valid-form').find('.path-inp').val(path);
                    
                    var html = '<div class="loading-box"><img src="'+sysconfig.ctx+'/style/images/submit-loading.gif" style="width:100%;height:100%;"><p>文件上传中...</p></div>';
            		$("body").append(html);

                    
                    var currency=$("#currency").val();
                	var receiveAmt=$("#receiveAmt").val();
                	
                	$('#fileAddress').parent().find('.tips').empty();
                	 $("#fileAddress").parent().find(".tips").removeClass('err');

                    $('.result-wrap-success').addClass('hide');
                    $('.result-wrap-fail').addClass('hide');
                    $("#fileAddress").val('');
                    
                    
                    $.ajaxFileUpload({
                        url: sysconfig.ctx+"/collAndSettle/fileImport/fileSubmit.htm",
                        type: 'POST',
                        fileElementId: 'fileUpload',
                        data: {currency:currency, receiveAmt:receiveAmt, collAndSettleFlag:"0"},                       
                        dataType: 'text',
                        success: function(data) {
                        	//若页面过期跳转至首页
                        	isIntercepted(data);
                        	var obj = eval('(' + data+ ')');
                            if(obj.success){
                                $('.path-inp').siblings('.tips').empty();
                                $('.result-wrap-success').removeClass('hide');
                                $('.result-wrap-fail').addClass('hide');
                                $('#fileAddress').val(obj.fileAddress);
                                $('#detailAmount').val(obj.detailAmount);
                            }else{
                                $('.result-wrap-success').addClass('hide');
                                $('.result-wrap-fail').removeClass('hide');
                                $('.result-wrap-fail .gray').html(obj.message);
                            }
                            $(".loading-box").remove();
                        },
                        
                        error: function() {
                        	$(".loading-box").remove();
                            //console.log('error');
                        }
                    });
                    
                });
            }         
        }
        sjhApply.init();

        //汇款金额计算
        $(document).on("click",".exchange-count-btn",function(){
            var html = $("#count-tpl").html();
            var win = $.dialog({
                        title : "汇款金额计算",
                        content : html,
                        width : 620,
                        onShow : function($dialog, callback){
                            exchangeCountValidate();
                            //计算
                            $("#exchangeAmount").focus(
                                function(){
                                    if($("#exchangeCount").valid()){
                                        var batchPayNum = $("#batchPayAmount").val();
                                        var rate = $("#feeRate").val();
                                        var exchange = batchPayNum/(1-rate)
                                        exchange = exchange.toFixed(2);
                                        $("#exchangeAmount").val(exchange);
                                    }
                                }
                            )
                            //取消按钮
                            $("#cancel").click(function(){
                                callback.close();
                            })
                        },
                        blur: false
                        })
            win.show();
        });

        function exchangeCountValidate(){
            $.validator.addMethod('batchPayAmount',function (value, element) {
                var input=/^(([1-9]{1}\d{0,12})(\.(\d){1,2})?)$/;
                return input.test(value);
            },'');
            $.validator.addMethod('feeRate',function (value, element) {
                var input=/^((0\.\d{1,5}))$/;
                return input.test(value);
            },'');
            $.validator.addMethod('rate0',function (value, element) {
                var input=/^((0\.0{1,5}))$/;
                return !input.test(value);
            },'');
            var valid = $("#exchangeCount").validate({
                ignore:":hidden:not(select)",
                errorClass: 'tip-red',
                errorElement: 'p',
                rules: {
                    batchPayAmount:{
                        required: true,
                        number:true,
                        batchPayAmount:true
                    },feeRate:{
                        required: true,
                        number:true,
                        feeRate:true,
                        rate0:true
                    }
                },
                messages: {
                    batchPayAmount:{
                        required: '请输入批付金额',
                        number: '请输入数字或输入小数位值',
                        batchPayAmount: '整数位最大长度为13位，小数精度不超过2位'
                    },feeRate:{
                        required: '请输入手续费费率',
                        number: '请输入数字或输入小数位值',
                        feeRate: '请输入0到1之间的小数，精度不超过5位',
                        rate0: '请输入0到1之间的小数，精度不超过5位'
                    }
                },
                errorPlacement : function(error, element) {
                    //报错信息放入元素最后
                    error.appendTo(element.parent());
                },
                submitHandler: function() {
                }
            });
            return valid;
        }
    
});
