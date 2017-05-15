
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInnerItemDto.java
*
* @Author : jcsi
*
* @Date : 2016年8月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;

/**
*
* @author jcsi
* @date 2016年8月14日
*/

public class PartInnerItemDto {

    private String storageCode; //仓库代码
    
    private String storagePositionCode;  //库位代码
    
    private String partNo;  //配件代码
    
    private String partName; //配件名称
    
    
    private String unit;  //计量单位
    
    private Double outQuantity;  //出库数量
    
    private Double costPrice; //成本单价
    
    private Double costAmount; //成本金额
    
    private Double outPrice;  //出库单价
    
    private Double outAmount; //出库金额
    
    private Long isFinished;  //是否入账
    
    private Date finishedDate; //入账日期

    
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

    
    public Long getIsFinished() {
        return isFinished;
    }

    
    public void setIsFinished(Long isFinished) {
        this.isFinished = isFinished;
    }

    
    public Date getFinishedDate() {
        return finishedDate;
    }

    
    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
    
}
