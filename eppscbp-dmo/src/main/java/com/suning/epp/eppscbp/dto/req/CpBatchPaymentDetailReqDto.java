/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpBatchPaymentDetailReqDto.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * 〈批付明细DTO〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CpBatchPaymentDetailReqDto {

    /**
     * 付款方商户户头号
     */
    private String payerAccount;

    /**
     * 平台标识
     */
    private String platformCode;

    /**
     * 收款方开户名
     */
    private String payeeName;
    /**
     * 出款状态
     */
    private String status;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 创建起始时间
     */
    private String createStartTime;
    /**
     * 创建终止时间
     */
    private String createEndTime;
    /**
     * 交易流水号
     */
    private String tradeNo;
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

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
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

    /**
     * @return the tradeNo
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * @param tradeNo the tradeNo to set
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        return "CpBatchPaymentDetailReqDto [payerAccount=" + payerAccount + ", platformCode=" + platformCode + ", payeeName=" + payeeName + ", status=" + status + ", batchNo=" + batchNo + ", createStartTime=" + createStartTime + ", createEndTime=" + createEndTime + ", currentPage=" + currentPage + "]";
    }
}
