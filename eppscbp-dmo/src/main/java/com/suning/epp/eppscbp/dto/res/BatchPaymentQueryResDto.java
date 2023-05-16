package com.suning.epp.eppscbp.dto.res;

import com.suning.ftmc.util.hr.FrontHideBitRule;

public class BatchPaymentQueryResDto {

    /**
     * 境内商户号
     */
    private String payeeMerchantCode;

    /**
     * 收款方商户名称
     */
    private String payeeMerchantName;

    /**
     * 银行账号
     */
    private String bankAccountNo;

    /**
     * 出款金额
     */
    private String amount;

    /**
     * 出款状态
     */
    private String status;

    /**
     * 出款状态,中文描述
     */
    private String statusStr;

    /**
     * 出款失败原因
     */
    private String errorMessage;

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getPayeeMerchantName() {
        return payeeMerchantName;
    }

    public void setPayeeMerchantName(String payeeMerchantName) {
        this.payeeMerchantName = payeeMerchantName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    @Override
    public String toString() {
        return "BatchPaymentQueryResDto [payeeMerchantCode=" + payeeMerchantCode + ", payeeMerchantName=" + payeeMerchantName + ", bankAccountNo=" + FrontHideBitRule.bankCardNoHide(bankAccountNo) + ", amount=" + amount + ", status=" + status + ", statusStr=" + statusStr + ", errorMessage=" + errorMessage + "]";
    }

}
