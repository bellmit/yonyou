package com.yonyou.dms.vehicle.domains.DTO.afterSales.weixinreserve;

import java.util.Date;

public class TmWxMaintainLabourDTO {
	private Integer dealType;
	private Double price;
	private Integer isWxSend;
	private String labourName;
	private Date updateDate;
	private Long createBy;
	private Float frt;
	private Integer isDel;
	private String labourCode;
	private Long updateBy;
	private Long id;
	private Integer ver;
	private Long packageId;
	private Integer isDmsSend;
	private Date createDate;
	private Double fee;

	public void setDealType(Integer dealType){
		this.dealType=dealType;
	}

	public Integer getDealType(){
		return this.dealType;
	}

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return this.price;
	}

	public void setIsWxSend(Integer isWxSend){
		this.isWxSend=isWxSend;
	}

	public Integer getIsWxSend(){
		return this.isWxSend;
	}

	public void setLabourName(String labourName){
		this.labourName=labourName;
	}

	public String getLabourName(){
		return this.labourName;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setFrt(Float frt){
		this.frt=frt;
	}

	public Float getFrt(){
		return this.frt;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setLabourCode(String labourCode){
		this.labourCode=labourCode;
	}

	public String getLabourCode(){
		return this.labourCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setPackageId(Long packageId){
		this.packageId=packageId;
	}

	public Long getPackageId(){
		return this.packageId;
	}

	public void setIsDmsSend(Integer isDmsSend){
		this.isDmsSend=isDmsSend;
	}

	public Integer getIsDmsSend(){
		return this.isDmsSend;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setFee(Double fee){
		this.fee=fee;
	}

	public Double getFee(){
		return this.fee;
	}
}
