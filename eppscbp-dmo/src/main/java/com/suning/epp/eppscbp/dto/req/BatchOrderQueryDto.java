/**
 * 
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * @author 17080704
 *
 */
public class BatchOrderQueryDto {

    /**
     * 付款方商户户头号
     */
    private String payerAccount;

    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 支付状态
     */
    private String status;
    /**
     * 监管状态
     */
    private String supStatus;
    /**
     * 订单起始时间
     */
    private String creatOrderStartTime;
    /**
     * 订单终止时间
     */
    private String creatOrderEndTime;
    /**
     * 当前页
     */
    private String currentPage;

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupStatus() {
        return supStatus;
    }

    public void setSupStatus(String supStatus) {
        this.supStatus = supStatus;
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

    @Override
    public String toString() {
        return "BatchOrderQueryDto [payerAccount=" + payerAccount + ", businessNo=" + businessNo + ", status=" + status + ", supStatus=" + supStatus + ", creatOrderStartTime=" + creatOrderStartTime + ", creatOrderEndTime=" + creatOrderEndTime + ", currentPage=" + currentPage + "]";
    }

}
