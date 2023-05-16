/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

/**
 * 购汇方式枚举
 * 
 * @author 17010395
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * 
 */
public enum ExchangeType {

    RMB_EXCHANGE("01", "人民币购汇"), 
    FOREIGN_EXCHANGE("02", "外币购汇");

    private String code;

    private String description;

    /**
     * @param code
     * @param description
     */
    ExchangeType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 功能描述: <br>
     * 〈获取类型描述〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDescriptionFromCode(String code) {
        if (code == null) {
            return "";
        }
        for (ExchangeType type : ExchangeType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }
}
