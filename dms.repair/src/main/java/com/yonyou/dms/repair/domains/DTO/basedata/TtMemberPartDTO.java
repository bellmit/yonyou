package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

/**
 * 会员配件项目
 * 
 * @author chenwei
 * @date 2017年4月7日
 */
public class TtMemberPartDTO {

    private String  dealerCode;
    private Long    itemId;
    private String  labourCode;
    private Double  partCostAmount;
    private Double  partCostPrice;
    private Float  usedPartCount;
    private String  chargePartitionCode;
    private Double  discount;
    private String  unitName;
    private Integer isMainPart;
    private Integer dKey;
    private Integer ver;
    private String  storageCode;
    private Integer cardId;
    private String  partNo;
    private String  partName;
    private Double  partQuantity;
    private Double  partSalesPrice;
    private Double  partSalesAmount;
    private Integer businessType;
    private Date    expDate;
    private Date    ddcnUpdateDate;
    // added by chenwei
    private String  createdBy;
    private Date    createdAt;
    private String  updatedBy;
    private Date    updatedAt;

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getLabourCode() {
        return labourCode;
    }

    public void setLabourCode(String labourCode) {
        this.labourCode = labourCode;
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



    
    public Float getUsedPartCount() {
        return usedPartCount;
    }

    
    public void setUsedPartCount(Float usedPartCount) {
        this.usedPartCount = usedPartCount;
    }

    public String getChargePartitionCode() {
        return chargePartitionCode;
    }

    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getIsMainPart() {
        return isMainPart;
    }

    public void setIsMainPart(Integer isMainPart) {
        this.isMainPart = isMainPart;
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

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
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

    public Double getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
    }

    public Double getPartSalesPrice() {
        return partSalesPrice;
    }

    public void setPartSalesPrice(Double partSalesPrice) {
        this.partSalesPrice = partSalesPrice;
    }

    public Double getPartSalesAmount() {
        return partSalesAmount;
    }

    public void setPartSalesAmount(Double partSalesAmount) {
        this.partSalesAmount = partSalesAmount;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getDdcnUpdateDate() {
        return ddcnUpdateDate;
    }

    public void setDdcnUpdateDate(Date ddcnUpdateDate) {
        this.ddcnUpdateDate = ddcnUpdateDate;
    }

}
