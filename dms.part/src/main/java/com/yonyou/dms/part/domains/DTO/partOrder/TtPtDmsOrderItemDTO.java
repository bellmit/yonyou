
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : TtPtDmsOrderItemDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月18日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.partOrder;

import java.util.Date;

/**
 * @author zhanshiwei
 * @date 2017年4月18日
 */

public class TtPtDmsOrderItemDTO {

    private Integer dKey;
    private String  entityCode;
    private Long    createBy;
    private Integer ver;
    private Integer orderCancelStatus;
    private Date    createDate;
    private Double  unitPrice;
    private Long    itemId;
    private String  partNo;
    private Date    updateDate;
    private String  storageCode;
    private Float   quotaCount;
    private Integer isLackGoods;
    private Float   count;
    private String  unitCode;
    private String  orderNo;
    private String  partName;
    private Integer orderItemStatus;
    private Long    updateBy;
    private Float   ongoingCount;
    private Double  totalPrices;
    private Float   signatureCount;
    private Float   delayCount;
    private String  modelCode;
    private String  arktx;
    private Double  netwr;
    private Double  total;
    private Double  opnetwr;
    private Double  optotal;
    private Integer hasChange;
    private Integer isStock;
    private Double  noTaxPrice;
    private Double  singleDiscount;
    private Float   minPackage;
    private String  shortId;
    private String  detailRemark;

    public Integer getdKey() {
        return dKey;
    }

    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public void setDKey(Integer dKey) {
        this.dKey = dKey;
    }

    public Integer getDKey() {
        return this.dKey;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getVer() {
        return this.ver;
    }

    public void setOrderCancelStatus(Integer orderCancelStatus) {
        this.orderCancelStatus = orderCancelStatus;
    }

    public Integer getOrderCancelStatus() {
        return this.orderCancelStatus;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPartNo() {
        return this.partNo;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageCode() {
        return this.storageCode;
    }

    public void setQuotaCount(Float quotaCount) {
        this.quotaCount = quotaCount;
    }

    public Float getQuotaCount() {
        return this.quotaCount;
    }

    public void setIsLackGoods(Integer isLackGoods) {
        this.isLackGoods = isLackGoods;
    }

    public Integer getIsLackGoods() {
        return this.isLackGoods;
    }

    public Float getCount() {
        return count;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setOrderItemStatus(Integer orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public Integer getOrderItemStatus() {
        return this.orderItemStatus;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setOngoingCount(Float ongoingCount) {
        this.ongoingCount = ongoingCount;
    }

    public Float getOngoingCount() {
        return this.ongoingCount;
    }

    public void setTotalPrices(Double totalPrices) {
        this.totalPrices = totalPrices;
    }

    public Double getTotalPrices() {
        return this.totalPrices;
    }

    public void setSignatureCount(Float signatureCount) {
        this.signatureCount = signatureCount;
    }

    public Float getSignatureCount() {
        return this.signatureCount;
    }

    public void setDelayCount(Float delayCount) {
        this.delayCount = delayCount;
    }

    public Float getDelayCount() {
        return this.delayCount;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelCode() {
        return this.modelCode;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public String getArktx() {
        return arktx;
    }

    public void setArktx(String arktx) {
        this.arktx = arktx;
    }

    public Integer getHasChange() {
        return hasChange;
    }

    public void setHasChange(Integer hasChange) {
        this.hasChange = hasChange;
    }

    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
    }

    public Double getNetwr() {
        return netwr;
    }

    public void setNetwr(Double netwr) {
        this.netwr = netwr;
    }

    public Double getNoTaxPrice() {
        return noTaxPrice;
    }

    public void setNoTaxPrice(Double noTaxPrice) {
        this.noTaxPrice = noTaxPrice;
    }

    public Double getOpnetwr() {
        return opnetwr;
    }

    public void setOpnetwr(Double opnetwr) {
        this.opnetwr = opnetwr;
    }

    public Double getOptotal() {
        return optotal;
    }

    public void setOptotal(Double optotal) {
        this.optotal = optotal;
    }

    public Double getSingleDiscount() {
        return singleDiscount;
    }

    public void setSingleDiscount(Double singleDiscount) {
        this.singleDiscount = singleDiscount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Float getMinPackage() {
        return minPackage;
    }

    public void setMinPackage(Float minPackage) {
        this.minPackage = minPackage;
    }
}
