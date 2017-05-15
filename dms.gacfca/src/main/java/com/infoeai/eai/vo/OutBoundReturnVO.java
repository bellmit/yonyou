package com.infoeai.eai.vo;

import java.util.Date;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class OutBoundReturnVO extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dealerCode;
	private String dmsOwnerId;//车主号
	private String name;//车主姓名
	private String clientType;//车主类型
	private String gender ;//性别
	private String address;//地址
	private String provinceId ;//省份
	private String cityId;//城市
	private String district;//区县
	private String idOrCompCode;//身份证号
	private String postCode;//邮编
	private String cellphone;//手机
	private String email;
	private String vin;
	private String brandId;//品牌
	private String modeId;//车系
	private String styleId;//车型
	private String colorId;//颜色
	private String isVerifyAddress;//是否核实地址(1、是 0、否)
	private String isOutbound;//数据是否外呼(1.否 2、是)
	private String obIsSuccess;//外呼是否成功（1、是 0 、否）
	private String reasons; //失败原因 (obIsSuccess=0) 1 非机主 2、非 车主 3、空号/错号 4、占线/无人接听/停机
							//外呼成功原因(obIsSuccess=1) 5、需再联系 6 成功核实 7、信息未核实
	private String isUpdate;//客户信息是否有更新（1、是 0、否） 外乎失败(obIsSuccess=0) 则isUpdate=0;成功 isUpdate=1; 
	private String outboundRemark;//外呼备注
	private String isOwner;//是否车主本人 1、是本人 2 不是本人
	private Date outboundTime;//外呼时间
	private String isBinding;//是否进行微信认证
	private Date bindingDate;//微信绑定日期
	private String obFlag;//OB标识（""||null关注OB结果,2只关注微信认证结果）
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDmsOwnerId() {
		return dmsOwnerId;
	}
	public void setDmsOwnerId(String dmsOwnerId) {
		this.dmsOwnerId = dmsOwnerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
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
	public String getIdOrCompCode() {
		return idOrCompCode;
	}
	public void setIdOrCompCode(String idOrCompCode) {
		this.idOrCompCode = idOrCompCode;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getIsVerifyAddress() {
		return isVerifyAddress;
	}
	public void setIsVerifyAddress(String isVerifyAddress) {
		this.isVerifyAddress = isVerifyAddress;
	}
	public String getIsOutbound() {
		return isOutbound;
	}
	public void setIsOutbound(String isOutbound) {
		this.isOutbound = isOutbound;
	}
	public String getObIsSuccess() {
		return obIsSuccess;
	}
	public void setObIsSuccess(String obIsSuccess) {
		this.obIsSuccess = obIsSuccess;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getOutboundRemark() {
		return outboundRemark;
	}
	public void setOutboundRemark(String outboundRemark) {
		this.outboundRemark = outboundRemark;
	}
	public String getIsOwner() {
		return isOwner;
	}
	public void setIsOwner(String isOwner) {
		this.isOwner = isOwner;
	}
	public Date getOutboundTime() {
		return outboundTime;
	}
	public void setOutboundTime(Date outboundTime) {
		this.outboundTime = outboundTime;
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
	public String getObFlag() {
		return obFlag;
	}
	public void setObFlag(String obFlag) {
		this.obFlag = obFlag;
	}
	

}
