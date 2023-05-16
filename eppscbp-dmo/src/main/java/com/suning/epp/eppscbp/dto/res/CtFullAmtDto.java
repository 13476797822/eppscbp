package com.suning.epp.eppscbp.dto.res;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈全额到账费用〉
 *
 * @author 17060921
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CtFullAmtDto {
    // 币种
    private String cur;
    // 费用名称
    private String amt;
    // 币种名称
    private String curName;
    // 收款方编码
    private String payeeMerchantCode;

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur == null ? null : cur.trim();
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName == null ? null : curName.trim();
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CtFullAmtDto [cur=" + cur + ", amt=" + amt + ", curName=" + curName + ", payeeMerchantCode=" + payeeMerchantCode + "]";
    }
}