package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class MaintainActivityLabourDTO {

	private String labourCode;
	
	private String labourName;
	
	private Integer dealType;
	
	private Double frt;
	
	private Double price;
	
	private Double fee;
	
	private Date downTimestamp;
	
	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getFrt() {
		return frt;
	}

	public void setFrt(Double frt) {
		this.frt = frt;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

}
