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
        	<!-- tab切 -->
            <div class="batch-tab-list">
                <ul class="clearfix">
               		<a href="${ctx}/singleQuery/singleOrderQuery/init.htm"><li class="tab-item" >购付汇<i class="top-line"></i></li></a>
                    <li class="tab-item hover">收结汇<i class="top-line"></i></li>
                    
                </ul>
            </div>
        	
        	
			
            <ul class="condition-list clearfix">
                <li class="condition-item" style="position:relative;z-index:4;">
                    <span class="condition-title">结汇币种：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="currency" id="currency" class="select-input input_text empty" value="${(requestDto.currency)!''}" key="">
                            <ul class="select-box">
                            	<#if curType??>
                            	<#list curType as unit>
                                <li class="select-item">
                                    <a class="sel-val" id="sel-val-currency" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>                                
                            </ul>
                        </div>
                    </span>
                </li>
                
                
                 <li class="condition-item" style="position:relative;z-index:4;">
                    <span class="condition-title">批付状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" name="batchPayFlag" class="select-input input_text empty" readonly key="" value="${(requestDto.batchPayFlag)!''}">
                            <ul class="select-box">
                            	<#if batchPayFlag??>
                            	<#list batchPayFlag as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>                                
                            </ul>
                        </div>
                    </span>
                </li>
                 
                
                <li class="condition-item" style="position:relative;z-index:3;<#if requestDto.currency=='人民币'>display:none</#if>" id="condition-item-1">
                    <span class="condition-title">订单状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly <#if requestDto.currency!='人民币'>name="orderState"</#if> id="orderState1" class="select-input input_text empty" value="${(requestDto.orderState)!''}" key="">
                            <ul class="select-box">
                            	<#if collSettType??>
                            	<#list collSettType as unit>
                                <li class="select-item">
                                    <a class="sel-val" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                                </li>
                                </#list>
                                </#if>                                
                            </ul>
                        </div>
                    </span>
                </li>
                
                <li class="condition-item" style="position:relative;z-index:3;<#if requestDto.currency!='人民币'>display:none</#if>" id="condition-item-2">
                    <span class="condition-title">订单状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly <#if requestDto.currency=='人民币'>name="orderState"</#if> id="orderState2" class="select-input input_text empty" value="${(requestDto.orderState)!''}" key="">
                            <ul class="select-box">
                            	<#if collType??>
                            	<#list collType as unit>
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
                    <span class="condition-title">业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" name="bizType" class="select-input input_text empty" readonly key="" value="${(requestDto.bizType)!''}">
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
                    <span class="condition-title">结汇金额：</span>
                    <span class="condition-input">
                        <input name="orderAmt" type="text" value="${(requestDto.orderAmt)!''}">
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
                    <span class="condition-title">订单号：</span>
                    <span class="condition-input">
                        <input name="businessNo" type="text" value="${(requestDto.businessNo)!''}">
                    </span>
                </li>
                
                <li class="condition-item">
                    <span class="condition-title"></span>
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
                <table class="fixed-table" id="fixed-table-result" style="width:100px">
                    <thead>
                        <tr>
                            <th width="100" class="opration">操作</th>
                        </tr>                        
                    </thead>
                    <tbody >
                    	<#list resultList as item>
                    		<tr><td class="opration">
                    		<span class="gopay disabled"></span>
                    		<span class="vertical-line"></span>
                            <#if ((item.state=='31') || (item.state=='21' && item.currency=='CNY'))&&(item.batchPayFlag=='1') >
                                <span class="batchpay" data-num="${item.orderNo}">批付详情</span>
                            <#else>
                    		    <span class="goclose-cs<#if item.state!='10'&&item.state!='11'> disabled </#if>" data-num="${item.orderNo}" >关闭订单</span>
                            </#if>
                    		</td></tr>                      		
                    	</#list>
                    </tbody>
                </table>
                
                
                <div class="result-table">                   
                    <table style="table-layout:  fixed;">
                        <thead>
                            <tr>
                                <th width="40">序号</th>
                                <th width="150">订单号</th>
                                <th width="110">订单币种</th>
                                <th width="100">订单金额</th>
                                <th width="90">订单状态</th>
                                <th width="90">商户实际收入</th>
                                <th width="90">已批付金额</th>
                                <th width="90">待批付金额</th>
                                <th width="90">批付状态</th>
								<th width="150">真实性材料上传状态</th>
								<th width="200">上传失败原因</th>
                                <th width="200">创建时间</th>                                  
                                <th width="100" class="opration">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                           <#list resultList as item>
	        				<tr>
	        					<td>${item_index+1}</td>
		        				<td><a href="${ctx}/singleQuery/collAndSettleQuery/detailInfo.htm?orderNo=${item.orderNo}" class="detail">${item.orderNo}</a></td>
		        				<td>${item.currencyName}</td>
		        				<td title="${item.orderAmt}">${item.orderAmt}</td>
		        				<td>${item.stateName}</td>
		        				<td title="${item.actualRevenue}">${item.actualRevenue}</td>
		        				<#if item.dataSource == '03' || item.dataSource == '05'><td></td><#else><td title="${item.payedAmount}">${item.payedAmount}</td></#if>
                                <#if item.dataSource == '03' || item.dataSource == '05'><td></td><#else><td title="${item.prePayAmount}">${item.prePayAmount}</td></#if>
		        				<td title="${item.batchPayFlagStr}">${item.batchPayFlagStr}</td>
		        				<td>${item.supStatusName}
		        					<#if (item.superviseCombineTypeAuth =='N') && ((item.state!='99') && (item.supStatus=='P' || item.supStatus=='F'))> <span class="upload-btn">[点击上传]</span> </#if> 
		        				</td>
		        				<td title="${item.supErrMsg}">${item.supErrMsg}</td>
		        				<td title="${item.orderCreateTime}">${item.orderCreateTime}</td>
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
                    <input type="text" name="uploadUrl" value="/singleQuery/collAndSettleQuery/superviseSubmit.htm" id="uploadUrlHid" class="hide"></input> 
             </div>
             </#if>        
        </div>

        <div class="content info-content hide">
            <div>
                <a href="javascript:;" class="back-btn">&lt;&nbsp;返回上级</a>
            </div>

            <div class="clearfix">
                <div class="head-amount-wrap fl">
                    <p class="head-amount-content">待批付金额：<span class="num"></span> 元</p>
                    <input style="display:none" id="prePayAmount" name="prePayAmount" value=""></input>
                    <input style="display:none" id="bpBusNo" name="bpBusNo" value=""></input>
                    <input style="display:none" id="bpFileAdd" name="bpFileAdd" value=""></input>
                    <div class="tip-box">
                        <i class="tip-icon"></i>
                        <div class="tip-pop-box">
                            <i class="tip-arrow before"></i>
                            <i class="tip-arrow after"></i>
                            <div class="tip-pop-cnt">出款总额不大于待批付金额</div>
                        </div>
                    </div>
                </div>
                <a href="javascript:void(0)" class="import-btn fr" id="upload-bp-btn" "><i></i> 导入</a>
            </div>
            
            <!-- 批付详情 -->
            <div class="pfxq-wrap">
            </div>
            <div class="msg-box">
            </div>
            <div class="pf-btn-wrap">
                <a href="javascript:;" class="yes-btn mr20">批付</a>
                <a href="javascript:;" class="clear-btn">清空金额</a>
            </div>
            <div class="pf-btn-wrap pf-btn-wrap2 mt48 pb35 hide">
                <a href="javascript:;" class="query-btn mr20" id="query">查询处理结果</a>
            </div>
        </div>
    </div>
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
	    	formObj.attr("action", sysconfig.ctx+"/singleQuery/collAndSettleQuery/query.htm");
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
                            formObj.attr("action", sysconfig.ctx+"/singleQuery/collAndSettleQuery/export.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/singleQuery/collAndSettleQuery/query.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/singleQuery/collAndSettleQuery/query.htm");
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
    	    		formObj.attr("action", sysconfig.ctx+"/singleQuery/collAndSettleQuery/query.htm");
    	    		formObj.submit(); 
            	}
            	      
            	           
            }         
    	} 
            
  
	</script>
	
	<!-- 收结汇监管文件上传 -->
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
    
    
    
    <!-- 批付文件上传 -->
    <script type="text/html" id="bp-upload-tpl">
    	<form method='post' id="f5" name="f5">
    	<input type="text" name="bpBusinessNo" value="" id="bpBusinessNo" class="hide"></input> 
        <input type="text" name="bpPrePayAmount" value="" id="bpPrePayAmount" class="hide"></input> 
        <div class="pop-upload bp-upload-files">
            <div>
                <label>文件上传：</label>                
                <input type="text" class="input file-path" readonly="true" name="filePath" value="" id="bpFile"> 
					<input type="text" name="bpFileAddress" id="bpFileAddress" value="" class="input file-path hide"></input>
                 	<div class="file-operation">
                        <a class="up" href="javascript:;">选择文件
                        <form id="bpUploadForm" enctype="multipart/form-data">  
                        	<input type="file" id="bp-file-btn" name="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"></a>
                        </form> 
                        <a class="download" href="${ctx}/template/file/cp_batch_payment.xlsx">下载模版</a>
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
                    <p class="title">上传失败，请修改后重新上传</p>
                    <span class="err-text"></span>
                </div>
            </div>
            <a href="javascript:;" class="submit-btn J_setfile disabled" id="BP_placeOrder" style="margin-left:144px;">保 存</a>
            <a href="javascript:;" class="cancel-btn">取 消</a>
        </div>
        </form>
    </script>
    

    <!--批付详情 [[-->
    <script type="text/html" id="info-tpl">
        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th class="padd8">收款方名称</th>
                        <th>银行账号</th>
                        <th>出款金额</th>
                        <th>出款笔数</th>
                        <th>附言</th>
                    </tr>
                </thead>
                <tbody>
                    {{each resultList as item}}
                    <tr>
                        <td>{{item.payeeMerchantName}}</td>
                        <td>{{item.bankAccountNo}}</td>
                        <td> <input class='bankcard-inp' name='amount' type='text' value="{{item.amount}}" t_value="" o_value=""/></td>
                        <td> <input class='number-inp' name='number' type='text' value="1" t_value="" o_value=""/></td>
                        <td> <input class='ordername-inp' name='orderName' type='text' value="" t_value="" o_value=""/></td>
                    </tr>
                    {{/each}}
                </tbody>
            </table>
        </div>
    </script>
    <!--批付详情]]-->


    <!-- 20210319分页开始 -->
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
    <!-- 20210319分页结束 -->
    
    <!--批付信息已提交 [[-->
    <script type="text/html" id="pf-result-tpl">
        <div class="msg-box green">
            <i></i>{{responseMsg}}
        </div>
        <div class="pf-btn-wrap pb35">
            <a href="javascript:;" class="yes-btn">确定</a>
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
    
    <!-- 查询为空模版 [[ -->
    <script type="text/html" id="empty-tpl">
        <ul class="g-row wait-wrap">
            <li class="wait">
                <div class="info">
                    <div class="desc">抱歉，没有找到相应的信息</div>
                </div>
            </li>
        </ul>
    </script>
    <!-- 20210319分页结束 -->
    <!--批付信息已提交 ]]-->
    <!--查询处理结果 [[-->
    <script type="text/html" id="pf-result-form-tpl">
        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th class="padd8">收款方名称</th>
                        <th>银行账号</th>
                        <th>出款金额</th>
                        <th>出款笔数</th>
                    </tr>
                </thead>
                <tbody>
                    {{each resultList as item}}
                    <tr class="{{item.trIndex % 2 == 0 ? '' : 'active'}}">
                        <td>{{item.payeeMerchantName}}</td>
                        <td>{{item.bankAccountNo}}</td>
                        <td>{{item.amount}}</td>
                    </tr>
                    {{/each}}
                </tbody>
            </table>            
        </div>
    </script>
    <!--查询处理结果 ]]-->
  

    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/kjf-single-order-search" src="${ctx}/scripts/lib/require/require.min.js"></script>
    
    
    
    
    