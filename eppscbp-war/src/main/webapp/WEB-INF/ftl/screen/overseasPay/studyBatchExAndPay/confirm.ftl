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
            <h3 class="content-title">批量购付汇</h3>
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
	                    <span class="info-content">${criteria.paymentRmbAmt}人民币</span>
	                	<input type="hidden" id ="paymentRmbAmt" name="paymentRmbAmt" value="${criteria.paymentRmbAmt}">
	                </li>
	                <li class="input-item">
	                    <span class="info-title">申请金额 ：</span>
	                    <span class="info-content" id="applyAmt">
	                    ${criteria.applyAmt}人民币
	                    </span>
	                </li>
	                <li class="input-item">
	                    <span class="info-title">手续费 ：</span>
	                    <span class="info-content" id ="feeAmt">${criteria.feeAmt}人民币</span>
	                </li>
	                <li class="input-item">
	                    <span class="info-title">全额到账费 ：</span>
	                    <span class="info-content" id="fullExAmt">${criteria.fullRmbAmt}人民币</span>
	                </li>
	            </ul>
	            <div class="tip-text">提示：为保证订单有效时间内支付成功，仅支持余额支付，请先行充值</div>
	            <a href="javascript:;" class="submit-btn" id="J_pay">去支付</a>
	            <input type="hidden" id ="businessNo" name="businessNo" value="${criteria.businessNo}">
	            <input type="hidden" id ="cur" name="cur" value="${criteria.applyCur}">
	            <!-- 批量购付汇订单详情列表 -->
	            <div class="order-detail-box">
	                <h3 class="content-title">支付信息</h3>
	                <div class="table-box">
	                    <div class="result-table">  
                    		<table style="table-layout:  fixed;">
	                            <thead>
	                                <tr>
	                                    <th width="150">业务明细单号</th>
	                                    <th>收款商户编码</th>
	                                    <th width="85">收款商户名称</th>
	                                    <th width="85">客户名称</th>
	                                    <th class="money">申请金额</th>
	                                    <th width="55">汇率</th>
	                                    <th class="money">手续费</th>
	                                    <th class="money">全额到账金额</th>
	                                </tr>
	                            </thead>
	                            <tbody id="resultList">  
	                            	
	                            </tbody>
	                        </table>
	                    </div>
                    	<div class="page" id="pageClass">
		                    <div class="posi-right" >
		                        <span class="fl">每页10条</span>                    
		                        <a class="prev-page page-num border fl" href="javascript:;" ><i class="arrow-left"></i>上一页</a>
		                        <a class="next-page page-num border fl" href="javascript:;" >下一页<i class="arrow-right"></i></a>
		                        <span class="fl m-left">第<span id="nowPage"></span>页/共<span id="pages"></span>页   &nbsp;&nbsp;	向第</span><input type="text" name="" class="fl page-num input-num" id="targetPage" ><span class="fl">页</span>
		                        <a class="fl goto border" href="javascript:;" onclick="targetQuery();">跳转</a>
		                    </div>
		                    <input style="display:none" id="currentPage" name="currentPage" value="${page.currentPage}"></input>
		                    <input style="display:none" id="pageSize" value="${page.pages}" name="pageSize"></input>
		             	</div>
	                </div>
	            </div>         
	    	</form>
		</div>
    </div>
    <script type="text/html" id="purchase-pay-tpl">
	        <div class="pay-box">
	            <div class="surplus">支付剩余时间<em class="surplus-time"> </em></div>
	            <div class="over-time hide">支付超时，请刷新汇率后再试</div>
	            <p>应付总金额（人民币）</P>
	            <p><em>${criteria.paymentRmbAmt}</em>元</P>
	            <p>账户余额（人民币）：<span>{{balance}}</span>&nbsp;元</P>
	            <p><input type="password" id = "password" placeholder="请输入支付密码" autocomplete="off"></P>
	            <a href="javascript:;" class="submit-btn" id="J_pay_confirm">确认支付</a>
				<input type="hidden" id ="key" name="key" value="{{key}}">
	        </div> 
    </script> 
    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/main" src="${ctx}/scripts/lib/require/require.min.js"></script>
    <script type="text/javascript"> 
    $(function(){ 
    	query();
    	//分页查询,上一页
    	$(".prev-page").on("click",function(event){  
            event.preventDefault();         
            var currentPage=$('#currentPage').val(); 
            var nextPage=parseInt(currentPage)-1;
            //首页无法点击上一页
            if(currentPage==1){
            	return;
            }else{ 
            	document.getElementById("currentPage").value=nextPage;          	
            	query();         
            }            
        }); 	 	
    	//分页查询,下一页
    	$(".next-page").on("click",function(event){  
            event.preventDefault();  
            var currentPage=$('#currentPage').val(); 
            var nextPage=parseInt(currentPage)+1;           
            var pages=parseInt($('#pageSize').val())
            //末页无法点击下一页
            if(currentPage==pages){
            	return;
            }else{          	
            	document.getElementById("currentPage").value=nextPage;  
            	        	
            	query();            
            }            
         });                
    });  
    	//分页查询,指定页
        function targetQuery(){
    		var currentPage=parseInt($('#targetPage').val()); 
            var pageSize=parseInt($('#pageSize').val());
        	if(currentPage === "" || currentPage == null){
        		alert("请输入页数!");
        	}else if(currentPage>pageSize){
        		alert("指定页数不能超过最大页数!");
        	}else if(currentPage<=0){
        		alert("指定页数不能小于0!");
        	}else if(isNaN(currentPage)){
        		alert("请输入数字!");
        	}else{
        		document.getElementById("currentPage").value=currentPage;          	
        		query();    
        	}	                      
    	} 
    	
    	function query(){
			var currentPage = $("#currentPage").val();
    		var businessNo = $("#businessNo").val();
        	$.ajax({
            	type: "POST",
            	data:{"currentPage":currentPage,"businessNo":businessNo},
            	url:sysconfig.ctx+"/overseasPay/studyBatchExAndPay/query.htm",
            	dataType: "JSON",
            	success: function(data) {
            		 if(data.success == "s"){
            			var pageObject = data.page,text="";
            			document.getElementById("currentPage").value = pageObject.currentPage;
            			document.getElementById("pageSize").value = pageObject.pages;
   						$('#nowPage').html(pageObject.currentPage); 
            			$('#pages').html(pageObject.pages); 
            			var list = new Array();
            			list = data.resultList;
            			if(list){
                        	for(var i = 0;i<list.length;i++){  //循环LIST
                              	var object = list[i];//获取LIST里面的对象
								text=text+"<tr>"+
									"<td title='"+object.tradeOrderNo+"'>"+object.tradeOrderNo+"</td>"+
                                    "<td>"+object.payeeMerchantCode+"</td>"+
                                    "<td width='85' title='"+object.payeeMerchantName+"'>"+object.payeeMerchantName+"</td>"+
                                    "<td title='"+object.personalName+"'>"+object.personalName+"</td>"+
                                    "<td title='"+object.applyAmt+"("+object.applyCurName+")' class='money'>"+object.applyAmt+"("+object.applyCurName+")</td>"+
                                    "<td>"+object.exchangeRateStr+"</td>"+
                                    "<td class='money'>"+object.feeAmt+"(人民币)</td>"+
                                    "<td class='money'>"+object.fullExAmt+"("+object.remitCurName+")</td>"+
                                    "</tr>";	
                            }
                       }
                       $('#resultList').html(text); 
                       $(".prev-page").removeClass("disable");  
                       $(".next-page").removeClass("disable"); 
                       if(pageObject.currentPage==1){
                       		$(".prev-page").addClass("disable");
                       }
                       if(pageObject.currentPage==pageObject.pages){
                       		$(".next-page").addClass("disable");
                       }
            		}         			
	        	},
	        	error: function(msg) {    
	        	}
	    	});
		}
		
    	//去支付按钮事件
    	function submitPayment(){
			var cur = $("#cur").val();
    		var paymentRmbAmt = $("#paymentRmbAmt").val();
        	$.ajax({
            	type: "POST",
            	data:{"cur":cur},
            	url:sysconfig.ctx+"/overseasPay/studyBatchExAndPay/submitPayment.htm",
            	dataType: "JSON",
            	success: function(data) {
            		var html;
            		if(data.success == "s"){
	            		var balance = data.balance;
	            	 	html = template('purchase-pay-tpl',data);
	                 	var win = $.dialog({
	                     	title : "支付",
	                     	content : html,
	                     	width : 500,
	                     	onShow : function($dialog, callback){
	            				if(parseFloat(paymentRmbAmt)>parseFloat(balance)){
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
    	    formObj.attr("action", sysconfig.ctx+"/overseasPay/studyBatchExAndPay/refreshRate.htm");
    	    formObj.submit();
    	}
    	//提交支付按钮事件
    	function confirmPayment(){
	        var password = $("#password").val(),key = $("#key").val(),businessNo = $("#businessNo").val();
	        $.ajax({
	            type: "POST",
	            data:{"password":password,"key":key,"businessNo":businessNo},
	            url:sysconfig.ctx+"/overseasPay/studyBatchExAndPay/confirmPayment.htm",
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
    					
    					var productType = document.createElement("textarea");
    					meg.name = "productType";
    					meg.value = "FB11";
    					temp.appendChild(productType);
    					
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
    