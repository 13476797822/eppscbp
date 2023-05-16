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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.RefundOrderStatus;
import com.suning.epp.eppscbp.common.enums.RejectOrderStatus;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.RejectOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.AcRejectOrderResHisDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.MessageBoxQueryRespDto;
import com.suning.epp.eppscbp.dto.res.RefundOrderQueryRespDto;
import com.suning.epp.eppscbp.dto.res.RejectDetailResDto;
import com.suning.epp.eppscbp.dto.res.RejectOrderQueryRespDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.RejectOrderService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;

@Service
public class RejectOrderServiceImpl implements RejectOrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RejectOrderServiceImpl.class);

	@Autowired
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
	@Override
	public ApiResDto<List<RejectOrderQueryRespDto>> rejectOrderQuery(RejectOrderQueryReqDto requestDto) {
		ApiResDto<List<RejectOrderQueryRespDto>> apiResDto = new ApiResDto<List<RejectOrderQueryRespDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用OCA拒付订单查询");
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestDto);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REJECT_ORDER_QUERY, "queryRejectOrder", new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                List<RejectOrderQueryRespDto> resultList = JSONObject.parseArray(outputParam.get("context"), RejectOrderQueryRespDto.class);
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
	public ApiResDto<RejectDetailResDto> rejectDetailInfoQuery(String payerAccount, String rejectOrderNo) {
		ApiResDto<RejectDetailResDto> apiResDto = new ApiResDto<RejectDetailResDto>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行拒付详情查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            inputParam.put("rejectOrderNo", rejectOrderNo);
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REJECT_ORDER_QUERY, "queryRejectOrderDetail", new Object[] { inputParam });
            LOGGER.info("拒付详情查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                RejectDetailResDto result = new RejectDetailResDto();
                result.setRejectOrderQueryRespDto(JSON.parseObject(outputParam.get(EPPSCBPConstants.CONTEXT), RejectOrderQueryRespDto.class));
                result.setAcRejectOrderHisList(JSON.parseArray(outputParam.get("hisList"), AcRejectOrderResHisDto.class));
                apiResDto.setResult(result);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("拒付详情查询返回:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
	}

	@Override
	public ApiResDto<Boolean> accept(String payerAccount, String merchantOrderNo, String rejectOrderNo) {
		ApiResDto<Boolean> apiResDto = new ApiResDto<Boolean>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行指定收结汇订单查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            //商户订单号
            inputParam.put("merchantOrderNo", merchantOrderNo);
            //拒付业务单号
            inputParam.put("rejectOrderNo", rejectOrderNo);
            //标识 Y接受
            inputParam.put("appealResult", "10");
            
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REJECT_ORDER_QUERY, "rejectAppeal", new Object[] { inputParam });
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
	public ApiResDto<Boolean> appeal(String payerAccount, String rejectOrderNo, String reason, String filePath) {
		ApiResDto<Boolean> apiResDto = new ApiResDto<Boolean>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用跨境结算进行指定收结汇订单查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            //拒付业务单号
            inputParam.put("rejectOrderNo", rejectOrderNo);
            //申诉原因
            inputParam.put("appealReason", reason);
            //申诉地址
            inputParam.put("appealAttach", filePath);
             //标识 N申诉
            inputParam.put("appealResult", "30");
            
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REJECT_ORDER_QUERY, "rejectAppeal", new Object[] { inputParam });
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
    public ApiResDto<JSONArray> rejectMessageBoxQuery(String merchantOrderNo, String payerAccount) {

        ApiResDto<JSONArray> apiResDto = new ApiResDto<JSONArray>();
        JSONArray jsonArray = new JSONArray();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用OCA退款订单查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("merchantOrderNo",merchantOrderNo);
            inputParam.put("payerAccount",payerAccount);
            inputParam.put("pageNo","1");
            inputParam.put("pageSize","10");
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParamRefund = generalRsfService.invoke(ApiCodeConstant.REFUND_ORDER_QUERY, "queryRefundOrder", new Object[] { inputParam });
            inputParam.put("status","");
            Map<String, String> outputParamReject = generalRsfService.invoke(ApiCodeConstant.REJECT_ORDER_QUERY, "queryRejectOrder", new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParamRefund:{}", outputParamRefund);
            LOGGER.info("查询返回参数,outputParamReject:{}", outputParamReject);
            final String responseCodeRefund = MapUtils.getString(outputParamRefund, CommonConstant.RESPONSE_CODE);
            final String responseCodeReject = MapUtils.getString(outputParamReject, CommonConstant.RESPONSE_CODE);
            apiResDto.setResponseCode(CommonConstant.RESPONSE_SUCCESS_CODE);

            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCodeRefund)) {
                List<RefundOrderQueryRespDto> resultList = JSON.parseArray(outputParamRefund.get("context"), RefundOrderQueryRespDto.class);
                for (RefundOrderQueryRespDto dto : resultList) {
                    if (RefundOrderStatus.REFUND_SUCESS.getCode().equals(dto.getRefundStatus())) {
                        MessageBoxQueryRespDto messageBoxQueryRespDto = new MessageBoxQueryRespDto();
                        messageBoxQueryRespDto.setMerchantOrderNo(dto.getMerchantOrderNo());
                        messageBoxQueryRespDto.setFlag("1");
                        messageBoxQueryRespDto.setCur(dto.getCur());
                        messageBoxQueryRespDto.setCreateTime(dto.getRefundCreateTime());
                        messageBoxQueryRespDto.setAmt(dto.getRefundAmt());
                        jsonArray.add(messageBoxQueryRespDto);
                    }
                }
            }
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCodeReject)) {
                List<RejectOrderQueryRespDto> resultList = JSON.parseArray(outputParamReject.get("context"), RejectOrderQueryRespDto.class);
                for (RejectOrderQueryRespDto dto : resultList) {
                    if (RejectOrderStatus.REJECT_ORDER_ACCEPT_SUCCESS.getCode().equals(dto.getStatus())
                            ||RejectOrderStatus.REJECT_ORDER_RULE_SUCCESS.getCode().equals(dto.getStatus())) {
                        MessageBoxQueryRespDto messageBoxQueryRespDto = new MessageBoxQueryRespDto();
                        messageBoxQueryRespDto.setMerchantOrderNo(dto.getMerchantOrderNo());
                        messageBoxQueryRespDto.setFlag("2");
                        messageBoxQueryRespDto.setCur(dto.getCur());
                        messageBoxQueryRespDto.setCreateTime(dto.getCreateTime());
                        messageBoxQueryRespDto.setAmt(dto.getRejectAmt());
                        jsonArray.add(messageBoxQueryRespDto);
                    }
                }
            }
            apiResDto.setResult(jsonArray);
            LOGGER.info("size:{},jsonArray:{}",jsonArray.size(),jsonArray);
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }


}
