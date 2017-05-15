
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInfoDTO.java
*
* @Author : xukl
*
* @Date : 2016年7月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月5日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.basedata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateDeserializer;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * PartInfoDTO
 * 
 * @author xukl
 * @date 2016年7月5日
 */

public class PartInfoDTO extends DataImportDto {
	@ExcelColumnDefine(value = 1)
	@Required
	private String brand;

	@ExcelColumnDefine(value = 2)
	@Required
	private String partNo;

	@ExcelColumnDefine(value = 3)
	@Required
	private String partName;

	@ExcelColumnDefine(value = 5)
	private Integer reportWay;

	@ExcelColumnDefine(value = 7)
	private String partInfixName;
	
	@ExcelColumnDefine(value = 6)
	private String partInfix;
	
	@ExcelColumnDefine(value = 10)
	private String spellCode;
	
	@ExcelColumnDefine(value = 19)
	private String unitCode;
	
	@ExcelColumnDefine(value = 20, dataType = ExcelDataType.Dict, dataCode = 1300)
	private Integer partGroupCode;
	
	@ExcelColumnDefine(value = 45, dataType = ExcelDataType.Dict, dataCode = 1278)
	private Integer isAcc;
	
	@ExcelColumnDefine(value = 24, dataType = ExcelDataType.Dict, dataCode = 1278)
	private Integer oemTag;
	
	@ExcelColumnDefine(value = 11)
	private String goodsBrands;
	
	@ExcelColumnDefine(value = 12)
	private String oemNo;
	
	@ExcelColumnDefine(value = 13)
	private String bigCategoryCode;
	
	@ExcelColumnDefine(value = 14)
	private String subCategoryCode;
	
	@ExcelColumnDefine(value = 15)
	private String thdCategoryCode;
	
	@ExcelColumnDefine(value = 17)
	private String accMode;
	
	@ExcelColumnDefine(value = 22)
	private String partModelGroupCodeSet;
	
	@ExcelColumnDefine(value = 16)
	private String minPackage;
	
	@ExcelColumnDefine(value = 18)
	private String limitNo;
	
	@ExcelColumnDefine(value = 25)
	private Double limitPrice;
	
	@ExcelColumnDefine(value = 26)
	private Double instructPrice;
	
	@ExcelColumnDefine(value = 23)
	private Double insuranctPrice;
	
	@ExcelColumnDefine(value = 31)
	private String oriProCode;
	
	@ExcelColumnDefine(value = 21, dataType = ExcelDataType.Dict, dataCode = 1366)
	private Integer partMainType;
	
	@ExcelColumnDefine(value = 28)
	private Double regularPrice;
	
	@ExcelColumnDefine(value = 32)
	private String mainOrderType;
	
	@ExcelColumnDefine(value = 33)
	private Integer maxStock;
	
	@ExcelColumnDefine(value = 34)
	private String safeStock;
	
	@ExcelColumnDefine(value = 35)
	private Integer minStock;
	
	@ExcelColumnDefine(value = 8)
	private String optionNo;
	
	@ExcelColumnDefine(value = 9)
	private String optionRelation;
	
	@ExcelColumnDefine(value = 46, dataType = ExcelDataType.Dict, dataCode = 1278)
	private Integer partStatus;
	
	@ExcelColumnDefine(value = 47, dataType = ExcelDataType.Dict, dataCode = 1278)
	private Integer isUnsafe;
	
	@ExcelColumnDefine(value = 36)
	private String leadTime;
	
	@ExcelColumnDefine(value = 50)
	private String providerCode;
	
	@ExcelColumnDefine(value = 51) 
	private String providerName;
	
	@ExcelColumnDefine(value = 38)
	private String productingArea;
	
	@ExcelColumnDefine(value = 39)
	private String quantityPerCar;
	
	@ExcelColumnDefine(value = 52)
	private String remark;
	
	@ExcelColumnDefine(value = 40)
	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private String createdAt;
	
	@ExcelColumnDefine(value = 41)
	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private String updatedAt;
	
	@ExcelColumnDefine(value = 42)
	private String unitName;
	
	@ExcelColumnDefine(value = 43, dataType = ExcelDataType.Dict, dataCode = 1278)
	private Integer boFlag;
	
	@ExcelColumnDefine(value = 44, dataType = ExcelDataType.Dict, dataCode = 1278)
	private Integer isStorageSale;
	
	@ExcelColumnDefine(value = 4)
	private String partType;
	
	@ExcelColumnDefine(value = 48, dataType = ExcelDataType.Dict, dataCode = 1278)
	private Integer isMop;
	
	@ExcelColumnDefine(value = 49, dataType = ExcelDataType.Dict, dataCode = 1278)
	private Integer isSjv;
	
	@ExcelColumnDefine(value = 37)
	private String partVehicleModel;
	
	@ExcelColumnDefine(value = 27)
	private Double claimPrice;
	
	@ExcelColumnDefine(value = 29)
	private Double urgentPrice;
	
	@ExcelColumnDefine(value = 30)
	private Double insurancePrice;

	public Double getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(Double insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public Double getUrgentPrice() {
		return urgentPrice;
	}

	public void setUrgentPrice(Double urgentPrice) {
		this.urgentPrice = urgentPrice;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getReportWay() {
		return reportWay;
	}

	public void setReportWay(Integer reportWay) {
		this.reportWay = reportWay;
	}

	public String getPartInfixName() {
		return partInfixName;
	}

	public void setPartInfixName(String partInfixName) {
		this.partInfixName = partInfixName;
	}

	public String getPartInfix() {
		return partInfix;
	}

	public void setPartInfix(String partInfix) {
		this.partInfix = partInfix;
	}

	public String getSpellCode() {
		return spellCode;
	}

	public void setSpellCode(String spellCode) {
		this.spellCode = spellCode;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Integer getPartGroupCode() {
		return partGroupCode;
	}

	public void setPartGroupCode(Integer partGroupCode) {
		this.partGroupCode = partGroupCode;
	}

	public Integer getIsAcc() {
		return isAcc;
	}

	public void setIsAcc(Integer isAcc) {
		this.isAcc = isAcc;
	}

	public Integer getOemTag() {
		return oemTag;
	}

	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}

	public String getGoodsBrands() {
		return goodsBrands;
	}

	public void setGoodsBrands(String goodsBrands) {
		this.goodsBrands = goodsBrands;
	}

	public String getOemNo() {
		return oemNo;
	}

	public void setOemNo(String oemNo) {
		this.oemNo = oemNo;
	}

	public String getBigCategoryCode() {
		return bigCategoryCode;
	}

	public void setBigCategoryCode(String bigCategoryCode) {
		this.bigCategoryCode = bigCategoryCode;
	}

	public String getSubCategoryCode() {
		return subCategoryCode;
	}

	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}

	public String getThdCategoryCode() {
		return thdCategoryCode;
	}

	public void setThdCategoryCode(String thdCategoryCode) {
		this.thdCategoryCode = thdCategoryCode;
	}

	public String getAccMode() {
		return accMode;
	}

	public void setAccMode(String accMode) {
		this.accMode = accMode;
	}

	public String getPartModelGroupCodeSet() {
		return partModelGroupCodeSet;
	}

	public void setPartModelGroupCodeSet(String partModelGroupCodeSet) {
		this.partModelGroupCodeSet = partModelGroupCodeSet;
	}

	public String getMinPackage() {
		return minPackage;
	}

	public void setMinPackage(String minPackage) {
		this.minPackage = minPackage;
	}

	public String getLimitNo() {
		return limitNo;
	}

	public void setLimitNo(String limitNo) {
		this.limitNo = limitNo;
	}

	public Double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(Double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public Double getInstructPrice() {
		return instructPrice;
	}

	public void setInstructPrice(Double instructPrice) {
		this.instructPrice = instructPrice;
	}

	public Double getInsuranctPrice() {
		return insuranctPrice;
	}

	public void setInsuranctPrice(Double insuranctPrice) {
		this.insuranctPrice = insuranctPrice;
	}

	public String getOriProCode() {
		return oriProCode;
	}

	public void setOriProCode(String oriProCode) {
		this.oriProCode = oriProCode;
	}

	public Integer getPartMainType() {
		return partMainType;
	}

	public void setPartMainType(Integer partMainType) {
		this.partMainType = partMainType;
	}

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public String getMainOrderType() {
		return mainOrderType;
	}

	public void setMainOrderType(String mainOrderType) {
		this.mainOrderType = mainOrderType;
	}

	public Integer getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(Integer maxStock) {
		this.maxStock = maxStock;
	}

	public String getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(String safeStock) {
		this.safeStock = safeStock;
	}

	public Integer getMinStock() {
		return minStock;
	}

	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}

	public String getOptionNo() {
		return optionNo;
	}

	public void setOptionNo(String optionNo) {
		this.optionNo = optionNo;
	}

	public String getOptionRelation() {
		return optionRelation;
	}

	public void setOptionRelation(String optionRelation) {
		this.optionRelation = optionRelation;
	}

	public Integer getPartStatus() {
		return partStatus;
	}

	public void setPartStatus(Integer partStatus) {
		this.partStatus = partStatus;
	}

	public Integer getIsUnsafe() {
		return isUnsafe;
	}

	public void setIsUnsafe(Integer isUnsafe) {
		this.isUnsafe = isUnsafe;
	}

	public String getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProductingArea() {
		return productingArea;
	}

	public void setProductingArea(String productingArea) {
		this.productingArea = productingArea;
	}

	public String getQuantityPerCar() {
		return quantityPerCar;
	}

	public void setQuantityPerCar(String quantityPerCar) {
		this.quantityPerCar = quantityPerCar;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getBoFlag() {
		return boFlag;
	}

	public void setBoFlag(Integer boFlag) {
		this.boFlag = boFlag;
	}

	public Integer getIsStorageSale() {
		return isStorageSale;
	}

	public void setIsStorageSale(Integer isStorageSale) {
		this.isStorageSale = isStorageSale;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public Integer getIsMop() {
		return isMop;
	}

	public void setIsMop(Integer isMop) {
		this.isMop = isMop;
	}

	public Integer getIsSjv() {
		return isSjv;
	}

	public void setIsSjv(Integer isSjv) {
		this.isSjv = isSjv;
	}

	public String getPartVehicleModel() {
		return partVehicleModel;
	}

	public void setPartVehicleModel(String partVehicleModel) {
		this.partVehicleModel = partVehicleModel;
	}

	public Double getClaimPrice() {
		return claimPrice;
	}

	public void setClaimPrice(Double claimPrice) {
		this.claimPrice = claimPrice;
	}
	
}
