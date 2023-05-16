package com.suning.epp.eppscbp.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CtArrivalNoticeStatus;
import com.suning.epp.eppscbp.common.enums.PreOrderFileStatus;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.dto.req.PreArrivalQueryReqDto;
import com.suning.epp.eppscbp.dto.req.PreBpOrderParseCalcReqDto;
import com.suning.epp.eppscbp.dto.req.PreCollAndSettleQueryDto;
import com.suning.epp.eppscbp.dto.req.PreOrderCollAndPaymentApplyReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentOrderQueryAndCalculateResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.PreAmountQueryResDto;
import com.suning.epp.eppscbp.dto.res.PreCollAndSettleQueryResDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.BatchPaymentService;
import com.suning.epp.eppscbp.service.PreOrderCollAndSettleService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSMimeType;
import com.suning.epp.eppscbp.util.oss.OSSUploadParams;
import com.suning.epp.eppscbp.util.validator.ValidateBaseHibernateUtil;

/**
 * 订单前置
 */
@Service("preOrderCollAndSettleService")
public class PreOrderCollAndSettleServiceImpl implements PreOrderCollAndSettleService {
    private static final Logger log = LoggerFactory.getLogger(PreOrderCollAndSettleServiceImpl.class);

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    @Autowired
    private GeneralRsfService<Map<String, Object>> generalRsfServiceObj;

    @Autowired
    private BatchPaymentService batchPaymentService;

    // 来账信息查询
    private static final String ARRIVAL_QUERY = "arrivalQuery";

    // 收结汇付款"提交申请"
    private static final String COLL_PAYMENT_APPLY = "collPaymentApply";
    // 前置订单文件解析"提交申请"
    private static final String APPLY_PRE_DETAIL_FILE = "applyPreDetailFile";
    // 发起查询前置订单文件
    private static final String QUERY_PRE_ORDER = "queryPreOrder";
    // 发起查询前置预存订单剩余可用余额
    private static final String QUERY_PRE_ORDER_AMT = "queryPreOrderAmt";

    @Override
    public Map<String, Object> arrivalQuery(PreArrivalQueryReqDto params) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            params.setStatus(CtArrivalNoticeStatus.getCodeFromDescription(params.getStatus()));
            Map<String, Object> reqParams = BeanConverterUtil.beanToMap(params);
            log.info("来账信息查询入参:{}", reqParams);
            Map<String, Object> response = generalRsfServiceObj.invoke(ApiCodeConstant.PRE_ORDER, ARRIVAL_QUERY, new Object[]{reqParams});
            log.debug("来账信息查询返回参数:{}", response);

            return response;
        } catch (Exception ex) {
            log.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));

            result.put(EPPSCBPConstants.RESPONSE_CODE, EPPSCBPConstants.ERROR_CODE);
            result.put(EPPSCBPConstants.RESPONSE_MSG, EPPSCBPConstants.SYSTEM_ERROR);
        }

        return result;
    }

    @Override
    public Map<String, Object> bpOrdersParseCalc(PreBpOrderParseCalcReqDto params, MultipartFile targetFile) {
        Map<String, Object> result = new HashMap<String, Object>();

        //字段校验
        String validate = ValidateBaseHibernateUtil.validate(params);
        if (!StringUtil.isEmpty(validate)) {
            result.put(CommonConstant.RESPONSE_CODE, WebErrorCode.CHECK_FAIL.getCode());
            result.put(CommonConstant.RESPONSE_MESSAGE, validate);
            return result;
        }

        try {
            // 上传文件到oss
            String fileAddress  = this.uploadCollSett(targetFile, OSSBucket.BATCH_PAYMENT_FILE);
            params.setFileAddress(fileAddress);
            log.info("解析excel、计算批付总金额开始,请求参数{}", params);
            // 请求cbs，解析excel，返回list,进行计算
            ApiResDto<BatchPaymentOrderQueryAndCalculateResDto> apiResDto = batchPaymentService.bpOrdersParseAndCalculate(null, params.getPayerAccount(), params.getFileAddress(), CommonConstant.PRE_CODE, params.getBpableAmount(), params.getBizType());
            log.info("解析excel、计算批付总金额请求触发跨境返回:{}", apiResDto);
            BatchPaymentOrderQueryAndCalculateResDto dto = apiResDto.getResult();
            if (apiResDto.getFlag()) {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
                result.put(EPPSCBPConstants.AMOUNT, dto.getAmountCount());
                result.put(EPPSCBPConstants.NUMBER, dto.getNumberCount());
                result.put(EPPSCBPConstants.FILE_ADDRESS, fileAddress);
                result.put(EPPSCBPConstants.RESULT_LIST, dto.getDetails());
            } else {
                if(EPPSCBPConstants.BPAYMENT_INFO_PARSE_FAIL.equals(apiResDto.getResponseCode())) {
                    result.put(EPPSCBPConstants.FILE_ADDRESS, apiResDto.getResponseMsg());
                }else{
                    result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
                }
            }
        } catch (Exception e) {
            log.error("解析excel、计算批付总金额发送异常:{}", org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e));
            result.put(CommonConstant.RESPONSE_MESSAGE, EPPSCBPConstants.SYSTEM_ERROR);
        }
        log.info("解析excel、计算批付总金额结束");
        return result;
    }

    @Override
    public FileUploadResDto collAndSettleFileUpload(MultipartFile targetFile, String payerAccount) throws IOException {
        FileUploadResDto res = new FileUploadResDto();
        // 上传文件到oss
        String fileAddress  = this.uploadCollSett(targetFile, OSSBucket.COLL_SETT_FILE);
        log.info("文件地址:{}", fileAddress);
        res.setFileAddress(fileAddress);
        return res;
    }


    @Override
    public Map<String, String> collAndSettleFileApply(String fileAddress, String payerAccount, String bizType) {
        log.info("文件地址:{},户头号：{}， 业务类型：{}", fileAddress, payerAccount, bizType);
        // 调用eppcbs的收结汇前置明细解析接口
        Map<String, String> response = new HashMap<String, String>();
        response.put(CommonConstant.RESPONSE_CODE, CommonConstant.SYSTEM_ERROR_CODE);
        response.put(CommonConstant.RESPONSE_MESSAGE, CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> reqParams = new HashMap<String, Object>();
            reqParams.put("fileAddress", fileAddress);
            reqParams.put("payerAccount", payerAccount);
            reqParams.put("bizType", bizType);
            reqParams.put("fileNo", fileAddress.replace(".xlsx", ""));
            log.info("收结汇前置订单明细解析申请入参:{}", reqParams);
            response = generalRsfService.invoke(ApiCodeConstant.PRE_ORDER, APPLY_PRE_DETAIL_FILE, new Object[]{reqParams});
            log.info("收结汇前置订单明细解析申请返回结果:{}", response);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);

            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                response.put(EPPSCBPConstants.RESPONSE_CODE, responseCode);
                response.put(EPPSCBPConstants.RESPONSE_MSG, responseMessage);
                response.put("success","s");
            } else {
                // 未查询到数据或查询出错
                log.info("未成功状态:{}-{}", responseCode, responseMessage);
                response.put(CommonConstant.RESPONSE_MESSAGE, responseMessage);
                response.put("success","f");
            }

        } catch (Exception ex) {
            log.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));

            response.put(EPPSCBPConstants.RESPONSE_CODE, EPPSCBPConstants.ERROR_CODE);
            response.put(EPPSCBPConstants.RESPONSE_MSG, EPPSCBPConstants.SYSTEM_ERROR);
            response.put("success","f");
        }

        return response;
    }


    @Override
    public ApiResDto<List<PreCollAndSettleQueryResDto>> collAndSettlePreQuery(PreCollAndSettleQueryDto requestDto) {
        ApiResDto<List<PreCollAndSettleQueryResDto>> apiResDto = new ApiResDto<List<PreCollAndSettleQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            PreCollAndSettleQueryDto queryDto = new PreCollAndSettleQueryDto();
            queryDto.setCreatFileEndTime(StringUtil.isEmpty(requestDto.getCreatFileEndTime())? null : requestDto.getCreatFileEndTime());
            queryDto.setCreatFileStartTime(StringUtil.isEmpty(requestDto.getCreatFileStartTime())? null : requestDto.getCreatFileStartTime());
            queryDto.setCurrentPage(StringUtil.isEmpty(requestDto.getCurrentPage())? null : requestDto.getCurrentPage());
            queryDto.setPayerAccount(StringUtil.isEmpty(requestDto.getPayerAccount())? null : requestDto.getPayerAccount());
            queryDto.setBizType(StringUtil.isEmpty(requestDto.getBizType())? null : ChargeBizType.getCodeFromDescription(requestDto.getBizType()));
            queryDto.setFileStatus(StringUtil.isEmpty(requestDto.getFileStatus())? null : PreOrderFileStatus.getCodeFromDescription(requestDto.getFileStatus()));
            log.info("开始调用跨境结算进行前置文件收单信息查询:{}",queryDto);
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(queryDto);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.PRE_ORDER, QUERY_PRE_ORDER, new Object[]{inputParam});
            log.info("查询返回参数,outputParam:********");
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                List<PreCollAndSettleQueryResDto> result = JSONObject.parseArray(outputParam.get("context"), PreCollAndSettleQueryResDto.class);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get("page"), DalPage.class);
                apiResDto.setResult(result);
                apiResDto.setPage(pageInfo);
                apiResDto.setResponseMsg("");
            } else {
                // 未查询到数据或查询出错
                log.info("未查询成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            log.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }


    @Override
    public ApiResDto<List<PreAmountQueryResDto>> queryPreOrderAmt(String payerAccount) {
        ApiResDto<List<PreAmountQueryResDto>> apiResDto = new ApiResDto<List<PreAmountQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            log.info("开始调用跨境结算进行前置文件可用金额信息查询");
            Map<String, Object> inputParam = new HashMap<String, Object>();
            inputParam.put("payerAccount", payerAccount);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.PRE_ORDER, QUERY_PRE_ORDER_AMT, new Object[]{inputParam});
            log.info("查询返回参数,outputParam:********");
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                List<PreAmountQueryResDto> result = JSONObject.parseArray(outputParam.get("context"), PreAmountQueryResDto.class);
                apiResDto.setResult(result);
                apiResDto.setResponseMsg("");
            } else {
                // 未查询到数据或查询出错
                log.info("未查询成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            log.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }


    @Override
    public Map<String, Object> collAndPaymentApply(PreOrderCollAndPaymentApplyReqDto params) {
        // 调用eppcbs的收结汇付款申请接口
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> reqParams = BeanConverterUtil.beanToMap(params);

            log.info("收结汇付款提交申请入参:{}", reqParams);
            Map<String, Object> response = generalRsfServiceObj.invoke(ApiCodeConstant.PRE_ORDER, COLL_PAYMENT_APPLY, reqParams);
            log.info("收结汇付款提交申请返回参数:{}", response);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);

            result.put(EPPSCBPConstants.RESPONSE_CODE, responseCode);
            result.put(EPPSCBPConstants.RESPONSE_MSG, responseMessage);
        } catch (Exception ex) {
            log.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));

            result.put(EPPSCBPConstants.RESPONSE_CODE, EPPSCBPConstants.ERROR_CODE);
            result.put(EPPSCBPConstants.RESPONSE_MSG, EPPSCBPConstants.SYSTEM_ERROR);
        }

        return result;
    }


    /**
     * 功能描述: 上传收结汇文件<br>
     * 〈功能详细描述〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private String uploadCollSett(MultipartFile targetFile, String bucketName) throws IOException {
        String mimeType = "";
        String fileType = "";
        String fileName = targetFile.getOriginalFilename();
        // 区分文件类型
        if (fileName.endsWith(CommonConstant.XLS)) {
            mimeType = OSSMimeType.XLS;
            fileType = CommonConstant.XLS;
        } else if (fileName.endsWith(CommonConstant.XLSX)) {
            mimeType = OSSMimeType.XLSX;
            fileType = CommonConstant.XLSX;
        }
        OSSUploadParams params = new OSSUploadParams(bucketName, targetFile, mimeType);
        // 上传路径
        StringBuilder remotePath = new StringBuilder("R").append(new Date().getTime()).append(".").append(fileType);
        params.setRemotePath(remotePath.toString());
        params.setValidDays(180);
        log.info("明细文件上传参数:{}", params);
        String fileId = OSSClientUtil.uploadStream(params);
        return fileId;
    }
}
