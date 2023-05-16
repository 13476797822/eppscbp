package com.suning.epp.eppscbp.service;

import javax.servlet.http.HttpSession;

import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.OcaHomeResultDto;

/**
 * OCA首页Service
 * @author 19043747
 *
 */
public interface OcaHomeService {
	/**
	 * 初始化
	 * 获取公告、拒付单数量、可用余额、在途资金、保证金
	 * @return
	 */
	ApiResDto<OcaHomeResultDto> initHomeInfo(String remoteUser,  HttpSession session);
	
	/**
	 * 获取订单笔数、订单金额、已结算金额
	 * @param fromTime
	 * @param toTime
	 * @param remoteUser
	 * @return
	 */
	ApiResDto<OcaHomeResultDto> queryHomeInfo(String fromTime, String toTime, String remoteUser);

	
	
}
