/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import com.suning.epp.eppscbp.common.enums.CbpStatus;
import com.suning.epp.eppscbp.common.enums.CbpSupStatus;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈批量交易单查询返回DTO〉
 *
 * @author 17080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchTradeOrderQueryResDto {

    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 创建时间
     */
    private String creatTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 监管上传状态
     */
    private String supStatus;
    /**
     * 汇款笔数
     */
    private Integer numbers;
    /**
     * 支付金额
     */
    private String paymentAmt;
    /**
     * 监管失败原因
     */
    private String supErrMsg;
    /**
     * 产品类型
     */
    private String productType;

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupStatus() {
        return supStatus;
    }

    public void setSupStatus(String supStatus) {
        this.supStatus = supStatus;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public String getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(String paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public String getSupErrMsg() {
        return supErrMsg;
    }

    public void setSupErrMsg(String supErrMsg) {
        this.supErrMsg = supErrMsg;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStatusName() {
        return CbpStatus.getDescriptionFromCode(this.status);
    }

    public String getSupStatusName() {
        return CbpSupStatus.getDescriptionFromCode(this.supStatus);
    }

    @Override
    public String toString() {
        return "BatchTradeOrderQueryResDto [businessNo=" + businessNo + ", creatTime=" + creatTime + ", status=" + status + ", supStatus=" + supStatus + ", numbers=" + numbers + ", paymentAmt=" + paymentAmt + ", supErrMsg=" + supErrMsg + ", productType=" + productType + "]";
    }

}
