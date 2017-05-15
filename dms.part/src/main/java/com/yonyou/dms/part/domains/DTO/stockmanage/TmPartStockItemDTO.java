package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;
/**
 * 
* TODO description
* @author yujiangheng
* @date 2017年5月6日
 */
public class TmPartStockItemDTO {
    //private Date updateDate;
    private Integer dKey;
    private Integer ver;
//  private Long createBy;

  //private Date createDate;
    //private Long updateBy;
    private String dealerCode;
    private String partNo;
    private String storageCode;
    private String unitCode;
    private String storagePositionCode;
    private String partName;
    private String partBatchNo;
    private String spellCode;
    private String remark;
    
    private Date lastStockIn;
    private Date lastStockOut;
    private Date foundDate;
    private Date reportBIDatetime;//bi接口上报时间
    
    private Integer partGroupCode;
    private Integer partStatus;
    
    private Float borrowQuantity;
    private Float stockQuantity;
    private Float lendQuantity;
    
    private Double latestPrice;
    private Double salesPrice;
    private Double insurancePrice;
    private Double claimPrice;
    private Double limitPrice;
    private Double costPrice;
    private Double costAmount;
    private Double nodePrice;
    
    
    @Override
    public String toString() {
        return "TmPartStockItemDTO [dealerCode=" + dealerCode + ", partNo=" + partNo + ", storageCode=" + storageCode
               + ", unitCode=" + unitCode + ", storagePositionCode=" + storagePositionCode + ", partName=" + partName
               + ", partBatchNo=" + partBatchNo + ", spellCode=" + spellCode + ", remark=" + remark + ", lastStockIn="
               + lastStockIn + ", lastStockOut=" + lastStockOut + ", foundDate=" + foundDate + ", reportBIDatetime="
               + reportBIDatetime + ", partGroupCode=" + partGroupCode + ", partStatus=" + partStatus
               + ", borrowQuantity=" + borrowQuantity + ", stockQuantity=" + stockQuantity + ", lendQuantity="
               + lendQuantity + ", latestPrice=" + latestPrice + ", salesPrice=" + salesPrice + ", insurancePrice="
               + insurancePrice + ", claimPrice=" + claimPrice + ", limitPrice=" + limitPrice + ", costPrice="
               + costPrice + ", costAmount=" + costAmount + ", nodePrice=" + nodePrice + "]";
    }

    
    public Integer getdKey() {
        return dKey;
    }

    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }

    
    public Integer getVer() {
        return ver;
    }

    
    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }
    
    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }
    
    public Double getSalesPrice() {
        return salesPrice;
    }
    
    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public Float getBorrowQuantity() {
        return borrowQuantity;
    }
    
    public void setBorrowQuantity(Float borrowQuantity) {
        this.borrowQuantity = borrowQuantity;
    }
    
    public Double getClaimPrice() {
        return claimPrice;
    }
    
    public void setClaimPrice(Double claimPrice) {
        this.claimPrice = claimPrice;
    }
    
    public Date getLastStockIn() {
        return lastStockIn;
    }
    
    public void setLastStockIn(Date lastStockIn) {
        this.lastStockIn = lastStockIn;
    }
    
    public Integer getPartGroupCode() {
        return partGroupCode;
    }
    
    public void setPartGroupCode(Integer partGroupCode) {
        this.partGroupCode = partGroupCode;
    }
    
    public Integer getPartStatus() {
        return partStatus;
    }
    
    public void setPartStatus(Integer partStatus) {
        this.partStatus = partStatus;
    }
    
    public Float getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public Double getInsurancePrice() {
        return insurancePrice;
    }
    
    public void setInsurancePrice(Double insurancePrice) {
        this.insurancePrice = insurancePrice;
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
    
    public String getUnitCode() {
        return unitCode;
    }
    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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
    
    public Double getCostAmount() {
        return costAmount;
    }
    
    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }
    
    public Date getFoundDate() {
        return foundDate;
    }
    
    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }
    
    public String getPartBatchNo() {
        return partBatchNo;
    }
    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
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
    
    public Double getNodePrice() {
        return nodePrice;
    }
    
    public void setNodePrice(Double nodePrice) {
        this.nodePrice = nodePrice;
    }
    
    public Date getReportBIDatetime() {
        return reportBIDatetime;
    }
    
    public void setReportBIDatetime(Date reportBIDatetime) {
        this.reportBIDatetime = reportBIDatetime;
    }
    
}
