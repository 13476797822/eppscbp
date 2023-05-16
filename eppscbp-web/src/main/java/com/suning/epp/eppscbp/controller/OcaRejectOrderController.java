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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.RejectOrderStatus;
import com.suning.epp.eppscbp.dto.req.RejectOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.RejectDetailResDto;
import com.suning.epp.eppscbp.dto.res.RejectOrderQueryRespDto;
import com.suning.epp.eppscbp.service.RejectOrderService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSDownloadParams;

/**
 * 〈国际卡收单_拒付订单〉<br>
 * 〈功能详细描述〉
 *
 * @author 19043747
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/oca/ocaRejectOrderController/")
public class OcaRejectOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OcaRejectOrderController.class);
	private static final String INIT_QUERY = "screen/oca/rejectOrder/ocaRejectOrderMain";
	private static final String DETAIL_INFO = "screen/oca/rejectOrder/ocaRejectOrderDetail";
	
	//导出
	private static final String REJECT_ORDER_EXPORT_URL = "/cbpExport/rejectOrder.do?";
	
	@Autowired
	private RejectOrderService rejectOrderService;
	
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
        LOGGER.info("拒付订单查询初始化开始");
        ModelAndView mav = createMainView();
        mav.addObject(EPPSCBPConstants.STATUS, RejectOrderStatus.values());
        LOGGER.info("拒付订单查询初始化结束");
        return mav;
    }
    
    
    /**
     * 功能描述: <br>
     * 〈拒付订单查询〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String rejectOrderQuery(@ModelAttribute("requestDto") RejectOrderQueryReqDto requestDto, HttpServletRequest request) {
	    	LOGGER.info("收到拒付订单查询请求,requestDto:{}", requestDto);
	
	        ModelAndView mav = createMainView();
	        mav.addObject("requestDto", requestDto);
	        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
	        requestDto.setPayerAccount(request.getRemoteUser());
	
	        LOGGER.info("触发rsf调用进行查询,requestDto:{}", requestDto);
	        ApiResDto<List<RejectOrderQueryRespDto>> responseParam = rejectOrderService.rejectOrderQuery(requestDto);
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
     * 〈拒收订单详情〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("detailInfo")
    public ModelAndView detailInfoQuery(@ModelAttribute("rejectOrderNo") String rejectOrderNo, HttpServletRequest request) {
        String payerAccount = request.getRemoteUser();
        LOGGER.info("OCA拒收订单详情查询,rejectOrderNo:{},payerAccount:{}", rejectOrderNo, payerAccount);

        ModelAndView mav = new ModelAndView(DETAIL_INFO);
        LOGGER.info("触发rsf调用进行查询,rejectOrderNo:{},payerAccount:{}", rejectOrderNo, payerAccount);
        ApiResDto<RejectDetailResDto> responseParam = rejectOrderService.rejectDetailInfoQuery(payerAccount, rejectOrderNo);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        if (responseParam.isSuccess()) {
            mav.addObject(EPPSCBPConstants.RESULT, responseParam.getResult().getRejectOrderQueryRespDto());
            mav.addObject(EPPSCBPConstants.RESULT_LIST, responseParam.getResult().getAcRejectOrderHisList());
            LOGGER.info("获取到查询结果,result:{}", responseParam.getResult());
        }
        return mav;
    }
    
    
    
    /**
     * 功能描述: <br>
     * 〈拒付单数据导出〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("export")
    public void export(@ModelAttribute("requestDto") RejectOrderQueryReqDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {

        LOGGER.info("获取付款方户头号:{}", request.getRemoteUser());
        requestDto.setPayerAccount(request.getRemoteUser());

        // 接口入参预处理
        LOGGER.info("开始拒付单导出入参预处理");
        requestDto.setRejectOrderNo(StringUtil.isEmpty(requestDto.getRejectOrderNo()) ? null : requestDto.getRejectOrderNo().trim());
        requestDto.setMerchantOrderNo(StringUtil.isEmpty(requestDto.getMerchantOrderNo()) ? null : requestDto.getMerchantOrderNo());
        //拒付状态
        requestDto.setStatus(requestDto.getStatus());
        requestDto.setCreateTimeStart(StringUtil.isEmpty(requestDto.getCreateTimeStart()) ? "" : requestDto.getCreateTimeStart());
        requestDto.setCreateTimeEnd(StringUtil.isEmpty(requestDto.getCreateTimeEnd()) ? "" : requestDto.getCreateTimeEnd());
        requestDto.setUpdateTimeStart(StringUtil.isEmpty(requestDto.getUpdateTimeStart()) ? "" : requestDto.getUpdateTimeStart());
        requestDto.setUpdateTimeEnd(StringUtil.isEmpty(requestDto.getUpdateTimeEnd()) ? "" : requestDto.getUpdateTimeEnd());

        // 获取导出URL
        String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
        String url = ocaUrl + REJECT_ORDER_EXPORT_URL + tempUrl;

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
     * 〈拒付单-下载〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "downLoadFile", produces = "text/html;charset=UTF-8")
    public void downLoadFile(@ModelAttribute("fileAddress") String fileAddress, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        try {
            LOGGER.info("下载文件开始。fileAddress:{}", fileAddress);
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileAddress, "UTF-8"));
            //response.setHeader("Content-Disposition", "attachment; filename=\"" + fileAddress + "\"; filename*=utf-8''" + fileAddress);
            OSSDownloadParams params = new OSSDownloadParams(OSSBucket.OCA, fileAddress);
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
