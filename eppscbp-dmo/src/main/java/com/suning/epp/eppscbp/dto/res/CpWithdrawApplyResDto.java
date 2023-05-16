/**
 *
 */
package com.suning.epp.eppscbp.dto.res;

/**
 * @author 17090884
 */
public class CpWithdrawApplyResDto {

    /**
     * 付款金额
     */
    private String payerAmt;

    /**
     * 出账批次
     */
    private String outAccountBatch;

    /**
     * 付款账号
     */
    private String payAccount;

    public String getPayerAmt() {
        return payerAmt;
    }

    public void setPayerAmt(String payerAmt) {
        this.payerAmt = payerAmt;
    }

    public String getOutAccountBatch() {
        return outAccountBatch;
    }

    public void setOutAccountBatch(String outAccountBatch) {
        this.outAccountBatch = outAccountBatch;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    @Override
    public String toString() {
        return "CpWithdrawApplyResDto{" +
                "payerAmt='" + payerAmt + '\'' +
                ", outAccountBatch='" + outAccountBatch + '\'' +
                ", payAccount='" + payAccount + '\'' +
                '}';
    }
}
