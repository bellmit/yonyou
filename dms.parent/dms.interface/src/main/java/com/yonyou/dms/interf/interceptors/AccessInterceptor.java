
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.interface
*
* @File name : AccessInterceptor.java
*
* @Author : zhangxc
*
* @Date : 2016年12月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月9日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.interf.interceptors;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yonyou.dms.function.common.RestServiceConstant;
import com.yonyou.dms.function.exception.AuthForbiddenException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.http.RestServiceUtil;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.interf.constant.InterfaceConstants;

/**
* 实现权限拦截
* @author zhangxc
* @date 2016年12月9日
*/

public class AccessInterceptor extends HandlerInterceptorAdapter{

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);
    /**
     * 进行预校验
    * @author zhangxc
    * @date 2016年12月9日
    * @param request
    * @param response
    * @param handler
    * @return
    * @throws Exception
    * (non-Javadoc)
    * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 获取请求的URL
        String path = httpServletRequest.getRequestURI();
        logger.debug("into LoginFilter:请求的地址：" + path + ";请求方法：" + httpServletRequest.getMethod()+";"+httpServletRequest.getQueryString());
        // 从头信息中获得客户端加密的MD5信息
        String decodeMd5Str = httpServletRequest.getHeader(RestServiceConstant.REQUEST_URL_BODY_MD5_CODE);
        if (decodeMd5Str == null) {
            throw new AuthForbiddenException("签名不能为空");
        }
        if(!StringUtils.isNullOrEmpty(httpServletRequest.getQueryString())){
            path+="?"+URLDecoder.decode(httpServletRequest.getQueryString(),"utf-8");
        }
        // 获得body 的字符串
        String bodyStr = IOUtils.getInputStreamBody(request.getInputStream());
        String decodeMd5Check = RestServiceUtil.generateEncryptStr(path, bodyStr,InterfaceConstants.B2C_CRYPT_KEY);
        // 比较MD5是否一致
        if (!decodeMd5Str.equals(decodeMd5Check)) {
            throw new AuthForbiddenException("签名不正确");
        }
        return true;
    }

    
}
