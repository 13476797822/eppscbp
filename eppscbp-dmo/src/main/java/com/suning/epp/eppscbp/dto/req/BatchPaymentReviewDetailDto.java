package com.suning.epp.eppscbp.dto.req;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class BatchPaymentReviewDetailDto {
    /**
     * 平台标识
     */
    @NotBlank(message = "平台标识不能为空")
    @Length(min = 3, max = 3, message = "平台标识长度必须为3")
    private String platformCode;

    /**
     * 商户会员户头号
     */
    @NotBlank(message = "payerAccount不能为空")
    @Length(min = 19, max = 19, message = "商户会员户头号长度必须为19")
    private String payerAccount;

    /**
     * 商户号
     */
    private String payerMerchantCode;


    /**
     * api批付申请批次号
     */
    @NotBlank(message = "批次号不能为空")
    private String expendNo;

    /**
     * 每页条数
     */
    private String pageSize;

    /**
     * 页码
     */
    private String currentPage;

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

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

    public String getExpendNo() {
        return expendNo;
    }

    public void setExpendNo(String expendNo) {
        this.expendNo = expendNo;
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

    @Override
    public String toString() {
        return "BatchPaymentReviewDetailDto{" +
                "platformCode='" + platformCode + '\'' +
                ", payerAccount='" + payerAccount + '\'' +
                ", payerMerchantCode='" + payerMerchantCode + '\'' +
                ", expendNo='" + expendNo + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", currentPage='" + currentPage + '\'' +
                '}';
    }
}
