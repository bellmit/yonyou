
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : NumberFormatTest.java
*
* @Author : zhangxc
*
* @Date : 2016年8月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月31日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.service;

import java.text.DecimalFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.utils.common.NumberUtil;


/**
* 数字格式化Test
* @author zhangxc
* @date 2016年8月31日
*/
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:applicationContext_common.xml"})
@ActiveProfiles("test")
public class NumberFormatTest {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(NumberFormatTest.class);
    @Autowired
    CommonNoService noService;
    
    @Test
    public void test() {
        double value = 100000000.00001;
        logger.debug(NumberUtil.getShortString(value));
        logger.debug(String.valueOf(value));
        
        value = 100000000;
        DecimalFormat df = new DecimalFormat("0.########");
        String formatValue = df.format(value);
        logger.debug(formatValue);
        String[] digitArray = formatValue.split("\\.");
        logger.debug(digitArray.length+"");
        if(digitArray.length>=1){
            int digitLength = digitArray[0].length();
            logger.debug(digitLength+"");
        }
        
    }

}
