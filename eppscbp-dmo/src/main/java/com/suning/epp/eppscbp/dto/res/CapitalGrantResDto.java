/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: CapitalGrantResDto.java
 * Author:   88412423
 * Date:     2019年5月14日
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.dto.res;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 〈接口返回结果类〉<br> 
 * 〈资金批付〉
 *
 * @author 88412423
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CapitalGrantResDto implements Serializable {

    private static final long serialVersionUID = 8395319291719764931L;

    /**
     * 境内收款方编号
     */
    private String payeeMerchantCode;

    /**
     * 开户名
     */
    private String accountName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 出款金额
     */
    private String amount;

    /**
     * 待批付金额
     */
    private double stayAmount;

    public double getStayAmount() {
        return stayAmount;
    }

    public void setStayAmount(double stayAmount) {
        this.stayAmount = stayAmount;
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
