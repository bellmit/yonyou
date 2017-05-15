/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : CookieUtil.java
*
* @Author : 
*
* @Date : 2016年2月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月29日                                    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.function.utils.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.function.exception.UtilException;

/*
*
* @author 
* 获得cookiesUtil
* @date 2016年2月29日
*/

public class CookieUtil {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
    
    private CookieUtil() {
    }
    

    /*
     * @author 清空cookies
     * @date 2016年2月29日
     * @param request
     * @param response
     * @throws UtilException
     */

    public static void cleanCookie(HttpServletRequest request,HttpServletResponse response,String cookieKey) throws UtilException {
        logger.info("@@@ getValueFromCookie");
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals(cookieKey)){
                        cookie.setValue(null);
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        } catch (Exception e) {
            throw new UtilException("cleanCookie error", e);
        }
    }
    
    /**
     * 判断是否需要转码
     */
    public static boolean isNeedDecodeCookie(String orianCookie){
        return true;   
    }

    public static String getValueFromCookie(HttpServletRequest requestContext,String cookieKey){
        String value=null;
        if(requestContext.getCookies()!=null){
            for(javax.servlet.http.Cookie cookie : requestContext.getCookies()){
                if(cookie.getName().equals(cookieKey)){
                    value=cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }
}
