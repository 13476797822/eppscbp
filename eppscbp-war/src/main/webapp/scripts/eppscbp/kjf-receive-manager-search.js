require.config({
    baseUrl: 'scripts',
    paths: {
        'jquery': '/eppscbp/scripts/lib/jquery.1.7.2',
        "select": '/eppscbp/scripts/lib/select',
        'ECode.calendar': '/eppscbp/scripts/lib/calendar/ECode.calendar',
        dialog: '/eppscbp/scripts/lib/dialog/dialog',
        valid: '/eppscbp/scripts/lib/validation/validate',
        template: '/eppscbp/scripts/lib/template.min',
        ajaxfileupload: '/eppscbp/scripts/lib/upload/ajaxfileupload',
        chosen:'/eppscbp/scripts/lib/chosen.jquery',
        'common': '/eppscbp/js/common/common'
    },
    shim: {
        // 公共头尾需要的js
        'ECode.calendar': ['jquery'],
        valid: ['jquery'],
        dialog: ['jquery'],
        ajaxfileupload:['jquery'],
        chosen:['jquery']      
    }
});

require(['jquery','template','valid','select','ECode.calendar','dialog', 'ajaxfileupload', 'chosen','common'], function($,template,valid,Common) {

    /*重置*/
    $(document).on("click",".reset",function(){

        $(this).parents(".condition-list").find("input").val("");
    })
    
    $(document).on("blur","input[name='payeeName']",function(){
    		var payeeNameAdd = $("input[name='payeeName']").val();
    		var bank = /[b|B][a|A][n|N][k|K]/;
    		
        	if(bank.test(payeeNameAdd)){
        		$("input[name='payeeName']").parent().append("<p class='red-warn'>请勿填写银行名称</p>");
     		}else{
     			//$('#bizTypeDiv').removeAttr('style');
     			//$("input[name='payeeName']").removeAttr('style');
     			$("input[name='payeeName']").parents(".condition-item").find("p.red-warn").remove();
     		}
   	 })
    
    
    var country=["ABW-阿鲁巴", "AFG-阿富汗", "AGO-安哥拉", "AIA-安圭拉", "ALB-阿尔巴尼亚", "AND-安道尔", "ANT-荷属安的列斯", "ARE-阿联酋", "ARG-阿根廷", "ARM-亚美尼亚", "ASM-美属萨摩亚", "ATA-南极洲", "ATF-法属南部领土", "ATG-安提瓜和巴布达", "AUS-澳大利亚", "AUT-奥地利", "AZE-阿塞拜疆", "BDI-布隆迪", "BEL-比利时", "BEN-贝宁", "BFA-布基纳法索", "BGD-孟加拉国", "BGR-保加利亚", "BHR-巴林", "BHS-巴哈马", "BIH-波斯尼亚和黑塞哥维那", "BLR-白俄罗斯", "BLZ-伯利兹", "BMU-百慕大", "BOL-玻利维亚", "BRA-巴西", "BRB-巴巴多斯", "BRN-文莱", "BTN-不丹", "BVT-布维岛", "BWA-博茨瓦纳", "CAF-中非", "CAN-加拿大", "CCK-科科斯(基林)群岛", "CHE-瑞士", "CHL-智利", "CHN-中国", "CIV-科特迪瓦", "CMR-喀麦隆", "COD-刚果（金）", "COG-刚果（布）", "COK-库克群岛", "COL-哥伦比亚", "COM-科摩罗", "CPV-佛得角", "CRI-哥斯达黎加", "CUB-古巴", "CXR-圣诞岛", "CYM-开曼群岛", "CYP-塞浦路斯", "CZE-捷克", "DEU-德国", "DJI-吉布提", "DMA-多米尼克", "DNK-丹麦", "DOM-多米尼加共和国", "DZA-阿尔及利亚", "ECU-厄瓜多尔", "EGY-埃及", "ERI-厄立特里亚", "ESH-西撒哈拉", "ESP-西班牙", "EST-爱沙尼亚", "ETH-埃塞俄比亚", "FIN-芬兰", "FJI-斐济", "FLK-福克兰群岛（马尔维纳斯群岛）", "FRA-法国", "FRO-法罗群岛", "FSM-密克罗尼西亚联邦", "GAB-加蓬", "GBR-英国", "GEO-格鲁吉亚", "GHA-加纳", "GIB-直布罗陀", "GIN-几内亚", "GLP-瓜德罗普", "GMB-冈比亚", "GNB-几内亚比绍", "GNQ-赤道几内亚", "GRC-希腊", "GRD-格林纳达", "GRL-格陵兰", "GTM-危地马拉", "GUF-法属圭亚那", "GUM-关岛", "GUY-圭亚那", "HKG-香港", "HMD-赫德岛和麦克唐纳岛", "HND-洪都拉斯", "HRV-克罗地亚", "HTI-海地", "HUN-匈牙利", "IDN-印度尼西亚", "IND--印度", "IOT-英属印度洋领地", "IRL-爱尔兰", "IRN-伊朗", "IRQ-伊拉克", "ISL-冰岛", "ISR-以色列", "ITA-意大利", "JAM-牙买加", "JOR-约旦", "JPN-日本", "KAZ-哈萨克斯坦", "KEN-肯尼亚", "KGZ-吉尔吉斯斯坦", "KHM-柬埔寨", "KIR-基里巴斯", "KNA-圣基茨和尼维斯", "KOR-韩国", "KWT-科威特", "LAO-老挝", "LBN-黎巴嫩", "LBR-利比里亚", "LBY-利比亚", "LCA-圣卢西亚", "LIE-列支敦士登", "LKA-斯里兰卡", "LSO-莱索托", "LTU-立陶宛", "LUX-卢森堡", "LVA-拉脱维亚", "MAC-澳门", "MAR-摩洛哥", "MCO-摩纳哥", "MDA-摩尔多瓦", "MDG-马达加斯加", "MDV-马尔代夫", "MEX-墨西哥", "MHL-马绍尔群岛", "MKD-前南马其顿", "MLI-马里", "MLT-马耳他", "MMR-缅甸","MNE-黑山", "MNG-蒙古", "MNP-北马里亚纳", "MOZ-莫桑比克", "MRT-毛里塔尼亚", "MSR-蒙特塞拉特", "MTQ-马提尼克", "MUS-毛里求斯", "MWI-马拉维", "MYS-马来西亚", "MYT-马约特", "NAM-纳米比亚", "NCL-新喀里多尼亚", "NER-尼日尔", "NFK-诺福克岛", "NGA-尼日利亚", "NIC-尼加拉瓜", "NIU-纽埃", "NLD-荷兰", "NOR-挪威", "NPL-尼泊尔", "NRU-瑙鲁", "NZL-新西兰", "OMN-阿曼", "PAK-巴基斯坦", "PAN-巴拿马", "PCN-皮特凯恩", "PER-秘鲁", "PHL-菲律宾", "PLW-帕劳", "PNG-巴布亚新几内亚", "POL-波兰", "PRI-波多黎各", "PRK-朝鲜", "PRT-葡萄牙", "PRY-巴拉圭", "PSE-巴勒斯坦", "PYF-法属波利尼西亚", "QAT-卡塔尔", "REU-留尼汪", "ROM-罗马尼亚", "RUS-俄罗斯联邦", "RWA-卢旺达", "SAU-沙竺阿拉伯", "SDN-苏丹", "SEN-塞内加尔", "SGP-新加坡", "SGS-南乔治亚岛和南桑德韦奇岛", "SHN-圣赫勒拿", "SJM-斯瓦尔巴岛和扬马延岛", "SLB-所罗门群岛", "SLE-塞拉利昂", "SLV-萨尔瓦多", "SMR-圣马力诺", "SOM-索马里", "SPM-圣皮埃尔和密克隆", "SRB-塞尔维亚", "STP-圣多美和普林西比", "SUR-苏里南", "SVK-斯洛伐克", "SVN-斯洛文尼亚", "SWE-瑞典", "SWZ-斯威士兰", "SYC-塞舌尔", "SYR-叙利亚", "TCA-特克斯科斯群岛", "TCD-乍得", "TGO-多哥", "THA-泰国", "TJK-塔吉克斯坦", "TKL-托克劳", "TKM-土库曼斯坦", "TMP-东帝汶", "TON-汤加", "TTO-特立尼达和多巴哥", "TUN-突尼斯", "TUR-土耳其", "TUV-图瓦卢", "TWN-中国台湾", "TZA-坦桑尼亚", "UGA-乌干达", "UKR-乌克兰", "UMI-美国本土外小岛屿", "URY-乌拉圭", "USA-美国", "UZB-乌兹别克斯坦", "VAT-梵蒂冈", "VCT-圣文森特和格林纳丁斯", "VEN-委内瑞拉", "VGB-英属维尔京群岛", "VIR-美属维尔京群岛", "VNM-越南", "VUT-瓦努阿图", "WLF-瓦利斯和富图纳", "WSM-萨摩亚", "YEM-也门", "ZAF-南非", "ZMB-赞比亚", "ZWE-津巴布韦"];
    
    function loadOptions(){
    	$("<option></option>").appendTo("#country"); 
		for(var i=0;i<country.length;i++){
			$("<option value='"+country[i]+"'>"+country[i]+"</option>").appendTo("#country"); 
		}
		$("#country").chosen({
    	    no_results_text: "没有找到结果！",
    	    search_contains:true
    	}); 
	}
    
    /*新增 如果账号性质是境外付款账号，业务类型隐藏，并写死为000*/
    function bizTypeDisplay(){
    	$(document).on("click",".sel-val",function(){
    		var accountCharacter = $("#accountCharacter").val();
        	if(accountCharacter=='收结汇-境外付款账号'){
     			$('#bizTypeDiv').attr({style:'display:none'});
     		}else{
     			$('#bizTypeDiv').removeAttr('style');
     		}
   	 });
	}
    /*修改 如果账号性质是境外付款账号，业务类型隐藏*/
    function bizTypeEdit(){
        var accountCharacter = $("#accountCharacterAdd").val();
        if(accountCharacter=='收结汇-境外付款账号'){
            $('#bizTypeEditDiv').attr({style:'display:none'});
        }else{
            $('#bizTypeEditDiv').removeAttr('style');
        }
    }
    /*修改 根据账户性质展示业务类型*/
    function bizTypeEditDisplay(){
            $(document).on("click",".sel-val",function(){
                var accountCharacter = $("#accountCharacterAdd").val();
                if(accountCharacter=='收结汇-境外付款账号'){
                    $('#bizTypeEditDiv').attr({style:'display:none'});
                }else{
                    $('#bizTypeEditDiv').removeAttr('style');
                }
     		});
        }

    /*批量新增*/
    $(document).on("click",".batch-add-btn",function(){
        var html = $("#upload-tpl").html();
        var win = $.dialog({
            title : "批量新增",
            content : html,
            width : 520,
            onShow : function($dialog, callback){
                //保存按钮
                $("#J_placeOrder").click(function(){
                	var requestDto = $('#f4').serialize();
                	if($("#J_placeOrder").hasClass("disabled")){
                		return false;
                	}
            	    $.ajax({     
                            type: "POST",
                            dataType: "JSON",
                            url: sysconfig.ctx+"/merchantHandle/batchAdd.htm",
                            data: requestDto,
                            success: function (data) {
                            	//若页面过期跳转至首页
                            	isIntercepted(data);
                            	
                                if(data.success == "s"){
                                	var win = $.dialog({
                                        title : "",
                                        content : "<p class='add-success'><i></i>添加成功</p>",
                                        width : 114,
                                        onShow : function($dialog, callback){
                                            setTimeout(function(){
                                                callback.close();
                                                submitQuery('none');
                                            },1000)
                                        },
                                        showClose : true,
                                        maskCss: { // 遮罩层背景
                                            opacity: 0
                                        }
                                    })
                                    win.show();
                              	  	callback.close();
                        	 	}else{
                        	 		$(".upload-files .result-text .fail .err-text").html(data.responseMsg);
                        	 		$(".upload-files .result-text .fail").show();
                					$(".upload-files .result-text .success").hide();
                					$(".upload-files .result-text .wait").hide();
                        	 	}
                            },
                            error : function() {
                            	$(".upload-files .result-text .fail .err-text").html("系统错误");
                    	 		$(".upload-files .result-text .fail").show();
            					$(".upload-files .result-text .success").hide();
            					$(".upload-files .result-text .wait").hide();
                            }
                        });
                })
                //取消按钮
                $(".cancel-btn").click(function(){
                    callback.close();
                })
            },
            showClose : true
        })
        win.show();
    });


    /*单个新增*/
    $(document).on("click",".add-btn",function(){
        var html = $("#add-tpl").html();
        var win = $.dialog({
            title : "新增境外商户",
            content : html,
            width : 520,
            onShow : function($dialog, callback){

            	validate();
            	loadOptions();
            	bizTypeDisplay();

                //保存按钮
                $("#addMerchant").click(function(){
                	if($('#f2').valid()){
                		var requestDto = $('#f2').serialize();
                    	$.ajax({     
                                type: "POST",
                                dataType: "JSON",
                                url: sysconfig.ctx+"/merchantHandle/addOrUpdate.htm",
                                data: requestDto,
                                success: function (data) {
                                	//若页面过期跳转至首页
                                	isIntercepted(data);
                                	
                                    if(data.success == "s"){
                                  	  var win = $.dialog({
                                            title : "",
                                            content : "<p class='add-success'><i></i>添加成功</p>",
                                            width : 114,
                                            onShow : function($dialog, callback){
                                                setTimeout(function(){
                                                    callback.close();
                                                    submitQuery('none');
                                                },1000)
                                            },
                                            showClose : true,
                                            maskCss: { // 遮罩层背景
                                                opacity: 0
                                            }

                                        })
                                        win.show();
                                  	  	callback.close();
                            	 	}else{	
                            	 		var win = $.dialog({
                                            title : "",
                                            content : "<p class='add-fail'><i></i>添加失败" + data.responseMsg + "</p>",
                                            width : 114,
                                            onShow : function($dialog, callback){
                                                setTimeout(function(){
                                                    callback.close();
                                                },3000)
                                            },
                                            showClose : true,
                                            maskCss: { // 遮罩层背景
                                                opacity: 0
                                            }

                                        })
                                        win.show();
                            	 	}
                                },
                                error : function() {
                                	var win = $.dialog({
                                        title : "",
                                        content : "<p class='add-fail'><i></i> 系统异常</p>",
                                        width : 114,
                                        onShow : function($dialog, callback){
                                            setTimeout(function(){
                                                callback.close();
                                            },3000)
                                        },
                                        showClose : true,
                                        maskCss: { // 遮罩层背景
                                            opacity: 0
                                        }
                                    })
                                    win.show();
                                }
                            });            		             		
                	}            	
                })
                //取消按钮
                $(".cancel-btn").click(function(){
                    callback.close();
                })                             
                var $html = $(".jr-select")
                $html.initSelect();
            },
            showClose : true
        })
        win.show();
    });

    
    /*单个修改*/
    $(document).on("click",".edit-btn",function(){
    	var html = $("#edit-tpl").html();
		var win = $.dialog({
        			title : "修改境外商户",
        			content : html,
        			width : 520,
        			onShow : function($dialog, callback){
        				validate();
        				loadOptions();
        				bizTypeEditDisplay();

        				//保存按钮
                        $("#updateMerchant").click(function(){
                        	if($('#f3').valid()	){
                        		var requestDto = $('#f3').serialize();
                         	    $.ajax({     
	                                 type: "POST",
	                                 dataType: "JSON",
	                                 url: sysconfig.ctx+"/merchantHandle/addOrUpdate.htm",
	                                 data: requestDto,
	                                 success: function (data) {
	                                	//若页面过期跳转至首页
	                                 	isIntercepted(data);
	                                	 
	                                     if(data.success == "s"){
	                                   	  var win = $.dialog({
	                                             title : "",
	                                             content : "<p class='add-success'><i></i> 修改申请已提交,待审核</p>",
	                                             width : 114,
	                                             onShow : function($dialog, callback){
	                                                 setTimeout(function(){
	                                                     callback.close();
	                                                 },1000)
	                                             },
	                                             showClose : true,
	                                             maskCss: { // 遮罩层背景
	                                                 opacity: 0
	                                             }
	
	                                        });
	                                        win.show();
	                                        setTimeout(function(){
                                                callback.close();
                                            },1000)
	                             	 	}else if(data.success == "sqt"){
		                                   	  var win = $.dialog({
		                                             title : "",
		                                             content : "<p class='add-success'><i></i> 修改成功</p>",
		                                             width : 114,
		                                             onShow : function($dialog, callback){
		                                                 setTimeout(function(){
		                                                     callback.close();
		                                                 },1000)
		                                             },
		                                             showClose : true,
		                                             maskCss: { // 遮罩层背景
		                                                 opacity: 0
		                                             }
		
		                                        });
		                                        win.show();
		                                        setTimeout(function(){
	                                                callback.close();
	                                            },1000)
		                             	 	}else{	
	                             	 		var win = $.dialog({
                                                 title : "",
                                                 content : "<p class='add-fail'><i></i> 修改失败, "+ data.responseMsg +"</p>",
                                                 width : 114,
                                                 onShow : function($dialog, callback){
                                                     setTimeout(function(){
                                                         callback.close();
                                                     },3000)
                                                 },
                                                 showClose : true,
                                                 maskCss: { // 遮罩层背景
                                                     opacity: 0
                                                 }

                                             })
                                             win.show();
	                             	 	}
	                                 },
	                                 error : function() {
	                                     var win = $.dialog({
                                             title : "",
                                             content : "<p class='add-fail'><i></i> 系统异常</p>",
                                             width : 114,
                                             onShow : function($dialog, callback){
                                                 setTimeout(function(){
                                                     callback.close();
                                                 },3000)
                                             },
                                             showClose : true,
                                             maskCss: { // 遮罩层背景
                                                 opacity: 0
                                             }

                                         })
                                         win.show();
	                                 }
	                             });
                        	}
                        })
            					//取消按钮
            				$(".cancel-btn").click(function(){
                				callback.close();
            				})
            				var $html = $(".jr-select")
            				$html.initSelect();
        					},
        		   showClose : true,
        		   onClose:function(){  
        			   var formObj = $("form[name='f0']");
        	    	    formObj.attr("action", sysconfig.ctx+"/merchantHandle/query.htm");
        	    	    formObj.submit();   
        		    }
        					
    				})
    	win.show();
		
		/*通过收款方查询详情*/
    	$.ajax({
         	type: "POST",
         	data:{"payeeMerchantCode":$(this).parents(".opration").find(".payeeMerchantCodeUpdate").val()},
         	url:sysconfig.ctx+"/merchantHandle/queryDetail.htm",
         	dataType: "JSON",
         	success: function(data) {
         		//若页面过期跳转至首页
            	isIntercepted(data);
        	 	if(data.success == "s"){
        		 	document.getElementById("payeeMerchantCodeAdd1").innerText = data.detailDto.payeeMerchantCode;
        		 	document.getElementById("payeeMerchantCodeAdd2").value = data.detailDto.payeeMerchantCode;
        		 	document.getElementById("payeeMerchantNameAdd").value = data.detailDto.payeeMerchantName;
        		 	document.getElementById("currencyAdd").value = data.detailDto.currency;
        		 	document.getElementById("payeeAccountAdd").value = data.detailDto.payeeAccount;
        		 	document.getElementById("payeeNameAdd").value = data.detailDto.payeeName;
        		 	document.getElementById("payeeBankAdd").value = data.detailDto.payeeBank;
        		 	document.getElementById("payeeBankAddAdd").value = data.detailDto.payeeBankAdd;
        		 	document.getElementById("payeeBankSwiftCodeAdd").value = data.detailDto.payeeBankSwiftCode;
        		 	document.getElementById("payeeAddressAdd").value = data.detailDto.payeeAddress;      
        		 	document.getElementById("bizTypeAdd").value = data.detailDto.bizType;
        		 	document.getElementById("accountCharacterAdd").value = data.detailDto.accountCharacter;      
        		 	document.getElementById("mobilePhoneAdd").value = data.detailDto.mobilePhone; 
        		 	bizTypeEdit();
        		 	for(var i=0;i<country.length;i++){
        		 		if(country[i].indexOf(data.detailDto.payeeCountry)>=0){
        		 			$("#country").val(country[i]);
        		 			$("#country").trigger('chosen:updated');
        		 			break;
        		 		}
        		 	}           		 		
        	 	}
	        	},
	        	error: function(msg) { 
	        	}
	    	});       		
    });
    
    jQuery.validator.addMethod("isPayeeAccount", function(value, element) {
        var payeeAccount1 = /^\S{1,35}$/;
        return this.optional(element) || (payeeAccount1.test(value));
    }, "请正确填写您的账号");
    
    
    jQuery.validator.addMethod("isCapital", function(value, element) {
        var payeeBankSwiftCode1 = /[a-z]/;
        var result= payeeBankSwiftCode1.test(value);
        return !result;
    }, "英文字符请大写");
    

    
    //表单验证
    function validate(){
        var valid=$("#f2,#f3").validate({
        	ignore:":hidden:not(select)",
            errorClass: 'tip-red',
            errorElement: 'p',
            rules: {
            	payeeMerchantName: {
                    required: true,
                    maxlength:100
                },currency: {
                    required: true
                },accountCharacter: {
                    required: true
                },payeeAccount: {
                    required:true,
                    maxlength:35,
                    isPayeeAccount:true
                },payeeName: {
                    required:true,
                    maxlength:120
                }, payeeBank: {
                    required: true,
                    maxlength:138
                },payeeBankAdd: {
                    required: true,
                    maxlength:140
                },payeeBankSwiftCode: {
                    required: true,
                    maxlength:11,
                    isCapital: true
                },payeeAddress:{
                	required: true,
                	maxlength:120
                },mobilePhone:{
                	required:true,
                	digits:true,
                	rangelength:[11,11]
                },payeeCountry:{
                	required:true
                },bizType:{
                	required:true
                }
            },
            messages: {
            	payeeMerchantName: {
                    required: '请输入收款方名称',
                    maxlength:'收款方名称长度不超过100位'	
                },currency: {
                    required: '请输入结算币种'
                },accountCharacter: {
                    required: '请选择账号性质'
                },payeeAccount: {
                    required: '请输入收款帐号',
                    maxlength:'收款帐号长度不超过35位',
                    isPayeeAccount:'请填写正确的账号'
                },payeeName: {
                    required: '请输入账户户名',
                    maxlength:'账户户名长度不超过120位'
                },payeeBank: {
                    required: '请输入收款开户行',
                    maxlength:'收款开户行长度不超过138位'
                },payeeBankAdd: {
                    required: '请输入收款开户行地址',
                    maxlength:'收款开户行地址长度不超过140位'
                },payeeBankSwiftCode: {
                    required: '请输入收款开户行swift code',
                    maxlength:'收款开户行swift code长度不超过11位'
                },payeeAddress:{
                	required: '请输入境外商户地址',
                	maxlength:'境外商户地址长度不超过120位'
                },mobilePhone:{
                	required:'请填写手机号码',
                	digits:'请填写正确的手机号码',
                	rangelength:'请填写正确的手机号码'
                },payeeCountry:{
                	required:'请选择正确的国家码'
                },bizType:{
                	required:'请选择正确的业务类型'
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

    // 文件上传
    $(document).on("change",".upload-files input[type=file]",function(e){
    	var fileAddress = $(this).val();
    	if(fileAddress ==''||fileAddress  == undefined || fileAddress == null){
    		return false;
    	}
        $("#J_placeOrder").addClass("disabled");
        $.ajaxFileUpload({  
            url: sysconfig.ctx+"/merchantHandle/fileSubmit.htm",  
            type : 'POST',
   			fileElementId : 'file-btn',
            dataType: 'text',
            success: function (data) {
            	//若页面过期跳转至首页
            	isIntercepted(data);
            	
            	var obj = eval('(' + data+ ')');
            	if(obj.success){
            		$('.upload-files').find('.file-path').val(fileAddress);
	            	$('.upload-files').find('.file-path[name="fileAddress"]').val(obj.fileAddress);
	            	$("#uploadSuccess").show();
	            	$("#uploadFail").hide();
	            	$("#wait").hide();
	            	if($("#J_placeOrder").hasClass("disabled"))
	                    $("#J_placeOrder").removeClass('disabled'); 
            	} else {
            		$('.upload-files').find('.file-path').val(fileAddress);
            		$("#uploadFail .err-text").html(obj.message);
                	$("#uploadFail").show();
                	$("#uploadSuccess").hide();
                	$("#wait").hide();
            	}           	
            },  
            complete: function(xmlHttpRequet){
            	$("input[name='file']").replaceWith('<input type="file" id="file-btn" name="file">');
            },
            error: function (data) {  	
            }  
        }); 
    });
    //保存按钮
                        
    $(document).on("click",".J_set",function(){
        var valid=validate();
        valid.form();
    })
    
});