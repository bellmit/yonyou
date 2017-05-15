/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 *
 * @Author : zhengcong
 *
 * @Date : 2017年5月3日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年5月3日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.repair.domains.DTO.advice;

import java.util.List;

/**
 * 工单维修建议-配件DTO
 * 
 * @author zhengcong	
 * @date 2017年5月3日
 */
public class RepairAdvicePartDTO {
	private List advice_part;
	
	private String  delPID;
	

	public String getDelPID() {
		return delPID;
	}

	public void setDelPID(String delPID) {
		this.delPID = delPID;
	}

	public List getAdvice_part() {
		return advice_part;
	}

	public void setAdvice_part(List advice_part) {
		this.advice_part = advice_part;
	}
	


	

}
