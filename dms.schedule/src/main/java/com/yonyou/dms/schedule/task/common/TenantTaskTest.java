
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.schedule
*
* @File name : TenantTaskTest.java
*
* @Author : zhangxc
*
* @Date : 2017年2月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月11日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.schedule.task.common;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.dms.schedule.service.impl.SADMS054Service;
import com.yonyou.dms.schedule.util.test.BaseScheduleTest;

/**
* 租户唯一类型的定时任务测试
* @author zhangxc
* @date 2017年2月11日
*/
public class TenantTaskTest extends BaseScheduleTest{

    //定时任务清理
    @Autowired
    SADMS054Service fileCleanSingleTask;
    
    @Test
    public void testFileClean() throws Exception{
        //自行控制事务
        fileCleanSingleTask.getSADMS054();
        
    }
}

