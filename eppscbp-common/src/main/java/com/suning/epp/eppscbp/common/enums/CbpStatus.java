/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈跨境付订单状态〉<br>
 * 
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum CbpStatus {
    PREPARE("P", "待支付"),

    ING("I", "支付中"),

    FAIL("F", "支付失败"),

    CLOSED("C", "订单关闭"),

    SUCESS("S", "支付成功"),

    INIT("00", "初始"),

    PRE_GH("10", "购汇中"),

    SUCESS_GH("11", "购汇成功"),

    PRE_FH("20", "付汇中"),

    SUCESS_FH("21", "付汇成功"),

    SUCESS_TH("31", "已退汇");

    private String code;

    private String description;

    CbpStatus(String code, String description) {
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

        for (CbpStatus type : CbpStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }

        return "";

    }

    /**
     * 功能描述: 根据描述获取状态编码<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCodeFromDescription(String des) {
        if (des == null) {
            return "";
        }

        for (CbpStatus type : CbpStatus.values()) {
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
        for (CbpStatus type : CbpStatus.values()) {

            if (!INIT.code.equals(type.getCode())) {
                resultList.add(type.getDescription());
            }

        }
        return resultList;

    }

    /**
     * 功能描述: 获取所有支付状态描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getAllPayDescription() {

        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        resultList.add(PREPARE.getDescription());
        resultList.add(FAIL.getDescription());
        resultList.add(CLOSED.getDescription());
        resultList.add(SUCESS.getDescription());

        return resultList;

    }

    /**
     * 功能描述: 获取所有购付汇状态描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getAllRemitDescription() {

        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        resultList.add(INIT.getDescription());
        resultList.add(PRE_GH.getDescription());
        resultList.add(SUCESS_GH.getDescription());
        resultList.add(PRE_FH.getDescription());
        resultList.add(SUCESS_FH.getDescription());
        resultList.add(SUCESS_TH.getDescription());

        return resultList;

    }

}
