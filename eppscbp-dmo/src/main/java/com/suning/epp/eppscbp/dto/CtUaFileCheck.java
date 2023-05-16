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

public class CtUaFileCheck {

    /**
     * 明细业务单号
     */
    @NotBlank(message = "明细业务单号不能为空", groups = { Default.class })
    @Size(min = 0, max = 50, message = "明细业务单号长度超过50", groups = { Default.class })
    private String tradeNo;

    /**
     * 交易类型
     */
    @NotBlank(message = "交易类型不能为空", groups = { Default.class })
    @Option(value = EnumCodes.TRADE_TYPE, message = "未知的交易类型", groups = { Default.class })
    private String orderType;

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
    @Pattern(regexp = "\\d{8} \\d{2}:\\d{2}:\\d{2}", message = "交易支付时间格式错误", groups = { Default.class })
    private String payTime;

    /**
     * 原明细业务单号
     */
    @Size(min = 0, max = 50, message = "原明细业务单号长度超过50", groups = { Default.class })
    private String oldTradeNo;

    /**
     * 结算金额
     */
    @NotNull(message = "交易金额不能为空", groups = { Default.class })
    @DecimalMin(value = "0.01", message = "交易金额小于等于0或是非数字", groups = { Default.class })
    @Digits(integer = 15, fraction = 2, message = "交易金额整数部分不超过15位，精确到2位小数", groups = { Default.class })
    private double totalAmt;

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
    private String personId;

    /**
     * 机构名
     */
    @NotBlank(message = "机构名不能为空", groups = Second.class)
    @Size(min = 0, max = 80, message = "机构名长度不能超过80", groups = Second.class)
    private String orgName;

    /**
     * 机构证件类型
     */
    @NotBlank(message = "机构证件类型不能为空", groups = Second.class)
    @Option(value = EnumCodes.ORG_TYPE, message = "机构证件类型只支持11-营业执照", groups = Second.class)
    private String orgType;

    /**
     * 机构证件号
     */
    @NotBlank(message = "机构证件号不能为空", groups = Second.class)
    @Size(min = 9, max = 9, message = "机构证件号长度必须为9", groups = Second.class)
    private String orgId;

    /**
     * 发货方式
     */
    @Option(value = EnumCodes.TRANS_TYPE, message = "未知的发货方式", groups = { Default.class })
    private String transType;

    /**
     * 留学者计划境外居留时间
     */
    @Option(value = EnumCodes.ABOARD_DURATION, message = "未知的留学者计划境外居留时间类型", groups = { Default.class })
    private String aboardDuration;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空", groups = { Default.class })
    private String productName;

    /**
     * 商品数量
     */
    @NotBlank(message = "商品数量不能为空", groups = { Default.class })
    @Size(max = 19, message = "商品数量长度不能超过{max}位", groups = { Default.class })
    @Pattern(regexp = "^\\+{0,1}[1-9]\\d*", message = "商品数量必须为整数", groups = Default.class)
    private String productNum;
    /**
     * 物流企业名称
     */
    private String logistcsCompName;
    /**
     * 物流单号
     */
    private String logistcsWoNo;

    /**
     * 商品种类
     */
    @NotBlank(message = "商品种类不能为空", groups = { Default.class })
    @Option(value = EnumCodes.MERCHANDISE_TYPE, message = "未知的商品种类", groups = { Default.class })
    private String merchandiseType;

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
     * @return the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
     * @return the oldTradeNo
     */
    public String getOldTradeNo() {
        return oldTradeNo;
    }

    /**
     * @param oldTradeNo the oldTradeNo to set
     */
    public void setOldTradeNo(String oldTradeNo) {
        this.oldTradeNo = oldTradeNo;
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
     * @param orgId the orgId to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the transType
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param transType the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getLogistcsCompName() {
        return logistcsCompName;
    }

    public void setLogistcsCompName(String logistcsCompName) {
        this.logistcsCompName = logistcsCompName;
    }

    public String getLogistcsWoNo() {
        return logistcsWoNo;
    }

    public void setLogistcsWoNo(String logistcsWoNo) {
        this.logistcsWoNo = logistcsWoNo;
    }

    public String getMerchandiseType() {
        return merchandiseType;
    }

    public void setMerchandiseType(String merchandiseType) {
        this.merchandiseType = merchandiseType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CtUaFileCheck [tradeNo=" + tradeNo + ", orderType=" + orderType + ", createTime=" + createTime + ", payTime=" + payTime + ", oldTradeNo=" + oldTradeNo + ", totalAmt=" + totalAmt + ", customType=" + customType + ", personName=" + FrontHideBitRule.nameHideForCN(personName) + ", personId=" + BackHideBitRule.idCardHide(personId) + ", personIdType=" + personIdType + ", orgName=" + orgName + ", orgType=" + orgType + ", orgId=" + orgId + ", transType=" + transType + ", aboardDuration=" + aboardDuration + ", productName=" + productName + ", productNum=" + productNum + ",logistcsCompName="
                + logistcsCompName + ", logistcsWoNo=" + logistcsWoNo + ", merchandiseType=" + merchandiseType + "]";
    }
}
