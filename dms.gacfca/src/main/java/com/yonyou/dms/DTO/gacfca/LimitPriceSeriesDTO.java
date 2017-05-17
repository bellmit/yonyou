package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class LimitPriceSeriesDTO {

	private String entityCode;

	private String brandCode;

	private String seriesCode;
	
	private String repairTypeCode;

	private Double limitPriceRate;

	private Integer isValid;
	
	private Date downTimestamp;

	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public Date getDownTimestamp() {
		return downTimestamp;
	}
	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Double getLimitPriceRate() {
		return limitPriceRate;
	}
	public void setLimitPriceRate(Double limitPriceRate) {
		this.limitPriceRate = limitPriceRate;
	}
	public String getRepairTypeCode() {
		return repairTypeCode;
	}
	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
}
