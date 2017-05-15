package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrSpecialPartDTO {
	
	private String wrPrice;
	private Date updateDate;
	private Long createBy;
	private Integer status;
	private Integer isDel;
	private String ptCode;
	private Long oemCompanyId;
	private String mvs;
	private Long updateBy;
	private Integer isCanMod;
	private Long id;
	private Integer ver;
	private String ptName;
	private Date createDate;
	private Integer isArc;

	public void setWrPrice(String wrPrice){
		this.wrPrice=wrPrice;
	}

	public String getWrPrice(){
		return this.wrPrice;
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

	public void setPtCode(String ptCode){
		this.ptCode=ptCode;
	}

	public String getPtCode(){
		return this.ptCode;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setMvs(String mvs){
		this.mvs=mvs;
	}

	public String getMvs(){
		return this.mvs;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setIsCanMod(Integer isCanMod){
		this.isCanMod=isCanMod;
	}

	public Integer getIsCanMod(){
		return this.isCanMod;
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

	public void setPtName(String ptName){
		this.ptName=ptName;
	}

	public String getPtName(){
		return this.ptName;
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
