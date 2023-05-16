/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: EventConstant.java
 * Author:   韩顶彪
 * Date:     2014-9-24 上午9:35:35
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.constant;

/**
 * 
 * 公共常量类<br>
 * 〈功能详细描述〉
 * 
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CommonConstant {

    /**
     * 返回码
     */
    public static final String RESPONSE_CODE = "responseCode";

    /**
     * 返回码
     * 
     */
    public static final String RESPONSE_SUCCESS_CODE = "0000";

    /**
     * 返回消息
     */
    public static final String RESPONSE_MESSAGE = "responseMsg";

    /**
     * 返回数据
     */
    public static final String CONTEXT = "context";
    /**
     * 支付中
     * 
     */
    public static final String ORDER_PAY_EXCEPTION = "E001";

    /**
     * 文件后缀
     */
    public static final String XLS = "xls";

    public static final String XLSX = "xlsx";

    /**
     * 最大尝试次数
     * 
     */
    public static final String MAX_RETRY_COUNT = "maxRetryCount";

    /**
     * 校验错误后剩余次数
     * 
     */
    public static final String REMAIN_COUNT = "remainCount";

    /**
     * 会员检验支付密码返回码-7404-支付密码不正确
     * 
     */
    public static final String PAYPASSWORD_ERROR_CODE = "7404";

    /**
     * 会员检验支付密码返回码-7405-密码错误超过最大次数
     * 
     */
    public static final String PAYPASSWORD_TIMES_CODE = "7405";

    /**
     * 系统异常
     * 
     */
    public static final String SYSTEM_ERROR_CODE = "9999";

    /**
     * 商户权限不存在
     * 
     */
    public static final String AUTH_NULL = "3009";
    /**
     * 系统异常错误信息
     * 
     */
    public static final String SYSTEM_ERROR_MES = "系统异常，请稍后再试。";
    /**
     * 支付操作
     */
    public static final String OPERATING_TYPE_PAY = "pay";
    /**
     * 订单关闭
     */
    public static final String OPERATING_TYPE_CLOSE = "close";

    /**
     * 有效期
     */
    public static final Integer MONTH_VALID = 30;

    public static final Integer YEAR_VALID = 365;

    /**
     * 退款类型
     */
    public static final String REFUND = "02";

    /**
     * 跨境电商模式下收汇用途:退款
     */
    public static final String REFUND_APPLICATION = "1";

    /**
     * 是否表示
     */
    public static final String Y = "Y";
    public static final String N = "N";

    /**
     * 企业门户平台标识
     */
    public static final String CBP_CODE = "cbp";

    /**
     * 资金批付平台标识
     */
    public static final String CCP_CODE = "ccp";

    /**
     * 订单前置平台标识
     */
    public static final String PRE_CODE = "pre";
    
    /**
     * 竖线|
     */
    public static final String VERTICAL_LINE = "|";
    
    /**
     * 转义竖线|
     */
    public static final String VERTICAL_LINE_ZY = "\\|";
    
    /**
     * 受理成功
     */
    public static final String ACCEPT_SUCCESS = "受理成功";

    /**
     * 接口返回成功
     */
    public static final String SUCCESS = "接口返回成功";
    
    /**
     * 修改成功
     */
    public static final String MODIFY_SUCCESS = "修改成功";
    
    /**
     * 全额到账费用不能为空
     */
    public static final String AMT_TYPE_NOT_NULL = "全额到账类型不能为空";
    
    /**
     * 结算金额不能为空 
     */
    public static final String RECEIVE_AMT_NOT_NULL = "结算金额不能为空";
    
    /**
     * 监管文件表头名称ORDER_NO  LOGISTCS_COMP_NAME LOGISTCS_WO_NO
     */
    public static final String ORDER_NO = "业务明细单号";
    public static final String LOGISTCS_COMP_NAME = "物流企业名称";
    public static final String LOGISTCS_WO_NO = "物流单号";
    
    /**
     * 获取数据为空，用"--"
     */
    public static final String VALUE_IS_NULL = "--";
    
    /**
     * oca banner背景图片路径
     */
    public static final String BANNER_PATH = "/style/images/oca/banner.jpg";
    
    /**
     *  标识金额正负值
     */
    public static final String ADD_FLAG = "+";
    public static final String SUB_FLAG = "-";
    
    private CommonConstant() {

    }

}
