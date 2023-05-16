/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: CBBConstants.java
 * Author:   14070519
 * Date:     2014-11-7 上午9:15:53
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.constant;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EPPSCBPConstants {
    // 空字符串
    public static final String EMPTY_STRING = "";

    public static final String OBJECT = "object";

    // 系统标示
    public static final String PLATFORMCODE = "cbp";    

    /** criteria */
    public static final String CRITERIA = "criteria";

    public static final String ACCSRCCOD_RMB = "01";

    public static final String ACCSRCCOD_EX = "02";
    
    //类型:保证金
    public static final String ACCSRCCOD_03 = "03";

    // 人民币常量
    public static final String CUR_CODE_CNY = "CNY";
    // 余额常量
    public static final String BALANCE = "balance";
    // 外币余额常量
    public static final String EX_BALANCE = "exBalance";
    // 会员aes密钥
    public static final String KEY = "key";
    public static final String PAYMENT_KEY = "paymentPasswordAesSecretKey";

    // 文件模板路径
    public static final String TEMPLATE_PATH = "/WEB-INF/template/file/";

    // 货物贸易 监管文件模板
    public static final String TRADE_SUPERVISE = "trade_supervise.xlsx";

    // 收结汇 监管文件模板
    public static final String ECS_SUPERVISE = "ecs_supervise.xls";

    // 批量新增商户文件模板
    public static final String BATCH_MERCHANT = "batch_merchant.xlsx";
    // 批量新增境内商户文件模板
    public static final String BATCH_DOMESTIC_MERCHANT = "batch_domestic_merchant.xlsx";

    // JSON返回標示
    public static final String REQ_FLG = "success";
    // JSON返回成功標示
    public static final String REQ_FLG_SUCESS = "s";
    // JSON返回成功標示
    public static final String REQ_FLG_SUCESS_QT = "sqt";
    // JSON返回失敗標示
    public static final String REQ_FLG_FAIL = "f";
    // JSON返回异常標示
    public static final String REQ_FLG_EX = "n";
    // JSON返回支付失败標示
    public static final String REQ_FLG_PAY_FAIL = "pf";
    // JSON返回支付异常標示
    public static final String REQ_FLG_PAY_EXCIPTION = "o";
    // JSON返回异常標示
    public static final String SYSTEM_ERROR = "系统异常，请稍后再试";

    // 留学缴费的业务细类-学费
    public static final String BIZ_DETAIL_TYPE = "0031";

    // 接口返回成功
    public static final String SUCCESS_CODE = "0000";
    // 接口返回异常
    public static final String ERROR_CODE = "9999";
    //根据指定条件查无数据
    public static final String OCA_NO_RESULT = "0004";
    // Epp会员编号
    public static final String PAYERACCOUNT = "payerAccount";
    // 操作员
    public static final String OPERATORCODE = "operatorCode";
    // 收款方信息
    public static final String MESSAGE = "message";
    // 产品类型标识
    public static final String PRODUCT_TYPE = "productType";
    // 帐号类型
    public static final String ACCOUNT_CHARACTER = "accountCharacter";
    // 收件人邮箱地址
    public static final String EMAIL_ADDRE = "recipientEmailAddre"	;
    /**
     * 调会员系统参数
     */
    // 流水号
    public static final String SERIAL_NO = "serialNo";
    // 系统名称
    public static final String SYSNAME = "sysName";
    // Epp会员编号
    public static final String USE_NO = "userNo";
    // 调用系统名称
    public static final String SYSNAME_CODE = "EPPSCBP";
    // 支付密码
    public static final String PAYMENT_PASSWORD = "paymentPassword";

    /**
     * 其他接口返回值
     */
    // 指定交易单不存在
    public static final String ORDER_NOT_EXIST = "3004";
    // 指定条件未查询到数据
    public static final String NO_RESULT = "3008";

    /**
     * 公共返回常量
     */
    public static final String RESPONSE_CODE = "responseCode";
    public static final String RESPONSE_MSG = "responseMsg";
    public static final String PAGE = "page";
    public static final String CONTEXT = "context";

    /**
     * 区别产品类型
     */
    public static final String ECS = "cbp";
    public static final String CCP = "ccp";

    // 页面交互使用
    public static final String BUSINESS_NO = "businessNo";
    public static final String FILE_ADDRESS = "fileAddress";
    public static final String RESULT_LIST = "resultList";
    public static final String AMT_RESULT_LIST = "amtResultList";
    public static final String RESULT = "result";
    public static final String BIZ_TYPE = "bizType";
    public static final String STATUS = "status";
    public static final String PAY_STATUS = "payStatus";
    public static final String REMIT_STATUS = "remitStatus";
    public static final String SUP_STATUS = "supStatus";
    public static final String ALL = "全部";
    public static final String CUR_TYPE = "curType";
    public static final String CUR_TYPE_ADD = "curTypeAdd";
    public static final String ERROR_MSG_CODE = "errMessage";
    public static final String PAY_RESULT = "payResult";
    public static final String MEG = "meg";
    public static final String CUR_NAME = "curName";
    public static final String NO_RESULT_MSG = "没有找到记录请重试";
    public static final String ERROR_QUERY_MSG = "查询出错请重试";
    public static final String BIZ_TYPE_ADD = "bizTypeAdd";
    public static final String COLL_SETTLE_TYPE = "collSettType";
    public static final String COLL_TYPE = "collType";
    public static final String MERCHANT_IN_OUT = "merchantInOut";
    public static final String CHARGE_BIZ_TYPE = "chargeBizType";
    public static final String AMOUNT = "amount";
    public static final String NUMBER = "number";
    public static final String STORE_SITE = "site";
    public static final String STORE_PLATFORM = "storePlatform";
    public static final String ORDER_TYPE = "orderType";
    public static final String CURRENCY = "currency";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String BATCH_PAY_FLAG = "batchPayFlag";
    public static final String PREPAY_AMOUNT = "prePayAmount";

    public static final String DETAIL_FLAG = "detailFlag";
    
    public static final String APPLICATION_IS_ZERO = "跨境电商模式下收汇用途为0(支付)时,原订单编号不能填写";

    public static final String DATA_TYPE = "dataType";
    public static final String DATA_TYPE_ADD = "dataTypeAdd";
    public static final String LOGISTICS_TYPE = "logisticsType";
    
    // 聚合交易数据查询（2021-03-19 晚上7点上线，上线前的数据无法查询）
    public static final String QUERY_CONDITION = "2021-03-19";
    // 聚合交易数据查询 提示结果
    public static final String QUERY_ERROR_MSG_1="订单更新时间在2021-03-19(含)之前的交易，请到原对账中心-交易记录进行查询";
    public static final String QUERY_ERROR_MSG_2="批付姓名、批付账号、金额只支持一个月内时间查询，请重新选择查询条件";

    // 前置订单收单文件查询结果提示
    public static final String PRE_QUERY_ERROR_MSG="上传时间范围只支持三个月, 请确认重新选择查询条件";
    public static final String PRE_QUERY_ERROR_MSG_2="查询条件的上传时间范围不能为空";
   
    // 邮件结果
    public static final String SEND_EMAIL_SUCC="sendEmailSucc";
    public static final String SEND_EMAIL_FAIL="sendEmailFail";
    
    // 操作员账号-主账号
    public static final String OPERATOR_CODE_MAIN="0";

    //批付信息解析失败
    public static final String BPAYMENT_INFO_PARSE_FAIL="6008";

    private EPPSCBPConstants() {

    }
}
