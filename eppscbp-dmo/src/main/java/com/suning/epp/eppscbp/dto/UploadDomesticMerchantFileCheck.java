/**
 * 
 */
package com.suning.epp.eppscbp.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;

import com.suning.epp.eppscbp.common.enums.EnumCodes;
import com.suning.epp.eppscbp.common.validator.Option;
import com.suning.epp.eppscbp.util.validator.First;
import com.suning.ftmc.util.hr.FrontHideBitRule;

/**
 * @author 17080704
 *
 */
public class UploadDomesticMerchantFileCheck {

    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空", groups = { Default.class })
    @Size(max = 50, message = "商户名称长度不能超过50位", groups = { Default.class })
    private String payeeMerchantName;

    /**
     * 商户名称
     */
    @NotBlank(message = "账号性质不能为空", groups = { Default.class })
    @Option(value = EnumCodes.ACCOUNT_CHARACTER, message = "未知的账号性质", groups = { Default.class })
    private String accountCharacter;

    /**
     * 组织机构代码/身份证号码
     */
    @NotBlank(message = "组织机构代码/身份证号码不能为空", groups = { Default.class })
    @Pattern(regexp = "^(\\d{17})([0-9]|X)$", message = "组织机构代码/身份证号码格式有误", groups = { Default.class })
    private String companyOrPersonCode;

    /**
     * 银行开户名
     */
    @NotBlank(message = "银行开户名不能为空", groups = { Default.class })
    @Size(max = 50, message = "银行开户名长度不能超过为50位", groups = { Default.class })
    private String bankAccountName;

    /**
     * 收款账号
     */
    @NotBlank(message = "银行账号不能为空", groups = { Default.class })
    @Pattern(regexp = "^\\S{1,34}$", message = "银行账号长度只能为1到34位", groups = { Default.class })
    private String bankAccountNo;

    /**
     * 收款开户行
     */
    @NotBlank(message = "开户行不能为空", groups = { Default.class })
    @Size(max = 256, message = "开户行长度不能超过256位", groups = { Default.class })
    @Pattern(regexp = "[\u4e00-\u9fa5]+", message = "开户行只能为汉字", groups = { Default.class })
    private String bank;

    /**
     * 开户行编号
     */
    @NotBlank(message = "开户行编号不能为空", groups = { Default.class })
    @Size(max = 16, message = "开户行编号长度不能超过16位", groups = { Default.class })
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "开户行编号为字母、数字", groups = { Default.class })
    private String bankCode;

    /**
     * 开户行省
     */
    @Size(max = 64, message = "开户行省长度不能超过64位", groups = { Default.class })
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]*$", message = "开户行省只能为汉字", groups = { Default.class })
    private String bankProvince;

    /**
     * 开户行市
     */
    @Size(max = 64, message = "开户行市长度不能超过64位", groups = { Default.class })
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]*$", message = "开户行市只能为汉字", groups = { Default.class })
    private String bankCity;

    /**
     * 联行号
     */
    @Size(max = 12, message = "联行号长度不能超过12位", groups = { Default.class })
    private String bankNo;

    /**
     * 住所营业场所名称及代码
     */
    @NotBlank(message = "住所营业场所名称及代码不能为空", groups = { First.class })
    @Option(value = EnumCodes.AREA_CODE, message = "未知的住所营业场所代码", groups = { First.class })
    private String areaCode;

    /**
     * 常驻国家（地区）名称及代码
     */
    @NotBlank(message = "常驻国家（地区）名称及代码不能为空", groups = { First.class })
    @Option(value = EnumCodes.COUNTRY_CODE, message = "未知的常驻国家（地区）代码", groups = { First.class })
    private String countryCode;

    /**
     * 经济类型代码
     */
    @NotBlank(message = "经济类型代码不能为空", groups = { First.class })
    @Option(value = EnumCodes.ATTR_CODE, message = "未知的经济类型代码", groups = { First.class })
    private String attrCode;

    /**
     * 所属行业属性代码
     */
    @NotBlank(message = "所属行业属性代码不能为空", groups = { First.class })
    @Option(value = EnumCodes.INDUSTRY_CODE, message = "未知的所属行业属性代码", groups = { First.class })
    private String industryCode;

    /**
     * 特殊经济区内企业类型
     */
    @NotBlank(message = "特殊经济区内企业类型不能为空", groups = { First.class })
    @Option(value = EnumCodes.TAX_FREE_CODE, message = "未知的特殊经济区内企业类型", groups = { First.class })
    private String taxFreeCode;

    /**
     * 手机号码
     */
    @Size(max = 11, message = "手机号码长度不能超过11位", groups = { Default.class })
    private String mobilePhone;

    /**
     * 业务类型
     */
    @NotBlank(message = "业务类型不能为空", groups = { Default.class })
    @Size(min = 3, max = 3, message = "业务类型长度必须为3位", groups = { Default.class })
    @Option(value = EnumCodes.BIZ_TYPE_GOOD_LOGISTICS, message = "未知的业务类型", groups = { Default.class })
    private String bizType;

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

    public void setBank(String bank) {
        this.bank = bank;
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

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    @Override
    public String toString() {
        return "UploadDomesticMerchantFileCheck [payeeMerchantName=" + payeeMerchantName + ", accountCharacter=" + accountCharacter + ", companyOrPersonCode=" + companyOrPersonCode + ", bankAccountName=" + bankAccountName + ", bankAccountNo=" + FrontHideBitRule.bankCardNoHide(bankAccountNo) + ", bank=" + bank + ", bankCode=" + bankCode + ", bankProvince=" + bankProvince + ", bankCity=" + bankCity + ", bankNo=" + bankNo + ", areaCode=" + areaCode + ", countryCode=" + countryCode + ", attrCode=" + attrCode + ", industryCode=" + industryCode + ", taxFreeCode=" + taxFreeCode + ", mobilePhone="
                + FrontHideBitRule.mobileNumberHide(mobilePhone) + ", bizType=" + bizType + "]";
    }

}
