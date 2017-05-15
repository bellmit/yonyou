
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : ImportResultDto.java
*
* @Author : zhangxc
*
* @Date : 2016年8月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月18日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.domains.DTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
* 定义excel 导入反馈结果
* @author zhangxc
* @date 2016年8月18日
*/

public class ImportResultDto<T extends DataImportDto> implements Serializable{
    
    private static final long serialVersionUID = -5294248904920920280L;
    
    private boolean isSucess;
    private ArrayList<T> dataList;
    private ArrayList<T> errorList;
    
    public boolean isSucess() {
        return isSucess;
    }
    
    public void setSucess(boolean isSucess) {
        this.isSucess = isSucess;
    }
    
    public ArrayList<T> getDataList() {
        return dataList;
    }
    
    public void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
    }
    
    public ArrayList<T> getErrorList() {
        return errorList;
    }
    
    public void setErrorList(ArrayList<T> errorList) {
        this.errorList = errorList;
    }
}
