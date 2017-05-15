
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : UserMenuActionDto.java
 *
 * @Author : yll
 *
 * @Date : 2016年8月19日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月19日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.domains.DTO.basedata.user;


/**
 * 用户菜单操作权限Dto
 * @author yll
 * @date 2016年8月19日
 */

public class UserMenuActionDto {

	private Integer userMenuCuringId;
	private Integer userMenuId;
	private String dealerCode;
	private String actionCode;
	private String actionName;
	private String module;//模块
	private String actionctl;//是否启用权限控制
	public Integer getUserMenuCuringId() {
		return userMenuCuringId;
	}
	public void setUserMenuCuringId(Integer userMenuCuringId) {
		this.userMenuCuringId = userMenuCuringId;
	}
	public Integer getUserMenuId() {
		return userMenuId;
	}
	public void setUserMenuId(Integer userMenuId) {
		this.userMenuId = userMenuId;
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
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getActionctl() {
		return actionctl;
	}
	public void setActionctl(String actionctl) {
		this.actionctl = actionctl;
	}


}
