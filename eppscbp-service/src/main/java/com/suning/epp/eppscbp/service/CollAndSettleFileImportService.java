/**
 * 
 */
package com.suning.epp.eppscbp.service;

import com.suning.epp.eppscbp.dto.req.CollAndSettleApplyDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleResDto;

/**
 * @author 17080704
 *
 */
public interface CollAndSettleFileImportService {

    /**
     * 
     * 收结汇申请<br>
     * 〈功能详细描述〉
     *
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<CollAndSettleResDto> submitSettleOrder(CollAndSettleApplyDto collAndSettleApplyDto);

}
