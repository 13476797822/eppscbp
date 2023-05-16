<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>批付明细</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/batch-detail.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/reset.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/header.css" type="text/css"/>
</head>
<body>
<#include "/screen/header.ftl">
<div class="platform-container">
    <div class="platform-panel">
        <form method='post' id="f0" name="f0">
            <div class="search-form-box clearfix J_searchForm">
                <dl class="mb">
                    <dt>收款方开户名：</dt>
                    <dd><input type="text" class="platform-input" name="payeeName" value="${(requestDto.payeeName)!''}">
                    </dd>
                </dl>
                <dl class="mb">
                    <dt>出款状态：</dt>
                    <dd>
                        <div class="platform-select jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="status" class="select-input input_text empty"
                                   value="${(requestDto.status)!''}" key="">
                            <ul class="select-box">
                            <#if status??>
                                <#list status as unit>
                                    <li class="select-item">
                                        <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                    </li>
                                </#list>
                            </#if>
                            </ul>
                        </div>
                    </dd>
                </dl>
                <dl class="mb">
                    <dt>批次号：</dt>
                    <dd><input type="text" class="platform-input" name="batchNo" value="${(requestDto.batchNo)!''}">
                    </dd>
                </dl>
                <dl>
                    <dt>创建时间：</dt>
                    <dd>
                        <input type="text" class="platform-input date" id="startDateInput" name="createStartTime"
                               readonly="true"
                               value="${(requestDto.createStartTime)!''}">
                        <span class="date-txt">至</span>
                        <input type="text" class="platform-input date" id="endDateInput" name="createEndTime"
                               readonly="true"
                               value="${(requestDto.createEndTime)!''}">
                    </dd>
                </dl>
                <dl>
                    <dt></dt>
                    <dd>
                        <a href="javascript:void(0);" class="platform-button active J_searchBtn"
                           onclick="submitQuery();">查询</a>
                        <a href="javascript:void(0);" class="platform-button J_resetBtn">重置</a>
                    </dd>
                </dl>
            </div>
            <div class="batch-detail-table-wrap">
                <div class="top-ctrl clearfix">
                    <a href="" class="platform-button fr" onclick="submitExport();"><i
                            class="btn-icon icon-export"></i>导出</a>
                </div>
                <div class="J_listContent">
                <#if errMessage??>
                    <div class="no-result"><i></i>${errMessage}</div>
                </#if>
                <#if resultList??>
                    <div class="batch-detail-table" id="fixed-table-result">
                        <table class="platform-table">
                            <thead>
                            <tr>
                                <th width="60">序号</th>
                                <th>批次号</th>
                                <th>批次总金额</th>
                                <th>收款方开户名</th>
                                <th>银行账号</th>
                                <th>交易流水号</th>
                                <th>出款金额</th>
                                <th>出款状态</th>
                                <th width="160">附言</th>
                                <th width="160">出款失败原因</th>
                                <th>创建时间</th>
                                <th>申请时间</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list resultList as item>
                                <tr>
                                    <td>${item_index+1}</td>
                                    <td>${item.batchNo!''}</td>
                                    <td class="fail-detail" title="${item.batchSumAmt}">${item.batchSumAmtStr!''}</td>
                                    <td class="fail-detail" title="${item.payeeName}">${item.payeeName!''}</td>
                                    <td>${item.bankAcc!''}</td>
                                    <td>${item.serialNo!''}</td>
                                    <td class="fail-detail" title="${item.payAmt}">${item.payAmtStr!''}</td>
                                    <td>${item.statusName!''}</td>
                                    <td title="${item.orderName!''}">${item.orderName!''}</td>
                                    <td class="fail-detail" title="${item.payFailReason}">${item.payFailReason!''}</td>
                                    <td>${item.createTime!''}</td>
                                    <td>${item.applyTime!''}</td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </#if>
                <#if page??>
                    <div class="pagination-box clearfix" id="pageClass">
                        <div class="jr-pagination margin">
                            <span class="fl pagination-txt mr7">每页10条</span>
                            <div class="previous <#if page.currentPage==1> previous-disabled </#if> fl"><i></i><span
                                    class="ml20">上一页</span></div>
                            <div class="next <#if page.currentPage==page.pages> next-disabled </#if> fl"><span
                                    class="mr20 fr">下一页</span><i></i></div>
                            <div class="page fl">
                                <span class="fl pagination-txt mr7">第${page.currentPage}页/共${page.pages}页   &nbsp;&nbsp;	向第</span>
                            </div>
                            <input class="jump-to-num fl" value="" id="targetPage">
                            <span class="fl pagination-txt mr7">页</span>
                            <span class="jump fl pagination-txt" onclick="targetQuery();">跳转</span>
                        </div>
                        <input style="display:none" id="currentPage" name="currentPage" value="${page.currentPage}">
                        <input style="display:none" id="pageSize" value="${page.pages}">
                    </div>
                </#if>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    //提交查询
    function submitQuery() {
    	if( $("#error").css("display") == "block"){
			return false;
		}
		if(flag=='none'){
			if($("#pageClass").length>0){
				$("#currentPage").attr("value","1");					
			}				
		}	
        var formObj = $("form[name='f0']");
        formObj.attr("action", "${ctx}" + "/cpBatchPaymentDetail/cpBatchPaymentDetailQuery/query.htm");
        formObj.submit();
    }

    //分页查询,指定页
    function targetQuery() {
        var currentPage = parseInt($('#targetPage').val());
        var pageSize = parseInt($('#pageSize').val());

        if ($("#pageClass").length <= 0) {
            alert("请先查询数据!");
        } else {
            if (currentPage === "" || currentPage == null) {
                alert("请输入页数!");
            }
            else if (currentPage > pageSize) {
                alert("指定页数不能超过最大页数!");
            }
            else if (currentPage <= 0) {
                alert("指定页数不能小于0!");
            } else if (isNaN(currentPage)) {
                alert("请输入数字!");
            }
            else {
                document.getElementById("currentPage").value = currentPage;
                var formObj = $("form[name='f0']");
                formObj.attr("action", "${ctx}" + "/cpBatchPaymentDetail/cpBatchPaymentDetailQuery/query.htm");
                formObj.submit();
            }
        }
    }

    //提交导出
    function submitExport() {
        if ($("#fixed-table-result").length <= 0) {
            alert("请先查询数据!");
        }
        else {
            $.ajax({
                url: "${ctx}" + '/authStatus',
                crossDomain: true,
                cache: false,
                dataType: 'jsonp',
                success: function (data) {
                    if (data.hasLogin) {
                        var formObj = $("form[name='f0']");
                        formObj.attr("action", sysconfig.ctx + "/cpBatchPaymentDetail/cpBatchPaymentDetailQuery/export.htm");
                        formObj.submit();
                    } else {
                        window.location.href = sysconfig.ctx + "/goodsTrade/goodsTradeInit/init.htm";
                    }
                },
                error: function (data) {
                    window.location.href = sysconfig.ctx + "/goodsTrade/goodsTradeInit/init.htm";
                }
            });

        }

    }
</script>

<script type="text/javascript" data-main="${ctx}/js/cpBatchPaymentDetail/main"
        src="${ctx}/scripts/lib/require/require.min.js"></script>

</body>
</html>