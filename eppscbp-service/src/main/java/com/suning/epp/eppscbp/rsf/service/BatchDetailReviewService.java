/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpBatchDetailQueryService.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.rsf.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.req.BatchPaymentReviewDetailDto;
import com.suning.epp.eppscbp.dto.req.BatchPaymentReviewQueryReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentOrderReviewData;
import com.suning.epp.eppscbp.dto.res.BatchPaymentReviewDetail;

/**
 * 〈明细查询和导出service〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface BatchDetailReviewService {

    ApiResDto<List<BatchPaymentOrderReviewData>> ordersReviewQuery(BatchPaymentReviewQueryReqDto reqDto);

    ApiResDto<List<BatchPaymentReviewDetail>> ordersReviewDetail(BatchPaymentReviewDetailDto reqDto);

    ApiResDto<Boolean> confirmBatchPayment(BatchPaymentReviewDetailDto reqDto);
    
    ApiResDto<List<String>> batchPaymentValidate(BatchPaymentReviewDetailDto reqDto);
    
    ApiResDto<Boolean> batchPaymentNotPass(BatchPaymentReviewDetailDto reqDto);

}
