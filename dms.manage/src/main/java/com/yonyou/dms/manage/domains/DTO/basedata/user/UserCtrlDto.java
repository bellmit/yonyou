
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserCtrlDto.java
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
* 用户受控权限Dto
* @author yll
* @date 2016年8月19日
*/

public class UserCtrlDto {
	private Integer userCtrlId;
	private Integer userId;
	private String dealerCode;
	private String ctrlCode;
	public Integer getUserCtrlId() {
		return userCtrlId;
	}
	public void setUserCtrlId(Integer userCtrlId) {
		this.userCtrlId = userCtrlId;
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
	public String getCtrlCode() {
		return ctrlCode;
	}
	public void setCtrlCode(String ctrlCode) {
		this.ctrlCode = ctrlCode;
	}
	
	
}
