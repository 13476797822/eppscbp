<meta charset="UTF-8">
    <title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="https://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/deal-center.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/placeholder.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cross-border-payment.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/sjh-order-query.css">
    

	
    <div class="batch-payment">
        <#include "/screen/title.ftl">
        
        <form method='post' method="post" id="f0" name="f0">
        
   
        
        <div class="content">      
            <h3 class="content-title">
	            收结汇资源库：
	        </h3>
	        
        	<textarea type="text" class="input show" readonly placeholder="" id="fundinfo">订单可用额度：
                <#if preAmount??>
            	<#list preAmount as unit>
${unit.bizTypeStr}:人民币${unit.cnyAmountStr}元（美元${unit.usdAmountStr}）
                </#list>
                </#if>
                </textarea> 
            <div class="clear"></div>
            
            <ul class="condition-list clearfix">
                <li class="condition-item" style="position:relative;z-index:4;">
                    <span class="condition-title">业务类型：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" readonly name="bizType" id="bizType" class="select-input input_text empty" value="${(criteria.bizType)!''}" key="" style="margin-left: 0px;">
                            <ul class="select-box">
                            	<#if chargeBizType??>
                            	<#list chargeBizType as unit>
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
                    <span class="condition-title">文件状态：</span>
                    <span class="condition-input">
                        <div class="jr-select">
                            <i class="select-input-arrow-icon arrow-icon-closed"></i>
                            <input type="text" name="fileStatus" class="select-input input_text empty" readonly key="" value="${(criteria.fileStatus)!''}" style="margin-left: 0px;">
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
                    <span class="condition-title">上传时间：</span>
                    <span class="condition-input" style="padding:1px 0;">
                        <input type="text" class="input_mod input_time" id="date1" name="creatFileStartTime" readonly="true" value="${(criteria.creatFileStartTime)!''}" />
                        <input type="text" class="input_mod input_time" id="date2" name="creatFileEndTime" readonly="true" value="${(criteria.creatFileEndTime)!''}" />
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
                        <a href="javascript:;" class="submit-btn" id="uploadDetail" style="margin-left: 20px;"><i></i> 上传交易明细</a>
                    </span>
                </li>
            </ul>
            
            <div style="
			    height: 10px;
			"></div>
            
            <div class="table-box" id="table-box-result">
            
            	<#if errMessage??>
            	<p class="no-result"><i></i>${errMessage}</p>
            	</#if>
            	
            	
            	<#if resultList??>
                
                <div class="result-table">                   
                    <table style="table-layout:  fixed;">
                        <thead>
                            <tr>
                                <th width="200">上传时间</th> 
                                <th width="150">文件号</th>
                                <th width="110">业务类型</th>
                                <th width="90">文件状态</th>                        
                                <th width="100" class="opration">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                           <#list resultList as item>
	        				<tr>
	        			        <td>${item.fileDate}</td>
		        				<td>${item.fileNo}</td>
		        				<td>${item.bizTypeStr}</td>
		        				<td>${item.fileStatusStr}</td>
		        				<td>
		        				<#if item.fileStatus == '103'>
		        				<a href="javascript:;" class="op-btn" id="submitExport" onclick="submitExport('${item.failFileAddress}');">下载错误信息提示文件</a>
		        				</#if> 
		        				</td>
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
             
             <input type="text" name="uploadUrl" value="/preOrderCollAndSettle/preFileSubmit.htm" id="uploadUrlHid" class="hide"></input>      
        </div>
        
    </div>
    </form>
    
    <form method='post' action="${ctx}/preOrderCollAndSettle/downloadErrorCsv.htm" name="f1">
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
	    	formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/query.htm");
	    	formObj.submit();
    	}      	
    	
    	//提交导出
		function submitExport(failFileAddress){
	    	$.ajax({
                url: sysconfig.ctx + '/authStatus',
                crossDomain: true,
                cache: false,
                dataType: 'jsonp',
                success: function (data) {
                    if (data.hasLogin) {
                        var formObj = $("form[name='f1']");
                        formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/downloadErrorCsv.htm?failFileAddress="+failFileAddress);
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
    	    	formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/query.htm");
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
    	    	formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/query.htm");
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
    	    		formObj.attr("action", sysconfig.ctx+"/preOrderCollAndSettle/query.htm");
    	    		formObj.submit(); 
            	}	           
            }         
    	} 
	</script>
	
	<!-- 明细文件上传 -->
    <script type="text/html" id="upload-tpl">
    	<form method='post' id="f4" name="f4">     	  
        <div class="pop-upload upload-files">
        	<div id="dialogSelect" class="jr-select" style="min-height: 30px;margin-bottom: 10px;">
        		<label>业务类型：</label>
                <i class="select-input-arrow-icon arrow-icon-closed" style="left: 348px;"></i>
                <input type="text" readonly name="bizType" id="bizType" class="select-input input_text empty" key="" style="height: 28px;margin-left: 0;width: 268px;">
                <ul class="select-box" style="left: 87px;">
                	<#if chargeBizType??>
                	<#list chargeBizType as unit>
                	<#if unit!='全部'>
                    <li class="select-item">
                        <a class="sel-val" id="sel-val-currency" href="javascript:void(0);" key="1" value=${unit}>${unit}</a>
                    </li>
                    </#if>
                    </#list>
                    </#if>                                
                </ul>
            </div>
            <div>
                <label>文件上传：</label>                
                <input type="text" class="input file-path" readonly="true" name="filePath" value="" id="file" style="height: 20px;"> 
				<input type="text" name="fileAddress" value="" class="input file-path hide"></input>                  
             	<div class="file-operation">
                    <a class="up" href="javascript:;">选择文件
                    <form id="uploadForm" enctype="multipart/form-data">  
                    	<input type="file" id="file-btn" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"></a>
                    </form> 
                  
                </div>
            	<div class="clear"></div>
            </div>
            <div style="text-align: center;">
              	<a class="download" href="${ctx}/template/file/trade-csa-new.xlsx">模版下载(货物贸易)</a>
                <a class="download" href="${ctx}/template/file/logi-csa-new.xlsx">模版下载(国际物流)</a>
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
  
    <script type="text/javascript" data-main="${ctx}/scripts/eppscbp/sjh-pre-order-main.js" src="${ctx}/scripts/lib/require/require.min.js"></script>
    
    
    
    
    