<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <title>苏宁国际卡支付平台</title>
    <link rel="stylesheet" href="${ctx}/style/css/oca/reset.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/commen.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/refund-order-details.css">
</head>
<script>
    var baseJSPath = '';
</script>
<body>
    <div class="header">
        <div class="content-nr clearfix">
            <div class="title">苏宁国际卡支付平台</div>
            <div class="nav-list">
                <div class="item"><a href="${ctx}/oca/ocaHomeController/init.htm">首页</a></div>
                <div class="item"><a href="${ctx}/oca/ocaSaleOrderController/init.htm">销售订单</a></div>
                <div class="item current"><a href="${ctx}/oca/ocaRefundOrderController/init.htm">退款订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRejectOrderController/init.htm">拒付订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaLogisticsInfoController/init.htm">物流信息上传</a></div>
            </div>
        </div>
    </div>

    <div class="main">
        <div class="go-backorder"><a href="${ctx}/oca/ocaRefundOrderController/init.htm">返回上一级</a></div>
        <div class="order-details-box">
            <div class="details-title">订单详情</div>
            <div class="details-content clearfix">
                <div class="details-content-item"><span class="item-name">退款订单号：</span>${result.refundOrderNo}</div>
                <div class="details-content-item"><span class="item-name">商户订单号：</span>${result.merchantOrderNo}</div>
                <div class="details-content-item"><span class="item-name nnth">退款创建时间：</span>${result.refundCreateTimeStr}</div>
                <div class="details-content-item"><span class="item-name">退款完成时间：</span>${result.refundFinishTimeStr}</div>
                <div class="details-content-item"><span class="item-name">汇率：</span>${result.exchangeRate}</div>
                <div class="details-content-item"><span class="item-name nnth">订单金额(订单币种)：</span>${result.amtStr}</div>
                <div class="details-content-item"><span class="item-name">订单金额(清算币种)：</span>${result.amtCnyStr}</div>
                <div class="details-content-item"><span class="item-name">本次退款金额(订单币种)：</span>${result.refundAmtStr}</div>
                <div class="details-content-item"><span class="item-name nnth">本次退款金额(清算币种)：</span>${result.refundAmtCnyStr} </div>
                <div class="details-content-item"><span class="item-name">剩余可退金额(订单币种)：</span>${result.remainingAmtStr} </div>
                <div class="details-content-item"><span class="item-name">剩余可退金额(清算币种)：</span>${result.remainingAmtCnyStr}</div>
            </div>
            <#if result.refundStatus == "300">
	            <div class="details-title reason-failure-title clearfix">失败原因</div>
	            <div class="reason-failure-nr">
	               ${result.refundFailReason}
	            </div>
            </#if>
        </div>

        

    </div>
    


</body>
</html>
