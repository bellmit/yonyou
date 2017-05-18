
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserCopyDto.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月16日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.domains.DTO.basedata.user;

/**
 * 权限复制
 * 
 * @author zhanshiwei
 * @date 2017年1月16日
 */

public class UserCopyDto {

    private Long parentId;
    private Long targetId;
    private Integer isPermCheckRadio;

    public Long getParentId() {
        return parentId;
    }

    
    public Integer getIsPermCheckRadio() {
        return isPermCheckRadio;
    }

    
    public void setIsPermCheckRadio(Integer isPermCheckRadio) {
        this.isPermCheckRadio = isPermCheckRadio;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

}
