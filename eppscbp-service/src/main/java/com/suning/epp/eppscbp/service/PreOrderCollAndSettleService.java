package com.suning.epp.eppscbp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import org.springframework.web.multipart.MultipartFile;

import com.suning.epp.eppscbp.dto.req.PreArrivalQueryReqDto;
import com.suning.epp.eppscbp.dto.req.PreBpOrderParseCalcReqDto;
import com.suning.epp.eppscbp.dto.req.PreCollAndSettleQueryDto;
import com.suning.epp.eppscbp.dto.req.PreOrderCollAndPaymentApplyReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.PreAmountQueryResDto;
import com.suning.epp.eppscbp.dto.res.PreCollAndSettleQueryResDto;

public interface PreOrderCollAndSettleService {

    /**
     * 收结汇明细文件"上传提交"
     *
     * @param
     * @return
     */
    FileUploadResDto collAndSettleFileUpload(MultipartFile targetFile, String payerAccount) throws IOException;


    /**
     * 收结汇明细文件"上传提交"
     *
     * @param
     * @return
     */
    Map<String, String> collAndSettleFileApply(String fileAddress, String payerAccount, String bizType);


    /**
     * 收结汇明细文件收单查询
     *
     * @param requestDto
     * @return
     */
    ApiResDto<List<PreCollAndSettleQueryResDto>> collAndSettlePreQuery(PreCollAndSettleQueryDto requestDto);

    /**
     * 收结汇明细文件收单查询
     *
     * @param payerAccount
     * @return
     */
    ApiResDto<List<PreAmountQueryResDto>> queryPreOrderAmt(String payerAccount);

    /**
     * 来账信息查询
     *
     * @param params
     * @return
     */
    Map<String, Object> arrivalQuery(PreArrivalQueryReqDto params);

    /**
     * 批付文件解析并计算
     *
     * @param params
     * @return
     */
    Map<String, Object> bpOrdersParseCalc(PreBpOrderParseCalcReqDto params, MultipartFile targetFile);

    /**
     * 收结汇付款"提交申请"
     *
     * @param params
     * @return
     */
    Map<String, Object> collAndPaymentApply(PreOrderCollAndPaymentApplyReqDto params);
}
