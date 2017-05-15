
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : FrameworkParamBean.java
*
* @Author : zhangxc
*
* @Date : 2016年7月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月1日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* 存放系统可能发生变化的变量
* @author zhangxc
* @date 2016年7月1日
*/
@Component
public class FrameworkParamBean {

    /**
     * 系统tenantKey的名称，如Dealer_code
     */
    @Value("${f4.mvc.tenant.key}")  
    private String tenantKey;
    
    /**
     * 权限拦截用户名的key
     */
    @Value("${f4.mvc.aclUser.key}")  
    private String aclUserKey;
    
    /**
     * 匿名用户的ID
     */
    @Value("${f4.mvc.aclUser.anonymUID}")  
    private String aclAnonymUID;
    
    /**
     * 数据库类型
     */
    @Value("${dms.framework.dbType}")  
    private String dbType;

    
    public String getTenantKey() {
        return tenantKey;
    }

    
    public void setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
    }


    
    public String getAclUserKey() {
        return aclUserKey;
    }


    
    public void setAclUserKey(String aclUserKey) {
        this.aclUserKey = aclUserKey;
    }


    
    public String getAclAnonymUID() {
        return aclAnonymUID;
    }


    
    public void setAclAnonymUID(String aclAnonymUID) {
        this.aclAnonymUID = aclAnonymUID;
    }


    
    public String getDbType() {
        return dbType;
    }


    
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
