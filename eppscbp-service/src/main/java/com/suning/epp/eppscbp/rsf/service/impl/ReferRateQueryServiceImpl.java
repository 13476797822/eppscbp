/**
 * 
 */
package com.suning.epp.eppscbp.rsf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.ReferRateQueryResDto;
import com.suning.epp.eppscbp.rsf.service.ReferRateQueryService;

/**
 * @author 17080704
 *
 */
@Service("referRateQueryServiceImpl")
public class ReferRateQueryServiceImpl implements ReferRateQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferRateQueryServiceImpl.class);

    @Autowired
    private GeneralRsfService<Map<String, String>> generalRsfService;

    // 汇率查询方法
    private static final String REFER_RATE_QUERY = "referRateQuery";

    // 汇率查询方法
    private static final String REFER_RATE_QUERY_ZX_GD = "referRateQueryZXorGD";

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.ReferRateQueryService#referRateQuery()
     */
    @Override
    public ApiResDto<List<ReferRateQueryResDto>> referRateQuery() {
        ApiResDto<List<ReferRateQueryResDto>> apiResDto = new ApiResDto<List<ReferRateQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用购付汇进行汇率查询");
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REFER_RATE_QUERY, REFER_RATE_QUERY);
            LOGGER.info("汇率查询结果:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                List<ReferRateQueryResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), ReferRateQueryResDto.class);
                apiResDto.setResult(resultList);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("单笔查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }


    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.ReferRateQueryService#referRateQueryZXorGD()
     */
    @Override
    public ApiResDto<List<ReferRateQueryResDto>> referRateQueryZXorGD(String channelId) {
        ApiResDto<List<ReferRateQueryResDto>> apiResDto = new ApiResDto<List<ReferRateQueryResDto>>();
        apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
        apiResDto.setResponseMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            LOGGER.info("开始调用购付汇进行汇率查询,查询报价机构:{}",channelId);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("channelId",channelId);
            Map<String, String> outputParam = generalRsfService.invoke(ApiCodeConstant.REFER_RATE_QUERY, REFER_RATE_QUERY_ZX_GD,map);
            LOGGER.info("{}:查询汇率查询结果:{}", channelId,outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            apiResDto.setResponseCode(responseCode);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                apiResDto.setResponseMsg("");
                List<ReferRateQueryResDto> resultList = JSONObject.parseArray(outputParam.get(EPPSCBPConstants.CONTEXT), ReferRateQueryResDto.class);
                apiResDto.setResult(resultList);
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("单笔查询未成功状态:{}-{}", responseCode, responseMessage);
                apiResDto.setResponseMsg(responseMessage);
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return apiResDto;
    }

}
