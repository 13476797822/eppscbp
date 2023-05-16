package com.suning.epp.eppscbp.dto.req;

import com.suning.ftmc.util.hr.FrontHideBitRule;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈跨境付批量查询商户请求参数〉
 *
 * @author 17060921
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MerchantInfoQueryDto {

    // 会员户头号
    private String payerAccount;

    // 付款方商户号
    private String payerMerchantCode;

    // 收款方编号
    private String payeeMerchantCode;

    // 收款方银行账号
    private String payeeAccount;

    // 收款币种
    private String currency;

    // 收款方商户名称
    private String payeeMerchantName;

    // 收款户名
    private String payeeName;
    // 收款开户行
    private String payeeBank;
    // 收款开户行地址
    private String payeeBankAdd;
    // 收款人常驻国
    private String payeeCountry;
    // 收款开户行swift code
    private String payeeBankSwiftCode;
    // 收款人地址
    private String payeeAddress;
    // 业务类型
    private String bizType;
    // 账号类型
    private String accountCharacter;
    // 操作类型
    private String operationType;
    // 平台标识
    private String platformCode;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 当前页
     */
    private String currentPage;

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAcount) {
        this.payerAccount = payerAcount;
    }

    public String getPayerMerchantCode() {
        return payerMerchantCode;
    }

    public void setPayerMerchantCode(String payerMerchantCode) {
        this.payerMerchantCode = payerMerchantCode;
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayeeMerchantName() {
        return payeeMerchantName;
    }

    public void setPayeeMerchantName(String payeeMerchantName) {
        this.payeeMerchantName = payeeMerchantName;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeBank() {
        return payeeBank;
    }

    public void setPayeeBank(String payeeBank) {
        this.payeeBank = payeeBank;
    }

    public String getPayeeBankAdd() {
        return payeeBankAdd;
    }

    public void setPayeeBankAdd(String payeeBankAdd) {
        this.payeeBankAdd = payeeBankAdd;
    }

    public String getPayeeCountry() {
        return payeeCountry;
    }

    public void setPayeeCountry(String payeeCountry) {
        this.payeeCountry = payeeCountry;
    }

    public String getPayeeBankSwiftCode() {
        return payeeBankSwiftCode;
    }

    public void setPayeeBankSwiftCode(String payeeBankSwiftCode) {
        this.payeeBankSwiftCode = payeeBankSwiftCode;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * @return the bizType
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * @param bizType the bizType to set
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getAccountCharacter() {
        return accountCharacter;
    }

    public void setAccountCharacter(String accountCharacter) {
        this.accountCharacter = accountCharacter;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    @Override
    public String toString() {
        return "MerchantInfoQueryDto [payerAccount=" + payerAccount + ", payerMerchantCode=" + payerMerchantCode + ", payeeMerchantCode=" + payeeMerchantCode + ", payeeAccount=" + payeeAccount + ", currency=" + currency + ", payeeMerchantName=" + payeeMerchantName + ", payeeName=" + payeeName + ", payeeBank=" + payeeBank + ", payeeBankAdd=" + payeeBankAdd + ", payeeCountry=" + payeeCountry + ", payeeBankSwiftCode=" + payeeBankSwiftCode + ", payeeAddress=" + payeeAddress + ", bizType=" + bizType + ", operationType=" + operationType + ", accountCharacter=" + accountCharacter + ", mobilePhone="
                + FrontHideBitRule.mobileNumberHide(mobilePhone) + ", currentPage=" + currentPage + ", platformCode=" + platformCode + "]";
    }
}
