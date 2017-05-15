package com.yonyou.dms.DTO.gacfca;

public class ProperServManInfoDTO {

	private String serviceAdviser;// 客户经理ID
	private String employeeName;// 客户经理姓名
	private String mobile;// 联系电话
	private Integer isValid;// 是否启用is_valid
	private Integer isServiceAdvisor;// 是否客户经理 IS_SERVICE_ADVISOR

	// new add by wangjian 2015-5-27
	private Integer isDefaultWxSa;// 是否微信默认客户经理

	// add by weixia 2016-12-07 区分上报是新增还是修改
	private String employeeStatus;// 上报状态 A 代表新增 U 代表修改
	private String dealerCode; // 下端：经销商代码 CHAR(8) 上端：



	public String getServiceAdviser() {
		return serviceAdviser;
	}

	public void setServiceAdviser(String serviceAdviser) {
		this.serviceAdviser = serviceAdviser;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getIsServiceAdvisor() {
		return isServiceAdvisor;
	}

	public void setIsServiceAdvisor(Integer isServiceAdvisor) {
		this.isServiceAdvisor = isServiceAdvisor;
	}

	public Integer getIsDefaultWxSa() {
		return isDefaultWxSa;
	}

	public void setIsDefaultWxSa(Integer isDefaultWxSa) {
		this.isDefaultWxSa = isDefaultWxSa;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

}
