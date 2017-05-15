
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VehicleDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月9日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.common.domains.DTO.stockmanage;

import java.util.Date;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 车辆信息
 * 
 * @author zhanshiwei
 * @date 2016年8月9日
 */

public class VehicleDTO {

    private Long vehicleId;
    @Required
    private String  vin;//vin号

    private Integer ownerId;

    private String  license;//车牌号
    @Required
    private String  engineNo;//发动机号

    private Date    productDate;//制造日期
    @Required
    private String  brandCode;//品牌
    @Required
    private String  seriesCode;//车系
    @Required
    private String  modelCode;//车型

    private String  configCode;//配置

    private String  color;//颜色

    private String  productCode;//产品代码

    private String  serviceAdvisor;//服务专员

    private String  dcrcAdvisor;//DCRC专员

    private String  insuranceAdvisor;//续保专员

    private Integer vehiclePurpose;

    private String  innerColor;//内饰颜色

    private String  gearNo;//变速箱号

    private String  keyNo;//钥匙编号

    private String  productingArea;//产地

    private Integer dischargeStandard;

    private String  remark;

    private Integer isSelfCompany;//是否本公司购车

    private String  consultant;//销售顾问

    private Double  salesMileage;//

    private Date    salesDate;//销售日期

    private Date    licenseDate;//上牌日期

    private Double  mileage;

    private Integer isChangeOdograph;

    private Date    changeDate;

    private Double  dailyAverageMileage;

    private Date    wrtBeginDate;

    private Date    wrtEndDate;

    private Double  wrtBeginMileage;

    private Double  wrtEndMileage;

    private Date    firstInDate;

    private Date    lastMaintainDate;

    private Double  lastMaintainMileage;

    private Date    lastMaintenanceDate;

    private Double  lastMaintenanceMileage;

    private Date    nextMaintenanceDate;

    private Double  nextMaintainMileage;

    private String  contactorName;//联系人

    private Integer contactorGender;//联系性别

    private String  contactorPhone;//联系电话

    private String  contactorMobile;//联系手机

    private String  contactorProvince;//联系省

    private String  contactorCity;//联系市

    private String  contactorDistrict;//联系区

    private String  contactorAddress;//邮编

    private String  contactorZipCode;//地址

    private String  contactorEmail;//联系人E_MAIL

    private String  remark2;

    private Integer recordVersion;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin == null ? null : vin.trim();
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo == null ? null : engineNo.trim();
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode == null ? null : brandCode.trim();
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode == null ? null : seriesCode.trim();
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode == null ? null : modelCode.trim();
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode == null ? null : configCode.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getServiceAdvisor() {
        return serviceAdvisor;
    }

    public void setServiceAdvisor(String serviceAdvisor) {
        this.serviceAdvisor = serviceAdvisor == null ? null : serviceAdvisor.trim();
    }

    public String getDcrcAdvisor() {
        return dcrcAdvisor;
    }

    public void setDcrcAdvisor(String dcrcAdvisor) {
        this.dcrcAdvisor = dcrcAdvisor == null ? null : dcrcAdvisor.trim();
    }

    public String getInsuranceAdvisor() {
        return insuranceAdvisor;
    }

    public void setInsuranceAdvisor(String insuranceAdvisor) {
        this.insuranceAdvisor = insuranceAdvisor == null ? null : insuranceAdvisor.trim();
    }

    public Integer getVehiclePurpose() {
        return vehiclePurpose;
    }

    public void setVehiclePurpose(Integer vehiclePurpose) {
        this.vehiclePurpose = vehiclePurpose;
    }

    public String getInnerColor() {
        return innerColor;
    }

    public void setInnerColor(String innerColor) {
        this.innerColor = innerColor == null ? null : innerColor.trim();
    }

    public String getGearNo() {
        return gearNo;
    }

    public void setGearNo(String gearNo) {
        this.gearNo = gearNo == null ? null : gearNo.trim();
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo == null ? null : keyNo.trim();
    }

    public String getProductingArea() {
        return productingArea;
    }

    public void setProductingArea(String productingArea) {
        this.productingArea = productingArea == null ? null : productingArea.trim();
    }

    public Integer getDischargeStandard() {
        return dischargeStandard;
    }

    public void setDischargeStandard(Integer dischargeStandard) {
        this.dischargeStandard = dischargeStandard;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getIsSelfCompany() {
        return isSelfCompany;
    }

    public void setIsSelfCompany(Integer isSelfCompany) {
        this.isSelfCompany = isSelfCompany;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant == null ? null : consultant.trim();
    }

    public Double getSalesMileage() {
        return salesMileage;
    }

    public void setSalesMileage(Double salesMileage) {
        this.salesMileage = salesMileage;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Integer getIsChangeOdograph() {
        return isChangeOdograph;
    }

    public void setIsChangeOdograph(Integer isChangeOdograph) {
        this.isChangeOdograph = isChangeOdograph;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Double getDailyAverageMileage() {
        return dailyAverageMileage;
    }

    public void setDailyAverageMileage(Double dailyAverageMileage) {
        this.dailyAverageMileage = dailyAverageMileage;
    }

    public Date getWrtBeginDate() {
        return wrtBeginDate;
    }

    public void setWrtBeginDate(Date wrtBeginDate) {
        this.wrtBeginDate = wrtBeginDate;
    }

    public Date getWrtEndDate() {
        return wrtEndDate;
    }

    public void setWrtEndDate(Date wrtEndDate) {
        this.wrtEndDate = wrtEndDate;
    }

    public Double getWrtBeginMileage() {
        return wrtBeginMileage;
    }

    public void setWrtBeginMileage(Double wrtBeginMileage) {
        this.wrtBeginMileage = wrtBeginMileage;
    }

    public Double getWrtEndMileage() {
        return wrtEndMileage;
    }

    public void setWrtEndMileage(Double wrtEndMileage) {
        this.wrtEndMileage = wrtEndMileage;
    }

    public Date getFirstInDate() {
        return firstInDate;
    }

    public void setFirstInDate(Date firstInDate) {
        this.firstInDate = firstInDate;
    }

    public Date getLastMaintainDate() {
        return lastMaintainDate;
    }

    public void setLastMaintainDate(Date lastMaintainDate) {
        this.lastMaintainDate = lastMaintainDate;
    }

    public Double getLastMaintainMileage() {
        return lastMaintainMileage;
    }

    public void setLastMaintainMileage(Double lastMaintainMileage) {
        this.lastMaintainMileage = lastMaintainMileage;
    }

    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public Double getLastMaintenanceMileage() {
        return lastMaintenanceMileage;
    }

    public void setLastMaintenanceMileage(Double lastMaintenanceMileage) {
        this.lastMaintenanceMileage = lastMaintenanceMileage;
    }

    public Date getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }

    public void setNextMaintenanceDate(Date nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public Double getNextMaintainMileage() {
        return nextMaintainMileage;
    }

    public void setNextMaintainMileage(Double nextMaintainMileage) {
        this.nextMaintainMileage = nextMaintainMileage;
    }

    public String getContactorName() {
        return contactorName;
    }

    public void setContactorName(String contactorName) {
        this.contactorName = contactorName == null ? null : contactorName.trim();
    }

    public Integer getContactorGender() {
        return contactorGender;
    }

    public void setContactorGender(Integer contactorGender) {
        this.contactorGender = contactorGender;
    }

    public String getContactorPhone() {
        return contactorPhone;
    }

    public void setContactorPhone(String contactorPhone) {
        this.contactorPhone = contactorPhone == null ? null : contactorPhone.trim();
    }

    public String getContactorMobile() {
        return contactorMobile;
    }

    public void setContactorMobile(String contactorMobile) {
        this.contactorMobile = contactorMobile == null ? null : contactorMobile.trim();
    }

    public String getContactorProvince() {
        return contactorProvince;
    }

    public void setContactorProvince(String contactorProvince) {
        this.contactorProvince = contactorProvince == null ? null : contactorProvince.trim();
    }

    public String getContactorCity() {
        return contactorCity;
    }

    public void setContactorCity(String contactorCity) {
        this.contactorCity = contactorCity == null ? null : contactorCity.trim();
    }

    public String getContactorDistrict() {
        return contactorDistrict;
    }

    public void setContactorDistrict(String contactorDistrict) {
        this.contactorDistrict = contactorDistrict == null ? null : contactorDistrict.trim();
    }

    public String getContactorAddress() {
        return contactorAddress;
    }

    public void setContactorAddress(String contactorAddress) {
        this.contactorAddress = contactorAddress == null ? null : contactorAddress.trim();
    }

    public String getContactorZipCode() {
        return contactorZipCode;
    }

    public void setContactorZipCode(String contactorZipCode) {
        this.contactorZipCode = contactorZipCode == null ? null : contactorZipCode.trim();
    }

    public String getContactorEmail() {
        return contactorEmail;
    }

    public void setContactorEmail(String contactorEmail) {
        this.contactorEmail = contactorEmail == null ? null : contactorEmail.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
}
