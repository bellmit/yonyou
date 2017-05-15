
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : PlatformDTO.java
*
* @Author : Administrator
*
* @Date : 2017年2月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月8日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.domains.DTO.platform;



/**
* TODO description
* @author Administrator
* @date 2017年2月8日
*/

public class PlatformDto {
    
    private String    noList;//隐藏字段 ，用于预计成交时段 
    
    private Long   exceptTimesRange;//预计成交时段
    
    public String getNoList() {
        return noList;
    }

    
    public void setNoList(String noList) {
        this.noList = noList;
    }


    
    public Long getExceptTimesRange() {
        return exceptTimesRange;
    }


    
    public void setExceptTimesRange(Long exceptTimesRange) {
        this.exceptTimesRange = exceptTimesRange;
    }
    
   
    
    

}
