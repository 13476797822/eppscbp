/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: AmtType.java
 * Author:   17033387
 * Date:     2017年9月20日 上午11:48:06
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
 * 〈币种名称代码〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum CurType {
    CNY("CNY", "人民币", "元"), HKD("HKD", "港币", "港币"), USD("USD", "美元", "元"), JPY("JPY", "日元", "元"), MOP("MOP", "澳门元", "元"), IDR("IDR", "印度尼西亚卢比", "元"), THB("THB", "泰铢", "元"), TWD("TWD", "新台湾元", "元"), EUR("EUR", "欧元", "元"), GBP("GBP", "英镑", "元"), MYR("MYR", "马来西亚元(林吉特)", "元"), VND("VND", "越南盾", "元"), SGD("SGD", "新加坡元", "元"), CHF("CHF", "瑞士法郎", "元"), SEK("SEK", "瑞典克朗", "元"), DKK("DKK", "丹麦克朗", "元"), NOK("NOK", "挪威克朗", "元"), CAD("CAD", "加拿大元", "元"), AUD("AUD", "澳大利亚元", "元"), KRW("KRW", "韩元", "元"), NZD("NZD", "新西兰元", "元");

    private String code;
    private String description;
    private String unit;

    /**
     * @param code
     * @param description
     */
    private CurType(String code, String description, String unit) {
        this.code = code;
        this.description = description;
        this.unit = unit;
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
     * @return the unit
     */
    public String getUnit() {
        return unit;
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
        for (CurType type : CurType.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }

    /**
     * 功能描述: 获取新增币种,去除人民币<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getAddMerchantDescription() {
        List<String> resultList = new ArrayList<String>();
        for (CurType type : CurType.values()) {
            resultList.add(type.getDescription());
        }
        return resultList;

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
        for (CurType type : CurType.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code;
    }
    
    /**
     * 功能描述: <br>
     * 〈根据代码取得对应的单位〉
     * 
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getUnitFromCode(String code) {
        if (code == null) {
            return "";
        }
        for (CurType type : CurType.values()) {
            if (type.code.equals(code)) {
                return type.getUnit();
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
        for (CurType type : CurType.values()) {
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
    public static List<String> getAllDescriptionWithoutCny() {

        List<String> resultList = new ArrayList<String>();
        for (CurType type : CurType.values()) {
            if (!"CNY".equals(type.getCode())) {
                resultList.add(type.getDescription());
            }

        }

        return resultList;

    }
    
    
    /**
     * 功能描述: 仅获取人民币的描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getDescriptionByCny() {

        List<String> resultList = new ArrayList<String>();
        for (CurType type : CurType.values()) {
            if ("CNY".equals(type.getCode())) {
                resultList.add(type.getDescription());
            }

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
    public static List<String> getAllDescriptionWithoutAll() {

        List<String> resultList = new ArrayList<String>();
        for (CurType type : CurType.values()) {
            resultList.add(type.getDescription());

        }

        return resultList;

    }
}
