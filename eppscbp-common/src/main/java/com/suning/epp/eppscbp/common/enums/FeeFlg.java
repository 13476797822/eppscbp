/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: FeeFlg.java
 * Author:   17033387
 * Date:     2017年9月20日 下午3:35:20
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
public enum FeeFlg {
    BOTH_SIDE("01","共同承担"),
    OUR_SIDE("02","我方承担"),
    OPP_SITE("03","对方承担");
    
    private String code;
    private String description;
    
    /**
     * @param code
     * @param description
     */
    private FeeFlg(String code, String description) {
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
        for (FeeFlg type : FeeFlg.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }
}
