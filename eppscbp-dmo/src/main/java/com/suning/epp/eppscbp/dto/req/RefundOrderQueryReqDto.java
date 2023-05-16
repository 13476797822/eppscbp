package com.suning.epp.eppscbp.dto.req;

public class RefundOrderQueryReqDto {
	//商户户头号
	private String payerAccount;
	//商户订单号
	private String merchantOrderNo;
	//退款订单号
	private String refundOrderNo;
	//退款状态
	private String refundStatus;
	//退款创建时间开始
	private String refundCreateFromTime;
	//退款创建时间结束
	private String refundCreateToTime;
	//退款完成时间开始
	private String refundFinishFromTime;
	//退款完成时间结束
	private String refundFinishToTime;
	//展示条数
	private String pageSize;
	//当前页
	private String pageNo;
	
	//收单单号
	private String receiptOrderNo;

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundCreateFromTime() {
		return refundCreateFromTime;
	}

	public void setRefundCreateFromTime(String refundCreateFromTime) {
		this.refundCreateFromTime = refundCreateFromTime;
	}

	public String getRefundCreateToTime() {
		return refundCreateToTime;
	}

	public void setRefundCreateToTime(String refundCreateToTime) {
		this.refundCreateToTime = refundCreateToTime;
	}

	public String getRefundFinishFromTime() {
		return refundFinishFromTime;
	}

	public void setRefundFinishFromTime(String refundFinishFromTime) {
		this.refundFinishFromTime = refundFinishFromTime;
	}

	public String getRefundFinishToTime() {
		return refundFinishToTime;
	}

	public void setRefundFinishToTime(String refundFinishToTime) {
		this.refundFinishToTime = refundFinishToTime;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getReceiptOrderNo() {
		return receiptOrderNo;
	}

	public void setReceiptOrderNo(String receiptOrderNo) {
		this.receiptOrderNo = receiptOrderNo;
	}

	@Override
    public String toString() {
        return "RefundOrderQueryReqDto{" +
                "payerAccount='" + payerAccount + '\'' +
                ", refundOrderNo='" + refundOrderNo + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", refundCreateFromTime='" + refundCreateFromTime + '\'' +
                ", refundCreateToTime='" + refundCreateToTime + '\'' +
                ", refundFinishFromTime='" + refundFinishFromTime + '\'' +
                ", refundFinishToTime='" + refundFinishToTime + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", pageNo='" + pageNo + '\'' +
                ", receiptOrderNo='" + receiptOrderNo + '\'' +
                
                '}';
    }
}
