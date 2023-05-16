/**
 * 
 */
package com.suning.epp.eppscbp.dto.req;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈订单汇率刷新请求参数〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OrderExRateRefreshDto extends OrderOperationDto{

    /**
     * 刷新标识
     */
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "OrderExRateRefreshDto [payerAccount=" + super.getPayerAccount() + ", businessNo=" + super.getBusinessNo() + ", flag=" + flag + "]";
    }

}
