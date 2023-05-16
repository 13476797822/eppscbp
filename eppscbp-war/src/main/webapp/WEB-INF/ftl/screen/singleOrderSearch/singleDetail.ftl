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

            <a href="${ctx}/singleQuery/singleOrderQuery/init.htm" class="back-top-up"><i class="back-icon"></i>返回上级</a>
            <h2 class="detail-title">订单详情</h2>

            <!-- 订单详情 -->
            <div class="order-detail-box clearfix">
            
            <#if result.productType=='RS01'||result.productType=='FS01'>
            	<div class="list clearfix">
                    <div class="step ">
                        <span class="title-text">${result.statusName}
                        </span>
                        <span  class="vertical-line"></span>
                        <ul class="clearfix">
                            <li <#if result.status!='P' && result.status!='I'  && result.status!='F' && result.status!='C'> class="hover" </#if>>
                                <span class="step-icon">1</span>
                                <span>支付</span>
                                <span class="line"></span>
                                <#if result.status=='P' || result.status=='I'|| result.status=='F' || result.status=='C'>
                                <span class="half-line"></span>
                                <#else>
                                <span class="all-line"></span>
                                </#if>                                
                            </li>
                            <li <#if result.status=='21'> class="hover" </#if>>
                            	<span class="step-icon">2</span>
                            	<span>付汇</span>
                            </li>
                        </ul>
                    </div>
                </div>
            
            <#else>
            
                
                <#if result.status!='31'>
                <div class="list clearfix">
                    <div class="step ">
                        <span class="title-text">${result.statusName}
                        </span>
                        <span  class="vertical-line"></span>
                        <ul class="clearfix">
                            <li <#if result.status!='P' && result.status!='I'  && result.status!='F' && result.status!='C'> class="hover" </#if>>
                                <span class="step-icon">1</span>
                                <span>支付</span>
                                <span class="line"></span>
                                <#if result.status=='P' || result.status=='I'|| result.status=='F' || result.status=='C'>
                                <span class="half-line"></span>
                                <#else>
                                <span class="all-line"></span>
                                </#if>                                
                            </li>
                            <li <#if result.status=='11'||result.status=='20'||result.status=='21'> class="hover" </#if>>
                                <span class="step-icon">2</span>
                                <span>购汇</span>
                                <span class="line"></span>
                                <#if result.status=='20'||result.status=='21'>
                                <span class="all-line"></span>
                                <#else>
                                <span class="half-line"></span>
                                </#if>
                            </li>
                            <li <#if result.status=='21'> class="hover" </#if>>
                            	<span class="step-icon">3</span>
                            	<span>付汇</span>
                            </li>
                        </ul>
                    </div>
                </div>
                
                <#else>
                <div class="list retreat clearfix">
                    <div class="step ">
                        <span class="title-text">${result.statusName}
                        	<p>${result.orderFailReason}</p>
                        </span>
                        <span class="vertical-line"></span>
                        <ul class="clearfix">
                            <li class="hover">
                                <span class="step-icon">1</span>
                                <span>支付</span>
                                <span class="line"></span>
                                <span class="all-line"></span>
                            </li>
                            <li class="hover">
                                <span class="step-icon">2</span>
                                <span>购汇</span>
                                <span class="line"></span>
                                <span class="all-line"></span>
                            </li>
                            <li class="hover">
                                <span class="step-icon">3</span>
                                <span>付汇</span>
                                <span class="line"></span>
                                <span class="all-line"></span>
                            </li>
                            <li class="hover">
                            	<span class="step-icon">4</span>
                            	<span>退汇</span>
                            </li>
                        </ul>
                        <!-- <span class="err-text">汇款失败</span> -->
                    </div>
                </div>
                
                </#if>

			</#if>
                
                <h3 class="content-title">订单信息</h3>
                <div class="list">
                    <ul class="clearfix last">
                        <li>
                            <span class="list-item-title">跨境付汇款单号：</span>
                            <span class="list-item-content">${result.businessNo}</span>
                        </li>
                        <li>
                            <span class="list-item-title">订单创建时间：</span>
                            <span class="list-item-content">${result.orderCreatTime}</span>
                        </li>
                        <li>
                            <span class="list-item-title">业务类型：</span>
                            <span class="list-item-content">${result.bizTypeName}</span>
                        </li>
                        <li>
                            <span class="list-item-title">订单完成时间：</span>
                            <span class="list-item-content">${result.orderCompleteTime}</span>
                        </li>
                        <#if result.bizDetailType=='0031'>
                        <li>
                            <span class="list-item-title">缴费类型：</span>
                            <span class="list-item-content">${result.bizDetailTypeName}</span>
                        </li>
                        </#if>
                        <li>
                            <span class="list-item-title">产品类型：</span>
                            <span class="list-item-content">${result.productTypeName}</span>
                        </li>
                        <li>
                            <span class="list-item-title">全额到账标识：</span>
                            <span class="list-item-content">${result.fullAmtTypeName}</span>
                        </li>
                        <li>
                            <span class="list-item-title">指定购汇币种：</span>
                            <span class="list-item-content">${result.applyCurName}</span>
                        </li>
                        <li >
                            <span class="list-item-title">明细笔数：</span>
                            <span class="list-item-content">${result.numbers}笔</span>
                        </li>
                        <li class="long-text">
                            <span class="list-item-title">交易附言：</span>
                            <span class="list-item-content long">${result.remark}</span>
                        </li>
                    </ul>
                </div>
                <h3 class="content-title">收款商户信息</h3>
                <div class="list">
                    <ul class="clearfix last">
                        <li>
                            <span class="list-item-title">收款商户编码：</span>
                            <span class="list-item-content">${result.payeeMerchantCode}</span>
                        </li>
                        <li>
                            <span class="list-item-title">收款方商户名称：</span>
                            <span class="list-item-content">${result.payeeMerchantName}</span>
                        </li>
                        <li>
                            <span class="list-item-title">收款方币种：</span>
                            <span class="list-item-content">${result.remitCurName}</span>
                        </li>
                        
                    </ul>
                </div>
                <h3 class="content-title">真实性材料上传</h3>
                <div class="list">
                    <ul class="clearfix last">
                        <li>
                            <span class="list-item-title">上传状态：</span>
                            <span class="list-item-content">${result.supStatusName}</span>
                        </li>
                        <#if result.supStatus=='F'>
                        <li>
                            <span class="list-item-title">上传失败原因：</span>
                            <span class="list-item-content" title="${result.supFailReason}">${result.supFailReason}</span>
                        </li>
                        </#if>
                    </ul>
                </div>
                <h3 class="content-title">支付信息</h3>
                <div class="list">
                    <ul class="clearfix">
                        <li>
                            <span class="list-item-title">汇率：</span>
                            <span class="list-item-content">${result.exchangeRateStr}</span>
                        </li>
                        <li>
                            <span class="list-item-title">申请金额：</span>
                            <span class="list-item-content">${result.applyAmtAndCur}</span>
                        </li>
                        <#if (result.status=='P' || result.status=='F')&&result.productType!='RS01'>
                        <li>
                        	<span class="list-item-title">汇率失效时间：</span>
                        	<span class="list-item-content">${result.rateExpiredTime}</span>
                        </li>
                        <#else>
                        <li><span class="list-item-title"></span><span class="list-item-content"></span></li>
                        </#if>
                        <li>
                            <span class="list-item-title">手续费：</span>
                            <span class="list-item-content">${result.feeAmtAndCur}</span>
                        </li>
                        <li><span class="list-item-title"></span><span class="list-item-content"></span></li>
                        <li>
                            <span class="list-item-title">全额到账：</span>
                            <span class="list-item-content">${result.fullAmtAndCur}</span>
                        </li>
                    </ul>
                    <ul class="clearfix last">
                        <li><span class="list-item-title"></span><span class="list-item-content"></span></li>
                        <li>
                            <span class="list-item-title">实付金额：</span>
                            <span class="list-item-content ">${result.realPayAmt}</span>
                        </li>
                        <li><span class="list-item-title"></span><span class="list-item-content"></span></li>
                        <li>
                            <span class="list-item-title">汇出金额：</span>
                            <span class="list-item-content ">${result.remitAmtAndCur}</span>
                        </li>
                        <li><span class="list-item-title"></span><span class="list-item-content"></span></li>                        
                        <#if result.status=='31'>
                        <li>
                            <span class="list-item-title">退汇金额：</span>
                            <span class="list-item-content ">${result.refundAmtAndCur}</span>
                        </li>
                        </#if>
                    </ul>
                    
                    
                </div>


                
                
                

                
            </div>


            
        </div>
    </div>




    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/kjf-single-order-search" src="${ctx}/scripts/lib/require/require.min.js"></script>
    