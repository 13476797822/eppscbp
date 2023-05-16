/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: RateRefreshController.java
 * Author:   17061545
 * Date:     2018年4月8日 下午5:31:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.ReferRateQueryResDto;
import com.suning.epp.eppscbp.rsf.service.ReferRateQueryService;
import com.suning.epp.eppscbp.util.DateUtil;

/**
 * 〈汇率查询〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller()
@RequestMapping("/rateQuery/rateQuery/")
public class RateQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateQueryController.class);

    private static final String RATE_QUERY = "screen/rateQuery/rateQuery";

    @Autowired
    private ReferRateQueryService referRateQueryService;

    /**
     * 功能描述: <br>
     * 〈中信汇率查询初始化〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("init")
    public ModelAndView init(@RequestParam("channelId") String channelId) {
        LOGGER.info("汇率查询初始化开始");
        ModelAndView mav = new ModelAndView(RATE_QUERY);
        LOGGER.info("汇率查询初始化结束");
        //ApiResDto<List<ReferRateQueryResDto>> apiResDto = referRateQueryService.referRateQuery();
        ApiResDto<List<ReferRateQueryResDto>> apiResDto = referRateQueryService.referRateQueryZXorGD(channelId);
        LOGGER.info("汇率查询结果:{}", apiResDto.toString());
        if(apiResDto.isSuccess()){
            mav.addObject("quoteTime", DateUtil.formatDate(new Date(), DateConstant.DATEFORMATEyyyyMMddHHmmss));
            mav.addObject("resultList", apiResDto.getResult());
        }
        return mav;
    }
}
