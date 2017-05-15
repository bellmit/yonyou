package com.yonyou.dms.vehicle.domains.DTO.UpholsterWorker;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UpholsterWorkerDTO {
	private String soNo;

	private String upholsterCode;
	
	private String employeeName; //派工技师
	
	private String employeeNo; //技师编号
	
	private String positionCode; //工位代码
	
	private String factor; //工时系数
	
	private String checked; //检验人
	
	private String workTypeCode; //工种代码
	
	private String labourHour; //分工工时
	
	private Date roCreateDate; //开工时间
	
	private Date roCreateDate1; //预计完成时间
	
	private Date roCreateDate2; //完工日期
	
	private String finishedTag; //完工标志
	
	@SuppressWarnings("rawtypes")
	private List<Map> intentList;

	public String getLabourHour() {
		return labourHour;
	}

	public void setLabourHour(String labourHour) {
		this.labourHour = labourHour;
	}

	public Date getRoCreateDate() {
		return roCreateDate;
	}

	public Date getRoCreateDate2() {
		return roCreateDate2;
	}

	public void setRoCreateDate2(Date roCreateDate2) {
		this.roCreateDate2 = roCreateDate2;
	}

	public String getFinishedTag() {
		return finishedTag;
	}

	public void setFinishedTag(String finishedTag) {
		this.finishedTag = finishedTag;
	}

	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}

	public Date getRoCreateDate1() {
		return roCreateDate1;
	}

	public void setRoCreateDate1(Date roCreateDate1) {
		this.roCreateDate1 = roCreateDate1;
	}

	public String getWorkTypeCode() {
		return workTypeCode;
	}

	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}


	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getFactor() {
		return factor;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getUpholsterCode() {
		return upholsterCode;
	}

	public void setUpholsterCode(String upholsterCode) {
		this.upholsterCode = upholsterCode;
	}



	//private List<IntentDTO> intentList;
	
	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getIntentList() {
		return intentList;
	}

	@SuppressWarnings("rawtypes")
	public void setIntentList(List<Map> intentList) {
		this.intentList = intentList;
	}

	
}
