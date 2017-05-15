/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : TestController.java
*
* @Author : zhangxc
*
* @Date : 2016年6月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年6月29日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/*
* 不再使用
* @author zhangxianchao
* XSSFilter
* @date 2016年2月23日
*/

public class XSSFilter implements Filter {
    // 定义日志接口
//    private static final Logger logger = LoggerFactory.getLogger(XSSFilter.class);
    
    /**
     * @author zhangxianchao
     * @date 2016年2月23日
     * @param filterConfig
     * @throws ServletException (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do nothing because of must override
    }

    /**
     * @author zhangxianchao
     * @date 2016年2月23日
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        XSSHttpServletRequestWrapper xssRequest = new XSSHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    /**
     * @author zhangxianchao
     * @date 2016年2月23日 (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // Do nothing because of must override
    }

}
