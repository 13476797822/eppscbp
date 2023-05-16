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
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/sjh-order-query.css">
    

	
        <div class="batch-payment">
        <#include "/screen/title.ftl">
        
        <form method='post' method="post" id="f0" name="f0">
        
   
        
        <div class="content">      
            <h3 class="content-title">
	            结汇付款：
	        </h3>
	                  
            <ul class="condition-list clearfix">
                <li class="condition-item" style="position:relative;z-index:4;">
                    <span class="condition-title" style="
					    margin-left: 10px;
					">待解付资金流水号：</span>
                    <span class="condition-input">               
                    	<input type="text" name="payNo" id="payNo" value="${(criteria.payNo)!''}">
                    </span>
                </li>
                
                
                <li class="condition-item" style="position:relative;z-index:4;">
                    <span class="condition-title" style="
					    width: 246px;
					">状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" name="status" class="select-input input_text empty" readonly key="" value="${(criteria.status)!''}" style="margin-left: 0px;">
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
                    </span>
                </li>

                <li class="condition-item date">
                    <span class="condition-title" style="
					    margin-left: 10px;
					">来账通知时间：</span>
                    <span class="condition-input" style="padding:1px 0;">
                        <input type="text" class="input_mod input_time" id="date1" name="frNoticeTime" readonly="true" value="${(criteria.frNoticeTime)!''}" />
                        <input type="text" class="input_mod input_time" id="date2" name="toNoticeTime" readonly="true" value="${(criteria.toNoticeTime)!''}" />
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
                        <a href="javascript:;" class="submit-btn" id="submitQuery" onclick="submitQuery('none');">查 询</a>
                        <a href="javascript:void(0)" class="outport-btn" onclick="submitExport();" style="margin-left: 20px;"><i></i> 导出</a>
                    </span>
                </li>
            </ul>
            
            <div class="table-box" id="table-box-result">
            
            	<#if errMessage??>
            	<p class="no-result"><i></i>${errMessage}</p>
            	</#if>
            	
            	<#if resultList??>
                <table class="fixed-table" id="fixed-table-result" style="width:60px">
                    <thead>
                        <tr>
                            <th width="60" class="opration">操作</th>
                        </tr>                        
                    </thead>
                    <tbody >
                    	<#list resultList as item>
                    		<tr><td class="opration">
                            <#if item.status == '103'>
	        					<a href="javascript:;" class="op-btn" id="submitApply" 
	        					data="${item.amount}" data2="${item.payNo}" data3="${item.bankCode}" data4="${item.payerBankCard}" data5="${item.currency}" data6="${item.payeeMerchantCode}"
	        					>结汇付款</a>
	        				<#else>
                    		    <span style="width: 58px;display: block;float: left;"></span>
                            </#if> 
                    		</td></tr>                      		
                    	</#list>
                    </tbody>
                </table>
                
                <div class="result-table">                   
                    <table style="table-layout:  fixed;">
                        <thead>
                            <tr>
                                <th width="100">来账通知时间</th> 
                                <th width="150">待解付资金流水号</th>
                                <th width="150">金额</th>
                                <th width="150">付款账号</th>
                                <th width="200">付款人名称</th> 
                                <th width="200">附言</th>
                                <th width="90">状态</th>  
                                <th width="60" class="opration">操作</th>                      
                            </tr>
                        </thead>
                        <tbody>
                           <#list resultList as item>
	        				<tr>
	        			        <td>${item.noticeTime}</td>
		        				<td>${item.payNo}</td>
		        				<td>${item.amount}(${item.currency})</td>
		        				<td>${item.payerBankCard}(${item.bank})</td>
		        				<td title="${item.accountNameAdd}">${item.accountNameAdd}</td>
		        				<td title="${item.remark}">${item.remark}</td>
		        				<td><#if item.status == '103'>待匹配申请<#elseif item.status == '104'>匹配申请成功</#if></td>
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
                    <input type="text" name="uploadUrl" value="/preOrderCollAndSettle/preUploadFile.htm" id="uploadUrlHid" class="hide"></input> 
             </div>
             </#if>        
        </div>
        
    </div>
    </form>
    
    <form method='post' action="${ctx}/preOrderCollAndSettle/export.htm" name="f1">
	</form>
    
    
    
    
    
    <script type="text/javascript">
    	//提交查询
		function submitQuery(flag){
			if( $("#error").css("display") == "block"){
				return false;
			}
			if(flag=='none'){
				if($("#pageClass").length>0){
					$("#currentPage").attr("value","1");					
				}				
			}			
			var formObj = $("form[name='f0']");
	    	formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/arrivalQuery.htm");
	    	formObj.submit();
    	}      	
    	
    	//提交导出
		function submitExport(){
			if($("#fixed-table-result").length <=0){
				alert("请先查询数据!");
			}
			else{   	    	
    	    	$.ajax({
                    url: sysconfig.ctx + '/authStatus',
                    crossDomain: true,
                    cache: false,
                    dataType: 'jsonp',
                    success: function (data) {
                        if (data.hasLogin) {
                            var formObj = $("form[name='f0']");
                            formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/export.htm");
                            formObj.submit(); 
                        } else {
                            window.location.href=sysconfig.ctx+"/preOrderCollAndSettle/preFile/init.htm";
                        }
                    },
                    error : function(data) {
                        window.location.href=sysconfig.ctx+"/preOrderCollAndSettle/preFile/init.htm";
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
    	    	formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/arrivalQuery.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/arrivalQuery.htm");
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
    	    		formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/arrivalQuery.htm");
    	    		formObj.submit(); 
            	}
            	      
            	           
            }         
    	} 
            
  
	</script>

    <!-- 批付文件上传 -->
    <script type="text/html" id="bp-upload-tpl">
        <div class="pop-upload bp-upload-files">
        	<div id="dialogSelect" class="jr-select" style="min-height: 30px;margin-bottom: 10px;">
        		<label>业务类型：</label>
                <i class="select-input-arrow-icon arrow-icon-closed" style="left: 348px;"></i>
                <input type="text" readonly name="bizType" id="bizType" class="select-input input_text empty" key="" style="height: 28px;margin-left: 0;width: 268px;">
                <ul class="select-box" style="left: 87px;">
                	<#if chargeBizType??>
                	<#list chargeBizType as unit>
                    <li class="select-item">
                        <a class="sel-val" id="sel-val-currency" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                    </li>
                    </#list>
                    </#if>                                
                </ul>
            </div>
            <div>
            	<p id="bpPrePayAmountTxt" style="margin-left: 17px;margin-bottom: 5px;"></p>
                <input type="text" name="bpPrePayAmount" value="" id="bpPrePayAmount" class="hide"></input> 
            </div>
            <div>
            	<label>文件上传：</label>                
                <input type="text" class="input file-path" readonly="true" name="filePath" value="" id="bpFile" style="height: 20px;"> 
				<input type="text" name="bpFileAddress" id="bpFileAddress" value="" class="input file-path hide"></input>
             	<div class="file-operation">
             		<form id="bpUploadForm" enctype="multipart/form-data">  
	                    <a class="up" href="javascript:;">选择文件 
	                    	<input type="file" id="bp-file-btn" name="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
	                    </a>
                    </form> 
                    <a class="download" href="${ctx}/template/file/pre_batch_payment.xlsx" style="position: fixed;">下载模版</a>
                </div>
            	<div class="clear"></div>
            </div>
            <div class="result-text">
                <div class="wait" id="bpWait">
                    <img src="${ctx}/style/images/empty-tip.jpg">
                    <p class="title">请上传模版文件</p>
                </div>
                <div class="success hide" id="bpUploadSuccess">
                    <img src="${ctx}/style/images/icon_success_24.png">
                    <p class="title">上传成功</p>
                </div>
                <div class="fail hide" id="bpUploadFail">
                    <img src="${ctx}/style/images/icon_error_32.png">
                    <p class="title">分发文件错误信息</p>
                    <span class="err-text"></span>
                    <a href="javascript:void(0)" class="download disabled">下载错误信息提示文件</a>
                </div>
            </div>
            <a href="javascript:;" class="submit-btn J_setfile disabled" id="BP_placeOrder" style="margin-left:144px;">保 存</a>
            <a href="javascript:;" class="cancel-btn">取 消</a>
        </div>
    </script>

    <!--批付详情弹窗[[-->
    <script type="text/html" id="pf-dialog-tpl">
        <div id="alertTable"></div>
        <div>
            <p>总金额：{{amount}} 总笔数：{{number}}</p>
        </div>
        <div class="msg-box">
        </div>
        <div class="pf-btn-wrap pb35">
            <a href="javascript:;" class="yes-btn mr20" id="submit">提交</a>
            <a href="javascript:;" class="clear-btn" id="close">关闭</a>
        </div>
    </script>
    <!--批付详情弹窗]]-->

    <!--表格 [[-->
    <script type="text/html" id="table-result-tpl">
        <div class="table-wrap table-wrap1">
            <div style="max-height: 250px;overflow-y: auto;">
                <table>
                    <thead>
                        <tr>
                            <th class="padd8">收款方名称</th>
                            <th>银行账号</th>
                            <th>出款金额</th>
                            <th>附言</th>
                        </tr>
                    </thead>
                    <tbody>
                        {{each list as item}}
                        <tr class="{{item.trIndex % 2 == 0 ? '' : 'active'}}">
                            <td>{{item.payeeMerchantName}}</td>
                            <td class="bankAccountNo">{{item.bankAccountNo}}</td>
                            <td>{{item.amount}}</td>
                            <td>{{item.orderName}}</td>
                        </tr>
                        {{/each}}
                    </tbody>
                </table>
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
        </div>
        
    </script>
    <!--表格 ]]-->
    
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
    
    <!--批付信息已提交 [[-->
    <script type="text/html" id="pf-result-tpl">
        <div class="msg-box green">
            <i></i>{{responseMsg}}
        </div>
        <div class="pf-btn-wrap pb35">
            <a href="javascript:;" class="yes-btn">确定</a>
        </div>
    </script>

    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/sjh-pre-order-main.js" src="${ctx}/scripts/lib/require/require.min.js"></script>
    
    
    
    
    