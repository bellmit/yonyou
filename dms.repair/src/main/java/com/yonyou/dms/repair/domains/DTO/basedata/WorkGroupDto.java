/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkGroupDto.java
*
* @Author : jcsi
*
* @Date : 2016年7月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月8日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* WorkGroupDto
* @author xukl
* @date 2016年7月8日
*/
	
public class WorkGroupDto {
    @Required
    private String workgroupCode;
    
    @Required
    private String workgroupName;
    
    private String orgCode;
    
    private String workerTypeCode;
    
    private String workerType;
    
    public String getWorkgroupCode() {
        return workgroupCode;
    }

    
    public void setWorkgroupCode(String workgroupCode) {
        this.workgroupCode = workgroupCode;
    }

    
    public String getWorkgroupName() {
        return workgroupName;
    }

    
    public void setWorkgroupName(String workgroupName) {
        this.workgroupName = workgroupName;
    }


	public String getOrgCode() {
		return orgCode;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	public String getWorkerTypeCode() {
		return workerTypeCode;
	}


	public void setWorkerTypeCode(String workerTypeCode) {
		this.workerTypeCode = workerTypeCode;
	}


	public String getWorkerType() {
		return workerType;
	}


	public void setWorkerType(String workerType) {
		this.workerType = workerType;
	}


   
}