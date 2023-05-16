/*
 * Copyright (C), 2002-2016, 苏宁易购电子商务有限公司
 * FileName: BaseController.java
 * Author:   13073409
 * Date:     2016年11月1日 下午3:46:48
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 14072597
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BaseController {

    /**
     * 
     * 绑定字符串处理方法，处理页面请求参数trim <br>
     * 〈功能详细描述〉
     *
     * @param binder
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @InitBinder
    public void webInitBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    

}
