/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈跨境付产品类型〉<br>
 * 
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum CbpProductType {

    SINGLE_GFH("FS11", "单笔购付汇"),

    SINGLE_FH("FS01", "单笔付汇"),

    SINGLE_RMB("RS01", "单笔跨境人民币"),

    BATCH_GFH("FB11", "批量购付汇");

    private String code;

    private String description;

    CbpProductType(String code, String description) {
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
     * 功能描述: 根据产品编码获取描述<br>
     * 〈功能详细描述〉
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

        for (CbpProductType type : CbpProductType.values()) {
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
        for (CbpProductType type : CbpProductType.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }

    /**
     * 功能描述: 获取所有单笔下单产品描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getSingleDescription() {

        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        for (CbpProductType type : CbpProductType.values()) {
            if (!CbpProductType.BATCH_GFH.getCode().equals(type.getCode())) {
                resultList.add(type.getDescription());
            }

        }

        return resultList;

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
    public static String getCodeFromDescription(String des) {
        if (des == null) {
            return "";
        }

        for (CbpProductType type : CbpProductType.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }

        return "";

    }

    /**
     * 功能描述: 取得相关产品的收单类型<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getAcquireType(String code) {
        if (CbpProductType.SINGLE_GFH.getCode().equals(code) || CbpProductType.BATCH_GFH.getCode().equals(code)) {
            // 10 购汇
            return "10";
        } else {
            // 11 付汇
            return "11";
        }
    }

    /**
     * 功能描述: 取得相关产品的产品编码<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getProductCode(String code) {
        if (CbpProductType.SINGLE_GFH.getCode().equals(code) || CbpProductType.BATCH_GFH.getCode().equals(code)) {
            return "00000000538";
        } else {
            return "01000000540";
        }
    }
}
