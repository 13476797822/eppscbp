package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈一句话功能简述〉<br>
 * 〈支付收单交易状态〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum TradeOrderStatus {

    PAY_SUCESS(100, "支付成功"),

    PAY_FAIL(300, "支付失败"),

    PAY_EXCEPTION(301, "支付处理中");

    private Integer code;

    private String description;

    TradeOrderStatus(Integer code, String description) {
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

        for (TradeOrderStatus type : TradeOrderStatus.values()) {
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
        for (TradeOrderStatus type : TradeOrderStatus.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }

}
