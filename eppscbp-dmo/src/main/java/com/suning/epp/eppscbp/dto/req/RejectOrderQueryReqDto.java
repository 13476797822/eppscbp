package com.suning.epp.eppscbp.dto.req;

public class RejectOrderQueryReqDto {
	//商户户头号
	private String payerAccount;
	
	//商户订单号
	private String merchantOrderNo;
	
	//拒付业务单号
	private String rejectOrderNo;
	
	//拒付状态
	private String status;
	
	//拒付创建时间开始
	private String createTimeStart;
	
	//拒付创建时间结束
	private String createTimeEnd;
	
	//拒付完成时间开始
	private String updateTimeStart;
	
	//拒付完成时间结束
	private String updateTimeEnd;
	
	//页数
	private String pageSize;
	
	//第几页
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

	public String getRejectOrderNo() {
		return rejectOrderNo;
	}

	public void setRejectOrderNo(String rejectOrderNo) {
		this.rejectOrderNo = rejectOrderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(String updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public String getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(String updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
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
        return "RejectOrderQueryReqDto{" +
                "payerAccount='" + payerAccount + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", rejectOrderNo='" + rejectOrderNo + '\'' +
                ", status='" + status + '\'' +
                ", createTimeStart='" + createTimeStart + '\'' +
                ", createTimeEnd='" + createTimeEnd + '\'' +
                ", updateTimeStart='" + updateTimeStart + '\'' +
                ", updateTimeEnd='" + updateTimeEnd + '\'' +
                '}';
    }

}
