/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.CtCollAndSettleOrderStatus;
import com.suning.epp.eppscbp.common.enums.CurType;

/**
 * @author 17080704
 *
 */
public class CollAndSettleDetailResDto {
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
     * 交易明细数量
     */
    private String transactionNum;
    /**
     * 订单交易汇率
     */
    private String orderRate;
    /**
     * 订单完成时间
     */
    private String orderComplateTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 商户结汇金额RMB
     */
    private String actualAmount;
    /**
     * 商户实际结汇金额RMB(减手续费)
     */
    private String actualSubAmount;
    /**
     * 手续费
     */
    private String poundageAmount;

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

    public String getTransactionNum() {
        return transactionNum;
    }

    public void setTransactionNum(String transactionNum) {
        this.transactionNum = transactionNum;
    }

    public String getOrderRate() {
        return orderRate;
    }

    public void setOrderRate(String orderRate) {
        this.orderRate = orderRate;
    }

    public String getOrderComplateTime() {
        return orderComplateTime;
    }

    public void setOrderComplateTime(String orderComplateTime) {
        this.orderComplateTime = orderComplateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getActualSubAmount() {
        return actualSubAmount;
    }

    public void setActualSubAmount(String actualSubAmount) {
        this.actualSubAmount = actualSubAmount;
    }

    public String getPoundageAmount() {
        return poundageAmount;
    }

    public void setPoundageAmount(String poundageAmount) {
        this.poundageAmount = poundageAmount;
    }

    public String getCurrencyName() {
        return CurType.getDescriptionFromCode(currency);
    }

    public String getStatusName() {
        return CtCollAndSettleOrderStatus.getDescriptionFromCode(status);
    }

    @Override
    public String toString() {
        return "CollAndSettleDetailResDto [orderNo=" + orderNo + ", currency=" + currency + ", orderCreateTime=" + orderCreateTime + ", orderAmt=" + orderAmt + ", transactionNum=" + transactionNum + ", orderRate=" + orderRate + ", orderComplateTime=" + orderComplateTime + ", status=" + status + ", actualAmount=" + actualAmount + ", actualSubAmount=" + actualSubAmount + ", poundageAmount=" + poundageAmount + "]";
    }

}
