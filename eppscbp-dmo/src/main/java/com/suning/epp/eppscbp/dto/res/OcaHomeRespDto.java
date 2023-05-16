package com.suning.epp.eppscbp.dto.res;

public class OcaHomeRespDto {
	//拒付笔数
	private int  rejectOrderNum;
	//在途资金
	private Long  onTheWayMoney;
	//销售成功笔数
	private int saleSuccessCount;
	
	//退款成功笔数
	private int refundSuccessCount;
	
	//拒付交易笔数
	private int rejectSuccessCount;
	
	//销售成功金额
	private Long saleSuccessAmount;
	
	//退款成功金额
	private Long refundSuccessAmount;
	
	//拒付交易金额
	private Long rejectSuccessAmount;
	
	//已结算金额
	private Long settledAmount;
	
	//纳入保证金金额
	private Long includeMarginAmount;
	
	//释放保证金金额
	private Long releaseMarginAmount;

	public int getRejectOrderNum() {
		return rejectOrderNum;
	}

	public void setRejectOrderNum(int rejectOrderNum) {
		this.rejectOrderNum = rejectOrderNum;
	}

	public Long getOnTheWayMoney() {
		return onTheWayMoney;
	}

	public void setOnTheWayMoney(Long onTheWayMoney) {
		this.onTheWayMoney = onTheWayMoney;
	}

	public int getSaleSuccessCount() {
		return saleSuccessCount;
	}

	public void setSaleSuccessCount(int saleSuccessCount) {
		this.saleSuccessCount = saleSuccessCount;
	}

	public int getRefundSuccessCount() {
		return refundSuccessCount;
	}

	public void setRefundSuccessCount(int refundSuccessCount) {
		this.refundSuccessCount = refundSuccessCount;
	}

	public int getRejectSuccessCount() {
		return rejectSuccessCount;
	}

	public void setRejectSuccessCount(int rejectSuccessCount) {
		this.rejectSuccessCount = rejectSuccessCount;
	}

	public Long getSaleSuccessAmount() {
		return saleSuccessAmount;
	}

	public void setSaleSuccessAmount(Long saleSuccessAmount) {
		this.saleSuccessAmount = saleSuccessAmount;
	}

	public Long getRefundSuccessAmount() {
		return refundSuccessAmount;
	}

	public void setRefundSuccessAmount(Long refundSuccessAmount) {
		this.refundSuccessAmount = refundSuccessAmount;
	}

	public Long getRejectSuccessAmount() {
		return rejectSuccessAmount;
	}

	public void setRejectSuccessAmount(Long rejectSuccessAmount) {
		this.rejectSuccessAmount = rejectSuccessAmount;
	}

	public Long getSettledAmount() {
		return settledAmount;
	}

	public void setSettledAmount(Long settledAmount) {
		this.settledAmount = settledAmount;
	}

	public Long getIncludeMarginAmount() {
		return includeMarginAmount;
	}

	public void setIncludeMarginAmount(Long includeMarginAmount) {
		this.includeMarginAmount = includeMarginAmount;
	}

	public Long getReleaseMarginAmount() {
		return releaseMarginAmount;
	}

	public void setReleaseMarginAmount(Long releaseMarginAmount) {
		this.releaseMarginAmount = releaseMarginAmount;
	}
	@Override
    public String toString() {
        return "OcaHomeRespDto{" +
        		", rejectOrderNum='" + rejectOrderNum + '\'' +
                ", saleSuccessCount='" + saleSuccessCount + '\'' +
                ", refundSuccessCount='" + refundSuccessCount + '\'' +
                ", rejectSuccessCount='" + rejectSuccessCount + '\'' +
                ", saleSuccessAmount='" + saleSuccessAmount + '\'' +
                ", refundSuccessAmount='" + refundSuccessAmount + '\'' +
                ", rejectSuccessAmount='" + rejectSuccessAmount + '\'' +
                ", settledAmount='" + settledAmount + '\'' +
                ", includeMarginAmount='" + includeMarginAmount + '\'' +
                ", releaseMarginAmount='" + releaseMarginAmount + '\'' +
                '}';
    }
}
