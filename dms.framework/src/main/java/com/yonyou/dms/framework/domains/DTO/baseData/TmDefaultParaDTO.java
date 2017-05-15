
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : TmDefaultParaDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月18日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.domains.DTO.baseData;

/**
 * 基础参数
 * 
 * @author zhanshiwei
 * @date 2017年1月18日
 */

public class TmDefaultParaDTO {

    private String itemDesc;
    private String itemCode;
    private String defaultValue;
    private Integer isCheckbox;

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc == null ? null : itemDesc.trim();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    
    public Integer getIsCheckbox() {
        return isCheckbox;
    }

    
    public void setIsCheckbox(Integer isCheckbox) {
        this.isCheckbox = isCheckbox;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }
}
