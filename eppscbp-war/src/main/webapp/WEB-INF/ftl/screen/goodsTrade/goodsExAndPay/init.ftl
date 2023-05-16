<meta charset="UTF-8">
	<title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <#include "/commons/fd.ftl">
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/quickQuery.css" />
	<div class="batch-payment">
        <#include "/screen/title.ftl">
        <div class="content">
            <!-- 单笔购付汇填写页面 -->
            <h3 class="content-title">单笔购付汇</h3>
            <!-- 填写购付汇信息 -->
        	<ul class="axis clearfix">
                <li class="step active"><span class="icon">1</span>填写结算信息<span class="line"></span></li>
                <li class="step"><span class="icon">2</span>支付<span class="line"></span></li>
                <li class="step"><span class="icon">3</span>支付结果</li>
            </ul>
            <form id="fillCross" method="post" name="f0">
               <ul class="input-list" id="accountContent">
               		<li class="input-item receive-name clearfix">
                        <span class="input-title"><i class="red-warn">*</i>平台名称 ：</span>                
                        <input type="text" class="input" name="platformName" id="platformName" value="${criteria.platformName}" placeholder="4~8个字符,只支持汉字/swift字符集" autocomplete="off">
                        <div class="clear"></div>
                    </li>
                    <li class="input-item receive-name clearfix">
                        <span class="input-title"><i class="red-warn">*</i>收款方 ：</span>                
                        <input type="text" class="quickQuery$focus input show" name="payMes" id="receiveName" value="${criteria.payMes}"  placeholder="收款方编码或收款方名称" autocomplete="off">
                        <div class="quickQuery$focus"></div>
                        <input type="text" id="payeeMerchantCode" name="payeeMerchantCode" value="${criteria.payeeMerchantCode}" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
                        <input type="hidden" id="productType" name="productType" value="FS11">
                        <input type="hidden" id="pcToken" name="pcToken">
                        <div class="clear"></div>
                    </li>
                    <li class="input-item clearfix">
                        <span class="input-title"><i class="red-warn">*</i>购汇方式 ：</span>
                        <input type="radio" class="rmb-buy radio <#if criteria.exchangeType == "01" || criteria.exchangeType == "">active</#if>" name="exchangeType" value="01" <#if criteria.exchangeType == "01" || criteria.exchangeType == "">checked</#if>>指定人民币购汇</input>
                		<input type="radio" class="foreign-buy radio <#if criteria.exchangeType == "02">active</#if>" name="exchangeType" value="02" <#if criteria.exchangeType == "02">checked</#if>>指定外币购汇 </input>
                        <em class="warn-img active">
                            <i></i>
                            <span class="warn-text">如支付的人民币金额固定，需要兑换相应的外币，选择指定人民币购汇；如要求兑换固定的外币金额，则选择指定外币购汇。</span>
                        </em>
                    </li>
                    <li class="input-item clearfix">
                        <span class="input-title"><i class="red-warn">*</i>申请总金额 ：</span>
                        <input type="text" name="payAmt" class="input" id="applyMoney" <#if criteria.payAmt != 0> value="${criteria.payAmt}" </#if>  placeholder="申请金额"><i id="curName">人民币</i>
                        <input type="hidden" id="cur" name="cur" value="${criteria.cur}">
                        <input type="hidden" name="curName" value="${criteria.curName}">
                        <div class="clear"></div>
                    </li>
                    <li class="input-item clearfix ifAll">
                        <span class="input-title"><i class="red-warn">*</i>是否全额到账 ：</span>
                        <input type="radio" class="not-full-account radio active disabled" name="amtType" value="00" <#if criteria.amtType == "00" || criteria.amtType == "">checked</#if>>非全额到账</input>
                		<input type="radio" class="full-account radio disabled" name="amtType" value="01" <#if criteria.amtType == "01">checked</#if>>全额到账 </input><span class="full-money hide" id ="fullAmt"></span>
                   		<em class="warn-img active">
                            <i></i>
                            <span class="warn-text">我司收取用于境外中转行费用支出，以实现汇款金额全额到达收款人账户。</span>
                        </em>
                    </li>
                    <li class="input-item clearfix">
                        <span class="input-title"><i class="red-warn">*</i>交易附言 ：</span>
                        <textarea type="text" class="input show" name="remark" maxlength="140" placeholder="不超过140个字符，只支持英文字母、英文符号和数字" height="74" id="postscript">${criteria.remark}</textarea> 
                        <div class="clear"></div>
                    </li>                  
                    <input type="text" name="requestUrl" value="/goodsTrade/goodsExAndPay/fileSubmit.htm" class="input file-path" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
                    <li class="input-item clearfix">
                        <span class="input-title"><i class="red-warn">*</i>交易笔数 ：</span>
                        <input type="text" class="input remarks show" name="detailAmount" min="1" max="20000" id="detailAmount" <#if criteria.detailAmount != 0> value="${criteria.detailAmount}" </#if> placeholder="小于等于20000笔">
                        <div class="clear"></div>
                    </li>
                    <li class="input-item upload-files"> 
                        <span class="input-title"><i class="red-warn">*</i>结算明细文件上传 ：</span>
                        <input readonly="readonly" type="text" name="filePath" value="${criteria.filePath}" class="input file-path" id="file">
                        <input type="text" name="fileAddress" class="input file-path" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">                       
                        <div class="file-operation">
                        	<form id="uploadForm" enctype="multipart/form-data"> 
                            <a class="up" href="javascript:;">选择文件
							<input type="file" id="file-btn" name="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"></a>
                            </form>
                            <a href="javascript:void(0)" class="download" id="download">下载模板</a>
                        </div>
                        <!-- 上传文件报错 -->
						<div class="result-text">
							<div class="success hide">
								<img src="${ctx}/style/images/icon_success_24.png">
								<p class="title">上传成功</p>
							</div>
							<div class="fail hide">
								<img src="${ctx}/style/images/icon_error_32.png">
								<p class="title">上传失败，请修改后重新上传</p>
								<span class="err-text"></span>
							</div>
						</div>
                        <div class="clear"></div>				
                    </li>
                    
                    
                    <li class="btn clearfix">
                        <a href="javascript:void(0)" class="submit-btn <#if responseMsg??>disabled</#if>" id="J_placeOrder" >提交订单</a>
                        <#if responseMsg??>
                        <i class="red-warn">${responseMsg}</i>
                        </#if>
                    </li>
                </ul> 
            </form>
        </div>
    </div>
    <script type="text/javascript">
    	document.onclick=function(e){
			var ev = e || window.event;  
	        var target = ev.target || ev.srcElement;  
	        if (target.className != "submit-btn" && target.className != "download") {  
	         	window.onbeforeunload=function(e){
	         		e = e || window.event;
	         		if(e){
				        e.returnValue = '';
				    }
	                return '';
	            }
	        }
		}
   		
		//下载模板
		function download(){
			window.onbeforeunload=function(){
                return;
            };
 			window.location.href=sysconfig.ctx+"/template/file/apply_template.xlsx";
 			return false; 
    	}
    	//提交订单
		function submitOrder(){
			window.onbeforeunload=function(){
                return;
            };
            $("#pcToken").val(_dfp.getToken());
			var html = '<div class="loading-box"><img src="'+sysconfig.ctx+'/style/images/submit-loading.gif"><p>提交中...</p></div>';
			$("body").append(html);
    		$("#J_placeOrder").addClass("disabled");
    		var formObj = $("form[name='f0']");
    	    formObj.attr("action", sysconfig.ctx+"/goodsTrade/goodsExAndPay/applySubmit.htm");
    	    formObj.submit(); 
    	    //$("#J_placeOrder").removeAttr('disabled');  
    	    //$(".loading-box").remove(); 
            return false;  
    	}
    	//校验输入收款方信息
    	function queryMerchantMeg(){
			var message = $("#receiveName").val(),productType = $("#productType").val();
			if(message ==''||message  == undefined || message == null){
				$("#receiveName").parent().append("<p class='red-warn'>请输入收款方编码或名称</p>");
				return false; 
			}
	    	$.ajax({
             	type: "POST",
             	data:{"message":message,"productType":productType},
             	url:sysconfig.ctx+"/merchantHandle/queryMerchantMeg.htm",
             	dataType: "JSON",
             	success: function(data) {
             		document.getElementById("fullAmt").setAttribute("data-type",data.amt);
             		document.getElementById("payeeMerchantCode").value = "";
             		$("#receiveName").parent().find("p.red-warn").remove();
            	 	if(data.success == "s"){
            		 	document.getElementById("payeeMerchantCode").value = data.payeeMerchantCode;
            		 	if($(".rmb-buy").hasClass("active")){
            		 		document.getElementById("curName").innerText = "人民币";
            		 	}else{
            		 		document.getElementById("curName").innerText = data.curName;
            		 	}
            		 	document.getElementsByName("curName").value=data.curName;
            		 	document.getElementById("cur").value = data.cur;
            		 	var amtType = $('input[name="amtType"]:checked ').val();
            		 	if(data.amt == "N/A" && amtType == "01"){
            		 		$("#fullAmt").parent().find(".red-warn").remove();
    						$("#fullAmt").parent().append("<p class='red-warn'>该币种的全额到账费用不存在</p>");
            		 	}else{
            		 		document.getElementById("fullAmt").innerText = "("+data.amt+data.curName+")";
            		 		if(amtType == "01"){
            		 			$("#fullAmt").show();
            		 		} 
            		 	}
            	 	}else{	
            	 		$("#receiveName").parent().append("<p class='red-warn'>"+data.responseMsg+"</p>");
            	 		document.getElementById("curName").innerText = "";
            	 	}
 	        	},
 	        	error: function(msg) {    
 	        	}
 	    	});      
    	}
	</script>
    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/main" src="${ctx}/scripts/lib/require/require.min.js"></script>
    <script src="${ctx}/scripts/lib/select/quickQuery-packer.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">    	
		window.onload = function(){
			var merchantArray = JSON.parse('${Session["merchantInfoList"]}');
			if(merchantArray[0] == ",,"){
				merchantArray[0][0] = "0";
				merchantArray[0][2] = "无收款方信息";
			}
			if(merchantArray){
				$quickQuery(merchantArray);
			}
		}
	</script>
  