package com.yonyou.dms.vehicle.domains.DTO.afterSales.weixinreserve;

import java.util.Date;

public class TmWxMaintainPartDTO {
	private Integer isMain;
	private Integer dealType;
	private Double price;
	private Integer isWxSend;
	private Date updateDate;
	private String partName;
	private Long createBy;
	private String partCode;
	private Double amount;
	private String labourCode;
	private Integer isDel;
	private Long updateBy;
	private Long id;
	private Integer ver;
	private Long packageId;
	private Integer isDmsSend;
	private Date createDate;
	private Double fee;

	public void setIsMain(Integer isMain){
		this.isMain=isMain;
	}

	public Integer getIsMain(){
		return this.isMain;
	}

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

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setPartName(String partName){
		this.partName=partName;
	}

	public String getPartName(){
		return this.partName;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setPartCode(String partCode){
		this.partCode=partCode;
	}

	public String getPartCode(){
		return this.partCode;
	}

	public void setAmount(Double amount){
		this.amount=amount;
	}

	public Double getAmount(){
		return this.amount;
	}

	public void setLabourCode(String labourCode){
		this.labourCode=labourCode;
	}

	public String getLabourCode(){
		return this.labourCode;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
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
