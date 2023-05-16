/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: BatchPaymentService.java
 * Author:   17033387
 * Date:     2018年11月10日 上午11:30:16
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.req.OrderCalculateDto;
import com.suning.epp.eppscbp.dto.req.OrdersAuditRequestDetailDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentOrderQueryAndCalculateResDto;
import com.suning.epp.eppscbp.dto.res.BatchPaymentQueryResDto;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface BatchPaymentService {

    /**
     * 
     * 功能描述: 批付申请<br>
     * 〈功能详细描述〉
     *
     * @param payerAccount
     * @param businessNo
     * @param batchPaymentDetail
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<String> ordersAudit(String payerAccount, String businessNo, List<OrdersAuditRequestDetailDto> batchPaymentDetail);

    /**
     * 
     * 功能描述: 批付分笔计算<br>
     * 〈功能详细描述〉
     *
     * @param payerAccount
     * @param businessNo
     * @param batchPaymentDetail
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    OrderCalculateDto ordersCalculate(OrderCalculateDto ordersAuditRequestDto);

    /**
     * 
     * 功能描述: 批付分笔计算<br>
     * 〈功能详细描述〉
     *
     * @param payerAccount
     * @param businessNo
     * @param batchPaymentDetail
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    OrderCalculateDto cpOrdersCalculate(OrderCalculateDto ordersAuditRequestDto);

    /**
     * 
     * 功能描述: 批付查询<br>
     * 〈功能详细描述〉
     *
     * @param payerAccount
     * @param businessNo
     * @param batchPaymentDetail
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<List<BatchPaymentQueryResDto>> ordersQuery(String payerAccount, String businessNo);

    /**
     * 
     * 功能描述: 批付查询-调批付查询<br>
     * 〈功能详细描述〉
     *
     * @param payerAccount
     * @param businessNo
     * @param batchPaymentDetail
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<List<BatchPaymentQueryResDto>> resultQuery(String payerAccount, String businessNo);

    /**
     * 
     * 功能描述: 批付查询-调批付查询<br>
     * 〈功能详细描述〉
     *
     * @param payerAccount
     * @param businessNo
     * @param batchPaymentDetail
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<String> batchPaymentUpload(String businessNo, String payerAccount, String fileAdd, String type);

    
    /**
     * 
     * 功能描述: 批付查询-调批付查询<br>
     * 〈功能详细描述〉
     *
     * @param payerAccount
     * @param businessNo
     * @param batchPaymentDetail
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<BatchPaymentOrderQueryAndCalculateResDto> bpOrdersParseAndCalculate(String businessNo, String remoteUser, String fileAddress, String platformCode, String prePayAmount, String bizType);
}
