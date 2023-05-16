/**
 * 
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * @author 17080704
 *
 */
public class SingleOrderQueryDto {

    /**
     * 付款方商户户头号
     */
    private String payerAccount;

    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 状态
     */
    private String status;
    /**
     * 监管状态
     */
    private String supStatus;
    /**
     * 创建起始时间
     */
    private String creatOrderStartTime;
    /**
     * 创建终止时间
     */
    private String creatOrderEndTime;
    /**
     * 收款方商户号
     */
    private String payeeMerchantCode;
    /**
     * 收款方商户名称
     */
    private String payeeMerchantName;
    /**
     * 当前页
     */
    private String currentPage;
    /**
     * 明细校验状态
     */
    private String detailFlag;

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

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the detailFlag
     */
    public String getDetailFlag() {
        return detailFlag;
    }

    /**
     * @param detailFlag the detailFlag to set
     */
    public void setDetailFlag(String detailFlag) {
        this.detailFlag = detailFlag;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SingleOrderQueryDto [payerAccount=" + payerAccount + ", businessNo=" + businessNo + ", bizType=" + bizType + ", productType=" + productType + ", status=" + status + ", supStatus=" + supStatus + ", creatOrderStartTime=" + creatOrderStartTime + ", creatOrderEndTime=" + creatOrderEndTime + ", payeeMerchantCode=" + payeeMerchantCode + ", payeeMerchantName=" + payeeMerchantName + ", currentPage=" + currentPage + ", detailFlag=" + detailFlag + "]";
    }
    
}
