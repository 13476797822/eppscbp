/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: AmtType.java
 * Author:   17033387
 * Date:     2017年9月20日 上午11:48:06
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.enums;

/**
 * 〈全额到账标识〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum AmtType {
    TYPE_FULL("01","全额到账"),
    TYPE_NOT_FULL("00","非全额到账");
    
    private String code;
    private String description;
    
    /**
     * @param code
     * @param description
     */
    private AmtType(String code, String description) {
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
     * 〈根据代码取得对应的描述〉
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
        for (AmtType type : AmtType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }
}
