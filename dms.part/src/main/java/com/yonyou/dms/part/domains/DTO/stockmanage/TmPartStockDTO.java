package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;
/**
 * 
* TODO description
* @author yujiangheng
* @date 2017年5月6日
 */
public class TmPartStockDTO {
    /**
    ver，status，recordId，minPackage，productingArea，optionNo，brand，empNo
         */
    // remark，partNo，partName， unitCode，storagePositionCode，storageCode，spellCode，partModelGroupCodeSet，
    private String partNo;
    private String spellCode;
    private String remark;
    private String providerCode;//
    private String providerName;//
    private String storagePositionCode;
    private String dealerCode;//
    private String partName;
    private String storageCode;
    private String unitCode;
    private String partModelGroupCodeSet;
    //monthlyQtyFormula，partMainType，partStatus，partSpeType，isSuggestOrder，partGroupCode，
    private Integer monthlyQtyFormula;
    private Integer partMainType;
    private Integer partGroupCode;
    private Integer partStatus;
    private Integer isautoMaxminStock;//
    private Integer partSpeType;
    private Integer isSuggestOrder;

    //lastStockIn，lastStockOut，
    private Date slowMovingDate;//
    private Date foundDate;//
    private Date lastStockIn;
    private Date lastStockOut;

    //salesPrice，latestPrice，insurancePrice， costAmount，limitPrice，costPrice，claimPrice，
    private Double salesPrice;
    private Double latestPrice;
    private Double insurancePrice;
    private Double costAmount;
    private Double limitPrice;
    private Double costPrice;
    private Double claimPrice;
    private Double nodePrice;//
    private Double otherPartCostPrice;//
    private Double otherPartCostAmount;//
  
    //stockQuantity，octModulus，safeStock，augModulus，minStock，julModulus，febModulus，decModulus，janModulus，sepModulus，
    //aprModulus，marModulus，borrowQuantity，maxStock，lendQuantity，lockedQuantity，novModulus，junModulus，mayModulus，
    private Float stockQuantity;
    private Float borrowQuantity;
    private Float lockedQuantity;
    private Float lendQuantity;
    
    private Float safeStock;
    private Float minStock;
    private Float maxStock;
    
    private Float augModulus;
    private Float octModulus;
    private Float julModulus;
    private Float febModulus;
    private Float decModulus;
    private Float janModulus;
    private Float sepModulus;
    private Float aprModulus;
    private Float marModulus;
    private Float novModulus;
    private Float junModulus;
    private Float mayModulus;
    
    
    
    
   
    
    
   
    
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getPartName() {
        return partName;
    }
    
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    public String getStorageCode() {
        return storageCode;
    }
    
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
    
    public String getUnitCode() {
        return unitCode;
    }
    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    
    public Integer getMonthlyQtyFormula() {
        return monthlyQtyFormula;
    }
    
    public void setMonthlyQtyFormula(Integer monthlyQtyFormula) {
        this.monthlyQtyFormula = monthlyQtyFormula;
    }
    
    public Double getLatestPrice() {
        return latestPrice;
    }
    
    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }
    
    public Float getSafeStock() {
        return safeStock;
    }
    
    public void setSafeStock(Float safeStock) {
        this.safeStock = safeStock;
    }
    
    public Float getAugModulus() {
        return augModulus;
    }
    
    public void setAugModulus(Float augModulus) {
        this.augModulus = augModulus;
    }
    
    public Double getSalesPrice() {
        return salesPrice;
    }
    
    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }
    
    public String getPartModelGroupCodeSet() {
        return partModelGroupCodeSet;
    }
    
    public void setPartModelGroupCodeSet(String partModelGroupCodeSet) {
        this.partModelGroupCodeSet = partModelGroupCodeSet;
    }
    
    public Date getLastStockIn() {
        return lastStockIn;
    }
    
    public void setLastStockIn(Date lastStockIn) {
        this.lastStockIn = lastStockIn;
    }
    
    public Integer getPartMainType() {
        return partMainType;
    }
    
    public void setPartMainType(Integer partMainType) {
        this.partMainType = partMainType;
    }
    
    public Integer getPartGroupCode() {
        return partGroupCode;
    }
    
    public void setPartGroupCode(Integer partGroupCode) {
        this.partGroupCode = partGroupCode;
    }
    
    public Float getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public Float getOctModulus() {
        return octModulus;
    }
    
    public void setOctModulus(Float octModulus) {
        this.octModulus = octModulus;
    }
    
    public Double getInsurancePrice() {
        return insurancePrice;
    }
    
    public void setInsurancePrice(Double insurancePrice) {
        this.insurancePrice = insurancePrice;
    }
    
    public Float getJulModulus() {
        return julModulus;
    }
    
    public void setJulModulus(Float julModulus) {
        this.julModulus = julModulus;
    }
    
    public String getPartNo() {
        return partNo;
    }
    
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    
    public Float getMinStock() {
        return minStock;
    }
    
    public void setMinStock(Float minStock) {
        this.minStock = minStock;
    }
    
    public Float getFebModulus() {
        return febModulus;
    }
    
    public void setFebModulus(Float febModulus) {
        this.febModulus = febModulus;
    }
    
    public String getStoragePositionCode() {
        return storagePositionCode;
    }
    
    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }
    
    public Double getCostAmount() {
        return costAmount;
    }
    
    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }
    
    public Float getDecModulus() {
        return decModulus;
    }
    
    public void setDecModulus(Float decModulus) {
        this.decModulus = decModulus;
    }
    
    public Float getJanModulus() {
        return janModulus;
    }
    
    public void setJanModulus(Float janModulus) {
        this.janModulus = janModulus;
    }
    
    public Float getSepModulus() {
        return sepModulus;
    }
    
    public void setSepModulus(Float sepModulus) {
        this.sepModulus = sepModulus;
    }
    
    public Float getAprModulus() {
        return aprModulus;
    }
    
    public void setAprModulus(Float aprModulus) {
        this.aprModulus = aprModulus;
    }
    
    public Float getMarModulus() {
        return marModulus;
    }
    
    public void setMarModulus(Float marModulus) {
        this.marModulus = marModulus;
    }
    
    public Float getBorrowQuantity() {
        return borrowQuantity;
    }
    
    public void setBorrowQuantity(Float borrowQuantity) {
        this.borrowQuantity = borrowQuantity;
    }
    
    public Float getMaxStock() {
        return maxStock;
    }
    
    public void setMaxStock(Float maxStock) {
        this.maxStock = maxStock;
    }
    
    public Double getClaimPrice() {
        return claimPrice;
    }
    
    public void setClaimPrice(Double claimPrice) {
        this.claimPrice = claimPrice;
    }
    
    public Float getMayModulus() {
        return mayModulus;
    }
    
    public void setMayModulus(Float mayModulus) {
        this.mayModulus = mayModulus;
    }
    
    public Integer getPartStatus() {
        return partStatus;
    }
    
    public void setPartStatus(Integer partStatus) {
        this.partStatus = partStatus;
    }
    
    public Double getLimitPrice() {
        return limitPrice;
    }
    
    public void setLimitPrice(Double limitPrice) {
        this.limitPrice = limitPrice;
    }
    
    public Double getCostPrice() {
        return costPrice;
    }
    
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }
    
    public Integer getIsautoMaxminStock() {
        return isautoMaxminStock;
    }
    
    public void setIsautoMaxminStock(Integer isautoMaxminStock) {
        this.isautoMaxminStock = isautoMaxminStock;
    }
    
    public Integer getPartSpeType() {
        return partSpeType;
    }
    
    public void setPartSpeType(Integer partSpeType) {
        this.partSpeType = partSpeType;
    }
    
    public Float getLockedQuantity() {
        return lockedQuantity;
    }
    
    public void setLockedQuantity(Float lockedQuantity) {
        this.lockedQuantity = lockedQuantity;
    }
    
    public Float getLendQuantity() {
        return lendQuantity;
    }
    
    public void setLendQuantity(Float lendQuantity) {
        this.lendQuantity = lendQuantity;
    }
    
    public Date getLastStockOut() {
        return lastStockOut;
    }
    
    public void setLastStockOut(Date lastStockOut) {
        this.lastStockOut = lastStockOut;
    }
    
    public Float getNovModulus() {
        return novModulus;
    }
    
    public void setNovModulus(Float novModulus) {
        this.novModulus = novModulus;
    }
    
    public Integer getIsSuggestOrder() {
        return isSuggestOrder;
    }
    
    public void setIsSuggestOrder(Integer isSuggestOrder) {
        this.isSuggestOrder = isSuggestOrder;
    }
    
    public Float getJunModulus() {
        return junModulus;
    }
    
    public void setJunModulus(Float junModulus) {
        this.junModulus = junModulus;
    }
    
    public Date getFoundDate() {
        return foundDate;
    }
    
    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }
    
    public String getSpellCode() {
        return spellCode;
    }
    
    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
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
    
    public Date getSlowMovingDate() {
        return slowMovingDate;
    }
    
    public void setSlowMovingDate(Date slowMovingDate) {
        this.slowMovingDate = slowMovingDate;
    }
    
    
    
}
