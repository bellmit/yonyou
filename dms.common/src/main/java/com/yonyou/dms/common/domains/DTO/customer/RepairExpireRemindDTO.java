package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class RepairExpireRemindDTO {
	
	private String remindContent;
	private String ownerNo;
	private Double remindId;
	private String customerFeedback;
	private Date remindDate;
	private String reminder;
	private Integer remindWay;
	private String vin;
	private String remark;
	private Integer remindFailReason;
	private Integer remindStatus;
	private Integer isReturnFactory;
	private String roNo;
	public String getRemindContent() {
		return remindContent;
	}
	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}
	public String getOwnerNo() {
		return ownerNo;
	}
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}
	public Double getRemindId() {
		return remindId;
	}
	public void setRemindId(Double remindId) {
		this.remindId = remindId;
	}
	public String getCustomerFeedback() {
		return customerFeedback;
	}
	public void setCustomerFeedback(String customerFeedback) {
		this.customerFeedback = customerFeedback;
	}
	public Date getRemindDate() {
		return remindDate;
	}
	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}
	public String getReminder() {
		return reminder;
	}
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}
	public Integer getRemindWay() {
		return remindWay;
	}
	public void setRemindWay(Integer remindWay) {
		this.remindWay = remindWay;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getRemindFailReason() {
		return remindFailReason;
	}
	public void setRemindFailReason(Integer remindFailReason) {
		this.remindFailReason = remindFailReason;
	}
	public Integer getRemindStatus() {
		return remindStatus;
	}
	public void setRemindStatus(Integer remindStatus) {
		this.remindStatus = remindStatus;
	}
	public Integer getIsReturnFactory() {
		return isReturnFactory;
	}
	public void setIsReturnFactory(Integer isReturnFactory) {
		this.isReturnFactory = isReturnFactory;
	}
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}

}
