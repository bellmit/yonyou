package com.yonyou.dms.manage.domains.DTO.basedata.organization;

/**
 * 经销商集团维护
 * @author Administrator
 *
 */
public class DealerGroupDTO {
	
	private Long groupId;
	private String groupCode;
	private String groupName;
	private String groupShortName;
	private Integer status;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupShortName() {
		return groupShortName;
	}
	public void setGroupShortName(String groupShortName) {
		this.groupShortName = groupShortName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
