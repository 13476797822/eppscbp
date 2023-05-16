/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: shopSiteResDto.java
 * Author:   88412423
 * Date:     2019年5月7日 19:46:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epp.eppscbp.dto.res;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 〈接口返回结果类〉<br> 
 * 〈店铺站点〉
 *
 * @author 88412423
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StoreSiteResDto implements Serializable {

    private static final long serialVersionUID = -7584342443084100112L;

    /**
     * 编号
     */
    private Long id;

    /**
     *电商平台
     */
    private String eCommercePlatform;

    /**
     * 收款平台编号
     */
    private String cpId;

    /**
     * 美国站usd
     */
    private String USD;

    /**
     * 欧洲站eur
     */
    private String EUR;

    /**
     * 英国站gbp
     */
    private String GBP;

    /**
     * 日本站
     */
    private String JPY;

    /**
     * 离岸人民币
     */
    private String CNH;

    /**
     * 澳大利亚元站aud
     */
    private String AUD;

    /**
     * 香港站
     */
    private String HKD;

    /**
     * 加拿大站
     */
    private String CAD;

    /**
     * 新台币
     */
    private String TWD;

    /**
     * 新加坡站
     */
    private String SGD;

    /**
     * 新西兰站
     */
    private String NZD;


    public String geteCommercePlatform() {
        return eCommercePlatform;
    }

    public void seteCommercePlatform(String eCommercePlatform) {
        this.eCommercePlatform = eCommercePlatform;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getUSD() {
        return USD;
    }

    public void setUSD(String USD) {
        this.USD = USD;
    }

    public String getEUR() {
        return EUR;
    }

    public void setEUR(String EUR) {
        this.EUR = EUR;
    }

    public String getGBP() {
        return GBP;
    }

    public void setGBP(String GBP) {
        this.GBP = GBP;
    }

    public String getJPY() {
        return JPY;
    }

    public void setJPY(String JPY) {
        this.JPY = JPY;
    }

    public String getCNH() {
        return CNH;
    }

    public void setCNH(String CNH) {
        this.CNH = CNH;
    }

    public String getAUD() {
        return AUD;
    }

    public void setAUD(String AUD) {
        this.AUD = AUD;
    }

    public String getHKD() {
        return HKD;
    }

    public void setHKD(String HKD) {
        this.HKD = HKD;
    }

    public String getCAD() {
        return CAD;
    }

    public void setCAD(String CAD) {
        this.CAD = CAD;
    }

    public String getTWD() {
        return TWD;
    }

    public void setTWD(String TWD) {
        this.TWD = TWD;
    }

    public String getSGD() {
        return SGD;
    }

    public void setSGD(String SGD) {
        this.SGD = SGD;
    }

    public String getNZD() {
        return NZD;
    }

    public void setNZD(String NZD) {
        this.NZD = NZD;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
