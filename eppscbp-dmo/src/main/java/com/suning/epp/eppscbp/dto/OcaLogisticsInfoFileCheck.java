package com.suning.epp.eppscbp.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 物流信息解析dto
 * @author 19043747
 *
 */
public class OcaLogisticsInfoFileCheck {

	//商户订单号 doit长度
	@NotBlank(message = "商户订单号不能为空", groups = { Default.class })
    @Size(min = 1, max = 30, message = "商户订单号长度超过30", groups = { Default.class })
	private String merchantOrderNo;
	
	//发货日期
	@NotBlank(message = "发货日期不能为空", groups = { Default.class })
    @Pattern(regexp = "\\d{8} \\d{2}:\\d{2}:\\d{2}", message = "发货日期式错误", groups = { Default.class })
	private String shippingDate;
	
	//物流公司名称
	@NotBlank(message = "物流公司名称不能为空", groups = Default.class)
    @Size(min = 0, max = 128, message = "物流公司名称长度不能超过128位", groups = Default.class)
	private String logisticsCompName;
	
	//物流单号
	@NotBlank(message = "物流单号不能为空", groups = Default.class)
    @Size(min = 0, max = 128, message = "物流单号长度不能超过128位", groups = Default.class)
	private String logisticsNo;

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getLogisticsCompName() {
		return logisticsCompName;
	}

	public void setLogisticsCompName(String logisticsCompName) {
		this.logisticsCompName = logisticsCompName;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	@Override
    public String toString() {
        return "OcaLogisticsInfoFileCheck{" +
                "merchantOrderNo='" + merchantOrderNo + '\'' +
                ", shippingDate='" + shippingDate + '\'' +
                ", logisticsCompName='" + logisticsCompName + '\'' +
                ", logisticsNo='" + logisticsNo + '\'' +
                '}';
    }
}
