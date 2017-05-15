package com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek;

import java.util.Date;

public class TmHolidayDTO {
	private Long holidayId;
	private Date startDate;
	private Date adjustDate2;
	private Date adjustDate;
	private Long updateBy;
	private String holidayYear;
	private Date updateDate;
	private Date endDate;
	private Long createBy;
	private Date createDate;
	private Integer status;
	private String holidayName;

	public void setHolidayId(Long holidayId){
		this.holidayId=holidayId;
	}

	public Long getHolidayId(){
		return this.holidayId;
	}

	public void setStartDate(Date startDate){
		this.startDate=startDate;
	}

	public Date getStartDate(){
		return this.startDate;
	}

	public void setAdjustDate2(Date adjustDate2){
		this.adjustDate2=adjustDate2;
	}

	public Date getAdjustDate2(){
		return this.adjustDate2;
	}

	public void setAdjustDate(Date adjustDate){
		this.adjustDate=adjustDate;
	}

	public Date getAdjustDate(){
		return this.adjustDate;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}


	public String getHolidayYear() {
		return holidayYear;
	}

	public void setHolidayYear(String holidayYear) {
		this.holidayYear = holidayYear;
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

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setHolidayName(String holidayName){
		this.holidayName=holidayName;
	}

	public String getHolidayName(){
		return this.holidayName;
	}

}
