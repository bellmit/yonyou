package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class WxServiceAdvisorChangeDTO {


	private Integer boundType;
	private String mobile;
	private Date updateDate;
	private String entityCode;
	private Long createBy;
	private Integer ver;
	private Date createDate;
	private String phone;
	private String employeeName;
	private Long updateBy;
	private String vin;
	private String serviceAdvisor;

	public void setBoundType(Integer boundType){
		this.boundType=boundType;
	}

	public Integer getBoundType(){
		return this.boundType;
	}

	public void setMobile(String mobile){
		this.mobile=mobile;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setEntityCode(String entityCode){
		this.entityCode=entityCode;
	}

	public String getEntityCode(){
		return this.entityCode;
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

	public void setEmployeeName(String employeeName){
		this.employeeName=employeeName;
	}

	public String getEmployeeName(){
		return this.employeeName;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

	public void setServiceAdvisor(String serviceAdvisor){
		this.serviceAdvisor=serviceAdvisor;
	}

	public String getServiceAdvisor(){
		return this.serviceAdvisor;
	}

//	public String toXMLString(){
//		return POFactoryUtil.beanToXmlString(this);
//	}

}
