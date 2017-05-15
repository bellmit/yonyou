
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SoDecrodatePartDTO.java
*
* @Author : xukl
*
* @Date : 2016年9月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月23日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;



/**
* 销售订单装潢材料
* @author xukl
* @date 2016年9月23日
*/

public class SoDecrodatePartDTO {
    private Integer decrodatePartId;

    private Integer soNoId;

    private String dealerCode;

    private String storageCode;

    private String storagePositionCode;

    private String partNo;

    private String partName;

    private Double partQuantity;

    private Double partSalesPrice;

    private Double partSalesAmount;

    private Double discount;

    private Double afterDiscountAmount;
    
    public Double getAfterDiscountAmountPartList() {
        return afterDiscountAmountPartList;
    }

    
    public void setAfterDiscountAmountPartList(Double afterDiscountAmountPartList) {
        this.afterDiscountAmountPartList = afterDiscountAmountPartList;
        
    }

    private Double afterDiscountAmountPartList;

    private String partSequence;

    private Integer accountMode;

    private String remark;

    public Integer getDecrodatePartId() {
        return decrodatePartId;
    }

    public void setDecrodatePartId(Integer decrodatePartId) {
        this.decrodatePartId = decrodatePartId;
    }

    public Integer getSoNoId() {
        return soNoId;
    }

    public void setSoNoId(Integer soNoId) {
        this.soNoId = soNoId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
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

    public Double getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
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

    public String getPartSequence() {
        return partSequence;
    }

    public void setPartSequence(String partSequence) {
        this.partSequence = partSequence == null ? null : partSequence.trim();
    }

    public Integer getAccountMode() {
        return accountMode;
    }

    public void setAccountMode(Integer accountMode) {
        this.accountMode = accountMode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
