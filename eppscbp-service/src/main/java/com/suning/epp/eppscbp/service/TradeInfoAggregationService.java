package com.suning.epp.eppscbp.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.req.TradeInfoReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CollSettTradeInfoResDto;
import com.suning.epp.eppscbp.dto.res.PurchasePaymentTradeInfoResDto;

public interface TradeInfoAggregationService {

	/**
	 * 根据条件查询购付汇聚合交易数据
	 * @param requestDto
	 * @return
	 */
	ApiResDto<CollSettTradeInfoResDto> queryCollSettTradeInfo(TradeInfoReqDto requestDto);

	/**
	 * 根据条件查询购付汇聚合交易数据
	 * @param requestDto
	 * @return
	 */
	ApiResDto<PurchasePaymentTradeInfoResDto> purchasePaymentTradeInfo(TradeInfoReqDto requestDto);

	/**
	 * 根据订单号+交易状态 获取PDF文件所在地址
	 * @param orderNo
	 * @param transactionType
	 * @return
	 */
	ApiResDto<String> getFileAddrePDF(String orderNo, String transactionType);

	/**
	 * 根据条件查询汇总电子回单地址
	 * @param tradeInfoReqDto
	 * @return
	 */
	ApiResDto<String> queryPDFAddressByCondition(TradeInfoReqDto tradeInfoReqDto);

}
