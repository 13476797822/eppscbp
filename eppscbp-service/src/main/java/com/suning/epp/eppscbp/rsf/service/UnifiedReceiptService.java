/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: UnifiedReceiptService.java
 * Author:   17061545
 * Date:     2018年3月20日 上午11:49:35
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.rsf.service;

import java.io.IOException;

import com.suning.epp.eppscbp.dto.req.CollAndSettleApplyDto;
import com.suning.epp.eppscbp.dto.req.OrderApplyAcquireDto;
import com.suning.epp.eppscbp.dto.req.OrderExRateRefreshDto;
import com.suning.epp.eppscbp.dto.req.OrderOperationDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleResDto;
import com.suning.epp.eppscbp.dto.res.OrderlAcquireResponseDto;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17061545
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface UnifiedReceiptService {

    /**
     * 
     * 单笔申请<br>
     * 〈功能详细描述〉
     *
     * @param stream
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<OrderlAcquireResponseDto> submiteSettleOrder(OrderApplyAcquireDto orderApplyAcquireDto);

    /**
     * 
     * 单笔申请<br>
     * 〈功能详细描述〉
     *
     * @param stream
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<OrderlAcquireResponseDto> batchSubmiteOrder(OrderApplyAcquireDto orderApplyAcquireDto);

    /**
     * 
     * 订单操作接口<br>
     * 〈功能详细描述〉
     *
     * @param stream
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<String> doOrderOperation(OrderOperationDto orderOperationDto, String operatingType);

    /**
     * 
     * 汇率刷新<br>
     * 〈功能详细描述〉
     *
     * @param businessNo
     * @param operatingType
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    OrderlAcquireResponseDto refreshRate(OrderExRateRefreshDto orderExRateRefreshDto);

    /**
     * 
     * 汇率刷新<br>
     * 〈功能详细描述〉
     *
     * @param businessNo
     * @param operatingType
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<CollAndSettleResDto> submitCollSettleOrder(CollAndSettleApplyDto collAndSettleApplyDto);

    /**
     * 
     * 收结汇订单关闭接口<br>
     * 〈功能详细描述〉
     *
     * @param stream
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<String> closeCollSettOrder(String payerAccount, String orderNo);

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param orderOperationDto
     * @param operatingTypeClose
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ApiResDto<String> queryDetailFlag(OrderOperationDto orderOperationDto);

}
