package com.suning.epp.eppscbp.dto.req;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;


public class BatchPaymentReviewQueryReqDto {

    /**
     * 商户会员户头号
     */
    @NotBlank(message = "payerAccount不能为空")
    @Length(min = 19, max = 19, message = "商户会员户头号长度必须为19")
    private String payerAccount;

    /**
     * 付款方商户号
     */
    private String payerMerchantCode;

    /**
     * 平台标识
     */
    @NotBlank(message = "平台标识不能为空")
    @Size(max = 3, message = "平台标识长度不能超过{max}位")
    private String platformCode;


    /**
     * 出款状态
     */
    private String reviewStatus;

    /**
     * 出款状态集合list
     */
    private List<String>  reviewStatuss;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 创建开始时间
     */
    private String createStartTime;

    /**
     * 创建结束时间
     */
    private String createEndTime;

    /**
     * 每页条数
     */
    private String pageSize;

    /**
     * 页码
     */
    private String currentPage;

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPayerMerchantCode() {
        return payerMerchantCode;
    }

    public void setPayerMerchantCode(String payerMerchantCode) {
        this.payerMerchantCode = payerMerchantCode;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }


    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<String> getReviewStatuss() {
        return reviewStatuss;
    }

    public void setReviewStatuss(List<String> reviewStatuss) {
        this.reviewStatuss = reviewStatuss;
    }

    @Override
    public String toString() {
        return "BatchPaymentReviewQueryReqDto{" +
                "payerAccount='" + payerAccount + '\'' +
                ", payerMerchantCode='" + payerMerchantCode + '\'' +
                ", platformCode='" + platformCode + '\'' +
                ", reviewStatus='" + reviewStatus + '\'' +
                ", reviewStatuss=" + reviewStatuss +
                ", batchNo='" + batchNo + '\'' +
                ", createStartTime='" + createStartTime + '\'' +
                ", createEndTime='" + createEndTime + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", currentPage='" + currentPage + '\'' +
                '}';
    }
}
