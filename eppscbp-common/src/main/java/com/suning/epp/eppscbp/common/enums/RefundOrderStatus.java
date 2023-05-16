package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈一句话功能简述〉<br>
 * 〈支付退款单状态〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum RefundOrderStatus {

	EFUND_ING(100, "退款中"),

    REFUND_FAIL(300, "退款失败"),

    REFUND_SUCESS(101, "退款成功");

    private Integer code;

    private String description;

    RefundOrderStatus(Integer code, String description) {
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

        for (RefundOrderStatus type : RefundOrderStatus.values()) {
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
        for (RefundOrderStatus type : RefundOrderStatus.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }

}
