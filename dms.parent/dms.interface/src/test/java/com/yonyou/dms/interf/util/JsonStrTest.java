
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.interface
*
* @File name : JsonStrTest.java
*
* @Author : zhangxc
*
* @Date : 2016年12月21日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月21日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.interf.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.interf.domains.DTO.common.RestServiceParamDto;

/**
* JSON 字符串测试类
* @author zhangxc
* @date 2016年12月21日
*/

public class JsonStrTest {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(JsonStrTest.class);
    @Test
    public void testJsonStr() {
        RestServiceParamDto paramDto = new RestServiceParamDto();
        paramDto.setUrl("test");
        paramDto.setJson("test");
        logger.debug(JSONUtil.objectToJson(paramDto));
    }
}
