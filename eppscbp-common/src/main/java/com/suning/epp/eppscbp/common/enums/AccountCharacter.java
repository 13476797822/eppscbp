/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

/**
 * @author 17080704
 *
 */
public enum AccountCharacter {
    PERSON("01", "个人"),

    COMPANY("02", "企业");

    private String code;
    private String description;

    /**
     * @param code
     * @param description
     */
    private AccountCharacter(String code, String description) {
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
        for (AccountCharacter type : AccountCharacter.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }

    /**
     * 功能描述: 根据描述获取产品编码<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCodeFromDescription(String des) {
        if (des == null) {
            return "";
        }

        for (AccountCharacter type : AccountCharacter.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }

        return "";

    }

}
