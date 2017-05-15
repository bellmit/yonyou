package com.yonyou.dms.part.domains.DTO.basedata;


public class TtPartBorrowItemDTO {
//    private Long updateBy;
//    private Date createDate;
//    private Date updateDate;
//    private Long createBy;
    
    private String dealerCode;
    private String storagePositionCode;
    private String unitCode;
    private String partName;
    private String storageCode;
    private String partNo;
    private String partBatchNo;
    private String borrowNo;
    
    private String itemId;
    
    private Integer dKey;
    private Integer ver;
    
    private Float writeOffQuantity;
    private Float inQuantity;
    
    private Double costPrice;
    private Double costAmount;
    private Double inPrice;
    private Double inAmount;
    private Double otherPartCostPrice;
    private Double otherPartCostAmount;
    
    private String flag;
    
  
 
    
    
    
    @Override
    public String toString() {
        return "TtPartBorrowItemDTO [dealerCode=" + dealerCode + ", storagePositionCode=" + storagePositionCode
               + ", unitCode=" + unitCode + ", partName=" + partName + ", storageCode=" + storageCode + ", partNo="
               + partNo + ", partBatchNo=" + partBatchNo + ", borrowNo=" + borrowNo + ", itemId=" + itemId + ", dKey="
               + dKey + ", ver=" + ver + ", writeOffQuantity=" + writeOffQuantity + ", inQuantity=" + inQuantity
               + ", costPrice=" + costPrice + ", costAmount=" + costAmount + ", inPrice=" + inPrice + ", inAmount="
               + inAmount + ", otherPartCostPrice=" + otherPartCostPrice + ", otherPartCostAmount="
               + otherPartCostAmount + ", flag=" + flag + "]";
    }





    public String getFlag() {
        return flag;
    }




    
    public void setFlag(String flag) {
        this.flag = flag;
    }




    public String getDealerCode() {
        return dealerCode;
    }



    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }



    public String getStoragePositionCode() {
        return storagePositionCode;
    }
    
    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }
    
    public String getUnitCode() {
        return unitCode;
    }
    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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
    
    public String getPartNo() {
        return partNo;
    }
    
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    
    public String getPartBatchNo() {
        return partBatchNo;
    }
    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }
    
    public String getBorrowNo() {
        return borrowNo;
    }
    
    public void setBorrowNo(String borrowNo) {
        this.borrowNo = borrowNo;
    }
    
  
    
    
    public String getItemId() {
        return itemId;
    }




    
    public void setItemId(String itemId) {
        this.itemId = itemId;
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
    
    public Float getWriteOffQuantity() {
        return writeOffQuantity;
    }
    
    public void setWriteOffQuantity(Float writeOffQuantity) {
        this.writeOffQuantity = writeOffQuantity;
    }
    
    public Float getInQuantity() {
        return inQuantity;
    }
    
    public void setInQuantity(Float inQuantity) {
        this.inQuantity = inQuantity;
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
    
    public Double getInPrice() {
        return inPrice;
    }
    
    public void setInPrice(Double inPrice) {
        this.inPrice = inPrice;
    }
    
    public Double getInAmount() {
        return inAmount;
    }
    
    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
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
    
    
    
    
    
}
