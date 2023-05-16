/**
 * 
 */
package com.suning.epp.eppscbp.rsf.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.suning.epp.eppscbp.common.exception.ExcelForamatException;
import com.suning.epp.eppscbp.dto.req.BatchOrderQueryDto;
import com.suning.epp.eppscbp.dto.req.BatchOrderRemitQueryDto;
import com.suning.epp.eppscbp.dto.req.CollAndSettleQueryDto;
import com.suning.epp.eppscbp.dto.req.SingleOrderQueryDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchTradeOrderQueryResDto;
import com.suning.epp.eppscbp.dto.res.BatchTradeOrderRemitQueryResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleDetailResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleQueryResDto;
import com.suning.epp.eppscbp.dto.res.FileUploadResDto;
import com.suning.epp.eppscbp.dto.res.SingleTradeOrderQueryResDto;
import com.suning.epp.eppscbp.dto.res.TradeOrderDetailQueryResDto;

/**
 * @author 17080704
 *
 */
public interface OrderQueryService {

    public ApiResDto<List<SingleTradeOrderQueryResDto>> singleOrderQuery(SingleOrderQueryDto reqDto);

    public ApiResDto<List<BatchTradeOrderQueryResDto>> batchOrderQuery(BatchOrderQueryDto reqDto);

    public ApiResDto<List<BatchTradeOrderRemitQueryResDto>> batchOrderRemitQuery(BatchOrderRemitQueryDto reqDto);

    public ApiResDto<TradeOrderDetailQueryResDto> detailInfoQuery(String payerAccount, String subBusinessNo);

    // 货物贸易监管上传触发跨境
    ApiResDto<String> tradeSuperviseUpload(String businessNo, String fileAddress, String payerAccount, String type);

    // 留学教育触发监管上传
    ApiResDto<String> studySuperviseUpload(String businessNo, String payerAccount);

    // 货物贸易监管上传校验
    FileUploadResDto uploadFile(MultipartFile targetFile, String user) throws IOException, ExcelForamatException;
    
    // 收结汇订单查询
    public ApiResDto<List<CollAndSettleQueryResDto>> collAndSettleQuery(CollAndSettleQueryDto requestDto);

    // 收结汇订单详情查询
    public ApiResDto<CollAndSettleDetailResDto> collAndSettleDetailInfoQuery(String payerAccount, String orderNo);

}
