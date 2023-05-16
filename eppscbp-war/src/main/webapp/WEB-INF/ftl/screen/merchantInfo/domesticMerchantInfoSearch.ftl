 <meta charset="UTF-8">
    <title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/placeholder.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">
    <link rel="stylesheet" href="${ctx}/style/css/chosen.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/quickQuery-2c.css" />
    <div class="batch-payment">
        <#include "/screen/title.ftl">  
        
        <form method='post' id="f0" name="f0">
        <div class="content">
        
        	<div class="batch-tab-list">
                <ul class="clearfix">
                    <a href="${ctx}/merchantHandle/init.htm"><li class="tab-item">境外商户查询<i class="top-line"></i></li></a>
                    <a href="javascript:void(0);" ><li class="tab-item hover" >境内商户查询<i class="top-line"></i></li></a>
                </ul>
            </div>
        	
        	<ul class="condition-list clearfix">
                <li class="condition-item">
                    <span class="condition-title">名称：</span>
                    <span class="condition-input">
                        <input type="text"  name="payeeMerchantName" value="${(requestDto.payeeMerchantName)!''}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title">银行账号：</span>
                    <span class="condition-input">
                        <input type="text"  name="bankAccountNo" value="${(requestDto.bankAccountNo)!''}">
                    </span>
                </li>              
                <li class="condition-item" style="position:relative;z-index:3;">
                    <span class="condition-title">业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input input_text empty" name="bizType" value="${(requestDto.bizType)!''}" key="">
                            <ul class="select-box">
                            	<#if bizType??>
                            	<#list bizType as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:3;">
                    <span class="condition-title">数据类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input input_text empty" name="dataType" value="${(requestDto.dataType)!''}" key="">
                            <ul class="select-box">
                                <#if dataType??>
                                <#list dataType as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"></span>
                    <span class="condition-input">
                        <a href="javascript:void(0);" class="submit-btn J_search" onclick="submitQuery('none');">查 询</a>
                        <a href="javascript:;" class="cancel-btn reset">重 置</a>
                    </span>
                </li>
            </ul>
            
            <div class="btn-list">
                <a href="javascript:;" class="outport-btn" onclick="submitExport();"><i></i> 导出</a>
                <a href="javascript:;" class="submit-btn add-btn">+ 新增</a>
                <a href="javascript:;" class="cancel-btn batch-add-btn">+ 批量新增</a>
            </div>
            
            
            <div class="table-box" id="table-box-result">
            	<#if errMessage??>
            	<p class="no-result"><i></i>${errMessage}</p>
            	</#if>
            
            	<#if resultList??>
                <table class="fixed-table  edit-table" id="fixed-table-result">
                    <thead>
                        <tr class="edit">
                            <th width="81">操作</th>
                        </tr>
                        
                    </thead>
                    <tbody>
                    	<#list resultList as item>
                    	
                    	<#if item.accountCharacter=='01'>
					    <tr class="edit"><td class="opration" data-id="1234" ><span class="edit-btn">修改</span><input type="text" class="payeeMerchantCodeUpdate hide" value="${(item.payeeMerchantCode)!''}"/></td></tr>
					    <#else>
					    <tr class="view"><td class="opration1" data-id="12345" ><span class="view-btn">查看</span><input type="text" class="payeeMerchantCodeView hide" value="${(item.payeeMerchantCode)!''}"/></td></tr>
					   </#if>
                    		
                        </#list>
                    </tbody>
                </table>		
                <div class="result-table">  
                    <table style="table-layout:  fixed;">
                        <thead>
                            <tr>
                                <th width="40">序号</th>
                                <th width="110">境内收款方编号</th>
                                <th width="150">名称</th>
                                <th width="90">账号性质</th>
                                <th width="150">组织机构代码/身份证号</th>
                                <th width="150">银行开户名</th>
                                <th width="150">银行账号</th>
                                <th width="150">开户行</th>
                                <th width="150">联行号</th>
                                <th width="180">业务类型</th>
                                <th width="90">数据类型</th>
                                <th width="190">资质认证</th>      
                            </tr>
                        </thead>
                        <tbody>
                           <#list resultList as item>
                            <tr>
                                <td>${item_index+1}</td>
                                <td>${item.payeeMerchantCode}</td>
                                <td title="${item.payeeMerchantName}">${item.payeeMerchantName}</td>
                                <td title="${item.accountCharacterStr}">${item.accountCharacterStr}</td>
                                <td title="${item.companyOrPersonCode}">${item.companyOrPersonCode}</td>
                                <td title="${item.bankAccountName}">${item.bankAccountName}</td>
                                <td title="${item.bankAccountNo}">${item.bankAccountNo}</td>
                                <td title="${item.bank}">${item.bank}</td>
                                <td title="${item.bankNo}">${item.bankNo}</td>
                                <td>${item.bizTypeStr}</td>
                                <td>${item.dataTypeStr}</td>
                                <td>${item.qualificationStatusStr}</td>
                            </tr>
                            </#list>                                
                        </tbody>
                    </table>
                </div>  
                </#if>             
            </div>
            
            
            <#if page??>
            <div class="page" id="pageClass">
                    <div class="posi-right" >
                        <span class="fl">每页10条</span>                    
                        <a class="prev-page page-num border <#if page.currentPage==1> disable </#if> fl"  href="javascript:;" ><i class="arrow-left"></i>上一页</a>
                        <a class="next-page page-num border <#if page.currentPage==page.pages> disable </#if> fl" href="javascript:;" >下一页<i class="arrow-right"></i></a>

                        <span class="fl m-left">第${page.currentPage}页/共${page.pages}页   &nbsp;&nbsp;	向第</span><input type="text" name="" class="fl page-num input-num" id="targetPage" ><span class="fl">页</span>
                        <a class="fl goto border" href="javascript:;" onclick="targetQuery();">跳转</a>
                        
                        
                    </div>
                    <input style="display:none" id="currentPage" name="currentPage" value="${page.currentPage}"></input>
                    <input style="display:none" id="pageSize" value="${page.pages}"></input>
             </div>
             </#if>
            
        </div>     
		</form>    
	</div>
	
	<form method='post' id="f1" name="f1">
	</form>
	<!-- 批量新增 -->
    <script type="text/html" id="upload-tpl">
    	<form method='post' id="f4" name="f4">
        <div class="pop-upload upload-files">
            <div>
                <label>文件上传：</label>                
                <input type="text" class="input file-path" readonly="true" name="filePath" value="" id="file"> 
					<input type="text" name="fileAddress" value="" class="input file-path hide"></input>                  
                 	<div class="file-operation">
                        <a class="up" href="javascript:;">选择文件
                        <form id="uploadForm" enctype="multipart/form-data">  
                        	<input type="file" id="file-btn" name="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"></a>
                        </form> 
                        <a class="download" href="${ctx}/template/file/batch_domestic_merchant.xlsx">下载模版</a>
                    </div>
                	<div class="clear"></div>
            </div>
            <!-- 上传文件报错 -->
			<div class="result-text">
				<div class="wait" id="wait">
                    <img src="${ctx}/style/images/empty-tip.jpg">
                    <p class="title">请上传模版文件</p>
                </div>
				<div class="wait_1 hide" id="wait_1">
                    <img src="${ctx}/style/images/empty-tip.jpg">
                    <p class="title">文件提交成功。</p>
					<span class="err-text"></span>
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
    
	<!-- 单笔新增 -->
    <script type="text/html" id="add-tpl">
        <form id="f2" name="f2" method="post"> 
        <ul class="condition-list pop-add-list w490 clearfix">
                <#--<li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>名称：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeMerchantName" id="payeeMerchantNameNew">
                    </span>
                </li>-->
                <li class="condition-item" style="position:relative;z-index:6;">
                    <span class="condition-title-add"><i class="red-warn">*</i>账号性质：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="accountCharacter" placeholder="请选择">
                            <ul class="select-box">
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value="个人">个人</a>
                                </li>

                            </ul>
                        </div>
                    </span>
                </li>
                
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>身份证号：</span>
                    <span class="condition-input">
                        <input type="text" name="companyOrPersonCode" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>银行开户名：</span>
                    <span class="condition-input">
                        <#--<input type="text" name="bankAccountName" id="bankAccountNameNew" placeholder="" readonly>-->
                        <input type="text" name="bankAccountName" id="bankAccountNameNew">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>银行账号：</span>
                    <span class="condition-input">
                        <input type="text" name="bankAccountNo" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>开户行：</span>
                    <span class="condition-input">
                        <input type="text" name="bank" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>开户行编号：</span>
                    <span class="condition-input">
                        <input class="quickQuery$focus" type="text" name="bankCode" placeholder="请选择开户行编号">
                        <div class="quickQuery$focus"></div>
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add">开户行省：</span>
                    <span class="condition-input">
                        <input type="text" name="bankProvince" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title-add">开户行市：</span>
                    <span class="condition-input">
                        <input type="text" name="bankCity" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title-add">联行号：</span>
                    <span class="condition-input">
                        <input type="text" name="bankNo" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add">手机号码：</span>
                    <span class="condition-input">
                        <input type="text" name="mobilePhone" placeholder="">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:2;">
                    <span class="condition-title-add"><i class="red-warn">*</i>业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="bizType" id="bizType" placeholder="请选择" value="" key="">
                            <ul class="select-box">
                                <#if bizTypeAdd??>
                            	<#list bizTypeAdd as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                    </span>
                </li>
                <li class="condition-item hide">
                    <span class="condition-title-add">操作类型：</span>
                    <span class="condition-input">
                        <input type="text"  name="operationType" value="N">
                    </span>
                </li>
                               
                <li class="condition-item item-btn">
                    <span class="condition-input">
                        <a href="javascript:;" class="submit-btn J_set" id="addMerchant" >保 存</a>
                        <a href="javascript:;" class="cancel-btn">取 消</a>
                    </span>
                </li>                
            </ul>
            </form>
    </script>
    
    <!-- 编辑 -->
    <script type="text/html" id="edit-tpl">
        <form id="f3" name="f3" method="post">
        <ul class="condition-list pop-add-list w490 clearfix">
                <li class="condition-item">
                    <span class="condition-title-add">境内收款方编号：</span>
                    <span class="condition-input">
                    	<a id="payeeMerchantCodeAdd1"></a>
                        <input type="text" readonly name="payeeMerchantCode" class="hide" id="payeeMerchantCodeAdd"  value="">
                    </span>
                </li>
                <#--<li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>名称：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeMerchantName" id="payeeMerchantNameAdd">
                    </span>
                </li>-->
                
                <li class="condition-item" style="position:relative;z-index:6;">
                    <span class="condition-title-add"><i class="red-warn">*</i>账号性质：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="accountCharacter" id="accountCharacterAdd" placeholder="请选择">
                        </div>
                    </span>
                </li>
                
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>身份证号：</span>
                    <span class="condition-input">
                        <input type="text" id="companyOrPersonCodeAdd" name="companyOrPersonCode" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>银行开户名：</span>
                    <span class="condition-input">
                        <input type="text" id="bankAccountNameAdd" name="bankAccountName" placeholder="" >
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>银行账号：</span>
                    <span class="condition-input">
                        <input type="text" id="bankAccountNoAdd" name="bankAccountNo" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>开户行：</span>
                    <span class="condition-input">
                        <input type="text" id="bankAdd" name="bank" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title-add"><i class="red-warn">*</i>开户行编号：</span>
                    <span class="condition-input">
                        <input class="quickQuery$focus" type="text" id="bankCodeAdd" name="bankCode" placeholder="请选择开户行编号">
                        <div class="quickQuery$focus"></div>
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title-add">开户行省：</span>
                    <span class="condition-input">
                        <input type="text" id="bankProvinceAdd" name="bankProvince" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title-add">开户行市：</span>
                    <span class="condition-input">
                        <input type="text" id="bankCityAdd" name="bankCity" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title-add">联行号：</span>
                    <span class="condition-input">
                        <input type="text" id="bankNoAdd" name="bankNo" placeholder="">
                    </span>
                </li>
                <li class="condition-item" id="mobilePhoneItem">
                    <span class="condition-title-add">手机号码：</span>
                    <span class="condition-input">
                        <input type="text" id="mobilePhone" name="mobilePhone" placeholder="">
                    </span>
                </li>
                 <li class="condition-item" style="position:relative;z-index:2;">
                    <span class="condition-title-add"><i class="red-warn">*</i>业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="bizType" id="bizTypeAdd" placeholder="请选择" value="" key="">
                            <ul class="select-box">
                                <#if bizTypeAdd??>
                            	<#list bizTypeAdd as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                    </span>
                </li>
                
                <li class="condition-item hide">
                    <span class="condition-title-add">操作类型：</span>
                    <span class="condition-input">
                        <input type="text"  name="operationType" value="U">
                    </span>
                </li>
                
                <li class="condition-item item-btn">
                    <span class="condition-input">
                        <a href="javascript:;" class="submit-btn J_set"  id="updateMerchant">保 存</a>
                        <a href="javascript:;" class="cancel-btn">取 消</a>
                    </span>
                </li>               
            </ul>
            </form>

    </script>
    
    <!-- 查看 -->
    <script type="text/html" id="view-tpl">
        <form id="f4" name="f4" method="post">
        <ul class="condition-list pop-add-list w490 clearfix">
                <li class="condition-item">
                    <span class="condition-title">境内收款方编号：</span>
                    <span class="condition-input">
                    	<a id="payeeMerchantCodeView1"></a>
                        <input type="text" readonly name="payeeMerchantCode" class="hide" id="payeeMerchantCodeView"  value="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>名称：</span>
                    <span class="condition-input">
                        <input type="text" readonly placeholder="" name="payeeMerchantName" id="payeeMerchantNameView">
                    </span>
                </li>
                
                <li class="condition-item" style="position:relative;z-index:6;">
                    <span class="condition-title"><i class="red-warn">*</i>账号性质：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="accountCharacter" id="accountCharacterView" placeholder="请选择">
                        </div>
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:5;">
                    <span class="condition-title"><i class="red-warn">*</i>住所/营业场所名称及代码：</span>
                    <span class="condition-select">
                    <select id="areaCode" name="areaCode" class="chosen-select" disabled="disabled" data-placeholder="请选择..." style="width: 280px;"> 
                    </select>
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:4;">
                    <span class="condition-title" ><i class="red-warn">*</i>常驻国家（地区）名称及代码：</span>
                    <span class="condition-select">
                    <select id="countryCode" name="countryCode" disabled="disabled" class="chosen-select" data-placeholder="请选择..." style="width: 280px;"> 
                    </select>
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:3;">
                    <span class="condition-title"><i class="red-warn">*</i>经济类型：</span>
                    <span class="condition-select">
                    <select id="attrCode" name="attrCode" disabled="disabled" class="chosen-select" data-placeholder="请选择..." style="width: 280px;"> 
                    </select>
                    </span>
                </li>
 				<li class="condition-item">
                    <span class="condition-title">投资国别/地区：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="investmentCountryView" name="investmentCountry" placeholder="">
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:2;">
                    <span class="condition-title"><i class="red-warn">*</i>所属行业属性：</span>
                    <span class="condition-select">
                    <select id="industryCode" name="industryCode" disabled="disabled" class="chosen-select" data-placeholder="请选择..." style="width: 280px;"> 
                    </select>
                    </span>
                </li>
                <li class="condition-item-company" style="position:relative;z-index:1;">
                    <span class="condition-title"><i class="red-warn">*</i>特殊经济区企业：</span>
                    <span class="condition-select">
                    <select id="taxFreeCode" name="taxFreeCode" disabled="disabled" class="chosen-select" data-placeholder="请选择..." style="width: 280px;"> 
                    </select>
                    </span>
                </li> 
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>组织机构代码/身份证号：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="companyOrPersonCodeView" name="companyOrPersonCode" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>银行开户名：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="bankAccountNameView" name="bankAccountName" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>银行账号：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="bankAccountNoView" name="bankAccountNo" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>开户行：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="bankView" name="bank" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>开户行编号：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="bankCodeView" name="bankCode" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                        <span class="condition-title">开户行省：</span>
                    <span class="condition-input">
                        <input type="text"  readonly id="bankProvinceView" name="bankProvince" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title">开户行市：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="bankCityView" name="bankCity" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title">联行号：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="bankNoView" name="bankNo" placeholder="">
                    </span>
                </li>
                <li class="condition-item" id="mobilePhoneItem">
                    <span class="condition-title">手机号码：</span>
                    <span class="condition-input">
                        <input type="text" readonly id="mobilePhoneView" name="mobilePhone" placeholder="">
                    </span>
                </li>

 				<li class="condition-item" style="position:relative;z-index:6;">
                    <span class="condition-title"><i class="red-warn">*</i>业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="bizType" id="bizTypeView" placeholder="请选择">
                        </div>
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:6;">
                    <span class="condition-title"><i class="red-warn">*</i>数据类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="dataType" id="dataTypeView" placeholder="请选择">
                        </div>
                    </span>
                </li>
                <li class="condition-item item-btn">
                    <span class="condition-input">
                        <a href="javascript:;" class="cancel-btn">取 消</a>
                    </span>
                </li>               
            </ul>
            </form>

    </script>

	<script type="text/javascript">
		//提交查询
		function submitQuery(flag){
			if(flag=='none'){
				if($("#pageClass").length>0){
					$("#currentPage").attr("value","1");					
				}				
			}
			
			var formObj = $("form[name='f0']");
    	    formObj.attr("action", sysconfig.ctx+"/merchantHandle/domestic/query.htm");
    	    formObj.submit(); 
			
    	}
    	    	
    	//提交导出
		function submitExport(){
			if($("#fixed-table-result").length <=0){
				alert("请先查询数据!");
			}else{
    	    	
    	    	$.ajax({
            			url: sysconfig.ctx + '/authStatus',
            			crossDomain: true,
            			cache: false,
            			dataType: 'jsonp',
            			success: function (data) {
                			if (data.hasLogin) {
                    			var formObj = $("form[name='f0']");
    	    					formObj.attr("action", sysconfig.ctx+"/merchantHandle/domestic/export.htm");
    	    					formObj.submit(); 
                			} else {
                    			window.location.href=sysconfig.ctx+"/goodsTrade/goodsTradeInit/init.htm";
                			}
            			},
            			error : function(data) {
            				window.location.href=sysconfig.ctx+"/goodsTrade/goodsTradeInit/init.htm";
            			}
        			});	
    	    	
    	    	
			}    		
    	}
    	
	
		//分页查询,上一页
    	$(".prev-page").on("click",function(event){  
            event.preventDefault();  
            
            var currentPage=$('#currentPage').val(); 
            var nextPage=parseInt(currentPage)-1;
            
            //首页无法点击上一页
            if(currentPage==1){
            	return;
            }
            
            if($("#pageClass").length <=0){
            	alert("请先查询数据!");
            }else{ 
            	document.getElementById("currentPage").value=nextPage;          	
            	var formObj = $("form[name='f0']");
    	    	formObj.attr("action", sysconfig.ctx+"/merchantHandle/domestic/query.htm");
    	    	formObj.submit();            
            }            
                }); 	
    	
    	
    	
    	//分页查询,下一页
    	$(".next-page").on("click",function(event){  
            event.preventDefault();  
                        
            
            var currentPage=$('#currentPage').val(); 
            var nextPage=parseInt(currentPage)+1;           
            var pages=parseInt($('#pageSize').val())
            
            //末页无法点击下一页
            if(currentPage==pages){
            	return;
            }
            
            if($("#pageClass").length <=0){
            	alert("请先查询数据!");
            }else{ 
            	document.getElementById("currentPage").value=nextPage;          	
            	var formObj = $("form[name='f0']");
    	    	formObj.attr("action", sysconfig.ctx+"/merchantHandle/domestic/query.htm");
    	    	formObj.submit();            
            }            
                }); 
                
                
        //分页查询,指定页
         function targetQuery(){
    		var currentPage=parseInt($('#targetPage').val()); 
            var pageSize=parseInt($('#pageSize').val());
            
            if($("#pageClass").length <=0){
            	alert("请先查询数据!");
            }else{ 
            	if(currentPage === "" || currentPage == null){
            		alert("请输入页数!");
            	}
            	else if(currentPage>pageSize){
            		alert("指定页数不能超过最大页数!");
            	}
            	else if(currentPage<=0){
            		alert("指定页数不能小于0!");
            	}else if(isNaN(currentPage)){
            		alert("请输入数字!");
            	}           	
            	else{
            		document.getElementById("currentPage").value=currentPage;          	
            		var formObj = $("form[name='f0']");
    	    		formObj.attr("action", sysconfig.ctx+"/merchantHandle/domestic/query.htm");
    	    		formObj.submit(); 
            	}    	           
            }
                        
    	} 
          
        
	</script>
	<script type="text/javascript" data-main="${ctx}/scripts/eppscbp/sjh-receive-manager-search" src="${ctx}/scripts/lib/require/require.min.js"></script>
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
