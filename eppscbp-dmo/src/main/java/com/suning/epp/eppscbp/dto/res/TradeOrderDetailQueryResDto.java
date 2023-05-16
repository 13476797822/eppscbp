/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;

import com.suning.epp.eppscbp.common.enums.AmtType;
import com.suning.epp.eppscbp.common.enums.BizDetailType;
import com.suning.epp.eppscbp.common.enums.CbpProductType;
import com.suning.epp.eppscbp.common.enums.CbpStatus;
import com.suning.epp.eppscbp.common.enums.CbpSupStatus;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;

/**
 * @author 17080704
 *
 */
public class TradeOrderDetailQueryResDto {

    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 业务子单号
     */
    private String subBusinessNo;
    /**
     * 订单创建时间
     */
    private String orderCreatTime;
    /**
     * 订单完成时间
     */
    private String orderCompleteTime;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 业务细类
     */
    private String bizDetailType;
    /**
     * 购汇类型
     */
    private String exchangeType;
    /**
     * 交易状态
     */
    private String status;
    /**
     * 交易失败原因
     */
    private String orderFailReason;
    /**
     * 监管状态
     */
    private String supStatus;
    /**
     * 监管失败原因
     */
    private String supFailReason;
    /**
     * 收款方商户号
     */
    private String payeeMerchantCode;
    /**
     * 收款方商户名称
     */
    private String payeeMerchantName;
    /**
     * 申请金额
     */
    private String applyAmt;
    /**
     * 申请币种
     */
    private String applyCur;
    /**
     * 申请币种名称
     */
    private String applyCurName;
    /**
     * 结算汇率
     */
    private BigDecimal exchangeRate;
    /**
     * 汇率失效时间
     */
    private String rateExpiredTime;
    /**
     * 结算金额
     */
    private String remitAmt;
    /**
     * 结算币种
     */
    private String remitCur;
    /**
     * 结算币种名称
     */
    private String remitCurName;
    /**
     * 人民币账户支出总金额
     */
    private String paymentRMBAmt;
    /**
     * 外币账户支出总金额
     */
    private String paymentExAmt;
    /**
     * 手续费
     */
    private String feeAmt;
    /**
     * 全额到账标识
     */
    private String fullAmtType;
    /**
     * 全额到账外币
     */
    private String fullExAmt;
    /**
     * 全额到账人民币
     */
    private String fullRMBAmt;
    /**
     * 退汇金额
     */
    private String refundAmt;
    /**
     * 明细笔数
     */
    private Integer numbers;
    /**
     * 交易附言
     */
    private String remark;
    /**
     * 客户名称
     */
    private String personalName;
    /**
     * 客户证件号码
     */
    private String personalId;

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getSubBusinessNo() {
        return subBusinessNo;
    }

    public void setSubBusinessNo(String subBusinessNo) {
        this.subBusinessNo = subBusinessNo;
    }

    public String getOrderCreatTime() {
        return orderCreatTime;
    }

    public void setOrderCreatTime(String orderCreatTime) {
        this.orderCreatTime = orderCreatTime;
    }

    public String getOrderCompleteTime() {
        return orderCompleteTime;
    }

    public void setOrderCompleteTime(String orderCompleteTime) {
        this.orderCompleteTime = orderCompleteTime;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizDetailType() {
        return bizDetailType;
    }

    public void setBizDetailType(String bizDetailType) {
        this.bizDetailType = bizDetailType;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderFailReason() {
        return orderFailReason;
    }

    public void setOrderFailReason(String orderFailReason) {
        this.orderFailReason = orderFailReason;
    }

    public String getSupStatus() {
        return supStatus;
    }

    public void setSupStatus(String supStatus) {
        this.supStatus = supStatus;
    }

    public String getSupFailReason() {
        return supFailReason;
    }

    public void setSupFailReason(String supFailReason) {
        this.supFailReason = supFailReason;
    }

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

    public String getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(String applyAmt) {
        this.applyAmt = applyAmt;
    }

    public String getApplyCur() {
        return applyCur;
    }

    public void setApplyCur(String applyCur) {
        this.applyCur = applyCur;
    }

    public String getApplyCurName() {
        return applyCurName;
    }

    public void setApplyCurName(String applyCurName) {
        this.applyCurName = applyCurName;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getRateExpiredTime() {
        return rateExpiredTime;
    }

    public void setRateExpiredTime(String rateExpiredTime) {
        this.rateExpiredTime = rateExpiredTime;
    }

    public String getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(String remitAmt) {
        this.remitAmt = remitAmt;
    }

    public String getRemitCur() {
        return remitCur;
    }

    public void setRemitCur(String remitCur) {
        this.remitCur = remitCur;
    }

    public String getRemitCurName() {
        return remitCurName;
    }

    public void setRemitCurName(String remitCurName) {
        this.remitCurName = remitCurName;
    }

    public String getPaymentRMBAmt() {
        return paymentRMBAmt;
    }

    public void setPaymentRMBAmt(String paymentRMBAmt) {
        this.paymentRMBAmt = paymentRMBAmt;
    }

    public String getPaymentExAmt() {
        return paymentExAmt;
    }

    public void setPaymentExAmt(String paymentExAmt) {
        this.paymentExAmt = paymentExAmt;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getFullAmtType() {
        return fullAmtType;
    }

    public void setFullAmtType(String fullAmtType) {
        this.fullAmtType = fullAmtType;
    }

    public String getFullExAmt() {
        return fullExAmt;
    }

    public void setFullExAmt(String fullExAmt) {
        this.fullExAmt = fullExAmt;
    }

    public String getFullRMBAmt() {
        return fullRMBAmt;
    }

    public void setFullRMBAmt(String fullRMBAmt) {
        this.fullRMBAmt = fullRMBAmt;
    }

    public String getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getStatusName() {
        return CbpStatus.getDescriptionFromCode(this.status);
    }

    public String getBizTypeName() {
        return ChargeBizType.getDescriptionFromCode(this.bizType);
    }

    public String getBizDetailTypeName() {
        return BizDetailType.getDescriptionFromCode(this.bizDetailType);
    }

    public String getFullAmtTypeName() {
        return AmtType.getDescriptionFromCode(this.fullAmtType);
    }

    public String getProductTypeName() {
        return CbpProductType.getDescriptionFromCode(this.productType);
    }

    public String getSupStatusName() {
        return CbpSupStatus.getDescriptionFromCode(this.supStatus);
    }

    public String getApplyAmtAndCur() {
        return this.applyAmt + " " + this.applyCurName;
    }

    public String getFeeAmtAndCur() {
        return this.feeAmt + " 人民币";
    }

    public String getFullAmtAndCur() {
        if ("CNY".equals(this.remitCur)) {
            return this.fullRMBAmt + " 人民币";
        } else {
            return this.fullExAmt + " " + this.remitCurName + "(" + this.fullRMBAmt + " 人民币)";
        }

    }

    public String getRealPayAmt() {

        if (CbpProductType.SINGLE_FH.getCode().equals(this.productType)) {
            return this.paymentExAmt + " " + this.remitCurName + " + " + this.paymentRMBAmt + " 人民币";
        } else {
            return this.paymentRMBAmt + " 人民币";
        }

    }

    public String getRemitAmtAndCur() {

        if ((CbpProductType.SINGLE_GFH.getCode().equals(productType) || CbpProductType.BATCH_GFH.getCode().equals(productType)) && "CNY".equals(applyCur) && (!CbpStatus.SUCESS_GH.getCode().equals(status) && !CbpStatus.PRE_FH.getCode().equals(status) && !CbpStatus.SUCESS_FH.getCode().equals(status) && !CbpStatus.SUCESS_TH.getCode().equals(status))) {
            return "";
        } else {
            return this.remitAmt + " " + this.remitCurName;
        }

    }

    public String getRefundAmtAndCur() {

        return this.refundAmt + " " + this.remitCurName;

    }

    public String getExchangeRateStr() {
        // 指定人民币购汇,汇率等待返盘之后显示
        if ((CbpProductType.SINGLE_GFH.getCode().equals(productType) || CbpProductType.BATCH_GFH.getCode().equals(productType)) && "CNY".equals(applyCur) && (!CbpStatus.SUCESS_GH.getCode().equals(status) && !CbpStatus.PRE_FH.getCode().equals(status) && !CbpStatus.SUCESS_FH.getCode().equals(status) && !CbpStatus.SUCESS_TH.getCode().equals(status))) {
            return "";
        } else {
            return this.exchangeRate == null ? "" : this.exchangeRate.stripTrailingZeros().toString();
        }

    }

    @Override
    public String toString() {
        return "TradeOrderDetailQueryResDto [businessNo=" + businessNo + ", subBusinessNo=" + subBusinessNo + ", orderCreatTime=" + orderCreatTime + ", orderCompleteTime=" + orderCompleteTime + ", productType=" + productType + ", bizType=" + bizType + ", bizDetailType=" + bizDetailType + ", exchangeType=" + exchangeType + ", status=" + status + ", orderFailReason=" + orderFailReason + ", supStatus=" + supStatus + ", supFailReason=" + supFailReason + ", payeeMerchantCode=" + payeeMerchantCode + ", payeeMerchantName=" + payeeMerchantName + ", applyAmt=" + applyAmt + ", applyCur=" + applyCur
                + ", applyCurName=" + applyCurName + ", exchangeRate=" + exchangeRate + ", rateExpiredTime=" + rateExpiredTime + ", remitAmt=" + remitAmt + ", remitCur=" + remitCur + ", remitCurName=" + remitCurName + ", paymentRMBAmt=" + paymentRMBAmt + ", paymentExAmt=" + paymentExAmt + ", feeAmt=" + feeAmt + ", fullAmtType=" + fullAmtType + ", fullExAmt=" + fullExAmt + ", fullRMBAmt=" + fullRMBAmt + ", refundAmt=" + refundAmt + ", numbers=" + numbers + ", remark=" + remark + ", personalName=" + personalName + ", personalId=" + personalId + "]";
    }

}
