package com.yonyou.dms.DTO.gacfca;

public class ActivityLabourDTO {

	private String labourCode;

	private String labourName;

	private Double stdLabourHour;
	
	private Double discount;

	public String getLabourCode() {
		return labourCode;
	}

	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}

	public String getLabourName() {
		return labourName;
	}

	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}

	public Double getStdLabourHour() {
		return stdLabourHour;
	}

	public void setStdLabourHour(Double stdLabourHour) {
		this.stdLabourHour = stdLabourHour;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
}
