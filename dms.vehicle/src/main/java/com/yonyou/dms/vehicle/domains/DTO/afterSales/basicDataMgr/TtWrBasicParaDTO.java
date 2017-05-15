package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

public class TtWrBasicParaDTO {
	private Long paraId;
	private Long dealerId;
	private Date updateDate;
	private Double partMangefee;
	private Long createBy;
	private Integer isDown;
	private Integer isDel;
	private Long oemCompanyId;
	private Integer validDays;
	private Double labourPrice;
	private Long updateBy;
	private Double taxRate;
	private Integer ver;
	private Date createDate;

	public void setParaId(Long paraId){
		this.paraId=paraId;
	}

	public Long getParaId(){
		return this.paraId;
	}

	public void setDealerId(Long dealerId){
		this.dealerId=dealerId;
	}

	public Long getDealerId(){
		return this.dealerId;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setPartMangefee(Double partMangefee){
		this.partMangefee=partMangefee;
	}

	public Double getPartMangefee(){
		return this.partMangefee;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setValidDays(Integer validDays){
		this.validDays=validDays;
	}

	public Integer getValidDays(){
		return this.validDays;
	}

	public void setLabourPrice(Double labourPrice){
		this.labourPrice=labourPrice;
	}

	public Double getLabourPrice(){
		return this.labourPrice;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setTaxRate(Double taxRate){
		this.taxRate=taxRate;
	}

	public Double getTaxRate(){
		return this.taxRate;
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
}
