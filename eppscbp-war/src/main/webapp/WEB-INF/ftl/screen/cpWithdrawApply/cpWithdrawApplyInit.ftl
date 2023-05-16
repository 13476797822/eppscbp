<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>提现申请</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <#include "/commons/fd.ftl">
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/withdraw-apply.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/reset.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/header.css" type="text/css"/>
</head>
<body>
<#include "/screen/header.ftl" />
<div class="platform-container">
    <div class="platform-panel">
        <form id="applyForm" method="post" name="applyForm">
            <div class="withdraw-apply-form">
                <dl class="clearfix">
                    <dt>提现币种：</dt>
                    <dd>
                        <div class="platform-select jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input" key="" id="currency" key="">
                            <ul class="select-box">
                            <#if curType??>
                                <#list curType as unit>
                                    <li class="select-item">
                                        <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                    </li>
                                </#list>
                            </#if>
                            </ul>
                        </div>
                        <div class="tip-txt"></div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt>付款账号：</dt>
                    <dd>
                        <div class="platform-select jr-select disabled" id="select-merchant">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input" key="" id="paymentAccountSel">
                            <ul class="select-box" id="select-box-merchant">
                                <li class="select-item">
                                </li>
                            </ul>
                        </div>
                        <div class="tip-txt">请先选择提现币种</div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt>付款金额：</dt>
                    <dd>
                        <div class="platform-select jr-select disabled" id="select-Amount">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input" key="" id="paymentAmountSel">
                            <input type="text" name="payNo" id="payNo" class="hide">
                            <input type="text" name="arrivalNoticeId" id="arrivalNoticeId" class="hide">
                            <ul class="select-box" id="select-box-Amount">
                                <li class="select-item">
                                </li>
                            </ul>
                        </div>
                        <div class="tip-txt">请先选择付款账号</div>
                    </dd>
                </dl>              
                <dl class="clearfix">
                    <dt>是否包含易付宝商户资金：</dt>
                    <dd>
                        <div class="platform-select jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input" key="" id="isIncludeSel">
                            <ul class="select-box">
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value="是">是</a>
                                </li>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="0" value="否">否</a>
                                </li>
                            </ul>
                        </div>
                        <div class="tip-txt"></div>
                    </dd>
                </dl>
                <dl class="clearfix hide J_yfbInfoLine">
                    <dt>出账批次：</dt>
                    <dd>
                        <div class="platform-select jr-select" id="select-batch">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input" key="" id="accountBatchSel">
                            <ul class="select-box" id="select-box-batch">
                                <li class="select-item">
                                </li>
                            </ul>
                        </div>
                        <div class="tip-txt"></div>
                    </dd>
                </dl>
                <dl class="clearfix hide J_yfbInfoLine">
                    <dt>易付宝商户提现笔数：</dt>
                    <dd class="J_yfbWithdrawNum"></dd>
                </dl>
                <dl class="clearfix hide J_yfbInfoLine">
                    <dt>易付宝商户提现金额：</dt>
                    <dd class="J_yfbWithdrawAmount"></dd>
                </dl>
                <dl class="clearfix">
                    <dt>申请提现金额：</dt>
                    <dd class="J_withdrawAmount"></dd>
                </dl>
                <dl class="clearfix" hidden="hidden" id="platformNameDiv">
                    <dt>平台名称：</dt>
                    <dd>
                        <div class="platform-select jr-select">
                            <input type="text" class="input" id="platformName" value="${criteria.platformName}" placeholder="4~8个字符,只支持汉字/swift字符集" autocomplete="off">              
                        </div>
                        <div class="tip-txt"></div>
                    </dd>
                </dl>
                <dl class="clearfix hide J_detailUploadLine">
                    <dt>申请明细文件上传：</dt>
                    <dd>
                        <input type="text" class="platform-input J_fileNameInput" readonly>
                        <input type="text" name="detailAmount" id="detailAmount"
                               style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;" value = "0">
                        <input type="text" name="fileAddress" id="fileAddress"
                               style="position:absolute;z-index:-1;font-size:0;border:0;left:-1000%;">
                        <input type="text" name="applyAmtHD" id="applyAmtHD" class="hide">
                        <a href="javascript:void(0);" class="platform-button file-btn J_fileBtn">选择文件</a>
                        <input type="file" name="file" class="hide J_fileInput" id="fileUpload"
                               accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                        <a href="${ctx}/template/file/coll_sett_apply_template.xlsx" class="tmpl-link">下载模板</a>
                        <div class="result-text">
                            <div class="success hide">
                                <i class="icon"></i>
                                <p class="title">上传成功</p>
                            </div>
                            <div class="fail hide">
                                <i class="icon"></i>
                                <p class="title">上传失败，请修改后重新上传</p>
                                <p class="error-txt">
                                </p>
                            </div>
                        </div>
                    </dd>
                </dl>
                <dl class="clearfix">
                    <dt></dt>
                    <dd>
                        <a href="javascript:void(0);"
                           class="platform-button active disabled submit-button J_submitBtn">提交</a>
                    </dd>
                </dl>
            </div>
        </form>
    </div>
</div>

<!-- 信息确认弹窗 -->
<script type="text/html" id="inf-confirm-dialog-tpl">
    <div class="inf-confirm-dialog-body">
        <div class="inf-display-box">
            <dl>
                <dt>提现币种：</dt>
                <dd id="currencyDialog">{{currency}}</dd>
            </dl>
            <dl>
                <dt>付款账号：</dt>
                <dd id="payAccountDialog">{{payAccount}}</dd>
            </dl>
            <dl>
                <dt>付款金额：</dt>
                <dd id="payAmtDialog">{{payAmt}}</dd>
            </dl>
            {{if platformName != ''}}
            <dl>
                <dt>平台名称：</dt>
                <dd id="platformName">{{platformName}}</dd>
            </dl>
            {{/if}}
            {{if isIncludeSel == '是'}}
            <dl>
                <dt>出账批次：</dt>
                <dd id="batchDialog">{{batch}}</dd>
            </dl>
            <dl>
                <dt>易付宝商户提现笔数：</dt>
                <dd id="withdrawNumDialog">{{withdrawNum}}</dd>
            </dl>
            <dl>
                <dt>易付宝商户提现金额：</dt>
                <dd id="withdrawAmtDialog">{{withdrawAmt}}</dd>
            </dl>
            {{/if}}
            <dl>
                <dt>申请提现金额：</dt>
                <dd id="applyAmtDialog">{{applyAmt}}</dd>
            </dl>
            <dl>
                <dt>申请明细笔数：</dt>
                <dd id="applyNumDialog">{{applyNum}}</dd>
            </dl>
        </div>
        <div class="dialog-ctrl">
            <a href="javascript:void(0);" class="platform-button active mr J_submit">确定</a>
            <a href="javascript:void(0);" class="platform-button J_cancel">取消</a>
        </div>
    </div>
</script>

<script type="text/javascript" data-main="${ctx}/js/cpWithdrawApply/main"
        src="${ctx}/scripts/lib/require/require.min.js"></script>
</body>
</html>