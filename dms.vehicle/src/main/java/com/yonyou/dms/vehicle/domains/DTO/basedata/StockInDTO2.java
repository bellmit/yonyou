
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0private String  a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockInDTO2.java
*
* @Author : yangjie
*
* @Date : 2017年1月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月6日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.io.Serializable;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年1月6日
 */

@SuppressWarnings("serial")
public class StockInDTO2 implements Serializable {

    private String seNo;                // 入库单编号
    private String productCode;         // 产品代码
    private String vin;                 // VIN
    private String purchasePrice;       // 含税采购价格
    private String storageCode;         // 仓库
    private String storagePositionCode; // 库位代码
    private String engine;              // 发动机号
    private String exhaustQuantity;     // 排气量
    private String dischargeStandard;   // 排放标准
    private String keyNumber;           // 钥匙编号
    private String certificateNumber;   // 合格证号
    private String manufactureDate;     // 生产日期
    private String factoryDate;         // 出厂日期
    private String productingArea;      // 产地
    private String additionalCost;      // 附加成本
    private String yearModel;           // 年款
    private String remark;              // 备注
    private String shippingOrderNO;     // 发货单号
    private String shipperLicense;      // 承运车牌号
    private String shipperName;         // 承运商名称
    private String shippingDate;        // 发车日期
    private String arrivingDate;        // 预计送到日期
    private String arrivedDate;         // 实际送到日期
    private String deliverymanName;     // 送车人姓名
    private String deliverymanPhone;    // 送车人电话
    private String shippingAddress;     // 收货地址
    private String procureDate;         // 采购日期
    private String vendorCode;          // 供应单位代码
    private String vendorName;          // 供应单位名称
    private String consignerCode;       // 委托单位代码
    private String consignerName;       // 委托单位名称
    private String poNo;                // 采购订单编号
    private String vsn;                 // VSN

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }

    public String getSeNo() {
        return seNo;
    }

    public void setSeNo(String seNo) {
        this.seNo = seNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getExhaustQuantity() {
        return exhaustQuantity;
    }

    public void setExhaustQuantity(String exhaustQuantity) {
        this.exhaustQuantity = exhaustQuantity;
    }

    public String getDischargeStandard() {
        return dischargeStandard;
    }

    public void setDischargeStandard(String dischargeStandard) {
        this.dischargeStandard = dischargeStandard;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getFactoryDate() {
        return factoryDate;
    }

    public void setFactoryDate(String factoryDate) {
        this.factoryDate = factoryDate;
    }

    public String getProductingArea() {
        return productingArea;
    }

    public void setProductingArea(String productingArea) {
        this.productingArea = productingArea;
    }

    public String getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(String additionalCost) {
        this.additionalCost = additionalCost;
    }

    public String getYearModel() {
        return yearModel;
    }

    public void setYearModel(String yearModel) {
        this.yearModel = yearModel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShippingOrderNO() {
        return shippingOrderNO;
    }

    public void setShippingOrderNO(String shippingOrderNO) {
        this.shippingOrderNO = shippingOrderNO;
    }

    public String getShipperLicense() {
        return shipperLicense;
    }

    public void setShipperLicense(String shipperLicense) {
        this.shipperLicense = shipperLicense;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getArrivingDate() {
        return arrivingDate;
    }

    public void setArrivingDate(String arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public String getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(String arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    public String getDeliverymanName() {
        return deliverymanName;
    }

    public void setDeliverymanName(String deliverymanName) {
        this.deliverymanName = deliverymanName;
    }

    public String getDeliverymanPhone() {
        return deliverymanPhone;
    }

    public void setDeliverymanPhone(String deliverymanPhone) {
        this.deliverymanPhone = deliverymanPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getProcureDate() {
        return procureDate;
    }

    public void setProcureDate(String procureDate) {
        this.procureDate = procureDate;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getConsignerCode() {
        return consignerCode;
    }

    public void setConsignerCode(String consignerCode) {
        this.consignerCode = consignerCode;
    }

    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }
}
