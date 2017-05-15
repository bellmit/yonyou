package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class SalesPartDetailVO extends BaseVO{

	private String entityCode;
	private String customerCode;
	private String soNo;
	private String remark1;
	private String customerName;
	private String vin;
	private Double salesPartAmount;
	private String salesPartNo;
	private String remark;
	private Integer balanceStatus;
	private String lockUser;
	private String roNo;
	private Date ddcnUpdateDate;
	private String consultant;
	public String getEntityCode() {
		return entityCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public String getSoNo() {
		return soNo;
	}
	public String getRemark1() {
		return remark1;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getVin() {
		return vin;
	}
	public Double getSalesPartAmount() {
		return salesPartAmount;
	}
	public String getSalesPartNo() {
		return salesPartNo;
	}
	public String getRemark() {
		return remark;
	}
	public Integer getBalanceStatus() {
		return balanceStatus;
	}
	public String getLockUser() {
		return lockUser;
	}
	public String getRoNo() {
		return roNo;
	}
	public Date getDdcnUpdateDate() {
		return ddcnUpdateDate;
	}
	public String getConsultant() {
		return consultant;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public void setSalesPartAmount(Double salesPartAmount) {
		this.salesPartAmount = salesPartAmount;
	}
	public void setSalesPartNo(String salesPartNo) {
		this.salesPartNo = salesPartNo;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setBalanceStatus(Integer balanceStatus) {
		this.balanceStatus = balanceStatus;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public void setDdcnUpdateDate(Date ddcnUpdateDate) {
		this.ddcnUpdateDate = ddcnUpdateDate;
	}
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}

}
