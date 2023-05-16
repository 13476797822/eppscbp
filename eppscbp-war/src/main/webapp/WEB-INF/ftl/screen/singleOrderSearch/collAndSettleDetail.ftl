 <meta charset="UTF-8">
    <title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/placeholder.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">
    
    

    <div class="batch-payment">
       <#include "/screen/title.ftl">
        <div class="content">

            <a href="${ctx}/singleQuery/collAndSettleQuery/init.htm" class="back-top-up"><i class="back-icon"></i>返回上级</a>
            <h2 class="detail-title">订单详情</h2>

            <!-- 订单详情 -->
            <div class="order-detail-box clearfix">
            
           
            
                
                <div class="list clearfix">
                    <div class="step ">
                        <span class="title-text">${result.statusName}
                        </span>
                        <span  class="vertical-line"></span>
                        <#if result.status=='99'||result.status=='01'>
                        <ul class="clearfix">
                            <li>
                                <span class="step-icon">1</span>
                                <span>挂起</span>
                                <span class="line"></span>                               
                            </li>
                            <li>
                                <span class="step-icon">2</span>
                                <span>收汇</span>
                                <#if result.currency!='CNY'>
                                <span class="line"></span>
                                </#if>
                            </li>
                            <#if result.currency!='CNY'>
                            <li>
                            	<span class="step-icon">3</span>
                            	<span>结汇</span>
                            </li>
                            </#if>
                        </ul>
                        
                        <#else>
                        <ul class="clearfix">
                            <li class="hover">
                                <span class="step-icon">1</span>
                                <span>挂起</span>
                                <span class="line"></span>
                                <#if result.status=='00' || result.status=='10'>
                                <span class="half-line"></span>
                                <#else>
                                <span class="all-line"></span>
                                </#if>                                
                            </li>
                            <li <#if result.status=='21'||result.status=='30'||result.status=='31'> class="hover" </#if>>
                                <span class="step-icon">2</span>
                                <span>收汇</span>
                                <#if result.currency!='CNY'>
                                <span class="line"></span>
                                <#if result.status=='31'>
                                <span class="all-line"></span>
                                <#else>
                                <span class="half-line"></span>
                                </#if>
                                </#if>
                            </li>
                            <#if result.currency!='CNY'>
                            <li <#if result.status=='31'> class="hover" </#if>>
                            	<span class="step-icon">3</span>
                            	<span>结汇</span>
                            </li>
                            </#if>
                        </ul>
                        </#if>
                    </div>
                </div>
                
                

                
                <h3 class="content-title">订单信息</h3>
                <div class="list">
                    <ul class="clearfix last">
                        <li>
                            <span class="list-item-title">收结汇订单号：</span>
                            <span class="list-item-content">${result.orderNo}</span>
                        </li>
                        <li>
                            <span class="list-item-title">订单创建时间：</span>
                            <span class="list-item-content">${result.orderCreateTime}</span>
                        </li>
                        <li>
                        	<#if result.currency=='CNY'>
                        	<span class="list-item-title">收汇币种：</span>
                        	<#else>
                            <span class="list-item-title">结汇币种：</span>
                            </#if>
                            <span class="list-item-content">${result.currencyName}</span>
                        </li>
                        <li>
                        	<#if result.currency=='CNY'>
                        	<span class="list-item-title">收汇金额：</span>
                        	<#else>
                            <span class="list-item-title">结汇金额：</span>
                            </#if>
                            <span class="list-item-content">${result.orderAmt}</span>
                        </li>
                        <li>
                            <span class="list-item-title">交易订单明细笔数：</span>
                            <span class="list-item-content">${result.transactionNum}</span>
                        </li>
                        <li>
                        	<#if result.currency=='CNY'>
                        	<span class="list-item-title">收汇完成时间：</span>
                            <span class="list-item-content"><#if result.status==21>${result.orderComplateTime}</#if></span>
                        	<#else>
                            <span class="list-item-title">结汇完成时间：</span>
                            <span class="list-item-content"><#if result.status==31>${result.orderComplateTime}</#if></span>
                            </#if>
                        </li>
                       
                        
                    </ul>
                </div>
                <#if result.currency=='CNY'>
                <h3 class="content-title">收汇信息</h3>
                <#else>
                <h3 class="content-title">结汇信息</h3>
                </#if>

                <div class="list">
                    <ul class="clearfix last">
                        <li>
                        	<#if result.currency=='CNY'>
                        	<span class="list-item-title">收汇收入（人民币/元）：</span>
                            <span class="list-item-content"><#if result.status==21>${result.actualAmount}</#if></span>                            
                            <#else>
                            <span class="list-item-title">结汇收入（人民币/元）：</span>
                            <span class="list-item-content"><#if result.status==31>${result.actualAmount}</#if></span>
                            </#if>
                        </li>
                        <li>
                            <span class="list-item-title">商户收入（人民币/元）：</span>
                            <#if result.currency=='CNY'>
                            <span class="list-item-content"><#if result.status==21>${result.actualSubAmount}</#if></span>
                            <#else>
                            <span class="list-item-content"><#if result.status==31>${result.actualSubAmount}</#if></span>
                            </#if>
                        </li>
                        <#if result.currency=='CNY'>
                        <li>
                            <span class="list-item-title"></span>
                            <span class="list-item-content"></span>
                        </li>
                        <#else>
                        <li>
                            <span class="list-item-title">结汇实际汇率：</span>
                            <span class="list-item-content"><#if result.status==31>${result.orderRate}</#if></span>
                        </li>
                        </#if>
                        <li>
                            <span class="list-item-title">手续费：</span>
                            <#if result.currency=='CNY'>
                            <span class="list-item-content"><#if result.status==21>${result.poundageAmount}</#if></span>
                            <#else>
                            <span class="list-item-content"><#if result.status==31>${result.poundageAmount}</#if></span>
                            </#if>
                        </li>
                    </ul>
                </div>
                


                
                
                

                
            </div>


            
        </div>
    </div>




    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/kjf-single-order-search" src="${ctx}/scripts/lib/require/require.min.js"></script>
    