package com.suning.epp.eppscbp.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;

import com.suning.epp.eppscbp.common.enums.EnumCodes;
import com.suning.epp.eppscbp.common.validator.Option;
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
public class UploadMerchantFileCheck {

    /**
     * 收款方商户名称
     */
    @NotBlank(message = "名称不能为空", groups = { Default.class })
    @Size(max = 100, message = "名称长度不能超过100位", groups = { Default.class })
    private String payeeMerchantName;

    /**
     * 币种
     */
    @NotBlank(message = "结算币种不能为空", groups = { Default.class })
    @Option(value = EnumCodes.CUR_TYPE, message = "未知的结算币种", groups = { Default.class })
    private String currency;
    /**
     * 收款账号
     */
    @NotBlank(message = "银行账号不能为空", groups = { Default.class })
    @Pattern(regexp = "^\\S{1,35}$", message = "银行账号长度不能超过35位", groups = { Default.class })
    private String payeeAccount;
    /**
     * 收款户名
     */
    @NotBlank(message = "户名不能为空", groups = { Default.class })
    @Size(max = 120, message = "户名长度不能超过120位", groups = { Default.class })
    private String payeeName;

    /**
     * 收款开户行
     */
    @NotBlank(message = "收款开户行不能为空", groups = { Default.class })
    @Size(max = 138, message = "收款开户行长度不能超过138位", groups = { Default.class })
    private String payeeBank;
    /**
     * 收款开户行地址
     */
    @NotBlank(message = "开户行地址不能为空", groups = { Default.class })
    @Size(max = 140, message = "开户行地址长度不能超过140位", groups = { Default.class })
    private String payeeBankAdd;
    /**
     * 收款人常驻国
     */
    @NotBlank(message = "对方常驻国不能为空", groups = { Default.class })
    @Option(value = EnumCodes.COUNTRY_CODE, message = "未知的常驻国家（地区）名称及代码", groups = { Default.class })
    private String payeeCountry;
    /**
     * 收款开户行swift code
     */
    @NotBlank(message = "开户行swift code不能为空", groups = { Default.class })
    @Size(max = 11, message = "开户行swift code长度不能超过11位", groups = { Default.class })
    private String payeeBankSwiftCode;
    /**
     * 收款人地址
     */
    @NotBlank(message = "地址不能为空", groups = { Default.class })
    @Size(max = 120, message = "地址长度不能超过120位", groups = { Default.class })
    private String payeeAddress;

    /**
     * 业务类型
     */
    @Size(max = 3, message = "业务类型不能超过3位", groups = { Default.class })
    private String bizType;

    /**
     * 账号性质
     */
    @NotBlank(message = "账号性质不能为空", groups = { Default.class })
    @Option(value = EnumCodes.MERCHANT_CHARACTER, message = "未知的账号性质", groups = { Default.class })
    private String accountCharacter;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = { Default.class })
    @Size(min = 11, max = 11, message = "手机号长度必须为11位", groups = { Default.class })
    private String mobilePhone;

    public String getPayeeMerchantName() {
        return payeeMerchantName;
    }

    public void setPayeeMerchantName(String payeeMerchantName) {
        this.payeeMerchantName = payeeMerchantName;
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

    @Override
    public String toString() {
        return "UploadMerchantFileCheck [payeeMerchantName=" + payeeMerchantName + ", currency=" + currency + ", payeeAccount=" + payeeAccount + ", payeeName=" + payeeName + ", payeeBank=" + payeeBank + ", payeeBankAdd=" + payeeBankAdd + ", payeeCountry=" + payeeCountry + ", payeeBankSwiftCode=" + payeeBankSwiftCode + ", payeeAddress=" + payeeAddress + ", bizType=" + bizType + ", accountCharacter=" + accountCharacter + ", mobilePhone=" + FrontHideBitRule.mobileNumberHide(mobilePhone) + "]";
    }

}
