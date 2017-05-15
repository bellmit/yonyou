package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

/**
 * 配件库存 TODO description
 * 
 * @author chenwei
 * @date 2017年4月30日
 */
public class TmPartStockDTO {

    private Integer monthlyQtyFormula;
    private Double  latestPrice;
    private Float   safeStock;
    private Float   augModulus;
    private Double  salesPrice;
    private String  dealerCode;
    private Integer ver;
    private Date    createdDate;
    private String  partModelGroupCodeSet;
    private Date    lastStockIn;
    private Integer partMainType;
    private Integer partGroupCode;
    private Float   stockQuantity;
    private Float   octModulus;
    private Double  insurancePrice;
    private Float   julModulus;
    private String  partNo;
    private Float   minStock;
    private String  storageCode;
    private Float   febModulus;
    private String  storagePositionCode;
    private String  partName;
    private Double  costAmount;
    private Long    updatedBy;
    private Float   decModulus;
    private Float   janModulus;
    private Float   sepModulus;
    private Float   aprModulus;
    private Integer dKey;
    private Float   marModulus;
    private Long    createdBy;
    private Float   borrowQuantity;
    private Float   maxStock;
    private Double  claimPrice;
    private Float   mayModulus;
    private Integer partStatus;
    private Double  limitPrice;
    private Double  costPrice;
    private Integer isautoMaxminStock;
    private Date    updatedDate;
    private Integer partSpeType;
    private Float   lockedQuantity;
    private Float   lendQuantity;
    private Date    lastStockOut;
    private String  unitCode;
    private Float   novModulus;
    private Integer isSuggestOrder;
    private Float   junModulus;
    private Date    foundDate;
    private String  spellCode;
    private String  remark;
    private String  providerCode;
    private String  providerName;
    private Double  nodePrice;
    private Double  otherPartCostPrice;
    private Double  otherPartCostAmount;
    private Date    slowMovingDate;

    public Date getSlowMovingDate() {
        return slowMovingDate;
    }

    public void setSlowMovingDate(Date slowMovingDate) {
        this.slowMovingDate = slowMovingDate;
    }

    public Double getOtherPartCostAmount() {
        return otherPartCostAmount;
    }

    public void setOtherPartCostAmount(Double otherPartCostAmount) {
        this.otherPartCostAmount = otherPartCostAmount;
    }

    public Double getOtherPartCostPrice() {
        return otherPartCostPrice;
    }

    public void setOtherPartCostPrice(Double otherPartCostPrice) {
        this.otherPartCostPrice = otherPartCostPrice;
    }

    public void setMonthlyQtyFormula(Integer monthlyQtyFormula) {
        this.monthlyQtyFormula = monthlyQtyFormula;
    }

    public Integer getMonthlyQtyFormula() {
        return this.monthlyQtyFormula;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public Double getLatestPrice() {
        return this.latestPrice;
    }

    public void setSafeStock(Float safeStock) {
        this.safeStock = safeStock;
    }

    public Float getSafeStock() {
        return this.safeStock;
    }

    public void setAugModulus(Float augModulus) {
        this.augModulus = augModulus;
    }

    public Float getAugModulus() {
        return this.augModulus;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Double getSalesPrice() {
        return this.salesPrice;
    }


    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getVer() {
        return this.ver;
    }

    public void setPartModelGroupCodeSet(String partModelGroupCodeSet) {
        this.partModelGroupCodeSet = partModelGroupCodeSet;
    }

    public String getPartModelGroupCodeSet() {
        return this.partModelGroupCodeSet;
    }

    public void setLastStockIn(Date lastStockIn) {
        this.lastStockIn = lastStockIn;
    }

    public Date getLastStockIn() {
        return this.lastStockIn;
    }

    public void setPartMainType(Integer partMainType) {
        this.partMainType = partMainType;
    }

    public Integer getPartMainType() {
        return this.partMainType;
    }

    public void setPartGroupCode(Integer partGroupCode) {
        this.partGroupCode = partGroupCode;
    }

    public Integer getPartGroupCode() {
        return this.partGroupCode;
    }

    public void setStockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Float getStockQuantity() {
        return this.stockQuantity;
    }

    public void setOctModulus(Float octModulus) {
        this.octModulus = octModulus;
    }

    public Float getOctModulus() {
        return this.octModulus;
    }

    public void setInsurancePrice(Double insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public Double getInsurancePrice() {
        return this.insurancePrice;
    }

    public void setJulModulus(Float julModulus) {
        this.julModulus = julModulus;
    }

    public Float getJulModulus() {
        return this.julModulus;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPartNo() {
        return this.partNo;
    }

    public void setMinStock(Float minStock) {
        this.minStock = minStock;
    }

    public Float getMinStock() {
        return this.minStock;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageCode() {
        return this.storageCode;
    }

    public void setFebModulus(Float febModulus) {
        this.febModulus = febModulus;
    }

    public Float getFebModulus() {
        return this.febModulus;
    }

    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }

    public String getStoragePositionCode() {
        return this.storagePositionCode;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }

    public Double getCostAmount() {
        return this.costAmount;
    }

    public void setDecModulus(Float decModulus) {
        this.decModulus = decModulus;
    }

    public Float getDecModulus() {
        return this.decModulus;
    }

    public void setJanModulus(Float janModulus) {
        this.janModulus = janModulus;
    }

    public Float getJanModulus() {
        return this.janModulus;
    }

    public void setSepModulus(Float sepModulus) {
        this.sepModulus = sepModulus;
    }

    public Float getSepModulus() {
        return this.sepModulus;
    }

    public void setAprModulus(Float aprModulus) {
        this.aprModulus = aprModulus;
    }

    public Float getAprModulus() {
        return this.aprModulus;
    }

    public void setDKey(Integer dKey) {
        this.dKey = dKey;
    }

    public Integer getDKey() {
        return this.dKey;
    }

    public void setMarModulus(Float marModulus) {
        this.marModulus = marModulus;
    }

    public Float getMarModulus() {
        return this.marModulus;
    }

    public void setBorrowQuantity(Float borrowQuantity) {
        this.borrowQuantity = borrowQuantity;
    }

    public Float getBorrowQuantity() {
        return this.borrowQuantity;
    }

    public void setMaxStock(Float maxStock) {
        this.maxStock = maxStock;
    }

    public Float getMaxStock() {
        return this.maxStock;
    }

    public void setClaimPrice(Double claimPrice) {
        this.claimPrice = claimPrice;
    }

    public Double getClaimPrice() {
        return this.claimPrice;
    }

    public void setMayModulus(Float mayModulus) {
        this.mayModulus = mayModulus;
    }

    public Float getMayModulus() {
        return this.mayModulus;
    }

    public void setPartStatus(Integer partStatus) {
        this.partStatus = partStatus;
    }

    public Integer getPartStatus() {
        return this.partStatus;
    }

    public void setLimitPrice(Double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public Double getLimitPrice() {
        return this.limitPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getCostPrice() {
        return this.costPrice;
    }

    public void setIsautoMaxminStock(Integer isautoMaxminStock) {
        this.isautoMaxminStock = isautoMaxminStock;
    }

    public Integer getIsautoMaxminStock() {
        return this.isautoMaxminStock;
    }

    public void setPartSpeType(Integer partSpeType) {
        this.partSpeType = partSpeType;
    }

    public Integer getPartSpeType() {
        return this.partSpeType;
    }

    public void setLockedQuantity(Float lockedQuantity) {
        this.lockedQuantity = lockedQuantity;
    }

    public Float getLockedQuantity() {
        return this.lockedQuantity;
    }

    public void setLendQuantity(Float lendQuantity) {
        this.lendQuantity = lendQuantity;
    }

    public Float getLendQuantity() {
        return this.lendQuantity;
    }

    public void setLastStockOut(Date lastStockOut) {
        this.lastStockOut = lastStockOut;
    }

    public Date getLastStockOut() {
        return this.lastStockOut;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setNovModulus(Float novModulus) {
        this.novModulus = novModulus;
    }

    public Float getNovModulus() {
        return this.novModulus;
    }

    public void setIsSuggestOrder(Integer isSuggestOrder) {
        this.isSuggestOrder = isSuggestOrder;
    }

    public Integer getIsSuggestOrder() {
        return this.isSuggestOrder;
    }

    public void setJunModulus(Float junModulus) {
        this.junModulus = junModulus;
    }

    public Float getJunModulus() {
        return this.junModulus;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public Date getFoundDate() {
        return this.foundDate;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }

    public String getSpellCode() {
        return this.spellCode;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getdKey() {
        return dKey;
    }

    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    @Override
    public String toString() {
        return "TmPartStockDTO [monthlyQtyFormula=" + monthlyQtyFormula + ", latestPrice=" + latestPrice
               + ", safeStock=" + safeStock + ", augModulus=" + augModulus + ", salesPrice=" + salesPrice
               + ", dealerCode=" + dealerCode + ", ver=" + ver + ", createdDate=" + createdDate
               + ", partModelGroupCodeSet=" + partModelGroupCodeSet + ", lastStockIn=" + lastStockIn + ", partMainType="
               + partMainType + ", partGroupCode=" + partGroupCode + ", stockQuantity=" + stockQuantity
               + ", octModulus=" + octModulus + ", insurancePrice=" + insurancePrice + ", julModulus=" + julModulus
               + ", partNo=" + partNo + ", minStock=" + minStock + ", storageCode=" + storageCode + ", febModulus="
               + febModulus + ", storagePositionCode=" + storagePositionCode + ", partName=" + partName
               + ", costAmount=" + costAmount + ", updatedBy=" + updatedBy + ", decModulus=" + decModulus
               + ", janModulus=" + janModulus + ", sepModulus=" + sepModulus + ", aprModulus=" + aprModulus + ", dKey="
               + dKey + ", marModulus=" + marModulus + ", createdBy=" + createdBy + ", borrowQuantity=" + borrowQuantity
               + ", maxStock=" + maxStock + ", claimPrice=" + claimPrice + ", mayModulus=" + mayModulus
               + ", partStatus=" + partStatus + ", limitPrice=" + limitPrice + ", costPrice=" + costPrice
               + ", isautoMaxminStock=" + isautoMaxminStock + ", updatedDate=" + updatedDate + ", partSpeType="
               + partSpeType + ", lockedQuantity=" + lockedQuantity + ", lendQuantity=" + lendQuantity
               + ", lastStockOut=" + lastStockOut + ", unitCode=" + unitCode + ", novModulus=" + novModulus
               + ", isSuggestOrder=" + isSuggestOrder + ", junModulus=" + junModulus + ", foundDate=" + foundDate
               + ", spellCode=" + spellCode + ", remark=" + remark + ", providerCode=" + providerCode
               + ", providerName=" + providerName + ", nodePrice=" + nodePrice + ", otherPartCostPrice="
               + otherPartCostPrice + ", otherPartCostAmount=" + otherPartCostAmount + ", slowMovingDate="
               + slowMovingDate + "]";
    }

}
