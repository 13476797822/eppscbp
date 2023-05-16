/*
 * Copyright (C), 2002-2021, 苏宁易购电子商务有限公司
 * FileName: RejectDetailResDto.java
 * Author:   17033387
 * Date:     2021年9月13日 下午5:35:58
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
public class RejectDetailResDto {

    //拒付信息
    private RejectOrderQueryRespDto rejectOrderQueryRespDto;
    
    //申诉记录
    private List<AcRejectOrderResHisDto> acRejectOrderHisList;   

    /**
     * @return the rejectOrderQueryRespDto
     */
    public RejectOrderQueryRespDto getRejectOrderQueryRespDto() {
        return rejectOrderQueryRespDto;
    }

    /**
     * @param rejectOrderQueryRespDto the rejectOrderQueryRespDto to set
     */
    public void setRejectOrderQueryRespDto(RejectOrderQueryRespDto rejectOrderQueryRespDto) {
        this.rejectOrderQueryRespDto = rejectOrderQueryRespDto;
    }

    /**
     * @return the acRejectOrderHisList
     */
    public List<AcRejectOrderResHisDto> getAcRejectOrderHisList() {
        return acRejectOrderHisList;
    }

    /**
     * @param acRejectOrderHisList the acRejectOrderHisList to set
     */
    public void setAcRejectOrderHisList(List<AcRejectOrderResHisDto> acRejectOrderHisList) {
        this.acRejectOrderHisList = acRejectOrderHisList;
    }

}
