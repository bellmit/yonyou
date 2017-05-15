package com.yonyou.dms.DTO.gacfca;

public class OutBoundReportDTO {
	private String customerName;//客户名称
	private String customerType;//客户类型
	private String contactorMobile;//联系电话
	private String contactorName;//联系人名称
//	private String gender;//性别
//	private String address;//地址
	private String certificateNo;//身份证号
	private String colorCode;//颜色
	private String clientType;
	/** 车主姓名 */
	private String name ;
	/** 车主性别（1：男， 2：女）*/
	private String gender ;
	/** 车主手机号/座机电话 */
	private String cellphone ;
	/** 车主所在省 */
	private String provinceId;
	/** 车主所在市 */
	private String cityId;
	/** 车主所在区/县 */
	private String district;
	/** 车主地址 */
	private String address ;
	/** 车主所在地邮编 */
	private String postCode;
	/** 身份证号  */
	private String idOrCompCode ;
	/** 电子邮箱  */
	private String email  ;
	/** DMS车主记录的主键值  */
	private String dmsOwnerId  ;
	/** 经销商代码  */
	private String dealerCode;
	/** 交车时间  */
	private String buyTime;
	/** 车架号  */
	private String vin ;
	/** 品牌  */
	private String brandId;
	/** 车系  */
	private String modeId;
	/** 车型  */
	private String styleId;
	/** 颜色  */
	private String colorId;
	/** 是否二次录入  */
	private String isSecondTime;
	
	/** 是否逾期  */
	private String isOverDue;
	/** 是否销售退回  */
	private String isSalesReturnStatus;
	private String companyName; //公司名称
	private String vehiclePurpose; //车辆用途
	private String engineNo;   	 // 发动机号
	private String orderNo;//订单号
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	private String zipCode;//邮编
	private String eMail;//邮箱
//	private String vin;
	private String soNo;
	private String ctCode;//证件号码
	public String getCustomerName() {
		return customerName;
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
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getModeId() {
		return modeId;
	}
	public void setModeId(String modeId) {
		this.modeId = modeId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public String getIsSecondTime() {
		return isSecondTime;
	}
	public void setIsSecondTime(String isSecondTime) {
		this.isSecondTime = isSecondTime;
	}

	public String getIsOverDue() {
		return isOverDue;
	}
	public void setIsOverDue(String isOverDue) {
		this.isOverDue = isOverDue;
	}
	public String getIsSalesReturnStatus() {
		return isSalesReturnStatus;
	}
	public void setIsSalesReturnStatus(String isSalesReturnStatus) {
		this.isSalesReturnStatus = isSalesReturnStatus;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getVehiclePurpose() {
		return vehiclePurpose;
	}
	public void setVehiclePurpose(String vehiclePurpose) {
		this.vehiclePurpose = vehiclePurpose;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getContactorMobile() {
		return contactorMobile;
	}
	public void setContactorMobile(String contactorMobile) {
		this.contactorMobile = contactorMobile;
	}
	public String getContactorName() {
		return contactorName;
	}
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public String getCtCode() {
		return ctCode;
	}
	public void setCtCode(String ctCode) {
		this.ctCode = ctCode;
	}
}
