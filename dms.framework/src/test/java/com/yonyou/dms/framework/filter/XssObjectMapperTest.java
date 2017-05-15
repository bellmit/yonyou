
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : XssObjectMapper.java
*
* @Author : zhangxc
*
* @Date : 2016年9月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月6日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.filter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.yonyou.dms.framework.DAO.PageInfoDto;



/**
* 测试
* @author zhangxc
* @date 2016年9月6日
*/

public class XssObjectMapperTest {
    
    @Test
    public void testXssObjectMapper() throws FileNotFoundException, IOException{
        ObjectMapper objectMapper = new XssObjectMappper();
        ObjectWriter objectWrite = objectMapper.writer();
        PageInfoDto pageDto = new PageInfoDto();
        pageDto.setTotal(10l);
        List<Map> resultList = new ArrayList<>();
        Map map1 = new HashMap();
        map1.put("name", "<script>alert('hello，gaga!');</script>");
        map1.put("age", 20);
        resultList.add(map1);
        pageDto.setRows(resultList);
        objectWrite.writeValueAsString(pageDto);
        
    }
}
