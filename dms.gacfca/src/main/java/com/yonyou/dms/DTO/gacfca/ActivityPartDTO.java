package com.yonyou.dms.DTO.gacfca;

public class ActivityPartDTO {

	private Integer isMain;

	private String partNo;
	
	private String partName;
	
	private String labourCode;
	
	private Float partQuantity;
	
	private Double partSalesPrice;
	
	private Double partSalesAmount;
	
	private Double discount;

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getLabourCode() {
		return labourCode;
	}

	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}

	public Float getPartQuantity() {
		return partQuantity;
	}

	public void setPartQuantity(Float partQuantity) {
		this.partQuantity = partQuantity;
	}

	public Double getPartSalesPrice() {
		return partSalesPrice;
	}

	public void setPartSalesPrice(Double partSalesPrice) {
		this.partSalesPrice = partSalesPrice;
	}

	public Double getPartSalesAmount() {
		return partSalesAmount;
	}

	public void setPartSalesAmount(Double partSalesAmount) {
		this.partSalesAmount = partSalesAmount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}
