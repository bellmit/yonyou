
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : OrderDispatchDetailTransFormDTO.java
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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;

/**
 * 派工传输dto
* @author rongzoujie
* @date 2016年9月27日
*/

public class OrderDispatchDetailTransFormDTO {
    private String dispatchTechnicianSelect;// 派工技师
    private Long dispatchPositions;// 派工工位 
    private Long workTypeId;//工种类型
    private Double assignLabourHourForDisPatch;//派工工时 
    private String selectRepairOrder;// 选择的工单
    private String selectRepairPro;//选中的维修项目
    private String finishUser;//终检人
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date itemStartTime;// 开工时间 
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date estimateEndTime;//预计完工时间
    
    public String getDispatchTechnicianSelect() {
        return dispatchTechnicianSelect;
    }
    
    public void setDispatchTechnicianSelect(String dispatchTechnicianSelect) {
        this.dispatchTechnicianSelect = dispatchTechnicianSelect;
    }
    
    public Long getDispatchPositions() {
        return dispatchPositions;
    }
    
    public void setDispatchPositions(Long dispatchPositions) {
        this.dispatchPositions = dispatchPositions;
    }
    
    public Long getWorkTypeId() {
        return workTypeId;
    }
    
    public void setWorkTypeId(Long workTypeId) {
        this.workTypeId = workTypeId;
    }
    
    public Double getAssignLabourHourForDisPatch() {
        return assignLabourHourForDisPatch;
    }
    
    public void setAssignLabourHourForDisPatch(Double assignLabourHourForDisPatch) {
        this.assignLabourHourForDisPatch = assignLabourHourForDisPatch;
    }
    
    
    public String getSelectRepairOrder() {
        return selectRepairOrder;
    }
    
    public void setSelectRepairOrder(String selectRepairOrder) {
        this.selectRepairOrder = selectRepairOrder;
    }
    
    public String getSelectRepairPro() {
        return selectRepairPro;
    }
    
    public void setSelectRepairPro(String selectRepairPro) {
        this.selectRepairPro = selectRepairPro;
    }

    
    public String getFinishUser() {
        return finishUser;
    }

    
    public void setFinishUser(String finishUser) {
        this.finishUser = finishUser;
    }

    
    public Date getItemStartTime() {
        return itemStartTime;
    }

    
    public void setItemStartTime(Date itemStartTime) {
        this.itemStartTime = itemStartTime;
    }

    
    public Date getEstimateEndTime() {
        return estimateEndTime;
    }

    
    public void setEstimateEndTime(Date estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
    }
}
