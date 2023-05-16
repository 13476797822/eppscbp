/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: ExcelForamatException.java
 * Author:   17033387
 * Date:     2018年9月12日 下午3:05:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.exception;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ExcelForamatException extends Exception{

    /**
     */
    private static final long serialVersionUID = -2964831037675604571L;

    /**
     * 
     */
    public ExcelForamatException() {
        super();
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ExcelForamatException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     */
    public ExcelForamatException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public ExcelForamatException(Throwable arg0) {
        super(arg0);
    }   

}
