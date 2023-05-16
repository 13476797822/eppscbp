/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: CustomType.java
 * Author:   17033387
 * Date:     2018年4月17日 上午11:09:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.enums;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum MerchantAuthEnum {
    GOODS_TRADE("进口货物贸易结算", "goodsTrade", "资金跨境"),
    OVERSEAS_PAY("留学缴费", "overseasPay", "资金跨境"),
    SINGLE_QUERY("单笔订单查询", "singleQuery", "资金跨境"),
    BATCH_QUERY("批量订单查询", "batchQuery", "资金跨境"),
    BATCH_PAYMENT_QUERY("批付明细查询", "batchPaymentQuery", "资金跨境"),
    MERCHANT_HANDLE("收款商户管理", "merchantHandle", "资金跨境"),
    RATE_QUERY("汇率查询", "rateQuery", "资金跨境"),
    COLL_AND_SETTLE("收结汇申请", "collAndSettle", "资金跨境"),
    LOGISTICS_SETTLEMENT("国际物流结算", "logisticsSettlement", "资金跨境"),
    BATCH_PAYMENT_REVIEW("批付复核管理", "batchPaymentReview", "资金跨境"),
    CP_STORE_HANDLE("店铺管理", "cpStoreHandle", "收款平台"),
    CP_WITHDRAW_APPLY("提现申请", "cpWithdrawApply", "收款平台"),
    CP_BATCH_PAYMENT("资金批付", "cpBatchPayment", "收款平台"),
    CP_WITHDRAW_DETAIL("提现明细", "cpWithdrawDetail", "收款平台"),
    CP_BATCHPAYMENT_DETAIL("批付明细", "cpBatchPaymentDetail", "收款平台"),
    CP_TRAN_IN_OUT_DETAIL("出入账明细", "cpTranInOutDetail", "收款平台"),
    CP_WITHDRAW_ACCOUNT("提现账号", "cpWithdrawAccount", "收款平台"),
    CP_BATCH_PAYMENT_REVIEW("资金下发复核管理", "cpBatchPaymentReview", "收款平台"),
    TRADE_QUERY_AUTH("交易查询", "tradeQueryAuth", "交易查询");

    private String code;
    private String description;
    private String type;

    /**
     * @param code
     * @param description
     */
    private MerchantAuthEnum(String description, String code, String type) {
        this.code = code;
        this.description = description;
        this.type = type;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @return the description
     */
    public String getType() {
        return type;
    }
    
    public static Map<String, List<Map<String, Object>>> getAuthMap() throws UnsupportedEncodingException{
        Map<String, List<Map<String, Object>>> authMap = new HashMap<String, List<Map<String, Object>>>();
        for(MerchantAuthEnum auth : MerchantAuthEnum.values()) {
            List<Map<String, Object>> typeList = authMap.get(URLEncoder.encode(auth.type,"utf-8"));
            if(typeList == null) {
                typeList = new ArrayList<Map<String, Object>>();
                Map<String, Object> keyValue = new HashMap<String, Object>();
                keyValue.put("code", auth.code);
                keyValue.put("description", URLEncoder.encode(auth.description,"utf-8"));
                typeList.add(keyValue);
                authMap.put(URLEncoder.encode(auth.type,"utf-8"), typeList);
            } else {
                Map<String, Object> keyValue = new HashMap<String, Object>();
                keyValue.put("code", auth.code);
                keyValue.put("description", URLEncoder.encode(auth.description,"utf-8"));
                typeList.add(keyValue);
            }
        }
        return authMap;
    }
}
