
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : UserMenuRangeDto.java
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
 * 用户菜单操作范围Dto
 * @author yll
 * @date 2016年8月19日
 */

public class UserMenuRangeDto {
	private Integer userMenuRangeId;
	private Integer userMenuId;
	private String dealerCode;
	private Integer rangeCode;
	
	public Integer getUserMenuRangeId() {
		return userMenuRangeId;
	}
	public void setUserMenuRangeId(Integer userMenuRangeId) {
		this.userMenuRangeId = userMenuRangeId;
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
	public Integer getRangeCode() {
		return rangeCode;
	}
	public void setRangeCode(Integer rangeCode) {
		this.rangeCode = rangeCode;
	}

}
