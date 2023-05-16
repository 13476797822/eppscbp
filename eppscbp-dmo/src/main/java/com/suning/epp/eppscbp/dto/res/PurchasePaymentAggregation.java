package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;
import java.util.Date;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.enums.AggregateOrderStatus;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.TransactionType;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

public class PurchasePaymentAggregation {
	// ID
	private long id;
	
	// 订单号
	private String orderNo;
	
	// 易付宝会员户头号
	private String merchantAccount;
	
	// 业务类型
	private String bizType;
	
	// 交易类型
	private String transType;
	
	// 人民币金额
	private long rmbAmt;
	
	// 外币金额
	private long foreingAmt;
	
	// 金额
	private long amt;
	
	// 汇率
	private BigDecimal exchangeRate;
	
	// 手续费
	private long feeAmt;
	
	// 订单状态
	private String status;
	
	//币种
	private String cur;
	
	// 订单创建时间
	private Date orderCreateTime;
	
	// 入表时间
	private Date createTime;
	
	// 电子回单编号
	private String serialNumber;
	
	// 电子回单地址
	private String fileAddress;
	
	// 工单号
	private String receiptNo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	public String getTransTypeStr() {
		return TransactionType.getDescriptionFromCode(transType);
	}

	public long getRmbAmt() {
		return rmbAmt;
	}

	public void setRmbAmt(long rmbAmt) {
		this.rmbAmt = rmbAmt;
	}

	public long getForeingAmt() {
		return foreingAmt;
	}

	public void setForeingAmt(long foreingAmt) {
		this.foreingAmt = foreingAmt;
	}
	
	public String getAmtStr() {
		StringBuffer amtStr = new StringBuffer();
		String rmbAmtStr = StringUtil.formatMoney(rmbAmt);
		String foreingAmtStr = StringUtil.formatMoney(foreingAmt);
		if(TransactionType.FOREIGN_PAYMENT.getCode().equals(transType) || TransactionType.FOREIGN_REEXCHANGE_REPAY.getCode().equals(transType)) {
			// 1. 外币付汇/外币退汇重付 ：外币-
			amtStr = amtStr.append(CommonConstant.SUB_FLAG).append(foreingAmtStr);
		}else if(TransactionType.PURCHASE.getCode().equals(transType)) {
			// 2. 购汇：人民币- ，外币+
			amtStr = amtStr.append(CommonConstant.SUB_FLAG).append(rmbAmtStr).append("\r\n")
					.append(CommonConstant.ADD_FLAG).append(foreingAmtStr);
		}else if(TransactionType.RMB_REEXCHANGE.getCode().equals(transType)) {
			// 3.人民币退汇：人民币+
			amtStr = amtStr.append(CommonConstant.ADD_FLAG).append(rmbAmtStr);
		}else if(TransactionType.RMB_PAYMENT.getCode().equals(transType) || TransactionType.RMB_REEXCHANGE_REPAY.getCode().equals(transType)) {
			// 4.人民币付汇/人民币外币退汇重付：人民币-
			amtStr = amtStr.append(CommonConstant.SUB_FLAG).append(rmbAmtStr);
		}else if(TransactionType.FOREIGN_REEXCHANGE.getCode().equals(transType) ) {
			// 5.外币退汇 ：外币+
			amtStr = amtStr.append(CommonConstant.ADD_FLAG).append(foreingAmtStr);
		}
		return amtStr.toString();
	}

	public long getAmt() {
		return amt;
	}

	public void setAmt(long amt) {
		this.amt = amt;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public long getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(long feeAmt) {
		this.feeAmt = feeAmt;
	}
	
	public String getFeeAmtStr() {
		return StringUtil.formatMoney(feeAmt);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusStr() {
		return AggregateOrderStatus.getDescriptionFromCode(status);
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}
	public String getCurStr() {
		StringBuffer curStr = new StringBuffer();
		String curDB = CurType.getDescriptionFromCode(cur);
		
		if(TransactionType.FOREIGN_PAYMENT.getCode().equals(transType) || TransactionType.FOREIGN_REEXCHANGE.getCode().equals(transType)|| TransactionType.FOREIGN_REEXCHANGE_REPAY.getCode().equals(transType)) {
			// 1. 外币付汇 /外币退汇：外币
			curStr = curStr.append(curDB);
		}else if(TransactionType.PURCHASE.getCode().equals(transType)) {
			// 2. 购汇：人民币 ，外币
			curStr = curStr.append(CurType.CNY.getDescription()).append("\r\n").append(curDB);
		}else if(TransactionType.RMB_REEXCHANGE.getCode().equals(transType) || TransactionType.RMB_PAYMENT.getCode().equals(transType)|| TransactionType.RMB_REEXCHANGE_REPAY.getCode().equals(transType)) {
			// 3.人民币退汇/人民币付汇：人民币
			curStr = curStr.append(curDB);
		}
		return curStr.toString();
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderCreateTimeStr() {
		return DateUtil.formatDate(this.orderCreateTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
	}
	public String getCreateTimeStr() {
		return DateUtil.formatDate(this.createTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
	}
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Override
    public String toString() {
        return "PurchasePaymentAggregation [id=" + id + ", orderNo=" + orderNo+ ", receiptNo=" + receiptNo + ", merchantAccount=" + merchantAccount + ", bizType=" + bizType + ", transType=" + transType+", rmbAmt=" + rmbAmt+ ", foreingAmt=" + foreingAmt+ ", cur=" + cur+ ", exchangeRate=" + exchangeRate+ ", feeAmt=" + feeAmt+ ", status=" + status+ ", orderCreateTime=" + orderCreateTime+ ", createTime=" + createTime+ ", serialNumber=" + serialNumber+ ", fileAddress=" + fileAddress+ "]";
    }
	
}
