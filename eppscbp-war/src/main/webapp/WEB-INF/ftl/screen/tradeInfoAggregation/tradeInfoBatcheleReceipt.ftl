<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <title>电子回单</title>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${ctx}/style/css/account_index.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/tradeInfo/transaction_inquiry_e-receipt.css" type="text/css" />
</head>
<body>	
<form method="post" id="f0" name="f0">
	<input type="hidden" name="fileAddress" value="${pdfAddress}" id="fileAddress">
	<div class="e-receiptContent">
		<a href="${ctx}/tradeQueryAuth/tradeInfo/init.htm" class="back-top-up"><i class="back-icon"></i>返回上级</a>
		<#if success=='2'>
			<!-- 电子回单开具中-->
			<div id="status01">
				<div class="e-receiptIcon-op"></div>
				<div class="e-receiptIcon-title">电子回单开具中…</div>
				<div class="e-receiptIcon-tip" style="text-align: center;">所选期间交易笔数过多时生成时间较慢，请耐心等待，建议您缩小时间范围重新生成</div>
			</div>
		</#if>
		<#if success=='1'>
			<!-- 电子回单开具成功 -->
			<div id="status02">
				<div class="e-receiptIcon-success"></div>
				<div class="e-receiptIcon-title">电子回单开具成功</div>
				<div class="e-receiptIcon-btn" id="btnDownload">下载电子版</div>
				<div class="e-receiptIcon-tip" style="padding-left: 300px;">*用户申请电子回单常作为财产纠纷、诉纷、税务稽查等事务的证明材料，电子回单的法律效力以有关机关实际判断为准；</div>
				<div class="e-receiptIcon-tip" style="padding-left: 300px;">*汇总电子回单仅包含以下交易类型数据：外币收汇、外币结汇、批付、购汇、外币付汇、人民币付汇。</div>
			</div>
		</#if>
		<#if success=='0'>
			<div id="status03">
				<!-- 三种图标样式 -->
				<div class="e-receiptIcon-fail"></div>
				<!-- 状态 -->
				<div class="e-receiptIcon-title">${msg}</div>
				<div class="e-receiptIcon-btn gray">下载电子版</div>
			</div>
		</#if>
	</div>
</form>
<script type="text/javascript" data-main="${ctx}/scripts/eppscbp/tradeInfo/transaction_inquiry_e_preview.js" src="${ctx}/scripts/lib/require/require.min.js"></script>
<!-- 外联js需要放在页面底部，body结束标签前 -->
</body>
</html>