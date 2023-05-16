<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <title>苏宁国际卡支付平台</title>
    <link rel="stylesheet" href="${ctx}/style/css/oca/reset.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/commen.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/details-dishonor.css">
</head>
<script>
   var baseJSPath = '/eppscbp/js/oca/';
</script>
<body>
    <div class="header">
        <div class="content-nr clearfix">
            <div class="title">苏宁国际卡支付平台</div>
            <div class="nav-list">
                <div class="item"><a href="${ctx}/oca/ocaHomeController/init.htm">首页</a></div>
                <div class="item"><a href="${ctx}/oca/ocaSaleOrderController/init.htm">销售订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRefundOrderController/init.htm">退款订单</a></div>
                <div class="item current"><a href="${ctx}/oca/ocaRejectOrderController/init.htm">拒付订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaLogisticsInfoController/init.htm">物流信息上传</a></div>
            </div>
        </div>
    </div>

    <div class="main">
        <div class="go-backorder"><a href="${ctx}/oca/ocaRejectOrderController/init.htm">返回上一级</a></div>
        <div class="order-details-box">
            <div class="details-title">拒付详情</div>
            <div class="details-content clearfix">
                <div class="details-content-item"><span class="item-name">拒付业务单号：</span>${result.rejectOrderNo}</div>
                <div class="details-content-item"><span class="item-name">商户订单号：</span>${result.merchantOrderNo}</div>
                <div class="details-content-item"><span class="item-name">拒付申请时间：</span>${result.applyDateStr}</div>
                <div class="details-content-item"><span class="item-name">拒付完成时间：</span>${result.lastDateStr}</div>
                <div class="details-content-item"><span class="item-name">订单金额：</span>${result.amtStr}</div>
                <div class="details-content-item"><span class="item-name">汇率：</span>${result.exchangeRate} </div>
                <div class="details-content-item"><span class="item-name">清算金额：</span>${result.amtCnyStr} </div>
                <div class="details-content-item"><span class="item-name">本次拒付订单金额：</span>${result.rejectAmtStr} </div>
                <div class="details-content-item"><span class="item-name">本次拒付清算金额：</span>${result.rejectAmtCnyStr}</div>
                <div class="details-content-item"><span class="item-name">拒付处理费：</span>${result.rejectFeeStr}</div>
				<div class="details-content-item"><span class="item-name">拒付状态：</span><#if result.statusStr== '103' || result.statusStr='104'>申诉中<#else>${result.statusStr}</#if></div>
                <#if result.lastDateFlag == 'T'>
                    <div class="details-content-item" style="color: #FF0000"><span class="item-name">最后处理时间：</span>${result.lastDateStr}</div>
                <#else >
                    <div class="details-content-item"><span class="item-name">最后处理时间：</span>${result.lastDateStr}</div>
                </#if>
                <div class="details-content-item" style="width: 1050px;"><span class="item-name">拒付原因：</span>${result.reasonCode}</div>
            </div>
	  		<#if resultList??>
	            <div class="details-title grievance-record">申诉记录</div>
	            <div class="details-content grievance-content clearfix">
	                <div class="grievance-content-head clearfix">
	                    <div class="item th01">申诉编号</div>
	                    <div class="item th02">申诉材料名称</div>
	                    <div class="item th03">上传时间</div>
	                    <div class="item th04">审核结果</div>
	                    <div class="item th05">审核意见</div>
	                    <div class="item th06">最终申诉结果</div>
	                    <div class="item th07">银行结果附件</div>
	                </div>
	                <div class="grievance-content-box">
	                 <#list resultList as item>
	                    <div class="list-item clearfix">
	                        <div class="item th01">${item.rejectAppealNo}</div>
	                        <div class="item th02" title=${item.appealAttachStr}><#if item.appealAttachStr != "--"><a href="${ctx}/oca/ocaRejectOrderController/downLoadFile.htm?fileAddress=${item.appealAttach}"><span class="result">${item.appealAttachStr}</span></a><#else>--</#if></div>
	                        <div class="item th03">${item.uploadTimeStr}</div>
	                        <div class="item th04">${item.statusStr}</div>
	                        <div class="item th05">${item.opinion}</div>
	                        <div class="item th06">${item.lastStatusStr}</div>
	                        <div class="item th07" title=${item.bankAttachStr}><#if item.bankAttachStr != "--"><a href="${ctx}/oca/ocaRejectOrderController/downLoadFile.htm?fileAddress=${item.bankAttach}"><span class="result">${item.bankAttachStr}</span></a><#else>--</#if></div>
	                    </div>
	                  </#list>                    
	                </div>
	             </div>
	         </#if>
        </div>
    </div>
</body>
</html>
