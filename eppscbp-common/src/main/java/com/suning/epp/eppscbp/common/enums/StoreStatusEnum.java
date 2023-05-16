package com.suning.epp.eppscbp.common.enums;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺审核状态
 *
 * @author 88412423
 * @date 2019-05-29
 */
public enum StoreStatusEnum {
	STORE_REGISTER("0", "登记"),
    STORE_UNAUDITED("1", "通过"),
    STORE_ADOPT("2", "未审核"),
    STORE_REJECT("3", "驳回");
    


    private String code;

    private String description;

    private StoreStatusEnum(String code, String description) {
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
        for (StoreStatusEnum type : StoreStatusEnum.values()) {
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

        for (StoreStatusEnum type : StoreStatusEnum.values()) {
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
        for (StoreStatusEnum type : StoreStatusEnum.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }
}
