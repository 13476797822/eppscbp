package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.IndexConstant;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CtArrivalNoticeStatus;
import com.suning.epp.eppscbp.common.enums.PreOrderFileStatus;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.dto.req.PreArrivalQueryReqDto;
import com.suning.epp.eppscbp.dto.req.PreBpOrderParseCalcReqDto;
import com.suning.epp.eppscbp.dto.req.PreCollAndSettleQueryDto;
import com.suning.epp.eppscbp.dto.req.PreOrderCollAndPaymentApplyReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.PreAmountQueryResDto;
import com.suning.epp.eppscbp.dto.res.PreCollAndSettleQueryResDto;
import com.suning.epp.eppscbp.service.PreOrderCollAndSettleService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSDownloadParams;

/**
 * 订单前置相关接口
 */
@Controller()
@RequestMapping("/preOrderCollAndSettle/")
public class PreOrderCollAndSettleController {
    private static final Logger log = LoggerFactory.getLogger(PreOrderCollAndSettleController.class);

    private static final String INIT_FILE = "screen/preOrderCollAndSettle/preOrderFile";

    private static final String INIT_APPLY = "screen/preOrderCollAndSettle/preOrderApply";

    @Autowired
    private PreOrderCollAndSettleService preOrderCollAndSettleService;

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    private static final String PRE_PAYMENT_ARRIVAL_EXPORT_URL = "/cbpExport/prePaymentArrivalExport.do?";

    /**
     * 功能描述: <br>
     * 〈页面初期化〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("preFile/init")
    public ModelAndView initFile(HttpServletRequest request) {
        String payerAccount = request.getRemoteUser();
        log.debug("{}订单前置明细上传初始化开始", payerAccount);

        ModelAndView mav = new ModelAndView(INIT_FILE);
        mav.addObject(EPPSCBPConstants.CHARGE_BIZ_TYPE, ChargeBizType.getGoodAndLogistics());
        mav.addObject(EPPSCBPConstants.ORDER_STATUS, PreOrderFileStatus.getAllDescription());
        ApiResDto<List<PreAmountQueryResDto>> queryPreOrderAmt = preOrderCollAndSettleService.queryPreOrderAmt(payerAccount);
        mav.addObject("preAmount", queryPreOrderAmt.getResult());
        log.debug("订单前置明细上传初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈页面初期化〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("preApply/init")
    public ModelAndView init() {
        log.debug("订单前置订单提交初始化开始");
        ModelAndView mav = new ModelAndView(INIT_APPLY);
        mav.addObject(EPPSCBPConstants.CHARGE_BIZ_TYPE, ChargeBizType.getGoodAndLogistics());
        mav.addObject(EPPSCBPConstants.ORDER_STATUS, CtArrivalNoticeStatus.getAllDescription());
        log.debug("订单前置订单提交初始化结束");
        return mav;
    }

    /**
     * 来账信息查询
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "arrivalQuery")
    public ModelAndView arrivalQuery(@ModelAttribute("criteria") PreArrivalQueryReqDto params, HttpServletRequest request) {
        log.info("来账信息查询开始,请求参数{}", params);
        ModelAndView mav = init();
        mav.addObject(EPPSCBPConstants.CRITERIA, params);
        params.setPlatformCode(CommonConstant.CBP_CODE);
        params.setPayerAccount(request.getRemoteUser());
        Map<String, Object> response = preOrderCollAndSettleService.arrivalQuery(params);
        // 解析返回值
        if (EPPSCBPConstants.SUCCESS_CODE.equals(response.get("responseCode"))) {
            DalPage pageInfo = new DalPage();
            pageInfo.setCurrentPage((Integer) response.get("pageNo"));
            pageInfo.setCount((Long) response.get("totalCount"));
            pageInfo.setPageSize((Integer) response.get("pageSize"));
            mav.addObject(EPPSCBPConstants.RESULT_LIST, response.get("data"));
            mav.addObject(EPPSCBPConstants.PAGE, pageInfo);
        } else {
            // 未查询到数据或查询出错
            mav.addObject(EPPSCBPConstants.RESPONSE_MSG, EPPSCBPConstants.NO_RESULT_MSG);
        }
        log.info("来账信息查询结束,{}", response);
        return mav;
    }

    /**
     * 批付文件解析并计算
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "bpOrdersParseCalc", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> bpOrdersParseCalc(@RequestParam("file") MultipartFile targetFile, @RequestParam("prePayAmount") String prePayAmount, @RequestParam("bizType") String bizType, HttpServletRequest request) throws IOException {

        log.info("申请前置明细文件上传开始, payerAccount:{}， fileName：{}", request.getRemoteUser(), targetFile.getOriginalFilename());

        Map<String, Object> result = new HashMap<String, Object>();

        if (targetFile.isEmpty()) {
            log.info("文件为空或文件不存在");
            result.put(EPPSCBPConstants.RESPONSE_CODE, WebErrorCode.FILE_NOT_EXIST.getCode());
            result.put(EPPSCBPConstants.RESPONSE_MSG, WebErrorCode.FILE_NOT_EXIST.getDescription());
            return result;
        }

        if (!targetFile.getOriginalFilename().endsWith("xlsx")) {
            log.info("只支持.xlsx后缀类型的文件");
            result.put(EPPSCBPConstants.RESPONSE_CODE, WebErrorCode.FILE_TYPE_MISMATCH.getCode());
            result.put(EPPSCBPConstants.RESPONSE_MSG, WebErrorCode.FILE_TYPE_MISMATCH.getDescription());

            return result;
        }

        PreBpOrderParseCalcReqDto params = new PreBpOrderParseCalcReqDto();
        params.setPayerAccount(request.getRemoteUser());
        params.setBpableAmount(prePayAmount);
        params.setBizType(bizType);

        result = preOrderCollAndSettleService.bpOrdersParseCalc(params, targetFile);
        log.info("解析excel、计算批付总金额结束,{}", result);
        return result;
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
    @RequestMapping(value = "preUploadFile", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public FileUploadResDto preUploadFile(@RequestParam("file") MultipartFile targetFile, HttpServletRequest request) throws IOException {
        log.info("申请前置明细文件上传开始, payerAccount:{}， fileName：{}", request.getRemoteUser(), targetFile.getOriginalFilename());

        FileUploadResDto result = new FileUploadResDto();

        if (targetFile.isEmpty()) {
            log.info("文件为空或文件不存在");
            result.fail(WebErrorCode.FILE_NOT_EXIST.getCode(), WebErrorCode.FILE_NOT_EXIST.getDescription());
            return result;
        }

        if (!targetFile.getOriginalFilename().endsWith("xlsx")) {
            log.info("只支持.xlsx后缀类型的文件");
            result.fail(WebErrorCode.FILE_TYPE_MISMATCH.getCode(), WebErrorCode.FILE_TYPE_MISMATCH.getDescription());
            return result;
        }
        
        Workbook sheet = new XSSFWorkbook(targetFile.getInputStream());
        int rows = sheet.getSheetAt(IndexConstant.ZERO).getPhysicalNumberOfRows();
        log.info("明细文件笔数:{}",rows);
        sheet.close();

        if (rows-1 > 50000) {
            log.info("明细文件超过50000笔:{}",rows);
            result.fail(WebErrorCode.QUANTITY_OVERSTEP_50000.getCode(), WebErrorCode.QUANTITY_OVERSTEP_50000.getDescription());
            return result;
        }
        
        try {
            result = preOrderCollAndSettleService.collAndSettleFileUpload(targetFile, request.getRemoteUser());
            log.info("文件上传返回信息:{}", result);
        } catch (IOException e) {
            log.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            result.fail(WebErrorCode.FORMAT_ERROR.getCode(), "文件上传失败");
        } catch (Exception e) {
            log.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            result.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        log.info("申请明细文件上传结束");
        return result;
    }

    /**
     * 功能描述: <br>
     * 〈提交前置明细文件申请〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "preFileSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fileSubmit(@RequestParam("fileAddress") String fileAddress, @RequestParam("bizType") String bizType, HttpServletRequest request, HttpServletResponse response) {
        log.info("前置请求文件提交开始,bizType:{}, payerAccount:{}", bizType, request.getRemoteUser());
        Map<String, String> result = new HashMap<String, String>();
        result.put("success", "f");
        if (StringUtil.isEmpty(fileAddress, bizType, request.getRemoteUser())) {
            log.info("请求参数不能为空");
            result.put(CommonConstant.RESPONSE_CODE, WebErrorCode.CHECK_FAIL.getCode());
            result.put(CommonConstant.RESPONSE_MESSAGE, "业务类型或文件地址为空");
            return JSON.toJSONString(result);
        }

        if (!ChargeBizType.TYPE_GOODS_TRADE.getDescription().equals(bizType) && !ChargeBizType.TYPE_LOGISTICS.getDescription().equals(bizType)) {
            log.info("业务类型不为货物贸易或者国际物流");
            result.put(CommonConstant.RESPONSE_CODE, WebErrorCode.CHECK_FAIL.getCode());
            result.put(CommonConstant.RESPONSE_MESSAGE, "业务类型只能为货物贸易或者国际物流");
            return JSON.toJSONString(result);
        }
        bizType = ChargeBizType.getCodeFromDescription(bizType);
        try {
            result = preOrderCollAndSettleService.collAndSettleFileApply(fileAddress, request.getRemoteUser(), bizType);
            log.info("前置请求文件提交返回信息:{}", result);
        } catch (Exception e) {
            log.error("前置文件提交出错,错误:{}", ExceptionUtils.getStackTrace(e));
            result.put(CommonConstant.RESPONSE_CODE, WebErrorCode.SYSTEM_ERROR.getCode());
            result.put(CommonConstant.RESPONSE_MESSAGE, WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        log.info("前置请求文件提交结束");
        return JSON.toJSONString(result);

    }

    /**
     * 功能描述: <br>
     * 〈单笔交易查询〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("query")
    public ModelAndView collAndSettlePreQuery(@ModelAttribute("criteria") PreCollAndSettleQueryDto requestDto, HttpServletRequest request) {
        log.info("收到前置明细文件收单查询请求,requestDto:{}", requestDto);

        ModelAndView mav = initFile(request);
        try {
            mav.addObject(EPPSCBPConstants.CRITERIA, requestDto);
            log.info("获取付款方户头号:{}", request.getRemoteUser());
            requestDto.setPayerAccount(request.getRemoteUser());

            if (StringUtil.isEmpty(request.getRemoteUser())) {
                log.info("商户户头号为空");
                mav.addObject(EPPSCBPConstants.RESPONSE_MSG, "商户户头号不能为空");
                return mav;
            }

            if (!StringUtil.isEmpty(requestDto.getCreatFileStartTime(), requestDto.getCreatFileEndTime())) {
                // 时间格式化
                String creatFileStartTime = requestDto.getCreatFileStartTime();
                String creatFileEndTime = requestDto.getCreatFileEndTime();
                Date creatFileStartTimeDate = DateUtil.getDateOfTimeStr(creatFileStartTime, DateConstant.DATE_FORMAT);
                Date creatFileEndTimeDate = DateUtil.getDateOfTimeStr(creatFileEndTime, DateConstant.DATE_FORMAT);

                // 上传时间范围只支持三个月
                if (DateUtil.getIntervalMonths(creatFileStartTimeDate, creatFileEndTimeDate) > 3) {
                    log.info("上传时间开始:{}, 结束:{}", creatFileStartTimeDate, creatFileEndTimeDate);
                    mav.addObject(EPPSCBPConstants.RESPONSE_MSG, EPPSCBPConstants.PRE_QUERY_ERROR_MSG);
                    return mav;
                }
            }

            log.info("触发rsf调用进行查询,requestPreDto:{}", requestDto);
            ApiResDto<List<PreCollAndSettleQueryResDto>> responseParam = preOrderCollAndSettleService.collAndSettlePreQuery(requestDto);
            log.info("rsf查询结果,responsePreParam:{}", responseParam);
            if (responseParam.isSuccess()) {
                List<PreCollAndSettleQueryResDto> resultList = responseParam.getResult();
                DalPage pageInfo = responseParam.getPage();
                mav.addObject(EPPSCBPConstants.RESULT_LIST, resultList);
                log.info("获取到查询结果,resultList:{}", resultList);
                mav.addObject(EPPSCBPConstants.PAGE, pageInfo);
                log.info("获取到分页信息,page:{}", pageInfo);
            } else {
                // 未查询到数据或查询出错
                mav.addObject(EPPSCBPConstants.RESPONSE_MSG, EPPSCBPConstants.NO_RESULT_MSG);
            }
        } catch (Exception e) {
            log.error("查询列表异常:{}", ExceptionUtils.getStackTrace(e));
            mav.addObject(EPPSCBPConstants.RESPONSE_MSG, EPPSCBPConstants.SYSTEM_ERROR);
        }
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈商户前置订单明细剩余可用余额〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "queryPreOrderAmt", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPreOrderAmt(HttpServletRequest request) {

        log.info("获取付款方户头号:{}", request.getRemoteUser());
        String payerAccount = request.getRemoteUser();
        ApiResDto<List<PreAmountQueryResDto>> apiResDto = new ApiResDto<List<PreAmountQueryResDto>>();

        if (StringUtil.isEmpty(payerAccount)) {
            log.info("商户户头号为空");
            apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
            apiResDto.setResponseMsg("商户户头号不能为空");
            return JSON.toJSONString(apiResDto);
        }

        try {
            log.info("触发rsf调用进行查询,payerAccount:{}", payerAccount);
            apiResDto = preOrderCollAndSettleService.queryPreOrderAmt(payerAccount);
            log.info("rsf查询结果,responsePreParam:{}", apiResDto);

        } catch (Exception e) {
            log.error("查询列表异常:{}", ExceptionUtils.getStackTrace(e));
        }
        return JSON.toJSONString(apiResDto);
    }

    /**
     * 功能描述: <br>
     * 〈前置明细文件解析异常信息文件-下载〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("downloadErrorCsv")
    public void downloadErrorCsv(@ModelAttribute("type") String type, @ModelAttribute("failFileAddress") String failFileAddress, HttpServletResponse response) {
        log.info("异常信息文件开始,failFileAddress:{}", failFileAddress);
        String bucketName;
        if("bp".equals(type)) {
            bucketName = OSSBucket.BATCH_PAYMENT_FILE;
        } else {
            bucketName = OSSBucket.COLL_SETT_FILE;
        }
        InputStream in = null;
        OutputStream out = null;
        // 文件地址为空
        if (StringUtil.isEmpty(failFileAddress)) {
            log.info("错误文件地址为空");
            return;
        }

        /*
         * // 判断oss上是否有该文件 if (OSSClientUtil.exists(bucketName, failFileAddress)) { log.info("前置明细文件错误信息文件不存在"); return;
         * }
         */

        // 根据地址以及bucketName 下载
        log.info("下载文件开始,路径为:{}", failFileAddress);
        try {
            OSSDownloadParams params = new OSSDownloadParams(bucketName, failFileAddress);
            response.setContentType("application/csv;charset=GBK");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(failFileAddress, "UTF-8"));
            in = OSSClientUtil.getInputStream(params);
            if (in == null) {
                log.error("文件不存在。文件路径:{}", failFileAddress);
                return;
            }
            out = response.getOutputStream();
            int len = 0;
            byte buf[] = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            log.info("下载结束,文件名:{}", failFileAddress);
        } catch (Exception e) {
            log.error("下载异常文件:{}失败, 错误异常:{}", failFileAddress, ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("关闭流失败");
            }
        }

    }

    /**
     * 收结汇付款"提交申请"
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "collAndPaymentApply", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String collAndPaymentApply(@RequestBody PreOrderCollAndPaymentApplyReqDto params, HttpServletRequest request) {

        params.setPlatformCode(CommonConstant.CBP_CODE);
        params.setPayerAccount(request.getRemoteUser());
        params.setBizType(ChargeBizType.getCodeFromDescription(params.getBizType()));
        log.info("订单前置收结汇付款提交申请开始,请求参数{}", params);
        Map<String, Object> response = preOrderCollAndSettleService.collAndPaymentApply(params);
        log.info("订单前置收结汇付款提交申请结束,{}", response);
        return JSON.toJSONString(response);
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
    @RequestMapping("export")
    public void batchPaymentDetailExport(@ModelAttribute("requestDto") PreArrivalQueryReqDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("收到前置结汇付款导出请求,requestDto:{}", requestDto);

        log.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());
        // 接口入参预处理
        log.info("开始收到前置结汇付款导出请求入参预处理");
        // 平台标识
        requestDto.setPlatformCode(CommonConstant.CBP_CODE);

        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = eppcbsUrl + PRE_PAYMENT_ARRIVAL_EXPORT_URL + tempUrl;

        log.info("导出请求url,url:{}", url);

        InputStream input = HttpUtil.httpGetRequestIO(url);
        String fileName = DateUtil.formatDate(new Date(), DateConstant.DATEFORMATE_YYYYMMDDHHMMSSSSS);

        OutputStream outputStream;
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
        outputStream = response.getOutputStream();

        log.info("查询完毕,开始导出");

        outputStream.write(HttpUtil.readInputStream(input));

    }

}
