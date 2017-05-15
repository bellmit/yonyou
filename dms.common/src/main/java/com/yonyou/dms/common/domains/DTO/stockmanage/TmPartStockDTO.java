package com.yonyou.dms.common.domains.DTO.stockmanage;

import java.util.Date;

public class TmPartStockDTO {
	private String dealerCode;
	
	private String partNo;
	private String storageCode;
 	private String storagePositionCode;
	private String partName;
	private String spellCode;
	private Integer partGroupCode;
	private String unitCode;
 	private Double stocjQuantity;
	private Double salesPrice;
	private Double claimPrice;
	private Double limitPrice;
	private Double latestPrice;
	private Double insurancePrice;
	private Double costPrice;
	private Double costAmount;
	private Double maxStock;
	private Double minStock;
	private Double borrowQuantity;
	private Double lendQuantity;
	private Double lockeduantity;
	private Integer partStatus;
	
	private String partModelGroupCodeSet;
	private Integer partSpeType;
	private Integer isSuggestOrder;
	private Integer monthlyOtyFormula;
 	private Date lastStockIn;
	private Date lastStockOut;
	private Date foundDate;
	private Integer partMainType;
	private Integer isautoMaxminStock;
	private Double safeStock;
	private String remark;
	private String providerCode;
	private String providerName;
	private Double nodePrice;
	private Double otherPartCostPrice;
	private Double otherPartCostAmount;
	private Integer isBack;
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
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
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getSpellCode() {
		return spellCode;
	}
	public void setSpellCode(String spellCode) {
		this.spellCode = spellCode;
	}
	public Integer getPartGroupCode() {
		return partGroupCode;
	}
	public void setPartGroupCode(Integer partGroupCode) {
		this.partGroupCode = partGroupCode;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public Double getStocjQuantity() {
		return stocjQuantity;
	}
	public void setStocjQuantity(Double stocjQuantity) {
		this.stocjQuantity = stocjQuantity;
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
	public Double getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(Double latestPrice) {
		this.latestPrice = latestPrice;
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
	public Double getMaxStock() {
		return maxStock;
	}
	public void setMaxStock(Double maxStock) {
		this.maxStock = maxStock;
	}
	public Double getMinStock() {
		return minStock;
	}
	public void setMinStock(Double minStock) {
		this.minStock = minStock;
	}
	public Double getBorrowQuantity() {
		return borrowQuantity;
	}
	public void setBorrowQuantity(Double borrowQuantity) {
		this.borrowQuantity = borrowQuantity;
	}
	public Double getLendQuantity() {
		return lendQuantity;
	}
	public void setLendQuantity(Double lendQuantity) {
		this.lendQuantity = lendQuantity;
	}
	public Double getLockeduantity() {
		return lockeduantity;
	}
	public void setLockeduantity(Double lockeduantity) {
		this.lockeduantity = lockeduantity;
	}
	public Integer getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(Integer partStatus) {
		this.partStatus = partStatus;
	}
	public String getPartModelGroupCodeSet() {
		return partModelGroupCodeSet;
	}
	public void setPartModelGroupCodeSet(String partModelGroupCodeSet) {
		this.partModelGroupCodeSet = partModelGroupCodeSet;
	}
	public Integer getPartSpeType() {
		return partSpeType;
	}
	public void setPartSpeType(Integer partSpeType) {
		this.partSpeType = partSpeType;
	}
	public Integer getIsSuggestOrder() {
		return isSuggestOrder;
	}
	public void setIsSuggestOrder(Integer isSuggestOrder) {
		this.isSuggestOrder = isSuggestOrder;
	}
	public Integer getMonthlyOtyFormula() {
		return monthlyOtyFormula;
	}
	public void setMonthlyOtyFormula(Integer monthlyOtyFormula) {
		this.monthlyOtyFormula = monthlyOtyFormula;
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
	public Integer getPartMainType() {
		return partMainType;
	}
	public void setPartMainType(Integer partMainType) {
		this.partMainType = partMainType;
	}
	public Integer getIsautoMaxminStock() {
		return isautoMaxminStock;
	}
	public void setIsautoMaxminStock(Integer isautoMaxminStock) {
		this.isautoMaxminStock = isautoMaxminStock;
	}
	public Double getSafeStock() {
		return safeStock;
	}
	public void setSafeStock(Double safeStock) {
		this.safeStock = safeStock;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Double getNodePrice() {
		return nodePrice;
	}
	public void setNodePrice(Double nodePrice) {
		this.nodePrice = nodePrice;
	}
	public Double getOtherPartCostPrice() {
		return otherPartCostPrice;
	}
	public void setOtherPartCostPrice(Double otherPartCostPrice) {
		this.otherPartCostPrice = otherPartCostPrice;
	}
	public Double getOtherPartCostAmount() {
		return otherPartCostAmount;
	}
	public void setOtherPartCostAmount(Double otherPartCostAmount) {
		this.otherPartCostAmount = otherPartCostAmount;
	}
	public Integer getIsBack() {
		return isBack;
	}
	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}
}
