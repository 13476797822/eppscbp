package com.suning.epp.eppscbp.dto.req;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class OrdersAuditRequestDetailDto {

    /**
     * 境内商户号
     */
    @NotBlank(message = "境内商户号不能为空")
    @Length(min = 7, max = 8, message = "境内商户号长度不超过8位")
    private String payeeMerchantCode;

    /**
     * 商户会员户头号
     */
    @NotBlank(message = "出款金额不能为空")
    @Digits(integer = 14, fraction = 2, message = "出款金额整数部分不可超过14位，且需精确到2位小数")
    private String amount;

    /**
     * 附言--订单名称
     */
    @Length(min = 1, max = 254, message = "附言长度不超过254位")
    private String orderName;

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        return "OrdersAuditRequestDetailDto [payeeMerchantCode=" + payeeMerchantCode + ", amount=" + amount + ", orderName=" + orderName + "]";
    }

}
