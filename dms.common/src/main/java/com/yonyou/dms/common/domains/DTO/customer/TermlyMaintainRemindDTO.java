package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class TermlyMaintainRemindDTO {

	private Integer dKey;
	private Date updateDate;
	private String remindContent;
	private String dealercode;
	private String createdBy;
	private String ownerNo;
	private Date createdAt;
	private Double remindId;
	private String customerFeedback;
	private String updatedBy;
	private Date remindDate;
	private Integer lastTag;
	private String reminder;
	private Integer remindWay;
	private String vin;
	private String remark;
	private Integer remindFailReason;
	private Integer remindStatus;

	private Date lastNextMaintainDate;
	private Double lastNextMaintainMileage;

	private Double roNo;
	private Double isSuccessRemind;
	
	private String nextmaintainmileage;//下次保养里程
	private Date nextmaintainDate;//预计下次保养日期
	private String currentmileage;//当前实际里程
	
	private String vins;
	
	/**
	 * @return the vins
	 */
	public String getVins() {
		return vins;
	}

	/**
	 * @param vins the vins to set
	 */
	public void setVins(String vins) {
		this.vins = vins;
	}

	/**
	 * @return the nextmaintainmileage
	 */
	public String getNextmaintainmileage() {
		return nextmaintainmileage;
	}

	/**
	 * @param nextmaintainmileage the nextmaintainmileage to set
	 */
	public void setNextmaintainmileage(String nextmaintainmileage) {
		this.nextmaintainmileage = nextmaintainmileage;
	}

	/**
	 * @return the nextmaintainDate
	 */
	public Date getNextmaintainDate() {
		return nextmaintainDate;
	}

	/**
	 * @param nextmaintainDate the nextmaintainDate to set
	 */
	public void setNextmaintainDate(Date nextmaintainDate) {
		this.nextmaintainDate = nextmaintainDate;
	}

	/**
	 * @return the currentmileage
	 */
	public String getCurrentmileage() {
		return currentmileage;
	}

	/**
	 * @param currentmileage the currentmileage to set
	 */
	public void setCurrentmileage(String currentmileage) {
		this.currentmileage = currentmileage;
	}

	

	public Double getIsSuccessRemind() {
		return isSuccessRemind;
	}

	public void setIsSuccessRemind(Double isSuccessRemind) {
		this.isSuccessRemind = isSuccessRemind;
	}

	public Double getRoNo() {
		return roNo;
	}

	public void setRoNo(Double roNo) {
		this.roNo = roNo;
	}

	public Date getLastNextMaintainDate() {
		return lastNextMaintainDate;
	}

	public void setLastNextMaintainDate(Date lastNextMaintainDate) {
		this.lastNextMaintainDate = lastNextMaintainDate;
	}

	public Double getLastNextMaintainMileage() {
		return lastNextMaintainMileage;
	}

	public void setLastNextMaintainMileage(Double lastNextMaintainMileage) {
		this.lastNextMaintainMileage = lastNextMaintainMileage;
	}

	public Integer getRemindFailReason() {
		return remindFailReason;
	}

	public void setRemindFailReason(Integer remindFailReason) {
		this.remindFailReason = remindFailReason;
	}

	public void setDKey(Integer dKey) {
		this.dKey = dKey;
	}

	public Integer getDKey() {
		return this.dKey;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}

	public String getRemindContent() {
		return this.remindContent;
	}

	public void setEntityCode(String dealercode) {
		this.dealercode = dealercode;
	}

	public String getEntityCode() {
		return this.dealercode;
	}

	public void setCreateBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public String getOwnerNo() {
		return this.ownerNo;
	}

	public void setCreateDate(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCreateDat() {
		return this.createdAt;
	}

	public void setRemindId(Double remindId) {
		this.remindId = remindId;
	}

	public Double getRemindId() {
		return this.remindId;
	}

	public void setCustomerFeedback(String customerFeedback) {
		this.customerFeedback = customerFeedback;
	}

	public String getCustomerFeedback() {
		return this.customerFeedback;
	}

	public void setUpdateBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}

	public Date getRemindDate() {
		return this.remindDate;
	}

	public void setLastTag(Integer lastTag) {
		this.lastTag = lastTag;
	}

	public Integer getLastTag() {
		return this.lastTag;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getReminder() {
		return this.reminder;
	}

	public void setRemindWay(Integer remindWay) {
		this.remindWay = remindWay;
	}

	public Integer getRemindWay() {
		return this.remindWay;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getVin() {
		return this.vin;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public Integer getRemindStatus() {
		return remindStatus;
	}

	public void setRemindStatus(Integer remindStatus) {
		this.remindStatus = remindStatus;
	}	

}
