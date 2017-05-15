
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartBuyItemDTO.java
*
* @Author : xukl
*
* @Date : 2016年8月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月3日    xukl    1.0
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
* PartBuyItemDTO
* @author xukl
* @date 2016年8月3日
*/

public class PartBuyItemDTO extends DataImportDto{
    private Integer partBuyId;

    private String dealerCode;

    private Integer deliverItemId;

    @ExcelColumnDefine(value=3)
    @Required
    private String storageCode;

    @ExcelColumnDefine(value=4)
    private String storagePositionCode;
    
    @ExcelColumnDefine(value=1)
    @Required
    private String partNo;
    
    @ExcelColumnDefine(value=2)
    @Required
    private String partName;

    @ExcelColumnDefine(value=5)
    private String unit;

    @ExcelColumnDefine(value=6)
    @Required
    private Double inQuantity;

    @ExcelColumnDefine(value=8)
    @Required
    private Double inAmount;

    @ExcelColumnDefine(value=7)
    @Required
    private Double inPrice;

    @ExcelColumnDefine(value=9)
    @Required
    private Double inPriceTaxed;

    @ExcelColumnDefine(value=10)
    @Required
    private Double inAmountTaxed;

    private Integer fromType;

    private Integer isFinished;

    private Date finishedDate;

    private String finishedNo;

    private String oldStockInNo;
    
    //可退货数量
    private Double returnQuantity;
    
    

    
    public Double getReturnQuantity() {
        return returnQuantity;
    }

    
    public void setReturnQuantity(Double returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Integer getPartBuyId() {
        return partBuyId;
    }

    public void setPartBuyId(Integer partBuyId) {
        this.partBuyId = partBuyId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public Integer getDeliverItemId() {
        return deliverItemId;
    }

    public void setDeliverItemId(Integer deliverItemId) {
        this.deliverItemId = deliverItemId;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode == null ? null : storageCode.trim();
    }

    public String getStoragePositionCode() {
        return storagePositionCode;
    }

    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode == null ? null : storagePositionCode.trim();
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo == null ? null : partNo.trim();
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName == null ? null : partName.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Double getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Double inQuantity) {
        this.inQuantity = inQuantity;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public Double getInPrice() {
        return inPrice;
    }

    public void setInPrice(Double inPrice) {
        this.inPrice = inPrice;
    }

    public Double getInPriceTaxed() {
        return inPriceTaxed;
    }

    public void setInPriceTaxed(Double inPriceTaxed) {
        this.inPriceTaxed = inPriceTaxed;
    }

    public Double getInAmountTaxed() {
        return inAmountTaxed;
    }

    public void setInAmountTaxed(Double inAmountTaxed) {
        this.inAmountTaxed = inAmountTaxed;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getFinishedNo() {
        return finishedNo;
    }

    public void setFinishedNo(String finishedNo) {
        this.finishedNo = finishedNo == null ? null : finishedNo.trim();
    }

    public String getOldStockInNo() {
        return oldStockInNo;
    }

    public void setOldStockInNo(String oldStockInNo) {
        this.oldStockInNo = oldStockInNo == null ? null : oldStockInNo.trim();
    }
}
