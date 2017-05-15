
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : TmDefaultParaListDTO.java
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
	
package com.yonyou.dms.manage.domains.DTO.basedata;

import java.util.List;

import com.yonyou.dms.framework.domains.DTO.baseData.TmDefaultParaDTO;

/**
* 基础参数设置
* @author zhanshiwei
* @date 2017年1月18日
*/

public class TmDefaultParaListDTO {
    List<TmDefaultParaDTO> paramList;

    
    public List<TmDefaultParaDTO> getParamList() {
        return paramList;
    }

    
    public void setParamList(List<TmDefaultParaDTO> paramList) {
        this.paramList = paramList;
    }
}
