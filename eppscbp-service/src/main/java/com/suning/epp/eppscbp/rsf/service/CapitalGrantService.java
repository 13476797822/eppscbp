package com.suning.epp.eppscbp.rsf.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.req.CapitalGrantDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CapitalGrantResDto;

/**
 * @author 88412423
 *
 */
public interface CapitalGrantService {

    ApiResDto<String> getCapitalGrant(CapitalGrantDto capitalGrantDto);

    ApiResDto<List<CapitalGrantResDto>> pendingPaymentQuery(CapitalGrantDto capitalGrantDto);

    ApiResDto<String> batchPayment(OrdersAuditRequestDto requestDto);

    ApiResDto<String> importBatchPaymentDetails(CapitalGrantDto capitalGrantDto);
}
