package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.RefundOrderStatus;
import com.suning.epp.eppscbp.dto.req.RefundOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.RefundOrderQueryRespDto;
import com.suning.epp.eppscbp.service.RefundOrderService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈国际卡收单_退款订单〉<br>
 * 〈功能详细描述〉
 *
 * @author 19043747
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/oca/ocaRefundOrderController/")
public class OcaRefundOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OcaRefundOrderController.class);
	private static final String INIT_QUERY = "screen/oca/refundOrder/ocaRefundOrderMain";
	private static final String DETAIL_QUERY = "screen/oca/refundOrder/ocaRefundOrderDetail";
	//导出
	private static final String REFUND_ORDER_EXPORT_URL = "/cbpExport/refundOrder.do?";
	
	@Autowired
	private RefundOrderService refundOrderService;
	
	@Value("${oca.url}")
    private String ocaUrl;
	
	/**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
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
    public ModelAndView init(@ModelAttribute("merchantOrderNo") String merchantOrderNo) {
        LOGGER.info("退款订单查询初始化开始");
        ModelAndView mav = createMainView();
        mav.addObject(EPPSCBPConstants.STATUS, RefundOrderStatus.values());
        mav.addObject(EPPSCBPConstants.CRITERIA, merchantOrderNo);
        LOGGER.info("退款订单查询初始化结束");
        return mav;
    }
    /**
     * 功能描述: <br>
     * 〈退款订单查询〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String refundOrderQuery(@ModelAttribute("requestDto") RefundOrderQueryReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到退款订单查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<RefundOrderQueryRespDto>> responseParam = refundOrderService.queryRefundOrder(requestDto);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        String list = "[]";
        String success = "false";
        long totalCount = 0l;
        if (responseParam.isSuccess()) {
        	list = JSON.toJSONString(responseParam.getResult());
            success = "true";
            totalCount = responseParam.getPage().getCount();
            
        } else if (EPPSCBPConstants.OCA_NO_RESULT.equals(responseParam.getResponseCode())) {
            success = "true";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"list\":").append(list).append(",\"success\":").append(success).append(",\"totalCount\":").append(totalCount).append("}");
        return sb.toString();
    }
    
    /**
     * 功能描述: <br>
     * 〈退款-详情〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("refundOrderDetailInfo")
    public ModelAndView refundOrderdetailInfo(@ModelAttribute("refundOrderNo") String refundOrderNo, HttpServletRequest request) {
    	LOGGER.info("退款单详情查询开始");
    	String payerAccount = request.getRemoteUser();
        ModelAndView mav = new ModelAndView(DETAIL_QUERY);
        LOGGER.info("触发rsf调用进行查询,refundOrderNo:{},payerAccount:{}", refundOrderNo, payerAccount);
        ApiResDto<RefundOrderQueryRespDto> responseParam = refundOrderService.queryDetailInfo(payerAccount, refundOrderNo);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            mav.addObject(EPPSCBPConstants.RESULT, responseParam.getResult());
            LOGGER.info("获取到查询结果,result:{}", responseParam.getResult().toString());
        }
        return mav;
    }
    
    /**
     * 功能描述: <br>
     * 〈退款-导出〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void export(@ModelAttribute("requestDto") RefundOrderQueryReqDto requestDto,  HttpServletRequest request, HttpServletResponse response) throws IOException {
    	LOGGER.info("收到退款单导出请求,requestDto:{}", requestDto);

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        // 接口入参预处理
        LOGGER.info("开始退款单导出入参预处理");
        // 退款订单号
        requestDto.setRefundOrderNo(requestDto.getRefundOrderNo());
        // 商户订单号
        requestDto.setMerchantOrderNo(StringUtil.isEmpty(requestDto.getMerchantOrderNo()) ? null : requestDto.getMerchantOrderNo().trim());
        // 退款状态
        requestDto.setRefundStatus(StringUtil.isEmpty(requestDto.getRefundStatus()) ? null : requestDto.getRefundStatus().trim());
        // 退款创建时间结束
        requestDto.setRefundCreateFromTime(StringUtil.isEmpty(requestDto.getRefundCreateFromTime()) ? null : requestDto.getRefundCreateFromTime());
        // 退款创建时间结束
        requestDto.setRefundCreateToTime(StringUtil.isEmpty(requestDto.getRefundCreateToTime()) ? null : requestDto.getRefundCreateToTime());
        // 退款完成时间开始
        requestDto.setRefundFinishFromTime(StringUtil.isEmpty(requestDto.getRefundFinishFromTime()) ? null : requestDto.getRefundFinishFromTime());
        // 退款完成结束时间
        requestDto.setRefundFinishToTime(StringUtil.isEmpty(requestDto.getRefundFinishToTime()) ? null : requestDto.getRefundFinishToTime());

        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = ocaUrl + REFUND_ORDER_EXPORT_URL + tempUrl;

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
     * 〈退款-确认退款〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "refundOrderSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String refundOrderSubmit(@ModelAttribute("receiptOrderNo") String receiptOrderNo, @ModelAttribute("merchantOrderNo") String merchantOrderNo, @ModelAttribute("refundAmt") String refundAmt, HttpServletRequest request) {
        String payerAccount = request.getRemoteUser();

        LOGGER.info("OCA退款确认，触发rsf调用进行查询,receiptOrderNo:{},refundAmt:{},payerAccount:{},merchantOrderNo:{}", receiptOrderNo, refundAmt, payerAccount, merchantOrderNo);
        ApiResDto<Boolean> responseParam = refundOrderService.refundSubmit(payerAccount, receiptOrderNo, merchantOrderNo, refundAmt);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        StringBuilder sb = new StringBuilder();
        String success = "false";
        if(responseParam.isSuccess()) {
        	success = "true";
        }
        sb.append("{").append("\"success\":").append(success).append(",").append("\"message\":").append("\"" +responseParam.getResponseMsg()+"\"").append("}");
        return sb.toString();
    }
    
    
}
