
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtCustomerVehicleListDTO.java
*
* @Author : LiGaoqi
*
* @Date : 2016年12月21日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月21日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

/**
* TODO description
* @author LiGaoqi
* @date 2016年12月21日
*/

public class TtCustomerVehicleListDTO {
    
    private Long itemId; 
    private String customerNo;
    private String dealerCode;
    private String brandName;
    private String seriesName;
    private String modelName;
    private String remark;
    private String colorName;
    private long vehicleCount;
    private long oemTag;
    private long ownedBy;
    private long dKey;
    private long cusType;
    private long useType;
    private long trafficInsureInfo;
    private long drivingLicense;
    private long businessInsureInfo;
    private long registry;
    private long originCertificate;
    private long purchaseTax;
    private long vehicleAndVesselTax;
    private long isAssessed;
    private double assessedPrice;
    private long isSpadCreate;
    private long spadCustomerId;
    private Date purchaseDate;
    private Date ddcnCpdateDate;
    private Date productionDate;
    private Date buyDate;
    private Date annualInspectionDate;
    private Date scrapDate;
    private Date trafficInsureData;
    private Date spadUpdateDate;
    private String license;
    private String vin;
    private String engineNum;
    private String gearForm;
    private String fuelType;
    private String vehicleAllocation;
    private String procedureSpecialExplain;
    private String otherAccessory;
    private String accessoryWithVehicle;
    private String fileMessageA;
    private String fileMessageB;
    private String fileUrlmessageA;
    private String fileUrlmessageB;
    private String fileMessageC;
    private String fileUrlmessageC;
    private double mileage;
    
    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public String getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getBrandName() {
        return brandName;
    }
    
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    public String getSeriesName() {
        return seriesName;
    }
    
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
    
    public String getModelName() {
        return modelName;
    }
    
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getColorName() {
        return colorName;
    }
    
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    
    public long getVehicleCount() {
        return vehicleCount;
    }
    
    public void setVehicleCount(long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }
    
    public long getOemTag() {
        return oemTag;
    }
    
    public void setOemTag(long oemTag) {
        this.oemTag = oemTag;
    }
    
    public long getOwnedBy() {
        return ownedBy;
    }
    
    public void setOwnedBy(long ownedBy) {
        this.ownedBy = ownedBy;
    }
    
    public long getdKey() {
        return dKey;
    }
    
    public void setdKey(long dKey) {
        this.dKey = dKey;
    }
    
    public long getCusType() {
        return cusType;
    }
    
    public void setCusType(long cusType) {
        this.cusType = cusType;
    }
    
    public long getUseType() {
        return useType;
    }
    
    public void setUseType(long useType) {
        this.useType = useType;
    }
    
    public long getTrafficInsureInfo() {
        return trafficInsureInfo;
    }
    
    public void setTrafficInsureInfo(long trafficInsureInfo) {
        this.trafficInsureInfo = trafficInsureInfo;
    }
    
    public long getDrivingLicense() {
        return drivingLicense;
    }
    
    public void setDrivingLicense(long drivingLicense) {
        this.drivingLicense = drivingLicense;
    }
    
    public long getBusinessInsureInfo() {
        return businessInsureInfo;
    }
    
    public void setBusinessInsureInfo(long businessInsureInfo) {
        this.businessInsureInfo = businessInsureInfo;
    }
    
    public long getRegistry() {
        return registry;
    }
    
    public void setRegistry(long registry) {
        this.registry = registry;
    }
    
    public long getOriginCertificate() {
        return originCertificate;
    }
    
    public void setOriginCertificate(long originCertificate) {
        this.originCertificate = originCertificate;
    }
    
    public long getPurchaseTax() {
        return purchaseTax;
    }
    
    public void setPurchaseTax(long purchaseTax) {
        this.purchaseTax = purchaseTax;
    }
    
    public long getVehicleAndVesselTax() {
        return vehicleAndVesselTax;
    }
    
    public void setVehicleAndVesselTax(long vehicleAndVesselTax) {
        this.vehicleAndVesselTax = vehicleAndVesselTax;
    }
    
    public long getIsAssessed() {
        return isAssessed;
    }
    
    public void setIsAssessed(long isAssessed) {
        this.isAssessed = isAssessed;
    }
    
    public double getAssessedPrice() {
        return assessedPrice;
    }
    
    public void setAssessedPrice(double assessedPrice) {
        this.assessedPrice = assessedPrice;
    }
    
    public long getIsSpadCreate() {
        return isSpadCreate;
    }
    
    public void setIsSpadCreate(long isSpadCreate) {
        this.isSpadCreate = isSpadCreate;
    }
    
    public long getSpadCustomerId() {
        return spadCustomerId;
    }
    
    public void setSpadCustomerId(long spadCustomerId) {
        this.spadCustomerId = spadCustomerId;
    }
    
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    public Date getDdcnCpdateDate() {
        return ddcnCpdateDate;
    }
    
    public void setDdcnCpdateDate(Date ddcnCpdateDate) {
        this.ddcnCpdateDate = ddcnCpdateDate;
    }
    
    public Date getProductionDate() {
        return productionDate;
    }
    
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }
    
    public Date getBuyDate() {
        return buyDate;
    }
    
    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
    
    public Date getAnnualInspectionDate() {
        return annualInspectionDate;
    }
    
    public void setAnnualInspectionDate(Date annualInspectionDate) {
        this.annualInspectionDate = annualInspectionDate;
    }
    
    public Date getScrapDate() {
        return scrapDate;
    }
    
    public void setScrapDate(Date scrapDate) {
        this.scrapDate = scrapDate;
    }
    
    public Date getTrafficInsureData() {
        return trafficInsureData;
    }
    
    public void setTrafficInsureData(Date trafficInsureData) {
        this.trafficInsureData = trafficInsureData;
    }
    
    public Date getSpadUpdateDate() {
        return spadUpdateDate;
    }
    
    public void setSpadUpdateDate(Date spadUpdateDate) {
        this.spadUpdateDate = spadUpdateDate;
    }
    
    public String getLicense() {
        return license;
    }
    
    public void setLicense(String license) {
        this.license = license;
    }
    
    public String getVin() {
        return vin;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public String getEngineNum() {
        return engineNum;
    }
    
    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }
    
    public String getGearForm() {
        return gearForm;
    }
    
    public void setGearForm(String gearForm) {
        this.gearForm = gearForm;
    }
    
    public String getFuelType() {
        return fuelType;
    }
    
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    

    
    
    public String getVehicleAllocation() {
        return vehicleAllocation;
    }

    
    public void setVehicleAllocation(String vehicleAllocation) {
        this.vehicleAllocation = vehicleAllocation;
    }

    public String getProcedureSpecialExplain() {
        return procedureSpecialExplain;
    }
    
    public void setProcedureSpecialExplain(String procedureSpecialExplain) {
        this.procedureSpecialExplain = procedureSpecialExplain;
    }
    
    public String getOtherAccessory() {
        return otherAccessory;
    }
    
    public void setOtherAccessory(String otherAccessory) {
        this.otherAccessory = otherAccessory;
    }
    
    public String getAccessoryWithVehicle() {
        return accessoryWithVehicle;
    }
    
    public void setAccessoryWithVehicle(String accessoryWithVehicle) {
        this.accessoryWithVehicle = accessoryWithVehicle;
    }
    
    public String getFileMessageA() {
        return fileMessageA;
    }
    
    public void setFileMessageA(String fileMessageA) {
        this.fileMessageA = fileMessageA;
    }
    
    public String getFileMessageB() {
        return fileMessageB;
    }
    
    public void setFileMessageB(String fileMessageB) {
        this.fileMessageB = fileMessageB;
    }
    
    public String getFileUrlmessageA() {
        return fileUrlmessageA;
    }
    
    public void setFileUrlmessageA(String fileUrlmessageA) {
        this.fileUrlmessageA = fileUrlmessageA;
    }
    
    public String getFileUrlmessageB() {
        return fileUrlmessageB;
    }
    
    public void setFileUrlmessageB(String fileUrlmessageB) {
        this.fileUrlmessageB = fileUrlmessageB;
    }
    
    public String getFileMessageC() {
        return fileMessageC;
    }
    
    public void setFileMessageC(String fileMessageC) {
        this.fileMessageC = fileMessageC;
    }
    
    public String getFileUrlmessageC() {
        return fileUrlmessageC;
    }
    
    public void setFileUrlmessageC(String fileUrlmessageC) {
        this.fileUrlmessageC = fileUrlmessageC;
    }
    
    public double getMileage() {
        return mileage;
    }
    
    public void setMileage(double mileage) {
        this.mileage = mileage;
    }
    

}
