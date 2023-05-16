package com.suning.epp.eppscbp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suning.epp.eppscbp.common.enums.LogisticsType;
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
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.CollAndSettleApplyDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.ColAndSettleMultiInfoDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.MerchantInfoQueryResDto;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.CollAndSettleFileImportService;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈物流款项结算申请：国际物流收款〉<br>
 * 〈功能详细描述〉
 *
 * @author 88397357
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/logisticsSettlement/fileImport/")
public class LogisticsSettlementFileImportController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsSettlementFileImportController.class);

    private static final String INIT = "screen/logisticsSettlement/fileImport/apply";
    
    private static final String LOGISTICS_RESULT = "screen/logisticsSettlement/fileImport/result";

    @Autowired
    private ApplyFileUploadService applyFileUploadService;

    @Autowired
    private CollAndSettleFileImportService collAndSettleFileImportService;

    @Autowired
    private MerchantInfoService merchantInfoService;
    /**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT);
        mvn.addObject(EPPSCBPConstants.CUR_TYPE, CurType.getDescriptionByCny());
        mvn.addObject(EPPSCBPConstants.LOGISTICS_TYPE, LogisticsType.getDescriptions());
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
        LOGGER.info("收结汇申请初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("收结汇申请初始化结束");
        return mav;
    }
    
    /**
     * 功能描述: <br>
     * 〈来账信息查询〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "arrivalQuery", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String arrivalQuery(@RequestParam("currency") String currency, HttpServletRequest request) {
        LOGGER.info("来账信息查询开始");
        String currencyCode = CurType.getCodeFromDescription(currency);

        ApiResDto<ColAndSettleMultiInfoDto> res = new ApiResDto<ColAndSettleMultiInfoDto>();
        try {
            res = merchantInfoService.arrivalQuery(currencyCode, request.getRemoteUser());
        } catch (Exception e) {
            LOGGER.error("来账信息查询出错,错误:{}", ExceptionUtils.getStackTrace(e));
        }
        LOGGER.info("来账信息查询结束,{}", res);

        return JSON.toJSONString(res);
    }
    
    /**
     * 功能描述: <br>
     * 〈查询该付款账号是否开通收汇功能〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "isOpenColl", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String isOpenColl(@RequestParam("payeeMerchantCode") String payeeMerchantCode, HttpServletRequest request) {
        LOGGER.info("查询该付款账号是否开通收汇功能开始");

        ApiResDto<MerchantInfoQueryResDto> res = new ApiResDto<MerchantInfoQueryResDto>();
        try {
            res = merchantInfoService.isOpenColl(payeeMerchantCode, request.getRemoteUser());
        } catch (Exception e) {
            LOGGER.error("查询该付款账号是否开通收汇功能,错误:{}", ExceptionUtils.getStackTrace(e));
        }
        LOGGER.info("查询该付款账号是否开通收汇功能结束,{}", res);

        return JSON.toJSONString(res);
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
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, @RequestParam("currency") String currency, @RequestParam("receiveAmt") String receiveAmt, @RequestParam("collAndSettleFlag") String collAndSettleFlag, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("申请明细文件上传开始,currency:{},receiveAmt:{},collAndSettleFlag:{}", currency, receiveAmt,collAndSettleFlag);
        response.setContentType("text/html;charset=utf-8");

        // 获取金额
        double receiveAmtReal = 0;
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
     * 〈提交订单〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("applySubmit")
    public ModelAndView applySubmit(@ModelAttribute("apply") CollAndSettleApplyDto apply, HttpServletRequest request) {
    	LOGGER.info("收结汇订单提交开始:{}", apply.toString());
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName(LOGISTICS_RESULT);
        try {
           
            String userNo = request.getRemoteUser();
            if (StringUtil.isNotNull(userNo)) {
                apply.setPayerAccount(userNo);
            }

            ApiResDto<CollAndSettleResDto> apiResDto = collAndSettleFileImportService.submitSettleOrder(apply);
            if (apiResDto.isSuccess()) {
                // 提交订单成功
                LOGGER.info("收结汇申请返回成功");
                mav.addObject(EPPSCBPConstants.CRITERIA, apiResDto.getResult());
            } else {
                // 提交订单失败
                LOGGER.info("收结汇申请失败状态:{}-{}", apiResDto.getResponseCode(), apiResDto.getResponseMsg());
                mav.addObject(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
            }
            LOGGER.info("收结汇申请提交结束");
        } catch (Exception ex) {
            mav.addObject(CommonConstant.RESPONSE_MESSAGE, "系统异常");
            LOGGER.error("收结汇申请提交异常:{}", ExceptionUtils.getStackTrace(ex));
        }

        return mav;
    }
}
