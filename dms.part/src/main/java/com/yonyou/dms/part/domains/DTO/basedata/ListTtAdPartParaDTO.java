
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ListTtAdPartParaDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.List;

/**
 * @author zhanshiwei
 * @date 2017年4月13日
 */

public class ListTtAdPartParaDTO {

    private List<TtAdPartParaDTO> partList;

    public List<TtAdPartParaDTO> getPartList() {
        return partList;
    }

    public void setPartList(List<TtAdPartParaDTO> partList) {
        this.partList = partList;
    }

}
