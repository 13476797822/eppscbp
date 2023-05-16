package com.suning.epp.eppscbp.dto.res;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈跨境商户权限〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CtMerchantAuth {
    
    // 户头号
    private String payerAccount;
    // 操作员
    private String operatorCode;
    
    /**
     * 资金跨境
     */
    // 进口货物贸易结算
    private String goodsTrade;
    // 留学缴费
    private String overseasPay;
    // 单笔订单查询
    private String singleQuery;
    // 批量订单查询
    private String batchQuery;
    // 批付明细查询
    private String batchPaymentQuery;
    // 收款商户管理
    private String merchantHandle;
    // 汇率查询
    private String rateQuery;
    // 收结汇申请
    private String collAndSettle;
    // 国际物流结算
    private String logisticsSettlement;
    // 批付复核管理
    private String batchPaymentReview;
    /**
     * 收款平台
     */
    // 店铺管理
    private String cpStoreHandle;
    // 提现申请
    private String cpWithdrawApply;
    // 资金批付
    private String cpBatchPayment;
    // 提现明细
    private String cpWithdrawDetail;
    // 批付明细
    private String cpBatchPaymentDetail;
    // 出入账明细
    private String cpTranInOutDetail;
    // 提现账号
    private String cpWithdrawAccount;
    // 资金下发复核管理
    private String cpBatchPaymentReview;
    /**
     * 交易查询
     */
    private String tradeQueryAuth;

    /**
     * @return the payerAccount
     */
    public String getPayerAccount() {
        return payerAccount;
    }

    /**
     * @param payerAccount the payerAccount to set
     */
    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    /**
     * @return the operatorCode
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * @param operatorCode the operatorCode to set
     */
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getGoodsTrade() {
        return goodsTrade;
    }

    public void setGoodsTrade(String goodsTrade) {
        this.goodsTrade = goodsTrade;
    }

    public String getOverseasPay() {
        return overseasPay;
    }

    public void setOverseasPay(String overseasPay) {
        this.overseasPay = overseasPay;
    }

    public String getBatchQuery() {
        return batchQuery;
    }

    public void setBatchQuery(String batchQuery) {
        this.batchQuery = batchQuery;
    }

    public String getSingleQuery() {
        return singleQuery;
    }

    public void setSingleQuery(String singleQuery) {
        this.singleQuery = singleQuery;
    }

    public String getMerchantHandle() {
        return merchantHandle;
    }

    public void setMerchantHandle(String merchantHandle) {
        this.merchantHandle = merchantHandle;
    }

    public String getRateQuery() {
        return rateQuery;
    }

    public void setRateQuery(String rateQuery) {
        this.rateQuery = rateQuery;
    }

    public String getCollAndSettle() {
        return collAndSettle;
    }

    public void setCollAndSettle(String collAndSettle) {
        this.collAndSettle = collAndSettle;
    }

    public String getLogisticsSettlement() {
        return logisticsSettlement;
    }

    public void setLogisticsSettlement(String logisticsSettlement) {
        this.logisticsSettlement = logisticsSettlement;
    }

    public String getBatchPaymentQuery() {
        return batchPaymentQuery;
    }

    public void setBatchPaymentQuery(String batchPaymentQuery) {
        this.batchPaymentQuery = batchPaymentQuery;
    }

    public String getCpStoreHandle() {
        return cpStoreHandle;
    }

    public void setCpStoreHandle(String cpStoreHandle) {
        this.cpStoreHandle = cpStoreHandle;
    }

    public String getCpWithdrawApply() {
        return cpWithdrawApply;
    }

    public void setCpWithdrawApply(String cpWithdrawApply) {
        this.cpWithdrawApply = cpWithdrawApply;
    }

    public String getCpBatchPayment() {
        return cpBatchPayment;
    }

    public void setCpBatchPayment(String cpBatchPayment) {
        this.cpBatchPayment = cpBatchPayment;
    }

    public String getCpWithdrawDetail() {
        return cpWithdrawDetail;
    }

    public void setCpWithdrawDetail(String cpWithdrawDetail) {
        this.cpWithdrawDetail = cpWithdrawDetail;
    }

    public String getCpBatchPaymentDetail() {
        return cpBatchPaymentDetail;
    }

    public void setCpBatchPaymentDetail(String cpBatchPaymentDetail) {
        this.cpBatchPaymentDetail = cpBatchPaymentDetail;
    }

    public String getCpTranInOutDetail() {
        return cpTranInOutDetail;
    }

    public void setCpTranInOutDetail(String cpTranInOutDetail) {
        this.cpTranInOutDetail = cpTranInOutDetail;
    }

    public String getCpWithdrawAccount() {
        return cpWithdrawAccount;
    }

    public void setCpWithdrawAccount(String cpWithdrawAccount) {
        this.cpWithdrawAccount = cpWithdrawAccount;
    }

    public String getTradeQueryAuth() {
        return tradeQueryAuth;
    }

    public void setTradeQueryAuth(String tradeQueryAuth) {
        this.tradeQueryAuth = tradeQueryAuth;
    }

    public String getBatchPaymentReview() {
        return batchPaymentReview;
    }

    public void setBatchPaymentReview(String batchPaymentReview) {
        this.batchPaymentReview = batchPaymentReview;
    }

    public String getCpBatchPaymentReview() {
        return cpBatchPaymentReview;
    }

    public void setCpBatchPaymentReview(String cpBatchPaymentReview) {
        this.cpBatchPaymentReview = cpBatchPaymentReview;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CtMerchantAuth [payerAccount=");
        builder.append(payerAccount);
        builder.append(", operatorCode=");
        builder.append(operatorCode);
        builder.append(", goodsTrade=");
        builder.append(goodsTrade);
        builder.append(", overseasPay=");
        builder.append(overseasPay);
        builder.append(", singleQuery=");
        builder.append(singleQuery);
        builder.append(", batchQuery=");
        builder.append(batchQuery);
        builder.append(", batchPaymentQuery=");
        builder.append(batchPaymentQuery);
        builder.append(", merchantHandle=");
        builder.append(merchantHandle);
        builder.append(", rateQuery=");
        builder.append(rateQuery);
        builder.append(", collAndSettle=");
        builder.append(collAndSettle);
        builder.append(", logisticsSettlement=");
        builder.append(logisticsSettlement);
        builder.append(", batchPaymentReview=");
        builder.append(batchPaymentReview);
        builder.append(", cpStoreHandle=");
        builder.append(cpStoreHandle);
        builder.append(", cpWithdrawApply=");
        builder.append(cpWithdrawApply);
        builder.append(", cpBatchPayment=");
        builder.append(cpBatchPayment);
        builder.append(", cpWithdrawDetail=");
        builder.append(cpWithdrawDetail);
        builder.append(", cpBatchPaymentDetail=");
        builder.append(cpBatchPaymentDetail);
        builder.append(", cpTranInOutDetail=");
        builder.append(cpTranInOutDetail);
        builder.append(", cpWithdrawAccount=");
        builder.append(cpWithdrawAccount);
        builder.append(", cpBatchPaymentReview=");
        builder.append(cpBatchPaymentReview);
        builder.append(", tradeQueryAuth=");
        builder.append(tradeQueryAuth);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((batchPaymentQuery == null) ? 0 : batchPaymentQuery.hashCode());
        result = prime * result + ((batchPaymentReview == null) ? 0 : batchPaymentReview.hashCode());
        result = prime * result + ((batchQuery == null) ? 0 : batchQuery.hashCode());
        result = prime * result + ((collAndSettle == null) ? 0 : collAndSettle.hashCode());
        result = prime * result + ((cpBatchPayment == null) ? 0 : cpBatchPayment.hashCode());
        result = prime * result + ((cpBatchPaymentDetail == null) ? 0 : cpBatchPaymentDetail.hashCode());
        result = prime * result + ((cpBatchPaymentReview == null) ? 0 : cpBatchPaymentReview.hashCode());
        result = prime * result + ((cpStoreHandle == null) ? 0 : cpStoreHandle.hashCode());
        result = prime * result + ((cpTranInOutDetail == null) ? 0 : cpTranInOutDetail.hashCode());
        result = prime * result + ((cpWithdrawAccount == null) ? 0 : cpWithdrawAccount.hashCode());
        result = prime * result + ((cpWithdrawApply == null) ? 0 : cpWithdrawApply.hashCode());
        result = prime * result + ((cpWithdrawDetail == null) ? 0 : cpWithdrawDetail.hashCode());
        result = prime * result + ((goodsTrade == null) ? 0 : goodsTrade.hashCode());
        result = prime * result + ((logisticsSettlement == null) ? 0 : logisticsSettlement.hashCode());
        result = prime * result + ((merchantHandle == null) ? 0 : merchantHandle.hashCode());
        result = prime * result + ((overseasPay == null) ? 0 : overseasPay.hashCode());
        result = prime * result + ((rateQuery == null) ? 0 : rateQuery.hashCode());
        result = prime * result + ((singleQuery == null) ? 0 : singleQuery.hashCode());
        result = prime * result + ((tradeQueryAuth == null) ? 0 : tradeQueryAuth.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CtMerchantAuth other = (CtMerchantAuth) obj;
        if (batchPaymentQuery == null) {
            if (other.batchPaymentQuery != null)
                return false;
        } else if (!batchPaymentQuery.equals(other.batchPaymentQuery))
            return false;
        if (batchPaymentReview == null) {
            if (other.batchPaymentReview != null)
                return false;
        } else if (!batchPaymentReview.equals(other.batchPaymentReview))
            return false;
        if (batchQuery == null) {
            if (other.batchQuery != null)
                return false;
        } else if (!batchQuery.equals(other.batchQuery))
            return false;
        if (collAndSettle == null) {
            if (other.collAndSettle != null)
                return false;
        } else if (!collAndSettle.equals(other.collAndSettle))
            return false;
        if (cpBatchPayment == null) {
            if (other.cpBatchPayment != null)
                return false;
        } else if (!cpBatchPayment.equals(other.cpBatchPayment))
            return false;
        if (cpBatchPaymentDetail == null) {
            if (other.cpBatchPaymentDetail != null)
                return false;
        } else if (!cpBatchPaymentDetail.equals(other.cpBatchPaymentDetail))
            return false;
        if (cpBatchPaymentReview == null) {
            if (other.cpBatchPaymentReview != null)
                return false;
        } else if (!cpBatchPaymentReview.equals(other.cpBatchPaymentReview))
            return false;
        if (cpStoreHandle == null) {
            if (other.cpStoreHandle != null)
                return false;
        } else if (!cpStoreHandle.equals(other.cpStoreHandle))
            return false;
        if (cpTranInOutDetail == null) {
            if (other.cpTranInOutDetail != null)
                return false;
        } else if (!cpTranInOutDetail.equals(other.cpTranInOutDetail))
            return false;
        if (cpWithdrawAccount == null) {
            if (other.cpWithdrawAccount != null)
                return false;
        } else if (!cpWithdrawAccount.equals(other.cpWithdrawAccount))
            return false;
        if (cpWithdrawApply == null) {
            if (other.cpWithdrawApply != null)
                return false;
        } else if (!cpWithdrawApply.equals(other.cpWithdrawApply))
            return false;
        if (cpWithdrawDetail == null) {
            if (other.cpWithdrawDetail != null)
                return false;
        } else if (!cpWithdrawDetail.equals(other.cpWithdrawDetail))
            return false;
        if (goodsTrade == null) {
            if (other.goodsTrade != null)
                return false;
        } else if (!goodsTrade.equals(other.goodsTrade))
            return false;
        if (logisticsSettlement == null) {
            if (other.logisticsSettlement != null)
                return false;
        } else if (!logisticsSettlement.equals(other.logisticsSettlement))
            return false;
        if (merchantHandle == null) {
            if (other.merchantHandle != null)
                return false;
        } else if (!merchantHandle.equals(other.merchantHandle))
            return false;
        if (overseasPay == null) {
            if (other.overseasPay != null)
                return false;
        } else if (!overseasPay.equals(other.overseasPay))
            return false;
        if (rateQuery == null) {
            if (other.rateQuery != null)
                return false;
        } else if (!rateQuery.equals(other.rateQuery))
            return false;
        if (singleQuery == null) {
            if (other.singleQuery != null)
                return false;
        } else if (!singleQuery.equals(other.singleQuery))
            return false;
        if (tradeQueryAuth == null) {
            if (other.tradeQueryAuth != null)
                return false;
        } else if (!tradeQueryAuth.equals(other.tradeQueryAuth))
            return false;
        return true;
    }  

}