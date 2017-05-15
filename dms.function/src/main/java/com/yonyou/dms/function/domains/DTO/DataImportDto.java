
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : DataImportDto.java
*
* @Author : zhangxc
*
* @Date : 2016年8月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月16日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.domains.DTO;

/**
* 数据导入DTO
* @author zhangxc
* @date 2016年8月16日
*/

public class DataImportDto {

    private Integer rowNO;
    private String errorMsg;
    
    public Integer getRowNO() {
        return rowNO;
    }
    
    public void setRowNO(Integer rowNO) {
        this.rowNO = rowNO;
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
