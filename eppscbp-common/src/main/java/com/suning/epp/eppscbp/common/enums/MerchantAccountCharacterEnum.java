/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 17080704
 *
 */
public enum MerchantAccountCharacterEnum {

    PAYEE_MERCHANT_CODE("01", "购付汇-境外收款账号"),

    PAYER_MERCHANT_CODE("02", "收结汇-境外付款账号");

    private String code;

    private String description;

    private MerchantAccountCharacterEnum(String code, String description) {
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
        for (MerchantAccountCharacterEnum type : MerchantAccountCharacterEnum.values()) {
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

        for (MerchantAccountCharacterEnum type : MerchantAccountCharacterEnum.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }

        return "";

    }

    /**
     * 功能描述: 获取所有描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getAllDescription() {

        List<String> resultList = new ArrayList<String>();
        for (MerchantAccountCharacterEnum type : MerchantAccountCharacterEnum.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }
}
