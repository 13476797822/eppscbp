package com.suning.epp.eppscbp.common.enums;

/**
 * 〈一句话功能简述〉<br>
 * 〈拒付单审核〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum RejectAuditStatus {

    ING("100", "审核中"),

    SUCESS("200", "审核通过"),

    FAIL("300", "审核不通过");

    private String code;

    private String description;

    RejectAuditStatus(String code, String description) {
        this.code = code;
        this.description = description;
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

    public static String getDescriptionFromCode(String code) {
        if (null == code) {
            return "";
        }

        for (RejectAuditStatus type : RejectAuditStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }

        return "";

    }

}
