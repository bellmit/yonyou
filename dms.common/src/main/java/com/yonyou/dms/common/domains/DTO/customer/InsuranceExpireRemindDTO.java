package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

/**
 * 保修到期提醒表
 * 
 * @author Administrator
 *
 */
public class InsuranceExpireRemindDTO {

	private Double remindId;
	private String ownerNo;
	private String vin;
	private Date remindDate;
	private String remindContent;
	
	private String customerFeedback;
	private String remark;
	private String reminder;
	private String remindWay;
	private Integer lastTag;
	private Integer remindStatus;
	private Integer remindFailReason;
	private Integer isReturnFactory;
	private String proposalCode;//投保单号
	private Integer renewalRemindStatus;
	private Integer renewlFailedReason;//续保战败原因
	private String renewalRemark;//续保战败备注
	private Date renewalFailedDate;//续保战败日期
	
	

	public String getRemindContent() {
		return remindContent;
	}

	public void setRemindCountent(String remindContent) {
		this.remindContent = remindContent;
	}

	public Double getRemindId() {
		return remindId;
	}

	public void setRemindId(Double remindId) {
		this.remindId = remindId;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Date getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}

	public String getCustomerFeedback() {
		return customerFeedback;
	}

	public void setCustomerFeedback(String customerFeedback) {
		this.customerFeedback = customerFeedback;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getRemindWay() {
		return remindWay;
	}

	public void setRemindWay(String remindWay) {
		this.remindWay = remindWay;
	}

	public Integer getLastTag() {
		return lastTag;
	}

	public void setLastTag(Integer lastTag) {
		this.lastTag = lastTag;
	}

	public Integer getRemindStatus() {
		return remindStatus;
	}

	public void setRemindStatus(Integer remindStatus) {
		this.remindStatus = remindStatus;
	}

	public Integer getRemindFailReason() {
		return remindFailReason;
	}

	public void setRemindFailReason(Integer remindFailReason) {
		this.remindFailReason = remindFailReason;
	}

	public Integer getIsReturnFactory() {
		return isReturnFactory;
	}

	public void setIsReturnFactory(Integer isReturnFactory) {
		this.isReturnFactory = isReturnFactory;
	}

	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	public Integer getRenewalRemindStatus() {
		return renewalRemindStatus;
	}

	public void setRenewalRemindStatus(Integer renewalRemindStatus) {
		this.renewalRemindStatus = renewalRemindStatus;
	}

	public Integer getRenewlFailedReason() {
		return renewlFailedReason;
	}

	public void setRenewlFailedReason(Integer renewlFailedReason) {
		this.renewlFailedReason = renewlFailedReason;
	}

	public String getRenewalRemark() {
		return renewalRemark;
	}

	public void setRenewalRemark(String renewalRemark) {
		this.renewalRemark = renewalRemark;
	}

	public Date getRenewalFailedDate() {
		return renewalFailedDate;
	}

	public void setRenewalFailedDate(Date renewalFailedDate) {
		this.renewalFailedDate = renewalFailedDate;
	}

}
