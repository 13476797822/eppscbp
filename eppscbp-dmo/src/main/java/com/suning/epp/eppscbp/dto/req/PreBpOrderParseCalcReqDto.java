package com.suning.epp.eppscbp.dto.req;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 结汇付款
 */
public class PreBpOrderParseCalcReqDto {
    /**
     * 商户户头号
     */
    @Size(max = 19, message = "商户户头号长度不能超过{max}位")
    private String payerAccount;

    /**
     * 文件地址,文件模式使用
     */
    private String fileAddress;

    private String bizType;

    /**
     * 可批付金额
     */
    @NotNull(message = "金额不可为空")
    @Digits(integer = 15, fraction = 2, message = "批付金额整数部分不可超过15位，且需精确到2位小数")
    private String bpableAmount;


    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getBpableAmount() {
        return bpableAmount;
    }

    public void setBpableAmount(String bpableAmount) {
        this.bpableAmount = bpableAmount;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    @Override
    public String toString() {
        return "PreBpOrderParseCalcReqDto{" +
                "payerAccount='" + payerAccount + '\'' +
                ", fileAddress='" + fileAddress + '\'' +
                ", bizType='" + bizType + '\'' +
                ", bpableAmount='" + bpableAmount + '\'' +
                '}';
    }
}
