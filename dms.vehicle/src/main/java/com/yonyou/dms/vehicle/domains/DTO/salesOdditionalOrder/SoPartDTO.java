package com.yonyou.dms.vehicle.domains.DTO.salesOdditionalOrder;

public class SoPartDTO {
	
	private String storageCode;
	private String storagePositionCode;
	private String partNo;
	private String partName;
	private String unitCode;
	private double partSalesPrice;
	private double realPrice;
	private Integer partQuantity;
	private Integer accountMode;
	private double discount;
	private double partSalesAmount;
	private String remark;
	private double discountAmount;
	public String getStorageCode() {
		return storageCode;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}
	public String getStoragePositionCode() {
		return storagePositionCode;
	}
	public void setStoragePositionCode(String storagePositionCode) {
		this.storagePositionCode = storagePositionCode;
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
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public double getPartSalesPrice() {
		return partSalesPrice;
	}
	public void setPartSalesPrice(double partSalesPrice) {
		this.partSalesPrice = partSalesPrice;
	}
	public double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
	}
	public Integer getPartQuantity() {
		return partQuantity;
	}
	public void setPartQuantity(Integer partQuantity) {
		this.partQuantity = partQuantity;
	}
	public Integer getAccountMode() {
		return accountMode;
	}
	public void setAccountMode(Integer accountMode) {
		this.accountMode = accountMode;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getPartSalesAmount() {
		return partSalesAmount;
	}
	public void setPartSalesAmount(double partSalesAmount) {
		this.partSalesAmount = partSalesAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	

}
