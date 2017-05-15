/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.

* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : LoginInfoDto.java
*
* @Author : zhangxc
*
* @Date : 2016年6月30日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年6月30日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.domain;

import java.util.Date;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
 * 记录收到权限控制的URL
 * @author  zhangxc
 * @date   2016年11月21号
 */
@Component
@Scope("session")
public class UserAccessInfoDto {
	
	  /**
     * method:module:url,分隔符使用：FrameworkConstants.ACL_RESOUCCE_SPLIT
     * 
     */
    private String[] userResouces; //用户的权限URL列表
    
    private Map<String, Map<String, Object>> dataRange;  // 用户的数据权限范围

    private String  childDepts; // 获取该用户所在部门及其下属部门的OrgalizationId
    
    //记录当前有效的token(第一个)
    private String validFirstToken;
    //记录当前有效的token(第二个)
    private String validSecodeToken;
    
    //记录token 的有效值
    private Date validTokenDate;
    
    //记录是否刷新过token
    private boolean isFirstToken = true;
    
    public String[] getUserResouces() {
        return userResouces;
    }

    public void setUserResouces(String[] userResouces) {
        this.userResouces = userResouces;
    }
    
    public Date getValidTokenDate() {
        return validTokenDate;
    }

    
    public void setValidTokenDate(Date validTokenDate) {
        this.validTokenDate = validTokenDate;
    }


    public Map<String, Map<String, Object>> getDataRange() {
        return dataRange;
    }

    public void setDataRange(Map<String, Map<String, Object>> dataRange) {
        this.dataRange = dataRange;
    }

    public String getChildDepts() {
        return childDepts;
    }

    public void setChildDepts(String childDepts) {
        this.childDepts = childDepts;
    }

    public String getValidFirstToken() {
        return validFirstToken;
    }


    
    public void setValidFirstToken(String validFirstToken) {
        this.validFirstToken = validFirstToken;
    }


    
    public String getValidSecodeToken() {
        return validSecodeToken;
    }

    public void setValidSecodeToken(String validSecodeToken) {
        this.validSecodeToken = validSecodeToken;
    }

    
    public boolean isFirstToken() {
        return isFirstToken;
    }

    
    public void setFirstToken(boolean isFirstToken) {
        this.isFirstToken = isFirstToken;
    }
}
