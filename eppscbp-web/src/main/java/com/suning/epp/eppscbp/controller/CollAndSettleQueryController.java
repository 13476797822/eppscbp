/**
 * 
 */
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

import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.suning.epp.eppscbp.common.enums.BatchPayFlag;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CtCollAndSettleOrderStatus;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.BatchPaymentOrderFileRequestDto;
import com.suning.epp.eppscbp.dto.req.CollAndSettleQueryDto;
import com.suning.epp.eppscbp.dto.req.OrderCalculateDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentOrderQueryAndCalculateResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentQueryResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleDetailResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleQueryResDto;
import com.suning.epp.eppscbp.dto.res.CommonResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.rsf.service.OrderQueryService;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.service.ApplyFileUploadService;
import com.suning.epp.eppscbp.service.BatchPaymentService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * @author 17080704
 *
 */
@Controller()
@RequestMapping("/singleQuery/collAndSettleQuery/")
public class CollAndSettleQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollAndSettleQueryController.class);

    private static final String INIT_QUERY = "screen/singleOrderSearch/collAndSettleQuery";

    private static final String DETAIL_INFO = "screen/singleOrderSearch/collAndSettleDetail";

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    private static final String ORDER_EXPORT_URL = "/cbpExport/ctCollAndSettleOrder.do?";

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private UnifiedReceiptService unifiedReceiptService;

    @Autowired
    private BatchPaymentService batchPaymentService;

    @Autowired
    private ApplyFileUploadService applyFileUploadService;

    /**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.CUR_TYPE, CurType.getAllDescription());
        mvn.addObject(EPPSCBPConstants.COLL_SETTLE_TYPE, CtCollAndSettleOrderStatus.getAllDescription());
        mvn.addObject(EPPSCBPConstants.COLL_TYPE, CtCollAndSettleOrderStatus.getAllCollDescription());
        mvn.addObject(EPPSCBPConstants.BIZ_TYPE, ChargeBizType.getGoodAndLogistics());
        mvn.addObject(EPPSCBPConstants.BATCH_PAY_FLAG, BatchPayFlag.getAllDescription());

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
        LOGGER.info("收结汇查询初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("收结汇查询初始化结束");
        return mav;
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
    public ModelAndView collAndSettleQuery(@ModelAttribute("requestDto") CollAndSettleQueryDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到收结汇订单查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<CollAndSettleQueryResDto>> responseParam = orderQueryService.collAndSettleQuery(requestDto);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            List<CollAndSettleQueryResDto> resultList = responseParam.getResult();
            DalPage pageInfo = responseParam.getPage();
            mav.addObject(EPPSCBPConstants.RESULT_LIST, resultList);
            LOGGER.info("获取到查询结果,resultList:{}", resultList);
            mav.addObject(EPPSCBPConstants.PAGE, pageInfo);
            LOGGER.info("获取到分页信息,page:{}", pageInfo);
        } else {
            // 未查询到数据或查询出错
            mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);
        }
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈指定收单详情〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("detailInfo")
    public ModelAndView detailInfoQuery(@ModelAttribute("orderNo") String orderNo, HttpServletRequest request) {
        String payerAccount = request.getRemoteUser();
        LOGGER.info("收到指定收结汇订单详情查询请求,orderNo:{},payerAccount:{}", orderNo, payerAccount);

        ModelAndView mav = new ModelAndView(DETAIL_INFO);
        LOGGER.info("触发rsf调用进行查询,orderNo:{},payerAccount:{}", orderNo, payerAccount);
        ApiResDto<CollAndSettleDetailResDto> responseParam = orderQueryService.collAndSettleDetailInfoQuery(payerAccount, orderNo);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            mav.addObject(EPPSCBPConstants.RESULT, responseParam.getResult());
            LOGGER.info("获取到查询结果,result:{}", responseParam.getResult());
        }
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈收结汇订单数据导出〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void collAndSettleOrderExport(@ModelAttribute("requestDto") CollAndSettleQueryDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("收到收结汇订单导出请求,requestDto:{}", requestDto);

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        // 接口入参预处理
        LOGGER.info("开始收结汇订单导出入参预处理");
        // 户头号
        requestDto.setPayerAccount(request.getRemoteUser());
        // 货币
        requestDto.setCurrency(StringUtil.isEmpty(CurType.getCodeFromDescription(requestDto.getCurrency())) ? null : CurType.getCodeFromDescription(requestDto.getCurrency()));
        // 订单金额
        requestDto.setOrderAmt(StringUtil.isEmpty(requestDto.getOrderAmt()) ? null : requestDto.getOrderAmt().trim());
        // 订单状态
        requestDto.setOrderState(StringUtil.isEmpty(requestDto.getOrderState()) ? null : CtCollAndSettleOrderStatus.getCodeFromDescription(requestDto.getOrderState()));
        // 创建开始时间
        requestDto.setCreatOrderStartTime(StringUtil.isEmpty(requestDto.getCreatOrderStartTime()) ? null : requestDto.getCreatOrderStartTime());
        // 创建结束时间
        requestDto.setCreatOrderEndTime(StringUtil.isEmpty(requestDto.getCreatOrderEndTime()) ? null : requestDto.getCreatOrderEndTime());
        // 业务类型
        requestDto.setBizType(StringUtil.isEmpty(ChargeBizType.getCodeFromDescription(requestDto.getBizType())) ? null : ChargeBizType.getCodeFromDescription(requestDto.getBizType()));
        // 批付状态
        requestDto.setBatchPayFlag(BatchPayFlag.getCodeFromDescription(requestDto.getBatchPayFlag()));

        LOGGER.info("收结汇订单导出入参:{}", requestDto);

        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = eppcbsUrl + ORDER_EXPORT_URL + tempUrl;

        LOGGER.info("导出请求url,url:{}", url);

        InputStream input = HttpUtil.httpGetRequestIO(url);
        String fileName = DateUtil.formatDate(new Date(), DateConstant.DATEFORMATE_YYYYMMDDHHMMSSSSS);

        OutputStream outputStream = null;
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
        outputStream = response.getOutputStream();

        LOGGER.info("查询完毕,开始导出");

        outputStream.write(HttpUtil.readInputStream(input));

    }

    /**
     * 功能描述: <br>
     * 〈关闭订单〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "toClose")
    @ResponseBody
    public CommonResDto toClose(HttpServletRequest request, @ModelAttribute("orderNo") String orderNo) {
        CommonResDto commonResDto = new CommonResDto();
        try {
            LOGGER.info("关闭订单开始:{}", orderNo);
            ApiResDto<String> apiResDto = unifiedReceiptService.closeCollSettOrder(request.getRemoteUser(), orderNo);
            if (apiResDto.isSuccess()) {
                // 关闭成功
                commonResDto.setMessage("关闭成功");
            } else {
                // 关闭失败
                commonResDto.fail(apiResDto.getResponseCode(), apiResDto.getResponseMsg());
            }
            LOGGER.info("关闭订单结束");
        } catch (Exception ex) {
            LOGGER.error("关闭订单异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return commonResDto;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈收结汇监管文件上传〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "superviseSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String tradeSuperviseUpload(@RequestParam("fileAddress") String fileAddress, @RequestParam("businessNo") String businessNo, HttpServletRequest request) {
        LOGGER.info("收结汇监管文件请求跨境开始{},{}", businessNo, fileAddress);
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<String> apiResDto = orderQueryService.tradeSuperviseUpload(businessNo, fileAddress, request.getRemoteUser(), EPPSCBPConstants.ECS);
        LOGGER.info("收结汇监管文件触发跨境返回:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("收结汇监管文件返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("收结汇监管文件请求跨境同步结束");
        return JSON.toJSONString(result);
    }

    /**
     * 
     * 功能描述: <br>
     * 〈批付文件上传〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "bpFileSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fileSubmit(@RequestParam("bpFileAddress") String fileAddress, @RequestParam("bpBusinessNo") String businessNo, HttpServletRequest request) {
        LOGGER.info("批付文件请求跨境开始{},{}", businessNo, fileAddress);
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<String> apiResDto = batchPaymentService.batchPaymentUpload(businessNo, request.getRemoteUser(), fileAddress, CommonConstant.CBP_CODE);
        LOGGER.info("批付文件触发跨境返回:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("收结汇监管文件返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("批付文件请求跨境同步结束");
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈批付文件校验〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "bpFileUpload", produces = "application/json;charset=UTF-8")
    public void bpFileUpload(@RequestParam("file") MultipartFile targetFile, HttpServletRequest request, @RequestParam("prePayAmount") String prePayAmount, HttpServletResponse response) throws IOException {
        LOGGER.info("批付文件上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();

        // 获取待批付金额
        double amount = Double.parseDouble(prePayAmount);
        try {
            res = applyFileUploadService.uploadBatchFiles(targetFile, amount, request.getRemoteUser());
        } catch (ExcelForamatException e) {
            LOGGER.error("批付文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("批付文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("批付文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }

    /**
     * 
     * 功能描述: <br>
     * 〈批付查询〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "ordersQuery", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> ordersQuery(HttpServletRequest request, @RequestParam("businessNo") String businessNo) {
        LOGGER.info("批付查询请求跨境开始,业务单号:{}", businessNo);
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<List<BatchPaymentQueryResDto>> apiResDto = batchPaymentService.ordersQuery(request.getRemoteUser(), businessNo);
        LOGGER.info("批付查询请求触发跨境返回:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
            result.put(EPPSCBPConstants.RESULT, apiResDto.getFlag());
            result.put(EPPSCBPConstants.RESULT_LIST, apiResDto.getResult());
            result.put(EPPSCBPConstants.PREPAY_AMOUNT, StringUtil.formatMoney(Long.valueOf(apiResDto.getResponseMsg())));
        } else {
            LOGGER.info("批付查询请求返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("批付查询请求跨境同步结束");
        return result;
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
        LOGGER.info("批付分笔计算开始");
        Map<String, Object> result = new HashMap<String, Object>();
        // 进行计算
        try {
            OrderCalculateDto apiResDto = batchPaymentService.ordersCalculate(requestDto);
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
     * 
     * 功能描述: <br>
     * 〈批付查询-调用批付查询接口〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "resultQuery", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> resultQuery(HttpServletRequest request, @RequestParam("businessNo") String businessNo) {
        LOGGER.info("批付查询请求跨境开始,业务单号:{}", businessNo);
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<List<BatchPaymentQueryResDto>> apiResDto = batchPaymentService.ordersQuery(request.getRemoteUser(), businessNo);
        LOGGER.info("批付查询请求触发跨境返回:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
            result.put(EPPSCBPConstants.RESULT, apiResDto.getFlag());
            result.put(EPPSCBPConstants.RESULT_LIST, apiResDto.getResult());
        } else {
            LOGGER.info("批付查询请求返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("批付查询请求跨境同步结束");
        return result;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈批付提交〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "ordersAudit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String ordersAudit(HttpServletRequest request, @RequestBody OrdersAuditRequestDto requestDto) {
        LOGGER.info("批付申请提交请求跨境开始,业务单号:{}", requestDto.getBusinessNo());
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<String> apiResDto = batchPaymentService.ordersAudit(request.getRemoteUser(), requestDto.getBusinessNo(), requestDto.getDetails());
        LOGGER.info("批付申请提交触发跨境返回:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("批付申请提交返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);           
        }
        result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        LOGGER.info("批付提交申请请求跨境同步结束");
        return JSON.toJSONString(result);
    }
    
    /**
     * 
     * 功能描述: <br>
     * 〈批量保存〉
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
    public Map<String, Object> bpOrdersParseAndCalculate(@RequestParam("bpFileAddress") String fileAddress, @RequestParam("bpBusinessNo") String businessNo, @RequestParam("bpPrePayAmount") String prePayAmount, HttpServletRequest request) {
        LOGGER.info("解析excel、计算批付总金额开始");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	// 请求跨境，解析excel，返回list,进行计算
        	ApiResDto<BatchPaymentOrderQueryAndCalculateResDto> apiResDto = batchPaymentService.bpOrdersParseAndCalculate(businessNo, request.getRemoteUser(), fileAddress, CommonConstant.CBP_CODE, prePayAmount, null);
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
    
    /**
     * 
     * 功能描述: <br>
     * 〈批付文件上传〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "batchPaymentOrderFileSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchPaymentOrderFileSubmit(@RequestBody BatchPaymentOrderFileRequestDto requestDto, HttpServletRequest request) {
        LOGGER.info("批付文件请求跨境开始{}", requestDto);
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<String> apiResDto = batchPaymentService.batchPaymentUpload(requestDto.getBusinessNo(), request.getRemoteUser(), requestDto.getFileAddress(), CommonConstant.CBP_CODE);
        LOGGER.info("批付文件触发跨境返回:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("收结汇监管文件返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
        }
        result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        LOGGER.info("批付文件请求跨境同步结束");
        return JSON.toJSONString(result);
    }

}
