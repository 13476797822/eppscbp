/**
 * 
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈跨境付监管信息上传状态〉<br>
 * 
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum CbpSupStatus {

    PREPARE("P", "待上传"),

    UPLOADING("U", "上传中"),

    FAIL("F", "上传失败"),

    SUCESS("S", "上传成功");

    private String code;

    private String description;

    CbpSupStatus(String code, String description) {
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

        for (CbpSupStatus type : CbpSupStatus.values()) {
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

        for (CbpSupStatus type : CbpSupStatus.values()) {
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
        for (CbpSupStatus type : CbpSupStatus.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }

}
