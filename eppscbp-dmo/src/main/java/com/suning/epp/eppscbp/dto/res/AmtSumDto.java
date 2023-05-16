package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 支付金额汇总/收入金额汇总dto
 * 
 * @author 19043747
 *
 */
public class AmtSumDto {
	// 币种中文名
	private String curZh;
	// 收入金额合计
	private long revenueSum;
	// 支出金额合计
	private long expensesSum;
	
	public String getCurZh() {
		return curZh;
	}
	public void setCurZh(String curZh) {
		this.curZh = curZh;
	}
	public String getCurZhStr() {
		return curZh.split(":")[1];
	}
	public String getCur() {
		return CurType.getUnitFromCode(curZh.split(":")[0]);
	}
	public long getRevenueSum() {
		return revenueSum;
	}
	public String getRevenueSumStr() {
		return StringUtil.formatMoney(revenueSum);
	}
	public void setRevenueSum(long revenueSum) {
		this.revenueSum = revenueSum;
	}
	public long getExpensesSum() {
		return expensesSum;
	}
	public String getExpensesSumStr() {
		return StringUtil.formatMoney(expensesSum);
	}
	public void setExpensesSum(long expensesSum) {
		this.expensesSum = expensesSum;
	}
	@Override
    public String toString() {
        return "AmtSumDto [curZh=" + curZh + ", revenueSum=" + revenueSum + ", expensesSum=" + expensesSum  + "]";
    }
}
