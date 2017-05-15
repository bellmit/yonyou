
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : BasicParametersDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.domains.DTO.basedata;

import java.util.List;

import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;

/**
 * 
 * 基本参数设置ListDTO
 * @author zhanshiwei
 * @date 2016年7月19日
 */

public class BasicParametersListDTO {
    List<BasicParametersDTO> paramList;

    
    public List<BasicParametersDTO> getParamList() {
        return paramList;
    }

    
    public void setParamList(List<BasicParametersDTO> paramList) {
        this.paramList = paramList;
    }
}
