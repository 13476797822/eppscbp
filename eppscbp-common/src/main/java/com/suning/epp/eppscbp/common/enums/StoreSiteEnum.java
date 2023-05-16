package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺站点
 *
 * @author 88412423
 * @date 2019-05-10
 */
public enum StoreSiteEnum {
    USA_SITE("USD", "美国站"),
    EUR_SITE("EUR", "欧洲站"),
    GBP_SITE("GBP", "英国站"),
    JPY_SITE("JPY", "日本站"),
    CNH_SITE("CNH", "中国站"),
    AUD_SITE("AUD", "澳大利亚站"),
    HKD_SITE("HKD", "香港站"),
    CAD_SITE("CAD", "加拿大站"),
    TWD_SITE("TWD", "台湾站"),
    SGD_SITE("SGD", "新加坡站"),
    NZD_SITE("NZD", "新西兰站");


    private String code;

    private String description;

    StoreSiteEnum(String code, String description) {
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
        for (StoreSiteEnum type : StoreSiteEnum.values()) {
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

        for (StoreSiteEnum type : StoreSiteEnum.values()) {
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
        for (StoreSiteEnum type : StoreSiteEnum.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }
}
