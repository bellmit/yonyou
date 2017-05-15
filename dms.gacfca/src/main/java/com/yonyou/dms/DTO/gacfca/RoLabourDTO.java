package com.yonyou.dms.DTO.gacfca;

public class RoLabourDTO {

	private String labourCode;

	private String labourName;

	private Double stdLabourHour;

	private String remark;
	private Float labourPrice;
	private Double labourAmount;
	private String activityCode;
	private Integer oemTag;
	private Float discount;
	private String troubleCause;//故障原因
	public String getLabourCode() {
		return labourCode;
	}
	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
	public String getLabourName() {
		return labourName;
	}
	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}
	public Double getStdLabourHour() {
		return stdLabourHour;
	}
	public void setStdLabourHour(Double stdLabourHour) {
		this.stdLabourHour = stdLabourHour;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Float getLabourPrice() {
		return labourPrice;
	}
	public void setLabourPrice(Float labourPrice) {
		this.labourPrice = labourPrice;
	}
	public Double getLabourAmount() {
		return labourAmount;
	}
	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public String getTroubleCause() {
		return troubleCause;
	}
	public void setTroubleCause(String troubleCause) {
		this.troubleCause = troubleCause;
	}
	
}
