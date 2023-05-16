package com.suning.epp.eppscbp.dto.req;

/**
 * @author 88412423
 *
 */
public class StoreManageDto {

    /**
     * 店铺编号
     */
    private String id;
    
   /**
    * 店铺流水号 
    */
    private String storeNo;

    /**
     * 付款方商户户头号
     */
    private String payerAccount;

    /**
     * 店铺平台
     */
    private String storePlatform;

    /**
     * 结算明细文件路径
     */
    private String fileAddress;

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
    private String sellerID;

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
     * 收款平台
     */
    private String collPlatform;

    /**
     * 电商平台
     */
    private String eCommercePlatform;

    /**
     * 商户号
     */
    private String cpMerchantCode;

    /**
     * 商户组织机构代码
     */
    private String merchantOrgCode;

    /**
     * 注册状态
     */
    private String status;

    /**
     * 待提现余额
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
     * 收款账号
     */
    private String custAccount;

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
     * 操作类型
     */
    private String operationType;

    /**
     * 当前页
     * 
     */
    private String currentPage;

    /**
     * 明细笔数
     */
    private String detailAmount;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
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

	public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getOperationType() {
        return operationType;
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

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getCpMerchantCode() {
        return cpMerchantCode;
    }

    public void setCpMerchantCode(String cpMerchantCode) {
        this.cpMerchantCode = cpMerchantCode;
    }

    public String getMerchantOrgCode() {
        return merchantOrgCode;
    }

    public void setMerchantOrgCode(String merchantOrgCode) {
        this.merchantOrgCode = merchantOrgCode;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
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

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(String detailAmount) {
        this.detailAmount = detailAmount;
    }

    @Override
    public String toString() {
        return "StoreManageDto{" + "id='" + id + ",storeNo='" + storeNo+ '\'' + ", payerAccount='" + payerAccount + '\'' + ", storePlatform='" + storePlatform + '\'' + ", fileAddress='" + fileAddress + '\'' + ", storeName='" + storeName + '\'' + ", site='" + site + '\'' + ", storeUrl='" + storeUrl + '\'' + ", sellerID='" + sellerID + '\'' + ", accessKey='" + accessKey + '\'' + ", secretKey='" + secretKey + '\'' + ", sellGoods='" + sellGoods + '\'' + ", runTime='" + runTime + '\'' + ", mountAmt='" + mountAmt + '\'' + ", collPlatform='" + collPlatform + '\'' + ", eCommercePlatform='" + eCommercePlatform + '\''
                + ", cpMerchantCode='" + cpMerchantCode + '\'' + ", merchantOrgCode='" + merchantOrgCode + '\'' + ", status='" + status + '\'' + ", withdrawBal='" + withdrawBal + '\'' + ", currBal='" + currBal + '\'' + ", availableBal='" + availableBal + '\'' + ", frozenBal='" + frozenBal + '\'' + ", custAccount='" + custAccount + '\'' + ", custOrgCode='" + custOrgCode + '\'' + ", bankCountryCode='" + bankCountryCode + '\'' + ", bankName='" + bankName + '\'' + ", cur='" + cur + '\'' + ", routeType='" + routeType + '\'' + ", ABA='" + ABA + '\'' + ", iban='" + iban + '\'' + ", swiftCode='"
                + swiftCode + '\'' + ", delFlag='" + delFlag + '\'' + ", createTime='" + createTime + '\'' + ", updateTime='" + updateTime + '\'' + ", operationType='" + operationType + '\'' + ", currentPage='" + currentPage + '\'' + ", detailAmount='" + detailAmount + '\'' + '}';
    }

}
