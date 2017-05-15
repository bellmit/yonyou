package com.yonyou.dms.vehicle.domains.DTO.activityManage;

import java.sql.Date;

public class ActivityManageDTO {
	
	
	
	private Long id;
	private String groupIds;
	private String activityId;
	private String activityId1;
	private String activityId2;
	
	private Integer ageType;
	private Date startDate; 	//开始日期
	private Date endDate; 	//结束日期	
	
	private String vins;
	private Date recallDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityId1() {
		return activityId1;
	}

	public void setActivityId1(String activityId1) {
		this.activityId1 = activityId1;
	}

	public String getActivityId2() {
		return activityId2;
	}

	public void setActivityId2(String activityId2) {
		this.activityId2 = activityId2;
	}

	public Integer getAgeType() {
		return ageType;
	}

	public void setAgeType(Integer ageType) {
		this.ageType = ageType;
	}

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

	public String getVins() {
		return vins;
	}

	public void setVins(String vins) {
		this.vins = vins;
	}

	public Date getRecallDate() {
		return recallDate;
	}

	public void setRecallDate(Date recallDate) {
		this.recallDate = recallDate;
	}

	

}
