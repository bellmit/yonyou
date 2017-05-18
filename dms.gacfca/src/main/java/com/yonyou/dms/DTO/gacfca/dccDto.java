package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class dccDto {
	
	private String dealerCode;
	private Long nid;//上端主键
	private String entityCode;//
	private String dmsCustomerId;//DMS系统客户编号
	private Integer cityId;//城市ID
	private String socialityAccount;//社交账号
	private Integer considerationId;//吸引客户原因ID即购车因素
	private String phone;//手机号码
	private String modelCode;//车型
	private String seriesCode;//车系
	private String configCode;//配置
	private String brandCode;//品牌
	private String colorCode;//意向车型颜色  先空着 不传的
	private String dealerRemark;//备注
	private Integer mediaNameId;//媒体名称ID字典 (下端转给上端字典，下端“渠道细分”)
	private Long id;//客户编号 上端的 下端不管
	private String email;//电子邮件
	private Date birthday;//生日
	private String name;//客户姓名
	private String address;//地址
	private Integer opportunityLevelId;//销售机会级别ID--（意向级别）-------上端注意下以前是long 要改Integer
	private String telephone;//固定电话
	private Integer provinceId;//省份ID
	private String postCode;//邮编
	private String gender;//性别字典（只有先生和女士）转为男和女下发给下端  端传先生女士
	private String salesConsultant;//负责销售顾问（传到下端备注字段中，上报备注信息不需要上报）
	private Integer mediaTypeId;//媒体类型ID字典 (下端转给上端字典，下端“信息渠道”)
	private Date createDate;//注册时间
	private String dealerUserName;//电话营销员（下端：放在备注中）
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
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getDmsCustomerId() {
		return dmsCustomerId;
	}
	public void setDmsCustomerId(String dmsCustomerId) {
		this.dmsCustomerId = dmsCustomerId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getSocialityAccount() {
		return socialityAccount;
	}
	public void setSocialityAccount(String socialityAccount) {
		this.socialityAccount = socialityAccount;
	}
	public Integer getConsiderationId() {
		return considerationId;
	}
	public void setConsiderationId(Integer considerationId) {
		this.considerationId = considerationId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getDealerRemark() {
		return dealerRemark;
	}
	public void setDealerRemark(String dealerRemark) {
		this.dealerRemark = dealerRemark;
	}
	public Integer getMediaNameId() {
		return mediaNameId;
	}
	public void setMediaNameId(Integer mediaNameId) {
		this.mediaNameId = mediaNameId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getOpportunityLevelId() {
		return opportunityLevelId;
	}
	public void setOpportunityLevelId(Integer opportunityLevelId) {
		this.opportunityLevelId = opportunityLevelId;
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
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSalesConsultant() {
		return salesConsultant;
	}
	public void setSalesConsultant(String salesConsultant) {
		this.salesConsultant = salesConsultant;
	}
	public Integer getMediaTypeId() {
		return mediaTypeId;
	}
	public void setMediaTypeId(Integer mediaTypeId) {
		this.mediaTypeId = mediaTypeId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDealerUserName() {
		return dealerUserName;
	}
	public void setDealerUserName(String dealerUserName) {
		this.dealerUserName = dealerUserName;
	}

}
