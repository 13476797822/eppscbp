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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.CbpStatus;
import com.suning.epp.eppscbp.common.enums.CbpSupStatus;
import com.suning.epp.eppscbp.dto.req.BatchOrderQueryDto;
import com.suning.epp.eppscbp.dto.req.OrderExRateRefreshDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchTradeOrderQueryResDto;
import com.suning.epp.eppscbp.dto.res.CommonResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.rsf.service.OrderQueryService;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈批量交易查询和导出〉<br>
 * 〈功能详细描述〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/batchQuery/batchOrderQuery/")
public class BatchOrderQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchOrderQueryController.class);

    private static final String INIT_QUERY = "screen/batchOrderSearch/batchOrderSearch";

    private static final String STUDY_BATCHEXANDPAY_CONFIRM = "screen/overseasPay/studyBatchExAndPay/confirm";

    // 系统异常页面
    private static final String OVERSEASPAY_PAY_ERROR = "screen/overseasPay/error";

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private UnifiedReceiptService unifiedReceiptService;

    private final String batchOrderExportUrl = "/cbpExport/batchTradeOrder.do?";

    /**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.PAY_STATUS, CbpStatus.getAllPayDescription());
        mvn.addObject(EPPSCBPConstants.SUP_STATUS, CbpSupStatus.getAllDescription());
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
        LOGGER.info("批量交易单查询初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("批量交易单查询初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈批量交易单查询〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("query")
    public ModelAndView batchOrderQuery(@ModelAttribute("requestDto") BatchOrderQueryDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到批量交易单查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());
        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<BatchTradeOrderQueryResDto>> apiResDto = orderQueryService.batchOrderQuery(requestDto);
        LOGGER.info("rsf查询结果,apiResDto:{}", apiResDto.toString());
        if (apiResDto.isSuccess()) {
            mav.addObject(EPPSCBPConstants.RESULT_LIST, apiResDto.getResult());
            LOGGER.info("获取到查询结果,resultList:{}", apiResDto.getResult().toString());
            mav.addObject(EPPSCBPConstants.PAGE, apiResDto.getPage());
            LOGGER.info("获取到分页信息,page:{}", apiResDto.getPage().toString());
        } else {
            // 未查询到数据或查询出错
            mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);
        }
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈批量交易单数据导出〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void singleOrderExport(@ModelAttribute("requestDto") BatchOrderQueryDto requestDto, HttpServletResponse response, HttpServletRequest request) throws IOException {
        LOGGER.info("收到批量交易单查询请求,requestDto:{}", requestDto);

        // 接口入参预处理
        LOGGER.info("开始批量交易单查询入参预处理");
        // 户头号
        requestDto.setPayerAccount(request.getRemoteUser());
        // 业务单号
        requestDto.setBusinessNo(StringUtil.isEmpty(requestDto.getBusinessNo()) ? null : requestDto.getBusinessNo().trim());
        // 状态
        requestDto.setStatus(StringUtil.isEmpty(CbpStatus.getCodeFromDescription(requestDto.getStatus())) ? null : CbpStatus.getCodeFromDescription(requestDto.getStatus()));
        // 监管状态
        requestDto.setSupStatus(StringUtil.isEmpty(CbpSupStatus.getCodeFromDescription(requestDto.getSupStatus())) ? null : CbpSupStatus.getCodeFromDescription(requestDto.getSupStatus()));
        // 创建开始时间
        requestDto.setCreatOrderStartTime(StringUtil.isEmpty(requestDto.getCreatOrderStartTime()) ? null : requestDto.getCreatOrderStartTime().trim());
        // 创建结束时间
        requestDto.setCreatOrderEndTime(StringUtil.isEmpty(requestDto.getCreatOrderEndTime()) ? null : requestDto.getCreatOrderEndTime().trim());

        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));

        String url = eppcbsUrl + batchOrderExportUrl + tempUrl;

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
     * 〈去支付〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "toPay")
    public ModelAndView toPay(HttpServletRequest request, @ModelAttribute("businessNo") String businessNo, @ModelAttribute("bizType") String bizType, @ModelAttribute("productType") String productType) {
        ModelAndView mav = new ModelAndView();

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
                mav.setViewName(OVERSEASPAY_PAY_ERROR);
            } else {
                // 刷新汇率成功
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

}
