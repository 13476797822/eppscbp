/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: MerchantInfoController.java
 * Author:   17061545
 * Date:     2018年3月20日 下午2:13:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
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

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.MerchantAccountCharacterEnum;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.MerchantInfoQueryDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CtFullAmtDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.MerchantInfoQueryResDto;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.FileConverterUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈商户信息管理〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@SessionAttributes({ "merchantInfoList", "merchantInfoListRMB" })
@RequestMapping("/merchantHandle/")
public class MerchantInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantInfoController.class);

    private static final String INIT_QUERY = "screen/merchantInfo/merchantInfoSearch";

    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    private final String MERCHANT_EXPORT_URL = "/cbpExport/merchantInfo.do?";

    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.CUR_TYPE, CurType.getAllDescription());
        mvn.addObject(EPPSCBPConstants.CUR_TYPE_ADD, CurType.getAddMerchantDescription());
        mvn.addObject(EPPSCBPConstants.BIZ_TYPE, ChargeBizType.getAllDescription());
        mvn.addObject(EPPSCBPConstants.BIZ_TYPE_ADD, ChargeBizType.getAddMerchantDescription());
        mvn.addObject(EPPSCBPConstants.MERCHANT_IN_OUT, MerchantAccountCharacterEnum.getAllDescription());
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
    public ModelAndView init(ModelMap modelMap, HttpServletRequest request) {
        LOGGER.info("收款方商户查询初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("收款方商户查询初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈商户查询页面〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("query")
    public ModelAndView merchantInfoQuery(@ModelAttribute("requestDto") MerchantInfoQueryDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到收款方商户查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        // 临时存储,界面币种条件,业务类型条件
        String curParam = requestDto.getCurrency();
        requestDto.setCurrency(StringUtil.isEmpty(CurType.getCodeFromDescription(requestDto.getCurrency())) ? null : CurType.getCodeFromDescription(requestDto.getCurrency()));
        String bizParam = requestDto.getBizType();
        requestDto.setBizType(StringUtil.isEmpty(ChargeBizType.getCodeFromDescription(requestDto.getBizType())) ? null : ChargeBizType.getCodeFromDescription(requestDto.getBizType()));
        // 获取户头号
        String userNo = request.getRemoteUser();
        if (StringUtil.isNotNull(userNo)) {
            requestDto.setPayerAccount(userNo);
        }
        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<MerchantInfoQueryResDto>> apiResDto = merchantInfoService.merchantInfoQuery(requestDto);
        LOGGER.info("rsf查询结果", apiResDto.toString());

        // 查询条件转义,页面展示需要
        requestDto.setCurrency(curParam);
        requestDto.setBizType(bizParam);
        if (apiResDto.isSuccess()) {
            mav.addObject(EPPSCBPConstants.RESULT_LIST, apiResDto.getResult());
            LOGGER.info("获取到收款方商户查询结果,resultList:{}", apiResDto.getResult().toString());
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
     * 〈收款方商户数据导出〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void merchantInfoExport(@ModelAttribute("requestDto") MerchantInfoQueryDto requestDto, HttpServletResponse response, HttpServletRequest request) throws IOException {
        LOGGER.info("收到收款方商户查询请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);
        // 获取户头号
        String userNo = request.getRemoteUser();
        if (StringUtil.isNotNull(userNo)) {
            requestDto.setPayerAccount(userNo);
        }
        // 币种名称转代码
        requestDto.setCurrency(StringUtil.isEmpty(CurType.getCodeFromDescription(requestDto.getCurrency())) ? null : CurType.getCodeFromDescription(requestDto.getCurrency()));
        // 收款方商户代码
        requestDto.setPayeeMerchantCode(StringUtil.isEmpty(requestDto.getPayeeMerchantCode()) ? null : requestDto.getPayeeMerchantCode().trim());
        // 收款方商户名称
        requestDto.setPayeeMerchantName(StringUtil.isEmpty(requestDto.getPayeeMerchantName()) ? null : requestDto.getPayeeMerchantName().trim());
        // 收款方银行账号
        requestDto.setPayeeAccount(StringUtil.isEmpty(requestDto.getPayeeAccount()) ? null : requestDto.getPayeeAccount().trim());
        // 业务类型
        requestDto.setBizType(StringUtil.isEmpty(ChargeBizType.getCodeFromDescription(requestDto.getBizType())) ? null : ChargeBizType.getCodeFromDescription(requestDto.getBizType()));

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
     * 功能描述: <br>
     * 〈批量新增商户文件模板〉
     *
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("批量新增商户文件下载开始");
        OutputStream outputStream = null;
        try {

            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + EPPSCBPConstants.BATCH_MERCHANT);
            response.setHeader("cache-control", "public");
            response.setCharacterEncoding("GBK");
            response.setContentType("application/vnd.ms-excel;charset=GBK");
            outputStream = response.getOutputStream();
            ServletContext context = request.getSession().getServletContext();
            String fullPath = context.getRealPath(EPPSCBPConstants.TEMPLATE_PATH + EPPSCBPConstants.BATCH_MERCHANT);
            File file = new File(fullPath);
            // 下载乱码问题
            byte[] bytes = FileConverterUtil.toByteExcel(file);
            outputStream.write(bytes);

        } catch (Exception e) {
            LOGGER.error("批量新增商户文件模板失败,原因是：{}", e);

        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error("批量新增商户文件模板,原因是：{}", e);

                }
            }
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 〈批量新增商户〉
     *
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "batchAdd", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchAdd(@RequestParam("fileAddress") String fileAddress, HttpServletRequest request, SessionStatus sessionStatus) {
        // 删除缓存数据
        sessionStatus.setComplete();
        LOGGER.info("批量新增商户上传开始{}", fileAddress);
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取户头号
        String userNo = request.getRemoteUser();
        // 触发跨境
        ApiResDto<String> apiResDto = merchantInfoService.batchAdd(fileAddress, userNo);
        LOGGER.info("触发跨境返回:{}", apiResDto.toString());
        if (apiResDto.isSuccess()) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else {
            LOGGER.info("返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("批量新增商户上传结束");
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈批量新增商户文件校验〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileSubmit", produces = "application/json;charset=UTF-8")
    public void fileSubmit(@RequestParam("file") MultipartFile targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("批量新增商户上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            res = merchantInfoService.uploadFile(targetFile, request.getRemoteUser());
        } catch (ExcelForamatException e) {
            LOGGER.error("批量新增商户出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.FORMAT_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("批量新增商户出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("申请明细文件上传结束");
        response.getWriter().write(JSON.toJSONString(res));
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
    public String merchantInfoManage(HttpServletRequest request, SessionStatus sessionStatus) {
        // 删除缓存数据
        sessionStatus.setComplete();
        Map<String, Object> result = new HashMap<String, Object>();
        MerchantInfoQueryDto requestDto = new MerchantInfoQueryDto();
        requestDto.setPayeeMerchantName(StringUtil.trim(request.getParameter("payeeMerchantName")));
        requestDto.setCurrency(StringUtil.trim(request.getParameter("currency")));
        requestDto.setPayeeAccount(StringUtil.trim(request.getParameter("payeeAccount")));
        requestDto.setPayeeName(StringUtil.trim(request.getParameter("payeeName")));
        requestDto.setPayeeBank(StringUtil.trim(request.getParameter("payeeBank")));
        requestDto.setPayeeBankAdd(StringUtil.trim(request.getParameter("payeeBankAdd")));
        requestDto.setPayeeCountry(StringUtil.trim(request.getParameter("payeeCountry").substring(0, 3)));
        requestDto.setPayeeBankSwiftCode(StringUtil.trim(request.getParameter("payeeBankSwiftCode")));
        requestDto.setPayeeAddress(StringUtil.trim(request.getParameter("payeeAddress")));
        requestDto.setBizType(StringUtil.trim(request.getParameter("bizType")));
        String operationType = request.getParameter("operationType");
        requestDto.setAccountCharacter(MerchantAccountCharacterEnum.getCodeFromDescription(StringUtil.trim(request.getParameter("accountCharacter"))));
        requestDto.setMobilePhone(StringUtil.trim(request.getParameter("mobilePhone")));
        requestDto.setPayeeMerchantName(StringUtil.trim(request.getParameter("payeeMerchantName")));
        requestDto.setOperationType(StringUtil.trim(operationType));
        requestDto.setPlatformCode("cbp");
        // 修改需要收款方商户号
        if ("U".equals(operationType)) {
            requestDto.setPayeeMerchantCode(request.getParameter("payeeMerchantCode"));
        }
        requestDto.setCurrency(StringUtil.isEmpty(CurType.getCodeFromDescription(requestDto.getCurrency())) ? null : CurType.getCodeFromDescription(requestDto.getCurrency()));
        // 账户性质为境外付款方账号时业务类型cbs后台赋值
        if (MerchantAccountCharacterEnum.PAYER_MERCHANT_CODE.getCode().equals(requestDto.getAccountCharacter())) {
            requestDto.setBizType(null);
        } else {
            requestDto.setBizType(StringUtil.isEmpty(ChargeBizType.getCodeFromDescription(requestDto.getBizType())) ? null : ChargeBizType.getCodeFromDescription(requestDto.getBizType()));
        }
        // 获取户头号
        String userNo = request.getRemoteUser();
        if (StringUtil.isNotNull(userNo)) {
            requestDto.setPayerAccount(userNo);
        }
        LOGGER.info("收到收款方商户新增修改请求,requestDto:{}", requestDto);

        ModelAndView mav = createMainView();
        mav.addObject("requestDto", requestDto);

        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<String> apiResDto = merchantInfoService.merchantInfoManage(requestDto);
        LOGGER.info("rsf查询结果");
        if (apiResDto.isSuccess() && "".equals(apiResDto.getResponseMsg())) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
        } else if (apiResDto.isSuccess() && apiResDto.getResponseMsg().equals(CommonConstant.MODIFY_SUCCESS)) {
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS_QT);
        } else {
            LOGGER.info("返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        return JSON.toJSONString(result);
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
        Map<String, Object> result = new HashMap<String, Object>();
        String userNo = request.getRemoteUser();
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("payeeMerchantCode", payeeMerchantCode);
        requestMap.put("payerAccount", userNo);
        LOGGER.info("指定商户详情请求:{}", requestMap);
        ApiResDto<MerchantInfoQueryResDto> apiResDto = merchantInfoService.detailsMerchantInfo(requestMap);
        LOGGER.info("指定商户查询结果:{}", apiResDto.toString());
        if (apiResDto.isSuccess()) {
            MerchantInfoQueryResDto detailDto = apiResDto.getResult();
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
            detailDto.setCurrency(CurType.getDescriptionFromCode(detailDto.getCurrency()));
            detailDto.setBizType(ChargeBizType.getDescriptionFromCode(detailDto.getBizType()));
            detailDto.setAccountCharacter(MerchantAccountCharacterEnum.getDescriptionFromCode(detailDto.getAccountCharacter()));
            if (StringUtil.isEmpty(detailDto.getMobilePhone())) {
                detailDto.setMobilePhone("");
            }
            result.put("detailDto", detailDto);
        } else {
            LOGGER.info("返回页面构建值:{}", apiResDto.getResponseMsg());
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
            result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
        }
        LOGGER.info("指定商户详情:{}", result);
        return JSON.toJSONString(result);
    }

    /**
     * 功能描述: <br>
     * 〈查询收款人信息〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "queryMerchantMeg", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMerchantMeg(String message, String productType, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            LOGGER.info("校验收款方商户信息开始:{}", request.getRemoteUser());
            String userNo = request.getRemoteUser();
            ApiResDto<CtFullAmtDto> apiResDto = merchantInfoService.queryMerchantMeg(userNo, message, productType);
            if (apiResDto.isSuccess()) {
                CtFullAmtDto ctfullamt = apiResDto.getResult();
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_SUCESS);
                result.put("payeeMerchantCode", ctfullamt.getPayeeMerchantCode());
                result.put("amt", ctfullamt.getAmt());
                result.put("curName", ctfullamt.getCurName());
                result.put("cur", ctfullamt.getCur());
            } else {
                LOGGER.info("返回页面构建值:{}", apiResDto.getResponseMsg());
                result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
                result.put(CommonConstant.RESPONSE_MESSAGE, apiResDto.getResponseMsg());
            }
        } catch (Exception ex) {
            LOGGER.error("校验收款方商户信息异常:{}", ExceptionUtils.getStackTrace(ex));
            result.put(EPPSCBPConstants.REQ_FLG, EPPSCBPConstants.REQ_FLG_FAIL);
        }
        LOGGER.info("返回页面构建值:{}", result);
        return JSON.toJSONString(result);
    }

}
