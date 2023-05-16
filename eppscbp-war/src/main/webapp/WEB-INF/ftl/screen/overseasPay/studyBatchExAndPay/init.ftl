<meta charset="UTF-8">
	<title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">     
	<div class="batch-payment">
        <#include "/screen/title.ftl">
        <div class="content">
            <h3 class="content-title">批量购付汇</h3>
            <!-- 填写购付汇信息 -->
        	<ul class="axis clearfix">
                <li class="step active"><span class="icon">1</span>填写结算信息<span class="line"></span></li>
                <li class="step"><span class="icon">2</span>支付<span class="line"></span></li>
                <li class="step"><span class="icon">3</span>支付结果</li>
            </ul>
            <form id="fillCross" method="post" name="f0">
           	    <input type="text" name="requestUrl" value="/overseasPay/studyBatchExAndPay/fileSubmit.htm" class="input file-path" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">        	    
                <ul class="input-list" id="accountContent"> 
                	 <li class="input-item upload-files"> 
                        <span class="input-title"><i class="red-warn">*</i>结算明细文件上传 ：</span>
                        <input readonly="readonly" type="text" name="filePath" value="${filePath}" class="input file-path" id="file">
                        <input type="text" name="fileAddress" value="${fileAddress}" class="input file-path" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;"> 
                        <input type="text" name="detailAmount" value=<#if detailAmount??>"${detailAmount}"<#else>"0"</#if> class="input file-path" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">   
                        <input type="text" name="payAmt" value="1.11" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">                   
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
                    <li class="btn">
                        <a href="javascript:;" class="submit-btn <#if responseMsg??>disabled</#if>" id="J_placeOrder" >提交订单</a>
                        <#if responseMsg??>
                        	<div class="fail" style="max-width:250px;max-height:100px;overflow-y:auto;padding:5px;">
								<span class="err-text"><i class="red-warn">${responseMsg}</i></span>
							</div>
					
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
 			window.location.href=sysconfig.ctx+"/template/file/batch_apply_template.xlsx";
 			return false; 
    	}
    	//提交订单
		function submitOrder(){
			window.onbeforeunload=function(){
                return;
            };
			var html = '<div class="loading-box"><img src="'+sysconfig.ctx+'/style/images/submit-loading.gif"><p>提交中...</p></div>';
			$("body").append(html);
    		$("#J_placeOrder").attr('disabled','disabled');
    		var formObj = $("form[name='f0']");
    	    formObj.attr("action", sysconfig.ctx+"/overseasPay/studyBatchExAndPay/applySubmit.htm");
    	    formObj.submit(); 
    	    $("#J_placeOrder").removeAttr('disabled'); 
    	    $(".loading-box").remove();
            return false; 
    	}
	</script>
    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/main" src="${ctx}/scripts/lib/require/require.min.js"></script>

    