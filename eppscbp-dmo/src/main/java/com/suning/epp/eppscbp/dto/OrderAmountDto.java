package com.suning.epp.eppscbp.dto;

public class OrderAmountDto {
	//名称(销售成功/退款成功/拒付交易)
	private String name;
	
	//金额(销售成功笔数/退款成功笔数/拒付交易)
	private String value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
    public String toString() {
        return "OrderAmountDto{" +
        		"name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
        }

}
