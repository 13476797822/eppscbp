package com.suning.epp.eppscbp.dto.res;
/**
 * 批量导入批付文件响应
 * @author 19043747
 *
 */
import java.util.List;

import com.suning.epp.eppscbp.dto.req.OrderCalculateDetailDto;

public class BatchPaymentOrderQueryAndCalculateResDto {
	//明细数据
	private List<OrderCalculateDetailDto> details;
	
	//文件地址
	private String fileAddress;
	
	//业务单号
	private String businessNo;
	
	// 金额总数
    private String amountCount;

    // 总笔数
    private String numberCount;
    
    public List<OrderCalculateDetailDto> getDetails() {
		return details;
	}

	public void setDetails(List<OrderCalculateDetailDto> details) {
		this.details = details;
	}

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

	public String getAmountCount() {
		return amountCount;
	}

	public void setAmountCount(String amountCount) {
		this.amountCount = amountCount;
	}

	public String getNumberCount() {
		return numberCount;
	}

	public void setNumberCount(String numberCount) {
		this.numberCount = numberCount;
	}

	@Override
    public String toString() {
        return "BatchPaymentOrderQueryAndCalculateResDto [details=" + details + ", fileAddress=" + fileAddress + ", businessNo=" + businessNo + ", amountCount=" + amountCount + ", numberCount=" + numberCount + "]";
    }

}
