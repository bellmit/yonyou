
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : OrderDispatchDetail.java
*
* @Author : rongzoujie
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.repairDispatching;

import java.util.Date;

/**
* TODO description
* @author rongzoujie
* @date 2016年9月27日
*/

public class OrderDispatchDetailDTO {
    private Long assignId;//派工id
    private Long roId;//工单id
    private Long roLabourId;//维修项目id
    private Long repairPositionId;//工位id
    private Long workTypeId;//工种id
    private String technician;//员工编号
    private Double factLabourHour;//实际工作工时
    private Date workingDay;//工作日期
    
    public Long getAssignId() {
        return assignId;
    }
    
    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }
    
    public Long getRoId() {
        return roId;
    }
    
    public void setRoId(Long roId) {
        this.roId = roId;
    }
    
    public Long getRoLabourId() {
        return roLabourId;
    }
    
    public void setRoLabourId(Long roLabourId) {
        this.roLabourId = roLabourId;
    }
    
    public Long getRepairPositionId() {
        return repairPositionId;
    }
    
    public void setRepairPositionId(Long repairPositionId) {
        this.repairPositionId = repairPositionId;
    }
    
    public Long getWorkTypeId() {
        return workTypeId;
    }
    
    public void setWorkTypeId(Long workTypeId) {
        this.workTypeId = workTypeId;
    }
    
    public String getTechnician() {
        return technician;
    }
    
    public void setTechnician(String technician) {
        this.technician = technician;
    }
    
    public Double getFactLabourHour() {
        return factLabourHour;
    }
    
    public void setFactLabourHour(Double factLabourHour) {
        this.factLabourHour = factLabourHour;
    }
    
    public Date getWorkingDay() {
        return workingDay;
    }
    
    public void setWorkingDay(Date workingDay) {
        this.workingDay = workingDay;
    }
    
    
    
}
