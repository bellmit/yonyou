package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;

public class VsStockEntryItemVO {
	
	private String entityCode;//char(8)
	private String vin;//VARCHAR(17)
	private String remark;//VARCHAR(300)
	private Date  inspectionDate ;                   //INSPECTION_DATE  对应了下端的DOWN_TIMESTAMP 上端的验收日期
	private String inspectionPerson;
	private String brand;
	private String series;
	private String model;
	private String color;
	private Integer inspectionResult;// 入库验收结果
	private Integer pdiResult;//PDI检查结果
	
	private LinkedList<VsStockDetailListVO> vsStockDetialList;
	
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getInspectionDate() {
		return inspectionDate;
	}
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	public String getInspectionPerson() {
		return inspectionPerson;
	}
	public void setInspectionPerson(String inspectionPerson) {
		this.inspectionPerson = inspectionPerson;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getInspectionResult() {
		return inspectionResult;
	}
	public void setInspectionResult(Integer inspectionResult) {
		this.inspectionResult = inspectionResult;
	}
	public Integer getPdiResult() {
		return pdiResult;
	}
	public void setPdiResult(Integer pdiResult) {
		this.pdiResult = pdiResult;
	}
	public LinkedList<VsStockDetailListVO> getVsStockDetialList() {
		return vsStockDetialList;
	}
	public void setVsStockDetialList(LinkedList<VsStockDetailListVO> vsStockDetialList) {
		this.vsStockDetialList = vsStockDetialList;
	}
	

}
