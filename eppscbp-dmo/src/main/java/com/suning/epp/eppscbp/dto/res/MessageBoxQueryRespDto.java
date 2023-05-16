package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.enums.RejectOrderStatus;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

import java.util.Date;

public class MessageBoxQueryRespDto {

	//商户订单号
	private String merchantOrderNo;
	//创建时间
	private Date createTime;
	//订单金额
	private Long amt;
	//币种
	private String cur;
    //拒付/退款标识
    private String flag;

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateTimeStr() {
		return DateUtil.formatDate(this.createTime,DateConstant.DATEFORMATEyyyyMMddHHmmss);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getAmt() {
		return amt;
	}

	public String getAmtStr() {
		return StringUtil.formatMoney(amt);
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public String getFlag() {
		return flag;
	}

	public String getFlagStr() {
		String flagStr = "";
		if (flag.equals("1")) {
			flagStr = "退款";
		}else {
			flagStr = "拒付";
		}
		return flagStr;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "MessageBoxQueryRespDto{" +
				"merchantOrderNo='" + merchantOrderNo + '\'' +
				", createTime=" + createTime +
				", amt=" + amt +
				", cur='" + cur + '\'' +
				", flag='" + flag + '\'' +
				'}';
	}
}
