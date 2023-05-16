package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.Map;

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
import com.suning.epp.eppscbp.dto.req.TradeInfoReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CollSettTradeInfoResDto;
import com.suning.epp.eppscbp.dto.res.PurchasePaymentTradeInfoResDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.TradeInfoAggregationService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;

/**
 * 〈交易查询〉<br>
 * 〈功能详细描述〉
 *
 * @author 19043747
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("tradeInfoAggregationService")
public class TradeInfoAggregationServiceImpl implements TradeInfoAggregationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DomesticMerchantInfoServiceImpl.class);

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;
    
    // 查询收结汇交易信息 分页
    private static final String COLL_SETT_QUERY = "batchCollSettQuery";
    
    // 查询购付汇交易信息 分页
    private static final String PURCH_PAY_QUERY = "batchPurchPayQuery";
    
    // 获取单个PDF文件地址
    private static final String PDF_FILE_ADDRESS = "generateDzhd";
    
    // 获取批量pdf文件地址
    private static final String QUERY_BATCH_PDF_ADDRESS = "queryBatchPDFAddress";
    
	/* (non-Javadoc)
	 * @see com.suning.epp.eppscbp.service.TradeInfoAggregationService#queryCollSettTradeInfo(com.suning.epp.eppscbp.dto.req.TradeInfoReqDto)
	 */
	@Override
	public  ApiResDto<CollSettTradeInfoResDto> queryCollSettTradeInfo(TradeInfoReqDto reqDto) {
		 ApiResDto<CollSettTradeInfoResDto> apiResDto = new ApiResDto<CollSettTradeInfoResDto>();
	        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
	        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
	        try {
	            LOGGER.info("开始调用跨境结算进行交易查询");
	            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
	            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.TRADE_INFO_QUERY, COLL_SETT_QUERY, new Object[] { inputParam });
	            LOGGER.info("查询返回参数,outputParam");
	            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
	            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
	            apiResDto.setResponseCode(responseCode);
	            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
	            	CollSettTradeInfoResDto result = JSONObject.parseObject(outputParam.get("context"), CollSettTradeInfoResDto.class);
	            	DalPage pageInfo = JSONObject.parseObject(outputParam.get("page"), DalPage.class);
	                apiResDto.setResult(result);
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
	public ApiResDto<PurchasePaymentTradeInfoResDto> purchasePaymentTradeInfo(TradeInfoReqDto reqDto) {
		ApiResDto<PurchasePaymentTradeInfoResDto> apiResDto = new ApiResDto<PurchasePaymentTradeInfoResDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行购付汇聚合交易信息查询");
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.TRADE_INFO_QUERY, PURCH_PAY_QUERY, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:********");
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
            	PurchasePaymentTradeInfoResDto result = JSONObject.parseObject(outputParam.get("context"), PurchasePaymentTradeInfoResDto.class);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get("page"), DalPage.class);
                apiResDto.setResult(result);
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
	public ApiResDto<String> getFileAddrePDF(String orderNo, String transactionType) {
		ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行pdf查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("orderNo", orderNo);
            inputParam.put("transType", transactionType);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.TRADE_INFO_DZHDAPI, PDF_FILE_ADDRESS, new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
            	String result = outputParam.get("context");
                apiResDto.setResult(result);
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
	public ApiResDto<String> queryPDFAddressByCondition(TradeInfoReqDto reqDto) {
		ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("汇总电子回单pdf地址查询开始");
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(reqDto);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.TRADE_INFO_QUERY, QUERY_BATCH_PDF_ADDRESS, new Object[] { inputParam });
            LOGGER.info("汇总电子回单pdf地址查询返回参数,outputParam:{}",outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
            	String result = JSONObject.parseObject(outputParam.get("context"), String.class);
            	LOGGER.info("查询返回参数,result:{}",result);
                apiResDto.setResult(result);
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
}
