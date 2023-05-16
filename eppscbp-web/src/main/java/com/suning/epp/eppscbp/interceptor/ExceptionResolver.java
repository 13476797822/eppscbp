///*
// * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
// * FileName: ExceptionResolver.java
// * Author:   14101476
// * Date:     2017-2-17 上午09:26:02
// * Description: //模块目的、功能描述      
// * History: //修改记录
// * <author>      <time>      <version>    <desc>
// * 修改人姓名             修改时间            版本号                  描述
// */
//package com.suning.epp.eppscbp.interceptor;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.suning.epps.eppscms.oscportal.common.util.IOUtils;
//import com.suning.epps.eppscms.oscportal.common.util.JSONUtils;
//import com.suning.framework.lang.AppException;
//import com.suning.framework.lang.Result;
//
///**
// * SpringMVC异常统一处理<br>
// * 〈功能详细描述〉 ajax请求的异常，返回错误消息；app异常打印错误信息，其它打印错误消息，并跳转到错误页面
// * 
// * @author shihui
// * @see [相关类/方法]（可选）
// * @since [产品/模块版本] （可选）
// */
//@Component
//public class ExceptionResolver implements HandlerExceptionResolver {
//
//    /**
//     * 错误页
//     */
//    private static final String ERROR_PAGE = "commons/error";
//
//    /**
//     * 日志对象
//     */
//    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);
//
//    /*
//     * (non-Javadoc)
//     * @see
//     * org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest,
//     * javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
//     */
//    @Override
//    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
//            Exception ex) {
//        LOGGER.error("common exception resolver", ex);
//        Result result = new Result();
//        // 判断是AJAX请求异常
//        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
//            result.fail(ex.getMessage());
//            response.setContentType("application/json; charset=utf-8");
//            response.setCharacterEncoding("UTF-8");
//            PrintWriter out = null;
//            try {
//                out = response.getWriter();
//                out.append(JSONUtils.toJSONString(result));
//            } catch (IOException e) {
//                LOGGER.error("IOException :", e);
//            } finally {
//                IOUtils.close(out);
//            }
//            return null;
//
//        } else {
//            if (ex instanceof AppException) {
//                result.fail(((AppException) ex).getErrorCode(), ex.getMessage());
//            } else {
//                result.fail(ex.getMessage());
//            }
//            LOGGER.error(JSONUtils.toJSONString(result));
//            return new ModelAndView(ERROR_PAGE);
//        }
//    }
//}
