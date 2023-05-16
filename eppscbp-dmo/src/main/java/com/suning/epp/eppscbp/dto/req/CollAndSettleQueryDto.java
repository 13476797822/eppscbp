/**
 * 
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * @author 17080704
 *
 */
public class CollAndSettleQueryDto {

    /**
     * 商户户头号
     */
    private String payerAccount;
    /**
     * 收单单号
     */
    private String receiptNo;
    /**
     * 结汇币种
     */
    private String currency;
    /**
     * 结汇金额
     */
    private String orderAmt;
    /**
     * 订单状态
     */
    private String orderState;

    /**
     * 批付状态
     */
    private String batchPayFlag;
    /**
     * 创建起始时间
     */
    private String creatOrderStartTime;
    /**
     * 创建终止时间
     */
    private String creatOrderEndTime;
    /**
     * 当前页
     */
    private String currentPage;
    /**
     * 业务单号
     */
    private String businessNo;

    /**
     * 业务类型
     */
    private String bizType;

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getCreatOrderStartTime() {
        return creatOrderStartTime;
    }

    public void setCreatOrderStartTime(String creatOrderStartTime) {
        this.creatOrderStartTime = creatOrderStartTime;
    }

    public String getCreatOrderEndTime() {
        return creatOrderEndTime;
    }

    public void setCreatOrderEndTime(String creatOrderEndTime) {
        this.creatOrderEndTime = creatOrderEndTime;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBatchPayFlag() {
        return batchPayFlag;
    }

    public void setBatchPayFlag(String batchPayFlag) {
        this.batchPayFlag = batchPayFlag;
    }

    @Override
    public String toString() {
        return "CollAndSettleQueryDto [payerAccount=" + payerAccount + ", receiptNo=" + receiptNo + ", currency=" + currency + ", orderAmt=" + orderAmt + ", orderState=" + orderState + ", batchPayFlag=" + batchPayFlag + ", creatOrderStartTime=" + creatOrderStartTime + ", creatOrderEndTime=" + creatOrderEndTime + ", currentPage=" + currentPage + ", businessNo=" + businessNo + ", bizType=" + bizType + "]";
    }

}
