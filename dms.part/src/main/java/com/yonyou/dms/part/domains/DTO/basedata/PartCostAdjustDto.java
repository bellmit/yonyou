
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartCostAdjustDto.java
*
* @Author : jcsi
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;



/**
*配件成本价调整流水账
* @author jcsi
* @date 2016年7月15日
*/

public class PartCostAdjustDto {
    
    private String adjustNo;  //成本调整单号
    private String storageCode; // 仓库代码
    private String storagePositionCode; // 库位
    private String partCode; // 配件代码
    private String partName;//  配件名称
    private double stockQuantity; //数量
    private double costPrice; //原成本价
    private double costAmount; // 原成本金额
    private double newPrice; // 新成本价
    private double newAmount; // 新成本金额
    private double dValue; //调整差异
    
    public String getAdjustNo() {
        return adjustNo;
    }
    
    public void setAdjustNo(String adjustNo) {
        this.adjustNo = adjustNo;
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
    
    public String getPartCode() {
        return partCode;
    }
    
    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }
    
    public String getPartName() {
        return partName;
    }
    
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    public double getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(double stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public double getCostPrice() {
        return costPrice;
    }
    
    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }
    
    public double getCostAmount() {
        return costAmount;
    }
    
    public void setCostAmount(double costAmount) {
        this.costAmount = costAmount;
    }
    
    public double getNewPrice() {
        return newPrice;
    }
    
    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
    
    public double getNewAmount() {
        return newAmount;
    }
    
    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }
    
    public double getdValue() {
        return dValue;
    }
    
    public void setdValue(double dValue) {
        this.dValue = dValue;
    }

    
}
