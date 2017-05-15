
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

import javax.validation.constraints.Digits;

/**
 * 配件盘点单明细Dto
 * @author ZhengHe
 * @date 2016年7月26日
 */

public class PartInventoryItemDTO {
    private Long partInventoryId;

    private String inventoryNo;

    private String storageCode;

    private String storagePositionCode;

    private String partNo;

    private String partName;

    @Digits(integer=10,fraction=4)
    private Double stockQuantity;

    @Digits(integer=10,fraction=4)
    private Double borrowQuantity;

    @Digits(integer=10,fraction=4)
    private Double lendQuantity;

    @Digits(integer=10,fraction=4)
    private Double realStock;

    @Digits(integer=10,fraction=4)
    private Double checkQuantity;

    @Digits(integer=10,fraction=4)
    private Double profitLossQuantity;

    @Digits(integer=12,fraction=2)
    private Double costPrice;

    @Digits(integer=12,fraction=2)
    private Double profitLossAmount;

    public Long getPartInventoryId() {
        return partInventoryId;
    }

    public void setPartInventoryId(Long partInventoryId) {
        this.partInventoryId = partInventoryId;
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
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

    public Double getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Double stockQuantity) {
        this.stockQuantity = stockQuantity;
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

    public Double getRealStock() {
        return realStock;
    }

    public void setRealStock(Double realStock) {
        this.realStock = realStock;
    }

    public Double getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(Double checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public Double getProfitLossQuantity() {
        return profitLossQuantity;
    }

    public void setProfitLossQuantity(Double profitLossQuantity) {
        this.profitLossQuantity = profitLossQuantity;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getProfitLossAmount() {
        return profitLossAmount;
    }

    public void setProfitLossAmount(Double profitLossAmount) {
        this.profitLossAmount = profitLossAmount;
    }
}