<meta charset="UTF-8">
    <title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/dialog.css">
    <div class="batch-payment">
        <#include "/screen/title.ftl">
        <div class="content">
            <!-- 单笔购付汇支付页面 -->
            <h3 class="content-title">单笔付汇</h3>
            <!-- 填写购付汇信息 -->
            <ul class="axis clearfix">
                <li class="step"><span class="icon">1</span>填写结算信息<span class="line"></span></li>
                <li class="step active"><span class="icon">2</span>支付<span class="line"></span></li>
                <li class="step"><span class="icon">3</span>支付结果</li>
            </ul>
            <form id="fillCross" method="post" name="f0">
	            <div class="time-tip"> 
	                <img src="${ctx}/style/images/icon_sigh_16.png"> 请在<em class="end-time" id="end-time">${criteria.orderExpiredTimeStr}</em> 前完成支付，若超时请刷新汇率后再支付   
	                <div class="surplus">支付剩余时间<em class="surplus-time" id="surplus-time" data-time="${criteria.rateExpiredTimeS}"></em></div>
	                <div class="over-time hide">已超时<em class="over J_refresh" id="J_refresh"><i></i>刷新汇率</em></div>
	            </div>
	            <ul class="pay-info-list clearfix">
	                <li class="input-item first-item">
	                    <span class="info-title">应付总金额 ：</span>
	                    <span class="info-content">${criteria.paymentRmbAmt}人民币</span>+<span class="info-content">${criteria.paymentExAmt}${criteria.applyCurName}</span>
	                	<input type="hidden" id ="paymentRmbAmt" name="paymentRmbAmt" value="${criteria.paymentExAmt}">
	                	<input type="hidden" id ="paymentExAmt" name="paymentExAmt" value="${criteria.paymentExAmt}">
	                </li>
	                <#if criteria.fullExAmt != "0.00">
		                <li class="input-item second-item">
		                    <span class="info-title">汇率 ：</span>
		                    <span class="info-content" id="exchangeRate">${criteria.exchangeRate}</span>
		                </li>
		            </#if>
	                <li class="input-item">
	                    <span class="info-title">申请金额 ：</span>
	                    <span class="info-content" id="applyAmt">
	                    ${criteria.applyAmt}${criteria.applyCurName}
	                    </span>
	                </li>
	                <li class="input-item">
	                    <span class="info-title">手续费 ：</span>
	                    <span class="info-content" id ="feeAmt">${criteria.feeAmt}人民币</span>
	                </li>
	                <li class="input-item">
	                    <span class="info-title">全额到账费 ：</span>
	                   	<span class="info-content" id="fullExAmt">${criteria.fullExAmt}${criteria.remitCurName}(${criteria.fullRmbAmt}人民币)</span>
	                </li>
	            </ul>
	            <div class="tip-text">提示：为保证订单有效时间内支付成功，仅支持余额支付，请先行充值</div>
	            <a href="javascript:;" class="submit-btn" id="J_pay">去支付</a>
	            <input type="hidden" id ="businessNo" name="businessNo" value="${criteria.businessNo}">
	            <input type="hidden" id ="cur" name="cur" value="${criteria.applyCur}">
	    	</form>
		</div>
    </div>
    <script type="text/html" id="purchase-pay-tpl">
	        <div class="pay-box">
	            <div class="surplus">支付剩余时间<em class="surplus-time"> </em></div>
	            <div class="over-time hide">支付超时，请刷新汇率后再试</div>
	            <p>应付总金额</P>
	            <p><em>${criteria.paymentRmbAmt}</em>人民币+<em>${criteria.paymentExAmt}</em>${criteria.applyCurName}</P>
	            <p>账户余额（人民币）：<span>{{balance}}</span>&nbsp;元</P>
	            <p>账户余额（外币）：<span>{{exBalance}}</span>&nbsp;${criteria.applyCurName}</P>
	            <p><input type="password" id = "password" placeholder="请输入支付密码" autocomplete="off"></P>
	            <a href="javascript:;" class="submit-btn" id="J_pay_confirm">确认支付</a>
				<input type="hidden" id ="key" name="key" value="{{key}}">
	        </div> 
    </script> 
    <script type="text/javascript">   	
    	//去支付按钮事件
    	function submitPayment(){
			var cur = $("#cur").val();
    		var paymentRmbAmt = $("#paymentRmbAmt").val();
    		var paymentExAmt = $("#paymentExAmt").val();
        	$.ajax({
            	type: "POST",
            	data:{"cur":cur},
            	url:sysconfig.ctx+"/overseasPay/studyPay/submitPayment.htm",
            	dataType: "JSON",
            	success: function(data) {
            		var html;
            		if(data.success == "s"){
	            		var balance = data.balance;
	            		var exBalance = data.exBalance;
	            	 	html = template('purchase-pay-tpl',data);
	                 	var win = $.dialog({
	                     	title : "支付",
	                     	content : html,
	                     	width : 500,
	                     	onShow : function($dialog, callback){
	            				if(parseFloat(paymentRmbAmt)>parseFloat(balance)||parseFloat(paymentExAmt)>parseFloat(exBalance)){
	            					$("#J_pay_confirm").addClass("disabled");
	            					$(".red-warn").remove();
	            					$("#J_pay_confirm").after("<i class='red-warn'>&nbsp;&nbsp;余额不足</i>");
	            				}
	                 	},
	               		showClose : true
	            		});
	            	}else{
	            		html =	"<p style='text-align:center;padding:20px 0;font-size: 16px;'>"+data.responseMsg+"</p>"
        						+"<a href='javascript:;' class='submit-btn' style='margin-left:175px;'>确认</a>";
        				var win = $.dialog({
	                     	title : "错误提示",
	                     	content : html,
	                     	width : 500,
	                     	onShow : function($dialog, callback){
	            				$(".submit-btn").click(function(){
	                    		callback.close();
	                    		});
	                 		},
	               			showClose : true
	            		});
	            	}   
	            	win.show();          		
	        	},
	        	error: function(msg) {    
	        	}
	    	});     
    	}
    	//刷新汇率按钮事件
    	function refreshRate(){
    		var formObj = $("form[name='f0']");
    	    formObj.attr("action", sysconfig.ctx+"/overseasPay/studyPay/refreshRate.htm");
    	    formObj.submit();
    	}
    	//提交支付按钮事件
    	function confirmPayment(){
	        var password = $("#password").val(),key = $("#key").val(),businessNo = $("#businessNo").val();
	        $.ajax({
	            type: "POST",
	            data:{"password":password,"key":key,"businessNo":businessNo},
	            url:sysconfig.ctx+"/overseasPay/studyPay/confirmPayment.htm",
	            dataType: "JSON",
	            success: function(data) {
	            	if(data.success == "s"||data.success == "pf"||data.success == "o"){
	            		
	            		var temp = document.createElement("form");
  						temp.action = sysconfig.ctx+"/overseasPay/overseasPayInit/payResult.htm";
  						temp.method = "post";
  						
  						var payResult = document.createElement("textarea");
    					payResult.name = "payResult";
    					payResult.value = data.success;
    					temp.appendChild(payResult);
    					
    					var meg = document.createElement("textarea");
    					meg.name = "meg";
    					meg.value = data.responseMsg;
    					temp.appendChild(meg);
    					
    					document.body.appendChild(temp);
    					temp.submit();
	            		
	            		
	           	 	}else{
	           	 		$(".red-warn").remove();
	           	 		$("#J_pay_confirm").after("<i class='red-warn'>&nbsp;&nbsp;"+data.responseMsg+"</i>");
	           	 	}
		        },
		        error: function(msg) {            
		        }
		    });
    	}
	</script>
    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/main" src="${ctx}/scripts/lib/require/require.min.js"></script>