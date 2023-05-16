package com.suning.epp.eppscbp.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.res.ApiResDto;

/**
 * 查询公告配置
 * @author 88397357
 *
 */
public interface NoticeService {
	
	/**
	 * 查询SCM配置平台公告
	 * @return
	 */
	public ApiResDto<List<String>> noticeQuery(String notice);
}
