/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpWithdrawDetailResDto.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.CbpSupStatus;
import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.StorePlatformEnum;
import com.suning.epp.eppscbp.common.enums.WithdrawOrderStatus;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈提现明细DTO〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CpWithdrawDetailResDto {

    /**
     * 店铺平台
     */
    private String storePlatform;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 付款方商户户头号
     */
    private String payerAccount;
    /**
     * 店铺站点
     */
    private String storeSite;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 收款方银行帐号
     */
    private String payeeAccount;
    /**
     * 提现金额
     */
    private Long withdrawAmt;
    /**
     * 币种
     */
    private String currency;
    /**
     * 提现时间
     */
    private String withdrawTime;
    /**
     * 结汇收入(人民币)
     */
    private Long settleAmt;
    /**
     * 结汇汇率
     */
    private String settleRate;
    /**
     * 手续费
     */
    private Long feeAmt;
    /**
     * 真实材料上传状态
     */
    private String realInfoStatus;
    /**
     * 真实材料上传失败原因
     */
    private String realInfoReason;
    /**
     * 提现订单ID
     */
    private String withdrawOrderNo;
    /**
     * 当前页
     */
    private String currentPage;
    
    /**
     * 真实性材料合并方式
     */
    private String superviseCombineTypeAuth;

    public String getStorePlatform() {
        return storePlatform;
    }

    public void setStorePlatform(String storePlatform) {
        this.storePlatform = storePlatform;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getStoreSite() {
        return storeSite;
    }

    public void setStoreSite(String storeSite) {
        this.storeSite = storeSite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWithdrawOrderNo() {
        return withdrawOrderNo;
    }

    public void setWithdrawOrderNo(String withdrawOrderNo) {
        this.withdrawOrderNo = withdrawOrderNo;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public Long getWithdrawAmt() {
        return withdrawAmt;
    }

    public void setWithdrawAmt(Long withdrawAmt) {
        this.withdrawAmt = withdrawAmt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Long getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(Long settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getSettleRate() {
        return settleRate;
    }

    public void setSettleRate(String settleRate) {
        this.settleRate = settleRate;
    }

    public Long getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(Long feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getRealInfoStatus() {
        return realInfoStatus;
    }

    public void setRealInfoStatus(String realInfoStatus) {
        this.realInfoStatus = realInfoStatus;
    }

    public String getRealInfoReason() {
        return realInfoReason;
    }

    public void setRealInfoReason(String realInfoReason) {
        this.realInfoReason = realInfoReason;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getStorePlatformName() {
        return StorePlatformEnum.getDescriptionFromCode(this.storePlatform);
    }

    public String getCurrencyName() {
        return CurType.getDescriptionFromCode(this.currency);
    }

    public String getStatusName() {
        return WithdrawOrderStatus.getDescriptionFromCode(this.status);
    }

    public String getWithdrawAmtStr() {
        return StringUtil.formatMoney(this.withdrawAmt);
    }

    public String getSettleAmtStr() {
        return StringUtil.formatMoney(this.settleAmt);
    }

    public String getFeeAmtStr() {
        return StringUtil.formatMoney(this.feeAmt);
    }
    
    public String getRealInfoStatusStr() {
        return CbpSupStatus.getDescriptionFromCode(this.realInfoStatus);
    }
    
    

    public String getSuperviseCombineTypeAuth() {
		return superviseCombineTypeAuth;
	}

	public void setSuperviseCombineTypeAuth(String superviseCombineTypeAuth) {
		this.superviseCombineTypeAuth = superviseCombineTypeAuth;
	}

	@Override
    public String toString() {
        return "CpWithdrawDetailResDto{" +
                "storePlatform='" + storePlatform + '\'' +
                ", storeName='" + storeName + '\'' +
                ", payerAccount='" + payerAccount + '\'' +
                ", storeSite='" + storeSite + '\'' +
                ", status='" + status + '\'' +
                ", payeeAccount='" + payeeAccount + '\'' +
                ", withdrawAmt='" + withdrawAmt + '\'' +
                ", currency='" + currency + '\'' +
                ", withdrawTime='" + withdrawTime + '\'' +
                ", settleAmt='" + settleAmt + '\'' +
                ", settleRate='" + settleRate + '\'' +
                ", feeAmt='" + feeAmt + '\'' +
                ", realInfoStatus='" + realInfoStatus + '\'' +
                ", realInfoReason='" + realInfoReason + '\'' +
                ", withdrawOrderNo='" + withdrawOrderNo + '\'' +
                ", currentPage='" + currentPage + '\'' +
                ", superviseCombineTypeAuth='" + superviseCombineTypeAuth + '\'' +
                '}';
    }
}
