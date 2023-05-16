package com.suning.epp.eppscbp.dto.req;

/**
 * @author 88412423
 *
 */
public class StoreWithDrawAndPaymentDto {

    /**
     * 美元
     */
    private String usd;

    /**
     * 英镑
     */
    private String pound;

    /**
     * 欧元
     */
    private String euro;

    /**
     * 日元
     */
    private String yen;

    /**
     * 待批付金额：人民币
     */
    private String rmb;

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getPound() {
        return pound;
    }

    public void setPound(String pound) {
        this.pound = pound;
    }

    public String getEuro() {
        return euro;
    }

    public void setEuro(String euro) {
        this.euro = euro;
    }

    public String getYen() {
        return yen;
    }

    public void setYen(String yen) {
        this.yen = yen;
    }

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    @Override
    public String toString() {
        return "ShopWithDrawAndPaymentDto{" +
            "usd='" + usd + '\'' +
            ", pound='" + pound + '\'' +
            ", euro='" + euro + '\'' +
            ", yen='" + yen + '\'' +
            ", rmb='" + rmb + '\'' +
            '}';
    }
}
