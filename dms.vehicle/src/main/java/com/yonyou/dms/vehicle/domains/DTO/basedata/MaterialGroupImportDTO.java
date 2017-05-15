package com.yonyou.dms.vehicle.domains.DTO.basedata;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class MaterialGroupImportDTO extends DataImportDto  {
	
	@ExcelColumnDefine(value=1)
	@Required
	private String brandCode;
	
	@ExcelColumnDefine(value=2)
	private String brandName;
	
	@ExcelColumnDefine(value=3)
	private String seriesCode;
	
	@ExcelColumnDefine(value=4)
	private String seriesName;
	
	@ExcelColumnDefine(value=5)
	private String typeCode;
	
	@ExcelColumnDefine(value=6)
	private String typeName;
	
	@ExcelColumnDefine(value=7)
	private String isForecast;
	
	@ExcelColumnDefine(value=8)
	@Required
	private String groupCode;
	
	@ExcelColumnDefine(value=9)
	private String groupName;
	
	@ExcelColumnDefine(value=10)
	private String modelYear;
	
	@ExcelColumnDefine(value=11)
	private String standardOption;
	
	@ExcelColumnDefine(value=12)
	private String factoryOpitons;
	
	@ExcelColumnDefine(value=13)
	private String localOption;
	
	@ExcelColumnDefine(value=14)
	private String isEc;
	
	@ExcelColumnDefine(value=15)
	private String status;
	

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getIsForecast() {
		return isForecast;
	}

	public void setIsForecast(String isForecast) {
		this.isForecast = isForecast;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public String getStandardOption() {
		return standardOption;
	}

	public void setStandardOption(String standardOption) {
		this.standardOption = standardOption;
	}

	public String getFactoryOpitons() {
		return factoryOpitons;
	}

	public void setFactoryOpitons(String factoryOpitons) {
		this.factoryOpitons = factoryOpitons;
	}

	public String getLocalOption() {
		return localOption;
	}

	public void setLocalOption(String localOption) {
		this.localOption = localOption;
	}

	public String getIsEc() {
		return isEc;
	}

	public void setIsEc(String isEc) {
		this.isEc = isEc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
