package com.suning.epp.eppscbp.dto;

public class OrderNumDto {
	//名称(销售成功/退款成功/拒付交易)
	private String name;
	
	//笔数(销售成功笔数/退款成功笔数/拒付交易)
	private int value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
    public String toString() {
        return "OrderNumDto{" +
        		", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
        }
	
}
