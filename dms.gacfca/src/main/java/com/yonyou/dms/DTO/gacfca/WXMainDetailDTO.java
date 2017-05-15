package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class WXMainDetailDTO {

	private String entityCode;
	private String customerName;
	private String mobilePhone;
	private String Vin;
	private Date updateDate;
	private String OwnerNo;
	private Date updateMobileDate;
	
	
	public Date getUpdateMobileDate() {
		return updateMobileDate;
	}


	public void setUpdateMobileDate(Date updateMobileDate) {
		this.updateMobileDate = updateMobileDate;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getEntityCode() {
		return entityCode;
	}


	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	public String getOwnerNo() {
		return OwnerNo;
	}


	public void setOwnerNo(String ownerNo) {
		OwnerNo = ownerNo;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public String getVin() {
		return Vin;
	}


	public void setVin(String vin) {
		Vin = vin;
	}
}
