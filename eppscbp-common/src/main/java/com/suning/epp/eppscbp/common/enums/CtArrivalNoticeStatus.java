/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈一句话功能简述〉<br>
 * 〈外汇来账明细状态〉
 *
 * @author 17070736
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum CtArrivalNoticeStatus {

    STATUS_WAIT_MATCH_APPLY(103, "待匹配申请"),

    STATUS_MATCH_APPLY_SUCCESS(104, "匹配申请成功");
    
    private Integer code;

    private String description;

    /**
     * @param code
     * @param description
     */
    CtArrivalNoticeStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 功能描述: <br>
     * 〈状态代码取得〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 功能描述: <br>
     * 〈状态描述取得〉
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
     * 〈根据状态代码取得对应的描述〉
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
        for (CtArrivalNoticeStatus type : CtArrivalNoticeStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code.toString();
    }


    /**
     * 功能描述: <br>
     * 〈根据购汇状态代码取得对应的描述〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDescriptionFromCode(String code) {
        if (code == null || "".equals(code)) {
            return "";
        }
        for (CtArrivalNoticeStatus type : CtArrivalNoticeStatus.values()) {
            if (type.code.equals(Integer.valueOf(code))) {
                return type.getDescription();
            }
        }
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
    public static List<String> getAllDescription() {
        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        for (CtArrivalNoticeStatus type : CtArrivalNoticeStatus.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;
    }
    
    /**
     * 功能描述: <br>
     * 〈根据购汇状态代码取得对应的描述〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCodeFromDescription(String description) {
        if (description == null || "".equals(description)) {
            return "";
        }
        for (CtArrivalNoticeStatus type : CtArrivalNoticeStatus.values()) {
            if (type.description.equals(description)) {
                return String.valueOf(type.getCode());
            }
        }
        return "";
    }
}
