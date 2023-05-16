/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpBatchPaymentDetailResDto.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.CpBatchPaymentStatus;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈批付明细DTO〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CpBatchPaymentDetailResDto {

    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 批次总金额
     */
    private Long batchSumAmt;
    /**
     * 收款方开户名
     */
    private String payeeName;
    /**
     * 银行帐号
     */
    private String bankAcc;
    /**
     * 交易流水号
     */
    private String serialNo;
    /**
     * 出款金额
     */
    private Long payAmt;
    /**
     * 出款状态
     */
    private String status;
    /**
     * 出款失败原因
     */
    private String payFailReason;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 申请时间
     */
    private String applyTime;
    /**
     * 当前页
     */
    private String currentPage;
    /**
     * 订单名称
     */
    private String orderName;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Long getBatchSumAmt() {
        return batchSumAmt;
    }

    public void setBatchSumAmt(Long batchSumAmt) {
        this.batchSumAmt = batchSumAmt;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Long getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(Long payAmt) {
        this.payAmt = payAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayFailReason() {
        return payFailReason;
    }

    public void setPayFailReason(String payFailReason) {
        this.payFailReason = payFailReason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getStatusName() {
        return CpBatchPaymentStatus.getDescriptionFromCode(this.status);
    }

    public String getPayAmtStr() {
        return StringUtil.formatMoney(this.payAmt);
    }

    public String getBatchSumAmtStr() {
        if (batchSumAmt == null) {
            return "";
        } else {
            return StringUtil.formatMoney(this.batchSumAmt);
        }

    }

    /**
     * @return the orderName
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * @param orderName the orderName to set
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        return "CpBatchPaymentDetailResDto [batchNo=" + batchNo + ", batchSumAmt=" + batchSumAmt + ", payeeName=" + payeeName + ", bankAcc=" + bankAcc + ", serialNo=" + serialNo + ", payAmt=" + payAmt + ", status=" + status + ", payFailReason=" + payFailReason + ", createTime=" + createTime + ", applyTime=" + applyTime + ", currentPage=" + currentPage + ", orderName=" + orderName + "]";
    }   
    
}
