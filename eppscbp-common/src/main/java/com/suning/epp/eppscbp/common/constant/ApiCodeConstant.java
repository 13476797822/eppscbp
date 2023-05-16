/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: ApiCodeConstant.java
 * Author:   17061545
 * Date:     2018年3月22日 上午10:56:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.constant;

/**
 * 〈Rsf接口标识〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ApiCodeConstant {
    // 账务核心查询余额接口
    public static final String QUERY_BALANCE = "queryBalance";
    // 支付密码验证
    public static final String VALIDATE_PAYMENTPASSWORD = "validatePaymentPassword";
    // 加解密密钥查询
    public static final String QUERY_SECRETKEY = "querySecretKey";
    // 商户权限查询
    public static final String QUERY_MERCHANT_AUTH = "queryMerchantAuth";
    // 单笔申请接口
    public static final String ORDER_APPLY = "apply";
    // 批量购付汇申请接口
    public static final String BATCH_APPLY = "batchApply";
    // 支付接口
    public static final String ORDER_DOORDEROPERATION = "doOrderOperation";
    // 汇率刷新接口
    public static final String ORDER_EX_RATE_REFRESH = "orderExRateRefresh";
    // 商户信息和全额到账费用查询
    public static final String QUERY_AMT = "queryAmt";
    // 跨境付订单查询
    public static final String ORDER_QUERY = "orderQuery";
    // 商户操作接口
    public static final String MERCHANT_MANAGE = "merchantManage";
    // 商户权限接口
    public static final String MERCHANT_AUTH_QUERY = "merchantAuthQuery";
    // 中国银行参考查询
    public static final String REFER_RATE_QUERY = "referRateQuery";
    // 跨境监管文件
    public static final String SUPERVISE_UPLOAD = "superviseUpload";
    // 收结汇申请
    public static final String COLL_SETTLE_APPLY = "collSettleApply";
    // 境内商户操作接口
    public static final String DOME_MERCHANT_MANAGE = "domeMerchantManage";
    // 收结汇订单查询
    public static final String COLL_SETT_ORDER_QUERY = "collSettOrderQuery";
    // 外管汇率查询
    public static final String SAFE_RATE_QUERY = "safeRateQuery";
    // 收结汇监管文件
    public static final String ECS_SUPERVISE_UPLOAD = "EcsSuperviseUpload";
    // 批付接口
    public static final String BATCH_PAYMENT = "batchPayment";
    // 店铺信息初始化
    public static final String STORE_MANAGE = "storeManage";
    // 批量查询店铺信息
    public static final String STORE_MANAGE_QUERY = "storeManageQuery";
    // 更新店铺信息
    public static final String STORE_MANAGE_UPDATE = "updateStore";
    // 新增店铺信息
    public static final String STORE_MANAGE_INSERT = "insertStore";
    // 删除店铺
    public static final String STORE_MANAGE_DELETE = "deleteCpStoreInfo";
    // 查询单个店铺信息
    public static final String DETAIL_CP_STORE_INFO = "detailCpStoreInfo";
    // 查询店铺审核进度
    public static final String STORE_AUDIT_QUERY = "storeAuditQuery";
    // 店铺管理提现
    public static final String STORE_CASH_WITHDRAW = "storeCashWithdraw";
    // 资金批付查询可批付余额
    public static final String CAPITAL_GRANT_BALANCE = "capitalGrantBalance";
    // 查询待批付数据
    public static final String PENDING_PAYMENT_QUERY = "pendingPaymentQuery";
    // 批量导入批付明细
    public static final String IMPORT_BATCH_PAYMENT_DETAILS = "importBatchPaymentDetails";

    // 文件模式批付出款
    public static final String BATCH_PAYMENT_AUDIT = "batchPaymentAudit";

    //oca首页
    public static final String HOME_QUERY = "homeQuery";
    
    //oca销售订单
    public static final String TRADE_RECEIPT_ORDER_QUERY = "tradeReceiptOrderQuery";
    
    //oca拒付单
    public static final String REJECT_ORDER_QUERY = "rejectOrderQuery";
    
    //oca退款单
    public static final String REFUND_ORDER_QUERY = "refundOrderQuery";
    
    //oca物流信息
    public static final String LOGISTICS_INFO = "logisticsInfo";

    //oca支付批量结果查询
    public static final String BATCH_QUERY_CHANNEL = "batchQueryChannel";
    
    // 查询交易信息
    public static final String TRADE_INFO_QUERY = "tradeInfoQuery";
    
    // 查询交易聚合信息-生成pdf
    public static final String  TRADE_INFO_DZHDAPI = "tradeInfoDzhdApi";
    
    // 查询交易聚合信息-生成pdf
    public static final String  QUERY_OPERATOR = "queryOperator";

    // 订单前置
    public static final String PRE_ORDER = "preOrder";
    
    private ApiCodeConstant() {
    }
}
