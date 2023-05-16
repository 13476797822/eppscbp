package com.suning.epp.eppscbp.common.enums;
/**
 * 〈订单聚合交易类型〉<br> 
 * 〈功能详细描述〉
 *
 * @author 19044327
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum TransactionType {
	FOREIGN_COLLECTION("001", "外币收汇"),
    FOREIGN_SETTLEMENT("002", "结汇"),
    RMB_COLLECTION("003", "人民币收汇"),
    BATCH_PAY("004", "批付"),
    PURCHASE("005","购汇"),
    FOREIGN_PAYMENT("006", "外币付汇"),
    RMB_PAYMENT("007", "人民币付汇"),
    FOREIGN_REEXCHANGE("008", "外币退汇"),
    RMB_REEXCHANGE("009", "人民币退汇"),
	FOREIGN_REEXCHANGE_REPAY("010", "外币退汇重付"),
    RMB_REEXCHANGE_REPAY("011", "人民币退汇重付");
    
    private String code;
    
    private String description;
    
    TransactionType(String code, String description) {
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
        for (TransactionType type : TransactionType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return "";
    }
    
    
}
