
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : UserMenuDto.java
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
 * 用户菜单Dto
 * @author yll
 * @date 2016年8月19日
 */

public class UserMenuDto {

	private Integer userMenuId;
	private Integer userId;
	private String dealerCode;
	private Integer menuId;
	public Integer getUserMenuId() {
		return userMenuId;
	}
	public void setUserMenuId(Integer userMenuId) {
		this.userMenuId = userMenuId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
