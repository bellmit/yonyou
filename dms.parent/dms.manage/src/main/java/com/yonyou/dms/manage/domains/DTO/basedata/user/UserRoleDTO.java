
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserRoleDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月7日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.domains.DTO.basedata.user;

/**
 * 用户角色DTO
 * 
 * @author zhanshiwei
 * @date 2017年1月7日
 */

public class UserRoleDTO {

    private Long   userId;
    private String roleCode;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

}
