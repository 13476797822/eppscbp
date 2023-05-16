<meta charset="UTF-8">
<title>苏汇通</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
<link rel="icon" href="https://respay.suning.com/sfbmws/style/temp/logo.ico" type="image/x-icon">
<link rel="stylesheet" href="${ctx}/style/css/sht-index.css">
<div class="wrap">
    <div class="notic-wrap">
        <div class="left-box"><i></i>最新公告：</div>
        <div class="notic-msg">
                <ul class="notic-bar">
                	<#if resultList??>
			         	<#list resultList as item>
			         	 <li>${item}</li>
			        	</#list>
			         </#if>
                </ul>
        </div>
    </div>
    <ul class="channel-list clearfix">
        <li>
            <div class="mask"></div>
            <a href="${ctx}/home/homeInit/homeZjkj.htm"><img src="${ctx}/style/images/homes/card_zjkj.png" alt="">资金跨境</a>
        </li>
        <li>
            <div class="mask"></div>
            	<#if Request["isPlatform"]=='N'>
                <a href="${ctx}/cpStoreHandle/cpStoreHandleQuery/init.htm"><img src="${ctx}/style/images/homes/card_jwsk.png" alt="">境外收款</a>
                </#if>
                
                <#if Request["isPlatform"]=='Y'>
                <a href="${ctx}/cpWithdrawApply/cpWithdrawApplyInit/init.htm"><img src="${ctx}/style/images/homes/card_jwsk.png" alt="">境外收款</a>
                </#if>
        </li>
        <li class="mr0">
            <div class="mask"></div>
            <a href="${ctx}/tradeQueryAuth/tradeInfo/init.htm"><img src="${ctx}/style/images/homes/card_trade_query.png" alt="">交易查询</a>
        </li>
        <#if Request["isOperator"]!='Y'>
        <li>
            <div class="mask"></div>
            <a href="${ctx}/authManage/authManage/init.htm"><img src="${ctx}/style/images/homes/card_qxgl.png" alt="">权限管理</a>
        </li>
        </#if>
    </ul>
</div>
<script type="text/javascript" data-main="${ctx}/scripts/eppscbp/sht-card-list" src="${ctx}/scripts/lib/require/require.min.js"></script>