package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.CapitalGrantDto;
import com.suning.epp.eppscbp.dto.req.DomesticMerchantInfoReqDto;
import com.suning.epp.eppscbp.dto.req.OrderCalculateDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentOrderQueryAndCalculateResDto;
import com.suning.epp.eppscbp.dto.res.DomesticMerchantInfoResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.rsf.service.CapitalGrantService;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.BatchPaymentService;
import com.suning.epp.eppscbp.service.DomesticMerchantInfoService;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 资金批付
 *
 * @author 88412423
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/cpBatchPayment/cpBatchPaymentInit/")
public class CpBatchPaymentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CpBatchPaymentController.class);

    private static final String BATCH_PAYMENT = "screen/cpBatchPayment/cpBatchPaymentInit";

    @Autowired
    private CapitalGrantService capitalGrantService;

    @Autowired
    private ApplyFileUploadService applyFileUploadService;

    @Autowired
    private DomesticMerchantInfoService domesticMerchantInfoService;

    @Autowired
    private BatchPaymentService batchPaymentService;

    /**
     * 功能描述: <br>
     * 〈页面初期化〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init(HttpServletRequest request) {
        LOGGER.info("资金批付初始化开始");
        ModelAndView mav = new ModelAndView(BATCH_PAYMENT);
        LOGGER.info("资金批付初始化结束");
        CapitalGrantDto capitalGrantDto = new CapitalGrantDto();
        capitalGrantDto.setPayerAccount(request.getRemoteUser());
        capitalGrantDto.setPlatformCode(CommonConstant.CCP_CODE);
        ApiResDto<String> capitalGrant = capitalGrantService.getCapitalGrant(capitalGrantDto);
        LOGGER.info("资金批付查询结果:{}", capitalGrant);
        if (capitalGrant.isSuccess()) {
            mav.addObject("prePayAmount", StringUtil.formatMoney(Long.valueOf(capitalGrant.getResult())));
        }
        LOGGER.info("资金批付初始化结束");
        return mav;
    }

    /**
     * 待批付查询
     *
     * @param requestDto
     * @param request
     * @return
     */
    @RequestMapping(value = "batchPaymentQuery", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchPaymentQuery(@RequestParam("payeeMerchantCode") String payeeMerchantCode, @RequestParam("accountName") String accountName, @RequestParam("bankAccount") String bankAccount, HttpServletRequest request) {
        LOGGER.info("待批付商户查询开始,payeeMerchantCode:{},accountName:{},bankAccount:{}", payeeMerchantCode, accountName, bankAccount);
        CapitalGrantDto capitalGrantDto = new CapitalGrantDto();
        capitalGrantDto.setPayerAccount(request.getRemoteUser());

        DomesticMerchantInfoReqDto requestDto = new DomesticMerchantInfoReqDto();
        requestDto.setPayeeMerchantCode(payeeMerchantCode);
        requestDto.setBankAccountNo(bankAccount);
        requestDto.setBankAccountName(accountName);
        requestDto.setPayerAccount(request.getRemoteUser());
        requestDto.setPlatformCode(CommonConstant.CBP_CODE);

        LOGGER.info("待批付商户查询开始,requestDto:{}", requestDto);
        ApiResDto<List<DomesticMerchantInfoResDto>> apiResDto = domesticMerchantInfoService.merchantInfoQuery(requestDto);

        Map<String, Object> result = new HashMap<String, Object>();
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
            result.put("merchantList", apiResDto.getResult());
        } else {
            LOGGER.info("收结汇监管文件返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("待批付商户查询结束,result:{}", result);
        return JSON.toJSONString(result);
    }

    /**
     * 
     * 功能描述: <br>
     * 〈批付分笔计算〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "ordersCalculate", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> ordersCalculate(HttpServletRequest request, @RequestBody OrderCalculateDto requestDto) {
        LOGGER.info("批付分笔计算开始,requestDto:{}", requestDto);
        Map<String, Object> result = new HashMap<String, Object>();
        // 进行计算
        try {
            OrderCalculateDto apiResDto = batchPaymentService.cpOrdersCalculate(requestDto);
            LOGGER.info("批付查询请求触发跨境返回:{}", apiResDto);
            if (apiResDto.getFlag() == true) {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
                result.put(EPPSCBPConstants.AMOUNT, requestDto.getAmountCount());
                result.put(EPPSCBPConstants.NUMBER, requestDto.getNumberCount());
                result.put(EPPSCBPConstants.RESULT_LIST, requestDto.getDetails());
            } else {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                result.put(CommonConstant.RESPONSE_MESSAGE, "出款金额大于可批付金额");
            }

        } catch (Exception e) {
            LOGGER.error("批付分笔计算发送异常:{}", ExceptionUtils.getFullStackTrace(e));
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, EPPSCBPConstants.SYSTEM_ERROR);
        }

        LOGGER.info("批付分笔计算结束");
        return result;
    }

    /**
     * 进行批付出款
     *
     * @param requestDto
     * @param request
     * @return
     */
    @RequestMapping(value = "ordersAudit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String ordersAudit(@RequestBody OrdersAuditRequestDto requestDto, HttpServletRequest request) {
        LOGGER.info("批付出款开始，入参:{}", requestDto);
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取户头号
        String payerAccount = request.getRemoteUser();

        requestDto.setPayerAccount(payerAccount);
        LOGGER.info("触发rsf进行批付出款,入参：{}", payerAccount);
        ApiResDto<String> apiResDto = capitalGrantService.batchPayment(requestDto);
        LOGGER.info("触发rsf进行批付返回信息,response:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("批付申请提交返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
        }
        result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        return JSON.toJSONString(result);
    }

    /**
     * 批量导入批付明细
     *
     * @param requestDto
     * @param request
     * @return
     */
    @RequestMapping(value = "importBatchPaymentDetails", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importBatchPaymentDetails(@RequestParam("fileAddress") String fileAddress, HttpServletRequest request) {
        LOGGER.info("批量导入批付明细开始，入参:{}", fileAddress);
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取户头号
        String payerAccount = request.getRemoteUser();

        LOGGER.info("触发rsf进行批付出款,入参：{}", payerAccount);
        ApiResDto<String> apiResDto = batchPaymentService.batchPaymentUpload(null, payerAccount, fileAddress, CommonConstant.CCP_CODE);
        LOGGER.info("触发rsf进行批付返回信息,response:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("批量导入批付明细返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
        }
        result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈上传明细文件校验〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileSubmit", produces = "application/json;charset=UTF-8")
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, @RequestParam("prePayAmount") String prePayAmount, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("资金批付申请明细文件上传开始,prePayAmount:{}", prePayAmount);
        response.setContentType("text/html;charset=utf-8");

        // 获取待批付金额
        double amount = Double.parseDouble(prePayAmount);

        FileUploadResDto res = new FileUploadResDto();
        try {
            res = applyFileUploadService.uploadBatchFiles(targetFile, amount, request.getRemoteUser());
        } catch (ExcelForamatException e) {
            LOGGER.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("资金批付文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }
    
    /**
     * 
     * 功能描述: <br>
     * 〈批付文件解析并计算〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "bpOrdersParseAndCalculate", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> bpOrdersParseAndCalculate(@RequestParam("fileAddress") String fileAddress, @RequestParam("prePayAmountHide") String prePayAmount, HttpServletRequest request) {
        LOGGER.info("解析excel、计算批付总金额开始");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	// 请求跨境，解析excel，返回list,进行计算
        	ApiResDto<BatchPaymentOrderQueryAndCalculateResDto> apiResDto = batchPaymentService.bpOrdersParseAndCalculate(null, request.getRemoteUser(), fileAddress, CommonConstant.CCP_CODE, prePayAmount, null);
            LOGGER.info("解析excel、计算批付总金额请求触发跨境返回:{}", apiResDto);
            BatchPaymentOrderQueryAndCalculateResDto dto = apiResDto.getResult();
            apiResDto.getResult();
            if (apiResDto.getFlag() == true) {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
                result.put(EPPSCBPConstants.AMOUNT, dto.getAmountCount());
                result.put(EPPSCBPConstants.NUMBER, dto.getNumberCount());
                result.put(EPPSCBPConstants.RESULT_LIST, dto.getDetails());
                result.put(EPPSCBPConstants.BUSINESS_NO, dto.getBusinessNo());
                result.put(EPPSCBPConstants.FILE_ADDRESS, dto.getFileAddress());
            } else {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
            }

        } catch (Exception e) {
            LOGGER.error("解析excel、计算批付总金额发送异常:{}", ExceptionUtils.getFullStackTrace(e));
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, EPPSCBPConstants.SYSTEM_ERROR);
        }

        LOGGER.info("解析excel、计算批付总金额结束");
        return result;
    }

}
