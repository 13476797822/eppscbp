package com.suning.epp.eppscbp.dto;

public class OcaLogisticsInfoFileCheckFail {

	//商户订单号
	private String merchantOrderNo;
	
	//发货日期
	private String shippingDate;
	
	//物流公司名称
	private String logisticsCompName;
	
	//物流单号
	private String logisticsNo;
	
	//错误原因
	private String failReason;

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getLogisticsCompName() {
		return logisticsCompName;
	}

	public void setLogisticsCompName(String logisticsCompName) {
		this.logisticsCompName = logisticsCompName;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	@Override
    public String toString() {
        return "OcaLogisticsInfoFileCheckFail{" +
                "merchantOrderNo='" + merchantOrderNo + '\'' +
                ", shippingDate='" + shippingDate + '\'' +
                ", logisticsCompName='" + logisticsCompName + '\'' +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", failReason='" + failReason + '\'' +
                '}';
    }
}
