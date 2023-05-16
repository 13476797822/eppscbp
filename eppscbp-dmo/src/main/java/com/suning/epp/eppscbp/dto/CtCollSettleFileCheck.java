/**
 * 
 */
package com.suning.epp.eppscbp.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;

import com.suning.epp.eppscbp.common.enums.EnumCodes;
import com.suning.epp.eppscbp.common.validator.Option;
import com.suning.epp.eppscbp.util.validator.First;
import com.suning.epp.eppscbp.util.validator.Second;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈收结汇申请明细文件〉
 *
 * @author 170080704
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CtCollSettleFileCheck {
    /**
     * 订单编号
     */
    @NotBlank(message = "订单编号不能为空", groups = { Default.class })
    private String orderNo;
    
    /**
     * 原订单编号
     */
    @NotBlank(message = "原订单编号不能为空", groups = { Second.class })
    @Size(min = 1, max = 30, message = "原订单编号长度不能超过30字符", groups = { Second.class })
    private String originalOrderNo;

    /**
     * 订单日期
     */
    @NotBlank(message = "订单日期不能为空", groups = { Default.class })
    @Pattern(regexp = "\\d{8} \\d{2}:\\d{2}:\\d{2}", message = "订单日期格式错误", groups = { Default.class })
    private String orderDate;

    /**
     * 跨境电商模式下收汇用途
     */
    @NotBlank(message = "收汇用途不能为空", groups = { Default.class })
    @Option(value = EnumCodes.COLL_SETTLE_APPLICATION, message = "未知的收汇用途", groups = { Default.class })
    private String application;

    /**
     * 收款人类型
     */
    @NotBlank(message = "收款人类型不能为空", groups = { Default.class })
    @Option(value = EnumCodes.PAYEE_MERCHANT_TYPE, message = "未知的收款人类型", groups = { Default.class })
    private String payeeMerchantType;

    /**
     * 收款人商户编号
     */
    // @NotBlank(message = "收款商户编号不能为空", groups = Default.class)
    // @Size(min = 1, max = 8, message = "收款商户编号长度不能超过8位", groups = Default.class)
    private String payeeMerchantCode;

    /**
     * 付款人名称
     */
    @NotBlank(message = "付款人名称不能为空", groups = Default.class)
    @Size(min = 0, max = 128, message = "付款人名称长度不能超过128位", groups = Default.class)
    private String payerName;

    /**
     * 收入款金额
     */
    @NotNull(message = "收入款金额不能为空", groups = { Default.class })
    @DecimalMin(value="0.01", message = "收入款金额小于等于0或是非数字", groups = { Default.class })
    private double revenue;

    /**
     * 付款人常驻国家/地区代码
     */
    @NotBlank(message = "国家码不能为空", groups = Default.class)
    @Option(value = EnumCodes.COUNTRY_CODE, message = "未知的国家码", groups = Default.class)
    private String countryCode;

    /**
     * 收款人银行账号
     */
    @NotBlank(message = "收款人银行账号不能为空", groups = { Default.class })
    @Size(min = 0, max = 32, message = "收款人银行账号长度不能超过32位", groups = Default.class)
    private String bankAccountNo;

    /**
     * 国际物流需要的列：货运类型
     */
    @NotBlank(message = "货运类型不能为空", groups = { First.class })
    @Option(value = EnumCodes.FREIGHT_TYPE, message = "货运类型", groups = First.class)
    private String freightType;
    
    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空", groups = Default.class)
    private String productName;
    
    /**
     * 商品数量
     */
    @NotBlank(message = "商品数量不能为空", groups = Default.class)
    @Size(min = 0, max = 19, message = "商品数量长度不能超过19位", groups = Default.class)
    @Pattern(regexp="^\\+{0,1}[1-9]\\d*", message = "商品数量必须为整数", groups = Default.class)
    private String productNum;
    
    /**
     * 物流企业名称
     */
    private String logistcsCompName;
    
    /**
     * 物流单号
     */
    private String logistcsWoNo;

    /**
     * 商品种类
     */
    @NotBlank(message = "商品种类不能为空", groups = { Default.class })
    @Option(value = EnumCodes.MERCHANDISE_TYPE, message = "未知的商品种类", groups = { Default.class })
    private String merchandiseType;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOriginalOrderNo() {
		return originalOrderNo;
	}

	public void setOriginalOrderNo(String originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}

	public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPayeeMerchantType() {
        return payeeMerchantType;
    }

    public void setPayeeMerchantType(String payeeMerchantType) {
        this.payeeMerchantType = payeeMerchantType;
    }

    public String getPayeeMerchantCode() {
        return payeeMerchantCode;
    }

    public void setPayeeMerchantCode(String payeeMerchantCode) {
        this.payeeMerchantCode = payeeMerchantCode;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFreightType() {
        return freightType;
    }

    public void setFreightType(String freightType) {
        this.freightType = freightType;
    }

    public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
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

    public String getMerchandiseType() {
        return merchandiseType;
    }

    public void setMerchandiseType(String merchandiseType) {
        this.merchandiseType = merchandiseType;
    }

    @Override
    public String toString() {
        return "CtCollSettleFileCheck [orderNo=" + orderNo + ", originalOrderNo=" + originalOrderNo + ", orderDate=" + orderDate + ", application=" + application + ", payeeMerchantType=" + payeeMerchantType + ", payeeMerchantCode=" + payeeMerchantCode + ", payerName=" + payerName + ", revenue=" + revenue + ", countryCode=" + countryCode + ", bankAccountNo=" + bankAccountNo + ", freightType=" + freightType + ", merchandiseType=" + merchandiseType + "]";
    }

}
