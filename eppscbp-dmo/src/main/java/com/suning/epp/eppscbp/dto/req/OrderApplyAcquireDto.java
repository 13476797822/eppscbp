package com.suning.epp.eppscbp.dto.req;
/**
 * 
 * 〈订单提交dto〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class OrderApplyAcquireDto {

    /**
     * 平台标识
     */
    private String platformCode;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 全额到账标识
     */
    private String amtType;

    /**
     * 付款方户头号
     */
    private String payerAccount;

    /**
     * 收款方商户编码
     */
    private String payeeMerchantCode;
    
    /**
     * 收款方填写的信息
     */
    private String payMes;

    /**
     * 国内外费用承担
     */
    private String feeFlg;

    /**
     * 收款人开户行的账户行swift code
     */
    private String payeeBankSwift;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务细类
     */
    private String bizDetailType;

    /**
     * 交易性质
     */
    private String tradeMode;

    /**
     * 指定购汇方式
     */
    private String exchangeType;

    /**
     * 申请金额
     */
    private double payAmt;
    
    /**
     * 申请金额币种
     */
    private String cur;
    
    /**
     * 申请金额币种名称
     */
    private String curName;

    /**
     * 结算明细文件路径
     */
    private String fileAddress;
    
    /**
     * 结算明细显示
     */
    private String filePath;

    /**
     * 明细笔数
     */
    private int detailAmount;

    /**
     * 交易附言
     */
    private String remark;     
    
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

    /**
     * @return the curName
     */
    public String getCurName() {
        return curName;
    }

    /**
     * @param curName the curName to set
     */
    public void setCurName(String curName) {
        this.curName = curName;
    }

    /**
     * @return the cur
     */
    public String getCur() {
        return cur;
    }

    /**
     * @param cur the cur to set
     */
    public void setCur(String cur) {
        this.cur = cur;
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

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

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

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getFeeFlg() {
        return feeFlg;
    }

    public void setFeeFlg(String feeFlg) {
        this.feeFlg = feeFlg;
    }

    public String getPayeeBankSwift() {
        return payeeBankSwift;
    }

    public void setPayeeBankSwift(String payeeBankSwift) {
        this.payeeBankSwift = payeeBankSwift;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizDetailType() {
        return bizDetailType;
    }

    public void setBizDetailType(String bizDetailType) {
        this.bizDetailType = bizDetailType;
    }

    public String getTradeMode() {
        return tradeMode;
    }

    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public double getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(double payAmt) {
        this.payAmt = payAmt;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public int getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(int detailAmount) {
        this.detailAmount = detailAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the payMes
     */
    public String getPayMes() {
        return payMes;
    }

    /**
     * @param payMes the payMes to set
     */
    public void setPayMes(String payMes) {
        this.payMes = payMes;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
        return "OrderApplyAcquireDto{" +
                "platformCode='" + platformCode + '\'' +
                ", productType='" + productType + '\'' +
                ", amtType='" + amtType + '\'' +
                ", payerAccount='" + payerAccount + '\'' +
                ", payeeMerchantCode='" + payeeMerchantCode + '\'' +
                ", payMes='" + payMes + '\'' +
                ", feeFlg='" + feeFlg + '\'' +
                ", payeeBankSwift='" + payeeBankSwift + '\'' +
                ", bizType='" + bizType + '\'' +
                ", bizDetailType='" + bizDetailType + '\'' +
                ", tradeMode='" + tradeMode + '\'' +
                ", exchangeType='" + exchangeType + '\'' +
                ", payAmt=" + payAmt +
                ", cur='" + cur + '\'' +
                ", curName='" + curName + '\'' +
                ", fileAddress='" + fileAddress + '\'' +
                ", filePath='" + filePath + '\'' +
                ", detailAmount=" + detailAmount +
                ", remark='" + remark + '\'' +
                ", pcToken='" + pcToken + '\'' +
                ", platformName='" + platformName + '\'' +
                ", logisticsType='" + logisticsType + '\'' +
                '}';
    }
}
