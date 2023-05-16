/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.BizDetailType;
import com.suning.epp.eppscbp.common.enums.ChargeBizType;
import com.suning.epp.eppscbp.util.StringUtil;

/**
 * @author 17080704
 *
 */
public class PreAmountQueryResDto {

    /**
     * 商户号
     */
    private String payerMerchantCode;


    /**
     * 人民币可用金额
     */
    private Long cnyAmount;
    private String cnyAmountStr;


    /**
     * 美元可用金额
     */
    private Long usdAmount;
    private String usdAmountStr;

    /**
     * 业务类型
     */
    private String bizType;
    private String bizTypeStr;


    public String getPayerMerchantCode() {
        return payerMerchantCode;
    }

    public void setPayerMerchantCode(String payerMerchantCode) {
        this.payerMerchantCode = payerMerchantCode;
    }

    public String getCnyAmountStr() {
        return StringUtil.formatMoney(cnyAmount);
    }

    public Long getCnyAmount() {
        return cnyAmount;
    }

    public void setCnyAmount(Long cnyAmount) {
        this.cnyAmount = cnyAmount;
    }

    public String getUsdAmountStr() {
        return StringUtil.formatMoney(usdAmount);
    }

    public Long getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(Long usdAmount) {
        this.usdAmount = usdAmount;
    }

    public String getBizType() {
        return bizType;
    }

    public String getBizTypeStr() {
        return ChargeBizType.getDescriptionFromCode(bizType);
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PreAmountQueryResDto{");
        sb.append("payerMerchantCode='").append(payerMerchantCode).append('\'');
        sb.append(", cnyAmount=").append(cnyAmount);
        sb.append(", cnyAmountStr=").append(cnyAmountStr);
        sb.append(", usdAmount=").append(usdAmount);
        sb.append(", usdAmountStr=").append(usdAmountStr);
        sb.append(", bizType='").append(bizType).append('\'');
        sb.append(", bizTypeStr='").append(bizTypeStr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
