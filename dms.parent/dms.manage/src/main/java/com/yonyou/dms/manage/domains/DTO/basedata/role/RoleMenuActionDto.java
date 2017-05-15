
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : RoleMenuActionDto.java
 *
 * @Author : Administrator
 *
 * @Date : 2016年7月28日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月28日    Administrator    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.domains.DTO.basedata.role;

/**
 * 角色菜单操作权限Dto
 * @author yll
 * @date 2016年7月28日
 */

public class RoleMenuActionDto {
	private Integer roleMenuCuringId;
	private Integer roleMenuId;
	private String dealerCode;
	private String actionCode;
	private String actionName;


	///////

	private String menuId;

	private String check;
	public Integer getRoleMenuCuringId() {
		return roleMenuCuringId;
	}
	public void setRoleMenuCuringId(Integer roleMenuCuringId) {
		this.roleMenuCuringId = roleMenuCuringId;
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
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}


}
