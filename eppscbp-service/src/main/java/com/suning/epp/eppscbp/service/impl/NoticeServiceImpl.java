package com.suning.epp.eppscbp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);
	
	@Autowired
	private ScmInitStartListener scmInitStartListener;

	@Override
	public ApiResDto<List<String>> noticeQuery(String str) {
		ApiResDto<List<String>> apiResDto = new ApiResDto<List<String>>();
		//从SCM配置平台读取公告
		LOGGER.info("从SCM配置平台读取公告信息开始");
		List<String> noticeList = new ArrayList<String>();
        String noticeStr = scmInitStartListener.getValue(str);
        if(noticeStr.contains(CommonConstant.VERTICAL_LINE)) {
        	//多条公告，用|分割
        	String[] noticeStrs = noticeStr.split(CommonConstant.VERTICAL_LINE_ZY);
        	for(int i=0;i<noticeStrs.length;i++) {
        		noticeList.add(i, noticeStrs[i]);
        	}
        }else {
        	//一条公告
        	noticeList.add(0, noticeStr);
        }
        apiResDto.setResult(noticeList);
        LOGGER.info("从SCM配置平台读取公告信息结束.noticeStr:{}", noticeStr);
	        
        return apiResDto;
	}

}
