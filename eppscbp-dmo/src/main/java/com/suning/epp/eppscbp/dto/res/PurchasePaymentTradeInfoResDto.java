package com.suning.epp.eppscbp.dto.res;

import java.util.List;

public class PurchasePaymentTradeInfoResDto {
	// 列表
	private List<PurchasePaymentAggregation> purchasePaymentAggregationList; 
	
	// 汇总金额
	private List<AmtSumDto> amtSumDtoList;

	public List<PurchasePaymentAggregation> getPurchasePaymentAggregationList() {
		return purchasePaymentAggregationList;
	}

	public void setPurchasePaymentAggregationList(List<PurchasePaymentAggregation> purchasePaymentAggregationList) {
		this.purchasePaymentAggregationList = purchasePaymentAggregationList;
	}

	public List<AmtSumDto> getAmtSumDtoList() {
		return amtSumDtoList;
	}

	public void setAmtSumDtoList(List<AmtSumDto> amtSumDtoList) {
		this.amtSumDtoList = amtSumDtoList;
	}
	
}
