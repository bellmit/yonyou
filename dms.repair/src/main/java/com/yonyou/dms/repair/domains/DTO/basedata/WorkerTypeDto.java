/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkerTypeDto.java
*
* @Author : jcsi
*
* @Date : 2016年7月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月8日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/



package com.yonyou.dms.repair.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 工种定义
* @author jcsi
* @date 2016年7月29日
 */
public class WorkerTypeDto {
	
    @Required
	private String workerTypeCode;   //工种代码
	
    @Required
	private String workerTypeName;  //工种名称
	
    @Required
	private Integer workerType;   //工种类型
	
	private Integer isValid;  //是否有效

	public String getWorkerTypeCode() {
		return workerTypeCode;
	}

	public void setWorkerTypeCode(String workerTypeCode) {
		this.workerTypeCode = workerTypeCode;
	}

	public String getWorkerTypeName() {
		return workerTypeName;
	}

	public void setWorkerTypeName(String workerTypeName) {
		this.workerTypeName = workerTypeName;
	}

	public Integer getWorkerType() {
		return workerType;
	}

	public void setWorkerType(Integer workerType) {
		this.workerType = workerType;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
	
	
	

}
