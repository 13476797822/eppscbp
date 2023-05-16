/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: StudyPayServiceImpl.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:57:39
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.AmtType;
import com.suning.epp.eppscbp.common.enums.CbpProductType;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.FeeFlg;
import com.suning.epp.eppscbp.common.enums.TradeMode;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.rsf.service.MemberInfoService;
import com.suning.epp.eppscbp.rsf.service.TradeCoreService;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.service.StudyPayService;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("studyPayService")
public class StudyPayServiceImpl implements StudyPayService {
    /*
     * Logging mechanism
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StudyPayServiceImpl.class);

    @Autowired
    private TradeCoreService tradeCoreService;

    @Autowired
    private UnifiedReceiptService unifiedReceiptService;

    @Autowired
    private MemberInfoService memberInfoService;

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.StudyPayService#submiteSettleOrder(com.suning.epp.eppscbp.dto.
     * OrderApplyAcquireDto)
     */
    @Override
    public ApiResDto<OrderlAcquireResponseDto> submiteSettleOrder(OrderApplyAcquireDto orderApplyAcquireDto) {
        LOGGER.info("留学缴费单笔付汇提交:{}", orderApplyAcquireDto.toString());
        orderApplyAcquireDto.setPlatformCode(EPPSCBPConstants.PLATFORMCODE);
        orderApplyAcquireDto.setProductType(CbpProductType.SINGLE_FH.getCode());
        if (AmtType.TYPE_FULL.getCode().endsWith(orderApplyAcquireDto.getAmtType())) {
            orderApplyAcquireDto.setFeeFlg(FeeFlg.OUR_SIDE.getCode());
        }else{
            orderApplyAcquireDto.setFeeFlg(FeeFlg.BOTH_SIDE.getCode());
        } 
        orderApplyAcquireDto.setBizType(ChargeBizType.TYPE_ABROAD_EDUCATION.getCode());
        orderApplyAcquireDto.setTradeMode(TradeMode.TRADE_MODE_02.getCode());
        LOGGER.info("留学缴费单笔付汇提交参数:{}", orderApplyAcquireDto.toString());
        return unifiedReceiptService.submiteSettleOrder(orderApplyAcquireDto);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsPayService#confirmPayment(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<String> confirmPayment(OrderOperationDto orderOperationDto) {
        return unifiedReceiptService.doOrderOperation(orderOperationDto, CommonConstant.OPERATING_TYPE_PAY);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsPayService#queryBalance(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ExitQueryBalance queryBalance(String payerMerchantId, String accSrcCod, String balCcy) {
        return tradeCoreService.queryBalance(payerMerchantId, accSrcCod, balCcy);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsPayService#eppSecretKeyRsfServer()
     */
    @Override
    public ApiResDto<String> eppSecretKeyRsfServer() {
        return memberInfoService.eppSecretKeyRsfServer();
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsPayService#validatePaymentPassword(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<String> validatePaymentPassword(String userNo, String paymentPassword) {
        return memberInfoService.validatePaymentPassword(userNo, paymentPassword);
    }
}
