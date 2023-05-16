package com.suning.epp.eppscbp.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈货物贸易监管文件校验参数〉
 *
 * @author 17060921
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class UploadTradeFileCheckPurchase {
    /**
     * 业务明细单号
     */
    @NotBlank(message = "业务明细单号不能为空")
    @Size(max = 50, message = "业务明细单号长度不能超过{max}位")
    private String tradeNo;
    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 256, message = "商品名称长度不能超过{max}位")
    private String productName;

    /**
     * 商品数量
     */
    @NotBlank(message = "商品数量不能为空")
    @Size(max = 19, message = "商品数量长度不能超过{max}位")
    private String productNum;
    /**
     * 物流企业名称
     */
    @NotBlank(message = "物流企业名称不能为空")
    @Size(max = 256, message = "物流企业名称长度不能超过{max}位")
    private String logistcsCompName;
    /**
     * 物流单号
     */
    @NotBlank(message = "物流单号不能为空")
    @Size(max = 64, message = "物流单号长度不能超过{max}位")
    private String logistcsWoNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getLogistcsCompName() {
        return logistcsCompName;
    }

    public void setLogistcsCompName(String logistcsCompName) {
        this.logistcsCompName = logistcsCompName;
    }

    public String getLogistcsWoNo() {
        return logistcsWoNo;
    }

    public void setLogistcsWoNo(String logistcsWoNo) {
        this.logistcsWoNo = logistcsWoNo;
    }

    @Override
    public String toString() {
        return "UploadMerchantFileCheck [tradeNo=" + tradeNo + ", productName=" + productName + ", productNum=" + productNum + ", logistcsCompName=" + logistcsCompName + ", logistcsWoNo=" + logistcsWoNo + "]";
    }
}
