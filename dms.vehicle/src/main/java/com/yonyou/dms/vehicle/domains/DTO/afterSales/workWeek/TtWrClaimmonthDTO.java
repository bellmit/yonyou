package com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek;

import java.util.Date;

public class TtWrClaimmonthDTO {
	private Date startDate;
	private Integer workNonth;
	private Long updateBy;
	private Integer workYear;
	private String workWeek;
	private Date updateDate;
	private Date endDate;
	private Long id;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Integer isDel;

	public void setStartDate(Date startDate){
		this.startDate=startDate;
	}

	public Date getStartDate(){
		return this.startDate;
	}

	public void setWorkNonth(Integer workNonth){
		this.workNonth=workNonth;
	}

	public Integer getWorkNonth(){
		return this.workNonth;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setWorkYear(Integer workYear){
		this.workYear=workYear;
	}

	public Integer getWorkYear(){
		return this.workYear;
	}

	public void setWorkWeek(String workWeek){
		this.workWeek=workWeek;
	}

	public String getWorkWeek(){
		return this.workWeek;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setEndDate(Date endDate){
		this.endDate=endDate;
	}

	public Date getEndDate(){
		return this.endDate;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}
}
