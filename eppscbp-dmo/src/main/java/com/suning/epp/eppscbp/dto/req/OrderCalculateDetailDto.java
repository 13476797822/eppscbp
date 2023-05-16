package com.suning.epp.eppscbp.dto.req;

public class OrderCalculateDetailDto {

    /**
     * 商户会员户头号
     */
    private String payeeMerchantCode;

    /**
     * 商户会员名称
     */
    private String payeeMerchantName;

    /**
     * 银行账号
     */
    private String bankAccountNo;

    /**
     * 出款金额
     */
    private String amount;

    /**
     * 出款笔数
     */
    private String number;

    /**
     * 附言--订单名称
     */
    private String orderName;

    public OrderCalculateDetailDto() {
        super();
    }

    /**
     * @param payeeMerchantCode
     * @param payeeMerchantName
     * @param bankAccountNo
     */
    public OrderCalculateDetailDto(OrderCalculateDetailDto dto) {
        super();
        this.payeeMerchantCode = dto.payeeMerchantCode;
        this.payeeMerchantName = dto.payeeMerchantName;
        this.bankAccountNo = dto.bankAccountNo;
        this.orderName = dto.orderName;
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

    public String getPayeeMerchantName() {
        return payeeMerchantName;
    }

    public void setPayeeMerchantName(String payeeMerchantCode) {
        this.payeeMerchantName = payeeMerchantCode;
    }

    /**
     * @return the bankAccountNo
     */
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    /**
     * @param bankAccountNo the bankAccountNo to set
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        return "OrderCalculateDetailDto [payeeMerchantCode=" + payeeMerchantCode + ", payeeMerchantName=" + payeeMerchantName + ", bankAccountNo=" + bankAccountNo + ", amount=" + amount + ", number=" + number + ", orderName=" + orderName + "]";
    }

}
