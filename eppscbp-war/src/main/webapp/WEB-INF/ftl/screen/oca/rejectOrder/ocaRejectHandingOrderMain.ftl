<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <title>苏宁国际卡支付平台</title>
    <link rel="stylesheet" href="${ctx}/style/css/oca/reset.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/commen.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/dishonor-handling.css">
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
                <div class="item"><a href="${ctx}/oca/ocaSaleOrderController/init.htm">销售订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRefundOrderController/init.htm">退款订单</a></div>
                <div class="item current"><a href="${ctx}/oca/ocaRejectOrderController/init.htm">拒付订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaLogisticsInfoController/init.htm">物流信息上传</a></div>
            </div>
        </div>
    </div>
    

    <div class="main">
        <div class="go-backorder"><a href="${ctx}/oca/ocaHomeController/init.htm">返回上一级</a></div>
        <div class="reminder-box">
            <div class="reminder">温馨提示：超过最后处理时间则自动接受拒付；操作完成后，您可以在拒付订单页面查看对应的拒付详情</div>
        </div>


       <div class="query-box-list clearfix">

       </div>
        
    </div>

    <div class="footer"></div>

    <!-- 列表 [[ -->
    <script type="text/html" id="loan-tpl">
        {{if totalCount > 0}}
        <div id="manage-table">
            <div class="operation shadowhow">
                <div class="operation-th">操作</div>
                {{each list as items}}
                <div class="operation-item" data-rejectOrderNo = {{items.rejectOrderNo}} data-paymentNo = {{items.merchantOrderNo}} data-orderAmount = {{items.amtStr}} data-rejectComment = {{items.rejectCommentStr}} data-rejectAmt = {{items.rejectAmtStr}} data-rejectAmtCny = {{items.rejectAmtCnyStr}} data-cur = {{items.cur}}>
                    <span class="accept">接受</span>
                    <span class="appeal">申诉</span>
                </div>
                
                {{/each}}
            </div>
            <div class="manage-header clearfix">
                <div class="th thitem01">序号</div>
                <div class="th thitem02">拒付业务单号</div>
                <div class="th thitem03">商户订单号</div>
                <div class="th thitem04">拒付申请时间</div>
                <div class="th thitem05">最后处理时间</div>
                <div class="th thitem08">拒付金额</div>
                <div class="th thitem07">清算金额</div>
                <div class="th thitem09">拒付处理费</div>
                <div class="th thitem10">拒付处理状态</div>
                <div class="th thitem11">拒付原因</div>
            </div>
            {{each list as item index}}
            <div class="manage-list clearfix">
                <div class="td thitem01">{{index + 1}}</div>
                <div class="td thitem02">{{item.rejectOrderNo}}</div>
                <div class="td thitem03">{{item.merchantOrderNo}}</div>
                <div class="td thitem04">{{item.applyDateStr}}</div>
                
                {{if item.lastDateFlag == "T"}}
                	<div class="td thitem05">{{item.lastDateStr}}</div>
                {{else}}
                	<div class="td thitem04">{{item.lastDateStr}}</div>
                {{/if}}
                
                <div class="td thitem08">{{item.rejectAmtStr}}</div>
                <div class="td thitem07">{{item.rejectAmtCnyStr}}</div>
                <div class="td thitem09">{{item.rejectFeeStr}}</div>
                <div class="td thitem10">{{item.statusStr}}</div>
                <div class="td thitem11" title="{{item.reasonCode}}">{{item.reasonCode}}</div>
                
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
            <div class="operation shadowhow">
                <div class="operation-th">操作</div>
            </div>
            <div class="manage-header clearfix">
                <div class="th thitem01">序号</div>
                <div class="th thitem02">拒付业务单号</div>
                <div class="th thitem03">商户订单号</div>
                <div class="th thitem04">拒付申请时间</div>
                <div class="th thitem05">最后处理时间</div>
                <div class="th thitem08">拒付金额</div>
                <div class="th thitem07">清算金额</div>           
                <div class="th thitem09">拒付处理费</div>
                <div class="th thitem10">拒付处理状态</div>
                <div class="th thitem11">拒付原因</div>
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
            <div class="operation">
                <div class="operation-th">操作</div>
            </div>
            <div class="manage-header clearfix">
                <div class="th thitem01">序号</div>
                <div class="th thitem02">拒付业务单号</div>
                <div class="th thitem03">商户订单号</div>
                <div class="th thitem04">拒付申请时间</div>
                <div class="th thitem05">最后处理时间</div>
                <div class="th thitem08">拒付金额</div>
                <div class="th thitem07">清算金额</div>   
                <div class="th thitem09">拒付处理费</div>
                <div class="th thitem10">拒付处理状态</div>
                <div class="th thitem11">拒付原因</div>
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


    <!--接受弹框[[-->
    <script type="text/html" id="dialog-accept">
        <div class="dialog-accept">
            <div class="title">
                拒付处理-接受拒付
                <div class="close"></div>
            </div>
            <div class="accept-content">
                <div class="accept-content-top">
                    <span class="line-color">商户订单号：{{content.merchantOrderNo}}</span>
                    <span class="line-block">订单金额：{{content.rejectOrderAmt}} {{content.cur}}</span>
                </div>
                <div class="accept-content-top">
                    <span class="line-color">拒付金额：{{content.rejectAmtStr}} {{content.cur}}</span>
                    <span class="line-block">清算金额：{{content.rejectAmtCnyStr}} CNY</span>
                </div>
                <div class="accept-content-top">
                    <span class="line-color-reason">拒付原因：{{content.rejectCommentStr}}</span>
                </div>
            </div>
            <div class="sure-txt">确定接受拒付？</div>
            <div class="sure-btn clearfix">
                <div class="btn surebtn">确定</div>
                <div class="btn canuer">取消</div>
            </div>
            <div class="messageBox">
                {{if content.totalCount > 0}}
                {{each messages as item}}
                <span>此交易已存在{{item.flagStr}}订单,
                        <#--商户订单号:{{item.merchantOrderNo}}，-->
                        {{item.flagStr}}金额 : {{item.amtStr}}，
                        交易币种 : {{item.cur}}，
                        {{item.flagStr}}日期 : {{item.createTimeStr}}
                    </span><br>
                {{/each}}
                {{/if}}
            </div>
        </div>
    </script>
    <!--]]接受弹框-->

    <!--拒付处理-拒付申诉[[-->
    <script type="text/html" id="dialog-appeal">
    <div class="dialog-appeal">
        <div class="title">
            拒付处理-拒付申诉
            <div class="close"></div>
        </div>

        <div class="appeal-contents">
            <div class="appeal-contents-top">
                <span class="line-color" style="">商户订单号：{{content.merchantOrderNo}}</span>
                <span class="line-block">订单金额：{{content.rejectOrderAmt}} {{content.cur}}</span>
            </div>
            <div class="appeal-contents-top">
                <span class="line-color">拒付金额：{{content.rejectAmtStr}} {{content.cur}}</span>
                <span class="line-block">清算金额：{{content.rejectAmtCnyStr}} CNY</span>
            </div>
            <div class="appeal-contents-top">
                <span class="line-color-reason">拒付原因：{{content.rejectCommentStr}}</span>
            </div>
        </div>

        <div class="appeal-content">
            <div class="appeal-content-item clearfix">
                <div class="appeal-title">申诉理由：</div>
                <div class="appeal-txt">
                    <textarea class="reason" maxlength="200"></textarea>
                    <div class="reason-infor"><span class="txt-num">0</span>/200</div>
                </div>
            </div>
            <div class="appeal-content-item clearfix">
                <div class="appeal-title">文件上传：</div>
                <div class="appeal-txt">
                    <input class="showfile" type="text" readonly>
                    <div class="fileclass">选择文件</div>
                    <input class="fileopt" type="file" id="fileopt" name="file">
                </div>
            </div>
            <div class="file-infor">
                <span>文件支持RAR/ZIP/DOC/DOCX/XLSX/XLS/PDF/BMP/</span>JPEG/PNG格式：大小不超过100M
            </div>
            <div class="file-format">
                <div class="file-format-title">申请材料包含但不限于：</div>
                <div class="file-format-list">
                    <div class="list-item">1、和消费者沟通的邮件或其他信息往来；</div>
                    <div class="list-item">2、物流证明：物流轨迹信息、收货人签收信息、海关说明、配送政策；</div>
                    <div class="list-item">3、商品信息：订单截图、商品描述、商品照片、商品授权或采购说明；</div>
                    <div class="list-item">4、已发生过退款的证明。</div>
                </div>
            </div>
            <div class="appeal-btn">
                <div class="btn btntj isbtn">保存</div>
                <div class="btn appeal-caner">取消</div>
            </div>
        </div>
    </div>
    </script>

    <!--退款弹框[[-->
    <script type="text/html" id="dialog-success">
        <div class="dialog-success">
            {{content}}
        </div>        
    </script>
    <!--]]退款弹框--> 
       
	<!--comm-失败弹框[[-->
    <script type="text/html" id="dialog-txt-err">
        <div class="dialog-err">
            {{content}}
        </div>
    </script>
    <!--]]comm-失败弹框-->

    <!--]]拒付处理-拒付申诉-->
    <script data-main="${ctx}/js/oca/dishonor-handling.js" src="${ctx}/js/oca/lib/require.min.js"></script>
</body>
</html>