package com.suning.epp.eppscbp.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.req.RefundOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.RefundOrderQueryRespDto;

public interface RefundOrderService {

	ApiResDto<List<RefundOrderQueryRespDto>> queryRefundOrder(RefundOrderQueryReqDto requestDto);

	/**
	 * 退款提交
	 * @param payerAccount
	 * @param merchantOrderNo
	 * @param refundAmt
	 * @return
	 */
	ApiResDto<Boolean> refundSubmit(String payerAccount,String receiptOrderNo, String merchantOrderNo, String refundAmt);

	/**
	 * 查询退款单详情
	 * @param payerAccount
	 * @param refundOrderNo
	 * @return
	 */
	ApiResDto<RefundOrderQueryRespDto> queryDetailInfo(String payerAccount, String refundOrderNo);

}
