package com.suning.epp.eppscbp.dto.res;

import java.util.List;

public class CollSettTradeInfoResDto {
	// 列表
	private List<CollectionSettlementAggregation> collectionSettlementAggregationList;
	
	// 金额汇总
	private List<AmtSumDto> amtSumDtoList;

	public List<CollectionSettlementAggregation> getCollectionSettlementAggregationList() {
		return collectionSettlementAggregationList;
	}

	public void setCollectionSettlementAggregationList(
			List<CollectionSettlementAggregation> collectionSettlementAggregationList) {
		this.collectionSettlementAggregationList = collectionSettlementAggregationList;
	}

	public List<AmtSumDto> getAmtSumDtoList() {
		return amtSumDtoList;
	}

	public void setAmtSumDtoList(List<AmtSumDto> amtSumDtoList) {
		this.amtSumDtoList = amtSumDtoList;
	}
	
	@Override
    public String toString() {
        return "CollSettTradeInfoResDto [collectionSettlementAggregationList=" + collectionSettlementAggregationList + ", amtSumDtoList=" + amtSumDtoList + "]";
    }
	
}
