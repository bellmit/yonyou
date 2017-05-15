package com.infoservice.dms.cgcsl.vo;

public class ProductGroupVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 22222222222222221L;
	private String brandCode;
	private String brandName;
	private String seriesCode;
	private String seriesName;
	private String modelCode;
	private String modelName;
	private String configCode;
	private String configName;
	private String colorCode;
	private String colorName;
	private Integer brandStatus;
	private Integer seriesStatus;
	private Integer modelStatus;
	/*
	 * wjs 2016-01-05
	 */
	private Integer oilType;// 燃油类型
	private String engineDesc;// 发动机排量

	/**
	 * @return the oilType
	 */
	public Integer getOilType() {
		return oilType;
	}

	/**
	 * @param oilType
	 *            the oilType to set
	 */
	public void setOilType(Integer oilType) {
		this.oilType = oilType;
	}

	/**
	 * @return the engineDesc
	 */
	public String getEngineDesc() {
		return engineDesc;
	}

	/**
	 * @param engineDesc
	 *            the engineDesc to set
	 */
	public void setEngineDesc(String engineDesc) {
		this.engineDesc = engineDesc;
	}

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

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public Integer getBrandStatus() {
		return brandStatus;
	}

	public void setBrandStatus(Integer brandStatus) {
		this.brandStatus = brandStatus;
	}

	public Integer getSeriesStatus() {
		return seriesStatus;
	}

	public void setSeriesStatus(Integer seriesStatus) {
		this.seriesStatus = seriesStatus;
	}

	public Integer getModelStatus() {
		return modelStatus;
	}

	public void setModelStatus(Integer modelStatus) {
		this.modelStatus = modelStatus;
	}

}
