
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.interface
*
* @File name : BodyReaderHttpServletRequestWrapper.java
*
* @Author : zhangxc
*
* @Date : 2016年12月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月12日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.interf.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.yonyou.dms.function.utils.io.IOUtils;

/**
* TODO description
* @author zhangxc
* @date 2016年12月12日
*/

public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;
    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException{
        super(request);
        body = IOUtils.getInputStreamBodyByte(request.getInputStream());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
    
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}
