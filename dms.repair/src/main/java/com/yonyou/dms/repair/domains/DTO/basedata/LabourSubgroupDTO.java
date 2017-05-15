
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourSubgroupDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年8月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月4日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* @author DuPengXin
* @date 2016年8月4日
*/

public class LabourSubgroupDTO {

    private String mainGroupCode;

    @Required
    private String subGroupCode;
    
    @Required
    private String subGroupName;
    
    
    public String getMainGroupCode() {
		return mainGroupCode;
	}


	public void setMainGroupCode(String mainGroupCode) {
		this.mainGroupCode = mainGroupCode;
	}


	public String getSubGroupCode() {
        return subGroupCode;
    }

    
    public void setSubGroupCode(String subGroupCode) {
        this.subGroupCode = subGroupCode;
    }

    
    public String getSubGroupName() {
        return subGroupName;
    }

    
    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

}
