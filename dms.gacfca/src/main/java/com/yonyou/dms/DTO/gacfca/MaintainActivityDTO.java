package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class MaintainActivityDTO {

	private String entityCode;
	
	private String packageCode;
	
	private String packageName;
	
	private Double maintainStartday;
	
	private Double maintainEndday;
	
	private Double maintainStartmileage;
	
	private Double maintainEndmileage;
	
	private String modelCode;
	
	private String groupCode;
	
	private String modelYear;
	
	private Date downTimestamp;
	private Date beginDate;
	private Date endDate;
	
	private LinkedList<MaintainActivityLabourDTO> labourVoList;
	
	private LinkedList<MaintainActivityPartDTO> partVoList;

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Double getMaintainEndday() {
		return maintainEndday;
	}

	public void setMaintainEndday(Double maintainEndday) {
		this.maintainEndday = maintainEndday;
	}

	public Double getMaintainEndmileage() {
		return maintainEndmileage;
	}

	public void setMaintainEndmileage(Double maintainEndmileage) {
		this.maintainEndmileage = maintainEndmileage;
	}

	public Double getMaintainStartday() {
		return maintainStartday;
	}

	public void setMaintainStartday(Double maintainStartday) {
		this.maintainStartday = maintainStartday;
	}

	public Double getMaintainStartmileage() {
		return maintainStartmileage;
	}

	public void setMaintainStartmileage(Double maintainStartmileage) {
		this.maintainStartmileage = maintainStartmileage;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public LinkedList<MaintainActivityLabourDTO> getLabourVoList() {
		return labourVoList;
	}

	public void setLabourVoList(LinkedList<MaintainActivityLabourDTO> labourVoList) {
		this.labourVoList = labourVoList;
	}

	public LinkedList<MaintainActivityPartDTO> getPartVoList() {
		return partVoList;
	}

	public void setPartVoList(LinkedList<MaintainActivityPartDTO> partVoList) {
		this.partVoList = partVoList;
	}
}
