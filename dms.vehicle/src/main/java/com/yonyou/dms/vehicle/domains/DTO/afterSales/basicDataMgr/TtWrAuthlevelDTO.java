package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

public class TtWrAuthlevelDTO {

	private Integer levelTier;
	private Date updateDate;
	private String levelName;
	private String levelCode;
	private Long updateBy;
	private Long createBy;
	private Integer ver;
	private Long oemCompanyId;
	private Date createDate;
	private Integer isDel;

	public void setLevelTier(Integer levelTier){
		this.levelTier=levelTier;
	}

	public Integer getLevelTier(){
		return this.levelTier;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setLevelName(String levelName){
		this.levelName=levelName;
	}

	public String getLevelName(){
		return this.levelName;
	}

	public void setLevelCode(String levelCode){
		this.levelCode=levelCode;
	}

	public String getLevelCode(){
		return this.levelCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
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

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

}
