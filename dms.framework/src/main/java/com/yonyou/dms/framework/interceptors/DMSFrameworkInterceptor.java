
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : LoginInterceptor.java
*
* @Author : zhangxc
*
* @Date : 2016年12月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月10日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.interceptors;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domain.RequestPageInfoDto;
import com.yonyou.dms.framework.domain.UserAccessInfoDto;
import com.yonyou.dms.framework.service.LoginFrameworkService;
import com.yonyou.dms.framework.util.acl.AccessUrlUtils;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.AuthForbiddenException;
import com.yonyou.dms.function.exception.AuthLoginOutException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.cookie.CookieUtil;

/**
* 对DMS 框架进行常规拦截
* @author zhangxc
* @date 2016年12月10日
*/

public class DMSFrameworkInterceptor extends HandlerInterceptorAdapter{
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(DMSFrameworkInterceptor.class);
    
    /**
     * Spring 的拦截器接口
    * @author zhangxc
    * @date 2017年1月15日
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
    	logger.debug("enter DMSFrameworkInterceptor preHandle");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //获取请求的URL
        String path = httpServletRequest.getRequestURI();
        logger.debug("into LoginFilter:请求的地址："+path+";请求方法："+httpServletRequest.getMethod());
        if (path.indexOf("/web/rest/common/login")!=-1||path.indexOf("/manage/rest/basedata/dealers")!=-1) {
            return true;
        }
        
        //上传控件特殊处理
        if (path.indexOf("/web/rest/upload/uploadjpg")!=-1) {
            return true;
        }
        
        
        //判断是否登陆
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        
        if(loginInfo==null || StringUtils.isNullOrEmpty(loginInfo.getUserAccount())){
            throw new AuthLoginOutException("用户未登录");
        }
        
        //获得token 的值
        UserAccessInfoDto userAccessInfoDto = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
/*      if(!userAccessInfoDto.isFirstToken()){
            if(System.currentTimeMillis() - userAccessInfoDto.getValidTokenDate().getTime()>=10*60*1000){
                throw new AuthLoginOutException("访问验证超时，请刷新重试");
            }
            String urlToken = request.getParameter("urlToken");
            if(!StringUtils.isNullOrEmpty(urlToken)&&!urlToken.equals(userAccessInfoDto.getValidSecodeToken())&&!urlToken.equals(userAccessInfoDto.getValidFirstToken())){
                throw new AuthLoginOutException("访问不合法");
            }
            
            if(StringUtils.isNullOrEmpty(urlToken)&&!AccessUrlUtils.isApplicationDefaultUrl(httpServletRequest.getRequestURI(),httpServletRequest.getMethod())){
                throw new AuthLoginOutException("访问不合法");
            }
        }*/
        
        String requestLocale;
        if((requestLocale = CookieUtil.getValueFromCookie(httpServletRequest,"language"))!=null){
            loginInfo.setLocale(new Locale(requestLocale));
        }
        
        //判断是否有功能权限
        String dmsFuncId = httpServletRequest.getParameter("dmsFuncId");
        if(!StringUtils.isNullOrEmpty(dmsFuncId)){
            LoginFrameworkService loginService = ApplicationContextHelper.getBeanByType(LoginFrameworkService.class);
            Long funcId = Long.parseLong(dmsFuncId);
            boolean isCanAccess = loginService.checkIsCanAccess(funcId, path,httpServletRequest.getMethod());
            if(!isCanAccess){
                logger.info("用户没有此功能的权限:"+funcId+";url:"+path+";method"+httpServletRequest.getMethod());
                throw new AuthForbiddenException("用户没有此功能的权限");
            }
        }
        
        //判断是否判断分页信息
        if(StringUtils.isEqualsNoCasetive(httpServletRequest.getMethod(),"GET")&&!StringUtils.isNullOrEmpty(httpServletRequest.getParameter("limit"))){
            RequestPageInfoDto requestPageInfoDto = ApplicationContextHelper.getBeanByType(RequestPageInfoDto.class);
            requestPageInfoDto.setLimit(httpServletRequest.getParameter("limit"));
            requestPageInfoDto.setOffset(httpServletRequest.getParameter("offset"));
            requestPageInfoDto.setSort(httpServletRequest.getParameter("sort"));
            requestPageInfoDto.setOrder(httpServletRequest.getParameter("order"));
        }
        logger.debug("out DMSFrameworkInterceptor sucess");
        return true;
    }

}
