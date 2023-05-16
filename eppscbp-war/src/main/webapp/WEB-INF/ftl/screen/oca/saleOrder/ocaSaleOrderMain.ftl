<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <title>苏宁国际卡支付平台</title>
    <link rel="stylesheet" href="${ctx}/style/css/oca/reset.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/commen.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/sales-order.css">
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
                <div class="item"><a href="${ctx}/oca/ocaHomeController/init.htm">首页</a></div>
                <div class="item current"><a href="${ctx}/oca/ocaSaleOrderController/init.htm">销售订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRefundOrderController/init.htm">退款订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRejectOrderController/init.htm">拒付订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaLogisticsInfoController/init.htm">物流信息上传</a></div>
            </div>
        </div>
    </div>
    <div class="main">
       <div class="main-criteria clearfix">
           <div class="criteria-list-item nth01">
               <div class="label">商户订单号：</div>
               <div class="inputbox"><input class="ordenumber" type="text"  name="merchantOrderNo" value="${(requestDto.merchantOrderNo)!''}"></div>
           </div>

           <div class="criteria-list-item nth02">
            <div class="label">收单单号：</div>
            <div class="inputbox"><input class="paymentno" type="text" name="receiptOrderNo" value="${(requestDto.receiptOrderNo)!''}"></div>
           </div>

           <div class="criteria-list-item nth03">
            <div class="label">订单状态：</div>
            <div class="inputbox jr-select">
                <i class="select-input-arrow-icon arrow-icon-closed"></i>
                <input type="text" readonly="" class="statuscode select-input empty" value="支付成功"  key="100">
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

           <div class="criteria-list-item nth04">
            <div class="label">订单币种：</div>
            <div class="inputbox jr-select">
                <i class="select-input-arrow-icon arrow-icon-closed"></i>
                <input type="text" class="currency select-input empty" value="全部"  key="">
                <ul class="select-box">
                      <#if currency??>
                                <#list currency as unit>
                                    <li class="select-item">
                                        <a class="sel-val" href="javascript:void(0);" key=${unit.code} value=${unit.description}>${unit.description}</a>
                                    </li>
                       </#list>
                      </#if> 
                       
                </ul> 

            </div>
           </div>
           
           <div class="criteria-list-item nth05">
            <div class="label">订单创建时间：</div>
            <div class="inputbox inputlsit">
                <input class="input creationstart" readonly id="creationstart" type="text">
                <input class="inputlater creationend" readonly id="creationend" type="text">
            </div>
           </div>
           
           <div class="criteria-list-item nth06">
            <div class="label">支付完成时间：</div>
            <div class="inputbox inputlsit">
                <input class="input paystart" readonly id="paystart" type="text">
                <input class="inputlater payend" readonly id="payend" type="text">
            </div>
           </div>           

       </div>

       <div class="query-box clearfix">
           <div class="query-btn queryfirest querybtn">查 询</div>
           <div class="query-btn resetbtn">重 置</div>
       </div>
       <div class="reminder-box">
        <div class="reminder">温馨提示：退款发起成功后，您可以在退款订单页面查看此笔退款详情</div>
        <div class="queryChannel" style="display: none">批量结果查询</div>
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
            <div class="operation shadowhow" style="width: 60px;">
                <div class="operation-th">操作</div>
                {{each list as items}}
                	<div class="operation-item">
                		{{if items.refundFlag=='1'}}
                			<span class="refund" data-id = {{items.receiptOrderNo}} data-merchantOrderNo = {{items.merchantOrderNo}}>退款</span>
                		{{else}}{{/if}}
                	</div>
                {{/each}}
            </div>
            <div class="manage-header clearfix">
                <div class="th thitem00">
                    <input style="padding-top: 30px;margin-top: 16px;" onclick="selectAll()" type="checkbox" id="chkAll" class="chkAll">
                </div>
                <div class="th thitem01">序号</div>
                <div class="th thitem02">订单状态</div>
                <div class="th thitem03">商户订单号</div>
                <div class="th thitem04">收单单号</div>
                <div class="th thitem05">订单创建时间</div>
                <div class="th thitem07">订单金额</div>
                <div class="th thitem08">清算金额</div>
            </div>
            {{each list as item index}}
            <div class="manage-list clearfix">
                    <div class="td thitem00"><input style="padding-top: 30px;margin-top: 16px;" type="checkbox" class="chk" id="{{item.receiptOrderNo}}" name="{{item.receiptOrderNo}}" value="{{item.receiptOrderNo}}" <#if isSelected> checked="checked"</#if> /></div>
                    <div class="td thitem01">{{index + 1}}</div>
                    <div class="td thitem02" >{{item.orderStatusStr}}</div>
                    <div class="td thitem03"><a href="${ctx}/oca/ocaSaleOrderController/saleOrderdetailInfo.htm?receiptOrderNo={{item.receiptOrderNo}}">{{item.merchantOrderNo}}</a></div>
                    <div class="td thitem04" title={{item.receiptOrderNo}}>{{item.receiptOrderNo}}</div>
                    <div class="td thitem05">{{item.orderCreateTimeStr}}</div>
                    <div class="td thitem07">{{item.amtStr}}</div>
                    <div class="td thitem08">{{item.clearingAmountStr}}</div>

                    <div class="td thitem11" style="display:none;" >{{item.receiptOrderNo}}</div>
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
            <div class="operation shadowhow" style="width: 60px;">
                <div class="operation-th">操作</div>
            </div>
            <div class="manage-header clearfix">
                    <div class="th thitem01">序号</div>
                    <div class="th thitem02">订单状态</div>
                    <div class="th thitem03">商户订单号</div>
                    <div class="th thitem04">收单单号</div>
                    <div class="th thitem05">订单创建时间</div>
                    <div class="th thitem07">订单金额</div>
                    <div class="th thitem08">清算金额</div>
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
            <div class="operation shadowhow" style="width: 60px;">
                <div class="operation-th">操作</div>
            </div>
            <div class="manage-header clearfix">
                    <div class="th thitem01">序号</div>
                    <div class="th thitem02">订单状态</div>
                    <div class="th thitem03">商户订单号</div>
                    <div class="th thitem04">收单单号</div>
                    <div class="th thitem05">订单创建时间</div>
                    <div class="th thitem07">订单金额</div>
                    <div class="th thitem08">清算金额</div>
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

    <!--退款弹框[[-->
    <script type="text/html" id="dialog-refund">
        <div class="dialog-refund clearfix">
            <div class="title">
                退款
                <div class="close"></div>
            </div>
            <div class="refund-content">
                <div class="refund-item">
                    商户订单号：
                    <div class="item-right">{{content.merchantOrderNo}}</div>
                </div>
                <div class="refund-item">
                    订单金额：
                    <div class="item-right">{{content.amtStr}}</div>
                </div>
                <div class="refund-item">
                    剩余可退金额(订单币种)：
                    <div class="item-right"><span id="refundAmt">{{content.refundAmtStr}} </span>{{content.cur}}<span class="record">退款记录</span></div>
                </div>
                <div class="refund-item">
                    剩余可退金额(清算币种)：
                    <div class="item-right" ><span id="refundAmtCny">{{content.refundAmtCnyStr}} </span>CNY</div>
                </div>   
                <div class="refund-item cour">
                    本次可退金额(订单币种)：
                    <div class="item-right">
                        <input type="text" class="returnmany" id="returnmany">
                        {{content.cur}}
                        <div class="err-txt">不能大于剩余可退金额（订单币种）</div>
                    </div>
                </div>   
                <div class="refund-item">
                    本次退款金额(清算币种)：
                    <div class="item-right"><span class="recurrency">--</span>{{content.currency}} CNY</div>
                </div>  
                <div class="refund-bttom clearfix">
                    <div class="refund-btn refundsrue">确认退款</div>
                    <div class="refund-btn canor">取消</div>
                </div>             
                
            </div>
        </div>
    </script>

    <!--]]退款弹框-->

    <!--退款弹框[[-->
    <script type="text/html" id="dialog-refund-success">
        <div class="dialog-refund-success">
            退款发起成功！
        </div>        
    </script>

    <!--]]退款弹框-->

<!--comm-失败弹框[[-->
    <script type="text/html" id="dialog-err">
        <div class="dialog-err">
            系统异常，请稍后重试
        </div>
    </script>
    <!--]]comm-失败弹框-->

    <!--comm-失败弹框-其他[[-->
        <script type="text/html" id="qtdialog-err">
            <div class="dialog-err">
                {{content}}
            </div>
        </script>
        <!--]]comm-失败弹框-其他-->   

    <!--comm-失败弹框[[-->
    <script type="text/html" id="dialog-order-err">
        <div class="dialog-order-err">
            当前订单有退款中订单，请等待处理完后再发起
            <div class="err-close"></div>
        </div>
    </script>
    <!--]]comm-失败弹框-->

    <!--批量结果查询弹框[[-->
    <script type="text/html" id="batch-query-success">
        <div class="batch-query-success">
            批量结果查询发起成功！
        </div>
    </script>

    <!--]]批量结果查询弹框-->
    <script>
        //全选
        function selectAll(){
            console.log("232")
            var checklist = document.getElementsByClassName("chk");
            console.log("432")
            if(document.getElementById("chkAll").checked)
            {
                for(var i=0;i<checklist.length;i++)
                {
                    checklist[i].checked = 1;
                }
            }else{
                for(var j=0;j<checklist.length;j++)
                {
                    checklist[j].checked = 0;
                }
            }
        }
    </script>


    <script data-main="${ctx}/js/oca/sales-order.js" src="${ctx}/js/oca/lib/require.min.js"></script>
</body>
</html>
