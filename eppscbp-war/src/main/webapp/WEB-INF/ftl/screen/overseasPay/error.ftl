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

            <!-- 名称 -->
            <h3 class="content-title">系统异常</h3>

            <!-- 状态步骤 -->

            <ul class="axis clearfix">
                <li class="step"><span class="icon">1</span>填写结算信息<span class="line"></span></li>
                <li class="step active"><span class="icon">2</span>支付<span class="line"></span></li>
                <li class="step"><span class="icon">3</span>支付结果</li>
            </ul>
            <div class="pay-state-box">
                <h3 class="message-box clearfix">
                    <i class="success-icon"></i>
                    <span class="message-text">系统异常,请稍后再试！</span>
                </h3>
                <p class="txt"></p>
                <div class="btn-box clearfix">
                	<input type="hidden" id ="businessNo" name="businessNo" value="${businessNo}">
                    <#if productType == "FB11">
						<a href="${ctx}/batchQuery/batchOrderQuery/init.htm" class="see-detail">查看交易订单</a>
					<#else>
						<a href="${ctx}/singleQuery/singleOrderQuery/init.htm" class="see-detail">查看交易订单</a>
					</#if> 
                    <a href="${ctx}/overseasPay/overseasPayInit/init.htm" class="more-btn">重新申请</a>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/main" src="${ctx}/scripts/lib/require/require.min.js"></script>
