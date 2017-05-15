
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InvtrMaTnceDTO.java
*
* @Author : yll
*
* @Date : 2016年9月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月14日    yll    1.0
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
* 车辆库存dto
* @author yll
* @date 2016年9月14日
*/

public class InvtrMaTnceDTO {
    private Long productId;
	private String vin;
	private String brandCode;
	private String seriesCode;
	private String modelCode;
	private String configCode;
	private String color;
	private String  engineNO;//发动机号
	private String  keyNumber;//钥匙编号
	private Integer disChargeStanard;//排放标准
	private String  exhaustQuantity;//排气量
	private String  productCode;//产品代码
	private String  productName;//产品名称
	private String  productArea;//产地
	private Date  manufactureDate;//生产日期
	private Date  factoryDate;//出厂日期
	private String remark;//备注
	private Integer  hasCertificate;//是否有合格证
	private String certificateNumber;//合格证号
	private String certificateLocus;//合格证存放地
	private Integer oemTag;
	private Integer  isDirect;//是否直销
	private String  storageCode;//仓库代码？
	private String  storagePositionCode;//库位代码
	private Integer ownStockStatus;//库存状态
	private Integer  dispatched;//配车状态
	private Integer isLock;//是否锁定
	private Integer  trafficMarStatus;//运损状态
	private Integer  isTestDrive;//是否试乘试驾
	private Integer entryType;//入库类型
	private Date   firstStockInDate;//首次入库日期
	private Date   lastStockInDate;//最后入库日期
	private String  lastStockInBy;//最后入库人
	private Integer  deliveryType;//出库类型
	private Date  firstStockOutDate;//首次出库日期
	private Date  lastStockOutDate;//最后出库日期
	private String  lastStockOutBy;//最后出库人
	private String  vsn;//车辆配置代码
	private String  purchasePrice;//采购价
	private String  oemDirectivePrice;//车厂销售指导价
	private String  directivePrice;//销售指导价
	private String  wholesaleDirectivePrice;//批售指导价格
	private List<InspectPdiDto> pdiList;
    private String  inspectPerson ;//检查人
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
	private Date inspectDate;//检查时间
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
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
	public Integer getIsDirect() {
		return isDirect;
	}
	public void setIsDirect(Integer isDirect) {
		this.isDirect = isDirect;
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
	public Integer getOwnStockStatus() {
		return ownStockStatus;
	}
	public void setOwnStockStatus(Integer ownStockStatus) {
		this.ownStockStatus = ownStockStatus;
	}
	public Integer getDispatched() {
		return dispatched;
	}
	public void setDispatched(Integer dispatched) {
		this.dispatched = dispatched;
	}
	public Integer getIsLock() {
		return isLock;
	}
	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}
	public Integer getTrafficMarStatus() {
		return trafficMarStatus;
	}
	public void setTrafficMarStatus(Integer trafficMarStatus) {
		this.trafficMarStatus = trafficMarStatus;
	}
	public Integer getIsTestDrive() {
		return isTestDrive;
	}
	public void setIsTestDrive(Integer isTestDrive) {
		this.isTestDrive = isTestDrive;
	}
	public Integer getEntryType() {
		return entryType;
	}
	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}
	public Date getFirstStockInDate() {
		return firstStockInDate;
	}
	public void setFirstStockInDate(Date firstStockInDate) {
		this.firstStockInDate = firstStockInDate;
	}
	public Date getLastStockInDate() {
		return lastStockInDate;
	}
	public void setLastStockInDate(Date lastStockInDate) {
		this.lastStockInDate = lastStockInDate;
	}
	public String getLastStockInBy() {
		return lastStockInBy;
	}
	public void setLastStockInBy(String lastStockInBy) {
		this.lastStockInBy = lastStockInBy;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public Date getFirstStockOutDate() {
		return firstStockOutDate;
	}
	public void setFirstStockOutDate(Date firstStockOutDate) {
		this.firstStockOutDate = firstStockOutDate;
	}
	public Date getLastStockOutDate() {
		return lastStockOutDate;
	}
	public void setLastStockOutDate(Date lastStockOutDate) {
		this.lastStockOutDate = lastStockOutDate;
	}
	public String getLastStockOutBy() {
		return lastStockOutBy;
	}
	public void setLastStockOutBy(String lastStockOutBy) {
		this.lastStockOutBy = lastStockOutBy;
	}
	public String getVsn() {
		return vsn;
	}
	public void setVsn(String vsn) {
		this.vsn = vsn;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getOemDirectivePrice() {
		return oemDirectivePrice;
	}
	public void setOemDirectivePrice(String oemDirectivePrice) {
		this.oemDirectivePrice = oemDirectivePrice;
	}
	public String getDirectivePrice() {
		return directivePrice;
	}
	public void setDirectivePrice(String directivePrice) {
		this.directivePrice = directivePrice;
	}
	public String getWholesaleDirectivePrice() {
		return wholesaleDirectivePrice;
	}
	public void setWholesaleDirectivePrice(String wholesaleDirectivePrice) {
		this.wholesaleDirectivePrice = wholesaleDirectivePrice;
	}
	public List<InspectPdiDto> getPdiList() {
		return pdiList;
	}
	public void setPdiList(List<InspectPdiDto> pdiList) {
		this.pdiList = pdiList;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
}
