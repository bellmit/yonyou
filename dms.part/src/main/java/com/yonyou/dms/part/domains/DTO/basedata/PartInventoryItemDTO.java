
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : PartInventoryItemDTO.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年7月26日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月26日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.part.domains.DTO.basedata;
/**
 * 盘点差异分析子表
* TODO description
* @author yujiangheng
* @date 2017年5月17日
 */
public class PartInventoryItemDTO {
//    private Date createDate;
//    private Date updateDate;
//    private Long createBy;
//    private Long updateBy;
    private Long itemId;
    
    private String partName;
    private String partBatchNo;
    private String dealerCode;
    private String partNo;
    private String storageCode;
    private String inventoryNo;
    private String unitCode;
    private String storagePositionCode;
    
    private Float profitLossQuantity;
    private Float borrowQuantity;
    private Float checkQuantity;
    private Float lendQuantity;
    
    private Float currentStock;
    private Float realStock;
    
    private Integer dKey;
    private Integer ver;
    
    private Double profitLossAmount;
    private Double costPrice;
    
    public Long getItemId() {
        return itemId;
    }
    
    @Override
    public String toString() {
        return "PartInventoryItemDTO [itemId=" + itemId + ", partName=" + partName + ", partBatchNo=" + partBatchNo
               + ", dealerCode=" + dealerCode + ", partNo=" + partNo + ", storageCode=" + storageCode + ", inventoryNo="
               + inventoryNo + ", unitCode=" + unitCode + ", storagePositionCode=" + storagePositionCode
               + ", profitLossQuantity=" + profitLossQuantity + ", borrowQuantity=" + borrowQuantity
               + ", checkQuantity=" + checkQuantity + ", lendQuantity=" + lendQuantity + ", currentStock="
               + currentStock + ", realStock=" + realStock + ", dKey=" + dKey + ", ver=" + ver + ", profitLossAmount="
               + profitLossAmount + ", costPrice=" + costPrice + "]";
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public String getPartName() {
        return partName;
    }
    
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    public String getPartBatchNo() {
        return partBatchNo;
    }
    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
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
    
    public String getStorageCode() {
        return storageCode;
    }
    
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
    
    public String getInventoryNo() {
        return inventoryNo;
    }
    
    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
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
    
    public Float getProfitLossQuantity() {
        return profitLossQuantity;
    }
    
    public void setProfitLossQuantity(Float profitLossQuantity) {
        this.profitLossQuantity = profitLossQuantity;
    }
    
    public Float getBorrowQuantity() {
        return borrowQuantity;
    }
    
    public void setBorrowQuantity(Float borrowQuantity) {
        this.borrowQuantity = borrowQuantity;
    }
    
    public Float getCheckQuantity() {
        return checkQuantity;
    }
    
    public void setCheckQuantity(Float checkQuantity) {
        this.checkQuantity = checkQuantity;
    }
    
    public Float getLendQuantity() {
        return lendQuantity;
    }
    
    public void setLendQuantity(Float lendQuantity) {
        this.lendQuantity = lendQuantity;
    }
    
    public Float getCurrentStock() {
        return currentStock;
    }
    
    public void setCurrentStock(Float currentStock) {
        this.currentStock = currentStock;
    }
    
    public Float getRealStock() {
        return realStock;
    }
    
    public void setRealStock(Float realStock) {
        this.realStock = realStock;
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
    
    public Double getProfitLossAmount() {
        return profitLossAmount;
    }
    
    public void setProfitLossAmount(Double profitLossAmount) {
        this.profitLossAmount = profitLossAmount;
    }
    
    public Double getCostPrice() {
        return costPrice;
    }
    
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }
    
    
}