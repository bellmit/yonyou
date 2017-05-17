package com.yonyou.dms.DTO.gacfca;


public class RecallServiceVehicleDTO {
	
	private Integer goupNo;//组合编号
	private String brandCode;//品牌
	private String seriesCode;//车系
	private String modelCode;//车型;
	private String recallNo;//召回编号
	public Integer getGoupNo() {
		return goupNo;
	}
	public void setGoupNo(Integer goupNo) {
		this.goupNo = goupNo;
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
	public String getRecallNo() {
		return recallNo;
	}
	public void setRecallNo(String recallNo) {
		this.recallNo = recallNo;
	}
}
