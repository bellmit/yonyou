package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import java.util.Date;

public class TtOpOldpartImpDTO {

	private Long ruleId;
	private Long updateBy;
	private Long partImp;
	private Date updateDate;
	private String partName;
	private Integer ver;
	private Long createBy;
	private Integer oldpartImpType;
	private String partNo;
	private Date createDate;
	private Integer isArc;
	private Integer isDel;

	public void setRuleId(Long ruleId){
		this.ruleId=ruleId;
	}

	public Long getRuleId(){
		return this.ruleId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setPartImp(Long partImp){
		this.partImp=partImp;
	}

	public Long getPartImp(){
		return this.partImp;
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

	public void setOldpartImpType(Integer oldpartImpType){
		this.oldpartImpType=oldpartImpType;
	}

	public Integer getOldpartImpType(){
		return this.oldpartImpType;
	}

	public void setPartNo(String partNo){
		this.partNo=partNo;
	}

	public String getPartNo(){
		return this.partNo;
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

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}
}
