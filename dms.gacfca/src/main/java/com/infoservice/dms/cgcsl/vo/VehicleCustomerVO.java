package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class VehicleCustomerVO extends BaseVO {
	/**
	 * 车主信息修改上报下发接口vo
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer ownerProperty;  //车主性质
	private String ownerName;       //车主姓名
	private String contactorMobile; //车主手机
	private String contactorPhone;  //车主电话
	private Integer ctCode;         //证件类型
	private String certificateNo;   //证件号码
	private Integer gender;         //性别
	private Integer familyIncome;   //家庭月收入
	private Date birthday;          //出生年月
	private Integer ownerMarriage;  //婚姻状况
	private Integer province;       //省份
	private Integer city;           //城市
	private Integer district;       //区县
	private String address;         //地址
	private Integer industryFirst;  //行业大类
	private Integer industrySecond; //行业小类
	private String zipCode;         //邮编
	private String email;           //电子邮箱
	private String vin;
	private Date upDate;            //更新日期 
	
	public String getContactorMobile() {
		return contactorMobile;
	}
	public void setContactorMobile(String contactorMobile) {
		this.contactorMobile = contactorMobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getOwnerProperty() {
		return ownerProperty;
	}
	public void setOwnerProperty(Integer ownerProperty) {
		this.ownerProperty = ownerProperty;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getContactorPhone() {
		return contactorPhone;
	}
	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}
	public Integer getCtCode() {
		return ctCode;
	}
	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getFamilyIncome() {
		return familyIncome;
	}
	public void setFamilyIncome(Integer familyIncome) {
		this.familyIncome = familyIncome;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getOwnerMarriage() {
		return ownerMarriage;
	}
	public void setOwnerMarriage(Integer ownerMarriage) {
		this.ownerMarriage = ownerMarriage;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getIndustryFirst() {
		return industryFirst;
	}
	public void setIndustryFirst(Integer industryFirst) {
		this.industryFirst = industryFirst;
	}
	public Integer getIndustrySecond() {
		return industrySecond;
	}
	public void setIndustrySecond(Integer industrySecond) {
		this.industrySecond = industrySecond;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getUpDate() {
		return upDate;
	}
	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}

	public String toXMLString() {
		// TODO Auto-generated method stub
		return null;
	}
}
