
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockOutDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年9月21日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月21日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.VIN;

/**
* 整车出库
* @author DuPengXin
* @date 2016年9月21日
*/

public class StockOutDTO {
    
    private Long deliveryId; //出库ID

    private String dealerCode; //经销商代码
    
    @Required
    private Integer deliveryType; //出库类型
    
    @Required
    private String sdNo; //出库单号
    
    @Required
    private String soNo; //订单编号

    private Date deliveryDate; //订单日期

    private Double vehiclePrice; //车辆价格

    @VIN
    private String vin; //VIN
    
    @Required
    private String productCode; //产品代码
    
    @Required
    private String storageCode; //仓库代码
    
    @Required
    private String storagePositionCode; //库位代码

    private Double purchasePrice; //采购价格

    private Integer isDelivery; //是否出库
    
    private List<InspectPdiDto> pdiList;
    
    
    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getSdNo() {
        return sdNo;
    }

    public void setSdNo(String sdNo) {
        this.sdNo = sdNo == null ? null : sdNo.trim();
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo == null ? null : soNo.trim();
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(Double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin == null ? null : vin.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
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

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    public List<InspectPdiDto> getPdiList() {
        return pdiList;
    }

    public void setPdiList(List<InspectPdiDto> pdiList) {
        this.pdiList = pdiList;
    }
}
