package com.suning.epp.eppscbp.common.enums;

/**
 * 〈一句话功能简述〉<br>
 * 〈拒付申诉校最终申诉结果〉
 *
 * @author 88492276
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum LastStatus {

    JUDGE_ING("100", "裁定中"),

    LAST_STATUS_CANCEL("200", "申诉成功"),

    LAST_STATUS_JUDGE("300", "已裁定拒付");

    private String code;

    private String description;

    LastStatus(String code, String description) {
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

        for (LastStatus type : LastStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }

        return "";

    }

}
