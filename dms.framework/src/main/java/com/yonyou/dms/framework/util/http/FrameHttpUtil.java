
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FrameHttpUtil.java
*
* @Author : zhangxc
*
* @Date : 2017年1月2日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月2日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.util.http;

import java.net.URLEncoder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.function.exception.UtilException;

/**
 * 框架的http 方法
 * 
 * @author zhangxc
 * @date 2017年1月2日
 */

public class FrameHttpUtil {

    /**
     * 获取文件名称
     * 
     * @author zhangxc
     * @date 2017年1月2日
     * @param request
     * @param name
     * @return
     */
    public static void setExportFileName(HttpServletRequest request,HttpServletResponse response, String fileName) {
        try {
            String userAgent = request.getHeader("user-agent");
            System.out.println(userAgent);
            if (userAgent != null && (userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0
                || userAgent.indexOf("Safari") >= 0)) {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF8"); // 其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + fileName+"\"");
            String mineType = new MimetypesFileTypeMap().getContentType(fileName);
            response.setContentType(mineType);
            
        } catch (Exception e) {
            throw new UtilException("获取导出文件名称失败", e);
        }
    }
    
    
}
