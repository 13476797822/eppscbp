package com.suning.epp.eppscbp.common.enums;
/**
 * 〈聚合订单状态〉<br> 
 * 〈功能详细描述〉
 *
 * @author 19044327
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum AggregateOrderStatus {
	COLLECTION_SUCCESS("001", "收汇成功"),
    SETTLEMENT_SUCCESS("002", "结汇成功"),
    BP_PAYMENT_SUCCESS("003", "出款成功"),
    BP_PAYMENT_FAIL("004", "出款失败"),
    BP_ACCEPT_FAIL("005", "受理失败"),
    BP_REFUND_TICKET("006", "退票"),
    BP_CLOSED("007","关闭"),
    PURCHASE_SUCCESS("008","购汇成功"),
    PAYMENT_ING("009", "付汇中"),
    PAYMENT_SUCCESS("010", "付汇成功"),
    REEXCHANGE_SUCCESS("011", "退汇成功"),
	REFUNDED("012", "已退汇");
    
    private String code;
    
    private String description;
    
    AggregateOrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean codeEquals(String source) {
        return code.equals(source);
    }
    
    public static String getDescriptionFromCode(String code) {
        if (code == null) {
            return "";
        }
        for (AggregateOrderStatus type : AggregateOrderStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }
}
