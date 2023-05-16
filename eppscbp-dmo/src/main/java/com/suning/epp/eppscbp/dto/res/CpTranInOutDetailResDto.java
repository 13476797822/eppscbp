/*
 * Copyright (C), 2002-2017, 苏宁易购电子商务有限公司
 * FileName: CpTranInOutDetailResDto.java
 * Author:   17090884
 * Date:     2019/05/13 9:19
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      〈time>      <version>    <desc>
 * 修改人姓名    修改时间       版本号      描述
 */
package com.suning.epp.eppscbp.dto.res;

import java.util.Date;

import com.suning.epp.eppscbp.common.enums.CurType;
import com.suning.epp.eppscbp.common.enums.OrderType;
import com.suning.epp.eppscbp.common.enums.StorePlatformEnum;
import com.suning.epp.eppscbp.util.DateUtil;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * 〈出入账明细DTO〉<br>
 * 〈功能详细描述〉
 *
 * @author 17090884
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CpTranInOutDetailResDto {

    /**
     * 店铺平台
     */
    private String storePlatform;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 店铺站点
     */
    private String storeSite;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 收款方银行帐号
     */
    private String payeeAccount;
    /**
     * 金额
     */
    private Long amt;
    /**
     * 币种
     */
    private String currency;
    /**
     * 通知时间
     */
    private Date adviceTime;
    /**
     * 当前页
     */
    private String currentPage;

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

    public String getStoreSite() {
        return storeSite;
    }

    public void setStoreSite(String storeSite) {
        this.storeSite = storeSite;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public Date getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(Date adviceTime) {
        this.adviceTime = adviceTime;
    }

    public String getStorePlatformName() {
        return StorePlatformEnum.getDescriptionFromCode(this.storePlatform);
    }

    public String getCurrencyName() {
        return CurType.getDescriptionFromCode(this.currency);
    }

    public String getOrderTypeName() {
        return OrderType.getDescriptionFromCode(this.orderType);
    }

    public String getAmtStr() {
        return StringUtil.formatMoney(this.amt);
    }
    
    public String getAdviceTimeStr() {
        return DateUtil.formatDate(adviceTime);
    }

    @Override
    public String toString() {
        return "CpTranInOutDetailResDto{" +
                "storePlatform='" + storePlatform + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeSite='" + storeSite + '\'' +
                ", orderType='" + orderType + '\'' +
                ", payeeAccount='" + payeeAccount + '\'' +
                ", amt='" + amt + '\'' +
                ", currency='" + currency + '\'' +
                ", adviceTime='" + adviceTime + '\'' +
                ", currentPage='" + currentPage + '\'' +
                '}';
    }
}
