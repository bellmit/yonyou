package com.yonyou.dms.DTO.gacfca;

public class PartStockDTO {

	private String entityCode; //经销商代码
	
	private String partNo;//配件编号
	
	private Float paperQuantity;//配件账面库存
	
	private Integer isValid;//是否有效
	
	private Double availableCount;//实际库存
	
	private Double chooseCount;//锁定数量
	
	private Double amcCount;
	
	private Double boCount;
	
	private Double orderCount;
	
	private Double unitCost;//成本价
	private Double salesPrice;//销售价
	private Double dnp;//索赔价
	private String partName;//配件名称
	
	private String storageCode; //仓库代码            2015/4/10  by wangjian
	private String storageName; //仓库名称      2015/4/10  by wangjian	
	private Integer uploadYear;//上报年份
	private Integer uploadMonth;//上报月份
	private String limitNo;//限价编号
	private Double taxCostPrice;//含税成本价
	
	
	

	public String getLimitNo() {
		return limitNo;
	}

	public void setLimitNo(String limitNo) {
		this.limitNo = limitNo;
	}

	public Integer getUploadMonth() {
		return uploadMonth;
	}

	public void setUploadMonth(Integer uploadMonth) {
		this.uploadMonth = uploadMonth;
	}

	public Integer getUploadYear() {
		return uploadYear;
	}

	public void setUploadYear(Integer uploadYear) {
		this.uploadYear = uploadYear;
	}

	public Double getDnp() {
		return dnp;
	}

	public void setDnp(Double dnp) {
		this.dnp = dnp;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getStorageCode() {
		return storageCode;
	}

	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public Double getAmcCount() {
		return amcCount;
	}

	public void setAmcCount(Double amcCount) {
		this.amcCount = amcCount;
	}

	public Double getAvailableCount() {
		return availableCount;
	}

	public void setAvailableCount(Double availableCount) {
		this.availableCount = availableCount;
	}

	public Double getBoCount() {
		return boCount;
	}

	public void setBoCount(Double boCount) {
		this.boCount = boCount;
	}

	public Double getChooseCount() {
		return chooseCount;
	}

	public void setChooseCount(Double chooseCount) {
		this.chooseCount = chooseCount;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Double getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Double orderCount) {
		this.orderCount = orderCount;
	}

	public Float getPaperQuantity() {
		return paperQuantity;
	}

	public void setPaperQuantity(Float paperQuantity) {
		this.paperQuantity = paperQuantity;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getTaxCostPrice() {
		return taxCostPrice;
	}

	public void setTaxCostPrice(Double taxCostPrice) {
		this.taxCostPrice = taxCostPrice;
	}


}
