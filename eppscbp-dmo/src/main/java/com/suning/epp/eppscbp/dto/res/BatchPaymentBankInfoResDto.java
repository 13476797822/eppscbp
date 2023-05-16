package com.suning.epp.eppscbp.dto.res;
/**
 * @author 88397357
 *
 */
public class BatchPaymentBankInfoResDto {
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 开户行编号
	 */
	private String bankCode;
	
	/**
	 * 开户行名称
	 */
	private String bankName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Override
    public String toString() {
        return "BatchPaymentBankInfoResDto [id=" + id + ", bankCode=" + bankCode + ", bankName=" + bankName + "]";
    }
}
