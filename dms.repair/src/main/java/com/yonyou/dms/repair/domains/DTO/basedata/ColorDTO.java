
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ColorDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年8月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月11日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 车辆颜色
* @author Benzc
* @date 2016年12月22日
*/

public class ColorDTO {

    private String dealerCode;
    
    @Required
    private String colorCode;
    
    @Required
    private String colorName;

    private Integer oemTag;
    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    public String getColorCode() {
        return colorCode;
    }

    
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    
    public String getColorName() {
        return colorName;
    }

    
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    
    public Integer getOemTag() {
        return oemTag;
    }

    
    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }

}
