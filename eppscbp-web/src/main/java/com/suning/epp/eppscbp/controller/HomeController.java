package com.suning.epp.eppscbp.controller;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.constant.ScmOnlineConfig;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.service.NoticeService;

/**
 * 〈首页初始化〉<br> 
 * 〈功能详细描述〉
 *
 * @author 88397357
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/home/homeInit/")
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	//首页
	private static final String HOME_INIT = "screen/home/homeInit";
	
	//资金跨境首页
	private static final String HOME_ZJKJ = "screen/home/homeZjkj";
	
	@Autowired
	private NoticeService  noticeService;
	
	/**
     * 功能描述: <br>
     * 〈页面初期化(获取公告配置信息)〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init() {
        LOGGER.info("首页初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(HOME_INIT);
        try {
	        //获取公告信息
	        ApiResDto<List<String>> noticeDto = noticeService.noticeQuery(ScmOnlineConfig.NOTICE);
	        if(noticeDto.isSuccess()) {
	        	mav.addObject(EPPSCBPConstants.RESULT_LIST, noticeDto.getResult());
	        }
        } catch (Exception ex) {
            LOGGER.error("获取公告信息异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("首页初始化结束");
        return mav;
    }
    
    /**
     * 功能描述: <br>
     * 〈页面初期化(获取公告配置信息)〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("homeZjkj")
    public ModelAndView home() {
        LOGGER.info("首页初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(HOME_ZJKJ);
        try {
	        //获取公告信息
	        ApiResDto<List<String>> noticeDto = noticeService.noticeQuery(ScmOnlineConfig.NOTICE);
	        if(noticeDto.isSuccess()) {
	        	mav.addObject(EPPSCBPConstants.RESULT_LIST, noticeDto.getResult());
	        }
        } catch (Exception ex) {
            LOGGER.error("获取公告信息异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("首页初始化结束");
        return mav;
    }

}
