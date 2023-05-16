<meta charset="UTF-8">
    <title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="https://www.suning.com/favicon.ico" type="image/x-icon">  
    <link rel="stylesheet" href="${ctx}/style/css/header.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/placeholder.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">
    <link rel="stylesheet" href="${ctx}/style/css/chosen.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/quickQuery-2c.css" />
    <link rel="stylesheet" href="${ctx}/style/css/withdraw-account.css" type="text/css" />

    <#include "/screen/header.ftl">
    <div class="platform-container">
        <div class="platform-panel">      	
            <div class="search-form-box clearfix J_searchForm">
            	<form id="f3" name="f3" method="post">
                <dl class="mb mr">
                    <dt>名称：</dt>
                    <dd><input type="text" class="platform-input" name="payeeMerchantName" value="${(requestDto.payeeMerchantName)!''}"></dd>
                </dl>
                <dl class="mb mr">
                    <dt>银行账号：</dt>
                    <dd><input type="text" class="platform-input" name="bankAccountNo" value="${(requestDto.bankAccountNo)!''}"></dd>
                </dl>
                <dl class="mb">
                    <dt>业务类型：</dt>
                    <dd>
                    <div class="platform-select jr-select">
                        <i class="select-input-arrow-icon arrow-icon-closed"></i>
                        <input type="hidden" name="bizType" value="">
                        <input type="text" readonly class="select-input" key="">
                        <ul class="select-box">
                            <#if bizType??>
                                <#list bizType as unit>
                                    <li class="select-item">
                                        <a class="sel-val" href="javascript:void(0);" key="1" value=${unit.code}>${unit.description}</a>
                                    </li>
                                </#list>
                            </#if>
                        </ul>
                    </div>
                </dl>
                <dl class="mb">
                    <dt>数据类型：</dt>
                    <dd>
                    <div class="platform-select jr-select">
                        <i class="select-input-arrow-icon arrow-icon-closed"></i>
                        <input type="text" readonly class="select-input" name="dataType" value="${(requestDto.dataType)!''}" key="">
                        <ul class="select-box">
                            <#if dataType??>
                                <#list dataType as unit>
                                    <li class="select-item">
                                        <a class="sel-val" href="javascript:void(0);" key="1" value=${unit.code}>${unit.description}</a>
                                    </li>
                                </#list>
                            </#if>
                        </ul>
                    </div>
                </dl>
                </form>
                <dl>
                    <dt></dt>
                    <dd>
                        <a href="javascript:void(0);" class="platform-button active J_searchBtn">查询</a>
                        <a href="javascript:void(0);" class="platform-button J_resetBtn">重置</a>
                    </dd>
                </dl>
            </div>       
            <div class="withdraw-account-table-wrap">
                <div class="top-ctrl clearfix">
                    <div class="top-ctrl-btns fr">
                        <a href="javascript:void(0);" class="platform-button J_add"><i class="btn-icon icon-add"></i>新增</a>
                        <a href="javascript:void(0);" class="platform-button J_import"><i class="btn-icon icon-add"></i>批量新增</a>
                        <a href="javascript:void(0);" class="platform-button J_export"><i class="btn-icon icon-export"></i>导出</a>
                    </div>
                </div>
                <div class="J_listContent">
                </div>           
            </div>
        </div>
    </div>
    <script type="text/html" id="list-tpl">  
        <div class="withdraw-account-table-box clearfix">
            <div class="withdraw-account-table">
                <table class="platform-table" id="fixed-table-result">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>境内收款方编号</th>
                            <th>名称</th>
                            <th>账号性质</th>
                            <th>组织机构代码/身份证号</th>
                            <th>银行开户名</th>
                            <th>银行账号</th>
                            <th>开户行</th>
                            <th>联行号</th>
                            <th>业务类型</th>
                            <th>数据类型</th>
                            <th>资质认证</th>
                        </tr>
                    </thead>
                    <tbody>
                        {{each list as item index}}
                        <tr>
                            <td>{{index+1}}</td>
                            <td>{{item.payeeMerchantCode}}</td>
                            <td title="{{item.payeeMerchantName}}">{{item.payeeMerchantName}}</td>
                            <td title="{{item.accountCharacterStr}}">{{item.accountCharacterStr}}</td>
                            <td title="{{item.companyOrPersonCode}}">{{item.companyOrPersonCode}}</td>
                            <td title="{{item.bankAccountName}}">{{item.bankAccountName}}</td>
                            <td title="{{item.bankAccountNo}}">{{item.bankAccountNo}}</td>
                            <td title="{{item.bank}}">{{item.bank}}</td>
                            <td title="{{item.bankNo}}">{{item.bankNo}}</td>
                            <td>{{item.bizTypeStr}}</td>
                            <td>{{item.dataTypeStr}}</td>
                            <td>{{item.qualificationStatusStr}}</td>
                        </tr>
                        {{/each}}
                    </tbody>
                </table>
            </div>
            <div class="withdraw-account-table-ctrl">
                <table class="platform-table">
                    <thead>
                        <tr>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        {{each list as item}}                           
                            <tr class="edit">
                                <td class="opration">
                                    <a href="javascript:void(0);" class="J_modify">
                                    {{if item.accountCharacter=='01'}}修改{{else}}查看{{/if}}
                                    </a>
                                    <input type="text" class="payeeMerchantCodeUpdate hide" value="{{item.payeeMerchantCode}}"/>
                                </td>
                            </tr>                           
                        {{/each}}
                    </tbody>
                </table>
            </div> 
        </div> 
        <div class="pagination-box clearfix">
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
        </div>
    </script>    
    <script type="text/html" id="wait-tpl">
        <div class="wait-wrap"><i></i>请求中...</div>
    </script>
    <script type="text/html" id="empty-tpl">
        <div class="no-result"><i></i>没有找到相关数据</div>
    </script>

	<!-- 单笔 -->
    <script type="text/html" id="add-tpl">
        <form id="f2" name="f2" method="post">
            <ul class="condition-list pop-add-list w490 clearfix">
                {{if typeof payeeMerchantCode != 'undefined'}}
                <li class="condition-item">
                    <span class="condition-title-add">境内收款方编号：</span>
                    <span class="condition-input">
                    	<p id="payeeMerchantCodeAdd1">{{payeeMerchantCode}}</p>
                        <input type="text" readonly name="payeeMerchantCode" class="hide" value="{{payeeMerchantCode}}">
                    </span>
                </li>
                {{/if}}
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>名称：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeMerchantName" value="{{payeeMerchantName}}">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:6;">
                    <span class="condition-title-add"><i class="red-warn">*</i>账号性质：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" class="select-input-hidden" name="accountCharacter" value="{{accountCharacter}}">
                            <input type="text" readonly class="select-input input_text empty" placeholder="请选择" value="{{accountCharacterStr}}">
                            <ul class="select-box">
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value="01">个人</a>
                                </li>
                            </ul>
                        </div>
                    </span>
                </li>
                {{if accountCharacter=='02'}}
                <li class="condition-item-company" style="position:relative;z-index:5;">
                    <span class="condition-titles"><i class="red-warn">*</i>住所/营业场所名称及代码：</span>
                    <span class="condition-select">
                    <select id="areaCode" name="areaCode" class="chosen-select" disabled="disabled" data-placeholder="请选择..." style="width: 280px;">
                    </select>
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:4;">
                    <span class="condition-titles" style="font-size: 10px;"><i class="red-warn">*</i>常驻国家（地区）名称及代码：</span>
                    <span class="condition-select">
                    <select id="countryCode" name="countryCode" disabled="disabled" class="chosen-select" data-placeholder="请选择..." style="width: 280px;">
                    </select>
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:3;">
                    <span class="condition-titles"><i class="red-warn">*</i>经济类型：</span>
                    <span class="condition-select">
                    <select id="attrCode" name="attrCode" disabled="disabled" class="chosen-select" data-placeholder="请选择..." style="width: 280px;">
                    </select>
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:2;">
                    <span class="condition-titles"><i class="red-warn">*</i>所属行业属性：</span>
                    <span class="condition-select">
                    <select id="industryCode" name="industryCode" disabled="disabled" class="chosen-select" data-placeholder="请选择..." style="width: 280px;">
                    </select>
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:1;">
                    <span class="condition-titles"><i class="red-warn">*</i>特殊经济区企业：</span>
                    <span class="condition-select">
                    <select id="taxFreeCode" name="taxFreeCode" disabled="disabled" class="chosen-select" data-placeholder="请选择..." style="width: 280px;">
                    </select>
                    </span>
                </li>
                {{/if}}
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>身份证号：</span>
                    <span class="condition-input">
                        <input type="text" name="companyOrPersonCode" placeholder="" value="{{companyOrPersonCode}}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>银行开户名：</span>
                    <span class="condition-input">
                        <input type="text" name="bankAccountName" placeholder="" readonly value="{{bankAccountName}}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>银行账号：</span>
                    <span class="condition-input">
                        <input type="text" name="bankAccountNo" placeholder="" value="{{bankAccountNo}}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>开户行：</span>
                    <span class="condition-input">
                        <input type="text" name="bank" placeholder="" value="{{bank}}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>开户行编号：</span>
                    <span class="condition-input">
                        <input class="quickQuery$focus" type="text" name="bankCode" placeholder="请选择开户行编号" value="{{bankCode}}">
                        <div class="quickQuery$focus"></div>
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title-add"><i class="red-warn"></i>开户行省：</span>
                    <span class="condition-input">
                        <input type="text" name="bankProvince" placeholder="" value="{{bankProvince}}">
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title-add"><i class="red-warn"></i>开户行市：</span>
                    <span class="condition-input">
                        <input type="text" name="bankCity" placeholder="" value="{{bankCity}}">
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title-add"><i class="red-warn"></i>联行号：</span>
                    <span class="condition-input">
                        <input type="text" name="bankNo" placeholder="" value="{{bankNo}}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn"></i>手机号码：</span>
                    <span class="condition-input">
                        <input type="text" name="mobilePhone" placeholder="" value="{{mobilePhone}}">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:2;">
                    <span class="condition-title-add"><i class="red-warn">*</i>业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" class="select-input-hidden" name="bizType" value="{{bizType}}">
                            <input type="text" readonly class="select-input input_text empty" placeholder="请选择" value="{{bizTypeStr}}" key="">
                            <ul class="select-box">
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value="001">货物贸易</a>
                                </li>
                            </ul>
                        </div>
                    </span>
                </li>
                <li class="condition-item hide">
                    <span class="condition-title-add">操作类型：</span>
                        <span class="condition-input">
                        {{if typeof payeeMerchantCode == 'undefined'}}           
                        <input type="text"  name="operationType" value="N">
                        {{else}} 
                        <input type="text"  name="operationType" value="U">
                        {{/if}}
                    </span>
                </li>
                <li class="condition-item item-btn">
                    <span class="condition-input">
                        {{if accountCharacter!='02'}}
                        <a href="javascript:;" class="submit-btn J_set" id="addMerchants" >保 存</a>
                        <a href="javascript:;" class="cancel-btn">取 消</a>
                        {{else}}
                        <a href="javascript:;" class="cancel-btn quit-btn">关 闭</a>
                        {{/if}}
                    </span>
                </li>
            </ul>
        </form>
    </script>

	<script type="text/javascript" data-main="${ctx}/js/cpWithdrawAccount/main.js" src="${ctx}/scripts/lib/require/require.min.js"></script>
    <script src="${ctx}/scripts/lib/select/quickquery-twocolumn.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        function loadBankList(){
            var bankArray = JSON.parse('${Session["bankList"]}');
            if(bankArray[0] == ",,"){
                bankArray[0][0] = "0";
                bankArray[0][2] = "无银行信息";
            }
            if(bankArray){
                $quickQuery(bankArray);
            }
        }
    </script>
