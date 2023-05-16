package com.suning.epp.eppscbp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
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
import com.suning.edm.api.dto.FileData;
import com.suning.edm.api.dto.Result;
import com.suning.edm.api.util.MailUtils;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.ScmOnlineConfig;
import com.suning.epp.eppscbp.common.enums.DataType;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.TradeInfoReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CollSettTradeInfoResDto;
import com.suning.epp.eppscbp.dto.res.PurchasePaymentTradeInfoResDto;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.service.TradeInfoAggregationService;
import com.suning.epp.eppscbp.service.impl.ScmInitStartListener;
import com.suning.epp.eppscbp.util.BeanConverterUtil;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.HttpUtil;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSDownloadParams;

/**
 * 〈交易查询 包含购付汇、收结汇〉<br>
 * 〈功能详细描述〉
 *
 * @author 19043747
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/tradeQueryAuth/tradeInfo/")
public class TradeInfoAggregationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeInfoAggregationController.class);

	// 列表
    private static final String INIT_QUERY = "screen/tradeInfoAggregation/tradeInfo";
    // 电子回单预览
    private static final String TRADE_INFO_PREVIEW = "screen/tradeInfoAggregation/tradeInfoPreview";

    // 汇总电子回单结果页
    private static final String TRADE_INFO_ELE_RECEIPT_RESULT = "screen/tradeInfoAggregation/tradeInfoBatcheleReceipt";
    
    @Value("${eppcbs.url}")
    private String eppcbsUrl;

    private static final String TRADE_INFO_EXPORT_URL = "/cbpExport/tradeInfo.do?";
    public static final String FILE_SEPARATOR = "/";
    // 临时目录
    public static final String TEMP_FILE_PATH = "/opt/logs/epp/log/eppscbp/pdf"; 
    public static final String ONLINE = "on"; 
    
    
    @Autowired
    private TradeInfoAggregationService tradeInfoAggregationService;
    
    @Autowired
	private ScmInitStartListener scmInitStartListener;
    
    @Autowired
    private MerchantInfoService  merchantInfoService;
    
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
     * 〈页面初始化〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init(HttpServletRequest request) {
        LOGGER.info("交易查询初始化-收结汇交易查询开始");
        ModelAndView mav = createMainView();
        LOGGER.info("交易查询初始化-收结汇交易查询结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈查询按钮〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("queryTradeInfo")
    public ModelAndView queryTradeInfo(@ModelAttribute("requestDto") TradeInfoReqDto requestDto, HttpServletRequest request) {
        LOGGER.info("查询列表开始。requestDto：{}", requestDto);
        ModelAndView mav = createMainView();
        
        // 标识购付汇01还是收结汇02
        String flag = requestDto.getFlag();
        requestDto.setMerchantAccount(request.getRemoteUser());
        mav.addObject("requestDto", requestDto);
        
        // 时间格式化
        String orderCreateTimeStart = requestDto.getOrderCreateTimeStart();
        String orderCreateTimeEnd = requestDto.getOrderCreateTimeEnd();
        Date orderCreateTimeStartDate = DateUtil.getDateOfTimeStr(orderCreateTimeStart, DateConstant.DATE_FORMAT);
        Date orderCreateTimeEndDate = DateUtil.getDateOfTimeStr(orderCreateTimeEnd, DateConstant.DATE_FORMAT);
        try {
        	
        	// 判断订单结束时间是否为2021-03-19之前的日期，如果是，则提示 “请回原交易查询页面，进行查询”
            String online = scmInitStartListener.getValue(ScmOnlineConfig.ONLINE_20210319);
            if(orderCreateTimeEnd.compareTo(EPPSCBPConstants.QUERY_CONDITION) <= 0 && online.equals(ONLINE)) {
            	LOGGER.info("订单结束时间：{}", orderCreateTimeEnd);
           	 	mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.QUERY_ERROR_MSG_1);
           	 	return mav;
            }
            
            // 批付姓名、账号、金额只支持一个月（30天）内时间查询
            if(!StringUtil.isEmpty(requestDto.getAmtStart(), requestDto.getAmtEnd(),requestDto.getReceiverCardNo(),requestDto.getReceiverName()) && DateUtil.getIntervalDays(orderCreateTimeStartDate, orderCreateTimeEndDate) > 30) {
            	 LOGGER.info("批付姓名、账号、金额只支持一个月内时间查询，orderCreateTimeStart：{}， orderCreateTimeEnd：{}", orderCreateTimeStart, orderCreateTimeEnd);
            	 mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.QUERY_ERROR_MSG_2);
            	 return mav;
            }
        	
            //  根据条件查询
        	if(DataType.EXCHANGE_PAYMENT.getCode().equals(flag)) {
        		// 查询购付汇交易
        		this.queryPurchasePayementTradeInfo(requestDto, mav);
        		
        	}else if(DataType.COLLECTION_SETTLE.getCode().equals(flag)) {
        		// 查询收结汇交易
        		this.queryCollSettTradeInfo(requestDto, mav);
        	}else {
        		LOGGER.info("非购付汇或者收结汇，不处理.flag:{}", flag);
        	}
        } catch (Exception ex) {
            LOGGER.error("查询列表异常:{}", ExceptionUtils.getStackTrace(ex));
            mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.SYSTEM_ERROR);
        }
        
        LOGGER.info("查询列表结束");
        return mav;
    }

    /**
     *  查询购付汇聚合交易数据
     * @param requestDto
     * @param mav
     */
    private void queryPurchasePayementTradeInfo(TradeInfoReqDto requestDto, ModelAndView mav) {
    	ApiResDto<PurchasePaymentTradeInfoResDto> apiResDto = tradeInfoAggregationService.purchasePaymentTradeInfo(requestDto);
		PurchasePaymentTradeInfoResDto dto = apiResDto.getResult();
		if (apiResDto.isSuccess()) {
			mav.addObject(EPPSCBPConstants.AMT_RESULT_LIST, dto.getAmtSumDtoList());
		    mav.addObject(EPPSCBPConstants.RESULT_LIST, dto.getPurchasePaymentAggregationList());
		    mav.addObject(EPPSCBPConstants.PAGE, apiResDto.getPage());
		} else {
		    // 未查询到数据或查询出错
		    mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);
		}
		LOGGER.info("查询购付汇聚合交易数据结束");
	}

	/**
     *   查询收结汇聚合交易数据
     * @param requestDto
     * @param mav
     */
	private void queryCollSettTradeInfo(TradeInfoReqDto requestDto, ModelAndView mav) {
		ApiResDto<CollSettTradeInfoResDto> apiResDto = tradeInfoAggregationService.queryCollSettTradeInfo(requestDto);
		CollSettTradeInfoResDto dto = apiResDto.getResult();
		LOGGER.debug("rsf查询结果,dto:{}", apiResDto.getResult());
		
		if (apiResDto.isSuccess()) {
			mav.addObject(EPPSCBPConstants.AMT_RESULT_LIST, dto.getAmtSumDtoList());
		    mav.addObject(EPPSCBPConstants.RESULT_LIST, dto.getCollectionSettlementAggregationList());
		    mav.addObject(EPPSCBPConstants.PAGE, apiResDto.getPage());
		} else {
		    // 未查询到数据或查询出错
		    mav.addObject(EPPSCBPConstants.ERROR_MSG_CODE, EPPSCBPConstants.NO_RESULT_MSG);
		}
	}
    
    /**
     * 功能描述: <br>
     * 〈导出按钮〉
     * 1.如果有勾选，则导出勾选的
     * 2.如果没有勾选，则导出条件下的
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("exportTradeInfo")
    public void exportTradeInfo(@ModelAttribute("requestDto") TradeInfoReqDto requestDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("导出开始。requestDto：{}", requestDto);
        try {
				if (StringUtil.isNotNull(request.getRemoteUser())) {
					requestDto.setMerchantAccount(request.getRemoteUser());
				}
				requestDto.setBizType(StringUtil.isEmpty(requestDto.getBizType())? null : requestDto.getBizType().trim());
				requestDto.setAmtEnd(StringUtil.isEmpty(requestDto.getAmtEnd())? null : requestDto.getAmtEnd().trim());
				requestDto.setAmtStart(StringUtil.isEmpty(requestDto.getAmtStart())? null : requestDto.getAmtStart().trim());
				requestDto.setId(StringUtil.isEmpty(requestDto.getId())? null : requestDto.getId().trim());
				requestDto.setFlag(StringUtil.isEmpty(requestDto.getFlag())? null : requestDto.getFlag().trim());
				requestDto.setMerchantAccount(StringUtil.isEmpty(requestDto.getMerchantAccount())? null : requestDto.getMerchantAccount().trim());
				requestDto.setOrderCreateTimeEnd(StringUtil.isEmpty(requestDto.getOrderCreateTimeEnd())? null : requestDto.getOrderCreateTimeEnd().trim());
				requestDto.setOrderCreateTimeStart(StringUtil.isEmpty(requestDto.getOrderCreateTimeStart())? null : requestDto.getOrderCreateTimeStart().trim());
				requestDto.setOrderNo(StringUtil.isEmpty(requestDto.getOrderNo())? null : requestDto.getOrderNo().trim());
				requestDto.setReceiverCardNo(StringUtil.isEmpty(requestDto.getReceiverCardNo())? null : requestDto.getReceiverCardNo().trim());
				requestDto.setReceiverName(StringUtil.isEmpty(requestDto.getReceiverName())? null : requestDto.getReceiverName().trim());
				requestDto.setStatus(StringUtil.isEmpty(requestDto.getStatus())? null : requestDto.getStatus().trim());
				requestDto.setTransType(StringUtil.isEmpty(requestDto.getTransType())? null : requestDto.getTransType().trim());
				LOGGER.info("导出开始。requestDto：{}", requestDto);
	        	String tempUrl = HttpUtil.transferToUrlFromBean(BeanConverterUtil.beanToMap(requestDto));
	            String url = eppcbsUrl + TRADE_INFO_EXPORT_URL + tempUrl;
	            
	            InputStream input = HttpUtil.httpGetRequestIO(url);
	            String fileName = DateUtil.formatDate(new Date(), DateConstant.DATEFORMATE_YYYYMMDDHHMMSSSSS);

	            OutputStream outputStream = null;
	            response.reset();
	            response.setContentType("application/ms-excel;charset=UTF-8");
	            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
	            outputStream = response.getOutputStream();
				LOGGER.info("查询完毕,开始导出");

	            outputStream.write(HttpUtil.readInputStream(input));
        	
        } catch (Exception ex) {
            LOGGER.error("导出异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("导出结束");
    }

    /**
     * 功能描述: <br>
     * 〈汇总电子回单-点击，获取电子回单地址〉
     * 1. 勾选直接获取电子回单地址
     * 2. 根据查询条件字段获取电子回单地址
     * @return
     * @throws  
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "queryBatchEleReceiptPDFAddress", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryBatchEleReceiptPDFAddress(@ModelAttribute("requestDto") TradeInfoReqDto requestDto, HttpServletRequest request) {
    	LOGGER.info("汇总电子回单, 获取电子回单地址。requestDto：{}", requestDto);
    	String pdfAddress = "";
		String msg = "";
        boolean success = false;
        String bucketname = OSSBucket.BATCH_PAYMENT_FILE;
        StringBuilder sbStr = new StringBuilder();
    	
    	// 时间格式化
        String orderCreateTimeStart = requestDto.getOrderCreateTimeStart();
        String orderCreateTimeEnd = requestDto.getOrderCreateTimeEnd();
        Date orderCreateTimeStartDate = DateUtil.getDateOfTimeStr(orderCreateTimeStart, DateConstant.DATE_FORMAT);
        Date orderCreateTimeEndDate = DateUtil.getDateOfTimeStr(orderCreateTimeEnd, DateConstant.DATE_FORMAT);
        // 判断查询日期是否超过30天
        if(DateUtil.getIntervalDays(orderCreateTimeStartDate, orderCreateTimeEndDate) > 30) {
    		LOGGER.info("订单时间超出30天：{}，{}", orderCreateTimeStart, orderCreateTimeEnd);
    		msg = "电子回单开具失败，所选时间段超过30天";
    		sbStr.append("{").append("\"success\":").append(success).append(",\"pdfAddress\":").append("\""+pdfAddress+"\"").append(",\"msg\":").append("\""+msg+"\"").append("}");
    	    LOGGER.error("订单时间超出30天:{}", sbStr.toString());
    	    return sbStr.toString();
    	}
        
        // 业务处理
        String fileAddress = requestDto.getFileAddress();
        if(!StringUtil.isEmpty(fileAddress)) {
        	// 如果勾选, 则地址拼接，返回成功
        	String[] fileAddressArr = fileAddress.split(",");
        	// 判断是否勾选超过1000个
        	if(fileAddressArr.length > 1000) {
        		msg = "电子回单开具失败，汇总电子回单个数超过1000个";
        		sbStr.append("{").append("\"success\":").append(success).append(",\"pdfAddress\":").append("\""+pdfAddress+"\"").append(",\"msg\":").append("\""+msg+"\"").append("}");
        	    LOGGER.error("超出1000:{}", sbStr.toString());
        	    return sbStr.toString();
        	}
        	pdfAddress = fileAddress;
        }else {
        	// 条件查询
        	requestDto.setMerchantAccount(request.getRemoteUser());
        	ApiResDto<String> apiResDto = tradeInfoAggregationService.queryPDFAddressByCondition(requestDto);
    		if (apiResDto.isSuccess()) {
    			pdfAddress = apiResDto.getResult();
    		}else {
    			// 返回错误信息
    			msg = apiResDto.getResponseMsg();
    		}
        }
        
        // 判断所有的pdf地址是否有效
        if(!StringUtil.isEmpty(pdfAddress)) {
        	String[] pdfAddressArr = pdfAddress.split(",");
        	for(String pdfAddressResult : pdfAddressArr) {
        		if(!StringUtil.isEmpty(pdfAddressResult) && OSSClientUtil.exists(bucketname, pdfAddressResult)) {
        			success = true;
        		}else {
        		    LOGGER.error("oss上不存在该电子回单pdfAddressResult:{}", pdfAddressResult);
        		    success = false;
        			msg = "电子回单开具失败";
        			break;
        		}
        	}
        }
        
		sbStr.append("{").append("\"success\":").append(success).append(",\"pdfAddress\":").append("\""+pdfAddress+"\"").append(",\"msg\":").append("\""+msg+"\"").append("}");
        LOGGER.info("result:{}", sbStr.toString());
        return sbStr.toString();
    }

    /**
     * 渲染 汇总电子 回单 结果页
     * @param success
     * @param request
     * @return
     */
    @RequestMapping("batchEleResult")
    public ModelAndView batchEleResult(HttpServletRequest request, @ModelAttribute("success") String success,@ModelAttribute("pdfAddress") String pdfAddress,@ModelAttribute("msg") String msg) {
    	ModelAndView mav = new ModelAndView(TRADE_INFO_ELE_RECEIPT_RESULT);
    	mav.addObject("success", success);
    	mav.addObject("pdfAddress", pdfAddress);
    	mav.addObject("msg", msg);
    	return mav;
    }
    	
    /**
     * 功能描述: <br>
     * 〈汇总电子回单-下载按钮〉
     * 1.如果有勾选，则下载勾选的
     * 2.如果没有勾选，则下载条件下的 
     * 
     * @return
     * @throws  
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("downloadBatchElectronicPDF")
    public void downloadBatchElectronicPDF(@ModelAttribute("fileAddress") String fileAddress, HttpServletRequest request, HttpServletResponse resp){
        LOGGER.info("汇总电子回单下载开始。fileAddress：{}", fileAddress);
        String zipFileName = DateUtil.formatDate(new Date(), DateConstant.DATEFORMATE_YYYYMMDDHHMMSS)+".zip";
        
        String bucketname = OSSBucket.BATCH_PAYMENT_FILE;
        HttpURLConnection conn = null;
    	InputStream is = null;
    	ZipOutputStream zipos = null;
        try {
        	// 设置头信息
        	resp.reset();
        	resp.setContentType("application/zip;charset=UTF-8");
        	resp.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(zipFileName, "UTF-8"))));
        	
        	// 下载
        	zipos = new ZipOutputStream(resp.getOutputStream());
        	zipos.setMethod(ZipOutputStream.DEFLATED);
        	// 判断pdf地址是否有效
    		String[] pdfAddressArr = fileAddress.split(",");
        	for(String pdfAddressResult : pdfAddressArr) {
        		// 判断pdf地址是否存在oss
        		if(!OSSClientUtil.exists(bucketname, pdfAddressResult)) {
        			LOGGER.error("oss上不存在该文件,文件名为：{}", pdfAddressResult);
        			return;
        		}
        		// 压缩成zip
        		this.dealZipFile(bucketname, pdfAddressResult, conn, is, zipos);
        	}
        	
		} catch (Exception e) {
			LOGGER.error("汇总电子回单下载失败:{}", ExceptionUtils.getStackTrace(e));
		}finally {
			if(null != conn) {
				conn.disconnect();
			}
			try {
				if(null != is) {
					is.close();
				}
				if(null != zipos) {
					zipos.close();
				}
			} catch (Exception ex) {
				LOGGER.error("汇总电子回单流关闭失败:{}", ExceptionUtils.getStackTrace(ex));
			}
			
		}
        
        LOGGER.info("汇总电子回单下载结束");
    }
    
    /**
     * pdf压缩成zip 下载
     * @param bucketname
     * @param pdfAddressResult
     * @param conn
     * @param is
     * @param zipos
     * @param os
     */
    private void dealZipFile(String bucketname, String pdfAddressResult, HttpURLConnection conn,InputStream is, ZipOutputStream zipos) {
		try {
			OSSDownloadParams params = new OSSDownloadParams(bucketname, pdfAddressResult);
			is = OSSClientUtil.getInputStream(params);
			zipos.putNextEntry(new ZipEntry(pdfAddressResult));
			
			byte[] b = new byte[1024];
			int length = 0;
			while ((length = is.read(b)) != -1) {
				zipos.write(b, 0, length);
			}
		} catch (Exception e) {
			LOGGER.error("汇总电子回单压缩异常:{}", ExceptionUtils.getStackTrace(e));
		}
    	
    }
 
    /**
     * 功能描述: <br>
     * 〈单笔电子回单按钮-预览〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("previewSingleElectronicPDF")
    public ModelAndView previewSingleElectronicPDF(@ModelAttribute("orderNo") String orderNo, @ModelAttribute("transactionType") String transactionType,
    		@ModelAttribute("fileAddress") String fileAddress, @ModelAttribute("serialNumber") String serialNumber, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("单笔电子回单-预览 开始。orderNo：{},transactionType:{}, fileAddress:{}", orderNo, transactionType, fileAddress);
        ModelAndView mvn = new ModelAndView(TRADE_INFO_PREVIEW);
        String targetURL = "";
        try {
        	String bucketName = OSSBucket.BATCH_PAYMENT_FILE;
        	if(StringUtil.isEmpty(fileAddress)) {
        		// 如果没有，则调接口重新生成pdf，返回 pdf地址(不含域名)
        		ApiResDto<String> fileAddressDto =  tradeInfoAggregationService.getFileAddrePDF(orderNo, transactionType);
        		if(fileAddressDto.isSuccess()) {
        			fileAddress = fileAddressDto.getResult();
        		}else {
        			 LOGGER.error("单笔电子回单-预览 调接口生成失败");
        		}
        	}
        	targetURL = OSSClientUtil.getDownloadURL(bucketName, fileAddress, false);
        	
        	LOGGER.info("单笔电子回单-预览,targetURL:{},fileAddress:{}", targetURL, fileAddress);
        	
        	mvn.addObject("targetURL", targetURL);
        	mvn.addObject("orderNo", orderNo);
        	mvn.addObject("transactionType", transactionType);
        	mvn.addObject("fileAddress", fileAddress);
        	mvn.addObject("serialNumber", serialNumber);
        	
        } catch (Exception ex) {
            LOGGER.error("单笔电子回单-预览 异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("单笔电子回单-预览 结束");
        return mvn;
    }
    
   /**
    * pdf文件输出流展示到页面
    * @param targetURL
    * @param request
    * @param response
    */
    @RequestMapping("toShowPdf")
    public void toShowPdf(@ModelAttribute("targetURL") String pdfUrl, HttpServletRequest request, HttpServletResponse response) {
    	LOGGER.info("pdf展示开始，targetURL:{}", pdfUrl);
    	response.setContentType("application/pdf");
    	OutputStream out = null;
        InputStream input = null;
        try {
        	// 返回pdf文件流
        	URL u = new URL(pdfUrl);
        	input = u.openStream();
        	out = response.getOutputStream();;
        	byte[] data = new byte[512];
        	int n = 0;
        	while (-1 != (n = input.read(data))) {
        		out.write(data, 0, n);
        	}
        	
        	out.flush();
        	input.close();
        	out.close();
		} catch (Exception e) {
			LOGGER.error("pdf展示异常，{}", e);
		}
    	
    }
    
    /**
     * 功能描述: <br>
     * 〈单笔电子回单-下载〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("downloadSingleElectronicPDF")
    public void downloadSingleElectronicPDF(@ModelAttribute("orderNo") String orderNo, @ModelAttribute("transactionType") String transactionType, @ModelAttribute("fileAddress") String fileAddress, HttpServletResponse response) {
    	LOGGER.info("单笔电子回单下载文件开始,orderNo:{},transactionType:{},fileAddress:{}",orderNo, transactionType,fileAddress);
    	String bucketName = OSSBucket.BATCH_PAYMENT_FILE;
    	InputStream in = null;
		OutputStream out = null;
    	// 判断oss上是否有该文件
    	if(!OSSClientUtil.exists(bucketName, fileAddress)) {
    		// 如果没有，则调接口重新生成pdf，返回 pdf地址
    		ApiResDto<String> fileAddressDto =  tradeInfoAggregationService.getFileAddrePDF(orderNo, transactionType);
    		fileAddress = StringUtil.isEmpty(fileAddressDto.getResult()) ? "" : fileAddressDto.getResult() ;
    	}
    	
    	// 电子回单地址不存在
    	if(StringUtil.isEmpty(fileAddress)) {
    		LOGGER.info("电子回单地址不存在");
    		return ;
    	}
    	int index = fileAddress.lastIndexOf(FILE_SEPARATOR) + 1;
    	String fileName = fileAddress.substring(index);
    	
    	// 根据地址以及bucketName 下载
		LOGGER.info("下载文件开始,路径为:{},文件名称为{}",fileAddress, fileName);
		try {
			OSSDownloadParams params = new OSSDownloadParams(bucketName, fileAddress);
			response.setContentType("application/pdf;charset=UTF-8");
			response.setHeader("content-disposition", "attachment;filename=" + fileName);
			in = OSSClientUtil.getInputStream(params);
			if (in == null) {
				LOGGER.error("文件不存在。文件路径:{}", fileAddress);
				return;
			}
			out = response.getOutputStream();
			int len = 0;
			byte buf[] = new byte[1024];
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			LOGGER.info("下载结束,文件名:{}", fileName);
		} catch (Exception e) {
			LOGGER.error("下载明细文件:{}失败, 错误异常:{}", fileName, ExceptionUtils.getStackTrace(e));
		} finally {
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
    
    /**
     * 功能描述: <br>
     * 〈单笔电子回单-查询邮箱地址〉
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "queryEmail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryEmail(HttpServletRequest request) {
    	 LOGGER.info("查询收件人电子邮箱开始");
    	 Map<String, String> result = merchantInfoService.queryMerchantAuth(request.getRemoteUser(), EPPSCBPConstants.OPERATOR_CODE_MAIN);
    	 final String responseCode = MapUtils.getString(result, CommonConstant.RESPONSE_CODE);
    	 StringBuilder sb = new StringBuilder();
    	 String success = "false";
    	 String payerMerchantName = "";
    	 String recipientEmail = "";
    	 if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
             final String context = MapUtils.getString(result, CommonConstant.CONTEXT);
             Map<String, String> ctMerchantAuth = JSON.parseObject(context, Map.class);
             payerMerchantName = (String)ctMerchantAuth.get("payerMerchantName");
             recipientEmail = (String)ctMerchantAuth.get("recipientEmail");
             success = "true";
         }
         sb.append("{").append("\"success\":").append(success).append(",\"mail\":").append("\""+recipientEmail+"\"").append(",\"payerMerchantName\":").append("\""+payerMerchantName+"\"").append("}");
         return sb.toString();
    }
    
    /**
     * 功能描述: <br>
     * 〈单笔电子回单-发送邮件〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "sendEmail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendEmail(@ModelAttribute("merchantName") String merchantName, @ModelAttribute("fileAddress") String fileAddress, @ModelAttribute("recipientEmailAddress") String recipientEmailAddress,
    		@ModelAttribute("serialNumber") String serialNumber, HttpServletResponse response, HttpServletRequest request) {
    	LOGGER.info("发送邮件开始");
    	 boolean success = false;
		 StringBuffer sb = new StringBuffer();
    	try {
    		 // 构建收件人
    		 List<String> recipientList = new ArrayList<String>();
			 String[] recipientEmailAddresss = recipientEmailAddress.split(",");
			 for(String email : recipientEmailAddresss) {
				 recipientList.add(email); 
			 }
    		 
    		 // 构建附件信息(所有的附件加起来大小不能超过1M)
    		 List<FileData> fileDataList = new ArrayList();
    		 // 获取文件名
    		 int index = fileAddress.lastIndexOf(FILE_SEPARATOR) + 1;
    	     String fileName = fileAddress.substring(index);
    	     // 从oss获取文件流
    		 OSSDownloadParams params = new OSSDownloadParams(OSSBucket.BATCH_PAYMENT_FILE,  fileAddress);
             InputStream in = OSSClientUtil.getInputStream(params);
    		 byte[] data = HttpUtil.readInputStream(in);
    		 fileDataList.add(new FileData(fileName, data));
    		 // 主题
    		 String subject = "苏宁金融电子回单"+ serialNumber;
    		 // 邮件内容
    		 String mailContent = merchantName+"，您好！你的电子回单"+serialNumber + "已生成，请查收。";
    		 String sender = "";
    		 // 从scm获取: senderNameCh=南京苏宁易付宝网络科技有限公司
    		 String senderNickName = scmInitStartListener.getValue(ScmOnlineConfig.SENDER_NAME_CH);
    		 // 0-普通邮件，1-验证码
    		 int priority = 0;
    		 // 发送邮件
    		 Result result = MailUtils.sendTextMailWithAttach(recipientList , mailContent , subject , sender, senderNickName ,fileDataList,priority );
    		 LOGGER.info("邮件发送结果:{},{}", result.getErrorCode(),result.getErrorMsg());
    		
    		 if(result.isSuccess()) {
    			 success = true;
	    		 // 获取商户户头号
				 String merchantNo = request.getRemoteUser();
		    	 // 保存邮箱地址至权限表邮箱字段
				 merchantInfoService.updateRecipientEmailAddre(merchantNo, recipientEmailAddress);
    		 }
    		 
    	 } catch (Exception e) {
    		 LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(e));
    		 }
    	
    	sb.append("{").append("\"success\":").append(success).append(",\"emailAddress\":").append("\""+recipientEmailAddress+"\"").append("}");
    	return sb.toString();
    }
}
