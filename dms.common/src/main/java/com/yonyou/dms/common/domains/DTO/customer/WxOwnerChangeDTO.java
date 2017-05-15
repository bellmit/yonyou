package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class WxOwnerChangeDTO {


	private Date updateDate;
	private String mobile;
	private String ownerName;
	private String entityCode;
	private String eMail;
	private Long createBy;
	private Integer ver;
	private String ownerNo;
	private Date createDate;
	private String phone;
	private String zipCode;
	private Long updateBy;
	private Integer gender;
	private String vin;
	private String address;

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setMobile(String mobile){
		this.mobile=mobile;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setOwnerName(String ownerName){
		this.ownerName=ownerName;
	}

	public String getOwnerName(){
		return this.ownerName;
	}

	public void setEntityCode(String entityCode){
		this.entityCode=entityCode;
	}

	public String getEntityCode(){
		return this.entityCode;
	}

	public void setEMail(String eMail){
		this.eMail=eMail;
	}

	public String getEMail(){
		return this.eMail;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setOwnerNo(String ownerNo){
		this.ownerNo=ownerNo;
	}

	public String getOwnerNo(){
		return this.ownerNo;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setZipCode(String zipCode){
		this.zipCode=zipCode;
	}

	public String getZipCode(){
		return this.zipCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setGender(Integer gender){
		this.gender=gender;
	}

	public Integer getGender(){
		return this.gender;
	}

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

	public void setAddress(String address){
		this.address=address;
	}

	public String getAddress(){
		return this.address;
	}

//	public String toXMLString(){
//		return POFactoryUtil.beanToXmlString(this);
//	}

}
