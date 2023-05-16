package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;
import java.util.Date;

import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

public class SaleOrderDetailQueryRespDto {
	 //商户订单号
	 private String merchantOrderNo;
	
	 //支付请求单号
	 private String payNo;
	
	 //订单创建时间
	 private Date createTime;
	
	 //支付完成时间
	 private Date payFinishTime;
	 
	 //汇率
	 private BigDecimal exchangeRate;
	 
	 //订单金额
	 private Long amt;
	 
	 //订单人民币金额
	 private Long amtCny;
	 
	//清算金额
	 private Long clearingAmount;
	 
	 //保证金
	 private Long deposit;
	 
	 //手续费
	 private Long fee;
	 
	 //商品名称
	 private String productName;
	 
	 //卡号
	 private String cardNo;
	
	 //标志
	 private String success;
	
	 //已退款金额
	 private Long refundedAmt;
	 
	 //已退款人民币金额
	 private Long refundedAmtCny;
	 
	 //退款记录
	 private int record;
	 
	 //收单单号
	 private String receiptOrderNo;
	 
	 //币种
	 private String cur;

	 // 失败原因
	private String failreason;

	//手续费
	private Long certificationFee3DS;
	 
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayFinishTime() {
		return payFinishTime;
	}

	public void setPayFinishTime(Date payFinishTime) {
		this.payFinishTime = payFinishTime;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Long getAmt() {
		return amt;
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}

	public Long getClearingAmount() {
		return clearingAmount;
	}

	public void setClearingAmount(Long clearingAmount) {
		this.clearingAmount = clearingAmount;
	}

	public Long getDeposit() {
		return deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}

	public Long getFee() {
		return fee;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getRefundedAmt() {
		return refundedAmt;
	}

	public void setRefundedAmt(Long refundedAmt) {
		this.refundedAmt = refundedAmt;
	}

	public Long getRefundedAmtCny() {
		return refundedAmtCny;
	}

	public void setRefundedAmtCny(Long refundedAmtCny) {
		this.refundedAmtCny = refundedAmtCny;
	}

	public Long getAmtCny() {
		return amtCny;
	}

	public void setAmtCny(Long amtCny) {
		this.amtCny = amtCny;
	}

	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

	public String getReceiptOrderNo() {
		return receiptOrderNo;
	}

	public void setReceiptOrderNo(String receiptOrderNo) {
		this.receiptOrderNo = receiptOrderNo;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	//格式化
	public String getCreateTimeStr() {
		return DateUtil.formatDate(this.createTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
	}
	public String getPayFinishTimeStr() {
		return DateUtil.formatDate(this.payFinishTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
	}
	public String getAmtStr() {
		return StringUtil.formatMoney(amt)+" "+cur;
	}
	
	public String getClearingAmountStr() {
		return StringUtil.formatMoney(clearingAmount)+" CNY";
	}
	
	public String getDepositStr() {
		return StringUtil.formatMoney(deposit)+" CNY";
	}
	
	public String getRefundAmtStr() {
		return StringUtil.formatMoney(this.amt-this.refundedAmt);
	}
	
	public String getRefundAmtCnyStr() {
		return StringUtil.formatMoney(this.amtCny-this.refundedAmtCny);
	}
	
	public String getFeeStr() {
		return StringUtil.formatMoney(fee)+" CNY";
	}

	public String getFailreason() {
		return failreason;
	}

	public void setFailreason(String failreason) {
		this.failreason = failreason;
	}

	public Long getCertificationFee3DS() {
		return certificationFee3DS;
	}

	public String getCertificationFee3DSStr() {
		return StringUtil.formatMoney(certificationFee3DS)+" CNY";
	}

	public void setCertificationFee3DS(Long certificationFee3DS) {
		this.certificationFee3DS = certificationFee3DS;
	}

	@Override
    public String toString() {
        return "SaleOrderDetailQueryRespDto{" +
                "merchantOrderNo='" + merchantOrderNo + '\'' +
                ", payNo='" + payNo + '\'' +
                ", createTime='" + createTime + '\'' +
                ", payFinishTime='" + payFinishTime + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                ", amt='" + amt + '\'' +
                ", amtCny='" + amtCny + '\'' +
                ", clearingAmount='" + clearingAmount + '\'' +
                ", deposit='" + deposit + '\'' +
                ", fee='" + fee + '\'' +
                ", productName='" + productName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", success='" + success + '\'' +
                ", refundedAmt='" + refundedAmt + '\'' +
                ", refundedAmtCny='" + refundedAmtCny + '\'' +
                ", record='" + record + '\'' +
				", failreason='" + failreason + '\'' +
                ", receiptOrderNo='" + receiptOrderNo + '\'' +
                ", cur='" + cur + '\'' +
                '}';
    }
}
