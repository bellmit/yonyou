
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ModelGroupItemDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年7月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月22日    DuPengXin   1.0
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
* @date 2016年7月22日
*/

public class ModelGroupItemDTO {
    private Long itemId;

    private Long modelGroupId;
    
    @Required
    private String modelCode;
    
    @Required
    private String modelName;
    
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getModelGroupId() {
        return modelGroupId;
    }

    public void setModelGroupId(Long modelGroupId) {
        this.modelGroupId = modelGroupId;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode =modelCode;
    }
    
    @Override
    public String toString() {
        return "ModelGroupItem [itemId=" + itemId + ", modelGroupId=" + modelGroupId + ", modelCode=" + modelCode + ", modelName="
               + modelName + "]";
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }
}
