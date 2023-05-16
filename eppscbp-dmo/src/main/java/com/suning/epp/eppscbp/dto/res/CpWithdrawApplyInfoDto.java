/*
 * Copyright (C), 2002-2019, 苏宁易购电子商务有限公司
 * FileName: CpWithdrawApplyInfoDto.java
 * Author:   17090884
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
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CpWithdrawApplyInfoDto {

    /**
     * 易付宝商户提现笔数
     */
    private String withdrawAmount;
    /**
     * 易付宝商户提现总金额
     */
    private String withdrawTotalAmt;

    /**
     * 提现币种组合信息
     */
    private List<CpWithdrawApplyResDto> arrivalInfoList;

    /**
     * 出账批次组合信息
     */
    private List<CpWithdrawApplyResDto> list;

    public List<CpWithdrawApplyResDto> getArrivalInfoList() {
        return arrivalInfoList;
    }

    public void setArrivalInfoList(List<CpWithdrawApplyResDto> arrivalInfoList) {
        this.arrivalInfoList = arrivalInfoList;
    }

    public List<CpWithdrawApplyResDto> getList() {
        return list;
    }

    public void setList(List<CpWithdrawApplyResDto> list) {
        this.list = list;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getWithdrawTotalAmt() {
        return withdrawTotalAmt;
    }

    public void setWithdrawTotalAmt(String withdrawTotalAmt) {
        this.withdrawTotalAmt = withdrawTotalAmt;
    }

    @Override
    public String toString() {
        return "CpWithdrawApplyInfoDto{" +
                "withdrawAmount='" + withdrawAmount + '\'' +
                ", withdrawTotalAmt='" + withdrawTotalAmt + '\'' +
                ", arrivalInfoList=" + arrivalInfoList +
                ", list=" + list +
                '}';
    }
}
