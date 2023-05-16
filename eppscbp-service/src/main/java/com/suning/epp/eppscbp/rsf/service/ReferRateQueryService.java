/**
 * 
 */
package com.suning.epp.eppscbp.rsf.service;

import java.util.List;

import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.ReferRateQueryResDto;

/**
 * @author 17080704
 *
 */
public interface ReferRateQueryService {

    /**
     * 功能描述: <br>
     * 〈中信银行参考汇率查询〉
     * 
     * @return
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<List<ReferRateQueryResDto>> referRateQuery();


    /**
     * 功能描述: <br>
     * 〈中信/光大银行参考汇率查询〉
     *
     * @return
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public ApiResDto<List<ReferRateQueryResDto>> referRateQueryZXorGD(String channelId);

}
