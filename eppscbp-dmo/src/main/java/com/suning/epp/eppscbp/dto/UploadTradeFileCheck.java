package com.suning.epp.eppscbp.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.suning.epp.eppscbp.util.validator.First;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈货物贸易监管文件校验参数〉
 *
 * @author 17060921
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class UploadTradeFileCheck {
    /**
     * 业务明细单号
     */
    @NotBlank(message = "业务明细单号不能为空")
    @Size(max = 50, message = "业务明细单号长度不能超过{max}位")
    private String tradeNo;
    /**
     * 物流企业名称
     */
    @NotBlank(message = "物流企业名称不能为空" , groups = { First.class })
    @Size(max = 256, message = "物流企业名称长度不能超过{max}位", groups = { First.class })
    private String logistcsCompName;
    /**
     * 物流单号
     */
    @NotBlank(message = "物流单号不能为空", groups = { First.class })
    private String logistcsWoNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
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
        return "UploadMerchantFileCheck [tradeNo=" + tradeNo + ", logistcsCompName=" + logistcsCompName + ", logistcsWoNo=" + logistcsWoNo + "]";
    }
}