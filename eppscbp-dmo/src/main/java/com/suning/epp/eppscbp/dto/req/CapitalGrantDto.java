package com.suning.epp.eppscbp.dto.req;

import java.util.List;

/**
 * @author 88412423
 *
 */
public class CapitalGrantDto {

    /**
     * 付款方商户户头号
     */
    private String payerAccount;

    /**
     * 平台标识
     */
    private String platformCode;

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
     * 结算明细文件路径
     */
    private String fileAddress;

    /**
     * 境内收款方编号
     */
    private List<String> payeeMerchantCodes;

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
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

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    @Override
    public String toString() {
        return "CapitalGrantDto{" + "payerAccount='" + payerAccount + '\'' + ", PlatformCode='" + platformCode + '\'' + ", payeeMerchantCode='" + payeeMerchantCode + '\'' + ", accountName='" + accountName + '\'' + ", bankAccount='" + bankAccount + '\'' + ", fileAddress='" + fileAddress + '\'' + '}';
    }
}
