package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.RejectOrderStatus;
import com.suning.epp.eppscbp.common.errorcode.WebErrorCode;
import com.suning.epp.eppscbp.dto.req.RejectOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.RejectOrderQueryRespDto;
import com.suning.epp.eppscbp.service.OcaApplyFileUploadService;
import com.suning.epp.eppscbp.service.RejectOrderService;
import com.suning.epp.eppscbp.util.StringUtil;

@Controller()
@RequestMapping("/oca/ocaRejectHandingOrderController/")
public class OcaRejectHandingOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OcaRejectOrderController.class);
	private static final String HANDING_INFO = "screen/oca/rejectOrder/ocaRejectHandingOrderMain";

	@Autowired
	private RejectOrderService rejectOrderService;
	
	@Autowired
	private OcaApplyFileUploadService ocaApplyFileUploadService;
	
	/**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(HANDING_INFO);
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
        LOGGER.info("拒付订单查询初始化结束");
        return mav;
    }
    
    /**
     * 功能描述: <br>
     * 〈拒付待办查询〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "queryRejectOrderHanding", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryRejectOrderHanding(@ModelAttribute("pageNo") String pageNo, @ModelAttribute("pageSize") String pageSize, HttpServletRequest request) {
    	RejectOrderQueryReqDto requestDto = new  RejectOrderQueryReqDto();
    	requestDto.setPayerAccount(request.getRemoteUser());
    	requestDto.setPageNo(pageNo);
    	requestDto.setPageSize(pageSize);
    	requestDto.setStatus(RejectOrderStatus.REJECT_ORDER_HANDLE.getCode().toString()+"-"+RejectOrderStatus.REJECT_ORDER_APPEAL_FAIL.getCode().toString());
    	LOGGER.info("收到拒付订单查询请求,requestDto:{}", requestDto);

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
     * 〈拒付待办-接受〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "accept", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String accept(@ModelAttribute("merchantOrderNo") String merchantOrderNo, @ModelAttribute("rejectOrderNo") String rejectOrderNo, HttpServletRequest request) {
        String payerAccount = request.getRemoteUser();

        LOGGER.info("OCA拒收待办-接受，触发rsf调用进行查询,orderNo:{},rejectOrderNo:{},payerAccount:{}", merchantOrderNo, rejectOrderNo, payerAccount);
        ApiResDto<Boolean> responseParam = rejectOrderService.accept(payerAccount, merchantOrderNo, rejectOrderNo);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        StringBuilder sb = new StringBuilder();
        String success = "false";
        if(responseParam.isSuccess()) {
        	success = "true";
        }
        sb.append("{").append("\"success\":").append(success).append("}");
        return sb.toString();
    }
    
    /**
     * 功能描述: <br>
     * 〈拒付待办-申诉材料上传〉
     * 
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "appealFileSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appealFileSubmit(@RequestParam("file") MultipartFile targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("申诉材料上传开始");
        response.setContentType("text/html;charset=utf-8");
        FileUploadResDto res = new FileUploadResDto();
        try {
            LOGGER.info("文件类型:{}",targetFile.getContentType());
            res = ocaApplyFileUploadService.uploadFile(targetFile, request.getRemoteUser());
        } catch (Exception e) {
            LOGGER.error("文件上传出错,错误:{}", ExceptionUtils.getStackTrace(e));
            res.fail(WebErrorCode.SYSTEM_ERROR.getCode(), WebErrorCode.SYSTEM_ERROR.getDescription());
        }
        LOGGER.info("申诉材料上传结束");
        String success = "false";
        if(!StringUtil.isEmpty(res.getFileAddress())) {
        	success = "true";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"fileAddress\":").append("\"" +res.getFileAddress()+"\"").append(",\"success\":").append(success).append("}");
        return sb.toString();
    }
    
    /**
     * 功能描述: <br>
     * 〈拒付待办-申诉提交〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "appealSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appealSubmit(@ModelAttribute("rejectOrderNo") String rejectOrderNo, @ModelAttribute("reason") String reason, @ModelAttribute("showfile") String showfile, HttpServletRequest request) {
        String payerAccount = request.getRemoteUser();

        LOGGER.info("OCA拒收待办-申诉，触发rsf调用进行查询,orderNo:{},showfile:{},payerAccount:{}", showfile, rejectOrderNo, payerAccount);
        ApiResDto<Boolean> responseParam = rejectOrderService.appeal(payerAccount, rejectOrderNo, reason, showfile);
        LOGGER.info("rsf查询结果,responseParam:{}", responseParam);
        String success = "false";
        if (responseParam.isSuccess()) {
        	success = "true";
            LOGGER.info("获取到查询结果,result:{}", responseParam.getResult());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"message\":").append("\"" +responseParam.getResponseMsg()+"\"").append(",\"success\":").append(success).append("}");
        return sb.toString();
    }


    /**
     * 功能描述: <br>
     * 〈拒付待办-接受〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "messageBox", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String messageBox(@ModelAttribute("merchantOrderNo") String merchantOrderNo, HttpServletRequest request) {
        String payerAccount = request.getRemoteUser();

        LOGGER.info("oca接收拒付弹窗提示存在拒付订单和退款订单,商户订单号merchantOrderNo:{},payerAccount:{}", merchantOrderNo, payerAccount);
        ApiResDto<JSONArray> objectApiResDto = rejectOrderService.rejectMessageBoxQuery(merchantOrderNo,payerAccount);
        LOGGER.info("objectApiResDto:{}",objectApiResDto.getResult());
        JSONArray objects = objectApiResDto.getResult();
        int size = objects.size();
        boolean success = true;
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"messagelist\":").append( objects).append(",\"success\":").append(success).append(",\"totalCount\":").append(size).append("}");
        LOGGER.info("sb:{}",sb);
        return sb.toString();
    }
}
