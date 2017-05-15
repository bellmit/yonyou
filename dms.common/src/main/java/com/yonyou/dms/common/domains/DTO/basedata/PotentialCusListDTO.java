
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : PotentialCusListDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年10月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月18日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.List;

/**
* 
* @author zhanshiwei
* @date 2016年10月18日
*/

public class PotentialCusListDTO {
    private List<PotentialCusDTO> cusList;

    
    public List<PotentialCusDTO> getCusList() {
        return cusList;
    }

    
    public void setCusList(List<PotentialCusDTO> cusList) {
        this.cusList = cusList;
    }
}
