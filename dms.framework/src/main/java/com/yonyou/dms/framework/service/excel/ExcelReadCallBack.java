
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : ExcelReadCallBack.java
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
	
package com.yonyou.dms.framework.service.excel;



/**
* 定义excel 解悉后的回调函数
* @author zhangxc
* @date 2016年8月12日
*/

public interface ExcelReadCallBack<T> {
    
    
    /**
    * 解析后方法回调
    * @author zhangxc
    * @date 2016年8月16日
    * @param rowDto
    * @param isValidateSucess：excel 全部数据是否存在错误
    * @return
    */
    	
    void readRowCallBack(T rowDto,boolean isValidateSucess);
}
