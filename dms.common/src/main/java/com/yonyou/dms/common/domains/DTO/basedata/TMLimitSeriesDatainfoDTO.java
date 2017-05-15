package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class TMLimitSeriesDatainfoDTO {

	 private String dealerCode;//经销商代码
	 
	 private Integer itemId;//序号
	 
	 private String brandCode;//品牌代码
	 
	 private String seriesCode;//车系代码
     
	 private String repairTypeCode;//维修类型代码
	 
	 private Double limitPriceRate;//配件限价浮动比例
	 
	 private Integer isValid;//是否有效
	 
	 private Integer oemTag;//是否oem下方法
	 
	 private Integer dKey;//
	 
	 private Date downTimeStamp;//

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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

	public String getRepairTypeCode() {
		return repairTypeCode;
	}

	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getOemTag() {
		return oemTag;
	}

	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}

	public Integer getdKey() {
		return dKey;
	}

	public void setdKey(Integer dKey) {
		this.dKey = dKey;
	}

	public Date getDownTimeStamp() {
		return downTimeStamp;
	}

	public void setDownTimeStamp(Date downTimeStamp) {
		this.downTimeStamp = downTimeStamp;
	}

	public Double getLimitPriceRate() {
		return limitPriceRate;
	}

	public void setLimitPriceRate(Double limitPriceRate) {
		this.limitPriceRate = limitPriceRate;
	}
	 
}
