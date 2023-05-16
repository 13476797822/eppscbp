<meta charset="UTF-8">
	<title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/sjh-result-page.css" type="text/css">
    
    <div class="batch-payment">
    <#include "/screen/title.ftl">
    <div class="content">
    
        <p class="title">
            国际物流收款
        </p>
        <div class="result-wrap">
        	<#if responseMsg ??>
        	<ul>					
				<li><img src='${ctx}/style/images/apply_fail.png' style='width:25px;height:25px'><a style='font-size:25px;height:25px' >结汇申请提交失败</a></li>
				<br>
				<br>				
                <li>${responseMsg}</li>
                
            </ul>	
            
            <#else>
            <ul>					
				<li><img src='${ctx}/style/images/apply.png' style='width:25px;height:25px'><a style='font-size:25px;height:25px' >结汇申请提交成功</a></li>
				<br>
				<br>
				<li>付款账号：${criteria.payeeBankCard}</li>
                <li>结汇币种：${criteria.currencyName}</li>
                <li>结汇金额：${criteria.receiveAmt}</li>
                <#if criteria.currency!='CNY'>
                <li>当前汇率：${criteria.referenceRate}</li>
                </#if>
                <li>预计兑换（人民币/元）：${criteria.expectedExchangeRMB}</li>
                <li>预计收入（人民币/元）：${criteria.expectedCustomerIncomeRMB}</li>
                <li>参考手续费（人民币/元）：${criteria.referenceFeeAmt}</li>
                <li>交易类型：${criteria.bizTypeStr}</li>
                <li>结汇订单明细笔数：${criteria.detailAmount}</li>                
            </ul>
            <div class="btn-wrap clearfix">
                <a href="javascript:;"" class="more"  id="submitQuery" onclick="submitQuery();">查看结汇进度</a>
                <a href="${ctx}/logisticsSettlement/fileImport/init.htm" class="back">返回申请页面</a>
            </div>
            <form method='post' method="post" id="f0" name="f0">
            	<input type="text" name="businessNo" id="businessNo" value="${criteria.businessNo}" style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
            </form>
            
            </#if>
        </div>
    </div>
    </div>
    
    <script type="text/javascript">
    	//提交查询
		function submitQuery(){			
    		var formObj = $("form[name='f0']");
    	    formObj.attr("action", sysconfig.ctx+"/singleQuery/collAndSettleQuery/query.htm");
    	    formObj.submit(); 
    	}  
		

	</script>
