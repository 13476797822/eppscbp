<meta charset="UTF-8">
	<title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">   
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/quickQuery.css" />
	<div class="batch-payment">
        <#include "/screen/title.ftl">
        <div class="content">
        	<h3 class="content-title">单笔付汇</h3>
            <!-- 填写付汇信息 -->
        	<ul class="axis clearfix">
                <li class="step active"><span class="icon">1</span>填写结算信息<span class="line"></span></li>
                <li class="step"><span class="icon">2</span>支付<span class="line"></span></li>
                <li class="step"><span class="icon">3</span>支付结果</li>
            </ul>
            <form id="fillCross" id="single-pay"  method="post" name="f0">
                <ul class="input-list" id="accountContent clearfix">
                	<li class="input-item clearfix">
                        <span class="input-title">缴费类型 ：</span>
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="input select-input input_text empty" name="bizDetailType" value="${criteria.bizDetailType}">
                            <input type="text" readonly class="input select-input input_text empty" value="学费" key="">
                            <ul class="select-box">
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="${criteria.bizDetailType}" value="学费">学费
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="clear"></div>
                    </li>
                    <li class="input-item receive-name clearfix">
                        <span class="input-title"><i class="red-warn">*</i>收款方 ：</span>
                        <input type="text" class="quickQuery$focus input show" name="payMes" id="receiveName" value="${criteria.payMes}"  placeholder="收款方编码或收款方名称" autocomplete="off">
                        <div class="quickQuery$focus"></div>
                        <input type="text" id="payeeMerchantCode" name="payeeMerchantCode" value="${criteria.payeeMerchantCode}" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
                        <input type="hidden" id="productType" name="productType" value="FS01">
                        <div class="clear"></div>
                    </li>
                    <li class="input-item clearfix">
                        <span class="input-title"><i class="red-warn">*</i>申请总金额 ：</span>
                        <input type="text" name="payAmt" class="input" id="applyMoney" <#if criteria.payAmt != 0> value="${criteria.payAmt}" </#if> placeholder="申请金额"> <span class="text"></span> <span class="balance-btn">余额查询</span>
                        <input type="hidden" id="cur" name="cur" value="${criteria.cur}">
                        <input type="hidden" id="curName" name="curName" value="${criteria.curName}">
                        <div class="clear"></div>
                    </li>
                    <li class="input-item clearfix">
                        <span class="input-title"><i class="red-warn">*</i>是否全额到账 ：</span>
                        <input type="radio" class="not-full-account radio active" name="amtType" value="00" <#if criteria.amtType == "00" || criteria.amtType == "">checked</#if>>非全额到账</input>
                		<input type="radio" class="full-account radio" name="amtType" value="01" <#if criteria.amtType == "01">checked</#if>>全额到账 </input><span class="full-money hide" id ="fullAmt"></span>
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
                    <li class="input-item clearfix">
                        <span class="input-title"><i class="red-warn">*</i>交易笔数 ：</span>
                        <input type="text" class="input remarks show" name="detailAmount" min="1" max="20000" id="detailAmount" <#if criteria.detailAmount != 0> value="${criteria.detailAmount}" </#if> placeholder="小于等于20000笔">
                        <div class="clear"></div>
                    </li>
                    <input type="text" name="requestUrl" value="/overseasPay/studyPay/fileSubmit.htm" class="input file-path" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
                    <li class="input-item upload-files"> 
                        <span class="input-title"><i class="red-warn">*</i>结算明细文件上传 ：</span>
                        <input readonly="readonly" type="text" name="filePath" value="${criteria.filePath}" class="input file-path" id="file">
                        <input type="text" name="fileAddress" value="${criteria.fileAddress}" class="input file-path" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">                       
                        <div class="file-operation">
                            <a class="up" href="javascript:;">选择文件
                            <form id="uploadForm" enctype="multipart/form-data">  
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
                        <a href="javascript:;" class="submit-btn <#if responseMsg??>disabled</#if>" id="J_placeOrder">提交订单</a>
                        <#if responseMsg??>
                        <i class="red-warn">${responseMsg}</i>
                        </#if>
                    </li>
                </ul> 
            </form>
    	</div>        
    </div>
    <!-- 购付汇支付页面 -->
    <script type="text/html" id="balance-tpl">
        <div class="balance">
            <table>
                <thead>
                    <tr>
                        <th>币种</th>
                        <th>账户余额({{curName}})</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{{curName}}</td><td>{{exBalance}}</td>
                    </tr>
                </tbody>
            </table>
            <a href="javascript:;" class="submit-btn">确 定</a>
        </div>
            
    </script>
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
			var html = '<div class="loading-box"><img src="'+sysconfig.ctx+'/style/images/submit-loading.gif"><p>提交中...</p></div>';
			$("body").append(html);
    		$("#J_placeOrder").addClass("disabled");
    		var formObj = $("form[name='f0']");
    	    formObj.attr("action", sysconfig.ctx+"/overseasPay/studyPay/applySubmit.htm");
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
						$(":input[name='payAmt']").parent().find(".text").html(data.curName);
            		 	document.getElementById("cur").value = data.cur;	 	
            		 	document.getElementById("curName").value = data.curName;	
            		 	var amtType = $('input[name="amtType"]:checked ').val();
            		 	if(data.amt == "N/A" && amtType == "01"){
            		 		$("#fullAmt").parent().find(".red-warn").remove();
    						$("#fullAmt").parent().append("<p class='red-warn'>该币种的全额到账费用不存在</p>");
            		 	}else{
            		 		document.getElementById("fullAmt").innerText = "("+data.amt+data.curName+")";
            		 	}
            	 	}else{
            	 	    $("#receiveName").parent().append("<p class='red-warn'>"+data.responseMsg+"</p>")	   	 	
            	 	}
 	        	},
 	        	error: function(msg) {    
 	        	}
 	    	});      
    	}
    	
	    //余额查询
	    $(document).on("click",".balance-btn",function(){
	    	var cur = $("#cur").val();
	    	var curName = $("#curName").val();
	    	var payeeMerchantCode = $("#payeeMerchantCode").val();
	    	if($(".receive-name p.tip-red").css("display") == "block"||$(".receive-name p").hasClass("red-warn")||payeeMerchantCode ==""){
	    	 	return false;
	    	}
	        $.ajax({
	            type: "POST",
	            data:{"cur":cur,"curName":curName},
	            url:sysconfig.ctx+"/overseasPay/studyPay/queryBalance.htm",
	            dataType: "json",
	            success: function(data) {
	            	var html,title;
	            	if(data.success == "s"){
	            		html = template('balance-tpl',data);
	            		title = "账户余额";
	            	}else{
	            		html =	"<p style='text-align:center;padding:20px 0;font-size: 16px;'>"+data.responseMsg+"</p>"
        						+"<a href='javascript:;' class='submit-btn' style='margin-left:175px;'>确认</a>";
	            		title = "错误提示";
               	 	} 
               	 	var win = $.dialog({
	                    title : title,
	                    content : html,
	                    width : 500,
	                    onShow : function($dialog, callback){
	                    	$(".submit-btn").click(function(){
	                    		window.onbeforeunload=function(){
					                return;
					            };
	                    		callback.close();
	                    		return false; 
	                    	});
	                    },
	                    showClose : true
	                });
               	 	win.show();
	            },
	            error: function(msg) {        
	            }
	        });
	
	    })
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
    