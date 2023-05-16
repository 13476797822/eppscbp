package com.suning.epp.eppscbp.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.req.LogisticsInfoQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.LogisticsInfoQueryResDto;

public interface OcaLogisticsInfoService {

	ApiResDto<List<LogisticsInfoQueryResDto>> logisticsInfoQuery(LogisticsInfoQueryReqDto requestDto);

	/**
	 * 物流信息提交
	 * @param fileAddress
	 * @param userNo
	 * @return
	 */
	ApiResDto<String> logisticsInfoSubmit(String fileAddress, String userNo);

}
