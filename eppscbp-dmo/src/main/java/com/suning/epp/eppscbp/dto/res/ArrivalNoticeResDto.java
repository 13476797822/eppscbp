/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

/**
 * @author 17080704
 *
 */
public class ArrivalNoticeResDto {

    /**
     * 待解付资金流水号
     */
    private String payNo;

    /**
     * 金额
     */
    private String amount;

    /**
     * 来账通知ID
     */
    private String arrivalNoticeId;
    
    /**
     * 付款账号
     */
    private String payerBankCard;
    
    /**
     * 参考汇率
     * @return
     */
    private String referenceRate;
    
    /**
     * 境外付款方商户号
     */
    private String payeeMerchantCode;

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getArrivalNoticeId() {
        return arrivalNoticeId;
    }

    public void setArrivalNoticeId(String arrivalNoticeId) {
        this.arrivalNoticeId = arrivalNoticeId;
    }
    
    public String getPayerBankCard() {
        return payerBankCard;
    }

    public void setPayerBankCard(String payerBankCard) {
        this.payerBankCard = payerBankCard;
    }

    public String getReferenceRate() {
        return referenceRate;
    }

    public void setReferenceRate(String referenceRate) {
        this.referenceRate = referenceRate;
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ArrivalNoticeResDto [payNo=" + payNo + ", amount=" + amount + ", arrivalNoticeId=" + arrivalNoticeId + ", payerBankCard=" + payerBankCard + ", referenceRate=" + referenceRate + ", payeeMerchantCode=" + payeeMerchantCode + "]";
    }

}
