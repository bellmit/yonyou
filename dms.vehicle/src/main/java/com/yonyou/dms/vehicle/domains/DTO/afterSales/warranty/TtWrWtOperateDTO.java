package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrWtOperateDTO {
	
	private Long actId;
	private Date updateDate;
	private Long createBy;
	private String optCode;
	private Integer isDel;
	private String optNameCn;
	private Long oemCompanyId;
	private String mvs;
	private Long updateBy;
	private Long id;
	private Integer ver;
	private Long optId;
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

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setOptCode(String optCode){
		this.optCode=optCode;
	}

	public String getOptCode(){
		return this.optCode;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setOptNameCn(String optNameCn){
		this.optNameCn=optNameCn;
	}

	public String getOptNameCn(){
		return this.optNameCn;
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

	public void setOptId(Long optId){
		this.optId=optId;
	}

	public Long getOptId(){
		return this.optId;
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
