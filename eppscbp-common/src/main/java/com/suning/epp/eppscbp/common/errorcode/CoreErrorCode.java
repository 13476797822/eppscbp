/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: CoreErrorCode.java
 * Author:   12075176
 * Date:     2014-8-11 下午2:28:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 *   xipy       2014-11-5      1.0.0       描述
 */
package com.suning.epp.eppscbp.common.errorcode;

/**
 * 错误码常量<br>
 * 
 * @since 1.0.0
 */
public class CoreErrorCode {

    /**
     * 未知异常
     */
    public static final String UNKNOW_EXCEPTION = "4999";

    /**
     * 日期解析异常
     */
    public static final String DATE_FORMAT_ERROR = "4998";
    public static final String DATE_FORMAT_ERROR_CN = "日期解析异常";

    /**
     * 业务正在处理
     */
    public static final String ONE_BY_ONE_EXCEPTION = "4997";
    public static final String ONE_BY_ONE_EXCEPTION_CN = "业务正在处理";

    /**
     * 数据库查询条件不足
     */
    public static final String DB_CONDITION_ERROR = "4994";
    public static final String DB_CONDITION_ERROR_CN = "数据库查询条件不足";

    /**
     * 数据库查询结果不符合条件
     */
    public static final String DB_RESULE_ERROR = "4993";
    public static final String DB_RESULT_ERROR_CN = "数据库查询结果不符合条件";

    /**
     * 数据库执行SQL异常
     */
    public static final String DB_EXEC_ERROR = "4992";
    public static final String DB_EXEC_ERROR_CN = "数据库执行SQL异常";

    /**
     * 拦截调用到异常Throwable
     */
    public static final String ERROR_SERVICE_INTERCEPTOR_INVOKE = "4991";
    public static final String ERROR_SERVICE_INTERCEPTOR_INVOKE_CN = "拦截调用到异常Throwable";

    /**
     * 状态未定义
     */
    public static final String STATUS_UNDEFINED = "4990";
    public static final String STATUS_UNDEFINED_CN = "状态未定义";

    public static final String ERROR_PARAM_NULL = "4989";
    public static final String ERROR_PARAM_NULL_CN = "数据源为空";

    public static final String ERROR_SERVICE_TEMPLATE_EXECUTE = "4988";
    public static final String ERROR_SERVICE_TEMPLATE_EXECUTE_CN = "服务模板执行出现异常";

    /**
     * 查询结果为空
     **/
    public static final String QUERY_RESULT_EMPTY = "4989";
    public static final String QUERY_RESULT_EMPTY_CN = "查询结果为空";

    /**
     * 编码重复
     */
    public static final String EXIST_CODE_REPEAT = "4990";
    public static final String EXIST_CODE_REPEAT_CN = "商户编码重复";

    /**
     * 新增失败
     */
    public static final String INSERT_RESULT_FAILURE = "4991";
    public static final String INSERT_RESULT_FAILURE_CN = "新增失败";

    /**
     * 操作类型不存在
     */
    public static final String OPT_TYPE_NO_EXIST = "4992";
    public static final String OPT_TYPE_NO_EXIST_CN = "操作类型不存在";

    /**
     * ems系统无返还
     */
    public static final String EMS_QUERY_MERCHANT_FAILUE = "4993";
    public static final String EMS_QUERY_MERCHANT_FAILUE_CN = "调用EMS查询商户信息失败,无返回";

    /**
     * 调用EMS系统出错
     */
    public static final String CALL_EMS_FAILUE = "4994";

    /**
     * 新增失败
     */
    public static final String UPDATE_RESULT_FAILURE = "4995";
    public static final String UPDATE_RESULT_FAILURE_CN = "更新失败";

    /**
     * 非空
     */
    public static final String NO_EMPTY = "4996";
    public static final String NO_EMPTY_CN = "%s不能为空";

    /**
     * 长度
     */
    public static final String LIMIT_LENGTH = "4997";
    public static final String LIMIT_LENGTH_CN = "%s长度不能超过%d";

    /**
     * 日期间隔
     */
    public static final String DATE_RANGE = "4998";
    public static final String DATE_RANGE_CN = "日期间隔不能超过%d天";

    /**
     * ftp登陆失败
     */
    public static final String FTP_LOGIN_ERROR = "4999";
    public static final String FTP_LOGIN_ERROR_CN = "FTP登陆失败";

    /**
     * ftp文件不存在
     */
    public static final String FTP_NO_FILE = "5000";
    public static final String FTP_NO_FILE_CN = "FTP文件不存在";

    /**
     * CBS系统无返还
     */
    public static final String CBS_CALL_FAILUE = "5001";
    public static final String CBS_CALL_FAILUE_CN = "调用CB失败,无返回";

    /**
     * 调用CBS系统出错
     */
    public static final String CALL_CBS_FAILUE = "5002";

    /**
     * 结算更新失败
     */
    public static final String SETTLE_UPDATE_RESULT_FAILURE = "5003";
    public static final String SETTLE_UPDATE_RESULT_FAILURE_CN = "更新失败";

    /**
     * 结算查询结果为空
     **/
    public static final String SETTLE_QUERY_RESULT_EMPTY = "5004";
    public static final String SETTLE_QUERY_RESULT_EMPTY_CN = "查询结果为空";
    /**
     * 字段校验不正确
     */
    public static final String FIELD_ERROR = "5005";
    public static final String FIELD_ERROR_CN = "%s字段校验不通过";
    public static final String FIELD_ERROR_CN_CHAR = "%s字段不在字符集内";


    /**
     * 删除失败
     */
    public static final String DELETE_RESULT_FAILURE = "5006";
    public static final String DELETE_RESULT_FAILURE_CN = "删除失败";

    /**
     * 商户登记已经发送。请耐心等待
     **/
    public static final String MERCHANTINFO_REGIST_CN = "商户登记已经发送。请耐心等待";

    private CoreErrorCode() {

    }
}
