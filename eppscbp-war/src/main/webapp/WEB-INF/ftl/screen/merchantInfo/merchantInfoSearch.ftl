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
    <div class="batch-payment">
        <#include "/screen/title.ftl">
        
        <form method='post' id="f0" name="f0">
        <div class="content">
        	<div class="batch-tab-list">
                <ul class="clearfix">
                	<a href="javascript:void(0);" ><li class="tab-item hover" >境外商户查询<i class="top-line"></i></li></a>
                    <a href="${ctx}/merchantHandle/domestic/init.htm"><li class="tab-item">境内商户查询<i class="top-line"></i></li></a>                    
                </ul>
            </div>
        	
        	<ul class="condition-list clearfix">
                <li class="condition-item">
                    <span class="condition-title">境外商户编号：</span>
                    <span class="condition-input">
                        <input type="text"  name="payeeMerchantCode" value="${(requestDto.payeeMerchantCode)!''}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title">银行帐号：</span>
                    <span class="condition-input">
                        <input type="text"  name="payeeAccount" value="${(requestDto.payeeAccount)!''}">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:4;">
                    <span class="condition-title">结算币种：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly class="select-input input_text empty" name="currency" value="${(requestDto.currency)!''}" key="">
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
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title">境外商户名称：</span>
                    <span class="condition-input">
                        <input type="text"  name="payeeMerchantName" value="${(requestDto.payeeMerchantName)!''}">
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
                <li class="condition-item">
               		<span class="condition-title"></span>
                    <span class="condition-input">
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
                    	
                    	 <tr class="edit"><td class="opration" data-id="1234" ><span class="edit-btn">修改</span><input type="text" class="payeeMerchantCodeUpdate hide" value="${(item.payeeMerchantCode)!''}"/></td></tr>
                        </#list>
                    </tbody>
                </table>		
                <div class="result-table">  
                    <table style="table-layout:  fixed;">
                        <thead>
                            <tr>
                                <th width="40">序号</th>
                                <th width="110">境外商户编号</th>
                                <th width="150">境外商户名称</th>
                                <th width="150">账号性质</th>
                                <th width="120">对方常驻国/地区</th>
                                <th width="90">结算币种</th>
                                <th width="150">银行账号</th>
                                <th width="350">开户户名</th>
                                <th width="350">开户行</th>
                                <th width="350">开户行地址</th>
                                <th width="200">开户行swift code</th>
                                <th width="200">对方地址</th>
                                <th width="180">业务类型</th>
                            </tr>
                        </thead>
                        <tbody>
                           <#list resultList as item>
                            <tr>
                                <td>${item_index+1}</td>
                                <td>${item.payeeMerchantCode}</td>
                                <td title="${item.payeeMerchantName}">${item.payeeMerchantName}</td>
                                <td title="${item.accountCharacterName}">${item.accountCharacterName}</td>
                                <td>${item.payeeCountry}</td>
                                <td>${item.currency}</td>
                                <td title="${item.payeeAccount}">${item.payeeAccount}</td>
                                <td title="${item.payeeName}">${item.payeeName}</td>
                                <td title="${item.payeeBank}">${item.payeeBank}</td>
                                <td title="${item.payeeBankAdd}">${item.payeeBankAdd}</td>
                                <td>${item.payeeBankSwiftCode}</td>
                                <td title="${item.payeeAddress}">${item.payeeAddress}</td>
                                <td>${item.bizType}</td>
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
                        <a class="download" href="${ctx}/template/file/batch_merchant.xlsx">下载模版</a>
                    </div>
                	<div class="clear"></div>
            </div>
            <!-- 上传文件报错 -->
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
    
	<!-- 单笔新增 -->
    <script type="text/html" id="add-tpl">
        <form id="f2" name="f2" method="post"> 
        <ul class="condition-list pop-add-list w500 clearfix">
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>境外商户名称：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeMerchantName">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:2;">
                    <span class="condition-title"><i class="red-warn">*</i>结算币种：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="currency" placeholder="请选择"  id="currency">
                            <ul class="select-box">
                               <#if curType??>
                            	<#list curTypeAdd as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:1;">
                    <span class="condition-title"><i class="red-warn">*</i>账号性质：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="accountCharacter" placeholder="请选择"  id="accountCharacter">
                            <ul class="select-box">
                               <#if merchantInOut??>
                            	<#list merchantInOut as unit>
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
                    <span class="condition-title"><i class="red-warn">*</i>银行账号：</span>
                    <span class="condition-input">
                        <input type="text" name="payeeAccount" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>账户户名：</span>
                    <span class="condition-input">
                        <input type="text" name="payeeName" placeholder="请勿填写银行名称">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>开户行：</span>
                    <span class="condition-input">
                        <input type="text" name="payeeBank" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>开户行地址：</span>
                    <span class="condition-input">
                        <input type="text" name="payeeBankAdd" placeholder="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>手机号：</span>
                    <span class="condition-input">
                        <input type="text" name="mobilePhone" placeholder="">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:3;">
                    <span class="condition-title"><i class="red-warn">*</i>对方常驻国/地区：</span>
                    <span class="condition-select">
                    <select id="country" name="payeeCountry" class="chosen-select" data-placeholder="选择一个国家..." style="width: 280px;"> 
                    </select>
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>开户行swift code：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="如有英文字符,请大写" name="payeeBankSwiftCode">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>境外商户地址：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeAddress">
                    </span>
                </li>
                <li class="condition-item hide">
                    <span class="condition-title">操作类型：</span>
                    <span class="condition-input">
                        <input type="text"  name="operationType" value="N">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:2;" id="bizTypeDiv">
                    <span class="condition-title"><i class="red-warn">*</i>业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="bizType" id="bizType" placeholder="请选择" value="" key="">
                            <ul class="select-box">
                                <#if bizType??>
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
        <ul class="condition-list pop-add-list w500 clearfix">
                <li class="condition-item">
                    <span class="condition-title">境外商户编号：</span>
                    <span class="condition-input">
                        <a id="payeeMerchantCodeAdd1"></a><input type="text" class="hide" name="payeeMerchantCode" id="payeeMerchantCodeAdd2"  value="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>境外商户名称：</span>
                    <span class="condition-input">
                        <input type="text" name="payeeMerchantName" id="payeeMerchantNameAdd" placeholder="" value="">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:2;">
                    <span class="condition-title"><i class="red-warn">*</i>结算币种：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="currency" id="currencyAdd" placeholder="请选择" value="" key="">
                            <ul class="select-box">
                                <#if curType??>
                            	<#list curTypeAdd as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:1;">
                    <span class="condition-title"><i class="red-warn">*</i>账号性质：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="accountCharacter" placeholder=""  id="accountCharacterAdd">
                            <ul class="select-box">
                               <#if merchantInOut??>
                                <#list merchantInOut as unit>
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
                    <span class="condition-title"><i class="red-warn">*</i>银行账号：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeAccount" id="payeeAccountAdd" value="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>账户户名：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeName" id="payeeNameAdd" value="请勿填写银行名称">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>开户行：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeBank" id="payeeBankAdd" value="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>开户行地址：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeBankAdd" id="payeeBankAddAdd" value="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>手机号：</span>
                    <span class="condition-input">
                        <input type="text" name="mobilePhone" id="mobilePhoneAdd" placeholder="">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:3;">
                    <span class="condition-title"><i class="red-warn">*</i>对方常驻国/地区：</span>
                    <select id="country" name="payeeCountry" class="chosen-select" data-placeholder="选择一个国家..." style="width: 280px;"> 
                    </select>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>开户行swift code：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="如有英文字符,请大写" name="payeeBankSwiftCode" id="payeeBankSwiftCodeAdd"  value="">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title"><i class="red-warn">*</i>境外商户地址：</span>
                    <span class="condition-input">
                        <input type="text" placeholder="" name="payeeAddress" id="payeeAddressAdd"  value="">
                    </span>
                </li>
                <li class="condition-item hide">
                    <span class="condition-title">操作类型：</span>
                    <span class="condition-input">
                        <input type="text"  name="operationType" value="U">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:2;" id="bizTypeEditDiv">
                    <span class="condition-title"><i class="red-warn">*</i>业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty" value="">
                            <input type="text" readonly class="select-input input_text empty" name="bizType" id="bizTypeAdd" placeholder="请选择" value="" key="">
                            <ul class="select-box">
                                <#if bizType??>
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
                <li class="condition-item item-btn">
                    <span class="condition-input">
                        <a href="javascript:;" class="submit-btn J_set"  id="updateMerchant">保 存</a>
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
    	    formObj.attr("action", sysconfig.ctx+"/merchantHandle/query.htm");
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
    	    					formObj.attr("action", sysconfig.ctx+"/merchantHandle/export.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/merchantHandle/query.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/merchantHandle/query.htm");
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
    	    		formObj.attr("action", sysconfig.ctx+"/merchantHandle/query.htm");
    	    		formObj.submit(); 
            	}    	           
            }
                        
    	} 
          
        
	</script>
<script type="text/javascript" data-main="${ctx}/scripts/eppscbp/kjf-receive-manager-search" src="${ctx}/scripts/lib/require/require.min.js"></script>
