
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : AttachUnitDto.java
*
* @Author : Administrator
*
* @Date : 2016年12月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月16日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 车辆信息挂靠单位DTO
* TODO description
* @author Administrator
* @date 2016年12月16日
*/

public class AttachUnitDto {
    
    @Required
    private String unitAttachcode;
    
    private String unitAttachname;

    
    public String getUnitAttachcode() {
        return unitAttachcode;
    }

    
    public void setUnitAttachcode(String unitAttachcode) {
        this.unitAttachcode = unitAttachcode;
    }

    
    public String getUnitAttachname() {
        return unitAttachname;
    }

    
    public void setUnitAttachname(String unitAttachname) {
        this.unitAttachname = unitAttachname;
    }
    
    
    
    
    
    
    
}
