/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.BatchPayFlag;
import com.suning.epp.eppscbp.common.enums.CbpSupStatus;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.common.enums.CtCollAndSettleOrderStatus;
import com.suning.epp.eppscbp.common.enums.CurType;

/**
 * @author 17080704
 *
 */
public class CollAndSettleQueryResDto {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 币种代码
     */
    private String currency;
    /**
     * 订单创建时间
     */
    private String orderCreateTime;
    /**
     * 结汇申请金额
     */
    private String orderAmt;
    /**
     * 状态
     */
    private String state;

    /**
     * 监管信息状态
     */
    private String supStatus;

    /**
     * 监管失败原因
     */
    private String supErrMsg;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 商户收入
     */
    private String actualRevenue;

    /**
     * 已批付金额
     */
    private String payedAmount;

    /**
     * 待批付金额
     */
    private String prePayAmount;

    /**
     * 批付状态
     */
    private String batchPayFlag;
    
    /**
     * 真实性材料合并状态
     */
    private String superviseCombineTypeAuth;

    /**
     * 数据来源
     */
    private String dataSource;


    public String getActualRevenue() {
        return actualRevenue;
    }

    public void setActualRevenue(String actualRevenue) {
        this.actualRevenue = actualRevenue;
    }

    public String getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(String payedAmount) {
        this.payedAmount = payedAmount;
    }

    public String getPrePayAmount() {
        return prePayAmount;
    }

    public void setPrePayAmount(String prePayAmount) {
        this.prePayAmount = prePayAmount;
    }

    public String getBatchPayFlag() {
        return batchPayFlag;
    }

    public void setBatchPayFlag(String batchPayFlag) {
        this.batchPayFlag = batchPayFlag;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the supStatus
     */
    public String getSupStatus() {
        return supStatus;
    }

    /**
     * @param supStatus the supStatus to set
     */
    public void setSupStatus(String supStatus) {
        this.supStatus = supStatus;
    }

    public String getCurrencyName() {
        return CurType.getDescriptionFromCode(currency);
    }

    public String getStateName() {
        return CtCollAndSettleOrderStatus.getDescriptionFromCode(state);
    }

    public String getSupStatusName() {
        return CbpSupStatus.getDescriptionFromCode(supStatus);
    }

    public String getSupErrMsg() {
        return supErrMsg;
    }

    public void setSupErrMsg(String supErrMsg) {
        this.supErrMsg = supErrMsg;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizTypeStr() {
        return ChargeBizType.getDescriptionFromCode(bizType);
    }

    public String getBatchPayFlagStr() {
        return BatchPayFlag.getDescriptionFromCode(this.batchPayFlag);
    }

    public String getSuperviseCombineTypeAuth() {
		return superviseCombineTypeAuth;
	}

	public void setSuperviseCombineTypeAuth(String superviseCombineTypeAuth) {
		this.superviseCombineTypeAuth = superviseCombineTypeAuth;
	}

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String toString() {
        return "CollAndSettleQueryResDto [orderNo=" + orderNo + ", currency=" + currency + ", orderCreateTime=" + orderCreateTime + ", orderAmt=" + orderAmt + ", state=" + state + ", supStatus=" + supStatus + ", supErrMsg=" + supErrMsg + ", bizType=" + bizType + ", actualRevenue=" + actualRevenue + ", payedAmount=" + payedAmount + ", prePayAmount=" + prePayAmount + ", batchPayFlag=" + batchPayFlag + ", dataSource=" + dataSource + ", superviseCombineTypeAuth=" + superviseCombineTypeAuth+ "]";
    }

}
