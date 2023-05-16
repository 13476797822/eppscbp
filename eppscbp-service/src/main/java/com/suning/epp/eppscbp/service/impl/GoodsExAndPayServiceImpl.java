/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: GoodsExAndPayServiceImpl.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:55:51
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
import com.suning.epp.eppscbp.service.GoodsExAndPayService;
import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈货物贸易单笔购付汇〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("goodsExAndPayService")
public class GoodsExAndPayServiceImpl implements GoodsExAndPayService {

    /**
     * Logging mechanism
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsExAndPayServiceImpl.class);

    @Autowired
    private TradeCoreService tradeCoreService;

    @Autowired
    private UnifiedReceiptService unifiedReceiptService;

    @Autowired
    private MemberInfoService memberInfoService;

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsExAndPayService#submiteSettleOrder(com.suning.epp.eppscbp.dto.
     * OrderApplyAcquireDto)
     */
    @Override
    public ApiResDto<OrderlAcquireResponseDto> submiteSettleOrder(OrderApplyAcquireDto orderApplyAcquireDto) {
        LOGGER.info("货物贸易单笔购付汇提交:{}", orderApplyAcquireDto);
        
        //全额到账未获取到，判空，防止空指针
        ApiResDto<OrderlAcquireResponseDto> apiResDto = new ApiResDto<OrderlAcquireResponseDto>();
        if(StringUtil.isEmpty(orderApplyAcquireDto.getAmtType())) {
        	apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
            apiResDto.setResponseMsg(CommonConstant.AMT_TYPE_NOT_NULL);
        	return apiResDto;
        }
        
        orderApplyAcquireDto.setPlatformCode(EPPSCBPConstants.PLATFORMCODE);
        orderApplyAcquireDto.setProductType(CbpProductType.SINGLE_GFH.getCode());
        if (AmtType.TYPE_FULL.getCode().endsWith(orderApplyAcquireDto.getAmtType())) {
            orderApplyAcquireDto.setFeeFlg(FeeFlg.OUR_SIDE.getCode());
        } else {
            orderApplyAcquireDto.setFeeFlg(FeeFlg.BOTH_SIDE.getCode());
        }
        orderApplyAcquireDto.setBizType(ChargeBizType.TYPE_GOODS_TRADE.getCode());
        orderApplyAcquireDto.setTradeMode(TradeMode.TRADE_MODE_01.getCode());
        LOGGER.info("货物贸易单笔购付汇提交参数:{}", orderApplyAcquireDto);
        return unifiedReceiptService.submiteSettleOrder(orderApplyAcquireDto);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsExAndPayService#confirmPayment(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<String> confirmPayment(OrderOperationDto orderOperationDto) {
        return unifiedReceiptService.doOrderOperation(orderOperationDto, CommonConstant.OPERATING_TYPE_PAY);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsExAndPayService#queryBalance(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ExitQueryBalance queryBalance(String payerMerchantId, String accSrcCod, String balCcy) {
        return tradeCoreService.queryBalance(payerMerchantId, accSrcCod, balCcy);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsExAndPayService#eppSecretKeyRsfServer()
     */
    @Override
    public ApiResDto<String> eppSecretKeyRsfServer() {
        return memberInfoService.eppSecretKeyRsfServer();
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.GoodsExAndPayService#validatePaymentPassword(java.lang.String,
     * java.lang.String)
     */
    @Override
    public ApiResDto<String> validatePaymentPassword(String userNo, String paymentPassword) {
        LOGGER.info("验证支付密码,用户:{},密码:{}", userNo, paymentPassword);
        return memberInfoService.validatePaymentPassword(userNo, paymentPassword);
    }
}
