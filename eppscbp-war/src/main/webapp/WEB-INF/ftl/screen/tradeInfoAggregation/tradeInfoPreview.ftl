<!DOCTYPE html
  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <title>电子回单预览</title>
  <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="${ctx}/style/css/account_index.css" type="text/css" />
  <link rel="stylesheet" type="text/css" href="${ctx}/style/css/tradeInfo/transaction_inquiry_preview.css" />
</head>

<body>
<input type="hidden" name="pdfAjaxPath" value="/eppscbp/tradeQueryAuth/tradeInfo/toShowPdf.htm" id="pdfAjaxPath">
<input type="hidden" name="targetURL" value="${targetURL}" id="targetURL">
<input type="hidden" name="fileAddress" value="${fileAddress}" id="fileAddress">
<input type="hidden" name="transactionType" value="${transactionType}" id="transactionType">
<input type="hidden" name="orderNo" value="${orderNo}" id="orderNo">
<input type="hidden" name="serialNumber" value="${serialNumber}" id="serialNumber">

  <div class="e-receiptContent">
    <a href="${ctx}/tradeQueryAuth/tradeInfo/init.htm" class="back-top-up"><i class="back-icon"></i>返回上级</a>
    <div class="previewContent">	
      <iframe src="${ctx}/pdf/web/viewer.html" frameborder="0" width="100%" height="777"params="{}"></iframe></div>
    <div class="previewBtnArea">
      <div class="btnLeft" data-href="${ctx}/tradeQueryAuth/tradeInfo/downloadSingleElectronicPDF.htm?orderNo=${orderNo}&transactionType=&${transactionType}&fileAddress=${fileAddress}">下载电子版</div>
      <div class="btnRight">发送电子版至邮箱</div>
    </div>
  </div>

  <!-- 弹出框 -->
<script type="text/html" id="mailPop">
  <div id="infoMailBox" class="mailContent">
    <div id="inputArea">
    <div class="mailInputArea">
     	 邮箱地址：<div id="mailBox" class="mailBox" contenteditable="true">
          <div class="ipt-box"><input type="text" class="mail-input"></div>
        </div>
    </div>
    <div id="errorTip" class="inputErrorTips"></div>
    <div class="inputTips">注：邮箱地址最多输入5个，以“；”隔开</div>
    <div class="mailPopBtns">
      <div class="btnOk" id="btnOk1">确定</div>
      <div class="btnCancel">取消</div>
    </div>
  </div>
  <div id="infoAreaSuccess" class="hide">
    <div class="infoTitle"><img src="../../style/images/apply.png" />发送成功</div>
    <div class="infoContent"></div>
    <div class="mailPopBtns">
      <div class="btnOk" id="btnOk2">知道了</div>
    </div>
  </div>
  <div id="infoAreaFail" class="hide">
    <div class="infoTitle"><img src="../../style/images/apply_fail.png" />发送失败</div>
    <div class="infoContent">系统繁忙，请稍后重试</div>
    <div class="mailPopBtns">
      <div class="btnOk"  id="btnOk3">知道了</div>
    </div>
  </div>
</div>
</script>
<script language="javascript">
	var sysconfig = {
		ctx : "/eppscbp",
    stimestamp : '1613789111482'   
	};
	var configList = {
    queryMailUrl: '',
    sendMailUrl: ''
  }
</script>
  <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/tradeInfo/transaction_inquiry_preview.js"
    src="${ctx}/scripts/lib/require/require.min.js"></script>
  <!-- 外联js需要放在页面底部，body结束标签前 -->
</body>

</html>