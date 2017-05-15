package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OwnerDTO {

	private String dealercode;

	private String ownerNo;//车主编号

	private String ownerGroupNo;//OWNER_GROUP_NO

	private String subOwnerNo;//工厂车主编号

	private Integer ownerProperty;//车主性质

	private String ownerName;//车主

	private String ownerSpell;//车主拼音

	private Double gender;//性别

	private Double ctCode;//证件类型代码

	private String certificateNo;//证件号码

	private String taxNo;//税号

	private Double province;//省份

	private Double city;//城市

	private Double district;//区县

	private String address;//地址

	private String zipCode;//邮编

	private String secondAddress;//地址二

	private Double industryFirst;//所在行业大类

	private Double industrySecond;//所在行业二类

	private String phone;//电话

	private String mobile;//手机

	private Date birthday;//出生日期

	private String eMail;//E_MAIL

	private Double familyIncome;//家庭月收入

	private Double ownerMarriage;//婚姻状况

	private Double eduLevel;//学历

	private List hobby;//爱好

	private String decisionMarkerName;//决策人姓名

	private Double decisionMarkerGender;//决策人性别

	private String decisionMarkerPhone;//决策人电话

	private String decisionMarkerMobile;//决策人手机

	private String decisionMarkerAddress;//决策人地址

	private Double decisionMarkerPosition;//决策人职位

	private Date decisionMarkerBirthday;//决策人生日

	private String decisionMarkerEMail;//决策人EMail

	private String decisionMarkerHobby;//决策人爱好

	private String contactorName;//联系人

	private Double contactorGender;//联系人性别

	private String contactorPhone;//联系人电话

	private String contactorMobile;//联系人手机

	private Double contactorProvince;//联系人省份

	private String contactorCity;//联系人城市

	private String contactorDistrict;//联系人区县

	private String contactorAddress;//联系人详细地址

	private String contactorZipCode;//联系人邮编

	private String contactorEmail;//联系人E_MAIL

	private Double contactorHobbyContact;//联系人最佳联系方式

	private Double contactorPosition;//联系人职业职称

	private String contactVocationType;//联系人职业类别
	
	private String contactorFax;//联系人传真号码

	private String remark;//备注

	private String ownerNoOld;//原车主编号

	private Date downTimestamp;//下发时序
	
	private Date foundDate;//建档日期

	private Date submitTime;//上报日期

	private Double isUpload;//是否上报

	private Double isUploadGroup;//是否上报集团

	private Double prePay;//预收款

	private Double arrearageAmount;//欠款金额

	private Double cusReceiveSort;//客户收款类别

	private String memberNo;//会员编号

	private Date createDat;//CREATE_DATE

	private Date updateDat;//UPDATE_DATE

	private Double updatedBy;//UPDATE_BY

	private Double createdBy;//CREATE_BY

	private Double ver;//VER

	private String customerCode;//客户代码
	
	private String ownerMemo;//车主备注 对应车主备注表
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the ddcnUpdatDate
	 */
	public Date getDdcnUpdatDate() {
		return ddcnUpdatDate;
	}

	/**
	 * @param ddcnUpdatDate
	 *            the ddcnUpdatDate to set
	 */
	public void setDdcnUpdatDate(Date ddcnUpdatDate) {
		this.ddcnUpdatDate = ddcnUpdatDate;
	}

	/**
	 * @return the reportTime
	 */
	public Date getReportTime() {
		return reportTime;
	}

	/**
	 * @param reportTime
	 *            the reportTime to set
	 */
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	/**
	 * @return the reportBIDatetime
	 */
	public Date getReportBIDatetime() {
		return reportBIDatetime;
	}

	/**
	 * @param reportBIDatetime
	 *            the reportBIDatetime to set
	 */
	public void setReportBIDatetime(Date reportBIDatetime) {
		this.reportBIDatetime = reportBIDatetime;
	}

	private Date ddcnUpdatDate;

	private Integer contactorVocationType;

	private Date reportTime;

	private Date reportBIDatetime;

	public String getContactVocationType() {
		return contactVocationType;
	}

	public void setContactVocationType(String contactVocationType) {
		this.contactVocationType = contactVocationType;
	}

	@SuppressWarnings("rawtypes")
	private LinkedList listVehicle;

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getOwnerMemo() {
		return ownerMemo;
	}

	public void setOwnerMemo(String ownerMemo) {
		this.ownerMemo = ownerMemo;
	}

	public void setDecisionMarkerBirthday(Date decisionMarkerBirthday) {
		this.decisionMarkerBirthday = decisionMarkerBirthday;
	}

	public Date getDecisionMarkerBirthday() {
		return this.decisionMarkerBirthday;
	}

	public void setContactorVocationType(Integer contactorVocationType) {
		this.contactorVocationType = contactorVocationType;
	}

	public Integer getContactorVocationType() {
		return this.contactorVocationType;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setOwnerNoOld(String ownerNoOld) {
		this.ownerNoOld = ownerNoOld;
	}

	public String getOwnerNoOld() {
		return this.ownerNoOld;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getEMail() {
		return this.eMail;
	}

	public void setEntityCode(String dealercode) {
		this.dealercode = dealercode;
	}

	public String getEntityCode() {
		return this.dealercode;
	}

	public void setVer(Double ver) {
		this.ver = ver;
	}

	public Double getVer() {
		return this.ver;
	}

	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}

	public String getContactorPhone() {
		return this.contactorPhone;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public String getOwnerNo() {
		return this.ownerNo;
	}

	public void setCreateDate(Date createDat) {
		this.createDat = createDat;
	}

	public Date getCreateDat() {
		return this.createDat;
	}

	public void setContactorAddress(String contactorAddress) {
		this.contactorAddress = contactorAddress;
	}

	public String getContactorAddress() {
		return this.contactorAddress;
	}

	public void setContactorEmail(String contactorEmail) {
		this.contactorEmail = contactorEmail;
	}

	public String getContactorEmail() {
		return this.contactorEmail;
	}

	public void setIsUpload(Double isUpload) {
		this.isUpload = isUpload;
	}

	public Double getIsUpload() {
		return this.isUpload;
	}

	public void setContactorDistrict(String contactorDistrict) {
		this.contactorDistrict = contactorDistrict;
	}

	public String getContactorDistrict() {
		return this.contactorDistrict;
	}

	public void setDecisionMarkerMobile(String decisionMarkerMobile) {
		this.decisionMarkerMobile = decisionMarkerMobile;
	}

	public String getDecisionMarkerMobile() {
		return this.decisionMarkerMobile;
	}

	public void setPrePay(Double prePay) {
		this.prePay = prePay;
	}

	public Double getPrePay() {
		return this.prePay;
	}

	public void setOwnerMarriage(Double ownerMarriage) {
		this.ownerMarriage = ownerMarriage;
	}

	public Double getOwnerMarriage() {
		return this.ownerMarriage;
	}

	public void setContactorCity(String contactorCity) {
		this.contactorCity = contactorCity;
	}

	public String getContactorCity() {
		return this.contactorCity;
	}

	public void setDecisionMarkerGender(Double decisionMarkerGender) {
		this.decisionMarkerGender = decisionMarkerGender;
	}

	public Double getDecisionMarkerGender() {
		return this.decisionMarkerGender;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getTaxNo() {
		return this.taxNo;
	}

	public void setOwnerGroupNo(String ownerGroupNo) {
		this.ownerGroupNo = ownerGroupNo;
	}

	public String getOwnerGroupNo() {
		return this.ownerGroupNo;
	}

	public void setContactorProvince(Double contactorProvince) {
		this.contactorProvince = contactorProvince;
	}

	public Double getContactorProvince() {
		return this.contactorProvince;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setContactorZipCode(String contactorZipCode) {
		this.contactorZipCode = contactorZipCode;
	}

	public String getContactorZipCode() {
		return this.contactorZipCode;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberNo() {
		return this.memberNo;
	}

	public void setDecisionMarkerHobby(String decisionMarkerHobby) {
		this.decisionMarkerHobby = decisionMarkerHobby;
	}

	public String getDecisionMarkerHobby() {
		return this.decisionMarkerHobby;
	}

	public void setContactorHobbyContact(Double contactorHobbyContact) {
		this.contactorHobbyContact = contactorHobbyContact;
	}

	public Double getContactorHobbyContact() {
		return this.contactorHobbyContact;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setDecisionMarkerPhone(String decisionMarkerPhone) {
		this.decisionMarkerPhone = decisionMarkerPhone;
	}

	public String getDecisionMarkerPhone() {
		return this.decisionMarkerPhone;
	}

	public void setUpdateBy(Double updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Double getUpdatedBy() {
		return this.updatedBy;
	}

	public void setCity(Double city) {
		this.city = city;
	}

	public Double getCity() {
		return this.city;
	}

	public void setOwnerSpell(String ownerSpell) {
		this.ownerSpell = ownerSpell;
	}

	public String getOwnerSpell() {
		return this.ownerSpell;
	}

	public void setContactorFax(String contactorFax) {
		this.contactorFax = contactorFax;
	}

	public String getContactorFax() {
		return this.contactorFax;
	}

	public void setIndustrySecond(Double industrySecond) {
		this.industrySecond = industrySecond;
	}

	public Double getIndustrySecond() {
		return this.industrySecond;
	}

	public void setCreateBy(Double createdBy) {
		this.createdBy = createdBy;
	}

	public Double getCreatedBy() {
		return this.createdBy;
	}

	public void setProvince(Double province) {
		this.province = province;
	}

	public Double getProvince() {
		return this.province;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getCertificateNo() {
		return this.certificateNo;
	}

	public void setDistrict(Double district) {
		this.district = district;
	}

	public Double getDistrict() {
		return this.district;
	}

	public void setSubOwnerNo(String subOwnerNo) {
		this.subOwnerNo = subOwnerNo;
	}

	public String getSubOwnerNo() {
		return this.subOwnerNo;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setDecisionMarkerEMail(String decisionMarkerEMail) {
		this.decisionMarkerEMail = decisionMarkerEMail;
	}

	public String getDecisionMarkerEMail() {
		return this.decisionMarkerEMail;
	}

	public void setOwnerProperty(Integer ownerProperty) {
		this.ownerProperty = ownerProperty;
	}

	public Integer getOwnerProperty() {
		return this.ownerProperty;
	}

	public void setCtCode(Double ctCode) {
		this.ctCode = ctCode;
	}

	public Double getCtCode() {
		return this.ctCode;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public Date getDownTimestamp() {
		return this.downTimestamp;
	}

	public void setDecisionMarkerPosition(Double decisionMarkerPosition) {
		this.decisionMarkerPosition = decisionMarkerPosition;
	}

	public Double getDecisionMarkerPosition() {
		return this.decisionMarkerPosition;
	}

	public void setIndustryFirst(Double industryFirst) {
		this.industryFirst = industryFirst;
	}

	public Double getIndustryFirst() {
		return this.industryFirst;
	}

	public void setArrearageAmount(Double arrearageAmount) {
		this.arrearageAmount = arrearageAmount;
	}

	public Double getArrearageAmount() {
		return this.arrearageAmount;
	}

	public void setCusReceiveSort(Double cusReceiveSort) {
		this.cusReceiveSort = cusReceiveSort;
	}

	public Double getCusReceiveSort() {
		return this.cusReceiveSort;
	}

	public void setFamilyIncome(Double familyIncome) {
		this.familyIncome = familyIncome;
	}

	public Double getFamilyIncome() {
		return this.familyIncome;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setUpdateDate(Date updateDat) {
		this.updateDat = updateDat;
	}

	public Date getUpdateDat() {
		return this.updateDat;
	}

	public void setHobby(List hobby) {
		this.hobby = hobby;
	}

	public List getHobby() {
		return this.hobby;
	}

	public void setContactorPosition(Double contactorPosition) {
		this.contactorPosition = contactorPosition;
	}

	public Double getContactorPosition() {
		return this.contactorPosition;
	}

	public void setIsUploadGroup(Double isUploadGroup) {
		this.isUploadGroup = isUploadGroup;
	}

	public Double getIsUploadGroup() {
		return this.isUploadGroup;
	}

	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}

	public String getContactorName() {
		return this.contactorName;
	}

	public void setDecisionMarkerName(String decisionMarkerName) {
		this.decisionMarkerName = decisionMarkerName;
	}

	public String getDecisionMarkerName() {
		return this.decisionMarkerName;
	}

	public void setDecisionMarkerAddress(String decisionMarkerAddress) {
		this.decisionMarkerAddress = decisionMarkerAddress;
	}

	public String getDecisionMarkerAddress() {
		return this.decisionMarkerAddress;
	}

	public void setContactorMobile(String contactorMobile) {
		this.contactorMobile = contactorMobile;
	}

	public String getContactorMobile() {
		return this.contactorMobile;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public Date getFoundDate() {
		return this.foundDate;
	}

	public void setGender(Double gender) {
		this.gender = gender;
	}

	public Double getGender() {
		return this.gender;
	}

	public void setEduLevel(Double eduLevel) {
		this.eduLevel = eduLevel;
	}

	public Double getEduLevel() {
		return this.eduLevel;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}

	public String getSecondAddress() {
		return this.secondAddress;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setContactorGender(Double contactorGender) {
		this.contactorGender = contactorGender;
	}

	public Double getContactorGender() {
		return this.contactorGender;
	}

	// public String toXMLString() {
	// return VOUtil.vo2Xml(this);
	// }

	@SuppressWarnings("rawtypes")
	public LinkedList getListVehicle() {
		return listVehicle;
	}

	public void setListVehicle(LinkedList listVehicle) {
		this.listVehicle = listVehicle;
	}

}
