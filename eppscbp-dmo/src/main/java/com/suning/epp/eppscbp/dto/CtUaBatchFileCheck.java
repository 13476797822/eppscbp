package com.suning.epp.eppscbp.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;

import com.suning.epp.eppscbp.common.enums.EnumCodes;
import com.suning.epp.eppscbp.common.validator.Option;
import com.suning.epp.eppscbp.util.validator.First;
import com.suning.epp.eppscbp.util.validator.Second;
import com.suning.ftmc.util.hr.BackHideBitRule;
import com.suning.ftmc.util.hr.FrontHideBitRule;

public class CtUaBatchFileCheck {

    /**
     * 业务细类
     */
    @NotBlank(message = "业务细类不能为空", groups = { Default.class })
    @Size(min = 4, max = 4, message = "业务细类长度必须为4", groups = { Default.class })
    @Option(value = EnumCodes.BIZ_DETAIL_TYPE, message = "未知的业务细类", groups = { Default.class })
    private String bizDetailType;

    /**
     * 明细业务单号
     */
    @NotBlank(message = "明细业务单号不能为空", groups = { Default.class })
    @Size(min = 0, max = 50, message = "明细业务单号长度超过50", groups = { Default.class })
    private String tradeNo;

    /**
     * 收款方商户编码
     */
    @NotBlank(message = "收款方商户编码不能为空", groups = { Default.class })
    @Size(min = 7, max = 8, message = "收款方商户编码长度为7或8位", groups = { Default.class })
    private String payeeMerchantCode;

    /**
     * 结算金额
     */
    @NotNull(message = "交易金额不能为空", groups = { Default.class })
    @DecimalMin(value = "0.01", message = "交易金额小于等于0或是非数字", groups = { Default.class })
    @Digits(integer = 15, fraction = 2, message = "交易金额整数部分不超过15位，精确到2位小数", groups = { Default.class })
    private double totalAmt;

    /**
     * 交易创建时间
     */
    @NotBlank(message = "交易创建时间不能为空", groups = { Default.class })
    @Pattern(regexp = "\\d{8} \\d{2}:\\d{2}:\\d{2}", message = "交易创建时间格式错误", groups = { Default.class })
    private String createTime;

    /**
     * 交易支付时间
     */
    @NotBlank(message = "交易支付时间不能为空", groups = { Default.class })
    @Pattern(regexp = "\\d{8} \\d{2}:\\d{2}:\\d{2}", message = "交易支付时间长度必须为19", groups = { Default.class })
    private String payTime;

    /**
     * 客户类型
     */
    @NotBlank(message = "客户类型不能为空", groups = { Default.class })
    @Option(value = EnumCodes.CUSTOM_TYPE, message = "未知的客户类型", groups = { Default.class })
    private String customType;

    /**
     * 个人姓名
     */
    @NotBlank(message = "个人姓名不能为空", groups = First.class)
    @Size(min = 0, max = 20, message = "个人姓名长度不能超过20", groups = First.class)
    private String personName;

    /**
     * 个人证件类型
     */
    @NotBlank(message = "个人证件类型不能为空", groups = First.class)
    @Option(value = EnumCodes.PERSON_ID, message = "个人证件类型只支持1-身份证", groups = First.class)
    private String personIdType;

    /**
     * 个人证件号码
     */
    @NotBlank(message = "个人证件号码不能为空", groups = First.class)
    @Pattern(regexp = "^(\\d{17})([0-9]|X)$", message = "个人证件号码格式不正确", groups = First.class)
    private String personId;

    /**
     * 机构名
     */
    @NotBlank(message = "机构名不能为空", groups = Second.class)
    @Size(min = 0, max = 80, message = "机构名长度不能超过80", groups = Second.class)
    private String orgName;

    /**
     * 机构类型
     */
    @NotBlank(message = "机构类型不能为空", groups = Second.class)
    @Size(min = 2, max = 2, message = "机构类型长度必须为2", groups = Second.class)
    private String orgType;

    /**
     * 机构证件号
     */
    @NotBlank(message = "机构证件号不能为空", groups = Second.class)
    @Size(min = 18, max = 18, message = "机构证件号长度必须为18", groups = Second.class)
    private String orgId;

    /**
     * 留学者计划境外居留时间
     */
    @NotBlank(message = "留学者计划境外居留时间标识不能为空", groups = { Default.class })
    @Option(value = EnumCodes.ABOARD_DURATION, message = "未知的留学者计划境外居留时间类型", groups = { Default.class })
    private String aboardDuration;

    /**
     * 全额到账标识
     */
    @NotBlank(message = "全额到账标识不能为空", groups = { Default.class })
    @Size(min = 2, max = 2, message = "全额到账标识长度必须为2", groups = { Default.class })
    @Option(value = EnumCodes.AMT_TYPE, message = "未知的全额到账标识", groups = { Default.class })
    private String amtType;

    /**
     * 收款人开户行的账户行swift code
     */
    @Size(max = 11, message = "收款人开户行的账户行swift code长度超过11", groups = { Default.class })
    @Pattern(regexp = "^([a-z]|[A-Z]|[0-9]|[/\\?\\(\\)\\.\\,\\:\\-\\'\\+\\{\\}\\]]|[ ]){1,}$|^$", message = "收款人开户行的账户行需要满足SWIFT字符集", groups = { Default.class })
    private String payeeBankSwift;

    /**
     * 交易附言
     */
    @NotBlank(message = "交易附言不能为空", groups = { Default.class })
    @Size(min = 0, max = 140, message = "交易附言长度超过140", groups = { Default.class })
    @Pattern(regexp = "^([a-z]|[A-Z]|[0-9]|[/\\?\\(\\)\\.\\,\\:\\-\\'\\+\\{\\}\\]]|[ ]){1,}$|^$", message = "交易附言只支持英文字母、英文符号和数字", groups = { Default.class })
    private String remark;

    /**
     * 指定购汇方式
     */
    @NotBlank(message = "指定购汇方式不能为空", groups = { Default.class })
    @Size(min = 2, max = 2, message = "指定购汇方式长度必须为2", groups = { Default.class })
    @Option(value = EnumCodes.EXCHANGE_TYPE, message = "未知的指定购汇方式", groups = { Default.class })
    private String exchangeType;

    /**
     * @return the bizDetailType
     */
    public String getBizDetailType() {
        return bizDetailType;
    }

    /**
     * @param bizDetailType the bizDetailType to set
     */
    public void setBizDetailType(String bizDetailType) {
        this.bizDetailType = bizDetailType;
    }

    /**
     * @return the tradeNo
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * @param tradeNo the tradeNo to set
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * @return the payeeMerchantCode
     */
    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    /**
     * @param payeeMerchantCode the payeeMerchantCode to set
     */
    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    /**
     * @return the totalAmt
     */
    public double getTotalAmt() {
        return totalAmt;
    }

    /**
     * @param totalAmt the totalAmt to set
     */
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the payTime
     */
    public String getPayTime() {
        return payTime;
    }

    /**
     * @param payTime the payTime to set
     */
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    /**
     * @return the customType
     */
    public String getCustomType() {
        return customType;
    }

    /**
     * @param customType the customType to set
     */
    public void setCustomType(String customType) {
        this.customType = customType;
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * @return the personIdType
     */
    public String getPersonIdType() {
        return personIdType;
    }

    /**
     * @param personIdType the personIdType to set
     */
    public void setPersonIdType(String personIdType) {
        this.personIdType = personIdType;
    }

    /**
     * @return the personId
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * @param personId the personId to set
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgType
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * @param orgType the orgType to set
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    /**
     * @return the orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @return the aboardDuration
     */
    public String getAboardDuration() {
        return aboardDuration;
    }

    /**
     * @param aboardDuration the aboardDuration to set
     */
    public void setAboardDuration(String aboardDuration) {
        this.aboardDuration = aboardDuration;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the amtType
     */
    public String getAmtType() {
        return amtType;
    }

    /**
     * @param amtType the amtType to set
     */
    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    /**
     * @return the aboardDuration
     */
    public String getPayeeBankSwift() {
        return payeeBankSwift;
    }

    /**
     * @param aboardDuration the aboardDuration to set
     */
    public void setPayeeBankSwift(String payeeBankSwift) {
        this.payeeBankSwift = payeeBankSwift;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the exchangeType
     */
    public String getExchangeType() {
        return exchangeType;
    }

    /**
     * @param exchangeType the exchangeType to set
     */
    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CtUaBatchFileCheck [bizDetailType=" + bizDetailType + ", tradeNo=" + tradeNo + ", payeeMerchantCode=" + payeeMerchantCode + ", totalAmt=" + totalAmt + ", createTime=" + createTime + ", payTime=" + payTime + ", customType=" + customType + ", personName=" + FrontHideBitRule.nameHideForCN(personName) + ", personId=" + BackHideBitRule.idCardHide(personId) + ", personIdType=" + personIdType + ", orgName=" + orgName + ", orgType=" + orgType + ", orgId=" + FrontHideBitRule.idCardHide(orgId) + ", aboardDuration=" + aboardDuration + ", amtType=" + amtType + ", payeeBankSwift="
                + payeeBankSwift + ", remark=" + remark + ", exchangeType=" + exchangeType + "]";
    }
}
