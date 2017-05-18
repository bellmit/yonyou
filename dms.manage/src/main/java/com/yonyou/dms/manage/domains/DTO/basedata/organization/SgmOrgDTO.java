package com.yonyou.dms.manage.domains.DTO.basedata.organization;

/**
 * 公司组织维护
 * @author Administrator
 *
 */
public class SgmOrgDTO {
	private Long orgId;				//上级部门id
	private String orgName;			//上级部门名称
	private Long deptId;
	private String deptCode;		//组织代码
	private String deptName;		//组织名称
	private Integer deptStatus;		//组织状态
	private Integer deptType;		//组织类型
	private Integer busiType;		//业务类型
	private String deptDesc;		//备注
	private String areaIds;         //业务范围
	
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getDeptStatus() {
		return deptStatus;
	}
	public void setDeptStatus(Integer deptStatus) {
		this.deptStatus = deptStatus;
	}
	public Integer getDeptType() {
		return deptType;
	}
	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}
	public Integer getBusiType() {
		return busiType;
	}
	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}
	public String getDeptDesc() {
		return deptDesc;
	}
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
	public String getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}
	
	
}
