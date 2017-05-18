package com.yonyou.dms.DTO.gacfca;

public class SADMS021Dto {
	private String serviceAdvisor;// 客户经理ID
	private String vin;// vin号
	private String entityCode;// 经销商Code
	private String mobile;// 联系电话
	private String employeeName;// 客户经理姓名
	private Integer boundType;// 绑定类型：0为DMS;1为微信
	private String dealerCode;
	
	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getServiceAdvisor() {
		return serviceAdvisor;
	}

	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getBoundType() {
		return boundType;
	}

	public void setBoundType(Integer boundType) {
		this.boundType = boundType;
	}
}
