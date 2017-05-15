package com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TmpWrClaimmonthDTO extends  DataImportDto{
	@ExcelColumnDefine(value = 4)
	@Required
	private Date startDate;
	
	@ExcelColumnDefine(value = 2)
	@Required
	private String workNonth;
	private Long updateBy;
	
	@ExcelColumnDefine(value = 1)
	@Required
	private String workYear;
	
	@ExcelColumnDefine(value = 3)
	@Required
	private String workWeek;

	private Date updateDate;
	@ExcelColumnDefine(value = 5)
	@Required
	private Date endDate;
	
	private Long id;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Integer isDel;
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
/*    public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}*/
	public String getWorkNonth() {
		return workNonth;
	}
	public void setWorkNonth(String workNonth) {
		this.workNonth = workNonth;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public String getWorkYear() {
		return workYear;
	}
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	public String getWorkWeek() {
		return workWeek;
	}
	public void setWorkWeek(String workWeek) {
		this.workWeek = workWeek;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
/*	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
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
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}



}
