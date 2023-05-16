/*
 * Copyright (C), 2002-2016, 苏宁易购电子商务有限公司
 * FileName: InitSfbmpListener.java
 * Author:   13073409
 * Date:     2016年11月2日 上午11:23:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.listener;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 13073409 2016年11月2日 上午11:23:32
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AppServletContextListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(AppServletContextListener.class);

    private ServletContext context = null;

    public AppServletContextListener() {
    }

    // 该方法在ServletContext启动之后被调用，并准备好处理客户端请求
    public void contextInitialized(ServletContextEvent event) {
        LOG.info("AppServletContextListener 初始化......");
        this.context = event.getServletContext();
        // 时间戳(可应用于版本发布控制)
        context.setAttribute("stimestamp", String.valueOf(new Date().getTime()));
    }

    // 这个方法在ServletContext 将要关闭的时候调用
    public void contextDestroyed(ServletContextEvent event) {
        this.context = null;
    }
}