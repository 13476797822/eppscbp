package com.suning.epp.eppscbp.rsf.service;

import java.math.BigDecimal;

/**
 * 〈外管汇率查询〉<br>
 * 〈功能详细描述〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface SafeRateQueryService {

    /**
     * 功能描述: <br>
     * 〈外管汇率查询〉
     * 
     * @return
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public BigDecimal safeRateQuery(String cur);

}
