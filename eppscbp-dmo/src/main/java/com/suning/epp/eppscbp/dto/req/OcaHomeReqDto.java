package com.suning.epp.eppscbp.dto.req;

public class OcaHomeReqDto {
	//商户户头号
	private String payerAccount;
	
	//查询周期开始时间
	private String fromTime;
	
	//查询周期结束时间
	private String toTime;
	
	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	 @Override
	    public String toString() {
	        return "OcaHomeReqDto [payerAccount=" + payerAccount + ", fromTime=" + fromTime + ", toTime=" + toTime + "]";
	    }
}
