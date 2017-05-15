
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartProfitItemDto.java
*
* @Author : xukl
*
* @Date : 2016年8月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月12日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;

/**
* TODO description
* @author xukl
* @date 2016年8月12日
*/

public class PartProfitItemDto {
    private Integer partProfitId;

    private String dealerCode;

    private String storageCode;

    private String storagePositionCode;

    private String partNo;

    private String partName;

    private String unit;

    private Double profitQuantity;

    private Double costPrice;

    private Double costAmount;

    private Double profitPrice;

    private Double profitAmount;

    private Integer isFinished;

    private Date finishedDate;

    
    /**
     * @return the partProfitId
     */
    public Integer getPartProfitId() {
        return partProfitId;
    }

    
    /**
     * @param partProfitId the partProfitId to set
     */
    public void setPartProfitId(Integer partProfitId) {
        this.partProfitId = partProfitId;
    }

    
    /**
     * @return the dealerCode
     */
    public String getDealerCode() {
        return dealerCode;
    }

    
    /**
     * @param dealerCode the dealerCode to set
     */
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    /**
     * @return the storageCode
     */
    public String getStorageCode() {
        return storageCode;
    }

    
    /**
     * @param storageCode the storageCode to set
     */
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    
    /**
     * @return the storagePositionCode
     */
    public String getStoragePositionCode() {
        return storagePositionCode;
    }

    
    /**
     * @param storagePositionCode the storagePositionCode to set
     */
    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }

    
    /**
     * @return the partNo
     */
    public String getPartNo() {
        return partNo;
    }

    
    /**
     * @param partNo the partNo to set
     */
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    
    /**
     * @return the partName
     */
    public String getPartName() {
        return partName;
    }

    
    /**
     * @param partName the partName to set
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    
    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    
    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    
    /**
     * @return the profitQuantity
     */
    public Double getProfitQuantity() {
        return profitQuantity;
    }

    
    /**
     * @param profitQuantity the profitQuantity to set
     */
    public void setProfitQuantity(Double profitQuantity) {
        this.profitQuantity = profitQuantity;
    }

    
    /**
     * @return the costPrice
     */
    public Double getCostPrice() {
        return costPrice;
    }

    
    /**
     * @param costPrice the costPrice to set
     */
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    
    /**
     * @return the costAmount
     */
    public Double getCostAmount() {
        return costAmount;
    }

    
    /**
     * @param costAmount the costAmount to set
     */
    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }

    
    /**
     * @return the profitPrice
     */
    public Double getProfitPrice() {
        return profitPrice;
    }

    
    /**
     * @param profitPrice the profitPrice to set
     */
    public void setProfitPrice(Double profitPrice) {
        this.profitPrice = profitPrice;
    }

    
    /**
     * @return the profitAmount
     */
    public Double getProfitAmount() {
        return profitAmount;
    }

    
    /**
     * @param profitAmount the profitAmount to set
     */
    public void setProfitAmount(Double profitAmount) {
        this.profitAmount = profitAmount;
    }

    
    /**
     * @return the isFinished
     */
    public Integer getIsFinished() {
        return isFinished;
    }

    
    /**
     * @param isFinished the isFinished to set
     */
    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    
    /**
     * @return the finishedDate
     */
    public Date getFinishedDate() {
        return finishedDate;
    }

    
    /**
     * @param finishedDate the finishedDate to set
     */
    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
    
    
}
