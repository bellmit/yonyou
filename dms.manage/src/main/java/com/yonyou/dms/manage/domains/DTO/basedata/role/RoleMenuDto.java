
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : RoleMenuDto.java
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
 * 角色菜单权限dto
 * @author yll
 * @date 2016年7月28日
 */

public class RoleMenuDto {

	private Integer roleMenuId;
	private Integer roleId;
	private String dealerCode;
	private Integer menuId;

	public Integer getRoleMenuId() {
		return roleMenuId;
	}
	public void setRoleMenuId(Integer roleMenuId) {
		this.roleMenuId = roleMenuId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}



}
