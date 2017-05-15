package com.infoservice.dms.cgcsl.vo;

@SuppressWarnings("serial")
public class SADCS060VO extends BaseVO{
	private String clientType;//客户类型（1：客户，2：公司）
	private String name;//车主姓名
	private String gender;//车主性别（1：男， 2：女）
	private String cellphone;//车主手机号/座机电话
	private String provinceId;//车主所在省
	private String cityId;//车主所在市
	private String district;//车主所在区/县
	private String address;//车主地址
	private String postCode;//车主所在地邮编
	private String idOrCompCode;//身份证号
	private String email;//电子邮箱
	private String dmsOwnerId;//车主编号
	private String dealerCode;//经销商代码
	private String buyTime;//交车时间(即保修登记上报时间)
	private String vin;//车架号
	private String serviceAdviser;//客户经理ID
	private String employeeName;//客户经理姓名
	private String mobile;//客户经理联系电话 
	private String wxBindTime;//绑定时间
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
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getIdOrCompCode() {
		return idOrCompCode;
	}
	public void setIdOrCompCode(String idOrCompCode) {
		this.idOrCompCode = idOrCompCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDmsOwnerId() {
		return dmsOwnerId;
	}
	public void setDmsOwnerId(String dmsOwnerId) {
		this.dmsOwnerId = dmsOwnerId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getServiceAdviser() {
		return serviceAdviser;
	}
	public void setServiceAdviser(String serviceAdviser) {
		this.serviceAdviser = serviceAdviser;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWxBindTime() {
		return wxBindTime;
	}
	public void setWxBindTime(String wxBindTime) {
		this.wxBindTime = wxBindTime;
	}
	

}
