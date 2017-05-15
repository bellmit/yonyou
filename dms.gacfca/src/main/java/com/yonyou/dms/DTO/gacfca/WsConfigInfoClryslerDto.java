package com.yonyou.dms.DTO.gacfca;

public class WsConfigInfoClryslerDto {
	
	private String dealerCode;//经销商代码 	CHAR(8)	
	private String configCode;//配置代码		VARCHAR(30)
	private String seriesCode;//车系代码	VARCHAR(30)
	private Integer purchaseCount;//购买数量	NUMERIC(6)
	private String wsNo;//批售审批单号	 CHAR(12)
	private String brandCode;//品牌代码	VARCHAR(30)
	private String modelCode;//车型代码	VARCHAR(30)
	private Long itemId;//主键ID LONG(14)
	private String colorCode; //颜色代码 VARCHAR(30)
	private Integer isResApply;//是否资源申请		NUMERIC(8)
	private String configName;
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public Integer getPurchaseCount() {
		return purchaseCount;
	}
	public void setPurchaseCount(Integer purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
	public String getWsNo() {
		return wsNo;
	}
	public void setWsNo(String wsNo) {
		this.wsNo = wsNo;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public Integer getIsResApply() {
		return isResApply;
	}
	public void setIsResApply(Integer isResApply) {
		this.isResApply = isResApply;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}

}
