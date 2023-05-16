<meta charset="utf-8"/>
    <title>交易查询</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/batch-detail.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/reset.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/header.css" type="text/css"/>

    
    <div class="batch-payment">
        <div class="platform-container">
        <div class="platform-panel">
            <div class="platform-error-wrap">
                <div class="error-img noservice"></div>
                <h4 class="error-title">服务未开通</h4>
                <#if Request["isOperator"]=='Y'>
                <p>未配置此菜单权限，请联系管理员进行配置</p>
                <#else>
                <p class="error-tip">您尚未签署跨境结算协议，暂时无法使用本服务，如需帮助请联系易付宝客服 <span class="high">4008-365-365</span> 转 <span class="high">9</span></p>
            	</#if>
            </div>
        </div>
    	</div>
    </div>