package com.suning.epp.eppscbp.dto.res;

import java.util.Date;

import com.suning.epp.eppscbp.common.enums.BatchPaymentReviewStatus;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

public class BatchPaymentOrderReviewData {

    /**
     * 
     */
    private static final long serialVersionUID = -869214256233348179L;


    /**
     * 付款商户
     */
    private String payerMerchantCode;


    /**
     * 批次号
     */
    private String expendNo;

    /**
     * 批次总金额
     */
    private Long expendNoAmount;

    /**
     * 批次总笔数
     */
    private Long batchPaymentCount;


    /**
     * 状态
     */
    private String reviewStatus;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 复核时间
     */
    private Date reviewTime;

    public String getPayerMerchantCode() {
        return payerMerchantCode;
    }

    public void setPayerMerchantCode(String payerMerchantCode) {
        this.payerMerchantCode = payerMerchantCode;
    }

    public String getExpendNo() {
        return expendNo;
    }

    public void setExpendNo(String expendNo) {
        this.expendNo = expendNo;
    }

    public Long getExpendNoAmount() {
        return expendNoAmount;
    }

    public void setExpendNoAmount(Long expendNoAmount) {
        this.expendNoAmount = expendNoAmount;
    }

    public Long getBatchPaymentCount() {
        return batchPaymentCount;
    }

    public void setBatchPaymentCount(Long batchPaymentCount) {
        this.batchPaymentCount = batchPaymentCount;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewStatusStr() {
        return BatchPaymentReviewStatus.getDescriptionFromCode(this.reviewStatus);
    }

    public String getCreateTimeStr() {
        return DateUtil.formatDate(this.createTime);
    }

    public String getReviewTimeStr() {
        return DateUtil.formatDate(this.reviewTime);
    }

    public String getExpendNoAmountStr() {
        return StringUtil.formatMoney(this.expendNoAmount);
    }

    @Override
    public String toString() {
        return "BatchPaymentOrderReviewData{" +
                "payerMerchantCode='" + payerMerchantCode + '\'' +
                ", expendNo='" + expendNo + '\'' +
                ", expendNoAmount=" + expendNoAmount +
                ", batchPaymentCount=" + batchPaymentCount +
                ", reviewStatus='" + reviewStatus + '\'' +
                ", createTime=" + createTime +
                ", reviewTime=" + reviewTime +
                '}';
    }
}
