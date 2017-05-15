package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrPartPriceDTO {
	
	private String modeCode;
	private Double rate;
	private Date updateDate;
	private Long createBy;
	private Integer status;
	private Integer isDel;
	private String mvs;
	private Long oemCompanyId;
	private Long updateBy;
	private Long id;
	private Integer ver;
	private Date createDate;
	private Integer isArc;

	public void setModeCode(String modeCode){
		this.modeCode=modeCode;
	}

	public String getModeCode(){
		return this.modeCode;
	}

	public void setRate(Double rate){
		this.rate=rate;
	}

	public Double getRate(){
		return this.rate;
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

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setMvs(String mvs){
		this.mvs=mvs;
	}

	public String getMvs(){
		return this.mvs;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
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

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}
}
