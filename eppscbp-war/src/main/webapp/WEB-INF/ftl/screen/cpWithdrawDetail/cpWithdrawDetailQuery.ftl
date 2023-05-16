<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>提现明细</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/withdraw-detail.css" type="text/css"/>
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
                    <dt>店铺名称：</dt>
                    <dd><input type="text" class="platform-input" name="storeName" value="${(requestDto.storeName)!''}">
                    </dd>
                </dl>
                <dl class="mb">
                    <dt>店铺平台：</dt>
                    <dd>
                        <div class="platform-select jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="storePlatform" class="select-input input_text empty"
                                   value="${(requestDto.storePlatform)!''}" key="">
                            <ul class="select-box">
                            <#if storePlatform??>
                                <#list storePlatform as unit>
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
                    <dt>币种：</dt>
                    <dd>
                        <div class="platform-select jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="currency" class="select-input input_text empty"
                                   value="${(requestDto.currency)!''}" key="">
                            <ul class="select-box">
                            <#if currency??>
                                <#list currency as unit>
                                    <li class="select-item">
                                        <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                    </li>
                                </#list>
                            </#if>
                            </ul>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>订单状态：</dt>
                    <dd>
                        <div class="platform-select jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="orderStatus" class="select-input input_text empty"
                                   value="${(requestDto.orderStatus)!''}" key="">
                            <ul class="select-box">
                            <#if orderStatus??>
                                <#list orderStatus as unit>
                                    <li class="select-item">
                                        <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                    </li>
                                </#list>
                            </#if>
                            </ul>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>提现时间：</dt>
                    <dd>
                        <input type="text" class="platform-input date" id="startDateInput" name="withdrawStartTime"
                               readonly="true"
                               value="${(requestDto.withdrawStartTime)!''}">
                        <span class="date-txt">至</span>
                        <input type="text" class="platform-input date" id="endDateInput" name="withdrawEndTime"
                               readonly="true"
                               value="${(requestDto.withdrawEndTime)!''}">
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
            <div class="withdraw-detail-table-wrap">
                <div class="top-ctrl clearfix">
                    <a href="" class="platform-button fr" onclick="submitExport();"><i
                            class="btn-icon icon-export"></i>导出</a>
                </div>
                <div class="J_listContent">
                <#if errMessage??>
                    <div class="no-result"><i></i>${errMessage}</div>
                </#if>
                <#if resultList??>
                    <div class="withdraw-detail-table" id="fixed-table-result">
                        <table class="platform-table">
                            <thead>
                            <tr>
                                <th width="70">序号</th>
                                <th>店铺名称</th>
                                <th>店铺平台</th>
                                <th>店铺站点</th>
                                <th>收款方银行账号</th>
                                <th>提现金额</th>
                                <th>币种</th>
                                <th width="160">提现时间</th>
                                <th>订单状态</th>
                                <th>结汇收入(人民币)</th>
                                <th>真实性材料上传状态</th>
                                <th width="160">上传失败原因</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list resultList as item>
                                <tr>
                                    <td>${item_index+1}</td>
                                    <td>${item.storeName!''}</td>
                                    <td>${item.storePlatformName!''}</td>
                                    <td>${item.storeSite!''}</td>
                                    <td>${item.payeeAccount!''}</td>
                                    <td>${item.withdrawAmtStr!''}</td>
                                    <td>${item.currencyName!''}</td>
                                    <td>${item.withdrawTime!''}</td>
                                    <td>${item.statusName!''}</td>
                                    <td>
                                        <a href="javascript:void(0);" class="J_amount" id="settleAmt${item_index+1}"
                                           name="settleAmt${item_index+1}">${item.settleAmtStr!''}</a></td>
                                    <td>${item.realInfoStatusStr!''}
                                        <#if (item.superviseCombineTypeAuth =='N') && (item.realInfoStatus=='P' || item.realInfoStatus=='F')> 
                                        	<a href="javascript:void(0);" class="J_upload">点击上传</a>
                                        </#if>
                                    </td>
                                    <td class="fail-detail" title="${item.realInfoReason}">${item.realInfoReason!''}</td>
                                    <input style="display:none" id="orderID${item_index+1}"
                                           name="orderID${item_index+1}" value="${item.withdrawOrderNo}">
                                    <input style="display:none" id="settleRate${item_index+1}"
                                           name="settleRate${item_index+1}"
                                           value="${item.settleRate}">
                                    <input style="display:none" id="feeAmt${item_index+1}" name="feeAmt${item_index+1}"
                                           value="${item.feeAmtStr}">
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
                        <input type="text" name="uploadUrl"
                               value="/cpWithdrawDetail/cpWithdrawDetailQuery/tradeSubmit.htm" id="uploadUrlHid"
                               class="hide">
                    </div>
                </#if>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- 结汇收入详情弹窗模板 -->
<script type="text/html" id="settle-detail-dialog-tpl">
    <div class="detail-dialog-body">
        <div class="detail-list-wrap">
            <dl class="clearfix">
                <dt>结汇收入：</dt>
                <dd id="settleAmt"></dd>
            </dl>
            <dl class="clearfix">
                <dt>结汇汇率：</dt>
                <dd id="settleRate"></dd>
            </dl>
            <dl class="clearfix">
                <dt>手续费：</dt>
                <dd id="feeAmt"></dd>
            </dl>
        </div>
        <div class="dialog-ctrl">
            <a href="javascript:void(0);" class="platform-button active J_close">关闭</a>
        </div>
    </div>
</script>

<!-- 提现明细真实性材料文件上传 -->
<script type="text/html" id="upload-tpl">
    <form method='post' id="f4" name="f4">
        <input type="text" name="businessNo" value="" id="businessNoHid" class="hide">
        <div class="pop-upload upload-files">
            <div>
                <label>文件上传：</label>
                <input type="text" class="input file-path" readonly="true" name="filePath" value="" id="file">
                <input type="text" name="fileAddress" value="" class="input file-path hide">
                <div class="file-operation">
                    <a class="up" href="javascript:;">选择文件
                        <form id="uploadForm" enctype="multipart/form-data">
                            <input type="file" id="file-btn" name="file"
                                   accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                    </a>
    </form>
    <a class="download" href="${ctx}/template/file/trade_supervise.xlsx">下载模版</a>
    </div>
    <div class="clear"></div>
    </div>
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
    <a href="javascript:;" class="submit-btn J_setfile disabled" id="J_placeOrder" style="margin-left:144px;">保 存</a>
    <a href="javascript:;" class="cancel-btn">取 消</a>
    </div>
    </form>
</script>

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
        formObj.attr("action", "${ctx}" + "/cpWithdrawDetail/cpWithdrawDetailQuery/query.htm");
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
                formObj.attr("action", "${ctx}" + "/cpWithdrawDetail/cpWithdrawDetailQuery/query.htm");
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
                        formObj.attr("action", sysconfig.ctx + "/cpWithdrawDetail/cpWithdrawDetailQuery/export.htm");
                        formObj.submit();
                    } else {
                        window.location.href = sysconfig.ctx + "/cpWithdrawDetail/cpWithdrawDetailQuery/init.htm";
                    }
                },
                error: function (data) {
                    window.location.href = sysconfig.ctx + "/cpWithdrawDetail/cpWithdrawDetailQuery/init.htm";
                }
            });

        }

    }

</script>

<script type="text/javascript" data-main="${ctx}/js/cpWithdrawDetail/main"
        src="${ctx}/scripts/lib/require/require.min.js"></script>
</body>
</html>