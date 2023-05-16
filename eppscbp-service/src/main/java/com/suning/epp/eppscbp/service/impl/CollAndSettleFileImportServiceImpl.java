/**
 * 
 */
package com.suning.epp.eppscbp.service.impl;

import com.suning.epp.eppscbp.common.enums.LogisticsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.EPPSCBPConstants;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CollSettProductType;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.dto.req.CollAndSettleApplyDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleResDto;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;
import com.suning.epp.eppscbp.service.CollAndSettleFileImportService;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * @author 17080704
 *
 */
@Service("collAndSettleFileImportService")
public class CollAndSettleFileImportServiceImpl implements CollAndSettleFileImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollAndSettleFileImportServiceImpl.class);

    @Autowired
    private UnifiedReceiptService unifiedReceiptService;

    /**
     * 
     * 收结汇申请<br>
     * 〈功能详细描述〉
     *
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Override
    public ApiResDto<CollAndSettleResDto> submitSettleOrder(CollAndSettleApplyDto collAndSettleApplyDto) {
        LOGGER.info("收结汇提交:{}", collAndSettleApplyDto);

        String receiveAmtPara = collAndSettleApplyDto.getReceiveAmt();
        // 金额未获取到，判空，防止空指针
        ApiResDto<CollAndSettleResDto> apiResDto = new ApiResDto<CollAndSettleResDto>();
        if (StringUtil.isEmpty(receiveAmtPara)) {
            apiResDto.setResponseCode(CommonConstant.SYSTEM_ERROR_CODE);
            apiResDto.setResponseMsg(CommonConstant.RECEIVE_AMT_NOT_NULL);
            return apiResDto;
        }

        // 获取金额
        String payNo = null;
        String arrivalNoticeId = null;
        String receiveAmt = null;
        if (receiveAmtPara.contains(";")) {
            receiveAmt = receiveAmtPara.split(";")[0];
            arrivalNoticeId = receiveAmtPara.split(";")[1];
            payNo = receiveAmtPara.split(";")[2];
        } else {
            receiveAmt = receiveAmtPara;
        }

        // 生成请求参数
        CollAndSettleApplyDto requestDto = new CollAndSettleApplyDto();
        requestDto.setPlatformCode(EPPSCBPConstants.PLATFORMCODE);
        if (CurType.CNY.getDescription().equals(collAndSettleApplyDto.getCurrency())) {
            requestDto.setProductType(CollSettProductType.SINGLE_SH.getCode());
        } else {
            requestDto.setProductType(CollSettProductType.SINGLE_SJH.getCode());
        }
        requestDto.setPayeeMerchantCode(collAndSettleApplyDto.getPayeeMerchantCode());
        requestDto.setPayerAccount(collAndSettleApplyDto.getPayerAccount());
        requestDto.setPayNo(payNo);
        requestDto.setArrivalNoticeId(arrivalNoticeId);
        requestDto.setPayeeBankCard(collAndSettleApplyDto.getPayeeBankCard());
        requestDto.setBizType(ChargeBizType.getCodeFromDescription(collAndSettleApplyDto.getBizType()));
        requestDto.setCurrency(CurType.getCodeFromDescription(collAndSettleApplyDto.getCurrency()));
        requestDto.setReceiveAmt(receiveAmt);
        requestDto.setFileAddress(collAndSettleApplyDto.getFileAddress());
        requestDto.setDetailAmount(collAndSettleApplyDto.getDetailAmount());
        requestDto.setReferenceRate(collAndSettleApplyDto.getReferenceRate());
        requestDto.setPcToken(collAndSettleApplyDto.getPcToken());
        requestDto.setPlatformName(collAndSettleApplyDto.getPlatformName());
        requestDto.setLogisticsType(LogisticsType.getCodeFromDescription(collAndSettleApplyDto.getLogisticsType()));

        LOGGER.info("收结汇申请提交参数:{}", requestDto);
        return unifiedReceiptService.submitCollSettleOrder(requestDto);
    }

}
