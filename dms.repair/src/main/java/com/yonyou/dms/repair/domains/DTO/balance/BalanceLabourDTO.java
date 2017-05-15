
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceLabourDTO.java
*
* @Author : ZhengHe
*
* @Date : 2016年9月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.balance;

import javax.validation.constraints.Digits;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 结算单维修项目明细DTO
* @author ZhengHe
* @date 2016年9月26日
*/

public class BalanceLabourDTO {
    @Required
    private Long balanceLabourId;

    @Required
    private Long balanceId;

    private Long roLabourId;

    private Integer repairTypeCode;

    private String chargePartitionCode;

    private String labourCode;

    private String labourName;

    private String localLabourCode;

    private String localLabourName;
    
    @Digits(integer=10,fraction=2)
    private Double stdLabourHour;

    @Digits(integer=10,fraction=2)
    private Double assignLabourHour;

    @Digits(integer=8,fraction=2)
    private Double labourPrice;

    @Digits(integer=12,fraction=2)
    private Double labourAmount;

    @Digits(integer=5,fraction=4)
    private Double discount;

    @Digits(integer=12,fraction=2)
    private Double afterDiscountAmount;

    private String troubleDesc;

    private String technician;

    private String workerTypeCode;

    private String remark;

    private Integer assignTag;

    private String activityCode;

    private String packageCode;

    private String modelLabourCode;

    public Long getBalanceLabourId() {
        return balanceLabourId;
    }

    public void setBalanceLabourId(Long balanceLabourId) {
        this.balanceLabourId = balanceLabourId;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getRoLabourId() {
        return roLabourId;
    }

    public void setRoLabourId(Long roLabourId) {
        this.roLabourId = roLabourId;
    }

    public Integer getRepairTypeCode() {
        return repairTypeCode;
    }

    public void setRepairTypeCode(Integer repairTypeCode) {
        this.repairTypeCode = repairTypeCode;
    }

    public String getChargePartitionCode() {
        return chargePartitionCode;
    }

    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
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

    public Double getStdLabourHour() {
        return stdLabourHour;
    }

    public void setStdLabourHour(Double stdLabourHour) {
        this.stdLabourHour = stdLabourHour;
    }

    public Double getAssignLabourHour() {
        return assignLabourHour;
    }

    public void setAssignLabourHour(Double assignLabourHour) {
        this.assignLabourHour = assignLabourHour;
    }

    public Double getLabourPrice() {
        return labourPrice;
    }

    public void setLabourPrice(Double labourPrice) {
        this.labourPrice = labourPrice;
    }

    public Double getLabourAmount() {
        return labourAmount;
    }

    public void setLabourAmount(Double labourAmount) {
        this.labourAmount = labourAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAfterDiscountAmount() {
        return afterDiscountAmount;
    }

    public void setAfterDiscountAmount(Double afterDiscountAmount) {
        this.afterDiscountAmount = afterDiscountAmount;
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

    public String getWorkerTypeCode() {
        return workerTypeCode;
    }

    public void setWorkerTypeCode(String workerTypeCode) {
        this.workerTypeCode = workerTypeCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getModelLabourCode() {
        return modelLabourCode;
    }

    public void setModelLabourCode(String modelLabourCode) {
        this.modelLabourCode = modelLabourCode;
    }
}
