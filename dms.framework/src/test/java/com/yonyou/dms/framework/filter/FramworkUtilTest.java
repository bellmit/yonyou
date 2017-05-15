
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FramworkUtilTest.java
*
* @Author : zhangxc
*
* @Date : 2016年8月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月1日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.filter;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* 执行单元测试
* @author zhangxc
* @date 2016年8月1日
*/

public class FramworkUtilTest {

    /**
     * Test method for {@link com.yonyou.dms.framework.util.FrameworkUtil#isCanAccess(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testIsCanAccess() {
       assertTrue(FrameworkUtil.isCanAccess("/dms.web/manage/rest/users", "/users"));
       assertTrue(!FrameworkUtil.isCanAccess("/dms.web/manage/rest/users/export", "/users"));
       assertTrue(FrameworkUtil.isCanAccess("/dms.web/manage/rest/users/123/456/dd", "/users/{id}/{item}/{ee}"));
       assertTrue(!FrameworkUtil.isCanAccess("/dms.web/manage/rest/users", "/users/{id}"));
    }
    
    @Test
    public void testReg(){
        System.out.println(StringUtils.isMatcherPatten("^(?i)(txt|ini|md)$","TxT"));
        System.out.println("##@1".split("##@").length);
    }
    
    @Test
    public void testRegNumber(){
        System.out.println(StringUtils.isMatcherPatten(".+/rest/\\w+/.*\\d+.*","/dms.web/report/rest/users/12/export/fdf"));
    }
    
    

    
}
