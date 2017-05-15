package com.yonyou.dms.vehicle.domains.DTO.salesOdditionalOrder;

public class SoServiceDTO {
	
	private String serviceCode;
	private String serviceName;
	private Integer serviceType;
	private double directivePrice;
	private double realPrice;
	private Integer accountMode;
	private double receiveableAmount;
	private double consignedAmount;
	private String remark;
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Integer getServiceType() {
		return serviceType;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	public double getDirectivePrice() {
		return directivePrice;
	}
	public void setDirectivePrice(double directivePrice) {
		this.directivePrice = directivePrice;
	}
	public double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
	}
	public Integer getAccountMode() {
		return accountMode;
	}
	public void setAccountMode(Integer accountMode) {
		this.accountMode = accountMode;
	}
	public double getReceiveableAmount() {
		return receiveableAmount;
	}
	public void setReceiveableAmount(double receiveableAmount) {
		this.receiveableAmount = receiveableAmount;
	}
	public double getConsignedAmount() {
		return consignedAmount;
	}
	public void setConsignedAmount(double consignedAmount) {
		this.consignedAmount = consignedAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
