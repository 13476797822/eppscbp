/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: UnifiedReceiptServiceImpl.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:54:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.CollAndSettleApplyDto;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderExRateRefreshDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("unifiedReceiptService")
public class UnifiedReceiptServiceImpl implements UnifiedReceiptService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnifiedReceiptServiceImpl.class);

    // 单笔申请接口方法名
    private static final String ORDER_APPLY = "apply";
    // 单笔申请接口方法名
    private static final String BATCH_APPLY = "batchApply";
    // 支付接口方法名
    private static final String ORDER_DOORDEROPERATION = "doOrderOperation";
    // 汇率刷新接口
    private static final String ORDER_EX_RATE_REFRESH = "orderExRateRefresh";
    // 收结汇申请
    public static final String COLL_SETTLE_APPLY = "applyExColleSettle";
    // 收结汇订单关闭
    public static final String COLL_SETTLE_CLOSE = "closeExColleSettle";
    // 明细校验状态查询
    public static final String QUERY_DETAIL_FLAG = "queryDetailFlag";

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService#submiteSettleOrder(com.suning.epp.eppscbp.dto. OrderApplyAcquireDto)
     */
    @Override
    public ApiResDto<OrderlAcquireResponseDto> submiteSettleOrder(OrderApplyAcquireDto orderApplyAcquireDto) {
        ApiResDto<OrderlAcquireResponseDto> apiResDto = new ApiResDto<OrderlAcquireResponseDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = BeanConverterUtil.beanToMap(orderApplyAcquireDto);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("单笔人民币结算申请的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.ORDER_APPLY, ORDER_APPLY, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("单笔人民币结算申请返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
            apiResDto.setResponseCode(responseCode);
            if (!CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                // 提交订单失败
                LOGGER.info("单笔人民币结算申请未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            } else {
                // 提交订单成功
                LOGGER.info("单笔人民币结算申请返回成功");
                apiResDto.setResponseMsg("");
                OrderlAcquireResponseDto orderlAcquireResponseDto = JSON.parseObject(context, OrderlAcquireResponseDto.class);
                Timestamp now = new Timestamp(new Date().getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp time = Timestamp.valueOf(formatter.format(orderlAcquireResponseDto.getRateExpiredTime()));
                orderlAcquireResponseDto.setRateExpiredTimeS(time.getTime() - now.getTime());
                apiResDto.setResult(orderlAcquireResponseDto);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService#confirmPayment(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<String> doOrderOperation(OrderOperationDto orderOperationDto, String operatingType) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            LOGGER.info("订单操作:{},订单信息:{}", operatingType, orderOperationDto);
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("businessNo", orderOperationDto.getBusinessNo());
            request.put("payerAccount", orderOperationDto.getPayerAccount());
            request.put("operatingType", operatingType);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("订单操作申请的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.ORDER_DOORDEROPERATION, ORDER_DOORDEROPERATION, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("订单操作申请返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMsg = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
            } else if (CommonConstant.ORDER_PAY_EXCEPTION.equals(responseCode)) {
                apiResDto.setResponseMsg("订单支付中，请稍后查询。");
            } else if (response != null) {
                apiResDto.setResponseMsg(responseMsg);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService#refreshRate(java.lang.String)
     */
    @Override
    public OrderlAcquireResponseDto refreshRate(OrderExRateRefreshDto orderExRateRefreshDto) {
        try {
            // 调用参数
            LOGGER.info("汇率刷新的参数:{}", orderExRateRefreshDto.toString());
            Map<String, Object> request = BeanConverterUtil.beanToMap(orderExRateRefreshDto);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.ORDER_EX_RATE_REFRESH, ORDER_EX_RATE_REFRESH, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("汇率刷新返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
            if (response == null || !CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                LOGGER.error("刷新业务单号{}汇率未成功,原因:{}", orderExRateRefreshDto.getBusinessNo(), responseMessage);
                return null;
            }
            LOGGER.info("刷新汇率返回成功");
            OrderlAcquireResponseDto orderlAcquireResponseDto = JSON.parseObject(context, OrderlAcquireResponseDto.class);
            Timestamp now = new Timestamp(new Date().getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp time = Timestamp.valueOf(formatter.format(orderlAcquireResponseDto.getRateExpiredTime()));
            orderlAcquireResponseDto.setRateExpiredTimeS(time.getTime() - now.getTime());
            return orderlAcquireResponseDto;
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService#batchSubmiteOrder(com.suning.epp.eppscbp.dto.res. OrderApplyAcquireDto)
     */
    @Override
    public ApiResDto<OrderlAcquireResponseDto> batchSubmiteOrder(OrderApplyAcquireDto orderApplyAcquireDto) {
        ApiResDto<OrderlAcquireResponseDto> apiResDto = new ApiResDto<OrderlAcquireResponseDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = BeanConverterUtil.beanToMap(orderApplyAcquireDto);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("批量购付汇申请的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.BATCH_APPLY, BATCH_APPLY, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("批量购付汇申请返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
            apiResDto.setResponseCode(responseCode);
            if (!CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                // 提交订单失败
                LOGGER.info("留学缴费单笔购付汇申请未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            } else {
                // 提交订单成功
                LOGGER.info("留学缴费单笔购付汇申请返回成功");
                apiResDto.setResponseMsg("");
                OrderlAcquireResponseDto orderlAcquireResponseDto = JSON.parseObject(context, OrderlAcquireResponseDto.class);
                Timestamp now = new Timestamp(new Date().getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp time = Timestamp.valueOf(formatter.format(orderlAcquireResponseDto.getRateExpiredTime()));
                orderlAcquireResponseDto.setRateExpiredTimeS(time.getTime() - now.getTime());
                apiResDto.setResult(orderlAcquireResponseDto);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService#submitCollSettleOrder(com.suning.epp.eppscbp.dto.req.CollAndSettleApplyDto)
     */
    @Override
    public ApiResDto<CollAndSettleResDto> submitCollSettleOrder(CollAndSettleApplyDto collAndSettleApplyDto) {
        ApiResDto<CollAndSettleResDto> apiResDto = new ApiResDto<CollAndSettleResDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = BeanConverterUtil.beanToMap(collAndSettleApplyDto);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("收结汇申请请求参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.COLL_SETTLE_APPLY, COLL_SETTLE_APPLY, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("收结汇申请返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
            apiResDto.setResponseCode(StringUtil.isEmpty(responseCode) ? CommonConstant.SYSTEM_ERROR_CODE : responseCode);
            if (!CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                // 提交订单失败
                LOGGER.info("收结汇申请未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(StringUtil.isEmpty(responseMessage) ? CommonConstant.SYSTEM_ERROR_MES : responseMessage);
            } else {
                // 提交订单成功
                LOGGER.info("收结汇申请返回成功");
                apiResDto.setResponseMsg("");
                CollAndSettleResDto collAndSettleResDto = JSON.parseObject(context, CollAndSettleResDto.class);

                apiResDto.setResult(collAndSettleResDto);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService#closeCollSettOrder(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<String> closeCollSettOrder(String payerAccount, String orderNo) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("payerAccount", payerAccount);
            request.put("orderNo", orderNo);
            request.put("operation", "01");
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("收结汇订单关闭申请开始,requestStr:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.COLL_SETTLE_APPLY, COLL_SETTLE_CLOSE, new Object[] { request });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("收结汇订单关闭申请返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMsg = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
            } else {
                apiResDto.setResponseMsg(responseMsg);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /* (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService#queryDetailFlag(com.suning.epp.eppscbp.dto.req.OrderOperationDto, java.lang.String)
     */
    @Override
    public ApiResDto<String> queryDetailFlag(OrderOperationDto orderOperationDto) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            LOGGER.info("查询明细校验状态:{}", orderOperationDto);
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("businessNo", orderOperationDto.getBusinessNo());
            request.put("payerAccount", orderOperationDto.getPayerAccount());
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("查询明细校验状态结果:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.ORDER_APPLY, QUERY_DETAIL_FLAG, request);
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("查询明细校验状态返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            apiResDto.setResponseCode(responseCode);
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }
    
    
}
