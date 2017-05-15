
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceSalesPartDTO.java
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
* 结算单销售配件明细DTO
* @author ZhengHe
* @date 2016年9月26日
*/

public class BalanceSalesPartDTO {
    @Required
    private Long balanceSalesPartId;
    
    @Required
    private Long balanceId;

    @Required
    private Long itemId;

    private Long salesPartId;

    private String storageCode;

    private String storagePositionCode;

    private String partNo;

    private String partName;

    @Digits(integer=10,fraction=4)
    private Double partQuantity;

    private String unit;

    @Digits(integer=3,fraction=2)
    private Double discount;

    private Integer priceType;

    @Digits(integer=12,fraction=2)
    private Double priceRate;

    @Digits(integer=12,fraction=2)
    private Double costPrice;

    @Digits(integer=12,fraction=2)
    private Double partSalesPrice;

    @Digits(integer=12,fraction=2)
    private Double partCostAmount;

    @Digits(integer=12,fraction=2)
    private Double partSalesAmount;

    @Digits(integer=12,fraction=2)
    private Double salesAmount;

    public Long getBalanceSalesPartId() {
        return balanceSalesPartId;
    }

    public void setBalanceSalesPartId(Long balanceSalesPartId) {
        this.balanceSalesPartId = balanceSalesPartId;
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

    public Long getSalesPartId() {
        return salesPartId;
    }

    public void setSalesPartId(Long salesPartId) {
        this.salesPartId = salesPartId;
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

    public Double getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Double getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getPartSalesPrice() {
        return partSalesPrice;
    }

    public void setPartSalesPrice(Double partSalesPrice) {
        this.partSalesPrice = partSalesPrice;
    }

    public Double getPartCostAmount() {
        return partCostAmount;
    }

    public void setPartCostAmount(Double partCostAmount) {
        this.partCostAmount = partCostAmount;
    }

    public Double getPartSalesAmount() {
        return partSalesAmount;
    }

    public void setPartSalesAmount(Double partSalesAmount) {
        this.partSalesAmount = partSalesAmount;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }
}
