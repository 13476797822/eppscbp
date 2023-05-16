/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: BusinessDateSource.java
 * Author:   17010395
 * Date:     2017年8月7日 上午11:40:35
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.enums;

/**
 * 〈业务数据来源〉<br>
 * 〈功能详细描述〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum OcaDataSource {

    API_SOURCE("01", "网关"),

    COUNTER_SOURCE("02", "收银台"),

    EPPSCBP_SOURCE("04", "门户");

    private String code;

    private String description;

    OcaDataSource() {
    }

    /**
     * @param code
     * @param description
     */
    OcaDataSource(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.core.enums.CodeObject#getCode()
     */
    public String getCode() {
        return code;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getDescription() {
        return description;
    }

    /**
     * 功能描述: <br>
     * 〈获取业务数据来源描述〉
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
        for (OcaDataSource type : OcaDataSource.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }

    /**
     * 功能描述: <br>
     * 〈获取业务数据来源〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static OcaDataSource fromCode(String code) {
        for (OcaDataSource type : OcaDataSource.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum const " + OcaDataSource.class + "." + code);
    }

    public boolean codeEquals(String source) {
        return code.equals(source);
    }

}

