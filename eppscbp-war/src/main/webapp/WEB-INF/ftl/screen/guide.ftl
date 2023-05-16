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
	<script type="text/javascript" src="${ctx}/scripts/lib/require/require.min.js"></script>   
    <script type="text/javascript" src="${ctx}/scripts/eppscbp/main.js"> </script>
	<div class="batch-payment">
        <#include "/screen/title.ftl">
        <div class="content">
            <div class="not-open">
                <div class="not-txt">
                    <img src="${ctx}/style/images/not-open.png">
                    <h2>服务未开通</h2>
                    <#if Request["isOperator"]=='Y'>
                    <p>未配置此菜单权限，请联系管理员进行配置</p>
                    <#else>
                    <p>您尚未签署跨境结算协议，暂时无法使用本服务，如需帮助请联系易付宝客服<i class="red-warn">4008-365-365</i>转<i class="red-warn">9</i></p>
                    </#if>
                </div>
            </div>         
        </div>
    </div>
