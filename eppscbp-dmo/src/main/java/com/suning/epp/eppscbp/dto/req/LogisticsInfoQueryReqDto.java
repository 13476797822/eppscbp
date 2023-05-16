package com.suning.epp.eppscbp.dto.req;

public class LogisticsInfoQueryReqDto {
	//商户户头号
	private String payerAccount;
	//商户订单号
	private String merchantOrderNo;
	//物流状态
	private String logisticsStatus;
	//支付开始时间
	private String payFromTime;
	//支付结束时间
	private String payToTime;
	//上传开始时间
	private String uploadFromTime;
	//上传结束时间
	private String uploadToTime;
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

	public String getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public String getPayFromTime() {
		return payFromTime;
	}

	public void setPayFromTime(String payFromTime) {
		this.payFromTime = payFromTime;
	}

	public String getPayToTime() {
		return payToTime;
	}

	public void setPayToTime(String payToTime) {
		this.payToTime = payToTime;
	}

	public String getUploadFromTime() {
		return uploadFromTime;
	}

	public void setUploadFromTime(String uploadFromTime) {
		this.uploadFromTime = uploadFromTime;
	}

	public String getUploadToTime() {
		return uploadToTime;
	}

	public void setUploadToTime(String uploadToTime) {
		this.uploadToTime = uploadToTime;
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
        return "RefundOrderQueryRespDto{" +
                "logisticsStatus='" + logisticsStatus + '\'' +
                ", payFromTime='" + payFromTime + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", payToTime='" + payToTime + '\'' +
                ", uploadFromTime='" + uploadFromTime + '\'' +
                ", uploadToTime='" + uploadToTime + '\'' +
                 ", pageSize='" + pageSize + '\'' +
                ", pageNo='" + pageNo + '\'' +
                '}';
    }
}
