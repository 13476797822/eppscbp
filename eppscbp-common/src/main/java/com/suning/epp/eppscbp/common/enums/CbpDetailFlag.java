/*
 * Copyright (C), 2002-2019, 苏宁易购电子商务有限公司
 * FileName: CbpDetailFlag.java
 * Author:   17033387
 * Date:     2019年7月11日 下午6:00:14
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum CbpDetailFlag {
    UNCHECKED("0", "未校验"),

    PASSED("1", "校验通过"),

    FAIL("2", "部分校验通过"),

    RISK_CHECK_IN("3", "风控审核中"),

    RISK_CHECK_REJECT("4", "风控审核驳回");

    private String code;

    private String description;

    CbpDetailFlag(String code, String description) {
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
    
    public boolean codeEquals(String flag) {
        return code.equals(flag);
    }

    public static String getDescriptionFromCode(String code) {
        if (code == null) {
            return "";
        }

        for (CbpDetailFlag type : CbpDetailFlag.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return "";
    }
    
    public static String getCodeFromDescription(String description) {
        if (description == null) {
            return "";
        }

        for (CbpDetailFlag type : CbpDetailFlag.values()) {
            if (type.description.equals(description)) {
                return type.getCode();
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
        for (CbpDetailFlag type : CbpDetailFlag.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }
}
