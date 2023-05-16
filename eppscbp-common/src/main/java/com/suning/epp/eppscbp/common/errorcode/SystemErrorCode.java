/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: 12075176
 * Author:   昌雷
 * Date:     2014-11-11 下午05:37:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.errorcode;

/**
 * 〈一句话功能简述〉<br>
 * 〈系统间错误码〉 9000-9299
 * 
 * @author 12075176
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SystemErrorCode {

    /**
     * 结算请求参数不合法
     */
    public static final String SETTLE_REQUEST_ERROR = "9000";

    /**
     * 结算请求受理失败
     */
    public static final String SETTLE_REQUEST_FAIL = "9001";

    private SystemErrorCode() {

    }
}
