/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartAllocateInItemDto.java
*
* @Author : xukl
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月10日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
*PartAllocateInItemDto
* @author xukl
* @date 2016年8月10日
*/
public class PartAllocateInItemDto extends DataImportDto {
   private String itemId;//ITEM_ID id
   private String dealerCode;
   private String storageCode;//STORAGE_CODE 仓库
   private String storagePositionCode;//库位
   private String partNo; //配件代码
   private String partName;//配件名称
   private String unitCode;//单位代码
   
   private Double inPrice;//入库不含税单价
   private Double inAmount;//入库不含税金额 IN_AMOUNT
   private String otherPartCostPrice; //
   private String otherPartCostAmount;//
   private Double costPrice;//成本价
   private Double costAmount;//
   private Float inQuantity;//入库数量
   

   
   private String allocateInNo;//
   private String partBatchNo;
   private String statusItem;
   private String recordId;
   private String flag;
   private Integer downTag;

   






@Override
public String toString() {
    return "PartAllocateInItemDto [itemId=" + itemId + ", dealerCode=" + dealerCode + ", storageCode=" + storageCode
           + ", storagePositionCode=" + storagePositionCode + ", partNo=" + partNo + ", partName=" + partName
           + ", unitCode=" + unitCode + ", inPrice=" + inPrice + ", inAmount=" + inAmount + ", otherPartCostPrice="
           + otherPartCostPrice + ", otherPartCostAmount=" + otherPartCostAmount + ", costPrice=" + costPrice
           + ", costAmount=" + costAmount + ", inQuantity=" + inQuantity + ", allocateInNo=" + allocateInNo
           + ", partBatchNo=" + partBatchNo + ", statusItem=" + statusItem + ", recordId=" + recordId + ", flag=" + flag
           + ", downTag=" + downTag + "]";
}






public Integer getDownTag() {
    return downTag;
}






public void setDownTag(Integer downTag) {
    this.downTag = downTag;
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



public String getItemId() {
    return itemId;
}



public void setItemId(String itemId) {
    this.itemId = itemId;
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

public String getUnitCode() {
    return unitCode;
}

public void setUnitCode(String unitCode) {
    this.unitCode = unitCode;
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




public String getOtherPartCostPrice() {
    return otherPartCostPrice;
}

public void setOtherPartCostPrice(String otherPartCostPrice) {
    this.otherPartCostPrice = otherPartCostPrice;
}

public String getOtherPartCostAmount() {
    return otherPartCostAmount;
}

public void setOtherPartCostAmount(String otherPartCostAmount) {
    this.otherPartCostAmount = otherPartCostAmount;
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


public Float getInQuantity() {
    return inQuantity;
}





public void setInQuantity(Float inQuantity) {
    this.inQuantity = inQuantity;
}




public String getAllocateInNo() {
    return allocateInNo;
}

public void setAllocateInNo(String allocateInNo) {
    this.allocateInNo = allocateInNo;
}

public String getPartBatchNo() {
    return partBatchNo;
}

public void setPartBatchNo(String partBatchNo) {
    this.partBatchNo = partBatchNo;
}

public String getStatusItem() {
    return statusItem;
}

public void setStatusItem(String statusItem) {
    this.statusItem = statusItem;
}

public String getRecordId() {
    return recordId;
}

public void setRecordId(String recordId) {
    this.recordId = recordId;
}
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}