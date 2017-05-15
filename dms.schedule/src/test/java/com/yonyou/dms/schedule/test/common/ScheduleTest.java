
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.schedule
*
* @File name : ScheduleTest.java
*
* @Author : zhangxc
*
* @Date : 2016年11月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月8日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.schedule.test.common;

import java.util.UUID;

import org.junit.Test;

/**
* 常规测试
* @author zhangxc
* @date 2016年11月8日
*/

public class ScheduleTest {

    
    @Test
    public void testUUID(){
        System.out.println(UUID.randomUUID().toString()); 
    }

}
