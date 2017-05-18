/**
 * @Description:发送消息VO
 * @Copyright: Copyright (c) 2014
 * @Company: http://autosoft.ufida.com
 * @Date: 2014-2-28
 * @author lukezu
 * @version 1.0
 */

package com.infoservice.dms.cgcsl.vo;


@SuppressWarnings("serial")
public class WarrantyRegistVO extends BaseVO{
	/** 客户类型（1：客户，2：公司）*/
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
	private String entityCode;
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
	
	/** add by weixia 2015-6-12 **/
	/** 是否二次录入  */
	private String isSecondTime;
	/** 是否逾期  */
	private String isOverDue;
	/** 是否销售退回  */
	private String isSalesReturnStatus;
	
	/** add by weixia 2015-6-12 **/
	private String companyName; //公司名称
	
	private String vehiclePurpose; //车辆用途
	
	private String engineNo;   	 // 发动机号
	
	private String orderNo;//订单号
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getIsSalesReturnStatus() {
		return isSalesReturnStatus;
	}
	public void setIsSalesReturnStatus(String isSalesReturnStatus) {
		this.isSalesReturnStatus = isSalesReturnStatus;
	}
	public String getIsOverDue() {
		return isOverDue;
	}
	public void setIsOverDue(String isOverDue) {
		this.isOverDue = isOverDue;
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
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
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
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
}
