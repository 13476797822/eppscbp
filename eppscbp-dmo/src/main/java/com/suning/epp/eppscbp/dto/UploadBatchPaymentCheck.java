package com.suning.epp.eppscbp.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;

import com.suning.epp.eppscbp.common.enums.EnumCodes;
import com.suning.epp.eppscbp.common.validator.Option;
import com.suning.ftmc.util.hr.FrontHideBitRule;

/**
 * 资金批付上传明细文件提现
 *
 * @author 88412423
 * @date 2019-05-16
 */
public class UploadBatchPaymentCheck {

    /**
     * 收款方名称
     */
    @NotBlank(message = "收款方名称不能为空", groups = { Default.class })
    @Size(min = 0, max = 100, message = "收款方名称长度超过100", groups = { Default.class })
    private String payeeName;

    /**
     * 账号性质
     */
    @NotBlank(message = "账号性质不能为空", groups = { Default.class })
    @Option(value = EnumCodes.ACCOUNT_NATURE, message = "未知的账号性质", groups = { Default.class })
    private String accountNature;

    /**
     * 银行卡号
     */
    @NotBlank(message = "银行卡号不能为空", groups = { Default.class })
    @Size(min = 0, max = 32, message = "银行卡号长度超过32", groups = { Default.class })
    private String bankAccountNo;

    /**
     * 出款金额
     */
    @NotNull(message = "出款金额不能为空", groups = { Default.class })
    @DecimalMin(value = "0.01", message = "出款金额小于等于0或是非数字", groups = { Default.class })
    private double payAmount;

    /**
     * 附言--订单名称
     */
    @Size(min = 0, max = 32, message = "附言长度超过32", groups = { Default.class })
    private String orderName;

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getAccountNature() {
        return accountNature;
    }

    public void setAccountNature(String accountNature) {
        this.accountNature = accountNature;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        return "UploadBatchPaymentCheck [payeeName=" + payeeName + ", accountNature=" + accountNature + ", bankAccountNo=" + FrontHideBitRule.bankCardNoHide(bankAccountNo) + ", payAmount=" + payAmount + ", orderName=" + orderName + "]";
    }
}
