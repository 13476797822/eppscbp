package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;
import java.util.Date;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.enums.RefundOrderStatus;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

public class RefundOrderQueryRespDto {
	//退款订单号
	private String refundOrderNo;
	//商户订单号
	private String merchantOrderNo;
	//退款创建时间
	private Date refundCreateTime;
	//退款完成时间
	private Date refundFinishTime;
	//订单金额
	private Long amt;
	//清算金额
	private Long amtCny;
	//退款金额(订单币种)
	private Long refundAmt;
	//退款金额(清算币种)
	private Long refundAmtCny;
	//剩余可退金额(订单币种)
	private Long remainingAmt;
	//剩余可退金额(清算币种)
	private Long remainingAmtCny;
	//退款状态
	private Integer refundStatus;
	//失败原因
	private String refundFailReason;
	//币种
	private String cur;
	// 汇率 
    private BigDecimal exchangeRate;
	
	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public Date getRefundCreateTime() {
		return refundCreateTime;
	}

	public void setRefundCreateTime(Date refundCreateTime) {
		this.refundCreateTime = refundCreateTime;
	}

	public Date getRefundFinishTime() {
		return refundFinishTime;
	}

	public void setRefundFinishTime(Date refundFinishTime) {
		this.refundFinishTime = refundFinishTime;
	}

	public Long getAmt() {
		return amt;
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}

	public Long getAmtCny() {
		return amtCny;
	}

	public void setAmtCny(Long amtCny) {
		this.amtCny = amtCny;
	}

	public Long getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(Long refundAmt) {
		this.refundAmt = refundAmt;
	}

	public Long getRefundAmtCny() {
		return refundAmtCny;
	}

	public void setRefundAmtCny(Long refundAmtCny) {
		this.refundAmtCny = refundAmtCny;
	}

	public Long getRemainingAmt() {
		return remainingAmt;
	}

	public void setRemainingAmt(Long remainingAmt) {
		this.remainingAmt = remainingAmt;
	}

	public Long getRemainingAmtCny() {
		return remainingAmtCny;
	}

	public void setRemainingAmtCny(Long remainingAmtCny) {
		this.remainingAmtCny = remainingAmtCny;
	}
	
	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundFailReason() {
		return refundFailReason;
	}
	
	public String getRefundFailReasonStr() {
		return StringUtil.isEmpty(refundFailReason) ? CommonConstant.VALUE_IS_NULL : refundFailReason ;
	}

	public void setRefundFailReason(String refundFailReason) {
		this.refundFailReason = refundFailReason;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	//格式化
	public String getRefundCreateTimeStr() {
		String refundCreateTimeStr = DateUtil.formatDate(this.refundCreateTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(refundCreateTimeStr) ? CommonConstant.VALUE_IS_NULL : refundCreateTimeStr;
	}
	
	public String getRefundFinishTimeStr() {
		String refundFinishTimeStr = DateUtil.formatDate(this.refundFinishTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(refundFinishTimeStr) ? CommonConstant.VALUE_IS_NULL : refundFinishTimeStr; 
	}
	
	public String getAmtStr() {
		String amtStr = StringUtil.formatMoney(amt);
		return  amt == null ? CommonConstant.VALUE_IS_NULL : amtStr+ " "+cur;
	}
	public String getAmtCnyStr() {
		String amtCnyStr = StringUtil.formatMoney(amtCny);
		return  amtCny == null ? CommonConstant.VALUE_IS_NULL : amtCnyStr + " CNY";
	}
	
	public String getRefundAmtStr() {
		String refundAmtStr = StringUtil.formatMoney(refundAmt);
		return  refundAmt == null ? CommonConstant.VALUE_IS_NULL : refundAmtStr +" "+cur;
	}
	
	public String getRefundAmtCnyStr() {
		String refundAmtCnyStr = StringUtil.formatMoney(refundAmtCny);
		return  refundAmtCny == null ? CommonConstant.VALUE_IS_NULL : refundAmtCnyStr + " CNY";
	}
	
	public String getRemainingAmtStr() {
		String remainingAmtStr = StringUtil.formatMoney(remainingAmt);
		return  remainingAmt == null ? CommonConstant.VALUE_IS_NULL : remainingAmtStr +" "+cur;
	}
	
	public String getRemainingAmtCnyStr() {
		String remainingAmtCnyStr = StringUtil.formatMoney(remainingAmtCny);
		return  remainingAmtCny == null ? CommonConstant.VALUE_IS_NULL : remainingAmtCnyStr + " CNY";
	}
	
	public String getRefundStatusStr() {
		return RefundOrderStatus.getDescriptionFromCode(refundStatus);
	}
	
	@Override
    public String toString() {
        return "RefundOrderQueryRespDto{" +
                "refundCreateTime='" + refundCreateTime + '\'' +
                ", refundOrderNo='" + refundOrderNo + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", refundFinishTime='" + refundFinishTime + '\'' +
                ", amt='" + amt + '\'' +
                ", amtCny='" + amtCny + '\'' +
                ", refundAmt='" + refundAmt + '\'' +
                ", refundAmtCny='" + refundAmtCny + '\'' +
                ", remainingAmt='" + remainingAmt + '\'' +
                ", remainingAmtCny='" + remainingAmtCny + '\'' +
                ", refundFailReason='" + refundFailReason + '\'' + 
                ", cur='" + cur + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                '}';
    }
}
