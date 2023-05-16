package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.MerchantAccountCharacterEnum;
import com.suning.ftmc.util.hr.BackHideBitRule;
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
public class MerchantInfoQueryResDto {

    /**
     * 收款方商户名称
     */

    private String payeeMerchantName;
    /**
     * 收款方商户编号
     */

    private String payeeMerchantCode;

    /**
     * 收款币种
     */

    private String currency;
    /**
     * 收款账号
     */
    private String payeeAccount;
    /**
     * 收款人常驻国
     */
    private String payeeCountry;

    /**
     * 收款户名
     */

    private String payeeName;
    /**
     * 收款开户行
     */
    private String payeeBank;

    /**
     * 收款开户行地址
     */
    private String payeeBankAdd;

    /**
     * 收款开户行swift code
     */
    private String payeeBankSwiftCode;
    /**
     * 收款人地址
     */
    private String payeeAddress;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 账号性质
     */
    private String accountCharacter;
    /**
     * 手机号
     */
    private String mobilePhone;

    /**
     * 是否收汇 COLLECTION_TYPE
     * 
     * @return
     */
    private String collectionType;

    public String getPayeeMerchantName() {
        return payeeMerchantName;
    }

    public void setPayeeMerchantName(String payeeMerchantName) {
        this.payeeMerchantName = payeeMerchantName;
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getPayeeCountry() {
        return payeeCountry;
    }

    public void setPayeeCountry(String payeeCountry) {
        this.payeeCountry = payeeCountry;
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

    public String getAccountCharacterName() {
        return MerchantAccountCharacterEnum.getDescriptionFromCode(accountCharacter);
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    @Override
    public String toString() {
        return "MerchantInfoQueryResDto [payeeMerchantName=" + BackHideBitRule.nameHideForCN(payeeMerchantName) + ", payeeMerchantCode=" + payeeMerchantCode + ", currency=" + currency + ", payeeAccount=" + payeeAccount + ", payeeCountry=" + payeeCountry + ", payeeName=" + payeeName + ", payeeBank=" + payeeBank + ", payeeBankAdd=" + payeeBankAdd + ", payeeBankSwiftCode=" + payeeBankSwiftCode + ", payeeAddress=" + payeeAddress + ", bizType=" + bizType + ", accountCharacter=" + accountCharacter + ", mobilePhone=" + FrontHideBitRule.mobileNumberHide(mobilePhone) + ", collectionType=" + collectionType + "]";
    }

}
