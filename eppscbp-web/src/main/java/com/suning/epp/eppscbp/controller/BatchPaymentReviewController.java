package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.BatchPaymentReviewStatus;
import com.suning.epp.eppscbp.dto.req.BatchPaymentReviewDetailDto;
import com.suning.epp.eppscbp.dto.req.BatchPaymentReviewQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentOrderReviewData;
import com.suning.epp.eppscbp.dto.res.BatchPaymentReviewDetail;
import com.suning.epp.eppscbp.rsf.service.BatchDetailReviewService;
import com.suning.epp.eppscbp.rsf.service.MemberInfoService;
import com.suning.epp.eppscbp.util.AESUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈门户批付明细查询和导出〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/batchPaymentReview/batchPaymentReview/")
public class BatchPaymentReviewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchPaymentReviewController.class);

    private static final String INIT_QUERY = "screen/batchPaymentSetting/init";

    @Autowired
    private BatchDetailReviewService batchDetailReviewService;
    
    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 初始化主视图
     *
     * @return
     */
    private ModelAndView createMainView() {
        LOGGER.info("批付明细查询初始参数开始");
        return new ModelAndView(INIT_QUERY);
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
        LOGGER.info("批付明细查询初始化开始");
        ModelAndView mav = createMainView();
        BatchPaymentReviewQueryReqDto requestDto = new BatchPaymentReviewQueryReqDto();
        requestDto.setCreateStartTime(DateUtil.getCurrentDateString());
        requestDto.setCreateEndTime(DateUtil.getCurrentDateString());
        requestDto.setReviewStatus(BatchPaymentReviewStatus.INIT.getDescription());
        mav.addObject("requestDto", requestDto);
        LOGGER.info("批付明细查询初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈批付明细查询〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("query")
    public ModelAndView ordersReviewQuery(@ModelAttribute("requestDto") BatchPaymentReviewQueryReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到批付订单查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        if(StringUtil.isEmpty(requestDto.getCurrentPage())) {
            requestDto.setCurrentPage("1");
            requestDto.setPageSize("10");
        }
        mav.addObject("requestDto", requestDto);
        ApiResDto<List<BatchPaymentOrderReviewData>> responseParam = query(requestDto, request);

        if (!responseParam.isSuccess()) {
            // 未查询到数据或查询出错
            mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);

        } else {
            mav.addObject(EPPSCBPConstants.RESULT_LIST, responseParam.getResult());
            LOGGER.info("获取到批付订单查询结果,resultList:{}", responseParam.getResult());
            mav.addObject(EPPSCBPConstants.PAGE, responseParam.getPage());
            LOGGER.info("获取到批付订单分页信息,page:{}", responseParam.getPage());
        }

        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈批付明细查询〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("queryDetail")
    @ResponseBody
    public Map<String, Object> ordersReviewDetail(@RequestBody BatchPaymentReviewDetailDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到批付明细查询请求,requestDto:{}", requestDto);
        getCommonParam(requestDto, request);
        LOGGER.info("触发rsf调用进行批付明细查询,requestDto:{}", requestDto);
        Map<String, Object> result = new HashMap<String, Object>();
        ApiResDto<List<BatchPaymentReviewDetail>> responseParam = batchDetailReviewService.ordersReviewDetail(requestDto);
        LOGGER.info("rsf批付明细查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            List<BatchPaymentReviewDetail> list = responseParam.getResult();
            LOGGER.info("获取到批付明细查询结果,resultList:{}", list);
            LOGGER.info("获取到批付明细分页信息,page:{}", responseParam.getPage());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
            Long amt = list.get(0).getExpendNoAmount();
            LOGGER.info("总笔数:{}",responseParam.getPage().getCount());
            result.put(EPPSCBPConstants.AMOUNT, StringUtil.formatMoney(amt));
            result.put(EPPSCBPConstants.NUMBER, responseParam.getPage().getCount());
            result.put(EPPSCBPConstants.RESULT_LIST, list);
            result.put(EPPSCBPConstants.PAGE, responseParam.getPage());
            result.put(EPPSCBPConstants.BUSINESS_NO, requestDto.getExpendNo());
        } else {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);
        }
        return result;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param requestDto
     * @param request
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void getCommonParam(BatchPaymentReviewDetailDto requestDto, HttpServletRequest request) {
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());
        requestDto.setPlatformCode(CommonConstant.CBP_CODE);
    }

    /**
     * 功能描述: <br>
     * 〈批付状态校验〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("validate")
    @ResponseBody
    public String batchPaymentValidate(@ModelAttribute("requestDto") BatchPaymentReviewDetailDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到批付状态校验请求,requestDto:{}", requestDto);
        getCommonParam(requestDto, request);

        LOGGER.info("触发rsf调用进行批付状态校验,requestDto:{}", requestDto);
        ApiResDto<List<String>> responseParam = batchDetailReviewService.batchPaymentValidate(requestDto);
        LOGGER.info("rsf批付状态校验结果,responseParam:{}", responseParam);

        Map<String, Object> result = new HashMap<String, Object>();
        if (!responseParam.isSuccess()) {
            // 校验不通过
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(EPPSCBPConstants.ERROR_MSG_CODE, responseParam.getResponseMsg());
        } else {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        }

        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈批付支付〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("pay")
    @ResponseBody
    public String confirmBatchPayment(String expendNo, String password, HttpServletRequest request) {
        LOGGER.info("收到批付支付请求,expendNo:{}", expendNo);
        BatchPaymentReviewDetailDto requestDto = new BatchPaymentReviewDetailDto();
        requestDto.setExpendNo(expendNo);
        Map<String, String> result = new HashMap<String, String>();
        getCommonParam(requestDto, request);
        LOGGER.info("验证支付密码,用户:{}", requestDto.getPayerAccount());
        boolean payFlg = false;
        LOGGER.info("查询会员AES开始:{}", request.getRemoteUser());
        ApiResDto<String> eppSecretKeyRsfServer = memberInfoService.eppSecretKeyRsfServer();
        if (eppSecretKeyRsfServer.isSuccess()) {
            LOGGER.info("查询会员AES成功");
            ApiResDto<String> validatePaymentPassword = memberInfoService.validatePaymentPassword(requestDto.getPayerAccount(), AESUtil.encryptBase64DecorateAES(password, eppSecretKeyRsfServer.getResult()));
            if (validatePaymentPassword.isSuccess()) {
                payFlg = true;
            } else {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                result.put(CommonConstant.RESPONSE_MESSAGE, validatePaymentPassword.getResponseMsg());
            }
            LOGGER.info("验证支付密码正确与否结束:{}", request.getRemoteUser());
        } else {
            LOGGER.info("查询会员AES失败：{}-{}", eppSecretKeyRsfServer.getResponseCode(), eppSecretKeyRsfServer.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, eppSecretKeyRsfServer.getResponseMsg());
        }
        if (payFlg) {
            LOGGER.info("触发rsf调用进行批付支付,requestDto:{}", requestDto);
            ApiResDto<Boolean> responseParam = batchDetailReviewService.confirmBatchPayment(requestDto);
            LOGGER.info("rsf批付支付结果,responseParam:{}", responseParam);
            if (!responseParam.isSuccess()) {
                // 支付失败
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_PAY_FAIL);
                result.put(CommonConstant.RESPONSE_MESSAGE, responseParam.getResponseMsg());
            } else {
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
            }
        }
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈批付复核不通过〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("notPass")
    @ResponseBody
    public String batchPaymentNotPass(String expendNo, HttpServletRequest request) {
        LOGGER.info("收到批付复核不通过请求,expendNo:{}", expendNo);
        BatchPaymentReviewDetailDto requestDto = new BatchPaymentReviewDetailDto();
        requestDto.setExpendNo(expendNo);
        getCommonParam(requestDto, request);

        LOGGER.info("触发rsf调用进行批付复核不通过,requestDto:{}", requestDto);
        ApiResDto<Boolean> responseParam = batchDetailReviewService.batchPaymentNotPass(requestDto);
        LOGGER.info("rsf批付复核不通过结果,responseParam:{}", responseParam);

        Map<String, Object> result = new HashMap<String, Object>();
        if (!responseParam.isSuccess()) {
            // 校验不通过
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(EPPSCBPConstants.ERROR_MSG_CODE, responseParam.getResponseMsg());
        } else {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        }

        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param requestDto
     * @param request
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private ApiResDto<List<BatchPaymentOrderReviewData>> query(BatchPaymentReviewQueryReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());
        requestDto.setPlatformCode(CommonConstant.CBP_CODE);

        LOGGER.info("触发rsf调用进行批付明细查询,requestDto:{}", requestDto);
        ApiResDto<List<BatchPaymentOrderReviewData>> responseParam = batchDetailReviewService.ordersReviewQuery(requestDto);
        LOGGER.info("rsf批付明细查询结果,responseParam:{}", responseParam);
        return responseParam;
    }

    /**
     * 功能描述: <br>
     * 〈批付明细数据导出〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "export", produces = "text/html;charset=UTF-8")
    public void batchPaymentDetailExport(@ModelAttribute("requestDto") BatchPaymentReviewQueryReqDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("收到批付明细导出请求,requestDto:{}", requestDto);
        requestDto.setCurrentPage(null);
        requestDto.setPageSize(null);
        ApiResDto<List<BatchPaymentOrderReviewData>> responseParam = this.query(requestDto, request);
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String fileName = DateUtil.formatDate(new Date(), DateConstant.DATEFORMATE_YYYYMMDDHHMMSSSSS) + ".xls";
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
            response.setHeader(headerKey, headerValue);

            HSSFWorkbook book = new HSSFWorkbook();
            exportData(book, responseParam.getResult());
            book.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            LOGGER.error("导出异常:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                LOGGER.error("流关闭异常:{}", ExceptionUtils.getStackTrace(e));
            }
        }
    }

    /**
     * 构建导出数据
     *
     * @param book
     * @param type
     * @param scaleList
     */
    private void exportData(HSSFWorkbook book, List<BatchPaymentOrderReviewData> reviewDataList) {

        HSSFSheet sheet = book.createSheet();
        Row row = sheet.createRow(0);

        int n = 0;
        row.createCell(n++).setCellValue("创建时间");
        row.createCell(n++).setCellValue("复核时间");
        row.createCell(n++).setCellValue("批次号");
        row.createCell(n++).setCellValue("批次总金额");
        row.createCell(n++).setCellValue("批次总笔数");
        row.createCell(n++).setCellValue("复核状态");
        int i = 1;
        // 写入数据
        for (BatchPaymentOrderReviewData reviewData : reviewDataList) {
            n = 0;
            Row dataRow = sheet.createRow(i++);
            dataRow.createCell(n++).setCellValue(reviewData.getCreateTimeStr());
            dataRow.createCell(n++).setCellValue(reviewData.getReviewTimeStr());
            dataRow.createCell(n++).setCellValue(reviewData.getExpendNo());
            dataRow.createCell(n++).setCellValue(reviewData.getExpendNoAmountStr());
            dataRow.createCell(n++).setCellValue(reviewData.getBatchPaymentCount());
            dataRow.createCell(n++).setCellValue(reviewData.getReviewStatusStr());
        }

    }

}
