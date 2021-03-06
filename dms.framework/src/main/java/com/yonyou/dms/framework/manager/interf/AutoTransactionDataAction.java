
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.schedule
*
* @File name : ScheduleAutoTransaction.java
*
* @Author : zhangxc
*
* @Date : 2016年11月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月9日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.manager.interf;



/**
* 定义接口
* @author zhangxc
* @date 2016年11月9日
*/

public interface AutoTransactionDataAction<E> {
    /**
     * 
    * 自动事务控制
    * @author zhangxc
    * @date 2016年12月22日
    * @param dataValue
     */
    void autoTransAction(E dataValue);
}
