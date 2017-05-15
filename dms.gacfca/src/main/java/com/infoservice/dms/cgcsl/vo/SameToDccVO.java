package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class SameToDccVO extends BaseVO{
	
	private String customerNo;
	private String dealerCode;
	private Long nid ;//上端主键
	private String status;//反馈状态，1表示成功，2表示失败，3表示休眠
	private String sleepReason;//休眠原因（只在休眠时使用）
	private Date sleepTime;//休眠时间（只在休眠时使用）
	private String brandCode;//品牌（只在成交时使用）
	private String seriasCode;//车系
	private String modelCode;//车型
	private String configerCode;//配置（只在成交时使用）
	private Date purchaseTime;//购车时间（只在成交时使用）
	private String salesConsultant;//销售顾问
	private Integer opportunityLevelID;
	private String name;//客户姓名   
    private Integer gender;//性别 (1、男  2、女)
    private String phone;//手机号码
    private String telephone;//固话
    private Integer provinceID;//省份
    private Integer cityID;//城市
    private String considerationID;//适合车型原因
    private Date createdAt;//注册时间
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Long getNid() {
		return nid;
	}
	public void setNid(Long nid) {
		this.nid = nid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSleepReason() {
		return sleepReason;
	}
	public void setSleepReason(String sleepReason) {
		this.sleepReason = sleepReason;
	}
	public Date getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Date sleepTime) {
		this.sleepTime = sleepTime;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getSeriasCode() {
		return seriasCode;
	}
	public void setSeriasCode(String seriasCode) {
		this.seriasCode = seriasCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getConfigerCode() {
		return configerCode;
	}
	public void setConfigerCode(String configerCode) {
		this.configerCode = configerCode;
	}
	public Date getPurchaseTime() {
		return purchaseTime;
	}
	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	public String getSalesConsultant() {
		return salesConsultant;
	}
	public void setSalesConsultant(String salesConsultant) {
		this.salesConsultant = salesConsultant;
	}
	public Integer getOpportunityLevelID() {
		return opportunityLevelID;
	}
	public void setOpportunityLevelID(Integer opportunityLevelID) {
		this.opportunityLevelID = opportunityLevelID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getProvinceID() {
		return provinceID;
	}
	public void setProvinceID(Integer provinceID) {
		this.provinceID = provinceID;
	}
	public Integer getCityID() {
		return cityID;
	}
	public void setCityID(Integer cityID) {
		this.cityID = cityID;
	}
	public String getConsiderationID() {
		return considerationID;
	}
	public void setConsiderationID(String considerationID) {
		this.considerationID = considerationID;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
