/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;

import com.suning.epp.eppscbp.common.enums.CbpStatus;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈批量明细汇出查询返回DTO〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchTradeOrderRemitQueryResDto {

    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 业务子单号
     */
    private String subBusinessNo;
    /**
     * 创建时间
     */
    private String creatTime;
    /**
     * 购付汇状态
     */
    private String status;
    /**
     * 申请金额
     */
    private String applyAmt;
    /**
     * 申请币种代码
     */
    private String applyCur;
    /**
     * 申请币种名称
     */
    private String applyCurName;
    /**
     * 汇率
     */
    private BigDecimal exchangeRate;
    /**
     * 汇出金额
     */
    private String remitAmt;
    /**
     * 汇出金额代码
     */
    private String remitCur;
    /**
     * 汇出金额名称
     */
    private String remitCurName;
    /**
     * 收款方商户代码
     */
    private String payeeMerchantCode;
    /**
     * 收款方商户名称
     */
    private String payeeMerchantName;
    /**
     * 客户名称
     */
    private String personalName;
    /**
     * 交易订单号
     */
    private String tradeOrderNo;
    /**
     * 手续费
     */
    private String feeAmt;
    /**
     * 全额到账外币
     */
    private String fullExAmt;
    /**
     * 全额到账人民币
     */
    private String fullRMBAmt;

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

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(String applyAmt) {
        this.applyAmt = applyAmt;
    }

    public String getApplyCur() {
        return applyCur;
    }

    public void setApplyCur(String applyCur) {
        this.applyCur = applyCur;
    }

    public String getApplyCurName() {
        return applyCurName;
    }

    public void setApplyCurName(String applyCurName) {
        this.applyCurName = applyCurName;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(String remitAmt) {
        this.remitAmt = remitAmt;
    }

    public String getRemitCur() {
        return remitCur;
    }

    public void setRemitCur(String remitCur) {
        this.remitCur = remitCur;
    }

    public String getRemitCurName() {
        return remitCurName;
    }

    public void setRemitCurName(String remitCurName) {
        this.remitCurName = remitCurName;
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

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getFullExAmt() {
        return fullExAmt;
    }

    public void setFullExAmt(String fullExAmt) {
        this.fullExAmt = fullExAmt;
    }

    public String getFullRMBAmt() {
        return fullRMBAmt;
    }

    public void setFullRMBAmt(String fullRMBAmt) {
        this.fullRMBAmt = fullRMBAmt;
    }

    public String getStatusName() {
        return CbpStatus.getDescriptionFromCode(this.status);
    }

    public String getApplyCurAndName() {
        return this.applyAmt + " " + this.applyCurName;
    }

    public String getRemitCurAndName() {
        // 指定人民币购汇,汇出金额等待返盘之后显示
        if ("CNY".equals(applyCur) && (!CbpStatus.SUCESS_GH.getCode().equals(status) && CbpStatus.INIT.getCode().equals(status))) {
            return "";
        } else {
            return this.remitAmt + " " + this.remitCurName;
        }

    }

    public String getExchangeRateStr() {
        // 指定人民币购汇,汇率等待返盘之后显示
        if ("CNY".equals(applyCur) && (!CbpStatus.SUCESS_GH.getCode().equals(status) && CbpStatus.INIT.getCode().equals(status))) {
            return "";
        } else {
            return this.exchangeRate==null ?"":this.exchangeRate.stripTrailingZeros().toString();
        }

    }

    @Override
    public String toString() {
        return "BatchTradeOrderRemitQueryResDto [businessNo=" + businessNo + ", subBusinessNo=" + subBusinessNo + ", creatTime=" + creatTime + ", status=" + status + ", applyAmt=" + applyAmt + ", applyCur=" + applyCur + ", applyCurName=" + applyCurName + ", exchangeRate=" + exchangeRate + ", remitAmt=" + remitAmt + ", remitCur=" + remitCur + ", remitCurName=" + remitCurName + ", payeeMerchantCode=" + payeeMerchantCode + ", payeeMerchantName=" + payeeMerchantName + ", personalName=" + personalName + ", tradeOrderNo=" + tradeOrderNo + ", feeAmt=" + feeAmt + ", fullExAmt=" + fullExAmt
                + ", fullRMBAmt=" + fullRMBAmt + "]";
    }

}
