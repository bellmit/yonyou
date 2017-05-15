package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 微信绑定接口
 * @author wjs
 * @date 2015年11月26日下午4:14:12 修改日期
 */
public class WXBindingRsgDTO {
	private String vin;
	private String dealerCode;
	private String isBinding;//是否进行微信认证
	private Date bindingDate;//微信绑定日期
	
	//wjs 2015-11-26修改日期 ----begin
	private String isEntity;//是否实销经销商
	private String customerName; //车主名称
	private String ownerNo; //车主编号
	private Integer gender;//性别
	private Date birthday;//生日
	private String zipCode;//邮编
	private Integer province;//省
	private Integer city;//市
	private Integer district;//县
	private Integer ctCode;//证件类型
	private String certificateNo;//证件号码
	private String hobby;//爱好
	private String industryFirst;//所在行业大类
	private String industrySecond;//所在行业二类
	private Integer educationLevel;//学历
	private Date submitTime; //提交日期
	private Integer ctmType;//客户类型
	private String soldBy;//销售顾问
	
	private String phone;//手机/电话==不存储
	private String address;//地址==不存储
	private String email;//邮编==不存储	
	//车辆信息
	private String productCode; //产品代码 上端拼接给DMS
	private String license; //车牌号
	private String engineNo; //发动机号
	private String modelYear; //车型年
	private String brand;//品牌
	private String series;//车系
	private String model;//车型
	private String color;//颜色
	private Double vehiclePrice;//车辆价格
	private String salesAdviser;//销售顾问//原来是有soldBy
	private Date salesDate;//销售日期
	private Integer vehiclePurpose;//车辆用途===涉及到三包
	private Date wrtBeginDate;;//保修起始日期
	private Date wrtEndDate;//保修结束日期
	private Double wrtBeginMileage;//保修起始里程
	private Double wrtEndMileage;//保修结束里程
	//wjs end 2015-11-26
	
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getIsBinding() {
		return isBinding;
	}
	public void setIsBinding(String isBinding) {
		this.isBinding = isBinding;
	}
	public Date getBindingDate() {
		return bindingDate;
	}
	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}
	public String getIsEntity() {
		return isEntity;
	}
	public void setIsEntity(String isEntity) {
		this.isEntity = isEntity;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOwnerNo() {
		return ownerNo;
	}
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getIndustryFirst() {
		return industryFirst;
	}
	public void setIndustryFirst(String industryFirst) {
		this.industryFirst = industryFirst;
	}
	public String getIndustrySecond() {
		return industrySecond;
	}
	public void setIndustrySecond(String industrySecond) {
		this.industrySecond = industrySecond;
	}
	public Integer getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(Integer educationLevel) {
		this.educationLevel = educationLevel;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Integer getCtmType() {
		return ctmType;
	}
	public void setCtmType(Integer ctmType) {
		this.ctmType = ctmType;
	}
	public String getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getVehiclePrice() {
		return vehiclePrice;
	}
	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}
	public String getSalesAdviser() {
		return salesAdviser;
	}
	public void setSalesAdviser(String salesAdviser) {
		this.salesAdviser = salesAdviser;
	}
	public Date getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}
	public Integer getVehiclePurpose() {
		return vehiclePurpose;
	}
	public void setVehiclePurpose(Integer vehiclePurpose) {
		this.vehiclePurpose = vehiclePurpose;
	}
	public Date getWrtBeginDate() {
		return wrtBeginDate;
	}
	public void setWrtBeginDate(Date wrtBeginDate) {
		this.wrtBeginDate = wrtBeginDate;
	}
	public Date getWrtEndDate() {
		return wrtEndDate;
	}
	public void setWrtEndDate(Date wrtEndDate) {
		this.wrtEndDate = wrtEndDate;
	}
	public Double getWrtBeginMileage() {
		return wrtBeginMileage;
	}
	public void setWrtBeginMileage(Double wrtBeginMileage) {
		this.wrtBeginMileage = wrtBeginMileage;
	}
	public Double getWrtEndMileage() {
		return wrtEndMileage;
	}
	public void setWrtEndMileage(Double wrtEndMileage) {
		this.wrtEndMileage = wrtEndMileage;
	}
	
}
