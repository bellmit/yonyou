
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : DmsPrepareResourceImpl.java
*
* @Author : zhangxc
*
* @Date : 2016年11月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月17日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.interceptors.acl;

import javax.servlet.http.HttpServletRequest;

import com.yonyou.dms.framework.common.FrameworkConstants;
import com.yonyou.f4.common.acl.PrepareResource;


/**
* 定义resouce 的格式
* @author zhangxc
* @date 2016年11月17日
*/

public class DmsPrepareResourceImpl implements PrepareResource {

    /**
    * @author zhangxc
    * @date 2016年11月17日
    * @param arg0
    * @return
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.f4.common.acl.PrepareResource#prepare(java.lang.Object)
    */

    @Override
    public Object prepare(HttpServletRequest request) throws Exception {
        return request.getMethod()+FrameworkConstants.ACL_RESOUCCE_SPLIT+request.getRequestURI();
    }
}
