/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: StudyBatchExAndPayController.java
 * Author:   17061545
 * Date:     2018年3月20日 下午2:08:57
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.TradeCoreConstant;
import com.suning.epp.eppscbp.common.enums.CbpProductType;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.TradeMode;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.BatchOrderRemitQueryDto;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderExRateRefreshDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchTradeOrderRemitQueryResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.rsf.service.OrderQueryService;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.StudyBatchExAndPayService;
import com.suning.epp.eppscbp.util.AESUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈留学缴费批量购付汇〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/overseasPay/studyBatchExAndPay/")
public class StudyBatchExAndPayController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudyBatchExAndPayController.class);

    private static final String STUDY_BATCHEXANDPAY_INIT = "screen/overseasPay/studyBatchExAndPay/init";

    private static final String STUDY_BATCHEXANDPAY_CONFIRM = "screen/overseasPay/studyBatchExAndPay/confirm";

    // 系统异常页面
    private static final String OVERSEASPAY_PAY_ERROR = "screen/overseasPay/error";

    @Autowired
    private StudyBatchExAndPayService batchExAndPayService;
    @Autowired
    private ApplyFileUploadService applyFileUploadService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private UnifiedReceiptService unifiedReceiptService;

    /**
     * 功能描述: <br>
     * 〈页面初期化〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init() {
        LOGGER.info("留学缴费批量购付汇初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(STUDY_BATCHEXANDPAY_INIT);
        LOGGER.info("留学缴费批量购付汇初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈提交订单〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("applySubmit")
    public ModelAndView applySubmit(@ModelAttribute("fileAddress") String fileAddress, @ModelAttribute("filePath") String filePath, int detailAmount, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            LOGGER.info("留学缴费批量购付汇提交开始:{}-{}", fileAddress, filePath);
            String userNo = request.getRemoteUser();
            OrderApplyAcquireDto orderApplyAcquireDto = new OrderApplyAcquireDto();
            if (StringUtil.isNotNull(userNo)) {
                orderApplyAcquireDto.setPayerAccount(userNo);
            }
            orderApplyAcquireDto.setPlatformCode(EPPSCBPConstants.PLATFORMCODE);
            orderApplyAcquireDto.setProductType(CbpProductType.BATCH_GFH.getCode());
            orderApplyAcquireDto.setBizType(ChargeBizType.TYPE_ABROAD_EDUCATION.getCode());
            orderApplyAcquireDto.setTradeMode(TradeMode.TRADE_MODE_02.getCode());
            orderApplyAcquireDto.setFileAddress(fileAddress);
            orderApplyAcquireDto.setDetailAmount(detailAmount);
            ApiResDto<OrderlAcquireResponseDto> apiResDto = batchExAndPayService.submiteSettleOrder(orderApplyAcquireDto);
            if (apiResDto.isSuccess()) {
                LOGGER.info("留学缴费批量购付汇申请返回成功");
                mav.addObject(EPPSCBPConstants.CRITERIA, apiResDto.getResult());
                DalPage pageInfo = new DalPage();
                pageInfo.setCurrentPage(1);
                mav.addObject(EPPSCBPConstants.PAGE, pageInfo);
                mav.setViewName(STUDY_BATCHEXANDPAY_CONFIRM);
            } else {
                // 提交订单失败
                LOGGER.error("留学缴费批量购付汇申请未成功状态:{}-{}", apiResDto.getResponseCode(), apiResDto.getResponseMsg());
                mav.addObject("fileAddress", fileAddress);
                mav.addObject("filePath", filePath);
                mav.addObject("detailAmount", detailAmount);
                mav.addObject(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
                mav.setViewName(STUDY_BATCHEXANDPAY_INIT);
            }
            LOGGER.info("留学缴费批量购付汇提交结束");
        } catch (Exception ex) {
            LOGGER.error("留学缴费批量购付汇提交开始异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈明细汇出查询〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("query")
    @ResponseBody
    public String query(@ModelAttribute("currentPage") String currentPage, @ModelAttribute("businessNo") String businessNo, HttpServletRequest request) {
        LOGGER.info("收到支付信息查询请求,当前页:{},页面长度:{},业务单号:{}", currentPage, businessNo);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        try {
            BatchOrderRemitQueryDto requestDto = new BatchOrderRemitQueryDto();
            requestDto.setBusinessNo(businessNo);
            requestDto.setCurrentPage(currentPage);
            requestDto.setPayerAccount(request.getRemoteUser());
            LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
            ApiResDto<List<BatchTradeOrderRemitQueryResDto>> apiResDto = orderQueryService.batchOrderRemitQuery(requestDto);
            LOGGER.info("rsf查询结果,responseParam:{}", apiResDto.toString());
            if (apiResDto.isSuccess()) {
                result.put(EPPSCBPConstants.RESULT_LIST, apiResDto.getResult());
                LOGGER.info("获取到查询结果,resultList:{}", apiResDto.getResult().toString());
                result.put(EPPSCBPConstants.PAGE, apiResDto.getPage());
                LOGGER.info("获取到分页信息,page:{}", apiResDto.getPage().toString());
            } else {
                // 未查询到数据或查询出错
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                result.put(CommonConstant.RESPONSE_MESSAGE, "系统异常，请稍后再试。");
            }
        } catch (Exception ex) {
            LOGGER.error("留学缴费批量购付汇支付信息查询开始异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈刷新汇率〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "refreshRate", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView refreshRate(HttpServletRequest request, String businessNo) {
        ModelAndView mav = new ModelAndView();
        try {
            LOGGER.info("汇率刷新开始:{}", businessNo);
            OrderExRateRefreshDto orderExRateRefreshDto = new OrderExRateRefreshDto();
            orderExRateRefreshDto.setPayerAccount(request.getRemoteUser());
            orderExRateRefreshDto.setBusinessNo(businessNo);
            orderExRateRefreshDto.setFlag(CommonConstant.Y);
            OrderlAcquireResponseDto orderlAcquireResponseDto = unifiedReceiptService.refreshRate(orderExRateRefreshDto);
            if (orderlAcquireResponseDto == null) {
                // 刷新汇率失败
                mav.addObject(EPPSCBPConstants.BUSINESS_NO, businessNo);
                mav.addObject(EPPSCBPConstants.PRODUCT_TYPE, CbpProductType.BATCH_GFH.getCode());
                mav.setViewName(OVERSEASPAY_PAY_ERROR);
            } else {
                // 刷新汇率成功
                DalPage pageInfo = new DalPage();
                pageInfo.setCurrentPage(1);
                mav.addObject(EPPSCBPConstants.PAGE, pageInfo);
                mav.addObject(EPPSCBPConstants.CRITERIA, orderlAcquireResponseDto);
                mav.setViewName(STUDY_BATCHEXANDPAY_CONFIRM);
            }
            LOGGER.info("汇率刷新结束");
        } catch (Exception ex) {
            LOGGER.error("汇率刷新异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈去支付〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "submitPayment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String submitePayment(HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        boolean payFlg = false;
        try {
            LOGGER.info("查询商户人民币余额开始:{}", request.getRemoteUser());
            String userNo = request.getRemoteUser();
            ExitQueryBalance balance = batchExAndPayService.queryBalance(userNo, EPPSCBPConstants.ACCSRCCOD_RMB, EPPSCBPConstants.CUR_CODE_CNY);
            if (TradeCoreConstant.SUCCESS.equals(balance.getRspCode())) {
                String balanceStr = StringUtil.formatMoney(balance.getUseAmt());
                result.put(EPPSCBPConstants.BALANCE, balanceStr);
                payFlg = true;
            } else {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                result.put(CommonConstant.RESPONSE_MESSAGE, balance.getRspMsg());
            }
            LOGGER.info("查询商户人民币余额结束:{}", request.getRemoteUser());
            if (payFlg) {
                LOGGER.info("查询会员AES开始:{}", request.getRemoteUser());
                ApiResDto<String> apiResDto = batchExAndPayService.eppSecretKeyRsfServer();
                if (apiResDto.isSuccess()) {
                    result.put(EPPSCBPConstants.KEY, apiResDto.getResult());
                } else {
                    result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                    result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseCode());
                }
                LOGGER.info("查询会员AES结束:{}", request.getRemoteUser());
            }
        } catch (Exception ex) {
            LOGGER.error("留学缴费批量提交异常:{}", ExceptionUtils.getStackTrace(ex));
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, CommonConstant.SYSTEM_ERROR_MES);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈确认支付〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "confirmPayment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String confirmPayment(String businessNo, String key, String password, HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        boolean payFlg = false;
        try {
            LOGGER.info("验证支付密码正确与否开始:{}", request.getRemoteUser());
            String userNo = request.getRemoteUser();
            ApiResDto<String> apiResDto = batchExAndPayService.validatePaymentPassword(userNo, AESUtil.encryptBase64DecorateAES(password, key));
            if (apiResDto.isSuccess()) {
                payFlg = true;
            } else {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
            }
            LOGGER.info("验证支付密码正确与否结束:{}", request.getRemoteUser());
            if (payFlg) {
                LOGGER.info("发起支付操作,业务单号:{}", businessNo);
                OrderOperationDto orderOperationDto = new OrderOperationDto();
                orderOperationDto.setBusinessNo(businessNo);
                orderOperationDto.setPayerAccount(userNo);
                apiResDto = batchExAndPayService.confirmPayment(orderOperationDto);
                if (!apiResDto.isSuccess()) {
                    if (CommonConstant.ORDER_PAY_EXCEPTION.equals(apiResDto.getResponseCode())) {
                        result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_PAY_EXCIPTION);
                    } else {
                        result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_PAY_FAIL);
                    }
                    result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
                }
                LOGGER.info("发起支付操作结束,业务单号:{}", businessNo);
            }
        } catch (Exception ex) {
            LOGGER.error("留学缴费批量购付汇确认支付异常:{}", ExceptionUtils.getStackTrace(ex));
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈上传明细文件〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileSubmit", produces = "application/json;charset=UTF-8")
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("申请明细文件上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            res = applyFileUploadService.uploadBatchFile(targetFile, ChargeBizType.TYPE_ABROAD_EDUCATION.getCode(), request.getRemoteUser());
        } catch (ExcelForamatException e) {
            LOGGER.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());       
        } catch (Exception e) {
            LOGGER.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("申请明细文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }
}
