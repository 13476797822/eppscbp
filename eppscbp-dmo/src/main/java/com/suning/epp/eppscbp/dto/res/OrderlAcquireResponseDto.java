package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;
import java.util.Date;

import com.suning.epp.eppscbp.util.DateUtil;

public class OrderlAcquireResponseDto {
    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 购汇类型
     */
    private String exchangeType;
    /**
     * 应付人民币总金额
     */
    private String paymentRmbAmt;
    /**
     * 应付外币总金额
     */
    private String paymentExAmt;
    /**
     * 结算/汇出金额
     */
    private String remitAmt;
    /**
     * 汇出币种
     */
    private String remitCur;
    /**
     * 汇出币种名称
     */
    private String remitCurName;
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
     * 汇率
     */
    private BigDecimal exchangeRate;
    /**
     * 总手续费
     */
    private String feeAmt;
    /**
     * 全额到账标识
     */
    private String fullAmtFlag;
    /**
     * 全额到账人民币费用
     */
    private String fullRmbAmt;
    /**
     * 全额到账外币费用
     */
    private String fullExAmt;
    /**
     * 汇率失效时间
     */
    private Date rateExpiredTime;

    /**
     * 汇率失效倒计时
     */
    private Long rateExpiredTimeS;

    /**
     * @return the rateExpiredTimeS
     */
    public Long getRateExpiredTimeS() {
        return rateExpiredTimeS;
    }

    /**
     * @param rateExpiredTimeS the rateExpiredTimeS to set
     */
    public void setRateExpiredTimeS(Long rateExpiredTimeS) {
        this.rateExpiredTimeS = rateExpiredTimeS;
    }

    /**
     * 订单失效时间
     */
    private Date orderExpiredTime;

    private String orderExpiredTimeStr;

    public String getOrderExpiredTimeStr() {
        return DateUtil.formatDate(orderExpiredTime);
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getPaymentRmbAmt() {
        return paymentRmbAmt;
    }

    public void setPaymentRmbAmt(String paymentRmbAmt) {
        this.paymentRmbAmt = paymentRmbAmt;
    }

    public String getPaymentExAmt() {
        return paymentExAmt;
    }

    public void setPaymentExAmt(String paymentExAmt) {
        this.paymentExAmt = paymentExAmt;
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

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    /**
     * @return the fullAmtFlag
     */
    public String getFullAmtFlag() {
        return fullAmtFlag;
    }

    /**
     * @param fullAmtFlag the fullAmtFlag to set
     */
    public void setFullAmtFlag(String fullAmtFlag) {
        this.fullAmtFlag = fullAmtFlag;
    }

    public void setOrderExpiredTimeStr(String orderExpiredTimeStr) {
        this.orderExpiredTimeStr = orderExpiredTimeStr;
    }

    public String getFullRmbAmt() {
        return fullRmbAmt;
    }

    public void setFullRmbAmt(String fullRmbAmt) {
        this.fullRmbAmt = fullRmbAmt;
    }

    public String getFullExAmt() {
        return fullExAmt;
    }

    public void setFullExAmt(String fullExAmt) {
        this.fullExAmt = fullExAmt;
    }

    public Date getRateExpiredTime() {
        return rateExpiredTime;
    }

    public void setRateExpiredTime(Date rateExpiredTime) {
        this.rateExpiredTime = rateExpiredTime;
    }

    public Date getOrderExpiredTime() {
        return orderExpiredTime;
    }

    public void setOrderExpiredTime(Date orderExpiredTime) {
        this.orderExpiredTime = orderExpiredTime;
    }

    @Override
    public String toString() {
        return "OrderlAcquireResponseDto [businessNo=" + businessNo + ", productType=" + productType + ", exchangeType=" + exchangeType + ", paymentRmbAmt=" + paymentRmbAmt + ", paymentExAmt=" + paymentExAmt + ", remitAmt=" + remitAmt + ", remitCur=" + remitCur + ", remitCurName=" + remitCurName + ", applyAmt=" + applyAmt + ", applyCur=" + applyCur + ", applyCurName=" + applyCurName + ", exchangeRate=" + exchangeRate + ", feeAmt=" + feeAmt + ", fullAmtFlag=" + fullAmtFlag + ", fullRmbAmt=" + fullRmbAmt + ", fullExAmt=" + fullExAmt + ", rateExpiredTime=" + rateExpiredTime
                + ", rateExpiredTimeS=" + rateExpiredTimeS + ", orderExpiredTime=" + orderExpiredTime + ", orderExpiredTimeStr=" + orderExpiredTimeStr + "]";
    }
}
