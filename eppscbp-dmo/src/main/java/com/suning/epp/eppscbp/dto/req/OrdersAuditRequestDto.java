package com.suning.epp.eppscbp.dto.req;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class OrdersAuditRequestDto {
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
     * 业务单号
     */
    @NotBlank(message = "业务单号不能为空")
    @Length(min = 15, max = 15, message = "业务单号长度必须为19")
    private String businessNo;

    /**
     * 批付明细
     */
    private List<OrdersAuditRequestDetailDto> details;

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

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public List<OrdersAuditRequestDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<OrdersAuditRequestDetailDto> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "OrdersAuditRequestDto [platformCode=" + platformCode + ", payerAccount=" + payerAccount + ", businessNo=" + businessNo + ", details=" + details + "]";
    }

}
