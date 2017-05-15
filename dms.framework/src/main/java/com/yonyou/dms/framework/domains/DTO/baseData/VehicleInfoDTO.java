package com.yonyou.dms.framework.domains.DTO.baseData;

public class VehicleInfoDTO {
	
	private String brandId;
	private String modelId;
	private String styleId;
	private String colorId;
	private String carModle;//车款
	public String getCarModle() {
		return carModle;
	}
	public void setCarModle(String carModle) {
		this.carModle = carModle;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

}
