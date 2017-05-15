
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : DecorationDTO.java
*
* @Author : xukl
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;

import java.util.List;

import com.yonyou.dms.function.utils.validate.define.Required;

/**装潢项目定义表字段
* 
* @author zhongsw
* @date 2016年9月12日
*/
	
public class DecorationDTO {
    private Long decrodateId;//装潢项目定义表ID

    private String dealerCode;//经销商代码

    @Required
    private String labourCode;//维修项目代码

    @Required
    private String labourName;//维修项目名称

    private String spellCode;//拼音代码

    private String localLabourCode;//行管项目代码

    private String localLabourName;//行管项目名称

    @Required
    private Integer decrodateType;//装潢类型

    private String modelLabourCode;//维修车型分组代码

    @Required
    private Double stdLabourHour;//标准工时

    private Double assignLabourHour;//派工工时

    private Integer workerTypeCode;//工种代码
    
    private List<DecorationPartDTO> decorationpartItemList;//装潢子表List
    
    private Long userId;//新增和修改界面选择ID名称
    
   // private String delDecrodateId;   //记录删除配件的id


    
    
   

    public Long getDecrodateId() {
        return decrodateId;
    }

    public void setDecrodateId(Long decrodateId) {
        this.decrodateId = decrodateId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getLabourCode() {
        return labourCode;
    }

    public void setLabourCode(String labourCode) {
        this.labourCode = labourCode == null ? null : labourCode.trim();
    }

    public String getLabourName() {
        return labourName;
    }

    public void setLabourName(String labourName) {
        this.labourName = labourName == null ? null : labourName.trim();
    }

    public String getSpellCode() {
        return spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode == null ? null : spellCode.trim();
    }

    public String getLocalLabourCode() {
        return localLabourCode;
    }

    public void setLocalLabourCode(String localLabourCode) {
        this.localLabourCode = localLabourCode == null ? null : localLabourCode.trim();
    }

    public String getLocalLabourName() {
        return localLabourName;
    }

    public void setLocalLabourName(String localLabourName) {
        this.localLabourName = localLabourName == null ? null : localLabourName.trim();
    }

    public Integer getDecrodateType() {
        return decrodateType;
    }

    public void setDecrodateType(Integer decrodateType) {
        this.decrodateType = decrodateType;
    }

    public String getModelLabourCode() {
        return modelLabourCode;
    }

    public void setModelLabourCode(String modelLabourCode) {
        this.modelLabourCode = modelLabourCode == null ? null : modelLabourCode.trim();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<DecorationPartDTO> getDecorationpartItemList() {
        return decorationpartItemList;
    }

    public void setDecorationpartItemList(List<DecorationPartDTO> decorationpartItemList) {
        this.decorationpartItemList = decorationpartItemList;
    }

    public Integer getWorkerTypeCode() {
        return workerTypeCode;
    }

    public void setWorkerTypeCode(Integer workerTypeCode) {
        this.workerTypeCode = workerTypeCode;
    }
}
