/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

/**
 * 中国银行实时参考牌价 <br>
 * 〈功能详细描述〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ReferRateQueryResDto {

    /**
     * 交易货币
     */
    private String tradeCur;

    /**
     * 汇兑信息
     */
    private String remitInfo;
    /**
     * 汇兑单位
     */
    private String remitUnit;
    /**
     * 汇买价
     */
    private String buyingRate;
    /**
     * 汇卖价
     */
    private String sellingRate;
    /**
     * 中间价
     */
    private String middleRate;
    /**
     * 报价时间
     */
    private String quoteTime;

    public String getTradeCur() {
        return tradeCur;
    }

    public void setTradeCur(String tradeCur) {
        this.tradeCur = tradeCur;
    }

    public String getRemitInfo() {
        return remitInfo;
    }

    public void setRemitInfo(String remitInfo) {
        this.remitInfo = remitInfo;
    }

    public String getRemitUnit() {
        return remitUnit;
    }

    public void setRemitUnit(String remitUnit) {
        this.remitUnit = remitUnit;
    }

    public String getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(String buyingRate) {
        this.buyingRate = buyingRate;
    }

    public String getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(String sellingRate) {
        this.sellingRate = sellingRate;
    }

    public String getMiddleRate() {
        return middleRate;
    }

    public void setMiddleRate(String middleRate) {
        this.middleRate = middleRate;
    }

    public String getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(String quoteTime) {
        this.quoteTime = quoteTime;
    }

    @Override
    public String toString() {
        return "ReferRateQueryDto [tradeCur=" + tradeCur + ", remitInfo=" + remitInfo + ", remitUnit=" + remitUnit + ", buyingRate=" + buyingRate + ", sellingRate=" + sellingRate + ", middleRate=" + middleRate + ", quoteTime=" + quoteTime + "]";
    }

}
