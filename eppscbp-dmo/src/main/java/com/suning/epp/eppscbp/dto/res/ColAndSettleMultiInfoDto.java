/*
 * Copyright (C), 2002-2019, 苏宁易购电子商务有限公司
 * FileName: ColAndSettleMultiInfoDto.java
 * Author:   17033387
 * Date:     2019年2月21日 下午5:17:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.dto.res;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ColAndSettleMultiInfoDto {
    
    /**
     * 来账组合信息
     */
    private List<ArrivalInfoResDto> arrivalInfoList;

    /**
     * @return the arrivalInfoList
     */
    public List<ArrivalInfoResDto> getArrivalInfoList() {
        return arrivalInfoList;
    }

    /**
     * @param arrivalInfoList the arrivalInfoList to set
     */
    public void setArrivalInfoList(List<ArrivalInfoResDto> arrivalInfoList) {
        this.arrivalInfoList = arrivalInfoList;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ColAndSettleMultiInfoDto [arrivalInfoList=" + arrivalInfoList + "]";
    }
     
}
