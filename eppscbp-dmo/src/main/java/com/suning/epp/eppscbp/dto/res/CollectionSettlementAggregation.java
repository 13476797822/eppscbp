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
import com.suning.ftmc.util.hr.FrontHideBitRule;

public class CollectionSettlementAggregation {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ID
	private long id;
	
	// 订单号
	private String orderNo;
	
	// 商户号
	private String merchantAccount;
	
	// 业务类型
	private String bizType;
	
	// 交易类型
	private String transType;
	
	// 收款方名称
	private String receiverName;
	
	// 收款方银行卡号
	private String receiverCardNo;
	
	// 人民币金额
	private long rmbAmt;
	
	// 外币金额
	private long foreingAmt;
	
	// 币种
	private String cur;
	
	// 汇率
	private BigDecimal exchangeRate;
	
	// 手续费
	private long feeAmt;
	
	// 订单状态
	private String status;
	
	// 订单创建时间
	private Date orderCreateTime;
	
	// 入库时间
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

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverCardNo() {
		return receiverCardNo;
	}

	public void setReceiverCardNo(String receiverCardNo) {
		this.receiverCardNo = receiverCardNo;
	}
	
	public String getBatchInfoStr() {
		// 收款人姓名隐位姓
		if(!TransactionType.BATCH_PAY.getCode().equals(transType)) {
			return "";
		}
		if(!StringUtil.isEmpty(receiverName)) {
			if(receiverName.length()>2) {
				receiverName = "*"+receiverName.substring(receiverName.length()-2, receiverName.length());
			}else {
				receiverName = "*"+receiverName.substring(receiverName.length()-1, receiverName.length());
			}
		}else {
			receiverName = "*";
		}
		// 银行卡号截取
		if(!StringUtil.isEmpty(receiverCardNo)) {
			receiverCardNo = receiverCardNo.substring(receiverCardNo.length()-4, receiverCardNo.length());
		}else {
			receiverCardNo = "*";
		}
		return receiverName +"("+receiverCardNo+")";
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
		String foreingAmtStr = StringUtil.formatMoney(foreingAmt);
		String rmbAmtStr = StringUtil.formatMoney(rmbAmt);
		if(TransactionType.BATCH_PAY.getCode().equals(transType)) {
			// 1.批付: 人民币-
			amtStr = amtStr.append(CommonConstant.SUB_FLAG).append(rmbAmtStr);
		}else if(TransactionType.FOREIGN_SETTLEMENT.getCode().equals(transType)){
			// 2.外币结汇 :外币- 人民币+ 
			amtStr = amtStr.append(CommonConstant.SUB_FLAG).append(foreingAmtStr).append("\r\n").append(CommonConstant.ADD_FLAG).append(rmbAmtStr);
		}else if(TransactionType.FOREIGN_COLLECTION.getCode().equals(transType)) {
			// 3.外币收汇：外币+
			amtStr = amtStr.append(CommonConstant.ADD_FLAG).append(foreingAmtStr);
		}else if(TransactionType.RMB_COLLECTION.getCode().equals(transType)) {
			// 4.人民币收汇：人民币+
			amtStr = amtStr.append(CommonConstant.ADD_FLAG).append(rmbAmtStr);
		}
		return amtStr.toString();
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
		
		if(TransactionType.BATCH_PAY.getCode().equals(transType) || TransactionType.RMB_COLLECTION.getCode().equals(transType)) {
			// 1. 批付 /人民币收汇：人民币
			curStr = curStr.append(curDB);
		}else if(TransactionType.FOREIGN_SETTLEMENT.getCode().equals(transType)) {
			// 2. 外币结汇：外币，人民币 
			curStr = curStr.append(curDB).append("\r\n").append(CurType.CNY.getDescription());
		}else if(TransactionType.FOREIGN_COLLECTION.getCode().equals(transType)) {
			// 3.外币收汇：外币
			curStr = curStr.append(curDB);
		}
		return curStr.toString();
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

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	
	public String getOrderCreateTimeStr() {
		return DateUtil.formatDate(this.orderCreateTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
        return "CollectionSettlementAggregation [id=" + id + ", orderNo=" + orderNo+ ", receiptNo=" + receiptNo + ", merchantAccount=" + merchantAccount + ", bizType=" + bizType + ", transType=" + transType+ ", receiverName=" + FrontHideBitRule.nameHideForCN(receiverName) + ", receiverCardNo=" + FrontHideBitRule.bankCardNoHide(receiverCardNo) + ", rmbAmt=" + rmbAmt+ ", foreingAmt=" + foreingAmt+ ", cur=" + cur+ ", exchangeRate=" + exchangeRate+ ", feeAmt=" + feeAmt+ ", status=" + status+ ", orderCreateTime=" + orderCreateTime+ ", createTime=" + createTime+ ", serialNumber=" + serialNumber+ ", fileAddress=" + fileAddress+ "]";
    }
}
