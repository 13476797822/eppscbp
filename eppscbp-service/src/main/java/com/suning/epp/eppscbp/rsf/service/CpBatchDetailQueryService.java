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

import com.suning.epp.eppscbp.dto.req.CpBatchPaymentDetailReqDto;
import com.suning.epp.eppscbp.dto.req.CpTranInOutDetailReqDto;
import com.suning.epp.eppscbp.dto.req.CpWithdrawDetailReqDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CpBatchPaymentDetailResDto;
import com.suning.epp.eppscbp.dto.res.CpTranInOutDetailResDto;
import com.suning.epp.eppscbp.dto.res.CpWithdrawDetailResDto;

import java.util.List;

/**
 * 〈明细查询和导出service〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface CpBatchDetailQueryService {

    ApiResDto<List<CpBatchPaymentDetailResDto>> batchPaymentDetailQuery(CpBatchPaymentDetailReqDto reqDto);

    ApiResDto<List<CpTranInOutDetailResDto>> tranInOutDetailQuery(CpTranInOutDetailReqDto reqDto);

    ApiResDto<List<CpWithdrawDetailResDto>> withdrawDetailQuery(CpWithdrawDetailReqDto reqDto);

}
