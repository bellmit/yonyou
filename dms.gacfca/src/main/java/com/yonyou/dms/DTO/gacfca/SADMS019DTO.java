package com.yonyou.dms.DTO.gacfca;

/**
 * @description SADMS019接受数据DTO
 * @author Administrator
 *
 */
public class SADMS019DTO {
	private String dealerCode;
	
	private String[] updateStatus;
	
	private String[] employeeNo;
	
	private String[] employeeName;
	
	private String[] isServiceAdvisor;
	
	private String[] phone;
	
	private String[] mobile;
	
	private String[] isValid;
	
	private String[] isDefaultManager;

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String[] getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String[] updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String[] getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String[] employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String[] getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String[] employeeName) {
		this.employeeName = employeeName;
	}

	public String[] getIsServiceAdvisor() {
		return isServiceAdvisor;
	}

	public void setIsServiceAdvisor(String[] isServiceAdvisor) {
		this.isServiceAdvisor = isServiceAdvisor;
	}

	public String[] getPhone() {
		return phone;
	}

	public void setPhone(String[] phone) {
		this.phone = phone;
	}

	public String[] getMobile() {
		return mobile;
	}

	public void setMobile(String[] mobile) {
		this.mobile = mobile;
	}

	public String[] getIsValid() {
		return isValid;
	}

	public void setIsValid(String[] isValid) {
		this.isValid = isValid;
	}

	public String[] getIsDefaultManager() {
		return isDefaultManager;
	}

	public void setIsDefaultManager(String[] isDefaultManager) {
		this.isDefaultManager = isDefaultManager;
	}
}
