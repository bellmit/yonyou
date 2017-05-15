package com.yonyou.dms.vehicle.domains.DTO.activityManage;

import java.sql.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class RecallActivityImportDTO extends DataImportDto{
	
	@ExcelColumnDefine(value = 1)
	@Required
	private String vin;
	
	@ExcelColumnDefine(value = 2)
	@Required
	private String dutyDealer;
		
	private String recallNo;
	private Integer inportType;
	private Integer inportClass;
	private Long createBy;
	private Date createDate;
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getDutyDealer() {
		return dutyDealer;
	}
	public void setDutyDealer(String dutyDealer) {
		this.dutyDealer = dutyDealer;
	}
	public String getRecallNo() {
		return recallNo;
	}
	public void setRecallNo(String recallNo) {
		this.recallNo = recallNo;
	}
	public Integer getInportType() {
		return inportType;
	}
	public void setInportType(Integer inportType) {
		this.inportType = inportType;
	}
	public Integer getInportClass() {
		return inportClass;
	}
	public void setInportClass(Integer inportClass) {
		this.inportClass = inportClass;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
