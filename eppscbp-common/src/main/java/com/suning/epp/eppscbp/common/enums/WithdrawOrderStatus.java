/*
 * Copyright (C), 2002-2019, 苏宁易购电子商务有限公司
 * FileName: WithdrawOrderStatus.java
 * Author:   17033387
 * Date:     2019年5月17日 上午9:48:48
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
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum WithdrawOrderStatus {
    ACCEPTED(0, "受理成功"), CONFIRMED(1, "交易确认"), TRANSOUT_COMPLETED(2, "出账完成"), RECEIPT_CREATED(3, "收结汇中"), RECEIPT_SUCCEED(4, "收结汇成功");

    /**
     * 交易状态
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String description;

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

    /**
     * @param status 交易状态
     */
    WithdrawOrderStatus(int status, String description) {
        this.code = status;
        this.description = description;
    }

    /**
     * 功能描述: <br>
     * 〈根据交易状态代码取得对应的描述〉
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
        for (WithdrawOrderStatus type : WithdrawOrderStatus.values()) {
            if (type.code.equals(code)) {
                return type.getDescription();
            }
        }
        return code.toString();
    }

    /**
     * 功能描述: <br>
     * 〈根据交易状态代码取得对应的描述〉
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
        for (WithdrawOrderStatus type : WithdrawOrderStatus.values()) {
            if (type.code.equals(Integer.valueOf(code))) {
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
        for (WithdrawOrderStatus type : WithdrawOrderStatus.values()) {
            if (type.description.equals(des)) {
                return String.valueOf(type.getCode());
            }
        }
        return "";
    }

    /**
     * 功能描述: 获取所有描述<br>
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String> getAllDescription() {

        List<String> resultList = new ArrayList<String>();
        resultList.add(EPPSCBPConstants.ALL);
        for (WithdrawOrderStatus type : WithdrawOrderStatus.values()) {
            resultList.add(type.getDescription());
        }

        return resultList;

    }
}
