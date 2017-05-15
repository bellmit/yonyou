package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiDmsNCustomerInfoDto {

	private String entityCode;
	private Integer cityId;// 城市ID
	private String oppLevelId;// 客户级别ID
	private String sourceType;// 客户来源
	private String secondSourceType;// 二级客户来源
	private String modelId;// 车型ID
	private Integer buyCarcondition;// 购车类型
	private String dealerUserId;// 销售人员的ID
	private Date birthday;// 生日
	private String carStyleId;// 车款ID
	private String uniquenessId;// DMS客户唯一ID
	private String name;// 客户的姓名
	private String dealerCode;// 经销商代码
	private String brandId;// 品牌ID
	private String clientType;// 客户类型
	private String telephone;// 固定电话
	private Integer provinceId;// 省份ID
	private String gender;// 性别
	private String intentCarColor;// 车辆颜色ID
	private String phone;// 手机号
	private Date createDate;// 创建时间
	private String buyCarBugget;// 购车预算
	private Integer isToShop;// 是否到店 2016-6-17 潜客改造
	private Date timeToShop;// 到店时间

	public Integer getIsToShop() {
		return isToShop;
	}

	public void setIsToShop(Integer isToShop) {
		this.isToShop = isToShop;
	}

	public Date getTimeToShop() {
		return timeToShop;
	}

	public void setTimeToShop(Date timeToShop) {
		this.timeToShop = timeToShop;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setOppLevelId(String oppLevelId) {
		this.oppLevelId = oppLevelId;
	}

	public String getOppLevelId() {
		return this.oppLevelId;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceType() {
		return this.sourceType;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelId() {
		return this.modelId;
	}

	public void setBuyCarcondition(Integer buyCarcondition) {
		this.buyCarcondition = buyCarcondition;
	}

	public Integer getBuyCarcondition() {
		return this.buyCarcondition;
	}

	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	public String getDealerUserId() {
		return this.dealerUserId;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setCarStyleId(String carStyleId) {
		this.carStyleId = carStyleId;
	}

	public String getCarStyleId() {
		return this.carStyleId;
	}

	public void setUniquenessId(String uniquenessId) {
		this.uniquenessId = uniquenessId;
	}

	public String getUniquenessId() {
		return this.uniquenessId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerCode() {
		return this.dealerCode;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandId() {
		return this.brandId;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientType() {
		return this.clientType;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return this.gender;
	}

	public void setIntentCarColor(String intentCarColor) {
		this.intentCarColor = intentCarColor;
	}

	public String getIntentCarColor() {
		return this.intentCarColor;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setBuyCarBugget(String buyCarBugget) {
		this.buyCarBugget = buyCarBugget;
	}

	public String getBuyCarBugget() {
		return this.buyCarBugget;
	}

//	public String toXMLString() {
//		return VOUtil.vo2Xml(this);
//	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getSecondSourceType() {
		return secondSourceType;
	}

	public void setSecondSourceType(String secondSourceType) {
		this.secondSourceType = secondSourceType;
	}
}
