package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺币种
 *
 * @author 88412423
 * @date 2019-05-29
 */
public enum StoreCurEnum {
    USA_SITE("USD", "美元"),
    EUR_SITE("EUR", "欧元"),
    GBP_SITE("GBP", "英镑"),
    JPY_SITE("JPY", "日元"),
    CNH_SITE("CNH", "人民币"),
    AUD_SITE("AUD", "澳元"),
    HKD_SITE("HKD", "港币"),
    CAD_SITE("CAD", "加元"),
    TWD_SITE("TWD", "台币"),
    SGD_SITE("SGD", "新加坡元"),
    NZD_SITE("NZD", "新西兰元");


    private String code;

    private String description;

    StoreCurEnum(String code, String description) {
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
        for (StoreCurEnum type : StoreCurEnum.values()) {
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
     * @param des
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCodeFromDescription(String des) {
        if (des == null) {
            return "";
        }

        for (StoreCurEnum type : StoreCurEnum.values()) {
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
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getAllDescription() {

        List<String> resultList = new ArrayList<String>();
        for (StoreCurEnum type : StoreCurEnum.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }
}
