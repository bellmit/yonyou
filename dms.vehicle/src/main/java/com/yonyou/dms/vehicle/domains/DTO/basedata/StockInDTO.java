
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : StockInDTO.java
 *
 * @Author : yll
 *
 * @Date : 2016年9月18日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月18日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;

/**
 * 整车入库dto
 * 
 * @author yll
 * @date 2016年9月18日
 */

public class StockInDTO {

    private Long                   entryId;
    private Integer                entryType;          // 入库类型
    private String                 seNo;               // 入库单编号
    private String                 vin;
    private String                 brandCode;
    private String                 seriesCode;
    private String                 modelCode;
    private String                 configCode;
    private String                 color;
    private String                 storageCode;        // 仓库代码？
    private String                 storagePositionCode;// 库位代码
    private String                 engineNO;           // 发动机号
    private String                 keyNumber;          // 钥匙编号
    private Integer                disChargeStanard;   // 排放标准
    private String                 exhaustQuantity;    // 排气量
    private String                 productCode;        // 产品代码
    private String                 productName;        // 产品名称
    private String                 productArea;        // 产地
    private Date                   manufactureDate;    // 生产日期
    private Date                   factoryDate;        // 出厂日期
    private String                 remark;             // 备注
    private Integer                hasCertificate;     // 是否有合格证
    private String                 certificateNumber;  // 合格证号
    private String                 certificateLocus;   // 合格证存放地
    private Integer                isEntry;            // 是否入库
    private Date                   finishedDate;       // 入帐日期
    private String                 finishedBy;         // 入帐人
    private Integer                oemTag;             //
    private Integer                inspectionConsigned;// 是否代验收
    private Integer                isDirect;           // 是否直销
    private String                 poNo;               // 采购单号
    private String                 purchasePrice;      // 采购价
    private Date                   purchaseDate;       // 采购日期
    private Date                   shippingDate;       // 发车日期
    private Date                   arrivingDate;       // 预计送到日期
    private String                 deliveryType;       // 运送方式
    private String                 vendorCode;         // 供应商代码
    private String                 vendorName;         // 供应商名称
    private String                 shipperLicense;     // 承运车牌号
    private String                 shipperName;        // 承运商名称
    private String                 deliverymanName;    // 送车人姓名
    private String                 deliverymanPhone;   // 送车人电话
    private String                 shippingAdderess;   // 收货地址
    private String                 shippingOrderNO;    // 发货单号
    private Date                   arriveDate;         // 实际送到日期
    private String                 consignerCode;      // 委托单位代码
    private String                 sendEntityCode;     // 调拨方代码
    private Long                   vsShippingId;       // 货运单id
    private Integer                inspectionResult;   // 验收结果
    private Integer                trafficMarStatus;   // 运损状态
    private String                 inspectionBY;       // 验收人
    private List<InspectionMarDTO> marList;
    private List<InspectPdiDto>    pdiList;
    private String                 entryIds;
    private String                 inspectPerson;      // 检查人
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date                   inspectDate;        // 检查时间
    // 文件上传的ID
    private String                 dmsFileIds;

    public String getDmsFileIds() {
        return dmsFileIds;
    }

    public void setDmsFileIds(String dmsFileIds) {
        this.dmsFileIds = dmsFileIds;
    }

    public Integer getEntryType() {
        return entryType;
    }

    public String getEntryIds() {
        return entryIds;
    }

    public void setEntryIds(String entryIds) {
        this.entryIds = entryIds;
    }

    public void setEntryType(Integer entryType) {
        this.entryType = entryType;
    }

    public String getSeNo() {
        return seNo;
    }

    public void setSeNo(String seNo) {
        this.seNo = seNo;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getEngineNO() {
        return engineNO;
    }

    public void setEngineNO(String engineNO) {
        this.engineNO = engineNO;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public Integer getDisChargeStanard() {
        return disChargeStanard;
    }

    public void setDisChargeStanard(Integer disChargeStanard) {
        this.disChargeStanard = disChargeStanard;
    }

    public String getExhaustQuantity() {
        return exhaustQuantity;
    }

    public void setExhaustQuantity(String exhaustQuantity) {
        this.exhaustQuantity = exhaustQuantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCertificateLocus() {
        return certificateLocus;
    }

    public void setCertificateLocus(String certificateLocus) {
        this.certificateLocus = certificateLocus;
    }

    public Integer getIsEntry() {
        return isEntry;
    }

    public void setIsEntry(Integer isEntry) {
        this.isEntry = isEntry;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getFinishedBy() {
        return finishedBy;
    }

    public void setFinishedBy(String finishedBy) {
        this.finishedBy = finishedBy;
    }

    public Integer getOemTag() {
        return oemTag;
    }

    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }

    public Integer getInspectionConsigned() {
        return inspectionConsigned;
    }

    public void setInspectionConsigned(Integer inspectionConsigned) {
        this.inspectionConsigned = inspectionConsigned;
    }

    public Integer getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(Integer isDirect) {
        this.isDirect = isDirect;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Date getArrivingDate() {
        return arrivingDate;
    }

    public void setArrivingDate(Date arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
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

    public String getShippingAdderess() {
        return shippingAdderess;
    }

    public void setShippingAdderess(String shippingAdderess) {
        this.shippingAdderess = shippingAdderess;
    }

    public String getShippingOrderNO() {
        return shippingOrderNO;
    }

    public void setShippingOrderNO(String shippingOrderNO) {
        this.shippingOrderNO = shippingOrderNO;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getConsignerCode() {
        return consignerCode;
    }

    public void setConsignerCode(String consignerCode) {
        this.consignerCode = consignerCode;
    }

    public String getSendEntityCode() {
        return sendEntityCode;
    }

    public void setSendEntityCode(String sendEntityCode) {
        this.sendEntityCode = sendEntityCode;
    }

    public List<InspectPdiDto> getPdiList() {
        return pdiList;
    }

    public void setPdiList(List<InspectPdiDto> pdiList) {
        this.pdiList = pdiList;
    }

    public List<InspectionMarDTO> getMarList() {
        return marList;
    }

    public void setMarList(List<InspectionMarDTO> marList) {
        this.marList = marList;
    }

    public Long getVsShippingId() {
        return vsShippingId;
    }

    public void setVsShippingId(Long vsShippingId) {
        this.vsShippingId = vsShippingId;
    }

    public Integer getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(Integer inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public Integer getTrafficMarStatus() {
        return trafficMarStatus;
    }

    public void setTrafficMarStatus(Integer trafficMarStatus) {
        this.trafficMarStatus = trafficMarStatus;
    }

    public String getInspectionBY() {
        return inspectionBY;
    }

    public void setInspectionBY(String inspectionBY) {
        this.inspectionBY = inspectionBY;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getInspectPerson() {
        return inspectPerson;
    }

    public void setInspectPerson(String inspectPerson) {
        this.inspectPerson = inspectPerson;
    }

    public Date getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }

}
