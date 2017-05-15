package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

import com.yonyou.dms.framework.domain.OemBaseModel;

/**
 * 工时
 * @author Administrator
 *
 */
public class TtWrMaintainLabourDTO extends OemBaseModel{
	private Float frt;
	private Date updateDate;
	private Long createBy;
	private Integer ver;
	private String labourCode;
	private String labourName;
	private Date createDate;
	private Double fee;
	private Long updateBy;
	private Double price;
	private Long packageId;
	private Integer isDel;
	private Long id;
	private Integer dealType;

	public void setFrt(Float frt){
		this.frt=frt;
	}

	public Float getFrt(){
		return this.frt;
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

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setLabourCode(String labourCode){
		this.labourCode=labourCode;
	}

	public String getLabourCode(){
		return this.labourCode;
	}

	public void setLabourName(String labourName){
		this.labourName=labourName;
	}

	public String getLabourName(){
		return this.labourName;
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

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return this.price;
	}

	public void setPackageId(Long packageId){
		this.packageId=packageId;
	}

	public Long getPackageId(){
		return this.packageId;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setDealType(Integer dealType){
		this.dealType=dealType;
	}

	public Integer getDealType(){
		return this.dealType;
	}
}
