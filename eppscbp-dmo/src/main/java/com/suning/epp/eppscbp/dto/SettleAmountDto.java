package com.suning.epp.eppscbp.dto;

public class SettleAmountDto {
	//名称(已结算/纳入保证金/释放保证金)
	private String name;
		
	//金额(已结算/纳入保证金/释放保证金)
	private String value;

	//金额百分比
	private String proportion;
		
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

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	@Override
    public String toString() {
        return "SettleAmountDto{" +
        		"name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", proportion='" + proportion + '\'' +
                '}';
        }

}
