package com.yonyou.dms.vehicle.domains.DTO.salesOdditionalOrder;

import java.util.List;

public class SoUpholsterDTO {
	
	private Integer itemId;
	private String upholsterCode;
	private String upholsterName;
	private double stdLabourHour;
	private double labourPrice;
	private Integer accountMode;
	private double discount;
	private double discountAmount;
	private double receiveAmount;
	private String remark;
	private List<SoUpholsterDTO> soDecrodateList;
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getUpholsterCode() {
		return upholsterCode;
	}
	public void setUpholsterCode(String upholsterCode) {
		this.upholsterCode = upholsterCode;
	}
	public String getUpholsterName() {
		return upholsterName;
	}
	public void setUpholsterName(String upholsterName) {
		this.upholsterName = upholsterName;
	}
	public double getStdLabourHour() {
		return stdLabourHour;
	}
	public void setStdLabourHour(double stdLabourHour) {
		this.stdLabourHour = stdLabourHour;
	}
	public double getLabourPrice() {
		return labourPrice;
	}
	public void setLabourPrice(double labourPrice) {
		this.labourPrice = labourPrice;
	}
	public Integer getAccountMode() {
		return accountMode;
	}
	public void setAccountMode(Integer accountMode) {
		this.accountMode = accountMode;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<SoUpholsterDTO> getSoDecrodateList() {
		return soDecrodateList;
	}
	public void setSoDecrodateList(List<SoUpholsterDTO> soDecrodateList) {
		this.soDecrodateList = soDecrodateList;
	}	

}
