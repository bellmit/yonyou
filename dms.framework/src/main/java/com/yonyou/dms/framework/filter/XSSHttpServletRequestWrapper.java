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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.yonyou.dms.function.utils.common.StringUtils;

/*
*不再使用
* @author zhangxianchao
* XSSHttpServletRequestWrapper
* @date 2016年2月23日
*/

public class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {
    // 定义日志接口
//    private static final Logger logger = LoggerFactory.getLogger(XSSHttpServletRequestWrapper.class);
    
    public XSSHttpServletRequestWrapper(HttpServletRequest request){
        super(request);
    }

    @Override
    public String getHeader(String name) {
        return StringUtils.escapeHtml(super.getHeader(name));
    }

    @Override
    public String getQueryString() {
        return StringUtils.escapeHtml(super.getQueryString());  
    }

    @Override
    public String getParameter(String name) {
        return StringUtils.escapeHtml(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = StringUtils.escapeHtml(values[i]);
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }
}
