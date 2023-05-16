package com.suning.epp.eppscbp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suning.epp.dal.web.DalPage;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.OcaDataSource;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.req.LogisticsInfoQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.LogisticsInfoQueryResDto;
import com.suning.epp.eppscbp.rsf.service.impl.GeneralRsfService;
import com.suning.epp.eppscbp.service.OcaLogisticsInfoService;
import com.suning.epp.eppscbp.util.BeanConverterUtil;

@Service("ocaLogisticsInfoService")
public class OcaLogisticsInfoServiceImpl implements OcaLogisticsInfoService {
private static final Logger LOGGER = LoggerFactory.getLogger(OcaLogisticsInfoServiceImpl.class);

	
	@Autowired
	private GeneralRsfService<Map<String, String>> generalRsfService;
	
	@Override
	public ApiResDto<List<LogisticsInfoQueryResDto>> logisticsInfoQuery(LogisticsInfoQueryReqDto requestDto) {
		ApiResDto<List<LogisticsInfoQueryResDto>> apiResDto = new ApiResDto<List<LogisticsInfoQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用OCA物流信息查询");
            Map<String, Object> inputParam = BeanConverterUtil.beanToMap(requestDto);
            inputParam.put("dataSource", OcaDataSource.EPPSCBP_SOURCE.getCode());
            LOGGER.info("查询入参,inputParam:{}", inputParam);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.LOGISTICS_INFO, "queryLogisticsInfo", new Object[] { inputParam });
            LOGGER.info("查询返回参数,outputParam:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                List<LogisticsInfoQueryResDto> resultList = JSONObject.parseArray(outputParam.get("context"), LogisticsInfoQueryResDto.class);
                DalPage pageInfo = JSONObject.parseObject(outputParam.get("page"), DalPage.class);
                apiResDto.setResult(resultList);
                apiResDto.setPage(pageInfo);
                apiResDto.setResponseMsg("");
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
	}

	@Override
	public ApiResDto<String> logisticsInfoSubmit(String fileAddress, String userNo) {
		ApiResDto<String> apiResDto = new ApiResDto<String>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            Map<String, Object> requestMap = new HashMap<String, Object>();
            requestMap.put("fileAddress", fileAddress);
            requestMap.put("payerAccount", userNo);
            requestMap.put("dataSource", OcaDataSource.EPPSCBP_SOURCE.getCode());
            LOGGER.info("物流信息提交请求跨境入参:{}", requestMap);
            Map<String, String> response = generalRsfService.invoke(ApiCodeConstant.LOGISTICS_INFO, "logisticsInfoSubmit", new Object[] { requestMap });
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("物流信息提交请求跨境返回参数:{}", responseStr);
            final String responseCode = MapUtils.getString(response, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(response, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
            } else {
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
	}

}
