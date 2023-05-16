/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpBatchPaymentStatus.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.common.enums;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈批付明细出款状态〉<br>
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum CpBatchPaymentStatus {
    INIT("00", "待出款"),

    ACCEPT_SUCESS("10", "受理成功"),

    ACCEPT_FAIL("20", "受理失败"),

    PAYMENT_SUCESS("11", "出款成功"),

    PAYMENT_FAIL("21", "出款失败"),

    REFUND("30", "退票"),

    CLOSE("40","关闭");

    private String code;

    private String description;

    CpBatchPaymentStatus(String code, String description) {
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
     * 功能描述: 根据状态编码获取描述<br>
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

        for (CpBatchPaymentStatus type : CpBatchPaymentStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }

        return "";

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

        for (CpBatchPaymentStatus type : CpBatchPaymentStatus.values()) {
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
        for (CpBatchPaymentStatus type : CpBatchPaymentStatus.values()) {
            resultList.add(type.getDescription());
        }
        return resultList;

    }
}
