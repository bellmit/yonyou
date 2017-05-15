package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class TtSalesQuoteItemDTO {

    private Integer dKey;
    private Double  outAmount;
    private String  entityCode;
    private Long    createBy;
    private Double  oemLimitPrice;
    private Integer ver;
    private Date    createDate;
    private Float   priceRate;
    private Long    itemId;
    private Float   outQuantity;
    private Double  costPrice;
    private Integer priceType;
    private String  partNo;
    private Date    updateDate;
    private String  storageCode;
    private String  chargePartitionCode;
    private String  unitCode;
    private String  storagePositionCode;
    private String  partName;
    private Double  costAmount;
    private Long    updateBy;
    private String  salesQuoteNo;
    private String  partBatchNo;
    private Double  outPrice;
    
    private String updateStatus;
    
    
    public String getUpdateStatus() {
        return updateStatus;
    }

    
    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public void setDKey(Integer dKey) {
        this.dKey = dKey;
    }

    public Integer getDKey() {
        return this.dKey;
    }

    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }

    public Double getOutAmount() {
        return this.outAmount;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public void setOemLimitPrice(Double oemLimitPrice) {
        this.oemLimitPrice = oemLimitPrice;
    }

    public Double getOemLimitPrice() {
        return this.oemLimitPrice;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getVer() {
        return this.ver;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setPriceRate(Float priceRate) {
        this.priceRate = priceRate;
    }

    public Float getPriceRate() {
        return this.priceRate;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setOutQuantity(Float outQuantity) {
        this.outQuantity = outQuantity;
    }

    public Float getOutQuantity() {
        return this.outQuantity;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getCostPrice() {
        return this.costPrice;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Integer getPriceType() {
        return this.priceType;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPartNo() {
        return this.partNo;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageCode() {
        return this.storageCode;
    }

    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
    }

    public String getChargePartitionCode() {
        return this.chargePartitionCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitCode() {
        return this.unitCode;
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

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setSalesQuoteNo(String salesQuoteNo) {
        this.salesQuoteNo = salesQuoteNo;
    }

    public String getSalesQuoteNo() {
        return this.salesQuoteNo;
    }

    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }

    public String getPartBatchNo() {
        return this.partBatchNo;
    }

    public Double getOutPrice() {
        return outPrice;
    }
    
    public void setOutPrice(Double outPrice) {
        this.outPrice = outPrice;
    }

}
