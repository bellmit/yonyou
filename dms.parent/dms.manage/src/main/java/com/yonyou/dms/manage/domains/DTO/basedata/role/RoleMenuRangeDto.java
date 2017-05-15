
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : RoleMenuRangeDto.java
*
* @Author : yll
*
* @Date : 2016年7月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月28日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.domains.DTO.basedata.role;

/**
* 角色菜单数据权限
* @author yll
* @date 2016年7月28日
*/

public class RoleMenuRangeDto {

	private Integer roleMenuRangeId;
	private Integer roleMenuId;
	private String dealerCode;
	private Integer rangeCode;
	
	public Integer getRoleMenuRangeId() {
		return roleMenuRangeId;
	}
	public void setRoleMenuRangeId(Integer roleMenuRangeId) {
		this.roleMenuRangeId = roleMenuRangeId;
	}
	public Integer getRoleMenuId() {
		return roleMenuId;
	}
	public void setRoleMenuId(Integer roleMenuId) {
		this.roleMenuId = roleMenuId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getRangeCode() {
		return rangeCode;
	}
	public void setRangeCode(Integer rangeCode) {
		this.rangeCode = rangeCode;
	}
	
	
}
