package com.suning.epp.eppscbp.dto.res;

import java.util.Date;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.LogisticsInfoStatus;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

public class LogisticsInfoQueryResDto {
	//商户订单号
	private String merchantOrderNo;
	//币种
	private String cur;
	//订单金额
	private Long amt;
	//支付时间
	private Date payFinishTime;
	//发货日期
	private Date shipingDate;
	//物流公司名称
	private String logisticsCompName;
	//物流单号
	private String logisticsWoNo;
	//物流状态
	private Integer logisticsStatus;
	//上传时间
	private Date uploadTime;
	
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public Long getAmt() {
		return amt;
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}

	public Date getPayFinishTime() {
		return payFinishTime;
	}

	public void setPayFinishTime(Date payFinishTime) {
		this.payFinishTime = payFinishTime;
	}

	public Date getShipingDate() {
		return shipingDate;
	}

	public void setShipingDate(Date shipingDate) {
		this.shipingDate = shipingDate;
	}

	public String getLogisticsCompName() {
		return logisticsCompName;
	}

	public void setLogisticsCompName(String logisticsCompName) {
		this.logisticsCompName = logisticsCompName;
	}

	public String getLogisticsWoNo() {
		return logisticsWoNo;
	}

	public void setLogisticsWoNo(String logisticsWoNo) {
		this.logisticsWoNo = logisticsWoNo;
	}

	public Integer getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(Integer logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	//格式化
	public String getCurStr() {
		return CurType.getDescriptionFromCode(cur);
	}
	
	public String getAmtStr() {
		return amt == null ? CommonConstant.VALUE_IS_NULL : StringUtil.formatMoney(amt)+" "+cur;
	}
	
	public String getPayFinishTimeStr() {
		String payFinishTimeStr = DateUtil.formatDate(this.payFinishTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(payFinishTimeStr) ? CommonConstant.VALUE_IS_NULL :payFinishTimeStr;
	}
	public String getShipingDateStr() {
		String shipingDateStr = DateUtil.formatDate(this.shipingDate, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(shipingDateStr) ? CommonConstant.VALUE_IS_NULL :shipingDateStr;
	}
	public String getLogisticsStatusStr() {
		return LogisticsInfoStatus.getDescriptionFromCode(logisticsStatus);
	}
	
	public String getUploadTimeStr() {
		String uploadTimeStr =DateUtil.formatDate(this.uploadTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
		return StringUtil.isEmpty(uploadTimeStr) ? CommonConstant.VALUE_IS_NULL : uploadTimeStr;
	}
	
	public String getLogisticsCompNameStr() {
		return StringUtil.isEmpty(logisticsCompName) ? CommonConstant.VALUE_IS_NULL : logisticsCompName;
	}
	
	public String getLogisticsWoNoStr() {
		return StringUtil.isEmpty(logisticsWoNo) ? CommonConstant.VALUE_IS_NULL : logisticsWoNo;
	}
	
	@Override
    public String toString() {
        return "LogisticsInfoQueryResDto{" +
                "logisticsStatus='" + logisticsStatus + '\'' +
                ", cur='" + cur + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", amt='" + amt + '\'' +
                ", payFinishTime='" + payFinishTime + '\'' +
                ", shipingDate='" + shipingDate + '\'' +
                ", logisticsCompName='" + logisticsCompName + '\'' +
                 ", logisticsWoNo='" + logisticsWoNo + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                '}';
    }
}
