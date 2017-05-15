
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairTypeDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年7月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月27日    DuPengXin   1.0
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
* @date 2016年7月27日
*/

public class RepairTypeDTO {
    private Long repairTypeId;

    private String dealerCode;
    
    @Required
    private String repairTypeCode;
    
    @Required
    private String repairTypeName;

    private Integer isReserved;
    
    private String remark;
    
    public Long getRepairTypeId() {
        return repairTypeId;
    }

    
    public void setRepairTypeId(Long repairTypeId) {
        this.repairTypeId = repairTypeId;
    }

    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    public String getRepairTypeCode() {
        return repairTypeCode;
    }

    
    public void setRepairTypeCode(String repairTypeCode) {
        this.repairTypeCode = repairTypeCode;
    }

    
    public String getRepairTypeName() {
        return repairTypeName;
    }

    
    public void setRepairTypeName(String repairTypeName) {
        this.repairTypeName = repairTypeName;
    }

    
    public Integer getIsReserved() {
        return isReserved;
    }

    
    public void setIsReserved(Integer isReserved) {
        this.isReserved = isReserved;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   
}
