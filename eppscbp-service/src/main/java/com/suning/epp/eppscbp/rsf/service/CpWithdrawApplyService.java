/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpWithdrawApplyService.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.rsf.service;

import com.suning.epp.eppscbp.dto.req.CpWithdrawApplyDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CpWithdrawApplyInfoDto;
import com.suning.epp.eppscbp.dto.res.CpWithdrawApplyResDto;

/**
 * 〈提现币种查询和导出service〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface CpWithdrawApplyService {

    /**
     * 出账批次信息查询<br>
     * 〈功能详细描述〉
     *
     * @param outAccountBatch 出账批次
     * @param payerAccount    商户户头号
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<CpWithdrawApplyInfoDto> batchQuery(String outAccountBatch, String currency, String payerAccount);

    /**
     * 是否包含易付宝商户资金查询<br>
     * 〈功能详细描述〉
     *
     * @param currency     币种
     * @param payerAccount 商户户头号
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<CpWithdrawApplyInfoDto> outBatchQuery(String currency, String payerAccount);

    /**
     * 提现申请<br>
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<CpWithdrawApplyResDto> submitWithdrawApply(CpWithdrawApplyDto dto);

}
