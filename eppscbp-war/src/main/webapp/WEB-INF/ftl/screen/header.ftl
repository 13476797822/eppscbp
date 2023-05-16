<div class="platform-header">
    <div class="platform-header-inner">
        <div class="platform-logo">
            <div class="platform-name">境外收款平台</div>
        </div>
        <div class="platform-nav">
            <ul class="platform-nav-list">
            	<#if Request["isPlatform"]=='N'>
                <li class="nav-item <#if request.requestUri?starts_with("/eppscbp/cpStoreHandle/")> on </#if>">
                	<a href="${ctx}/cpStoreHandle/cpStoreHandleQuery/init.htm">店铺管理</a>
                </li>
                </#if>
                
                <#if Request["isPlatform"]=='Y'>
                <li class="nav-item <#if request.requestUri?starts_with("/eppscbp/cpWithdrawApply/")> on </#if>">
                	<a href="${ctx}/cpWithdrawApply/cpWithdrawApplyInit/init.htm">提现申请</a>
                </li>
                </#if>
                
                <li class="nav-item <#if request.requestUri?starts_with("/eppscbp/cpBatchPayment/")> on </#if>">
                	<a href="${ctx}/cpBatchPayment/cpBatchPaymentInit/init.htm">资金下发</a>
                </li>
                
                <li class="nav-item <#if request.requestUri?starts_with("/eppscbp/cpBatchPaymentReview/")> on </#if>">
                	<a href="${ctx}/cpBatchPaymentReview/cpBatchPaymentReview/init.htm">资金下发复核管理</a>
                </li>
                
                <li class="nav-item <#if request.requestUri?starts_with("/eppscbp/cpWithdrawDetail/")> on </#if>">
                	<a href="${ctx}/cpWithdrawDetail/cpWithdrawDetailQuery/init.htm">提现明细</a>
                </li>
                
                <li class="nav-item <#if request.requestUri?starts_with("/eppscbp/cpBatchPaymentDetail/")> on </#if>">
                	<a href="${ctx}/cpBatchPaymentDetail/cpBatchPaymentDetailQuery/init.htm">批付明细</a>
                </li>
                
                <li class="nav-item <#if request.requestUri?starts_with("/eppscbp/cpTranInOutDetail/")> on </#if>">
                	<a href="${ctx}/cpTranInOutDetail/cpTranInOutDetailQuery/init.htm">出入账明细</a>
                </li>
                
                <li class="nav-item <#if request.requestUri?starts_with("/eppscbp/cpWithdrawAccount/")> on </#if>">
                	<a href="${ctx}/cpWithdrawAccount/init.htm">提现账号</a>
                </li>
                
            </ul>
        </div>
    </div>
</div>
