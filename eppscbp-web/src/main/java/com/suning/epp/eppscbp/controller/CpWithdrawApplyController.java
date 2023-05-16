/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpWithdrawApplyController.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.controller;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.IsEppFundType;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.CpWithdrawApplyDto;
import com.suning.epp.eppscbp.dto.res.*;
import com.suning.epp.eppscbp.rsf.service.CpWithdrawApplyService;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈提现申请〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/cpWithdrawApply/cpWithdrawApplyInit/")
public class CpWithdrawApplyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpWithdrawApplyController.class);

    private static final String INIT_QUERY = "screen/cpWithdrawApply/cpWithdrawApplyInit";

    @Autowired
    private CpWithdrawApplyService cpWithdrawApplyService;

    @Autowired
    private ApplyFileUploadService applyFileUploadService;

    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * 初始化主视图
     *
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.CUR_TYPE, CurType.getAllDescriptionWithoutAll());
        return mvn;
    }

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
        LOGGER.info("收结汇引导初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("收结汇引导初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈提现币种查询〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "currencyQuery", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String currencyQuery(@RequestParam("currency") String currency, HttpServletRequest request) {
        LOGGER.info("提现币种查询开始");
        String currencyCode = CurType.getCodeFromDescription(currency);

        ApiResDto<ColAndSettleMultiInfoDto> res = new ApiResDto<ColAndSettleMultiInfoDto>();
        try {
            res = merchantInfoService.arrivalQuery(currencyCode, request.getRemoteUser());
        } catch (Exception e) {
            LOGGER.error("提现币种查询出错,错误:{}", ExceptionUtils.getStackTrace(e));
        }
        LOGGER.info("提现币种查询结束,{}", res);

        return JSON.toJSONString(res);
    }

    /**
     * 功能描述: <br>
     * 〈是否包含易付宝商户资金查询〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "outBatchQuery", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String outBatchQuery(@RequestParam("currency") String currency, HttpServletRequest request) {
        LOGGER.info("查询是否包含易付宝商户资金开始");
        String currencyCode = CurType.getCodeFromDescription(currency);

        ApiResDto<CpWithdrawApplyInfoDto> res = new ApiResDto<CpWithdrawApplyInfoDto>();

        try {
            res = cpWithdrawApplyService.outBatchQuery(currencyCode, request.getRemoteUser());
        } catch (Exception e) {
            LOGGER.error("查询是否包含易付宝商户资金出错,错误:{}", ExceptionUtils.getStackTrace(e));
        }
        LOGGER.info("查询是否包含易付宝商户资金结束,{}", res);

        return JSON.toJSONString(res);
    }

    /**
     * 功能描述: <br>
     * 〈出账批次查询〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "batchQuery", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchQuery(@RequestParam("outAccountBatch") String outAccountBatch, @RequestParam("currency") String currency, HttpServletRequest request) {
        LOGGER.info("出账批次查询开始");
        String currencyCode = CurType.getCodeFromDescription(currency);

        ApiResDto<CpWithdrawApplyInfoDto> res = new ApiResDto<CpWithdrawApplyInfoDto>();

        try {
            res = cpWithdrawApplyService.batchQuery(outAccountBatch, currencyCode, request.getRemoteUser());
        } catch (Exception e) {
            LOGGER.error("出账批次查询出错,错误:{}", ExceptionUtils.getStackTrace(e));
        }
        LOGGER.info("出账批次查询结束,{}", res);

        return JSON.toJSONString(res);
    }

    /**
     * 功能描述: <br>
     * 〈提交提现申请〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "applySubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String applySubmit(@RequestBody CpWithdrawApplyDto apply, HttpServletRequest request) {
        LOGGER.info("提现申请提交开始:{}", apply);
        String isEppFund = IsEppFundType.getCodeFromDescription(apply.getIsEppFund());
        String currencyCode = CurType.getCodeFromDescription(apply.getWithdrawCur());
        ApiResDto<CpWithdrawApplyResDto> res = new ApiResDto<CpWithdrawApplyResDto>();
        apply.setPayerAccount(request.getRemoteUser());//设置商户户头号
        apply.setIsEppFund(isEppFund);//是否包含易付宝商户资金
        apply.setWithdrawCur(currencyCode);//提现币种
        try {
            res = cpWithdrawApplyService.submitWithdrawApply(apply);
        } catch (Exception e) {
            LOGGER.error("提现申请提交出错,错误:{}", ExceptionUtils.getStackTrace(e));
        }
        return JSON.toJSONString(res);
    }

    /**
     * 功能描述: <br>
     * 〈上传提现申请明细文件〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileSubmit", produces = "application/json;charset=UTF-8")
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, @RequestParam("currency") String currency, @RequestParam("receiveAmt") String receiveAmt, @RequestParam("collAndSettleFlag") String collAndSettleFlag, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("提现申请明细文件上传开始,currency:{},receiveAmt:{},collAndSettleFlag:{}", currency, receiveAmt, collAndSettleFlag);
        response.setContentType("text/html;charset=utf-8");

        // 获取金额
        double receiveAmtReal;
        if (receiveAmt.contains(";")) {
            receiveAmtReal = Double.valueOf(receiveAmt.split(";")[0]);
        } else {
            receiveAmtReal = Double.valueOf(receiveAmt);
        }

        String curType = CurType.getCodeFromDescription(currency);

        LOGGER.info("发起文件校验,curType:{},receiveAmtReal:{}", curType, receiveAmtReal);

        FileUploadResDto res = new FileUploadResDto();
        try {
            res = applyFileUploadService.uploadForCollAndSettle(targetFile, receiveAmtReal, request.getRemoteUser(), curType, collAndSettleFlag);
        } catch (ExcelForamatException e) {
            LOGGER.error("提现申请文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("提现申请文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("提现申请明细文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }

}
