
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : AclDmsResouce.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.util.acl.AccessUrlUtils;
import com.yonyou.f4.common.acl.AclResource;


/**
* 实现AclResource 方法
* @author zhangxc
* @date 2016年11月17日
*/

public class AclDmsResouce extends AclResource {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(AclDmsResouce.class);
    private String module;
    private String actionUrl;
    private String method;
    /**
     * 
    * 构造对象
    * @author zhangxc
    * @date 2016年11月17日
    * @param module
    * @param actionUrl
    * @param method
     */
    public AclDmsResouce(String method,String module,String actionUrl){
        this.module = module;
        this.actionUrl = actionUrl;
        this.method = method;
    }
    /**
    * @author zhangxc
    * @date 2016年11月17日
    * @param obj:method:requestUrl 
    * @return
    * (non-Javadoc)
    * @see com.yonyou.f4.common.acl.AclResource#check(java.lang.Object)
    */
  
    @Override
    public boolean check(Object obj) {
        return AccessUrlUtils.isValidUrl(obj.toString(), this.method, this.module, this.actionUrl);
    }

}
