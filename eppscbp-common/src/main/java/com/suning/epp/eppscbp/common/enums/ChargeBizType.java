/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: ChargeProductType.java
 * Author:   16080441
 * Date:     2017年8月1日 上午10:33:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;

/**
 * 〈计费编码类型枚举类〉<br>
 * 〈功能详细描述：计费编码类型枚举类〉
 *
 * @author 16080441
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum ChargeBizType {

    TYPE_GOODS_TRADE("001","货物贸易"),
    TYPE_HOTEL_ACCOMMODATION("002","酒店住宿"),
    TYPE_ABROAD_EDUCATION("003","留学教育"),
    TYPE_AIR_TICKET("004","航空机票"),
	TYPE_LOGISTICS("005","国际物流");

    private String code;
    private String description;

    
    /**
     * 
     * 功能描述: 获取货物贸易、留学教育枚举<br>
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static ChargeBizType[] getChargeBizTypes() {
        return new ChargeBizType[] { TYPE_GOODS_TRADE, TYPE_ABROAD_EDUCATION };
    }

    /**
     * @param code
     * @param description
     */
    private ChargeBizType(String code, String description) {
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
        for (ChargeBizType type : ChargeBizType.values()) {
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
        for (ChargeBizType type : ChargeBizType.values()) {
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
    public static List<String> getAddMerchantDescription() {

        List<String> resultList = new ArrayList<String>();
        for (ChargeBizType type : ChargeBizType.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }

    /**
     * 功能描述: <br>
     * 〈根据描述取得对应的代码〉
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
        for (ChargeBizType type : ChargeBizType.values()) {
            if (type.description.equals(des)) {
                return type.getCode();
            }
        }
        return "";
    }
    
    /**
     * 功能描述: 仅获取货物贸易的描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getDescriptionByGoods() {

        List<String> resultList = new ArrayList<String>();
        for (ChargeBizType type : ChargeBizType.values()) {
            if ("001".equals(type.getCode())) {
                resultList.add(type.getDescription());
            }
        }

        return resultList;

    }
    
    /**
     * 功能描述: 仅获取物流款项的描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getDescriptionByLogistics() {

        List<String> resultList = new ArrayList<String>();
        for (ChargeBizType type : ChargeBizType.values()) {
            if ("005".equals(type.getCode())) {
                resultList.add(type.getDescription());
            }
        }

        return resultList;

    }
    
    /**
     * 功能描述: 仅获取货物贸易、国际物流的描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getGoodAndLogistics() {

        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        for (ChargeBizType type : ChargeBizType.values()) {
            if (type.getCode().equals(ChargeBizType.TYPE_GOODS_TRADE.getCode()) || type.getCode().equals(ChargeBizType.TYPE_LOGISTICS.getCode())) {
                resultList.add(type.getDescription());
            }
        }
        return resultList;

    }
    
    /**
     * 功能描述: 仅获取货物贸易、国际物流的描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getGoodAndLogisticsWithoutAll() {

        List<String> resultList = new ArrayList<String>();
        for (ChargeBizType type : ChargeBizType.values()) {
            if (type.getCode().equals(ChargeBizType.TYPE_GOODS_TRADE.getCode()) || type.getCode().equals(ChargeBizType.TYPE_LOGISTICS.getCode())) {
                resultList.add(type.getDescription());
            }
        }
        return resultList;

    }
    
    /**
     * 功能描述: 仅获取货物贸易、国际物流的描述<br>
     * 〈功能详细描述〉
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<Map<String, String>> getGoodsMapWithoutAll() {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("code", ChargeBizType.TYPE_GOODS_TRADE.getCode());
        resultMap.put("description", ChargeBizType.TYPE_GOODS_TRADE.getDescription());
        resultList.add(resultMap);
        return resultList;
    }

}
