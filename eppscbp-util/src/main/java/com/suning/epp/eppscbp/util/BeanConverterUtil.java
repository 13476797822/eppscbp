/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: BeanConverterUtil.java
 * Author:   韩顶彪
 * Date:     2014-8-6 上午09:28:18
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.util;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * Bean转换工具类<br>
 * 〈功能详细描述〉
 * 
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BeanConverterUtil {

    /**
     * Logging mechanism
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanConverterUtil.class);

    private BeanConverterUtil() {

    }

    /**
     * json转成javabean
     * 
     * @param clazz
     * @param data
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws Exception
     */
    public static <T> T toJavaBeanByJSON(Class<T> clazz, Map<String, Object> data) {
        String tmp = JSON.toJSONString(data);
        return JSON.parseObject(tmp, clazz);
    }

    /**
     * 
     * 功能描述: <br>
     * 〈 json转成javabean Map<String, String>〉
     *
     * @param clazz
     * @param data
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <T> T toJavaBeanByJSONString(Class<T> clazz, Map<String, String> data) {
        String tmp = JSON.toJSONString(data);
        return JSON.parseObject(tmp, clazz);
    }

    /**
     * javabean转成json
     * 
     * @param <T>
     * @param clazz
     * @param data
     * @return
     */
    public static String toJsonByJavaBean(Object data) {
        return JSON.toJSONString(data);
    }

    /**
     * 
     * 功能描述: javabean转成map<br>
     * 〈功能详细描述〉
     *
     * @param obj
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            LOGGER.info("javabean 转换 map 错误");
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
        }
        return params;
    }
}
