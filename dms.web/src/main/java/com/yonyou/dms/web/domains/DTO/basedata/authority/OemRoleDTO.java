package com.yonyou.dms.web.domains.DTO.basedata.authority;

import java.util.Date;

public class OemRoleDTO {
	private String roleDesc;
	private Integer roleType;
	private Long oemCompanyId;
	private Long updateBy;
	private Integer roleStatus;
	private Date updateDate;
	private Long createBy;
	private Date createDate;
	private String roleName;
	private Long roleId;
	private String nowTree;

	public String getNowTree() {
		return nowTree;
	}

	public void setNowTree(String nowTree) {
		this.nowTree = nowTree;
	}

	public void setRoleDesc(String roleDesc){
		this.roleDesc=roleDesc;
	}

	public String getRoleDesc(){
		return this.roleDesc;
	}

	public void setRoleType(Integer roleType){
		this.roleType=roleType;
	}

	public Integer getRoleType(){
		return this.roleType;
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

	public void setRoleStatus(Integer roleStatus){
		this.roleStatus=roleStatus;
	}

	public Integer getRoleStatus(){
		return this.roleStatus;
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

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setRoleName(String roleName){
		this.roleName=roleName;
	}

	public String getRoleName(){
		return this.roleName;
	}

	public void setRoleId(Long roleId){
		this.roleId=roleId;
	}

	public Long getRoleId(){
		return this.roleId;
	}
}
