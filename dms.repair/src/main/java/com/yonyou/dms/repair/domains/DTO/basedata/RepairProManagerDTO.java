
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairProManagerDTO.java
*
* @Author : rongzoujie
*
* @Date : 2016年8月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月15日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
* TODO description
* @author rongzoujie
* @date 2016年8月15日
*/

public class RepairProManagerDTO extends DataImportDto{
    private Integer labourId;//维修项目ID
    private String dealerCode;//经销商代码
    @ExcelColumnDefine(value=1)
    @Required
    private String modelLabourCode;//维修车型分组代码
    @ExcelColumnDefine(value=2)
    @Required
    private String labourCode;//维修项目代码
    @ExcelColumnDefine(value=3)
    @Required
    private String labourName;//维修项目名称
    private String spellCode;//拼音代码
    private String repairGroupCode;//维修项目分组代码
    @ExcelColumnDefine(value=12)
    private String localLabourCode;//行管项目代码
    @ExcelColumnDefine(value=13)
    private String localLabourName;//LOCAL_LABOUR_NAME
    @ExcelColumnDefine(value=4)
    private String repairTypeDesc;
    private Integer repairName;//维修分类
    @ExcelColumnDefine(value=11)
    private String workTypeDesc;
    private String workTypeName;//工种代码
    @ExcelColumnDefine(value=5)
    private Double stdLabourHour;//标准工时
    @ExcelColumnDefine(value=6)
    private Double assignLabourHour;//派工工时
    @ExcelColumnDefine(value=7)
    private Double claimLabHour;//索赔工时
    private String operationCode;//索赔代码
    @ExcelColumnDefine(value=8)
    private String mainGroupCode;//主分类码
    @ExcelColumnDefine(value=9)
    private String subGroupCode;//二级分类码
    @ExcelColumnDefine(value=10)
    private String omeTagDesc;
    private Integer omeTag;
    private Integer recordVersion;
    private String createdBy;
    private Date createdAt;
    private String updateBy;
    private Date updateAt;
    private String workTypeCode;
    private Integer repairTypeCode;
    
    public Integer getLabourId() {
        return labourId;
    }
    
    public void setLabourId(Integer labourId) {
        this.labourId = labourId;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getModelLabourCode() {
        return modelLabourCode;
    }
    
    public void setModelLabourCode(String modelLabourCode) {
        this.modelLabourCode = modelLabourCode;
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
    
    public String getSpellCode() {
        return spellCode;
    }
    
    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }
    
    public String getRepairGroupCode() {
        return repairGroupCode;
    }
    
    public void setRepairGroupCode(String repairGroupCode) {
        this.repairGroupCode = repairGroupCode;
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
    
    public Double getClaimLabHour() {
        return claimLabHour;
    }
    
    public void setClaimLabour(Double claimLabHour) {
        this.claimLabHour = claimLabHour;
    }
    
    public String getOperationCode() {
        return operationCode;
    }
    
    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }
    
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
    
    public Integer getOmeTag() {
        return omeTag;
    }
    
    public void setOmeTag(Integer omeTag) {
        this.omeTag = omeTag;
    }
    
    public Integer getRecordVersion() {
        return recordVersion;
    }
    
    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUpdateBy() {
        return updateBy;
    }
    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    
    public Date getUpdateAt() {
        return updateAt;
    }
    
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    
    public void setClaimLabHour(Double claimLabHour) {
        this.claimLabHour = claimLabHour;
    }

    
    public Integer getRepairName() {
        return repairName;
    }

    
    public void setRepairName(Integer repairName) {
        this.repairName = repairName;
    }

    
    public String getWorkTypeName() {
        return workTypeName;
    }

    
    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    
    public String getWorkTypeCode() {
        return workTypeCode;
    }

    
    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
    }

    
    public Integer getRepairTypeCode() {
        return repairTypeCode;
    }

    
    public void setRepairTypeCode(Integer repairTypeCode) {
        this.repairTypeCode = repairTypeCode;
    }

    
    public String getRepairTypeDesc() {
        return repairTypeDesc;
    }

    
    public void setRepairTypeDesc(String repairTypeDesc) {
        this.repairTypeDesc = repairTypeDesc;
    }

    
    public String getOmeTagDesc() {
        return omeTagDesc;
    }

    
    public void setOmeTagDesc(String omeTagDesc) {
        this.omeTagDesc = omeTagDesc;
    }

    
    public String getWorkTypeDesc() {
        return workTypeDesc;
    }

    
    public void setWorkTypeDesc(String workTypeDesc) {
        this.workTypeDesc = workTypeDesc;
    }
}
