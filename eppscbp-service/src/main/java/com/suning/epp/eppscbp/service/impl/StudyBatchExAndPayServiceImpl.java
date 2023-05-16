/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: StudyBatchExAndPayServiceImpl.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:57:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;
import com.suning.epp.eppscbp.rsf.service.MemberInfoService;
import com.suning.epp.eppscbp.rsf.service.TradeCoreService;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.service.StudyBatchExAndPayService;
import com.suning.fab.faeppprotocal.protocal.accountmanage.ExitQueryBalance;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("studyBatchExAndPayService")
public class StudyBatchExAndPayServiceImpl implements StudyBatchExAndPayService {

    @Autowired
    private TradeCoreService tradeCoreService;

    @Autowired
    private UnifiedReceiptService unifiedReceiptService;

    @Autowired
    private MemberInfoService memberInfoService;

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.StudyBatchExAndPayService#submiteSettleOrder(com.suning.epp.eppscbp.dto.res.
     * OrderApplyAcquireDto)
     */
    @Override
    public ApiResDto<OrderlAcquireResponseDto> submiteSettleOrder(OrderApplyAcquireDto orderApplyAcquireDto) {
        return unifiedReceiptService.batchSubmiteOrder(orderApplyAcquireDto);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.StudyBatchExAndPayService#confirmPayment(java.lang.String, java.lang.String)
     */
    @Override
    public ApiResDto<String> confirmPayment(OrderOperationDto orderOperationDto) {
        return unifiedReceiptService.doOrderOperation(orderOperationDto, CommonConstant.OPERATING_TYPE_PAY);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.StudyBatchExAndPayService#queryBalance(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ExitQueryBalance queryBalance(String payerMerchantId, String accSrcCod, String balCcy) {
        return tradeCoreService.queryBalance(payerMerchantId, accSrcCod, balCcy);
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.StudyBatchExAndPayService#eppSecretKeyRsfServer()
     */
    @Override
    public ApiResDto<String> eppSecretKeyRsfServer() {
        return memberInfoService.eppSecretKeyRsfServer();
    }

    /*
     * (non-Javadoc)
     * @see com.suning.epp.eppscbp.service.StudyBatchExAndPayService#validatePaymentPassword(java.lang.String,
     * java.lang.String)
     */
    @Override
    public ApiResDto<String> validatePaymentPassword(String userNo, String paymentPassword) {
        return memberInfoService.validatePaymentPassword(userNo, paymentPassword);
    }

}
