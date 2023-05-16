/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.AccountCharacter;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.DataType;
import com.suning.epp.eppscbp.common.enums.QualificationStatusEnum;
import com.suning.ftmc.util.hr.BackHideBitRule;
import com.suning.ftmc.util.hr.FrontHideBitRule;

/**
 * @author 17080704
 *
 */
public class DomesticMerchantInfoResDto {

    /**
     * 收款方编号
     */
    private String payeeMerchantCode;

    /**
     * 收款方商户名称
     */
    private String payeeMerchantName;

    /**
     * 收款方性质:01-个人,02-企业
     */
    private String accountCharacter;

    /**
     * 企业代码/身份证
     */
    private String companyOrPersonCode;

    /**
     * 银行开户名
     */
    private String bankAccountName;

    /**
     * 银行账号
     */
    private String bankAccountNo;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 开户行编号
     */
    private String bankCode;

    /**
     * 开户行省
     */
    private String bankProvince;

    /**
     * 开户行市
     */
    private String bankCity;

    /**
     * 联行号
     */
    private String bankNo;

    /**
     * 资质认证,01:登记,02:查询中,03:通过,04:未通过,05:查询异常
     */
    private String qualificationStatus;

    /**
     * 住所/营业场所名称及代码
     */
    private String areaCode;

    /**
     * 常驻国家（地区）名称及代码
     */
    private String countryCode;

    /**
     * 经济类型
     */
    private String attrCode;
    /**
     * 投资国别/地区
     */
    private String investmentCountry;

    /**
     * 所属行业属性
     */
    private String industryCode;

    /**
     * 特殊经济区内企业
     */
    private String taxFreeCode;

    /**
     * 手机号码
     */
    private String mobilePhone;

    // 业务类型
    private String bizType;

    /**
     * 数据类型
     */
    private String dataType;

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

    public String getAccountCharacter() {
        return accountCharacter;
    }

    public void setAccountCharacter(String accountCharacter) {
        this.accountCharacter = accountCharacter;
    }

    public String getCompanyOrPersonCode() {
        return companyOrPersonCode;
    }

    public void setCompanyOrPersonCode(String companyOrPersonCode) {
        this.companyOrPersonCode = companyOrPersonCode;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBank() {
        return bank;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(String qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getInvestmentCountry() {
        return investmentCountry;
    }

    public void setInvestmentCountry(String investmentCountry) {
        this.investmentCountry = investmentCountry;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getTaxFreeCode() {
        return taxFreeCode;
    }

    public void setTaxFreeCode(String taxFreeCode) {
        this.taxFreeCode = taxFreeCode;
    }

    public String getAccountCharacterStr() {
        return AccountCharacter.getDescriptionFromCode(this.accountCharacter);
    }

    public String getQualificationStatusStr() {
        return QualificationStatusEnum.getDescriptionFromCode(this.qualificationStatus);
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * @return the bizType
     */
    public String getBizType() {
        return bizType;
    }

    public String getBizTypeStr() {
        return ChargeBizType.getDescriptionFromCode(bizType);
    }

    /**
     * @param bizType the bizType to set
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getDataType() {
        return dataType;
    }

    public String getDataTypeStr() {
        return DataType.getDescriptionFromCode(dataType);
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DomesticMerchantInfoResDto [payeeMerchantCode=" + payeeMerchantCode + ", payeeMerchantName=" + FrontHideBitRule.nameHideForCN(payeeMerchantName) + ", accountCharacter=" + accountCharacter + ", companyOrPersonCode=" + FrontHideBitRule.idCardHide(companyOrPersonCode) + ", bankAccountName=" + FrontHideBitRule.nameHideForCN(bankAccountName) + ", bankAccountNo=" + FrontHideBitRule.bankCardNoHide(bankAccountNo) + ", bank=" + bank + ", bankCode=" + bankCode + ", bankProvince=" + bankProvince + ", bankCity=" + bankCity + ", bankNo=" + bankNo + ", qualificationStatus=" + qualificationStatus + ", areaCode=" + areaCode
                + ", countryCode=" + countryCode + ", attrCode=" + attrCode + ", investmentCountry=" + investmentCountry + ", industryCode=" + industryCode + ", taxFreeCode=" + taxFreeCode + ", mobilePhone=" + BackHideBitRule.mobileNumberHide(mobilePhone) + ", bizType=" + bizType + ", dataType=" + dataType + "]";
    }

}
