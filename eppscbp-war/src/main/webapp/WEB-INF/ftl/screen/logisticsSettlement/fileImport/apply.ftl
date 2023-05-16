<meta charset="UTF-8">
	<title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <#include "/commons/fd.ftl">
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/sjh-apply-main.css" type="text/css">
    
    
	<div class="batch-payment">
		<#include "/screen/title.ftl">
	
		<div class="content">
        <form  id="applyForm" method="post" name="applyForm">
            <p class="title">国际物流收款
                <font>将资金自动结到易付宝账户</font>
            </p>
            <ul class="valid-form">
                <li class="clearfix">
                    <div class="label-filed"><em>*</em>结汇币种：</div>
                    <div class="input-item jr-select" id="select-currency">
                        <i class="select-input-arrow-icon arrow-icon-closed"></i>
                        <input type="text" name="currency" class="select-input empty" readonly id="currency" key="">
                        <ul class="select-box">
                                <#if curType??>
                            	<#list curType as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                        </ul>
                        <p class="tips"></p>
                    </div>
                </li>
                <li class="clearfix" id="merchantDiv">
                    <div class="label-filed"><em>*</em>付款方账号：</div>
                    <div class="input-item jr-select" id="select-merchant">
                        <i class="select-input-arrow-icon arrow-icon-closed"></i>
                        <input type="text" name="payeeBankCard" class="select-input empty" readonly id="payeeBankCard" key="">
                    	<input type="text" name="payeeMerchantCode" id="payeeMerchantCode" class="input file-path"  style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;"> 
                        <input type="text" name="pcToken" id="pcToken" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
                        <ul class="select-box" id="select-box-merchant">
                            <li class="select-item">
                            </li>
                        </ul>
                        <p class="tips"></p>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="label-filed"><em>*</em>结汇金额：</div>
                                          
                    <div class="input-item" >
                    	<input type="text" name="receiveAmt" id="receiveAmt"  class="input file-path"  style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
						<input type="text" name="referenceRateFake" id="referenceRateFake" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">						
                        <input type="text" name="jhTotalNum"  id="jhTotalNum">
                        <select id="arrival" class="hide">
                        </select>
                        <p class="tips"></p>
                    </div>
                    <a class="exchange-count-btn">汇款金额计算</a>
                </li>
                <li class="clearfix">
                    <div class="label-filed"><em>*</em>业务类型：</div>
                    <div class="input-item jr-select">
                        <i class="select-input-arrow-icon arrow-icon-closed"></i>
                        <input type="text" readonly name="bizType" class="select-input empty" key="">
                        <ul class="select-box">
                            <li class="select-item">
                                <a class="sel-val" href="javascript:void(0);" key="1" value="国际物流">
                                   	国际物流
                                </a>
                            </li>                            
                        </ul>
                        <p class="tips"></p>
                    </div>
                </li>

                <li class="clearfix">
                    <div class="label-filed"><em>*</em>国际物流进出境运输：</div>
                    <div class="input-item jr-select">
                        <i class="select-input-arrow-icon arrow-icon-closed"></i>
                        <input type="text" name="logisticsType" class="select-input empty" readonly id="logisticsType" key="请选择">
                        <ul class="select-box">
                                <#if logisticsType??>
                            	<#list logisticsType as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                        </ul>
                        <p class="tips"></p>
                    </div>
                </li>

                
                
                <li class="clearfix">
                    <div class="label-filed"><em>*</em>结汇明细文件上传：</div>
                    <div class="input-item clearfix">
                        <input type="text" class="path-inp w208 fl" readonly id="fakepath">
                        <input type="text" name="detailAmount" id="detailAmount" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
                        <input type="text" name="fileAddress" id="fileAddress" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
                        <span class="file-upload-wrap fl">
                            <a href="javascript:;" class="file">选择文件 </a>             
                            <input type="file" name="file" id="fileUpload" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"> 
                        </span>
                        <a href="${ctx}/template/file/logistics_settlement_apply_template.xlsx" class="download fl">下载模板</a>
                        <p class="tips"></p>
                        <span class="result-wrap result-wrap-success hide">
                            <i class="icon icon-success"></i>
                            <p>上传成功</p>
                        </span>
                        <span class="result-wrap result-wrap-fail hide">
                            <i class="icon icon-fail"></i>
                            <p>上传失败，请修改后重新上传</p>
                            <p class="gray mt8"></p>
                        </span>
                    </div>
                </li>
            </ul>
            <a href="javascript:void(0)" class="submit-btn" id="J_placeOrder">提交申请</a>
                
        </form>
    </div>
	
        
    </div>

    <script type="text/html" id="count-tpl">
            <form id="exchangeCount" name="exchangeCount" method="post">
                <ul class="count">
                    <li class="count-item clearfix">
                        <div class="count-title"><em>*</em>批付金额 ：</div>
                        <div class="count-input">
                            <input type="text" name="batchPayAmount" id="batchPayAmount" placeholder="" />
                        </div>
                    </li>
                    <li class="count-item clearfix">
                        <div class="count-title"><em>*</em>手续费费率 ：</div>
                        <div class="count-input">
                            <input type="text" name="feeRate" id="feeRate" placeholder="" /><font>格式例如：0.003</font>
                        </div>
                    </li>
                    <li class="count-item clearfix">
                        <div class="count-title"><em>*</em>汇款金额 ：</div>
                        <div class="count-input">
                            <input type="text" name="exchangeAmount" id="exchangeAmount" placeholder="" readonly /><font>(计算公式：汇款金额=批付金额/(1-手续费费率))</font>
                        </div>
                    </li>
                </ul>
                <a href="javascript:;" class="back-btn" id="cancel">返 回</a>
            </form>
        </script>
    
	<script type="text/javascript">
    	document.onclick=function(e){
			var ev = e || window.event;  
	        var target = ev.target || ev.srcElement;  
	        if (target.className != "submit-btn" && target.className != "download fl" && target.className != "sel-val" && target.className != "sel-val-other") {  
	         	window.onbeforeunload=function(e){
	         		e = e || window.event;
	         		if(e){
				        e.returnValue = '';
				    }
	                return '';
	            }
	        }
		}
		

	</script>
    
    
    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/logisticsSettlement-apply-main" src="${ctx}/scripts/lib/require/require.min.js"></script>

    