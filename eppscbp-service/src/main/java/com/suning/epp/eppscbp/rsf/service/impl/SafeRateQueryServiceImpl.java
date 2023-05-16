package com.suning.epp.eppscbp.rsf.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.epp.eppscbp.common.constant.ApiCodeConstant;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.errorcode.RsfErrorCode;
import com.suning.epp.eppscbp.rsf.service.SafeRateQueryService;
import com.suning.epp.eppscbp.util.DateUtil;

/**
 * 〈外管汇率查询〉<br>
 * 〈功能详细描述〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("safeRateQueryServiceImpl")
public class SafeRateQueryServiceImpl implements SafeRateQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SafeRateQueryServiceImpl.class);

    @Autowired
    private GeneralRsfService<Map<String, Object>> generalRsfService;

    // 外管汇率方法
    private static final String SAFE_RATE_QUERY = "queryCTSafeRate";

    private static final String CUR_CODE = "curCode";

    private static final String PRIDE_START_DATE = "prideStartDate";

    public static final String RATE = "rate";

    @Override
    public BigDecimal safeRateQuery(String cur) {
        try {
            Map<String, String> requestParam = new HashMap<String, String>();
            requestParam.put(CUR_CODE, cur);
            requestParam.put(PRIDE_START_DATE, DateUtil.formatDate(new Date(), "YYYY-MM"));
            LOGGER.info("开始调用购付汇进行汇率查询,requestParam:{}", requestParam);

            Map<String, Object> outputParam = generalRsfService.invoke(ApiCodeConstant.SAFE_RATE_QUERY, SAFE_RATE_QUERY, requestParam);
            LOGGER.info("外管汇率查询结果:{}", outputParam);
            final String responseCode = MapUtils.getString(outputParam, CommonConstant.RESPONSE_CODE);
            final String responseMessage = MapUtils.getString(outputParam, CommonConstant.RESPONSE_MESSAGE);
            if (EPPSCBPConstants.SUCCESS_CODE.equals(responseCode)) {
                return new BigDecimal(MapUtils.getString(outputParam, RATE));
            } else {
                // 未查询到数据或查询出错
                LOGGER.info("外管汇率查询未成功状态:{}-{}", responseCode, responseMessage);
                return null;
            }
        } catch (Exception ex) {
            LOGGER.error(RsfErrorCode.UNKNOWN_CN, ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }

}
