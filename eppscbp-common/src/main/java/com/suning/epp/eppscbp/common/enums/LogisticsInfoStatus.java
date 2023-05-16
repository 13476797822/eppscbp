package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

public enum LogisticsInfoStatus {
    NOT_UPLOAD(100, "未上传"),
	
	AUDITING(200, "审核中"),
	
	AUDIT_PASS(300, "审核通过"),

	AUDIT_NOT_PASS(301, "审核不通过");

    private Integer code;

    private String description;

    LogisticsInfoStatus(Integer code, String description) {
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

    public static String getDescriptionFromCode(Integer code) {
        if (code == null) {
            return "";
        }

        for (LogisticsInfoStatus type : LogisticsInfoStatus.values()) {
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
        for (LogisticsInfoStatus type : LogisticsInfoStatus.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }
}
