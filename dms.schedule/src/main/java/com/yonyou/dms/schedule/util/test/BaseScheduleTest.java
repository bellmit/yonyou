
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.schedule
*
* @File name : BaseScheduleTest.java
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
	
package com.yonyou.dms.schedule.util.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
* 定义测试的基类
* @author zhangxc
* @date 2017年2月11日
*/
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:applicationContext_schedule.xml"})
@ActiveProfiles("test")
public class BaseScheduleTest {

    public String testTenantId="TENANT_1";
}
