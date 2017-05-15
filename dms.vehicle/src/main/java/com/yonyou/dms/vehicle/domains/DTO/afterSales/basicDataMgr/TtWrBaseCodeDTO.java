package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

public class TtWrBaseCodeDTO {
	private Long oemCompanyId;
	private String baseCode;
	private Long updateBy;
	private Date updateDate;
	private Long baseId;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Integer isDown;
	private String baseName;
	private String baseType;
	private Integer isDel;

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setBaseCode(String baseCode){
		this.baseCode=baseCode;
	}

	public String getBaseCode(){
		return this.baseCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setBaseId(Long baseId){
		this.baseId=baseId;
	}

	public Long getBaseId(){
		return this.baseId;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setBaseName(String baseName){
		this.baseName=baseName;
	}

	public String getBaseName(){
		return this.baseName;
	}

	public void setBaseType(String baseType){
		this.baseType=baseType;
	}

	public String getBaseType(){
		return this.baseType;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}
}
