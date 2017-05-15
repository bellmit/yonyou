package com.yonyou.dms.common.domains.DTO.stockmanage;

import java.util.Date;

public class TmPartStockItemDTO {
	private String dealerCode;
	private String partNo2;
 	
	private String storageCode;
	private String partBatchNo;
	private String storagePositionCode;
	private String partName;
	private String spellsCode;
	private String partGroupCode; 
	private String unitCode;
	
	
	private Double stockQuantity;
	private Double salesPrice;
	private Double claimPrice;
	private Double limitPrice;
	private Double insurancePrice;
	private Double costPrice;
	
	private Double costAmount;
	private Double birrowQuantity;
	private Integer partStatus;
	private Double lendQuantity;
	private Date lastStockIn;
	private Date lastStockOut;
	private Date foundDate;
	private String remark;
	
	public String getPartNo2() {
		return partNo2;
	}
	public void setPartNo2(String partNo2) {
		this.partNo2 = partNo2;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	public String getStorageCode() {
		return storageCode;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}
	public String getPartBatchNo() {
		return partBatchNo;
	}
	public void setPartBatchNo(String partBatchNo) {
		this.partBatchNo = partBatchNo;
	}
	public String getStoragePositionCode() {
		return storagePositionCode;
	}
	public void setStoragePositionCode(String storagePositionCode) {
		this.storagePositionCode = storagePositionCode;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getSpellsCode() {
		return spellsCode;
	}
	public void setSpellsCode(String spellsCode) {
		this.spellsCode = spellsCode;
	}
	public String getPartGroupCode() {
		return partGroupCode;
	}
	public void setPartGroupCode(String partGroupCode) {
		this.partGroupCode = partGroupCode;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public Double getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Double stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public Double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Double getClaimPrice() {
		return claimPrice;
	}
	public void setClaimPrice(Double claimPrice) {
		this.claimPrice = claimPrice;
	}
	public Double getLimitPrice() {
		return limitPrice;
	}
	public void setLimitPrice(Double limitPrice) {
		this.limitPrice = limitPrice;
	}
	public Double getInsurancePrice() {
		return insurancePrice;
	}
	public void setInsurancePrice(Double insurancePrice) {
		this.insurancePrice = insurancePrice;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	public Double getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}
	public Double getBirrowQuantity() {
		return birrowQuantity;
	}
	public void setBirrowQuantity(Double birrowQuantity) {
		this.birrowQuantity = birrowQuantity;
	}
	public Integer getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(Integer partStatus) {
		this.partStatus = partStatus;
	}
	public Double getLendQuantity() {
		return lendQuantity;
	}
	public void setLendQuantity(Double lendQuantity) {
		this.lendQuantity = lendQuantity;
	}
	public Date getLastStockIn() {
		return lastStockIn;
	}
	public void setLastStockIn(Date lastStockIn) {
		this.lastStockIn = lastStockIn;
	}
	public Date getLastStockOut() {
		return lastStockOut;
	}
	public void setLastStockOut(Date lastStockOut) {
		this.lastStockOut = lastStockOut;
	}
	public Date getFoundDate() {
		return foundDate;
	}
	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
