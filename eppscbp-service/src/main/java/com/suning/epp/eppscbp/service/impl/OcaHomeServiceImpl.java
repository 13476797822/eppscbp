package com.suning.epp.eppscbp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.ScmOnlineConfig;
import com.suning.epp.eppscbp.common.constant.TradeCoreConstant;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.OrderAmountDto;
import com.suning.epp.eppscbp.dto.OrderNumDto;
import com.suning.epp.eppscbp.dto.SettleAmountDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.OcaHomeRespDto;
import com.suning.epp.eppscbp.dto.res.OcaHomeResultDto;
import com.suning.epp.eppscbp.rsf.service.TradeCoreService;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.OcaHomeService;
import com.suning.epp.eppscbp.util.Arith;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.epp.eppscbp.util.oss.OSSBucket;
import com.suning.epp.eppscbp.util.oss.OSSClientUtil;
import com.suning.epp.eppscbp.util.oss.OSSDownloadParams;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

import net.oss.result.SdossResult;

@Service
public class OcaHomeServiceImpl implements OcaHomeService {
	
private static final Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);
	
	@Autowired
	private ScmInitStartListener scmInitStartListener;
	
	@Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;
	
	@Autowired
    private TradeCoreService tradeCoreService;

	
	@Override
	public ApiResDto<OcaHomeResultDto> initHomeInfo(String remoteUser,  HttpSession session) {
		LOGGER.info("首页service开始");
		Map<String, Object> inputParam = Maps.newHashMap();
		inputParam.put("payerAccount", remoteUser);
		
		OcaHomeResultDto dto = new OcaHomeResultDto();
		
		//从SCM获取第一条公告信息
		String noticePre = scmInitStartListener.getValue(ScmOnlineConfig.OCA_HOME_NOTICE_PRE);
		String noticeSuf = scmInitStartListener.getValue(ScmOnlineConfig.OCA_HOME_NOTICE_SUF);
		//从SCM上获取多条公告信息
		List<String> list = new ArrayList<String>();
		String ocaHomeNoticeList = scmInitStartListener.getValue(ScmOnlineConfig.OCA_HOME_NOTICE_LIST);
		if(!StringUtil.isEmpty(ocaHomeNoticeList)) {
			if(ocaHomeNoticeList.contains(CommonConstant.VERTICAL_LINE)) {
	        	//多条公告，用|分割
	        	String[] noticeStrs = ocaHomeNoticeList.split(CommonConstant.VERTICAL_LINE_ZY);
	        	for(int i=0;i<noticeStrs.length;i++) {
	        		list.add(i, noticeStrs[i]);
	        	}
	        }else {
	        	//一条公告
	        	list.add(0, ocaHomeNoticeList);
	        }
			dto.setNoticeMsgList(list);
		}
		
		//提现地址
		String withdrawPath = scmInitStartListener.getValue(ScmOnlineConfig.OCA_WITHDRAW);
		//传送门地址
		String mtradePath = scmInitStartListener.getValue(ScmOnlineConfig.OCA_MTRAD);
    	//提现地址
    	dto.setWithdrawPath(withdrawPath);
    	//传送门地址
    	dto.setMtradePath(mtradePath);
    	//拒付笔数
    	dto.setRejectOrderNum(0);
    	//在途资金
    	dto.setOnTheWayMoney("0.00");
    	//公告信息
    	dto.setNoticeMsg("1.暂无待办");
    	
    	//调用OCA系统接口，获取首页响应数据 
		ApiResDto<OcaHomeResultDto> ocaHomeResultDto = new ApiResDto<OcaHomeResultDto>();
        try {
        	  //调账务核心接口
    		  this.queryBalance(remoteUser, dto);
    		  LOGGER.info("oca首页OcaHomeResultDto:{}", dto);
    		
    		  ocaHomeResultDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
    	      ocaHomeResultDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES); 
              LOGGER.info("开始调用OCA系统接口查询.inputParam:{}", inputParam);
              Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.HOME_QUERY, "queryOnTheWayMoneyAndRejectOrderNum", new Object[] { inputParam });
              LOGGER.info("调用OCA系统接口查询结果:{}", outputParam);
              final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
              final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
              ocaHomeResultDto.setResponseCode(responseCode);
              if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                 
            	  final String context = MapUtils.getString(outputParam, CommonConstant.CONTEXT);
            	  OcaHomeRespDto ocaHomeResp = JSON.parseObject(context, OcaHomeRespDto.class);
            	  int rejectOrderNum = ocaHomeResp.getRejectOrderNum();
            	  //拒付笔数
            	  dto.setRejectOrderNum(rejectOrderNum);
            	  //在途资金
            	  String onTheWayMoney = StringUtil.formatMoney(ocaHomeResp.getOnTheWayMoney());
            	  dto.setOnTheWayMoney(Arith.fmtMicrometer(onTheWayMoney).contains("-") ? "0.00" : Arith.fmtMicrometer(onTheWayMoney));
            	  //公告信息
            	  if(rejectOrderNum > 0) {
            		  dto.setNoticeMsg(noticePre + rejectOrderNum + noticeSuf); 
            	  }
            	 
            	  
            	  ocaHomeResultDto.setResponseMsg("");
              } else if(EPPSCBPConstants.OCA_NO_RESULT.equals(responseCode)) {
                  LOGGER.info("返回码非成功:{}-{}", responseCode, responseMessage);
                  ocaHomeResultDto.setResponseCode(EPPSCBPConstants.SUCCESS_CODE);
                  ocaHomeResultDto.setResponseMsg(responseMessage);
                  
              }else {
            	  LOGGER.info("返回码非成功:{}-{}", responseCode, responseMessage);
            	  ocaHomeResultDto.setResponseMsg(responseMessage);
              }
              
        	  // 直接展示oss上的背景图片
        	  String targetURL = OSSClientUtil.getDownloadURL(OSSBucket.OCA, OSSBucket.BANNER,false);
        	  dto.setTargetURL(targetURL);
        	  LOGGER.info("下载背景图片路径:{}", targetURL); 
        	  
             ocaHomeResultDto.setResult(dto);
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
		return ocaHomeResultDto;
	}

	private void queryBalance(String remoteUser, OcaHomeResultDto dto) {
		//调账户核心查询 可用余额
    	ExitQueryBalance balanceResult = tradeCoreService.queryBalance(remoteUser, EPPSCBPConstants.ACCSRCCOD_RMB, EPPSCBPConstants.CUR_CODE_CNY);
    	LOGGER.info("调账务核心查询可用余额，返回结果:{}", balanceResult);
    	if (TradeCoreConstant.SUCCESS.equals(balanceResult.getRspCode())) {
            String balanceStr = StringUtil.formatMoney(balanceResult.getUseAmt());
            dto.setAvailableBalance(Arith.fmtMicrometer(balanceStr));
        } else {
        	 LOGGER.error("调账务核心查询可用余额失败");
        	 dto.setAvailableBalance("0.00");
        }
    	
    	//调账户核心查询 保证金余额
    	ExitQueryBalance exitQueryBalance = tradeCoreService.queryBalance(remoteUser, EPPSCBPConstants.ACCSRCCOD_03, EPPSCBPConstants.CUR_CODE_CNY);
    	LOGGER.info("调账务核心查询保证金余额，返回结果:{}", exitQueryBalance);
    	if (TradeCoreConstant.SUCCESS.equals(exitQueryBalance.getRspCode())) {
            String balanceStr = StringUtil.formatMoney(exitQueryBalance.getUseAmt());
            dto.setEarnestAmount(Arith.fmtMicrometer(balanceStr));
        } else {
        	 LOGGER.error("调账务核心查询保证金失败");
        	 dto.setEarnestAmount("0.00");
        }
	}
	
	@Override
	public ApiResDto<OcaHomeResultDto> queryHomeInfo(String fromTime, String toTime, String remoteUser) {
		LOGGER.info("饼状图service开始");
		Map<String, Object> inputParam = Maps.newHashMap();
		inputParam.put("payerAccount", remoteUser);
		inputParam.put("fromTime", fromTime);
		inputParam.put("toTime", toTime);
		//调用OCA系统接口，获取首页响应数据 
		ApiResDto<OcaHomeResultDto> ocaHomeResultDto = new ApiResDto<OcaHomeResultDto>();
		ocaHomeResultDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
		ocaHomeResultDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES); 
        try {
            LOGGER.info("开始调用OCA系统接口查询.inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.HOME_QUERY, "homeQuery", new Object[] { inputParam });
            LOGGER.info("调用OCA系统接口查询结果:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            ocaHomeResultDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
            	ocaHomeResultDto.setResponseMsg("");
            	final String context = MapUtils.getString(outputParam, CommonConstant.CONTEXT);
            	OcaHomeRespDto ocaHomeResp = JSON.parseObject(context, OcaHomeRespDto.class);
            	
            	OcaHomeResultDto dto = new OcaHomeResultDto();
            	//订单笔数
            	List<OrderNumDto> orderNumDtoList = new ArrayList<OrderNumDto>();
            	int saleSuccessCount = ocaHomeResp.getSaleSuccessCount();
            	int refundSuccessCount = ocaHomeResp.getRefundSuccessCount();
            	int rejectSuccessCount = ocaHomeResp.getRejectSuccessCount();
            	Long saleSuccessAmount = ocaHomeResp.getSaleSuccessAmount();
            	Long refundSuccessAmount = ocaHomeResp.getRefundSuccessAmount();
            	Long rejectSuccessAmount = ocaHomeResp.getRejectSuccessAmount();
            	Long settledAmount = ocaHomeResp.getSettledAmount();
            	Long includeMarginAmount = ocaHomeResp.getIncludeMarginAmount();
            	Long releaseMarginAmount = ocaHomeResp.getReleaseMarginAmount();
            	//总金额
            	Long orderAmountSum = saleSuccessAmount + refundSuccessAmount + rejectSuccessAmount;
            	Long settleAmountSum = settledAmount + includeMarginAmount + releaseMarginAmount;
            	
            	OrderNumDto orderNumDtoSaleSucc = new OrderNumDto();
            	orderNumDtoSaleSucc.setName("销售成功");
            	orderNumDtoSaleSucc.setValue(saleSuccessCount);
            	OrderNumDto orderNumDtoRefundSucc = new OrderNumDto();
            	orderNumDtoRefundSucc.setName("退款成功");
            	orderNumDtoRefundSucc.setValue(refundSuccessCount);
            	OrderNumDto orderNumDtoRejectSucc = new OrderNumDto();
            	orderNumDtoRejectSucc.setName("拒付交易");
            	orderNumDtoRejectSucc.setValue(ocaHomeResp.getRejectSuccessCount());
            	orderNumDtoList.add(orderNumDtoSaleSucc);
            	orderNumDtoList.add(orderNumDtoRefundSucc);
            	orderNumDtoList.add(orderNumDtoRejectSucc);
            	dto.setOrderNum(orderNumDtoList);
            	//订单总笔数
            	int orderNumSum = saleSuccessCount + refundSuccessCount + rejectSuccessCount;
            	dto.setOrderNumSum(orderNumSum);
            	
            	//订单金额
	        	List<OrderAmountDto> orderAmountDtoList = new ArrayList<OrderAmountDto>();
	        	OrderAmountDto orderAmountDtoSaleSucc = new OrderAmountDto();
	        	orderAmountDtoSaleSucc.setName("销售成功");
	        	orderAmountDtoSaleSucc.setValue(StringUtil.formatMoney(saleSuccessAmount));
	        	OrderAmountDto orderAmountDtoRefundSucc = new OrderAmountDto();
	        	orderAmountDtoRefundSucc.setName("退款成功");
	            orderAmountDtoRefundSucc.setValue(StringUtil.formatMoney(refundSuccessAmount));
	        	OrderAmountDto orderAmountDtoRejectSucc = new OrderAmountDto();
	        	orderAmountDtoRejectSucc.setName("拒付交易");
	        	orderAmountDtoRejectSucc.setValue(StringUtil.formatMoney(rejectSuccessAmount));
	        	orderAmountDtoList.add(orderAmountDtoSaleSucc);
	        	orderAmountDtoList.add(orderAmountDtoRefundSucc);
	        	orderAmountDtoList.add(orderAmountDtoRejectSucc);
	        	dto.setOrderAmount(orderAmountDtoList);
	        	//订单总金额
	        	dto.setOrderAmountSum(StringUtil.formatMoney(orderAmountSum));
            	
	        	//结算金额
	        	List<SettleAmountDto> settleAmountDtoList = new ArrayList<SettleAmountDto>();
	        	SettleAmountDto settleAmountDtoSaleSucc = new SettleAmountDto();
	        	settleAmountDtoSaleSucc.setName("已结算");
	        	settleAmountDtoSaleSucc.setValue(StringUtil.formatMoney(settledAmount));
	        	SettleAmountDto settleAmountDtoRefundSucc = new SettleAmountDto();
	        	settleAmountDtoRefundSucc.setName("纳入保证金");
	        	settleAmountDtoRefundSucc.setValue(StringUtil.formatMoney(includeMarginAmount));
	            SettleAmountDto settleAmountDtoRejectSucc = new SettleAmountDto();
	            settleAmountDtoRejectSucc.setName("释放保证金");
	            settleAmountDtoRejectSucc.setValue(StringUtil.formatMoney(releaseMarginAmount));
	        	settleAmountDtoList.add(settleAmountDtoSaleSucc);
	        	settleAmountDtoList.add(settleAmountDtoRefundSucc);
	        	settleAmountDtoList.add(settleAmountDtoRejectSucc);
	        	dto.setSettleAmount(settleAmountDtoList);
	        	//结算总金额
	        	dto.setSettleAmountSum(StringUtil.formatMoney(settleAmountSum));
	        	
            	ocaHomeResultDto.setResult(dto);
            	LOGGER.info("饼状图结果.ocaHomeResultDto:{}", ocaHomeResultDto);
            } else {
                LOGGER.info("返回码非成功:{}-{}", responseCode, responseMessage);
                ocaHomeResultDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
		return ocaHomeResultDto;
	}
	
}
