
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : TestUtil.java
*
* @Author : ZhengHe
*
* @Date : 2016年7月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月13日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
* TODO description
* @author ZhengHe
* @date 2016年7月13日
*/

public class TestUtil {
    
    @Autowired
    private static RestTemplate rt;

    public void newRt(){
        rt=new RestTemplate();
    }
    
    public String get(String url,Map<String,Object> params){
       newRt();
       return rt.getForObject(url,String.class,params);
    }
    
    public String get(String url){
        newRt();
        return rt.getForObject(url,String.class);
    }
    
    public String post(String url,Object obj){
        newRt();
        return rt.postForObject(url,obj, String.class);
    }
    
    public String put(String url,Object obj,Long id){
        newRt();
        rt.put(url,obj, id);
        return obj.toString();
    }
}
