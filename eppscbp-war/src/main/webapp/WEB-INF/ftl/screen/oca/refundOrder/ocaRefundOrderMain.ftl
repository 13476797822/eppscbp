<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <title>苏宁国际卡支付平台</title>
    <link rel="stylesheet" href="${ctx}/style/css/oca/reset.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/commen.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/refund-order.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/calendar-alter.css">
</head>
<script>
    var baseJSPath ='/eppscbp/js/oca/';
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
       <div class="main-criteria clearfix">
           <input class="receiptOrderNo" type="hidden" value=${receiptOrderNo}>
           <div class="criteria-list-item">
               <div class="label">退款订单号：</div>
               <div class="inputbox"><input class="ordenumber" type="text"></div>
           </div>

           <div class="criteria-list-item">
            <div class="label">商户订单号：</div>
            <div class="inputbox"><input class="paymentno" type="text" value=${merchantOrderNo}></div>
           </div>

           <div class="criteria-list-item nth03">
            <div class="label">退款状态：</div>
            <div class="inputbox jr-select">
                <i class="select-input-arrow-icon arrow-icon-closed"></i>
                <input type="text" readonly="" class="statuscode select-input empty" value="全部"  key="">
	               
	                <ul class="select-box">
	                <li class="select-item">
	                   <a class="sel-val" href="javascript:void(0);" key="" value="全部">全部</a>
	                </li>
                     <#if status??>
                                <#list status as unit>
                                    <li class="select-item">
                                        <a class="sel-val" href="javascript:void(0);" key=${unit.code} value=${unit.description}>${unit.description}</a>
                                    </li>
                                </#list>
                      </#if>                         
                </ul>               
            </div>
           </div>

           <div class="criteria-list-item">
            <div class="label">退款创建时间：</div>
            <div class="inputbox inputlsit">
                <input class="input creationstart" readonly id="creationstart" type="text">
                <input class="inputlater creationend" readonly id="creationend" type="text">
            </div>
           </div>
           
           <div class="criteria-list-item">
            <div class="label">退款完成时间：</div>
            <div class="inputbox inputlsit">
                <input class="input paystart" readonly id="paystart" type="text">
                <input class="inputlater payend" readonly id="payend" type="text">
            </div>
           </div> 
           
           <div class="criteria-list-item nth03">
            <div class="query-btn resetbtn">重 置</div>
            <div class="query-btn queryfirest querybtn">查 询</div>
           </div>

       </div>
       
       <div class="reminder-box">
        <div class="export">导出</div>
       </div>

       <div class="query-box-list clearfix">

       </div>      

        
    </div>

    <div class="footer"></div>

    <!-- 列表 [[ -->
    <script type="text/html" id="loan-tpl">
        {{if totalCount > 0}}
        <div id="manage-table">
            <div class="manage-header clearfix">
                    <div class="th thitem01">序号</div>
                    <div class="th thitem02">退款状态</div>
                    <div class="th thitem03">退款订单号</div>
                    <div class="th thitem04">商户订单号</div>
                    <div class="th thitem05">退款创建时间</div>
                    <div class="th thitem07">退款金额(订单币种)</div>
                    <div class="th thitem08">退款金额(清算币种)</div>

            </div>
            {{each list as item index}}
            <div class="manage-list clearfix">
                    <div class="td thitem01">{{index + 1}}</div>
                    <div class="td thitem02">{{item.refundStatusStr}}</div>
                    <div class="td thitem03"><a href="${ctx}/oca/ocaRefundOrderController/refundOrderDetailInfo.htm?refundOrderNo={{item.refundOrderNo}}">{{item.refundOrderNo}}</a></div>
                    <div class="td thitem04">{{item.merchantOrderNo}}</div>
                    <div class="td thitem05">{{item.refundCreateTimeStr}}</div>
                    <div class="td thitem07">{{item.refundAmtStr}}</div>
                    <div class="td thitem08">{{item.refundAmtCnyStr}}</div>
            </div>
            {{/each}}
            
        </div>
        {{/if}}
        {{if totalCount > 0}}
        <div class="jr-pagination mt20">
            <div class="totalCount">当前页面展示<span> 10 </span>条， 共 <span> {{ totalCount }} </span> 条</div>
            <div class="fr">
                <div class="previous fl"><span class="ml20"><</span></div>
                <div class="page fl">
                    {{#paginationHtml}}
                </div>
                <div class="next fl"><span class="mr20">></span></div>
                <span class="fl pagination-txt">跳到</span>
                <input class="jump-to-num fl" value="6">
                <span class="fl pagination-txt mr7">页</span>
            </div>
        </div>
        {{/if}}
    </script>

    <!-- 查询等待模版 [[ -->
    <script type="text/html" id="wait-tpl">
        <div id="manage-table">
            <div class="manage-header clearfix">
                      <div class="th thitem01">序号</div>
                      <div class="th thitem02">退款状态</div>
                      <div class="th thitem03">退款订单号</div>
                      <div class="th thitem04">商户订单号</div>
                      <div class="th thitem05">退款创建时间</div>
                      <div class="th thitem07">退款金额(订单币种)</div>
                      <div class="th thitem08">退款金额(清算币种)</div>
            </div>
            <ul class="g-row wait-wrap">
                <li class="wait">
                    <div class="info">
                        <img width="24px" height="12px"
                            src="https://resjrprd.suning.com/finance/project/myfinance/style/images/loading.gif"></img>
                        <span class="wait-txt">正在查询，请稍候...</span>
                    </div>
                </li>
            </ul>
        </div>
    </script>
    <!-- 查询等待模版 ]] -->

    <!-- 查询为空模版 [[ -->
    <script type="text/html" id="empty-tpl">
        <div id="manage-table">
            <div class="manage-header clearfix">
                      <div class="th thitem01">序号</div>
                      <div class="th thitem02">退款状态</div>
                      <div class="th thitem03">退款订单号</div>
                      <div class="th thitem04">商户订单号</div>
                      <div class="th thitem05">退款创建时间</div>
                      <div class="th thitem07">退款金额(订单币种)</div>
                      <div class="th thitem08">退款金额(清算币种)</div>
            </div>
            <ul class="g-row wait-wrap">
                <li class="wait">
                    <div class="info">
                        <div class="desc">
                            <img class="empty" src="${ctx}/style/images/oca/icon-info-lg.png" alt="无数据" title="">
                            抱歉，没有找到符合筛选条件的记录
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </script>
    <!-- 查询为空模版 ]] -->





    <script data-main="${ctx}/js/oca/refund-order.js" src="${ctx}/js/oca/lib/require.min.js"></script>
</body>
</html>
