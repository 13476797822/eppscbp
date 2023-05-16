package com.suning.epp.eppscbp.dto.res;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.suning.epp.eppscbp.util.StringUtil;
import com.suning.ftmc.util.hr.BackHideBitRule;

public class BatchPaymentReviewDetail {

    /**
     * 明细id
     */
     @Length(max = 64, message = "批付明细序列号id长度不可超过64位")
     private String detailNo;

	/**
	 * 收款方编号
	 */
	 private String payeeMerchantCode;
	 
    /**
     * 收款方名称
     */
    @NotBlank(message = "收款方名称不能为空")
    @Length(max = 100, message = "收款方名称长度不超过100")
    private String payeeMerchantName;


    /**
     * 银行卡号
     */
    private String receiverCardNo;

    /**
     * 金额
     */
    @NotNull(message = "金额不可为空")
    private Long amount;

    /**
     * 附言--订单名称
     */
    @Length(max = 254, message = "附言长度不超过254")
    private String orderName;

    /**
     * 批次总金额
     */
    private Long expendNoAmount;


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


    public String getReceiverCardNo() {
		return receiverCardNo;
	}

	public void setReceiverCardNo(String receiverCardNo) {
		this.receiverCardNo = receiverCardNo;
	}

	public Long getAmount() {
        return amount;
    }

    public String getAmountStr() {
        return StringUtil.formatMoney(this.amount);
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
    }

    public Long getExpendNoAmount() {
        return expendNoAmount;
    }

    public void setExpendNoAmount(Long expendNoAmount) {
        this.expendNoAmount = expendNoAmount;
    }



    @Override
    public String toString() {
        return "BatchPaymentFile{" +
                "detailNo='" + detailNo + '\'' +
                ", payeeMerchantCode='" + payeeMerchantCode + '\'' +
                ", payeeMerchantName='" + BackHideBitRule.nameHideForCN(payeeMerchantName) + '\'' +
                ", bankAccountNo='" + BackHideBitRule.bankCardNoHide(receiverCardNo) + '\'' +
                ", amount='" + amount + '\'' +
                ", expendNoAmount='" + expendNoAmount + '\'' +
                ", orderName='" + orderName + '\'' +
                '}';
    }
}
