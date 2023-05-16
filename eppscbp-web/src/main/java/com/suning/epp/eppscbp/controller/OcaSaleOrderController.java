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
import com.suning.epp.eppscbp.common.enums.OcaCurType;
import com.suning.epp.eppscbp.common.enums.TradeOrderStatus;
import com.suning.epp.eppscbp.dto.req.SaleOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.SaleOrderDetailQueryRespDto;
import com.suning.epp.eppscbp.dto.res.SaleOrderQueryRespDto;
import com.suning.epp.eppscbp.service.SaleOrderService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈国际卡收单_销售订单〉<br>
 * 〈功能详细描述〉
 *
 * @author 19043747
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/oca/ocaSaleOrderController/")
public class OcaSaleOrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OcaSaleOrderController.class);
    private static final String INIT_QUERY_MAIN = "screen/oca/saleOrder/ocaSaleOrderMain";

    private static final String INIT_QUERY_DETAIL = "screen/oca/saleOrder/ocaSaleOrderDetail";

    private static final String SALE_ORDER_EXPORT_URL = "/cbpExport/saleOrder.do?";

    @Autowired
    private SaleOrderService saleOrderService;

    @Value("${oca.url}")
    private String ocaUrl;

    /**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY_MAIN);
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
        LOGGER.info("销售订单查询初始化开始");
        ModelAndView mav = createMainView();
        mav.addObject(EPPSCBPConstants.STATUS, TradeOrderStatus.values());
        mav.addObject(EPPSCBPConstants.CURRENCY, OcaCurType.values());
        LOGGER.info("销售订单查询初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈销售订单查询〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saleOrderQuery(@ModelAttribute("requestDto") SaleOrderQueryReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到销售订单查询请求,requestDto:{}", requestDto);

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<SaleOrderQueryRespDto>> responseParam = saleOrderService.saleOrderQuery(requestDto);
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
     * 〈销售订单详情查询〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("saleOrderdetailInfo")
    public ModelAndView saleOrderdetailInfo(@ModelAttribute("receiptOrderNo") String receiptOrderNo, HttpServletRequest request) {
        LOGGER.info("销售订单详情查询开始");
        String payerAccount = request.getRemoteUser();
        ModelAndView mav = new ModelAndView(INIT_QUERY_DETAIL);
        LOGGER.info("触发rsf调用进行查询,receiptOrderNo:{},payerAccount:{}", receiptOrderNo, payerAccount);
        ApiResDto<SaleOrderDetailQueryRespDto> responseParam = saleOrderService.queryDetailInfo(payerAccount, receiptOrderNo);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            mav.addObject(EPPSCBPConstants.RESULT, responseParam.getResult());
            LOGGER.info("获取到查询结果,result:{}", responseParam.getResult().toString());
        }
        mav.addObject(EPPSCBPConstants.RESULT, responseParam.getResult());
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈点击退款〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "refundInfo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String refundInfo(@ModelAttribute("receiptOrderNo") String receiptOrderNo, HttpServletRequest request) {
        LOGGER.info("退款开始");
        String payerAccount = request.getRemoteUser();
        LOGGER.info("触发rsf调用进行查询,receiptOrderNo:{},payerAccount:{}", receiptOrderNo, payerAccount);
        ApiResDto<SaleOrderDetailQueryRespDto> responseParam = saleOrderService.queryDetailInfo(payerAccount, receiptOrderNo);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            SaleOrderDetailQueryRespDto dto = responseParam.getResult();
            dto.setSuccess("true");
            LOGGER.info("获取到查询结果,dto:{}", dto);
            return JSON.toJSONString(dto);
        }

        return null;
    }

    /**
     * 功能描述: <br>
     * 〈销售订单数据导出〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void export(@ModelAttribute("requestDto") SaleOrderQueryReqDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("收到销售订单导出请求,requestDto:{}", requestDto);

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        // 接口入参预处理
        LOGGER.info("开始销售订单导出入参预处理");
        // 户头号
        requestDto.setPayerAccount(request.getRemoteUser());
        // 商户订单号
        requestDto.setMerchantOrderNo(StringUtil.isEmpty(requestDto.getMerchantOrderNo()) ? null : requestDto.getMerchantOrderNo().trim());
        // 支付请求单号
        requestDto.setReceiptOrderNo(StringUtil.isEmpty(requestDto.getReceiptOrderNo()) ? null : requestDto.getReceiptOrderNo());
        // 订单状态
        requestDto.setOrderStatus(StringUtil.isEmpty(requestDto.getOrderStatus()) ? null : requestDto.getOrderStatus());
        // 订单币种
        requestDto.setCur(StringUtil.isEmpty(requestDto.getCur()) ? null : requestDto.getCur());
        // 订单创建开始时间
        requestDto.setOrderCreateFromTime(StringUtil.isEmpty(requestDto.getOrderCreateFromTime()) ? null : requestDto.getOrderCreateFromTime());
        // 订单创建结束时间
        requestDto.setOrderCreateToTime(StringUtil.isEmpty(requestDto.getOrderCreateToTime()) ? null : requestDto.getOrderCreateToTime().trim());
        // 支付完成开始时间
        requestDto.setPayFinishFromTime(StringUtil.isEmpty(requestDto.getPayFinishFromTime()) ? null : requestDto.getPayFinishFromTime().trim());
        // 支付完成结束时间
        requestDto.setPayFinishToTime(StringUtil.isEmpty(requestDto.getPayFinishToTime()) ? null : requestDto.getPayFinishToTime().trim());

        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = ocaUrl + SALE_ORDER_EXPORT_URL + tempUrl;

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

    @RequestMapping(value = "batchQueryChannel", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchQueryChannel(@ModelAttribute("receiptOrderNoStr") String receiptOrderNoStr, HttpServletRequest request) {

        // 判断批量结果查询的参数: 外卡收单单号是否为空
        if (StringUtil.isEmpty(receiptOrderNoStr)) {
            LOGGER.info("请求参数为空");
            return null;
        }
        String payerAccount = request.getRemoteUser();
        LOGGER.info("触发rsf调用进行查询,receiptOrderNoStr:{},payerAccount:{}", receiptOrderNoStr, payerAccount);
        ApiResDto<Boolean> responseParam = saleOrderService.batchQueryChannel(payerAccount, receiptOrderNoStr);
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
