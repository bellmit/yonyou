
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : PositionDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    DuPengXin   1.0
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
* @date 2016年7月15日
*/

public class RepairPositionDTO {
    private Long repairPositionId;

    private String dealerCode;
    
    @Required
    private String labourPositionCode; //工位代码
    
    @Required
    private String labourPositionName; //工位名称

    private Integer labourPositionType; //工位类型

    private Integer isValid; //是否有效
    
    public Long getRepairPositionId() {
        return repairPositionId;
    }

    public void setRepairPositionId(Long repairPositionId) {
        this.repairPositionId = repairPositionId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getLabourPositionCode() {
        return labourPositionCode;
    }

    public void setLabourPositionCode(String labourPositionCode) {
        this.labourPositionCode = labourPositionCode == null ? null : labourPositionCode.trim();
    }

    public String getLabourPositionName() {
        return labourPositionName;
    }

    public void setLabourPositionName(String labourPositionName) {
        this.labourPositionName = labourPositionName == null ? null : labourPositionName.trim();
    }

    public Integer getLabourPositionType() {
        return labourPositionType;
    }

    public void setLabourPositionType(Integer labourPositionType) {
        this.labourPositionType = labourPositionType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
