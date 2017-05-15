package com.infoeai.eai.vo;
/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2013-05-30 10:26:00
* CreateBy   : Administrator
* Comment    : generate by com.sgm.po.POGen
*/


import java.util.Date;

@SuppressWarnings("serial")
public class TmVhclMaterialVO extends BaseVO{

	private Long companyId;
	private Long createBy;
	private Integer ver;
	private Date unIssueDate;
	private Date createDate;
	private String displacement;
	private Date enableDate;
	private Integer ifStatus;
	private Double vhclPrice;
	private Date issueDate;
	private Integer status;
	private Date disableDate;
	private String trimName;
	private Long materialId;
	private String materialName;
	private Integer orderFlag;
	private Integer isDel;
	private Double salePrice;
	private Date updateDate;
	private String colorName;
	private String colorCode;
	private String trimCode;
	private String materialCode;
	private Long updateBy;
	private Integer isArc;
	private String remark;
	private Integer isSales;
	private Integer isEc;

	public void setCompanyId(Long companyId){
		this.companyId=companyId;
	}

	public Long getCompanyId(){
		return this.companyId;
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

	public void setUnIssueDate(Date unIssueDate){
		this.unIssueDate=unIssueDate;
	}

	public Date getUnIssueDate(){
		return this.unIssueDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setDisplacement(String displacement){
		this.displacement=displacement;
	}

	public String getDisplacement(){
		return this.displacement;
	}

	public void setEnableDate(Date enableDate){
		this.enableDate=enableDate;
	}

	public Date getEnableDate(){
		return this.enableDate;
	}

	public void setIfStatus(Integer ifStatus){
		this.ifStatus=ifStatus;
	}

	public Integer getIfStatus(){
		return this.ifStatus;
	}

	public void setVhclPrice(Double vhclPrice){
		this.vhclPrice=vhclPrice;
	}

	public Double getVhclPrice(){
		return this.vhclPrice;
	}

	public void setIssueDate(Date issueDate){
		this.issueDate=issueDate;
	}

	public Date getIssueDate(){
		return this.issueDate;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setDisableDate(Date disableDate){
		this.disableDate=disableDate;
	}

	public Date getDisableDate(){
		return this.disableDate;
	}

	public void setTrimName(String trimName){
		this.trimName=trimName;
	}

	public String getTrimName(){
		return this.trimName;
	}

	public void setMaterialId(Long materialId){
		this.materialId=materialId;
	}

	public Long getMaterialId(){
		return this.materialId;
	}

	public void setMaterialName(String materialName){
		this.materialName=materialName;
	}

	public String getMaterialName(){
		return this.materialName;
	}

	public void setOrderFlag(Integer orderFlag){
		this.orderFlag=orderFlag;
	}

	public Integer getOrderFlag(){
		return this.orderFlag;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setSalePrice(Double salePrice){
		this.salePrice=salePrice;
	}

	public Double getSalePrice(){
		return this.salePrice;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setColorName(String colorName){
		this.colorName=colorName;
	}

	public String getColorName(){
		return this.colorName;
	}

	public void setColorCode(String colorCode){
		this.colorCode=colorCode;
	}

	public String getColorCode(){
		return this.colorCode;
	}

	public void setTrimCode(String trimCode){
		this.trimCode=trimCode;
	}

	public String getTrimCode(){
		return this.trimCode;
	}

	public void setMaterialCode(String materialCode){
		this.materialCode=materialCode;
	}

	public String getMaterialCode(){
		return this.materialCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public Integer getIsSales() {
		return isSales;
	}

	public void setIsSales(Integer isSales) {
		this.isSales = isSales;
	}

	public Integer getIsEc() {
		return isEc;
	}

	public void setIsEc(Integer isEc) {
		this.isEc = isEc;
	}
	
}