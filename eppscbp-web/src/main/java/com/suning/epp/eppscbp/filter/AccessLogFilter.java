package com.suning.epp.eppscbp.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 访问记录过滤器<br>
 * 〈功能详细描述〉
 * 
 * @author 14101476
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AccessLogFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

    /**
     * 流水号
     */
    private static final String INVOKE_NO_KEY = "invokeNo";

    /**
     * IP地址
     */
    private static final String MDC_KEY_IP = "ip";

    /**
     * Session-ID
     */
    private static final String KEY_SESSION_ID = "sessionId";

    /**
     * 例外URI后缀
     */
    private String[] excludeSuffixes = new String[0];

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclude = filterConfig.getInitParameter("exclude_suffix");
        if (StringUtils.isNotBlank(exclude))
            excludeSuffixes = StringUtils.split(exclude, ",");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (excludeSuffixes.length > 0) {
            for (String exclude : excludeSuffixes) {
                if (requestURI.endsWith(exclude)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        // Session
        String sessionId;
        HttpSession session = request.getSession(false);
        if (session == null)
            sessionId = "<NO_SESSION>";
        else
            sessionId = session.getId();
        // URI
        String path = request.getRequestURI();
        @SuppressWarnings("unchecked")
        Enumeration<String> paramNames = request.getParameterNames();
        long execTime = 0;
        try {
            MDC.put(MDC_KEY_IP, request.getRemoteAddr());
            MDC.put(KEY_SESSION_ID, sessionId);
            MDC.put(INVOKE_NO_KEY, UUID.randomUUID().toString());

            long startTime = System.currentTimeMillis();
            chain.doFilter(request, response);
            execTime = System.currentTimeMillis() - startTime;
        } finally {
            StringBuilder message = new StringBuilder("Controller:");
            message.append(path);

            StringBuilder params = new StringBuilder();
            while (paramNames.hasMoreElements()) {
                String param = paramNames.nextElement();
                LOGGER.debug("原始报文：key={},value={}", param, request.getParameter(param));
                params.append(param).append("=");
                params.append(request.getParameter(param));
                if (paramNames.hasMoreElements())
                    params.append("&");
            }
            String paramsStr = params.toString();
            if (paramsStr.length() > 0)
                message.append("|Params:").append(paramsStr);

            message.append("|Spend:").append(execTime);
            // 记录日志
            LOGGER.info(message.toString());
            // 清除MDC里面的信息
            MDC.remove(MDC_KEY_IP);
            MDC.remove(KEY_SESSION_ID);
            MDC.remove(INVOKE_NO_KEY);
        }
    }

    @Override
    public void destroy() {
    }
}
