<meta charset="UTF-8">
    <title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/placeholder.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">
    


    <div class="batch-payment">
        <#include "/screen/title.ftl">
 
        
        <div class="content">
           <!-- tab切 -->
            <div class="batch-tab-list">
                <ul class="clearfix">
                    <a href="${ctx}/batchQuery/batchOrderQuery/init.htm"><li class="tab-item">交易单查询<i class="top-line"></i></li></a>
                    <a href="javascript:void(0);" ><li class="tab-item hover" >明细汇出查询<i class="top-line"></i></li></a>
                </ul>
            </div>

			<form method='post' method="post" id="f0" name="f0">	
            <!-- 明细汇出查询 -->
            <ul class="condition-list clearfix">
                <li class="condition-item">
                    <span class="condition-title">汇款单号：</span>
                    <span class="condition-input">
                        <input type="text" name="businessNo" value="${(requestDto.businessNo)!''}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title">汇款子单号：</span>
                    <span class="condition-input">
                        <input type="text" name="subBusinessNo" value="${(requestDto.subBusinessNo)!''}">
                    </span>
                </li>
                <li class="condition-item">
                    <span class="condition-title">收款商户编码：</span>
                    <span class="condition-input">
                        <input name="payeeMerchantCode" type="text" value="${(requestDto.payeeMerchantCode)!''}">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:4;">
                    <span class="condition-title">收款商户名称：</span>
                    <span class="condition-input">
                        <input name="payeeMerchantName" type="text" value="${(requestDto.payeeMerchantName)!''}">
                    </span>
                </li>
                <li class="condition-item" style="position:relative;z-index:3;">
                    <span class="condition-title">汇款状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="hidden" class="select-input input_text empty"value="">
                            <input type="text" readonly class="select-input input_text empty"  key="" name="status" value="${(requestDto.status)!''}">
                            <ul class="select-box">
                                <#if remitStatus??>
                            	<#list remitStatus as unit>
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
                    <span class="condition-title">订单创建时间：</span>
                    <span class="condition-input" style="padding:1px 0;">
                        <input type="text" class="input_mod input_time" id="date1" name="creatOrderStartTime" readonly="true" value="${(requestDto.creatOrderStartTime)!''}" />                        
                        <input type="text" class="input_mod input_time" id="date2" name="creatOrderEndTime" readonly="true" value="${(requestDto.creatOrderEndTime)!''}" />
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
                <div class="result-table" id="result-table-result">                   
                    <table style="table-layout:  fixed;">
                        <thead>
                            <tr>
                                <th width="40">序号</th>
                                <th width="150">汇款单号</th>
                                <th width="150">汇款子单号</th>
                                <th width="110">收款商户编码</th>
                                <th width="110">收款商户名称</th>
                                <th width="200">客户名称</th>
                                <th width="110">汇款状态</th>                               
                                <th width="150">创建时间</th>                                
                                <th width="150" class="money">申请金额</th>
                                <th width="100">汇率</th>
                                <th width="110" class="money">汇出金额(外币)</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<#list resultList as item>
	        				<tr>
	        					<td>${item_index+1}</td>
		        				<td>${item.businessNo}</td>
		        				<td><a href="${ctx}/batchQuery/batchOrderRemitQuery/detailInfo.htm?subBusinessNo=${item.subBusinessNo}" class="detail">${item.subBusinessNo}</a></td>
		        				<td>${item.payeeMerchantCode}</td>
		        				<td title="${item.payeeMerchantName}">${item.payeeMerchantName}</td>
		        				<td title="${item.personalName}">${item.personalName}</td>
		        				<td>${item.statusName}</td>
		        				<td>${item.creatTime}</td>
		        				<td class="money" title="${item.applyCurAndName}">${item.applyCurAndName}</td>
		        				<td>${item.exchangeRateStr}</td>
		        				<td class="money" title="${item.remitCurAndName}">${item.remitCurAndName}</td>

		    			   </tr>
	        			 </#list>
                        </tbody>
                    </table>
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
    </div>

            

    


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
    	    formObj.attr("action", sysconfig.ctx+"/batchQuery/batchOrderRemitQuery/query.htm");
    	    formObj.submit(); 
    	}  
    	
    	//提交导出
		function submitExport(){
			if($("#result-table-result").length <=0){
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
    	    					formObj.attr("action", sysconfig.ctx+"/batchQuery/batchOrderRemitQuery/export.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/batchQuery/batchOrderRemitQuery/query.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/batchQuery/batchOrderRemitQuery/query.htm");
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
    	    		formObj.attr("action", sysconfig.ctx+"/batchQuery/batchOrderRemitQuery/query.htm");
    	    		formObj.submit(); 
            	}
            	      
            	           
            }         
    	} 
            
  
	</script>



    
 
            
    





    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/kjf-batch-search" src="${ctx}/scripts/lib/require/require.min.js"></script>
