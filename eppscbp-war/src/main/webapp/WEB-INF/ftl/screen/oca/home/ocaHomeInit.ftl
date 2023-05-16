<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
	<title>苏宁国际卡支付平台</title>
	<link rel="stylesheet" href="${ctx}/style/css/oca/reset.css">
	<link rel="stylesheet" href="${ctx}/style/css/oca/commen.css">
	<link rel="stylesheet" href="${ctx}/style/css/oca/index.css">
	<link rel="stylesheet" href="${ctx}/style/css/oca/calendar-alter.css">
</head>
<script>
    var baseJSPath = '/eppscbp/js/oca/';
</script>
<body>
    <div class="header">
        <div class="content-nr clearfix">
            <div class="title">苏宁国际卡支付平台</div>
            <div class="nav-list">
                <div class="item current"><a href="${ctx}/oca/ocaHomeController/init.htm">首页</a></div>
                <div class="item"><a href="${ctx}/oca/ocaSaleOrderController/init.htm">销售订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRefundOrderController/init.htm">退款订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRejectOrderController/init.htm">拒付订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaLogisticsInfoController/init.htm">物流信息上传</a></div>
            </div>
        </div>
    </div>
    
    <!--banner[[-->
    <div class="banner">
    
        <div class="notice">
            <div class="notice-bord">
            <#if criteria.result.rejectOrderNum != '0'>
            	<span class="more"><a href="${ctx}/oca/ocaRejectHandingOrderController/init.htm">查看详情</a></span>
            </#if>
            <div class="notice-box">
                <div class="list-item details-top">
                    ${criteria.result.noticeMsg}
                </div>
                <#if criteria.result.noticeMsgList??>
		         	<#list criteria.result.noticeMsgList as item>
		         	 <div class="list-item">${item}</div>
		        	</#list>
			     </#if>
             </div>
            </div>
        </div>
        <div class="banner-bg">
	        <#if criteria.result.targetURL== null>
	        	<img src="${ctx}/style/images/oca/banner.jpg"+Math.random()>
	        <#else>
	        	<img src=${criteria.result.targetURL}>
	        </#if>
        	
        </div>
    </div>
    <!--]]banner-->

    <div class="main">
        <!--头部信息[[-->
        <div class="top-amount">
            <div class="amount-item">
                <div class="amount-item-tit">可用余额<span class="anunt-btn"><a href=${criteria.result.withdrawPath}>提现</a></span></div>
                <div class="amount-many">人民币：<span class="anunt-color" name="availableBalance">${criteria.result.availableBalance}</span>元</div>
            </div>

            <div class="amount-item">
                <div class="amount-item-tit funds-transit">在途资金</div>
                <div class="amount-many">人民币：<span class="anunt-color" name="onTheWayMoney">${criteria.result.onTheWayMoney}</span>元</div>
            </div>

            <div class="amount-item">
                <div class="amount-item-tit bond">保证金</div>
                <div class="amount-many">人民币：<span class="anunt-color" name="earnestAmount">${criteria.result.earnestAmount}</span>元</div>
            </div>            
        </div>
        <!--]]头部信息-->

        <!--交易概览[[-->
        <div class="transaction-overview">
            <div class="transaction-title">交易概览</div>
            <!--交易列表[[-->
            <div class="transaction-content-query clearfix">
                <div class="title-infor">查询周期：</div>
                <div class="title-datafirst"><input class="datafirst" readonly type="text"/></div>
                <div class="title-datalast"><input class="datalast" readonly type="text"/></div>
                <div class="title-btn sumbitn">确定</div>
            </div>

            <div class="query-box clearfix">
                <div class="query-border"></div>
                <div class="query-list-item">
                    <div class="content-box" id="orderquan"></div>
                    <div class="query-data">
                        <div class="query-name">订单数量</div>
                        <div class="query-txt djslbox"><span class="query-num djsl">--</span>笔</div>
                    </div>
                </div>

             
                <div class="query-list-item">
                    <div class="content-box" id="ordermany"></div>
                    <div class="query-data">
                        <div class="query-name">订单金额</div>
                        <div class="query-txt djjebox"><span class="query-num djje">--</span>元</div>
                    </div>
                </div>

                <div class="query-list-item laster-item">
                    <div class="content-box" id="orderjs"></div>
                    <div class="query-data">
                        <div class="query-name">结算金额</div>
                        <div class="query-txt jsjebox"><span class="query-num jsje">--</span>元</div>
                    </div>
                </div>      
                


            </div>
            <!--]]交易列表-->

        </div>

        <!--交易概览[[-->
        </div>

        <!--商户结算流程[[--> 
        <div class="settlement-process">
            <div class="settlement-title">
                <div class="settlement-title-txt">商户结算流程</div>                
            </div>

            <div class="settlement-content clearfix">
                <div class="content-item laster01">
                    <div class="content-itemimg"><img src="${ctx}/style/images/oca/liuchen01.png"></div>
                    <div class="content-txt">根据协议触发结算</div>
                </div>

                <div class="content-item laster02">
                    <div class="content-itemimg"><img src="${ctx}/style/images/oca/liuchen02.png"></div>
                    <div class="content-txt">当日将资金结算至易付宝虚户及保证金账户</div>
                </div>

                <div class="content-item laster03">
                    <div class="content-itemimg"><img src="${ctx}/style/images/oca/liuchen03.png"></div>
                    <div class="content-txt">结算后可以随时下载结算单进行对账，下载<a class="csm" href=${criteria.result.mtradePath}>传送门</a></div>
                </div>

                <div class="content-item laster04">
                    <div class="content-itemimg"><img src="${ctx}/style/images/oca/liuchen04.png"></div>
                    <div class="content-txt">商户发现对账差异，请邮件联系jrcbd@suning.com</div>
                </div>   

                <div class="content-item laster05">
                    <div class="content-itemimg"><img src="${ctx}/style/images/oca/liuchen05.png"></div>
                    <div class="content-txt">保证金到期后自动释放至易付宝虚户</div>
                </div>                 
                
            </div>

        </div>
        <!--]]商户结算流程-->    
        
        <!--拒付处理流程[[-->
        <div class="dishonor">
            <div class="dishonor-title">
                <div class="dishonor-title-txt">拒付处理流程</div>
            </div>
            <div class="dishonor-content">
                <div class="dishonor-content-dist"></div>
                <div class="dishonor-content-item dishonor01">
                    <div class="dishonor-content-num">1</div>
                    <div class="dishonor-content-txt">
                        苏宁通过短信和邮件通知拒付信息
                    </div>
                </div>
                <div class="dishonor-content-item dishonor02">
                    <div class="dishonor-content-num">2</div>
                    <div class="dishonor-content-txt">
                        商户需要尽快在申诉时间内对拒付进行申诉或同意，超时默认为同意
                    </div>
                </div>
                <div class="dishonor-content-item dishonor03">
                    <div class="dishonor-content-num">3</div>
                    <div class="dishonor-content-txt">
                        您的申诉材料，苏宁会进行审核，审核失败需要您在申诉时间内重新提交
                    </div>
                </div>     
                <div class="dishonor-content-item dishonor04">
                    <div class="dishonor-content-num">4</div>
                    <div class="dishonor-content-txt">
                        所有申诉结果，由卡组织最后裁定
                    </div>
                </div>                            

            </div>
        </div>
        <!--]]拒付处理流程-->
        
    </div>

    <div class="footer"></div>
    <script data-main="${ctx}/js/oca/index.js" src="${ctx}/js/oca/lib/require.min.js"></script>
</body>
</html>