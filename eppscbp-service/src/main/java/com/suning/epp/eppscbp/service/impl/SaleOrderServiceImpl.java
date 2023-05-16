package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suning.epp.eppscbp.util.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.SaleOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.SaleOrderDetailQueryRespDto;
import com.suning.epp.eppscbp.dto.res.SaleOrderQueryRespDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.SaleOrderService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;


@Service
public class SaleOrderServiceImpl implements SaleOrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaleOrderServiceImpl.class);

	@Autowired
	private GeneralRsfService<Map<String, String>> generalRsfService;

	@Override
	public ApiResDto<List<SaleOrderQueryRespDto>> saleOrderQuery(SaleOrderQueryReqDto requestDto) {
		ApiResDto<List<SaleOrderQueryRespDto>> apiResDto = new ApiResDto<List<SaleOrderQueryRespDto>>();
		apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
		apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
		
		try {
			LOGGER.info("开始调用OCA销售订单查询");
			Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestDto);
			LOGGER.info("查询入参,inputParam:{}", inputParam);
			Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.TRADE_RECEIPT_ORDER_QUERY, "queryTradeReceiptOrder", new Object[] { inputParam });
			LOGGER.info("查询返回参数,outputParam:{}", outputParam);
			final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
			final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
			apiResDto.setResponseCode(responseCode);
			if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
				List<SaleOrderQueryRespDto> resultList = JSONObject.parseArray(outputParam.get("context"), SaleOrderQueryRespDto.class);
				DalPage pageInfo = JSONObject.parseObject(outputParam.get("page"), DalPage.class);
				apiResDto.setResult(resultList);
				apiResDto.setPage(pageInfo);
				apiResDto.setResponseMsg("");
			} else {
				// 未查询到数据或查询出错
				LOGGER.info("未成功状态:{}-{}", responseCode, responseMessage);
				apiResDto.setResponseMsg(responseMessage);
			}
		} catch (Exception ex) {
			LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
		}
		return apiResDto;
	}
	

	@Override
	public ApiResDto<SaleOrderDetailQueryRespDto> queryDetailInfo(String payerAccount, String receiptOrderNo) {
		    ApiResDto<SaleOrderDetailQueryRespDto> apiResDto = new ApiResDto<SaleOrderDetailQueryRespDto>();
	        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
	        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
	        try {
	            LOGGER.info("开始调用跨境结算进行销售订单详情查询");
	            Map<String, Object> inputParam = new HashMap<String, Object>();
	            inputParam.put("payerAccount", payerAccount);
	            inputParam.put("receiptOrderNo", receiptOrderNo);
	            LOGGER.info("查询入参,inputParam:{}", inputParam);
	            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.TRADE_RECEIPT_ORDER_QUERY, "queryTradeReceiptOrderDetail", new Object[] { inputParam });
	            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
	            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
	            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
	            apiResDto.setResponseCode(responseCode);
	            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
	                apiResDto.setResponseMsg("");
	                SaleOrderDetailQueryRespDto result = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.CONTEXT), SaleOrderDetailQueryRespDto.class);
	                apiResDto.setResult(result);
	            } else {
	                // 未查询到数据或查询出错
	                LOGGER.info("单笔查询未成功状态:{}-{}", responseCode, responseMessage);
	                apiResDto.setResponseMsg(responseMessage);
	            }
	        } catch (Exception ex) {
	            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
	        }
	        return apiResDto;
	    }


	@Override
	public ApiResDto<Boolean> batchQueryChannel(String payerAccount, String receiptOrderNoStr) {

		// 外卡收单单号转换类型为list
		List<String> receiptOrderNoList = StringUtil.StringTOList(receiptOrderNoStr);
		if (null == receiptOrderNoList) {
			LOGGER.info("外卡收单单号为空");
			return null;
		}

		ApiResDto<Boolean> apiResDto = new ApiResDto<Boolean>();
		apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
		apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);

		try {
			LOGGER.info("开始调用OCA销售订单批量结果查询");
			Map<String, Object> inputParam = new HashMap<String, Object>();
			inputParam.put("payerAccount", payerAccount);
			inputParam.put("receiptOrderNoList", receiptOrderNoList);
			LOGGER.info("查询入参,inputParam:{}", inputParam);
			Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.BATCH_QUERY_CHANNEL, "batchQueryChannel", new Object[]{inputParam});
			LOGGER.info("查询返回参数,outputParam:{}", outputParam);
			final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
			final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
			apiResDto.setResponseCode(responseCode);
			if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
				apiResDto.setResponseMsg("");
				apiResDto.setResult(true);
			} else {
				// 未查询到数据或查询出错
				LOGGER.info("未成功状态:{}-{}", responseCode, responseMessage);
				apiResDto.setResponseMsg(StringUtil.isEmpty(responseMessage)? "系统异常,请稍候重试!":responseMessage);
				apiResDto.setResult(false);
			}
		} catch (Exception ex) {
			LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
		}
		return apiResDto;
	}
}
