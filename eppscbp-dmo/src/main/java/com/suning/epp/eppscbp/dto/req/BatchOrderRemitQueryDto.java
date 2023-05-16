/**
 * 
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * @author 17080704
 *
 */
public class BatchOrderRemitQueryDto {
    /**
     * 付款方商户户头号
     */
    private String payerAccount;
    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 业务子单号
     */
    private String subBusinessNo;
    /**
     * 收款方商户号
     */
    private String payeeMerchantCode;
    /**
     * 收款方商户名称
     */
    private String payeeMerchantName;
    /**
     * 状态
     */
    private String status;
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

    public String getSubBusinessNo() {
        return subBusinessNo;
    }

    public void setSubBusinessNo(String subBusinessNo) {
        this.subBusinessNo = subBusinessNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "BatchOrderRemitQueryDto [payerAccount=" + payerAccount + ", businessNo=" + businessNo + ", subBusinessNo=" + subBusinessNo + ", payeeMerchantCode=" + payeeMerchantCode + ", payeeMerchantName=" + payeeMerchantName + ", status=" + status + ", creatOrderStartTime=" + creatOrderStartTime + ", creatOrderEndTime=" + creatOrderEndTime + ", currentPage=" + currentPage + "]";
    }

}
