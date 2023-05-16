/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: RsfErrorCode.java
 * Author:   17033387
 * Date:     2017年9月8日 上午9:42:47
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.errorcode;

/**
 * 〈RSF异常代码〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RsfErrorCode {
    
    //私有构造方法
    private RsfErrorCode() {
        
    }
    
    public static final String EXCEED_ACTIVE_LIMIT = "RSF001";
    public static final String EXCEED_ACTIVE_LIMIT_CN = "超出消费方流控阀值:{}";
    public static final String EXCEED_RETRY_LIMIT = "RSF002";
    public static final String EXCEED_RETRY_LIMIT_CN = "超出重试次数，调用失败，请求异常明细：:{}";
    public static final String EXCEED_RETRY_LIMIT_CN_2 = "超出服务方流控阀值:{}";
    public static final String TIMEOUT = "RSF003";  
    public static final String TIMEOUT_CN = "调用超时:{}";
    public static final String OTHER = "RSF004";
    public static final String OTHER_CN = "其他框架异常:{}";
    public static final String UNKNOWN = "RSF005";
    public static final String UNKNOWN_CN = "未知异常:{}";

}
