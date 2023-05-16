package com.suning.epp.eppscbp.dto.req;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 结汇付款
 */
public class PreOrderCollAndPaymentApplyReqDto {
    /**
     * 平台标识
     * 跨境分配，3位英文字母api
     */
    private String platformCode;

    /**
     * 平台名称
     * 最多8个字符(8个汉字),只支持汉字/swift字符集
     */
    private String platformName;

    /**
     * 收结汇申请单号
     * 商户填写：70商户号（8位）+7位（商户自行定义），必须保证全局唯一
     */
    private String remitNo;

    /**
     * 业务类型
     * 001货物贸易 002国际物流
     */
    private String bizType;

    /**
     * 待解付资金流水号
     */
    private String payNo;

    /**
     * 来账银行代码
     */
    private String bankCode;

    /**
     * 商户户头号
     */
    @Size(max = 19, message = "商户户头号长度不能超过{max}位")
    private String payerAccount;

    /**
     * 商户户头号
     * 境外付款方商户号  B开头的7位商户编号
     */
    private String payeeMerchantCode;

    /**
     * 境外付款账号
     */
    private String payeeBankCard;

    /**
     * 金额
     */
    private String amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * PC设备ID
     */
    private String pcToken;

    /**
     * 批付收款人列表
     */
    private List<PreOrderBpayeeListDto> bpayeeList;


    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getPayeeBankCard() {
        return payeeBankCard;
    }

    public void setPayeeBankCard(String payeeBankCard) {
        this.payeeBankCard = payeeBankCard;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<PreOrderBpayeeListDto> getBpayeeList() {
        return bpayeeList;
    }

    public void setBpayeeList(List<PreOrderBpayeeListDto> bpayeeList) {
        this.bpayeeList = bpayeeList;
    }

    public String getRemitNo() {
        return remitNo;
    }

    public void setRemitNo(String remitNo) {
        this.remitNo = remitNo;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPcToken() {
        return pcToken;
    }

    public void setPcToken(String pcToken) {
        this.pcToken = pcToken;
    }

    @Override
    public String toString() {
        return "PreOrderCollAndPaymentApplyReqDto{" +
                "platformCode='" + platformCode + '\'' +
                ", platformName='" + platformName + '\'' +
                ", remitNo='" + remitNo + '\'' +
                ", bizType='" + bizType + '\'' +
                ", payNo='" + payNo + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", payerAccount='" + payerAccount + '\'' +
                ", payeeMerchantCode='" + payeeMerchantCode + '\'' +
                ", payeeBankCard='" + payeeBankCard + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", bpayeeList=" + bpayeeList +
                '}';
    }
}
