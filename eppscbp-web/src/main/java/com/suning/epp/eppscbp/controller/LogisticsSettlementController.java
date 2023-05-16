package com.suning.epp.eppscbp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.CurType;

/**
 * 〈物流款项结算，收汇申请〉<br>
 * 〈功能详细描述〉
 *
 * @author 88397357
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/logisticsSettlement/logisticsSettlement/")
public class LogisticsSettlementController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsSettlementController.class);

    private static final String INIT_QUERY = "screen/logisticsSettlement/init";

    /**
     * 初始化主视图
     * 
     * @return
     */
    private ModelAndView createMainView() {
        ModelAndView mvn = new ModelAndView(INIT_QUERY);
        mvn.addObject(EPPSCBPConstants.CUR_TYPE, CurType.getDescriptionByCny());
        return mvn;
    }

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
        LOGGER.info("物流款项收汇引导初始化开始");
        ModelAndView mav = createMainView();
        LOGGER.info("物流款项收汇引导初始化结束");
        return mav;
    }
}
