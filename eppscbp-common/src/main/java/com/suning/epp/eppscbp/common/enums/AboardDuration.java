/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: CustomType.java
 * Author:   17033387
 * Date:     2018年4月17日 上午11:09:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.enums;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum AboardDuration {
    //修改时需同时修改EnumCodes中的ABOARDDURATION
    PERSON("01", "1年以内"),

    ORG("02", "1年以上");

    private String code;
    private String description;

    /**
     * @param code
     * @param description
     */
    private AboardDuration(String code, String description) {
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
}
