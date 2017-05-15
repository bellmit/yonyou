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
 * 工单维修建议-项目DTO
 * 
 * @author zhengcong	
 * @date 2017年5月3日
 */
public class RepairAdviceLabourDTO {
    private List advice_labour;
	
	private String  delLID;

	public List getAdvice_labour() {
		return advice_labour;
	}

	public void setAdvice_labour(List advice_labour) {
		this.advice_labour = advice_labour;
	}

	public String getDelLID() {
		return delLID;
	}

	public void setDelLID(String delLID) {
		this.delLID = delLID;
	}
	
	
}
