/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import java.util.List;

/**
 * @author 17080704
 *
 */
public class ArrivalInfoResDto {

    /**
     * 境外付款方商户号
     */
    private String payeeMerchantCode;

    /**
     * 境外付款方帐号
     */
    private String payerBankCard;

    /**
     * 收汇权限
     */
    private String collectionType;

    /**
     * 结汇权限
     */
    private String settlementType;

    /**
     * 来账信息
     */
    private List<ArrivalNoticeResDto> arrivalNoticeList;

    public List<ArrivalNoticeResDto> getArrivalNoticeList() {
        return arrivalNoticeList;
    }

    public void setArrivalNoticeList(List<ArrivalNoticeResDto> arrivalNoticeList) {
        this.arrivalNoticeList = arrivalNoticeList;
    }

    /**
     * @return the payeeMerchantCode
     */
    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    /**
     * @param payeeMerchantCode the payeeMerchantCode to set
     */
    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    /**
     * @return the payerBankCard
     */
    public String getPayerBankCard() {
        return payerBankCard;
    }

    /**
     * @param payerBankCard the payerBankCard to set
     */
    public void setPayerBankCard(String payerBankCard) {
        this.payerBankCard = payerBankCard;
    }

    /**
     * @return the collectionType
     */
    public String getCollectionType() {
        return collectionType == null ? "0" : collectionType;
    }

    /**
     * @param collectionType the collectionType to set
     */
    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    /**
     * @return the settlementType
     */
    public String getSettlementType() {
        return settlementType == null ? "0" : settlementType;
    }

    /**
     * @param settlementType the settlementType to set
     */
    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ArrivalInfoResDto [payeeMerchantCode=" + payeeMerchantCode + ", payerBankCard=" + payerBankCard + ", collectionType=" + collectionType + ", settlementType=" + settlementType + ", arrivalNoticeList=" + arrivalNoticeList + "]";
    }

}
