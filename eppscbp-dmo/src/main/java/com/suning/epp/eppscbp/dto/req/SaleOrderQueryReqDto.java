package com.suning.epp.eppscbp.dto.req;

public class SaleOrderQueryReqDto {

	//商户户头号
	private String payerAccount;
	
	//商户订单号
	private String merchantOrderNo;
	
	//收单单号
	private String receiptOrderNo;
	
	//订单状态
	private String orderStatus;
	
	//订单币种
	private String cur;
	
	//订单创建开始时间
	private String orderCreateFromTime;
	
	//订单创建结束时间
	private String orderCreateToTime;
	
	//支付完成开始时间
	private String payFinishFromTime;
	
	//支付完成结束时间
	private String payFinishToTime;
	
	//展示条数
	private String pageSize;
	
	//当前页
	private String pageNo;
	
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

	public String getReceiptOrderNo() {
		return receiptOrderNo;
	}

	public void setReceiptOrderNo(String receiptOrderNo) {
		this.receiptOrderNo = receiptOrderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public String getOrderCreateFromTime() {
		return orderCreateFromTime;
	}

	public void setOrderCreateFromTime(String orderCreateFromTime) {
		this.orderCreateFromTime = orderCreateFromTime;
	}

	public String getOrderCreateToTime() {
		return orderCreateToTime;
	}

	public void setOrderCreateToTime(String orderCreateToTime) {
		this.orderCreateToTime = orderCreateToTime;
	}

	public String getPayFinishFromTime() {
		return payFinishFromTime;
	}

	public void setPayFinishFromTime(String payFinishFromTime) {
		this.payFinishFromTime = payFinishFromTime;
	}

	public String getPayFinishToTime() {
		return payFinishToTime;
	}

	public void setPayFinishToTime(String payFinishToTime) {
		this.payFinishToTime = payFinishToTime;
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

	@Override
    public String toString() {
        return "SaleOrderQueryReqDto{" +
                "payerAccount='" + payerAccount + '\'' +
                ", receiptOrderNo='" + receiptOrderNo + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", cur='" + cur + '\'' +
                ", orderCreateFromTime='" + orderCreateFromTime + '\'' +
                ", orderCreateToTime='" + orderCreateToTime + '\'' +
                ", payFinishFromTime='" + payFinishFromTime + '\'' +
                ", payFinishToTime='" + payFinishToTime + '\'' +
                 ", pageSize='" + pageSize + '\'' +
                ", pageNo='" + pageNo + '\'' +
                '}';
    }
}
