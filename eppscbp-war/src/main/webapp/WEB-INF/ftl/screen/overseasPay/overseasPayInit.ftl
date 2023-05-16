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

        	<!-- 功能列表 -->
        	<ul class="function-item-list">
        		<li class="function-item">
        			<a href="${ctx}/overseasPay/studyExAndPay/init.htm">
	    				<div class="right-icon bg-buy_icon"></div>
	    				<div class="left-content">
	    					<h3>单笔购付汇</h3>
	    					<p>用于单个留学生的留学缴费业务申请，或多个留学生合并汇款至同一所海外学校的留学缴费申请。商户发起一次申请，由易付宝系统自动完成后续的购汇与付汇操作。仅支持余额支付，发起申请前请先充值。</p>
	    				</div>
    				</a>
        		</li>
        		<li class="function-item">
        			<a href="${ctx}/overseasPay/studyPay/init.htm">
	    				<div class="right-icon bg-sell_icon"></div>
	    				<div class="left-content">
	    					<h3>单笔付汇</h3>
	    					<p>针对发生退汇后不再付的交易，易付宝将资金原路返回至商户的易付宝外币户，商户此时发起新的交易申请，在外币户足额的情况下，无需进行购汇，可直接发起付汇。</p>
	    				</div>
	    			</a>		
        		</li>
        		<li class="function-item">
					<a href="${ctx}/overseasPay/studyBatchExAndPay/init.htm">
	    				<div class="right-icon bg-settle_icon"></div>
	    				<div class="left-content">
							<h3>批量购付汇</h3>
	    					<p>用于多个留学生、不同汇款币种、不同收款方的批量汇款申请。商户发起一次申请，由易付宝系统自动按笔数分别发起多笔购汇与多笔付汇。仅支持余额支付，发起申请前请先充值。</p>
	    				</div>
					</a>
        		</li>
        	</ul>
            
        </div>
    </div>