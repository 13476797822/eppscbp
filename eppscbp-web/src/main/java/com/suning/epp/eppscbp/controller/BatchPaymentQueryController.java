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

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.CpBatchPaymentStatus;
import com.suning.epp.eppscbp.dto.req.CpBatchPaymentDetailReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CpBatchPaymentDetailResDto;
import com.suning.epp.eppscbp.rsf.service.CpBatchDetailQueryService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
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
@RequestMapping("/batchPaymentQuery/batchPaymentQuery/")
public class BatchPaymentQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchPaymentQueryController.class);

    private static final String INIT_QUERY = "screen/batchPaymentQuery/init";

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    @Autowired
    private CpBatchDetailQueryService cpBatchDetailQueryService;

    private static final String BATCH_PAYMENT_DETAIL_EXPORT_URL = "/cbpExport/batchPaymentExport.do?";

    /**
     * 初始化主视图
     *
     * @return
     */
    private ModelAndView createMainView() {
        LOGGER.info("批付明细查询初始参数开始");
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.STATUS, CpBatchPaymentStatus.getAllDescription());
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
        LOGGER.info("批付明细查询初始化开始");
        ModelAndView mav = createMainView();
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
    public ModelAndView batchPaymentDetailQuery(@ModelAttribute("requestDto") CpBatchPaymentDetailReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到批付明细查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());
        requestDto.setPlatformCode(CommonConstant.CBP_CODE);

        LOGGER.info("触发rsf调用进行批付明细查询,requestDto:{}", requestDto);
        ApiResDto<List<CpBatchPaymentDetailResDto>> responseParam = cpBatchDetailQueryService.batchPaymentDetailQuery(requestDto);
        LOGGER.info("rsf批付明细查询结果,responseParam:{}", responseParam);

        if (!responseParam.isSuccess()) {
            // 未查询到数据或查询出错
            mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);

        } else {
            mav.addObject(EPPSCBPConstants.RESULT_LIST, responseParam.getResult());
            LOGGER.info("获取到批付明细查询结果,resultList:{}", responseParam.getResult());
            mav.addObject(EPPSCBPConstants.PAGE, responseParam.getPage());
            LOGGER.info("获取到批付明细分页信息,page:{}", responseParam.getPage());
        }

        return mav;
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
    public void batchPaymentDetailExport(@ModelAttribute("requestDto") CpBatchPaymentDetailReqDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("收到批付明细导出请求,requestDto:{}", requestDto);

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        // 接口入参预处理
        LOGGER.info("开始批付明细导出入参预处理");
        // 户头号
        requestDto.setPayerAccount(requestDto.getPayerAccount());
        // 出款状态
        requestDto.setStatus(StringUtil.isEmpty(CpBatchPaymentStatus.getCodeFromDescription(requestDto.getStatus())) ? null : CpBatchPaymentStatus.getCodeFromDescription(requestDto.getStatus()));
        requestDto.setPlatformCode(CommonConstant.CBP_CODE);

        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = eppcbsUrl + BATCH_PAYMENT_DETAIL_EXPORT_URL + tempUrl;

        LOGGER.info("导出请求url,url:{}", url);

        InputStream input = HttpUtil.httpGetRequestIO(url);
        String fileName = DateUtil.formatDate(new Date(), DateConstant.DATEFORMATE_YYYYMMDDHHMMSSSSS);

        OutputStream outputStream;
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
        outputStream = response.getOutputStream();

        LOGGER.info("查询完毕,开始导出");

        outputStream.write(HttpUtil.readInputStream(input));

    }

}
