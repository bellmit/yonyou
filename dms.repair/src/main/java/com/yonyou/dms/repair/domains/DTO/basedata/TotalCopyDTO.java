
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : TotalCopyDTO.java
*
* @Author : rongzoujie
*
* @Date : 2016年8月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月23日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.domains.DTO.basedata;

/**
 * TODO description
 * 
 * @author rongzoujie
 * @date 2016年8月23日
 */

public class TotalCopyDTO {

    private String srcModeLabourCode;//原车型组代码
    private String destModelLabourCode;//复制目标车型组代码

    public String getSrcModeLabourCode() {
        return srcModeLabourCode;
    }

    public void setSrcModeLabourCode(String srcModeLabourCode) {
        this.srcModeLabourCode = srcModeLabourCode;
    }

    public String getDestModelLabourCode() {
        return destModelLabourCode;
    }

    public void setDestModelLabourCode(String destModelLabourCode) {
        this.destModelLabourCode = destModelLabourCode;
    }

}
