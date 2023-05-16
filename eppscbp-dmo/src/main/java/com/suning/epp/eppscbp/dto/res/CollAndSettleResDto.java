/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.ChargeBizType;

/**
 * @author 17080704
 *
 */
public class CollAndSettleResDto {

    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 境外账号
     */
    private String payeeBankCard;
    /**
     * 收结汇币种
     */
    private String currency;
    /**
     * 收结汇币种名称
     */
    private String currencyName;
    /**
     * 收结汇金额
     */
    private String receiveAmt;
    /**
     * 参考汇率
     */
    private String referenceRate;
    /**
     * 预计兑换(人民币/元)
     */
    private String expectedExchangeRMB;
    /**
     * 预计收入(人民币/元)
     */
    private String expectedCustomerIncomeRMB;
    /**
     * 手续费
     */
    private String referenceFeeAmt;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 明细笔数
     */
    private String detailAmount;

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getPayeeBankCard() {
        return payeeBankCard;
    }

    public void setPayeeBankCard(String payeeBankCard) {
        this.payeeBankCard = payeeBankCard;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getReceiveAmt() {
        return receiveAmt;
    }

    public void setReceiveAmt(String receiveAmt) {
        this.receiveAmt = receiveAmt;
    }

    public String getReferenceRate() {
        return referenceRate;
    }

    public void setReferenceRate(String referenceRate) {
        this.referenceRate = referenceRate;
    }

    public String getExpectedExchangeRMB() {
        return expectedExchangeRMB;
    }

    public void setExpectedExchangeRMB(String expectedExchangeRMB) {
        this.expectedExchangeRMB = expectedExchangeRMB;
    }

    public String getExpectedCustomerIncomeRMB() {
        return expectedCustomerIncomeRMB;
    }

    public void setExpectedCustomerIncomeRMB(String expectedCustomerIncomeRMB) {
        this.expectedCustomerIncomeRMB = expectedCustomerIncomeRMB;
    }

    public String getReferenceFeeAmt() {
        return referenceFeeAmt;
    }

    public void setReferenceFeeAmt(String referenceFeeAmt) {
        this.referenceFeeAmt = referenceFeeAmt;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(String detailAmount) {
        this.detailAmount = detailAmount;
    }

    public String getBizTypeStr() {
        return ChargeBizType.getDescriptionFromCode(bizType);
    }

    @Override
    public String toString() {
        return "CollAndSettleResDto [businessNo=" + businessNo + ", payeeBankCard=" + payeeBankCard + ", currency=" + currency + ", currencyName=" + currencyName + ", receiveAmt=" + receiveAmt + ", referenceRate=" + referenceRate + ", expectedExchangeRMB=" + expectedExchangeRMB + ", expectedCustomerIncomeRMB=" + expectedCustomerIncomeRMB + ", referenceFeeAmt=" + referenceFeeAmt + ", bizType=" + bizType + ", detailAmount=" + detailAmount + "]";
    }

}
