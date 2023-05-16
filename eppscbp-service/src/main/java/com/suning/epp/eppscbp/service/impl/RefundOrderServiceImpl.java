package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.List;
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
import com.suning.epp.eppscbp.common.enums.OcaDataSource;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.RefundOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.RefundOrderQueryRespDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.RefundOrderService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;

@Service("refundOrderService")
public class RefundOrderServiceImpl implements RefundOrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefundOrderServiceImpl.class);

	
	@Autowired
	private GeneralRsfService<Map<String, String>> generalRsfService;
	 
	@Override
	public ApiResDto<List<RefundOrderQueryRespDto>> queryRefundOrder(RefundOrderQueryReqDto requestDto) {
		ApiResDto<List<RefundOrderQueryRespDto>> apiResDto = new ApiResDto<List<RefundOrderQueryRespDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用OCA退款订单查询");
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestDto);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REFUND_ORDER_QUERY, "queryRefundOrder", new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                List<RefundOrderQueryRespDto> resultList = JSONObject.parseArray(outputParam.get("context"), RefundOrderQueryRespDto.class);
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
	public ApiResDto<Boolean> refundSubmit(String payerAccount,String receiptOrderNo, String merchantOrderNo, String refundAmt) {
		ApiResDto<Boolean> apiResDto = new ApiResDto<Boolean>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行指定收结汇订单查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            //收单单号
            inputParam.put("receiptOrderNo", receiptOrderNo);
            //商户订单号
            inputParam.put("merchantOrderNo", merchantOrderNo);
            //本次退款金额(订单币种)
            inputParam.put("amt", refundAmt);
            //数据来源
            inputParam.put("dataSource", OcaDataSource.EPPSCBP_SOURCE.getCode());
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REFUND_ORDER_QUERY, "apply", new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                apiResDto.setResult(true);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("指定收结汇订单未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
                apiResDto.setResult(false);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
	}

	@Override
	public ApiResDto<RefundOrderQueryRespDto> queryDetailInfo(String payerAccount, String refundOrderNo) {
		ApiResDto<RefundOrderQueryRespDto> apiResDto = new ApiResDto<RefundOrderQueryRespDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行退款单详情查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            inputParam.put("refundOrderNo", refundOrderNo);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REFUND_ORDER_QUERY, "queryRefundOrderDetailInfo", new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                RefundOrderQueryRespDto result = JSONObject.parseObject(outputParam.get(EPPSCBPConstants.CONTEXT), RefundOrderQueryRespDto.class);
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

}
