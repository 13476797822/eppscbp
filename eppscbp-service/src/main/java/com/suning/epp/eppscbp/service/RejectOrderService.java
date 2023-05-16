package com.suning.epp.eppscbp.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.suning.epp.eppscbp.dto.req.RejectOrderQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.RejectDetailResDto;
import com.suning.epp.eppscbp.dto.res.RejectOrderQueryRespDto;

public interface RejectOrderService {

	ApiResDto<List<RejectOrderQueryRespDto>> rejectOrderQuery(RejectOrderQueryReqDto requestDto);

	ApiResDto<RejectDetailResDto> rejectDetailInfoQuery(String payerAccount, String orderNo);

	ApiResDto<Boolean> accept(String payerAccount, String merchantOrderNo, String rejectOrderNo);

	ApiResDto<Boolean> appeal(String payerAccount,String rejectOrderNo, String reason, String filePath);

	ApiResDto<JSONArray> rejectMessageBoxQuery(String merchantOrderNo, String payerAccount);

}
