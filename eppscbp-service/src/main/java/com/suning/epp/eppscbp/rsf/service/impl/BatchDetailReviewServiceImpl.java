/*
 * Copyright (C), 2002-2021, 苏宁易购电子商务有限公司
 * FileName: BatchDetailReviewServiceImpl.java
 * Author:   17033387
 * Date:     2021年6月22日 下午6:04:56
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
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.BatchPaymentReviewDetailDto;
import com.suning.epp.eppscbp.dto.req.BatchPaymentReviewQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentOrderReviewData;
import com.suning.epp.eppscbp.dto.res.BatchPaymentReviewDetail;
import com.suning.epp.eppscbp.rsf.service.BatchDetailReviewService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class BatchDetailReviewServiceImpl implements BatchDetailReviewService {
    
    /**
    *Logging mechanism
    */
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchDetailReviewServiceImpl.class);
    
    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;
    
    private static final String ALL = "全部";
    
    private static final String ORDERS_QUERY = "ordersReviewQuery";
    
    private static final String DETAILS_QUERY = "ordersReviewDetail";
    
    private static final String CONFIRM_PAY = "confirmBatchPayment";
    
    private static final String VALIDATE = "batchPaymentValidate";
    
    private static final String NOT_PASS = "batchPaymentNotPass";

    @Override
    public ApiResDto<List<BatchPaymentOrderReviewData>> ordersReviewQuery(BatchPaymentReviewQueryReqDto reqDto) {
        ApiResDto<List<BatchPaymentOrderReviewData>> apiResDto = new ApiResDto<List<BatchPaymentOrderReviewData>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始批付订单查询入参预处理");
            reqDto.setReviewStatus(ALL.equals(reqDto.getReviewStatus()) ? null : reqDto.getReviewStatus());
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            LOGGER.info("查询批付订单入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, ORDERS_QUERY, inputParam);
            LOGGER.info("查询批付订单返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                List<BatchPaymentOrderReviewData> resultList = JSON.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), BatchPaymentOrderReviewData.class);
                apiResDto.setResult(resultList);
                DalPage pageInfo = JSON.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setPage(pageInfo);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("批付订单查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<List<BatchPaymentReviewDetail>> ordersReviewDetail(BatchPaymentReviewDetailDto reqDto) {
        ApiResDto<List<BatchPaymentReviewDetail>> apiResDto = new ApiResDto<List<BatchPaymentReviewDetail>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            LOGGER.info("查询批付明细入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, DETAILS_QUERY, inputParam);
            LOGGER.info("查询批付明细返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                List<BatchPaymentReviewDetail> resultList = JSON.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), BatchPaymentReviewDetail.class);
                apiResDto.setResult(resultList);
                DalPage pageInfo = JSON.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setPage(pageInfo);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("批付明细查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<Boolean> confirmBatchPayment(BatchPaymentReviewDetailDto reqDto) {
        ApiResDto<Boolean> apiResDto = new ApiResDto<Boolean>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            LOGGER.info("确认支付入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, CONFIRM_PAY, inputParam);
            LOGGER.info("确认支付返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                apiResDto.setResult(true);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("确认支付未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<List<String>> batchPaymentValidate(BatchPaymentReviewDetailDto reqDto) {
        ApiResDto<List<String>> apiResDto = new ApiResDto<List<String>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            LOGGER.info("批付复核状态校验入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, VALIDATE, inputParam);
            LOGGER.info("批付复核状态校验返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                apiResDto.setResult(null);
            } else {
                // 校验不通过
                LOGGER.info("批付复核状态校验不通过状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
                List<String> resultList = JSON.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), String.class);
                apiResDto.setResult(resultList);
                DalPage pageInfo = JSON.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setPage(pageInfo);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    @Override
    public ApiResDto<Boolean> batchPaymentNotPass(BatchPaymentReviewDetailDto reqDto) {
        ApiResDto<Boolean> apiResDto = new ApiResDto<Boolean>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            LOGGER.info("批付复核不通过入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, NOT_PASS, inputParam);
            LOGGER.info("批付复核不通过返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                apiResDto.setResult(true);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("批付复核不通过未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

}
