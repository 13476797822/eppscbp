<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <title>苏宁国际卡支付平台</title>
    <link rel="stylesheet" href="${ctx}/style/css/oca/reset.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/commen.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/order-details.css">
</head>
<script>
    var baseJSPath = '';
</script>
<body>
    <div class="header">
        <div class="content-nr clearfix">
            <div class="title">苏宁国际卡支付平台</div>
            <div class="nav-list">
                <div class="item"><a href="${ctx}/oca/ocaSaleOrderController/init.htm">首页</a></div>
                <div class="item current"><a href="${ctx}/oca/ocaSaleOrderController/init.htm">销售订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRefundOrderController/init.htm">退款订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRejectOrderController/init.htm">拒付订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaLogisticsInfoController/init.htm">物流信息上传</a></div>
            </div>
        </div>
    </div>

    <div class="main">
        <div class="go-backorder"><a href="${ctx}/oca/ocaSaleOrderController/init.htm">返回上一级</a></div>
        <div class="order-details-box">
            <div class="details-title">订单详情</div>
            <div class="details-content clearfix">
                <div class="details-content-item"><span class="item-name">商户订单号：</span>${result.merchantOrderNo}</div>
                <div class="details-content-item"><span class="item-name">收单单号：</span>${result.receiptOrderNo}</div>
                <div class="details-content-item nnth"><span class="item-name">订单创建时间：</span>${result.createTimeStr}</div>
                <div class="details-content-item"><span class="item-name">支付完成时间：</span>${result.payFinishTimeStr}</div>
                <div class="details-content-item"><span class="item-name">汇率：</span>${result.exchangeRate}</div>
                <div class="details-content-item nnth"><span class="item-name">订单金额：</span>${result.amtStr}</div>
                <div class="details-content-item"><span class="item-name">清算金额：</span>${result.clearingAmountStr}</div>
                <div class="details-content-item"><span class="item-name">保证金：</span>${result.depositStr}</div>
                <div class="details-content-item nnth"><span class="item-name">手续费：</span>${result.feeStr}</div>
                <div class="details-content-item"><span class="item-name">3DS认证费：</span>${result.certificationFee3DSStr}</div>
                <div class="details-content-item"><span class="item-name">卡号：</span>${result.cardNo}</div>
                <div class="details-content-item nnth"><span class="item-name">交易失败原因：</span>${result.failreason}</div>
                <div class="details-content-item"><span class="item-name">商品名称：</span><span class="item-content"><span class="txtcsaa">${result.productName}</span></span></div>
            </div>
        </div>

    </div>
    


</body>
</html>
