<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <title>苏宁国际卡支付平台</title>
    <link rel="stylesheet" href="${ctx}/style/css/oca/reset.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/commen.css">
    <link rel="stylesheet" href="${ctx}/style/css/oca/logistics-information-upload.css">
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
                <div class="item"><a href="${ctx}/oca/ocaRefundOrderController/init.htm">退款订单</a></div>
                <div class="item"><a href="${ctx}/oca/ocaRejectOrderController/init.htm">拒付订单</a></div>
                <div class="item current"><a href="${ctx}/oca/ocaLogisticsInfoController/init.htm">物流信息上传</a></div>
            </div>
        </div>
    </div>
    

    <div class="main">
    <div class="main-hidden">
       <div class="main-criteria clearfix">
           <div class="criteria-list-item">
               <div class="label">商户订单号：</div>
               <div class="inputbox"><input class="ordenumber" type="text"></div>
           </div>
    
           <div class="criteria-list-item">
            <div class="label">物流状态：</div>
            <div class="inputbox jr-select">
                <i class="select-input-arrow-icon arrow-icon-closed"></i>
                <input type="text" readonly="" class="statuscode select-input empty" value="全部" key="">
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

           <div class="criteria-list-item nth03">
            <div class="label">支付时间：</div>
            <div class="inputbox inputlsit">
                <input class="input creationstart" readonly id="creationstart" type="text">
                <input class="inputlater creationend" readonly id="creationend" type="text">
            </div>
           </div>
           
           <div class="criteria-list-item">
            <div class="label">上传时间：</div>
            <div class="inputbox inputlsit">
                <input class="input paystart" readonly id="paystart" type="text">
                <input class="inputlater payend" readonly id="payend" type="text">
            </div>
           </div> 
           
           <div class="criteria-list-item nthlaster">
            <div class="query-btn resetbtn">重 置</div>
            <div class="query-btn queryfirest querybtn">查 询</div>
           </div>

       </div>
       
       <div class="reminder-box">
        <div class="bulk-import">批量导入</div>   
        <div class="export">导出</div>
       </div>

       <div class="query-box-list clearfix">

       </div>      
	</div> 
        
    </div>

    <div class="footer"></div>

    <!-- 列表 [[ -->
    <script type="text/html" id="loan-tpl">
        {{if totalCount > 0}}
        <div id="manage-table">
            <div class="manage-header clearfix">
                    <div class="th thitem01">序号</div>
                    <div class="th thitem02">物流状态</div>
                    <div class="th thitem03">商户订单号</div>
                    <div class="th thitem04">订单金额</div>
                    <div class="th thitem05">支付时间</div>
                    <div class="th thitem06">发货日期</div>
                    <div class="th thitem07">物流公司名称</div>
                    <div class="th thitem08">物流单号</div>
            </div>
            {{each list as item index}}
            <div class="manage-list clearfix">
                    <div class="td thitem01">{{index + 1}}</div>
                    <div class="td thitem02">{{item.logisticsStatusStr}}</div>
                    <div class="td thitem03">{{item.merchantOrderNo}}</div>
                    <div class="td thitem04">{{item.amtStr}}</div>
                    <div class="td thitem05">{{item.payFinishTimeStr}}</div>
                    <div class="td thitem06">{{item.shipingDateStr}}</div>
                    <div class="td thitem07" title={{item.logisticsCompNameStr}}>{{item.logisticsCompNameStr}}</div>
                    <div class="td thitem08" title={{item.logisticsWoNoStr}}>{{item.logisticsWoNoStr}}</div>
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
                    <div class="th thitem02">物流状态</div>
                    <div class="th thitem03">商户订单号</div>
                    <div class="th thitem04">订单金额</div>
                    <div class="th thitem05">支付时间</div>
                    <div class="th thitem06">发货日期</div>
                    <div class="th thitem07">物流公司名称</div>
                    <div class="th thitem08">物流单号</div>
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
                    <div class="th thitem02">物流状态</div>
                    <div class="th thitem03">商户订单号</div>
                    <div class="th thitem04">订单金额</div>
                    <div class="th thitem05">支付时间</div>
                    <div class="th thitem06">发货日期</div>
                    <div class="th thitem07">物流公司名称</div>
                    <div class="th thitem08">物流单号</div>
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

    <!--文件导入[[-->
    <script type="text/html" id="dialog-upload">
        <div class="dialog-upload dialog-hide">
            <div class="title">
                物流信息批量上传
                <div class="close"></div>
            </div>
            <div class="upload-content clearfix">
                <div class="label">文件上传：</div>
                <input type="text" class="fileshow" readonly>
                <input type="file" id="fileinput" name="fileinput" accept=".xls,.xlsx" class="fileinput hide">
                <div class="upload-select">选择文件</div>
                <div class="download">下载模板</div>
            </div>
            <div class="pre-import">
                <div class="pre-title">预导入结果</div>
                <div class="pre-heard clearfix">
                    <div class="list-item nth01">总笔数</div>
                    <div class="list-item">成功笔数</div>
                    <div class="list-item">失败笔数</div>
                    <div class="list-item">结果文件</div>
                </div>
                <div class="pre-content-list clearfix">
                    <div class="list-item nth01">--</div>
                    <div class="list-item nth02">--</div>
                    <div class="list-item nth03">--</div>
                    <div class="list-item nth04 import-download">下载</div>
                </div>        
            </div>
            <div class="uplod-btn nobtn clearfix">
                <div class="btn preservbtn">保存</div>
                <div class="btn cancel">取消</div>
            </div>

        </div>
    </script>

    <!--]]文件导入-->

    <!--弹框[[-->
    <script type="text/html" id="dialog-success">
        <div class="dialog-success">
            {{content}}
        </div>        
    </script>
    <!--]]弹框-->
	<!--comm-失败弹框[[-->
    <script type="text/html" id="dialog-err">
        <div class="dialog-err">
            {{content}}
        </div>
    </script>
    <!--]]comm-失败弹框-->

    <script data-main="${ctx}/js/oca/logistics-information-upload.js" src="${ctx}/js/oca/lib/require.min.js"></script>
    <script>
        
    </script>
</body>
</html>
