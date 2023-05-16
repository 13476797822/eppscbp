package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

public enum OcaSaleOrderStatusEnums {
	PAY_SUCESS(100, "支付成功"),

    PAY_FAIL(300, "支付失败"),

    PAY_EXCEPTION(301, "支付处理中");
	
	private Integer code;

    private String description;

    OcaSaleOrderStatusEnums(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
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
    public static String getDescriptionFromCode(Integer code) {
        if (code == null) {
            return "";
        }

        for (OcaSaleOrderStatusEnums type : OcaSaleOrderStatusEnums.values()) {
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
        resultList.add(EPPSCBPConstants.ALL);
        for (CbpProductType type : CbpProductType.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }

    /**
     * 功能描述: 获取所有单笔下单产品描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getSingleDescription() {

        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        for (CbpProductType type : CbpProductType.values()) {
            if (!CbpProductType.BATCH_GFH.getCode().equals(type.getCode())) {
                resultList.add(type.getDescription());
            }

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
    public static Integer getCodeFromDescription(String des) {
        if (des == null) {
            return -1;
        }

        for (OcaSaleOrderStatusEnums type : OcaSaleOrderStatusEnums.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }

        return -1;

    }
   
}
