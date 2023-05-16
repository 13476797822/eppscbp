package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;
import java.util.Date;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.enums.TradeOrderStatus;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

public class SaleOrderQueryRespDto {
	//商户订单号
	private String merchantOrderNo;
	
	//支付请求单号
	private String payNo;
	
	//订单创建时间
	private Date orderCreateTime;
	
	//支付完成时间
	private Date payFinishTime;

	//订单金额
	private Long amt;
	
	//汇率
	private BigDecimal exchangeRate;
	
	//清算金额
	private Long clearingAmount;
	
	//保证金
	private Long deposit;
	
	//订单状态
	private Integer orderStatus;
	
	//卡号
	private String cardNo;
	
	//手续费
	private Long fee;
	
	//商品名称
	private String productName;
	
	//外卡收单单号
	private String receiptOrderNo;
	
	//退款标识
	private String refundFlag;
	
	//币种
	private String cur;

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	
	public String getOrderCreateTimeStr() {
		String orderCreateTimeStr = DateUtil.formatDate(this.orderCreateTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(orderCreateTimeStr) ? CommonConstant.VALUE_IS_NULL: orderCreateTimeStr;
	}
	
	public Date getPayFinishTime() {
		return payFinishTime;
	}

	public void setPayFinishTime(Date payFinishTime) {
		this.payFinishTime = payFinishTime;
	}

	public String getPayFinishTimeStr() {
		String payFinishTimeStr = DateUtil.formatDate(this.payFinishTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(payFinishTimeStr) ? CommonConstant.VALUE_IS_NULL: payFinishTimeStr;
	}
	
	public Long getAmt() {
		return amt;
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}
	
	public String getAmtStr() {
		return StringUtil.formatMoney(amt)+" "+cur;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Long getClearingAmount() {
		return clearingAmount;
	}

	public void setClearingAmount(Long clearingAmount) {
		this.clearingAmount = clearingAmount;
	}
	
	public String getClearingAmountStr() {
		String clearingAmountStr = StringUtil.formatMoney(clearingAmount);
		return clearingAmount == null ? CommonConstant.VALUE_IS_NULL : clearingAmountStr+ " CNY";
	}

	public Long getDeposit() {
		return deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}
	
	public String getDepositStr() {
		String depositStr = StringUtil.formatMoney(deposit);
		return deposit == null ? CommonConstant.VALUE_IS_NULL : depositStr + " CNY";
	}
	
	public Integer getOrderStatus() {
		return orderStatus;
	}
	
	public String getOrderStatusStr() {
		return TradeOrderStatus.getDescriptionFromCode(orderStatus);
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getFee() {
		return fee;
	}
	
	public String getFeeStr() {
		String feeStr = StringUtil.formatMoney(fee);
		return fee == null ? CommonConstant.VALUE_IS_NULL : feeStr+" CNY";
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getReceiptOrderNo() {
		return receiptOrderNo;
	}

	public void setReceiptOrderNo(String receiptOrderNo) {
		this.receiptOrderNo = receiptOrderNo;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	@Override
    public String toString() {
        return "SaleOrderQueryRespDto{" +
                "merchantOrderNo='" + merchantOrderNo + '\'' +
                ", payNo='" + payNo + '\'' +
                ", orderCreateTime='" + orderCreateTime + '\'' +
                ", payFinishTime='" + payFinishTime + '\'' +
                ", amt='" + amt + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                ", clearingAmount='" + clearingAmount + '\'' +
                ", deposit='" + deposit + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", fee='" + fee + '\'' +
                ", productName='" + productName + '\'' +
                ", receiptOrderNo='" + receiptOrderNo + '\'' +
                 ", refundFlag='" + refundFlag + '\'' +
                 ", cur='" + cur + '\'' +
                '}';
    }
	
}
