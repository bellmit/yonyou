package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class MaintainActivityPartDTO {
	private Integer isMain;

	private String partCode;

	private String partName;

	private Double dealType;

	private Double amount;

	private Double price;

	private Double fee;

	private String labourCode;

	private Date downTimestamp;

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getDealType() {
		return dealType;
	}
	public void setDealType(Double dealType) {
		this.dealType = dealType;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Integer getIsMain() {
		return isMain;
	}
	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
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
	public String getLabourCode() {
		return labourCode;
	}
	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
}
