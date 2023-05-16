package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

public enum BatchPayFlag {

    PAY_UNFINISHED("1", "批付未完成"),

    PAY_FINISHED("2", "批付完成");

    private String code;

    private String description;

    BatchPayFlag(String code, String description) {
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
        if (code == null) {
            return "";
        }

        for (BatchPayFlag type : BatchPayFlag.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }

        return code;

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
        resultList.add(EPPSCBPConstants.ALL);
        for (BatchPayFlag type : BatchPayFlag.values()) {
            resultList.add(type.getDescription());
        }
        return resultList;

    }

    /**
     * 功能描述: 根据描述获取状态编码<br>
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

        for (BatchPayFlag type : BatchPayFlag.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }

        return "";

    }

}
