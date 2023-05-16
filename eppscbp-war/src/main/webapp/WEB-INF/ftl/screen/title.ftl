<ul class="three-menu">
            <li class="three-menu-item single-transfer three-menu-item-first <#if request.requestUri?starts_with("/eppscbp/goodsTrade")>  hover </#if>">
                <a href="${ctx}/goodsTrade/goodsTradeInit/init.htm"><i class="icon jinkou_icon"></i>进口贸易结算</a>
            </li>
            <li class="three-menu-item single-transfer  <#if request.requestUri?starts_with("/eppscbp/overseasPay")>  hover </#if>">
                <a href="${ctx}/overseasPay/overseasPayInit/init.htm"><i class="icon liuxue_icon"></i>留学教育缴费</a>
            </li>
            <li class="three-menu-item single-transfer  <#if request.requestUri?starts_with("/eppscbp/collAndSettle")> hover </#if>">
                <a href="${ctx}/collAndSettle/collAndSettle/init.htm"><i class="icon chukou_icon"></i>出口贸易结算</a>
            </li>
            <li class="three-menu-item single-transfer  <#if request.requestUri?starts_with("/eppscbp/logisticsSettlement")>  hover </#if>">
                <a href="${ctx}/logisticsSettlement/logisticsSettlement/init.htm"><i class="icon wuliu_icon"></i>国际物流结算</a>
            </li>
            <li class="three-menu-item single-transfer <#if request.requestUri?starts_with("/eppscbp/preOrderCollAndSettle/preFile")>  hover </#if>">
                <a href="${ctx}/preOrderCollAndSettle/preFile/init.htm"><i class="icon new_chukou_icon"></i>收结汇资源库</a>
            </li>
            <li class="three-menu-item single-transfer <#if request.requestUri?starts_with("/eppscbp/preOrderCollAndSettle/preApply")>  hover </#if>">
                <a href="${ctx}/preOrderCollAndSettle/preApply/init.htm"><i class="icon chukou_icon"></i>结汇付款</a>
            </li>
            <li class="three-menu-item single-transfer <#if request.requestUri?starts_with("/eppscbp/singleQuery")>  hover </#if>">
                <a href="${ctx}/singleQuery/singleOrderQuery/init.htm"><i class="icon danbi_icon"></i>单笔交易订单查询</a>
            </li>
            <li class="three-menu-item single-transfer <#if request.requestUri?starts_with("/eppscbp/batchPaymentQuery")>  hover </#if>">
                <a href="${ctx}/batchPaymentQuery/batchPaymentQuery/init.htm"><i class="icon danbi_icon"></i>批付明细查询</a>
            </li>
            <li class="three-menu-item single-transfer <#if request.requestUri?starts_with("/eppscbp/batchPaymentReview")>  hover </#if>">
                <a href="${ctx}/batchPaymentReview/batchPaymentReview/init.htm"><i class="icon danbi_icon"></i>批付复核管理</a>
            </li>
            <li class="three-menu-item single-transfer <#if request.requestUri?starts_with("/eppscbp/batchQuery")>  hover </#if>">
                <a href="${ctx}/batchQuery/batchOrderQuery/init.htm"><i class="icon piliang_icon"></i>批量交易订单查询</a>
            </li>
            <li class="three-menu-item single-transfer <#if request.requestUri?starts_with("/eppscbp/merchantHandle")>  hover </#if>">
                <a href="${ctx}/merchantHandle/init.htm"><i class="icon shoukuan_icon"></i>收付款账号管理</a>
            </li>
            <li class="three-menu-item single-transfer three-menu-item-last <#if request.requestUri?starts_with("/eppscbp/rateQuery")>  hover </#if>">
                <a href="${ctx}/rateQuery/rateQuery/init.htm?channelId=11"><i class="icon huilv_icon"></i>汇率牌价查询</a>
            </li>
        </ul>