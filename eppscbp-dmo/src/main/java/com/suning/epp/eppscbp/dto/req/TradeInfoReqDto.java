package com.suning.epp.eppscbp.dto.req;

import com.suning.ftmc.util.hr.FrontHideBitRule;

public class TradeInfoReqDto {
	// 户头号
	private String merchantAccount;
	
	// 收结汇/购付汇标识
	private String flag;
	
	// 订单日期开始
	private String orderCreateTimeStart;
	
	// 订单日期结束
	private String orderCreateTimeEnd;
	
	// 批付姓名
	private String receiverName;
	
	// 批付账号
	private String receiverCardNo;
	
	// 金额开始
	private String amtStart;
	
	// 金额结束
	private String amtEnd;
	
	// 业务类型
	private String bizType;
	
	// 订单状态
	private String status;
	
	// 订单号
	private String orderNo;
	
	// 交易类型
	private String transType;
	
	// 导出 勾选列表checkbox 时，传的id
	private String id;
	
	// 汇总电子回单 勾选李彪checkbox时，传的fileAddress
	private String fileAddress;
	
	/**
     * 当前页
     */
    private String currentPage;

    /**
     * 分页大小 ,批量查询使用
     */
    private Integer pageSize;
	
	public String getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOrderCreateTimeStart() {
		return orderCreateTimeStart;
	}

	public void setOrderCreateTimeStart(String orderCreateTimeStart) {
		this.orderCreateTimeStart = orderCreateTimeStart;
	}

	public String getOrderCreateTimeEnd() {
		return orderCreateTimeEnd;
	}

	public void setOrderCreateTimeEnd(String orderCreateTimeEnd) {
		this.orderCreateTimeEnd = orderCreateTimeEnd;
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

	public String getAmtStart() {
		return amtStart;
	}

	public void setAmtStart(String amtStart) {
		this.amtStart = amtStart;
	}

	public String getAmtEnd() {
		return amtEnd;
	}

	public void setAmtEnd(String amtEnd) {
		this.amtEnd = amtEnd;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	@Override
    public String toString() {
        return "TradeInfoReqDto [merchantAccount=" + merchantAccount + ", flag=" + flag + ", orderCreateTimeStart=" + orderCreateTimeStart + ", orderCreateTimeEnd=" + orderCreateTimeEnd+ ", receiverName=" + FrontHideBitRule.nameHideForCN(receiverName) + ", receiverCardNo=" + FrontHideBitRule.bankCardNoHide(receiverCardNo) + ", amtStart=" + amtStart + ", amtEnd=" + amtEnd + ", status=" + status + ", orderNo=" + orderNo + ", transType=" + transType +", bizType=" + bizType +", id=" + id +", fileAddress=" + fileAddress+ "]";
    }
	

}
