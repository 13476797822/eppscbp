/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

/**
 *
 * @author 17010395
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 *
 *        经常项目： 01货物贸易 02服务贸易 03运输 04旅游 05金融和保险服务 06专有权利使用费和特许费 07咨询服务 08其他服务
 */
public enum TradeMode {
    TRADE_MODE_01("01", "货物贸易"), 
    TRADE_MODE_02("02", "服务贸易"), 
    TRADE_MODE_03("03", "运输"), 
    TRADE_MODE_04("04", "旅游"), 
    TRADE_MODE_05("05", "金融和保险服务"), 
    TRADE_MODE_06("06", "专有权利使用费和特许费"), 
    TRADE_MODE_07("07", "咨询服务"), 
    TRADE_MODE_08("08", "其他服务");

    private String code;

    private String description;

    /**
     * @param code
     * @param description
     */
    TradeMode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 功能描述: <br>
     * 〈代码取得〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getCode() {
        return code;
    }

    /**
     * 功能描述: <br>
     * 〈描述取得〉
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
        for (TradeMode type : TradeMode.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }

    /**
     * 功能描述: <br>
     * 〈根据代码取得对应对象〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static TradeMode getFromCode(String code) {
        for (TradeMode type : TradeMode.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
