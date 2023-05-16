/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: ShopManageServiceImpl.java
 * Author:   88412423
 * Date:     2019年5月8日 14:32:49
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.CapitalGrantDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CapitalGrantResDto;
import com.suning.epp.eppscbp.rsf.service.CapitalGrantService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈店铺管理service〉
 *
 * @author 88412423
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("capitalGrantService")
public class CapitalGrantServiceImpl implements CapitalGrantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CapitalGrantServiceImpl.class);

    // 资金批付查询方法
    private static final String CAPITAL_GRANT = "balanceQuery";
    // 待批付查询
    private static final String BATCH_PAYMENT_QUERY = "batchPaymentQuery";
    // 批付申请
    private static final String BATCH_PAYMENT_ADUIT = "ordersAudit";
    // 批量导入批付明细
    public static final String IMPORT_BATCH_PAYMENT_DETAILS = "importBatchPaymentDetails";

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    @Override
    public ApiResDto<String> getCapitalGrant(CapitalGrantDto capitalGrantDto) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("收款平台商户查询可批付金额触发rsf接口,入参:{}", capitalGrantDto);
            Map<String, Object> request = BeanConverterUtil.beanToMap(capitalGrantDto);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, CAPITAL_GRANT, new Object[] { request });
            LOGGER.info("收款平台商户查询可批付金额返回参数,outputParam:{}", response);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            final String amount = MapUtils.getString(response, "amount");
            apiResDto.setResponseCode(responseCode);
            if (!CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                // 提交订单失败
                LOGGER.info("查询资金批付未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            } else {
                // 提交订单成功
                LOGGER.info("查询可批付金额返回成功");
                apiResDto.setResponseMsg("");
                apiResDto.setResult(amount);
            }
        } catch (Exception e) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(e));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<List<CapitalGrantResDto>> pendingPaymentQuery(CapitalGrantDto capitalGrantDto) {
        ApiResDto<List<CapitalGrantResDto>> apiResDto = new ApiResDto<List<CapitalGrantResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("待批付查询触发rsf接口,入参:{}", capitalGrantDto);
            Map<String, Object> request = BeanConverterUtil.beanToMap(capitalGrantDto);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.PENDING_PAYMENT_QUERY, BATCH_PAYMENT_QUERY, new Object[] { request });
            LOGGER.info("待批付查询返回参数,outputParam:{}", response);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                List<CapitalGrantResDto> resultList = JSONObject.parseArray(response.get(EPPSCBPConstants.CONTEXT), CapitalGrantResDto.class);
                apiResDto.setResult(resultList);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("单笔查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception e) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(e));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<String> batchPayment(OrdersAuditRequestDto requestDto) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            OrdersAuditRequestDto request = new OrdersAuditRequestDto();
            request.setPayerAccount(requestDto.getPayerAccount());
            request.setPlatformCode("ccp");

            // 转换成MAP
            Map<String, Object> reqMap = BeanConverterUtil.beanToMap(request);
            reqMap.put("detailList", JSONObject.toJSONString(requestDto.getDetails()));
            LOGGER.info("请求申请批付的参数:{}", reqMap);

            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, BATCH_PAYMENT_ADUIT, new Object[] { reqMap });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("批付出款返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            apiResDto.setResponseMsg(responseMessage);
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<String> importBatchPaymentDetails(CapitalGrantDto capitalGrantDto) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> request = BeanConverterUtil.beanToMap(capitalGrantDto);
            LOGGER.info("批量导入批付明细请求入参{}", request);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.IMPORT_BATCH_PAYMENT_DETAILS, IMPORT_BATCH_PAYMENT_DETAILS, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("批量导入批付返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            apiResDto.setResponseMsg(responseMessage);
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }
}
