/**
 * 
 */
package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

/**
 * @author sqh
 *
 */
public class LossVehicleRemindDTO {

private String ownerNo;//车主

private String vin;//vin

private Date lastMaintainDate;//最近维修日期
	
private Date remindDate;//提醒时间

private String remindContent;//提醒内容

private String customerFeedback;//客户反馈信息

private Integer remindWay;//提醒方式

private String reminder;//最新记录标识

private Integer remindStatus;//提醒状态

private String remindFailReason;//提醒失败原因

private String remark;//备注

private String dealercode;

private Integer lastTga;//最新记录标识

private Integer remindId;//提醒编号


public Integer getRemindId() {
	return remindId;
}

public void setRemindId(Integer remindId) {
	this.remindId = remindId;
}

public Integer getLastTga() {
	return lastTga;
}

public void setLastTga(Integer lastTga) {
	this.lastTga = lastTga;
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

public Date getLastMaintainDate() {
	return lastMaintainDate;
}

public void setLastMaintainDate(Date lastMaintainDate) {
	this.lastMaintainDate = lastMaintainDate;
}

public Date getRemindDate() {
	return remindDate;
}

public void setRemindDate(Date remindDate) {
	this.remindDate = remindDate;
}

public String getRemindContent() {
	return remindContent;
}

public void setRemindContent(String remindContent) {
	this.remindContent = remindContent;
}

public String getCustomerFeedback() {
	return customerFeedback;
}

public void setCustomerFeedback(String customerFeedback) {
	this.customerFeedback = customerFeedback;
}

public Integer getRemindWay() {
	return remindWay;
}

public void setRemindWay(Integer remindWay) {
	this.remindWay = remindWay;
}

public String getReminder() {
	return reminder;
}

public void setReminder(String reminder) {
	this.reminder = reminder;
}

public Integer getRemindStatus() {
	return remindStatus;
}

public void setRemindStatus(Integer remindStatus) {
	this.remindStatus = remindStatus;
}

public String getRemindFailReason() {
	return remindFailReason;
}

public void setRemindFailReason(String remindFailReason) {
	this.remindFailReason = remindFailReason;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public String getDealercode() {
	return dealercode;
}

public void setDealercode(String dealercode) {
	this.dealercode = dealercode;
}

}