package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class TmModel {


	private String modelLabourCode;
	private Date updateDate;
	private String entityCode;
	private Long createBy;
	private Integer ver;
	private String seriesCode;
	private Date createDate;
	private String brandCode;
	private Float labourPrice;
	private Long updateBy;
	private Integer oemTag;
	private String modelGroupName;
	private String modelName;
	private String balanceModeCode;
	private String modelCode;
	private Date downStamp;
	private String localModelCode;
	private String modelGroupCode;
	private Integer isValid;
	public void setModelLabourCode(String modelLabourCode){
		this.modelLabourCode=modelLabourCode;
	}

	public String getModelLabourCode(){
		return this.modelLabourCode;
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

	public void setSeriesCode(String seriesCode){
		this.seriesCode=seriesCode;
	}

	public String getSeriesCode(){
		return this.seriesCode;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setBrandCode(String brandCode){
		this.brandCode=brandCode;
	}

	public String getBrandCode(){
		return this.brandCode;
	}

	public void setLabourPrice(Float labourPrice){
		this.labourPrice=labourPrice;
	}

	public Float getLabourPrice(){
		return this.labourPrice;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setOemTag(Integer oemTag){
		this.oemTag=oemTag;
	}

	public Integer getOemTag(){
		return this.oemTag;
	}

	public void setModelGroupName(String modelGroupName){
		this.modelGroupName=modelGroupName;
	}

	public String getModelGroupName(){
		return this.modelGroupName;
	}

	public void setModelName(String modelName){
		this.modelName=modelName;
	}

	public String getModelName(){
		return this.modelName;
	}

	public void setBalanceModeCode(String balanceModeCode){
		this.balanceModeCode=balanceModeCode;
	}

	public String getBalanceModeCode(){
		return this.balanceModeCode;
	}

	public void setModelCode(String modelCode){
		this.modelCode=modelCode;
	}

	public String getModelCode(){
		return this.modelCode;
	}

	public void setDownStamp(Date downStamp){
		this.downStamp=downStamp;
	}

	public Date getDownStamp(){
		return this.downStamp;
	}

	public void setLocalModelCode(String localModelCode){
		this.localModelCode=localModelCode;
	}

	public String getLocalModelCode(){
		return this.localModelCode;
	}
	
	public void setModelGroupCode(String modelGroupCode){
		this.modelGroupCode=modelGroupCode;
	}

	public String getModelGroupCode(){
		return this.modelGroupCode;
	}

//	public String toXMLString(){
//		return VOUtil.vo2Xml(this);
//	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

}
