<meta charset="UTF-8">
    <title>苏汇通</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chorme=1">
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <link rel="shortcut icon" href="http://www.suning.com/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/style/css/shop-manage.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/header.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/style/css/placeholder.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/cpBatchPaymentSetting.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css/dialog.css">
    <#include "/screen/header.ftl" />
    <div class="batch-payment">
        <form method='post' method="post" id="f0" name="f0">
            <div class="content" style="width: 1125px;">
                <div class="searchbox">
                    <div class="searchline">
                        <div class="searchitem">
                            <label>批次号：</label>
                            <input type="text" name="batchNo">
                        </div>
                        <div class="searchitem" style="margin-left: 84px">
                            <label>复核状态：</label>
                            <div class="jr-select">
                                <i class="select-input-arrow-icon arrow-icon-closed"></i>
                                <input type="text" readonly name="reviewStatus" class="select-input input_text empty" value="${(requestDto.reviewStatus)!'全部'}" key="" id="status">
                                <ul class="select-box">
                                    <li class="select-item">
                                        <span class="checkbox <#if requestDto.reviewStatus="">choosed</#if>"></span>
                                        <a class="sel-val" href="javascript:void(0);">全部</a>
                                    </li>
                                    <li class="select-item">
                                        <span class="checkbox <#if requestDto.reviewStatus=""||requestDto.reviewStatus?contains("待复核")>choosed</#if>"></span>
                                        <a class="sel-val" href="javascript:void(0);">待复核</a>
                                    </li>
                                    <li class="select-item">
                                        <span class="checkbox <#if requestDto.reviewStatus=""||requestDto.reviewStatus?contains("复核通过")>choosed</#if>"></span>
                                        <a class="sel-val" href="javascript:void(0);">复核通过</a>
                                    </li>
                                    <li class="select-item">
                                        <span class="checkbox <#if requestDto.reviewStatus=""||requestDto.reviewStatus?contains("复核不通过")>choosed</#if>"></span>
                                        <a class="sel-val" href="javascript:void(0);">复核不通过</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="searchline">
                        <div class="searchitem date">
                            <label>创建时间：</label>
                            <span class="condition-input" style="padding:1px 0;">
                                <input type="text" class="input_mod input_time" id="date1" name="createStartTime" readonly="true" value="${(requestDto.createStartTime)!''}" />
                                <em>至</em>
                                <input type="text" class="input_mod input_time" id="date2" name="createEndTime" readonly="true" value="${(requestDto.createEndTime)!''}" />
                            </span>
                        </div>
                        <div class="searchitem" style="margin-left: 84px">
                            <label></label>
                            <div class="btn search">查询</div>
                            <div class="btn resetbtn">重置</div>
                        </div>
                    </div>
                </div>
                <div class="menus">
                    <div class="btn menu1">
                        <img src="" alt="">
                        批量复核不通过
                    </div>
                    <div class="btn menu2">
                        <img src="" alt="">
                        批量复核通过
                    </div>
                    <div class="btn menu3">
                        <img src="${ctx}/style/images/checkout.png" alt="">
                        导出
                    </div>
                </div>
                <div class="table-box" id="table-box-result">
                    <#if errMessage??>
                        <p class="no-result"><i></i>${errMessage}</p>
                    </#if>


					<#if resultList??>
                        <div class="result-table">
                            <table cellpadding = "0" cellspacing = "0">
                                <thead>
                                <tr>
                                    <th width="40">
                                        <div class="checkicon checkall"></div>
                                    </th>
                                    <th width="120">创建时间</th>
                                    <th width="120">复核时间</th>
                                    <th width="200">批次号</th>
                                    <th width="200">批次总金额（人民币）</th>
                                    <th width="120">批次总笔数</th>
                                    <th width="150">复核状态</th>
                                    <th width="300">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                	<#list resultList as item>
				        				<tr>
				        					<td width="40"><div class="checkicon"></div></td>
				        					<td width="120">${item.createTimeStr}</td>
					        				<td width="120">${item.reviewTimeStr}</td>
			                                <td width="200" class="number">
	                                            <a href="javascript:void(0)">${item.expendNo}</a>
	                                        </td>
			                                <td width="200">${item.expendNoAmountStr}</td>
			                                <td width="120">${item.batchPaymentCount}</td>
			                                <td width="150">
	                                            <span <#if item.reviewStatus == "10">style="color: #13CE66"
	                                            	  <#elseif item.reviewStatus == "11">style="color: #FF3535"
	                                            	  </#if>>
	                                            	${item.reviewStatusStr}
	                                            </span>
                                            </td>
			                                <td width="300">
			                                	<#if item.reviewStatus == "00">
                                            	<a href="javascript:void(0)" class="checkpass">复核通过</a>
                                            	<em></em>
                                            	<a href="javascript:void(0)" class="checkunpass">复核不通过</a>
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
                            <a class="fl goto border" href="javascript:;">跳转</a>
                        </div>
                        <input style="display:none" id="currentPage" name="currentPage" value="${page.currentPage}"></input>
                        <input style="display:none" id="pageSize" value="${page.pages}"></input>
                        <input type="text" name="uploadUrl" value="/singleQuery/singleOrderQuery/tradeSubmit.htm" id="uploadUrlHid" class="hide"></input>
                    </div>
                </#if>
            </div>
        </form>
    </div>
    <script type="text/html" id="purchase-pay-tpl">
        <div class="pay-box">
            <p>应付总金额（人民币）：<span id="money"></span></p>
            <p>应付总笔数：<span id="totalcount"></span></p>
            <p style="margin-bottom:40px">
                <input type="password" id="password" autocomplete="off" placeholder="请输入支付密码">
                <span class="errormsg"></span>
            </p>
            <div class="btn noclick">
                确认支付
            </div>
        </div>
    </script>
    <script type="text/html" id="error-tpl">
        <div class="errorcontent">
            <p>
                <img src="${ctx}/style/images/icon-提醒.png" alt="">
                {{errMessage}}
            </p>
            <div class="errorbtn">
                知道了
            </div>
        </div>
    </script>
	<script type="text/html" id="unpass-tpl">
	    <div class="errorcontent">
	        <p>
	            <img src="${ctx}/style/images/icon-提醒.png" alt="">
	            确定审核不通过吗？
	        </p>
	        <div class="errorbtns">
	            <div class="btn surebtn">确定</div>
	            <div class="btn cancelbtn">取消</div>
	        </div>
	    </div>
	</script>
	<script type="text/html" id="detail-tpl">
	    <div class="detailcontent">
	        <p class="title">
	            总笔数：<span id="detailtotal">{{number}}</span>；总金额(人民币)：<span id="detailmoney">{{amount}}</span>，请确认无误
	        </p>
	        <table class="detailtable" cellpadding = "0" cellspacing = "0">      
	        </table>
	        <div class="page" id="detailpage">
	            <div class="posi-right" >
	                <span class="fl">每页10条</span>
	                <a class="prev-page page-num border  fl"  href="javascript:;" ><i class="arrow-left"></i>上一页</a>
	                <a class="next-page page-num border  fl" href="javascript:;" >下一页<i class="arrow-right"></i></a>
	                <span class="fl m-left">第{{page.currentPage}}页/共{{page.pages}}页   &nbsp;&nbsp;	向第</span><input type="text" name="" class="fl page-num input-num" id="detailtargetPage" ><span class="fl">页</span>
	                <a class="fl goto border" href="javascript:;">跳转</a>
	            </div>
	            <input style="display:none" id="detailcurrentPage" name="detailcurrentPage" value="{{page.currentPage}}"></input>
	            <input style="display:none" id="detailpageSize" value="{{page.pages}}"></input>
	            <input style="display:none" id="detailexpendNo" value="{{businessNo}}"></input>
	        </div>
	        <div class="btn">关闭</div>
	    </div>
	</script>
	<script type="text/html" id="detail-table-tpl">
		<thead>
	        <tr>
	            <th width="150">境内收款方编号</th>
	            <th width="100">收款方名称</th>
	            <th width="200">收款方账号</th>
	            <th width="120">出款金额(人民币)</th>
	            <th width="120">附言</th>
	        </tr>
	    </thead>
	    <tbody>
	    	{{each resultList as item}}
	            <tr>
	                <td>{{item.payeeMerchantCode}}</td>
	                <td>{{item.payeeMerchantName}}</td>
	                <td>{{item.receiverCardNo}}</td>
	                <td>{{item.amountStr}}</td>
	                <td>
	                    <p title="{{item.orderName}}">
	                        {{item.orderName}}
	                    </p>
	                </td>
	            </tr>
	        {{/each}}
	    </tbody>
	</script>
	<script type="text/html" id="success-tpl">
	    <div class="successcontent">
	        <p>
	            <img src="${ctx}/style/images/toast-success.png" alt="">
	            操作成功
	        </p>
	    </div>
	</script>
	<script type="text/html" id="fail-tpl">
	    <div class="successcontent">
	        <p>
	            <img src="${ctx}/style/images/toast-error.png" alt="">
	            {{errMessage}}
	        </p>
	    </div>
	</script>
	<script type="text/html" id="status-error-tpl">
		<div class="checkUnpass">
			<p>下列选中批次号的复核状态不为“待复核”，无法进行批量操作</p>
			<div class="rows">
				{{errMessage}}
			</div>
			<div class="errorbtn">
				知道了
			</div>
		</div>
	</script>
	<script type="text/javascript">
		var path = "/cpBatchPaymentReview/cpBatchPaymentReview/"
	</script>
<script type="text/javascript" data-main="${ctx}/scripts/eppscbp/kjf-single-ordersetting-search" src="${ctx}/scripts/lib/require/require.min.js"></script>