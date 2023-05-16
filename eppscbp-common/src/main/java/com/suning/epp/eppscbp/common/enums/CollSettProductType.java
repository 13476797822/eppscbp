package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum CollSettProductType {

    SINGLE_SJH("FS22", "单笔收结汇"),

    SINGLE_SH("RS20", "单笔人民币收汇");

    private String code;

    private String description;

    CollSettProductType(String code, String description) {
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
     * 功能描述: 根据产品编码获取描述<br>
     * 〈功能详细描述〉
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

        for (CollSettProductType type : CollSettProductType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
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
        for (CollSettProductType type : CollSettProductType.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

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

        for (CollSettProductType type : CollSettProductType.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }

        return "";

    }

}
