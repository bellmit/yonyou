package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiAppNCustomerInfoDto {
	private static final long serialVersionUID = 1L;
	private String dealerCode; // 经销商代码
	private Long customerId; 
	private Long fcaId;// 售中APP的客户ID
	private String clientType;// 客户类型
	private String name;// 客户的姓名
	private String gender;// 性别
	private String phone;// 手机号
	private String telephone;// 固定电话
	private Integer provinceId;// 省份ID
	private Integer cityId;// 城市ID
	private Date birthday;// 生日
	private String oppLevelId;// 客户级别ID
	private String sourceType;// 客户来源
	private String secondSourceType;// 客户来源
	private String dealerUserId;// 销售人员的ID
	private String buyCarBugget;// 购车预算
	private String brandId;// 品牌ID
	private String modelId;// 车型ID
	private String carStyleId;// 车款ID
	private String intentCarColor;// 车辆颜色ID
	private Integer buyCarcondition;// 购车类型
	private String giveUpType;// 休眠类型
	private String giveUpCause;// 休眠原因
	private String contendCar;// 竞品车型
	private Date giveUpDate;// 休眠时间
	private Date createDate;// 创建时间
	private Integer isToShop;// 是否到店 2016-6-17 潜客改造
	private Date timeToShop;// 到店时间
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getFcaId() {
		return fcaId;
	}
	public void setFcaId(Long fcaId) {
		this.fcaId = fcaId;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
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
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getOppLevelId() {
		return oppLevelId;
	}
	public void setOppLevelId(String oppLevelId) {
		this.oppLevelId = oppLevelId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSecondSourceType() {
		return secondSourceType;
	}
	public void setSecondSourceType(String secondSourceType) {
		this.secondSourceType = secondSourceType;
	}
	public String getDealerUserId() {
		return dealerUserId;
	}
	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}
	public String getBuyCarBugget() {
		return buyCarBugget;
	}
	public void setBuyCarBugget(String buyCarBugget) {
		this.buyCarBugget = buyCarBugget;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getCarStyleId() {
		return carStyleId;
	}
	public void setCarStyleId(String carStyleId) {
		this.carStyleId = carStyleId;
	}
	public String getIntentCarColor() {
		return intentCarColor;
	}
	public void setIntentCarColor(String intentCarColor) {
		this.intentCarColor = intentCarColor;
	}
	public Integer getBuyCarcondition() {
		return buyCarcondition;
	}
	public void setBuyCarcondition(Integer buyCarcondition) {
		this.buyCarcondition = buyCarcondition;
	}
	public String getGiveUpType() {
		return giveUpType;
	}
	public void setGiveUpType(String giveUpType) {
		this.giveUpType = giveUpType;
	}
	public String getGiveUpCause() {
		return giveUpCause;
	}
	public void setGiveUpCause(String giveUpCause) {
		this.giveUpCause = giveUpCause;
	}
	public String getContendCar() {
		return contendCar;
	}
	public void setContendCar(String contendCar) {
		this.contendCar = contendCar;
	}
	public Date getGiveUpDate() {
		return giveUpDate;
	}
	public void setGiveUpDate(Date giveUpDate) {
		this.giveUpDate = giveUpDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
