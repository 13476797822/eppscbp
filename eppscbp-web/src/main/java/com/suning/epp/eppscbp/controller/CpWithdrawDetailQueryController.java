/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpWithdrawDetailQueryController.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
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
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.StorePlatformEnum;
import com.suning.epp.eppscbp.common.enums.WithdrawOrderStatus;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.CpWithdrawDetailReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CpWithdrawDetailResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.rsf.service.CpBatchDetailQueryService;
import com.suning.epp.eppscbp.rsf.service.OrderQueryService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈提现明细查询和导出〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/cpWithdrawDetail/cpWithdrawDetailQuery/")
public class CpWithdrawDetailQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CpWithdrawDetailQueryController.class);

    private static final String INIT_QUERY = "screen/cpWithdrawDetail/cpWithdrawDetailQuery";

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    @Autowired
    private CpBatchDetailQueryService cpBatchDetailQueryService;

    @Autowired
    private OrderQueryService orderQueryService;

    private static final String WITHDWAR_DETAIL_EXPORT_URL = "/cbpExport/batchWithdrawExport.do?";

    /**
     * 初始化主视图
     *
     * @return
     */
    private ModelAndView createMainView() {
        LOGGER.info("提现明细查询初始参数开始");
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.STORE_PLATFORM, StorePlatformEnum.getAllDescription());//店铺平台类型
        mvn.addObject(EPPSCBPConstants.CURRENCY, CurType.getAllDescription());//币种类型
        mvn.addObject(EPPSCBPConstants.ORDER_STATUS, WithdrawOrderStatus.getAllDescription());//订单状态
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
        LOGGER.info("提现明细查询初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("提现明细查询初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈提现明细查询〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("query")
    public ModelAndView withdrawDetailQuery(@ModelAttribute("requestDto") CpWithdrawDetailReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到提现明细查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        LOGGER.info("触发rsf调用进行提现明细查询,requestDto:{}", requestDto);
        ApiResDto<List<CpWithdrawDetailResDto>> responseParam = cpBatchDetailQueryService.withdrawDetailQuery(requestDto);
        LOGGER.info("rsf提现明细查询结果,responseParam:{}", responseParam);

        if (responseParam.isSuccess()) {
            List<CpWithdrawDetailResDto> resultList = responseParam.getResult();
            DalPage pageInfo = responseParam.getPage();
            mav.addObject(EPPSCBPConstants.RESULT_LIST, resultList);
            LOGGER.info("获取到提现明细查询结果,resultList:{}", resultList);
            mav.addObject(EPPSCBPConstants.PAGE, pageInfo);
            LOGGER.info("获取到提现明细分页信息,page:{}", pageInfo);
        } else {
            // 未查询到数据或查询出错
            mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);
        }
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈提现明细数据导出〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void withdrawDetailExport(@ModelAttribute("requestDto") CpWithdrawDetailReqDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("收到提现明细导出请求,requestDto:{}", requestDto);

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        // 接口入参预处理
        LOGGER.info("开始提现明细导出入参预处理");
        // 户头号
        requestDto.setPayerAccount(requestDto.getPayerAccount());
        // 货币
        requestDto.setCurrency(StringUtil.isEmpty(CurType.getCodeFromDescription(requestDto.getCurrency())) ? null : CurType.getCodeFromDescription(requestDto.getCurrency()));
        // 店铺平台
        requestDto.setStorePlatform(StringUtil.isEmpty(StorePlatformEnum.getCodeFromDescription(requestDto.getStorePlatform())) ? null : StorePlatformEnum.getCodeFromDescription(requestDto.getStorePlatform()));
        // 订单状态
        requestDto.setOrderStatus(StringUtil.isEmpty(WithdrawOrderStatus.getCodeFromDescription(requestDto.getOrderStatus())) ? null : WithdrawOrderStatus.getCodeFromDescription(requestDto.getOrderStatus()));
        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = eppcbsUrl + WITHDWAR_DETAIL_EXPORT_URL + tempUrl;

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

    /**
     * 功能描述: <br>
     * 〈提现明细真实性材料文件上传〉
     *
     * @param fileAddress
     * @param request
     * @param businessNo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "tradeSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String tradeSuperviseUpload(@RequestParam("fileAddress") String fileAddress, @RequestParam("businessNo") String businessNo, HttpServletRequest request) {
        LOGGER.info("提现明细真实性材料文件请求跨境开始{},{}", businessNo, fileAddress);
        Map<String, Object> result = new HashMap<String, Object>();
        // 触发跨境
        ApiResDto<String> apiResDto = orderQueryService.tradeSuperviseUpload(businessNo, fileAddress, request.getRemoteUser(), EPPSCBPConstants.CCP);
        LOGGER.info("触发真实性材料跨境返回:{}", apiResDto);
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("返回真实性材料页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("提现明细真实性材料文件请求跨境同步结束");
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈真实性材料文件校验〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileCheck", produces = "application/json;charset=UTF-8")
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("真实性材料文件上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            res = orderQueryService.uploadFile(targetFile, request.getRemoteUser());
        } catch (ExcelForamatException e) {
            LOGGER.error("真实性材料文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("真实性材料文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("真实性材料文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }

}
