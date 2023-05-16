package com.suning.epp.eppscbp.dto.req;

/**
 * @author 88412423
 *
 */
public class AddStoreDto {

    /**
     * 店铺流水号
     */
    private String storeNo;
    /**
     * 店铺id
     */
    private String storeId;

    /**
     * 付款方商户户头号
     */
    private String payerAccount;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 站点
     */
    private String site;

    /**
     * 店铺网址
     */
    private String storeUrl;

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
     * 预估月收入
     */
    private String mountAmt;

    /**
     * 店铺主体名称
     */
    private String entityName;

    /**
     * 电商平台
     */
    private String eCommercePlatform;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 组织机构代码
     */
    private String orgCode;

    public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
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

    public String geteCommercePlatform() {
        return eCommercePlatform;
    }

    public void seteCommercePlatform(String eCommercePlatform) {
        this.eCommercePlatform = eCommercePlatform;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return "AddStoreDto [storeNO=" + storeNo + ", storeId=" + storeId + ", payerAccount=" + payerAccount + ", storeName=" + storeName + ", site=" + site + ", storeUrl=" + storeUrl + ", sellerId=" + sellerId + ", accessKey=" + accessKey + ", secretKey=" + secretKey + ", sellGoods=" + sellGoods + ", runTime=" + runTime + ", mountAmt=" + mountAmt + ", entityName=" + entityName + ", eCommercePlatform=" + eCommercePlatform + ", operationType=" + operationType + ", orgCode=" + orgCode + "]";
    }

}
