package com.suning.epp.eppscbp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.OcaHomeResultDto;
import com.suning.epp.eppscbp.service.OcaHomeService;

/**
 * 〈国际卡收单_首页〉<br>
 * 〈功能详细描述〉
 *
 * @author 19043747
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/oca/ocaHomeController/")
public class OcaHomeController {
	
private static final Logger LOGGER = LoggerFactory.getLogger(OcaHomeController.class);
	
	//首页
	private static final String OCA_HOME_INIT = "screen/oca/home/ocaHomeInit";
	
	@Autowired
	private OcaHomeService ocaHomeService;
	
	/**
     * 功能描述: <br>
     * 〈首页页面初始化，获取拒付数量、公告、可用余额、在途资金、保证金>
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init(HttpServletRequest request, HttpSession session) {
        LOGGER.info("初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(OCA_HOME_INIT);
        
        ApiResDto<OcaHomeResultDto> ocaHomeResultDto = new ApiResDto<OcaHomeResultDto>();
        try {
	        ocaHomeResultDto = ocaHomeService.initHomeInfo(request.getRemoteUser(), session);
	        if(ocaHomeResultDto.isSuccess()) {
	        	mav.addObject(EPPSCBPConstants.CRITERIA, ocaHomeResultDto);
	        }
        } catch (Exception ex) {
            LOGGER.error("获取初始化异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("初始化结束");
        return mav;
    }
    
    /**
     * 功能描述: <br>
     * 〈
     * 订单数量（销售成功笔数、退款成功笔数、拒付交易笔数）、
     * 订单金额（销售成功金额、退款成功金额、拒付交易金额）
     * 结算金额（已结算、纳入保证金、释放保证金）〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String query(@RequestParam("fromTime") String fromTime, @RequestParam("toTime") String toTime, HttpServletRequest request) {
        LOGGER.info("饼状图初始化开始");
        ApiResDto<OcaHomeResultDto> ocaHomeResultDto = new ApiResDto<OcaHomeResultDto>();
        try {
        	ocaHomeResultDto = ocaHomeService.queryHomeInfo(fromTime, toTime, request.getRemoteUser());
        } catch (Exception ex) {
            LOGGER.error("获取饼状图数据异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        LOGGER.info("饼状图初始化结束");
        return JSON.toJSONString(ocaHomeResultDto);
    }

}
