package com.suning.epp.eppscbp.common.enums;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺平台
 *
 * @author 88412423
 * @date 2019-05-10
 */
public enum OrderType {

    OUT("0", "出账"),

    IN("1", "入账");

    private String code;

    private String description;

    private OrderType(String code, String description) {
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
        for (OrderType type : OrderType.values()) {
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

        for (OrderType type : OrderType.values()) {
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
        resultList.add(EPPSCBPConstants.ALL);
        for (OrderType type : OrderType.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }
}
