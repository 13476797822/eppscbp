/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: MemberInfoServiceImpl.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:53:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.rsf.service.MemberInfoService;

/**
 * 〈会员系统接口〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("memberInfoService")
public class MemberInfoServiceImpl implements MemberInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoServiceImpl.class);
	//支付密码验证
	private static final String VALIDATE_PAYMENTPASSWORD = "validatePaymentPassword";
	//AES加解密密钥查询
	private static final String QUERY_SECRETKEY = "querySecretKey";
	@Autowired
	private GeneralRsfService<Map<String, Object>> generalRsfService;

	/* 
	 * (non-Javadoc)
	 * @see com.suning.epp.eppscbp.rsf.service.MemberInfoService#validatePaymentPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public ApiResDto<String> validatePaymentPassword(String userNo,String paymentPassword) {
	    ApiResDto<String> apiResDto=new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
		try {
		    //调用参数
	        Map<String, Object> request = new HashMap<String, Object>();
			request.put(EPPSCBPConstants.USE_NO, userNo);
			request.put(EPPSCBPConstants.PAYMENT_PASSWORD, paymentPassword);
			request.put(EPPSCBPConstants.SERIAL_NO, UUID.randomUUID().toString().replace("-", ""));
			request.put(EPPSCBPConstants.SYSNAME, EPPSCBPConstants.SYSNAME_CODE);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求支付密码验证的参数:{}", requestStr);
            Map<String, Object> response = generalRsfService.invoke(ApiCodeConstant.VALIDATE_PAYMENTPASSWORD, VALIDATE_PAYMENTPASSWORD,new Object[] {request});
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("支付密码验证的参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMsg = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (CommonConstant.PAYPASSWORD_ERROR_CODE.equals(responseCode) || CommonConstant.PAYPASSWORD_TIMES_CODE.equals(responseCode)) {
                final String remainCount = MapUtils.getString(response, CommonConstant.REMAIN_COUNT);
                apiResDto.setResponseMsg("密码错误,剩余支付密码输入次数" + remainCount);
                if(remainCount.equals("0")) {
                    apiResDto.setResponseMsg("密码错误,剩余支付密码输入次数0,密码将于明天0点解锁"); 
                }
            }else if(CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)){
                apiResDto.setResponseMsg("");
            }else if(response != null){
                apiResDto.setResponseMsg(responseMsg);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        } 
        return apiResDto;
	}

	/* (non-Javadoc)
	 * @see com.suning.epp.eppscbp.rsf.service.MemberInfoService#eppSecretKeyRsfServer()
	 */
	@Override
	public ApiResDto<String> eppSecretKeyRsfServer() {
	    ApiResDto<String> apiResDto=new ApiResDto<String>();
	    apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
		try {
		    //调用参数
	        Map<String, Object> request = new HashMap<String, Object>();
			request.put(EPPSCBPConstants.SERIAL_NO, UUID.randomUUID().toString().replace("-", ""));
			request.put(EPPSCBPConstants.SYSNAME, EPPSCBPConstants.SYSNAME_CODE);
            String requestStr = JSON.toJSONString(request);
            LOGGER.info("请求AES加解密密钥查询的参数:{}", requestStr);
            Map<String, Object> response = generalRsfService.invoke(ApiCodeConstant.QUERY_SECRETKEY, QUERY_SECRETKEY,new Object[] {request});
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("AES加解密密钥查询的参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            apiResDto.setResponseCode(responseCode);
            if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                String key = String.valueOf(response.get(EPPSCBPConstants.PAYMENT_KEY));        
                apiResDto.setResult(key);
            }else if(response != null){
                final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
                apiResDto.setResponseMsg(responseMessage);
            }    
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        } 
        return apiResDto;
	}
}
