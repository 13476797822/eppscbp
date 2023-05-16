/**
 * 
 */
package com.suning.epp.eppscbp.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSDownloadParams;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.CbpDetailFlag;
import com.suning.epp.eppscbp.common.enums.CbpProductType;
import com.suning.epp.eppscbp.common.enums.CbpStatus;
import com.suning.epp.eppscbp.common.enums.CbpSupStatus;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.OrderExRateRefreshDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.req.SingleOrderQueryDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CommonResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.dto.res.SingleTradeOrderQueryResDto;
import com.suning.epp.eppscbp.dto.res.TradeOrderDetailQueryResDto;
import com.suning.epp.eppscbp.rsf.service.OrderQueryService;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.FileConverterUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈单笔交易查询、详情和导出〉<br>
 * 〈功能详细描述〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/singleQuery/singleOrderQuery/")
public class SingleOrderQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleOrderQueryController.class);

    private static final String INIT_QUERY = "screen/singleOrderSearch/singleOrderSearch";

    private static final String DETAIL_INFO = "screen/singleOrderSearch/singleDetail";

    /*
     * 各个业务支付确认页面
     */
    private static final String GOODS_EXANDPAY_CONFIRM = "screen/goodsTrade/goodsExAndPay/confirm";
    private static final String GOODS_PAY_CONFIRM = "screen/goodsTrade/goodsPay/confirm";
    private static final String GOODS_RMBPAY_CONFIRM = "screen/goodsTrade/goodsRMBPay/confirm";
    private static final String STUDY_EXANDPAY_CONFIRM = "screen/overseasPay/studyExAndPay/confirm";
    private static final String STUDY_PAY_CONFIRM = "screen/overseasPay/studyPay/confirm";
    private static final String LOGISTICS_RMBPAY_CONFIRM = "screen/logisticsSettlement/logisticsFundsPay/confirm";
    // 货物贸易支付错误页面
    private static final String GOODS_PAY_ERROR = "screen/goodsTrade/error";
    // 留学缴费支付错误页面
    private static final String OVERSEASPAY_PAY_ERROR = "screen/overseasPay/error";
    
    //国际物流支付错误页面
    private static final String LOGISTICS_PAY_ERROR = "screen/logisticsSettlement/error";

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private UnifiedReceiptService unifiedReceiptService;

    private static final String SINGLE_ORDER_EXPORT_URL = "/cbpExport/singleTradeOrder.do?";

    /**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.PRODUCT_TYPE, CbpProductType.getSingleDescription());
        mvn.addObject(EPPSCBPConstants.BIZ_TYPE, ChargeBizType.getAllDescription());
        mvn.addObject(EPPSCBPConstants.STATUS, CbpStatus.getAllDescription());
        mvn.addObject(EPPSCBPConstants.SUP_STATUS, CbpSupStatus.getAllDescription());
        mvn.addObject(EPPSCBPConstants.DETAIL_FLAG, CbpDetailFlag.getAllDescription());
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
        LOGGER.info("单笔交易单查询初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("单笔交易单查询初始化结束");
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
    public ModelAndView singleOrderQuery(@ModelAttribute("requestDto") SingleOrderQueryDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到单笔交易单查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<SingleTradeOrderQueryResDto>> responseParam = orderQueryService.singleOrderQuery(requestDto);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            List<SingleTradeOrderQueryResDto> resultList = responseParam.getResult();
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
     * 〈单笔交易单数据导出〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void singleOrderExport(@ModelAttribute("requestDto") SingleOrderQueryDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("收到单笔交易单导出请求,requestDto:{}", requestDto);

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        // 接口入参预处理
        LOGGER.info("开始单笔交易单导出入参预处理");
        // 户头号
        requestDto.setPayerAccount(requestDto.getPayerAccount());
        // 业务单号
        requestDto.setBusinessNo(StringUtil.isEmpty(requestDto.getBusinessNo()) ? null : requestDto.getBusinessNo().trim());
        // 业务类型
        requestDto.setBizType(StringUtil.isEmpty(ChargeBizType.getCodeFromDescription(requestDto.getBizType())) ? null : ChargeBizType.getCodeFromDescription(requestDto.getBizType()));
        // 产品类型
        requestDto.setProductType(StringUtil.isEmpty(CbpProductType.getCodeFromDescription(requestDto.getProductType())) ? null : CbpProductType.getCodeFromDescription(requestDto.getProductType()));
        // 状态
        requestDto.setStatus(StringUtil.isEmpty(CbpStatus.getCodeFromDescription(requestDto.getStatus())) ? null : CbpStatus.getCodeFromDescription(requestDto.getStatus()));
        // 监管状态
        requestDto.setSupStatus(StringUtil.isEmpty(CbpSupStatus.getCodeFromDescription(requestDto.getSupStatus())) ? null : CbpSupStatus.getCodeFromDescription(requestDto.getSupStatus()));
        // 创建开始时间
        requestDto.setCreatOrderStartTime(StringUtil.isEmpty(requestDto.getCreatOrderStartTime()) ? null : requestDto.getCreatOrderStartTime().trim());
        // 创建结束时间
        requestDto.setCreatOrderEndTime(StringUtil.isEmpty(requestDto.getCreatOrderEndTime()) ? null : requestDto.getCreatOrderEndTime().trim());
        // 收款方商户号
        requestDto.setPayeeMerchantCode(StringUtil.isEmpty(requestDto.getPayeeMerchantCode()) ? null : requestDto.getPayeeMerchantCode().trim());
        // 收款方商户名称
        requestDto.setPayeeMerchantName(StringUtil.isEmpty(requestDto.getPayeeMerchantName()) ? null : requestDto.getPayeeMerchantName().trim());

        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = eppcbsUrl + SINGLE_ORDER_EXPORT_URL + tempUrl;

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
     * 〈支付结果页面〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("detailInfo")
    public ModelAndView detailInfoQuery(@ModelAttribute("subBusinessNo") String subBusinessNo, HttpServletRequest request) {
        String payerAccount = request.getRemoteUser();
        LOGGER.info("收到指定明细汇出详情查询请求,subBusinessNo:{},payerAccount:{}", subBusinessNo, payerAccount);

        ModelAndView mav = new ModelAndView(DETAIL_INFO);
        LOGGER.info("触发rsf调用进行查询,subBusinessNo:{},payerAccount:{}", subBusinessNo, payerAccount);
        ApiResDto<TradeOrderDetailQueryResDto> responseParam = orderQueryService.detailInfoQuery(payerAccount, subBusinessNo);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            mav.addObject(EPPSCBPConstants.RESULT, responseParam.getResult());
            LOGGER.info("获取到查询结果,result:{}", responseParam.getResult().toString());
        }
        return mav;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈下载单笔货物贸易监管文件模板〉
     *
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("downloadFile")
    public ModelAndView downloadFile(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("货物贸易监管模板下载开始");
        OutputStream outputStream = null;
        try {

            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + EPPSCBPConstants.TRADE_SUPERVISE);
            response.setHeader("cache-control", "public");
            response.setCharacterEncoding("GBK");
            response.setContentType("application/vnd.ms-excel;charset=GBK");
            outputStream = response.getOutputStream();
            ServletContext context = request.getSession().getServletContext();
            String fullPath = context.getRealPath(EPPSCBPConstants.TEMPLATE_PATH + EPPSCBPConstants.TRADE_SUPERVISE);
            File file = new File(fullPath);
            // 下载乱码问题
            byte[] bytes = FileConverterUtil.toByteExcel(file);
            outputStream.write(bytes);

        } catch (Exception e) {
            LOGGER.error("下载货物贸易监管文件失败,原因是：{}", e);

        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error("下载货物贸易监管文件失败,原因是：{}", e);

                }
            }
        }
        LOGGER.info("货物贸易监管文件下载结束");
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈货物贸易监管文件上传〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "tradeSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String tradeSuperviseUpload(@RequestParam("fileAddress") String fileAddress, @RequestParam("businessNo") String businessNo, HttpServletRequest request) {
        LOGGER.info("货物贸易监管文件请求跨境开始{},{}", businessNo, fileAddress);
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<String> apiResDto = orderQueryService.tradeSuperviseUpload(businessNo, fileAddress, request.getRemoteUser(), "");
        LOGGER.info("触发跨境返回:{}", apiResDto.toString());
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("货物贸易监管文件请求跨境同步结束");
        return JSON.toJSONString(result);
    }

    /**
     * 
     * 功能描述: <br>
     * 〈触发留学教育监管上传〉
     *
     * @param businessNo
     * @param flag
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "studySuperviseUpload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String studySuperviseUpload(@RequestParam("businessNo") String businessNo, HttpServletRequest request) {
        LOGGER.info("留学教育监管文件上传开始:{},{}", businessNo, request.getRemoteUser());
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<String> apiResDto = orderQueryService.studySuperviseUpload(businessNo, request.getRemoteUser());
        LOGGER.info("触发跨境留学教育返回:{}", apiResDto.toString());
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("留学教育监管文件同步上传结束");
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈货物贸易监管文件校验〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileCheck", produces = "application/json;charset=UTF-8")
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, HttpServletRequest request, HttpServletResponse response)throws IOException {
        LOGGER.info("货物贸易监管文件上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            res = orderQueryService.uploadFile(targetFile, request.getRemoteUser());
        } catch (ExcelForamatException e) {
            LOGGER.error("货物贸易监管文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());       
        } catch (Exception e) {
            LOGGER.error("货物贸易监管文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("货物贸易监管文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }
    
    /**
     * 功能描述: <br>
     * 〈去支付〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "toPay")
    @ResponseBody
    public ModelAndView toPay(HttpServletRequest request, @ModelAttribute("businessNo") String businessNo, @ModelAttribute("bizType") String bizType, @ModelAttribute("productType") String productType) {
        ModelAndView mav = new ModelAndView();
        String succesMav = "";
        String errorMav = "";
        if (ChargeBizType.TYPE_GOODS_TRADE.getCode().equals(bizType)) {
            errorMav = GOODS_PAY_ERROR;
            if (CbpProductType.SINGLE_GFH.getCode().equals(productType)) {
                succesMav = GOODS_EXANDPAY_CONFIRM;
            } else if (CbpProductType.SINGLE_FH.getCode().equals(productType)) {
                succesMav = GOODS_PAY_CONFIRM;
            } else if (CbpProductType.SINGLE_RMB.getCode().equals(productType)) {
                succesMav = GOODS_RMBPAY_CONFIRM;
            }
        } else if (ChargeBizType.TYPE_ABROAD_EDUCATION.getCode().equals(bizType)) {
            errorMav = OVERSEASPAY_PAY_ERROR;
            if (CbpProductType.SINGLE_GFH.getCode().equals(productType)) {
                succesMav = STUDY_EXANDPAY_CONFIRM;
            } else if (CbpProductType.SINGLE_FH.getCode().equals(productType)) {
                succesMav = STUDY_PAY_CONFIRM;
            }
        }else if(ChargeBizType.TYPE_LOGISTICS.getCode().contentEquals(bizType)) {
        	errorMav = LOGISTICS_PAY_ERROR;
        	if(CbpProductType.SINGLE_RMB.getCode().equals(productType)) {
        		succesMav = LOGISTICS_RMBPAY_CONFIRM;
        	}
        }
        try {
            LOGGER.info("汇率刷新开始:{}", businessNo);
            OrderExRateRefreshDto orderExRateRefreshDto = new OrderExRateRefreshDto();
            orderExRateRefreshDto.setPayerAccount(request.getRemoteUser());
            orderExRateRefreshDto.setBusinessNo(businessNo);
            orderExRateRefreshDto.setFlag(CommonConstant.N);
            OrderlAcquireResponseDto orderlAcquireResponseDto = unifiedReceiptService.refreshRate(orderExRateRefreshDto);
            if (orderlAcquireResponseDto == null) {
                // 刷新汇率失败
                mav.addObject(EPPSCBPConstants.BUSINESS_NO, businessNo);
                mav.setViewName(errorMav);
            } else {
                // 刷新汇率成功
                mav.addObject(EPPSCBPConstants.CRITERIA, orderlAcquireResponseDto);
                mav.setViewName(succesMav);
            }
            LOGGER.info("汇率刷新结束");
        } catch (Exception ex) {
            LOGGER.error("汇率刷新异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return mav;
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
    public CommonResDto toClose(HttpServletRequest request, @ModelAttribute("businessNo") String businessNo) {
        CommonResDto commonResDto = new CommonResDto();
        try {
            LOGGER.info("关闭订单开始:{}", businessNo);
            OrderOperationDto orderOperationDto = new OrderOperationDto();
            orderOperationDto.setPayerAccount(request.getRemoteUser());
            orderOperationDto.setBusinessNo(businessNo);
            ApiResDto<String> apiResDto = unifiedReceiptService.doOrderOperation(orderOperationDto, CommonConstant.OPERATING_TYPE_CLOSE);
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
     * 功能描述: <br>
     * 〈关闭订单〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "queryDetailFlag")
    @ResponseBody
    public CommonResDto queryDetailFlag(HttpServletRequest request, @ModelAttribute("businessNo") String businessNo) {
        CommonResDto commonResDto = new CommonResDto();
        try {
            LOGGER.info("查询明细校验状态:{}", businessNo);
            OrderOperationDto orderOperationDto = new OrderOperationDto();
            orderOperationDto.setPayerAccount(request.getRemoteUser());
            orderOperationDto.setBusinessNo(businessNo);
            ApiResDto<String> apiResDto = unifiedReceiptService.queryDetailFlag(orderOperationDto);
            if (!apiResDto.isSuccess()) {
                // 明细校验未通过
                commonResDto.fail();
            }
            LOGGER.info("查询明细校验状态结束");
        } catch (Exception ex) {
            LOGGER.error("查询明细校验状态异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return commonResDto;
    }

    /**
     * 功能描述: <br>
     * 〈二要素鉴权失败明细文件-下载〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "downLoadFailFile", produces = "text/html;charset=UTF-8")
    public void downLoadFailFile(@ModelAttribute("failFileAddress") String failFileAddress, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        try {
            LOGGER.info("下载文件开始。failFileAddress:{}", failFileAddress);
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(failFileAddress, "UTF-8"));
            OSSDownloadParams params = new OSSDownloadParams(OSSBucket.DETAIL_FILE, failFileAddress);
            in = OSSClientUtil.getInputStream(params);

            if (in == null) {
                LOGGER.error("文件不存在。文件名称:{}", failFileAddress);
                return;
            }
            out = response.getOutputStream();
            int len = 0;
            byte buf[] = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            LOGGER.info("下载结束,文件名:{}", failFileAddress);
        } catch (Exception e) {
            LOGGER.error("下载文件出错,错误异常:{}", org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
        }finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭流失败");
            }
        }
    }
}
