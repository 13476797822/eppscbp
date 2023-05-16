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
    
    

	
    <div class="batch-payment">
        <#include "/screen/title.ftl">
        
        <form method='post' method="post" id="f0" name="f0">
        <div class="content">      
        	<!-- tab切 -->
            <div class="batch-tab-list">
                <ul class="clearfix">
                    <li class="tab-item hover">购付汇<i class="top-line"></i></li>
                    <a href="${ctx}/singleQuery/collAndSettleQuery/init.htm"><li class="tab-item" >收结汇<i class="top-line"></i></li></a>
                </ul>
            </div>
        	
        	
			
            <ul class="condition-list clearfix">
                <li class="condition-item">
                    <span class="condition-title">汇款单号：</span>
                    <span class="condition-input">
                        <input name="businessNo" type="text" value="${(requestDto.businessNo)!''}">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:4;">
                    <span class="condition-title">业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="bizType" class="select-input input_text empty" value="${(requestDto.bizType)!''}" key="">
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
                    <span class="condition-title">产品类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="productType" class="select-input input_text empty"  value="${(requestDto.productType)!''}" key="">
                            <ul class="select-box">
                            	<#if productType??>
                            	<#list productType as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:2;">
                    <span class="condition-title">交易状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="status" class="select-input input_text empty"  value="${(requestDto.status)!''}" key="">
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
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:1;">
                    <span class="condition-title">真实材料状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="supStatus" class="select-input input_text empty"  value="${(requestDto.supStatus)!''}" key="">
                            <ul class="select-box">
                                <#if supStatus??>
                            	<#list supStatus as unit>
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
                    <span class="condition-title">明细校验状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="detailFlag" class="select-input input_text empty"  value="${(requestDto.detailFlag)!''}" key="">
                            <ul class="select-box">
                                <#if detailFlag??>
                            	<#list detailFlag as unit>
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
                    <span class="condition-title">收款商户编码：</span>
                    <span class="condition-input">
                        <input name="payeeMerchantCode" type="text" value="${(requestDto.payeeMerchantCode)!''}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title">收款商户名称：</span>
                    <span class="condition-input">
                        <input name="payeeMerchantName" type="text" value="${(requestDto.payeeMerchantName)!''}">
                    </span>
                </li>
                <li class="condition-item date">
                    <span class="condition-title">订单创建时间：</span>
                    <span class="condition-input" style="padding:1px 0;">
                        <input type="text" class="input_mod input_time" id="date1" name="creatOrderStartTime" readonly="true" value="${(requestDto.creatOrderStartTime)!''}" />
                        <input type="text" class="input_mod input_time" id="date2" name="creatOrderEndTime" readonly="true" value="${(requestDto.creatOrderEndTime)!''}" />
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
                        <a href="javascript:;" class="cancel-btn reset">重 置</a>
                    </span>
                </li>
            </ul>
            
            
            
            <a href="javascript:void(0)" class="outport-btn" onclick="submitExport();"><i></i> 导出</a>
            

            
            
            <div class="table-box" id="table-box-result">
            
            	<#if errMessage??>
            	<p class="no-result"><i></i>${errMessage}</p>
            	</#if>
            	
            	
            	<#if resultList??>
                <table class="fixed-table" id="fixed-table-result">
                    <thead>
                        <tr>
                            <th width="156" class="opration">操作</th>
                        </tr>                        
                    </thead>
                    <tbody>
                    	<#list resultList as item>
                    		<tr><td class="opration">
                    		<span class="gopay  <#if (item.status!='F' && item.status!='P') || item.detailFlag!='1'> disabled </#if> " data-num="${item.businessNo}" data-biz-type = "${item.bizType}" data-product-type = "${item.productType}">去支付</span>
                    		<span class="vertical-line">|</span>
                    		<span class="goclose <#if item.status!='F' && item.status!='P'> disabled </#if> " data-num="${item.businessNo}" data-biz-type = "${item.bizType}" data-product-type = "${item.productType}">关闭订单</span></td></tr>                      		
                    	</#list>
                    </tbody>
                </table>
                
                
                <div class="result-table">                   
                    <table style="table-layout:  fixed;">
                        <thead>
                            <tr>
                                <th width="40">序号</th>
                                <th width="150">汇款单号</th>
                                <th width="110">收款商户编码</th>
                                <th width="200">收款商户名称</th>
                                <th width="100">交易状态</th>
                                <th width="150">交易失败原因</th>
                                <th width="150">真实性材料上传状态</th>
                                <th width="200">上传失败原因</th>
                                <th width="150">订单创建时间</th>
                                <th width="80">明细笔数</th>
                                <th width="100" class="money">申请金额</th>
                                <th width="110" class="money">支付金额(人民币)</th>
                                <th width="110" class="money">支付金额(外币)</th>
                                <th width="100">汇率</th>
                                <th width="110" class="money">汇出金额</th>
                                <th width="150">明细校验状态</th>
                                <th width="156" class="opration">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                           <#list resultList as item>
	        				<tr>
	        					<td>${item_index+1}</td>
		        				<td><a href="${ctx}/singleQuery/singleOrderQuery/detailInfo.htm?subBusinessNo=${item.subBusinessNo}" class="detail">${item.businessNo}</a></td>
		        				<td>${item.payeeMerchantCode}</td>
		        				<td title="${item.payeeMerchantName}">${item.payeeMerchantName}</td>
		        				<td>${item.statusName}</td>
                                <td title=${item.failFileAddress}>
                                    <#if item.errorMessage == "F"><a href="${ctx}/singleQuery/singleOrderQuery/downLoadFailFile.htm?failFileAddress=${item.failFileAddress}"><span>二要素鉴权失败明细</span></a><#else>--</#if>
                                </td>
		        				<td>${item.supStatusName}
		        					<#if item.bizType=='001' && ( item.status!='P' && item.status!='F' && item.status!='C') && (item.supStatus=='P' || item.supStatus=='F')> <span class="upload-btn">[点击上传]</span> </#if>
		        					<#if item.bizType=='003' && ( item.status!='P' && item.status!='F' && item.status!='C') && (item.supStatus=='P' || item.supStatus=='F')> <span class="uploaded"><a onclick="studySubmit('${item.businessNo}');">凭证已上传</a></span> </#if>
		        					<#if item.bizType=='005' && ( item.status!='P' && item.status!='F' && item.status!='C') && (item.supStatus=='P' || item.supStatus=='F')> <span class="upload-btn">[点击上传]</span> </#if> 
		        				</td>
		        				<td title="${item.supErrMsg}">${item.supErrMsg}</td>
		        				<td>${item.creatTime}</td>
		        				<td>${item.numbers}</td>
		        				<td class="money" title="${item.applyAmt} ${item.applyCurName}">${item.applyAmt} ${item.applyCurName}</td>		        				
		        				<td class="money" title="${item.paymentRmbAmt} 元">${item.paymentRmbAmt} 元</td>
		        				<#if 'CNY'==item.remitCur>
		        				<td class="money"></td>
		        				<#else>
		        				<td class="money">${item.paymentExAmt} ${item.remitCurName}</td>
		        				</#if>
		        				<td>${item.exchangeRateStr}</td>		        				
		        				<td class="money" title="${item.remitAmtFormat}">${item.remitAmtFormat}</td>
								<td>${item.detailFlagName}</td>
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
                    <input type="text" name="uploadUrl" value="/singleQuery/singleOrderQuery/tradeSubmit.htm" id="uploadUrlHid" class="hide"></input> 
             </div>
             </#if>        
        </div>
    </div>
    </form>
    
    <!-- 货物贸易监管文件上传 -->
    <script type="text/html" id="upload-tpl">
    	<form method='post' id="f4" name="f4">
    	<input type="text" name="businessNo" value="" id="businessNoHid" class="hide"></input>  
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
	    	formObj.attr("action", sysconfig.ctx+"/singleQuery/singleOrderQuery/query.htm");
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
    	    					formObj.attr("action", sysconfig.ctx+"/singleQuery/singleOrderQuery/export.htm");
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
    	
    	//留学教育监管触发
    	function studySubmit(businessNo){
    		$.ajax({     
                    type: "POST",
                    dataType: "JSON",
                    url: sysconfig.ctx+"/singleQuery/singleOrderQuery/studySuperviseUpload.htm",
                    data: {"businessNo":businessNo},
                    success: function (data) {
                    	//若页面过期跳转至首页
                    	isIntercepted(data);
                    	
                        var html;                	
	                    if(data.success == "s"){
	                    	html = "<p class='add-success'><i></i>&nbsp;上传申请成功</p>";
	            	 	}else{
	            	 		html = "<p class='add-fail'><i></i>&nbsp;上传申请失败:"+data.responseMsg+"</p>";
	            	 	}
	            	 	var win = $.dialog({
	                            title : "上传结果",
	                            content : html,
	                            width : 250,
	                            blur:false,
	                            afterShow : function($dialog, callback){
	                                setTimeout(function(){
	                                    callback.close();
	                                    submitQuery();
	                                },3000)
	                            },
	                            showClose : true,
	                            maskCss: { // 遮罩层背景
	                                opacity: 0
	                            }
	                        });
	                    win.show();
                    },
                    error : function(status) {
                        var win = $.dialog({
                            title : "",
                            content : "<p class='add-fail'><i></i>&nbsp;上传申请异常</p>",
                            width : 250,
                            blur:false,
                            afterShow : function($dialog, callback){
                                setTimeout(function(){
                                    callback.close();
                                },3000)
                            },
                            showClose : true,
                            maskCss: { // 遮罩层背景
                                opacity: 0
                            }
                        })
                        win.show();
                    }
            });
           
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
    	    	formObj.attr("action", sysconfig.ctx+"/singleQuery/singleOrderQuery/query.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/singleQuery/singleOrderQuery/query.htm");
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
    	    		formObj.attr("action", sysconfig.ctx+"/singleQuery/singleOrderQuery/query.htm");
    	    		formObj.submit(); 
            	}
            	      
            	           
            }         
    	} 
            
  
	</script>

  

    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/kjf-single-order-search" src="${ctx}/scripts/lib/require/require.min.js"></script>
    
    
    
    
    
    