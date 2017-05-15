
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceAllocatePartDTO.java
*
* @Author : ZhengHe
*
* @Date : 2016年9月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.balance;

import javax.validation.constraints.Digits;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 结算单调拨出库单明细DTO
* @author ZhengHe
* @date 2016年9月26日
*/

public class BalanceAllocatePartDTO {
    @Required
    private Long balanceAllocatePartId;

    @Required
    private Long balanceId;

    @Required
    private Long itemId;

    private Long allocateOutId;

    private String storageCode;

    private String storagePositionCode;

    private String partNo;

    private String partName;

    private String unit;

    @Digits(integer=10,fraction=4)
    private Double outQuantity;

    @Digits(integer=12,fraction=2)
    private Double outPrice;

    @Digits(integer=12,fraction=2)
    private Double outAmount;

    @Digits(integer=12,fraction=2)
    private Double costPrice;

    @Digits(integer=12,fraction=2)
    private Double costAmount;

    public Long getBalanceAllocatePartId() {
        return balanceAllocatePartId;
    }

    public void setBalanceAllocatePartId(Long balanceAllocatePartId) {
        this.balanceAllocatePartId = balanceAllocatePartId;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getAllocateOutId() {
        return allocateOutId;
    }

    public void setAllocateOutId(Long allocateOutId) {
        this.allocateOutId = allocateOutId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(Double outQuantity) {
        this.outQuantity = outQuantity;
    }

    public Double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(Double outPrice) {
        this.outPrice = outPrice;
    }

    public Double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
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
}
