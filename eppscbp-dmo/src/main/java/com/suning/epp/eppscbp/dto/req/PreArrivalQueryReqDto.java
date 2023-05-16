package com.suning.epp.eppscbp.dto.req;

import javax.validation.constraints.Size;

/**
 * 来账信息查询
 */
public class PreArrivalQueryReqDto {

	/**
	 * 平台标识
	 */
	private String platformCode;

	/**
	 * 商户户头号
	 */
	@Size(max = 19, message = "商户户头号长度不能超过{max}位")
	private String payerAccount;

	/**
	 * 待解付资金流水号
	 */
	@Size(max = 30, message = "待解付资金流水号长度不能超过{max}位")
	private String payNo;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 来帐通知时间
	 */
	private String frNoticeTime;

	/**
	 * 来帐通知时间
	 */
	private String toNoticeTime;

	/**
	 * 每页条数
	 */
	private String pageSize;

	/**
	 * 页码
	 */
	private String currentPage;

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFrNoticeTime() {
		return frNoticeTime;
	}

	public void setFrNoticeTime(String frNoticeTime) {
		this.frNoticeTime = frNoticeTime;
	}

	public String getToNoticeTime() {
		return toNoticeTime;
	}

	public void setToNoticeTime(String toNoticeTime) {
		this.toNoticeTime = toNoticeTime;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public String toString() {
		return "PreArrivalQueryReqDto{" +
				"platformCode='" + platformCode + '\'' +
				", payerAccount='" + payerAccount + '\'' +
				", payNo='" + payNo + '\'' +
				", status='" + status + '\'' +
				", frNoticeTime='" + frNoticeTime + '\'' +
				", toNoticeTime='" + toNoticeTime + '\'' +
				", pageSize='" + pageSize + '\'' +
				", currentPage='" + currentPage + '\'' +
				'}';
	}
}
