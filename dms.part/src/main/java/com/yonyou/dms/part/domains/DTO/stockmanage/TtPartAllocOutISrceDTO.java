package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;

public class TtPartAllocOutISrceDTO {
    
    private String itemId;
    private String dealerCode;
    private String allocateOutNo;
    private String partNo;
    private String partName;
    private String storageCode;
    private String storagePositionCode;
    private String fromEntity;
    private String unitCode;
    private String costAmount;
    private String partBatchNo;
    private String outPrice;
    private String outAmount;
    private String outQuantity;
    private String costPrice;
    
    @Override
    public String toString() {
        return "TtPartAllocOutISrceDTO [itemId=" + itemId + ", dealerCode=" + dealerCode + ", allocateOutNo="
               + allocateOutNo + ", partNo=" + partNo + ", partName=" + partName + ", storageCode=" + storageCode
               + ", storagePositionCode=" + storagePositionCode + ", fromEntity=" + fromEntity + ", unitCode="
               + unitCode + ", costAmount=" + costAmount + ", partBatchNo=" + partBatchNo + ", outPrice=" + outPrice
               + ", outAmount=" + outAmount + ", outQuantity=" + outQuantity + ", costPrice=" + costPrice + "]";
    }

    public String getItemId() {
        return itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getAllocateOutNo() {
        return allocateOutNo;
    }
    
    public void setAllocateOutNo(String allocateOutNo) {
        this.allocateOutNo = allocateOutNo;
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
    
    public String getFromEntity() {
        return fromEntity;
    }
    
    public void setFromEntity(String fromEntity) {
        this.fromEntity = fromEntity;
    }
    
    public String getUnitCode() {
        return unitCode;
    }
    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    
   

    
    
    public String getCostAmount() {
        return costAmount;
    }

    
    public void setCostAmount(String costAmount) {
        this.costAmount = costAmount;
    }

    
    public String getOutPrice() {
        return outPrice;
    }

    
    public void setOutPrice(String outPrice) {
        this.outPrice = outPrice;
    }

    
    public String getOutAmount() {
        return outAmount;
    }

    
    public void setOutAmount(String outAmount) {
        this.outAmount = outAmount;
    }

    
    public String getOutQuantity() {
        return outQuantity;
    }

    
    public void setOutQuantity(String outQuantity) {
        this.outQuantity = outQuantity;
    }

    
    public String getCostPrice() {
        return costPrice;
    }

    
    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getPartBatchNo() {
        return partBatchNo;
    }
    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }
    
    
    
    
    
}
