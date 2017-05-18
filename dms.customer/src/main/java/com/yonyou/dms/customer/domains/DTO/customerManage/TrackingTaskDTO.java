
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : TrackingTaskDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月6日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.domains.DTO.customerManage;

/**
* 跟进任务定义DTO
* @author zhanshiwei
* @date 2016年9月6日
*/

public class TrackingTaskDTO {


	private Integer isValid;
	private String taskContent;
	private Integer intervalDays;
	private Integer customerStatus;
	private Integer taskType;
	private Integer contactorType;
	private Integer executeType;
	private Integer intentLevel;
	private String taskName;
	private Integer keepDays;
	private Integer bigCustomerIntervalDays;

	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public Integer getIntervalDays() {
		return intervalDays;
	}
	public void setIntervalDays(Integer intervalDays) {
		this.intervalDays = intervalDays;
	}
	public Integer getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(Integer customerStatus) {
		this.customerStatus = customerStatus;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public Integer getContactorType() {
		return contactorType;
	}
	public void setContactorType(Integer contactorType) {
		this.contactorType = contactorType;
	}
	public Integer getExecuteType() {
		return executeType;
	}
	public void setExecuteType(Integer executeType) {
		this.executeType = executeType;
	}
	public Integer getIntentLevel() {
		return intentLevel;
	}
	public void setIntentLevel(Integer intentLevel) {
		this.intentLevel = intentLevel;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getKeepDays() {
		return keepDays;
	}
	public void setKeepDays(Integer keepDays) {
		this.keepDays = keepDays;
	}
	public Integer getBigCustomerIntervalDays() {
		return bigCustomerIntervalDays;
	}
	public void setBigCustomerIntervalDays(Integer bigCustomerIntervalDays) {
		this.bigCustomerIntervalDays = bigCustomerIntervalDays;
	}

	
	
	
}
