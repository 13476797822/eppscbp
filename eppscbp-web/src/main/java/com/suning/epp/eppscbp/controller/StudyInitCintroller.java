/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: StudyInitCintroller.java
 * Author:   17061545
 * Date:     2018年3月23日 下午3:57:41
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

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈留学缴费业务初始化〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/overseasPay/overseasPayInit")
public class StudyInitCintroller {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudyInitCintroller.class);

    private static final String OVERSEASPAY__INIT = "screen/overseasPay/overseasPayInit";
    // 支付结果页面
    private static final String OVERSEASPAY_PAY__RESULT = "screen/overseasPay/result";

    /**
     * 功能描述: <br>
     * 〈页面初期化〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init() {
        LOGGER.info("留学缴费业务初始化开始");
        ModelAndView mav = new ModelAndView();
        mav.setViewName(OVERSEASPAY__INIT);
        LOGGER.info("留学缴费业务初始化结束");
        return mav;
    }

    /**
     * 功能描述: <br>
     * 〈支付结果页面〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("payResult")
    public ModelAndView payResult(String payResult, String meg, String productType) {
        LOGGER.info("留学缴费支付结果");
        ModelAndView mav = new ModelAndView();
        if (StringUtil.isNotNull(productType)) {
            mav.addObject(EPPSCBPConstants.PRODUCT_TYPE, productType);
        }
        mav.addObject(EPPSCBPConstants.PAY_RESULT, payResult);
        mav.addObject(EPPSCBPConstants.MEG, meg);
        mav.setViewName(OVERSEASPAY_PAY__RESULT);
        LOGGER.info("留学缴费支付结果展示");
        return mav;
    }

}
