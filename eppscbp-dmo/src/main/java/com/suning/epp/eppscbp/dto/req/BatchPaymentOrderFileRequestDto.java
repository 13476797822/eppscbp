package com.suning.epp.eppscbp.dto.req;

public class BatchPaymentOrderFileRequestDto {
	
	//文件地址
	private String fileAddress;
	//业务单号
	private String businessNo;
	
	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Override
    public String toString() {
        return "BatchPaymentOrderFileRequestDto [fileAddress=" + fileAddress + ", businessNo=" + businessNo +  "]";
    }
}
