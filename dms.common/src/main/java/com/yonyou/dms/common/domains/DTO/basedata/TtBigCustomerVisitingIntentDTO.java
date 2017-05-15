
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtBigCustomerVisitingIntentDTO.java
*
* @Author : LiGaoqi
*
* @Date : 2017年1月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月5日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;



/**
* TODO description
* @author LiGaoqi
* @date 2017年1月5日
*/

public class TtBigCustomerVisitingIntentDTO {
 private Integer intendingBuyTime;
 private Integer isUpload;
 private Integer dKey;
 private Integer purchaseCount;
 
 private String dealerCode;
 
public Integer getPurchaseCount() {
    return purchaseCount;
}


public void setPurchaseCount(Integer purchaseCount) {
    this.purchaseCount = purchaseCount;
}

private String intentProduct;
 private String intentBrand;
 private String intentSeries;
 private String intentModel;
 private String intentConfig;
 private String intentColor;
 private String competitorBrand;
 private String ownedBy;
 
 private Long intentId;
 private Long itemId;

public Integer getIntendingBuyTime() {
    return intendingBuyTime;
}

public void setIntendingBuyTime(Integer intendingBuyTime) {
    this.intendingBuyTime = intendingBuyTime;
}

public Integer getIsUpload() {
    return isUpload;
}

public void setIsUpload(Integer isUpload) {
    this.isUpload = isUpload;
}

public Integer getdKey() {
    return dKey;
}

public void setdKey(Integer dKey) {
    this.dKey = dKey;
}

public String getDealerCode() {
    return dealerCode;
}

public void setDealerCode(String dealerCode) {
    this.dealerCode = dealerCode;
}

public String getIntentProduct() {
    return intentProduct;
}

public void setIntentProduct(String intentProduct) {
    this.intentProduct = intentProduct;
}

public String getIntentBrand() {
    return intentBrand;
}

public void setIntentBrand(String intentBrand) {
    this.intentBrand = intentBrand;
}

public String getIntentSeries() {
    return intentSeries;
}

public void setIntentSeries(String intentSeries) {
    this.intentSeries = intentSeries;
}

public String getIntentModel() {
    return intentModel;
}

public void setIntentModel(String intentModel) {
    this.intentModel = intentModel;
}

public String getIntentConfig() {
    return intentConfig;
}

public void setIntentConfig(String intentConfig) {
    this.intentConfig = intentConfig;
}

public String getIntentColor() {
    return intentColor;
}

public void setIntentColor(String intentColor) {
    this.intentColor = intentColor;
}

public String getCompetitorBrand() {
    return competitorBrand;
}

public void setCompetitorBrand(String competitorBrand) {
    this.competitorBrand = competitorBrand;
}

public String getOwnedBy() {
    return ownedBy;
}

public void setOwnedBy(String ownedBy) {
    this.ownedBy = ownedBy;
}

public Long getIntentId() {
    return intentId;
}

public void setIntentId(Long intentId) {
    this.intentId = intentId;
}

public Long getItemId() {
    return itemId;
}

public void setItemId(Long itemId) {
    this.itemId = itemId;
}
}
