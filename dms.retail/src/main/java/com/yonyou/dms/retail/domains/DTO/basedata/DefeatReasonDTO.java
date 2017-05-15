
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : DefeatResonDTO.java
*
* @Author : DuPengXin
*
* @Date : 2016年7月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月1日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* @author DuPengXin
* @date 2016年7月1日
*/

public class DefeatReasonDTO {
    
    private Long defeatReasonId;

    private String dealerCode;
    
    @Required
    private String drCode;
    
    @Required
    private String drDesc;

    private Integer isValid;

    public Long getDefeatReasonId() {
        return defeatReasonId;
    }

    public void setDefeatReasonId(Long defeatReasonId) {
        this.defeatReasonId = defeatReasonId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode == null ? null : drCode.trim();
    }

    public String getDrDesc() {
        return drDesc;
    }

    public void setDrDesc(String drDesc) {
        this.drDesc = drDesc == null ? null : drDesc.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
