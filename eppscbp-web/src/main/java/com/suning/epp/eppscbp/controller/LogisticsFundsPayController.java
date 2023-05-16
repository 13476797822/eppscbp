package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suning.epp.eppscbp.common.enums.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.TradeCoreConstant;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.GoodsRMBPayService;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.util.AESUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈物流款项单笔跨境人民币结算——国际物流付款〉<br>
 * 〈功能详细描述〉
 *
 * @author 88397357
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@SessionAttributes("merchantInfoListRMB")
@RequestMapping("/logisticsSettlement/logisticsFundsPay/")
public class LogisticsFundsPayController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsFundsPayController.class);

    private static final String LOGISTICS_PAY_INIT = "screen/logisticsSettlement/logisticsFundsPay/init";

    private static final String LOGISTICS_PAY_CONFIRM = "screen/logisticsSettlement/logisticsFundsPay/confirm";
    
    private static final String SESSION_KEY = "merchantInfoListRMB";
    
    //支付结果页
    private static final String LOGISTICS_PAY_RESULT = "screen/logisticsSettlement/logisticsFundsPay/result";
    
    @Autowired
    private GoodsRMBPayService goodsRMBPayService;
    
    @Autowired
    private ApplyFileUploadService applyFileUploadService;
    
    @Autowired
    private MerchantInfoService merchantInfoService;
    /**
     * 功能描述: <br>
     * 〈页面初期化〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init(Model model, HttpServletRequest request) {
        LOGGER.info("国际物流付款初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.addObject(EPPSCBPConstants.LOGISTICS_TYPE, LogisticsType.getDescriptions());
        mav.addObject(EPPSCBPConstants.CRITERIA, new OrderApplyAcquireDto());
        mav.setViewName(LOGISTICS_PAY_INIT);
        try {
            LOGGER.info("获取收款方商户信息开始:{}", request.getRemoteUser());
            // 校验session是否过期
            if (!model.containsAttribute(SESSION_KEY)) {
                // 获取信息加入session
                String userNo = request.getRemoteUser();
                ApiResDto<String> apiResDto = merchantInfoService.queryMerchantMegForSearch(userNo, MerchantAccountCharacterEnum.PAYEE_MERCHANT_CODE.getCode(), CbpProductType.SINGLE_RMB.getCode());
                if (apiResDto.isSuccess()) {
                    model.addAttribute(SESSION_KEY, apiResDto.getResult());                   
                }
            }
        } catch (Exception ex) {
            LOGGER.error("获取收款方商户信息异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("获取收款方商户信息结束");
        LOGGER.info("国际物流付款初始化结束");
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
    public ModelAndView applySubmit(@ModelAttribute("criteria") OrderApplyAcquireDto criteria, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            LOGGER.info("国际物流付款提交开始:{}", criteria.toString());
            String userNo = request.getRemoteUser();
            if (StringUtil.isNotNull(userNo)) {
                criteria.setPayerAccount(userNo);
            }
            criteria.setBizType(ChargeBizType.TYPE_LOGISTICS.getCode());
            criteria.setTradeMode(TradeMode.TRADE_MODE_02.getCode());
            criteria.setLogisticsType(LogisticsType.getCodeFromDescription(criteria.getLogisticsType()));
            ApiResDto<OrderlAcquireResponseDto> apiResDto = goodsRMBPayService.submiteSettleOrder(criteria);
            if (apiResDto.isSuccess()) {
                // 提交订单成功
                LOGGER.info("国际物流付款申请返回成功");
                mav.addObject(EPPSCBPConstants.CRITERIA, apiResDto.getResult());
                mav.setViewName(LOGISTICS_PAY_CONFIRM);
            } else {
                // 提交订单失败
                LOGGER.info("国际物流付款失败状态:{}-{}", apiResDto.getResponseCode(), apiResDto.getResponseMsg());
                mav.addObject(EPPSCBPConstants.CRITERIA, criteria);
                mav.addObject(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
                mav.setViewName(LOGISTICS_PAY_INIT);
            }
            LOGGER.info("国际物流付款提交结束");
        } catch (Exception ex) {
            LOGGER.error("国际物流付款提交开始异常:{}", ExceptionUtils.getStackTrace(ex));
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
            ExitQueryBalance balance = goodsRMBPayService.queryBalance(userNo, EPPSCBPConstants.ACCSRCCOD_RMB, EPPSCBPConstants.CUR_CODE_CNY);
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
                ApiResDto<String> apiResDto = goodsRMBPayService.eppSecretKeyRsfServer();
                if (apiResDto.isSuccess()) {
                    result.put(EPPSCBPConstants.KEY, apiResDto.getResult());
                } else {
                    result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                    result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseCode());
                }
                LOGGER.info("查询会员AES结束:{}", request.getRemoteUser());
            }
        } catch (Exception ex) {
            LOGGER.error("国际物流付款提交异常:{}", ExceptionUtils.getStackTrace(ex));
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
            ApiResDto<String> apiResDto = goodsRMBPayService.validatePaymentPassword(userNo, AESUtil.encryptBase64DecorateAES(password, key));
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
                apiResDto = goodsRMBPayService.confirmPayment(orderOperationDto);
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
            LOGGER.error("国际物流付款确认支付异常:{}", ExceptionUtils.getStackTrace(ex));
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
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, @RequestParam("detailAmount") int detailAmount, @RequestParam("payAmt") double payAmt, @RequestParam("curType") String curType, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("申请明细文件上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            res = applyFileUploadService.uploadFile(targetFile, ChargeBizType.TYPE_LOGISTICS.getCode(), detailAmount, payAmt, request.getRemoteUser(), curType, "");
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
    
    /**
     * 功能描述: <br>
     * 〈支付结果页面〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("payResult")
    public ModelAndView payResult(String payResult, String meg) {
        LOGGER.info("国际物流付款支付结果payResult:{},meg:{}");
        ModelAndView mav = new ModelAndView();
        mav.addObject(EPPSCBPConstants.PAY_RESULT, payResult);
        mav.addObject(EPPSCBPConstants.MEG, meg);
        mav.setViewName(LOGISTICS_PAY_RESULT);
        LOGGER.info("国际物流付款支付结果展示");
        return mav;
    }

}
