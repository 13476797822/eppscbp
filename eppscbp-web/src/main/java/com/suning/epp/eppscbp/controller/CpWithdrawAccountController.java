/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpWithdrawAccountController.java
 * Author:   88412423
 * Date:     2019/05/26 9:19
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

/**
 * 〈提现申请〉<br>
 * 〈功能详细描述〉
 *
 * @author 88412423
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suning.epp.eppscbp.common.enums.DataType;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.AccountCharacter;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.DomesticMerchantInfoReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.DomesticMerchantInfoResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.service.DomesticMerchantInfoService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

@Controller()
@SessionAttributes("bankList")
@RequestMapping("/cpWithdrawAccount/")
public class CpWithdrawAccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CpWithdrawAccountController.class);

    private static final String WITHDRAW_ACCOUNT = "screen/cpWithdrawAccount/cpWithdrawAccount";

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    private static final String SESSION_KEY = "bankList";

    private static final String MERCHANT_EXPORT_URL = "/cbpExport/domesticPayeeInfo.do?";

    @Autowired
    private DomesticMerchantInfoService domesticMerchantInfoService;

    /**
     * 初始化主视图
     *
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(WITHDRAW_ACCOUNT);
        mvn.addObject(EPPSCBPConstants.BIZ_TYPE, ChargeBizType.getGoodsMapWithoutAll());
        mvn.addObject(EPPSCBPConstants.DATA_TYPE, DataType.getAll());
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
    public ModelAndView init(Model model) {
        LOGGER.info("收款方提现账号初始化开始");
        ModelAndView mav = createMainView();
        try {
            LOGGER.info("获取提现账号信息开始");
            // 获取信息加入session
            ApiResDto<JSONArray> apiResDto = domesticMerchantInfoService.getBankList();
            if (apiResDto.isSuccess()) {
                model.addAttribute(SESSION_KEY, JSON.toJSONString(apiResDto.getResult()));
            }

        } catch (Exception ex) {
            LOGGER.error("获取提现账号信息异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("获取提现账号信息结束");
        LOGGER.info("收款方提现账号初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈收款方提现账号数据导出〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void merchantInfoExport(@ModelAttribute("requestDto") DomesticMerchantInfoReqDto requestDto, HttpServletResponse response, HttpServletRequest request) throws IOException {
        LOGGER.info("收到境内提现账号请求,requestDto:{}", requestDto);

        // 获取户头号
        String userNo = request.getRemoteUser();
        if (StringUtil.isNotNull(userNo)) {
            requestDto.setPayerAccount(userNo);
        }

        // 业务类型
        requestDto.setBizType(StringUtil.isEmpty(ChargeBizType.getCodeFromDescription(requestDto.getBizType())) ? null : ChargeBizType.getCodeFromDescription(requestDto.getBizType()));
        //数据类型
        requestDto.setDataType(StringUtil.isEmpty(DataType.getCodeFromDescription(requestDto.getDataType())) ? null : DataType.getCodeFromDescription(requestDto.getDataType()));

        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));

        String url = eppcbsUrl + MERCHANT_EXPORT_URL + tempUrl;

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
     *
     * 查看详情<br>
     * 〈功能详细描述〉
     *
     * @param payeeMerchantCode
     * @param request
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "queryDetail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryDetail(@RequestParam("payeeMerchantCode") String payeeMerchantCode, HttpServletRequest request) {
        LOGGER.info("收到指定境内提现账号详情查询请求,payeeMerchantCode:{}", payeeMerchantCode);
        Map<String, Object> result = new HashMap<String, Object>();
        String userNo = request.getRemoteUser();
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("payeeMerchantCode", payeeMerchantCode);
        requestMap.put("payerAccount", userNo);
        LOGGER.info("修改详情请求{}", requestMap);
        ApiResDto<DomesticMerchantInfoResDto> apiResDto = domesticMerchantInfoService.merchantInfoDetail(requestMap);
        LOGGER.info("单笔查询结果{}", apiResDto);
        if (apiResDto.isSuccess()) {
            DomesticMerchantInfoResDto detailDto = apiResDto.getResult();
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
            result.put("detailDto", detailDto);
        } else {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈商户查询页面〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String merchantInfoQuery(@ModelAttribute("requestDto") DomesticMerchantInfoReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到境内提现账号查询请求,requestDto:{}", requestDto);
        // 获取户头号
        requestDto.setPayerAccount(request.getRemoteUser());
        requestDto.setPlatformCode(CommonConstant.CBP_CODE);
        requestDto.setDataType(DataType.getCodeFromDescription(requestDto.getDataType()));
        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<DomesticMerchantInfoResDto>> apiResDto = domesticMerchantInfoService.merchantInfoQuery(requestDto);
        LOGGER.info("rsf查询结果,apiResDto:{}", apiResDto);
        String list = "[]";
        String success = "false";
        long totalCount = 0l;
        if (apiResDto.isSuccess()) {
            list = JSON.toJSONString(apiResDto.getResult());
            success = "true";
            totalCount = apiResDto.getPage().getCount();
        } else if (WebErrorCode.NO_RESULT.getCode().equals(apiResDto.getResponseCode())) {
            success = "true";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"list\":").append(list).append(",\"success\":").append(success).append(",\"totalCount\":").append(totalCount).append("}");
        return sb.toString();
    }

    /**
     * 功能描述: <br>
     * 〈商户单笔新增修改商户〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "addOrUpdate", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String merchantInfoManage(@ModelAttribute("requestDto") DomesticMerchantInfoReqDto requestDto, HttpServletRequest request) {
        // 获取户头号
        requestDto.setPayerAccount(request.getRemoteUser());

        if (AccountCharacter.COMPANY.getCode().equals(requestDto.getAccountCharacter())) {
            // 住所/营业场所名称及代码
            requestDto.setAreaCode(requestDto.getAreaCode().substring(0, 6));
            // 常驻国家（地区）名称及代码
            requestDto.setCountryCode(requestDto.getCountryCode().substring(0, 3));
            // 经济类型
            requestDto.setAttrCode(requestDto.getAttrCode().substring(0, 3));
            // 所属行业属性
            requestDto.setIndustryCode(requestDto.getIndustryCode().split("-")[0]);
            // 特殊经济区内企业
            requestDto.setTaxFreeCode(requestDto.getTaxFreeCode().split("-")[0]);
        } else {
            // 住所/营业场所名称及代码
            requestDto.setAreaCode(null);
            // 常驻国家（地区）名称及代码
            requestDto.setCountryCode(null);
            // 经济类型
            requestDto.setAttrCode(null);
            // 所属行业属性
            requestDto.setIndustryCode(null);
            // 特殊经济区内企业
            requestDto.setTaxFreeCode(null);
        }

        requestDto.setPlatformCode("cbp");
        LOGGER.info("收到收款方商户新增修改请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);

        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<String> apiResDto = domesticMerchantInfoService.merchantInfoManage(requestDto);
        LOGGER.info("rsf查询结果:{}", apiResDto);
        Map<String, Object> result = new HashMap<String, Object>();
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈批量新增文件校验〉
     *
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileSubmit", produces = "application/json;charset=UTF-8")
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("批量新增境内提现账号上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            res = domesticMerchantInfoService.uploadFile(targetFile, request.getRemoteUser());
        } catch (ExcelForamatException e) {
            LOGGER.error("批量新增境内提现账号出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("批量新增境内提现账号出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("批量新增境内提现账号上传结束");
        response.getWriter().write(JSON.toJSONString(res));
    }

    /**
     *
     * 功能描述: <br>
     * 〈批量上传新增〉
     *
     * @param request
     * @param fileAddress
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "batchAdd", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchAdd(@RequestParam("fileAddress") String fileAddress, HttpServletRequest request) {
        LOGGER.info("批量新增提现账号上传开始:{}", fileAddress);
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取户头号
        String userNo = request.getRemoteUser();
        // 触发跨境
        ApiResDto<String> apiResDto = domesticMerchantInfoService.batchAdd(fileAddress, userNo);
        LOGGER.info("触发提现账号接口批量新增返回:{}", apiResDto.toString());
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
            if(apiResDto.getResponseMsg().contains("已存在")) {
            	result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_EX);
            }
        }
        LOGGER.info("批量新增上传结束");
        return JSON.toJSONString(result);
    }
}
