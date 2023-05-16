    <meta charset="utf-8" />
    <title>资金批付</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/fund-issue.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/style/css/reset.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/style/css/header.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/style/css/cpBatchPaymentInit.css" type="text/css" />
    

 <#include "/screen/header.ftl">
<div class="platform-container">
    <div class="platform-panel">
        <div class="top-head-box clearfix">
            <div class="head-amount-wrap fl">
                <p class="head-amount-content">待批付金额：<span class="num" >${prePayAmount}</span> 元</p>
                <input style="display:none" id="prePayAmount" name="prePayAmount" value=${prePayAmount}></input>
                <div class="tip-box">
                    <i class="tip-icon"></i>
                    <div class="tip-pop-box">
                        <i class="tip-arrow before"></i>
                        <i class="tip-arrow after"></i>
                        <div class="tip-pop-cnt">出款总额不大于待批付金额</div>
                    </div>
                </div>
            </div>
            <div class="head-ctrl-wrap fr">
                <a href="javascript:void(0);" class="platform-button J_addBtn"><i class="btn-icon icon-add"></i>添加</a>
                <a href="javascript:void(0);" class="platform-button J_delBtn"><i class="btn-icon icon-del"></i>删除</a>
                <a href="javascript:void(0);" class="platform-button J_importBtn"><i class="btn-icon icon-import"></i>导入</a></div>
        </div>
        <div class="fund-table-wrap J_fundTable" >
            <table class="platform-table batch-payment-table" id="fund-table-wrap">
                <thead>
                <tr>
                    <th width="56"><i class="platform-checkbox J_checkAll"></i></th>
                    <th>境内收款方编号</th>
                    <th>开户名</th>
                    <th>银行账号</th>
                    <th>出款金额</th>
                    <th>附言</th>
                </tr>
                </thead>
                <tbody>
                
                </tbody>
            </table>
            <p class="table-error-tip"></p>
            <a href="javascript:void(0);" class="platform-button active disabled submit-btn J_submitBtn ">批付</a>
        </div>
    </div>
</div>


<script type="text/html"  id="confirm-table-body">
	{{each merchantList as item}}
    <tr>
    	<td width="80"><i class="platform-checkbox">
    	<input style="display:none" id="disPayeeMerchantCode" name="disPayeeMerchantCode" value={{item.payeeMerchantCode}}></input>
    	<input style="display:none" id="disPayeeMerchantName" name="disPayeeMerchantName" value={{item.payeeMerchantName}}></input>
    	<input style="display:none" id="disBankAccountNo" name="disBankAccountNo" value={{item.bankAccountNo}}></input>
    	</i></td>
    	
    	<td>{{item.payeeMerchantCode}}</td>    	
    	<td>{{item.payeeMerchantName}}</td>
    	<td>{{item.bankAccountNo}}</td>
    	<td><input type="text" class="platform-input" name="amount">
    	<td><input type="text" class="platform-input" name="orderName">
    </tr>
    {{/each}}
</script>





<script type="text/html" id="upload-tpl">
    <form method='post' id="f4" name="f4">
    <input type="text" name="prePayAmountHide" value="" id="prePayAmountHide" class="hide"></input>   
        <div class="pop-upload upload-files">
            <div>
                <label>文件上传：</label>
                <input type="text" class="input file-path" readonly="true" name="filePath" value="" id="fileAdd">
                <input type="text" name="fileAddress" id="fileAddress" value="" class="input file-path hide"></input>
                <div class="file-operation">
                    <a class="up" href="javascript:;">选择文件
                        <form id="uploadForm" enctype="multipart/form-data">
                            <input type="file" id="file-btn" name="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"></a>
    					</form>
    <a class="download" href="${ctx}/template/file/cp_batch_payment.xlsx">下载模版</a>
    </div>
    <div class="clear"></div>
    </div>
    <!-- 上传文件报错 &ndash;&gt; -->
    <div class="result-text">
        <div class="wait" id="wait">
            <img src="${ctx}/style/images/empty-tip.jpg">
            <p class="title">请上传模版文件</p>
        </div>
        <div class="success hide" id="uploadSuccess">
            <img src="${ctx}/style/images/icon_success_24.png">
            <p class="title">上传成功</p>
        </div>
        <div class="fail hide" id="uploadFail">
            <img src="${ctx}/style/images/icon_error_32.png">
            <p class="title">上传失败，请修改后重新上传</p>
            <span class="err-text"></span>
        </div>
    </div>
    <a href="javascript:;" class="submit-btn J_setfile disabled" id="J_placeOrder"  style="margin-left:144px;">保 存</a>
    <a href="javascript:;" class="cancel-btn">取 消</a>
    </div>
    </form>
</script>



<!-- 添加收款账号弹窗 -->
<script type="text/html" id="add-account-dialog-tpl">
    <form method='post' id="f0" name="f0">
    <div class="add-account-dialog-body">
        <div class="account-search-form clearfix">
            <dl>
                <dt>境内收款方编号：</dt>
                <dd>
                    <input type="text" class="platform-input" id="payeeMerchantCode" name="payeeMerchantCode">
                </dd>
            </dl>
            <dl>
                <dt>开户名：</dt>
                <dd>
                    <input type="text" class="platform-input" id="accountName" name="accountName">
                </dd>
            </dl>
            <dl>
                <dt>银行账号：</dt>
                <dd>
                    <input type="text" class="platform-input" id="bankAccount" name="bankAccount">
                </dd>
            </dl>
            <dl>
                <dt></dt>
                <dd>
                    <a href="javascript:;" class="platform-button active J_search" >查询</a>
                </dd>
            </dl>
        </div>
        <div class="platform-scroll-table J_table">
            <div class="platform-scroll-table-head">
                <table class="platform-table">
                    <thead>
                    <tr>
                        <th width="80"><i class="platform-checkbox"></i></th>
                        <th class="">境内收款方编号</th>
                        <th>开户名</th>
                        <th>银行账号</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div class="platform-scroll-table-body">
                <table class="platform-table">
                    <tbody>
                    
                    </tbody>
                </table>
            </div>
        </div>
        <div class="dialog-ctrl">
            <a href="javascript:void(0);" class="platform-button active mr J_submit disabled">确定</a>
            <a href="javascript:void(0);" class="platform-button J_cancel">取消</a>
        </div>
    </div>
    </form>
</script>

<script type="text/html"  id="platform-scroll-table-body">
	{{each merchantList as item}}
    <tr>
    	<td width="80"><i class="platform-checkbox">
    	<input style="display:none" id="disPayeeMerchantCode" name="disPayeeMerchantCode" value={{item.payeeMerchantCode}}></input>
    	<input style="display:none" id="disPayeeMerchantName" name="disPayeeMerchantName" value={{item.payeeMerchantName}}></input>
    	<input style="display:none" id="disBankAccountNo" name="disBankAccountNo" value={{item.bankAccountNo}}></input>
    	</i></td>
    	
    	<td>{{item.payeeMerchantCode}}</td>    	
    	<td>{{item.payeeMerchantName}}</td>
    	<td>{{item.bankAccountNo}}</td>
    	
    </tr>
    {{/each}}
</script>

<!-- 20210319分页开始 -->
<!-- 批付信息确认 -->
<script type="text/html" id="inf-confirm-dialog-tpl">
    <div class="inf-confirm-dialog-body">
        <div class="platform-scroll-table J_table" id="alertTable">
            
        </div>
        <p class="inf-tip-txt">总笔数：{{confirmDetailArray.totalNumber}}笔；出款总额：{{confirmDetailArray.totalAmount}}元；请确认信息无误，提交后金额不能修改</p>
        <div class="dialog-ctrl">
            <a href="javascript:void(0);" class="platform-button active mr J_submit">确定</a>
            <a href="javascript:void(0);" class="platform-button J_cancel">取消</a>
        </div>
    </div>
</script>
<script type="text/html" id="table-result-tpl">
    <div style="max-height: 250px;overflow-y: auto;">
        <div class="platform-scroll-table-head">
            <table class="platform-table">
                <thead>
                <tr>
                    <th>境内收款方编号</th>
                    <th>开户名</th>
                    <th>银行账号</th>
                    <th>出款金额</th>
                    <th>附言</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="platform-scroll-table-body">
            <table class="platform-table">
                <tbody>
                    {{each list as item}}
                    <tr>
                    <td>{{item.payeeMerchantCode}}</td>    	
                    <td>{{item.payeeMerchantName}}</td>
                    <td>{{item.bankAccountNo}}</td>
                    <td>{{item.amount}}</td>
                    <td>{{item.orderName}}</td>
                    </tr>
                    {{/each}}
                </tbody>
            </table>
        </div>
    </div>
    <div class="jr-pagination margin">
        <div class="previous fl"><i></i><span class="ml20">上一页</span></div>
        <div class="page fl">
            {{#paginationHtml}}
        </div>
        <div class="next fl"><span class="mr20 fr">下一页</span><i></i></div>
        <span class="fl pagination-txt mr7">向第</span>
        <input class="jump-to-num fl" value="6"></input>
        <span class="fl pagination-txt mr7">页</span>
        <span class="jump fl pagination-txt">跳转</span>
    </div>
</script>
<!-- 查询等待模版 [[ -->
<script type="text/html" id="wait-tpl">
    <ul class="g-row wait-wrap">
        <li class="wait">
            <div class="info">
                <img width="24px" height="12px" src="https://resjrprd.suning.com/finance/project/myfinance/style/images/loading.gif"></img>
                <span class="wait-txt">正在查询，请稍候...</span>
            </div>
        </li>
    </ul>
</script>
<!-- 查询等待模版 ]] -->
<!-- 20210319分页结束 -->



<script type="text/javascript" data-main="${ctx}/js/cpBatchPayment/main" src="${ctx}/scripts/lib/require/require.min.js"></script>
