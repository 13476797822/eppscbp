package com.suning.epp.eppscbp.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.ScmOnlineConfig;
import com.suning.epp.eppscbp.service.MerchantInfoService;
import com.suning.epp.eppscbp.service.impl.ScmInitStartListener;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.ids.client.util.AuthUtils;

public class MerchantAuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantAuthInterceptor.class);
    // 没权限N
    private static final String IS_RIGHT = "Y";
    // 需要拦截的url
    public String[] interceptUrls = { "goodsTrade", "overseasPay", "batchQuery", "singleQuery", "merchantHandle", "rateQuery", "collAndSettle", "logisticsSettlement", "batchPaymentQuery", "cpWithdrawApply", "cpStoreHandle", "cpBatchPayment", "cpWithdrawDetail", "cpBatchPaymentDetail", "cpTranInOutDetail", "cpWithdrawAccount", "tradeQueryAuth", "batchPaymentReview", "cpBatchPaymentReview","preOrderCollAndSettle" };

    // 门户url
    private String cbpUrls = "/goodsTrade/overseasPay/batchQuery/singleQuery/merchantHandle/rateQuery/collAndSettle/logisticsSettlement/batchPaymentQuery/batchPaymentReview/";
    // 收款平台url
    private String ccpUrls = "/cpWithdrawApply/cpStoreHandle/cpBatchPayment/cpWithdrawDetail/cpBatchPaymentDetail/cpTranInOutDetail/cpWithdrawAccount/cpBatchPaymentReview/";

    // 交易信息查询url
    private String tradeInfoUrls = "/tradeQueryAuth/";

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private ScmInitStartListener scmInitStartListener;

    // 获取url
    public void setInterceptUrls(String[] interceptUrls) {
        this.interceptUrls = interceptUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            LOGGER.info("请求URL地址:{},{}", request.getRequestURL(), request.getContentType());
            String urlEvn = request.getRequestURL().toString().split("//")[1].split("/")[0];
            LOGGER.info("请求URL域名==:{}", urlEvn);

            String userNo = request.getRemoteUser();
            String userName = AuthUtils.getCurrentUserAttribute(request, "username");
            String operatorCode = EPPSCBPConstants.OPERATOR_CODE_MAIN;
            int index = userName.indexOf("#");     
            if(index > 0) {
                operatorCode = userName.substring(index+1);
                request.setAttribute("isOperator", "Y");
            } 
            String accounts = scmInitStartListener.getValue(ScmOnlineConfig.PLATFORM_MERCHANT_ACCOUNT);

            if (!StringUtil.isEmpty(accounts) && accounts.matches(".*" + userNo + ".*")) {
                request.setAttribute("isPlatform", "Y");
            } else {
                request.setAttribute("isPlatform", "N");
            }

            // 获取制定的关键目录值
            String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
            LOGGER.info("请求URI地址:{}", requestUrl);
            String[] urls = requestUrl.split("/");
            String key = urls[1];
            response.setContentType("text/html; charset=utf-8");
            LOGGER.info("商户权限查询开始:{}", key);
            // 判断是否为需要拦截的请求
            if (null != interceptUrls && interceptUrls.length >= 1)
                for (String url : interceptUrls) {
                    if (key.equals(url)) {
                        LOGGER.info("商户权限开始匹配:{}", url);
                        // 商户权限结果
                        Map<String, String> result = merchantInfoService.queryMerchantAuth(userNo, operatorCode);
                        final String responseCode = MapUtils.getString(result, CommonConstant.RESPONSE_CODE);
                        if (CommonConstant.RESPONSE_SUCCESS_CODE.equals(responseCode)) {
                            final String context = MapUtils.getString(result, CommonConstant.CONTEXT);
                            Map<String, String> ctMerchantAuth = JSON.parseObject(context, Map.class);
                            String isRight = (String) ctMerchantAuth.get(url);
                            LOGGER.info("商户权限开通标识匹配:{}", isRight);
                            if (!IS_RIGHT.equals(isRight)) {// isRight
                                LOGGER.info("该商户{}权限未开通", url);

                                if (cbpUrls.matches(".*/" + key + "/.*")) {
                                    response.sendRedirect("https://" + urlEvn + request.getContextPath() + "/common/guide.htm");
                                } else if (ccpUrls.matches(".*/" + key + "/.*")) {
                                    response.sendRedirect("https://" + urlEvn + request.getContextPath() + "/common/cpGuide.htm");
                                } else {
                                    response.sendRedirect("https://" + urlEvn + request.getContextPath() + "/common/tradeInfoGuide.htm");
                                }

                            }
                        } else if (CommonConstant.AUTH_NULL.equals(responseCode)) {
                            LOGGER.info("该商户:{}权限未查到", url);
                            if (cbpUrls.matches(".*/" + key + "/.*")) {
                                response.sendRedirect("https://" + urlEvn + request.getContextPath() + "/common/guide.htm");
                            } else if (ccpUrls.matches(".*/" + key + "/.*")) {
                                response.sendRedirect("https://" + urlEvn + request.getContextPath() + "/common/cpGuide.htm");
                            } else {
                                response.sendRedirect("https://" + urlEvn + request.getContextPath() + "/common/tradeInfoGuide.htm");
                            }
                        } else {
                            return false;
                        }
                    }
                }
        } catch (Exception ex) {
            LOGGER.error("商户权限查询异常:{}", ExceptionUtils.getStackTrace(ex));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
