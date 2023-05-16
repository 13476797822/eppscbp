/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: BatchPaymentServiceImpl.java
 * Author:   17033387
 * Date:     2018年11月10日 下午3:58:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import com.suning.epp.eppscbp.dto.req.BatchPaymentQueryReqDto;
import com.suning.epp.eppscbp.dto.req.OrderCalculateDetailDto;
import com.suning.epp.eppscbp.dto.req.OrderCalculateDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDetailDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentOrderQueryAndCalculateResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentQueryResDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.BatchPaymentService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class BatchPaymentServiceImpl implements BatchPaymentService {

    /**
     * Logging mechanism
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchPaymentServiceImpl.class);

    // 批付申请
    private static final String BATCH_PAYMENT_ADUIT = "ordersAudit";
    // 批付查询
    private static final String BATCH_PAYMENT_RESULT_QUERY = "resultQueryByBus";
    private static final String BATCH_PAYMENT_QUERY = "balanceQuery";
    // 笔数
    private static final String ONE = "1";
    // 文件模式批付出款
    private static final String FILE_BATCH_PAYMENT_ADUIT = "fileOrdersAudit";
    
    // 文件模式解析批付订单
    private static final String BATCH_PAYMENT_ORDER_PARSE_FILE = "batchPaymentOrderParseFile";

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.BatchPaymentService#ordersAudit(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<String> ordersAudit(String payerAccount, String businessNo, List<OrdersAuditRequestDetailDto> batchPaymentDetail) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            OrdersAuditRequestDto request = new OrdersAuditRequestDto();
            request.setBusinessNo(businessNo);
            request.setPayerAccount(payerAccount);
            request.setPlatformCode(EPPSCBPConstants.PLATFORMCODE);
            // 转换成MAP
            Map<String, Object> reqMap = BeanConverterUtil.beanToMap(request);
            reqMap.put("detailList", JSONObject.toJSONString(batchPaymentDetail));
            LOGGER.info("请求申请批付的参数:{}", request);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, BATCH_PAYMENT_ADUIT, reqMap);
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("申请批付的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("申请批付返回成功");
                final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                String resDto = JSON.parseObject(context, String.class);
                apiResDto.setResponseCode(responseCode);
                apiResDto.setResult(resDto);
            } else {
                LOGGER.error("申请批付结果未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.info("申请批付失败");
            }
            apiResDto.setResponseMsg(responseMessage);
            return apiResDto;
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.BatchPaymentService#ordersQuery(java.lang.String, java.lang.String, java.lang.Boolean)
     */
    @Override
    public ApiResDto<List<BatchPaymentQueryResDto>> ordersQuery(String payerAccount, String businessNo) {
        ApiResDto<List<BatchPaymentQueryResDto>> apiResDto = new ApiResDto<List<BatchPaymentQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            BatchPaymentQueryReqDto request = new BatchPaymentQueryReqDto();
            request.setPayerAccount(payerAccount);
            request.setBusinessNo(businessNo);
            request.setPlatformCode(EPPSCBPConstants.PLATFORMCODE);
            LOGGER.info("请求查询批付信息的参数:{}", request);
            // 转换成MAP
            Map<String, Object> reqMap = BeanConverterUtil.beanToMap(request);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, BATCH_PAYMENT_QUERY, reqMap);
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("查询批付信息的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("调用查询批付信息返回成功");
                final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                List<BatchPaymentQueryResDto> resDto = JSON.parseArray(context, BatchPaymentQueryResDto.class);
                apiResDto.setResponseCode(responseCode);
                apiResDto.setResult(resDto);
                apiResDto.setResponseMsg(MapUtils.getString(response, "amount"));
                // final String batchPaymentFlag = MapUtils.getString(response, "batchPaymentFlag");

                // 0代表可以修改金额
                apiResDto.setFlag(true);
            } else {
                LOGGER.error("查询批付信息结果未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.info("调用查询批付信息失败");
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.BatchPaymentService#resultQuery(java.lang.String)
     */
    @Override
    public ApiResDto<List<BatchPaymentQueryResDto>> resultQuery(String payerAccount, String businessNo) {
        ApiResDto<List<BatchPaymentQueryResDto>> apiResDto = new ApiResDto<List<BatchPaymentQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            // 调用参数
            BatchPaymentQueryReqDto request = new BatchPaymentQueryReqDto();
            request.setPayerAccount(payerAccount);
            request.setBusinessNo(businessNo);
            request.setPlatformCode(EPPSCBPConstants.PLATFORMCODE);
            LOGGER.info("请求查询批付信息的参数:{}", request);
            // 转换成MAP
            Map<String, Object> reqMap = BeanConverterUtil.beanToMap(request);
            // 先触发调用批付API
            Map<String, String> response1 = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, BATCH_PAYMENT_RESULT_QUERY, reqMap);
            String responseStr1 = JSON.toJSONString(response1);
            LOGGER.info("触发批付接口查询信息的返回参数:{}", responseStr1);
            final String responseCode1 = MapUtils.getString(response1, CommonConstant.RESPONSE_CODE);
            final String responseMessage1 = MapUtils.getString(response1, CommonConstant.RESPONSE_MESSAGE);
            if (!EPPSCBPConstants.SUCCESS_CODE.equals(responseCode1)) {
                LOGGER.error("触发批付接口查询信息未成功状态:{}-{}", responseCode1, responseMessage1);
                LOGGER.info("触发批付接口查询信息失败");
                return apiResDto;
            }
            // 触发查询
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, BATCH_PAYMENT_QUERY, reqMap);
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("查询批付信息的返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                LOGGER.info("调用查询批付信息返回成功");
                final String context = MapUtils.getString(response, CommonConstant.CONTEXT);
                List<BatchPaymentQueryResDto> resDto = JSON.parseArray(context, BatchPaymentQueryResDto.class);
                apiResDto.setResponseCode(responseCode);
                apiResDto.setResult(resDto);
                apiResDto.setResponseMsg("");
                final String batchPaymentFlag = MapUtils.getString(response, "batchPaymentFlag");
                // 0代表可以修改金额
                apiResDto.setFlag(batchPaymentFlag.equals("0"));
            } else {
                LOGGER.error("查询批付信息结果未成功状态:{}-{}", responseCode, responseMessage);
                LOGGER.info("调用查询批付信息失败");
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.BatchPaymentService#ordersCalculate(com.suning.epp.eppscbp.dto.req. OrdersAuditRequestDto)
     */
    @Override
    public OrderCalculateDto ordersCalculate(OrderCalculateDto ordersAuditRequestDto) {
        List<OrderCalculateDetailDto> details = ordersAuditRequestDto.getDetails();
        List<OrderCalculateDetailDto> newDetails = new ArrayList<OrderCalculateDetailDto>();
        // 可批付金额
        BigDecimal prePayAmount = new BigDecimal(ordersAuditRequestDto.getPrePayAmount());
        BigDecimal amountCount = new BigDecimal(0);
        Long numberCount = 0l;
        for (OrderCalculateDetailDto dto : details) {
            // 笔数为1 不处理
            if (dto.getNumber().equals(ONE)) {
                newDetails.add(dto);
                amountCount = amountCount.add(new BigDecimal(dto.getAmount()));
                numberCount++;
                continue;
            }
            BigDecimal amount = new BigDecimal(dto.getAmount());
            BigDecimal number = new BigDecimal(dto.getNumber());
            // 取余计算 获得商和余数
            // 商四舍五入保留2位小数
            BigDecimal result = amount.divide(number, 2, RoundingMode.HALF_UP);
            // 生成前n-1笔
            List<OrderCalculateDetailDto> newDetailsSingle = new ArrayList<OrderCalculateDetailDto>();
            for (int i = 0; i < number.intValue() - 1; i++) {
                OrderCalculateDetailDto newDetail = new OrderCalculateDetailDto(dto);
                newDetail.setAmount(result.toPlainString());
                newDetailsSingle.add(newDetail);
                amountCount = amountCount.add(result);
                numberCount++;
            }
            // 处理最后一条(取余只处理整数部分，不处理小数，故最后一条统一用减法处理)
            OrderCalculateDetailDto newDetail = new OrderCalculateDetailDto(dto);
            BigDecimal tmp = amount;
            for (OrderCalculateDetailDto newDto : newDetailsSingle) {
                tmp = tmp.subtract(new BigDecimal(newDto.getAmount()));
            }
            newDetail.setAmount(tmp.toPlainString());
            newDetailsSingle.add(newDetail);
            amountCount = amountCount.add(tmp);
            numberCount++;
            // 插入返回结果列表
            newDetails.addAll(newDetailsSingle);
        }

        if (amountCount.compareTo(prePayAmount) > 0) {
            ordersAuditRequestDto.setFlag(false);
        } else {
            ordersAuditRequestDto.setFlag(true);
            ordersAuditRequestDto.setDetails(newDetails);
            ordersAuditRequestDto.setAmountCount(numberFormatterToFinance(amountCount));
            ordersAuditRequestDto.setNumberCount(numberCount.toString());
        }

        return ordersAuditRequestDto;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.BatchPaymentService#ordersCalculate(com.suning.epp.eppscbp.dto.req. OrdersAuditRequestDto)
     */
    @Override
    public OrderCalculateDto cpOrdersCalculate(OrderCalculateDto ordersAuditRequestDto) {
        List<OrderCalculateDetailDto> details = ordersAuditRequestDto.getDetails();
        List<OrderCalculateDetailDto> newDetails = new ArrayList<OrderCalculateDetailDto>();
        // 可批付金额
        BigDecimal prePayAmount = new BigDecimal(ordersAuditRequestDto.getPrePayAmount());
        BigDecimal amountCount = new BigDecimal(0);
        Long numberCount = 0l;
        for (OrderCalculateDetailDto dto : details) {
            // 笔数为1 不处理
            newDetails.add(dto);
            amountCount = amountCount.add(new BigDecimal(dto.getAmount()));
            numberCount++;

        }

        if (amountCount.compareTo(prePayAmount) > 0) {
            ordersAuditRequestDto.setFlag(false);
        } else {
            ordersAuditRequestDto.setFlag(true);
            ordersAuditRequestDto.setDetails(newDetails);
            ordersAuditRequestDto.setAmountCount(numberFormatterToFinance(amountCount));
            ordersAuditRequestDto.setNumberCount(numberCount.toString());
        }

        return ordersAuditRequestDto;
    }

    /**
     * 将Bigdecial以财务形式显示,千分位表示
     * 
     * @param number
     * @return
     */
    public static String numberFormatterToFinance(BigDecimal number) {
        NumberFormat nf = new DecimalFormat("#,###.##");
        return nf.format(number).equals("0") ? "" : nf.format(number);
    }

    @Override
    public ApiResDto<String> batchPaymentUpload(String businessNo, String payerAccount, String fileAddress, String type) {
        ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行指定文件模式批付出款");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            inputParam.put("businessNo", businessNo);
            inputParam.put("platformCode", type);
            inputParam.put("fileAddress", fileAddress);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, FILE_BATCH_PAYMENT_ADUIT, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg(responseMessage);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("文件模式批付出款未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

	@Override
	public ApiResDto<BatchPaymentOrderQueryAndCalculateResDto> bpOrdersParseAndCalculate(String businessNo, String payerAccount, String fileAddress, String type, String prePayAmount, String bizType) {
		//请求跨境,得到批付明细数据list
		ApiResDto<BatchPaymentOrderQueryAndCalculateResDto> apiResDto = new ApiResDto<BatchPaymentOrderQueryAndCalculateResDto>();
		BatchPaymentOrderQueryAndCalculateResDto batchPaymentOrderQueryAndCalculateResDto = new BatchPaymentOrderQueryAndCalculateResDto();
		apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行指定文件模式批付出款");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            inputParam.put("businessNo", businessNo);
            inputParam.put("platformCode", type);
            inputParam.put("fileAddress", fileAddress);
            inputParam.put("bizType", bizType);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_PAYMENT, BATCH_PAYMENT_ORDER_PARSE_FILE, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
            	 final String context = MapUtils.getString(outputParam, CommonConstant.CONTEXT);
                 List<OrderCalculateDetailDto> details = JSON.parseArray(context, OrderCalculateDetailDto.class);
                 // 检查金额
                 Long auditAmount = 0L;
                 for(OrderCalculateDetailDto dto : details) {
                	  auditAmount = auditAmount + new BigDecimal(dto.getAmount()).multiply(new BigDecimal(100)).longValue();
                 }
                 
                 //比较提交的总金额是否大于可批付金额
                 if (auditAmount > new BigDecimal(prePayAmount).multiply(new BigDecimal(100)).longValue()) {
                	 LOGGER.info("提交的批付金额之和大于可批付金额,提交金额:{},可批付金额:{}", auditAmount, new BigDecimal(prePayAmount).multiply(new BigDecimal(100)).longValue());
                	 apiResDto.setFlag(false);
                     apiResDto.setResponseMsg("出款金额大于可批付金额");
                     return apiResDto;
                 }
                 
                 //页面展示数据
                 batchPaymentOrderQueryAndCalculateResDto.setDetails(details);
                 batchPaymentOrderQueryAndCalculateResDto.setBusinessNo(businessNo);
                 batchPaymentOrderQueryAndCalculateResDto.setFileAddress(fileAddress);
                 batchPaymentOrderQueryAndCalculateResDto.setAmountCount(numberFormatterToFinance(new BigDecimal(StringUtil.formatMoney(auditAmount))));
                 batchPaymentOrderQueryAndCalculateResDto.setNumberCount(String.valueOf(details.size()));
                 apiResDto.setResult(batchPaymentOrderQueryAndCalculateResDto);
                 apiResDto.setResponseCode(responseCode);
                 apiResDto.setFlag(true);
                 
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("文件模式批付出款未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(StringUtil.isNull(responseMessage) ? EPPSCBPConstants.SYSTEM_ERROR : responseMessage);
                apiResDto.setFlag(false);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
            apiResDto.setFlag(false);
            apiResDto.setResponseMsg(EPPSCBPConstants.SYSTEM_ERROR);
        }
        return apiResDto;
		
	}
}
