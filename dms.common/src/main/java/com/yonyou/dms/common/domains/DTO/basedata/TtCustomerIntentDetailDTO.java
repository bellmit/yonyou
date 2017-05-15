
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtCustomerIntentDetailDTO.java
*
* @Author : LiGaoqi
*
* @Date : 2016年12月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月23日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

/**
* TODO description
* @author LiGaoqi
* @date 2016年12月23日
*/

public class TtCustomerIntentDetailDTO {
    private long itemId;
    private long intentId;
    private long purchaseCount;
    private long intendingBuyTime;
    private double quotedAmount;
    private double sugRetailPrice;
    private double wsIntentPrice;
    private double wsDiscountAmount;
    private double wsDiscountRate;
    private double depositAmount;
    private long isUpload;
    private long ownedBy;
    private long dDey;
    private long isMainModel;
    private long ver;
    private long isEcoIntentModel;
    private String dealerCode;
    private String intentProduct;
    private String intentBrand;
    private String intentSeries;
    private String intentModel;
    private String intentConfig;
    private String intentColor;
    private String competitorBrand;
    private String quotedRemark;
    private String catCode;
    private String competitorSeries;
    private String chooseReason;
    private String otherRequirements;
    private String retailFinance;
    private String ecOrderNo;
    private Date salesQuoteDate;
    private Date wsSupplyDate;
    private Date ddcnUpdateDate;
    private Date determinedTime;
    
    public long getItemId() {
        return itemId;
    }
    
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    
    public long getIntentId() {
        return intentId;
    }
    
    public void setIntentId(long intentId) {
        this.intentId = intentId;
    }
    
    public long getPurchaseCount() {
        return purchaseCount;
    }
    
    public void setPurchaseCount(long purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
    
    public long getIntendingBuyTime() {
        return intendingBuyTime;
    }
    
    public void setIntendingBuyTime(long intendingBuyTime) {
        this.intendingBuyTime = intendingBuyTime;
    }
    
    public double getQuotedAmount() {
        return quotedAmount;
    }
    
    public void setQuotedAmount(double quotedAmount) {
        this.quotedAmount = quotedAmount;
    }
    
    public double getSugRetailPrice() {
        return sugRetailPrice;
    }
    
    public void setSugRetailPrice(double sugRetailPrice) {
        this.sugRetailPrice = sugRetailPrice;
    }
    
    public double getWsIntentPrice() {
        return wsIntentPrice;
    }
    
    public void setWsIntentPrice(double wsIntentPrice) {
        this.wsIntentPrice = wsIntentPrice;
    }
    
    public double getWsDiscountAmount() {
        return wsDiscountAmount;
    }
    
    public void setWsDiscountAmount(double wsDiscountAmount) {
        this.wsDiscountAmount = wsDiscountAmount;
    }
    
    public double getWsDiscountRate() {
        return wsDiscountRate;
    }
    
    public void setWsDiscountRate(double wsDiscountRate) {
        this.wsDiscountRate = wsDiscountRate;
    }
    
    public double getDepositAmount() {
        return depositAmount;
    }
    
    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }
    
    public long getIsUpload() {
        return isUpload;
    }
    
    public void setIsUpload(long isUpload) {
        this.isUpload = isUpload;
    }
    
    public long getOwnedBy() {
        return ownedBy;
    }
    
    public void setOwnedBy(long ownedBy) {
        this.ownedBy = ownedBy;
    }
    
    public long getdDey() {
        return dDey;
    }
    
    public void setdDey(long dDey) {
        this.dDey = dDey;
    }
    
    public long getIsMainModel() {
        return isMainModel;
    }
    
    public void setIsMainModel(long isMainModel) {
        this.isMainModel = isMainModel;
    }
    
    public long getVer() {
        return ver;
    }
    
    public void setVer(long ver) {
        this.ver = ver;
    }
    
    public long getIsEcoIntentModel() {
        return isEcoIntentModel;
    }
    
    public void setIsEcoIntentModel(long isEcoIntentModel) {
        this.isEcoIntentModel = isEcoIntentModel;
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
    
    public String getQuotedRemark() {
        return quotedRemark;
    }
    
    public void setQuotedRemark(String quotedRemark) {
        this.quotedRemark = quotedRemark;
    }
    
    public String getCatCode() {
        return catCode;
    }
    
    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }
    
    public String getCompetitorSeries() {
        return competitorSeries;
    }
    
    public void setCompetitorSeries(String competitorSeries) {
        this.competitorSeries = competitorSeries;
    }
    
    public String getChooseReason() {
        return chooseReason;
    }
    
    public void setChooseReason(String chooseReason) {
        this.chooseReason = chooseReason;
    }
    
    public String getOtherRequirements() {
        return otherRequirements;
    }
    
    public void setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }
    
    public String getRetailFinance() {
        return retailFinance;
    }
    
    public void setRetailFinance(String retailFinance) {
        this.retailFinance = retailFinance;
    }
    
    public String getEcOrderNo() {
        return ecOrderNo;
    }
    
    public void setEcOrderNo(String ecOrderNo) {
        this.ecOrderNo = ecOrderNo;
    }
    
    public Date getSalesQuoteDate() {
        return salesQuoteDate;
    }
    
    public void setSalesQuoteDate(Date salesQuoteDate) {
        this.salesQuoteDate = salesQuoteDate;
    }
    
    public Date getWsSupplyDate() {
        return wsSupplyDate;
    }
    
    public void setWsSupplyDate(Date wsSupplyDate) {
        this.wsSupplyDate = wsSupplyDate;
    }
    
    public Date getDdcnUpdateDate() {
        return ddcnUpdateDate;
    }
    
    public void setDdcnUpdateDate(Date ddcnUpdateDate) {
        this.ddcnUpdateDate = ddcnUpdateDate;
    }
    
    public Date getDeterminedTime() {
        return determinedTime;
    }
    
    public void setDeterminedTime(Date determinedTime) {
        this.determinedTime = determinedTime;
    }
    

}
