package com.yonyou.dms.part.domains.DTO.stockmanage;

/*
 * 
* TODO description
* @author yujiangheng
* @date 2017年5月9日
 */
public class TtPartAllocateInItemDTO {
   // private Date createDate;
//    private Date updateDate;
//    private Long createBy;
//    private Long updateBy;
    private Long itemId;
    
    private Float inQuantity;
    
    private Integer ver;
    private Integer dKey;
    private Integer downTag;
    
    private String dealerCode;
    private String partNo;
    private String allocateInNo;
    private String storagePositionCode;
    private String unitCode;
    private String partName;
    private String storageCode;
    private String partBatchNo;
    private String receiveRemark;
    
    private Double inPrice;
    private Double inAmount;
    private Double costPrice;
    private Double otherPartCostPrice;
    private Double otherPartCostAmount;
    private Double dnpPrice;
    private Double msrpPrice;
    private Double costAmount;
    
    
    
    @Override
    public String toString() {
        return "TtPartAllocateInItemDTO [itemId=" + itemId + ", inQuantity=" + inQuantity + ", ver=" + ver + ", dKey="
               + dKey + ", downTag=" + downTag + ", dealerCode=" + dealerCode + ", partNo=" + partNo + ", allocateInNo="
               + allocateInNo + ", storagePositionCode=" + storagePositionCode + ", unitCode=" + unitCode
               + ", partName=" + partName + ", storageCode=" + storageCode + ", partBatchNo=" + partBatchNo
               + ", receiveRemark=" + receiveRemark + ", inPrice=" + inPrice + ", inAmount=" + inAmount + ", costPrice="
               + costPrice + ", otherPartCostPrice=" + otherPartCostPrice + ", otherPartCostAmount="
               + otherPartCostAmount + ", dnpPrice=" + dnpPrice + ", msrpPrice=" + msrpPrice + ", costAmount="
               + costAmount + "]";
    }

    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public Float getInQuantity() {
        return inQuantity;
    }
    
    public void setInQuantity(Float inQuantity) {
        this.inQuantity = inQuantity;
    }
    
    public Integer getVer() {
        return ver;
    }
    
    public void setVer(Integer ver) {
        this.ver = ver;
    }
    
    public Integer getdKey() {
        return dKey;
    }
    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }
    
    public Integer getDownTag() {
        return downTag;
    }
    
    public void setDownTag(Integer downTag) {
        this.downTag = downTag;
    }
    
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
    
    public String getAllocateInNo() {
        return allocateInNo;
    }
    
    public void setAllocateInNo(String allocateInNo) {
        this.allocateInNo = allocateInNo;
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
    
    public String getPartBatchNo() {
        return partBatchNo;
    }
    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }
    
    public String getReceiveRemark() {
        return receiveRemark;
    }
    
    public void setReceiveRemark(String receiveRemark) {
        this.receiveRemark = receiveRemark;
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
    
    public Double getCostPrice() {
        return costPrice;
    }
    
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
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
    
    public Double getDnpPrice() {
        return dnpPrice;
    }
    
    public void setDnpPrice(Double dnpPrice) {
        this.dnpPrice = dnpPrice;
    }
    
    public Double getMsrpPrice() {
        return msrpPrice;
    }
    
    public void setMsrpPrice(Double msrpPrice) {
        this.msrpPrice = msrpPrice;
    }
    
    public Double getCostAmount() {
        return costAmount;
    }
    
    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }
    
 
    
 

    
    
    
    
}
