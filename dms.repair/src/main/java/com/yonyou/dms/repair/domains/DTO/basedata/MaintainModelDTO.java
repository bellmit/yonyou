
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : MaintainModelDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年7月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月12日    DuPengXin   1.0
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
* @date 2016年7月12日
*/

public class MaintainModelDTO {
    private Long modelGroupId;

    private String dealerCode;
    
    @Required
    private String modelLabourCode;
    
    @Required
    private String modelLabourName;

    private Integer oemTag;

    private Integer isValid;
    
    private ModelGroupItemDTO modelGroupItemDto;
    
    

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getModelLabourCode() {
        return modelLabourCode;
    }

    public void setModelLabourCode(String modelLabourCode) {
        this.modelLabourCode = modelLabourCode == null ? null : modelLabourCode.trim();
    }

    public String getModelLabourName() {
        return modelLabourName;
    }

    public void setModelLabourName(String modelLabourName) {
        this.modelLabourName = modelLabourName == null ? null : modelLabourName.trim();
    }

    public Integer getOemTag() {
        return oemTag;
    }

    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    
    public ModelGroupItemDTO getModelGroupItemDto() {
        return modelGroupItemDto;
    }

    
    public void setModelGroupItemDto(ModelGroupItemDTO modelGroupItemDto) {
        this.modelGroupItemDto = modelGroupItemDto;
    }

    public Long getModelGroupId() {
        return modelGroupId;
    }

    public void setModelGroupId(Long modelGroupId) {
        this.modelGroupId = modelGroupId;
    }

    
}
