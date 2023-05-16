/**
 *
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * 〈提现明细提交dto〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CpWithdrawApplyDto {

    /**
     * 提现币种
     */
    private String withdrawCur;
    /**
     * 付款方商户户头号
     */
    private String payerAccount;
    /**
     * 付款金额
     */
    private String payerAmt;
    /**
     * 是否包含易付宝商户资金
     */
    private String isEppFund;
    /**
     * 出账批次
     */
    private String outAccountBatch;
    /**
     * 申报明细文件路径
     */
    private String fileAddress;
    /**
     * 申报明细笔数
     */
    private String detailAmount;
    /**
     * 待解付资金流水号
     */
    private String payNo;
    /**
     * 待解付资金ID
     */
    private String arrivalNoticeId;
    /**
     * 申请提现金额
     */
    private String withdrawAmt;
    /**
     * 付款账号
     */
    private String payAccount;
    /**
     * 二代PC指纹
     */
    private String pcToken;
    /**
     * 平台名称
     */
    private String platformName;


    public String getWithdrawCur() {
        return withdrawCur;
    }

    public void setWithdrawCur(String withdrawCur) {
        this.withdrawCur = withdrawCur;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPayerAmt() {
        return payerAmt;
    }

    public void setPayerAmt(String payerAmt) {
        this.payerAmt = payerAmt;
    }

    public String getIsEppFund() {
        return isEppFund;
    }

    public void setIsEppFund(String isEppFund) {
        this.isEppFund = isEppFund;
    }

    public String getOutAccountBatch() {
        return outAccountBatch;
    }

    public void setOutAccountBatch(String outAccountBatch) {
        this.outAccountBatch = outAccountBatch;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getArrivalNoticeId() {
        return arrivalNoticeId;
    }

    public void setArrivalNoticeId(String arrivalNoticeId) {
        this.arrivalNoticeId = arrivalNoticeId;
    }

    public String getWithdrawAmt() {
        return withdrawAmt;
    }

    public void setWithdrawAmt(String withdrawAmt) {
        this.withdrawAmt = withdrawAmt;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(String detailAmount) {
        this.detailAmount = detailAmount;
    }

    /**
     * @return the pcToken
     */
    public String getPcToken() {
        return pcToken;
    }

    /**
     * @param pcToken the pcToken to set
     */
    public void setPcToken(String pcToken) {
        this.pcToken = pcToken;
    }

    /**
     * @return the platformName
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * @param platformName the platformName to set
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    @Override
    public String toString() {
        return "CpWithdrawApplyDto [withdrawCur=" + withdrawCur + ", payerAccount=" + payerAccount + ", payerAmt=" + payerAmt + ", isEppFund=" + isEppFund + ", outAccountBatch=" + outAccountBatch + ", fileAddress=" + fileAddress + ", detailAmount=" + detailAmount + ", payNo=" + payNo + ", arrivalNoticeId=" + arrivalNoticeId + ", withdrawAmt=" + withdrawAmt + ", payAccount=" + payAccount + ", pcToken=" + pcToken + ", platformName=" + platformName + "]";
    }

}
