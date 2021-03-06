package com.infoservice.dms.cgcsl.vo;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class RecallServiceVehicleVO extends BaseVO  {
	
	private static final long serialVersionUID = 1L;
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
