/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: TradeCoreServiceImpl.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:53:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.TradeCoreConstant;
import com.suning.epp.eppscbp.rsf.service.TradeCoreService;
import com.suning.fab.faeppprotocal.protocal.accountmanage.EntryQueryBalance;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;
import com.suning.fab.model.common.IFabRsfService;
import com.suning.fab.model.domain.protocal.ExitBusinessCommon;
import com.suning.rsf.RSFException;
import com.suning.rsf.consumer.ExceedActiveLimitException;
import com.suning.rsf.consumer.ExceedRetryLimitException;
import com.suning.rsf.consumer.ServiceLocator;
import com.suning.rsf.consumer.TimeoutException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("tradeCoreService")
public class TradeCoreServiceImpl implements TradeCoreService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeCoreServiceImpl.class);

    private IFabRsfService queryBalanceService;

    @PostConstruct
    public void init() {
        queryBalanceService = ServiceLocator.getService(IFabRsfService.class, "faepp-queryBalance");
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.rsf.service.TradeCoreService#queryBalance(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ExitQueryBalance queryBalance(String payerMerchantId, String accSrcCod, String balCcy) {
        ExitQueryBalance balance = new ExitQueryBalance();
        balance.setRspCode(CommonConstant.SYSTEM_ERROR_CODE);
        balance.setRspMsg(CommonConstant.SYSTEM_ERROR_MES);
        try {
            EntryQueryBalance req = new EntryQueryBalance(new Date(), TradeCoreConstant.CHANNELID_PARAM, TradeCoreConstant.PUB_BRC_CONSTANT);
            req.makeAccountInfo(payerMerchantId, accSrcCod, balCcy);
            String requestStr = JSON.toJSONString(req);
            LOGGER.info("请求账务核心查询余额的参数:{}", requestStr);
            ExitBusinessCommon response = (ExitBusinessCommon) queryBalanceService.execute(req);
            String responseStr = JSON.toJSONString(response);
            LOGGER.info("账务核心返回的参数:{}", responseStr);
            if (TradeCoreConstant.SUCCESS.equals(response.getRspCode())) {
                balance = (ExitQueryBalance) response;
            } else {
                balance.setRspCode(response.getRspCode());
                balance.setRspMsg(response.getRspMsg());
            }
        } catch (ExceedActiveLimitException ex) {
            LOGGER.error("超出消费方流控阀值:{}", ExceptionUtils.getStackTrace(ex));
        } catch (ExceedRetryLimitException ex) {
            LOGGER.error("超出重试次数，调用失败，请求异常明细:{}" + ExceptionUtils.getStackTrace(ex));
            if (ex.exceedConcurrencyThreshold()) {
                LOGGER.error("超出服务方流控阀值:{}", ExceptionUtils.getStackTrace(ex));
            }
        } catch (TimeoutException ex) {
            LOGGER.error("调用超时:{}", ExceptionUtils.getStackTrace(ex));
        } catch (RSFException ex) {
            LOGGER.error("其他框架异常:{}", ExceptionUtils.getStackTrace(ex));
        } catch (Exception ex) {
            LOGGER.error("未知异常:{}", ExceptionUtils.getStackTrace(ex));
        }
        return balance;
    }
}
