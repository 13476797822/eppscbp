/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

/**
 * @author 17080704
 *
 */
public enum QualificationStatusEnum {
    REGISTER_01("01", "登记"),
    QUREYING_02("02", "查询中"), 
    PASS_03("03", "通过"),
    NO_PASS_04("04", "未通过"), 
    QUERY_EXCEPTION_05("05", "查询异常"),
    NO_NEED_REGIST_06("06", "无需认证");

    private String code;

    private String description;

    private QualificationStatusEnum(String code, String description) {
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

    /**
     * 功能描述: <br>
     * 〈根据编码取得对应的描述〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDescriptionFromCode(String code) {
        if (code == null || code == "") {
            return "";
        }
        for (QualificationStatusEnum type : QualificationStatusEnum.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }
}
