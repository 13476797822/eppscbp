/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * @author 17080704
 *
 */
public enum CtCollAndSettleOrderStatus {

    ACCEPT_SUCCESS("01", "受理成功"),

    INIT("10", "挂起"),

    SUCCESS_NOTIC("11", "匹配来账成功"),

    PRE_SH("20", "收汇中"),

    SUCESS_SH("21", "收汇成功"),

    PRE_FH("30", "结汇中"),

    SUCESS_FH("31", "收结汇成功"),

    SUCESS_TH("99", "关闭");

    private String code;

    private String description;

    CtCollAndSettleOrderStatus(String code, String description) {
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

    public static String getDescriptionFromCode(String code) {
        if (code == null) {
            return "";
        }

        for (CtCollAndSettleOrderStatus type : CtCollAndSettleOrderStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }

        return code;

    }

    /**
     * 
     * 功能描述: <br>
     * 〈根据描述获取代码〉
     *
     * @param des
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCodeFromDescription(String des) {
        if (des == null) {
            return "";
        }
        for (CtCollAndSettleOrderStatus type : CtCollAndSettleOrderStatus.values()) {
            if (type.description.equals(des)) {
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
        for (CtCollAndSettleOrderStatus type : CtCollAndSettleOrderStatus.values()) {
            resultList.add(type.getDescription());
        }
        return resultList;

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
    public static List<String> getAllCollDescription() {

        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        for (CtCollAndSettleOrderStatus type : CtCollAndSettleOrderStatus.values()) {
            if (!type.getCode().equals(CtCollAndSettleOrderStatus.PRE_FH.getCode()) && !type.getCode().equals(CtCollAndSettleOrderStatus.SUCESS_FH.getCode())) {
                resultList.add(type.getDescription());
            }
        }
        return resultList;

    }

}
