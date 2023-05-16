/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: ApplicationEnv.java
 * Author:   14101476
 * Date:     2017-2-14 下午05:28:14
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环境相关配置<br>
 * 〈功能详细描述〉
 * 
 * @author 14101476
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ApplicationEnv {

    public static final Logger LOG = LoggerFactory.getLogger(ApplicationEnv.class);

    /**
     * 资源文件路径
     */
    private static final String FILE_PATH = "application-env.properties";

    /**
     * 资源属性
     */
    private static Properties properties = null;

    /**
     * 功能描述: 初始化<br>
     * 〈功能详细描述〉
     * 
     * @throws IOException
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void init() throws IOException {
        synchronized (ApplicationEnv.class) {
            if (properties == null || properties.isEmpty()) {
                properties = new Properties();
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_PATH);
                try {
                    properties.load(inputStream);
                } catch (IOException e) {
                    throw e;
                } finally {
                    inputStream.close();

                }
            }
        }
    }

    /**
     * 功能描述: 获取资源属性<br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Properties getProperties() {
        if (properties == null || properties.isEmpty()) {
            try {
                init();
            } catch (IOException e) {
                LOG.error("初始化ApplicationEnv异常", e);
            }
        }
        return properties;
    }

}
