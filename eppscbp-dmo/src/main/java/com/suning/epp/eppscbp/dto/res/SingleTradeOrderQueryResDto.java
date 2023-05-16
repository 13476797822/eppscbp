/**
 * 
 */
package com.suning.epp.eppscbp.dto.res;

import java.math.BigDecimal;

import com.suning.epp.eppscbp.common.enums.CbpDetailFlag;
import com.suning.epp.eppscbp.common.enums.CbpProductType;
import com.suning.epp.eppscbp.common.enums.CbpStatus;
import com.suning.epp.eppscbp.common.enums.CbpSupStatus;

/**
 * 〈一句话功能简述〉<br>
 * 〈单笔交易单查询返回DTO〉
 * 
 * @author 17080704
 * @SEE [相关类/方法]（可选）
 * @SINCE [产品/模块版本] （可选）
 */
public class SingleTradeOrderQueryResDto {

    /**
     * 业务单号
     */
    private String businessNo;
    /**
     * 业务子单号
     */
    private String subBusinessNo;
    /**
     * 收款方商户号
     */
    private String payeeMerchantCode;
    /**
     * 收款方商户名
     */
    private String payeeMerchantName;
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
     * 明细笔数
     */
    private Integer numbers;
    /**
     * 申请金额
     */
    private String applyAmt;
    /**
     * 申请币种代码
     */
    private String applyCur;
    /**
     * 申请币种名称
     */
    private String applyCurName;
    /**
     * 汇出金额
     */
    private String remitAmt;
    /**
     * 汇出币种代码
     */
    private String remitCur;
    /**
     * 汇出币种名称
     */
    private String remitCurName;
    /**
     * 人民币支付金额
     */
    private String paymentRmbAmt;
    /**
     * 外币支付金额
     */
    private String paymentExAmt;
    /**
     * 汇率
     */
    private BigDecimal exchangeRate;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 监管失败原因
     */
    private String supErrMsg;
    /**
     * 明细校验状态
     */
    private String detailFlag;

    /**
     * 失败原因
     */
    private String errorMessage;

    /**
     * 鉴权失败明细文件地址
     */
    private String failFileAddress;

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getSubBusinessNo() {
        return subBusinessNo;
    }

    public void setSubBusinessNo(String subBusinessNo) {
        this.subBusinessNo = subBusinessNo;
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getPayeeMerchantName() {
        return payeeMerchantName;
    }

    public void setPayeeMerchantName(String payeeMerchantName) {
        this.payeeMerchantName = payeeMerchantName;
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

    public String getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(String applyAmt) {
        this.applyAmt = applyAmt;
    }

    public String getApplyCur() {
        return applyCur;
    }

    public void setApplyCur(String applyCur) {
        this.applyCur = applyCur;
    }

    public String getApplyCurName() {
        return applyCurName;
    }

    public void setApplyCurName(String applyCurName) {
        this.applyCurName = applyCurName;
    }

    public String getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(String remitAmt) {
        this.remitAmt = remitAmt;
    }

    public String getRemitCur() {
        return remitCur;
    }

    public void setRemitCur(String remitCur) {
        this.remitCur = remitCur;
    }

    public String getRemitCurName() {
        return remitCurName;
    }

    public void setRemitCurName(String remitCurName) {
        this.remitCurName = remitCurName;
    }

    public String getPaymentRmbAmt() {
        return paymentRmbAmt;
    }

    public void setPaymentRmbAmt(String paymentRmbAmt) {
        this.paymentRmbAmt = paymentRmbAmt;
    }

    public String getPaymentExAmt() {
        return paymentExAmt;
    }

    public void setPaymentExAmt(String paymentExAmt) {
        this.paymentExAmt = paymentExAmt;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getSupErrMsg() {
        return supErrMsg;
    }

    public void setSupErrMsg(String supErrMsg) {
        this.supErrMsg = supErrMsg;
    }

    /**
     * @return the detailFlag
     */
    public String getDetailFlag() {
        return detailFlag;
    }

    /**
     * @param detailFlag the detailFlag to set
     */
    public void setDetailFlag(String detailFlag) {
        this.detailFlag = detailFlag;
    }

    public String getStatusName() {
        // 主表支付成功&子表初始做特殊处理
        if (CbpStatus.INIT.getCode().equals(this.status)) {
            return "支付成功";
        } else {
            return CbpStatus.getDescriptionFromCode(this.status);
        }

    }

    public String getSupStatusName() {
        return CbpSupStatus.getDescriptionFromCode(this.supStatus);
    }
    
    public String getDetailFlagName() {
        return CbpDetailFlag.getDescriptionFromCode(this.detailFlag);
    }

    public String getExchangeRateStr() {
        // 指定人民币购汇,汇率等待返盘之后显示
        if (CbpProductType.SINGLE_GFH.getCode().equals(productType) && "CNY".equals(applyCur) && (!CbpStatus.SUCESS_GH.getCode().equals(status) && !CbpStatus.PRE_FH.getCode().equals(status) && !CbpStatus.SUCESS_FH.getCode().equals(status) && !CbpStatus.SUCESS_TH.getCode().equals(status))) {
            return "";
        } else {
            return this.exchangeRate == null ? "" : this.exchangeRate.stripTrailingZeros().toString();
        }

    }

    public String getRemitAmtFormat() {
        // 指定人民币购汇,汇出金额等待返盘之后显示
        if (CbpProductType.SINGLE_GFH.getCode().equals(productType) && "CNY".equals(applyCur) && (!CbpStatus.SUCESS_GH.getCode().equals(status) && !CbpStatus.PRE_FH.getCode().equals(status) && !CbpStatus.SUCESS_FH.getCode().equals(status) && !CbpStatus.SUCESS_TH.getCode().equals(status))) {
            return "";
        } else {
            return this.remitAmt + " " + this.remitCurName;
        }

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFailFileAddress() {
        return failFileAddress;
    }

    public void setFailFileAddress(String failFileAddress) {
        this.failFileAddress = failFileAddress;
    }

    @Override
    public String toString() {
        return "SingleTradeOrderQueryResDto{" +
                "businessNo='" + businessNo + '\'' +
                ", subBusinessNo='" + subBusinessNo + '\'' +
                ", payeeMerchantCode='" + payeeMerchantCode + '\'' +
                ", payeeMerchantName='" + payeeMerchantName + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", status='" + status + '\'' +
                ", supStatus='" + supStatus + '\'' +
                ", numbers=" + numbers +
                ", applyAmt='" + applyAmt + '\'' +
                ", applyCur='" + applyCur + '\'' +
                ", applyCurName='" + applyCurName + '\'' +
                ", remitAmt='" + remitAmt + '\'' +
                ", remitCur='" + remitCur + '\'' +
                ", remitCurName='" + remitCurName + '\'' +
                ", paymentRmbAmt='" + paymentRmbAmt + '\'' +
                ", paymentExAmt='" + paymentExAmt + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", productType='" + productType + '\'' +
                ", bizType='" + bizType + '\'' +
                ", supErrMsg='" + supErrMsg + '\'' +
                ", detailFlag='" + detailFlag + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", failFileAddress='" + failFileAddress + '\'' +
                '}';
    }
}
