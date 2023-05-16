/*
 * Copyright (C), 2002-2019, 苏宁易购电子商务有限公司
 * FileName: OrderCalculateRequestDto.java
 * Author:   17033387
 * Date:     2019年1月8日 下午4:02:19
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.dto.req;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author 17033387
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OrderCalculateDto {

    // 计算结果
    private Boolean flag;

    // 可批付金额
    private String prePayAmount;

    // 金额总数
    private String amountCount;

    // 总笔数
    private String numberCount;

    // 明细
    private List<OrderCalculateDetailDto> details;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getPrePayAmount() {
        return prePayAmount;
    }

    public void setPrePayAmount(String prePayAmount) {
        this.prePayAmount = prePayAmount;
    }

    /**
     * @return the amountCount
     */
    public String getAmountCount() {
        return amountCount;
    }

    /**
     * @param amountCount the amountCount to set
     */
    public void setAmountCount(String amountCount) {
        this.amountCount = amountCount;
    }

    /**
     * @return the numberCount
     */
    public String getNumberCount() {
        return numberCount;
    }

    /**
     * @param numberCount the numberCount to set
     */
    public void setNumberCount(String numberCount) {
        this.numberCount = numberCount;
    }

    /**
     * @return the details
     */
    public List<OrderCalculateDetailDto> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(List<OrderCalculateDetailDto> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "OrderCalculateDto [flag=" + flag + ", prePayAmount=" + prePayAmount + ", amountCount=" + amountCount + ", numberCount=" + numberCount + "]";
    }

}
