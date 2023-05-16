/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpWithdrawApplyServiceImpl.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.rsf.service.impl;

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
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.CpWithdrawApplyDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CpWithdrawApplyInfoDto;
import com.suning.epp.eppscbp.dto.res.CpWithdrawApplyResDto;
import com.suning.epp.eppscbp.rsf.service.CpWithdrawApplyService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈提现币种查询和导出service〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("cpWithdrawApplyService")
public class CpWithdrawApplyServiceImpl implements CpWithdrawApplyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpWithdrawApplyServiceImpl.class);

    // 是否包含易付宝商户资金查询出账批次
    public static final String QUERY_BATCH_BY_CUR = "queryBatchByCur";
    // 出账批次查询易付宝商户资金信息
    public static final String QUERY_WITHDRAW_BY_BATCH = "queryWithdrawByBatch";
    // 提现申请提交
    public static final String PM_APPLY = "pmApply";

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    /**
     * 出账批次查询
     */
    @Override
    public ApiResDto<CpWithdrawApplyInfoDto> batchQuery(String outAccountBatch, String currency, String payerAccount) {
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("outAccountBatch", outAccountBatch);
            request.put("withdrawCur", currency);
            request.put(EPPSCBPConstants.PAYERACCOUNT, payerAccount);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求查询出账批次信息的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_CASH_WITHDRAW, QUERY_WITHDRAW_BY_BATCH, new Object[]{request});

            ApiResDto<CpWithdrawApplyInfoDto> apiResDto = new ApiResDto<CpWithdrawApplyInfoDto>();
            apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
            apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);

            String responseStr = JSON.toJSONString(response);

            LOGGER.info("查询出账批次信息的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("请求查询出账批次信息的参数成功开始");
                apiResDto.setResponseCode(CommonConstant.RESPONSE_SUCCESS_CODE);
                apiResDto.setResponseMsg("");
                final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                CpWithdrawApplyInfoDto detailDto = JSON.parseObject(context, CpWithdrawApplyInfoDto.class);
                apiResDto.setResult(detailDto);
                LOGGER.info("请求查询出账批次信息的参数成功结束");
            } else {
                apiResDto.setResponseMsg("出账批次未查询到");
            }
            return apiResDto;

        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }

    /**
     * 是否包含易付宝商户资金查询
     */
    @Override
    public ApiResDto<CpWithdrawApplyInfoDto> outBatchQuery(String currency, String payerAccount) {
        try {
            // 调用参数
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("withdrawCur", currency);
            request.put(EPPSCBPConstants.PAYERACCOUNT, payerAccount);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求查询易付宝商户资金信息的参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_CASH_WITHDRAW, QUERY_BATCH_BY_CUR, new Object[]{request});

            ApiResDto<CpWithdrawApplyInfoDto> apiResDto = new ApiResDto<CpWithdrawApplyInfoDto>();
            apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
            apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);

            String responseStr = JSON.toJSONString(response);

            LOGGER.info("查询易付宝商户资金的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("请求查询易付宝商户资金的参数成功开始");
                apiResDto.setResponseCode(CommonConstant.RESPONSE_SUCCESS_CODE);
                apiResDto.setResponseMsg("");
                final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                CpWithdrawApplyInfoDto detailDto = JSON.parseObject(context, CpWithdrawApplyInfoDto.class);
                apiResDto.setResult(detailDto);
                LOGGER.info("请求查询易付宝商户资金的参数成功结束");
            } else {
                apiResDto.setResponseMsg("易付宝商户资金未查询到");
            }
            return apiResDto;

        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }

    /**
     * 提现申请<br>
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Override
    public ApiResDto<CpWithdrawApplyResDto> submitWithdrawApply(CpWithdrawApplyDto dto) {
        LOGGER.info("提现申请提交:{}", dto);
        ApiResDto<CpWithdrawApplyResDto> apiResDto = new ApiResDto<CpWithdrawApplyResDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            Map<String, Object> request = BeanConverterUtil.beanToMap(dto);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("提现申请请请求参数:{}", requestStr);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.STORE_CASH_WITHDRAW, PM_APPLY, new Object[]{request});
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("提现申请返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(StringUtil.isEmpty(responseCode) ? CommonConstant.SYSTEM_ERROR_CODE : responseCode);
            if (!CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                // 提交提现申请失败
                LOGGER.info("提现申请未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(StringUtil.isEmpty(responseMessage) ? CommonConstant.SYSTEM_ERROR_MES : responseMessage);
            } else {
                // 提交订单成功
                LOGGER.info("提现申请返回成功");
                apiResDto.setResponseMsg("");
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

}
