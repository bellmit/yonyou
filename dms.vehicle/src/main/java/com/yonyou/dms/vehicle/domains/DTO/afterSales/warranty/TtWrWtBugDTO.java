package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrWtBugDTO {
	
	private Long actId;
	private Date updateDate;
	private String bugName;
	private Long createBy;
	private Integer isDel;
	private String bugCode;
	private Long oemCompanyId;
	private String mvs;
	private Long updateBy;
	private Long id;
	private Integer ver;
	private Date createDate;
	private Integer isArc;
	private Integer tag;

	public void setActId(Long actId){
		this.actId=actId;
	}

	public Long getActId(){
		return this.actId;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setBugName(String bugName){
		this.bugName=bugName;
	}

	public String getBugName(){
		return this.bugName;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setBugCode(String bugCode){
		this.bugCode=bugCode;
	}

	public String getBugCode(){
		return this.bugCode;
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

	public void setTag(Integer tag){
		this.tag=tag;
	}

	public Integer getTag(){
		return this.tag;
	}
}
