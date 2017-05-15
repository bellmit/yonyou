package com.yonyou.dms.DTO.gacfca;

public class RoRepairPartDTO {

	private static final long serialVersionUID = 1L;

	private String partNo;

	private String partName;

	private Float partQuantity;

	private Double partSalesPrice;
	private Double partSalesAmount;
	private Double partCostPrice;//配件成本单价
	private Double partCostAmount;//配件成本金额
	private Double partTaxClaimPrice;//含税索赔价
	private Integer isMain;
	private Integer oemTag;

	private String activityCode;

	private String lackMaterial;//是否缺件  10041001是，10041002否
	private Float discount;
	private Double taxPartCostPrice;//含税成本单价

	private String labourCode;

	private Double packageQuantity;

	private String storageCode;
	private String storagePositionCode;
	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getStoragePositionCode() {
		return storagePositionCode;
	}

	public void setStoragePositionCode(String storagePositionCode) {
		this.storagePositionCode = storagePositionCode;
	}

	public String getStorageCode() {
		return storageCode;
	}

	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}

	public Double getPartSalesAmount() {
		return partSalesAmount;
	}

	public void setPartSalesAmount(Double partSalesAmount) {
		this.partSalesAmount = partSalesAmount;
	}

	public String getLabourCode() {
		return labourCode;
	}

	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
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

	public Double getPartSalesPrice() {
		return partSalesPrice;
	}

	public void setPartSalesPrice(Double partSalesPrice) {
		this.partSalesPrice = partSalesPrice;
	}

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	public Double getPackageQuantity() {
		return packageQuantity;
	}

	public void setPackageQuantity(Double packageQuantity) {
		this.packageQuantity = packageQuantity;
	}

	public void setLackMaterial(String lackMaterial) {
		this.lackMaterial = lackMaterial;
	}

	public String getLackMaterial() {
		return lackMaterial;
	}

	public Integer getOemTag() {
		return oemTag;
	}

	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
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

	public Double getPartTaxClaimPrice() {
		return partTaxClaimPrice;
	}

	public void setPartTaxClaimPrice(Double partTaxClaimPrice) {
		this.partTaxClaimPrice = partTaxClaimPrice;
	}

	public Double getTaxPartCostPrice() {
		return taxPartCostPrice;
	}

	public void setTaxPartCostPrice(Double taxPartCostPrice) {
		this.taxPartCostPrice = taxPartCostPrice;
	}



}
