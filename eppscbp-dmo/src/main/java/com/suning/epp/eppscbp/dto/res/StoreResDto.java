/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: shopResDto.java
 * Author:   88412423
 * Date:     2019年5月7日 15:42:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.dto.res;

import java.io.Serializable;

/**
 * 〈接口返回结果类〉<br>
 * 〈店铺详情〉
 *
 * @author 88412423
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StoreResDto implements Serializable {

    private static final long serialVersionUID = -5839643664612330538L;

    /**
     * 店铺编号
     */
    private String id;

    /**
     * 店铺流水号
     * 
     */
    private String storeNo;

    /**
     * 店铺id
     * 
     */
    private String storeId;

    /**
     * 收款平台
     */
    private String collPlatform;

    /**
     * 电商平台
     */
    private String eCommercePlatform;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺网址
     */
    private String storeUrl;

    /**
     * 站点
     */
    private String site;

    /**
     * 商户号
     */
    private String cpMerchantCode;

    /**
     * 商户组织机构代码
     */
    private String orgCode;

    /**
     * 卖家ID
     */
    private String sellerId;

    /**
     * AWS访问KEY
     */
    private String accessKey;

    /**
     * 私有秘钥
     */
    private String secretKey;

    /**
     * 销售商品
     */
    private String sellGoods;

    /**
     * 运营时间
     */
    private String runTime;

    /**
     * 预估月收入总额
     */
    private String mountAmt;

    /**
     * 状态
     */
    private String status;

    /**
     * 未提现余额
     */
    private String withdrawBal;

    /**
     * 余额
     */
    private String currBal;

    /**
     * 可用余额
     */
    private String availableBal;

    /**
     * 冻结余额
     */
    private String frozenBal;

    /**
     * 收款账号组织机构代码
     */
    private String custOrgCode;

    /**
     * 收款银行所在地区
     */
    private String bankCountryCode;

    /**
     * 收款银行名称
     */
    private String bankName;

    /**
     * 币种
     */
    private String cur;

    /**
     * 路由方式
     */
    private String routeType;

    /**
     * aba号
     */
    private String ABA;

    /**
     * iban号
     */
    private String iban;

    /**
     * SwiftCode
     */
    private String swiftCode;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 付款方户头号
     */
    private String payerAccount;

    /**
     * 店铺平台
     */
    private String storePlatform;

    /**
     * 收款方银行账号
     * 
     */
    private String custAccount;

    /**
     * 店铺主体名称
     */
    private String entityName;

    /**
     * 收款银行地址
     */
    private String bankAddr;

    /**
     * 收款银行开户名
     */
    private String custName;

    public String getBankAddr() {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

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

    public String getCollPlatform() {
        return collPlatform;
    }

    public void setCollPlatform(String collPlatform) {
        this.collPlatform = collPlatform;
    }

    public String geteCommercePlatform() {
        return eCommercePlatform;
    }

    public void seteCommercePlatform(String eCommercePlatform) {
        this.eCommercePlatform = eCommercePlatform;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCpMerchantCode() {
        return cpMerchantCode;
    }

    public void setCpMerchantCode(String cpMerchantCode) {
        this.cpMerchantCode = cpMerchantCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSellGoods() {
        return sellGoods;
    }

    public void setSellGoods(String sellGoods) {
        this.sellGoods = sellGoods;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getMountAmt() {
        return mountAmt;
    }

    public void setMountAmt(String mountAmt) {
        this.mountAmt = mountAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWithdrawBal() {
        return withdrawBal;
    }

    public void setWithdrawBal(String withdrawBal) {
        this.withdrawBal = withdrawBal;
    }

    public String getCurrBal() {
        return currBal;
    }

    public void setCurrBal(String currBal) {
        this.currBal = currBal;
    }

    public String getAvailableBal() {
        return availableBal;
    }

    public void setAvailableBal(String availableBal) {
        this.availableBal = availableBal;
    }

    public String getFrozenBal() {
        return frozenBal;
    }

    public void setFrozenBal(String frozenBal) {
        this.frozenBal = frozenBal;
    }

    public String getCustAccount() {
        return custAccount;
    }

    public void setCustAccount(String custAccount) {
        this.custAccount = custAccount;
    }

    public String getCustOrgCode() {
        return custOrgCode;
    }

    public void setCustOrgCode(String custOrgCode) {
        this.custOrgCode = custOrgCode;
    }

    public String getBankCountryCode() {
        return bankCountryCode;
    }

    public void setBankCountryCode(String bankCountryCode) {
        this.bankCountryCode = bankCountryCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getABA() {
        return ABA;
    }

    public void setABA(String ABA) {
        this.ABA = ABA;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return "StoreResDto [id=" + id + ", storeNo=" + storeNo + ", storeId=" + storeId + ", collPlatform=" + collPlatform + ", eCommercePlatform=" + eCommercePlatform + ", storeName=" + storeName + ", storeUrl=" + storeUrl + ", site=" + site + ", cpMerchantCode=" + cpMerchantCode + ", orgCode=" + orgCode + ", sellerId=" + sellerId + ", accessKey=" + accessKey + ", secretKey=" + secretKey + ", sellGoods=" + sellGoods + ", runTime=" + runTime + ", mountAmt=" + mountAmt + ", status=" + status + ", withdrawBal=" + withdrawBal + ", currBal=" + currBal + ", availableBal=" + availableBal + ", frozenBal="
                + frozenBal + ", custOrgCode=" + custOrgCode + ", bankCountryCode=" + bankCountryCode + ", bankName=" + bankName + ", cur=" + cur + ", routeType=" + routeType + ", ABA=" + ABA + ", iban=" + iban + ", swiftCode=" + swiftCode + ", delFlag=" + delFlag + ", createTime=" + createTime + ", updateTime=" + updateTime + ", payerAccount=" + payerAccount + ", storePlatform=" + storePlatform + ", custAccount=" + custAccount + ", entityName=" + entityName + ", bankAddr=" + bankAddr + ", custName=" + custName + "]";
    }
}
