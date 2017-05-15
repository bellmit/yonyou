
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : ConfirmCarDTO.java
*
* @Author : LiGaoqi
*
* @Date : 2017年2月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月7日    LiGaoqi    1.0
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
* @date 2017年2月7日
*/

public class ConfirmCarAndUpdateCustomerDTO {
    //保修登记卡字段
    private Long vehicleId;
    private String customerName;
    private Integer customerType;
    private String linkedName;
    private String contactorMobile;
    private String zipCode;
    private Integer gender;
    private String zsManager;
    private Integer ctCode;
    private String certificateNo;
    private String address;
    private String eMail;
    private String vin;
    private String brand;
    private String series;
    private String model;
    private String config;
    private String productCode;
    private String color;
    private String usoNo;//隐藏字段
    private String uVin;//隐藏字段
    
    private String cusName;
    private Date birthday;
    private Integer buyReason;
    private String buyPurpose;
    private String license;
    private Date licenseDate;
    private Date salesDate;
    private Double salesMileage;
    private String customerNo;


    
    
    
    
    
    
    
    
    
    /**
     * @return the customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }









    
    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }









    /**
     * @return the licenseDate
     */
    public Date getLicenseDate() {
        return licenseDate;
    }








    
    /**
     * @param licenseDate the licenseDate to set
     */
    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }








    
    /**
     * @return the salesDate
     */
    public Date getSalesDate() {
        return salesDate;
    }








    
    /**
     * @param salesDate the salesDate to set
     */
    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }








    
    /**
     * @return the salesMileage
     */
    public Double getSalesMileage() {
        return salesMileage;
    }








    
    /**
     * @param salesMileage the salesMileage to set
     */
    public void setSalesMileage(Double salesMileage) {
        this.salesMileage = salesMileage;
    }








    /**
     * @return the license
     */
    public String getLicense() {
        return license;
    }







    
    /**
     * @param license the license to set
     */
    public void setLicense(String license) {
        this.license = license;
    }







    /**
     * @return the buyPurpose
     */
    public String getBuyPurpose() {
        return buyPurpose;
    }






    
    /**
     * @param buyPurpose the buyPurpose to set
     */
    public void setBuyPurpose(String buyPurpose) {
        this.buyPurpose = buyPurpose;
    }






    /**
     * @return the buyReason
     */
    public Integer getBuyReason() {
        return buyReason;
    }





    
    /**
     * @param buyReason the buyReason to set
     */
    public void setBuyReason(Integer buyReason) {
        this.buyReason = buyReason;
    }





    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }




    
    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }




    /**
     * @return the cusName
     */
    public String getCusName() {
        return cusName;
    }



    
    /**
     * @param cusName the cusName to set
     */
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }



    /**
     * @return the productCode
     */
    public String getProductCode() {
        return productCode;
    }


    
    /**
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    /**
     * @return the usoNo
     */
    public String getUsoNo() {
        return usoNo;
    }

    
    /**
     * @param usoNo the usoNo to set
     */
    public void setUsoNo(String usoNo) {
        this.usoNo = usoNo;
    }

    
    /**
     * @return the uVin
     */
    public String getuVin() {
        return uVin;
    }

    
    /**
     * @param uVin the uVin to set
     */
    public void setuVin(String uVin) {
        this.uVin = uVin;
    }

    public Long getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public Integer getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }
    
    public String getLinkedName() {
        return linkedName;
    }
    
    public void setLinkedName(String linkedName) {
        this.linkedName = linkedName;
    }
    
 
    
    
    /**
     * @return the contactorMobile
     */
    public String getContactorMobile() {
        return contactorMobile;
    }


    
    /**
     * @param contactorMobile the contactorMobile to set
     */
    public void setContactorMobile(String contactorMobile) {
        this.contactorMobile = contactorMobile;
    }


    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public Integer getGender() {
        return gender;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    public String getZsManager() {
        return zsManager;
    }
    
    public void setZsManager(String zsManager) {
        this.zsManager = zsManager;
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
        this.certificateNo = certificateNo;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String geteMail() {
        return eMail;
    }
    
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    public String getVin() {
        return vin;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getSeries() {
        return series;
    }
    
    public void setSeries(String series) {
        this.series = series;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getConfig() {
        return config;
    }
    
    public void setConfig(String config) {
        this.config = config;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    
    

}
