/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

/**
 * @author 17080704
 *
 */
public enum BizDetailType {

    TUITION("0031", "学费");

    private String code;
    private String description;

    /**
     * @param code
     * @param description
     */
    private BizDetailType(String code, String description) {
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
     * 〈根据代码取得对应的描述〉
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
        for (BizDetailType type : BizDetailType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return "";
    }

}
