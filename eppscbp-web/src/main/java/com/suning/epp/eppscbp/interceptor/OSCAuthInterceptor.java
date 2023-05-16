//package com.suning.epp.eppscbp.interceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.Assert;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.suning.epps.eppscms.oscportal.biz.SignProcessBiz;
//import com.suning.epps.eppscms.oscportal.common.constant.RedisConstants;
//import com.suning.epps.eppscms.oscportal.common.util.RedisClientUtils;
//import com.suning.epps.eppscms.oscportal.integration.dto.DetailSignedProduct;
//import com.suning.framework.lang.AppException;
//import com.suning.framework.web.session.util.StringUtils;
//
///**
// * 
// * 〈权限拦截〉<br>
// * 〈从请求中获取易付宝账号和订单id，判断订单创建时的易付宝账号是否和登陆账号一致，如果不一致不允许操作〉
// *
// * @author 14072597
// * @see [相关类/方法]（可选）
// * @since [产品/模块版本] （可选）
// */
//public class OSCAuthInterceptor extends HandlerInterceptorAdapter {
//    /**
//     * log
//     */
//    private static final Logger LOGGER = LoggerFactory.getLogger(OSCAuthInterceptor.class);
//
//    @Autowired
//    private SignProcessBiz signProcessBiz;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 取出订单号ID
//        String orderIdStr = request.getParameter("orderId");
//        if (!StringUtils.isBlank(orderIdStr)) {
//            authInterceptor(request, orderIdStr);
//        }
//
//        return true;
//    }
//
//    /**
//     * 
//     * 功能描述: <br>
//     * 〈功能详细描述〉
//     *
//     * @param httpServletRequest
//     * @param orderIdStr
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    private void authInterceptor(HttpServletRequest httpServletRequest, String orderIdStr) {
//        try {
//            Long orderId = Long.valueOf(orderIdStr);
//            // 查询订号信息
//            String sign_userNo = getUserNoSigned(orderId);
//            String userNo = httpServletRequest.getRemoteUser();
//            if (!sign_userNo.equals(userNo)) {
//                // 无权限，直接终止
//                throw new AppException("403", "您无权限操作该笔订单!如果为您本人账号申请订单，请线下联系易付宝业务人员");
//            }
//        } catch (NumberFormatException e) {
//            // 不需要处理
//            LOGGER.warn("orderId 转换异常", e);
//        }
//    }
//
//    /**
//     * 
//     * 功能描述: <br>
//     * 〈查询已签约订单的易付宝账号〉
//     *
//     * @param orderId
//     * @return
//     * @see [相关类/方法](可选)
//     * @since [产品/模块版本](可选)
//     */
//    private String getUserNoSigned(Long orderId) {
//        // 从缓存中取userNo，缓存中取不到,查server
//        String userNo = RedisClientUtils.get(RedisConstants.REDIS_KEY_QUERY_USERNO_BY_ORDERID_PREFIX + orderId);
//        if (StringUtils.isBlank(userNo)) {
//            DetailSignedProduct dto = signProcessBiz.querySignedProductById(orderId);
//            Assert.notNull(dto, "查询的订单不存在！");
//            userNo = dto.getUserNo();
//            // 推缓存
//            RedisClientUtils.setAndCatchException(RedisConstants.REDIS_KEY_QUERY_USERNO_BY_ORDERID_PREFIX + orderId,
//                    userNo, RedisConstants.ORDERID_USERNO_EXPIRE);
//        }
//        return userNo;
//    }
//
//}
