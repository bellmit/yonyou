
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceRepairPartDTO.java
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
* 结算单维修配件DTO
* @author ZhengHe
* @date 2016年9月26日
*/

public class BalanceRepairPartDTO {
    @Required
    private Long balanceRepairPartId;
    
    @Required
    private Long balanceId;

    @Required
    private Long balanceLabourId;
    
    //新增工单 RO_LABOUR_ID
    private Long labourId;

    private String storageCode;

    private String storagePositionCode;

    private String partNo;

    private String partName;

    private String chargePartitionCode;

    private String unit;

    private Integer isMainPart;

    @Digits(integer=10,fraction=4)
    private Double partQuantity;

    @Digits(integer=3,fraction=2)
    private Double priceRate;

    @Digits(integer=12,fraction=2)
    private Double partCostPrice;

    @Digits(integer=12,fraction=2)
    private Double partCostAmount;

    @Digits(integer=12,fraction=2)
    private Double partSalesPrice;

    @Digits(integer=12,fraction=2)
    private Double partSalesAmount;

    @Digits(integer=5,fraction=4)
    private Double discount;

    @Digits(integer=12,fraction=2)
    private Double afterDiscountAmount;

    public Long getBalanceRepairPartId() {
        return balanceRepairPartId;
    }

    public void setBalanceRepairPartId(Long balanceRepairPartId) {
        this.balanceRepairPartId = balanceRepairPartId;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getBalanceLabourId() {
        return balanceLabourId;
    }

    public void setBalanceLabourId(Long balanceLabourId) {
        this.balanceLabourId = balanceLabourId;
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

    public String getChargePartitionCode() {
        return chargePartitionCode;
    }

    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getIsMainPart() {
        return isMainPart;
    }

    public void setIsMainPart(Integer isMainPart) {
        this.isMainPart = isMainPart;
    }

    public Double getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
    }

    public Double getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    public Double getPartCostPrice() {
        return partCostPrice;
    }

    public void setPartCostPrice(Double partCostPrice) {
        this.partCostPrice = partCostPrice;
    }

    public Double getPartCostAmount() {
        return partCostAmount;
    }

    public void setPartCostAmount(Double partCostAmount) {
        this.partCostAmount = partCostAmount;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAfterDiscountAmount() {
        return afterDiscountAmount;
    }

    public void setAfterDiscountAmount(Double afterDiscountAmount) {
        this.afterDiscountAmount = afterDiscountAmount;
    }

    public Long getLabourId() {
        return labourId;
    }

    public void setLabourId(Long labourId) {
        this.labourId = labourId;
    }
}
