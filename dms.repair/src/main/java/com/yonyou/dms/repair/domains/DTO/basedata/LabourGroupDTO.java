
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourGroupDTO.java
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

public class LabourGroupDTO {
    private String dealerCode;
    
    @Required
    private String mainGroupCode;
    
    @Required
    private String mainGroupName;

    private Integer downTag;
    
    private LabourSubgroupDTO labourSubgroupDto;
    
    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    public String getMainGroupCode() {
        return mainGroupCode;
    }

    
    public void setMainGroupCode(String mainGroupCode) {
        this.mainGroupCode = mainGroupCode;
    }

    
    public String getMainGroupName() {
        return mainGroupName;
    }

    
    public void setMainGroupName(String mainGroupName) {
        this.mainGroupName = mainGroupName;
    }

    

    public Integer getDownTag() {
		return downTag;
	}


	public void setDownTag(Integer downTag) {
		this.downTag = downTag;
	}


	public LabourSubgroupDTO getLabourSubgroupDto() {
        return labourSubgroupDto;
    }


    public void setLabourSubgroupDto(LabourSubgroupDTO labourSubgroupDto) {
        this.labourSubgroupDto = labourSubgroupDto;
    }

}
