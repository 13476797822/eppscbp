/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CTRemittanceDetailStatus.java
 * Author:   17010395
 * Date:     2017年8月5日 下午3:40:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈订单前置文件解析状态〉<br>
 * 〈功能详细描述〉
 *
 * @author 17010395
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum PreOrderFileStatus {
    PARSR_IN(101, "解析中"),
    PARSR_SUCCESS(102, "解析成功"),
    PARSR_FIAL(103, "解析失败");

    private Integer code;

    private String description;

    /**
     * @param code
     * @param description
     */
    PreOrderFileStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 功能描述: <br>
     * 〈交易状态代码取得〉
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
     * 〈交易状态描述取得〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
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
    public static String getDescriptionFromCode(Integer code) {
        if (code == null) {
            return "";
        }

        for (PreOrderFileStatus type : PreOrderFileStatus.values()) {
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
        for (PreOrderFileStatus type : PreOrderFileStatus.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }


    /**
     * 功能描述: <br>
     * 〈根据描述取得对应的代码〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCodeFromDescription(String des) {
        if (des == null) {
            return null;
        }
        for (PreOrderFileStatus type : PreOrderFileStatus.values()) {
            if (type.description.equals(des)) {
                return String.valueOf(type.getCode());
            }
        }
        return "";
    }
}
