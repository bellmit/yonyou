
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : VehicleShippingDetailVO.java
*
* @Author : yangjie
*
* @Date : 2017年1月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月17日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年1月17日
 */

public class VehicleShippingDetailDto {

    private static final long serialVersionUID = 1L;
    private String            shippingOrderNo;
    private String            productCode;
    private String            engineNo;
    private String            keyNumber;
    private Integer           hasCertificate;
    private String            certificateNumber;
    private String            productingAreaCode;
    private String            productingAreaName;
    private String            vin;
    private Date              manufactureDate;
    private Date              factoryDate;
    private Double            vehiclePrice;
    private String            modelYear;

    public String getShippingOrderNo() {
        return shippingOrderNo;
    }

    public void setShippingOrderNo(String shippingOrderNo) {
        this.shippingOrderNo = shippingOrderNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public Integer getHasCertificate() {
        return hasCertificate;
    }

    public void setHasCertificate(Integer hasCertificate) {
        this.hasCertificate = hasCertificate;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getProductingAreaCode() {
        return productingAreaCode;
    }

    public void setProductingAreaCode(String productingAreaCode) {
        this.productingAreaCode = productingAreaCode;
    }

    public String getProductingAreaName() {
        return productingAreaName;
    }

    public void setProductingAreaName(String productingAreaName) {
        this.productingAreaName = productingAreaName;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Date getFactoryDate() {
        return factoryDate;
    }

    public void setFactoryDate(Date factoryDate) {
        this.factoryDate = factoryDate;
    }

    public Double getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(Double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
