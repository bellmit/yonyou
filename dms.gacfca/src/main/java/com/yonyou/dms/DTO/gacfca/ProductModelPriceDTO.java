/**
 * 
 */
package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class ProductModelPriceDTO {
	private String entityCode;//char(8)
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
	private Date downTimestamp;
	private String productCode;
	private String productName;
	private Double oemDirectivePrice;
	private Double purchasePrice;
	private Double mininumPrice;
	private Integer isValid;
	private String modelYear;
//	private Integer oilType;// 燃油类型
//	private String engineDesc;//发动机排量
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
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
	public Date getDownTimestamp() {
		return downTimestamp;
	}
	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
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
	public Double getOemDirectivePrice() {
		return oemDirectivePrice;
	}
	public void setOemDirectivePrice(Double oemDirectivePrice) {
		this.oemDirectivePrice = oemDirectivePrice;
	}
	public Double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Double getMininumPrice() {
		return mininumPrice;
	}
	public void setMininumPrice(Double mininumPrice) {
		this.mininumPrice = mininumPrice;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	
	
	
	
	
}
