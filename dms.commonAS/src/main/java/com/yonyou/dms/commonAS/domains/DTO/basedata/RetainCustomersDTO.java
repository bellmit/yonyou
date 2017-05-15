
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : RetainCustomersDTO.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年8月26日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月26日    zhanshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.commonAS.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 保有客户DTO
 * 
 * @author zhanshiwei
 * @date 2016年8月26日
 */

public class RetainCustomersDTO {

    private Long    vehicleId;

    private Long    ownerId;
    @Required
    private String  vin;              // vin号

    private String  license;          // 车牌号
    
    private String  engineNo;         // 发动机号

    private Date    productDate;      // 制造日期

    private String  brandCode;        // 品牌
    @Required
    private String  seriesCode;       // 车系
    @Required
    private String  modelCode;        // 车型
    private String  configCode;       // 配置

    private String  color;            // 颜色

    private String  productCode;      // 产品代码

    private String  serviceAdvisor;   // 服务专员

    private String  dcrcAdvisor;      // DCRC专员

    private String  insuranceAdvisor; // 续保专员

    private Integer vehiclePurpose;

    private String  innerColor;       // 内饰颜色

    private String  gearNo;           // 变速箱号

    private String  keyNo;            // 钥匙编号

    private String  productingArea;   // 产地

    private Integer dischargeStandard;

    private String  remark;

    private Integer isSelfCompany;    // 是否本公司购车
    
    private String  consultant;       // 销售顾问

    private Double  salesMileage;     //
    
    private Date    salesDate;        // 销售日期

    private Date    licenseDate;      // 上牌日期

    private Double  mileage;
    private Double  changeMileage;

    private Integer isChangeOdograph;

    public Double getChangeMileage() {
        return changeMileage;
    }

    public void setChangeMileage(Double changeMileage) {
        this.changeMileage = changeMileage;
    }

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

    private String  contactorName;         // 联系人

    private Integer contactorGender;       // 联系性别

    private String  contactorPhone;        // 联系电话

    private String  contactorMobile;       // 联系手机

    private String  contactorProvince;     // 联系省

    private String  contactorCity;         // 联系市

    private String  contactorDistrict;     // 联系区

    private String  contactorAddress;      // 邮编

    private String  contactorZipCode;      // 地址

    private String  contactorEmail;        // 联系人E_MAIL

    private String  remark2;

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
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

    private String  ownerNo;       // 车主编号
    @Required
    private String  ownerName;     // 车主
    private String  phone;         // 电话
    private String  mobile;        // 手机
    @Required
    private String  province;      // 省份
    @Required
    private String  city;          // 城市
    @Required
    private String  district;      // 区县
    @Required
    private String  address;       // 地址
    @Required
    private String  zipCode;       // 邮编
    @Required
    private Integer ownerProperty; // 车主性质
    private Integer gender;        // 性别
    private Integer ctCode;        // 证件类型代码
    private String  certificateNo; // 证件号码
    private String  eMail;         // E_MAIL
    private Integer familyIncome;  // 月收入
    private Integer ownerMarriage; // 婚姻状况
    private Integer eduLevel;      // 学历
    private String  hobby;         // 爱好
    private Date    birthday;      // 出生日期

    public String getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo == null ? null : ownerNo.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public Integer getOwnerProperty() {
        return ownerProperty;
    }

    public void setOwnerProperty(Integer ownerProperty) {
        this.ownerProperty = ownerProperty;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getCtCode() {
        return ctCode;
    }

    public void setCtCode(Integer ctCode) {
        this.ctCode = ctCode;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }

    public Integer getFamilyIncome() {
        return familyIncome;
    }

    public void setFamilyIncome(Integer familyIncome) {
        this.familyIncome = familyIncome;
    }

    public Integer getOwnerMarriage() {
        return ownerMarriage;
    }

    public void setOwnerMarriage(Integer ownerMarriage) {
        this.ownerMarriage = ownerMarriage;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    private Long    customerId;

    private String  customerNo;         // 客户编号
    
    private Integer cusSource;          // 客户来源
    private Integer mediaType;          // 信息渠道
    
    private Integer buyPurpose;         // 购车目的
    
    private String  buyReason;          // 购车因素

    private Integer isFirstBuy;         // 是否首次购车

    private String  contractNo;         // 合同编号

    private Date    stockOutDate;       // 出库日期

    private Date    insuranceExpireDate;// 保险到期

    private Double  vehiclePrice;       // 车辆价格

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public Integer getCusSource() {
        return cusSource;
    }

    public void setCusSource(Integer cusSource) {
        this.cusSource = cusSource;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getBuyPurpose() {
        return buyPurpose;
    }

    public void setBuyPurpose(Integer buyPurpose) {
        this.buyPurpose = buyPurpose;
    }

    public String getBuyReason() {
        return buyReason;
    }

    public void setBuyReason(String buyReason) {
        this.buyReason = buyReason == null ? null : buyReason.trim();
    }

    public Integer getIsFirstBuy() {
        return isFirstBuy;
    }

    public void setIsFirstBuy(Integer isFirstBuy) {
        this.isFirstBuy = isFirstBuy;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public Date getStockOutDate() {
        return stockOutDate;
    }

    public void setStockOutDate(Date stockOutDate) {
        this.stockOutDate = stockOutDate;
    }

    public Date getInsuranceExpireDate() {
        return insuranceExpireDate;
    }

    public void setInsuranceExpireDate(Date insuranceExpireDate) {
        this.insuranceExpireDate = insuranceExpireDate;
    }

    public Double getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(Double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }
    
    private List<String> buyReasonList;

    public List<String> getBuyReasonList() {
        return buyReasonList;
    }

    public void setBuyReasonList(List<String> buyReasonList) {
        this.buyReasonList = buyReasonList;
    }

}
