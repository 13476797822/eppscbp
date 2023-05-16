/**
 * 
 */
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
import org.springframework.web.servlet.ModelAndView;

import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.CbpStatus;
import com.suning.epp.eppscbp.dto.req.BatchOrderRemitQueryDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchTradeOrderRemitQueryResDto;
import com.suning.epp.eppscbp.dto.res.TradeOrderDetailQueryResDto;
import com.suning.epp.eppscbp.rsf.service.OrderQueryService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈批量明细汇出查询、详情和导出〉<br>
 * 〈功能详细描述〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/batchQuery/batchOrderRemitQuery/")
public class BatchOrderRemitQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchOrderQueryController.class);

    private static final String INIT_QUERY = "screen/batchOrderSearch/batchOrderRemitSearch";

    private static final String DETAIL_INFO = "screen/batchOrderSearch/batchDetail";

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    @Autowired
    private OrderQueryService orderQueryService;

    private static final String batchOrderRemitExportUrl = "/cbpExport/batchTradeOrderRemit.do?";

    /**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.REMIT_STATUS, CbpStatus.getAllRemitDescription());
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
        LOGGER.info("明细汇出查询初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("明细汇出查询初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈明细汇出查询〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("query")
    public ModelAndView batchOrderRemitQuery(@ModelAttribute("requestDto") BatchOrderRemitQueryDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到批量明细汇出查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<BatchTradeOrderRemitQueryResDto>> apiResDto = orderQueryService.batchOrderRemitQuery(requestDto);
        LOGGER.info("rsf查询结果,responseParam:{}", apiResDto.toString());
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
     * 〈明细汇出导出〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void batchOrderRemitExport(@ModelAttribute("requestDto") BatchOrderRemitQueryDto requestDto, HttpServletResponse response, HttpServletRequest request) throws IOException {
        LOGGER.info("收到批量明细汇出导出请求,requestDto:{}", requestDto);

        // 接口入参预处理
        LOGGER.info("开始批量明细汇出查询入参预处理");
        // 户头号
        requestDto.setPayerAccount(request.getRemoteUser());
        // 业务单号
        requestDto.setBusinessNo(StringUtil.isEmpty(requestDto.getBusinessNo()) ? null : requestDto.getBusinessNo().trim());
        // 业务子单号
        requestDto.setSubBusinessNo(StringUtil.isEmpty(requestDto.getSubBusinessNo()) ? null : requestDto.getSubBusinessNo());
        // 状态
        requestDto.setStatus(StringUtil.isEmpty(CbpStatus.getCodeFromDescription(requestDto.getStatus())) ? null : CbpStatus.getCodeFromDescription(requestDto.getStatus()));
        // 收款方商户号
        requestDto.setPayeeMerchantCode(StringUtil.isEmpty(requestDto.getPayeeMerchantCode()) ? null : requestDto.getPayeeMerchantCode().trim());
        // 收款方商户名称
        requestDto.setPayeeMerchantName(StringUtil.isEmpty(requestDto.getPayeeMerchantName()) ? null : requestDto.getPayeeMerchantName().trim());
        // 创建开始时间
        requestDto.setCreatOrderStartTime(StringUtil.isEmpty(requestDto.getCreatOrderStartTime()) ? null : requestDto.getCreatOrderStartTime().trim());
        // 创建结束时间
        requestDto.setCreatOrderEndTime(StringUtil.isEmpty(requestDto.getCreatOrderEndTime()) ? null : requestDto.getCreatOrderEndTime().trim());

        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));

        String url = eppcbsUrl + batchOrderRemitExportUrl + tempUrl;

        LOGGER.info("导出请求url,url:{}", url);

        InputStream input = HttpUtil.httpGetRequestIO(url);
        String fileName = DateUtil.formatDate(new Date(), DateConstant.DATEFORMATE_YYYYMMDDHHMMSSSSS);

        OutputStream outputStream = null;
        response.reset();
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
        outputStream = response.getOutputStream();

        LOGGER.info("查询完毕,开始导出");

        outputStream.write(HttpUtil.readInputStream(input));

    }

    /**
     * 功能描述: <br>
     * 〈明细汇出详情〉
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
        if (EPPSCBPConstants.SUCCESS_CODE.equals(responseParam.getResponseCode())) {
            mav.addObject(EPPSCBPConstants.RESULT, responseParam.getResult());
        }
        return mav;
    }

}
