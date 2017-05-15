package com.yonyou.dms.DTO.gacfca;

public class RoPartSalesDTO {
	private String roNo;
	private String salesPartNo;
	private String storageCode;
	private String partNo;
	private String partName;
	private Float partQuantity;
	private Double partCostPrice;
	private Double partSalesPrice;
	private Double partCostAmount;
	private Double partSalesAmount;
	private Double discountAmount;
	private Double realReceiveAmount;
	private Float discount;
	private String storagePositionCode;
	private String balanceNo;
	private String manageSortCode;
	private String chargePartitionCode;
	private Integer oemTag;
	

	public String getBalanceNo() {
		return balanceNo;
	}


	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}


	public String getChargePartitionCode() {
		return chargePartitionCode;
	}


	public void setChargePartitionCode(String chargePartitionCode) {
		this.chargePartitionCode = chargePartitionCode;
	}


	public Float getDiscount() {
		return discount;
	}


	public void setDiscount(Float discount) {
		this.discount = discount;
	}


	public Double getDiscountAmount() {
		return discountAmount;
	}


	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}


	public String getManageSortCode() {
		return manageSortCode;
	}


	public void setManageSortCode(String manageSortCode) {
		this.manageSortCode = manageSortCode;
	}


	public Double getRealReceiveAmount() {
		return realReceiveAmount;
	}


	public void setRealReceiveAmount(Double realReceiveAmount) {
		this.realReceiveAmount = realReceiveAmount;
	}


	public String getStoragePositionCode() {
		return storagePositionCode;
	}


	public void setStoragePositionCode(String storagePositionCode) {
		this.storagePositionCode = storagePositionCode;
	}


	public Double getPartCostAmount() {
		return partCostAmount;
	}


	public void setPartCostAmount(Double partCostAmount) {
		this.partCostAmount = partCostAmount;
	}


	public Double getPartCostPrice() {
		return partCostPrice;
	}


	public void setPartCostPrice(Double partCostPrice) {
		this.partCostPrice = partCostPrice;
	}


	public String getPartName() {
		return partName;
	}


	public void setPartName(String partName) {
		this.partName = partName;
	}


	public String getPartNo() {
		return partNo;
	}


	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}


	public Float getPartQuantity() {
		return partQuantity;
	}


	public void setPartQuantity(Float partQuantity) {
		this.partQuantity = partQuantity;
	}


	public Double getPartSalesAmount() {
		return partSalesAmount;
	}


	public void setPartSalesAmount(Double partSalesAmount) {
		this.partSalesAmount = partSalesAmount;
	}


	public Double getPartSalesPrice() {
		return partSalesPrice;
	}


	public void setPartSalesPrice(Double partSalesPrice) {
		this.partSalesPrice = partSalesPrice;
	}


	public String getRoNo() {
		return roNo;
	}


	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}


	public String getSalesPartNo() {
		return salesPartNo;
	}


	public void setSalesPartNo(String salesPartNo) {
		this.salesPartNo = salesPartNo;
	}


	public String getStorageCode() {
		return storageCode;
	}


	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}

	public Integer getOemTag() {
		return oemTag;
	}


	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}


}
