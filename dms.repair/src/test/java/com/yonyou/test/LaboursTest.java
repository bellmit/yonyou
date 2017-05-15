
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LaboursTest.java
*
* @Author : ZhengHe
*
* @Date : 2016年7月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月12日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.yonyou.dms.framework.domain.FrameworkParamBean;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourPriceDTO;

/**
* RESTTEMPLATE测试
* @author ZhengHe
* @date 2016年7月12日
*/
public class LaboursTest {

    @Autowired
    private static RestTemplate rt;
    
    @Autowired
    FrameworkParamBean frameworkParam;
    
    public HttpServletRequest request; 
    
    public static String url="http://localhost:8080/dms.web/repair/rest/basedata/labours";
    
    public void put(){
        LabourPriceDTO lpd=new LabourPriceDTO();
        lpd.setLabourPrice(123.45);
        Long id=(long) 1;
        TestUtil tu=new TestUtil();
        tu.put(url+"/{id}",lpd,id);
    }
    public String get(){
        TestUtil tu=new TestUtil();
        Map<String, Object> queryParam=new HashMap<String, Object>();
        queryParam.put("id",1);
        String result=tu.get(url+"/{id}",queryParam);
        return result;
    }
    public Map<String, HashMap<String, String>>getd(){
        rt=new RestTemplate();
        Map<String, Object> queryParam=new HashMap<String, Object>();
        queryParam.put("OEM_TAG", 10041001);
        queryParam.put("LABOUR_PRICE_CODE", "1");
        Map<String, HashMap<String, String>> result= rt.getForObject("http://localhost:8080/dms.web/manage/rest/basedata/dealers", Map.class);
        return result;
    }
    
    public String postTest(){
        TestUtil tu=new TestUtil();
        LabourPriceDTO lpdto=new LabourPriceDTO();
        lpdto.setLabourPrice(544.11);
        lpdto.setLabourPriceCode("3432");
        String result=tu.post(url, lpdto);
        return result;
    }
    @Test
    public void test(){
        TestUtil tu=new TestUtil();
        Map<String, Object> queryParam=new HashMap<String, Object>();
        queryParam.put("id",1);
        queryParam.put("OEM_TAG", 10041001);
        queryParam.put("LABOUR_PRICE_CODE", "1");
        String result=tu.get(url,queryParam);
        Assert.assertNull("非空", result);
    }
    
}
