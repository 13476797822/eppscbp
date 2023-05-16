/*
 * Copyright (C), 2002-2020, 苏宁易购电子商务有限公司
 * Author:   19043771-孙晓龙
 * FileName: DataType
 * Date:     2020/3/2  10:38
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.enums;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据类型
 *
 * @author 19043771
 * @date 2020/2/24 10:38
 */
public enum DataType {
    EXCHANGE_PAYMENT("01","购付汇"),
    COLLECTION_SETTLE("02","收结汇");

    private String code;

    private String description;

    private DataType(String code, String description){
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
     * 功能描述: <br>
     * 〈根据编码取得对应的描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDescriptionFromCode(String code) {
        if (code == null || code == "") {
            return "";
        }
        for (DataType type : DataType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }

    public static String getDescriptionFromCodeForPage(String code) {
        if (code == null) {
            return "";
        }
        if(code == ""){
            return EPPSCBPConstants.ALL;
        }
        for (DataType type : DataType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }

    public static String getCodeFromDescription(String des) {
        if (des == null) {
            return "";
        }
        for (DataType type : DataType.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }
        return "";
    }

    public static List<String> getAllDescriptions() {
        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        for (DataType type : DataType.values()) {
            resultList.add(type.getDescription());
        }
        return resultList;
    }

    public static List<String> getDescriptions() {
        List<String> resultList = new ArrayList<String>();
        for (DataType type : DataType.values()) {
            resultList.add(type.getDescription());
        }
        return resultList;
    }

    public static List<Map<String, String>> getAll() {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("code", "");
        resultMap.put("description", EPPSCBPConstants.ALL);
        resultList.add(resultMap);
        for (DataType type : DataType.values()) {
            Map<String, String> resultMap1 = new HashMap<String, String>();
            resultMap1.put("code", type.getCode());
            resultMap1.put("description", type.getDescription());
            resultList.add(resultMap1);
        }
        return resultList;
    }

}
