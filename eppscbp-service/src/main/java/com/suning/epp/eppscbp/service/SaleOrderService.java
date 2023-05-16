package com.suning.epp.eppscbp.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.req.SaleOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.SaleOrderDetailQueryRespDto;
import com.suning.epp.eppscbp.dto.res.SaleOrderQueryRespDto;

public interface SaleOrderService {

	/**
	 * 查询销售订单列表
	 * @param requestDto
	 * @return
	 */
	ApiResDto<List<SaleOrderQueryRespDto>> saleOrderQuery(SaleOrderQueryReqDto requestDto);

	/**
	 * 查询根据销售订单详情
	 * @param payerAccount
	 * @param merchantOrderNo
	 * @return
	 */
	ApiResDto<SaleOrderDetailQueryRespDto> queryDetailInfo(String payerAccount, String receiptOrderNo);


	/**
	 * 支付处理中批量结果查询
	 * @param payerAccount
	 * @param receiptOrderNoStr
	 * @return
	 */
	ApiResDto<Boolean> batchQueryChannel(String payerAccount,String receiptOrderNoStr);

}
