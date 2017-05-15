package com.yonyou.dms.DTO.gacfca;

public class ActivityOtherFeeDTO {

	private String otherFeeCode;	//其他项目代码

	private String otherFeeName;	//其他项目名称

	private Double amount;	//金额

	public String getOtherFeeCode() {
		return otherFeeCode;
	}

	public void setOtherFeeCode(String otherFeeCode) {
		this.otherFeeCode = otherFeeCode;
	}

	public String getOtherFeeName() {
		return otherFeeName;
	}

	public void setOtherFeeName(String otherFeeName) {
		this.otherFeeName = otherFeeName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
