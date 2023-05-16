/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: GeneralRsfService.java
 * Author:   17061545
 * Date:     2018年3月22日 上午10:40:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.rsf.RSFException;
import com.suning.rsf.consumer.ExceedActiveLimitException;
import com.suning.rsf.consumer.ExceedRetryLimitException;
import com.suning.rsf.consumer.ServiceAgent;
import com.suning.rsf.consumer.ServiceLocator;
import com.suning.rsf.consumer.TimeoutException;

/**
 * 〈RSF接口公共调用类〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("generalRsfService")
public class GeneralRsfService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralRsfService.class);

    private static final Map<String, ServiceAgent> API_CODES = new HashMap<String, ServiceAgent>();

    static {
        API_CODES.put(ApiCodeConstant.VALIDATE_PAYMENTPASSWORD, ServiceLocator.getServiceAgent("com.suning.epps.mps.client.rsf.EppPaymentPasswordRsfServer", "eppPaymentPasswordRsfServer", false));
        API_CODES.put(ApiCodeConstant.QUERY_SECRETKEY, ServiceLocator.getServiceAgent("com.suning.epps.mps.client.rsf.EppSecretKeyRsfServer", "eppSecretKeyRsfServer", false));
        API_CODES.put(ApiCodeConstant.QUERY_MERCHANT_AUTH, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CtMerchantAuthApi", "CtMerchantAuthApi", false));
        API_CODES.put(ApiCodeConstant.ORDER_APPLY, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CtUniversalAcquireApi", "CtUniversalAcquireApi", false));
        API_CODES.put(ApiCodeConstant.ORDER_DOORDEROPERATION, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CtReceiptOrderOperationApi", "CtReceiptOrderOperationApi", false));
        API_CODES.put(ApiCodeConstant.ORDER_EX_RATE_REFRESH, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CbpOrderExRateRefreshApi", "CbpOrderExRateRefreshApiImpl", false));
        API_CODES.put(ApiCodeConstant.QUERY_AMT, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CtFullAmtApi", "CtFullAmtApi", false));
        API_CODES.put(ApiCodeConstant.ORDER_QUERY, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CbpOrderQueryApi", "CbpOrderQueryApiImpl", false));
        API_CODES.put(ApiCodeConstant.MERCHANT_MANAGE, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.MerchantInfoManageApi", "MerchantInfoManageApi", false));
        API_CODES.put(ApiCodeConstant.REFER_RATE_QUERY, ServiceLocator.getServiceAgent("com.suning.cbcss.api.exchangerate.ExchangeRateQueryApi", "ExchangeRateQueryApi", false));
        API_CODES.put(ApiCodeConstant.BATCH_APPLY, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CtUniversalAcquireApi", "CtUniversalAcquireApi", false));
        API_CODES.put(ApiCodeConstant.SUPERVISE_UPLOAD, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.SuperviseUploadApi", "SuperviseUploadApi", false));
        API_CODES.put(ApiCodeConstant.COLL_SETTLE_APPLY, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CtExCollectionSettlementApi", "CtExCollectionSettlementApiImpl", false));
        API_CODES.put(ApiCodeConstant.DOME_MERCHANT_MANAGE, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.DomesticPayeeInfoManageApi", "DomesticPayeeInfoManageApi", false));
        API_CODES.put(ApiCodeConstant.COLL_SETT_ORDER_QUERY, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CbpCollAndSettleOrderQueryApi", "CbpCollAndSettleOrderQueryApiImpl", false));
        API_CODES.put(ApiCodeConstant.SAFE_RATE_QUERY, ServiceLocator.getServiceAgent("com.suning.cbcss.api.ctsaferate.CTSafeRateQueryApi", "CTSafeRateQueryApi", false));
        API_CODES.put(ApiCodeConstant.ECS_SUPERVISE_UPLOAD, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.EcsSuperviseUploadApi", "EcsSuperviseUploadApiImpl", false));
        API_CODES.put(ApiCodeConstant.BATCH_PAYMENT, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.BatchPaymentApi", "BatchPaymentApiImpl", false));
        API_CODES.put(ApiCodeConstant.STORE_MANAGE, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CpStoreInfoManageApi", "CpStoreInfoManageApi", false));
        API_CODES.put(ApiCodeConstant.STORE_CASH_WITHDRAW, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.CollectionPlatformApi", "CollectionPlatformApiImpl", false));
        API_CODES.put(ApiCodeConstant.HOME_QUERY, ServiceLocator.getServiceAgent("com.suning.oca.api.HomeApi", "HomeApiImpl", false));
        API_CODES.put(ApiCodeConstant.TRADE_RECEIPT_ORDER_QUERY, ServiceLocator.getServiceAgent("com.suning.oca.api.TradeReceiptOrderApi", "TradeReceiptOrderApiImpl", false));
        API_CODES.put(ApiCodeConstant.REJECT_ORDER_QUERY, ServiceLocator.getServiceAgent("com.suning.oca.api.RejectOrderApi", "RejectOrderApiImpl", false));
        API_CODES.put(ApiCodeConstant.REFUND_ORDER_QUERY, ServiceLocator.getServiceAgent("com.suning.oca.api.RefundOrderApi", "RefundOrderApiImpl", false));
        API_CODES.put(ApiCodeConstant.LOGISTICS_INFO, ServiceLocator.getServiceAgent("com.suning.oca.api.LogisticsInfoAPi", "LogisticsInfoAPiImpl", false));
        API_CODES.put(ApiCodeConstant.BATCH_QUERY_CHANNEL, ServiceLocator.getServiceAgent("com.suning.oca.api.PayOrderApi", "PayOrderApiImpl", false));
        API_CODES.put(ApiCodeConstant.TRADE_INFO_QUERY, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.TradeInfoAggregationApi", "TradeInfoAggregationApi", false));
        API_CODES.put(ApiCodeConstant.TRADE_INFO_DZHDAPI, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.DzhdApi", "DzhdApiImpl", false));
        API_CODES.put(ApiCodeConstant.QUERY_OPERATOR, ServiceLocator.getServiceAgent("com.suning.epps.sfbmacs.server.facade.OperatorManagerFacade", "queryAllOperatorListByUserNo", false));
        API_CODES.put(ApiCodeConstant.PRE_ORDER, ServiceLocator.getServiceAgent("com.suning.epp.cbs.api.cbp.PreOrderApi", "PreOrderApiImpl", false));
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param apiCode
     * @param method
     * @param result
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public T invoke(String apiCode, String method, Object... request) {
        try {
            ServiceAgent serviceAgent = API_CODES.get(apiCode);
            LOGGER.info("开始调用RSF接口调用,contract:{},implcode:{},method:{}", serviceAgent.getContract(), serviceAgent.getImplCode(), method);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("调用参数:{}", JSON.toJSONString(request));
            }
            @SuppressWarnings("unchecked")
            T reObj = (T) serviceAgent.invoke(method, request, getArgTypes(request));
            return reObj;
        } catch (ExceedActiveLimitException ex) {
            LOGGER.error("超出消费方流控阀值:{}", ExceptionUtils.getStackTrace(ex));
        } catch (ExceedRetryLimitException ex) {
            LOGGER.error("超出重试次数，调用失败，请求异常明细:{}", ExceptionUtils.getStackTrace(ex));
            if (ex.exceedConcurrencyThreshold()) {
                LOGGER.error("超出服务方流控阀值:{}", ExceptionUtils.getStackTrace(ex));
            }
        } catch (TimeoutException ex) {
            LOGGER.error("调用超时:{}", ExceptionUtils.getStackTrace(ex));
        } catch (RSFException ex) {
            LOGGER.error("其他框架异常:{}", ExceptionUtils.getStackTrace(ex));
        } catch (Exception ex) {
            LOGGER.error("未知异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param args
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private Class[] getArgTypes(Object[] args) {
        if (args == null) {
            return null;
        }
        Class[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                throw new IllegalArgumentException("Argument can not be null.");
            }
            if(arg instanceof Map) {
                argTypes[i] = Map.class;
            } else {
                argTypes[i] = arg.getClass();
            }
        }
        return argTypes;
    }
}
