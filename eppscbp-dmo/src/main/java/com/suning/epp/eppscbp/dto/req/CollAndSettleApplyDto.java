/**
 * 
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * 
 * 〈收结汇订单提交dto〉<br>
 * 〈功能详细描述〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CollAndSettleApplyDto {

    /**
     * 平台标识
     */
    private String platformCode;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 境外商户号
     */
    private String payeeMerchantCode;
    /**
     * 付款方商户户头号
     */
    private String payerAccount;
    /**
     * 待解付资金流水号
     */
    private String payNo;
    /**
     * 待解付资金ID
     */
    private String arrivalNoticeId;
    /**
     * 境外账号
     */
    private String payeeBankCard;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 收结汇币种
     */
    private String currency;
    /**
     * 收结汇金额
     */
    private String receiveAmt;
    /**
     * 明细文件路径
     */
    private String fileAddress;
    /**
     * 明细笔数
     */
    private String detailAmount;
    /**
     * 参考汇率
     */
    private String referenceRate;
    /**
     * 二代PC指纹
     */
    private String pcToken; 
    
    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 国际物流进出境运输类型
     */
    private String logisticsType;

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
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

    public String getPayeeBankCard() {
        return payeeBankCard;
    }

    public void setPayeeBankCard(String payeeBankCard) {
        this.payeeBankCard = payeeBankCard;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceiveAmt() {
        return receiveAmt;
    }

    public void setReceiveAmt(String receiveAmt) {
        this.receiveAmt = receiveAmt;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(String detailAmount) {
        this.detailAmount = detailAmount;
    }

    public String getReferenceRate() {
        return referenceRate;
    }

    public void setReferenceRate(String referenceRate) {
        this.referenceRate = referenceRate;
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
        return "CollAndSettleApplyDto{" +
                "platformCode='" + platformCode + '\'' +
                ", productType='" + productType + '\'' +
                ", payeeMerchantCode='" + payeeMerchantCode + '\'' +
                ", payerAccount='" + payerAccount + '\'' +
                ", payNo='" + payNo + '\'' +
                ", arrivalNoticeId='" + arrivalNoticeId + '\'' +
                ", payeeBankCard='" + payeeBankCard + '\'' +
                ", bizType='" + bizType + '\'' +
                ", currency='" + currency + '\'' +
                ", receiveAmt='" + receiveAmt + '\'' +
                ", fileAddress='" + fileAddress + '\'' +
                ", detailAmount='" + detailAmount + '\'' +
                ", referenceRate='" + referenceRate + '\'' +
                ", pcToken='" + pcToken + '\'' +
                ", platformName='" + platformName + '\'' +
                ", logisticsType='" + logisticsType + '\'' +
                '}';
    }

}
