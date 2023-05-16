package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈一句话功能简述〉<br>
 * 〈拒付订单状态〉
 *
 * @author 19043747
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum RejectOrderStatus {

    // 待办
    REJECT_ORDER_HANDLE(100, "待处理"),

    REJECT_ORDER_REJECTING(101, "拒付中"),

    REJECT_ORDER_ACCEPT_SUCCESS(102, "已接受拒付"),

    // 待办
    REJECT_ORDER_APPEAL_FAIL(301, "申诉不通过"),

    REJECT_ORDER_APPEAL_SUCCESS(105, "申诉成功"),

    REJECT_ORDER_RULE_SUCCESS(106, "已裁定拒付"),

    REJECT_ORDER_REJECT_CANCEL(107, "拒付撤销");

    private Integer code;

    private String description;

    RejectOrderStatus(Integer code, String description) {
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

        for (RejectOrderStatus type : RejectOrderStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }

        return code.toString();

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

        for (RejectOrderStatus type : RejectOrderStatus.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }

        return -1;

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
        for (RejectOrderStatus type : RejectOrderStatus.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }

}
