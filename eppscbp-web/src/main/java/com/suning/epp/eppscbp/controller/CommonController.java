/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: CommonController.java
 * Author:   17061545
 * Date:     2018年4月11日 下午5:12:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 〈公共controller〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/common/")
public class CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    private static final String GUIDE = "screen/guide";

    private static final String CP_GUIDE = "screen/cpGuide";
    
    private static final String TRADE_INFO_GUIDE = "screen/tradeInfoGuide";

    /**
     * 功能描述: <br>
     * 〈引导页面〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("guide")
    public ModelAndView guide() {
        LOGGER.info("引导页面初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(GUIDE);
        LOGGER.info("引导页面初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈引导页面〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("cpGuide")
    public ModelAndView cpGuide() {
        LOGGER.info("引导页面初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(CP_GUIDE);
        LOGGER.info("引导页面初始化结束");
        return mav;
    }
    
    /**
     * 功能描述: <br>
     * 〈引导页面〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("tradeInfoGuide")
    public ModelAndView tradeInfoGuide() {
        LOGGER.info("引导页面初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(TRADE_INFO_GUIDE);
        LOGGER.info("引导页面初始化结束");
        return mav;
    }


}
