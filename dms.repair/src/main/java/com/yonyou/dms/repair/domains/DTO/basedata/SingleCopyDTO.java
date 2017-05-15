/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : SingleCopyDTO.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月8日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.domains.DTO.basedata;


public class SingleCopyDTO {
    private String modelGroupIds;//车型项目组id
    private String modeLabourCode;//车型组code
    private String labourCode;//维修项目代码
    private String labourName;//维修项目名称
    private String spellCode;//拼音代码
    private String localLabourCode;//行管项目代码
    private String localLabourName;//行管项目名称
    private Integer repairTypeCode;//维修分类
    private String workTypeCode;//工种代码
    private Double stdLabourHour;//标准工时
    private Double assignLabourHour;//派工工时
    private Double claimLabHour;//索赔工时
    private String operationCode;//索赔代码
    private String mainGroupCode;//主分类码
    private String subGroupCode;//二级分类码
    private String repairTypeName;//维修项目类型名
    private String workTypeName;//工种类型名
    private Integer omgTag;
    
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
    
    public Integer getRepairTypeCode() {
        return repairTypeCode;
    }
    
    public void setRepairTypeCode(Integer repairTypeCode) {
        this.repairTypeCode = repairTypeCode;
    }
    
    public String getWorkTypeCode() {
        return workTypeCode;
    }
    
    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
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
    
    public void setClaimLabHour(Double claimLabHour) {
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

    
    public String getRepairTypeName() {
        return repairTypeName;
    }

    
    public void setRepairTypeName(String repairTypeName) {
        this.repairTypeName = repairTypeName;
    }

    
    public String getWorkTypeName() {
        return workTypeName;
    }

    
    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    
    public String getModelGroupIds() {
        return modelGroupIds;
    }

    
    public void setModelGroupIds(String modelGroupIds) {
        this.modelGroupIds = modelGroupIds;
    }

    
    public String getModeLabourCode() {
        return modeLabourCode;
    }

    
    public void setModeLabourCode(String modeLabourCode) {
        this.modeLabourCode = modeLabourCode;
    }
    
    public Integer getOmgTag() {
        return omgTag;
    }
    
    public void setOmgTag(Integer omgTag) {
        this.omgTag = omgTag;
    }

}
