/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpWithdrawDetailReqDto.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * 〈出入账明细DTO〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CpWithdrawDetailReqDto {

    /**
     * 付款方商户户头号
     */
    private String payerAccount;

    /**
     * 店铺平台
     */
    private String storePlatform;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 币种
     */
    private String currency;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 提现开始时间
     */
    private String withdrawStartTime;
    /**
     * 提现结束时间
     */
    private String withdrawEndTime;
    /**
     * 每页条数
     */
    private String pageSize;
    /**
     * 当前页
     */
    private String currentPage;

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getWithdrawStartTime() {
        return withdrawStartTime;
    }

    public void setWithdrawStartTime(String withdrawStartTime) {
        this.withdrawStartTime = withdrawStartTime;
    }

    public String getWithdrawEndTime() {
        return withdrawEndTime;
    }

    public void setWithdrawEndTime(String withdrawEndTime) {
        this.withdrawEndTime = withdrawEndTime;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "CpWithdrawDetailReqDto{" +
                "payerAccount='" + payerAccount + '\'' +
                ", storePlatform='" + storePlatform + '\'' +
                ", storeName='" + storeName + '\'' +
                ", currency='" + currency + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", withdrawStartTime='" + withdrawStartTime + '\'' +
                ", withdrawEndTime='" + withdrawEndTime + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", currentPage='" + currentPage + '\'' +
                '}';
    }
}
