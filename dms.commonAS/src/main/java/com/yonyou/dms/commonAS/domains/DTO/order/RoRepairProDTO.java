
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.commonAS
*
* @File name : RoRepairProDTO.java
*
* @Author : rongzoujie
*
* @Date : 2016年9月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月22日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.commonAS.domains.DTO.order;



/**
* TODO description
* @author rongzoujie
* @date 2016年9月22日
*/

public class RoRepairProDTO {
    private Long roLabourId;//维修项目id
    private Long roId;//工单id
    private String roNo;//工单号
    private String repairType;//维修分类
    private String chargePartitionName;//收费区分
    private String chargePartitionCode;
    private String labourCode;//维修项目代码
    private String labourName;//维修项目名称
    private String localLabourCode;//行管项目代码
    private String localLabourName;//行管项目名称
    private Double stdHour;//标准工时
    private Double assignLabourHour;//派工工时
    private Double workHourSinglePrice;//工时单价
    private Double workHourPrice;//工时费
    private Double discountRate;//折扣率
    private Double receiveMoney;//折后总金额
    private String troubleDesc;//故障描述
    private String technician;//责任技师
    private String technicianNO;//责任技师代码
    private String workType;//工种
    private String workTypeCode;//工种代码
    private Integer assignTag;//派工标志
    private Integer assignCode;//派工标志代码
    private String activityCode;//活动编号
    private String packageCode;//组合代码
    private String repairTypeCode;//维修类型代码
    private String modeGroup;//维修车型分组代码
    private String modelLabourName;//维修车型分组名
    private String modelLabourCode;//维修车型组代码
    
    
    public Long getRoLabourId() {
        return roLabourId;
    }
    
    public void setRoLabourId(Long roLabourId) {
        this.roLabourId = roLabourId;
    }
    
    public Long getRoId() {
        return roId;
    }
    
    public void setRoId(Long roId) {
        this.roId = roId;
    }
    
    public String getRoNo() {
        return roNo;
    }
    
    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }
    
//    public String getRepairType() {
//        return repairType;
//    }
//    
//    public void setRepairType(String repairType) {
//        this.repairType = repairType;
//    }
    
    public String getchargePartitionName() {
        return chargePartitionName;
    }
    
    public void setchargePartitionName(String chargePartitionName) {
        this.chargePartitionName = chargePartitionName;
    }
    
    public String getLabourCode() {
        return labourCode;
    }
    
    public void setLabourCode(String labourCode) {
        this.labourCode = labourCode;
    }
    
    public String getLabourName() {
        return labourName;
    }
    
    public void setLabourName(String labourName) {
        this.labourName = labourName;
    }
    
    public String getLocalLabourCode() {
        return localLabourCode;
    }
    
    public void setLocalLabourCode(String localLabourCode) {
        this.localLabourCode = localLabourCode;
    }
    
    public String getLocalLabourName() {
        return localLabourName;
    }
    
    public void setLocalLabourName(String localLabourName) {
        this.localLabourName = localLabourName;
    }
    
    public Double getStdHour() {
        return stdHour;
    }
    
    public void setStdHour(Double stdHour) {
        this.stdHour = stdHour;
    }
    
    public Double getAssignLabourHour() {
        return assignLabourHour;
    }
    
    public void setAssignLabourHour(Double assignLabourHour) {
        this.assignLabourHour = assignLabourHour;
    }
    
    public Double getWorkHourSinglePrice() {
        return workHourSinglePrice;
    }
    
    public void setWorkHourSinglePrice(Double workHourSinglePrice) {
        this.workHourSinglePrice = workHourSinglePrice;
    }
    
    public Double getWorkHourPrice() {
        return workHourPrice;
    }
    
    public void setWorkHourPrice(Double workHourPrice) {
        this.workHourPrice = workHourPrice;
    }
    
    public Double getDiscountRate() {
        return discountRate;
    }
    
    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }
    
    public Double getReceiveMoney() {
        return receiveMoney;
    }
    
    public void setReceiveMoney(Double receiveMoney) {
        this.receiveMoney = receiveMoney;
    }
    
    public String getTroubleDesc() {
        return troubleDesc;
    }
    
    public void setTroubleDesc(String troubleDesc) {
        this.troubleDesc = troubleDesc;
    }
    
    public String getTechnician() {
        return technician;
    }
    
    public void setTechnician(String technician) {
        this.technician = technician;
    }
    
    public String getWorkType() {
        return workType;
    }
    
    public void setWorkType(String workType) {
        this.workType = workType;
    }
    
    public Integer getAssignTag() {
        return assignTag;
    }
    
    public void setAssignTag(Integer assignTag) {
        this.assignTag = assignTag;
    }
    
    public String getActivityCode() {
        return activityCode;
    }
    
    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }
    
    public String getPackageCode() {
        return packageCode;
    }
    
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
    
    public String getRepairTypeCode() {
        return repairTypeCode;
    }
    
    public void setRepairTypeCode(String repairTypeCode) {
        this.repairTypeCode = repairTypeCode;
    }
    
    public String getModeGroup() {
        return modeGroup;
    }
    
    public void setModeGroup(String modeGroup) {
        this.modeGroup = modeGroup;
    }

    
//    public String getChargePartitionName() {
//        return chargePartitionName;
//    }
//
//    
//    public void setChargePartitionName(String chargePartitionName) {
//        this.chargePartitionName = chargePartitionName;
//    }

    
    public String getModelLabourName() {
        return modelLabourName;
    }

    
    public void setModelLabourName(String modelLabourName) {
        this.modelLabourName = modelLabourName;
    }

    
    public String getChargePartitionCode() {
        return chargePartitionCode;
    }

    
    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
    }

    
    public String getTechnicianNO() {
        return technicianNO;
    }

    
    public void setTechnicianNO(String technicianNO) {
        this.technicianNO = technicianNO;
    }

    
    public String getWorkTypeCode() {
        return workTypeCode;
    }

    
    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
    }

    
    public Integer getAssignCode() {
        return assignCode;
    }

    
    public void setAssignCode(Integer assignCode) {
        this.assignCode = assignCode;
    }

    
    public String getModelLabourCode() {
        return modelLabourCode;
    }

    
    public void setModelLabourCode(String modelLabourCode) {
        this.modelLabourCode = modelLabourCode;
    }

    
    public String getRepairType() {
        return repairType;
    }

    
    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

//    public String getChargePartitionName() {
//        return chargePartitionName;
//    }
//
//    
//    public void setChargePartitionName(String chargePartitionName) {
//        this.chargePartitionName = chargePartitionName;
//    }
//    
}
