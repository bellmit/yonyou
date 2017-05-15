package com.yonyou.dms.vehicle.domains.DTO.orderSendTimeManageDTO;

import java.util.Date;

public class TtOrderSendTimeManageDTO {
	private Integer week;
	private Long updateBy;
	private Date updateDate;
	private Long id;
	private Long createBy;
	private String stopTime;
	private Date createDate;
	private String startTime;
	private Integer status;
	private Integer isDel;

	public void setWeek(Integer week){
		this.week=week;
	}

	public Integer getWeek(){
		return this.week;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setStopTime(String stopTime){
		this.stopTime=stopTime;
	}

	public String getStopTime(){
		return this.stopTime;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setStartTime(String startTime){
		this.startTime=startTime;
	}

	public String getStartTime(){
		return this.startTime;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}
}
