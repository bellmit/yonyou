package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;
import java.util.LinkedList;

public class BigCustomerVisitItemDto {
	
	private String dealerCode;//经销商代码
	
	private Long itemId;//拜访记录ID
	
	private String customerNo;//客户编号
	
	private Date visitDate;//拜访时间
	
	private String priorGrade;//拜访前级别
	
	private String nextGrade;//拜访后级别
	
	private String visitSummary;//拜访小结

	private String customerName;//客户名称
	
	private Integer  policyType;//政策类型
	
	private String customerContactsName;//联系人名称
	
	private String customerContactsPost;//联系人职位
	
	private String customerContactsPhone;//联系人电话
	
	private LinkedList<BigCustomerVisitIntentDto> BigCustomerVisitIntentDtoList; //意向明细列表

	public LinkedList<BigCustomerVisitIntentDto> getBigCustomerVisitIntentDtoList() {
		return BigCustomerVisitIntentDtoList;
	}
	public void setBigCustomerVisitIntentDtoList(LinkedList<BigCustomerVisitIntentDto> bigCustomerVisitIntentDtoList) {
		BigCustomerVisitIntentDtoList = bigCustomerVisitIntentDtoList;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public String getPriorGrade() {
		return priorGrade;
	}
	public void setPriorGrade(String priorGrade) {
		this.priorGrade = priorGrade;
	}
	public String getNextGrade() {
		return nextGrade;
	}
	public void setNextGrade(String nextGrade) {
		this.nextGrade = nextGrade;
	}
	public String getVisitSummary() {
		return visitSummary;
	}
	public void setVisitSummary(String visitSummary) {
		this.visitSummary = visitSummary;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getPolicyType() {
		return policyType;
	}
	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}
	public String getCustomerContactsName() {
		return customerContactsName;
	}
	public void setCustomerContactsName(String customerContactsName) {
		this.customerContactsName = customerContactsName;
	}
	public String getCustomerContactsPost() {
		return customerContactsPost;
	}
	public void setCustomerContactsPost(String customerContactsPost) {
		this.customerContactsPost = customerContactsPost;
	}
	public String getCustomerContactsPhone() {
		return customerContactsPhone;
	}
	public void setCustomerContactsPhone(String customerContactsPhone) {
		this.customerContactsPhone = customerContactsPhone;
	}
	
	
	
}
