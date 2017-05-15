
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : WorkOrderRepairDetailDTO.java
*
* @Author : rongzoujie
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.order;



/**
 * 工单维修项目明细DTO
* @author rongzoujie
* @date 2016年9月5日
*/

public class WorkOrderRepairDetailDTO {
    private Double labourPrice;//维修项目单价
    private String discountModeCode;//优惠模式
    private String repairProListIds;//维修项目id
    private String roType;//维修类型
    
    public Double getLabourPrice() {
        return labourPrice;
    }
    
    public void setLabourPrice(Double labourPrice) {
        this.labourPrice = labourPrice;
    }
    
    public String getDiscountModeCode() {
        return discountModeCode;
    }
    
    public void setDiscountModeCode(String discountModeCode) {
        this.discountModeCode = discountModeCode;
    }
    
    public String getRepairProListIds() {
        return repairProListIds;
    }
    
    public void setRepairProListIds(String repairProListIds) {
        this.repairProListIds = repairProListIds;
    }
    
    public String getRoType() {
        return roType;
    }
    
    public void setRoType(String roType) {
        this.roType = roType;
    }
}