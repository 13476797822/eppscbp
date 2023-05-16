package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;
import java.util.Date;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.enums.RejectOrderStatus;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

public class RejectOrderQueryRespDto {
	//拒付业务单号
	private String rejectOrderNo;
	//商户订单号
	private String merchantOrderNo;
	//拒付创建时间
	private Date createTime;
	//拒付完成时间
	private Date updateTime;
	//订单金额
	private Long amt;
	//清算金额
    private Long amtCny;
	//拒付处理费
	private Long rejectFee;
	//拒付状态
	private Integer status;
	//拒付原因
	private String failReason;
	//拒付申请时间
	private Date applyDate;
	//最后处理时间
	private Date lastDate;
	//拒付金额
	private Long rejectAmt;
	//拒付清算金额
	private Long rejectAmtCny;
	//币种
	private String cur;
	//汇率
    private BigDecimal exchangeRate;
    //拒付说明
    private String rejectComment;
    //原因类型
    private String reasonCode;
    //原因分类
    private String reasonClass;
	
	public String getRejectOrderNo() {
		return rejectOrderNo;
	}

	public void setRejectOrderNo(String rejectOrderNo) {
		this.rejectOrderNo = rejectOrderNo;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	public Long getAmt() {
		return amt;
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}

	/**
     * @return the amtCny
     */
    public Long getAmtCny() {
        return amtCny;
    }

    /**
     * @param amtCny the amtCny to set
     */
    public void setAmtCny(Long amtCny) {
        this.amtCny = amtCny;
    }

    public Long getRejectFee() {
		return rejectFee;
	}

	public void setRejectFee(Long rejectFee) {
		this.rejectFee = rejectFee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getLastDate() {
		return lastDate;
	}
	
	public String getLastDateFlag() {
		if(null != lastDate && DateUtil.isToday(lastDate)) {
			return "T";
		}else {
			return "F";
		}
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Long getRejectAmt() {
		return rejectAmt;
	}

	public void setRejectAmt(Long rejectAmt) {
		this.rejectAmt = rejectAmt;
	}
	
	public Long getRejectAmtCny() {
		return rejectAmtCny;
	}

	public void setRejectAmtCny(Long rejectAmtCny) {
		this.rejectAmtCny = rejectAmtCny;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	/**
     * @return the exchangeRate
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate the exchangeRate to set
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getRejectComment() {
		return rejectComment;
	}

	public void setRejectComment(String rejectComment) {
		this.rejectComment = rejectComment;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonClass() {
		return reasonClass;
	}

	public void setReasonClass(String reasonClass) {
		this.reasonClass = reasonClass;
	}

	//格式化
	public String getUpdateTimeStr() {
		String updateTimeStr = DateUtil.formatDate(this.updateTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(updateTimeStr) ? CommonConstant.VALUE_IS_NULL : updateTimeStr;
	}
	public String getCreateTimeStr() {
		String createTimeStr = DateUtil.formatDate(this.createTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(createTimeStr) ? CommonConstant.VALUE_IS_NULL : createTimeStr;
	}
	public String getAmtStr() {
		String amtStr = StringUtil.formatMoney(amt);
		return amt == null ? CommonConstant.VALUE_IS_NULL : amtStr +" "+cur;
	}
	public String getAmtCnyStr() {
        String amtCnyStr = StringUtil.formatMoney(amtCny);
        return amtCny == null ? CommonConstant.VALUE_IS_NULL : amtCnyStr +" CNY";
    }
	public String getRejectAmtCnyStr() {
		String rejectAmtCnyStr = StringUtil.formatMoney(this.rejectAmtCny);
		return rejectAmtCny == null ? CommonConstant.VALUE_IS_NULL : rejectAmtCnyStr +" CNY";
	}
	
	public String getRejectFeeStr() {
		String rejectFeeStr = StringUtil.formatMoney(this.rejectFee);
		return rejectFee == null ? CommonConstant.VALUE_IS_NULL : rejectFeeStr +" CNY";
	}
	
	public String getStatusStr() {
		return RejectOrderStatus.getDescriptionFromCode(this.status);
	}
	
	public String getApplyDateStr() {
		String applyDateStr = DateUtil.formatDate(this.applyDate, DateConstant.DATE_FORMAT);
		return StringUtil.isEmpty(applyDateStr) ? CommonConstant.VALUE_IS_NULL : applyDateStr;
	}
	
	public String getLastDateStr() {
		String lastDateStr = DateUtil.formatDate(this.lastDate, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(lastDateStr) ? CommonConstant.VALUE_IS_NULL : lastDateStr;
	}
	public String getFailReasonStr() {
		return StringUtil.isEmpty(failReason) ? CommonConstant.VALUE_IS_NULL : failReason;
	}
	public String getRejectAmtStr() {
		String rejectAmtStr = StringUtil.formatMoney(this.rejectAmt);
		return rejectAmt == null ? CommonConstant.VALUE_IS_NULL : rejectAmtStr +" "+cur;
	}
	
	public String getRejectCommentStr() {
		String reasonClassStr = StringUtil.isEmpty(reasonClass) ? "" : reasonClass+",";
		String rejectCommentStr = StringUtil.isEmpty(rejectComment) ? "" : rejectComment;
		return reasonClassStr + rejectCommentStr;
	}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RejectOrderQueryRespDto [rejectOrderNo=");
        builder.append(rejectOrderNo);
        builder.append(", merchantOrderNo=");
        builder.append(merchantOrderNo);
        builder.append(", createTime=");
        builder.append(createTime);
        builder.append(", updateTime=");
        builder.append(updateTime);
        builder.append(", amt=");
        builder.append(amt);
        builder.append(", amtCny=");
        builder.append(amtCny);
        builder.append(", rejectFee=");
        builder.append(rejectFee);
        builder.append(", status=");
        builder.append(status);
        builder.append(", failReason=");
        builder.append(failReason);
        builder.append(", applyDate=");
        builder.append(applyDate);
        builder.append(", lastDate=");
        builder.append(lastDate);
        builder.append(", rejectAmt=");
        builder.append(rejectAmt);
        builder.append(", rejectAmtCny=");
        builder.append(rejectAmtCny);
        builder.append(", cur=");
        builder.append(cur);
        builder.append(", exchangeRate=");
        builder.append(exchangeRate);
        builder.append(", rejectComment=");
        builder.append(rejectComment);
        builder.append(", reasonCode=");
        builder.append(reasonCode);
        builder.append(", reasonClass=");
        builder.append(reasonClass);
        builder.append("]");
        return builder.toString();
    }
	
}
