<meta charset="UTF-8">
<title>苏汇通</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
<link rel="icon" href="https://respay.suning.com/sfbmws/style/temp/logo.ico" type="image/x-icon">
<link rel="stylesheet" href="${ctx}/style/css/sht_index_zjkj.css">
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
            <a href="${ctx}/goodsTrade/goodsTradeInit/init.htm"><img src="${ctx}/style/images/homes/card_jkmy.png" alt="">进口贸易结算</a>
        </li>
        <li>
            <div class="mask"></div>
            <a href="${ctx}/collAndSettle/collAndSettle/init.htm"><img src="${ctx}/style/images/homes/card_ckmy.png" alt="">出口贸易结算</a>
        </li>
        <li class="mr0">
            <div class="mask"></div>
            <a href="${ctx}/overseasPay/overseasPayInit/init.htm"><img src="${ctx}/style/images/homes/card_lxmy.png" alt="">留学教育缴费</a>
        </li>
        <li>
            <div class="mask"></div>
            <a href="${ctx}/logisticsSettlement/logisticsSettlement/init.htm"><img src="${ctx}/style/images/homes/card_gjwl.png" alt="">国际物流结算</a>
        </li>
        <li>
            <div class="mask"></div>
             <a href="javascript:;" class="wait"><img src="${ctx}/style/images/homes/card_wait.png" alt=""></a>
            <a href="javascript:;"><img src="${ctx}/style/images/homes/card_hkjp.png" alt=""></a>
        </li>
        <li class="mr0">
            <div class="mask"></div>
            <a href="javascript:;" class="wait"><img src="${ctx}/style/images/homes/card_wait.png" alt=""></a>
            <a href="javascript:;"><img src="${ctx}/style/images/homes/card_slzs.png" alt=""></a>
        </li>
    </ul>
</div>
<script type="text/javascript" data-main="${ctx}/scripts/eppscbp/sht-card-list" src="${ctx}/scripts/lib/require/require.min.js"></script>