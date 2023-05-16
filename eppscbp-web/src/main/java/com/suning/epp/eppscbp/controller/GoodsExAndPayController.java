/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: GoodsExAndPayController.java
 * Author:   17061545
 * Date:     2018年3月20日 下午2:07:24
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.suning.epp.eppscbp.common.enums.CbpProductType;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.MerchantAccountCharacterEnum;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderExRateRefreshDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.GoodsExAndPayService;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.util.AESUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈货物贸易单笔购付汇〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@SessionAttributes("merchantInfoList")
@RequestMapping("/goodsTrade/goodsExAndPay/")
public class GoodsExAndPayController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsExAndPayController.class);

    private static final String GOODS_EXANDPAY_INIT = "screen/goodsTrade/goodsExAndPay/init";

    private static final String GOODS_EXANDPAY_CONFIRM = "screen/goodsTrade/goodsExAndPay/confirm";

    // 错误页面
    private static final String GOODS_PAY_ERROR = "screen/goodsTrade/error";
    
    private static final String SESSION_KEY = "merchantInfoList";

    @Autowired
    private GoodsExAndPayService goodsExAndPayService;
    @Autowired
    private ApplyFileUploadService applyFileUploadService;
    @Autowired
    private UnifiedReceiptService unifiedReceiptService; 
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
        LOGGER.info("货物贸易单笔购付汇初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.addObject(EPPSCBPConstants.CRITERIA, new OrderApplyAcquireDto());
        mav.setViewName(GOODS_EXANDPAY_INIT);
        try {
            LOGGER.info("获取收款方商户信息开始:{}", request.getRemoteUser());
            // 校验session是否过期
            if (!model.containsAttribute(SESSION_KEY)) {
                // 获取信息加入session
                String userNo = request.getRemoteUser();
                ApiResDto<String> apiResDto = merchantInfoService.queryMerchantMegForSearch(userNo, MerchantAccountCharacterEnum.PAYEE_MERCHANT_CODE.getCode(), CbpProductType.SINGLE_GFH.getCode());
                if (apiResDto.isSuccess()) {
                    model.addAttribute(SESSION_KEY, apiResDto.getResult());                   
                }
            }
        } catch (Exception ex) {
            LOGGER.error("获取收款方商户信息异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("获取收款方商户信息结束");
        LOGGER.info("货物贸易单笔购付汇初始化结束");
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
            LOGGER.info("货物贸易单笔购付汇提交开始:{}", criteria.toString());
            String userNo = request.getRemoteUser();
            if (StringUtil.isNotNull(userNo)) {
                criteria.setPayerAccount(userNo);
            }
            ApiResDto<OrderlAcquireResponseDto> apiResDto = goodsExAndPayService.submiteSettleOrder(criteria);
            if (apiResDto.isSuccess()) {
                // 提交订单成功
                LOGGER.info("货物贸易单笔购付汇申请返回成功");
                mav.addObject(EPPSCBPConstants.CRITERIA, apiResDto.getResult());
                mav.setViewName(GOODS_EXANDPAY_CONFIRM);
            } else {
                // 提交订单失败
                LOGGER.info("货物贸易单笔购付汇申请失败状态:{}-{}", apiResDto.getResponseCode(), apiResDto.getResponseMsg());
                mav.addObject(EPPSCBPConstants.CRITERIA, criteria);
                mav.addObject(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
                mav.setViewName(GOODS_EXANDPAY_INIT);
            }
            LOGGER.info("货物贸易单笔购付汇提交结束");
        } catch (Exception ex) {
            LOGGER.error("货物贸易单笔购付汇提交开始异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return mav;
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
                mav.setViewName(GOODS_PAY_ERROR);
            } else {
                // 刷新汇率成功
                mav.addObject(EPPSCBPConstants.CRITERIA, orderlAcquireResponseDto);
                mav.setViewName(GOODS_EXANDPAY_CONFIRM);
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
            ExitQueryBalance balance = goodsExAndPayService.queryBalance(userNo, EPPSCBPConstants.ACCSRCCOD_RMB, EPPSCBPConstants.CUR_CODE_CNY);
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
                ApiResDto<String> apiResDto = goodsExAndPayService.eppSecretKeyRsfServer();
                if (apiResDto.isSuccess()) {
                    result.put(EPPSCBPConstants.KEY, apiResDto.getResult());
                } else {
                    result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                    result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseCode());
                }
                LOGGER.info("查询会员AES结束:{}", request.getRemoteUser());
            }
        } catch (Exception ex) {
            LOGGER.error("货物贸易单笔购付汇提交开始异常:{}", ExceptionUtils.getStackTrace(ex));
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
        LOGGER.info("确认支付业务单号:{},密钥key:{},password:{}", businessNo, key, password);
        Map<String, String> result = new HashMap<String, String>();
        result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        boolean payFlg = false;
        try {
            LOGGER.info("验证支付密码正确与否开始:{}", request.getRemoteUser());
            String userNo = request.getRemoteUser();
            ApiResDto<String> apiResDto = goodsExAndPayService.validatePaymentPassword(userNo, AESUtil.encryptBase64DecorateAES(password, key));
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
                apiResDto = goodsExAndPayService.confirmPayment(orderOperationDto);
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
            LOGGER.error("货物贸易单笔购付汇确认支付异常:{}", ExceptionUtils.getStackTrace(ex));
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
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, @RequestParam("detailAmount") int detailAmount, @RequestParam("payAmt") double payAmt, @RequestParam("curType") String curType, @RequestParam("exchangeType") String exchangeType, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("申请明细文件上传开始.exchangeType:{}", exchangeType);
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            res = applyFileUploadService.uploadFile(targetFile, ChargeBizType.TYPE_GOODS_TRADE.getCode(), detailAmount, payAmt, request.getRemoteUser(), curType, exchangeType);
        } catch (ExcelForamatException e) {
            LOGGER.error("文件上传出错,错误ExcelForamatException:{}", e);
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());       
        } catch (Exception e) {
            LOGGER.error("文件上传出错,错误Exception:{}", e);
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("申请明细文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }
}