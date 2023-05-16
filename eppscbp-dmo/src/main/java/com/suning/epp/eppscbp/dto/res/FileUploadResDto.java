/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: FileUploadResDto.java
 * Author:   17033387
 * Date:     2018年4月11日 下午5:08:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.dto.res;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FileUploadResDto extends CommonResDto {

    /**
     */
    private static final long serialVersionUID = 5630186881044028092L;
    
    private String fileAddress;
    
    private Integer detailAmount;

    /**
     * @return the fileAddress
     */
    public String getFileAddress() {
        return fileAddress;
    }

    /**
     * @param fileAddress the fileAddress to set
     */
    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    /**
     * @return the detailAmount
     */
    public Integer getDetailAmount() {
        return detailAmount;
    }

    /**
     * @param detailAmount the detailAmount to set
     */
    public void setDetailAmount(Integer detailAmount) {
        this.detailAmount = detailAmount;
    }
}
