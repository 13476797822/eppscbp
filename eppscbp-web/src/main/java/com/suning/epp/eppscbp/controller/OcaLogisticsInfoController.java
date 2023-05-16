package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.LogisticsInfoStatus;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.dto.req.LogisticsInfoQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadLogisticsInfoResDto;
import com.suning.epp.eppscbp.dto.res.LogisticsInfoQueryResDto;
import com.suning.epp.eppscbp.service.OcaApplyFileUploadService;
import com.suning.epp.eppscbp.service.OcaLogisticsInfoService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSDownloadParams;

/**
 * 〈国际卡收单_物流信息上传〉<br>
 * 〈功能详细描述〉
 *
 * @author 19043747
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/oca/ocaLogisticsInfoController/")
public class OcaLogisticsInfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OcaLogisticsInfoController.class);
	private static final String INIT_QUERY = "screen/oca/logisticsInfo/ocaLogisticsInfoMain";
	
	//导出
	private static final String LOGISTICS_INFO_EXPORT_URL = "/cbpExport/logisticsInfo.do?";
	
	@Autowired
	private OcaLogisticsInfoService logisticsInfoService;
	
	@Autowired
	private OcaApplyFileUploadService ocaApplyFileUploadService;
	
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
    public ModelAndView init() {
        LOGGER.info("物流信息查询初始化开始");
        ModelAndView mav = createMainView();
        mav.addObject(EPPSCBPConstants.STATUS, LogisticsInfoStatus.values());
        LOGGER.info("物流信息查询初始化结束");
        return mav;
    }
    /**
     * 功能描述: <br>
     * 〈物流信息查询〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String LogisOrderQuery(@ModelAttribute("requestDto") LogisticsInfoQueryReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("收到物流信息查询请求,requestDto:{}", requestDto);
        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
        ApiResDto<List<LogisticsInfoQueryResDto>> responseParam = logisticsInfoService.logisticsInfoQuery(requestDto);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        String list = "[]";
        String success = "false";
        long totalCount = 0l;
        if (responseParam.isSuccess()) {
            list = JSON.toJSONString(responseParam.getResult());
            success = "true";
            totalCount = responseParam.getPage().getCount();
            
        } else if (WebErrorCode.NO_RESULT.getCode().equals(responseParam.getResponseCode())) {
            success = "true";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"list\":").append(list).append(",\"success\":").append(success).append(",\"totalCount\":").append(totalCount).append("}");
        return sb.toString();
    }
    
    /**
     * 物流信息导出
     * @param requestDto
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("export")
    public void export(@ModelAttribute("requestDto") LogisticsInfoQueryReqDto requestDto,  HttpServletRequest request, HttpServletResponse response) throws IOException {
    	LOGGER.info("收到物流信息导出请求,requestDto:{}", requestDto);

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        // 接口入参预处理
        LOGGER.info("开始物流信息导出入参预处理");
        // 商户订单号
        requestDto.setMerchantOrderNo(StringUtil.isEmpty(requestDto.getMerchantOrderNo()) ? null : requestDto.getMerchantOrderNo().trim());
        // 物流状态
        requestDto.setLogisticsStatus(StringUtil.isEmpty(requestDto.getLogisticsStatus()) ? null : requestDto.getLogisticsStatus().trim());
        // 支付时间结束
        requestDto.setPayFromTime(StringUtil.isEmpty(requestDto.getPayFromTime()) ? null : requestDto.getPayFromTime());
        // 支付时间结束
        requestDto.setPayToTime(StringUtil.isEmpty(requestDto.getPayToTime()) ? null : requestDto.getPayToTime());
        // 上传时间开始
        requestDto.setUploadFromTime(StringUtil.isEmpty(requestDto.getUploadFromTime()) ? null : requestDto.getUploadFromTime());
        // 上传结束时间
        requestDto.setUploadToTime(StringUtil.isEmpty(requestDto.getUploadToTime()) ? null : requestDto.getUploadToTime());

        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = ocaUrl + LOGISTICS_INFO_EXPORT_URL + tempUrl;

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
     * 物流文件上传
     */
    @RequestMapping(value = "fileUpload", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String fileUpload(@RequestParam("fileinput") MultipartFile targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("物流文件上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadLogisticsInfoResDto res = new FileUploadLogisticsInfoResDto();
        try {
            res = ocaApplyFileUploadService.uploadFileLogisticsInfo(targetFile, request.getRemoteUser());
        } catch (Exception e) {
            LOGGER.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("物流文件上传结束");
        String success = "false";
        if(res.isSuccess()) {
        	success = "true";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"filePathSuccess\":").append("\"" +res.getFilePathSuccess()+"\"")
        .append(",\"logisticsCount\":").append("\"" +res.getLogisticsCount()+"\"")
        .append(",\"logisticsCountSucess\":").append("\"" +res.getLogisticsCountSucess()+"\"")
        .append(",\"logisticsCountFail\":").append("\"" +res.getLogisticsCountFail()+"\"")
        .append(",\"filePathFail\":").append("\"" +res.getFilePathFail()+"\"")
        .append(",\"message\":").append("\"" +res.getMessage()+"\"")
        .append(",\"success\":").append(success).append("}");
        return sb.toString();
    }
    
    /**
     * 功能描述: <br>
     * 〈物流信息提交〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "fileSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fileSubmit(@RequestParam("fileshow") String fileshow, HttpServletRequest request) {
    	LOGGER.info("物流信息提交开始:{}", fileshow);
         // 获取户头号
         String payerAccount = request.getRemoteUser();
         // 触发跨境
         ApiResDto<String> apiResDto = logisticsInfoService.logisticsInfoSubmit(fileshow, payerAccount);
         LOGGER.info("触发跨境返回:{}", apiResDto.toString());
         String success = "false";
         if (apiResDto.isSuccess()) {
        	 success = "true";
         } 
         StringBuilder sb = new StringBuilder();
         sb.append("{").append("\"responseMsg\":").append("\"" +apiResDto.getResponseMsg()+"\"").append(",\"success\":").append(success).append("}");
         LOGGER.info("物流信息上传结束。{}", sb.toString() );
         return sb.toString();
    }
    
    /**
     * 功能描述: <br>
     * 〈物流信息-错误的excel下载〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "downLoadErrorFile", produces = "text/html;charset=UTF-8")
    public void downLoadErrorFile(@ModelAttribute("fileAddress") String fileAddress, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        try {
            LOGGER.info("下载错误的物流信息文件开始。fileAddress:{}", fileAddress);
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileAddress);
            OSSDownloadParams params = new OSSDownloadParams(OSSBucket.UNDERLINE_FILE_BUCKET, fileAddress);
            in = OSSClientUtil.getInputStream(params);
            
            if (in == null) {
                LOGGER.error("文件不存在。文件名称:{}", fileAddress);
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
            LOGGER.info("下载结束,文件名:{}", fileAddress);
        } catch (Exception e) {
            LOGGER.error("下载文件出错,错误异常:{}", ExceptionUtils.getStackTrace(e));
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
