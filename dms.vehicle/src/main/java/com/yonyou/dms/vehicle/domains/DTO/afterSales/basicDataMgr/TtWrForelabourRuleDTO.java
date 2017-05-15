package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;
import java.util.List;

public class TtWrForelabourRuleDTO {
	private Long oemCompanyId;
	private Long ruleId;
	private Long updateBy;
	private Date updateDate;
	private Long modelGroup;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private List<String> authLevel;
	
	private Integer isDel;
	private String labourCode;
	private Long groupId;
	
	
	
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public List<String> getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(List<String> authLevel) {
		this.authLevel = authLevel;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

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

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setModelGroup(Long modelGroup){
		this.modelGroup=modelGroup;
	}

	public Long getModelGroup(){
		return this.modelGroup;
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

/*	public void setAuthLevel(String authLevel){
		this.authLevel=authLevel;
	}

	public String getAuthLevel(){
		return this.authLevel;
	}*/

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setLabourCode(String labourCode){
		this.labourCode=labourCode;
	}

	public String getLabourCode(){
		return this.labourCode;
	}
}
