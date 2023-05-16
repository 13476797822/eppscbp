/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: SitemeshFreemarkerDecoratorServlet.java
 * Author:   14101476
 * Date:     2017-2-17 上午09:26:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.servlet;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import com.opensymphony.module.sitemesh.freemarker.FreemarkerDecoratorServlet;
import com.suning.epp.pps.client.utils.PublicPageUtils;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

public class SitemeshFreemarkerDecoratorServlet extends FreemarkerDecoratorServlet {

    /**
     */
    private static final long serialVersionUID = -932927776246721655L;

    @Override
    protected boolean preTemplateProcess(HttpServletRequest request, HttpServletResponse response, Template template,
            TemplateModel templateModel) throws ServletException, IOException {
        boolean result = super.preTemplateProcess(request, response, template, templateModel);

        Configuration config = getConfiguration();

        SimpleHash hash = (SimpleHash) templateModel;

        HTMLPage htmlPage = (HTMLPage) request.getAttribute(RequestConstants.PAGE);

        String title, body, head;

        if (htmlPage == null) {
            title = "No Title";
            body = "No Body";
            head = "<!-- No head -->";
        } else {
            title = htmlPage.getTitle();

            StringWriter buffer = new StringWriter();
            htmlPage.writeBody(buffer);
            body = buffer.toString();

            buffer = new StringWriter();
            htmlPage.writeHead(buffer);
            head = buffer.toString();

            hash.put("page", htmlPage);
        }

        // -- PPS公用页相关
        // 页头
        String top;
        // 横幅（无导航）
        String banner;
        // 横幅（有导航）
        String bannerNavigation;
        // 底部
        String bottom;
        // 二级导航
        String bannerNavSecond;
        if (request.getRemoteUser() != null && request.getRemoteUser().length() > 0) {
            top = PublicPageUtils.queryPublicPageByCode("SFBMP_TopLogon");
        } else {
            top = PublicPageUtils.queryPublicPageByCode("SFBMP_TopLogout");
        }
        banner = PublicPageUtils.queryPublicPageByCode("SFBMP_Banner");
        bannerNavigation = PublicPageUtils.queryPublicPageByCode("SFBMP_BannerNvg");
        bottom = PublicPageUtils.queryPublicPageByCode("SFBMP_Bottom");
        bannerNavSecond = PublicPageUtils.queryPublicPageByCode("SFBMP_NvgSecond");
        hash.put("top", top);
        hash.put("banner", banner);
        hash.put("bannerNavigation", bannerNavigation);
        hash.put("bottom", bottom);
        hash.put("bannerNavSecond", bannerNavSecond);

        hash.put("title", title);
        hash.put("body", body);
        hash.put("head", head);
        String contextPath = request.getContextPath();
        hash.put("base", contextPath);
        String resRoot = System.getenv("resRoot");
        if (resRoot == null) {
            resRoot = System.getProperty("resRoot");
        }
        hash.put("resRoot", resRoot);
        if (!config.getSharedVariableNames().isEmpty()) {
            Object[] names = config.getSharedVariableNames().toArray();
            for (Object name : names) {
                hash.put(name.toString(), config.getSharedVariable(name.toString()));
            }
        }

        return result;
    }
}
