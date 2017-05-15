/**
 * 
 */
package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class TmTrackingTaskDTO {
	private Integer isValid;
	private Date updatedAt;
	private String taskContent;
	private Integer intervalDays;
	private String dealerCode;
	private Long createdBy;
	private Integer ver;
	private Date createdAt;
	private Integer customerStatus;
	private Integer taskType;
	private Integer contactorType;
	private Long updatedBy;
	private Integer executeType;
	private Integer intentLevel;
	private Long taskId;
	private String taskName;
	private Integer keepDays;
	private Date crExecuteDate;
	//新增大客户间隔天数
	private Integer bigCustomerIntervalDays;
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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
	public Date getCrExecuteDate() {
		return crExecuteDate;
	}
	public void setCrExecuteDate(Date crExecuteDate) {
		this.crExecuteDate = crExecuteDate;
	}
	public Integer getBigCustomerIntervalDays() {
		return bigCustomerIntervalDays;
	}
	public void setBigCustomerIntervalDays(Integer bigCustomerIntervalDays) {
		this.bigCustomerIntervalDays = bigCustomerIntervalDays;
	}
	
	
	

}
