
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : AbstractExcelReadCallBack.java
*
* @Author : zhangxc
*
* @Date : 2016年8月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月12日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service.excel.impl;

import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.function.domains.DTO.DataImportDto;


/**
* TODO description
* @author zhangxc
* @date 2016年8月12日
*/

public class AbstractExcelReadCallBack<T extends DataImportDto>{
    
    private Class<T> dtoClass;
    private ExcelReadCallBack<T> excelReadCallBack;
    public AbstractExcelReadCallBack(Class<T> dtoClass){
        this.dtoClass = dtoClass;
    }
    
    public AbstractExcelReadCallBack(Class<T> dtoClass,ExcelReadCallBack<T> callBack){
        this.dtoClass = dtoClass;
        this.excelReadCallBack = callBack;
    }
    
    public Class<T> getDtoClass() {
        return dtoClass;
    }
    
    public void setDtoClass(Class<T> dtoClass) {
        this.dtoClass = dtoClass;
    }

    
    public ExcelReadCallBack<T> getExcelReadCallBack() {
        return excelReadCallBack;
    }

    
    public void setExcelReadCallBack(ExcelReadCallBack<T> excelReadCallBack) {
        this.excelReadCallBack = excelReadCallBack;
    }
}
