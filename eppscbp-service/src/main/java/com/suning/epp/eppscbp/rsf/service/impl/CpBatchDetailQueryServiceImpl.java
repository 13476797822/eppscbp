/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpBatchDetailQueryServiceImpl.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
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

import com.alibaba.fastjson.JSONObject;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.CpBatchPaymentStatus;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.OrderType;
import com.suning.epp.eppscbp.common.enums.StorePlatformEnum;
import com.suning.epp.eppscbp.common.enums.WithdrawOrderStatus;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.CpBatchPaymentDetailReqDto;
import com.suning.epp.eppscbp.dto.req.CpTranInOutDetailReqDto;
import com.suning.epp.eppscbp.dto.req.CpWithdrawDetailReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CpBatchPaymentDetailResDto;
import com.suning.epp.eppscbp.dto.res.CpTranInOutDetailResDto;
import com.suning.epp.eppscbp.dto.res.CpWithdrawDetailResDto;
import com.suning.epp.eppscbp.rsf.service.CpBatchDetailQueryService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈明细查询和导出service〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("cpBatchDetailQueryService")
public class CpBatchDetailQueryServiceImpl implements CpBatchDetailQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpBatchDetailQueryServiceImpl.class);

    // 批付明细查询方法
    private static final String ORDERS_QUERY = "ordersQuery";
    // 出入账明细查询方法
    private static final String TRANS_IN_OUT_QUERY = "transInOutQuery";
    // 提现明细查询方法
    private static final String BATCH_WITHDRAW_QUERY = "batchWithdrawQuery";

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    /**
     * 批付明细查询
     */
    @Override
    public ApiResDto<List<CpBatchPaymentDetailResDto>> batchPaymentDetailQuery(CpBatchPaymentDetailReqDto reqDto) {
        ApiResDto<List<CpBatchPaymentDetailResDto>> apiResDto = new ApiResDto<List<CpBatchPaymentDetailResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            CpBatchPaymentDetailReqDto requestParam = new CpBatchPaymentDetailReqDto();
            LOGGER.info("开始批付明细查询入参预处理");
            requestParam.setPayerAccount(reqDto.getPayerAccount());
            requestParam.setPayeeName(StringUtil.isEmpty(reqDto.getPayeeName()) ? null : reqDto.getPayeeName());
            requestParam.setStatus(StringUtil.isEmpty(CpBatchPaymentStatus.getCodeFromDescription(reqDto.getStatus())) ? null : CpBatchPaymentStatus.getCodeFromDescription(reqDto.getStatus()));
            requestParam.setBatchNo(StringUtil.isEmpty(reqDto.getBatchNo()) ? null : reqDto.getBatchNo());
            requestParam.setCreateStartTime(StringUtil.isEmpty(reqDto.getCreateStartTime()) ? null : reqDto.getCreateStartTime());
            requestParam.setCreateEndTime(StringUtil.isEmpty(reqDto.getCreateEndTime()) ? null : reqDto.getCreateEndTime());
            requestParam.setCurrentPage(StringUtil.isEmpty(reqDto.getCurrentPage()) ? null : reqDto.getCurrentPage());
            requestParam.setPlatformCode(reqDto.getPlatformCode());
            requestParam.setTradeNo(StringUtil.isEmpty(reqDto.getTradeNo()) ? null : reqDto.getTradeNo());

            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestParam);
            LOGGER.info("查询批付明细入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, ORDERS_QUERY, new Object[] { inputParam });
            LOGGER.info("查询批付明细返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                List<CpBatchPaymentDetailResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), CpBatchPaymentDetailResDto.class);
                apiResDto.setResult(resultList);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
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

    /**
     * 出入账明细查询
     */
    @Override
    public ApiResDto<List<CpTranInOutDetailResDto>> tranInOutDetailQuery(CpTranInOutDetailReqDto reqDto) {
        ApiResDto<List<CpTranInOutDetailResDto>> apiResDto = new ApiResDto<List<CpTranInOutDetailResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            CpTranInOutDetailReqDto requestParam = new CpTranInOutDetailReqDto();
            // 接口入参预处理
            LOGGER.info("开始出入账明细查询入参预处理");
            requestParam.setPayerAccount(reqDto.getPayerAccount());
            requestParam.setStorePlatform(StringUtil.isEmpty(StorePlatformEnum.getCodeFromDescription(reqDto.getStorePlatform())) ? null : StorePlatformEnum.getCodeFromDescription(reqDto.getStorePlatform()));
            requestParam.setStoreName(StringUtil.isEmpty(reqDto.getStoreName()) ? null : reqDto.getStoreName());
            requestParam.setCurrency(StringUtil.isEmpty(CurType.getCodeFromDescription(reqDto.getCurrency())) ? null : CurType.getCodeFromDescription(reqDto.getCurrency()));
            requestParam.setOrderType(StringUtil.isEmpty(OrderType.getCodeFromDescription(reqDto.getOrderType())) ? null : OrderType.getCodeFromDescription(reqDto.getOrderType()));
            requestParam.setAdviceStartTime(StringUtil.isEmpty(reqDto.getAdviceStartTime()) ? null : reqDto.getAdviceStartTime());
            requestParam.setAdviceEndTime(StringUtil.isEmpty(reqDto.getAdviceEndTime()) ? null : reqDto.getAdviceEndTime());
            requestParam.setCurrentPage(StringUtil.isEmpty(reqDto.getCurrentPage()) ? null : reqDto.getCurrentPage());

            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestParam);
            LOGGER.info("查询出入账明细入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.COLL_SETT_ORDER_QUERY, TRANS_IN_OUT_QUERY, new Object[] { inputParam });
            LOGGER.info("查询出入账明细返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("查询出入账明细有数据", responseCode, responseMessage);
                apiResDto.setResponseMsg("");
                List<CpTranInOutDetailResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), CpTranInOutDetailResDto.class);
                apiResDto.setResult(resultList);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setPage(pageInfo);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("出入账明细查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /**
     * 提现明细查询
     */
    @Override
    public ApiResDto<List<CpWithdrawDetailResDto>> withdrawDetailQuery(CpWithdrawDetailReqDto reqDto) {
        ApiResDto<List<CpWithdrawDetailResDto>> apiResDto = new ApiResDto<List<CpWithdrawDetailResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            CpWithdrawDetailReqDto requestParam = new CpWithdrawDetailReqDto();
            // 接口入参预处理
            LOGGER.info("开始提现明细查询入参预处理");
            // 户头号
            requestParam.setPayerAccount(reqDto.getPayerAccount());
            requestParam.setStorePlatform(StringUtil.isEmpty(StorePlatformEnum.getCodeFromDescription(reqDto.getStorePlatform())) ? null : StorePlatformEnum.getCodeFromDescription(reqDto.getStorePlatform()));
            requestParam.setStoreName(StringUtil.isEmpty(reqDto.getStoreName()) ? null : reqDto.getStoreName());
            requestParam.setCurrency(StringUtil.isEmpty(CurType.getCodeFromDescription(reqDto.getCurrency())) ? null : CurType.getCodeFromDescription(reqDto.getCurrency()));
            requestParam.setOrderStatus(StringUtil.isEmpty(WithdrawOrderStatus.getCodeFromDescription(reqDto.getOrderStatus())) ? null : WithdrawOrderStatus.getCodeFromDescription(reqDto.getOrderStatus()));
            requestParam.setWithdrawStartTime(StringUtil.isEmpty(reqDto.getWithdrawStartTime()) ? null : reqDto.getWithdrawStartTime());
            requestParam.setWithdrawEndTime(StringUtil.isEmpty(reqDto.getWithdrawEndTime()) ? null : reqDto.getWithdrawEndTime());
            requestParam.setCurrentPage(StringUtil.isEmpty(reqDto.getCurrentPage()) ? null : reqDto.getCurrentPage());

            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestParam);
            LOGGER.info("查询提现明细入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.STORE_CASH_WITHDRAW, BATCH_WITHDRAW_QUERY, new Object[] { inputParam });
            LOGGER.info("查询提现明细返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("查询提现明细有数据", responseCode, responseMessage);
                apiResDto.setResponseMsg("");
                List<CpWithdrawDetailResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), CpWithdrawDetailResDto.class);
                apiResDto.setResult(resultList);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.PAGE), DalPage.class);
                apiResDto.setPage(pageInfo);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("提现明细查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }
}
