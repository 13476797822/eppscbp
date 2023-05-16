<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <title>交易查询</title>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/account_index.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/placeholder.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/tradeInfo/transaction_inquiry.css"/>
</head>
<body>


<!--头部logo ]]-->
<form method="post" id="f0" name="f0">
<input type="hidden" name="id" id="chooseTable">
  <div class="jy-main-content mt10">
    <div class="jy-main-content mt10">
      <div class="jy-main-right" style="width:1148px;">
        <!-- 表单[[-->
        <div class="data-long-form" style="width:100%;">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cal-table">
            <tbody>
              <tr>
                <td width="60px" class="tright">订单时间：</td>
                <td width="256px">
                <!-- 选择日期[[-->
                  <div class="chioce-data dh-time">
                    <div class="demo-wrap uitext" id="demo-wrap">
                      <input type="text" id="date01" class="input" value="${(requestDto.orderCreateTimeStart)!''}" placeholder="" name="orderCreateTimeStart" />
                    </div>
                    <span class="h-line">—</span>
                    <div class="demo-wrap uitext" id="demo-wrap2">
                      <input type="text" id="date02" class="input" value="${(requestDto.orderCreateTimeEnd)!''}" placeholder="" name="orderCreateTimeEnd" />
                    </div>
                  </div>
                </td>
                <td width="60px" class="tright">批付姓名：</td>
                <td width="256px">
                  <span class="uitext l">
                    <input type="text" class="data-input2" maxlength="50" value="${(requestDto.receiverName)!''}" name="receiverName" />
                  </span>
                </td>
                <td width="60px" class="tright">批付帐号：</td>
                <td width="256px"><span class="uitext l">
                  <input type="text" class="data-input2" maxlength="50" value="${(requestDto.receiverCardNo)!''}" name="receiverCardNo" />
                </span></td>
              </tr>
              <tr>
                <td width="60px" class="tright">金额范围：</td>
                <td width="256px"> <div class="chioce-data dh-range">
                  <div class="demo-wrap uitext" id="demo-wrap">
                    <input type="text" class="input" id="amtStart"  value="${(requestDto.amtStart)!''}" name="amtStart" />
                  </div>
                  <span class="h-line">至</span>
                  <div class="demo-wrap uitext" id="demo-wrap2">
                    <input type="text" class="input" id="amtEnd"  value="${(requestDto.amtEnd)!''}" name="amtEnd" />
                  </div>
                </div></td>
                <td class="tright">业务类型：</td>
                <td><span class="uitext uitext-select width130 zindex300 check-select2">
                  <input class="value" type="hidden" name="bizType" value="${(requestDto.bizType)!''}" />
                  <input class="name" type="text" name="bizTypeName" readonly="readonly" value="${(requestDto.bizTypeName)!'全部'}" />
                  <i></i>
                  <ul class="check-list2 hide">
                    <li title="全部" data-value="">全部</li>
                    <li title="货物贸易" data-value="001">货物贸易</li>
                    <li title="国际物流" data-value="005">国际物流</li>
                  </ul>
                </span></td>
                
                <td class="tright">订单状态：</td>
                <td> <span class="uitext uitext-select width130 zindex300 check-select">
                  <input class="value" type="hidden" name="status" value="${(requestDto.status)!'001'}" />
                  <input class="name" type="text" name="statusName" readonly="readonly" value="${(requestDto.statusName)!'收汇成功'}" placeholder="请选择" />
                  <i></i>
                  <ul class="check-list hide">
                    <li title="全部" data-value=""><input class="all" type="checkbox"><label>全部</label></li>
                    <li title="收汇成功" data-value="001"><input type="checkbox" ><label>收汇成功</label></li>
                    <li title="结汇成功" data-value="002"><input type="checkbox" ><label>结汇成功</label></li>
                    <li title="出款成功" data-value="003"><input type="checkbox" ><label>出款成功</label></li>
                    <li title="出款失败" data-value="004"><input type="checkbox" ><label>出款失败</label></li>
                    <li title="受理失败" data-value="005"><input type="checkbox" ><label>受理失败</label></li>
                    <li title="退票" data-value="006"><input type="checkbox" ><label>退票</label></li>
                    <li title="关闭" data-value="007"><input type="checkbox" ><label>关闭</label></li>
                    <li title="购汇成功" data-value="008"><input type="checkbox" ><label>购汇成功</label></li>
                    <li title="付汇中" data-value="009"><input type="checkbox" ><label>付汇中</label></li>
                    <li title="付汇成功" data-value="010"><input type="checkbox" ><label>付汇成功</label></li>
                    <li title="退汇成功"  data-value="011"><input type="checkbox" ><label>退汇成功</label></li>
                    <li title="已退汇"  data-value="012"><input type="checkbox" ><label>已退汇</label></li>
                  </ul>
                </span></td>
              </tr>
              <tr>
                <td class="tright">订单号：</td>
                <td><span class="uitext l">
                  <input type="text" class="data-input2" maxlength="50" value="${(requestDto.orderNo)!''}" name="orderNo" />
                </span></td>
               <td class="tright">交易类型：</td>
                <td><span class="uitext uitext-select width110" id="choose01">
                  <input class="value" type="hidden" name="flag" value="${(requestDto.flag)!'02'}" />
                  <input class="name" type="text" readonly="readonly" value="收结汇" />
                  <i></i>
                  <ul class="choose-list1 hide">
                    <li title="收结汇" name="flag" data-value="02">收结汇</li>
                    <li title="购付汇" name="flag" data-value="01">购付汇</li>
                  </ul>
                </span><span class="uitext uitext-select width150" style="border-left: 0;" id="choose02">
                  <input class="value" type="hidden" name="transType" value="${(requestDto.transType)!'001,002,003,004'}" />
                  <input class="name" type="text" name="transTypeName" readonly="readonly" value="${(requestDto.transTypeName)!'全部,外币收汇,结汇,人民币收汇,批付'}" placeholder="请选择" />
                  <i></i>
                   <ul class="choose-list2 hide">
                    <li title="全部" data-value=""><input class="all" type="checkbox"><label>全部</label></li>
                    <li title="外币收汇" data-value="001"><input type="checkbox"><label>外币收汇</label></li>
                    <li title="结汇" data-value="002"><input type="checkbox"><label>结汇</label></li>
                    <li title="人民币收汇" data-value="003"><input type="checkbox"><label>人民币收汇</label></li>
                    <li title="批付" data-value="004"><input type="checkbox"><label>批付</label></li>
                  </ul>
                  <ul class="choose-list2 hide">
                    <li title="全部" data-value=""><input class="all" type="checkbox" ><label>全部</label></li>
                    <li title="购汇"  data-value="005"><input type="checkbox" ><label>购汇</label></li>
                    <li title="外币付汇" data-value="006"><input type="checkbox" ><label>外币付汇</label></li>
                    <li title="人民币付汇" data-value="007"><input type="checkbox" ><label>人民币付汇</label></li>
                    <li title="外币退汇" data-value="008"><input type="checkbox" ><label>外币退汇</label></li>
                    <li title="人民币退汇" data-value="009"><input type="checkbox" ><label>人民币退汇</label></li>
                    <li title="外币退汇重付" data-value="010"><input type="checkbox" ><label>外币退汇重付</label></li>
                    <li title="人民币退汇重付" data-value="011"><input type="checkbox" ><label>人民币退汇重付</label></li>
                  </ul>
                </span></td>
                <td>&nbsp;</td>
                <td><a href="javascript:void(0);" class="btn26 btnquery mt10"><span>查询</span></a>
                <a href="javascript:void(0);" class="btnreset mt10" ><span>重置</span></a></td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <#if resultList??>
        <div class="bus-table-btn">
          <div class="column-total">
          
          	<#if amtResultList??>
          	 <#list amtResultList as item>
	            <div class="coloum-total-info">
	              <span class="currency-total">${item.curZhStr}合计</span>
	              <span>收入:</span>
	              <span>${item.revenueSumStr} ${item.cur}</span>
	              
	              <#if item.curZhStr == '人民币'>
	              <span>支出(含手续费):</span>
	              </#if>
	              <#if item.curZhStr !='人民币'>
	              <span>支出:</span>
	              </#if>
	              
	              <span>${item.expensesSumStr} ${item.cur}</span>
	            </div>
	            </#list>
            </#if>
            
            <div class="switchArea">
                <div id="btnUp"></div>
                <div id="btnDown"></div>
            </div>
          </div>
          <div class="outport-btn"><i></i>导出</div>
          <div class="summary-btn"><i></i>汇总电子回单</div>
        </div>
        </#if>
        
        <div class="table-box">
       	<#if errMessage??>
       	<p class="no-result"><i></i>${errMessage}</p>
       	</#if>
       
       	<#if resultList??>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="bus-table dh-table td33 fixed-table" id="tableResult">
           <thead>
            <tr style="background:#DCE9F5;">
              <th><div style="width:40px;margin:0px auto;"><input id="chkAll" type="checkbox" /></div></th>
              <th><div style="width:120px;text-align:center;">创建订单时间</div></th>
              <th><div style="width:120px;text-align:center;">订单号</div></th>
              <th><div style="width:100px;text-align:center;">交易类型</div></th>
              <#if requestDto.flag == '02'>
              	<th><div style="width:100px;text-align:center;">批付信息</div></th>
              </#if>
              <th><div style="width:100px;text-align:center;">金额</div></th>
              <th><div style="width:70px;margin:0px auto;text-align:center;">币种</div></th>
              <th><div style="width:100px;margin:0px auto;text-align:center;">汇率</div></th>
              <th><div style="width:100px;margin:0px auto;text-align:center;">手续费</div></th>
              <th><div style="width:100px;margin:0px auto;text-align:center;">订单状态</div></th>
              <th class="rightborder"><div style="width:80px;text-indent:6px;text-align:center;">操作</div></th>
            </tr>
            </thead>
            <tbody>
	          <#list resultList as item>
                 <tr>
                     <td><input data-value="${item.id}" data-fileAddress="${item.fileAddress}" class="chk" type="checkbox" /></td>
                     <td>${item.orderCreateTimeStr}</td>
                     <td>${item.orderNo}</td>
                     <td>${item.transTypeStr}</td>
                     <#if requestDto.flag == '02'>
                     	<td>${item.batchInfoStr}</td>
                     </#if>
                     
                     <td style="white-space: pre-line;">${item.amtStr}</td>
                     <td style="white-space: pre-line;">${item.curStr}</td>
                     <td>${item.exchangeRate}</td>
                     <td>${item.feeAmtStr}</td>
                     <#if (item.status =='001' || item.status =='002' || item.status =='003' || item.status =='008' || item.status =='010'|| item.status =='011'|| item.status =='012') > <td class="td-green">${item.statusStr}</td></#if>
                     <#if (item.status =='004' || item.status =='005' || item.status =='006' || item.status =='007') > <td class="td-red">${item.statusStr}</td></#if> 
                     <#if item.status =='009'> <td>${item.statusStr}</td></#if> 
                     <td style="display:none;">${item.fileAddress}</td>
                     <td style="display:none;">${item.id}</td>
                     <td style="display:none;">${item.serialNumber}</td>
                     <td>
                     <#if (item.status !='004' && item.status !='005' && item.status !='006'&& item.status !='007'&& item.status !='009'&& item.status !='011'&& item.status !='012')>  
                    	 <a href="${ctx}/tradeQueryAuth/tradeInfo/previewSingleElectronicPDF.htm?orderNo=${item.orderNo}&transactionType=${item.transType}&fileAddress=${item.fileAddress}&serialNumber=${item.serialNumber}">电子回单</a>
                     </#if>
                      </td>
                 </tr>
              </#list>   
          </tbody>
        </table>
        </#if>
        </div> 
        
       <#if page??>
            <div class="page" id="pageClass">
                <div class="posi-right" >
                    <span class="fl">每页10条</span>                    
                    <a class="prev-page page-num border <#if page.currentPage==1> disable </#if> fl"  href="javascript:;" ><i class="arrow-left"></i>上一页</a>
                    <a class="next-page page-num border <#if page.currentPage==page.pages> disable </#if> fl" href="javascript:;" >下一页<i class="arrow-right"></i></a>

                    <span class="fl m-left">第${page.currentPage}页/共${page.pages}页   &nbsp;&nbsp;	向第</span><input type="text" name="" class="fl page-num input-num" id="targetPage" ><span class="fl">页</span>
                    <a class="fl goto border" href="javascript:;">跳转</a>
                </div>
                <input style="display:none" id="currentPage" name="currentPage" value="${page.currentPage}"></input>
                <input style="display:none" id="pageSize" value="${page.pages}"></input>
           </div>
       </#if> 
        <!-- 表单]]-->  
      </div>
    </div>
  </div>
  <div style="height:40px;"></div>
</form>
<!-- 弹出框 -->
<script type="text/html" id="exportError">
  <div class="exportErrorContent">
    <div class="exportErrorTip"><i></i>没有可以导出的数据</div>
    <div class="exportErrorBtn">知道了</div>
  </div>
</script>
<script language="javascript">
	var sysconfig = {
		ctx : "/eppscbp",
    stimestamp : '1613789111482'   
	};
</script>
<script type="text/javascript" data-main="${ctx}/scripts/eppscbp/tradeInfo/transaction_inquiry.js" src="${ctx}/scripts/lib/require/require.min.js"></script>
<!-- 外联js需要放在页面底部，body结束标签前 -->
</body>
</html>