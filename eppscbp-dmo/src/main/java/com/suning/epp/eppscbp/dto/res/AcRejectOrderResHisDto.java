package com.suning.epp.eppscbp.dto.res;

import java.util.Date;

import com.suning.epp.eppscbp.common.constant.CommonConstant;
import com.suning.epp.eppscbp.common.constant.DateConstant;
import com.suning.epp.eppscbp.common.enums.LastStatus;
import com.suning.epp.eppscbp.common.enums.RejectAuditStatus;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

public class AcRejectOrderResHisDto {
	//申诉编号
	private String rejectAppealNo;
	//申诉材料路径
	private String appealAttach;
	//上传时间
	private Date uploadTime;
	//审核结果
	private String status;
	//审核意见
	private String opinion;
	//最终申诉结果
	private String lastStatus;
	//银行结果附件路径
	private String bankAttach;
	
	public String getRejectAppealNo() {
		return rejectAppealNo;
	}

	public void setRejectAppealNo(String rejectAppealNo) {
		this.rejectAppealNo = rejectAppealNo;
	}

	public String getAppealAttach() {
		return appealAttach;
	}

	public void setAppealAttach(String appealAttach) {
		this.appealAttach = appealAttach;
	}
	
	public String getAppealAttachStr() {
		if(!StringUtil.isEmpty(appealAttach)) {
			String[] appealAttachArr = appealAttach.split("_");
			return appealAttachArr[appealAttachArr.length-1];
		}
		return  CommonConstant.VALUE_IS_NULL;
	}

	public Date getUploadTime() {
		return uploadTime;
	}
	
	public String getUploadTimeStr() {
		return DateUtil.formatDate(this.uploadTime, DateConstant.DATEFORMATEyyyyMMddHHmmss);
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getStatus() {
		return status;
	}
	
	public String getStatusStr() {
		return RejectAuditStatus.getDescriptionFromCode(status);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getLastStatus() {
		return lastStatus;
	}
	public String getLastStatusStr() {
		return StringUtil.isEmpty(lastStatus) ? CommonConstant.VALUE_IS_NULL : LastStatus.getDescriptionFromCode(lastStatus);
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String getBankAttach() {
		return bankAttach;
	}
	public String getBankAttachStr() {
		if(!StringUtil.isEmpty(bankAttach)) {
			// 银行结果名称
			String[] bankAttachArr = bankAttach.split("_");
			return bankAttachArr[bankAttachArr.length-1];
		}
		return  CommonConstant.VALUE_IS_NULL;
		
	}

	public void setBankAttach(String bankAttach) {
		this.bankAttach = bankAttach;
	}

	@Override
    public String toString() {
        return "AcRejectOrderResHisDto{" +
                "rejectAppealNo='" + rejectAppealNo + '\'' +
                ", appealAttach='" + appealAttach + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", status='" + status + '\'' +
                ", opinion='" + opinion + '\'' +
                ", lastStatus='" + lastStatus + '\'' +
                ", bankAttach='" + bankAttach + '\'' +
                '}';
    }
}
