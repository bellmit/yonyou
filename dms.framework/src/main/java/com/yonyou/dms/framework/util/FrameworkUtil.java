
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FrameworkUtil.java
*
* @Author : zhangxc
*
* @Date : 2016年7月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月7日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* 提供框架层面的Util 方法
* @author zhangxc
* @date 2016年7月7日
*/

public class FrameworkUtil {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(FrameworkUtil.class);
    
    /**
     * 
    * 获取当前登录信息
    * @author zhangxc
    * @date 2016年7月7日
    * @return
     */
    public static LoginInfoDto getLoginInfo(){
        //如果包含经销商字段，则对经销商字段进行赋值
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if(!StringUtils.isNullOrEmpty(loginInfo)&&!StringUtils.isNullOrEmpty(loginInfo.getDealerCode())){
            return loginInfo;
        }
        return null;
    }
    
    
    /**
     * 
    * 判断是否有权限访问
    * @author zhangxc
     * @date 2016年8月1日
    * @param requestUrl
    * @param actionUrl
    * @return
     */
    public static boolean isCanAccess(String requestUrl,String actionUrl){
        String pattenstr = ".+/rest("+actionUrl+")$";
        Pattern patten = Pattern.compile(pattenstr);
        Matcher matcher = patten.matcher(requestUrl);
        if(matcher.find()){
//            String childUrl = matcher.group(1);
//            if(childUrl.split("/").length!=actionUrl.split("/").length){
//                return false;
//            }
            return true;
        }else{
            return false;
        }
    }
    
}
