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

/**
 * 〈批付明细出款复核状态〉<br>
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum BatchPaymentReviewStatus {
    INIT("00", "待复核"),

    REVIEW_SUCESS("10", "复核通过"),

    REVIEW_FAIL("11", "复核不通过");

    private String code;

    private String description;

    BatchPaymentReviewStatus(String code, String description) {
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

        for (BatchPaymentReviewStatus type : BatchPaymentReviewStatus.values()) {
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

        for (BatchPaymentReviewStatus type : BatchPaymentReviewStatus.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }

        return "";

    }

}
